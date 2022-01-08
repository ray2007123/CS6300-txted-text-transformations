package edu.gatech.seclass.txted;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    // Empty Main class for compiling Individual Project.
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement it
    private static final Charset charset = StandardCharsets.UTF_8;
    private static CommandLine cmd = null;
    private static List<String> lines;
    private static String fileContent;

    public static void main(String[] args) throws FileNotFoundException {
        // Empty Skeleton Method
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();

        options.addOption("f", false, "string");
        options.addOption("n", true, "integer");
        options.addOption("s", true, "integer, 0 or 1");
        options.addOption("x", true, "string");
        options.addOption("i", false, "together with e, insensitive matching");
        options.addOption("e", true, "string");
        options.addOption("r", false, "reverse");

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            usage();
            return;
        }
        ArrayList<String> argList = new ArrayList<>(cmd.getArgList());

        if ((cmd.getOptions().length) == 0 && (argList.size() > 1)) {
            usage();
            return;
        }

        if (argList.size() == 0 || (cmd.getOptions().length) == 0) {
            usage();
            return;
        }

        if (cmd.hasOption("i") && !cmd.hasOption("e")) {//查看是否符合条件，不能只有"i"
            usage();
            return;
        }

        String fileName = argList.get(argList.size() - 1);

        try {
            lines = Files.readAllLines(Paths.get(fileName), charset);
        } catch (InvalidPathException | IOException e) {
            usage();
            return;
        }

        try {
            fileContent = new String(Files.readAllBytes(Paths.get(fileName)), charset);
        } catch (InvalidPathException e) {
            usage();
            return;
        } catch (FileNotFoundException e) {
            usage();
            return;
        } catch (IOException e) {
            usage();
            return;
        }

        if (cmd.hasOption("s")) {
            String sss = cmd.getOptionValues("s")[cmd.getOptionValues("s").length - 1];
            try {
                if (Integer.parseInt(sss) == 0) {
                    lines = optionS(0);
                } else if (Integer.parseInt(sss) == 1) {
                    lines = optionS(1);
                } else {
                    usage();
                    return;
                }
            } catch (NumberFormatException e) {
                usage();
                return;
            }
        }

        if (cmd.hasOption("e")) {
            String a = cmd.getOptionValues("e")[cmd.getOptionValues("e").length - 1];
            boolean caseInsensitive = cmd.hasOption("i");//需要判断，need verify
            if (!"".equals(a)) {
                lines = optionE(a, caseInsensitive);
            }else{
                usage();
                return;
            }
        }

        if (cmd.hasOption("x")) {
            String a1 = cmd.getOptionValues("x")[cmd.getOptionValues("x").length - 1];
            if (!"".equals(a1)) {
                lines = optionX(a1);
            } else {
                usage();
                return;
            }
        }

        if (cmd.hasOption("r")) {
            optionR();
        }

        if (cmd.hasOption("n")) {
            String arg = cmd.getOptionValues("n")[cmd.getOptionValues("n").length-1];
            int b = Integer.parseInt(arg);
            if (b < 0) {
                usage();
                return;
            }
            lines = optionN(b);
        }

        if (cmd.hasOption("f")) {
            PrintWriter writer = new PrintWriter(fileName);
            for (String l : lines) {
                writer.write(l + System.getProperty("line.separator"));
            }
            writer.close();
            writer.flush();
        } else {
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }

    private static List<String> optionN(int n) {
        List<String> temp = new ArrayList<>();
        int i = 1;
        for (String p : lines) {
            if (n == 0) {
                temp.add(" " + p);
            } else if (String.valueOf(i).length() <= n) {
                String ln = StringUtils.leftPad(String.valueOf(i), n, "0");
                temp.add(ln + " " + p);
            } else {
                String ln = String.valueOf(i).substring(String.valueOf(i).length() - n);
                temp.add(ln + " " + p);
            }
            i++;
        }
        return temp;
    }

    private static List<String> optionX(String arg) {
        List<String> temp = new ArrayList<>();
        for (String xl : lines) {
            temp.add(xl + arg);
        }
        return temp;
    }

    private static void optionR() {

        Collections.reverse(lines);
    }

    private static List<String> optionE(String arg, boolean caseInsensitive) {
        List<String> temp = new ArrayList<>();
        for (String el : lines) {
            if (caseInsensitive) {
                if (!el.toLowerCase().contains(arg.toLowerCase())) {
                    temp.add(el);
                }
            } else {
                if (!el.contains(arg)) {
                    temp.add(el);
                }
            }
        }
        return temp;
    }
    private static List<String> optionS(int arg) {
        List<String> sl = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (i % 2 == arg) {
                sl.add(lines.get(i));
            }
        }
        return sl;
    }

    private static void usage() {
        System.err.println("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
    }
}
