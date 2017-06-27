package TowaInfrastructure;

import QEnablerCobol.ComcbCobol;

import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Alejandro on 8/10/2016.
 */
public class Tes2
{
    //=================================================================================================================
    /*TASK Tes2.PrepareStrTo Constants and initializer for strTo (Only the ones necessary for Fase 0*/
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

    //      												//Both arrays order by first.

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

    //                                                      //Both arrays order by first.
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


    //------------------------------------------------------------------------------------------------------------------
    /*SUPPORT METHODS FOR STATIC CONSTRUCTOR*/
    
    //------------------------------------------------------------------------------------------------------------------
	static                                     				//Prepara las constantes para poder utilizarlas.
	                                                        //CADA VEZ QUE SE AÑADAN CONSTANTES QUE REQUIERAN SER
	                                                        //      INICIALIZADAS, SE AÑADE LA LLAMADA A OTRO MÉTODO.
    {
                                                            //Reference passing for subPrepareConstantsForStrTo
        Oobject<char[]> oarrcharKEYBOARD = new Oobject<char[]>();
        Oobject<char[]> oarrcharNONVISIBLE = new Oobject<char[]>();
        Oobject<String[]> oarrstrDESCRIPTION_NONVISIBLE = new Oobject<String[]>();

        Tes2.subPrepareConstantsForStrTo(oarrcharKEYBOARD, oarrcharNONVISIBLE,oarrstrDESCRIPTION_NONVISIBLE);

                                                            //Put the values in the corresponding static variables.
        Tes2.arrcharKEYBOARD = oarrcharKEYBOARD.v;
        Tes2.arrcharNONVISIBLE = oarrcharNONVISIBLE.v;
        Tes2.arrstrDESCRIPTION_NONVISIBLE = oarrstrDESCRIPTION_NONVISIBLE.v;

                                                            //Reference passing for subPrepareConstantTypes
        Oobject<String[]> oarrstrPRIMITIVE_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrPRIMITIVE_PREFIX = new Oobject<String[]>();
        Oobject<String[]> oarrstrSYSTEM_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrSYSTEM_PREFIX = new Oobject<String[]>();
        Oobject<String[]> oarrstrGENERIC_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrGENERIC_PREFIX = new Oobject<String[]>();
        Oobject<String[]> oarrstrBOXING_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrBOXING_PREFIX = new Oobject<String[]>();

        Tes2.subPrepareConstantTypes(oarrstrPRIMITIVE_TYPE, oarrstrPRIMITIVE_PREFIX, oarrstrSYSTEM_TYPE,
            oarrstrSYSTEM_PREFIX, oarrstrGENERIC_TYPE, oarrstrGENERIC_PREFIX, oarrstrBOXING_TYPE, oarrstrBOXING_PREFIX);

                                                            //Put the values in the corresponding static variables.
        Tes2.arrstrPRIMITIVE_TYPE = oarrstrPRIMITIVE_TYPE.v;
        Tes2.arrstrPRIMITIVE_PREFIX = oarrstrPRIMITIVE_PREFIX.v;
        Tes2.arrstrSYSTEM_TYPE = oarrstrSYSTEM_TYPE.v;
        Tes2.arrstrSYSTEM_PREFIX = oarrstrSYSTEM_PREFIX.v;
        Tes2.arrstrGENERIC_TYPE = oarrstrGENERIC_TYPE.v;
        Tes2.arrstrGENERIC_PREFIX = oarrstrGENERIC_PREFIX.v;
        Tes2.arrstrBOXING_TYPE = oarrstrBOXING_TYPE.v;
        Tes2.arrstrBOXING_PREFIX = oarrstrBOXING_PREFIX.v;

        Tes2.subPrepareConstantsGetObjId();
        Tes2.subPrepareConstantsToBlockFormat();
        Tes2.subPrepareConstantsSubTrace();
        Tes2.subPrepareConstantsTestAbort();
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantTypes(        //Order and varify constants.

        Oobject <String[]> arrstrPRIMITIVE_TYPE_O,
        Oobject <String[]> arrstrPRIMITIVE_PREFIX_O,
        Oobject <String[]> arrstrSYSTEM_TYPE_O,
        Oobject <String[]> arrstrSYSTEM_PREFIX_O,
        Oobject <String[]> arrstrGENERIC_TYPE_O,
        Oobject <String[]> arrstrGENERIC_PREFIX_O,
        Oobject <String[]> arrstrBOXING_TYPE_O,
        Oobject <String[]> arrstrBOXING_PREFIX_O
        )
    {
                                                            //Order arr2strPRIMITIVE_TYPE_AND_PREFIX.
        arrstrPRIMITIVE_TYPE_O.v = new String[Tes2.arr2strPRIMITIVE_TYPE_AND_PREFIX.length];
        arrstrPRIMITIVE_PREFIX_O.v = new String[arrstrPRIMITIVE_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrPRIMITIVE_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrPRIMITIVE_TYPE_O.v[intI] = Tes2.arr2strPRIMITIVE_TYPE_AND_PREFIX[intI][0];
            arrstrPRIMITIVE_PREFIX_O.v[intI] = Tes2.arr2strPRIMITIVE_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrPRIMITIVE_TYPE_O.v, arrstrPRIMITIVE_PREFIX_O.v);

        for (int intI = 1; intI < arrstrPRIMITIVE_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Is duplicated.
                arrstrPRIMITIVE_TYPE_O.v[intI] == arrstrPRIMITIVE_TYPE_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrstrPRIMITIVE_TYPE_O, "arrstrPRIMITIVE_TYPE_O") + ", " +
                    Tes2.strTo(arrstrPRIMITIVE_TYPE_O.v[intI], "arrstrPRIMITIVE_TYPE_O[" + intI + "]") +
                    " is duplicated");
        }

                                                            //Order arr2strSYSTEM_TYPE_AND_PREFIX.
        arrstrSYSTEM_TYPE_O.v = new String[Tes2.arr2strSYSTEM_TYPE_AND_PREFIX.length];
        arrstrSYSTEM_PREFIX_O.v = new String[arrstrSYSTEM_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrSYSTEM_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrSYSTEM_TYPE_O.v[intI] = Tes2.arr2strSYSTEM_TYPE_AND_PREFIX[intI][0];
            arrstrSYSTEM_PREFIX_O.v[intI] = Tes2.arr2strSYSTEM_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrSYSTEM_TYPE_O.v, arrstrSYSTEM_PREFIX_O.v);

