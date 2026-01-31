/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.jme.meshglue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 *
 * @author Janusch Rentenatus
 */
public class GluedMeshNGTest {

    GlueConfig<String> config;

    public GluedMeshNGTest() {
        config = createConfig();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start GluedMeshTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End GluedMeshTest.");
        System.out.println("===============================================");
    }

    private GlueConfig<String> createConfig() {
        GlueConfig<String> ret = new GlueConfig<>(
                new String[]{"Position", "TexCoord", "Color"},
                new int[]{3, 3, 4}
        );
        ret.registerPosition("Position");
        ret.registerTexture("TexCoord");
        return ret;
    }

    private GluableSingleMesh<String> createTestAtom(String key, float x, float y, float z, float layer) {
        GluableSingleMesh<String> atom = new GluableSingleMesh<>(config);

        // Local translation
        atom.setPos(x, y, z);

        // Quad at origin (translation applied later by GluedMesh)
        atom.setContent("Position", new float[]{
            0, 0, 0,
            1, 0, 0,
            1, 1, 0,
            0, 1, 0
        });

        atom.setContent("TexCoord", new float[]{
            0, 0, layer,
            1, 0, layer,
            1, 1, layer,
            0, 1, layer
        });

        atom.setContent("Color", new float[]{
            1, 0, 0, 1,
            1, 0, 0, 1,
            1, 0, 0, 1,
            1, 0, 0, 1
        });

        atom.setIndexbuffer(new short[]{0, 1, 2, 0, 2, 3});
        return atom;
    }

    /**
     * Test of add method, of class GluedMesh.
     */
    @Test
    public void testAdd() {
        System.out.println("-------------- add");
        GluedMesh glued = new GluedMesh(config);

        GluableSingleMesh<String> atom1 = createTestAtom("A", 0, 0, 0, 2);
        GluableSingleMesh<String> atom2 = createTestAtom("B", 2, 0, 0, 5);

        glued.add(atom1);
        glued.add(atom2);
        glued.calculate();

        GluableSingleMesh<String> result = glued.getGlued();

        assertNotNull(result);
        assertEquals(8 * 3, result.getContent(config.getPositionIndex()).length); // 8 vertices * 3 components
    }

    @Test
    public void testReplaceWaste() {
        System.out.println("-------------- remove, replaceWaste");
        GluedMesh glued = new GluedMesh(config);

        GluableSingleMesh<String> atom1 = createTestAtom("A", 0, 0, 0, 2);
        GluableSingleMesh<String> atom2 = createTestAtom("B", 2, 0, 0, 5);

        glued.add(atom1);
        glued.add(atom2);
        glued.calculate();

        glued.remove(atom1);
        int oldOffset1 = atom1.getAtomOffset();

        GluableSingleMesh<String> atom3 = createTestAtom("C", 4, 0, 0, 7);
        glued.add(atom3); // should reuse atom1 slot

        glued.updateContent(config.getIndex("Color"), atom2);

        assertEquals(atom1.getAtomOffset(), -1);
        assertEquals(oldOffset1, atom3.getAtomOffset());
    }

    /**
     * Test of remove method, of class GluedMesh.
     */
    @Test
    public void testRemove() {
        System.out.println("-------------- removed");
        GluedMesh glued = new GluedMesh(config);

        GluableSingleMesh<String> atom1 = createTestAtom("A", 0, 0, 0, 2);
        GluableSingleMesh<String> atom2 = createTestAtom("B", 2, 0, 0, 5);

        glued.add(atom1);
        glued.add(atom2);
        glued.calculate();

        short[] idx = glued.getGlued().getIndexbuffer();
        for (short s : idx) {
            System.out.println("idx: " + s);
        }

        boolean removed = glued.remove(atom1);
        assertTrue(removed);

        System.out.println("  Atom 1 offset: " + atom1.getAtomOffset());
        System.out.println("  Atom 2 offset: " + atom2.getAtomOffset());

        glued.updateContent(config.getIndex("Color"), atom2);

        // atom1 indices should be gone
        idx = glued.getGlued().getIndexbuffer();
        for (short s : idx) {
            System.out.println("idx: " + s);
            // assertFalse(s < atom1.getAtomOffset() + 4);
        }
    }

    /**
     * Test of invalidate method, of class GluedMesh.
     */
    @Test
    public void testInvalidate() {
        System.out.println("-------------- invalidate");
        GluedMesh glued = new GluedMesh(config);

        glued.add(createTestAtom("A", 0, 0, 0, 2));
        glued.calculate();

        glued.invalidate();

        assertNull(glued.getGlued());
    }

    @Test
    public void testSetPos() {
        System.out.println("-------------- setPos");
        GluedMesh glued = new GluedMesh(config);

        GluableSingleMesh<String> atom = createTestAtom("A", 0, 0, 0, 2);
        glued.add(atom);
        glued.calculate();

        glued.setPos(atom, 5, 5, 0);

        float[] pos = glued.getGlued().getContent(config.getPositionIndex());
        assertEquals(5f, pos[0], 0.0001f);
        assertEquals(5f, pos[1], 0.0001f);
    }

    /**
     * Test of move method, of class GluedMesh.
     */
    @Test
    public void testMove() {
        System.out.println("-------------- move");

        GluedMesh glued = new GluedMesh(config);

        // Atom at origin
        GluableSingleMesh<String> atom = createTestAtom("A", 0, 0, 0, 2);

        glued.add(atom);
        glued.calculate();

        // Move atom by +3, +4, +0
        glued.move(atom, 3f, 4f, 0f);

        float[] pos = glued.getGlued().getContent(config.getPositionIndex());

        // First vertex should now be (3,4,0)
        assertEquals(3f, pos[0], 0.0001f);
        assertEquals(4f, pos[1], 0.0001f);
        assertEquals(0f, pos[2], 0.0001f);

        // Atom should report new position
        assertEquals(3f, atom.getX(), 0.0001f);
        assertEquals(4f, atom.getY(), 0.0001f);
    }

    /**
     * Test of changeImageIndex method, of class GluedMesh.
     */
    @Test
    public void testChangeImageIndex() {
        System.out.println("-------------- changeImageIndex");

        GluedMesh glued = new GluedMesh(config);

        // Atom with layer index 2
        GluableSingleMesh<String> atom = createTestAtom("A", 0, 0, 0, 2);

        glued.add(atom);
        glued.calculate();

        // Change layer index to 7
        glued.changeImageIndex(atom, 7f);

        float[] tex = glued.getGlued().getContent(config.getTexCoordIndex());

        // Every 3rd element is the layer index
        for (int i = 2; i < tex.length; i += 3) {
            assertEquals(7f, tex[i], 0.0001f);
        }

        // Atom-local data must also be updated
        float[] atomTex = atom.getContent(config.getTexCoordIndex());
        for (int i = 2; i < atomTex.length; i += 3) {
            assertEquals(7f, atomTex[i], 0.0001f);
        }
    }

}
