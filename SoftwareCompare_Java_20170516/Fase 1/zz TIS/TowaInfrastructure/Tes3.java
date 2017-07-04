package TowaInfrastructure;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;


//AUTHOR: Towa (EELR-Elí Linares).
                                                            //CO-AUTHOR: Towa ().
                                                            //DATE: 13-Mayo-2011.
                                                            //PURPOSE:
                                                            //Implementation of strTo method for all Java data types.

public class Tes3 {
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
                    new T2charDescriptionTuple((char) (128 * 64 + 11), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 12), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 13), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 14), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 40), "similar to \\n New Line"),
                    new T2charDescriptionTuple((char) (128 * 64 + 41), "similar to \\r Carriage Return"),
                    new T2charDescriptionTuple((char) (128 * 64 + 42), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 43), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 44), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 45), "\'\' empty character"),
                    new T2charDescriptionTuple((char) (128 * 64 + 46), "\'\' empty character"),
            };


    //Con el arreglo anterior se generan los siguientes 2
    //      arreglos y se ordenan por el primero.
    private static /*readonly*/ char[] arrcharNONVISIBLE;
    private static /*readonly*/ String[] arrstrDESCRIPTION_NONVISIBLE;

    private static int[][] arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION =
            {
                    {1, 6},
                    {14, 31},
                    {127, 128 + 31},
                    {128 + 45, 128 + 45},
                    {128 * 5 + 94, 128 * 5 + 94},
                    //Distorcionan el despliegue en forma extraña, parece que
                    //      tiene efecto sobre lo que se desplego ANTES.
                    {128 * 5 + 101, 128 * 5 + 101}, {128 * 5 + 103, 128 * 5 + 104},
                    {128 * 6, 128 * 6 + 17}, {128 * 6 + 19, 128 * 6 + 78}, {128 * 6 + 80, 128 * 6 + 111},
                    {128 * 9 + 3, 128 * 9 + 6},
                    {128 * 11 + 62, 128 * 11 + 62}, {128 * 11 + 64, 128 * 11 + 64}, {128 * 11 + 67, 128 * 11 + 67},
                    {128 * 11 + 70, 128 * 11 + 70}, {128 * 11 + 80, 128 * 11 + 106}, {128 * 11 + 112, 128 * 11 + 116},
                    {128 * 12, 128 * 12 + 3}, {128 * 12 + 11, 128 * 12 + 11}, {128 * 12 + 13, 128 * 12 + 13},
                    {128 * 12 + 27, 128 * 12 + 27}, {128 * 12 + 30, 128 * 12 + 31}, {128 * 12 + 33, 128 * 12 + 58},
                    {128 * 12 + 64, 128 * 12 + 74}, {128 * 12 + 109, 128 * 12 + 111}, {128 * 12 + 113, 128 * 12 + 127},
                    {128 * 13, 128 * 13 + 85}, {128 * 13 + 93, 128 * 13 + 93}, {128 * 13 + 101, 128 * 13 + 102},
                    {128 * 13 + 110, 128 * 13 + 111}, {128 * 13 + 122, 128 * 13 + 127},
                    {128 * 14, 128 * 14 + 13}, {128 * 14 + 16, 128 * 14 + 16}, {128 * 14 + 18, 128 * 14 + 47},
                    {128 * 14 + 77, 128 * 14 + 109},
                    {128 * 15, 128 * 15 + 37}, {128 * 15 + 49, 128 * 15 + 49},
                    //Non printable character
                    {'\uD800', '\uFFFF'},
            };

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

    //Object previously processed in other strTo execution.
    private static LinkedList<Object> lstobjPreviouslyProcessed;


    //------------------------------------------------------------------------------------------------------------------


    //==================================================================================================================
    /*TASK Tes3.strTo Set of Methods for Primitive Java Types*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
                                                            int int_I,
                                                            //Option should be SHORT.
                                                            TestoptionEnum testoptionSHORT_I

    ) {
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

    ) {
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
    ) {
        //It's prepare without additional information. It's separed
        //      in thousands by commas ("1000" = "1,000").
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        String strAnalizeAndFormatInt = decimalFormatter.format(int_I);

        //Add information in case it is the minimum or maximum Int.
        if (
                int_I == Integer.MIN_VALUE
                ) {
            strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MinValue>";
        } else if (
                int_I == Integer.MAX_VALUE
                ) {
            strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MaxValue>";
        } else {
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

    ) {
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

    ) {
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
    ) {
        //It's prepare without additional information. It's separed
        //      in thousands by commas ("1000" = "1,000").
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        String strAnalizeAndFormatLong = decimalFormatter.format(long_I);

        //Añade información adicional si es mínimo o máximo.
        if (
                long_I == Long.MIN_VALUE
                ) {
            strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MinValue>";
        } else if (
                long_I == Long.MAX_VALUE
                ) {
            strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MaxValue>";
        } else {
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

    ) {
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

    ) {
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
    ) {
        //Por lo pronto prepara sin información adicional.
        String strAnalizeAndFormatNum = String.format("%f", num_I);

        //Añade información adicional si es mínimo o máximo.
        if (
                num_I == Double.MIN_VALUE
                ) {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MinValue>";
        } else if (
                num_I == Double.MAX_VALUE
                ) {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MaxValue>";
        } else if (
                num_I == Double.NEGATIVE_INFINITY
                ) {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<-?/0.0>";
        } else if (
                num_I == Double.POSITIVE_INFINITY
                ) {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<?/0.0>";
        } else if (
            //A number has 4 posibibilities:
            //1. Beetwen MinValue and MaxValue
            //2. NegativeInfinity, (-?/0.0).
            //3. PositiveInfinity, (?/0.0).
            //4. NaN, (0.0/0.0).
            //num_I == Double.NaN, DO NOT FUNCTION AS EXPECTED
                !((num_I >= Double.MIN_VALUE) && (num_I <= Double.MAX_VALUE))
                ) {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<0.0/0.0>";
        } else {
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

    ) {
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

    ) {
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
    ) {
        //Se asigna true o false según corresponda.
        //Se hace de esta forma dado que el bool_I.ToString()
        //      produce "True" o  "False" (iniciando con mayúsculas
        //      que es distinto a las literales true y false).
        String strAnalizeAndFormatBool;
        if (
                bool_I
                ) {
            strAnalizeAndFormatBool = "true";
        } else {
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

    ) {
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

    ) {
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
    ) {
        //Determino tipo de caracter.
        TestchartypeEnum testchartype = Tes3.testchartypeKeyboardOrEtc(char_I);

        //Para formar lo que va a regresar, esto depende del tipo de
        //      caracter.
        String strAnalizeAndFormatChar;
        if (
            //Es visible.
                (testchartype == TestchartypeEnum.KEYBOARD) || (testchartype == TestchartypeEnum.VISIBLE_NONKEYBOARD)
                ) {
            //Es visible, solo pone entre comillas
            strAnalizeAndFormatChar = "'" + char_I + "'";
        } else {
            //Procesa cuando no es visible.
            strAnalizeAndFormatChar = "'" + charSUBSTITUTE_NONVISIBLE + "'";
        }

        //Añade info de diagnóstico cuando no es del KEYBOARD.
        if (
                testchartype != TestchartypeEnum.KEYBOARD
                ) {
            //Añade info de diagnóstico.

            //Formatea la tupla cuando no es visible (primera parte).
            strAnalizeAndFormatChar = strAnalizeAndFormatChar + "<" + "0x" + String.format("%04X", (int) char_I);

            if (
                    testchartype == TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION
                    ) {
                //Completa la tupla cuando tiene descripción.
                int intDesctiption = Arrays.binarySearch(arrcharNONVISIBLE, char_I);
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + ", " +
                        arrstrDESCRIPTION_NONVISIBLE[intDesctiption] + ">";
            } else {
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
    ) {
        TestchartypeEnum testchartypeKeyboardOrEtc;

        /*CASE*/
        if (
            //Es caracter del teclado.
                Arrays.binarySearch(Tes3.arrcharKEYBOARD, charARevise_I) >= 0
                ) {
            testchartypeKeyboardOrEtc = TestchartypeEnum.KEYBOARD;
        } else if (
                Arrays.binarySearch(Tes3.arrcharNONVISIBLE, charARevise_I) >= 0
                ) {
            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION;
        } else if (
                Tes3.boolIsNonVisibleWithoutDescription(charARevise_I)
                ) {
            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITHOUT_DESCRIPTION;
        } else {
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
    ) {
        //Extrae el número del caracter.
        int intChar = (int) charAVerificar_I;

        //Busca el rango donde pudiera estar incluido.
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
                //Ya no hay rangos.
                (intI >= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) ||
                        //El caracter a verificar puede estar en este rango.
                        (intChar <= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1])
        )) {
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
    ) {
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
    ) {
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
    ) {
        /*
    }
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

                                                            //Do the boxing.
            //Object objMain;
            //Object objKey;
            //Tes3.subfunConvertAndBox(out objMain, out objKey, obj_I);

                                                            //Call required strToSharedYyyyy
            //Type typeObj = obj_I.GetType();
            /*CASE* /
            if (
                typeObj.IsArray
                )
            {
                                                            //Is arrobj[], arrobj[,] or arrobj[, ,].
                                                            //All contents are boxed primitives, simple and
                                                            //      system types, bclass, btuple, enum or sysexcep

                int intRank = typeObj.GetArrayRank();
                /*CASE* /
                /* if (
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
                    //strToSupportAnyType = Tes3.strFormatArr2Main((Object[,])objMain,
                    //     testoptionOption_I, strText_I, obj_I);
                }
                else
                {
                                                            //Is arrobj[, ,]
                    //strToSupportAnyType = Tes3.strFormatArr3Main((Object[, ,])objMain,
                        //testoptionOption_I, strText_I, obj_I);
                }
                /*END-CASE*/
        //}
        //else if (
        //typeObj.IsGenericType
        // )
        //{
        //Is 1 or 2 arguments.
        //All contents are boxed primitives, simple and
        //      system types

                /*if (
                                                            //Is List<Object>, ...
                    //typeObj.Name.EndsWith("`1")
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
                        typeObj.Name == Tes3.strGENERIC_DICTIONARY_TYPE
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
            /*END-CASE* /
        }

        return strToSupportAnyType;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/
        //==================================================================================================================
    return null;
    }
}