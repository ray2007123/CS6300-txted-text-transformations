package edu.gatech.seclass.txted;

/**
 * Interface created for use in Georgia Tech CS6300.
 * <p>
 * IMPORTANT: This interface should NOT be altered in any way.
 */
public interface TxtEdInterface {

    /**
     * Reset the TxtEd object to its initial state, for reuse.
     */
    void reset();

    /**
     * Sets the path of the input file. This method has to be called
     * before invoking the {@link #txted()}  methods.
     *
     * @param filepath The file path to be set.
     */
    void setFilepath(String filepath);

    /**
     * Set to exclude all lines containing the given string.
     * This method has to be called
     * before invoking the {@link #txted()} methods.
     *
     * @param excludeString The string to be excluded
     */
    void setStringToExclude(String excludeString);

    /**
     * Set to apply case-insensitive matching when used with -e.
     * This method has to be called
     * before invoking the {@link #txted()} methods.
     *
     * @param caseInsensitive Flag to toggle functionality
     */
    void setCaseInsensitive(boolean caseInsensitive);

    /**
     * Set to apply the skipping of lines based upon the supplied
     * parameter, lineToSkip.  0 is considered even and 1 is odd.
     * All files start with line 1, which is to be considered odd.
     * This method has to be called
     * before invoking the {@link #txted()} methods.
     *
     * @param skipLines  Flag to toggle functionality
     * @param lineToSkip Determine which lines to skip
     */
    void setSkipLines(boolean skipLines, int lineToSkip);

    /**
     * Set to apply reversing the lines of a file.
     * This method has to be called
     * before invoking the {@link #txted()} methods.
     *
     * @param reverseFile Flag to toggle functionality
     */
    void setReverseFile(boolean reverseFile);

    /**
     * Sets the suffix. This method has to be called
     * before invoking the {@link #txted()} methods.
     *
     * @param suffix The suffix to be set.
     */
    void setSuffix(String suffix);

    /**
     * Set to add line numbers to each line, with the amount of
     * padding based upon the padding parameter, starting from 1.
     * This method has to be called before invoking the
     * {@link #txted()} methods.
     *
     * @param addLineNumber Flag to toggle functionality.
     * @param padding       The amount of padding to be used.
     */
    void setAddLineNumber(final boolean addLineNumber, int padding);

    /**
     * Set to edit file in place.
     * When set, the program overwrites the input file
     * with transformed text instead of writing to stdout.
     * This method has to be called
     * before invoking the {@link #txted()} methods.
     *
     * @param inplaceEdit Flag to toggle functionality
     */
    void setInplaceEdit(boolean inplaceEdit);

    /**
     * Outputs a System.lineSeparator() delimited string that contains
     * selected parts of the lines in the file specified using {@link #setFilepath}
     * and according to the current configuration, which is set
     * through calls to the other methods in the interface.
     * <p>
     * It throws a {@link TxtEdException} if an error condition
     * occurs (e.g., when the specified file does not exist).
     *
     * @throws TxtEdException thrown if an error condition occurs
     *
     */
    void txted() throws TxtEdException;
}
