/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.jme.meshglue;

/**
 * Represents a single, self-contained mesh fragment ("atom") that can be combined with other fragments to form a larger
 * mesh. A {@code GluableSingeleMesh} stores vertex attribute data according to a {@link GlueConfig}, along with an
 * optional index buffer and a local translation offset.
 *
 * <p>
 * Each instance manages its own attribute arrays (e.g., Position, TexCoord, Color) and provides utility methods for
 * modifying them, such as moving all vertices or updating the texture-layer index used in texture arrays.
 * </p>
 *
 * <p>
 * This class is typically used by a higher-level mesh builder that merges multiple {@code GluableSingeleMesh} objects
 * into a single large mesh by concatenating their vertex data and adjusting index offsets.
 * </p>
 *
 * @author Janusch Renteantus
 *
 * @param <KeyType> The key type used to identify vertex attribute channels. Typically
 * {@link com.jme3.scene.VertexBuffer.Type}.
 */
public class GluableSingeleMesh<KeyType> {

    private short[] indexbuffer;
    private final GlueConfig config;
    private final float[][] content;
    private float x;
    private float y;
    private float z;
    private int atomOffset;
    private int positionIndex;
    private int texCoordIndex;

    /**
     * Creates a new mesh fragment using the given configuration. All attribute arrays are initialized to {@code null}
     * and must be assigned via {@link #setContent(int, float[])} before use.
     *
     * @param config The vertex layout configuration describing attribute types and component counts.
     */
    public GluableSingeleMesh(GlueConfig config) {
        this.config = config;
        this.content = new float[config.componentsCount()][];
        this.indexbuffer = null;
        this.atomOffset = 0;
        this.positionIndex = -1;
        this.texCoordIndex = -1;
    }

    /**
     * Creates a new mesh fragment with the given configuration and index buffer.
     *
     * @param config The vertex layout configuration.
     * @param indexbuffer The index buffer for this mesh fragment.
     */
    public GluableSingeleMesh(GlueConfig config, short[] indexbuffer) {
        this.config = config;
        this.content = new float[config.componentsCount()][];
        this.indexbuffer = indexbuffer;
        this.atomOffset = 0;
        this.positionIndex = -1;
        this.texCoordIndex = -1;
    }

    /**
     * Creates a new mesh fragment with the given configuration and index buffer.
     *
     * @param config The vertex layout configuration.
     * @param indexbuffer The index buffer for this mesh fragment.
     * @param positionIndex the index of this mesh parameter.
     * @param texCoordIndex the index of this mesh parameter.
     */
    public GluableSingeleMesh(GlueConfig config, short[] indexbuffer, int positionIndex, int texCoordIndex) {
        this.config = config;
        this.content = new float[config.componentsCount()][];
        this.indexbuffer = indexbuffer;
        this.atomOffset = 0;
        this.positionIndex = positionIndex;
        this.texCoordIndex = texCoordIndex;
    }

    /**
     * Registers which attribute type represents vertex positions. This enables position-based operations such as
     * {@link #move(float, float, float)}.
     *
     * @param typePosition The attribute type corresponding to vertex positions.
     *
     * @return The index of the position attribute within the configuration.
     */
    public int registerPosition(KeyType typePosition) {
        return positionIndex = config.getIndex(typePosition);
    }

    /**
     * Registers which attribute type represents texture coordinates. This enables texture-layer operations such as
     * {@link #changeImageIndex(float)}.
     *
     * @param typeTexCoord The attribute type corresponding to texture coordinates.
     *
     * @return The index of the texture coordinate attribute.
     */
    public int registerTexture(KeyType typeTexCoord) {
        return texCoordIndex = config.getIndex(typeTexCoord);
    }

    /**
     * Returns the configuration describing the vertex layout of this mesh fragment.
     *
     * @return The associated {@link GlueConfig}.
     */
    public GlueConfig getConfig() {
        return config;
    }

    /**
     * Assigns the attribute array for the given attribute type.
     *
     * @param toType The attribute type.
     * @param arr The float array containing the attribute data.
     */
    public void setContent(KeyType toType, float[] arr) {
        final int index = config.getIndex(toType);
        content[index] = arr;
    }

