/*TASK Test Support for Testing*/
package TowaInfrastructure;


import java.util.*;

//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: 13-Mayo-2011.
															//PURPOSE:
															//Implementación de clase estática para facilitar el
															//		Testing

//=====================================================================================================================
public final class Test 									//Clase que incuye métodos estáticos (funciones o
                                                      		//      subrutinas) de uso compartido en todos los sistemas.
{
//	//-----------------------------------------------------------------------------------------------------------------
//	static                                     				//Prepara las constantes para poder utilizarlas.
//	                                                        //CADA VEZ QUE SE AÑADAN CONSTANTES QUE REQUIERAN SER
//	                                                        //      INICIALIZADAS, SE AÑADE LA LLAMADA A OTRO MÉTODO.
//    {
//        Test.subPrepareConstantsForStrTo(out Test.arrcharKEYBOARD, out Test.arrcharNONVISIBLE,
//            out Test.arrstrDESCRIPTION_NONVISIBLE);
//        Test.subPrepareConstantTypes(out Test.arrstrPRIMITIVE_TYPE, out Test.arrstrPRIMITIVE_PREFIX,
//            out Test.arrstrSYSTEM_TYPE, out Test.arrstrSYSTEM_PREFIX, out Test.arrstrGENERIC_TYPE,
//            out Test.arrstrGENERIC_PREFIX);
//        Test.subPrepareConstantsToBlockFormat();
//        Test.subPrepareConstantsSubTrace();
//        Test.subPrepareConstantsTestAbort();
//    }
//
//	//=================================================================================================================
//    /*TASK Test.PrepareStrTo Constants and initializer for strTo*/
//    //-----------------------------------------------------------------------------------------------------------------
//                                                      		//Set of methods strTo to analyse and format:
//                                                      		//a) Objects: bclass, btuple & enum.
//                                                      		//b) System objects: sysfile, sysdir, syssr & syssw.
//                                                      		//c) Primitives: int, long, num, char & bool.
//                                                      		//d) Simple objects like: str, date, time, ts & type.
//
//    //-----------------------------------------------------------------------------------------------------------------
//    /*CONSTANTS*/
//
//                                                      		//Si un String excede esta longitud, se muestra la longitud
//                                                      		//      ejemplo "abd def.... xyz"<88>.
//	private final static int intLONG_STRING = 50;
//
//                                                      		//In methods strTo, an Item/Row/Matrix of this characters
//                                                      		//      size will be include in one long line.
//    private final static int intLONG_ITEM_ROW_MATRIX = 40;
//
//                                                      		//This will be the maximun space reseved for key when strTo
//                                                      		//      display a dictionary, if we have longhest key the
//                                                      		//      content will not be aligned.
//    private final static int intKEY_LEN_MAX = 50;
//
//                                                      		//Caracter que será usado como substituto cuando un caracter
//                                                      		//      no sea "visible".
//    private final static char charSUBSTITUTE_NONVISIBLE = '^';
//
//                                                      		//Se tiene 4 posible tipos de caracteres, ver charType.
//
//                                                      		//El siguiente String son caracteres que se pueden
//                                                      		//      introducir por el teclaro.
//                                                      		//charType.KEBOARD.
//    private final static String strCHAR_KEYBOARD =
//                                                          	//El espacio en blanco es un caracter que se puede teclear.
//            " " +
//                                                          	//Caracteres normales.
//            "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" +
//                                                          	//Caracteres acentuados.
//            "ÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÂÊÎÔÛ" + "áéíóúàèìòùäëïöüâêîôû" + "Ññ" +
//                                                          	//Otras consonantes acentuadas.
//            "ŔÝŚĹŹĆŃŸŴŶŜĜĤĴĈ" + "ŕýśĺźćńÿŵŷŝĝĥĵĉ" +
//                                                          	//Acentos solos.
//            "´`¨^" +
//
//                                                          	//Caracteres especiales, que aparecen en teclado de Mac
//                                                          	//      (Spanish - ISO).
//
//                                                          	//Teclas de números.
//            "ºª" + "\\" + "!|" + "\"" + "@" + "·#" + "$" + "%" + "&¬" + "/" + "(" + ")" + "=" + "'?" + "¡¿" +
//                                                          	//Teclas QW.....
//            "€[*+]" +
//                                                          	//Teclas AS.....
//            "{çÇ}" +
//                                                          	//Teclas ZX.....
//            "<>,;.:-_" +
//                                                          	//Otras teclas que estan en DELL y no en la MAC
//            "~";
//
//    														//Los caracteres anteriores en un arreglo ordenado.
//    private static /*readonly*/ char[] arrcharKEYBOARD;
//
//                                                          	//El siguiente arreglo son tuplos con info de caracteres
//                                                          	//      que no se pueden desplegar (distorsionan la imagen
//                                                          	//      en pantalla y/o archivo de texto).
//                                                          	//charType.NONVISIBLE_WITH_DESCRIPTION.
//                                                          	//Los tuplos son: <caracter, descripción>.
//                                                          	//Estos caracteres no deben existir en arrcharKEYBOARD.
//    private static /*readonly*/ T2charDescriptionTuple[] arrt2charNONVISIBLE_WITH_DESCRIPTION =
//    {
//        new T2charDescriptionTuple('\0', "\\0 Zero"),
//        new T2charDescriptionTuple('\007', "\\a Bell (alert)"),
//        new T2charDescriptionTuple('\b', "\\b Backspace"),
//        new T2charDescriptionTuple('\f', "\\f Formfeed"),
//        new T2charDescriptionTuple('\n', "\\n New Line"),
//        new T2charDescriptionTuple('\r', "\\r Carriage Return"),
//        new T2charDescriptionTuple('\t', "\\t Horizontal Tab"),
//        new T2charDescriptionTuple('\u000B', "\\v Vertical Tab"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 11), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 12), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 13), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 14), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 40), "similar to \\n New Line"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 41), "similar to \\r Carriage Return"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 42), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 43), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 44), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 45), "\'\' empty character"),
//        new T2charDescriptionTuple(Convert.ToChar(128 * 64 + 46), "\'\' empty character"),
//    };
//
//
//                                                          	//Con el arreglo anterior se generan los siguientes 2
//        													//      arreglos y se ordenan por el primero.
//    private static /*readonly*/ char[] arrcharNONVISIBLE;
//    private static /*readonly*/ String[] arrstrDESCRIPTION_NONVISIBLE;
//
//                                                          	//ESTA PENDIENTE ANALIZAR QUE CARACTERES DE LOS 256x256 QUE
//                                                          	//      SON POSIBLES, SON VISIBLES.
//
//                                                          	//Rangos de otros caracteres que no son visibles los cuales
//                                                          	//      no tienen descripción como los de arriba.
//                                                          	//charType.NONVISIBLE_WITHOUT_DESCRIPTION.
//                                                          	//Los rangos deben estar en orden ascendente, no traslaparse
//                                                          	//      no estar en arrcharKEYBOARD ni en
//                                                          	//      arrcharNONVISIBLE_WITH_DESCRIPTION.
//    private static int[][] arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION =
//    {
//        { 1, 6 },
//        { 14, 31 },
//        { 127,  128 + 31 },
//        { 128 + 45, 128 + 45 },
//        { 128 * 5 + 94, 128 * 5 + 94 },
//                                                          	//Distorcionan el despliegue en forma extraña, parece que
//                                                          	//      tiene efecto sobre lo que se desplego ANTES.
//        { 128 * 5 + 101, 128 * 5 + 101 }, { 128 * 5 + 103, 128 * 5 + 104 },
//        { 128 * 6, 128 * 6 + 17 }, { 128 * 6 + 19, 128 * 6 + 78 }, { 128 * 6 + 80, 128 * 6 + 111 },
//        { 128 * 9 + 3, 128 * 9 + 6 },
//        { 128 * 11 + 62, 128 * 11 + 62 }, { 128 * 11 + 64, 128 * 11 + 64 }, { 128 * 11 + 67, 128 * 11 + 67 },
//        { 128 * 11 + 70, 128 * 11 + 70 }, { 128 * 11 + 80, 128 * 11 + 106 }, { 128 * 11 + 112, 128 * 11 + 116 },
//        { 128 * 12, 128 * 12 + 3 }, { 128 * 12 + 11, 128 * 12 + 11 }, { 128 * 12 + 13, 128 * 12 + 13 },
//        { 128 * 12 + 27, 128 * 12 + 27 }, { 128 * 12 + 30, 128 * 12 + 31 }, { 128 * 12 + 33, 128 * 12 + 58 },
//        { 128 * 12 + 64, 128 * 12 + 74 }, { 128 * 12 + 109, 128 * 12 + 111 }, { 128 * 12 + 113, 128 * 12 + 127 },
//        { 128 * 13, 128 * 13 + 85 }, { 128 * 13 + 93, 128 * 13 + 93 }, { 128 * 13 + 101, 128 * 13 + 102 },
//        { 128 * 13 + 110, 128 * 13 + 111 }, { 128 * 13 + 122, 128 * 13 + 127 },
//        { 128 * 14, 128 * 14 + 13 }, { 128 * 14 + 16, 128 * 14 + 16 }, { 128 * 14 + 18, 128 * 14 + 47 },
//        { 128 * 14 + 77, 128 * 14 + 109 },
//        { 128 * 15, 128 * 15 + 37 }, { 128 * 15 + 49, 128 * 15 + 49 },
//            												//Non printable character
//        //{ '\uD800', '\uFFFF' },
//    };
//
//    //--------------------------------------------------------------------------------------------------------------
//    /*STATIC VARIABLES*/
//
//                                              				//Object previously processed in other strTo execution.
    public static LinkedList</*NSTD*/Object/*END-NSTD*/> lstobjPreviouslyProcessed;
//
//    //--------------------------------------------------------------------------------------------------------------
//    /*SUPPORT METHODS FOR STATIC CONSTRUCTOR*/
//
//    //--------------------------------------------------------------------------------------------------------------
//    private static void subPrepareConstantsForStrTo(		//Método de apoyo llamado en constructor estático.
//                                                          	//Prepara las constantes para poder utilizarlas.
//                                                          	//1. Prepara arrcharKEYBOARD.
//                                                           	//2. Prepara arrcharNONVISIBLE_WITH_DESCRIPTION y
//                                                          	//      arrstrDESCRIPTION_NONVISIBLE_WITH_DESCRIPTION.
//
//            char[] arrcharKEYBOARD_O,
//            char[] arrcharNONVISIBLE_O,
//            String[] arrstrDESCRIPTION_NONVISIBLE_O
//            )
//    {
//        Test.subInitializeLstobjPreviouslyProcessed();
//
//                                                      		//Prepara arrcharKEYBOARD.
//        arrcharKEYBOARD_O = strCHAR_KEYBOARD.toCharArray();
//        Arrays.sort(arrcharKEYBOARD_O);
//
//                                                      		//Verifica que no haya caracteres duplicados.
//        for (int intI = 1; intI < arrcharKEYBOARD.length; intI = intI + 1)
//        {
//            if (
//                                                          	//Esta duplicado.
//                arrcharKEYBOARD[intI] == arrcharKEYBOARD[intI - 1]
//                )
//                Tools.subAbort(Test.strTo(arrcharKEYBOARD, "arrcharKEYBOARD") + ", " +
//                    Test.strTo(arrcharKEYBOARD[intI], "arrcharKEYBOARD[" + intI + "]") +
//                    " duplicated character");
//        }
//
//            												//Prepara arrcharNONVISIBLE_WITH_DESCRIPTION y
//            												//      arrstrDESCRIPTION_NONVISIBLE_WITH_DESCRIPTION.
//
//        arrcharNONVISIBLE_O = new char[arrt2charNONVISIBLE_WITH_DESCRIPTION.length];
//        arrstrDESCRIPTION_NONVISIBLE_O = new String[arrt2charNONVISIBLE_WITH_DESCRIPTION.length];
//        for (int intI = 0; intI < arrcharNONVISIBLE_O.length; intI = intI + 1)
//        {
//            arrcharNONVISIBLE_O[intI] = arrt2charNONVISIBLE_WITH_DESCRIPTION[intI].charChar;
//            arrstrDESCRIPTION_NONVISIBLE_O[intI] = arrt2charNONVISIBLE_WITH_DESCRIPTION[intI].strDescription;
//        }
//
//        Tools.sort(arrcharNONVISIBLE_O, arrstrDESCRIPTION_NONVISIBLE_O);
//
//            												//Verifica que no haya caracteres duplicados.
//        for (int intI = 1; intI < arrcharNONVISIBLE.length; intI = intI + 1)
//        {
//            if (
//                                                          	//Esta duplicado.
//                arrcharNONVISIBLE[intI] == arrcharNONVISIBLE[intI - 1]
//                )
//                Tools.subAbort(Test.strTo(arrcharNONVISIBLE, "arrcharNONVISIBLE") + ", " +
//                    Test.strTo(arrcharNONVISIBLE[intI], "arrcharNONVISIBLE[" + intI + "]") +
//                    " duplicated character");
//        }
//
//                                                          	//Verifica los rangos.
//        for (int intI = 0; intI < arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length; intI = intI + 1)
//        {
//                                                          	//Verifica que los rangos sean válidos.
//            final int intMaximo = (int)'\uFFFF';//256 * 256 - 1; ???
//            if (
//                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] < 0) ||
//                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] > intMaximo)
//                )
//                Tools.subAbort(
//                    Test.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0],
//                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 0]") +
//                    " should be between  0-" + intMaximo);
//            if (
//                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1] < 0) ||
//                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1] > intMaximo)
//                )
//                Tools.subAbort(
//                    Test.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1],
//                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 1]") +
//                    " should be between  0-" + intMaximo);
//            if (
//                arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] >
//                    arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1]
//                )
//                Tools.subAbort(
//                    Test.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0],
//                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 0]") + " should be <= " +
//                    Test.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1],
//                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 1]"));
//            if (
//            												//No es el primer rango.
//                (intI > 0) &&
//                    										//El rango NO ES ascendente.
//                (arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0] <=
//                    arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI - 1][1])
//                )
//                Tools.subAbort(
//                    Test.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0],
//                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + intI + ", 0]") + " should be > " +
//                    Test.strTo(arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI - 1][1],
//                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[" + (intI - 1) + ", 1]"));
//        }
//
//                                                          	//Existen 4 conjuntos de caracteres.
//
//                                                          	//El conjunto KEYBOARD no debe estar en los conjuntos
//                                                          	//      NONVISIBLE ni en
//                                                          	//      NONVISIBLE_WITHOUT_DESCRIPTION.
//        for (int intI = 0; intI < strCHAR_KEYBOARD.length(); intI = intI + 1)
//        {
//                                                          	//Verifica si esta en NONVISIBLE_WITH_DESCRIPTION.
//            if (
//                Arrays.binarySearch(arrcharNONVISIBLE, strCHAR_KEYBOARD.charAt(intI)) >= 0
//                )
//                Tools.subAbort(Test.strTo(strCHAR_KEYBOARD.charAt(intI), "strCHAR_KEYBOARD[" + intI + "]") +
//                    " is in " + Test.strTo(arrcharNONVISIBLE, "arrcharNONVISIBLE"));
//                                                          	//Verifica si esta en NONVISIBLE_WITHOUT_DESCRIPTION.
//            if (
//                Test.boolIsNonVisibleWithoutDescription(strCHAR_KEYBOARD.charAt(intI))
//                )
//                Tools.subAbort(Test.strTo(strCHAR_KEYBOARD.charAt(intI), "strCHAR_KEYBOARD[" + intI + "]") +
//                    " is in" +
//                    Test.strTo(Test.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION,
//                        "arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION"));
//        }
//
//                                                          	//El conjunto NONVISIBLE no debe estar en el conjunto
//                                                          	//      NONVISIBLE_WITHOUT_DESCRIPTION.
//        for (int intI = 0; intI < arrcharNONVISIBLE.length; intI = intI + 1)
//        {
//                                                          	//Verifica si esta en NONVISIBLE_WITH_DESCRIPTION.
//            if (
//                Test.boolIsNonVisibleWithoutDescription(arrcharNONVISIBLE_O[intI])
//                )
//                Tools.subAbort(Test.strTo(arrcharNONVISIBLE_O[intI], "arrcharNONVISIBLE_O[" + intI + "]") +
//                    " is in " + Test.strTo(Test.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION,
//                        "Test.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION"));
//        }
//    }
//
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static boolean boolIsNonVisibleWithoutDescription(
//                                                      		//Verifica si un caracter es no visible sin descripción.
//                                                      		//bool, true si es no visible sin descripción.
//
//    														//Caracter que se desea verificar.
//        char charAVerificar_I
//        )
//    {
//                                                      		//Extrae el número del caracter.
//        int intChar = (int)charAVerificar_I;
//
//                                                      		//Busca el rango donde pudiera estar incluido.
//        int intI = 0;
//        /*UNTIL-DO*/
//        while (!(
//                                                      		//Ya no hay rangos.
//            (intI >= arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) ||
//                                                      		//El caracter a verificar puede estar en este rango.
//            (intChar <= arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][1])
//            ))
//        {
//            intI = intI + 1;
//        }
//
//        return (
//                                                      		//Esta posicionado en un rango.
//            (intI < arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) &&
//                                                      		//El caracter a verificar esta incluido en este rango.
//            (intChar >= arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0])
//            );
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static TestchartypeEnum testchartypeKeyboardOrEtc(
//                                                      		//Revisa un caracter para determinar su tipo.
//
//                                                      		//Caracter que debrerá ser revisado.
//        char charARevise_I
//        )
//    {
//        TestchartypeEnum testchartypeKeyboardOrEtc;
//
//        /*CASE*/
//        if (
//                                                      		//Es caracter del teclado.
//            Arrays.binarySearch(arrcharKEYBOARD, charARevise_I) >= 0
//            )
//        {
//            testchartypeKeyboardOrEtc = TestchartypeEnum.KEYBOARD;
//        }
//        else if (
//            Arrays.binarySearch(arrcharNONVISIBLE, charARevise_I) >= 0
//            )
//        {
//            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION;
//        }
//        else if (
//            Test.boolIsNonVisibleWithoutDescription(charARevise_I)
//            )
//        {
//            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITHOUT_DESCRIPTION;
//        }
//        else
//        {
//            testchartypeKeyboardOrEtc = TestchartypeEnum.VISIBLE_NONKEYBOARD;
//        }
//        /*END-CASE*/
//
//        return testchartypeKeyboardOrEtc;
//    }
//
//
//    //--------------------------------------------------------------------------------------------------------------
//    /*SHARED METHODS*/
//
//    //--------------------------------------------------------------------------------------------------------------
    public static void subInitializeLstobjPreviouslyProcessed(
                                                      		//Reset list of previously porcessed
        )
    {
        Test.lstobjPreviouslyProcessed = new LinkedList</*NSTD*/Object/*END-NSTD*/>();
    }
//    /*END-TASK*/
//
//  	//==============================================================================================================
//    /*TASK Test.strTo Set of Methods to Display Object Info*/
//    //--------------------------------------------------------------------------------------------------------------
//    public static String strTo(                         	//Prepare for SHORT display.
//                                                        	//The strategy is:
//                                                        	//1. 2 strTo methods (this and next with obj_I parameter)
//                                                        	//      will handle all types except generic containing
//                                                        	//      bclass, btuple and enun.
//                                                        	//2. 2 strTo methods with 3 paramenters will handle 1
//                                                        	//      argument generic containing bclass, btuple and enun.
//                                                        	//3. 2 strTo methods with 4 paramenters will handle
//                                                        	//      dicbclass, dicbtuple and dicenun.
//                                                        	//4. 2 strTo methods with 4 paramenters will handle
//                                                        	//      kvpbclass, kvpbtuple and kvpenun.
//                                                        	//5a. Each one of the pair of strTo methods call a
//                                                        	//      strToSupportXxxxx private method (4 methods) to
//                                                        	//      handle most checks needed, process null values and
//                                                        	//      call strToSharedYyyyy private methods to generate
//                                                        	//      the information requested.
//                                                        	//5b. "primitives" are not easy to process (they require an
//                                                        	//      specific method for each one), to solve this
//                                                        	//      problem, "primitives" will be boxed using Oint,
//                                                        	//      Olong, ... boxing clases, this will be done in the
//                                                        	//      strToSupportAnyType method.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportAnyType method for paramenters
//                                                        	//      description
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//        TestoptionEnum testoptionSHORT_I
//        )
//    {
//        if (
//            testoptionSHORT_I != TestoptionEnum.SHORT
//            )
//            Tools.subAbort(Test.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");
//
//        return Test.strToSupportAnyType(obj_I, testoptionSHORT_I, null);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static String strTo(                         	//Prepare for FULL display.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportAnyType method for paramenters
//                                                        	//      description
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//        String strText_I
//        )
//    {
//        if (
//            strText_I == null
//            )
//            Tools.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");
//
//        return Test.strToSupportAnyType(obj_I, TestoptionEnum.FULL, strText_I);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strToSupportAnyType(          	//Continue preparation for display.
//
//                                                        	//str, info to display
//
//                                                        	//Any standard type except generic types containing bclass,
//                                                        	//      btuple or enum types (those require a transformation
//                                                        	//      before calling strTo method with 3 or 4 paramenters)
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//                                                        	//SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                        	//Variable name of the object.
//        String strText_I
//        )
//    {
//        String strToSupportAnyType;
//        if (
//            obj_I == null
//            )
//        {
//            if (
//                testoptionOption_I == TestoptionEnum.SHORT
//                )
//            {
//                strToSupportAnyType = "null";
//            }
//            else
//            {
//                strToSupportAnyType = strText_I + "(null)";
//            }
//        }
//        else
//        {
//                                                        	//Abort if not a valid type
//            Test.subVerifyAnyType(obj_I);
//
//                                                        //Do the boxing.
//            Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain;
//            Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey;
//            Test.subfunConvertAndBox(objMain, objKey, obj_I);
//
//
//                                                        	//Call required strToSharedYyyyy
//            Type typeObj = obj_I.GetType();
//            /*CASE*/
//            if (
//                typeObj./*END-NSTD*/IsArray/*END-NSTD*/
//                )
//            {
//                                                        	//Is arrobj[], arrobj[,] or arrobj[, ,].
//                                                        	//All contents are boxed primitives, simple and
//                                                        	//      system types, bclass, btuple and enum
//
//                int intRank = typeObj./*NSTD*/GetArrayRank()/*END-NSTD*/;
//                /*CASE*/
//                if (
//                    intRank == 1
//                    )
//                {
//                                                        	//Is arrobj[], call with 3 paramenters
//                    strToSupportAnyType = Test.strFormatArrOrOneArgumentGeneric(
//                        (/*NSTD*/Object[]/*END-NSTD*/)objMain.v, testoptionOption_I, strText_I, obj_I);
//                }
//                else if (
//                    intRank == 2
//                    )
//                {                                                        //Is arrobj[,]
//                    strToSupportAnyType = Test.strFormatArr2Main((/*NSTD*/Object[,]/*END-NSTD*/)objMain.v,
//
//                        testoptionOption_I, strText_I, obj_I);
//                }
//                else
//                {
//                                                        //Is arrobj[, ,]
//                    strToSupportAnyType = Test.strFormatArr3Main((/*NSTD*/Object[, ,]/*END-NSTD*/)objMain.v,
//
//                        testoptionOption_I, strText_I, obj_I);
//                }
//                /*END-CASE*/
//            }
//            else if (
//                typeObj./*END-NSTD*/IsGenericType/*END-NSTD*/
//                )
//            {
//                                                        	//Is 1 or 2 arguments.
//                                                        	//All contents are boxed primitives, simple and
//                                                        	//      system types
//
//                if (
//                                                        	//Is List<Object>, ...
//                    typeObj.Name.EndsWith("`1")
//                    )
//                {
//                                                        	//lstobj, ... were converted to arrobj
//                    strToSupportAnyType = Test.strFormatArrOrOneArgumentGeneric(
//                        (/*NSTD*/Object[]/*END-NSTD*/)objMain.v, testoptionOption_I, strText_I, obj_I);
//                }
//                else
//                {
//                                                        	//Is Dictionary<String, Object> or
//                                                        	//      KeyValuePair<String,_Object>
//
//                    if (
//                        typeObj.Name == Test.strGENERIC_DICTIONARY_TYPE
//                        )
//                    {
//                                                      		//dicobj was convertes to arrstr and arrobj
//                        strToSupportAnyType = Test.strFormatDicMain(
//                            (/*NSTD*/Object[]/*END-NSTD*/)objMain.v, (String[])objKey.v, testoptionOption_I, strText_I,
//                            obj_I);
//                    }
//                    else
//                    {
//                                                      	//kvpobj was convertes to str and obj
//                        strToSupportAnyType = Test.strFormatKvpMain(objMain.v, (String)objKey.v, testoptionOption_I,
//
//                            strText_I, obj_I);
//                    }
//                }
//            }
//            else
//            {
//            												//Is single type
//                strToSupportAnyType = Test.strFormatSingle(objMain.v, testoptionOption_I, strText_I);
//
//            }
//            /*END-CASE*/
//        }
//
//        return strToSupportAnyType;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subVerifyAnyType(               	//Verify it is standard type except a generic type
//                                                        	//      containing bclass, btuple or enum type
//
//                                                        	//Read strToSupportAnyType method for paramenters
//                                                        	//      description
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        Type typeObj = obj_I.GetType();
//        if (
//            !Test.boolIsStandard(typeObj, false)
//            )
//            Tools.subAbort(Test.strTo(typeObj, "obj_I.GetType") + " is not an standard type");
//
//                                                        	//If generic type, other verifications are required
//        if (
//            typeObj./*NSTD*/IsGenericType/*END-NSTD*/
//            )
//        {
//                                                        	//More verifications are needed
//
//                                                        	//Get argument type contained
//            Type[] arrtypeArgument = typeObj./*NSTD*/GetGenericArguments()/*END-NSTD*/;
//            Type typeArgument = arrtypeArgument[arrtypeArgument.Length - 1];
//
//            if (
//                                                        	//Is bclass
//                typeArgument == (BclassBaseClassAbstract) ||
//                    typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
//                                                        	//Is btuple
//                typeArgument == typeof(BtupleBaseTupleAbstract) ||
//                    typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
//                                                        	//Is enum
//                typeArgument == typeof(Enum) || typeArgument.IsSubclassOf(typeof(Enum)) ||
//                                                        	//Is Exception
//                typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
//                )
//                Tools.subAbort(Test.strTo(typeObj, "obj_I.GetType") +
//                    " generic types containing bclass, btuple or enum are not valid in ths method, " +
//                    "a 3 or 4 paramenters strTo method should be called instead");
//        }
//    }
//
//    //--------------------------------------------------------------------------------------------------------------
//    public static String strTo(                         	//Prepare for SHORT display.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportOneArgumentGeneric method for paramenters
//                                                        	//      description
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I,
//        TestoptionEnum testoptionSHORT_I,
//        /*NSTD*/Object/*END-NSTD*/ objOneArgumentGeneric_I
//        )
//    {
//        if (
//            testoptionSHORT_I != TestoptionEnum.SHORT
//            )
//            Tools.subAbort(Test.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");
//
//        return Test.strToSupportOneArgumentGeneric(arrobj_I, testoptionSHORT_I, null, objOneArgumentGeneric_I);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static String strTo(                         	//Prepare for FULL display.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportOneArgumentGeneric method for paramenters
//                                                        	//      description
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I,
//        String strText_I,
//        /*NSTD*/Object/*END-NSTD*/ objOneArgumentGeneric_I
//        )
//    {
//        if (
//            strText_I == null
//            )
//            Tools.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");
//
//        return Test.strToSupportOneArgumentGeneric(arrobj_I, TestoptionEnum.FULL, strText_I,
//            objOneArgumentGeneric_I);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strToSupportOneArgumentGeneric(
//                                                        	//Continue preparation for display.
//
//                                                        	//str, info to display
//
//                                                        	//arrstr, arrbclass, arrbtuple or arrenum.
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I,
//                                                        	//SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                        	//Variable name of the one argument generic.
//        String strText_I,
//                                                        	//lstbclass, lstbtuple or lstenum, queuebclass, ...
//                                                        	//Main should contain str or the type (or subtype)
//                                                        	//      contained in one argument generic.
//        /*NSTD*/Object/*END-NSTD*/ objOneArgumentGeneric_I
//        )
//    {
//        String strToSupportOneArgumentGeneric;
//        if (
//            objOneArgumentGeneric_I == null
//            )
//        {
//            if (
//                testoptionOption_I == TestoptionEnum.SHORT
//                )
//            {
//                strToSupportOneArgumentGeneric = "null";
//            }
//            else
//            {
//                strToSupportOneArgumentGeneric = strText_I + "(null)";
//            }
//        }
//        else
//        {
//                                                        	//Abort if both parameters are not consistent.
//            Test.subVerifyOneArgumentGeneric(arrobj_I, objOneArgumentGeneric_I);
//
//            strToSupportOneArgumentGeneric = Test.strFormatArrOrOneArgumentGeneric(arrobj_I, testoptionOption_I,
//                strText_I, objOneArgumentGeneric_I);
//        }
//
//        return strToSupportOneArgumentGeneric;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subVerifyOneArgumentGeneric(    	//Verify it is a standard one argument generic type
//                                                        	//      containing bclass, btuple or enum.
//
//                                                        	//Read strToSupportOneArgumentGeneric method for paramenters
//                                                        	//      description
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I,
//        /*NSTD*/Object/*END-NSTD*/ objOneArgumentGeneric_I
//        )
//    {
//        Type typeOneArgumentGeneric = objOneArgumentGeneric_I.GetType();
//                                                        	//The generic should be List, ... (any standard 1
//                                                        	//      argument generic type).
//        if (!(
//            typeOneArgumentGeneric./*END-NSTD*/IsGenericType/*END-NSTD*/ &&
//            typeOneArgumentGeneric.Name.EndsWith("`1")
//            ))
//            Tools.subAbort(Test.strTo(typeOneArgumentGeneric, "objOneArgumentGeneric_I.GetType") +
//                " should be standard one argument generic type");
//
//        Type typeArgument = typeOneArgumentGeneric./*NSTD*/GetGenericArguments()/*END-NSTD*/[0];
//        if (!(
//                                                        	//Is bclass
//            typeArgument == typeof(BclassBaseClassAbstract) ||
//                typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
//                                                        	//Is btuple
//            typeArgument == typeof(BtupleBaseTupleAbstract) ||
//                typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
//                                                        	//Is enum
//            typeArgument == typeof(Enum) || typeArgument.IsSubclassOf(typeof(Enum)) ||
//                                                        	//Is Exception
//                typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
//            ))
//            Tools.subAbort(Test.strTo(typeOneArgumentGeneric, "objOneArgumentGeneric_I.GetType") +
//                " should be generic type containing bclass, btuple or enum");
//
//        if (
//            arrobj_I == null
//            )
//            Tools.subAbort(Test.strTo(arrobj_I, "arrobj_I") + " should have a value");
//
//        Type typeElement = arrobj_I.GetType().GetElementType();
//        if (!(
//                                                        	//Array and generic are compatible
//            (typeElement == typeof(String)) || (typeElement == typeArgument)
//            ))
//            Tools.subAbort(Test.strTo(typeElement, "arrobj_I.GetType.GetElementType") + ", " +
//                Test.strTo(typeArgument, "objOneArgumentGeneric_I.GetType.GetGenericArguments[0]") + ", " +
//                " array and collection are not compatible");
//    }
//
//    //--------------------------------------------------------------------------------------------------------------
//    public static String strTo(                         	//Prepare for SHORT display.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportDic method for paramenters description
//        /*NSTD*/Object[]/*END-NSTD*/ arrobjValue_I,
//        String[] arrstrKey_I,
//        TestoptionEnum testoptionSHORT_I,
//        /*NSTD*/Object/*END-NSTD*/ objDicobj_I
//        )
//    {
//        if (
//            testoptionSHORT_I != TestoptionEnum.SHORT
//            )
//            Tools.subAbort(Test.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");
//
//        return Test.strToSupportDic(arrobjValue_I, arrstrKey_I, testoptionSHORT_I, null, objDicobj_I);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static String strTo(                         	//Prepare for FULL display.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportDic method for paramenters description
//        /*NSTD*/Object[]/*END-NSTD*/ arrobjValue_I,
//        String[] arrstrKey_I,
//        String strText_I,
//        /*NSTD*/Object/*END-NSTD*/ objDicobj_I
//        )
//    {
//        if (
//            strText_I == null
//            )
//            Tools.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");
//
//        return Test.strToSupportDic(arrobjValue_I, arrstrKey_I, TestoptionEnum.FULL, strText_I, objDicobj_I);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strToSupportDic(              	//Continue preparation for display.
//
//                                                        	//str, info to display
//
//                                                        	//arrstr, arrbclass, arrbtuple or arrenum
//        /*NSTD*/Object[]/*END-NSTD*/ arrobjValue_I,
//                                                        	//dic.Keys
//        String[] arrstrKey_I,
//                                                        	//SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                        	//Variable name of the dic.
//        String strText_I,
//                                                        	//dicbclass, dicbtuple or dicenum.
//                                                        	//Value should contain str or be the same type (or subtype)
//                                                        	//      contained in dic.
//        /*NSTD*/Object/*END-NSTD*/ objDicobj_I
//        )
//    {
//        String strToSupportDic;
//        if (
//            objDicobj_I == null
//            )
//        {
//            strToSupportDic = "null";
//            if (
//                testoptionOption_I == TestoptionEnum.SHORT
//                )
//            {
//                strToSupportDic = "null";
//            }
//            else
//            {
//                strToSupportDic = strText_I + "(null)";
//            }
//        }
//        else
//        {
//                                                        	//Abort if not a valid dic
//            Test.subVerifyDic(arrstrKey_I, arrobjValue_I, objDicobj_I);
//
//            strToSupportDic = Test.strFormatDicMain(arrobjValue_I, arrstrKey_I, testoptionOption_I, strText_I,
//                objDicobj_I);
//        }
//
//        return strToSupportDic;
//    }
//
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subVerifyDic(                   	//Verify it is a valid dic.
//
//                                                        	//Read strToSupportDic method for paramenters description
//        String[] arrstrKey_I,
//        /*NSTD*/Object[]/*END-NSTD*/ arrobjValue_I,
//        /*NSTD*/Object/*END-NSTD*/ objDicobj_I
//        )
//    {
//        Type typeDicobj = objDicobj_I.GetType();
//        if (
//            typeDicobj.Name != Test.strGENERIC_DICTIONARY_TYPE
//            )
//            Tools.subAbort(Test.strTo(typeDicobj, "objDicobj_I.GetType") + " should be dictionary");
//
//        Type typeArgument = typeDicobj./*NSTD*/GetGenericArguments()/*END-NSTD*/[1];
//        if (!(
//                                                        	//Is bclass
//            typeArgument == typeof(BclassBaseClassAbstract) ||
//                typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
//                                                        	//Is btuple
//            typeArgument == typeof(BtupleBaseTupleAbstract) ||
//                typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
//                                                        	//Is enum
//            typeArgument == typeof(Enum) || typeArgument.IsSubclassOf(typeof(Enum)) ||
//                                                        	//Is Exception
//                typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
//            ))
//            Tools.subAbort(Test.strTo(typeDicobj, "typeDicobj.GetType") +
//                " should be dictionary type containing bclass, btuple or enum");
//
//        if (
//            arrstrKey_I == null
//            )
//            Tools.subAbort(Test.strTo(arrstrKey_I, "arrstrKey_I") + " should have a value");
//
//        if (
//            arrobjValue_I == null
//            )
//            Tools.subAbort(Test.strTo(arrobjValue_I, "arrobjValue_I") + " should have a value");
//
//                                                        	//Array should contains str or the same type of Directory
//        Type typeValueElement = arrobjValue_I.GetType().GetElementType();
//        if (!(
//            (typeValueElement == typeof(String)) || (typeValueElement == typeArgument)
//            ))
//            Tools.subAbort(Test.strTo(arrobjValue_I.GetType(), "arrobjValue_I.GetType") + ", " +
//                Test.strTo(typeDicobj, "objDicobj_I.GetType") + " array and dictionary are not compatible");
//
//        if (
//            arrstrKey_I.Length != arrobjValue_I.Length
//            )
//            Tools.subAbort(Test.strTo(Tes2.strGetObjId(arrstrKey_I), "arrstrKey_I.strGetObjId") + ", " +
//                Test.strTo(Tes2.strGetObjId(arrobjValue_I), "arrobjValue_I.strGetObjId") +
//                " both arrays should be the same size");
//    }
//
//    //--------------------------------------------------------------------------------------------------------------
//    public static String strTo(                         	//Prepare for SHORT display.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportKey method for paramenters description
//        String strKey_I,
//        /*NSTD*/Object/*END-NSTD*/ objValue_I,
//        TestoptionEnum testoptionSHORT_I,
//        /*NSTD*/Object/*END-NSTD*/ objKvpobj_I
//        )
//    {
//        if (
//            testoptionSHORT_I != TestoptionEnum.SHORT
//            )
//            Tools.subAbort(Test.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");
//
//        return Test.strToSupportKvp(strKey_I, objValue_I, testoptionSHORT_I, null, objKvpobj_I);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static String strTo(                         	//Prepare for FULL display.
//
//                                                        	//str, info to display
//
//                                                        	//Read strToSupportKvp method for paramenters description
//        String strKey_I,
//        /*NSTD*/Object/*END-NSTD*/ objValue_I,
//        String strText_I,
//        /*NSTD*/Object/*END-NSTD*/ objKvpobj_I
//        )
//    {
//        if (
//            strText_I == null
//            )
//            Tools.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");
//
//        return Test.strToSupportKvp(strKey_I, objValue_I, TestoptionEnum.FULL, strText_I, objKvpobj_I);
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strToSupportKvp(              	//Continue preparation for display.
//
//                                                        	//str, info to display
//
//                                                        	//kvp.Key
//        String strKey_I,
//                                                        	//kvp.Value (can be null)
//        /*NSTD*/Object/*END-NSTD*/ objValue_I,
//                                                        	//SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                        	//Variable name of the kvp.
//        String strText_I,
//                                                        	//kvpbclass, kvpbtuple or kvpenum.
//        /*NSTD*/Object/*END-NSTD*/ objKvpobj_I
//        )
//    {
//        String strToSupportKvp;
//        if (
//            objKvpobj_I == null
//            )
//        {
//            if (
//                testoptionOption_I == TestoptionEnum.SHORT
//                )
//            {
//                strToSupportKvp = "null";
//            }
//            else
//            {
//                strToSupportKvp = strText_I + "(null)";
//            }
//        }
//        else
//        {
//                                                        	//Abort if not a valid kvp
//            Test.subVerifyKvp(objValue_I, strKey_I, TestoptionEnum.FULL, strText_I, objKvpobj_I);
//
//            strToSupportKvp = Test.strFormatKvpMain(objValue_I, strKey_I, testoptionOption_I, strText_I,
//                objKvpobj_I);
//        }
//
//        return strToSupportKvp;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subVerifyKvp(                   	//Verify it is a valid kvp.
//
//                                                        	//Read strToSupportKvp method for paramenters description
//        /*NSTD*/Object/*END-NSTD*/ objValue_I,
//        String strKey_I,
//        TestoptionEnum testoptionOption_I,
//        String strText_I,
//        /*NSTD*/Object/*END-NSTD*/ objKvpobj_I
//        )
//    {
//        Type typeKvpobj = objKvpobj_I.GetType();
//        if (
//            typeKvpobj.Name != Test.strGENERIC_KEYVALUEPAIR_TYPE
//            )
//            Tools.subAbort(Test.strTo(typeKvpobj, "objKvpobj_I.GetType") + " should be KeyValuePair");
//
//        Type typeArgument = typeKvpobj./*NSTD*/GetGenericArguments/*END-NSTD*/()[1];
//        if (!(
//                                                        	//Is bclass
//            typeArgument == typeof(BclassBaseClassAbstract) ||
//                typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
//                                                        	//Is btuple
//            typeArgument == typeof(BtupleBaseTupleAbstract) ||
//                typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
//                                                        	//Is enum
//            typeArgument == typeof(Enum) || typeArgument.IsSubclassOf(typeof(Enum)) ||
//                                                        	//Is Exception
//                typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
//            ))
//            Tools.subAbort(Test.strTo(typeKvpobj, "typeKvpobj.GetType") +
//                " should be dictionary type containing bclass, btuple or enum");
//
//                                                        	//Value could be null.
//        if (
//                                                        	//Value exists
//            objValue_I != null
//            )
//        {
//            Type typeValue = objValue_I.GetType();
//            if (!(
//                                                        	//Value and KeyValuePair are compatible
//                (typeValue == typeArgument) || typeValue.IsSubclassOf(typeArgument)
//                ))
//                Tools.subAbort(Test.strTo(typeValue, "objValue_I.GetType") + ", " +
//                    Test.strTo(typeKvpobj, "objKvpobj_I.GetType") + " value and KeyValuePair are not compatible");
//        }
//    }
//    /*END-TASK*/
//
//    //==============================================================================================================
//    /*TASK Test.Types Set of Methods to Analize Types*/
//    //--------------------------------------------------------------------------------------------------------------
//    /*CONSTANTS*/
//
//                                                     		//Towa's standard primitives
//    private static /*readonly*/ String[][] arr2strPRIMITIVE_TYPE_AND_PREFIX = {
//                                                      		//TO ADD NEW PRIMARY TYPES:
//                                                      		//a. Add an entry in this array (standard prefix xxxx).
//                                                      		//b. Add a method subfunConvertAndBoxXxxx, similar to
//                                                      		//      subfunConvertAndBoxTs).
//                                                      		//c. Add a method strAnalizeAndFormatXxxx, similar to
//                                                      		//      strAnalizeAndFormatTs).
//                                                      		//d. Add case branch in method
//                                                      		//      subfunConvertAndBoxPrimitive.
//                                                      		//e. Add case branch in methodstrAnalizeAndFormatBbox.
//
//                                                        //The java primitives, when passed as an argument that receives
//                                                        //      an Object, are automatically boxed in their
//                                                        //      corresponding Object (Integer for int, Double for
//                                                        //      double, etc.). Because the methods in strTo declare
//                                                        //      Object parameters, the "primitive" objects must be
//                                                        //      added here, so that when receiving an Integer, it will
//                                                        //      work as if it had received an int.
//                                                        //A way to avoid this would be to do a method overload for each
//                                                        //      kind of primitive for the methods that receive Objects,
//                                                        //      for example, making 5 overloads to the method
//                                                        //      strTo(Object, string) like this:
//                                                        //      strTo(int, string); strTo(long, string);
//                                                        //      strTo(char, string); strTo(double, string);
//                                                        //      strTo(boolean, string).
//                                                        //      However, because this method(strTo) also invokes other
//                                                        //      methods that have Objects as parameters, the number of
//                                                        //      method overloads would be too much. Because of this, the
//                                                        //      structure with the Object parameters will be kept for
//                                                        //      now, but may change in the future.
//        { "int", "int" }, { "long", "long" }, { "boolean", "bool" }, { "char", "char" }, { "double", "num" },
//        { "Integer", "int" }, { "Long", "long" }, { "Boolean", "bool" }, { "Character", "char" }, { "Double", "num" },
//                                                          //C# structures should be handled like primitives
//        { "LocalDateTime", "ts" },
//
//    };
//
//    //      												//Both arrays order by first.
//
//    public static /*readonly*/ String[] arrstrPRIMITIVE_TYPE;
//    public static /*readonly*/ String[] arrstrPRIMITIVE_PREFIX;
//
//                                                            //Towa's standard system types
//    private static /*readonly*/ String[][] arr2strSYSTEM_TYPE_AND_PREFIX = {
//                                                        	//TO ADD NEW SYSTEM TYPES:
//                                                        	//a. Add an entry in this array (standard prefix yyyyy).
//                                                        	//b. Add a method subfunConvertYyyyy, similar to
//                                                        	//      subfunConvertSysdir).
//                                                        	//c. Add a method strAnalizeAndFormatYyyyy, similar to
//                                                        	//      strAnalizeAndFormatSysdir).
//                                                        	//d. Add case branch in method subfunConvertSystemType.
//                                                        	//e. Add case branch in method
//                                                        	//      strAnalizeAndFormatSystemType.
//
//                                                        	//String should be handled like system tyes.
//        { "String", "str" },
//                                                        	//System types
//        { "RuntimeType", "type" }, { "DirectoryInfo", "Sysdir" }, { "FileInfo", "Sysfile" },
//        { "StreamReader", "Syssr" }, { "StreamWriter", "Syssw" },
//        };
//
//
//                                                        	//Both arrays order by first.
//    public static /*readonly*/ String[] arrstrSYSTEM_TYPE;
//    public static /*readonly*/ String[] arrstrSYSTEM_PREFIX;
//
//                                                        	//Towa's standard other types
//    private final static String strGENERIC_LIST_TYPE = "List`1";
//    private final static String strGENERIC_QUEUE_TYPE = "Queue`1";
//    private final static String strGENERIC_STACK_TYPE = "Stack`1";
//    private final static String strGENERIC_DICTIONARY_TYPE = "LinkedHashMap";
//    private final static String strGENERIC_KEYVALUEPAIR_TYPE = "Entry";
//    private static /*readonly*/ String[][] arr2strGENERIC_TYPE_AND_PREFIX = {
//        { strGENERIC_LIST_TYPE, "lst" }, { strGENERIC_QUEUE_TYPE, "queue" }, { strGENERIC_STACK_TYPE, "stack" },
//        { strGENERIC_DICTIONARY_TYPE, "dic" }, { strGENERIC_KEYVALUEPAIR_TYPE, "kvp" },
//        };
//
//                                                        	//Both arrays order by first.
//    public static /*readonly*/ String[] arrstrGENERIC_TYPE;
//    public static /*readonly*/ String[] arrstrGENERIC_PREFIX;
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*SUPPORT METHODS FOR STATIC CONSTRUCTOR*/
//    private static void subPrepareConstantTypes(            //Order and varify constants.
//        /*out*/ String[] arrstrPRIMITIVE_TYPE_O,
//        /*out*/ String[] arrstrPRIMITIVE_PREFIX_O,
//        /*out*/ String[] arrstrSYSTEM_TYPE_O,
//        /*out*/ String[] arrstrSYSTEM_PREFIX_O,
//        /*out*/ String[] arrstrGENERIC_TYPE_O,
//        /*out*/ String[] arrstrGENERIC_PREFIX_O
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandard(                   //Evaluate if type is standard type.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//        boolean boolIsStandard;
//            /*CASE*/
//        if (
//            class_I./*NSTD*/IsArray/*END-NSTD*/
//            )
//        {
//            boolIsStandard = Test.boolIsStandardArray(class_I, boolAbort_I);
//        }
//        else if (
//            class_I./*NSTD*/IsGenericType/*END-NSTD*/
//            )
//        {
//            boolIsStandard = Test.boolIsStandardGeneric(class_I, boolAbort_I);
//        }
//        else
//        {
//            boolIsStandard = Test.boolIsStandardSingle(class_I, boolAbort_I);
//        }
//            /*END-CASE*/
//
//        return boolIsStandard;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardSingle(             //Evaluate if type is standard single type.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//        {
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null.");
//        }
//
//        boolean boolIsStandardSingle;
//        /*CASE*/
//        if (
//            Arrays.binarySearch(Test.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0
//            )
//        {
//            boolIsStandardSingle = Test.boolIsStandardPrimitive(class_I, boolAbort_I);
//        }
//        else if (
//            Arrays.binarySearch(Test.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
//            )
//        {
//            boolIsStandardSingle = Test.boolIsStandardSystem(class_I, boolAbort_I);
//        }
//        else if (
//            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
//            )
//        {
//            boolIsStandardSingle = Test.boolIsStandardBclass(class_I, boolAbort_I);
//        }
//        else if (
//            BtupleBaseTupleAbstract.class.isAssignableFrom(class_I)
//            )
//        {
//            boolIsStandardSingle = Test.boolIsStandardBtuple(class_I, boolAbort_I);
//        }
//        else if (
//            Enum.class.isAssignableFrom(class_I)
//            )
//        {
//            boolIsStandardSingle = Test.boolIsStandardEnum(class_I, boolAbort_I);
//        }
//        else if (
//            Exception.class.isAssignableFrom(class_I)
//            )
//        {
//            boolIsStandardSingle = Test.boolIsStandardException(class_I, boolAbort_I);
//        }
//        else
//        {
//            boolIsStandardSingle = false;
//            if (
//                boolAbort_I
//                )
//                Tools.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
//                    Test.strTo(class_I, "class_I") + " is not an standard primitive type");
//        }
//        /*END-CASE*/
//
//        return boolIsStandardSingle;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardPrimitive(          //Evaluate if type is standard primitive type.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//
//        boolean boolIsStandardPrimitive = (
//                                                            //Is a primitive included in Towa Standard.
//            (Arrays.binarySearch(Test.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0)
//        );
//
//        if (
//            boolAbort_I && !boolIsStandardPrimitive
//            )
//            Tools.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
//                Test.strTo(class_I, "class_I") + " is not an standard primitive type");
//
//        return boolIsStandardPrimitive;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardSystem(             //Evaluate if type is standard system type.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        boolean boolX;
//        boolX = Modifier.isAbstract(class_I.getModifiers());
//
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//        boolean boolIsStandardSimpleOrSystem = (
//            Arrays.binarySearch(Test.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
//        );
//
//        if (
//            boolAbort_I && !boolIsStandardSimpleOrSystem
//            )
//            Tools.subAbort(Test.strTo(Test.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
//                Test.strTo(class_I, "class_I") + " is not an standard system type");
//
//        return boolIsStandardSimpleOrSystem;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardBclass(             //Evaluate if type is standard Bclass.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//        boolean boolIsStandardBclass = (
//            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
//        );
//
//        if (
//            boolAbort_I && !boolIsStandardBclass
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " is not an standard bclass type");
//
//        if (
//                                                            //Is bclass (or subclass of)
//            boolIsStandardBclass
//            )
//        {
//                                                            //It could be abstract or concrete class
//
//            if (
//                Modifier.isAbstract(class_I.getModifiers())
//                )
//            {
//                boolIsStandardBclass = (
//                                                            //The name has the form: Prefix.....Abstract
//                    class_I.getSimpleName().endsWith("Abstract") &&
//                        (class_I.getSimpleName().length()> "Abstract".length()) &&
//                        Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
//                );
//
//                if (
//                    boolAbort_I && !boolIsStandardBclass
//                    )
//                    Tools.subAbort(Test.strTo(class_I, "class_I") +
//                        " an standard abstract bclass type should have the form 'Prefix....Abstract'");
//            }
//            else
//            {
//                                                            //It is concrete class
//
//                String strNameLower = class_I.getSimpleName().toLowerCase();
//                boolIsStandardBclass = (
//                                                            //The name has de form: Prefix.... and do not end with
//                                                            //      Abstract, Enum, Tuple, Interface or Delegate
//                    !(strNameLower.endsWith("abstract") || strNameLower.endsWith("enum") ||
//                        strNameLower.endsWith("tuple") || strNameLower.endsWith("interface") ||
//                        strNameLower.endsWith("delegate")) &&
//                        Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
//                );
//
//                if (
//                    boolAbort_I && boolIsStandardBclass
//                    )
//                    Tools.subAbort(Test.strTo(class_I, "class_I") +
//                        " an standard concrete bclass type should have the form 'Prefix....' and" +
//                        " can not ends with Abstract, Enum, Tuple, Interface or Delegate (upper or lower letters)");
//            }
//        }
//
//        return boolIsStandardBclass;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardBtuple(             //Evaluate if type is standard Btuple.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//        boolean boolIsStandardBtuple = (
//            BtupleBaseTupleAbstract.class.isAssignableFrom(class_I) &&
//                                                            //The name has the form: TNprefix...Tuple (at least 3 char
//                                                            //      before "Tuple".
//                class_I.getSimpleName().endsWith("Tuple") &&
//                    (class_I.getSimpleName().length() >= ("Tuple".length() + 3)) &&
//                    (class_I.getSimpleName().charAt(0) == 'T') && (class_I.getSimpleName().charAt(1) >= '1') &&
//                    (class_I.getSimpleName().charAt(1) <= '9') &&
//                    Tools.boolIsLetterLower(class_I.getSimpleName().charAt(2))
//        );
//
//        if (
//            boolAbort_I && !boolIsStandardBtuple
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") +
//                " is not an standard tuple type, also should have the form 'TNprefix...Tuple'");
//
//        return boolIsStandardBtuple;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardEnum(               //Evaluate if type is standard enum type.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//        boolean boolIsStandardEnum = (
//            Enum.class.isAssignableFrom(class_I) &&
//                                                            //Has the form Prefix...Enum (at least 1 char before
//                                                            //      "Enum").
//            class_I.getName().endsWith("Enum") && (class_I.getName().length() > "Enum".length()) &&
//                Tools.boolIsLetterUpper(class_I.getName().charAt(0))
//        );
//
//        if (
//            boolAbort_I && !boolIsStandardEnum
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") +
//                " is not an standard Enum type, also should have the form 'Prefix...Enum'");
//
//        return boolIsStandardEnum;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardException(          //Evaluate if type is standard Exception type.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//        boolean boolIsStandardException = (
//            Exception.class.isAssignableFrom(class_I)
//        );
//
//        if (
//            boolAbort_I && !boolIsStandardException
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " is not an standard Exception type");
//
//        return boolIsStandardException;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardArray(              //Evaluate if type is standard array.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//    	if (
//    			type_I == null
//                )
//                Tools.subAbort(Test.strTo(type_I, "type_I") + " can not be null");
//    	boolean boolIsStandardArray = (
//                type_I./*NSTD*/isArray()/*END-NSTD*/ &&
//                //                                          Is obj[], obj[,] or obj[, ,]
//                (type_I./*NSTD*/GetArrayRank()/*END-NSTD*/ <= 3)
//                );
//
//        return false;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsStandardGeneric(            //Evaluate if type is standard generic type.
//
//                                                            //bool, is valid.
//
//        /*Type*/Class class_I,
//
//                                                            //true, abort if is not valid.
//        boolean boolAbort_I
//    )
//    {
//        if (
//            class_I == null
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " can not be null");
//
//        boolean boolIsStandardGeneric = (
//            /*NSTD*/Test.boolIsGenericType(class_I)/*END-NSTD*/ &&
//                (Arrays.binarySearch(Test.arrstrGENERIC_TYPE, class_I.getSimpleName()) >= 0)
//        );
//
//        if (
//            boolAbort_I && !boolIsStandardGeneric
//            )
//            Tools.subAbort(Test.strTo(class_I, "class_I") + " is not an standard generic type");
//
//        if (
//            boolIsStandardGeneric
//            )
//        {
//                                                            //The "main argument" of an generic should be standard type,
//                                                            //      but not array or generic
//
//            //TODO: GetGenericArguments can't be done in Java. Find an alternative.
////            Type[] arrtypeArgument = class_I./*NSTD*/GetGenericArguments/*END-NSTD*/();
////            Type typeMainArgument = arrtypeArgument[arrtypeArgument.Length - 1];
////
////            boolIsStandardGeneric = (!(
////                typeMainArgument./*NSTD*/IsArray/*END-NSTD*/ ||
////                    typeMainArgument./*NSTD*/IsGenericType/*END-NSTD*/ ||
////                    !Test.boolIsStandard(typeMainArgument, false)
////            ));
////
////            if (
////                boolAbort_I && !boolIsStandardGeneric
////                )
////                Tools.subAbort(Test.strTo(class_I, "class_I") + ", " +
////                    Test.strTo(typeMainArgument, "typeMainArgument") +
////                    " the main argument of standard generic type should be standard type," +
////                    " but not array or generic");
//
//                                                        //It could be a 2 arguments generic (Dictionary or
//                                                        //      KeyValuePair).
//
//            if (
//                boolIsStandardGeneric && (class_I.getSimpleName().equals(Test.strGENERIC_DICTIONARY_TYPE) ||
//                    class_I.getSimpleName().equals(Test.strGENERIC_KEYVALUEPAIR_TYPE))
//                )
//            {
//                //                                      //It should be dictionary or KeyValuePair, the first
//                //                                      //      argument should be String.
//
////                boolIsStandardGeneric = (
////                    arrtypeArgument[0] == typeof(String)
////                );
////
////                if (
////                    boolAbort_I && !boolIsStandardGeneric
////                    )
////                    Tools.subAbort(Test.strTo(class_I, "class_I") + ", " +
////                        Test.strTo(arrtypeArgument[0], "arrtypeArgument[0]") +
////                        " the first argument of standard 2 arguments generic type should be String");
//            }
//        }
//
//        return boolIsStandardGeneric;
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static boolean boolIsGenericType(                //Evaluate if the type received is generic. (It has at least
//                                                            //      one generic Type Variable.
//        Class class_I
//        )
//    {
//                                                            //Get the Type Variables from the class. Each TypeVariable
//                                                            //      represents a generic variable declared in the class.
//                                                            //      If the class does not use type variable, the array
//                                                            //      array will be of empty (length = 0).
//        /*NSTD*/TypeVariable[] /*END-NSTD*/ arrTypeVariables = class_I.getTypeParameters();
//
//                                                            //The class has at least 1 Type variable.
//        return (
//            arrTypeVariables.length > 0
//        );
//    }
//
//
//
//    /*END-TASK*/
//
//    //==================================================================================================================
//    /*TASK Test.strFormatYyyyy Set of Private Methods to Format Object to Display*/
//    //------------------------------------------------------------------------------------------------------------------
//    private static String strFormatSingle(                  //Format for display.
//
//                                                            //str, formated info
//
//                                                            //Any single type (no arrays or generic types)
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//                                                            //FULL or SHORT display.
//        TestoptionEnum testoptionOption_I,
//                                                            //Variable name of the single object.
//        String strText_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatSingleShort(             //Format for display.
//
//                                                            //str, formated info
//
//                                                            //Read strFormatSingle method for paramenters description
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatSingleFull(              //Format for display.
//
//                                                            //str, formated info
//
//                                                            //Read strFormatSingle method for paramenters description
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//        String strText_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatArrOrOneArgumentGeneric(
//                                                            //Format for display
//                                                            //An arr or One Argument Generic object should be display
//                                                            //      only once per run.
//
//                                                            //str, formated info
//
//                                                            //arr to format
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I,
//                                                            //SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                            //Variable name of arr or one argument generic object
//        String strText_I,
//                                                            //this is needed to get objId.
//        /*NSTD*/Object/*END-NSTD*/ objOriginal_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatArr(                     //Format for display, it could be:
//                                                            //Set of Lines(Items) or One Line(Row).
//
//                                                            //str, formated info.
//
//                                                            //Read strFormatArrOrOneArgumentGeneric method for
//                                                            //      paramenters description
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I,
//        String strText_I,
//        String strObjId_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strListItems(                     //Format an array to a Set of Lines(Items) inside a block.
//                                                            //Example:
//                                                            //[
//                                                            //{
//                                                            //[0] item
//                                                            //...
//                                                            //[x] item
//                                                            //}
//                                                            //]
//
//                                                            //str, set in block format
//
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I,
//        String strNL_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strLineRow(                       //Produces:
//                                                            //{ item, ..., item }.
//
//                                                            //str, arr in one line format.
//
//        /*NSTD*/Object[]/*END-NSTD*/ arrobj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatArr2Main(                //Format for display.
//                                                            //An arr2 object should be display only once per run.
//
//                                                            //str, formated info.
//
//                                                            //arr2 to format
//        /*NSTD*//*Object[,]*/Object[][]/*END-NSTD*/ arr2obj_I,
//                                                            //SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                            //Variable name of arr2
//        String strText_I,
//                                                            //this is needed to get objId.
//        /*NSTD*/Object/*END-NSTD*/ objOriginal_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatArr2(                    //Format for display, it could be:
//                                                            //Set of Lines(Items), Set of Lines(Rows) or One
//                                                            //      Line(Matrix).
//
//                                                            //str, formated info.
//
//                                                            //Read strFormatArr2Main method for paramenters description
//        /*NSTD*//*Object[,]*/Object[][]/*END-NSTD*/ arr2obj_I,
//        String strText_I,
//        String strObjId_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strListItems(                     //Format a matrix to a Set of Lines(Items) inside a block.
//                                                            //Example:
//                                                            //[
//                                                            //{
//                                                            //  {
//                                                            //[0, 0] item
//                                                            //...
//                                                            //[0, y] item
//                                                            //  }
//                                                            //.....
//                                                            //  {
//                                                            //[x, 0] item
//                                                            //...
//                                                            //[x, y] item
//                                                            //  }
//                                                            //}
//                                                            //]
//                                                            //str, matrix in block format
//
//                                                            //Read strFormatArr2Main method for paramenters description
//        /*NSTD*//*Object[,]*/Object[][]/*END-NSTD*/ arr2obj_I,
//        String strNL_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strListRows(                      //Format a matrix to a Set of Lines(Rows) inside a block.
//                                                            //Example:
//                                                            //[
//                                                            //{
//                                                            //[0, *] { item, ..., item }
//                                                            //.......
//                                                            //[x, *] { item, ..., item }
//                                                            //}
//                                                            //]
//
//                                                            //str, matrix in block format
//
//                                                            //Read strFormatArr2Main method for paramenters description
//        /*NSTD*//*Object[,]*/Object[][]/*END-NSTD*/ arr2Obj_I,
//        String strNL_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strLineMatrix(                    //Produces:
//                                                            //[
//                                                            //[
//                                                            //{ { item, ..., item }, ....., { item, ..., item } }
//                                                            //]
//
//                                                            //str, matrix in one long line format.
//
//                                                            //Read strFormatArr2Main method for paramenters description
//        /*NSTD*//*Object[,]*/Object[][]/*END-NSTD*/ arr2obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatArr3Main(                //Format for display.
//                                                            //An arr3 object should be display only once per run.
//
//                                                            //str, formated info.
//
//                                                            //arr3 to format
//        /*NSTD*//*Object[, ,]*/Object[][][]/*END-NSTD*/ arr3obj_I,
//                                                            //SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                            //Variable name of arr3
//        String strText_I,
//                                                            //this is needed to get objId.
//        /*NSTD*/Object/*END-NSTD*/ objOriginal_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatArr3(                    //Format for display, it could be:
//                                                            //Set of Lines(Items), Set of Lines(Rows), Set of
//                                                            //      Lines(Matrixes) or One Line(Cube).
//
//                                                            //str, formated info.
//
//                                                            //Read strFormatArr2Main method for paramenters description
//        /*NSTD*//*Object[, ,]*/Object[][][]/*END-NSTD*/ arr3obj_I,
//        String strText_I,
//        String strObjId_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strListItems(                     //Format a cube to a set of items inside a block.
//                                                            //Example:
//                                                            //[
//                                                            //{
//                                                            //  {
//                                                            //    {
//                                                            //[0, 0, 0] item
//                                                            //...
//                                                            //[0, 0, z] item
//                                                            //    }
//                                                            //.....
//                                                            //    {
//                                                            //[0, y, 0] item
//                                                            //...
//                                                            //[0, y, z] item
//                                                            //    }
//                                                            //  }
//                                                            //.......
//                                                            //  {
//                                                            //    {
//                                                            //[x, 0, 0] item
//                                                            //...
//                                                            //[x, 0, z] item
//                                                            //    }
//                                                            //.....
//                                                            //    {
//                                                            //[x, y, 0] item
//                                                            //...
//                                                            //[x, y, z] item
//                                                            //    }
//                                                            //  }
//                                                            //}
//                                                            //]
//                                                            //str, cube in block format
//
//        /*NSTD*//*Object[, ,]*/Object[][][]/*END-NSTD*/ arr3obj_I,
//        String strNL_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strListRows(                      //Format a cube to a set of rows inside a block.
//                                                            //Example:
//                                                            //[
//                                                            //{
//                                                            //  {
//                                                            //[0, 0, *] { item, ..., item }
//                                                            //.....
//                                                            //[0, y, *] { item, ..., item }
//                                                            //  }
//                                                            //.......
//                                                            //  {
//                                                            //[x, 0, *] { item, ..., item }
//                                                            //.....
//                                                            //[x, y, *] { item, ..., item }
//                                                            //  }
//                                                            //}
//                                                            //]
//
//                                                            //str, cube in block format
//
//        /*NSTD*//*Object[, ,]*/Object[][][]/*END-NSTD*/ arr3obj_I,
//        String strNL_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strListMatrixes(                  //Format a cube to a set of matrixes inside a block.
//                                                            //Example:
//                                                            //[
//                                                            //{
//                                                            //[0, *, *] { { item, ..., item }, .....,
//                                                            //{ item, ..., item } }
//                                                            //.......
//                                                            //[x, *, *] { { item, ..., item }, .....,
//                                                            //{ item, ..., item } }
//                                                            //}
//                                                            //]
//
//                                                            //str, cube in block format
//
//        /*NSTD*//*Object[, ,]*/Object[][][]/*END-NSTD*/ arr3obj_I,
//        String strNL_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strLineCube(                      //Produces:
//                                                            //[
//                                                            //{ { { item, ..., item }, ....., { item, ..., item } },
//                                                            // .......,
//                                                            //{ { item, ..., item }, ....., { item, ..., item } } }
//                                                            //]
//
//                                                            //str, array in one long line format.
//
//                                                            //Array of formated items
//        /*NSTD*//*Object[, ,]*/Object[][][]/*END-NSTD*/ arr3obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strVectorFromSet(                 //Produces:
//                                                            //{ stuff, ..., stuff }.
//                                                            //Posibilities:
//                                                            //Put a set of strItem in a vector (strRow).
//                                                            //Put a set of strRow in a vector (strMatrix).
//                                                            //Put a set of strMatrix in a vector (strCube).
//
//                                                            //str, vector format.
//
//                                                            //Stuff to be included in strVector.
//        String[] arrstrStuff_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatDicMain(                 //Format for display
//                                                            //A dic object should be display only once per run.
//
//                                                            //str, formated info
//
//                                                            //dic.Keys and dic.Values to format
//        /*NSTD*/Object[]/*END-NSTD*/ arrobjValue_I,
//        String[] arrstrKey_I,
//                                                            //SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                            //Variable name of dic
//        String strText_I,
//                                                            //dic, any type (this is needed to get objId).
//        /*NSTD*/Object/*END-NSTD*/ objDic_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatDic(                     //Format for display.
//
//                                                            //str, formated info.
//
//                                                            //Read strFormatDicMain method for paramenters description
//        /*NSTD*/Object[]/*END-NSTD*/ arrobjValue_I,
//        String[] arrstrKey_I,
//        String strText_I,
//        String strObjId_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strFormatKvpMain(                 //Format for display
//
//                                                            //str, formated info
//
//                                                            //kvp.Key and kvp.Value to format (Value could be null)
//        /*NSTD*/Object/*END-NSTD*/ objValue_I,
//        String strKey_I,
//                                                            //SHORT or FULL display
//        TestoptionEnum testoptionOption_I,
//                                                            //Variable name of kvp
//        String strText_I,
//                                                            //kvp, any type (this is needed to get objId).
//        /*NSTD*/Object/*END-NSTD*/ objKvp_I
//        )
//    {
//        return "";
//    }
//    /*END-TASK*/
//
//    //==================================================================================================================
//    /*TASK Test.Blocking Support blocking in the display Objects Info*/
//    //------------------------------------------------------------------------------------------------------------------
//    /*CONSTANTS*/
//
//    //TODO
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*STATIC VARIABLES*/
//
//    //TODO
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*STATIC CONSTRUCTOR SUPPORT METHODS*/
//
//    //------------------------------------------------------------------------------------------------------------------
//    private static void subPrepareConstantsToBlockFormat(
//                                                          	//Método de apoyo llamado en constructor estático.
//                                                          	//Inicia Nivel y StartEnd necesarios para indentar el log.
//        )
//    {
//
//    }
//
//    //------------------------------------------------------------------------------------------------------------------
//    private static String strNL(                            //NL + caracteres indentación.
//        )
//    {
//        return "";
//    }
//
//    //------------------------------------------------------------------------------------------------------------------
//    private static void subBlockStart(                      //Genera los parámetros requerido para subToBlockFormat.
//                                                            //Solo se usa este método cuando block START-END.
//
//                                                            //NL + caracteres indentación.
//        /*out*/ String strNL_O,
//                                                            //Label for block START_??? y END_???. (this is ???).
//        /*out*/ String strLabel_O,
//                                                            //String to start block information
//        /*out*/ String strTo_O,
//                                                            //Text to describe the object
//        String strText_I,
//                                                            //Object Id, if this block is por a bclass should be ""
//        String strObjId_I
//        )
//     {
//
//     }
//
//    //------------------------------------------------------------------------------------------------------------------
//    private static void subBlockEnd(                        //Termina el block StartEnd (regresa el nivel).
//                                                            //Solo se usa este método cuando block START-END.
//
//                                                            //NL + caracteres indentación.
//        /*ref*/ String strNL_IO,
//                                                            //String to append information
//        /*ref*/ String strTo_IO,
//        String strLabel_I
//        )
//    {
//
//    }
//    /*END-TASK*/
//
//    //==================================================================================================================
//    /*TASK Test.ObjId set of methods to compute object id*/
//    //------------------------------------------------------------------------------------------------------------------
    public static String strGetObjId(                       //Generate an object id.

                                                            //str, prefixSize:HashCode.
                                                            //prefix, data type prefix (int, arrint, lststr, etc.).
                                                            //Size, [l], [l,m], [l,m,n] or "".

        /*NSTD*/Object/*END-NSTD*/ obj_I
        )
    {
        return "strGetObjId-Object";
    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static String strPrefix(                         //Generate the object prefix corresponding to type.
//                                                            //Class Name has the structure:
//                                                            //1. AaaaaBbbbbCcccc (a could be a digit).
//                                                            //2. AaaaaBbbbbCcccc[], [,] or [,,] (array)
//                                                            //3. Dictionary`2[String,other FullName], KeyValuePair.
//                                                            //4. List`1[ns.FullName], Queue or Stack.
//                                                            //AaaaaBbbbbCcccc could be:
//                                                            //a. Bclass, Btuple or standard Enumerator.
//                                                            //b. Primitive or system type (included in
//                                                            //      arrstrPRIMITIVE_TYPE or arrstrSYSTEM_TYPE).
//
//                                                            //str.
//                                                            //1. prefix if is single type (prefix form PRIMITIVE or
//                                                            //      SYSTEM, or aaaaa taken from class name if is a
//                                                            //      Bclass, Btuple o Enum the name structure is
//                                                            //      "AaaaaBbbbbbCcccc, 'a' could be a digit.
//                                                            //2. arrprefix, arr1prefix or arr2prefix where "prefix" is
//                                                            //      the prefix corresponding to element type.
//                                                            //3. xxxarg, where "xxx" is prefix form GENERIC and "arg"
//                                                            //      is the prefix corresponding to argument type (first
//                                                            //      or second argument).
//                                                            //(see class_I definition).
//
//                                                            //1. Single type (not a array or generic type), Ex. str,
//                                                            //      syspath, cod, codcb, sepoodt, ... .
//                                                            //2. Array type ([], [,] or [, ,]), Ex. arrstr, arr2int,
//                                                            //      arrarrstr, arrdicstr, ... .
//                                                            //3. Generic type (1 or 2 arguments). Ex. dicstr, kvpint,
//                                                            //      lsttok, queuecod, ... .
//        /*Type*/Class class_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static String strCollectionSize(                 //Generate the collection size.
//
//                                                            //str, "", [l], [l,m] o [l,m,n].
//
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strArrSize(                       //Get [l].
//
//                                                            //str, [l].
//
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strArr2Size(                      //Get [l,m].
//
//                                                            //str, [l,m].
//
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strArr3Size(                      //Get [l,m,n].
//
//                                                            //str, [l,m,n].
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strLstSize(                       //Get [l].
//
//                                                            //str, [l].
//
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strQueueSize(                     //Get [l].
//
//                                                            //str, [l].
//
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strStackSize(                     //Get [l].
//
//                                                            //str, [l].
//
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strDicSize(                       //Get [l].
//
//                                                            //str, [l].
//
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        return "";
//    }
//    /*END-TASK*/
//
//    //==================================================================================================================
//    /*TASK Test.Boxing set of private methods to box primitives*/
//    //------------------------------------------------------------------------------------------------------------------
//    private static void subfunConvertAndBox(                //Convert and box if required.
//                                                            //Parameters have the following posibilities:
//                                                            //[ Main            Key             Object
//                                                            //  oppp              -             appp-primitive(int, ...)
//                                                            //  arr?oppp          -             arr?ppp
//                                                            //  arroppp           -             lstppp
//                                                            //  arropppValue    arrstrKey       dicppp
//                                                            //  opppValue       strKey          kvpppp
//                                                            //
//                                                            //  yyy               -             yyy-system(str,ts,...)
//                                                            //  arr?yyy           -             arr?yyy
//                                                            //  arryyy            -             lstyyy
//                                                            //  arryyyValue     arrstrKey       dicyyy
//                                                            //  yyyValue        strKey          kvpyyy
//                                                            //
//                                                            //  eee               -             eee-enum(+subclasses)
//                                                            //  arr?eee           -             arr?eee
//                                                            //  (do not work)                   lsteee
//                                                            //  (do not work)                   diceee
//                                                            //  (do not work)                   kvpeee
//                                                            //
//                                                            //  ccc               -             ccc-bclass(+subclasses)
//                                                            //  arr?ccc           -             arr?ccc
//                                                            //  (do not work)                   lstccc
//                                                            //  (do not work)                   dicccc
//                                                            //  (do not work)                   kvpccc
//                                                            //
//                                                            //  ttt               -             ttt-tuple(+subclasses)
//                                                            //  arr?ttt           -             arr?ttt
//                                                            //  (do not work)                   lstttt
//                                                            //  (do not work)                   dicttt
//                                                            //  (do not work)                   kvpttt
//                                                            //]
//
//                                                            //obj or arrobj
//        /*NSTD*/Oobject<Object>/*END-NSTD*/ objMain_O,
//                                                            //arrstrKey or strKey, only for 2 atribute generic, other
//                                                            //      will return null
//        /*NSTD*/Oobject<Object>/*END-NSTD*/ objKey_O,
//                                                            //Any standard object, except generic with atribute enum,
//                                                            //      bclass or btuple.
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//                                                            //To easy code
//        Class classObj = Tools.getClass(obj_I);
//        Class classContent;
//        /*CASE*/
//        if (
//            classObj./*END-NSTD*/IsArray/*END-NSTD*/
//            )
//        {
//            classContent = classObj.GetElementType();
//        }
//        else if (
//            Test.boolIsGenericType(classObj)
//            )
//        {
//                                                            //Get needed argument type
//            Type[] arrtypeArguments = classObj./*NSTD*/GetGenericArguments/*END-NSTD*/();
//            classContent = arrtypeArguments[arrtypeArguments.Length - 1];
//        }
//        else
//        {
//                                                            //Single type
//            classContent = classObj;
//        }
//        /*END-CASE*/
//
//                                                            //Select process.
//                                                            //Primitive, simple and system types will process any
//                                                            //      structure (single, array or generic).
//                                                            //Enum, bclass and btuple will process only single and array
//                                                            //      structures.
//        /*CASE*/
//        if (
//                                                            //Is any form (single, array or generic) of primiteve type
//            Arrays.binarySearch(Test.arrstrPRIMITIVE_TYPE, classContent.getSimpleName()) >= 0
//            )
//        {
//            Test.subfunConvertAndBoxPrimitive(objMain_O, objKey_O, obj_I, classContent);
//        }
//        else if (
//                                                            //Is a any form (single, array or generic) of system type
//            Arrays.binarySearch(Test.arrstrSYSTEM_TYPE, classContent.getSimpleName()) >= 0
//            )
//        {
//                                                            //Only generic form need to be converted
//
//            if (
//                Test.boolIsGenericType(classObj)
//                )
//            {
//                Test.subfunConvertSystemType(objMain_O, objKey_O, obj_I, classContent);
//            }
//            else
//            {
//                                                            //No convertion is required for single or array form
//
//                objMain_O.v = obj_I;
//                objKey_O.v = null;
//            }
//        }
//        else if (
//                                                            //The agrument is any user defined type (bclass, btuple,
//                                                            //      Enum or Exception)
//            BclassBaseClassAbstract.class.isAssignableFrom(classContent) ||
//                BtupleBaseTupleAbstract.class.isAssignableFrom(classContent) ||
//                Enum.class.isAssignableFrom(classContent) ||
//                Exception.class.isAssignableFrom(classContent)
//            )
//        {
//                                                            //Generic form can not be processed
//                                                            //This type can not be processed, please convert 1 argument
//                                                            //      generic to array, dic to 2 arrays and kvp to its 2
//                                                            //      components and call the correct strTo method
//
//            if (
//                boolIsGenericType(classObj)
//                )
//            {
//                if (
//                    classObj.getSimpleName().equals(Test.strGENERIC_DICTIONARY_TYPE)
//                    )
//                    Tools.subAbort(Test.strTo(classObj, "obj_I.GetType") +
//                        " dic type with argument bclass, btuple or enum can not be processed in this method," +
//                        " you should produce a arrobjValue and arrstrKey and call the correct strTo method");
//                if (
//                    classObj.getSimpleName().equals(Test.strGENERIC_KEYVALUEPAIR_TYPE)
//                    )
//                    Tools.subAbort(Test.strTo(classObj, "obj_I.GetType") +
//                        " kvp type with argument bclass, btuple or enum can not be processed in this method," +
//                        " you should produce a objValue anr strKey and call the correct strTo method");
//                if (
//                    true
//                    )
//                    Tools.subAbort(Test.strTo(classObj, "obj_I.GetType") +
//                        " 1 argument generic type with argument bclass, btuple or enum can not be processed in " +
//                        " this method, you should produce a arrobj and call the correct strTo method");
//
//                objMain_O.v = null;
//                objKey_O.v = null;
//            }
//            else
//            {
//                                                            //No convertion is required for single or array form
//
//                objMain_O.v = obj_I;
//                objKey_O.v = null;
//            }
//        }
//        else
//        {
//            if (
//                true
//                )
//                Tools.subAbort(Test.strTo(classObj, "obj_I.GetType") + Test.strTo(classContent, "classContent") +
//                    " SOMETHING IS WRONG!!!, the content seems nonstandard type");
//
//            objMain_O.v = null;
//            objKey_O.v = null;
//        }
//        /*END-CASE*/
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertAndBoxPrimitive(       //Convert and box primitives.
//                                                            //Parameters have the following posibilities:
//                                                            //[ Main            Key             Object
//                                                            //  oppp              -             appp-primitive(int, ...)
//                                                            //  arr?oppp          -             arr?ppp
//                                                            //  arroppp           -             lstppp
//                                                            //  arropppValue    arrstrKey       dicppp
//                                                            //  opppValue       strKey          kvpppp
//                                                            //]
//
//                                                            //obj or arrobj
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //arrstrKey or strKey, only for 2 atribute generic, other
//                                                            //      will return null
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //Any standard object, except generic with atribute enum,
//                                                            //      bclass or btuple.
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//        /*Type*/Class classContent_I
//        )
//    {
//                                                            //Select process.
//        /*CASE*/
//        if (
//            classContent_I == int.class || classContent_I == Integer.class
//            )
//        {
//            Test.subfunConvertAndBoxInt(objMain_O, objKey_O, obj_I);
//        }
//        else if (
//            classContent_I == long.class || classContent_I == Long.class
//
//            )
//        {
//            Test.subfunConvertAndBoxLong(objMain_O, objKey_O, obj_I);
//        }
//        else if (
//            classContent_I == double.class || classContent_I == Double.class
//            )
//        {
//            Test.subfunConvertAndBoxNum(objMain_O, objKey_O, obj_I);
//        }
//        else if (
//            classContent_I == boolean.class || classContent_I == Boolean.class
//            )
//        {
//            Test.subfunConvertAndBoxBool(objMain_O, objKey_O, obj_I);
//        }
//        else if (
//            classContent_I == char.class || classContent_I == Character.class
//            )
//        {
//            Test.subfunConvertAndBoxChar(objMain_O, objKey_O, obj_I);
//        }
//        else if (
//            classContent_I == LocalDateTime.class
//            )
//        {
//            Test.subfunConvertAndBoxTs(objMain_O, objKey_O, obj_I);
//        }
//        else
//        {
//            if (
//                true
//                )
//                Tools.subAbort(Test.strTo(Tools.getClass(obj_I), "obj_I.GetType") + " SOMETHING IS WRONG!!!," +
//                    " it seems to be any form (single, array or generic) of nonstandard primitive");
//
//            objMain_O.v = null;
//            objKey_O.v = null;
//        }
//        /*END-CASE*/
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertAndBoxInt(             //Convert and box int.
//
//                                                            //oint, oint[], oint[,] or oint[, ,].
//                                                            //lstint, ... (one atribute generic) will convert to oint[].
//                                                            //dicint will convert to ointValues[] and strKeys[].
//                                                            //kvpint will convert to ointValue and strKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, str[] (keys from dic) or or str (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //int type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//        /*CASE*/
//        if (
//            obj_I instanceof Integer
//            )
//        {
//                                                            //Box primitive
//            Oint oint = new Oint((int)obj_I);
//
//            objMain_O.v = oint;
//            objKey_O.v = null;
//        }
//        else if (
//            obj_I instanceof int[]
//            )
//        {
//            int[] arrint = (int[])obj_I;
//
//                                                            //Box primitives
//            Oint[] arroint = new Oint[arrint.length];
//            for (int intI = 0; intI < arrint.length; intI = intI + 1)
//            {
//                arroint[intI] = new Oint(arrint[intI]);
//            }
//
//            objMain_O.v = arroint;
//            objKey_O.v = null;
//        }
//        else if (
//            obj_I instanceof LinkedList
//            )
//        {
//            LinkedList<Integer> lstint = (LinkedList<Integer>)obj_I;
//
//                                                            //Box primitives
//            Oint[] arroint = new Oint[lstint.size()];
//            for (int intI = 0; intI < lstint.size(); intI = intI + 1)
//            {
//                arroint[intI] = new Oint(lstint.get(intI));
//            }
//
//            objMain_O.v = arroint;
//            objKey_O.v = null;
//        }
//        //TODO: Define if Queue in C# will be Queue in Java
//        else if (
//            obj_I instanceof Queue
//            )
//        {
//            Queue<Integer> queueint = (Queue<Integer>)obj_I;
//
//            //                                              //Box primitives
//            Oint[] arroint = new Oint[queueint.size()];
//            int intI = 0;
//            for (Integer intX: queueint)
//            {
//                arroint[intI] = new Oint(intX);
//
//                intI = intI + 1;
//            }
//
//            objMain_O.v = arroint;
//            objKey_O.v = null;
//        }
//        else if (
//            obj_I instanceof Stack
//            )
//        {
//            Stack<Integer> stackint = (Stack<Integer>)obj_I;
//
//                                                            //Box primitives
//            Oint[] arroint = new Oint[stackint.size()];
//            int intI = 0;
//            for (Integer intX : stackint)
//            {
//                arroint[intI] = new Oint(intX);
//
//                intI = intI + 1;
//            }
//
//            objMain_O.v = arroint;
//            objKey_O.v = null;
//        }
//        else if (
//            obj_I instanceof int[][] && Tools.boolArrayIsQuadratic((int[][])obj_I)
//            )
//        {
//            int[][] arr2int = (int[][])obj_I;
//
//                                                            //Box primitives
//            Oint[][] arr2oint = new Oint[arr2int.length][arr2int[0].length];
//            for (int intI = 0; intI < arr2int.length; intI = intI + 1)
//            {
//                for (int intJ = 0; intJ < arr2int[0].length; intJ = intJ + 1)
//                {
//                    arr2oint[intI][intJ] = new Oint(arr2int[intI][intJ]);
//                }
//            }
//
//            objMain_O.v = arr2oint;
//            objKey_O.v = null;
//        }
//        else if (
//            obj_I instanceof int[][][]
//            )
//        {
//            int[][][] arr3int = (int[][][])obj_I;
//
//                                                            //Box primitives
//            Oint[][][] arr3oint = new Oint[arr3int.length][arr3int[0].length][arr3int[0][0].length];
//            for (int intI = 0; intI < arr3int.length; intI = intI + 1)
//            {
//                for (int intJ = 0; intJ < arr3int[0].length; intJ = intJ + 1)
//                {
//                    for (int intK = 0; intK < arr3int[0][0].length; intK = intK + 1)
//                    {
//                        arr3oint[intI][intJ][intK] = new Oint(arr3int[intI][intJ][intK]);
//                    }
//                }
//            }
//
//            objMain_O.v = arr3oint;
//            objKey_O.v = null;
//        }
//        else if (
//            obj_I instanceof LinkedHashMap
//            )
//        {
//            LinkedHashMap<String, Integer> dicint = (LinkedHashMap<String, Integer>)obj_I;
//
//                                                            //Convert to arrays.
//            int[] arrintValues = new int[dicint.size()];
//            dicint.Values.CopyTo(arrintValues, 0);
//            String[] arrstrKeys = new String[dicint.size()];
//            dicint.Keys.CopyTo(arrstrKeys, 0);
//
//                                                            //Box primitives
//            Oint[] arrointValues = new Oint[arrintValues.Length];
//            for (int intI = 0; intI < arrintValues.Length; intI = intI + 1)
//            {
//                arrointValues[intI] = new Oint(arrintValues[intI]);
//            }
//
//            objMain_O = arrointValues;
//            objKey_O = arrstrKeys;
//        }
//        else if (
//            obj_I is KeyValuePair<String, int>
//            )
//        {
//            KeyValuePair<String, int> kvpint = (KeyValuePair<String, int>)obj_I;
//
//                                                            //Extract attributes.
//            int intValue = kvpint.Value;
//            String strKey = kvpint.Key;
//
//                                                            //Box primitive
//            Oint ointValue = new Oint(intValue);
//
//            objMain_O = ointValue;
//            objKey_O = strKey;
//        }
//        else
//        {
//            if (
//                true
//                )
//                Tools.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!!, this type could not be processed with other int types");
//
//            objMain_O = null;
//            objKey_O = null;
//        }
//        /*END_CASE*/
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertAndBoxLong(            //Convert and box long.
//
//                                                            //olong, olong[], olong[,] or olong[, ,].
//                                                            //lstlong, ... (one atribute generic) will convert to
//                                                            //      olong[].
//                                                            //diclong will convert to olongValues[] and strKeys[].
//                                                            //kvplong will convert to olongValue and strKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, str[] (keys from dic) or or str (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //long type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertAndBoxNum(             //Convert and box num.
//
//                                                            //onum, onum[], onum[,] or onum[, ,].
//                                                            //lstnum, ... (one atribute generic) will convert to onum[].
//                                                            //dicnum will convert to onumValues[] and strKeys[].
//                                                            //kvpnum will convert to onumValue and strKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, str[] (keys from dic) or or str (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //num type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertAndBoxBool(            //Convert and box bool.
//
//                                                            //obool, obool[], obool[,] or obool[, ,].
//                                                            //lstbool, ... (one atribute generic) will convert to
//                                                            //      obool[].
//                                                            //dicbool will convert to oboolValues[] and strKeys[].
//                                                            //kvpbool will convert to oboolValue and strKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, str[] (keys from dic) or or str (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //bool type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertAndBoxChar(            //Convert and box char.
//
//                                                            //ochar, ochar[], ochar[,] or ochar[, ,].
//                                                            //lstchar, ... (one atribute generic) will convert to
//                                                            //      ochar[].
//                                                            //dicchar will convert to ocharValues[] and strKeys[].
//                                                            //kvpchar will convert to ocharValue and strKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, str[] (keys from dic) or or str (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //char type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertAndBoxTs(              //Convert and box ts.
//
//                                                            //ots, ots[], ots[,] or ots[, ,].
//                                                            //lstts, ... (one atribute generic) will convert to ots[].
//                                                            //dicts will convert to otsValues[] and strKeys[].
//                                                            //kvpts will convert to otsValue and strKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, str[] (keys from dic) or or str (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //ts type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertSystemType(            //Convert system type.
//                                                            //Parameters have the following posibilities:
//                                                            //[ Main            Key             Object
//                                                            //  yyy               -             yyy-system(str,ts,...)
//                                                            //  arr?yyy           -             arr?yyy
//                                                            //  arryyy            -             lstyyy
//                                                            //  arryyyValue     arrstrKey       dicyyy
//                                                            //  yyyValue        strKey          kvpyyy
//                                                            //]
//
//                                                            //obj or arrobj
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //arrstrKey or strKey, only for 2 atribute generic, other
//                                                            //      will return null
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//
//                                                            //Any standard object, except generic with atribute enum,
//                                                            //      bclass or btuple.
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//        /*Type*/ /*NSTD*/Class typeContent_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertStr(                   //Convert str.
//
//                                                            //str, str[], str[,] or str[, ,].
//                                                            //lststr, ... (one atribute generic) will convert to
//                                                            //      str[].
//                                                            //dicstr will convert to strValues[] and strKeys[].
//                                                            //kvpstr will convert to strValue and strKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, str[] (keys from dic) or or str (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //str type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertSysdir(                //Convert sysdir.
//
//                                                            //sysdir, sysdir[], sysdir[,] or sysdir[, ,].
//                                                            //lstsysdir, ... (one atribute generic) will convert to
//                                                            //      sysdir[].
//                                                            //dicsysdir will convert to sysdirValues[] and sysdirKeys[].
//                                                            //kvpsysdir will convert to sysdirValue and sysdirKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, sysdir[] (keys from dic) or or sysdir (key form
//                                                            //      kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //sysdir type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertSysfile(               //Convert strfile.
//
//                                                            //strfile, strfile[], strfile[,] or strfile[, ,].
//                                                            //lststrfile, ... (one atribute generic) will convert to
//                                                            //      strfile[].
//                                                            //dicstrfile will convert to strfileValues[] and
//                                                            //      strfileKeys[].
//                                                            //kvpstrfile will convert to strfileValue and strfileKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, strfile[] (keys from dic) or or strfile (key form
//                                                            //      kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //strfile type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertSyssr(                 //Convert syssr.
//
//                                                            //syssr, syssr[], syssr[,] or syssr[, ,].
//                                                            //lstsyssr, ... (one atribute generic) will convert to
//                                                            //      syssr[].
//                                                            //dicsyssr will convert to syssrValues[] and syssrKeys[].
//                                                            //kvpsyssr will convert to syssrValue and syssrKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, syssr[] (keys from dic) or or syssr (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //syssr type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static void subfunConvertSyssw(                 //Convert syssw.
//
//                                                            //syssw, syssw[], syssw[,] or syssw[, ,].
//                                                            //lstsyssw, ... (one atribute generic) will convert to
//                                                            //      syssw[].
//                                                            //dicsyssw will convert to sysswValues[] and sysswKeys[].
//                                                            //kvpsyssw will convert to sysswValue and sysswKey.
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objMain_O,
//                                                            //null, syssw[] (keys from dic) or or syssw (key form kvp).
//        Oobject/*NSTD*/<Object>/*END-NSTD*/ objKey_O,
//                                                            //syssw type (single, array or generic).
//        /*NSTD*/Object/*END-NSTD*/ obj_I
//        )
//    {
//
//    }
//    /*END-TASK*/
//
//    //==================================================================================================================
//    /*TASK Test.strAnalizeAndFormat set of private methods to format a single object*/
//    //------------------------------------------------------------------------------------------------------------------
//    private static String strAnalizeAndFormatCheckNulls(    //Produces an object in string format.
//                                                            //Before calling strAnalizeAndFormatXxxx checks for null
//
//                                                            //str, object in string format, could be null.
//
//                                                            //Object to format
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatBbox(          //Produces an object in string format (boxed primitive)
//
//                                                            //str, object in string format.
//
//                                                            //bbox to format
//        BboxBaseBoxingAbstract bbox_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatInt(           //Prepara un entero (long) para su despliege con información
//                                                            //      adicional si es mínimo o máximo.
//                                                            //Ejemplos:
//                                                            //1 -3,835.
//                                                            //2 -9,223,372,036,854,775,808<MinValue>.
//                                                            //3 9,223,372,036,854,775,807<MaxValue>.
//                                                            //str, String para despligue con información adicional.
//
//                                                            //Entero a desplegar.
//        int int_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatLong(          //Prepara un entero (long) para su despliege con información
//                                                            //      adicional si es mínimo o máximo.
//                                                            //Ejemplos:
//                                                            //1 -3,835.
//                                                            //2 -9,223,372,036,854,775,808<MinValue>.
//                                                            //3 9,223,372,036,854,775,807<MaxValue>.
//                                                            //str, String para despligue con información adicional.
//
//                                                            //Entero a desplegar.
//        long long_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatNum(           //Prepara un número para su despliege con información
//                                                            //      adicional si es mínimo, máximo, etc.
//                                                            //Ejemplos:
//                                                            //1 -1.23456789012345E+038.
//                                                            //2 -1.79769313486232E+308<MinValue>;
//                                                            //3 1.79769313486232E+308<MaxValue>;
//                                                            //4 NaN<0/0>;
//                                                            //5 -Infinity<-?/0>;
//                                                            //6 Infinity<?/0>;
//                                                            //str, String para despligue con información adicional.
//
//                                                            //Entero a desplegar.
//        double num_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatBool(          //Prepara un booleno para su despliege.
//                                                            //Ejemplos:
//                                                            //1 true.
//                                                            //2 false.
//                                                            //str, String para despligue.
//
//                                                            //Booleano a desplegar.
//        boolean bool_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatChar(          //Prepara un caracter para su despliege con información
//                                                            //      adicional si el es caracter extraño.
//                                                            //      caracter es extraño.
//                                                            //Ejemplos:
//                                                            //1 'c'.
//                                                            //2 '©'<0x00A9>.
//                                                            //3 '^'<0x0009, \t, Horizontal Tab>.
//                                                            //1) No tiene nada extraño, solo se añaden las comillas.
//                                                            //2) El caracter © no aparece en el teclado, incluyo su
//                                                            //      su hexadecimal.
//                                                            //3) El caracter es un Horizonal Tab, no es visible, se
//                                                            //      sustituye por ^ (el caracter en
//                                                            //      charSUBSTITUTE_NONVISIBLE), incluyo su hexadecimal y
//                                                            //      su descripción.
//                                                            //str, String para despligue con diagnostico si el
//
//                                                            //Caracter a analizar.
//        char char_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatTs(            //Prepara un date, time o ts para su despliege.
//                                                            //Ejemplos:
//                                                            //1 2013-12-28.
//                                                            //2 2013-12-28 21:30:16.
//                                                            //3 2013-12-28T21:31:25.7030000-06:00.
//                                                            //str, String para despligue.
//
//                                                            //ts (date o time) a desplegar.
//                                                            //ts, si tiene milisegundos se asume que es ts.
//                                                            //time, si tiene hora, minuto y/o segundos se asume que es
//                                                            //      time.
//                                                            //date, si no fue ts o time.
//        DateTime ts_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatBclass(        //Analize and format bclass (or subclass of bclass).
//                                                            //A bclass object should be display only once per run.
//                                                            //str, bclass formated to display.
//
//                                                            //Bclass to be analized and format
//        BclassBaseClassAbstract bclass_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatBtuple(        //Analize and format btuple (or subclass of btuple).
//                                                            //A btuple object should be display only once per run.
//                                                            //str, btuple formated to display.
//
//                                                            //Bclass to be analized and format
//        BtupleBaseTupleAbstract btuple_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatEnum(          //Analize and format enum (or subclass of enum).
//                                                            //str, enum formated to display.
//
//                                                            //Enum to be analized and format
//        Enum enum_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatSysexcep(      //Prepare a object to display.
//                                                            //str, sysexcep_I prepared to display.
//
//                                                            //Object to be analized and format
//        Exception sysexcep_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatSystemType(    //Produces an object in string format (system type)
//
//                                                            //str, object in string format.
//
//                                                            //Object to format
//        /*NSTD*/Object/*END-NSTD*/ obj_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatStr(           //Prepara un String para su despliege con información de
//                                                            //      caracteres que no son del KEYBOARD.
//                                                            //Ejemplos:
//                                                            //1. "Esto es lo que se analizo"<25>.
//                                                            //2. "©XYX"<4>{ <0, '©', 0x00A9> }.
//                                                            //3. "^XYX"<4>{ <0, '^', 0x0001> }.
//                                                            //4. "^XYX"<4>{ <0, '^', 0x0009, \t, Horizontal Tab> }.
//                                                            //1) Todo es del KEYBOARD, solo se añaden las comillas y su
//                                                            //      longitud.
//                                                            //2) El primer caracter © no aparece en el KEYBOARD, incluyo
//                                                            //      su hexadecimal.
//                                                            //3) El primer caracter es NONVISIBLE_WITHOUT_DESCRIPTION,
//                                                            //      se sustituye por _ (el caracter en
//                                                            //      charSUBSTITUTE_NONVISIBLE) e incluyo su hexadecimal.
//                                                            //4) El primer caracter es un Horizonal Tab, no es
//                                                            //      visible, se sustituye por _ (el caracter en
//                                                            //      charSUBSTITUTE_NONVISIBLE), incluyo su hexadecimal y
//                                                            //      su descripción.
//                                                            //Puede haber más de un caracter que no es del KEYBOARD, se
//                                                            //      añade "{ <.....>, <.....>, ..., <......> }".
//                                                            //Si no hay ningún caracter que no es del KEYBOARD, no se
//                                                            //      añade nada en esta parte, esto es no se añade "{ }",
//                                                            //      esto fue lo que sucedió en el ejemplo 1.
//                                                            //str, String para despligue con diagnostico de caracteres
//                                                            //      que no están en el KEYBOARD.
//
//                                                            //String a analizar.
//        String str_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatType(          //Prepare a object to display.
//                                                            //str, type prepared to display.
//
//                                                            //Object to be analized and format
//        /*Type*/Class class_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatSysdir(        //Prepare a object to display.
//                                                            //str, sysdir prepared to display.
//
//                                                            //Object to be analized and format
//        DirectoryInfo sysdir_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatSysfile(       //Prepare a object to display.
//                                                            //str, sysfile prepared to display.
//
//                                                            //Object to be analized and format
//        FileInfo sysfile_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatSyssr(         //Prepare a object to display.
//                                                            //str, syssr prepared to display.
//
//                                                            //Object to be analized and format
//        StreamReader syssr_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    private static String strAnalizeAndFormatSyssw(         //Prepare a object to display.
//                                                            //str, syssw prepared to display.
//
//                                                            //Object to be analized and format
//        StreamWriter syssw_I,
//                                                            //SHORT or FULL
//        TestoptionEnum testoptionOption_I
//        )
//    {
//        return "";
//    }
//    /*END-TASK*/
//
//    //==================================================================================================================
//    /*TASK Test.Trace Support to implement a trace*/
//    //------------------------------------------------------------------------------------------------------------------
//
//                                                            //Implementación de apoyos para poder efectuar un Trece en
//                                                            //      en una apliación.
//                                                            //¿Cómo?.
//                                                            //En el código "driver" para ejecutar la prueba, en
//                                                            //      PruebaFase2.cs, llamar al método:
//                                                            //Test.subSetLog(sysswLog);
//                                                            //Dentro del código que se desea aplicar el trace en los
//                                                            //      puntos que se crea conveniente, añadir:
//                                                            //Test.subTrace(true, strLabel, intNivel, String a trace);
//                                                            //Imprimie el log que contendrá el trace y otra información
//                                                            //      de la prueba
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*CONSTANTS*/
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*STATIC VARIABLES*/
//
//    //TODO
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*STATIC CONSTRUCTOR SUPPORT METHODS*/
//
//    //------------------------------------------------------------------------------------------------------------------
//    private static void subPrepareConstantsSubTrace(        //Initialiaze trace state.
//        )
//    {
//
//    }
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*SHARED METHODS*/
//
//    //------------------------------------------------------------------------------------------------------------------
//    public static void subSetLog(                           //Asigna un log para el trace (seguira en off).
//
//                                                            //Log para trace.
//        StreamWriter sysswLogTrace_T
//        )
//    {
//
//    }
//
//    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//    public static void subTrace(                           //Genera un trace a writeline.
//
//
//                                                            //true, se desea generar el trace.
//                                                            //false, No se genera el trace.
//                                                            //Se incluye este parámetro para sin tener que eliminar la
//                                                            //      la ejecución del trace poder activarlo/desactivarlo.
//        boolean boolIsTraceOn_I,
//                                                            //Etiqueta para identificar el registro del trace en la
//                                                            //      impresión. Cada instrucción trace que se agregue al
//                                                            //      código debe tener una etiqueta distinta.
//        String strLabel_I,
//                                                            //Información a incluir en el trace, esta información se le
//                                                            //      da forma similar a los strTo.
//        String strInfoTrace_I
//        )
//    {
//
//    }
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*END-TASK*/
//
//    //==============================================================================================================
//    /*TASK Test.Abort Support for testing subAbort*/
//
//                                                            //Implementación de apoyos para poder efectuar pruebas de
//                                                            //      subAbort y regitrar su información en un log.
//                                                            //¿Cómo?.
//                                                            //En el código "driver" para ejecutar la prueba (ej. en
//                                                            //      Test Sys01.cs), llamar al método:
//                                                            //Test.subSetTestAbort(); o.
//                                                            //Test.subResetTestAbort(); o.
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*CONSTANTS*/
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*STATIC VARIABLES*/
//
//    														//Indicador de se desea test.
//    private static boolean boolTestAbortOn;
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*STATIC CONSTRUCTOR SUPPORT METHODS*/
//
//    //------------------------------------------------------------------------------------------------------------------
//    private static void subPrepareConstantsTestAbort(       //Intialize test state.
//        )
//    {
//    	Test.boolTestAbortOn = false;
//    }
//
//    //------------------------------------------------------------------------------------------------------------------
//    /*SHARED METHODS*/
//
//    //------------------------------------------------------------------------------------------------------------------
//    public static void subSetTestAbort(                     //Marca que desea test.
//        )
//    {
//    	Test.boolTestAbortOn = true;
//    }
//
//    //------------------------------------------------------------------------------------------------------------------
//    public static void subResetTestAbort(                   //Marca que desea concluir test.
//        )
//    {
//    	Test.boolTestAbortOn = false;
//    }
//
//    //------------------------------------------------------------------------------------------------------------------
//    public static boolean boolIsTestAbortOn(                //Determina si se desea Test Abort.
//        )
//    {
//    	return Test.boolTestAbortOn;
//    }
//    /*END-TASK*/
//
//    //------------------------------------------------------------------------------------------------------------------
}
//=====================================================================================================================
/*END-TASK*/
