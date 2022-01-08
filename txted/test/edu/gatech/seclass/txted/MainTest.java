package edu.gatech.seclass.txted;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// DO NOT ALTER THIS CLASS. Use it as an example for MyMainTest.java

public class MainTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    private final Charset charset = StandardCharsets.UTF_8;
    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
     *  TEST UTILITIES
     */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file = createTmpFile();
        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        fileWriter.write(input);
        fileWriter.close();
        return file;
    }

    private String getFileContent(String filename) {
        String content = null;
        try {
            content = Files.readString(Paths.get(filename), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /*
     *   TEST CASES
     */

    // Instructors Example 1
    @Test
    public void mainTest1() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Instructions Example 2
    @Test
    public void mainTest2() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "ABC", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Instructions Example 3
    @Test
    public void mainTest3() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789def" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "ABC", "-i", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Instructions Example 4
    @Test
    public void mainTest4() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789DEF" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234abc" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Instructions Example 5
    @Test
    public void mainTest5() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789def" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "1", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Instructions Example 6
    @Test
    public void mainTest6() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01234abc!" + System.lineSeparator() +
                "56789def!" + System.lineSeparator() +
                "01234ABC!" + System.lineSeparator() +
                "56789DEF!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Instructions Example 7
    @Test
    public void mainTest7() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 01234abc" + System.lineSeparator() +
                "002 56789def" + System.lineSeparator() +
                "003 01234ABC" + System.lineSeparator() +
                "004 56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Instructions Example 8
    @Test
    public void mainTest8() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "0001 56789DEF!!!" + System.lineSeparator() + "" +
                "0002 01234ABC!!!" + System.lineSeparator() +
                "0003 56789def!!!" + System.lineSeparator() +
                "0004 01234abc!!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "4", "-r", "-x", "!!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Instructors Example 9
    @Test
    public void mainTest9() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected = "01 alphanumeric123FOOBAR!" + System.lineSeparator() +
                "02 alphanumeric123foobar!" + System.lineSeparator() +
                "03 alphanumeric123FOO!" + System.lineSeparator() +
                "04 alphanumeric123foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "3", "-r", "-e", "Bar", "-s", "0", "-n", "2", "-x", "!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Instructors 10
    // command line argument errors should display a usage message on stderr
    @Test
    public void mainTest10() throws Exception {
        //no arguments on the command line will pass an array of length 0 to the application (not a null).
        String[] args = new String[0];
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
}