    /**
     * Assigns the attribute array at the given index.
     *
     * @param index The attribute index in the configuration.
     * @param arr The attribute data.
     */
    public void setContent(int index, float[] arr) {
        content[index] = arr;
    }

    /**
     * Returns the attribute array associated with the given type.
     *
     * @param toType The attribute type.
     *
     * @return The attribute data array, or {@code null} if not set.
     */
    public float[] getContent(KeyType toType) {
        final int index = config.getIndex(toType);
        return content[index];
    }

    /**
     * Returns the attribute array at the given index.
     *
     * @param index The attribute index.
     *
     * @return The attribute data array.
     */
    public float[] getContent(int index) {
        return content[index];
    }

    /**
     * Returns the current X translation of this mesh fragment.
     *
     * @return The X offset.
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the X translation of this mesh fragment.
     *
     * @param x The new X offset.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Returns the current Y translation of this mesh fragment.
     *
     * @return The Y offset.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the Y translation of this mesh fragment.
     *
     * @param y The new Y offset.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Returns the current Z translation of this mesh fragment.
     *
     * @return The Z offset.
     */
    public float getZ() {
        return z;
    }

    /**
     * Sets the Z translation of this mesh fragment.
     *
     * @param z The new Z offset.
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Sets the X , Y, Z translation of this mesh fragment.
     *
     * @param x The new X offset.
     * @param y The new Y offset.
     * @param z The new Z offset.
     */
    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Translates all vertex positions of this mesh fragment by the given deltas. This operation modifies the underlying
     * position buffer and updates the stored translation values.
     *
     * @param dx Offset along the X axis.
     * @param dy Offset along the Y axis.
     * @param dz Offset along the Z axis.
     *
     * @throws IllegalStateException If no position attribute has been registered.
     */
    public void move(final float dx, final float dy, final float dz) {
        if (positionIndex < 0) {
            throw new IllegalStateException("Position type is not registred.");
        }
        float[] posContent = content[positionIndex];
        int length = posContent.length;
        int i = 0;
        while (i < length) {
            posContent[i] += dx;
            i++;
            posContent[i] += dy;
            i++;
            posContent[i] += dz;
            i++;
        }
        x += dx;
        y += dy;
        z += dz;
    }

    /**
     * Updates the texture-layer index for all vertices in this mesh fragment. This is typically used when working with
     * texture arrays, where the third texture coordinate component selects the texture layer.
     *
     * @param imageIndex The new texture-layer index to assign.
     *
     * @throws IllegalStateException If no texture coordinate attribute has been registered or if the attribute does not
     * contain exactly three components.
     */
    public void changeImageIndex(final float imageIndex) {
        if (texCoordIndex < 0) {
            throw new IllegalStateException("TexCoord type is not registred.");
        }
        if (config.getComponents()[texCoordIndex] != 3) {
            throw new IllegalStateException("TexCoord type needs 3 komponents (x,y,index).");
        }
        float[] texContent = content[texCoordIndex];
        int length = texContent.length;
        int i = 0;
        while (i < length) {
            i++; // skip u
            i++; // skip v
            texContent[i] = imageIndex; // set layer index
            i++;
        }
    }

    /**
     * Returns the index buffer of this mesh fragment.
     *
     * @return The index buffer, or {@code null} if none is assigned.
     */
    public short[] getIndexbuffer() {
        return indexbuffer;
    }

    /**
     * Sets the index buffer for this mesh fragment.
     *
     * @param indexbuffer The new index buffer.
     */
    public void setIndexbuffer(short[] indexbuffer) {
        this.indexbuffer = indexbuffer;
    }

    /**
     * Returns the index offset used when merging this fragment into a larger mesh. This value is typically added to all
     * indices during assembly.
     *
     * @return The index offset.
     */
    public int getAtomOffset() {
        return atomOffset;
    }

    /**
     * Sets the index offset used when merging this fragment into a larger mesh.
     *
     * @param atomOffset The new index offset.
     */
    public void setAtomOffset(int atomOffset) {
        this.atomOffset = atomOffset;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public int getTexCoordIndex() {
        return texCoordIndex;
    }

}
