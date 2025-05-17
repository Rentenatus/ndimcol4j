/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 *
 * @author Janusch Rentenatus
 */
public class GeneratePrimitiveJavaFiles {

    final static String JAREDIR = "D:\\git_ndimcol4j\\ndimcol4j\\src\\de";

    public static void main(String[] args) {
        String directoryPath = args.length == 0 ? JAREDIR : args[0];
        calculate(directoryPath, "Int", "int", "Integer");
        calculate(directoryPath, "Float", "float", "Float");
    }

    public static void calculate(String directoryPath, String append, String prim, String primBox) {

        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(new File(directoryPath + "\\jare\\ndimcol\\ref"), javaFiles);

        System.out.println(directoryPath);
        System.out.println(javaFiles.size() + " files found.");

        for (File file : javaFiles) {
            String p = file.getAbsolutePath();
            final String newPath = "\\de\\jare\\ndimcol\\prim" + append + "\\";

            if (p.endsWith("ListSeason.java")) {
                System.out.println("x  " + p);
                System.out.println(" .");
                continue;
            }

            System.out.println(">  " + p);
            int indexOf = p.indexOf("\\de\\jare\\");
            p = p.substring(0, indexOf) + newPath;

            Path targetPath = Paths.get(p);
            try {
                Files.createDirectories(targetPath); // Erstellt fehlende Verzeichnisse              
            } catch (IOException e) {
                e.printStackTrace();
            }

            p = p + file.getName().substring(0, file.getName().length() - 5) + append + ".java";
            System.out.println("<  " + p);

            GeneratePrimitiveJavaProzessConsumer.processFile(file.toPath(),
                    Paths.get(p), append, prim, primBox);
        }
    }

    public static void collectJavaFiles(final File directory, final List<File> javaFiles) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        collectJavaFiles(file, javaFiles);
                    } else if (file.getName().endsWith(".java")) {
                        javaFiles.add(file);
                    }
                }
            }
        }
    }

}
