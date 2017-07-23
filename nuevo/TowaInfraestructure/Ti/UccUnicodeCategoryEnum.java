package Ti;

import java.util.LinkedList;

//==================================================================================================================
public class UccUnicodeCategoryEnum extends BenumBaseEnumAbstract
//                                                          //Similar to UnicodeCategory de .net.
{
    //##############################################################################################################
    private static LinkedList<String> lststrEnumSequence = new LinkedList<String>();
    private UccUnicodeCategoryEnum(String str_I)
    {
        super(str_I);
        UccUnicodeCategoryEnum.lststrEnumSequence.add(this.str());
    }
    @Override protected int intEnumSequence(String str_I)
    { return UccUnicodeCategoryEnum.lststrEnumSequence.indexOf(str_I); }
    //##############################################################################################################

    public static UccUnicodeCategoryEnum UPPERCASE_LETTER = new UccUnicodeCategoryEnum("UPPERCASE_LETTER");
    public static UccUnicodeCategoryEnum LOWERCASE_LETTER = new UccUnicodeCategoryEnum("LOWERCASE_LETTER");
    public static UccUnicodeCategoryEnum TITLECASE_LETTER = new UccUnicodeCategoryEnum("TITLECASE_LETTER");
    public static UccUnicodeCategoryEnum MODIFIER_LETTER = new UccUnicodeCategoryEnum("MODIFIER_LETTER");
    public static UccUnicodeCategoryEnum OTHER_LETTER = new UccUnicodeCategoryEnum("OTHER_LETTER");
    public static UccUnicodeCategoryEnum NON_SPACING_MARK = new UccUnicodeCategoryEnum("NON_SPACING_MARK");
    public static UccUnicodeCategoryEnum SPACING_COMBINING_MARK =
    new UccUnicodeCategoryEnum("SPACING_COMBINING_MARK");
    public static UccUnicodeCategoryEnum ENCLOSING_MARK = new UccUnicodeCategoryEnum("ENCLOSING_MARK");
    public static UccUnicodeCategoryEnum DECIMAL_DIGIT_NUMBER =
    new UccUnicodeCategoryEnum("DECIMAL_DIGIT_NUMBER");
    public static UccUnicodeCategoryEnum LETTER_NUMBER = new UccUnicodeCategoryEnum("LETTER_NUMBER");
    public static UccUnicodeCategoryEnum OTHER_NUMBER = new UccUnicodeCategoryEnum("OTHER_NUMBER");
    public static UccUnicodeCategoryEnum SPACE_SEPARATOR = new UccUnicodeCategoryEnum("SPACE_SEPARATOR");
    public static UccUnicodeCategoryEnum LINE_SEPARATOR = new UccUnicodeCategoryEnum("LINE_SEPARATOR");
    public static UccUnicodeCategoryEnum PARAGRAPH_SEPARATOR =
    new UccUnicodeCategoryEnum("PARAGRAPH_SEPARATOR");
    public static UccUnicodeCategoryEnum CONTROL = new UccUnicodeCategoryEnum("CONTROL");
    public static UccUnicodeCategoryEnum FORMAT = new UccUnicodeCategoryEnum("FORMAT");
    public static UccUnicodeCategoryEnum SURROGATE = new UccUnicodeCategoryEnum("SURROGATE");
    public static UccUnicodeCategoryEnum PRIVATE_USE = new UccUnicodeCategoryEnum("PRIVATE_USE");
    public static UccUnicodeCategoryEnum CONNECTOR_PUNCTUATION =
    new UccUnicodeCategoryEnum("CONNECTOR_PUNCTUATION");
    public static UccUnicodeCategoryEnum DASH_PUNCTUATION = new UccUnicodeCategoryEnum("DASH_PUNCTUATION");
    public static UccUnicodeCategoryEnum OPEN_PUNCTUATION = new UccUnicodeCategoryEnum("OPEN_PUNCTUATION");
    public static UccUnicodeCategoryEnum CLOSE_PUNCTUATION =
    new UccUnicodeCategoryEnum("CLOSE_PUNCTUATION");
    public static UccUnicodeCategoryEnum INITIAL_QUOTE_PUNCTUATION =
    new UccUnicodeCategoryEnum("INITIAL_QUOTE_PUNCTUATION");
    public static UccUnicodeCategoryEnum FINAL_QUOTE_PUNCTUATION =
    new UccUnicodeCategoryEnum("FINAL_QUOTE_PUNCTUATION");
    public static UccUnicodeCategoryEnum OTHER_PUNCTUATION =
    new UccUnicodeCategoryEnum("OTHER_PUNCTUATION");
    public static UccUnicodeCategoryEnum MATH_SYMBOL = new UccUnicodeCategoryEnum("MATH_SYMBOL");
    public static UccUnicodeCategoryEnum CURRENCY_SYMBOL = new UccUnicodeCategoryEnum("CURRENCY_SYMBOL");
    public static UccUnicodeCategoryEnum MODIFIER_SYMBOL = new UccUnicodeCategoryEnum("MODIFIER_SYMBOL");
    public static UccUnicodeCategoryEnum OTHER_SYMBOL = new UccUnicodeCategoryEnum("OTHER_SYMBOL");
    public static UccUnicodeCategoryEnum OTHER_NOT_ASSIGNED =
    new UccUnicodeCategoryEnum("OTHER_NOT_ASSIGNED");
}

//==================================================================================================================
/*END-TASK*/
