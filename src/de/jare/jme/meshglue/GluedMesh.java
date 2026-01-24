/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.jme.meshglue;

import de.jare.ndimcol.primfloat.ArrayTapeFloat;
import de.jare.ndimcol.primshort.ArrayTapeShort;
import java.util.ArrayList;

/**
 * Assembles ("glues") multiple {@link GluableSingeleMesh} fragments into a single combined mesh. This class performs
 * vertex-buffer concatenation, index-buffer offsetting, and structural validation based on a shared {@link GlueConfig}.
 *
 * <p>
 * A {@code GluedMesh} acts as a lightweight mesh builder: individual mesh atoms are added, validated, merged, and
 * finally written into a new {@link GluableSingeleMesh} instance that contains the combined vertex and index data.
 * </p>
 *
 * <p>
 * All atoms must share the same {@link GlueConfig}. Each atom must contain attribute arrays of equal vertex count, and
 * the index buffer must reference only local vertices. During assembly, indices are offset to match their new global
 * positions.
 * </p>
 *
 * @author Janusch Renteantus
 */
public class GluedMesh {

    /**
     * The vertex layout configuration shared by all mesh atoms.
     */
    private final GlueConfig config;

    /**
     * The list of mesh fragments to be merged.
     */
    private final ArrayList<GluableSingeleMesh> atoms;

    /**
     * The final merged mesh created after {@link #calculate()}.
     */
    private GluableSingeleMesh glued;

    /**
     * Creates a new mesh assembler using the given configuration.
     *
     * @param config The shared vertex layout configuration.
     */
    public GluedMesh(GlueConfig config) {
        this.atoms = new ArrayList<>();
        this.glued = null;
        this.config = config;
    }

    /**
     * Adds a mesh fragment to the collection. If a glued mesh already exists, the new fragment is merged directly into
     * the existing combined mesh without requiring a full rebuild.
     *
     * <p>
     * This enables incremental, on-the-fly mesh growth where new atoms can be appended efficiently at runtime.
     * </p>
     *
     * @param atom The mesh fragment to add.
     */
    public void add(GluableSingeleMesh atom) {
        atoms.add(atom);

        // If a glued mesh already exists, merge the new atom immediately
        if (glued != null) {
            enhance(atom);
        }
    }

    /**
     * Removes all previously added mesh fragments.
     */
    public void clear() {
        atoms.clear();
        this.glued = null;
    }

    /**
     * Builds the final merged mesh by concatenating all vertex attributes and offsetting index buffers. This method
     * performs:
     *
     * <ul>
     * <li>Validation of attribute sizes</li>
     * <li>Index offsetting</li>
     * <li>Concatenation of all vertex attribute arrays</li>
     * <li>Creation of a new {@link GluableSingeleMesh} containing the result</li>
     * </ul>
     *
     * @throws IllegalArgumentException If any atom uses a different {@link GlueConfig} or contains malformed attribute
     * arrays.
     *
     * @throws IndexOutOfBoundsException If the merged vertex count exceeds the range of {@code short} indexing (approx.
     * 32k vertices).
     */
    public void calculate() {

        if (atoms.isEmpty()) {
            glued = new GluableSingeleMesh(config);
            return;
        }

        // Global vertex offset for index-buffer adjustment
        int offset = 0;

        // Number of attribute channels (e.g., Position, TexCoord, Color)
        final int length = config.componentsCount();

        // Prepare dynamic buffers for each attribute channel
        final ArrayTapeFloat[] content = new ArrayTapeFloat[length];
        for (int i = 0; i < length; i++) {
            content[i] = new ArrayTapeFloat();
        }

        // Combined index buffer
        final ArrayTapeShort indexbuffer = new ArrayTapeShort();

        // Process each mesh fragment
        for (GluableSingeleMesh atom : atoms) {

            // Ensure all atoms use the same configuration
            if (atom.getConfig() != config) {
                throw new IllegalArgumentException("Bad config.");
            }

            // Merge index buffer with offset
            short[] atomIndexbuffer = atom.getIndexbuffer();
            for (int i = 0; i < atomIndexbuffer.length; i++) {
                indexbuffer.add((short) (atomIndexbuffer[i] + offset));
            }

            // Determine vertex count of this atom (based on first attribute)
            int step = atom.getContent(0).length / config.getComponents()[0];

            // Validate that all attributes have the same vertex count
            for (int i = 1; i < length; i++) {
                int expected = atom.getContent(i).length / config.getComponents()[i];
                if (step != expected) {
                    throw new IllegalArgumentException("Single mesh is malformed.");
                }
            }

            // Store vertex count inside the atom (optional metadata)
            atom.setAtomOffset(offset);

            // Increase global offset for next atom
            offset += step;

            // Append all attribute arrays
            for (int i = 0; i < length; i++) {
                content[i].addAll(atom.getContent(i));
            }

            // Ensure short indexing does not overflow
            if (offset > 32760) {
                throw new IndexOutOfBoundsException(
                        "Offset is too big for short indexing. Split the mesh set."
                );
            }
        }

        // Create final merged mesh
        GluableSingeleMesh atom = atoms.getFirst();
        glued = new GluableSingeleMesh(config, indexbuffer.toArray(),
                atom.getPositionIndex(), atom.getTexCoordIndex());

        // Assign merged attribute arrays
        for (int i = 0; i < length; i++) {
            glued.setContent(i, content[i].toArray());
        }
    }

