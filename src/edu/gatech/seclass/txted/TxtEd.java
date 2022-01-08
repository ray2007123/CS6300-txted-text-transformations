package edu.gatech.seclass.txted;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class TxtEd implements TxtEdInterface {
    // Empty Main class for compiling Individual Project.
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement it
    private String filePath = null;
    private boolean evalue = false;
    private String excludeString = null;
    private boolean caseInsensitive = false;
    private boolean skipLines = false;
    private int lineToSkip = 0;
    private boolean reverseFile = false;
    private boolean xvalue = false;
    private String suffix = null;
    private boolean addLineNumber = false;
    private int padding = 0;
    private boolean inplaceEdit = false;

    private List<String> fileContent = new ArrayList<>();

    @Override
    public void reset(){
        /**
         * Reset the TxtEd object to its initial state, for reuse.
         */
        this.filePath =  null;
        this.caseInsensitive = false;
        this.skipLines = false;
        this.reverseFile = false;
        this.addLineNumber = false;
        this.inplaceEdit = false;
        this.evalue = false;
        this.xvalue = false;

        this.excludeString = null;
        this.suffix = null;

        this.lineToSkip = 0;
        this.padding = 0;

        fileContent = new ArrayList<>();
    }

    @Override
    public void setFilepath(String filepath) {
        this.filePath = filepath;
    }

    @Override
    public void setStringToExclude(String excludeString) {
        this.evalue = true;
        this.excludeString = excludeString;

    }

    @Override
    public void setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public void setSkipLines(boolean skipLines, int lineToSkip) {
        this.skipLines = skipLines;
        this.lineToSkip = lineToSkip;
    }

    @Override
    public void setReverseFile(boolean reverseFile) {
        this.reverseFile = reverseFile;
    }

    @Override
    public void setSuffix(String suffix) {
        this.xvalue = true;
        this.suffix = suffix;
    }

    @Override
    public void setAddLineNumber(boolean addLineNumber, int padding) {
        this.addLineNumber = addLineNumber;
        this.padding = padding;
    }

    @Override
    public void setInplaceEdit(boolean inplaceEdit){
        this.inplaceEdit = inplaceEdit;
    }

    @Override
    public void txted() throws TxtEdException {

        if (filePath == null)
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        if (filePath.isEmpty())
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        if (evalue == true && excludeString == null)
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        if (evalue == true && excludeString.length() == 0)
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        if (skipLines == true && (lineToSkip < 0 || lineToSkip > 1))
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        if (xvalue == true && suffix == null)
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        if (xvalue == true && suffix.length() == 0)
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        if (addLineNumber == true && padding < 0)
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");


        String textLine = "";
        //Read the input file

        try (Scanner fileScanner = new Scanner(Paths.get(this.filePath))) {
            Pattern p = Pattern.compile(".*\\R|.+\\z", Pattern.MULTILINE);
            while ((textLine = fileScanner.findWithinHorizon(p, 0)) != null) {
                fileContent.add(textLine);
            }
        } catch (Exception e) {
            throw new TxtEdException("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        }


        //Text Transformations Start Here:
        if (skipLines == true && (lineToSkip == 0 || lineToSkip == 1)) {
            List<String> sl = new ArrayList<>();
            for (int i = 0; i < fileContent.size(); i++) {
                if (i % 2 == lineToSkip && this.skipLines == true) {
                    sl.add(fileContent.get(i));
                }
            }
            this.fileContent = new ArrayList<>(sl);
        }

        if (evalue == true && excludeString != null) {
            List<String> temp = new ArrayList<>();
            for (String el : fileContent) {
                if (caseInsensitive == true) {
                    if (!el.toLowerCase().contains(excludeString.toLowerCase())) {
                        temp.add(el);
                    }
                } else {
                    if (!el.contains(excludeString)) {
                        temp.add(el);
                    }
                }
            }
            this.fileContent = new ArrayList<>(temp);
        }

        if (xvalue == true && suffix != null) {
            List<String> temp = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for(int i = 0 ; i < fileContent.size() ; i++){
                String line = fileContent.get(i);
                String line2 = line.substring(0, line.length()-1);
                temp.add(line2 + suffix + System.lineSeparator());
            }
            this.fileContent = new ArrayList<>(temp);
        }

        if (reverseFile == true) {
            Collections.reverse(fileContent);
        }

        if (addLineNumber == true && padding > 0) {
            List<String> temp = new ArrayList<>();
            int i = 1;
            for (String p : fileContent) {
                if (padding == 0) {
                    temp.add(" " + p);
                } else if (String.valueOf(i).length() <= padding) {
                    String ln = StringUtils.leftPad(String.valueOf(i), padding, "0");
                    temp.add(ln + " " + p);
                } else {
                    String ln = String.valueOf(i).substring(String.valueOf(i).length() - padding);
                    temp.add(ln + " " + p);
                }
                i++;
            }
            this.fileContent = new ArrayList<>(temp);
        }

        if (this.inplaceEdit == true) {
            for (String l : fileContent) {
                System.out.print(l);
            }
        } else {
            for (String line : fileContent) {
                System.out.print(line);
            }
        }

        if(fileContent == null || fileContent.isEmpty()){
            System.out.print(System.lineSeparator());
        }

        reset();
    }
}

