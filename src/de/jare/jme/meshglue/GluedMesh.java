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
import de.jare.ndimcol.primshort.IterTapeWalkerShort;
import de.jare.ndimcol.ref.ArrayTape;
import de.jare.ndimcol.ref.IterTapeWalker;
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
     * The list of wasted mesh fragments after merge.
     */
    private final ArrayTape<GluableSingleMesh> waste;

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
        this.waste = new ArrayTape<>();
        this.glued = null;
        this.config = config;
    }

    public GluableSingleMesh getGlued() {
        return glued;
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
            if (replaceWaste(atom)) {
                // found a hole.
                return;
            }
            enhance(atom);
        }
    }

    /**
     * Removes a previously added mesh fragment ("atom") from this glued mesh. The removal is performed lazily: instead
     * of rebuilding the entire combined mesh, the method attempts to invalidate only the index references belonging to
     * the removed atom.
     *
     * <p>
     * The removal process works as follows:
     * </p>
     * <ul>
     * <li>The atom is removed from the internal atom list.</li>
     * <li>If the atom was never part of the glued mesh (no valid offset), the method returns immediately.</li>
     * <li>If no glued mesh exists yet, the removal is trivial.</li>
     * <li>If the number of removed atoms ("waste") grows too large relative to the number of active atoms, the entire
     * glued mesh is discarded and must be rebuilt on the next {@code calculate()} call.</li>
     * <li>Otherwise, the atom is added to the waste list and its index range is removed from the combined index buffer
     * via {@link #cutoff(GluableSingleMesh)}.</li>
     * </ul>
     *
     * <p>
     * This lazy removal strategy avoids expensive full rebuilds and allows the system to reuse freed vertex slots later
     * for new atoms, enabling efficient real-time mesh updates.
     * </p>
     *
     * @param atom The mesh fragment to remove.
     *
     * @return {@code true} if the atom was removed or invalidated successfully, {@code false} if the atom was not found
     * or was never part of the glued mesh.
     */
    public boolean remove(GluableSingleMesh atom) {
        final int index = atoms.indexOf(atom);
        if (index < 0) {
            return false;
        }
        atoms.remove(index);
        if (atom.getAtomOffset() < 0) {
            return false;
        }
        if (glued == null) {
            return true;
        }
        if (waste.size() > (atoms.size() >> 2)) {
            glued = null;
            waste.clear();
            return true;
        }
        waste.add(atom);
        cutoff(atom);
        return true;
    }

    /**
     * Removes all previously added mesh fragments.
     */
    public void clear() {
        atoms.clear();
        waste.clear();
        this.glued = null;
    }

    /**
     * Invalidates the current glued mesh and discards all incremental state.
     *
     * <p>
     * This method clears the waste list and removes the internally cached combined mesh, forcing the next
     * {@code calculate()} call to rebuild the entire structure from the remaining atoms. It is typically used when
     * incremental updates are no longer reliable or when a full reset of the glue state is required.
     * </p>
     *
     * <p>
     * <strong>Recommendation:</strong> If you already know in advance that a large number of structural changes will be
     * performed, you should avoid on-the-fly gluing and call {@code invalidate()} first. This prevents unnecessary
     * incremental updates and ensures that the next rebuild starts from a clean and consistent state.
     * </p>
     */
    public void invalidate() {
        waste.clear();
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
        waste.clear();

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

            validateVertexCount(count, atom, step);

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

    /**
     * Validate that all attributes have the same vertex count.
     *
     * @param count
     * @param atom
     * @param step
     * @throws IllegalArgumentException
     */
    private void validateVertexCount(final int count, GluableSingleMesh atom, int step) throws IllegalArgumentException {

        for (int i = 1; i < count; i++) {
            int expected = atom.getContent(i).length / config.getComponents()[i];
            if (step != expected) {
                throw new IllegalArgumentException("Single mesh is malformed.");
            }
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

        validateVertexCount(count, atom, step);

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
     * Attempts to reuse a previously freed vertex slot ("waste") for inserting the given mesh fragment ("atom") without
     * growing the combined mesh or triggering a full rebuild.
     *
     * <p>
     * When an atom is removed, its vertex range remains in the combined mesh but its index references are deleted.
     * These unused vertex regions are tracked in the {@code waste} list. This method checks whether one of these
     * regions has exactly the same vertex count as the new atom. If a matching slot is found, the atom is inserted
     * directly into that region:
     * </p>
     *
     * <ul>
     * <li>The slot's offset is reused as the atom's new {@code atomOffset}.</li>
     * <li>All vertex attributes are overwritten in-place inside the glued mesh.</li>
     * <li>The atom's index buffer is appended with the correct offset applied.</li>
     * <li>The waste entry is removed, shrinking the waste list.</li>
     * </ul>
     *
     * <p>
     * This slot-recycling mechanism avoids unnecessary buffer growth and prevents repeated re-batching. It enables
     * highly efficient real-time updates where atoms can be removed and replaced without disturbing the offsets of
     * other atoms or requiring a full mesh rebuild.
     * </p>
     *
     * @param atom The mesh fragment to insert into a previously freed slot.
     *
     * @return {@code true} if a matching waste slot was found and reused, {@code false} if no suitable slot exists and
     * the caller must fall back to normal incremental growth (via {@code enhance()}).
     *
     * @throws IllegalArgumentException If the atom uses a different {@link GlueConfig} than the glued mesh.
     */
    private boolean replaceWaste(GluableSingleMesh atom) {
        // Ensure all atoms use the same configuration
        if (atom.getConfig() != config) {
            throw new IllegalArgumentException("Bad config.");
        }

        // Determine vertex count of this atom (based on first attribute)
        int step = atom.getContent(0).length / config.getComponents()[0];

        // Number of attribute channels (e.g., Position, TexCoord, Color)
        final int count = config.componentsCount();
        validateVertexCount(count, atom, step);

        int offset = -1;
        IterTapeWalker<GluableSingleMesh> walker = waste.softWalker();
        while (walker.hasNext()) {
            GluableSingleMesh next = walker.next();
            if (step != atom.getContent(0).length / config.getComponents()[0]) {
                continue;
            }
            offset = next.getAtomOffset();
            next.setAtomOffset(-1); // recycled
            walker.remove();
            break;
        }

        if (offset < 0) {
            // no hole found
            return false;
        }
        // Attribute index for position data
        int positionIndex = config.getPositionIndex();

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

        // Store the global offset inside the atom for future on-the-fly updates
        atom.setAtomOffset(offset);

        // Rewrite all attribute arrays from the new atom
        for (int i = 0; i < count; i++) {
            final float[] gluedCcontent = glued.getContent(i);
            final float[] atomCcontent = atom.getContent(i);
            int index = 0;
            while (index < atomCcontent.length) {
                if (i != positionIndex) {
                    gluedCcontent[index + offset] = atomCcontent[index];
                    index++;
                } else {
                    gluedCcontent[index + offset] = atomCcontent[index] + atom.getX();  // X
                    index++;
                    gluedCcontent[index + offset] = atomCcontent[index] + atom.getY();  // Y
                    index++;
                    gluedCcontent[index + offset] = atomCcontent[index] + atom.getZ();  // Z
                    index++;
                }
            }
        }

        // Update the glued mesh with the new combined index buffer
        glued.setIndexbuffer(indexbufferTape.toArray());

        return true;
    }

    /**
     * Removes all index-buffer references belonging to the given atom from the combined mesh. This operation
     * effectively makes the atom invisible without modifying or shifting any vertex data.
     *
     * <p>
     * The method identifies the atom's vertex range using its stored {@code atomOffset} and vertex count. All indices
     * that fall within this range are removed from the combined index buffer. The vertex data remains in place and may
     * later be reused by inserting a new atom of identical vertex size.
     * </p>
     *
     * <p>
     * This approach avoids costly buffer compaction and preserves the offsets of all remaining atoms, making it
     * suitable for real-time mesh manipulation where stability and incremental updates are more important than memory
     * compactness.
     * </p>
     *
     * @param atom The mesh fragment whose index references should be removed.
     *
     * @throws IllegalArgumentException If the atom uses a different {@link GlueConfig} than the glued mesh.
     */
    private void cutoff(GluableSingleMesh atom) {
        // Global vertex offset for index-buffer adjustment
        int offset = atom.getAtomOffset();

        // Copy existing index buffer
        final ArrayTapeShort indexbufferTape = new ArrayTapeShort();
        // The passed reference will be corrupted. Therefore, we must not forget to reset this array.
        indexbufferTape.setBufferData(glued.getIndexbuffer());

        // Ensure all atoms use the same configuration
        if (atom.getConfig() != config) {
            throw new IllegalArgumentException("Bad config.");
        } // Determine vertex count of this atom (based on first attribute)
        int step = atom.getContent(0).length / config.getComponents()[0];
        int border = offset + step;
 
        // remove index buffer atom entries
        IterTapeWalkerShort walker = indexbufferTape.softWalker();
        while (walker.hasNext()) {
            short next = walker.next();
            if (next >= offset && next < border) {
                walker.remove();
            }
        }

        // Set the shortened index buffer
        glued.setIndexbuffer(indexbufferTape.toArray());
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
        } // Original per-atom vertex data
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
        } // Original per-atom texture coordinates
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