    /**
     * Merges a newly added mesh fragment ("atom") into the already glued mesh. This method performs an incremental
     * update instead of rebuilding the entire mesh from scratch.
     *
     * <p>
     * The operation includes:
     * </p>
     * <ul>
     * <li>Validating the atom's configuration</li>
     * <li>Computing the correct global vertex offset</li>
     * <li>Appending the atom's index buffer with offset adjustment</li>
     * <li>Appending all vertex attribute arrays</li>
     * <li>Updating the existing glued mesh in-place</li>
     * </ul>
     *
     * <p>
     * This method is designed for real-time mesh growth, such as dynamic vegetation, particle-like geometry, or
     * procedurally expanding structures.
     * </p>
     *
     * @param atom The mesh fragment to merge into the existing glued mesh.
     *
     * @throws IllegalArgumentException If the atom uses a different {@link GlueConfig} or contains malformed attribute
     * arrays.
     *
     * @throws IndexOutOfBoundsException If the merged vertex count exceeds the range of {@code short} indexing.
     */
    private void enhance(GluableSingeleMesh atom) {

        // Ensure all atoms use the same configuration
        if (atom.getConfig() != config) {
            throw new IllegalArgumentException("Bad config.");
        }

        // Compute the global vertex offset inside the glued mesh
        // (total vertices = total floats / components-per-vertex)
        int offset = glued.getContent(0).length / config.getComponents()[0];

        // Number of attribute channels (e.g., Position, TexCoord, Color)
        final int length = config.componentsCount();

        // Prepare new dynamic buffers and pre-fill them with existing glued data
        final ArrayTapeFloat[] content = new ArrayTapeFloat[length];
        for (int i = 0; i < length; i++) {
            content[i] = new ArrayTapeFloat();
            content[i].addAll(glued.getContent(i)); // copy existing data
        }

        // Copy existing index buffer
        final ArrayTapeShort indexbuffer = new ArrayTapeShort();
        indexbuffer.addAll(glued.getIndexbuffer());

        // Merge atom's index buffer with offset adjustment
        short[] atomIndexbuffer = atom.getIndexbuffer();
        for (int i = 0; i < atomIndexbuffer.length; i++) {
            indexbuffer.add((short) (atomIndexbuffer[i] + offset));
        }

        // Determine vertex count of this atom (based on first attribute)
        int step = atom.getContent(0).length / config.getComponents()[0];

        // Validate that all attributes have the same vertex count
        for (int i = 1; i < length; i++) {
            int expected = atom.getContent(i).length / config.getComponents()[i];
            if (step != expected) {
                throw new IllegalArgumentException("Single mesh is malformed.");
            }
        }

        // Store the global offset inside the atom for future on-the-fly updates
        atom.setAtomOffset(offset);

        // Increase global offset for next atom
        offset += step;

        // Append all attribute arrays from the new atom
        for (int i = 0; i < length; i++) {
            content[i].addAll(atom.getContent(i));
        }

        // Ensure short indexing does not overflow
        if (offset > 32760) {
            throw new IndexOutOfBoundsException(
                    "Offset is too big for short indexing. Split the mesh set."
            );
        }

        // Update the glued mesh with the new combined index buffer
        glued.setIndexbuffer(indexbuffer.toArray());

        // Update all vertex attribute arrays
        for (int i = 0; i < length; i++) {
            glued.setContent(i, content[i].toArray());
        }
    }

