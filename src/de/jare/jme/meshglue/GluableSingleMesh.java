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
public class GluableSingleMesh<KeyType> {

    private short[] indexbuffer;
    private final GlueConfig config;
    private final float[][] content;
    private float x;
    private float y;
    private float z;
    private int atomOffset;

    /**
     * Creates a new mesh fragment using the given configuration. All attribute arrays are initialized to {@code null}
     * and must be assigned via {@link #setContent(int, float[])} before use.
     *
     * @param config The vertex layout configuration describing attribute types and component counts.
     */
    public GluableSingleMesh(GlueConfig config) {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.config = config;
        this.content = new float[config.componentsCount()][];
        this.indexbuffer = null;
        this.atomOffset = 0;
    }

    /**
     * Creates a new mesh fragment with the given configuration and index buffer.
     *
     * @param config The vertex layout configuration.
     * @param indexbuffer The index buffer for this mesh fragment.
     */
    public GluableSingleMesh(GlueConfig config, short[] indexbuffer) {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.config = config;
        this.content = new float[config.componentsCount()][];
        this.indexbuffer = indexbuffer;
        this.atomOffset = 0;
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
     * Change the X , Y, Z translation of this mesh fragment.
     *
     * @param dx Offset along the X axis.
     * @param dy Offset along the Y axis.
     * @param dz Offset along the Z axis.
     *
     */
    public void move(final float dx, final float dy, final float dz) {
        x += dx;
        y += dy;
        z += dz;
    }

    /**
     * Updates the texture-layer index for all vertices in this mesh fragment.This is typically used when working with
     * texture arrays, where the third texture coordinate component selects the texture layer.
     *
     * @param imageIndex The new texture-layer index to assign.
     * @param copyBuffer instance new buffer and copy?
     *
     * @throws IllegalStateException If no texture coordinate attribute has been registered or if the attribute does not
     * contain exactly three components.
     */
    public void changeImageIndex(final float imageIndex, boolean copyBuffer) {
        final int texCoordIndex = config.getTexCoordIndex();
        if (texCoordIndex < 0) {
            throw new IllegalStateException("TexCoord type is not registred.");
        }
        if (config.getComponents()[texCoordIndex] != 3) {
            throw new IllegalStateException("TexCoord type needs 3 komponents (x,y,index).");
        }
        float[] texContent = content[texCoordIndex];
        int length = texContent.length;
        float[] trgContent = copyBuffer ? new float[length] : texContent;
        int i = 0;
        while (i < length) {
            trgContent[i] = texContent[i];
            i++; // skip u
            trgContent[i] = texContent[i];
            i++; // skip v
            trgContent[i] = imageIndex; // set layer index
            i++;
        }
        content[texCoordIndex] = trgContent;
    }

    public void moveTexCoord(float dx, float dy, boolean copyBuffer) {
        final int texCoordIndex = config.getTexCoordIndex();
        if (texCoordIndex < 0) {
            throw new IllegalStateException("TexCoord type is not registred.");
        }
        if (config.getComponents()[texCoordIndex] != 3) {
            throw new IllegalStateException("TexCoord type needs 3 komponents (x,y,index).");
        }
        float[] texContent = content[texCoordIndex];
        int length = texContent.length;
        float[] trgContent = copyBuffer ? new float[length] : texContent;
        int i = 0;
        while (i < length) {
            trgContent[i] = texContent[i] + dx;
            i++; // skip u
            trgContent[i] = texContent[i] + dy;
            i++; // skip v
            trgContent[i] = texContent[i];
            i++;
        }
        content[texCoordIndex] = trgContent;
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
    protected void setAtomOffset(int atomOffset) {
        this.atomOffset = atomOffset;
    }

    /**
     * Checks if this mash contains any points.
     *
     * @return true, if at least one point is noted.
     */
    public boolean isEmpty() {
        return indexbuffer == null || indexbuffer.length == 0;
    }

}
