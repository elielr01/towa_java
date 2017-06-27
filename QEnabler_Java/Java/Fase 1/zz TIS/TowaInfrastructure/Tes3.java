package TowaInfrastructure;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;


//AUTHOR: Towa (EELR-Elí Linares).
                                                            //CO-AUTHOR: Towa ().
                                                            //DATE: 13-Mayo-2011.
                                                            //PURPOSE:
                                                            //Implementation of strTo method for all Java data types.

public class Tes3
{
    //=================================================================================================================
    /*TASK Tes3.PrepareStrTo Constants and initializer for strTo (Only the ones necessary for Fase 0*/
    //-----------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS (Only the needed ones for Fase 0)*/
                                                            //Si un String excede esta longitud, se muestra la longitud
                                                            //      ejemplo "abd def.... xyz"<88>.
    private final static int intLONG_STRING = 50;

                                                            //In methods strTo, an Item/Row/Matrix of this characters
                                                            //      size will be include in one long line.
    private final static int intLONG_ITEM_ROW_MATRIX = 40;

                                                            //This will be the maximun space reseved for key when strTo
                                                            //      display a dictionary, if we have longhest key the
                                                            //      content will not be aligned.
    private final static int intKEY_LEN_MAX = 50;

                                                            //Caracter que será usado como substituto cuando un caracter
                                                            //      no sea "visible".
    private final static char charSUBSTITUTE_NONVISIBLE = '^';

                                                            //Se tiene 4 posible tipos de caracteres, ver charType.

                                                            //El siguiente String son caracteres que se pueden
                                                            //      introducir por el teclaro.
                                                            //charType.KEBOARD.
    private final static String strCHAR_KEYBOARD =
                                                            //El espacio en blanco es un caracter que se puede teclear.
        " " +
                                                            //Caracteres normales.
        "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" +
                                                            //Caracteres acentuados.
        "ÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÂÊÎÔÛ" + "áéíóúàèìòùäëïöüâêîôû" + "Ññ" +
                                                            //Otras consonantes acentuadas.
        "ŔÝŚĹŹĆŃŸŴŶŜĜĤĴĈ" + "ŕýśĺźćńÿŵŷŝĝĥĵĉ" +
                                                            //Acentos solos.
        "´`¨^" +

                                                            //Caracteres especiales, que aparecen en teclado de Mac
                                                            //      (Spanish - ISO).

                                                            //Teclas de números.
        "ºª" + "\\" + "!|" + "\"" + "@" + "·#" + "$" + "%" + "&¬" + "/" + "(" + ")" + "=" + "'?" + "¡¿" +
                                                            //Teclas QW.....
        "€[*+]" +
                                                            //Teclas AS.....
        "{çÇ}" +
                                                            //Teclas ZX.....
        "<>,;.:-_" +
                                                            //Otras teclas que estan en DELL y no en la MAC
        "~";

                                                            //Los caracteres anteriores en un arreglo ordenado.
    private static char[] arrcharKEYBOARD;

