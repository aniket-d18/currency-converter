package converter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * A custom document filter that limits the number of characters
 * that can be typed into a JTextField. Optionally converts input to uppercase.
 * 
 * @author Aniket Chaudhari
 */
public class InputLengthFilter extends PlainDocument {

    private final int maxLength;
    private final boolean forceUppercase;

    /**
     * Creates a filter with a maximum character limit.
     * @param maxLength  Maximum number of characters allowed
     */
    public InputLengthFilter(int maxLength) {
        this(maxLength, false);
    }

    /**
     * Creates a filter with a maximum character limit and optional uppercase conversion.
     * @param maxLength       Maximum number of characters allowed
     * @param forceUppercase  If true, all input is converted to uppercase
     */
    public InputLengthFilter(int maxLength, boolean forceUppercase) {
        super();
        this.maxLength = maxLength;
        this.forceUppercase = forceUppercase;
    }

    @Override
    public void insertString(int offset, String text, AttributeSet attributes)
            throws BadLocationException {
        if (text == null) {
            return;
        }

        // Only allow insertion if it doesn't exceed the max length
        if ((getLength() + text.length()) <= maxLength) {
            String insertText = forceUppercase ? text.toUpperCase() : text;
            super.insertString(offset, insertText, attributes);
        }
    }
}
