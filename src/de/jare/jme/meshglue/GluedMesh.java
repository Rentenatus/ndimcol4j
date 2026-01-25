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
 * Assembles ("glues") multiple {@link GluableSingleMesh} fragments into a single combined mesh.This class performs
 * vertex-buffer concatenation, index-buffer offsetting, and structural validation based on a shared {@link GlueConfig}
 * .<p>
 * A {@code GluedMesh} acts as a lightweight mesh builder: individual mesh atoms are added, validated, merged, and
 * finally written into a new {@link GluableSingleMesh} instance that contains the combined vertex and index data.
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
    private final ArrayList<GluableSingleMesh> atoms;

    /**
     * The final merged mesh created after {@link #calculate()}.
     */
    private GluableSingleMesh glued;

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
    public void add(GluableSingleMesh atom) {
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
     * Builds the final merged mesh by concatenating all vertex attributes and offsetting index buffers.This method
     * performs:
     *
     * <ul>
     * <li>Validation of attribute sizes</li>
     * <li>Index offsetting</li>
     * <li>Concatenation of all vertex attribute arrays</li>
     * <li>Creation of a new {@link GluableSingleMesh} containing the result</li>
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
            glued = new GluableSingleMesh(config);
            return;
        }

        // Attribute index for position data
        int positionIndex = config.getPositionIndex();

        // Global vertex offset for index-buffer adjustment
        int offset = 0;

        // Number of attribute channels (e.g., Position, TexCoord, Color)
        final int count = config.componentsCount();

        // Prepare dynamic buffers for each attribute channel
        final ArrayTapeFloat[] content = new ArrayTapeFloat[count];
        for (int i = 0; i < count; i++) {
            content[i] = new ArrayTapeFloat();
        }

        // Combined index buffer
        final ArrayTapeShort indexbuffer = new ArrayTapeShort();

        // Process each mesh fragment
        for (GluableSingleMesh atom : atoms) {

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
            for (int i = 1; i < count; i++) {
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
            appendNextAtomBuffers(atom, count, positionIndex, content);

            // Ensure short indexing does not overflow
            if (offset > 32760) {
                throw new IndexOutOfBoundsException(
                        "Offset is too big for short indexing. Split the mesh set."
                );
            }
        }

        // Create final merged mesh
        glued = new GluableSingleMesh(config, indexbuffer.toArray());

        // Assign merged attribute arrays
        for (int i = 0; i < count; i++) {
            glued.setContent(i, content[i].toArray());
        }
    }

    private void appendNextAtomBuffers(GluableSingleMesh atom, final int bufferCount, int positionIndex, final ArrayTapeFloat[] content) {
        for (int i = 0; i < bufferCount; i++) {
            if (i != positionIndex) {
                content[i].addAll(atom.getContent(i));
            } else {
                int index = 0;
                final float[] contentPosition = atom.getContent(i);
                while (index < contentPosition.length) {
                    content[i].add(contentPosition[index] + atom.getX());  // X
                    index++;
                    content[i].add(contentPosition[index] + atom.getY());  // Y
                    index++;
                    content[i].add(contentPosition[index] + atom.getZ());  // Z
                    index++;
                }
            }
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
    private void enhance(GluableSingleMesh atom) {

        // Ensure all atoms use the same configuration
        if (atom.getConfig() != config) {
            throw new IllegalArgumentException("Bad config.");
        }

        // Attribute index for position data
        int positionIndex = config.getPositionIndex();

        // Compute the global vertex offset inside the glued mesh
        // (total vertices = total floats / components-per-vertex)
        int offset = glued.getContent(0).length / config.getComponents()[0];

        // Number of attribute channels (e.g., Position, TexCoord, Color)
        final int count = config.componentsCount();

        // Prepare new dynamic buffers and pre-fill them with existing glued data
        final ArrayTapeFloat[] content = new ArrayTapeFloat[count];
        for (int i = 0; i < count; i++) {
            content[i] = new ArrayTapeFloat();
            // Internal knowledge: the next add operation extends the buffer, so it is more likely to create a new array.
            // The passed reference is therefore not corrupted in this algorithm.
            content[i].setBufferData(glued.getContent(i));
        }

        // Copy existing index buffer
        final ArrayTapeShort indexbufferTape = new ArrayTapeShort();
        // Internal knowledge: the next add operation extends the buffer, so it is more likely to create a new array.
        // The passed reference is therefore not corrupted in this algorithm.
        indexbufferTape.setBufferData(glued.getIndexbuffer());

        // Merge atom's index buffer with offset adjustment
        short[] atomIndexbuffer = atom.getIndexbuffer();
        for (int i = 0; i < atomIndexbuffer.length; i++) {
            indexbufferTape.add((short) (atomIndexbuffer[i] + offset));
        }

        // Determine vertex count of this atom (based on first attribute)
        int step = atom.getContent(0).length / config.getComponents()[0];

        // Validate that all attributes have the same vertex count
        for (int i = 1; i < count; i++) {
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
        appendNextAtomBuffers(atom, count, positionIndex, content);

        // Ensure short indexing does not overflow
        if (offset > 32760) {
            throw new IndexOutOfBoundsException(
                    "Offset is too big for short indexing. Split the mesh set."
            );
        }

        // Update the glued mesh with the new combined index buffer
        glued.setIndexbuffer(indexbufferTape.toArray());

        // Update all vertex attribute arrays
        for (int i = 0; i < count; i++) {
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
    public void setPos(GluableSingleMesh atom, final float px, final float py, final float pz) {

        // Starting vertex index inside the glued mesh
        int offset = atom.getAtomOffset();

        // Attribute index for position data
        int positionIndex = config.getPositionIndex();
        if (positionIndex < 0) {
            throw new IllegalStateException("Position type is not registered.");
        }

        // Original per-atom vertex data
        float[] posAtomContent = atom.getContent(positionIndex);
        int length = posAtomContent.length;

        // Combined mesh vertex data
        float[] posContent = glued.getContent(positionIndex);

        // Overwrite the glued mesh with translated values
        int index = 0;
        while (index < length) {
            posContent[index + offset] = posAtomContent[index] + px;  // X
            index++;
            posContent[index + offset] = posAtomContent[index] + py;  // Y
            index++;
            posContent[index + offset] = posAtomContent[index] + pz;  // Z
            index++;
        }
        atom.setPos(px, py, pz);
    }

    public void move(GluableSingleMesh atom, final float dx, final float dy, final float dz) {
        final float px = atom.getX() + dx;
        final float py = atom.getY() + dy;
        final float pz = atom.getZ() + dz;
        setPos(atom, px, py, pz);
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
    public void changeImageIndex(GluableSingleMesh atom, final float imageIndex) {

        // Starting vertex index inside the glued mesh
        int offset = atom.getAtomOffset();

        // Attribute index for texture coordinates
        int texCoordIndex = config.getTexCoordIndex();
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
        int index = 0;
        while (index < length) {
            index++; // skip u
            index++; // skip v

            // Update atom-local data
            texAtomContent[index] = imageIndex;

            // Update glued mesh data
            texContent[index + offset] = imageIndex;

            index++;
        }
    }

}
