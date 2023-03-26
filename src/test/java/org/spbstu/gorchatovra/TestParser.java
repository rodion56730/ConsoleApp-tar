package org.spbstu.gorchatovra;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestParser {

    private static boolean isEqual(Path firstFile, Path secondFile) {
        try {
            long size = Files.size(firstFile);
            if (size != Files.size(secondFile)) {
                return false;
            }
            try (BufferedReader bf1 = Files.newBufferedReader(firstFile);
                 BufferedReader bf2 = Files.newBufferedReader(secondFile)) {
                String line;
                while ((line = bf1.readLine()) != null) {
                    if (!line.equals(bf2.readLine())) {
                        return false;
                    }
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void testTarU(){
        Parser.main("-u src/test/TestFiles/expected/3.txt".split(" "));
        assertTrue(isEqual(Paths.get("src/test/TestFiles/actual/1.txt"),Paths.get("src/test/TestFiles/expected/1.txt")));
        assertTrue(isEqual(Paths.get("src/test/TestFiles/actual/2.txt"),Paths.get("src/test/TestFiles/expected/2.txt")));
        assertThrows("Неверные данные , нужен другой файл!",IOException.class,() -> new Delimiter().tar(new File("src/test/TestFiles/expected/4")));
    }

    @Test
    public void testTarOut(){
        Parser.main(("src/test/TestFiles/actual/1.txt src/test/TestFiles/actual/2.txt&-out&src/test/TestFiles/actual/3.txt").split("&"));
        assertTrue(isEqual(Paths.get("src/test/TestFiles/actual/3.txt"),Paths.get("src/test/TestFiles/expected/3.txt")));
        assertThrows("Не возможно создать новый файл",IOException.class,()-> new Delimiter().tar
                ("src/test/TestFiles/actual/1.txt src/test/TestFiles/actual/2.txt".split(" "),"src/test/TestFiles/actual/3.txt"));
    }
}
