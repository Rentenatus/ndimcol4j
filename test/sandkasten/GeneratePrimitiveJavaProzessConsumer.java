/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Janusch Rentenatus
 */
public class GeneratePrimitiveJavaProzessConsumer implements Consumer<String> {

    public static void processFile(Path sourcePath, Path targetPath, String append, String prim, String primBox) {
        final GeneratePrimitiveJavaProzessConsumer prozessConsumer = new GeneratePrimitiveJavaProzessConsumer();
        prozessConsumer.createReplacement(append, prim, primBox);

        try {
            // Zeilenweise lesen und Änderungen vornehmen
            Files.lines(sourcePath).forEach(prozessConsumer);

            // Geänderte Zeilen zurückschreiben
            Files.write(targetPath, prozessConsumer.modifiedLines);
            System.out.println(" .");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<List<String>> replacement;
    private String append;
    private String prim;
    private String primBox;

    private void createReplacement(String append, String prim, String primBox) {
        this.append = append;
        this.prim = prim;
        this.primBox = primBox;
        replacement = new ArrayList<>();
        replacement.add(List.of("package de.jare.ndimcol.ref;", "package de.jare.ndimcol.prim" + prim + ";"));
        replacement.add(List.of("import java.util.function.Consumer;", "import java.util.function." + append + "Consumer;"));
        replacement.add(List.of("import java.util.function.Predicate;", "import java.util.function." + append + "Predicate;"));
        replacement.add(List.of("Predicate<? super T>", append + "Predicate"));
        replacement.add(List.of("Consumer<? super T>", append + "Consumer"));
        replacement.add(List.of("import java.util.function.FloatConsumer;", "// FloatConsumer is unfortunately not available in java.util.function"));
        replacement.add(List.of("import java.util.function.FloatPredicate;", "// FloatPredicate is unfortunately not available in java.util.function"));
        replacement.add(List.of("<T> ArrayMovie<T>", "ArrayMovie" + append));
        replacement.add(List.of("ArrayMovie<?>", "ArrayMovie" + append));
        replacement.add(List.of("ArrayMatrix<?>", "ArrayMatrix" + append));
        replacement.add(List.of("<T>", append));
        replacement.add(List.of("extends Collection" + append, " "));
        //replacement.add(List.of("@Override", " "));
        replacement.add(List.of(" T ", " " + prim + " "));
        replacement.add(List.of("(T ", "(" + prim + " "));
        replacement.add(List.of("T[] ", prim + "[] "));
        replacement.add(List.of("<U> U[] ", prim + "[] "));
        replacement.add(List.of("U[] ", prim + "[] "));
        replacement.add(List.of("Object", prim));
        replacement.add(List.of("extends T", "extends " + primBox));
        replacement.add(List.of("super T", "super " + primBox));
        replacement.add(List.of("List" + append, "List<" + primBox + ">"));
        replacement.add(List.of("ArrayTape<ArrayMovie" + append + ">", "de.jare.ndimcol.ref.ArrayTape<ArrayMovie" + append + ">"));
        replacement.add(List.of("IterTapeWalker<ArrayMovie" + append + ">", "de.jare.ndimcol.ref.IterTapeWalker<ArrayMovie" + append + ">"));
        replacement.add(List.of("IteratorWalker<?>", "IteratorWalker" + append));
        replacement.add(List.of("data = new ArrayTape<>", "data = new de.jare.ndimcol.ref.ArrayTape<>"));
        replacement.add(List.of("content = new ArrayTape<>", "content = new de.jare.ndimcol.ref.ArrayTape<>"));
        replacement.add(List.of("new ArrayTape<>", "new ArrayTape" + append));
        replacement.add(List.of("new ArrayTapeHashable<>", "new ArrayTapeHashable" + append));
        replacement.add(List.of("new ArraySeasonHashable<>", "new ArraySeasonHashable" + append));
        replacement.add(List.of("public ArrayTape(", "public ArrayTape" + append + "("));
        replacement.add(List.of("public ArraySeason(", "public ArraySeason" + append + "("));
        replacement.add(List.of("public ArrayMatrix(", "public ArrayMatrix" + append + "("));
        replacement.add(List.of("public ArrayTapeHashable(", "public ArrayTapeHashable" + append + "("));
        replacement.add(List.of("public ArraySeasonHashable(", "public ArraySeasonHashable" + append + "("));
        replacement.add(List.of("public PredicateAllRunnable(", "public PredicateAllRunnable" + append + "("));
        replacement.add(List.of("Screenplay ", "Screenplay" + append + " "));
        replacement.add(List.of("Screenplay2d", "Screenplay2d" + append));
        replacement.add(List.of("Screenplay3d", "Screenplay3d" + append));
        replacement.add(List.of("ArraySeason<>", "ArraySeason" + append));
        replacement.add(List.of("public IterSeasonWalker(", "public IterSeasonWalker" + append + "("));
        replacement.add(List.of("IterSeasonWalker<>", "IterSeasonWalker" + append));
        replacement.add(List.of("public IterCoverWalker(", "public IterCoverWalker" + append + "("));
        replacement.add(List.of("IterCoverWalker<>", "IterCoverWalker" + append));
        replacement.add(List.of("public IterTapeWalkerFreeze(", "public IterTapeWalkerFreeze" + append + "("));
        replacement.add(List.of("IterTapeWalkerFreeze<>", "IterTapeWalkerFreeze" + append));
        replacement.add(List.of("public IterTapeWalker(", "public IterTapeWalker" + append + "("));
        replacement.add(List.of("IterTapeWalker<>", "IterTapeWalker" + append));
        replacement.add(List.of("HashStrategy<>", "HashStrategy" + append));
        replacement.add(List.of("ArrayTape.DEFAULT", "ArrayTape" + append + ".DEFAULT"));
        replacement.add(List.of("ArrayTape<? extends " + primBox + ">", "ArrayTape" + append + ""));
        replacement.add(List.of(" (T) ", " "));
        replacement.add(List.of(" (U) ", " "));
        replacement.add(List.of("Iterator<>", "Iterator" + append));
        replacement.add(List.of("SortedSeasonSetWorkerAdd<>", "SortedSeasonSetWorkerAdd" + append));
        replacement.add(List.of("SortedSeasonSetWorkerRemove<>", "SortedSeasonSetWorkerRemove" + append));
        replacement.add(List.of("SortedSeasonSetWorkerIndexOf<>", "SortedSeasonSetWorkerIndexOf" + append));
        replacement.add(List.of("BiPredicate<T, T>", "BiPredicate" + append + append));
        replacement.add(List.of("implements Set" + append, ""));
        replacement.add(List.of("public SortedSeasonSet(", "public SortedSeasonSet" + append + "("));
        replacement.add(List.of("SortedSeasonSet<>(", "SortedSeasonSet" + append + "("));
        replacement.add(List.of("ArraySeason(de.jare.ndimcol.ref.ArrayTape<ArrayMovie", "ArraySeason"+append+"(de.jare.ndimcol.ref.ArrayTape<ArrayMovie"));
       
        skipNext = false;
        noprim = false;
        isInterface = false;
    }

    boolean skipNext = false;
    boolean noprim = false;
    boolean isInterface = false;
    List<String> modifiedLines = new ArrayList<>();

    @Override
    public void accept(String line) {

        String trimLine = line.trim();
        if (trimLine.startsWith("public interface")) {
            isInterface = true;
        }

        if (trimLine.startsWith("* @param <T>") || trimLine.startsWith("* @param <U>")) {
            return;
        } else if (trimLine.startsWith("//prim:")) {
            String modifiedLine = line.replace("//prim:", "");
            modifiedLine = modifiedLine.replace("_PRIMBOX_", primBox);
            modifiedLine = modifiedLine.replace("_PRIM_", prim);
            modifiedLine = modifiedLine.replace("_APPEND_", append);
            modifiedLines.add(modifiedLine);
            skipNext = true;
            noprim = false;
        } else if (trimLine.startsWith("// prim:")) {
            String modifiedLine = line.replace("// prim:", "");
            modifiedLine = modifiedLine.replace("_PRIMBOX_", primBox);
            modifiedLine = modifiedLine.replace("_PRIM_", prim);
            modifiedLines.add(modifiedLine);
            skipNext = true;
            noprim = false;
        } else if (trimLine.startsWith("//noprim.start") || trimLine.startsWith("// noprim.start")) {
            noprim = true;
        } else if (trimLine.startsWith("//noprim.end") || trimLine.startsWith("// noprim.end")) {
            noprim = false;
        } else if (skipNext || noprim) {
            skipNext = false;
        } else if (trimLine.startsWith("* ")
                || trimLine.startsWith("/* ")
                || trimLine.startsWith("*/")
                || trimLine.startsWith("//")) {
            modifiedLines.add(line);
            if (trimLine.startsWith("*/")) {
                modifiedLines.add("    // #### This code has been generated. Please do not make any changes here.\n    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'");
            }
        } else {
            String modifiedLine = line;
            for (List<String> list12 : replacement) {
                modifiedLine = modifiedLine.replace(list12.get(0), list12.get(1));
            }
            if (isInterface) {
                modifiedLine = modifiedLine.replace("@Override", "");
            }
            modifiedLines.add(modifiedLine);
        }
    }

}
