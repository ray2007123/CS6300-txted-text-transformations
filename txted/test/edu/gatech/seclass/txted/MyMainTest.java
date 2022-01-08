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

public class MyMainTest {

    // Place all  of your tests in this class, optionally using MainTest.java as an example.
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

    // Frame #: 1
    @Test
    public void txtedTest1() throws Exception {
        String input = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();
        String expected = "1 2468" + System.lineSeparator() + "2 ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = { "-n", "1", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 2
    @Test
    public void txtedTest2() throws Exception {
        String input = "01234abc" + System.lineSeparator() + "56789def" + System.lineSeparator() + "01234ABC" + System.lineSeparator() + "56789DEF" + System.lineSeparator();
        String expected = "01234abc" + System.lineSeparator() + "56789def" + System.lineSeparator() + "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "ABC", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 3
    @Test
    public void txtedTest3() throws Exception {
        String input = "Hello" + System.lineSeparator() +
                "Beatrice" + System.lineSeparator() +
                "hello albert" + System.lineSeparator() +
                "123www" + System.lineSeparator() +
                "22Albert" + System.lineSeparator() +
                "qwerty911" + System.lineSeparator() +
                "hello" + System.lineSeparator();
        String expected = "Beatrice" + System.lineSeparator() +
                "123www" + System.lineSeparator() +
                "qwerty911" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "1", "-e", "albert", "-i", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }


    // Frame #: 4
    @Test
    public void txtedTest4() throws Exception {
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

    // Frame #: 5
    @Test
    public void txtedTest5() throws Exception {
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

    // Frame #: 6
    @Test
    public void txtedTest6() throws Exception {
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

    // Frame #: 7
    @Test
    public void txtedTest7() throws Exception {
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

    // Frame #: 8
    @Test
    public void txtedTest8() throws Exception {
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

    // Frame #: 9
    @Test
    public void txtedTest9() throws Exception {
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

    // Frame #: 10
    @Test
    public void txtedTest10() throws Exception {
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


    // Frame #: 11
    @Test
    public void txtedTest11() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "1 alphanumeric123foobar!!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", "-n", "1", "-r", "-x", "!!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 12
    @Test
    public void txtedTest12() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 13
    @Test
    public void txtedTest13() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected = "01 alphanumeric123foo!" + System.lineSeparator() +
                "02 alphanumeric123FOOBAR!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "bar", "-s", "0", "-x", "!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 14
    @Test
    public void txtedTest14() throws Exception {
        String input ="0123appleCare" + System.lineSeparator() +
                "0123appleCarePlus" + System.lineSeparator() +
                "4567googleDrive" + System.lineSeparator() +
                "4567googleDrivePlus" + System.lineSeparator() +
                "7890amazonAlex" + System.lineSeparator() +
                "7890amazonAlexPlus" + System.lineSeparator();
        String expected = "01 7890amazonAlexPlus!!" + System.lineSeparator() +
                "02 4567googleDrivePlus!!" + System.lineSeparator() +
                "03 0123appleCarePlus!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-r", "-s", "1", "-n", "2", "-x", "!!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 15
    @Test
    public void txtedTest15() throws Exception {
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
        String expected = "01 alphanumeric123Bar!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-r", "-e", "foo", "-i", "-r", "-s", "0", "-x", "!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 16
    @Test
    public void txtedTest16() throws Exception {
        String input ="0123appleCare" + System.lineSeparator();
        String expected = "1 0123appleCare!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-r", "-x", "!!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 17
    @Test
    public void txtedTest17() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "1 56789DEF!!!" + System.lineSeparator() +
                "2 01234ABC!!!" + System.lineSeparator() +
                "3 56789def!!!" + System.lineSeparator() +
                "4 01234abc!!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-r", "-x", "!!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 18
    @Test
    public void txtedTest18() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "1 56789DEF!!!" + System.lineSeparator() +
                "2 56789def!!!" + System.lineSeparator() +
                "3 01234abc!!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-e", "ABC", "-r", "-x", "!!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 19
    @Test
    public void txtedTest19() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "0001 01234abc!!!" + System.lineSeparator() +
                "0002 56789def!!!" + System.lineSeparator() +
                "0003 01234ABC!!!" + System.lineSeparator() +
                "0004 56789DEF!!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "4", "-x", "!!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }
    // Frame #: 20
    @Test
    public void txtedTest20() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "0001 01234abc" + System.lineSeparator() +
                "0002 01234ABC" + System.lineSeparator() +
                "0003 56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "4", "-e", "def", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }
    // Frame #: 21
    @Test
    public void txtedTest21() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789DEF" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "01234abc" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "def", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }
    // Frame #: 22
    @Test
    public void txtedTest22() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01234abc!" + System.lineSeparator() +
                "01234ABC!" + System.lineSeparator() +
                "56789DEF!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "def", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }
    // Frame #: 23
    @Test
    public void txtedTest23() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        String expected = "1 01234abc!" + System.lineSeparator() + "2 01234ABC!" + System.lineSeparator() + "3 56789DEF!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-e", "def", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }
    // Frame #: 24
    @Test
    public void txtedTest24() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        String expected = "01 01234abc!" + System.lineSeparator() + "02 01234ABC!" + System.lineSeparator() + "03 56789DEF!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-e", "def", "-x", "!", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 25
    @Test
    public void txtedTest25() throws Exception {
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
        String expected = "01 alphanumeric123Foo!" + System.lineSeparator() +
                "02 alphanumeric123FOO!" + System.lineSeparator() +
                "03 alphanumeric123bar!" + System.lineSeparator() +
                "04 alphanumeric123Bar!" + System.lineSeparator() +
                "05 alphanumeric123BAR!" + System.lineSeparator() +
                "06 alphanumeric123Foobar!" + System.lineSeparator() +
                "07 alphanumeric123FooBar!" + System.lineSeparator() +
                "08 alphanumeric123FOOBar!" + System.lineSeparator() +
                "09 alphanumeric123FooBAR!" + System.lineSeparator() +
                "10 alphanumeric123FOOBAR!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "foo", "-x", "!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 26
    @Test
    public void txtedTest26() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator();

        String expected = "alphanumeric123Foo!" + System.lineSeparator() +
                "alphanumeric123FOO!" + System.lineSeparator();

        File inputFile = createInputFile(input);

        String[] args = {"-e", "bar", "-e", "foo", "-x", "!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 27
    @Test
    public void txtedTest27() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator();

        String expected = "alphanumeric123foo!!" + System.lineSeparator() +
                "alphanumeric123FOO!!" + System.lineSeparator();

        File inputFile = createInputFile(input);

        String[] args = {"-f", "-e", "bar", "-i", "-x", "!!", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 28

    @Test
    public void txtedTest28() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator();

        String expected = "alphanumeric123foo!!" + System.lineSeparator() +
                "alphanumeric123FOO!!" + System.lineSeparator();

        File inputFile = createInputFile(input);

        String[] args = {"-f", "-f", "-e", "bar", "-i", "-x", "!!", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }


    // Frame #: 29
    @Test
    public void txtedTest29() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "def", "-s", "1", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }
    // Frame #: 30
    @Test
    public void txtedTest30() throws Exception {
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
        String expected = "01 alphanumeric123foo" + System.lineSeparator() + "02 alphanumeric123FOO" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-s", "0", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 31
    @Test
    public void txtedTest31() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "1 56789def" + System.lineSeparator() + "2 56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-e", "abc", "-i", "-s", "1", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 32
    @Test
    public void txtedTest32() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected = "01 alphanumeric123Foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-s", "1", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 33

    @Test
    public void txtedTest33() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator();

        String expected = "01 alphanumeric123foo" + System.lineSeparator() +
                "02 alphanumeric123FOO" + System.lineSeparator();

        File inputFile = createInputFile(input);

        String[] args = {"-f", "-f", "-n", "1", "-e", "bar", "-i", "-n", "2", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }


    // Frame #: 34
    @Test
    public void txtedTest34() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01 56789def" + System.lineSeparator() +
                "02 56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n","1","-e","abc","-e","ABC","-s","1","-n","2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }


    // Frame #: 35

    @Test
    public void txtedTest35() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected ="01 alphanumeric123bbq" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f","-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-s", "1", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }


    // Frame #: 36
    @Test
    public void txtedTest36() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected ="1 alphanumeric123bbq" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-s", "1", "-n", "1", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 37

    @Test
    public void txtedTest37() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01 56789def" + System.lineSeparator() + "02 56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n","1","-e","abc","-s","1","-n","2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 38
    @Test
    public void txtedTest38() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "001 alphanumeric123Bar" + System.lineSeparator() +
                "002 alphanumeric123bbq" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "foo", "-i", "-s", "0", "-n", "3", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 39
    @Test
    public void txtedTest39() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123bar" + System.lineSeparator() +
                "02 alphanumeric123BAR" + System.lineSeparator() +
                "03 alphanumeric123BBQ" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "foo", "-i", "-s", "1", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 40
    @Test
    public void txtedTest40() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123bar" + System.lineSeparator() + "02 alphanumeric123BAR" + System.lineSeparator() + "03 alphanumeric123BBQ" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-s", "1", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 41
    @Test
    public void txtedTest41() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123bar" + System.lineSeparator() + "02 alphanumeric123BAR" + System.lineSeparator() + "03 alphanumeric123BBQ" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-s", "1", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 42
    @Test
    public void txtedTest42() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123bbq" + System.lineSeparator() + "02 alphanumeric123BBQ" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-r", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 43
    @Test
    public void txtedTest43() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123BBQ!" + System.lineSeparator() + "02 alphanumeric123bbq!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-x", "!", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 44
    @Test
    public void txtedTest44() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123Bar!" + System.lineSeparator() + "02 alphanumeric123bbq!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-s", "0", "-x", "!", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 45
    @Test
    public void txtedTest45() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123foo!" + System.lineSeparator() +
                "02 alphanumeric123FOO!" + System.lineSeparator() +
                "03 alphanumeric123Bar!" + System.lineSeparator() +
                "04 alphanumeric123foobar!" + System.lineSeparator() +
                "05 alphanumeric123fooBar!" + System.lineSeparator() +
                "06 alphanumeric123FOOBar!" + System.lineSeparator() +
                "07 alphanumeric123FOOBAR!" + System.lineSeparator() +
                "08 alphanumeric123bbq!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-s", "0", "-x", "!", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 46
    @Test
    public void txtedTest46() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123foo!" + System.lineSeparator() + "02 alphanumeric123FOO!" + System.lineSeparator() + "03 alphanumeric123bbq!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-s", "0", "-x", "!", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 47
    @Test
    public void txtedTest47() throws Exception {
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
        String expected = "01 alphanumeric123FOO!" + System.lineSeparator() + "02 alphanumeric123foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-r", "-s", "0", "-x", "!", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 48
    @Test
    public void txtedTest48() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123bbq!" + System.lineSeparator() + "02 alphanumeric123Bar!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-r", "-s", "0", "-x", "!", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 49
    @Test
    public void txtedTest49() throws Exception {
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
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "1 alphanumeric123foo!" + System.lineSeparator() + "2 alphanumeric123FOO!" + System.lineSeparator() + "3 alphanumeric123Bar!" + System.lineSeparator() + "4 alphanumeric123foobar!" + System.lineSeparator() + "5 alphanumeric123fooBar!" + System.lineSeparator() + "6 alphanumeric123FOOBar!" + System.lineSeparator() + "7 alphanumeric123FOOBAR!" + System.lineSeparator() + "8 alphanumeric123bbq!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-s", "0", "-x", "!", "-n", "1", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 50
    @Test
    public void txtedTest50() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "01 alphanumeric123bbq!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-r", "-s", "0", "-x", "!", "-f", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 51
    @Test
    public void txtedTest51() throws Exception {
        String input ="alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                "alphanumeric123BBQ" + System.lineSeparator() +
                "alphanumeric123bbq" + System.lineSeparator();
        String expected = "001 alphanumeric123BBQ!" + System.lineSeparator() + "002 alphanumeric123bbq!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", "-f", "-n", "2", "-e", "Bar", "-i", "-e", "foo", "-i", "-x", "!", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 52
    @Test
    public void txtedTest52() throws Exception {
        String input ="football" + System.lineSeparator() +
                "football123" + System.lineSeparator() +
                "Football456" + System.lineSeparator() +
                "basketball123" + System.lineSeparator() +
                "basketball456" + System.lineSeparator() +
                "Basketball789" + System.lineSeparator();
        String expected = "001 Basketball789" + System.lineSeparator() + "002 basketball456" + System.lineSeparator() + "003 basketball123" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", "-f", "-n", "2", "-e", "football", "-i", "-e", "foot", "-i", "-r", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 53
    @Test
    public void txtedTest53() throws Exception {
        String input = "football" + System.lineSeparator() +
                "football123" + System.lineSeparator() +
                "Football456" + System.lineSeparator() +
                "basketball123" + System.lineSeparator() +
                "basketball456" + System.lineSeparator() +
                "Basketball789" + System.lineSeparator();

        String expected = "01 Basketball789!" + System.lineSeparator() + "02 basketball456!" + System.lineSeparator() + "03 basketball123!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", "-n", "2", "-e", "football", "-i", "-e", "foot", "-i", "-x", "!", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Frame #: 54
    @Test
    public void txtedTest54() throws Exception {
        String input = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();
        String expected = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }
    // Instructors 55
    // command line argument errors should display a usage message on stderr
    @Test
    public void txtedTest55() throws Exception {
        //no arguments on the command line will pass an array of length 0 to the application (not a null).
        String[] args = new String[0];
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }

    // Frame #: 56
    @Test
    public void txtedTest56() throws Exception {
        String input = "I have a cat" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My cat is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();
        String expected = "I have a dog" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My dog is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 57
    @Test
    public void txtedTest57() throws Exception {
        String input = "I have a cat" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My cat is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();
        String expected = "I have a cat" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My cat is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 58
    @Test
    public void txtedTest58() throws Exception {
        String input = "I have a cat" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My cat is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();
        String expected = "I have a dog" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My dog is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 59
    @Test
    public void txtedTest59() throws Exception {
        String input = "I have a cat" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My cat is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();
        String expected = "I have a dog" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My dog is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 60
    @Test
    public void txtedTest60() throws Exception {
        String input = "I have a cat" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My cat is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();
        String expected = "I have a dog" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My dog is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-i", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 61
    @Test
    public void txtedTest61() throws Exception {
        String input = "I have a cat" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My cat is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();
        String expected = "I have a dog" + System.lineSeparator() +
                "I have two birds" + System.lineSeparator() +
                "My dog is brown and his cat is yellow" + System.lineSeparator() +
                "I have 1 CaT and 2 birdS" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e","","-x","", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 62
    @Test
    public void txtedTest62() throws Exception {
        String input = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();
        String expected = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s","","-x","", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 63
    @Test
    public void txtedTest63() throws Exception {
        String input = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();
        String expected = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n","","-s","","-x","", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 64
    @Test
    public void txtedTest64() throws Exception {
        String input = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();
        String expected = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r","-i","-x","", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 65
    @Test
    public void txtedTest65() throws Exception {
        String input = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();
        String expected = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r","-f","","-e","", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }
    // Frame #: 66
    @Test
    public void txtedTest66() throws Exception {
        String input = "2468" + System.lineSeparator() + "ABC" + System.lineSeparator();
        String expected = "01 2468" + System.lineSeparator() + "02 ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n","","-r","-x","","-e","","-i", inputFile.getPath()};
        Main.main(args);
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
    }

}