                                                            //El siguiente arreglo son tuplos con info de caracteres
                                                            //      que no se pueden desplegar (distorsionan la imagen
                                                            //      en pantalla y/o archivo de texto).
                                                            //charType.NONVISIBLE_WITH_DESCRIPTION.
                                                            //Los tuplos son: <caracter, descripción>.
                                                            //Estos caracteres no deben existir en arrcharKEYBOARD.
    private static /*readonly*/ T2charDescriptionTuple[] arrt2charNONVISIBLE_WITH_DESCRIPTION =
    {
        new T2charDescriptionTuple('\0', "\\0 Zero"),
        new T2charDescriptionTuple('\007', "\\a Bell (alert)"),
        new T2charDescriptionTuple('\b', "\\b Backspace"),
        new T2charDescriptionTuple('\f', "\\f Formfeed"),
        new T2charDescriptionTuple('\n', "\\n New Line"),
        new T2charDescriptionTuple('\r', "\\r Carriage Return"),
        new T2charDescriptionTuple('\t', "\\t Horizontal Tab"),
        new T2charDescriptionTuple('\u000B', "\\v Vertical Tab"),
        new T2charDescriptionTuple((char)(128 * 64 + 11), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 12), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 13), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 14), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 40), "similar to \\n New Line"),
        new T2charDescriptionTuple((char)(128 * 64 + 41), "similar to \\r Carriage Return"),
        new T2charDescriptionTuple((char)(128 * 64 + 42), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 43), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 44), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 45), "\'\' empty character"),
        new T2charDescriptionTuple((char)(128 * 64 + 46), "\'\' empty character"),
    };


                                                            //Con el arreglo anterior se generan los siguientes 2
                                                            //      arreglos y se ordenan por el primero.
    private static /*readonly*/ char[] arrcharNONVISIBLE;
    private static /*readonly*/ String[] arrstrDESCRIPTION_NONVISIBLE;

    private static int[][] arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION =
        {
            { 1, 6 },
            { 14, 31 },
            { 127,  128 + 31 },
            { 128 + 45, 128 + 45 },
            { 128 * 5 + 94, 128 * 5 + 94 },
                                                            //Distorcionan el despliegue en forma extraña, parece que
                                                            //      tiene efecto sobre lo que se desplego ANTES.
            { 128 * 5 + 101, 128 * 5 + 101 }, { 128 * 5 + 103, 128 * 5 + 104 },
            { 128 * 6, 128 * 6 + 17 }, { 128 * 6 + 19, 128 * 6 + 78 }, { 128 * 6 + 80, 128 * 6 + 111 },
            { 128 * 9 + 3, 128 * 9 + 6 },
            { 128 * 11 + 62, 128 * 11 + 62 }, { 128 * 11 + 64, 128 * 11 + 64 }, { 128 * 11 + 67, 128 * 11 + 67 },
            { 128 * 11 + 70, 128 * 11 + 70 }, { 128 * 11 + 80, 128 * 11 + 106 }, { 128 * 11 + 112, 128 * 11 + 116 },
            { 128 * 12, 128 * 12 + 3 }, { 128 * 12 + 11, 128 * 12 + 11 }, { 128 * 12 + 13, 128 * 12 + 13 },
            { 128 * 12 + 27, 128 * 12 + 27 }, { 128 * 12 + 30, 128 * 12 + 31 }, { 128 * 12 + 33, 128 * 12 + 58 },
            { 128 * 12 + 64, 128 * 12 + 74 }, { 128 * 12 + 109, 128 * 12 + 111 }, { 128 * 12 + 113, 128 * 12 + 127 },
            { 128 * 13, 128 * 13 + 85 }, { 128 * 13 + 93, 128 * 13 + 93 }, { 128 * 13 + 101, 128 * 13 + 102 },
            { 128 * 13 + 110, 128 * 13 + 111 }, { 128 * 13 + 122, 128 * 13 + 127 },
            { 128 * 14, 128 * 14 + 13 }, { 128 * 14 + 16, 128 * 14 + 16 }, { 128 * 14 + 18, 128 * 14 + 47 },
            { 128 * 14 + 77, 128 * 14 + 109 },
            { 128 * 15, 128 * 15 + 37 }, { 128 * 15 + 49, 128 * 15 + 49 },
                                                            //Non printable character
            { '\uD800', '\uFFFF' },
        };

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

                                                            //Object previously processed in other strTo execution.
    private static LinkedList<Object> lstobjPreviouslyProcessed;

    //------------------------------------------------------------------------------------------------------------------

    //==============================================================================================================
    /*TASK Tes2.Types Set of Methods to Analize Types*/
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/
                                                            //Towa's standard primitives
    private static /*readonly*/ String[][] arr2strPRIMITIVE_TYPE_AND_PREFIX = {
                                                            //TO ADD NEW PRIMARY TYPES:
                                                            //a. Add an entry in this array (standard prefix xxxx).
                                                            //b. Add a method subfunConvertAndBoxXxxx, similar to
                                                            //      subfunConvertAndBoxTs).
                                                            //c. Add a method strAnalizeAndFormatXxxx, similar to
                                                            //      strAnalizeAndFormatTs).
                                                            //d. Add case branch in method
                                                            //      subfunConvertAndBoxPrimitive.
                                                            //e. Add case branch in method strAnalizeAndFormatBbox.

                                                            //The java primitives, when passed as an argument that receives
                                                            //      an Object, are automatically boxed in their
                                                            //      corresponding Object (Integer for int, Double for
                                                            //      double, etc.). Because the methods in strTo declare
                                                            //      Object parameters, the "primitive" objects must be
                                                            //      added here, so that when receiving an Integer, it will
                                                            //      work as if it had received an int.
                                                            //A way to avoid this would be to do a method overload for each
                                                            //      kind of primitive for the methods that receive Objects,
                                                            //      for example, making 5 overloads to the method
                                                            //      strTo(Object, string) like this:
                                                            //      strTo(int, string); strTo(long, string);
                                                            //      strTo(char, string); strTo(double, string);
                                                            //      strTo(boolean, string).
                                                            //      However, because this method(strTo) also invokes other
                                                            //      methods that have Objects as parameters, the number of
                                                            //      method overloads would be too much. Because of this, the
                                                            //      structure with the Object parameters will be kept for
                                                            //      now, but may change in the future.
        { "int", "int" }, { "long", "long" }, { "boolean", "bool" }, { "char", "char" }, { "double", "num" },
        { "Integer", "int" }, { "Long", "long" }, { "Boolean", "bool" }, { "Character", "char" }, { "Double", "num" },
                                                            //C# structures should be handled like primitives
        { "LocalDateTime", "ts" },

    };

          												    //Both arrays order by first.

    private static String[] arrstrPRIMITIVE_TYPE;
    private static String[] arrstrPRIMITIVE_PREFIX;

                                                            //Towa's standard system types
    private static /*readonly*/ String[][] arr2strSYSTEM_TYPE_AND_PREFIX = {
                                                            //TO ADD NEW SYSTEM TYPES:
                                                            //a. Add an entry in this array (standard prefix yyyyy).
                                                            //b. Add a method subfunConvertYyyyy, similar to
                                                            //      subfunConvertSysdir).
                                                            //c. Add a method strAnalizeAndFormatYyyyy, similar to
                                                            //      strAnalizeAndFormatSysdir).
                                                            //d. Add case branch in method subfunConvertSystemType.
                                                            //e. Add case branch in method
                                                            //      strAnalizeAndFormatSystemType.

                                                            //String should be handled like system tyes.
        { "String", "str" },
                                                            //System types
        { "Class", "type" }, { "Path", "Sysdir" }, { "File", "Sysfile" },
        { "Scanner", "Syssr" }, { "PrintWriter", "Syssw" },
    };


                                                            //Both arrays order by first.
    private static /*readonly*/ String[] arrstrSYSTEM_TYPE;
    private static /*readonly*/ String[] arrstrSYSTEM_PREFIX;

                                                            //Towa's standard other types
    private final static String strGENERIC_LIST_TYPE = "LinkedList";
    private final static String strGENERIC_QUEUE_TYPE = "ConcurrentLinkedDeque";
    private final static String strGENERIC_STACK_TYPE = "Stack";
    private final static String strGENERIC_DICTIONARY_TYPE = "LinkedHashMap";
    private final static String strGENERIC_KEYVALUEPAIR_TYPE = "Entry";
    private static /*readonly*/ String[][] arr2strGENERIC_TYPE_AND_PREFIX = {
        { strGENERIC_LIST_TYPE, "lst" }, { strGENERIC_QUEUE_TYPE, "queue" }, { strGENERIC_STACK_TYPE, "stack" },
        { strGENERIC_DICTIONARY_TYPE, "dic" }, { strGENERIC_KEYVALUEPAIR_TYPE, "kvp" },
    };

                                                            //Both arrays order by first.
    private static String[] arrstrGENERIC_TYPE;
    private static String[] arrstrGENERIC_PREFIX;


                                                            //Boxing classes used for boxing primitives when they must
                                                            //      be passed by reference (out, ref).
    private static String[][] arr2strBOXING_TYPE_AND_PREFIX = {
                                                            //TO ADD NEW BOXING TYPES:
                                                            //a. Add an entry in this array.
        { "Oint", "int" }, { "Olong", "long" }, { "Obool", "bool" }, { "Ochar","char" }, { "Onum","num" }
    };

                                                            //Both arrays order by first.
    public static String[] arrstrBOXING_TYPE;
    public static String[] arrstrBOXING_PREFIX;

    //==================================================================================================================
    /*TASK Tes3.strTo Set of Methods for Primitive Java Types*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        int int_I,
                                                            //Option should be SHORT.
        TestoptionEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != TestoptionEnum.SHORT
            )
            Tools.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatInt(int_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        int int_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tools.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatInt(int_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatInt(           //Prepara un entero (long) para su despliege con información
                                                            //      adicional si es mínimo o máximo.
                                                            //Ejemplos:
                                                            //1 -3,835.
                                                            //2 -9,223,372,036,854,775,808<MinValue>.
                                                            //3 9,223,372,036,854,775,807<MaxValue>.
                                                            //str, String para despligue con información adicional.

                                                            //Entero a desplegar.
        int int_I
    )
    {
                                                            //It's prepare without additional information. It's separed
                                                            //      in thousands by commas ("1000" = "1,000").
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        String strAnalizeAndFormatInt = decimalFormatter.format(int_I);

                                                            //Add information in case it is the minimum or maximum Int.
        if (
            int_I == Integer.MIN_VALUE
            )
        {
            strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MinValue>";
        }
        else if (
            int_I == Integer.MAX_VALUE
            )
        {
            strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MaxValue>";
        }
        else
        {
                                                            //No additional information.
        }
        return strAnalizeAndFormatInt;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        long long_I,
                                                            //Option should be SHORT.
        TestoptionEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != TestoptionEnum.SHORT
            )
            Tools.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatLong(long_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        long long_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tools.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatLong(long_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatLong(          //Prepara un entero (long) para su despliege con información
                                                            //      adicional si es mínimo o máximo.
                                                            //Ejemplos:
                                                            //1 -3,835.
                                                            //2 -9,223,372,036,854,775,808<MinValue>.
                                                            //3 9,223,372,036,854,775,807<MaxValue>.
                                                            //str, String para despligue con información adicional.

                                                            //Entero a desplegar.
        long long_I
    )
    {
                                                            //It's prepare without additional information. It's separed
                                                            //      in thousands by commas ("1000" = "1,000").
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        String strAnalizeAndFormatLong = decimalFormatter.format(long_I);

                                                            //Añade información adicional si es mínimo o máximo.
        if (
            long_I == Long.MIN_VALUE
            )
        {
            strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MinValue>";
        }
        else if (
            long_I == Long.MAX_VALUE
            )
        {
            strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MaxValue>";
        }
        else
        {
                                                            //Sin información adicional.
        }

        return strAnalizeAndFormatLong;
    }
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        double num_I,
                                                            //Option should be SHORT.
        TestoptionEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != TestoptionEnum.SHORT
            )
            Tools.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatNum(num_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        double num_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tools.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatNum(num_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatNum(           //Prepara un número para su despliege con información
                                                            //      adicional si es mínimo, máximo, etc.
                                                            //Ejemplos:
                                                            //1 -1.23456789012345E+038.
                                                            //2 -1.79769313486232E+308<MinValue>;
                                                            //3 1.79769313486232E+308<MaxValue>;
                                                            //4 NaN<0/0>;
                                                            //5 -Infinity<-?/0>;
                                                            //6 Infinity<?/0>;
                                                            //str, String para despligue con información adicional.

                                                            //Entero a desplegar.
        double num_I
    )
    {
                                                            //Por lo pronto prepara sin información adicional.
        String strAnalizeAndFormatNum = String.format("%f", num_I);

                                                            //Añade información adicional si es mínimo o máximo.
        if (
            num_I == Double.MIN_VALUE
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MinValue>";
        }
        else if (
            num_I == Double.MAX_VALUE
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MaxValue>";
        }
        else if (
            num_I == Double.NEGATIVE_INFINITY
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<-?/0.0>";
        }
        else if (
            num_I == Double.POSITIVE_INFINITY
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<?/0.0>";
        }
        else if (
                                                            //A number has 4 posibibilities:
                                                            //1. Beetwen MinValue and MaxValue
                                                            //2. NegativeInfinity, (-?/0.0).
                                                            //3. PositiveInfinity, (?/0.0).
                                                            //4. NaN, (0.0/0.0).
                                                            //num_I == Double.NaN, DO NOT FUNCTION AS EXPECTED
            !((num_I >= Double.MIN_VALUE) && (num_I <= Double.MAX_VALUE))
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<0.0/0.0>";
        }
        else
        {
                                                            //Sin información adicional.
        }

        return strAnalizeAndFormatNum;
    }
    //------------------------------------------------------------------------------------------------------------------

    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        boolean bool_I,
                                                            //Option should be SHORT.
        TestoptionEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != TestoptionEnum.SHORT
            )
            Tools.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatBool(bool_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        boolean bool_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tools.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatBool(bool_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatBool(          //Prepara un booleno para su despliege.
                                                            //Ejemplos:
                                                            //1 true.
                                                            //2 false.
                                                            //str, String para despligue.
        boolean bool_I
    )
    {
                                                            //Se asigna true o false según corresponda.
                                                            //Se hace de esta forma dado que el bool_I.ToString()
                                                            //      produce "True" o  "False" (iniciando con mayúsculas
                                                            //      que es distinto a las literales true y false).
        String strAnalizeAndFormatBool;
        if (
            bool_I
            )
        {
            strAnalizeAndFormatBool = "true";
        }
        else
        {
            strAnalizeAndFormatBool = "false";
        }
        return strAnalizeAndFormatBool;
    }
    //------------------------------------------------------------------------------------------------------------------

    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        char char_I,
                                                            //Option should be SHORT.
        TestoptionEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != TestoptionEnum.SHORT
            )
            Tools.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatChar(char_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        char char_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tools.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatChar(char_I) + ")";
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatChar(          //Prepara un caracter para su despliege con información
                                                            //      adicional si el caracter es extraño.
                                                            //Ejemplos:
                                                            //1 'c'.
                                                            //2 '©'<0x00A9>.
                                                            //3 '^'<0x0009, \t, Horizontal Tab>.
                                                            //1) No tiene nada extraño, solo se añaden las comillas.
                                                            //2) El caracter © no aparece en el teclado, incluyo su
                                                            //      su hexadecimal.
                                                            //3) El caracter es un Horizonal Tab, no es visible, se
                                                            //      sustituye por ^ (el caracter en
                                                            //      charSUBSTITUTE_NONVISIBLE), incluyo su hexadecimal y
                                                            //      su descripción.

                                                            //Caracter a analizar.
        char char_I
    )
    {
                                                            //Determino tipo de caracter.
        TestchartypeEnum testchartype = Tes3.testchartypeKeyboardOrEtc(char_I);

                                                            //Para formar lo que va a regresar, esto depende del tipo de
                                                            //      caracter.
        String strAnalizeAndFormatChar;
        if (
                                                            //Es visible.
            (testchartype == TestchartypeEnum.KEYBOARD) || (testchartype == TestchartypeEnum.VISIBLE_NONKEYBOARD)
            )
        {
                                                            //Es visible, solo pone entre comillas
            strAnalizeAndFormatChar = "'" + char_I + "'";
        }
        else
        {
                                                            //Procesa cuando no es visible.
            strAnalizeAndFormatChar = "'" + charSUBSTITUTE_NONVISIBLE + "'";
        }

                                                            //Añade info de diagnóstico cuando no es del KEYBOARD.
        if (
            testchartype != TestchartypeEnum.KEYBOARD
            )
        {
                                                            //Añade info de diagnóstico.

                                                            //Formatea la tupla cuando no es visible (primera parte).
            strAnalizeAndFormatChar = strAnalizeAndFormatChar + "<" + "0x" + String.format("%04X", (int)char_I);

            if (
                testchartype == TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION
                )
            {
                                                            //Completa la tupla cuando tiene descripción.
                int intDesctiption = Arrays.binarySearch(arrcharNONVISIBLE, char_I);
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + ", " +
                        arrstrDESCRIPTION_NONVISIBLE[intDesctiption] + ">";
            }
            else
            {
                                                            //Completa la tupla cuando NO tiene descripción.
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + ">";
            }
        }

        return strAnalizeAndFormatChar;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static TestchartypeEnum testchartypeKeyboardOrEtc(
                                                            //Revisa un caracter para determinar su tipo.

                                                            //Caracter que debrerá ser revisado.
        char charARevise_I
    )
    {
        TestchartypeEnum testchartypeKeyboardOrEtc;

        /*CASE*/
        if (
                                                            //Es caracter del teclado.
            Arrays.binarySearch(Tes3.arrcharKEYBOARD, charARevise_I) >= 0
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.KEYBOARD;
        }
        else if (
            Arrays.binarySearch(Tes3.arrcharNONVISIBLE, charARevise_I) >= 0
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION;
        }
        else if (
            Tes3.boolIsNonVisibleWithoutDescription(charARevise_I)
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITHOUT_DESCRIPTION;
        }
        else
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.VISIBLE_NONKEYBOARD;
        }
        /*END-CASE*/

        return testchartypeKeyboardOrEtc;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsNonVisibleWithoutDescription(
                                                            //Verifica si un caracter es no visible sin descripción.
                                                            //bool, true si es no visible sin descripción.

                                                            //Caracter que se desea verificar.
        char charAVerificar_I
    )
    {
                                                            //Extrae el número del caracter.
        int intChar = (int)charAVerificar_I;

                                                            //Busca el rango donde pudiera estar incluido.
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
                                                            //Ya no hay rangos.
            (intI >= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) ||
                                                            //El caracter a verificar puede estar en este rango.
            (intChar <= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][ 1])
        ))
        {
            intI = intI + 1;
        }

        return (
                                                            //Esta posicionado en un rango.
            (intI < Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) &&
                                                            //El caracter a verificar esta incluido en este rango.
            (intChar >= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0])
        );
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    /*TASK Tes3.strTo Set of Methods for Java Objects*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display.
                                                            //The strategy is:
                                                            //1. 2 strTo methods (this and next with obj_I parameter)
                                                            //      will handle all types except generic containing
                                                            //      bclass, btuple, enum or sysexcep.
                                                            //2. 2 strTo methods with 3 paramenters will handle 1
                                                            //      argument generic containing bclass, btuple, enum or
                                                            //      excep.
                                                            //3. 2 strTo methods with 4 paramenters will handle
                                                            //      dicbclass, dicbtuple and dicenun.
                                                            //4. 2 strTo methods with 4 paramenters will handle
                                                            //      kvpbclass, kvpbtuple and kvpenun.
                                                            //5a. Each one of the pair of strTo methods call a
                                                            //      strToSupportXxxxx private method (4 methods) to
                                                            //      handle most checks needed, process null values and
                                                            //      call strToSharedYyyyy private methods to generate
                                                            //      the information requested.
                                                            //5b. "primitives" are not easy to process (they require an
                                                            //      specific method for each one), to solve this
                                                            //      problem, "primitives" will be boxed using Oint,
                                                            //      Olong, ... boxing clases, this will be done in the
                                                            //      strToSupportAnyType method.

                                                            //str, info to display

                                                            //Read strToSupportAnyType method for paramenters
                                                            //      description
        Object obj_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        if (
            testoptionSHORT_I != TestoptionEnum.SHORT
            )
            Tools.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strToSupportAnyType(obj_I, testoptionSHORT_I, null);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(                             //Prepare for FULL display.

                                                            //str, info to display

                                                            //Read strToSupportAnyType method for paramenters
                                                            //      description
        Object obj_I,
        String strText_I
    )
    {
        if (
            strText_I == null
            )
            Tools.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return Tes3.strToSupportAnyType(obj_I, TestoptionEnum.FULL, strText_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportAnyType(              //Continue preparation for display.

                                                            //str, info to display

                                                            //Any standard type except generic types containing bclass,
                                                            //      btuple or enum types (those require a transformation
                                                            //      before calling strTo method with 3 or 4 paramenters)
        Object obj_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of the object.
        String strText_I
    )
    {
        String strToSupportAnyType;
        if (
            obj_I == null
            )
        {
            if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
            {
                strToSupportAnyType = "null";
            }
            else
            {
                strToSupportAnyType = strText_I + "(null)";
            }
        }
        else
        {

            //TODO creo que esto se puede quitar
                                                            //Abort if not a valid type
            //Tes3.subVerifyAnyType(obj_I);

            //TODO forget about the boxing for a while. But check it out cuz objMain is sent to all the methods
                                                            //Do the boxing.
            //Object objMain;
            //Object objKey;
            //Tes3.subfunConvertAndBox(out objMain, out objKey, obj_I);

                                                            //Call required strToSharedYyyyy
            Class classObj = obj_I.getClass();
            /*CASE*/
            if (
                classObj.isArray()
                )
            {
                                                            //Is arrobj[], arrobj[,] or arrobj[, ,].
                                                            //All contents are boxed primitives, simple and
                                                            //      system types, bclass, btuple, enum or sysexcep


                int intRank = Tools.intArrRank(classObj);
                /*CASE*/
                if (
                    intRank == 1
                    )
                {
                                                            //Is arrobj[], call with 3 paramenters
                    strToSupportAnyType = Tes3.strFormatArrOrOneArgumentGeneric(
                            (Object[])objMain, testoptionOption_I, strText_I, obj_I);
                }
                else if (
                    intRank == 2
                    )
                {
                                                            //Is arrobj[,]
                    strToSupportAnyType = Tes3.strFormatArr2Main((Object[,])objMain,
                        testoptionOption_I, strText_I, obj_I);
                }
                else
                {
                                                            //Is arrobj[, ,]
                    strToSupportAnyType = Tes3.strFormatArr3Main((Object[, ,])objMain,
                        testoptionOption_I, strText_I, obj_I);
                }
                /*END-CASE*/
            }
            else if (
                Tools.boolIsGenericType(classObj)
                )
            {
                                                            //Is 1 or 2 arguments.
                                                            //All contents are boxed primitives, simple and
                                                            //      system types

                if (
                                                            //Is List<Object>, ...
                    java.util.List.class.isAssignableFrom(classObj)
                    )
                {
                                                            //lstobj, ... were converted to arrobj
                    strToSupportAnyType = Tes3.strFormatArrOrOneArgumentGeneric(
                            (Object[])objMain, testoptionOption_I, strText_I, obj_I);
                }
                else
                {
                                                            //Is Dictionary<String, Object> or
                                                            //      KeyValuePair<String,_Object>

                    if (
                        java.util.Dictionary.class.isAssignableFrom(classObj)
                        )
                    {
                                                            //dicobj was converted to arrstr and arrobj
                        strToSupportAnyType = Tes3.strFormatDicMain(
                                (Object[])objMain, (String[])objKey, testoptionOption_I, strText_I,
                                obj_I);
                    }
                    else
                    {
                                                            //kvpobj was converted to str and obj
                        strToSupportAnyType = Tes3.strFormatKvpMain(objMain, (String)objKey, testoptionOption_I,
                                strText_I, obj_I);
                    }
                }
            }
            else
            {
                                                            //Is single type
                strToSupportAnyType = Tes3.strFormatSingle(objMain, testoptionOption_I, strText_I);
            }
            /*END-CASE*/
        }

        return strToSupportAnyType;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    /*TASK Tes3.strTo Set of Methods for Singletons*/
    //------------------------------------------------------------------------------------------------------------------
    private static String strFormatSingle(                  //Format for display.

                                                            //str, formated info

                                                            //Any single type (no arrays or generic types)
        Object obj_I,
                                                            //FULL or SHORT display.
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of the single object.
        String strText_I
    )
    {
        Class classObj = obj_I.getClass();
        if (
            classObj.isArray() ||
            Tools.boolIsGenericType(classObj)
            )
            Tools.subAbort(Tes3.strTo(classObj, "obj_I.GetType") +
                " only single types can be processed in this method");

        String strFormatSingle;
        if (
            testoptionOption_I == TestoptionEnum.SHORT
            )
        {
            strFormatSingle = Tes3.strFormatSingleShort(obj_I);
        }
        else
        {
            strFormatSingle = Tes3.strFormatSingleFull(obj_I, strText_I);
        }

        return strFormatSingle;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatSingleShort(             //Format for display.

                                                            //str, formated info

                                                            //Read strFormatSingle method for paramenters description
        Object obj_I
    )
    {
        /*
        String strFormatSingleShort;
        if (
            (BclassBaseClassAbstract.class.isAssignableFrom(obj_I.getClass())) ||
            (BtupleBaseTupleAbstract.class.isAssignableFrom(obj_I.getClass()))
            )
        {
                                                            //Only id.
            strFormatSingleShort = Tes3.strGetObjId(obj_I);
        }
        else
        {
                                                            //Any other type shows a single object
            strFormatSingleShort = Tes3.strAnalizeAndFormatCheckNulls(obj_I, TestoptionEnum.SHORT);
        }

        return strFormatSingleShort;
        */

        return Tes3.strAnalizeAndFormatCheckNulls(obj_I, TestoptionEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatSingleFull(              //Format for display.

                                                            //str, formated info

                                                            //Read strFormatSingle method for paramenters description
        Object obj_I,
        String strText_I
    )
    {
                                                            //To display a bclass first time requires a block
        String strFormatSingleFull;
        if (
                                                            //Bclass processed for first time.
            BclassBaseClassAbstract.class.isAssignableFrom(obj_I.getClass()) &&
            !Tes3.lstobjPreviouslyProcessed.contains(obj_I)
            )
        {
                                                            //A first time bclass should be display inside a block
            String strNL;
            String strLabel;
                                                            //The objId will be display before bclass, is not
                                                            //      in the block headings
            Tes3.subBlockStart(out strNL, out strLabel, out strFormatSingleFull, strText_I, "");

            strFormatSingleFull = strFormatSingleFull + strNL +
                    Test.strAnalizeAndFormatCheckNulls((BclassBaseClassAbstract)obj_I, TestoptionEnum.FULL);

            Tes3.subBlockEnd(ref strNL, ref strFormatSingleFull, strLabel);
        }
            else
        {
                                                            //No blocking requires, any single type
            strFormatSingleFull = strText_I + "(" +
                    Tes3.strAnalizeAndFormatCheckNulls(obj_I, TestoptionEnum.FULL) +")";
        }

        return strFormatSingleFull;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tes3.Blocking Support blocking in the display Objects Info*/
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

                                                            //Si hay mas de 28 niveles, se les pone el úlltimo.
    private final static String strLETTERS_FOR_LEVEL = "?ABCDEFGHIJKLMNOPQRSTUVWXYZ*";

                                                            //Si hay mas de 25 niveles, se usa el último valor.
    private static int[] arrintLevelSpaces = {
        0, 4, 8, 12, 16, 20, 24, 27, 30, 33, 36, 39, 42, 44, 46, 48, 50, 52, 54, 55, 56, 57, 58, 59, 60
    };


    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

                                                            //Todas las clases no estaticas incluyen el método strTo
                                                            //      para mostrar el contenido de dicha clase, algunos de
                                                            //      estos métodos requieren block START-END para mostrar
                                                            //      el contenido de sus objetos cuando estos contienen
                                                            //      colecciones.

                                                            //Cada block START-END debe estar en un nivel superior a su
                                                            //      base, se incrementa al iniciar el block y se
                                                            //      decrementa al cerrarlo.
    private static int intLevel;

                                                            //Esta variable se usa para en cada block START-END
                                                            //      asignarle un número único (para esto, al tomar el
                                                            //      valor se incrementa en 1).
    private static int intStartEnd;

                                                            //Cada nivel, del 1 en adelane, tiene asociada una letra (A,
                                                            //      B, ...).
                                                            //Tambien, a cada nivel se le asocia una indentación al
                                                            //      inicio de cada línea (esto es una cantidad de
                                                            //      espacios).

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsToBlockFormat(
            //Método de apoyo llamado en constructor estático.
            //Inicia Nivel y StartEnd necesarios para indentar el log.
    )
    {
        Tes3.intLevel = 0;
        Tes3.intStartEnd = 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subInitializeLog(
                                                            //Inicializa el log para UNA prueba. (Al inicio de cada Test
                                                            //      se debe ejecutar este método).
                                                            //1. Genera syssw para el log.
                                                            //2. Escribe en log: Nombre Tester, ts y name del log.

                                                            //Nombre de Tester (quien esta a cargo de la prueba)
        String strNameTester_I,
                                                            //Path del log, debe terminar con .log
        String strPathLog_I,
                                                            //Log.
        Oobject <PrintWriter> osysswLog_O,
                                                            //Indica si la prueba en para "comparación" automática (en
                                                            //      estos casos el ts y los hashcode se muestran como ?,
                                                            //      el tester name se muestra con "<Test for Automatic
                                                            //      Verification>")
        boolean boolIsTestForAutomaticVerification_I
        )
    {
        if (
            strNameTester_I == null
            )
            Tools.subAbort(Tes3.strTo(strNameTester_I, "strNameTester_I") + " should have a value");
        if (
            strPathLog_I == null
            )
            Tools.subAbort(Tes3.strTo(strPathLog_I, "strPathLog_I") + " should have a value");
        if (
            !strPathLog_I.endsWith(".log")
            )
            Tools.subAbort(Tes3.strTo(strPathLog_I, "strPathLog_I") + " should have File Extension .log");

                                                            //Genera log
        SyspathPath syspathLog = new SyspathPath(strPathLog_I);
        File sysfileLog = Sys.sysfileNew(syspathLog);
        Oobject<File> osysfileLog = new Oobject<File>(sysfileLog);
        if (
            syspathLog.boolIsFile()
            )
        {
            osysswLog_O.v = Sys.sysswNewRewriteTextFile(osysfileLog);
        }
        else
        {
            osysswLog_O.v = Sys.sysswNewWriteTextFile(osysfileLog);
        }

                                                            //Escribe primera linea en log
        String strNameTester;
        String strTsNow;
        if (
            boolIsTestForAutomaticVerification_I
            )
        {
                                                            //En verificación automática se cancela el despliegue del Ts
            strNameTester = "<Test for Automatic Verification>";
            strTsNow = "?";
        }
        else
        {
            strNameTester = strNameTester_I;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            strTsNow = LocalDateTime.now().format(formatter);

        }
        Sys.subWriteLine(strNameTester + ", Now(" + strTsNow + "), " + syspathLog.strName(), osysswLog_O);

                                                            //Set o Reset el despliegue del HashCode de los objeto.
        if (
            boolIsTestForAutomaticVerification_I
            )
        {
                                                            //En verificación automática, los HashCode serán ?
            Tes3.subResetHashCode();
        }
        else
        {
            Tes3.subSetHashCode();
        }

                                                            //Cada Test inicia la secuencia de los blocks An, Bn, ...
        Tes3.intStartEnd = 0;
    }


    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subInitializeLstobjPreviouslyProcessed(
                                                            //Reset list of previously porcessed
    )
    {
        Tes3.lstobjPreviouslyProcessed = new LinkedList<Object>();
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


    //------------------------------------------------------------------------------------------------------------------
    private static void subBlockStart(                      //Genera los parámetros requerido para subToBlockFormat.
                                                            //Solo se usa este método cuando block START-END.

                                                            //NL + caracteres indentación.
        Ostring strNL_O,
                                                            //Label for block START_??? y END_???. (this is ???).
        Ostring strLabel_O,
                                                            //String to start block information
        Ostring strTo_O,
                                                            //Text to describe the object
        String strText_I,
                                                            //Object Id, if this block is por a bclass should be ""
        String strObjId_I
    )
    {
        strNL_O.v = Tes3.strNL();

                                                            //Asigna el siguiente nivel (lo regresa al cerrar block).
        Tes3.intLevel = Tes3.intLevel + 1;

                                                            //Asigna una secuencia única.
        Tes3.intStartEnd = Tes3.intStartEnd + 1;

                                                            //Determina la etiqueta que corresponde al block.
        char charLettersStartEnd;
        if (
                                                            //El nivel excede las letras.
            intLevel >= strLETTERS_FOR_LEVEL.length()
            )
        {
                                                            //Cuando no alcance ni la "Z" usa "*".
            charLettersStartEnd = '*';
        }
        else
        {
            charLettersStartEnd = strLETTERS_FOR_LEVEL.charAt(intLevel);
        }

                                                            //Asigna la etiqueta StartEnd.
        strLabel_O.v = Character.toString(charLettersStartEnd) + intStartEnd;

                                                            //Append Start of block.
                                                            //If we are in level 1, is is the beginig of a Test (new
                                                            //      log or previously was a WriteLine), the NewLine
                                                            //      should not be include.
        String strNlForStart;
        if (
            intLevel == 1
            )
        {
                                                            //Remove NewLine Mark
            strNlForStart = strNL_O.v.substring(System.lineSeparator().length());
        }
        else
        {
            strNlForStart = strNL_O.v;
        }
        strTo_O.v = strNlForStart + "##########>>>>>START_" + strLabel_O.v;
        strTo_O.v = strTo_O.v + strNL_O.v + strText_I + "(" + strObjId_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void subBlockEnd(                        //Termina el block StartEnd (regresa el nivel).
                                                            //Solo se usa este método cuando block START-END.

                                                            //NL + caracteres indentación.
        Ostring strNL_IO,
                                                            //String to append information
        Ostring strTo_IO,
        String strLabel_I
    )
    {
                                                            //End of Block
        strTo_IO.v = strTo_IO.v + ")" + strNL_IO.v + "##########<<<<<END_" + strLabel_I;
                                                            //Regresa el nivel.
        Tes3.intLevel = Tes3.intLevel - 1;

        strNL_IO.v = strNL();
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strNL(                            //NL + caracteres indentación.
    )
    {
                                                            //Determina el NL+indentación que corresponde al block.
        if (
            Tes3.intLevel < 0
            )
            Tools.subAbort(Tes3.strTo(Tes3.intLevel, "Tes2.intNivel") + " should be 0 or positive");

                                                            //Determina la cantidad de espacios para la indentación.
        int intSpaces;
        if (
                                                            //El nivel excede el arreglo.
            Tes3.intLevel >= Tes3.arrintLevelSpaces.length
            )
        {
                                                            //Cuando no alcance usa el último valor.
            intSpaces = Tes3.arrintLevelSpaces[Tes3.arrintLevelSpaces.length - 1];
        }
        else
        {
            intSpaces = Tes3.arrintLevelSpaces[intLevel];
        }

                                                            //Return NL with spacing required
        return System.lineSeparator() + Tools.padRight("", intSpaces);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/
    //==================================================================================================================

    //==================================================================================================================
    /*TASK Test.ObjId set of methods to compute object id*/

                                                            //Implementación de apoyos apagar y pender la inclución del
                                                            //      HashCode en los ObjId (se substituye por ?).
                                                            //Esto es necesario para poder hacer pruebas dónde su log,
                                                            //      al repetirse la prueba, ES IDENTICO, esto será muy
                                                            //      útil en:
                                                            //Conversión del EW.Training Fase 0, 1 y 2 a otras
                                                            //      instancias de Object Oriented (Conversión de C# a
                                                            //      Java, Objective-C, Swift y otras en el futuro).
                                                            //Desarrollo de QEnabler, el User Aceptance Test del
                                                            //      QEnabler deberá ser convertirse a si mismo, la
                                                            //      nulificación de HashCode permitirá connfirmar que
                                                            //      en TODAS LAS PRUEBAS los resultados son identicos
                                                            //      pudiendo hacer una comparación automática.
                                                            //Se deberá confirmar que es ídentico el resultado de:
                                                            //a) Código C# desarrollado.
                                                            //b) Código C# generado por QEnabler.
                                                            //c) Código Java generado por QEnabler.
                                                            //d) Código Objective-C generado por QEnabler.
                                                            //e) Código Swift generado por QEnabler.
                                                            //Igualmente se deberá tomar el código Java, Objective-C y
                                                            //      Swift y confirmar que la generación de código a
                                                            //      partir de ellas en las 4 instancias producen
                                                            //      resultados identicos.

    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    private static final String strHASH_CODE_NULL = "?";


    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

                                                            //Indicador de se incluir el HashCode en los ObjId.
    private static boolean boolIsHashCodeOn;

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsGetObjId(        //Intialize HashCode On.
    )
    {
        Tes3.boolIsHashCodeOn = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subSetHashCode(                      //Marca que desea incluir el HashCode en los ObjIn.
    )
    {
        Tes3.boolIsHashCodeOn = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subResetHashCode(                    //Marca que desea NO incluir el HashCode en los ObjIn.
    )
    {
        Tes3.boolIsHashCodeOn = false;
    }
    //------------------------------------------------------------------------------------------------------------------
    public static String strGetObjId(                       //Generate an object id.

                                                            //str, prefixSize:HashCode.
                                                            //prefix, data type prefix (int, arrint, lststr, etc.).
                                                            //Size, [l], [l,m], [l,m,n] or "".

        Object obj_I
    )
    {
        if (
            obj_I == null
            )
            Tools.subAbort(Tes3.strTo(obj_I, "obj_I") + " should have a value");

        if (
            !Tes3.boolIsStandard(obj_I, false)
            )
            Tools.subAbort(Tes3.strTo(obj_I.getClass(), "obj_I.GetType") + " is nonstandard type");

                                                            //El HashCode puede ser nulificado (cambiado a ?)
        String strHashCode;
        if (
            Tes3.boolIsHashCodeOn
            )
        {
            strHashCode = Integer.toString(obj_I.hashCode());
        }
        else
        {
                                                            //Deja solo ?.
            strHashCode = Tes3.strHASH_CODE_NULL;
        }

        return Tes3.strPrefix(obj_I) + Tes3.strCollectionSize(obj_I) + ":" + strHashCode;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*SHARE METHODS*/
    /*TASK Test.BoolIsStandard*/
    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolIsStandard(                  //Evaluate if object has a standard type.

                                                            //bool, is valid.

                                                            //object whose type will be tested to be standard.
        Object obj_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            obj_I == null
            )
            Tools.subAbort(Tes3.strTo(obj_I, "obj_I") + " can not be null");

                                                            //A class may be received sometimes instead of the object
                                                            //      itself; in those cases, classObj = obj_I.
        Class classObj;
        if (
            obj_I instanceof Class
            )
        {
            classObj = (Class)obj_I;
        }
        else
        {
            classObj = obj_I.getClass();
        }

        boolean boolIsStandard;
        /*CASE*/
        if (
            classObj.isArray()
            )
        {
            boolIsStandard = Tes3.boolIsStandardArray(classObj, boolAbort_I);
        }
        else if (
            Tools.boolIsGenericType(classObj)
            )
        {
            boolIsStandard = Tes3.boolIsStandardGeneric(obj_I, boolAbort_I);
        }
        else
        {
            boolIsStandard = Tes3.boolIsStandardSingle(classObj, boolAbort_I);
        }
        /*END-CASE*/

        return boolIsStandard;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardSingle(           //Evaluate if type is standard single type.

                                                           //bool, is valid.

       Class class_I,
                                                           //true, abort if is not valid.
       boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardSingle;
        /*CASE*/
        if (
            Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardPrimitive(class_I, boolAbort_I);
        }
        else if (
            Arrays.binarySearch(Tes3.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardSystem(class_I, boolAbort_I);
        }
        else if (
            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardBclass(class_I, boolAbort_I);
        }
        else if (
            BtupleBaseTupleAbstract.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardBtuple(class_I, boolAbort_I);
        }
        else if (
            Enum.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardEnum(class_I, boolAbort_I);
        }
        else if (
            Exception.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardException(class_I, boolAbort_I);
        }
        else
        {
            boolIsStandardSingle = false;

            if (
                boolAbort_I && !boolIsStandardSingle
                )
                Tools.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                    Tes3.strTo(class_I, "type_I") + " is not an standard primitive type");
        }
        /*END-CASE*/

        return boolIsStandardSingle;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardPrimitive(         //Evaluate if type is standard primitive type.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardPrimitive = (
                                                            //Is a primitive included in Towa Standard.
            (Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0)
        );

        if (
            boolAbort_I && !boolIsStandardPrimitive
            )
            Tools.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                Tes3.strTo(class_I, "type_I") + " is not an standard primitive type");

        return boolIsStandardPrimitive;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardSystem(            //Evaluate if type is standard system type.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        boolean boolX;
        boolX = Modifier.isAbstract(class_I.getModifiers());


        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardSimpleOrSystem = (
            Arrays.binarySearch(Tes3.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
        );

        if (
            boolAbort_I && !boolIsStandardSimpleOrSystem
            )
            Tools.subAbort(Tes3.strTo(Tes3.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
                Tes3.strTo(class_I, "type_I") + " is not an standard system type");

        return boolIsStandardSimpleOrSystem;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardBclass(            //Evaluate if type is standard Bclass.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardBclass = (
            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
        );

        if (
            boolAbort_I && !boolIsStandardBclass
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " is not an standard bclass type");

        if (
                                                            //Is bclass (or subclass of)
            boolIsStandardBclass
            )
        {
                                                            //It could be abstract or concrete class

            if (
                Modifier.isAbstract(class_I.getModifiers())
                )
            {
                boolIsStandardBclass = (
                                                            //The name has the form: Prefix.....Abstract
                    class_I.getSimpleName().endsWith("Abstract") &&
                    (class_I.getSimpleName().length()> "Abstract".length()) &&
                    Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
                );

                if (
                    boolAbort_I && !boolIsStandardBclass
                    )
                    Tools.subAbort(Tes3.strTo(class_I, "type_I") +
                        " an standard abstract bclass type should have the form 'Prefix....Abstract'");
            }
            else
            {
                                                            //It is concrete class

                String strNameLower = class_I.getSimpleName().toLowerCase();
                boolIsStandardBclass = (
                                                            //The name has de form: Prefix.... and do not end with
                                                            //      Abstract, Tuple, Enum or Interface
                    !(strNameLower.endsWith("abstract") || strNameLower.endsWith("enum") ||
                    strNameLower.endsWith("tuple") || strNameLower.endsWith("interface")) &&
                    Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
                );

                if (
                    boolAbort_I && boolIsStandardBclass
                    )
                    Tools.subAbort(Tes3.strTo(class_I, "type_I") +
                        " an standard concrete bclass type should have the form 'Prefix....' and" +
                        " can not ends with Abstract, Tuple, Enum or Interface (upper or lower letters)");
            }
        }

        return boolIsStandardBclass;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardBtuple(            //Evaluate if type is standard Btuple.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardBtuple = (
            (BtupleBaseTupleAbstract.class.isAssignableFrom(class_I)) &&
                                                            //The name has the form: TNprefix...Tuple (at least 3 char
                                                            //      before "Tuple".
            class_I.getSimpleName().endsWith("Tuple") && (class_I.getSimpleName().length() >= ("Tuple".length() + 3)) &&
            (class_I.getSimpleName().charAt(0) == 'T') && (class_I.getSimpleName().charAt(1) >= '1') &&
            (class_I.getSimpleName().charAt(1) <= '9') && Tools.boolIsLetterLower(class_I.getSimpleName().charAt(2))
        );

        if (
            boolAbort_I && !boolIsStandardBtuple
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") +
                " is not an standard tuple type, also should have the form 'TNprefix...Tuple'");

        return boolIsStandardBtuple;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardEnum(              //Evaluate if type is standard enum type.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardEnum = (
            Enum.class.isAssignableFrom(class_I) &&
                                                            //Has the form Prefix...Enum (at least 1 char before
                                                            //      "Enum").
            class_I.getSimpleName().endsWith("Enum") && (class_I.getSimpleName().length() > "Enum".length()) &&
            Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
        );

        if (
            boolAbort_I && !boolIsStandardEnum
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") +
                " is not an standard Enum type, also should have the form 'Prefix...Enum'");

        return boolIsStandardEnum;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardException(         //Evaluate if type is standard Exception type.
                                                            //There is no much to check.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardException = (
            Exception.class.isAssignableFrom(class_I)
        );

        if (
            boolAbort_I && !boolIsStandardException
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " is not an standard Exception type");

        return boolIsStandardException;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardArray(             //Evaluate if type is standard array.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardArray = (
            class_I.isArray() &&
                                                            //Is obj[], obj[,] or obj[, ,]
            (Tools.intArrRank(class_I) <= 3)
        );

        if (
            boolAbort_I && !boolIsStandardArray
            )
            Tools.subAbort(Tes3.strTo(class_I, "type_I") + " is not an standard array type");

        if (
            boolIsStandardArray
            )
        {
                                                            //The "element" of an array should be standard type, but
                                                            //      not array or generic

            Class classElement = class_I.getComponentType();
            boolIsStandardArray = (!(
                classElement.isArray() ||
                Tools.boolIsGenericType(classElement) ||
                !Tes3.boolIsStandard(classElement, false)
            ));

            if (
                boolAbort_I && !boolIsStandardArray
                )
                Tools.subAbort(Tes3.strTo(class_I, "type_I") + ", " +
                    Tes3.strTo(classElement, "type_I.GetElementType") +
                    " the element of standard array type should be standard type, but not array or generic");
        }

        return boolIsStandardArray;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardGeneric(           //Evaluate if type is standard generic type.

                                                            //boolean, is valid.

        Object obj_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            obj_I == null
            )
            Tools.subAbort(Tes3.strTo(obj_I, "obj_I") + " can not be null");

        Class classObj = obj_I.getClass();
        boolean boolIsStandardGeneric = (
            Tools.boolIsGenericType(classObj) &&
            (Arrays.binarySearch(Tes3.arrstrGENERIC_TYPE, classObj.getSimpleName()) >= 0)
        );

        if (
            boolAbort_I && !boolIsStandardGeneric
            )
            Tools.subAbort(Tes3.strTo(classObj, "type_I") + " is not an standard generic Class");

        if (
            boolIsStandardGeneric
            )
        {
            if (
                Tools.boolGenericArgumentsPossible(obj_I)
                )
            {
                Class[] arrClassArgument = Tools.arrclassGetGenericArguments(obj_I);

                if (
                                                            //Generic arguments were obtained.
                    arrClassArgument.length > 0
                    )
                {
                                                            //The "main argument" of an generic should be standard type,
                                                            //      but not array or generic
                    Class classMainArgument = arrClassArgument[arrClassArgument.length - 1];

                    boolIsStandardGeneric = (!(
                        classMainArgument.isArray() ||
                        Tools.boolIsGenericType(classMainArgument) ||
                                                            //The method boolIsStandard usually receives the object and
                                                            //      then takes out the class, because the object itself
                                                            //      is needed for the method boolIsStandardGeneric (this
                                                            //      method), so normally the object must be passed to
                                                            //      boolIsStandard. However, in this case we know that
                                                            //      classMainArgument will not need to go to the
                                                            //      generic branch (boolIsStandardGeneric) on the
                                                            //      boolIsStandard method because it was previously
                                                            //      tested with Tools.boolIsGenericType(classMainArg..).
                        !Tes3.boolIsStandard(classMainArgument, false)
                    ));

                    if (
                        boolAbort_I && !boolIsStandardGeneric
                        )
                        Tools.subAbort(Tes3.strTo(obj_I, "type_I") + ", " +
                            Tes3.strTo(classMainArgument, "typeMainArgument") +
                            " the main argument of standard generic type should be standard type," +
                            " but not array or generic");

                                                            //It could be a 2 arguments generic (Dictionary or
                                                            //      KeyValuePair).
                    if (
                        boolIsStandardGeneric &&
                        ((classObj.getSimpleName().equals(Tes3.strGENERIC_DICTIONARY_TYPE)) ||
                        (classObj.getSimpleName().equals(Tes3.strGENERIC_KEYVALUEPAIR_TYPE)))
                        )
                    {
                                                            //It should be dictionary or KeyValuePair, the first
                                                            //      argument should be String.
                        boolIsStandardGeneric = (
                            arrClassArgument[0] == String.class
                        );

                        if (
                            boolAbort_I && !boolIsStandardGeneric
                            )
                            Tools.subAbort(Tes3.strTo(obj_I, "type_I") + ", " +
                                Tes3.strTo(arrClassArgument[0], "arrtypeArgument[0]") +
                                " the first argument of standard 2 arguments generic type should be String");
                    }
                }
            }
        }

        return boolIsStandardGeneric;
    }
    /*END-TASK*/

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strPrefix(                        //Generate the object prefix corresponding to type.
                                                            //Class Name has the structure:
                                                            //1. AaaaaBbbbbCcccc (a could be a digit).
                                                            //2. AaaaaBbbbbCcccc[], [,] or [,,] (array)
                                                            //3. Dictionary`2[String,other FullName], KeyValuePair.
                                                            //4. List`1[ns.FullName], Queue or Stack.
                                                            //AaaaaBbbbbCcccc could be:
                                                            //a. Bclass, Btuple or standard Enumerator.
                                                            //b. Primitive or system type (included in
                                                            //      arrstrPRIMITIVE_TYPE or arrstrSYSTEM_TYPE).

                                                            //str.
                                                            //1. prefix if is single type (prefix form PRIMITIVE or
                                                            //      SYSTEM, or aaaaa taken from class name if is a
                                                            //      Bclass, Btuple o Enum the name structure is
                                                            //      "AaaaaBbbbbbCcccc, 'a' could be a digit.
                                                            //2. arrprefix, arr1prefix or arr2prefix where "prefix" is
                                                            //      the prefix corresponding to element type.
                                                            //3. xxxarg, where "xxx" is prefix form GENERIC and "arg"
                                                            //      is the prefix corresponding to argument type (first
                                                            //      or second argument).
                                                            //(see type_I definition).

                                                            //1. Single type (not a array or generic type), Ex. str,
                                                            //      syspath, cod, codcb, sepoodt, ... .
                                                            //2. Array type ([], [,] or [, ,]), Ex. arrstr, arr2int,
                                                            //      arrarrstr, arrdicstr, ... .
                                                            //3. Generic type (1 or 2 arguments). Ex. dicstr, kvpint,
                                                            //      lsttok, queuecod, ... .

                                                            //Originally, a Class (Type) was received, but it was
                                                            //      changed in order to be able to get the arguments of
                                                            //      objects that are collections (it couldn't be done
                                                            //      with just the class).
        Object obj_I
    )
    {
                                                            //The class of the incoming object.
        Class classObj;
        if (
            obj_I instanceof Class
            )
        {
            classObj = (Class)obj_I;
        }
        else
        {
            classObj = obj_I.getClass();
        }

        String strPrefix;
        /*CASE*/
        if (
            classObj.isArray()
            )
        {
            strPrefix = "arr";


            int intRank = Tools.intArrRank(classObj);
            if (
                intRank > 1
                )
            {
                                                            //arr2 or arr3
                strPrefix = strPrefix + intRank;
            }

                                                            //arr?elem
            strPrefix = strPrefix + Tes3.strPrefix(Tes3.classOfArray(classObj));
        }
        else if (
            Tools.boolIsGenericType(classObj)
            )
        {
                                                            //dic, kvp, lst, queue or stack
            strPrefix = Tes3.arrstrGENERIC_PREFIX[
                Arrays.binarySearch(Tes3.arrstrGENERIC_TYPE, classObj.getSimpleName())];

            if (
                                                            //The generic arguments of the collection can be obtained.
                Tools.boolGenericArgumentsPossible(obj_I)
                )
            {
                Class[] arrClassArgument = Tools.arrclassGetGenericArguments(obj_I);

                if (
                                                            //Generic arguments were obtained.
                    arrClassArgument.length > 0
                    )
                {
                    strPrefix = strPrefix + Tes3.strPrefix(arrClassArgument[arrClassArgument.length - 1]);
                }
                else
                {
                    strPrefix = strPrefix + "EMPTY_COLLECTION";
                }
            }
            else
            {
                strPrefix = strPrefix + "EMPTY_COLLECTION";
            }
        }
        else
        {
                                                            //Single form (not an array or generic)
            int intPrimitive = Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, classObj.getSimpleName());
            if (
                                                            //Is standard primitive type
                intPrimitive >= 0
                )
            {
                strPrefix = Tes3.arrstrPRIMITIVE_PREFIX[intPrimitive];
            }
            else
            {
                int intSystem = Arrays.binarySearch(Tes3.arrstrSYSTEM_TYPE, classObj.getSimpleName());
                if (
                                                            //Is standard system type
                    intSystem >= 0
                    )
                {
                    strPrefix = Tes3.arrstrSYSTEM_PREFIX[intSystem];
                }
                else
                {
                    int intBoxing = Arrays.binarySearch(Tes3.arrstrBOXING_TYPE, classObj.getSimpleName());
                    if (
                                                            //Is boxing class.
                        intBoxing >= 0
                        )
                    {
                        strPrefix = Tes3.arrstrBOXING_PREFIX[intBoxing];
                    }
                    else
                    {
                                                            //Should be user defined type (bclass, btuple, enum or
                                                            //      excep)
                        if (!(
                            BclassBaseClassAbstract.class.isAssignableFrom(classObj) ||
                            BtupleBaseTupleAbstract.class.isAssignableFrom(classObj) ||
                            Enum.class.isAssignableFrom(classObj) ||
                            Exception.class.isAssignableFrom(classObj)
                        ))
                            Tools.subAbort(Tes3.strTo(classObj, "classObj") +
                                    " SOMETHING IS WRONG!!! at this point it should be bclass, btuple, enum or sysexcep");


                                                            //Is a user define type (bclass, btuple, enum or sysexcep).
                                                            //Search for B in AaaaaBbbbbCcccc.
                                                            //Start after first character (after 'A')
                        int intI = 1;
                        /*WHILE-DO*/
                        while (
                            (intI < classObj.getSimpleName().length()) &&
                                                            //Between a-z or 0-9
                            Tools.boolIsDigitOrLetterLower(classObj.getSimpleName().charAt(intI))
                            )
                        {
                            intI = intI + 1;
                        }

                                                            //Subtract class prefix (aaaaa).
                                                            //substring in C# was originally (1, int -1), however, in
                                                            //      Java, the parameter is different than in C#. In C#
                                                            //      the parameters are ss(beginIndex, length), while in
                                                            //      Java the parameters are
                                                            //      ss(beginIndex, exclusiveEndIndex). Because of this,
                                                            //      to achieve the desired result, we must add the
                                                            //      begindIndex to the endIndex, in this case = 1).
                        strPrefix = Character.toString(classObj.getSimpleName().charAt(0)).toLowerCase() +
                            classObj.getSimpleName().substring(1, intI - 1 + 1);
                    }
                }
            }
        }
        return strPrefix;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strCollectionSize(
        Object obj_I
    )
    {
        Class classCollection = obj_I.getClass();

        String strCollectionSize;
        if (
            classCollection.isArray()
            )
        {
            if (!(
                                                            //The array is standard according to the multidimension
                                                            //      rules (Only arrays in form [], [,], [,,].
                Tes3.boolMultiDimensionArrayIsStandard(obj_I)
            ))
            {
                Tools.subAbort(Tes3.strTo(obj_I, "obj_I") + " is not a standard multidimensional array. It's not in" +
                        " format [],[,] or [,,].");
            }
            int intRank = Tools.intArrRank(obj_I.getClass());

            /*CASE*/
            if (
                intRank == 1
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strArrSize(obj_I);
            }
            else if (
                intRank == 2
                )
            {
                                                            //Generate [l,m].
                strCollectionSize = Tes3.strArr2Size(obj_I);
            }
            else
            {
                                                            //Rank 3
                                                            //Generate [l,m,n].
                strCollectionSize = Tes3.strArr3Size(obj_I);
            }
            /*END-CASE*/
        }
        else if (
            Tools.boolIsGenericType(classCollection)
            )
        {
            /*CASE*/
            if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_DICTIONARY_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strDicSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_KEYVALUEPAIR_TYPE)
                )
            {
                                                            //This is not a collection.
                strCollectionSize = "";
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_LIST_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strLstSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_QUEUE_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strQueueSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_STACK_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strStackSize(obj_I);
            }
            else
            {
                if (
                    true
                    )
                    Tools.subAbort(Tes3.strTo(obj_I.getClass(), "obj_I.getClass()") +
                        " SOMETHING IS WRONG!!! is nonstandard generic collection");

                strCollectionSize = null;

            }
            /*END-CASE*/

        }
        else
        {
                                                            //It is not a collection
            strCollectionSize = "";
        }
        return strCollectionSize;
    }
    //------------------------------------------------------------------------------------------------------------------

    private static String strArrSize(                       //Get [l].

                                                            //str, [l].

        Object obj_I
    )
    {
        int intLength;
        /*CASE*/
        if (
            obj_I instanceof int[]
            )
        {
            intLength = ((int[])obj_I).length;
        }
        else if (
            obj_I instanceof long[]
            )
        {
            intLength = ((long[])obj_I).length;
        }
        else if (
            obj_I instanceof double[]
            )
        {
            intLength = ((double[])obj_I).length;
        }
        else if (
            obj_I instanceof boolean[]
            )
        {
            intLength = ((boolean[])obj_I).length;
        }
        else if (
            obj_I instanceof char[]
            )
        {
            intLength = ((char[])obj_I).length;
        }
        else if (
            obj_I instanceof LocalDateTime[]
            )
        {
            intLength = ((LocalDateTime[])obj_I).length;
        }
        else
        {
            Class classObj = obj_I.getClass();
            Class classElement = classObj.getComponentType();
            if (
                                                            //instanceof primitive type
                (Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, classElement.getSimpleName()) >= 0)
                )
                Tools.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes2.arrstrPRIMITIVE_TYPE") + ", " +
                    Tes2.strTo(classObj, "obj_I.getClass()") +
                    " SOMETHING is WRONG!!! a branch in previous case is missing");

            intLength = ((Object[])obj_I).length;
        }
        /*END-CASE*/

        return "[" + intLength + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strArr2Size(                      //Get [l,m].

                                                            //str, [l,m].

        Object obj_I
    )
    {
        int intLength0;
        int intLength1;

        /*CASE*/
        if (
            obj_I instanceof int[][]
            )
        {
            int[][] arr2int = (int[][])obj_I;
            intLength0 = arr2int.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2int[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof long[][]
            )
        {
            long[][] arr2long = (long[][])obj_I;
            intLength0 = arr2long.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2long[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof double[][]
            )
        {
            double[][] arr2num = (double[][])obj_I;
            intLength0 = arr2num.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2num[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof boolean[][]
            )
        {
            boolean[][] arr2bool = (boolean[][])obj_I;
            intLength0 = arr2bool.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2bool[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof char[][]
            )
        {
            char[][] arr2char = (char[][])obj_I;
            intLength0 = arr2char.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2char[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof LocalDateTime[][]
            )
        {
            LocalDateTime[][] arr2ts = (LocalDateTime[][])obj_I;
            intLength0 = arr2ts.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2ts[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else
        {
            Class typeObj = obj_I.getClass();
            Class typeElement = typeObj.getComponentType();
            if (
                                                            //instanceof primitive type
                (Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, typeElement.getSimpleName()) >= 0)
                )
                Tools.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                    Tes3.strTo(typeObj, "obj_I.GetType") +
                    " SOMETHING instanceof WRONG!!! a branch in previous case instanceof missing");


            Object[][] arr2obj = (Object[][])obj_I;
            intLength0 = arr2obj.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2obj[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        /*END-CASE*/

        return "[" + intLength0 + "," + intLength1 + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strArr3Size(                      //Get [l,m,n].

                                                            //str, [l,m,n].
                                                            Object obj_I
    )
    {
        int intLength0;
        int intLength1;
        int intLength2;
        /*CASE*/
        if (
                obj_I instanceof int[][][]
                )
        {
            int[][][] arr3int = (int[][][])obj_I;
            intLength0 = arr3int.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3int[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3int[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof long[][][]
                )
        {
            long[][][] arr3long = (long[][][])obj_I;
            intLength0 = arr3long.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3long[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3long[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof double[][][]
                )
        {
            double[][][] arr3num = (double[][][])obj_I;
            intLength0 = arr3num.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3num[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3num[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof boolean[][][]
                )
        {
            boolean[][][] arr3bool = (boolean[][][])obj_I;
            intLength0 = arr3bool.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3bool[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3bool[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof char[][][]
                )
        {
            char[][][] arr3char = (char[][][])obj_I;
            intLength0 = arr3char.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3char[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3char[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof LocalDateTime[][][]
                )
        {
            LocalDateTime[][][] arr3ts = (LocalDateTime[][][])obj_I;
            intLength0 = arr3ts.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3ts[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3ts[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else
        {
            Class typeObj = obj_I.getClass();
            Class typeElement = typeObj.getComponentType();
            if (
                //instanceof primitive type
                    (Arrays.binarySearch(Tes2.arrstrPRIMITIVE_TYPE, typeElement.getSimpleName()) >= 0)
                    )
                Tools.subAbort(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Tes2.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING instanceof WRONG!!! a branch in previous case instanceof missing");


            Object[][][] arr3obj = (Object[][][])obj_I;
            intLength0 = arr3obj.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3obj[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3obj[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        /*END-CASE*/

        return "[" + intLength0 + "," + intLength1 + "," + intLength2 + "]";
    }
    /*END-TASK*/
    //==================================================================================================================

}