    /**
     * Applies an on-the-fly translation to a specific sub-mesh ("atom") inside the already glued mesh.This operation
     * writes the updated vertex positions directly into the combined mesh buffer, without rebuilding the entire
     * mesh.<p>
     * The method uses the atom's stored {@code atomOffset} to locate the correct vertex range inside the merged mesh
     * and overwrites the corresponding position values with the translated coordinates.
     * </p>
     *
     * @param atom The sub-mesh whose vertices should be updated.
     * @param px Offset along the X axis.
     * @param py Offset along the Y axis.
     * @param pz Offset along the Z axis.
     *
     * @throws IllegalStateException If the atom has no registered position attribute.
     */
    public void setPos(GluableSingeleMesh atom, final float px, final float py, final float pz) {

        // Starting vertex index inside the glued mesh
        int offset = atom.getAtomOffset();

        // Attribute index for position data
        int positionIndex = atom.getPositionIndex();
        if (positionIndex < 0) {
            throw new IllegalStateException("Position type is not registered.");
        }

        // Original per-atom vertex data
        float[] posAtomContent = atom.getContent(positionIndex);
        int length = posAtomContent.length;

        // Combined mesh vertex data
        float[] posContent = glued.getContent(positionIndex);

        // Overwrite the glued mesh with translated values
        int i = 0;
        while (i < length) {
            posContent[i + offset] = posAtomContent[i] + px;  // X
            i++;
            posContent[i + offset] = posAtomContent[i] + py;  // Y
            i++;
            posContent[i + offset] = posAtomContent[i] + pz;  // Z
            i++;
        }
        atom.setPos(px, py, pz);
    }

    /**
     * Updates the texture-layer index of a specific sub-mesh ("atom") inside the already glued mesh. This is typically
     * used with texture arrays, where the third texture coordinate component selects the texture layer.
     *
     * <p>
     * The method writes the new layer index both into the atom's own local buffer and into the corresponding region of
     * the merged mesh buffer, enabling real-time texture changes without rebuilding the mesh.
     * </p>
     *
     * @param atom The sub-mesh whose texture layer should be changed.
     * @param imageIndex The new texture-layer index to assign.
     *
     * @throws IllegalStateException If the atom has no registered texture coordinate attribute, or if the attribute
     * does not contain exactly three components (u, v, layer).
     */
    public void changeImageIndex(GluableSingeleMesh atom, final float imageIndex) {

        // Starting vertex index inside the glued mesh
        int offset = atom.getAtomOffset();

        // Attribute index for texture coordinates
        int texCoordIndex = atom.getTexCoordIndex();
        if (texCoordIndex < 0) {
            throw new IllegalStateException("TexCoord type is not registered.");
        }
        if (config.getComponents()[texCoordIndex] != 3) {
            throw new IllegalStateException("TexCoord type needs 3 components (u, v, layerIndex).");
        }

        // Original per-atom texture coordinates
        float[] texAtomContent = atom.getContent(texCoordIndex);
        int length = texAtomContent.length;

        // Combined mesh texture coordinates
        float[] texContent = glued.getContent(texCoordIndex);

        // Overwrite the glued mesh with the new layer index
        int i = 0;
        while (i < length) {
            i++; // skip u
            i++; // skip v

            // Update atom-local data
            texAtomContent[i] = imageIndex;

            // Update glued mesh data
            texContent[i + offset] = imageIndex;

            i++;
        }
    }

}