        for (int intI = 1; intI < arrstrSYSTEM_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Is duplicated.
                arrstrSYSTEM_TYPE_O.v[intI] == arrstrSYSTEM_TYPE_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrstrSYSTEM_TYPE_O, "arrstrSYSTEM_TYPE_O") + ", " +
                    Tes2.strTo(arrstrSYSTEM_TYPE_O.v[intI], "arrstrSYSTEM_TYPE_O[" + intI + "]") +
                    " is duplicated");
        }

                                                            //Order arr2strGENERIC_TYPE_AND_PREFIX.
        arrstrGENERIC_TYPE_O.v = new String[Tes2.arr2strGENERIC_TYPE_AND_PREFIX.length];
        arrstrGENERIC_PREFIX_O.v = new String[arrstrGENERIC_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrGENERIC_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrGENERIC_TYPE_O.v[intI] = Tes2.arr2strGENERIC_TYPE_AND_PREFIX[intI][0];
            arrstrGENERIC_PREFIX_O.v[intI] = Tes2.arr2strGENERIC_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrGENERIC_TYPE_O.v, arrstrGENERIC_PREFIX_O.v);

        for (int intI = 1; intI < arrstrGENERIC_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Is duplicated.
                arrstrGENERIC_TYPE_O.v[intI] == arrstrGENERIC_TYPE_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrstrGENERIC_TYPE_O, "arrstrGENERIC_TYPE_O") + ", " +
                    Tes2.strTo(arrstrGENERIC_TYPE_O.v[intI], "arrstrGENERIC_TYPE_O[" + intI + "]") +
                    " is duplicated");
        }

        arrstrBOXING_TYPE_O.v = new String[Tes2.arr2strBOXING_TYPE_AND_PREFIX.length];
        arrstrBOXING_PREFIX_O.v = new String[arrstrBOXING_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrBOXING_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrBOXING_TYPE_O.v[intI] = Tes2.arr2strBOXING_TYPE_AND_PREFIX[intI][0];
            arrstrBOXING_PREFIX_O.v[intI] = Tes2.arr2strBOXING_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrBOXING_TYPE_O.v, arrstrBOXING_PREFIX_O.v);

        for (int intI = 1; intI < arrstrBOXING_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                //Is duplicated.
                arrstrBOXING_TYPE_O.v[intI] == arrstrBOXING_TYPE_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrstrBOXING_TYPE_O, "arrstrBOXING_TYPE_O") + ", " +
                    Tes2.strTo(arrstrBOXING_TYPE_O.v[intI], "arrstrBOXING_TYPE_O[" + intI + "]") +
                    " is duplicated");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SUPPORT METHODS FOR STATIC CONSTRUCTOR*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsForStrTo(		//Método de apoyo llamado en constructor estático.
                                                          	//Prepara las constantes para poder utilizarlas.
                                                          	//1. Prepara arrcharKEYBOARD.
                                                           	//2. Prepara arrcharNONVISIBLE_WITH_DESCRIPTION y
                                                          	//      arrstrDESCRIPTION_NONVISIBLE_WITH_DESCRIPTION.

        Oobject <char[]> arrcharKEYBOARD_O,
        Oobject <char[]> arrcharNONVISIBLE_O,
        Oobject <String[]> arrstrDESCRIPTION_NONVISIBLE_O
        )
    {
        Tes2.subInitializeLstobjPreviouslyProcessed();

                                                      		//Prepara arrcharKEYBOARD.
        arrcharKEYBOARD_O.v = strCHAR_KEYBOARD.toCharArray();
        Arrays.sort(arrcharKEYBOARD_O.v);

                                                      		//Verifica que no haya caracteres duplicados.
        for (int intI = 1; intI < arrcharKEYBOARD_O.v.length; intI = intI + 1)
        {
            if (
                                                          	//Esta duplicado.
                arrcharKEYBOARD_O.v[intI] == arrcharKEYBOARD_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrcharKEYBOARD_O.v, "arrcharKEYBOARD") + ", " +
                    Tes2.strTo(arrcharKEYBOARD_O.v[intI], "arrcharKEYBOARD[" + intI + "]") +
                    " duplicated character");
        }

            												//Prepara arrcharNONVISIBLE_WITH_DESCRIPTION y
            												//      arrstrDESCRIPTION_NONVISIBLE_WITH_DESCRIPTION.

        arrcharNONVISIBLE_O.v = new char[Tes2.arrt2charNONVISIBLE_WITH_DESCRIPTION.length];
        arrstrDESCRIPTION_NONVISIBLE_O.v = new String[arrt2charNONVISIBLE_WITH_DESCRIPTION.length];
        for (int intI = 0; intI < arrcharNONVISIBLE_O.v.length; intI = intI + 1)
        {
            arrcharNONVISIBLE_O.v[intI] = arrt2charNONVISIBLE_WITH_DESCRIPTION[intI].charChar;
            arrstrDESCRIPTION_NONVISIBLE_O.v[intI] = arrt2charNONVISIBLE_WITH_DESCRIPTION[intI].strDescription;
        }

        Tools.sort(arrcharNONVISIBLE_O.v, arrstrDESCRIPTION_NONVISIBLE_O.v);

            												//Verifica que no haya caracteres duplicados.
        for (int intI = 1; intI < arrcharNONVISIBLE_O.v.length; intI = intI + 1)
        {
            if (
                                                          	//Esta duplicado.
                arrcharNONVISIBLE_O.v[intI] == arrcharNONVISIBLE_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrcharNONVISIBLE_O.v, "arrcharNONVISIBLE") + ", " +
                    Tes2.strTo(arrcharNONVISIBLE_O.v[intI], "arrcharNONVISIBLE[" + intI + "]") +
                    " duplicated character");
        }

                                                          	//Verifica los rangos.
        for (int intI = 0; intI < arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length; intI = intI + 1)
        {
                                                          	//Verifica que los rangos sean válidos.
            final int intMaximo = (int)'\uFFFF';//256 * 256 - 1; ???
            if (
                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] < 0) ||
                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] > intMaximo)
                )
                Tools.subAbort(
                    Tes2.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0],
                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 0]") +
                    " should be between  0-" + intMaximo);
            if (
                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1] < 0) ||
                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1] > intMaximo)
                )
                Tools.subAbort(
                    Tes2.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1],
                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 1]") +
                    " should be between  0-" + intMaximo);
            if (
                arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] >
                    arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1]
                )
                Tools.subAbort(
                    Tes2.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0],
                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 0]") + " should be <= " +
                    Tes2.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1],
                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 1]"));
            if (
            												//No es el primer rango.
                (intI > 0) &&
                    										//El rango NO ES ascendente.
                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] <=
                    arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI - 1][1])
                )
                Tools.subAbort(
                    Tes2.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0],
                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 0]") + " should be > " +
                    Tes2.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI - 1][1],
                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + (intI - 1) + ", 1]"));
        }

                                                          	//Existen 4 conjuntos de caracteres.

                                                          	//El conjunto KEYBOARD no debe estar en los conjuntos
                                                          	//      NONVISIBLE ni en
                                                          	//      NONVISIBLE_WITHOUT_DESCRIPTION.
        for (int intI = 0; intI < strCHAR_KEYBOARD.length(); intI = intI + 1)
        {
                                                          	//Verifica si esta en NONVISIBLE_WITH_DESCRIPTION.
            if (
                Arrays.binarySearch(arrcharNONVISIBLE_O.v, strCHAR_KEYBOARD.charAt(intI)) >= 0
                )
                Tools.subAbort(Tes2.strTo(strCHAR_KEYBOARD.charAt(intI), "strCHAR_KEYBOARD[" + intI + "]") +
                    " is in " + Tes2.strTo(arrcharNONVISIBLE_O.v, "arrcharNONVISIBLE"));
                                                          	//Verifica si esta en NONVISIBLE_WITHOUT_DESCRIPTION.
            if (
                Tes2.boolIsNonVisibleWithoutDescription(strCHAR_KEYBOARD.charAt(intI))
                )
                Tools.subAbort(Tes2.strTo(strCHAR_KEYBOARD.charAt(intI), "strCHAR_KEYBOARD[" + intI + "]") +
                    " is in" +
                    Tes2.strTo(Tes2.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION,
                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION"));
        }

                                                          	//El conjunto NONVISIBLE no debe estar en el conjunto
                                                          	//      NONVISIBLE_WITHOUT_DESCRIPTION.
        for (int intI = 0; intI < arrcharNONVISIBLE_O.v.length; intI = intI + 1)
        {
                                                          	//Verifica si esta en NONVISIBLE_WITH_DESCRIPTION.
            if (
                Tes2.boolIsNonVisibleWithoutDescription(arrcharNONVISIBLE_O.v[intI])
                )
                Tools.subAbort(Tes2.strTo(arrcharNONVISIBLE_O.v[intI], "arrcharNONVISIBLE_O[" + intI + "]") +
                    " is in " + Tes2.strTo(Tes2.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION,
                        "Tes2.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION"));
        }
    }

    //==============================================================================================================
    /*TASK Tes2.Blocking Support blocking in the display Objects Info*/
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
        Tes2.intLevel = 0;
        Tes2.intStartEnd = 0;
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
            Tools.subAbort(Tes2.strTo(strNameTester_I, "strNameTester_I") + " should have a value");
        if (
            strPathLog_I == null
            )
            Tools.subAbort(Tes2.strTo(strPathLog_I, "strPathLog_I") + " should have a value");
        if (
            !strPathLog_I.endsWith(".log")
            )
            Tools.subAbort(Tes2.strTo(strPathLog_I, "strPathLog_I") + " should have File Extension .log");

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
            Tes2.subResetHashCode();
        }
        else
        {
            Tes2.subSetHashCode();
        }

                                                            //Cada Test inicia la secuencia de los blocks An, Bn, ...
        Tes2.intStartEnd = 0;
    }

    //------------------------------------------------------------------------------------------------------------------
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
            (intI >= Tes2.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) ||
                                                            //El caracter a verificar puede estar en este rango.
                (intChar <= Tes2.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][ 1])
        ))
        {
            intI = intI + 1;
        }

        return (
                                                            //Esta posicionado en un rango.
            (intI < Tes2.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) &&
                                                            //El caracter a verificar esta incluido en este rango.
                (intChar >= Tes2.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0])
        );
    }
    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subInitializeLstobjPreviouslyProcessed(
                                                      		//Reset list of previously porcessed
        )
    {
        Tes2.lstobjPreviouslyProcessed = new LinkedList<Object>();
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 
    //==================================================================================================================
    /*TASK Tes2.strTo(SHORT) strTo with only the needed parameters for Fase 0*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(
        boolean bool_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatBool(bool_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        int int_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatInt(int_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        char char_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatChar(char_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        String str_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatStr(str_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        int[] arrint_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrint_I);
        String strFormatArrOrOneArgumentGeneric = strObjId;

        return strFormatArrOrOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        String[] arrstr_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrstr_I);
        String strFormatArrOrOneArgumentGeneric = strObjId;

        return strFormatArrOrOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        char[] arrchar_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrchar_I);
        String strFormatArrOrOneArgumentGeneric = strObjId;

        return strFormatArrOrOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Enum enum_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatEnum(enum_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Exception sysexcep_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatSysexcep(sysexcep_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        BclassBaseClassAbstract bclass_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatBclass(bclass_I, testoptionSHORT_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Class class_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strAnalizeAndFormatClass(class_I, testoptionSHORT_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        BclassBaseClassAbstract[] arrbclass_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return Tes2.strToSupportOneArgumentGeneric(arrbclass_I, testoptionSHORT_I , null);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        File bool_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return "File-SHORT";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Object obj_I,
        TestoptionEnum testoptionSHORT_I
    )
    {
        return "Object-SHORT";
    }

    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tes2.strTo(FULL) strTo with only the needed parameters for Fase 0*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(
        boolean bool_I,
        String strText_I
    )
    {
        return strText_I + "(" + Tes2.strAnalizeAndFormatBool(bool_I) +")";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        int int_I,
        String strText_I
    )
    {
        return strText_I + "(" + Tes2.strAnalizeAndFormatInt(int_I) +")";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        String str_I,
        String strText_I
    )
    {
        return strText_I + "(" + Tes2.strAnalizeAndFormatStr(str_I) +")";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        char char_I,
        String strText_I
    )
    {
        return strText_I + "(" + Tes2.strAnalizeAndFormatChar(char_I) +")";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        int[] arrint_I,
        String strText_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrint_I);

        String strFormatArrInt;
        if (
            Tes2.lstobjPreviouslyProcessed.contains(arrint_I)
            )
        {
            strFormatArrInt = strText_I + "(" + strObjId + "|look object up|" + ")";
        }
        else
        {
                                                        //Register arr or one argument generic as processed
            Tes2.lstobjPreviouslyProcessed.add(arrint_I);
            strFormatArrInt = Tes2.strFormatArr(arrint_I, strText_I, strObjId);
        }

        return strFormatArrInt;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        String[] arrstr_I,
        String strText_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrstr_I);

        String strFormatArrStr;
        if (
            Tes2.lstobjPreviouslyProcessed.contains(arrstr_I)
            )
        {
            strFormatArrStr = strText_I + "(" + strObjId + "|look object up|" + ")";
        }
        else
        {
                                                            //Register arr or one argument generic as processed
            Tes2.lstobjPreviouslyProcessed.add(arrstr_I);
            strFormatArrStr = Tes2.strFormatArr(arrstr_I, strText_I, strObjId);
        }

        return strFormatArrStr;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
       char[] arrchar_I,
       String strText_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrchar_I);

        String strFormatArrStr;
        if (
            Tes2.lstobjPreviouslyProcessed.contains(arrchar_I)
            )
        {
            strFormatArrStr = strText_I + "(" + strObjId + "|look object up|" + ")";
        }
        else
        {
                                                            //Register arr or one argument generic as processed
            Tes2.lstobjPreviouslyProcessed.add(arrchar_I);
            strFormatArrStr = Tes2.strFormatArr(arrchar_I, strText_I, strObjId);
        }

        return strFormatArrStr;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
       boolean[] arrbool_I,
       String strText_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrbool_I);

        String strFormatArrStr;
        if (
            Tes2.lstobjPreviouslyProcessed.contains(arrbool_I)
            )
        {
            strFormatArrStr = strText_I + "(" + strObjId + "|look object up|" + ")";
        }
        else
        {
                                                            //Register arr or one argument generic as processed
            Tes2.lstobjPreviouslyProcessed.add(arrbool_I);
            strFormatArrStr = Tes2.strFormatArr(arrbool_I, strText_I, strObjId);
        }

        return strFormatArrStr;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        BclassBaseClassAbstract bclass_I,
        String strText_I
    )
    {
                                                            //To display a bclass first time requires a block
        Ostring ostrbclassFull = new Ostring();
        if (
                                                            //Bclass processed for first time.
            !Tes2.lstobjPreviouslyProcessed.contains(bclass_I)
            )
        {
                                                            //A first time bclass should be display inside a block
            Ostring ostrNL = new Ostring();
            Ostring ostrLabel = new Ostring();
                                                            //The objId will be display before bclass, is not in the
                                                            //      block headings.

            Tes2.subBlockStart(ostrNL, ostrLabel, ostrbclassFull, strText_I, "");

            ostrbclassFull.v = ostrbclassFull.v + ostrNL.v +
                Tes2.strAnalizeAndFormatBclass(bclass_I, TestoptionEnum.FULL);

            Tes2.subBlockEnd(ostrNL, ostrbclassFull, ostrLabel.v);
        }
        else
        {
            ostrbclassFull.v = strText_I + "(" + Tes2.strAnalizeAndFormatBclass(bclass_I, TestoptionEnum.FULL) + ")";
        }

        return ostrbclassFull.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
                                                            //For CodtypeEnum, SyspathwhereEnum, SyspathtypeEnum,
                                                            //      BclassmutabilityEnum, FULL Version
        Enum enum_I,
        String strText_I
    )
    {
        if (
                enum_I == null
                )
            Tools.subAbort("enum_I cannot be null" );

        return strText_I + "(" + Tes2.strAnalizeAndFormatEnum(enum_I) + ")";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        LinkedList<BclassBaseClassAbstract> lst_I,
                                                            //For CodtypeEnum, SyspathwhereEnum, SyspathtypeEnum,
                                                            //      BclassmutabilityEnum, SHORT Version
            Enum enum_I
    )
    {
        if (
            enum_I == null
            )
            Tools.subAbort("enum_I cannot be null" );

        return Tes2.strAnalizeAndFormatEnum(enum_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        BclassBaseClassAbstract[] arrbclass_I,
        String strText_I
    )
    {
        return Tes2.strToSupportOneArgumentGeneric(arrbclass_I,TestoptionEnum.FULL, strText_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        BtupleBaseTupleAbstract[] arrbclass_I,
        String strText_I
    )
    {
        return Tes2.strToSupportOneArgumentGeneric(arrbclass_I,TestoptionEnum.FULL , strText_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportOneArgumentGeneric(
                                                            //Continue preparation for display.

                                                            //str, info to display

                                                            //arrstr, arrbclass, arrbtuple or arrenum.
        BclassBaseClassAbstract[] arrbclass_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of the one argument generic.
        String strText_I
        )
    {
        String strToSupportOneArgumentGeneric;
        if (
                arrbclass_I == null
                )
        {
            if (
                    testoptionOption_I == TestoptionEnum.SHORT
                    )
            {
                strToSupportOneArgumentGeneric = "null";
            }
            else
            {
                strToSupportOneArgumentGeneric = strText_I + "(null)";
            }
        }
        else
        {
            strToSupportOneArgumentGeneric = Tes2.strFormatArrOrOneArgumentGeneric(arrbclass_I, testoptionOption_I,
                    strText_I);
        }

        return strToSupportOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportOneArgumentGeneric(
                                                            //Continue preparation for display.

                                                            //str, info to display

                                                            //arrstr, arrbclass, arrbtuple or arrenum.
        BtupleBaseTupleAbstract[] arrbtuple_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of the one argument generic.
        String strText_I
        )
    {
        String strToSupportOneArgumentGeneric;
        if (
            arrbtuple_I == null
            )
        {
            if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
            {
                strToSupportOneArgumentGeneric = "null";
            }
            else
            {
                strToSupportOneArgumentGeneric = strText_I + "(null)";
            }
        }
        else
        {
            strToSupportOneArgumentGeneric = Tes2.strFormatArrOrOneArgumentGeneric(arrbtuple_I, testoptionOption_I,
                strText_I);
        }

        return strToSupportOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportOneArgumentGeneric(
                                                            //Continue preparation for display.

                                                            //str, info to display

                                                            //arrstr, arrbclass, arrbtuple or arrenum.
        Class[] arrtype_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of the one argument generic.
        String strText_I
        )
    {
        String strToSupportOneArgumentGeneric;
        if (
            arrtype_I == null
            )
        {
            if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
            {
                strToSupportOneArgumentGeneric = "null";
            }
            else
            {
                strToSupportOneArgumentGeneric = strText_I + "(null)";
            }
        }
        else
        {
            strToSupportOneArgumentGeneric = Tes2.strFormatArrOrOneArgumentGeneric(arrtype_I, testoptionOption_I,
                strText_I);
        }

        return strToSupportOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArrOrOneArgumentGeneric(
                                                            //Format for display
                                                            //An arr or One Argument Generic object should be display
                                                            //      only once per run.

                                                            //str, formated info

                                                            //arr to format
        BclassBaseClassAbstract[] arrbclass_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of arr or one argument generic object
        String strText_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrbclass_I);

        String strFormatArrOrOneArgumentGeneric;
        if (
            testoptionOption_I == TestoptionEnum.SHORT
            )
        {
            strFormatArrOrOneArgumentGeneric = strObjId;
        }
        else
        {
                                                            //An Arr or One Argument Generic object should be display
                                                            //      only once per run.

            if (
                Tes2.lstobjPreviouslyProcessed.contains(arrbclass_I)
                    )
            {
                strFormatArrOrOneArgumentGeneric = strText_I + "(" + strObjId + "|look object up|" + ")";
            }
            else
            {
                                                            //Register arr or one argument generic as processed
                Tes2.lstobjPreviouslyProcessed.add(arrbclass_I);

                strFormatArrOrOneArgumentGeneric = Tes2.strFormatArr(arrbclass_I, strText_I, strObjId);
            }
        }

        return strFormatArrOrOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArrOrOneArgumentGeneric(
                                                            //Format for display
                                                            //An arr or One Argument Generic object should be display
                                                            //      only once per run.

                                                            //str, formated info

                                                            //arr to format
        BtupleBaseTupleAbstract[] arrbtuple_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of arr or one argument generic object
        String strText_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrbtuple_I);

        String strFormatArrOrOneArgumentGeneric;
        if (
            testoptionOption_I == TestoptionEnum.SHORT
            )
        {
            strFormatArrOrOneArgumentGeneric = strObjId;
        }
        else
        {
                                                            //An Arr or One Argument Generic object should be display
                                                            //      only once per run.

            if (
                Tes2.lstobjPreviouslyProcessed.contains(arrbtuple_I)
                    )
            {
                strFormatArrOrOneArgumentGeneric = strText_I + "(" + strObjId + "|look object up|" + ")";
            }
            else
            {
                                                            //Register arr or one argument generic as processed
                Tes2.lstobjPreviouslyProcessed.add(arrbtuple_I);

                strFormatArrOrOneArgumentGeneric = Tes2.strFormatArr(arrbtuple_I, strText_I, strObjId);
            }
        }

        return strFormatArrOrOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArrOrOneArgumentGeneric(
                                                            //Format for display
                                                            //An arr or One Argument Generic object should be display
                                                            //      only once per run.

                                                            //str, formated info

                                                            //arr to format
        Class[] arrtype_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of arr or one argument generic object
        String strText_I
    )
    {
        String strObjId = Tes2.strGetObjId(arrtype_I);

        String strFormatArrOrOneArgumentGeneric;
        if (
            testoptionOption_I == TestoptionEnum.SHORT
            )
        {
            strFormatArrOrOneArgumentGeneric = strObjId;
        }
        else
        {
                                                            //An Arr or One Argument Generic object should be display
                                                            //      only once per run.

            if (
                Tes2.lstobjPreviouslyProcessed.contains(arrtype_I)
                    )
            {
                strFormatArrOrOneArgumentGeneric = strText_I + "(" + strObjId + "|look object up|" + ")";
            }
            else
            {
                                                            //Register arr or one argument generic as processed
                Tes2.lstobjPreviouslyProcessed.add(arrtype_I);

                strFormatArrOrOneArgumentGeneric = Tes2.strFormatArr(arrtype_I, strText_I, strObjId);
            }
        }

        return strFormatArrOrOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                 //Format for display, it could be:
                                                        // Set of Lines(Items) or One Line(Row).

                                                        //str, formated info.

                                                        //Read strFormatArrOrOneArgumentGeneric method for
                                                        //      paramenters description
        BclassBaseClassAbstract[] arrbclass_I,
        String strText_I,
        String strObjId_I
    )
    {
        Ostring ostrFormatArr = new Ostring();
        Ostring ostrNL = new Ostring();
        Ostring ostrLabel = new Ostring();
        Tes2.subBlockStart(ostrNL, ostrLabel, ostrFormatArr, strText_I, strObjId_I);

        ostrFormatArr.v = ostrFormatArr.v + Tes2.strListItems(arrbclass_I, ostrNL.v);

        Tes2.subBlockEnd(ostrNL, ostrFormatArr, ostrLabel.v);

        return ostrFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strListItems(                 //Format an array to a Set of Lines(Items) inside a block.
                                                        //Example:
                                                        //[
                                                        //{
                                                        //[0] item
                                                        //...
                                                        //[x] item
                                                        //}
                                                        //]

                                                        //str, set in block format

        BclassBaseClassAbstract[] arrbclass_I,
        String strNL_I
    )
    {
                                                        //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrbclass_I.length - 1) + "]").length();

                                                        //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrbclass_I.length];
        for (int intI = 0; intI < arrbclass_I.length; intI = intI + 1)
        {
            String strItem = Tes2.strAnalizeAndFormatBclass(arrbclass_I[intI], TestoptionEnum.FULL);

                                                        //Format: NL [i]_ item
            arrstrIndexAndItem[intI] = strNL_I + Tools.padRight(("[" + intI + "]"), intCharsInLongestIndex)
                    + " " + strItem;
        }

        return strNL_I + "{" + Arrays.toString(arrstrIndexAndItem) + strNL_I + "}";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(                         //Prepare for FULL display.

                                                        //str, info to display

                                                        //Read strToSupportDic method for paramenters description
        Integer[] arrintValue_I,
        String[] arrstrKey_I,
        String strText_I,
        Object objDicobj_I
    )
    {
        if (
            strText_I == null
            )
            Tools.subAbort(Tes2.strTo(strText_I, "strText_I") + " should have a value");

        return Tes2.strToSupportDic(arrintValue_I, arrstrKey_I, TestoptionEnum.FULL, strText_I, objDicobj_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportDic(              //Continue preparation for display.

                                                        //str, info to display

                                                        //arrstr, arrbclass, arrbtuple or arrenum
        Integer[] arrintValue_I,
                                                        //dic.Keys
        String[] arrstrKey_I,
                                                        //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                        //Variable name of the dic.
        String strText_I,
                                                        //dicbclass, dicbtuple or dicenum.
                                                        //Value should contain str or be the same type (or subtype)
                                                        //      contained in dic.
        Object objDicobj_I
    )
    {
        String strToSupportDic;
        if (
                objDicobj_I == null
                )
        {
            if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
            {
                strToSupportDic = "null";
            }
            else
            {
                strToSupportDic = strText_I + "(null)";
            }
        }
        else
        {

            //TODO validation of both arrays
                                                        //Abort if not a valid dic
            //Test.subVerifyDic(arrintValue_I, arrstrKey_I, objDicobj_I);

            strToSupportDic = Tes2.strFormatDicMain(arrintValue_I, arrstrKey_I, testoptionOption_I, strText_I,
                    objDicobj_I);
        }

        return strToSupportDic;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strFormatDicMain(             //Format for display
                                                        //A dic object should be display only once per run.

                                                        //str, formated info

                                                        //dic.Keys and dic.Values to format
        Integer[] arrintValue_I,
        String[] arrstrKey_I,
                                                        //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                        //Variable name of dic
        String strText_I,
                                                        //dic, any type (this is needed to get objId).
        Object objDic_I
    )
    {
                                                        //Get size from arrkey
        String strObjId = Tes2.strGetObjId(objDic_I).replace("[?]", "[" + arrstrKey_I.length + "]");

        String strFormatDicMain;
        if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
        {
            strFormatDicMain = strObjId;
        }
        else
        {
            if (
                Tes2.lstobjPreviouslyProcessed.contains(objDic_I)
                )
            {
                strFormatDicMain = strText_I + "(" + strObjId + "|look object up|" + ")";
            }
            else
            {
                                                        //Register dic as processed
                Tes2.lstobjPreviouslyProcessed.add(objDic_I);

                strFormatDicMain = Tes2.strFormatDic(arrintValue_I, arrstrKey_I, strText_I, strObjId);
            }
        }

        return strFormatDicMain;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatDic(                 //Format for display.

                                                        //str, formated info.

                                                        //Read strFormatDicMain method for paramenters description
        Integer[] arrintValue_I,
        String[] arrstrKey_I,
        String strText_I,
        String strObjId_I
    )
    {
        Tools.sort(arrstrKey_I, arrintValue_I);

                                                        //Compute [key] size.
        int intLonghestKey = 0;
        for (String str : arrstrKey_I)
        {
            intLonghestKey = Math.max(intLonghestKey, str.length());
        }
        intLonghestKey = intLonghestKey + "[]".length();
        intLonghestKey = Math.min(Tes2.intKEY_LEN_MAX, intLonghestKey);

        Ostring ostrFormatDic = new Ostring();
        Ostring ostrNL = new Ostring();
        Ostring ostrLabel = new Ostring();
        Tes2.subBlockStart(ostrNL, ostrLabel, ostrFormatDic, strText_I, strObjId_I);


                                                        //Produces lines to include in block.
        String[] arrstrEntry = new String[arrintValue_I.length];
        for (int intI = 0; intI < arrintValue_I.length; intI = intI + 1)
        {
                                                        //Confirm key has only visible chars
            String strKeyAnalized = Tes2.strAnalizeAndFormatStr(arrstrKey_I[intI]);
            String strKey;
            /*CASE*/
            if (
                                                        //Has just string info ("string information")
                strKeyAnalized.endsWith("\"")
                )
            {
                                                        //Take key without ""
                strKey = strKeyAnalized.substring(1, strKeyAnalized.length() - 1);
            }
            else if (
                                                        //Has lenght info and no diagnotic info ("string info"<nn>)
                strKeyAnalized.endsWith(">")
                )
            {
                int intMinorThanMark = strKeyAnalized.lastIndexOf("<");

                                                        //Take key without ""<nn>
                strKey = strKeyAnalized.substring(1, intMinorThanMark - 2);
            }
            else
            {
                                                        //Include disgnostic info (or is null).
                strKey = strKeyAnalized;
            }
            /*END-CASE*/

            arrstrEntry[intI] = ostrNL.v +
                    Tools.padRight(("[" + strKey + "]"), intLonghestKey) + " " +
                    Tes2.strAnalizeAndFormatInt(arrintValue_I[intI]);
        }

        ostrFormatDic.v = ostrFormatDic.v + ostrNL.v + "{" + Tools.strStringArrayToString(arrstrEntry) + ostrNL.v + "}";
        Tes2.subBlockEnd(ostrNL, ostrFormatDic, ostrLabel.v);

        return ostrFormatDic.v;
    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
                                                            //Class, equivalent to Type in C#
        Class class_I,
        String strText_I
    )
    {
        return strText_I + "(" + Tes2.strAnalizeAndFormatClass(class_I, TestoptionEnum.FULL) + ")";
    }

    /*public static String strTo(
        LinkedHashMap<String, Integer> dic_I,
        String strText_I
    )
    {
        return "LinkedHashMap<String, Integer>-FULL";
    }*/

    /*//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        LinkedHashMap<String, Oint> dic_I,
        String strText_I
    )
    {

        Collection<Oint> colointValues = dic_I.values();
        Oint[] arrointValues = new Oint[]{};
        arrointValues = colointValues.toArray(arrointValues);
        int[] arrintValues = Tools.convertToPrimitiveArray(arrointValues);

        Set<String> setstrKeys = dic_I.keySet();
        String[] arrstrKeys = new String[]{};
        arrstrKeys = setstrKeys.toArray(arrstrKeys);


        return Tes2.strTo(arrintValues, arrstrKeys, strText_I, dic_I);
    }*/

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
            LinkedHashMap<String, Integer> dic_I,
            String strText_I
    )
    {

        Collection<Integer> colointValues = dic_I.values();
        Integer[] arrintValues = new Integer[]{};
        arrintValues = colointValues.toArray(arrintValues);



        Set<String> setstrKeys = dic_I.keySet();
        String[] arrstrKeys = new String[]{};
        arrstrKeys = setstrKeys.toArray(arrstrKeys);

        return Tes2.strTo(arrintValues, arrstrKeys, strText_I, dic_I);
    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Exception excep_I,
        String strText_I
    )
    {
        return strText_I + "(" + Tes2.strAnalizeAndFormatSysexcep(excep_I) + ")";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        File sysfile_I,
        String strText_I
    )
    {
        return "File-FULL";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Oarrchar oarrchar_I,
        String strText_I
    )
    {
        return "Oarrchar-FULL";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        double[] arrdouble_I,
        String strText_I
    )
    {
        return "double[]-FULL";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        long[] arrlong_I,
        String strText_I
    )
    {
        return "long[]-FULL";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Object[] arrobj_I,
        String strText_I
    )
    {
        return "Object[]-FULL";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(
        Object obj_I,
        String strText_I
    )
    {
        return "Object-FULL";
    }

    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tes2.strTo strTo(String[], strText) methods needed for Fase 0*/
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                 //Format for display, it could be:
                                                        //Set of Lines(Items) or One Line(Row).

                                                        //str, formated info.

                                                        //Read strFormatArrOrOneArgumentGeneric method for
                                                        //      paramenters description
        int[] arrint_I,
        String strText_I,
        String strObjId_I
    )
    {
                                                        //Need to look for long item
        boolean boolSetOfLinesItems = false;
        boolSetOfLinesItems = false;
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            boolSetOfLinesItems ||
                (intI >= arrint_I.length)
        ))
        {
            String strItem = Tes2.strAnalizeAndFormatInt(arrint_I[intI]);
            boolSetOfLinesItems = (
                strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
            );

            intI = intI + 1;
        }

        Ostring strFormatArr = new Ostring();
        if (
            boolSetOfLinesItems
            )
        {
            Ostring strNL = new Ostring();
            Ostring strLabel = new Ostring();
            Tes2.subBlockStart(strNL, strLabel, strFormatArr, strText_I, strObjId_I);

            strFormatArr.v = strFormatArr.v + Tes2.strListItems(arrint_I, strNL.v);

            Tes2.subBlockEnd(strNL, strFormatArr, strLabel.v);
        }
        else
        {
        strFormatArr.v = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrint_I) + ")";
        }

        return strFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                 //Format for display, it could be:
                                                        //Set of Lines(Items) or One Line(Row).

                                                        //str, formated info.

                                                        //Read strFormatArrOrOneArgumentGeneric method for
                                                        //      paramenters description
        String[] arrstr_I,
        String strText_I,
        String strObjId_I
    )
    {
        boolean boolSetOfLinesItems = false;
        boolSetOfLinesItems = false;
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            boolSetOfLinesItems ||
                (intI >= arrstr_I.length)
        ))
        {
            String strItem = Tes2.strAnalizeAndFormatStr(arrstr_I[intI]);
            boolSetOfLinesItems = (
                strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
            );

            intI = intI + 1;
        }

        Ostring strFormatArr = new Ostring();
        if (
            boolSetOfLinesItems
            )
        {
            Ostring strNL = new Ostring();
            Ostring strLabel = new Ostring();
            Tes2.subBlockStart(strNL, strLabel, strFormatArr, strText_I, strObjId_I);

            strFormatArr.v = strFormatArr.v + Tes2.strListItems(arrstr_I, strNL.v);

            Tes2.subBlockEnd(strNL, strFormatArr, strLabel.v);
        }
        else
        {
        strFormatArr.v = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrstr_I) + ")";
        }

        return strFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                 //Format for display, it could be:
                                                        //Set of Lines(Items) or One Line(Row).

                                                        //str, formated info.

                                                        //Read strFormatArrOrOneArgumentGeneric method for
                                                        //      paramenters description
        char[] arrchar_I,
        String strText_I,
        String strObjId_I
    )
    {
        boolean boolSetOfLinesItems = false;
        boolSetOfLinesItems = false;
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            boolSetOfLinesItems ||
                (intI >= arrchar_I.length)
        ))
        {
            String strItem = Tes2.strAnalizeAndFormatChar(arrchar_I[intI]);
            boolSetOfLinesItems = (
                strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
            );

            intI = intI + 1;
        }

        Ostring strFormatArr = new Ostring();
        if (
            boolSetOfLinesItems
            )
        {
            Ostring strNL = new Ostring();
            Ostring strLabel = new Ostring();
            Tes2.subBlockStart(strNL, strLabel, strFormatArr, strText_I, strObjId_I);

            strFormatArr.v = strFormatArr.v + Tes2.strListItems(arrchar_I, strNL.v);

            Tes2.subBlockEnd(strNL, strFormatArr, strLabel.v);
        }
        else
        {
            strFormatArr.v = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrchar_I) + ")";
        }

        return strFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                     //Format for display, it could be:
                                                            //Set of Lines(Items) or One Line(Row).

                                                            //str, formated info.

                                                            //Read strFormatArrOrOneArgumentGeneric method for
                                                            //      paramenters description
        boolean[] arrbool_I,
        String strText_I,
        String strObjId_I
    )
    {
        boolean boolSetOfLinesItems = false;
        boolSetOfLinesItems = false;
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            boolSetOfLinesItems ||
                (intI >= arrbool_I.length)
        ))
        {
            String strItem = Tes2.strAnalizeAndFormatBool(arrbool_I[intI]);
            boolSetOfLinesItems = (
                strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
            );

            intI = intI + 1;
        }

        Ostring ostrFormatArr = new Ostring();
        if (
            boolSetOfLinesItems
            )
        {
            Ostring strNL = new Ostring();
            Ostring strLabel = new Ostring();
            Tes2.subBlockStart(strNL, strLabel, ostrFormatArr, strText_I, strObjId_I);

            ostrFormatArr.v = ostrFormatArr.v + Tes2.strListItems(arrbool_I, strNL.v);

            Tes2.subBlockEnd(strNL, ostrFormatArr, strLabel.v);
        }
        else
        {
            ostrFormatArr.v = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrbool_I) + ")";
        }

        return ostrFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                     //Format for display, it could be:
                                                            //Set of Lines(Items) or One Line(Row).

                                                            //str, formated info.

                                                            //Read strFormatArrOrOneArgumentGeneric method for
                                                            //      paramenters description
        BtupleBaseTupleAbstract[] arrbtuple_I,
        String strText_I,
        String strObjId_I
    )
    {
                                                            //En este caso, ya que se tiene un arreglo de Btuples
                                                            //      boolSetOfLinesItems debe ser true

        boolean boolSetOfLinesItems = true;
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            boolSetOfLinesItems ||
                (intI >= arrbtuple_I.length)
        ))
        {
            String strItem = Tes2.strAnalizeAndFormatBtuple(arrbtuple_I[intI], TestoptionEnum.FULL);
            boolSetOfLinesItems = (
                strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
            );

            intI = intI + 1;
        }

        Ostring ostrFormatArr = new Ostring();
        if (
            boolSetOfLinesItems
            )
        {
            Ostring strNL = new Ostring();
            Ostring strLabel = new Ostring();
            Tes2.subBlockStart(strNL, strLabel, ostrFormatArr, strText_I, strObjId_I);

            ostrFormatArr.v = ostrFormatArr.v + Tes2.strListItems(arrbtuple_I, strNL.v);

            Tes2.subBlockEnd(strNL, ostrFormatArr, strLabel.v);
        }
        else
        {
            ostrFormatArr.v = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrbtuple_I) + ")";
        }

        return ostrFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                     //Format for display, it could be:
                                                            //Set of Lines(Items) or One Line(Row).

                                                            //str, formated info.

                                                            //Read strFormatArrOrOneArgumentGeneric method for
                                                            //      paramenters description
        Class[] arrtype_I,
        String strText_I,
        String strObjId_I
    )
    {
        boolean boolSetOfLinesItems = false;
        boolSetOfLinesItems = false;
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            boolSetOfLinesItems ||
                (intI >= arrtype_I.length)
        ))
        {
            String strItem = Tes2.strAnalizeAndFormatClass(arrtype_I[intI], TestoptionEnum.FULL);
            boolSetOfLinesItems = (
                strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
            );

            intI = intI + 1;
        }

        Ostring ostrFormatArr = new Ostring();
        if (
            boolSetOfLinesItems
            )
        {
            Ostring strNL = new Ostring();
            Ostring strLabel = new Ostring();
            Tes2.subBlockStart(strNL, strLabel, ostrFormatArr, strText_I, strObjId_I);

            ostrFormatArr.v = ostrFormatArr.v + Tes2.strListItems(arrtype_I, strNL.v);

            Tes2.subBlockEnd(strNL, ostrFormatArr, strLabel.v);
        }
        else
        {
            ostrFormatArr.v = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrtype_I) + ")";
        }

        return ostrFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strListItems(                     //For array of integers.
                                                            //Format an array to a Set of Lines(Items) inside a block.
                                                            //Example:
                                                            //{
                                                            //[0] item
                                                            //[
                                                            //...
                                                            //[x] item
                                                            //}
                                                            //]

                                                        //str, set in block format

        int[] arrint_I,
        String strNL_I
        )
    {
                                                        //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrint_I.length - 1) + "]").length();

                                                        //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrint_I.length];
        for (int intI = 0; intI < arrint_I.length; intI = intI + 1)
        {
            String strItem = Tes2.strAnalizeAndFormatInt(arrint_I[intI]);

                                                        //Format: NL [i]_ item

            arrstrIndexAndItem[intI] = strNL_I + Tools.padRight("[" + intI + "]", intCharsInLongestIndex) + " " +
                strItem;
        }

        return strNL_I + "{" + String.join("",arrstrIndexAndItem) + strNL_I + "}";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strListItems(                     //For array of Strings.
                                                            //Format an array to a Set of Lines(Items) inside a block.
                                                            //Example:
                                                            //{
                                                            //[0] item
                                                            //[
                                                            //...
                                                            //[x] item
                                                            //}
                                                            //]

                                                            //str, set in block format

        String[] arrstr_I,
        String strNL_I
        )
    {
                                                            //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrstr_I.length - 1) + "]").length();

                                                        //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrstr_I.length];
        for (int intI = 0; intI < arrstr_I.length; intI = intI + 1)
        {
            String strItem = Tes2.strAnalizeAndFormatStr(arrstr_I[intI]);

                                                        //Format: NL [i]_ item

            arrstrIndexAndItem[intI] = strNL_I + Tools.padRight("[" + intI + "]", intCharsInLongestIndex) + " " +
                strItem;
        }

        return strNL_I + "{" + String.join("",arrstrIndexAndItem) + strNL_I + "}";
    }
    
    //------------------------------------------------------------------------------------------------------------------
    private static String strListItems(                     //For array of Strings.
                                                            //Format an array to a Set of Lines(Items) inside a block.
                                                            //Example:
                                                            //{
                                                            //[0] item
                                                            //[
                                                            //...
                                                            //[x] item
                                                            //}
                                                            //]

                                                            //str, set in block format

        char[] arrchar_I,
        String strNL_I
        )
    {
                                                        //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrchar_I.length - 1) + "]").length();

                                                        //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrchar_I.length];
        for (int intI = 0; intI < arrchar_I.length; intI = intI + 1)
        {
            String strItem = Tes2.strAnalizeAndFormatChar(arrchar_I[intI]);

                                                        //Format: NL [i]_ item

            arrstrIndexAndItem[intI] = strNL_I + Tools.padRight("[" + intI + "]", intCharsInLongestIndex) + " " +
                strItem;
        }

        return strNL_I + "{" + String.join("",arrstrIndexAndItem) + strNL_I + "}";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strListItems(                     //For array of Strings.
                                                            //Format an array to a Set of Lines(Items) inside a block.
                                                            //Example:
                                                            //{
                                                            //[0] item
                                                            //[
                                                            //...
                                                            //[x] item
                                                            //}
                                                            //]

                                                            //str, set in block format

        boolean[] arrbool_I,
        String strNL_I
        )
    {
                                                        //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrbool_I.length - 1) + "]").length();

                                                        //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrbool_I.length];
        for (int intI = 0; intI < arrbool_I.length; intI = intI + 1)
        {
            String strItem = Tes2.strAnalizeAndFormatBool(arrbool_I[intI]);

                                                        //Format: NL [i]_ item

            arrstrIndexAndItem[intI] = strNL_I + Tools.padRight("[" + intI + "]", intCharsInLongestIndex) + " " +
                strItem;
        }

        return strNL_I + "{" + String.join("",arrstrIndexAndItem) + strNL_I + "}";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strListItems(                     //For array of Strings.
                                                            //Format an array to a Set of Lines(Items) inside a block.
                                                            //Example:
                                                            //{
                                                            //[0] item
                                                            //[
                                                            //...
                                                            //[x] item
                                                            //}
                                                            //]

                                                            //str, set in block format

        BtupleBaseTupleAbstract[] arrbtuple_I,
        String strNL_I
        )
    {
                                                        //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrbtuple_I.length - 1) + "]").length();

                                                        //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrbtuple_I.length];
        for (int intI = 0; intI < arrbtuple_I.length; intI = intI + 1)
        {
            String strItem = Tes2.strAnalizeAndFormatBtuple(arrbtuple_I[intI], TestoptionEnum.FULL);

                                                        //Format: NL [i]_ item

            arrstrIndexAndItem[intI] = strNL_I + Tools.padRight("[" + intI + "]", intCharsInLongestIndex) + " " +
                strItem;
        }

        return strNL_I + "{" + String.join("",arrstrIndexAndItem) + strNL_I + "}";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strListItems(                     //For array of Strings.
                                                            //Format an array to a Set of Lines(Items) inside a block.
                                                            //Example:
                                                            //{
                                                            //[0] item
                                                            //[
                                                            //...
                                                            //[x] item
                                                            //}
                                                            //]

                                                            //str, set in block format

        Class[] arrtype_I,
        String strNL_I
        )
    {
                                                        //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrtype_I.length - 1) + "]").length();

                                                        //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrtype_I.length];
        for (int intI = 0; intI < arrtype_I.length; intI = intI + 1)
        {
            String strItem = Tes2.strAnalizeAndFormatClass(arrtype_I[intI], TestoptionEnum.FULL);

                                                        //Format: NL [i]_ item

            arrstrIndexAndItem[intI] = strNL_I + Tools.padRight("[" + intI + "]", intCharsInLongestIndex) + " " +
                strItem;
        }

        return strNL_I + "{" + String.join("",arrstrIndexAndItem) + strNL_I + "}";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strLineRow(                       //For array of integers.
                                                            //Produces:
                                                            //{ item, ..., item }.

                                                            //str, arr in one line format.

        int[] arrint_I
    )
    {
        //Convert arrint to arrstr
        String[] arrstrItem = new String[arrint_I.length];
        for (int intI = 0; intI < arrint_I.length; intI = intI + 1)
        {
            arrstrItem[intI] = Tes2.strAnalizeAndFormatInt(arrint_I[intI]);
        }

        //Format: { item, item, ..., item }
        return Tes2.strVectorFromSet(arrstrItem);
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strLineRow(                       //Produces:
                                                            //{ item, ..., item }.

                                                            //str, arr in one line format.

        String[] arrstr_I
        )
    {
                                                        //Convert arrobj to arrstr
        String[] arrstrItem = new String[arrstr_I.length];
        for (int intI = 0; intI < arrstr_I.length; intI = intI + 1)
        {
            //arrstrItem[intI] = Tes2.strAnalizeAndFormatCheckNulls(arrstr_I[intI], TestoptionEnum.FULL);
        	arrstrItem[intI] = "\"" + arrstr_I[intI] + "\"";
        }

                                                    //Format: { item, item, ..., item }
        return Tes2.strVectorFromSet(arrstrItem);
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strLineRow(                       //For array of integers.
                                                            //Produces:
                                                            //{ item, ..., item }.

                                                            //str, arr in one line format.

        char[] arrchar_I
    )
    {
                                                            //Convert arrint to arrstr
        String[] arrstrItem = new String[arrchar_I.length];
        for (int intI = 0; intI < arrchar_I.length; intI = intI + 1)
        {
            arrstrItem[intI] = Tes2.strAnalizeAndFormatChar(arrchar_I[intI]);
        }

                                                            //Format: { item, item, ..., item }
        return Tes2.strVectorFromSet(arrstrItem);
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strLineRow(                       //For array of integers.
                                                            //Produces:
                                                            //{ item, ..., item }.

                                                            //str, arr in one line format.

        boolean[] arrbool_I
    )
    {
                                                            //Convert arrint to arrstr
        String[] arrstrItem = new String[arrbool_I.length];
        for (int intI = 0; intI < arrbool_I.length; intI = intI + 1)
        {
            arrstrItem[intI] = Tes2.strAnalizeAndFormatBool(arrbool_I[intI]);
        }

                                                            //Format: { item, item, ..., item }
        return Tes2.strVectorFromSet(arrstrItem);
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strLineRow(                       //For array of integers.
                                                            //Produces:
                                                            //{ item, ..., item }.

                                                            //str, arr in one line format.

        BtupleBaseTupleAbstract[] arrbtuple_I
    )
    {
                                                            //Convert arrint to arrstr
        String[] arrstrItem = new String[arrbtuple_I.length];
        for (int intI = 0; intI < arrbtuple_I.length; intI = intI + 1)
        {
            arrstrItem[intI] = Tes2.strAnalizeAndFormatBtuple(arrbtuple_I[intI], TestoptionEnum.FULL);
        }

                                                            //Format: { item, item, ..., item }
        return Tes2.strVectorFromSet(arrstrItem);
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strLineRow(                       //For array of integers.
                                                            //Produces:
                                                            //{ item, ..., item }.

                                                            //str, arr in one line format.

        Class[] arrbtuple_I
    )
    {
                                                            //Convert arrint to arrstr
        String[] arrstrItem = new String[arrbtuple_I.length];
        for (int intI = 0; intI < arrbtuple_I.length; intI = intI + 1)
        {
            arrstrItem[intI] = Tes2.strAnalizeAndFormatClass(arrbtuple_I[intI], TestoptionEnum.FULL);
        }

                                                            //Format: { item, item, ..., item }
        return Tes2.strVectorFromSet(arrstrItem);
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strVectorFromSet(                 //Produces:
                                                            //{ stuff, ..., stuff }.
                                                            //Posibilities:
                                                            //Put a set of strItem in a vector (strRow).
                                                            //Put a set of strRow in a vector (strMatrix).
                                                            //Put a set of strMatrix in a vector (strCube).

                                                            //str, vector format.

                                                            //Stuff to be included in strVector.
        String[] arrstrStuff_I
        )
    {
        String strRowFormatBeforeAddingBrackets;
        if (
            arrstrStuff_I.length == 0
            )
        {
            strRowFormatBeforeAddingBrackets = " ";
        }
        else
        {
            strRowFormatBeforeAddingBrackets = " " + String.join(", ", arrstrStuff_I) + " ";
        }

        return "{" + strRowFormatBeforeAddingBrackets + "}";
    }
    
    //------------------------------------------------------------------------------------------------------------------
    private static String strAnalizeAndFormatCheckNulls(    //Produces an object in string format.
                                                            //Before calling strAnalizeAndFormatXxxx checks for null

                                                            //str, object in string format, could be null.

                                                            //Object to format
        String obj_I,
                                                            //SHORT or FULL
        TestoptionEnum testoptionOption_I
        )
    {
        String strAnalizeAndFormatCheckNulls;
        if (
            obj_I == null
            )
        {
            strAnalizeAndFormatCheckNulls = "null";
        }
        else
        {
            /*CASE*/
            if (
//                obj_I is BboxBaseBoxingAbtract
            		false
                )
            {
//                strAnalizeAndFormatCheckNulls = Tes2.strAnalizeAndFormatBbox((BboxBaseBoxingAbtract)obj_I);
            }
//            else if (
//                obj_I is BclassBaseClassAbstract
//                )
//            {
//                strAnalizeAndFormatCheckNulls =
//                    Tes2.strAnalizeAndFormatBclass((BclassBaseClassAbstract)obj_I, testoptionOption_I);
//            }
//            else if (
//                obj_I is BtupleBaseTupleAbstract
//                )
//            {
//                strAnalizeAndFormatCheckNulls =
//                    Tes2.strAnalizeAndFormatBtuple((BtupleBaseTupleAbstract)obj_I, testoptionOption_I);
//            }
//            else if (
//                obj_I is Enum
//                )
//            {
//                strAnalizeAndFormatCheckNulls = Tes2.strAnalizeAndFormatEnum((Enum)obj_I);
//            }
//            else if (
//                obj_I is Exception
//                )
//            {
//                strAnalizeAndFormatCheckNulls = Tes2.strAnalizeAndFormatSysexcep((Exception)obj_I);
//            }
            else
            {
                strAnalizeAndFormatCheckNulls = Tes2.strAnalizeAndFormatSystemType(obj_I, testoptionOption_I);
            }
            /*END-CASE*/
        }

        return strAnalizeAndFormatCheckNulls;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    private static String strAnalizeAndFormatSystemType(    //Produces an object in string format (system type)

                                                            //str, object in string format.

                                                            //Object to format
        Object obj_I,
                                                            //SHORT or FULL
        TestoptionEnum testoptionOption_I
        )
    {
        String strAnalizeAndFormatSystemType;
        /*CASE*/
        if (
            obj_I instanceof String
            )
        {
            strAnalizeAndFormatSystemType = Tes2.strAnalizeAndFormatStr((String)obj_I);
        }
//        else if (
//            obj_I instanceof Type
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes2.strAnalizeAndFormatType((Type)obj_I, testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof DirectoryInfo
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes2.strAnalizeAndFormatSysdir((DirectoryInfo)obj_I,
//                testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof FileInfo
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes2.strAnalizeAndFormatSysfile((FileInfo)obj_I, testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof StreamReader
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes2.strAnalizeAndFormatSyssr((StreamReader)obj_I, testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof StreamWriter
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes2.strAnalizeAndFormatSyssw((StreamWriter)obj_I, testoptionOption_I);
//        }
        else
        {
            if (
                true
                )
//                Tools.subAbort(Tes2.strTo(obj_I.GetType(), "obj_I.GetType") + " SOMETHING IS WRONG!!!," +
//                    " method strAnalizeAndFormatXxxx to process this system type is missing");

            strAnalizeAndFormatSystemType = null;
        }
        /*END-CASE*/

        return strAnalizeAndFormatSystemType;
    }
    
    //==================================================================================================================
    /*TASK Tes2.strTo(SHORT, 3 Arguments) strTo with only the needed parameters for Fase 0*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(
        Object[] arrobj_I,
        TestoptionEnum testoptionSHORT_I,
        Object objOneArgumentGeneric_I
    )
    {
        if (
            testoptionSHORT_I != TestoptionEnum.SHORT
            )
            Tools.subAbort(Tes2.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");


        return Tes2.strToSupportOneArgumentGeneric(arrobj_I, testoptionSHORT_I, null, objOneArgumentGeneric_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportOneArgumentGeneric(
                                                            //Continue preparation for display.

                                                            //str, info to display

                                                            //arrstr, arrbclass, arrbtuple or arrenum.
        Object[] arrobj_I,
                                                            //SHORT or FULL display
        TestoptionEnum testoptionOption_I,
                                                            //Variable name of the one argument generic.
        String strText_I,
                                                            //lstbclass, lstbtuple or lstenum, queuebclass, ...
                                                            //Main should contain str or the type (or subtype)
                                                            //      contained in one argument generic.
        Object objOneArgumentGeneric_I
        )
    {
        String strToSupportOneArgumentGeneric;
        if (
            objOneArgumentGeneric_I == null
            )
        {
            if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
            {
                strToSupportOneArgumentGeneric = "null";
            }
            else
            {
                strToSupportOneArgumentGeneric = strText_I + "(null)";
            }
        }
        else
        {
            //TODO Validar este fusil y eta rama del if-else
                                                            //Abort if both parameters are not consistent.
            //Tes2.subVerifyOneArgumentGeneric(arrobj_I, objOneArgumentGeneric_I);


            strToSupportOneArgumentGeneric = "";//Tes2.strFormatArrOrOneArgumentGeneric(arrobj_I, testoptionOption_I,
                    //strText_I, objOneArgumentGeneric_I);
        }

        return strToSupportOneArgumentGeneric;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(
        BclassBaseClassAbstract[] arrbclass_I,
        TestoptionEnum testoptionSHORT_I,
        LinkedList<BclassBaseClassAbstract> lstbclass_I
    )
    {
        return "BclassBaseClassAbstract[]-SHORT, LinkedList<BclassBaseClassAbstract>";
    }

    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tes2 Metodos de apoyo para analizar y dar formato a los parametros necesarios para Fase 0.*/
    //------------------------------------------------------------------------------------------------------------------
    private static String strAnalizeAndFormatBool(
        boolean bool_I
    )
    {
        String strBool;
        if (
            bool_I
            )
        {
            strBool = "true";
        }
        else
        {
            strBool = "false";
        }
        return strBool;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatInt(
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
        TestchartypeEnum testchartype = Tes2.testchartypeKeyboardOrEtc(char_I);

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

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    private static String strAnalizeAndFormatStr(           //Prepara un String para su despliege con información de
                                                            //      caracteres que no son del KEYBOARD.
                                                            //Ejemplos:
                                                            //1. "Esto es lo que se analizo"<25>.
                                                            //2. "©XYX"<4>{ <0, '©', 0x00A9> }.
                                                            //3. "^XYX"<4>{ <0, '^', 0x0001> }.
                                                            //4. "^XYX"<4>{ <0, '^', 0x0009, \t, Horizontal Tab> }.
                                                            //1) Todo es del KEYBOARD, solo se añaden las comillas y su
                                                            //      longitud.
                                                            //2) El primer caracter © no aparece en el KEYBOARD, incluyo
                                                            //      su hexadecimal.
                                                            //3) El primer caracter es NONVISIBLE_WITHOUT_DESCRIPTION,
                                                            //      se sustituye por _ (el caracter en
                                                            //      charSUBSTITUTE_NONVISIBLE) e incluyo su hexadecimal.
                                                            //4) El primer caracter es un Horizonal Tab, no es
                                                            //      visible, se sustituye por _ (el caracter en
                                                            //      charSUBSTITUTE_NONVISIBLE), incluyo su hexadecimal y
                                                            //      su descripción.
                                                            //Puede haber más de un caracter que no es del KEYBOARD, se
                                                            //      añade "{ <.....>, <.....>, ..., <......> }".
                                                            //Si no hay ningún caracter que no es del KEYBOARD, no se
                                                            //      añade nada en esta parte, esto es no se añade "{ }",
                                                            //      esto fue lo que sucedió en el ejemplo 1.
                                                            //str, String para despligue con diagnostico de caracteres
                                                            //      que no están en el KEYBOARD.
    
                                                            //String a analizar.
        String str_I
        )
    {
                                                            //Para formar lo que va a regresar.
        String strAnalizeAndFormatStr;
        if (
                                                            //No hay String.
            str_I == null
            )
        {
            strAnalizeAndFormatStr = "null";
        }
        else
        {
                                                            //Paso a arrchar para poder modificarlo.
            char[] arrcharToAnalize = str_I.toCharArray();

                                                            //Para conjunto de información de diagnóstico.
            LinkedList<String> lststrDiagnosticInfo = new LinkedList<String>();

                                                            //Reviso todos los caracteres.
            for (int intI = 0; intI < arrcharToAnalize.length; intI = intI + 1)
            {
                                                            //Paso un caracter a formato desplegable, el formato será:
                                                            //'c', KEYBOARD.
                                                            //'c'<0x1234>, VISIBLE_NONKEYBOARD.
                                                            //'^'<0x1234>, NONVISIBLE_WITHOUT_DESCRIPTION.
                                                            //'^'<0x1234, descripción>, NONVISIBLE_WITH_DESCRIPTION.
                String strCharAnalized = Tes2.strAnalizeAndFormatChar(arrcharToAnalize[intI]);

                                                            //Si tiene información de diagnóstico la proceso.
                if (
                                                            //Si tiene información de diagnóstico.
                    strCharAnalized.length() > 3
                    )
                {
                                                            //Cambio caracter, la pos. 1 tiene el caracter revisado.
                    arrcharToAnalize[intI] = strCharAnalized.charAt(1);

                                                            //Debo formar un String:
                                                            //<n, 'c', 0x1234>, VISIBLE_NONKEYBOARD.
                                                            //<n, '^', 0x1234>, NONVISIBLE_WITHOUT_DESCRIPTION.
                                                            //<n, '^', 0x1234, descripción>,
                                                            //      NONVISIBLE_WITH_DESCRIPTION.
                    String strDiagnosticInfo = "<" + intI + ", " + strCharAnalized.substring(0, 3) + ", " +
                        strCharAnalized.substring(4);

                                                            //Añade info a la lista.
                    lststrDiagnosticInfo.add(strDiagnosticInfo);
                }
            }

                                                            //Forma la longitud del String, solo de desea mostrar cuando
                                                            //      excede intLONG_STRING.
            String strLongString;
            if (
                str_I.length() > Tes2.intLONG_STRING
                )
            {
                strLongString = "<" + str_I.length() + ">";
            }
            else
            {
                strLongString = "";
            }

                                                            //Forma el String a desplegar.
            if (
                                                            //No tiene ningún caracter con información de diagnóstico.
                lststrDiagnosticInfo.size() == 0
                )
            {
                                                            //Formatea cuando NO tiene información de diagnóstico.
                strAnalizeAndFormatStr = "\"" + str_I + "\"" + strLongString;
            }
            else
            {
                                                            //Formatea cuando SI tiene información de diagnóstico.
                strAnalizeAndFormatStr = "\"" + new String(arrcharToAnalize) + "\"" + "<" + arrcharToAnalize.length +
                    ">" + "{ " + String.join(", ", lststrDiagnosticInfo.toArray(new String[0])) + " }";
            }
        }

        return strAnalizeAndFormatStr;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatEnum(      //Analize and format enum (or subclass of enum).
                                                        // str, enum formated to display.

                                                        //Enum to be analized and format
        Enum enum_I
    )
    {
        return enum_I.name();
    }

     //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatSysexcep(      //Prepare a object to display.
                                                            //str, sysexcep_I prepared to display.

                                                            //Object to be analized and format
        Exception sysexcep_I
    )
    {
        return sysexcep_I.toString();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatClass(         //Prepare a object to display.
                                                            //str, type prepared to display.

                                                            //Object to be analized and format
        Class class_I,
                                                            //SHORT or FULL
        TestoptionEnum testoptionOption_I
    )
    {
        String strAnalizeAndFormatClass;
        if (
            testoptionOption_I == TestoptionEnum.SHORT
            )
        {
            strAnalizeAndFormatClass = "<" + class_I.getSimpleName() + ">";
        }
        else
        {
            strAnalizeAndFormatClass = "<Name(" + class_I.getSimpleName() + ")>";
        }

        return strAnalizeAndFormatClass;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatBtuple(        //Analize and format btuple (or subclass of btuple).
                                                            //A btuple object should be display only once per run.
                                                            //str, btuple formated to display.

                                                            //Bclass to be analized and format
        BtupleBaseTupleAbstract btuple_I,
                                                            //SHORT or FULL
        TestoptionEnum testoptionOption_I
        )
    {
        String strAnalizeAndFormatBtuple;
        if (
                                                            //Was processed before
            Tes2.lstobjPreviouslyProcessed.contains(btuple_I)
            )
        {
                                                            //Include only objId
            strAnalizeAndFormatBtuple = Tes2.strGetObjId(btuple_I) + "|look object up|";
        }
        else
        {
                                                            //Register as processed
            Tes2.lstobjPreviouslyProcessed.add(btuple_I);

            if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
            {
                strAnalizeAndFormatBtuple = btuple_I.strTo(TestoptionEnum.SHORT);
            }
            else
            {
                strAnalizeAndFormatBtuple = btuple_I.strTo();
            }
        }

        return strAnalizeAndFormatBtuple;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
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
            Arrays.binarySearch(Tes2.arrcharKEYBOARD, charARevise_I) >= 0
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.KEYBOARD;
        }
        else if (
            Arrays.binarySearch(Tes2.arrcharNONVISIBLE, charARevise_I) >= 0
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION;
        }
        else if (
            Tes2.boolIsNonVisibleWithoutDescription(charARevise_I)
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

//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatArr(                     //Format for display, it could be:
//                                                            //Set of Lines(Items) or One Line(Row).
//
//                                                            //str, formated info.
//
//                                                            //Read strFormatArrOrOneArgumentGeneric method for
//                                                            //      paramenters description
//        Object[] arrobj_I,
//        String strText_I,
//        String strObjId_I
//        )
//    {
//                                                            //Find if Set of Lines(Items) format is required.
//        boolean boolSetOfLinesItems = false;
//
//        /*NOT NEEDED FOR FASE 0*/
//        /*if (
//            (arrobj_I is BclassBaseClassAbstract[]) || (arrobj_I is BtupleBaseTupleAbstract[])
//        )
//        {
//            boolSetOfLinesItems = true;
//        }*/
//                                                                //Need to look for long item
//        boolSetOfLinesItems = false;
//        int intI = 0;
//        /*UNTIL-DO*/
//        while (!(
//            boolSetOfLinesItems ||
//            (intI >= arrobj_I.length)
//        ))
//        {
//            String strItem = Tes2.strAnalizeAndFormatCheckNulls(arrobj_I[intI]);
//            boolSetOfLinesItems = (
//                strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
//            );
//
//            intI = intI + 1;
//        }
//
//        String strFormatArr;
//        if (
//            boolSetOfLinesItems
//            )
//        {
//            String strNL;
//            String strLabel;
//            Tes2.subBlockStart(out strNL, out strLabel, out strFormatArr, strText_I, strObjId_I);
//
//            strFormatArr = strFormatArr + Tes2.strListItems(arrobj_I, strNL);
//
//            Tes2.subBlockEnd(ref strNL, ref strFormatArr, strLabel);
//        }
//        else
//        {
//            strFormatArr = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrobj_I) + ")";
//        }
//
//        return strFormatArr;
//    }
//
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
        strNL_O.v = Tes2.strNL();

                                                            //Asigna el siguiente nivel (lo regresa al cerrar block).
        Tes2.intLevel = Tes2.intLevel + 1;

                                                            //Asigna una secuencia única.
        Tes2.intStartEnd = Tes2.intStartEnd + 1;

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
                                                            //If we are in level 1, is is the beginig of a Tes2 (new
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
        Tes2.intLevel = Tes2.intLevel - 1;

        strNL_IO.v = strNL();
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strNL(                            //NL + caracteres indentación.
        )
    {
                                                            //Determina el NL+indentación que corresponde al block.
        if (
            Tes2.intLevel < 0
            )
            Tools.subAbort(Tes2.strTo(Tes2.intLevel, "Tes2.intNivel") + " should be 0 or positive");

                                                            //Determina la cantidad de espacios para la indentación.
        int intSpaces;
        if (
                                                            //El nivel excede el arreglo.
            Tes2.intLevel >= Tes2.arrintLevelSpaces.length
            )
        {
                                                            //Cuando no alcance usa el último valor.
            intSpaces = Tes2.arrintLevelSpaces[Tes2.arrintLevelSpaces.length - 1];
        }
        else
        {
            intSpaces = Tes2.arrintLevelSpaces[intLevel];
        }

                                                            //Return NL with spacing required
        return System.lineSeparator() + Tools.padRight("", intSpaces);
    }
    
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
        Tes2.boolIsHashCodeOn = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subSetHashCode(                      //Marca que desea incluir el HashCode en los ObjIn.
        )
    {
        Tes2.boolIsHashCodeOn = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subResetHashCode(                    //Marca que desea NO incluir el HashCode en los ObjIn.
        )
    {
        Tes2.boolIsHashCodeOn = false;
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
            Tools.subAbort(Tes2.strTo(obj_I, "obj_I") + " should have a value");

        if (
            !Tes2.boolIsStandard(obj_I, false)
            )
            Tools.subAbort(Tes2.strTo(obj_I.getClass(), "obj_I.GetType") + " is nonstandard type");

                                                            //El HashCode puede ser nulificado (cambiado a ?)
        String strHashCode;
        if (
            Tes2.boolIsHashCodeOn
            )
        {
            strHashCode = Integer.toString(obj_I.hashCode());
        }
        else
        {
                                                            //Deja solo ?.
            strHashCode = Tes2.strHASH_CODE_NULL;
        }

        return Tes2.strPrefix(obj_I) + Tes2.strCollectionSize(obj_I) + ":" + strHashCode;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*SHARE METHODS*/

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
            Tools.subAbort(Tes2.strTo(obj_I, "obj_I") + " can not be null");

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
            boolIsStandard = Tes2.boolIsStandardArray(classObj, boolAbort_I);
        }
        else if (
            Tools.boolIsGenericType(classObj)
            )
        {
            boolIsStandard = Tes2.boolIsStandardGeneric(obj_I, boolAbort_I);
        }
        else
        {
            boolIsStandard = Tes2.boolIsStandardSingle(classObj, boolAbort_I);
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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardSingle;
        /*CASE*/
        if (
            Arrays.binarySearch(Tes2.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0
            )
        {
            boolIsStandardSingle = Tes2.boolIsStandardPrimitive(class_I, boolAbort_I);
        }
        else if (
            Arrays.binarySearch(Tes2.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
            )
        {
            boolIsStandardSingle = Tes2.boolIsStandardSystem(class_I, boolAbort_I);
        }
        else if (
            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes2.boolIsStandardBclass(class_I, boolAbort_I);
        }
        else if (
            BtupleBaseTupleAbstract.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes2.boolIsStandardBtuple(class_I, boolAbort_I);
        }
        else if (
            Enum.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes2.boolIsStandardEnum(class_I, boolAbort_I);
        }
        else if (
            Exception.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes2.boolIsStandardException(class_I, boolAbort_I);
        }
        else
        {
            boolIsStandardSingle = false;

            if (
                boolAbort_I && !boolIsStandardSingle
                )
                Tools.subAbort(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                    Tes2.strTo(class_I, "type_I") + " is not an standard primitive type");
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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardPrimitive = (
                                                            //Is a primitive included in Towa Standard.
            (Arrays.binarySearch(Tes2.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0)
            );

        if (
            boolAbort_I && !boolIsStandardPrimitive
            )
            Tools.subAbort(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                Tes2.strTo(class_I, "type_I") + " is not an standard primitive type");

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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardSimpleOrSystem = (
            Arrays.binarySearch(Tes2.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
            );

        if (
            boolAbort_I && !boolIsStandardSimpleOrSystem
            )
            Tools.subAbort(Tes2.strTo(Tes2.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
                Tes2.strTo(class_I, "type_I") + " is not an standard system type");

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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardBclass = (
            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
            );

        if (
            boolAbort_I && !boolIsStandardBclass
            )
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " is not an standard bclass type");

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
                    Tools.subAbort(Tes2.strTo(class_I, "type_I") +
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
                    Tools.subAbort(Tes2.strTo(class_I, "type_I") +
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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") +
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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") +
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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardException = (
            Exception.class.isAssignableFrom(class_I)
            );

        if (
            boolAbort_I && !boolIsStandardException
            )
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " is not an standard Exception type");

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
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardArray = (
            class_I.isArray() &&
                                                            //Is obj[], obj[,] or obj[, ,]
            (Tools.intArrRank(class_I) <= 3)
            );

        if (
            boolAbort_I && !boolIsStandardArray
            )
            Tools.subAbort(Tes2.strTo(class_I, "type_I") + " is not an standard array type");

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
                !Tes2.boolIsStandard(classElement, false)
                ));

            if (
                boolAbort_I && !boolIsStandardArray
                )
                Tools.subAbort(Tes2.strTo(class_I, "type_I") + ", " +
                    Tes2.strTo(classElement, "type_I.GetElementType") +
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
            Tools.subAbort(Tes2.strTo(obj_I, "obj_I") + " can not be null");

        Class classObj = obj_I.getClass();
        boolean boolIsStandardGeneric = (
            Tools.boolIsGenericType(classObj) &&
            (Arrays.binarySearch(Tes2.arrstrGENERIC_TYPE, classObj.getSimpleName()) >= 0)
            );

        if (
            boolAbort_I && !boolIsStandardGeneric
            )
            Tools.subAbort(Tes2.strTo(classObj, "type_I") + " is not an standard generic Class");

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
                    !Tes2.boolIsStandard(classMainArgument, false)
                    ));

                    if (
                        boolAbort_I && !boolIsStandardGeneric
                        )
                        Tools.subAbort(Tes2.strTo(obj_I, "type_I") + ", " +
                            Tes2.strTo(classMainArgument, "typeMainArgument") +
                            " the main argument of standard generic type should be standard type," +
                            " but not array or generic");

                                                            //It could be a 2 arguments generic (Dictionary or
                                                            //      KeyValuePair).
                    if (
                        boolIsStandardGeneric &&
                        ((classObj.getSimpleName().equals(Tes2.strGENERIC_DICTIONARY_TYPE)) ||
                            (classObj.getSimpleName().equals(Tes2.strGENERIC_KEYVALUEPAIR_TYPE)))
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
                            Tools.subAbort(Tes2.strTo(obj_I, "type_I") + ", " +
                                Tes2.strTo(arrClassArgument[0], "arrtypeArgument[0]") +
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
            strPrefix = strPrefix + Tes2.strPrefix(Tes2.classOfArray(classObj));
        }
        else if (
            Tools.boolIsGenericType(classObj)
            )
        {
                                                            //dic, kvp, lst, queue or stack
            strPrefix = Tes2.arrstrGENERIC_PREFIX[
                Arrays.binarySearch(Tes2.arrstrGENERIC_TYPE, classObj.getSimpleName())];

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
                    strPrefix = strPrefix + Tes2.strPrefix(arrClassArgument[arrClassArgument.length - 1]);
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
            int intPrimitive = Arrays.binarySearch(Tes2.arrstrPRIMITIVE_TYPE, classObj.getSimpleName());
            if (
                                                            //Is standard primitive type
                intPrimitive >= 0
                )
            {
                strPrefix = Tes2.arrstrPRIMITIVE_PREFIX[intPrimitive];
            }
            else
            {
                int intSystem = Arrays.binarySearch(Tes2.arrstrSYSTEM_TYPE, classObj.getSimpleName());
                if (
                                                                //Is standard system type
                    intSystem >= 0
                    )
                {
                    strPrefix = Tes2.arrstrSYSTEM_PREFIX[intSystem];
                }
                else
                {
                    int intBoxing = Arrays.binarySearch(Tes2.arrstrBOXING_TYPE, classObj.getSimpleName());
                    if (
                                                            //Is boxing class.
                        intBoxing >= 0
                        )
                    {
                        strPrefix = Tes2.arrstrBOXING_PREFIX[intBoxing];
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
                            Tools.subAbort(Tes2.strTo(classObj, "classObj") +
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
                            ) {
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
                Tes2.boolMultiDimensionArrayIsStandard(obj_I)
                ))
            {
                Tools.subAbort(Tes2.strTo(obj_I, "obj_I") + " is not a standard multidimensional array. It's not in" +
                    " format [],[,] or [,,].");
            }
            int intRank = Tools.intArrRank(obj_I.getClass());

            /*CASE*/
            if (
                intRank == 1
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes2.strArrSize(obj_I);
            }
            else if (
                intRank == 2
                )
            {
                                                            //Generate [l,m].
                strCollectionSize = Tes2.strArr2Size(obj_I);
            }
            else
            {
                                                                //Rank 3
                                                                //Generate [l,m,n].
                strCollectionSize = Tes2.strArr3Size(obj_I);
            }
            /*END-CASE*/
        }
        else if (
            Tools.boolIsGenericType(classCollection)
            )
        {
            /*CASE*/
            if (
                classCollection.getSimpleName().equals(Tes2.strGENERIC_DICTIONARY_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes2.strDicSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes2.strGENERIC_KEYVALUEPAIR_TYPE)
                )
            {
                                                            //This is not a collection.
                strCollectionSize = "";
            }
            else if (
                classCollection.getSimpleName().equals(Tes2.strGENERIC_LIST_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes2.strLstSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes2.strGENERIC_QUEUE_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes2.strQueueSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes2.strGENERIC_STACK_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes2.strStackSize(obj_I);
            }
            else
            {
                if (
                    true
                    )
                    Tools.subAbort(Tes2.strTo(obj_I.getClass(), "obj_I.getClass()") +
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
                (Arrays.binarySearch(Tes2.arrstrPRIMITIVE_TYPE, classElement.getSimpleName()) >= 0)
                )
                Tools.subAbort(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "Tes2.arrstrPRIMITIVE_TYPE") + ", " +
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
                (Arrays.binarySearch(Tes2.arrstrPRIMITIVE_TYPE, typeElement.getSimpleName()) >= 0)
                )
                Tools.subAbort(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                    Tes2.strTo(typeObj, "obj_I.GetType") +
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
    //------------------------------------------------------------------------------------------------------------------
    private static String strLstSize(                       //Get [l].

                                                            //str, [l].

        Object obj_I
        )
    {
        //TODO: GetGenericArguments is commented in this and subsequent methods.
        String strCount;
        if (obj_I instanceof LinkedList)
        {
            strCount = Integer.toString(((LinkedList)obj_I).size());
        }
        else
        {
//            Class classObj = obj_I.getClass();
//            if (
//            Tools.boolIsGenericType(classObj)
//            )
//        {
//                                                            dic, kvp, lst, queue or stack
//            strPrefix = Tes2.arrstrGENERIC_PREFIX[
//                Arrays.binarySearch(Tes2.arrstrGENERIC_TYPE, classObj.getSimpleName())];
//
//            if (
//                                                            The generic arguments of the collection can be obtained.
//                Tools.boolGenericArgumentsPossible(obj_I)
//                )
//            {
//                Class[] arrClassArgument = Tools.arrclassGetGenericArguments(obj_I);
//
//                if (
//                                                            Generic arguments were obtained.
//                    arrClassArgument.length > 0
//                    )
//                {
//                    strPrefix = strPrefix + Tes2.strPrefix(arrClassArgument[arrClassArgument.length - 1]);
//                }
//                else
//                {
//                    strPrefix = strPrefix + "EMPTY_COLLECTION";
//                }
//            }
//            else
//            {
//                strPrefix = strPrefix + "EMPTY_COLLECTION";
//            }
//        }
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[0];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes2.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "Tes2.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes2.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrSYSTEM_TYPE, "Tes2.arrstrSYSTEM_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

            //Can note get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strQueueSize(                     //Get [l].

                                                            //str, [l].
        Object obj_I
        )
    {
        String strCount;
        if (obj_I instanceof Queue)
        {
            strCount = Integer.toString(((Queue)obj_I).size());
        }
        else
        {
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[0];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes2.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "Tes2.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes2.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrSYSTEM_TYPE, "Tes2.arrstrSYSTEM_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

            //Can note get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strStackSize(                     //Get [l].

                                                            //str, [l].

        Object obj_I
        )
    {
        String strCount;
        if (
            obj_I instanceof Stack
            )
        {
            strCount = Integer.toString(((Stack)obj_I).size());
        }
        else
        {
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[0];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes2.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "Tes2.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes2.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrSYSTEM_TYPE, "Tes2.arrstrSYSTEM_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

            //Can note get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strDicSize(                       //Get [l].

                                                            //str, [l].

        Object obj_I
        )
    {
        String strCount;
        if (
            obj_I instanceof LinkedHashMap
            )
        {
            strCount = Integer.toString(((LinkedHashMap)obj_I).size());
        }
        else
        {
            //TODO: GetGenericArguments cannot be done.
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[1];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes2.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrPRIMITIVE_TYPE, "Tes2.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes2.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes2.strTo(Tes2.arrstrSYSTEM_TYPE, "Tes2.arrstrSYSTEM_TYPE") + ", " +
//                    Tes2.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

                                                            //Can not get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }
    /*END-TASK*/

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatBclass(        //Analize and format bclass (or subclass of bclass).
                                                            //A bclass object should be display only once per run.
                                                            //str, bclass formated to display.

                                                            //Bclass to be analized and format
        BclassBaseClassAbstract bclass_I,
                                                            //SHORT or FULL
        TestoptionEnum testoptionOption_I
        )
    {
        String strAnalizeAndFormatBclass;

                                                            //Se verifica si es null o no
        if (
            bclass_I == null
            )
        {
            if (
                testoptionOption_I == TestoptionEnum.SHORT
                )
            {
                strAnalizeAndFormatBclass = "null";
            }
            else
            {
                //TODO Falta agregar el parámetro para cuando es FULL
                strAnalizeAndFormatBclass = "(null)";
            }
        }
        else {

            /*CASE*/
            if (
                                                            //Es un bclass DUMMY
                bclass_I.boolIsDummy()
                )
            {
                                                            //Include only objId + DUMMY
                strAnalizeAndFormatBclass = Tes2.strGetObjId(bclass_I) + "[DUMMY]";
            }
            else if (
                                                            //Was processed before
                Tes2.lstobjPreviouslyProcessed.contains(bclass_I)
                )
            {
                                                            //Include only objId
                strAnalizeAndFormatBclass = Tes2.strGetObjId(bclass_I) + "|look object up|";
            }
            else
            {
                                                            //Register as processed
                Tes2.lstobjPreviouslyProcessed.add(bclass_I);

                if (
                    testoptionOption_I == TestoptionEnum.SHORT
                    )
                {
                    strAnalizeAndFormatBclass = bclass_I.strTo(TestoptionEnum.SHORT);
                }
                else
                {
                    strAnalizeAndFormatBclass = bclass_I.strTo();
                }
            }
            /*END-CASE*/
        }

        return strAnalizeAndFormatBclass;
    }

    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    private static String strBboxPrefix(
        Class class_I
        )
    {
        if (
            class_I == null
            )
        Tools.subAbort(Tes2.strTo(class_I, "class_I") + " can not be null.");

        if (!(
            BboxBaseBoxingAbstract.class.isAssignableFrom(class_I)
            ))
        Tools.subAbort(Tes2.strTo(class_I, "class_I") + " is not a BboxBaseBox. Only boxing classes are allowed.");

        String strBboxBaseBoxingPrefix;
        /*CASE*/
        if (
            Oint.class.isAssignableFrom(class_I)
            )
        {
            strBboxBaseBoxingPrefix = "int";
        }
        else if (
            Olong.class.isAssignableFrom(class_I)
            )
        {
            strBboxBaseBoxingPrefix = "long";
        }
        else if (
            Obool.class.isAssignableFrom(class_I)
            )
        {
            strBboxBaseBoxingPrefix = "bool";
        }
        else if (
            Ochar.class.isAssignableFrom(class_I)
            )
        {
            strBboxBaseBoxingPrefix = "char";
        }
        else if (
            Onum.class.isAssignableFrom(class_I)
            )
        {
            strBboxBaseBoxingPrefix = "num";
        }
        else
        {
            Tools.subAbort(Tes2.strTo(class_I, "class_I") +
                " SOMETHING is WRONG!!! a branch in previous case is missing");

            strBboxBaseBoxingPrefix = null;
        }
        /*END-CASE*/

        return strBboxBaseBoxingPrefix;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static Class classOfArray(                      //Get the class of the elements of an array. For example,
                                                            //      get int from int[], String from String[][], etc.
                                                            // This method will work in similar way to C#'s
                                                            //      type.GetElementType()
                                                            //Java's method class.getComponentType() will be used,
                                                            //      however, it works differently than C#'s
                                                            //      type.GetElementType(), because in Java, due to the
                                                            //      fact that a multidimensional array is
                                                            //      technically, which means that the elementType of a
                                                            //      3D array is actually a 2D array. Examples:
                                                            //Note that int[].getComponentType returns class int;
                                                            //      int[][].getComponentType returns class int[];
                                                            //      int[][][].getComponentType return class int[][].

                                                            //The class of an array from which the element type should
                                                            //      be extracted.

        Class class_I
        )
    {
        Class classOfArray;
        if (
            class_I == null
            )
        {
            Tools.subAbort(Tes2.strTo(class_I, "class_I") + " should not be null.");
            classOfArray = null;
        }
        if (!(
            class_I.isArray()
            ))
        {
            Tools.subAbort(Tes2.strTo(class_I, "class_I") + " is not the class of an array.");
            classOfArray = null;
        }
        /*CASE*/
        if (
            int[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            int[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            int[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            long[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            long[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            long[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            boolean[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            boolean[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            boolean[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            char[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            char[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            char[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            double[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            double[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            double[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            Object[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            Object[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            Object[].class.isAssignableFrom(class_I)
        )
        {
            classOfArray = class_I.getComponentType();
        }
        else
        {
                Tools.subAbort(Tes2.strTo(class_I, "class_I") +
                    " is an array but is not of rank 1,2 or 3. It is not [], [,], or [,,]");
            classOfArray = null;
        }
        /*END-CASE*/
        return classOfArray;
    }

    //==================================================================================================================
    /*TASK Tools.boolMultiDimensionArrayIsStandard. Check if multidimension array is standard according to its size*/
    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolMultiDimensionArrayIsStandard(
                                                            //Check if the incoming array is standard for a
                                                            //      multidimensional arr. This means that even though,
                                                            //      technically, in Java, multidimensional arrays are
                                                            //      arrays of arrays, standard  arrays must be 2D o 3D.
                                                            //      This means that, an array like this cannot exist:
                                                            //[
                                                            //  arr[0] = elem1, elem2, elem3
                                                            //  arr[1] = elem1, elem2
                                                            //  arr[2] = elem1
                                                            //]
                                                            //It will also be checked that the arrays inside the arrays
                                                            //       (inner arrays) are not nulls. For example an
                                                            //        invalid 3D array:
                                                            //[
                                                            //   arr3d[][][] = new arr[2][1][1]
                                                            //   arr3d[0] = new arr[1][1]
                                                            //   arr3d[1] = null
                                                            //]
                                                            //Instead, the arrays must be like this (2D):
                                                            //[
                                                            //   arr[0] = elem1, elem2, elem3
                                                            //   arr[1] = elem1, elem2, elem3
                                                            //   arr[2] = elem1, elem2, elem3
                                                            //]
                                                            //In context of C#, the permitted multidimensional arrays
                                                            //      are: arr[], arr[,], and arr[,,].

                                                            //Note that in the case in which it's decided if the obj is
                                                            //      an instance of int[], int[][], ... Object[], etc.,
                                                            //      the branch for "instanceof Object[][][]" is before
                                                            //      the bracnh for "instanceof Object[][]" and the
                                                            //      branch for "instanceof Object[]". This is because an
                                                            //      object which is instanceof Object[][][], will also
                                                            //      be instanceof Object[][] and instanceof Object[], so
                                                            //      this issue is solved by the priority in which the
                                                            //      object tries to enter the branches of the case. So,
                                                            //      if an object is instanceof Object[][][], it will not
                                                            //      try to enter the instanceof Object[][] and
                                                            //      instanceof Object[] branches.

                                                            //Object to be analyzed. Array.
        Object obj_I
    )
    {
        boolean boolMultiDimensionArrayIsStandard;
        if (
            obj_I == null
            )
        {
            Tools.subAbort(Tes2.strTo(obj_I, "obj_I") + " can not be null");
            boolMultiDimensionArrayIsStandard = false;
        }


        if (!(
            obj_I.getClass().isArray()
        ))
        {
            Tools.subAbort(Tes2.strTo(obj_I.getClass(), "obj_I.getClass()") + " is not the class of an array.");
            boolMultiDimensionArrayIsStandard = false;
        }
        else
        {
            /*CASE*/
            if (
                obj_I instanceof int[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof int[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr2DIsStandard((int[][]) obj_I);
            }
            else if (
                obj_I instanceof int[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr3DIsStandard((int[][][]) obj_I);
            }
            else if (
                obj_I instanceof long[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof long[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr2DIsStandard((long[][]) obj_I);
            }
            else if (
                obj_I instanceof long[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr3DIsStandard((long[][][]) obj_I);
            }
            else if (
                obj_I instanceof boolean[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof boolean[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr2DIsStandard((boolean[][]) obj_I);
            }
            else if (
                obj_I instanceof boolean[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr3DIsStandard((boolean[][][]) obj_I);
            }
            else if (
                obj_I instanceof char[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof char[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr2DIsStandard((char[][]) obj_I);
            }
            else if (
                obj_I instanceof char[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr3DIsStandard((char[][][]) obj_I);
            }
            else if (
                obj_I instanceof double[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof double[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr2DIsStandard((double[][]) obj_I);
            }
            else if (
                obj_I instanceof double[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr3DIsStandard((double[][][]) obj_I);
            }
            else if (
                obj_I instanceof Object [][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr3DIsStandard((Object[][][])obj_I);
            }
            else if (
                obj_I instanceof Object [][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes2.boolArr2DIsStandard((Object[][])obj_I);
            }
            else if (
                obj_I instanceof Object[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else
            {
                Tools.subAbort(Tes2.strTo(obj_I, "obj_I") +
                    " is an array but is not of rank 1,2 or 3. It is not [], [,], or [,,]");
                boolMultiDimensionArrayIsStandard = false;
            }
            /*END-CASE*/
        }

        return boolMultiDimensionArrayIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of int's is standard.
        int [][] arr2int
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2int.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2int[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2int[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2int.length) ||
                (arr2int[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2int[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2int.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of long's is standard.
        long [][] arr2long
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2long.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2long[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2long[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2long.length) ||
                (arr2long[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2long[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2long.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of boolean's is standard.
        boolean [][] arr2boolean
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2boolean.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2boolean[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2boolean[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2boolean.length) ||
                (arr2boolean[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2boolean[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2boolean.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of char's is standard.
        char [][] arr2char
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2char.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2char[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2char[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2char.length) ||
                (arr2char[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2char[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2char.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of double's is standard.
        double [][] arr2num
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2num.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2num[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2num[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2num.length) ||
                (arr2num[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2num[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2num.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of Object's is standard.
        Object [][] arr2Object
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2Object.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2Object[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2Object[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2Object.length) ||
                (arr2Object[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2Object[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2Object.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of int's is standard.
        int [][][] arr3int
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3int.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3int[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3int[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3int[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3int[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3int[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3int.length) ||
                (arr3int[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3int[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3int[intI].length) ||
                    (arr3int[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3int[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3int[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3int.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of long's is standard.
        long [][][] arr3long
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3long.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3long[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3long[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3long[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3long[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3long[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3long.length) ||
                (arr3long[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3long[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3long[intI].length) ||
                    (arr3long[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3long[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3long[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3long.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of boolean's is standard.
        boolean [][][] arr3bool
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3bool.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3bool[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3bool[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3bool[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3bool[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3bool[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3bool.length) ||
                (arr3bool[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3bool[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3bool[intI].length) ||
                    (arr3bool[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3bool[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3bool[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3bool.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of char's is standard.
        char [][][] arr3char
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3char.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3char[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3char[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3char[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3char[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3char[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3char.length) ||
                (arr3char[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3char[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3char[intI].length) ||
                    (arr3char[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3char[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3char[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3char.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of double's is standard.
        double [][][] arr3num
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3num.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3num[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3num[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3num[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3num[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3num[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3num.length) ||
                (arr3num[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3num[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3num[intI].length) ||
                    (arr3num[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3num[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3num[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3num.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of Object's is standard.
        Object [][][] arr3obj
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3obj.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3obj[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3obj[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3obj[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3obj[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3obj[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3obj.length) ||
                (arr3obj[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3obj[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3obj[intI].length) ||
                    (arr3obj[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3obj[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3obj[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3obj.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Test.Trace Support to implement a trace*/
    //------------------------------------------------------------------------------------------------------------------

                                                            //Implementación de apoyos para poder efectuar un Trece en
                                                            //      en una apliación.
                                                            //¿Cómo?.
                                                            //En el código "driver" para ejecutar la prueba, en
                                                            //      PruebaFase2.cs, llamar al método:
                                                            //Test.subSetLog(sysswLog);
                                                            //Dentro del código que se desea aplicar el trace en los
                                                            //      puntos que se crea conveniente, añadir:
                                                            //Test.subTrace(true, strLabel, intNivel, String a trace);
                                                            //Imprimie el log que contendrá el trace y otra información
                                                            //      de la prueba

    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

                                                            //Log para trace.
                                                            //Se asigna un syssw con SubSetLog(sysswLog).
    private static PrintWriter sysswLogTrace;

                                                            //Cada Trace que se genere tendra un número único 1, 2, 3,
                                                            //      etc. (esto es, su secuencia).
                                                            //Antes de generar un nuevo trace se debe incrementar.
    private static int inTraceSequence;

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsSubTrace(   //Initialiaze trace state.
        )
    {
                                                            //Aún no hay log para trace definido.
        Tes2.sysswLogTrace = null;

                                                            //Inicia la cuenta de Trace que se genera.
        Tes2.inTraceSequence = 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subSetLog(                       //Asigna un log para el trace (seguira en off).

                                                            //Log para trace.
        PrintWriter sysswLogTrace_T
        )
    {
        Tes2.sysswLogTrace = sysswLogTrace_T;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subTrace(                        //Genera un trace a writeline.

                                                            //true, se desea generar el trace.
                                                            //false, No se genera el trace.
                                                            //Se incluye este parámetro para sin tener que eliminar la
                                                            //      la ejecución del trace poder activarlo/desactivarlo.
        boolean boolIsTraceOn_I,
                                                            //Etiqueta para identificar el registro del trace en la
                                                            //      impresión. Cada instrucción trace que se agregue al
                                                            //      código debe tener una etiqueta distinta.
        String strLabel_I,
                                                            //Información a incluir en el trace, esta información se le
                                                            //      da forma similar a los strTo.
        String strInfoTrace_I
        )
    {
        if (
            Tes2.sysswLogTrace == null
            )
            Tools.subAbort(Tes2.strTo(sysswLogTrace, "sysswLogTrace") + " should be created and asigned");

                                                            //Solo se procesa el trace si esta en ON.
        if (
            boolIsTraceOn_I
            )
        {
                                                            //Avanza una secuencia (esta es la secuencia única de este
                                                            //      trace).
            Tes2.inTraceSequence = Tes2.inTraceSequence + 1;

                                                            //Produce trace.
            Tes2.sysswLogTrace.println(">>> " + Tes2.inTraceSequence + " <<< " + strLabel_I);
            Tes2.sysswLogTrace.println(strInfoTrace_I);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //==============================================================================================================
    /*TASK Test.Abort Support for testing subAbort*/

                                                            //Implementación de apoyos para poder efectuar pruebas de
                                                            //      subAbort y regitrar su información en un log.
                                                            //¿Cómo?.
                                                            //En el código "driver" para ejecutar la prueba (ej. en
                                                            //      Test Sys01.cs), llamar al método:
                                                            //Test.subSetTestAbort(); o.
                                                            //Test.subResetTestAbort(); o.

    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

                                                            //Indicador de se desea test.
    public static boolean boolIsTestAbortOn;

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsTestAbort(   //Intialize test state.
        )
    {
        Tes2.boolIsTestAbortOn = false;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subSetTestAbort(                 //Marca que desea test.
        )
    {
        Tes2.boolIsTestAbortOn = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subResetTestAbort(               //Marca que desea concluir test.
        )
    {
        Tes2.boolIsTestAbortOn = false;
    }
    /*END-TASK*/
 }
/*END-TASK*/
