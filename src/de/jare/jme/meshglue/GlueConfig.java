/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.jme.meshglue;

/**
 * A configuration descriptor for defining the structure of vertex data used by a mesh�gluing or mesh�assembly system.
 * This class specifies which vertex attributes are present (e.g., Position, TexCoord, Color) and how many components
 * each attribute contains.
 * <p>
 * A {@code GlueConfig} acts as a compact schema describing the layout of per-vertex data arrays. It is typically used
 * by higher-level mesh builders to interpret, validate, and combine vertex buffers in a predictable way.
 * </p>
 *
 * <p>
 * Example configuration:</p>
 *
 * <pre>
 * // Defines a vertex layout of:
 * //   Position (3 floats), TexCoord (3 floats), Color (4 floats)
 * public void setConfig_Position_3_TexCoord_3_Color_4() {
 *     this.types = new VertexBuffer.Type[]{
 *         VertexBuffer.Type.Position,
 *         VertexBuffer.Type.TexCoord,
 *         VertexBuffer.Type.Color
 *     };
 *     this.components = new int[]{3, 3, 4};
 * }
 * </pre>
 *
 * @param <KeyType> The type used to identify vertex buffer categories. Typically
 * {@link com.jme3.scene.VertexBuffer.Type}, but may be any key type representing attribute channels.
 */
public class GlueConfig<KeyType> {

    private KeyType[] types;
    private int[] components;

    /**
     * Creates an empty configuration. Both {@code types} and {@code components} are initialized to {@code null} and
     * must be assigned before use.
     */
    public GlueConfig() {
        this.types = null;
        this.components = null;
    }

    /**
     * Creates a configuration with the given attribute types and component counts.
     *
     * @param types An array describing the vertex attribute categories in order.
     * @param components The number of float components for each attribute. Must have the same length as {@code types}.
     *
     * @throws IllegalArgumentException If {@code types.length != components.length}.
     */
    public GlueConfig(final KeyType[] types, final int[] components) {
        this.types = types;
        this.components = components;
        if (components.length != types.length) {
            throw new IllegalArgumentException("Bad config data.");
        }
    }

    /**
     * Returns the array of attribute types that define the vertex layout.
     *
     * @return The attribute type array.
     */
    public KeyType[] getTypes() {
        return types;
    }

    /**
     * Sets the array of attribute types that define the vertex layout.
     *
     * @param types The new attribute type array.
     */
    public void setTypes(KeyType[] types) {
        this.types = types;
    }

    /**
     * Returns the array of component counts associated with each attribute.
     *
     * @return The component count array.
     */
    public int[] getComponents() {
        return components;
    }

    /**
     * Sets the array of component counts associated with each attribute.
     *
     * @param components The new component count array.
     */
    public void setComponents(int[] components) {
        this.components = components;
    }

    /**
     * Returns the number of attribute entries defined in this configuration.
     *
     * @return The number of configured attribute channels.
     */
    public int componentsCount() {
        return components.length;
    }

    /**
     * Returns the index of the given attribute type within the configuration. The index corresponds to the position of
     * the attribute in the {@code types} array.
     *
     * @param toType The attribute type to look up.
     *
     * @return The index of the attribute, or {@code -1} if it is not present.
     */
    public int getIndex(KeyType toType) {
        int index = 0;
        for (KeyType type : types) {
            if (type == toType) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
