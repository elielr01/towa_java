/*TASK RPS.Cod Relevant Part Code*/
package QEnablerBase;

import TowaInfrastructure.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

//AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 23-Julio-2012.
                                                            //PURPOSE:
															//Especificación de clase Abstract para Comentarios.

//======================================================================================================================
public abstract class ComCommentsAbstract extends BclassBaseClassAbstract
                                                            //      Base para clases ComxxXxxxx.
                                                            //Comentarios para cualquier lenguaje.

{
    //--------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    @Override
    public BclassmutabilityEnum bclassmutability() {return BclassmutabilityEnum.INMUTABLE;}

    public abstract ComCommentsAbstract comDUMMY_UNIQUE();

                                                            //PARA COMENTARIOS.

                                                            //strCHAR_REQUIRED_IN_PARUN, si una línea de comentarios no
                                                            //      esta incluída en un parst, y contiene al menos 1
                                                            //      caracter requerido, esta línea será una línea de un
                                                            //      parun.
                                                            //arrcharREQUIRED_IN_PARUN_Z, lo anterior convertido a
                                                            //      arreglo y ordenado.
    public abstract char[] arrcharREQUIRED_IN_PARUN();

                                                            //La información dentro de los comentarios esta organizada
                                                            //      en párrafos los cuales tienen 3 formas distintas;
                                                            //      párrafos no estructurados, párrafos estructurados y
                                                            //      párrafos vacíos.
                                                            //Párrafo no estructurado se forma con un conjunto de
                                                            //      líneas que cada una tiene 1 ó varias palabras y
                                                            //      normalmente termina con un .(punto) o :(colón) al
                                                            //      final de la última línea.
                                                            //Párrafo estructurado se forma con un conjunto de líneas
                                                            //      que la primera línea, en la primer posición del
                                                            //      comentario, tiene un carácter de apertura {Ej. '[' ó
                                                            //      '('} y en la última línea, en la primer posición del
                                                            //      comentario, tiene un carácter de cierre {Ej. ']' ó
                                                            //      ')'}.
                                                            //Párrafo vacío se forma con un conjunto de líneas de
                                                            //      comentarios en blanco o líneas sin ningún caracter
                                                            //      en arrcharREQUIRED_IN_PARUN.
                                                            //(hay mas detalles respecto a la definición de párrafos,
                                                            //      esta explicación no es exhasutiva).
                                                            //Los comentarios pueden ser de 5 tipo:
                                                            //FULL_LINE, ocupan líneas completas de programa (Ej
                                                            //      así es en COBOL), puede tener las 3 formas de
                                                            //      párrafos.
                                                            //TAG_FULL_LINE, son similares a los FULL_LINE, sin embargo
                                                            //      esta en una sola línea, después de la marca de
                                                            //      comentario, el primer diferente de espacios es un
                                                            //      caracter que indica la apertura de un tag (Ej. en
                                                            //      COBOL es "<"), de la línea es extrae un solo párrafo
                                                            //      no estructurado.
                                                            //END_OF_LINE, están al final de líneas de programa (Ej
                                                            //      esto se tienen en C#, Java y Stored Program), puede
                                                            //      tener las 3 formas de párrafos.
                                                            //DELIMITED, inician y terminan con un DELIMITED,
                                                            //      ocupando el final de la línea donde inician y el
                                                            //      principio de la línea donde terminan, y la totalidad
                                                            //      de las líneas intermedias (Ej. esto también se tiene
                                                            //      en C#, Java y Stored Program), la primera línea y la
                                                            //      última serán párrafos no estructurados, todas las
                                                            //      líneas intermedias serán un solo párrafo
                                                            //      estructurado sin necesidad de carácteres de apertura
                                                            //      y cierre.
                                                            //EMBEDED, son similares a los DELIMITED, sin embargo
                                                            //      esta en una sola línea, todo el contenido es un solo
                                                            //      párrafo no estructurado.
                                                            //[Sintesis de Tipo de Comentarios vs Forma de Paragraphs
                                                            //       TipoCom       Par.Unstr     Par.Str     Par.Empty
                                                            //   FULL_LINE             X            X            X
                                                            //   TAG_FULL_LINE         X
                                                            //   END_OF_LINE           X            X            X
                                                            //   DELIMITED             X            X
                                                            //   EMBEDED               X
                                                            //]

                                                            //Para FULL_LINE y TAG_FULL_LINE.

                                                            //Indica si este tipo de comentarios son aceptados en el
                                                            //      lenguaje (Ej. en COBOL es true, en C# es false).
    public abstract boolean boolCOM_FL_OR_TL_IS_ACCEPTED();
                                                            //Caracteres con los que inicia (Ej. en COBOL es "*").
    public abstract String strCOM_FL_OR_TL();
                                                            //Tamaño de la línea de comentarios incluyendo caracteres de
                                                            //      inicio, normalmente es igual a intLONG_LIN_MAX (Ej
                                                            //      en COBOL es 66).
    public abstract int intCOM_FL_OR_TL_LINE_SIZE();
                                                            //Línea estándar con la que inicia y termina un conjunto de
                                                            //      comentarios de tipo FULL_LINE, cuando este
                                                            //      contiene al menos 1 párrafo no estructurado o todo
                                                            //      el conjunto no contiene ningún párrafo (Ej. en COBOL
                                                            //      "*----"). Nótese que si el conjunto contiene solo
                                                            //      párrafos estructurados y párrafos vacíos, por
                                                            //      estándar no se producen estás líneas.
    public abstract String strCOM_FL_START_AND_END_LINE();
                                                            //Caracteres con los que inicia y termina un tag (Ej. en
                                                            //      COBOL es "<" y ">").
                                                            //Si se eliminan del lado izquierto todos los espacios y los
                                                            //      caracteres de inicio de tag y del lado derecho todos
                                                            //      los espacios y los caracteres de fin de tag, lo que
                                                            //      resulta es la información de un párrafo no
                                                            //      estructurado (nótese que puede no tener palabras).
    public abstract char charCOM_TL_START();
    public abstract char charCOM_TL_END();

                                                            //Para END_OF_LINE.

                                                            //Indica si este tipo de comentarios son aceptados en el
                                                            //      lengueje (Ej. en COBOL es false, en C# es true).
    public abstract boolean boolCOM_EL_IS_ACCEPTED();
    //                                                      //Caracteres con los que inicia (Ej. en C# es "//").
    public abstract String strCOM_EL();
                                                            //Tamaño de la línea de comentarios incluyendo caracteres de
                                                            //      inicio (Ej. en C# es 60).
    public abstract int intCOM_EL_LINE_SIZE();

                                                            //Indica si se requiere adicionar otra marca strCOM_EL para 
                                                            //      mantener alineados los comentarios. Esto depende de
                                                            //      la tecnología y del FILE EXTENSION del file.
                                                            //Ordenar por el strFileExtension. Este será el mismo orden
                                                            //      que tiene arrstrFILE_EXTENSION.
    public abstract T2alignmentRequiresAlignmentTuple[] arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT();

                                                            //Representación estandar de comentario sin párrafos (Ej. en
                                                            //      C# puede ser "<COMMENT_WITH_NO_PARAGRAPHS>").
    public abstract String strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS();

                                                            //Para DELIMITED y EMBEDED.

                                                            //Indica si este tipo de comentarios son aceptados en el 
                                                            //      lengueje (Ej. en COBOL es false, en C# es true).
    public abstract boolean boolCOM_DE_OR_EM_IS_ACCEPTED();
                                                            //Caracteres con los que inicia y termina (Ej. en C# y 
                                                            //      Stored Program es "/*" y "*/").
    public abstract String strCOM_DE_OR_EM_START();
    public abstract String strCOM_DE_OR_EM_END();
                                                            //Tamaño de la línea del comentario DELIMITED, normalmente 
                                                            //      esto será igual a intLONG_LIN_MAX (Ej. en C# y 
                                                            //      Stores Program es 120).
    public abstract int intCOM_DE_LINE_SIZE();

                                                            //Para PÁRRAFO ESTRUCTURADO.

                                                            //Carácteres de apertura y cierre, estos caracteres estarán
                                                            //      inmediatamente después de los carácteres de inicio 
                                                            //      de la línea de comentario {Ej. en COBOL es ( y ), en
                                                            //      C#, Java y Stored Program es [ y ]}.
                                                            //Estos 2 caracteres deben ser DISTINTOS.
    public abstract char charPARST_START();
    public abstract char charPARST_END();

                                                            //Para PÁRRAFO NO ESTRUCTURADO.

                                                            //En FULL_LINE, indentación estandar de la primera línea y
                                                            //      de siguientes (Ej. en COBOL es 4 blancos y 8
                                                            //      blancos).
                                                            //La segunda indentación debe ser de longitud MAYOR que la
                                                            //      primera.
                                                            //Los 2 string deben contener solo espacios.
    public abstract String strPARUN_INDENT_FL_FIRST_LINE();
    public abstract String strPARUN_INDENT_FL_OTHER_LINES();
                                                            //En END_OF_LINE, indentación estandar de la primera línea 
                                                            //      y de siguientes (Ej. en C# es nada y 6 blancos).
                                                            //La segunda indentación debe ser de longitud MAYOR que la
                                                            //      primera.
                                                            //Los 2 string deben contener solo espacios.
    public abstract String strPARUN_INDENT_EL_FIRST_LINE();
    public abstract String strPARUN_INDENT_EL_OTHER_LINES();

    /*TO ACCESS CLASS CONSTANTS AND METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*SUPPORT METHODS FOR STATIC CONSTRUCTORS*/

    /*TASK Com1 subPrepareConstants*/
    //------------------------------------------------------------------------------------------------------------------
    protected static void subPrepareConstants(              //LOS CONSTRUCTORES ESTÁTICOS DE LAS CLASES ComxxXxxxx
                                                            //      DEBEN LLAMAR A ESTE MÉTODO PARA PREPARAR SU 
                                                            //      INFORMACIÓN.
                                                            //Ordena strCHAR_REQUIRED_IN_PARUN_I.
                                                            //Valida consistencia de otras constantes.

                                                            //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I,
                                                            //Caracteres considerados "útiles" en una línea de párrafo
                                                            //      no estructurado, si una línea de un párrafo no 
                                                            //      estructurado contiene al menos 1 caracter de estos,
                                                            //      la línea se considera que es útil, los caracteres
                                                            //      deben estar en arrcharUSEFUL del cod
                                                            //      correspondiente.
        String strCHAR_REQUIRED_IN_PARUN_I,
                                                            //Caracteres ordenados.
        Oobject <char[]> arrcharREQUIRED_IN_PARUN_O,
                                                            //Valida que los siguiente caracteres sean válidos.
        char charPARST_START_I,
        char charPARST_END_I,
                                                            //Si el bool es true valida que los parámetros asociados
                                                            //      sean válidos.
        boolean boolCOM_FL_OR_TL_IS_ACCEPTED_I,
        String strPARUN_INDENT_FL_FIRST_LINE_I,
        String strPARUN_INDENT_FL_OTHER_LINES_I,
        char charCOM_TL_START_I,
        char charCOM_TL_END_I,
                                                            //Si el bool es true valida que los parámetros asociados
                                                            //      sean válidos.
        boolean boolCOM_EL_IS_ACCEPTED_I,
        String strPARUN_INDENT_EL_FIRST_LINE_I,
        String strPARUN_INDENT_EL_OTHER_LINES_I,
                                                            //Si el bool es true, ordena por el FILE EXTENSION y
                                                            //      verifica los FILE EXTENSION sean los mismos que se
                                                            //      tienen en arrstrFILE_EXTENSION.
        T2alignmentRequiresAlignmentTuple[] arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M
        )
    {
        if (
            codDUMMY_I == null
            )
            Tools.subAbort(Tes2.strTo(codDUMMY_I, "codDUMMY_I") + " can not be null");

        ComCommentsAbstract.subPrepareCharRequiredInParun(codDUMMY_I, strCHAR_REQUIRED_IN_PARUN_I,
            arrcharREQUIRED_IN_PARUN_O);

        ComCommentsAbstract.subPrepareCharParst(codDUMMY_I, charPARST_START_I, charPARST_END_I);

        ComCommentsAbstract.subPrepareStringsParunComFl(codDUMMY_I, boolCOM_FL_OR_TL_IS_ACCEPTED_I,
            strPARUN_INDENT_FL_FIRST_LINE_I, strPARUN_INDENT_FL_OTHER_LINES_I, charCOM_TL_START_I, charCOM_TL_END_I);

        ComCommentsAbstract.subPrepareStringsParunComEl(boolCOM_EL_IS_ACCEPTED_I, strPARUN_INDENT_EL_FIRST_LINE_I,
            strPARUN_INDENT_EL_OTHER_LINES_I);

        ComCommentsAbstract.subPrepareRequiresAligment(codDUMMY_I, boolCOM_EL_IS_ACCEPTED_I,
            arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareCharRequiredInParun(      //Ordena strCHAR_REQUIRED_IN_PARUN_I.

                                                            //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I,
                                                            //Caracteres considerados "útiles" en una línea de párrafo
                                                            //      no estructurado, si una línea de un párrafo no 
                                                            //      estructurado contiene al menos 1 caracter de estos,
                                                            //      la línea se considera que es útil, los caracteres
                                                            //      deben estar en arrcharUSEFUL del cod
                                                            //      correspondiente.
        String strCHAR_REQUIRED_IN_PARUN_I,
                                                            //Caracteres ordenados.
        Oobject <char[]> arrcharREQUIRED_IN_PARUN_O
        )
    {
        if (
            strCHAR_REQUIRED_IN_PARUN_I == null
            )
            Tools.subAbort(Tes2.strTo(strCHAR_REQUIRED_IN_PARUN_I, "strCHAR_REQUIRED_IN_PARUN_I") +
                " can not be null");

                                                            //Ordena arrcharREQUIRED_IN_PARUN.
        arrcharREQUIRED_IN_PARUN_O.v = strCHAR_REQUIRED_IN_PARUN_I.toCharArray();
        Arrays.sort(arrcharREQUIRED_IN_PARUN_O.v);

                                                            //Verifica que no haya caracteres duplicados.
        for (int intI = 1; intI < arrcharREQUIRED_IN_PARUN_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Esta duplicado
                arrcharREQUIRED_IN_PARUN_O.v[intI] == arrcharREQUIRED_IN_PARUN_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrcharREQUIRED_IN_PARUN_O, "arrcharREQUIRED_IN_PARUN_O") + ", " +
                    Tes2.strTo(arrcharREQUIRED_IN_PARUN_O.v[intI], "arrcharREQUIRED_IN_PARUN_O[" + intI + "]") +
                    " duplicated character");
        }

                                                            //Verifica que arrcharREQUIRED_IN_PARUN_O SI este en
                                                            //      arrcharUSEFUL_O.
        for (int intI = 0; intI < arrcharREQUIRED_IN_PARUN_O.v.length; intI = intI + 1)
        {
            if (
                Arrays.binarySearch(codDUMMY_I.arrcharUSEFUL(), arrcharREQUIRED_IN_PARUN_O.v[intI]) < 0
                )
                Tools.subAbort(
                    Tes2.strTo(arrcharREQUIRED_IN_PARUN_O.v[intI], "arrcharREQUIRED_IN_PARUN_O[" + intI + "]") +
                    " does not exist in " + Tes2.strTo(codDUMMY_I.arrcharUSEFUL(), "codDUMMY_I.arrcharUSEFUL"));
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     private static void subPrepareCharParst(               //Valida consistencia.

                                                            //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I,
                                                            //Valida que los siguiente caracteres sean distintos.
        char charPARST_START_I,
        char charPARST_END_I
        ) 
    {
                                                            //Se verifica que las marcas de principio y fin sean
                                                            //      caracteres útiles en el código.
        if (
                                                            //Marca de principio no es caracter útil en el código
            Arrays.binarySearch(codDUMMY_I.arrcharUSEFUL(), charPARST_START_I) < 0
            )
            Tools.subAbort(Tes2.strTo(charPARST_START_I, "charPARST_START_I") +
                " does not exist in " + Tes2.strTo(codDUMMY_I.arrcharUSEFUL(), "codDUMMY_I.arrcharUSEFUL"));
        if (
                                                            //Marca de fin no es caracter útil en el código
            Arrays.binarySearch(codDUMMY_I.arrcharUSEFUL(), charPARST_END_I) < 0
            )
            Tools.subAbort(Tes2.strTo(charPARST_END_I, "charPARST_END_I") +
                " does not exist in " + Tes2.strTo(codDUMMY_I.arrcharUSEFUL(), "codDUMMY_I.arrcharUSEFUL"));
    
                                                            //Se verifican marcas de principio y fin de parst.
        if (
            charPARST_START_I == charPARST_END_I
            )
            Tools.subAbort(Tes2.strTo(charPARST_START_I, "charPARST_START_I") + ", " +
                Tes2.strTo(charPARST_END_I, "charPARST_END_I") + " can not be equal");
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareStringsParunComFl(        //Valida consistencia.

                                                            //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I,
                                                            //Si el bool es true valida que los parámetros asociados sean
                                                            //      válidos.
        boolean boolCOM_FL_OR_TL_IS_ACCEPTED_I,
        String strPARUN_INDENT_FL_FIRST_LINE_I,
        String strPARUN_INDENT_FL_OTHER_LINES_I,
        char charCOM_TL_START_I,
        char charCOM_TL_END_I
        )
    {
                                                            //Solo se verifica esto si la tecnología acepta COM_FL.
        if (
            boolCOM_FL_OR_TL_IS_ACCEPTED_I
            )
        {
            if (
                strPARUN_INDENT_FL_FIRST_LINE_I == null
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_FL_FIRST_LINE_I, "strPARUN_INDENT_FL_FIRST_LINE_I") + 
                    " can not be null");

            if (
                                                            //Al menos 1 caracter no es espacios
                !Tools.trimEnd(strPARUN_INDENT_FL_FIRST_LINE_I, ' ').equals("")
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_FL_FIRST_LINE_I, "strPARUN_INDENT_FL_FIRST_LINE_I") +
                    " should be only blank characters");
            if (
                strPARUN_INDENT_FL_OTHER_LINES_I == null
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_FL_OTHER_LINES_I, "strPARUN_INDENT_FL_OTHER_LINES_I") +
                    " can not be null");
            if (
                                                            //Al menos 1 caracter no es espacios
                !Tools.trimEnd(strPARUN_INDENT_FL_OTHER_LINES_I, ' ').equals("")
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_FL_OTHER_LINES_I, "strPARUN_INDENT_FL_OTHER_LINES_I") +
                    " should be only blank characters");
            if (
                strPARUN_INDENT_FL_OTHER_LINES_I.length() <= strPARUN_INDENT_FL_FIRST_LINE_I.length()
                )
                Tools.subAbort(Tes2.strTo(boolCOM_FL_OR_TL_IS_ACCEPTED_I, "boolCOM_FL_OR_TL_IS_ACCEPTED_I") +
                    ", " + Tes2.strTo(strPARUN_INDENT_FL_FIRST_LINE_I, "strPARUN_INDENT_FL_FIRST_LINE_I") +
                    ", " + Tes2.strTo(strPARUN_INDENT_FL_OTHER_LINES_I, "strPARUN_INDENT_FL_OTHER_LINES_I") +
                    ", FIRST_LINE length should be < OTHER_LINES length");
            if (
                                                            //Caracter no útil
                Arrays.binarySearch(codDUMMY_I.arrcharUSEFUL(), charCOM_TL_START_I) < 0
                )
                Tools.subAbort(Tes2.strTo(charCOM_TL_START_I, "charCOM_TL_START_I") +
                    " does not exist in " + Tes2.strTo(codDUMMY_I.arrcharUSEFUL(), "codDUMMY_I.arrcharUSEFUL"));
            if (
                                                            //Caracter no útil
                Arrays.binarySearch(codDUMMY_I.arrcharUSEFUL(), charCOM_TL_END_I) < 0
                )
                Tools.subAbort(Tes2.strTo(charCOM_TL_END_I, "charCOM_TL_END_I") +
                    " does not exist in " + Tes2.strTo(codDUMMY_I.arrcharUSEFUL(), "codDUMMY_I.arrcharUSEFUL"));
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareStringsParunComEl(        //Valida consistencia.

                                                            //Si el bool es true valida que el segundo String sea de
                                                            //      longitud mayor que el primero.
        boolean boolCOM_EL_IS_ACCEPTED_I,
        String strPARUN_INDENT_EL_FIRST_LINE_I,
        String strPARUN_INDENT_EL_OTHER_LINES_I
        )
    {
                                                            //Solo se verifica esto si la tecnología acepta COM_EL.
        if (
            boolCOM_EL_IS_ACCEPTED_I
            )
        {
            if (
                strPARUN_INDENT_EL_FIRST_LINE_I == null
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_EL_FIRST_LINE_I, "strPARUN_INDENT_EL_FIRST_LINE_I") +
                    " can not be null");
            if (
                                                                //Al menos 1 caracter no es espacios
                !Tools.trimEnd(strPARUN_INDENT_EL_FIRST_LINE_I,' ').equals("")
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_EL_FIRST_LINE_I, "strPARUN_INDENT_EL_FIRST_LINE_I") +
                    " should be only blank characters");
            if (
                strPARUN_INDENT_EL_OTHER_LINES_I == null
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_EL_OTHER_LINES_I, "strPARUN_INDENT_EL_OTHER_LINES_I") +
                    " can not be null");
            if (
                                                            //Al menos 1 caracter no es espacios
                !Tools.trimEnd(strPARUN_INDENT_EL_OTHER_LINES_I, ' ').equals("")
                )
                Tools.subAbort(Tes2.strTo(strPARUN_INDENT_EL_OTHER_LINES_I, "strPARUN_INDENT_EL_OTHER_LINES_I") +
                    " should be only blank characters");
            if (
                strPARUN_INDENT_EL_OTHER_LINES_I.length() <= strPARUN_INDENT_EL_FIRST_LINE_I.length()
                )
                Tools.subAbort(Tes2.strTo(boolCOM_EL_IS_ACCEPTED_I, "boolCOM_EL_IS_ACCEPTED_I") +
                    ", " + Tes2.strTo(strPARUN_INDENT_EL_FIRST_LINE_I, "strPARUN_INDENT_EL_FIRST_LINE_I") +
                    ", " + Tes2.strTo(strPARUN_INDENT_EL_OTHER_LINES_I, "strPARUN_INDENT_EL_OTHER_LINES_I") +
                    ", FIRST_LINE length should be < OTHER_LINES length");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareRequiresAligment(         //Ordena información para REQUIRES_ALIGMENT.

                                                            //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I,
                                                            //Si el bool es true ordena y valida la tupla.
                                                            //Debe contener exactamente las mismas FILE EXTENSION.
        boolean boolCOM_EL_IS_ACCEPTED_I,
        T2alignmentRequiresAlignmentTuple[] arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M
        )
    {
                                                            //Solo se verifica esto si la tecnología acepta COM_EL.
        if (
            boolCOM_EL_IS_ACCEPTED_I
            )
        {
            if (
                arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M == null
                )
                Tools.subAbort(
                    Tes2.strTo(arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M,"arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M") +
                        " can not be null");

                                                            //Ordena por el primer item de la tupla
            String[] arrstrFILE_EXTENSION = new String[arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M.length];
            for (int intI = 0; intI < arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M.length; intI = intI + 1)
            {
                arrstrFILE_EXTENSION[intI] = arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M[intI].strFileExtension;
            }
            Tools.sort(arrstrFILE_EXTENSION, arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M);

                                                            //Verifica que arrt2... tenga su correspondencia con las
                                                            //      FILE_EXTENSION posibles para la tecnología.

            if (
                arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M.length != codDUMMY_I.arrstrFILE_EXTENSION().length
                )
                Tools.subAbort(
                    Tes2.strTo(arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M, 
                        "arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M") +
                    ", " + Tes2.strTo(codDUMMY_I.arrstrFILE_EXTENSION(), "codDUMMY_I.arrstrFILE_EXTENSION") +
                    " should be arrays of the same length");

            for (int intI = 0; intI < arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M.length; intI = intI + 1)
            {
                if (
                    arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M[intI].strFileExtension != 
                        codDUMMY_I.arrstrFILE_EXTENSION()[intI]
                    )
                    Tools.subAbort(
                        Tes2.strTo(arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M, 
                            "arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_M") +
                        ", " + Tes2.strTo(codDUMMY_I.arrstrFILE_EXTENSION(), "codDUMMY_I.arrstrFILE_EXTENSION") +
                        " should include exactly the same FILE_EXTENSION");
            }
        }
    }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/
    
                                                            //Indica el tipo de comentarios.
    private ComtypeEnum comtype_Z;
    public ComtypeEnum comtype() { return this.comtype_Z; }

                                                            //Código Original correspondiente al objeto com.
    private CodCodeAbstract codOriginal_Z;
    public CodCodeAbstract codOriginal() { return this.codOriginal_Z; }

                                                            //Un comentario consta de 0 ó varios párrafos de 
                                                            //      información, posiblemente con párrafos vacíos 
                                                            //      intercalados.
    private ParParagraphAbstract[] arrpar_Z;
    public ParParagraphAbstract[] arrpar() { return this.arrpar_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

                                                            //Código Estandar correspondiente al com.
    private CodCodeAbstract codStandard_Z;
    public CodCodeAbstract codStandard()
    {
                                                            //Recalcula si el com es nuevo o ya fue reseteado.
        if (
            this.codStandard_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Construye el objeto código estándar.
            this.codStandard_Z = this.codOriginal().codxxNew(this);

            this.subSetIsResetOff();
        }

        return codStandard_Z;
    }

                                                            //El arrbool y arrstr de codOriginal son del mismo tamaño,
                                                            //      tiene true si esa línea del código original tiene
                                                            //      coincidencia.
    private boolean[] arrboolOriginalOK_Z;
    public boolean[] arrboolOriginalOK()
    {
                                                            //Recalcula si el com es nuevo o ya fue reseteado.
        if (
            this.arrboolOriginalOK_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //For method with out parameters.
            Oobject<boolean[]> oarrboolOriginalOK_Z = new Oobject<boolean[]>();
            Oobject<boolean[]> oarrboolStandardOK_Z = new Oobject<boolean[]>();

                                                            //Marca coincidencias.
            this.subStandardVsOriginal(oarrboolOriginalOK_Z, oarrboolStandardOK_Z);

                                                            //Assign the out values.
            this.arrboolOriginalOK_Z = oarrboolOriginalOK_Z.v;
            this.arrboolStandardOK_Z = oarrboolStandardOK_Z.v;

            this.subSetIsResetOff();
        }

        return arrboolOriginalOK_Z;
    }

                                                            //El arrbool y arrstr de codStandard son del mismo tamaño,
                                                            //      tiene true si esa línea del código estandar tiene
                                                            //      coincidencia.
    private boolean[] arrboolStandardOK_Z;
    public boolean[] arrboolStandardOK()
    {
                                                            //Recalcula si el com es nuevo o ya fue reseteado.
        if (
            this.arrboolStandardOK_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //For method with out parameters.
            Oobject<boolean[]> oarrboolOriginalOK_Z = new Oobject<boolean[]>();
            Oobject<boolean[]> oarrboolStandardOK_Z = new Oobject<boolean[]>();

                                                            //Marca coincidencias.
            this.subStandardVsOriginal(oarrboolOriginalOK_Z, oarrboolStandardOK_Z);

                                                            //Assign the out values.
            this.arrboolOriginalOK_Z = oarrboolOriginalOK_Z.v;
            this.arrboolStandardOK_Z = oarrboolStandardOK_Z.v;

            this.subSetIsResetOff();
        }

        return arrboolStandardOK_Z;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT COMPUTED VARIABLES*/

    /*TASK Com2 subStandardVsOriginal*/
    //------------------------------------------------------------------------------------------------------------------
    private void subStandardVsOriginal(                     //Compara la información de las líneas de código estándar 
                                                            //      con la información de las líneas de código original. 
                                                            //Se marcan como OK (true) las líneas que coincidan. 
                                                            //Dependiendo del tipo de comentario se tienen las siguiente
                                                            //      consideraciones para la comparación:
                                                            //FULL_LINE o END_OF_LINE.
                                                            //Se considera que las líneas coinciden si:
                                                            //a) Estando en un párrafo estructurado su contenido es 
                                                            //      idéntico, incluyendo sus espacios en blanco 
                                                            //      intermedios.
                                                            //b) Estando fuera párrafo estructurado (esto es, estando en
                                                            //      párrafo no estructurado o en párrafo vacío) 
                                                            //      contienen las mismas palabras (caracteres 
                                                            //      consecutivos DELIMITED por uno o varios 
                                                            //      espacios) en el mismo orden.
                                                            //TAG_FULL_LINE (es solo 1 línea).
                                                            //Se considera que la línea coincide si al eliminar los
                                                            //      inicios y fin de tag contienen las mismas palabras.
                                                            //EMBEDED (es solo 1 línea).
                                                            //a) Contienen las mismas palabras.
                                                            //b) Contienen ambos los DELIMITEDres (recuerdese que el 
                                                            //      código original, por un error podría no tener la 
                                                            //      marca de fin y de todos modos fue reconocido como 
                                                            //      EMBEDED).
                                                            //DELIMITED.
                                                            //Se considera que las líneas coinciden si:
                                                            //a) Estando en el primer párrafo que es no estructurado, 
                                                            //      contiene las mismas palabras en el mismo orden (para
                                                            //      ser reconocido tuvo que tener la marca de inicio).
                                                            //b) Estando en el segundo párrafo que es un párrafo 
                                                            //      estructurado, el contenido las líneas es idéntico.
                                                            //c) Estando en el tercer párrafos que es no estructurado, 
                                                            //      contiene las mismas palabras en el mismo orden y 
                                                            //      ambos contienen la marca de fin.
                                                            //
                                                            //Se buscan las líneas que coincidan, que entre estas líneas
                                                            //      y el principio o entre estas líneas y las líneas de 
                                                            //      la coicidencia inmediata anterior haya la menor 
                                                            //      diferencia posible (Ej. si no hay coincidencia en 
                                                            //      las líneas (Standard-Original) 1-1, 2-1, 1-2, etc., 
                                                            //      pero si hay coincidencia entre las líneas 3-3 y
                                                            //      4-1, se deberá reconocer la coicidencia 3-3 y no la 
                                                            //      4-1 dado que en la 3-3 la diferencia entre estas y 
                                                            //      el principio es 3 y en 4-1, la diferencia entre 4 y 
                                                            //      el principio es 4).
                                                            //Si hay coincidencia en 2(Standard)-1(Original) y en
                                                            //      1(Standard)-2(Original), se eligira la coincidencia 
                                                            //      2-1, esto le da prioridad a la menor diferencia en 
                                                            //      Original.
                                                            //this[M], toma info del objeto.

                                                            //Estos arreglos deben ser de la misma dimensión que los
                                                            //      arreglos de líneas, true indica que la línea
                                                            //      correspondiente tiene una línea que coincide.
        Oobject <boolean[]> oarrboolOriginalOK_O,
        Oobject <boolean[]> oarrboolStandardOK_O
        )
    {
                                                            //Los siguientes arreglos son para preparar la información
                                                            //      para simplificar la comparación.
        Oobject<String[]> oarrstrOriginal = new Oobject<String[]>();
        this.subfunPrepareVs(oarrstrOriginal, oarrboolOriginalOK_O, this.codOriginal());
        String[] arrstrOriginal = oarrstrOriginal.v;
        Oobject<String[]> oarrstrStandard = new Oobject<String[]>();
        this.subfunPrepareVs(oarrstrStandard, oarrboolStandardOK_O, this.codStandard());
        String[] arrstrStandard = oarrstrStandard.v;

                                                            //Inicia índices para barrer estandar y original.
        Oint ointStandard = new Oint(0);
        Oint ointOriginal = new Oint(0);

                                                            //Se cicla para marcar todas las coincidencias, el final se
                                                            //      puede consider otra coincidencia en la que determina
                                                            //      que ya no la encontró.
        /*UNTIL-DO*/
        while (!(
                                                            //Ambos arreglos ya terminaron.
            (ointStandard.v >= arrstrStandard.length) && (ointOriginal.v >= arrstrOriginal.length)
            ))
        {
            ComCommentsAbstract.subCoincidence(arrstrStandard, ointStandard, arrstrOriginal, ointOriginal);

                                                            //Si está dentro del código significa que encontro una
                                                            //      coincidencia.
            if (
                ointStandard.v < arrstrOriginal.length
                )
            {
                                                            //Marca la coincidencia.
                oarrboolStandardOK_O.v[ointStandard.v] = true;
                oarrboolOriginalOK_O.v[ointOriginal.v] = true;
            }

                                                            //Avanza al inicio de la posible siguiente coincidencia.
            ointStandard.v = ointStandard.v + 1;
            ointOriginal.v = ointOriginal.v + 1;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subfunPrepareVs(                           //Prepara arrstrStandard(Original) y
                                                            //      this.arrboolStandard(Original) para hacer la
                                                            //      comparación.
                                                            //1) Si es FULL_LINE o FIN-LINEAS, extrar la parte de
                                                            //      la línea de comentarios que tiene información, por
                                                            //      ejemplo si es COBOL quita "*", si es C# quita "//",
                                                            //      además, si no esta en conjunto de líneas de párrafo
                                                            //      estructurado, le hace TrimExcel.
                                                            //2) Si es TAG_FULL_LINE, elimina la marca de inicio de
                                                            //      comentarios y los caracteres de inicio y fin de tag,
                                                            //      y le hace TrimExcel.
                                                            //3) Si es EMBEDED, elimina la marca de inicio, si tiene la
                                                            //      marca de fin la separa con espacio, y le hace
                                                            //      TrimExcel (la marca de fin será una palabra para
                                                            //      efectos de la comparación).
                                                            //4) Si es DELIMITED:
                                                            //4a. En la primera línea, elimina la marca de inicio y le
                                                            //      hacer TrimExcel.
                                                            //4b. Si la última línea tiene marca de fin, la separa con
                                                            //      espacio, y le hace TrimExcel (la marca de fin será
                                                            //      palabra para efectos de comparación).
                                                            //this[I], toma propiedades del objeto.
    
                                                            //Arreglo de código ya preparado.
        Oobject <String[]> oarrstrLine_O,
                                                            //Arreglo de boolenos ya preparado (iniciado con false).
        Oobject <boolean[]> oarrboolLineOK_O,
                                                            //Código a preparar para la comparación.
        CodCodeAbstract codBase_I
        )
    {
        /*CASE*/ 
        if (
            this.comtype() == ComtypeEnum.FULL_LINE
            )
        {
                                                        //Prepara arreglo de com FULL_LINE.
            oarrstrLine_O.v = this.arrstrPrepareFlOrEl(codBase_I, this.strCOM_FL_OR_TL().length());
        }
        else if (
            this.comtype() == ComtypeEnum.END_OF_LINE
            )
        {
                                                        //Prepara arreglo de com END_OF_LINE.
            oarrstrLine_O.v = this.arrstrPrepareFlOrEl(codBase_I, this.strCOM_EL().length());
        }
        else if (
            this.comtype() == ComtypeEnum.TAG_FULL_LINE
            )
        {
                                                        //Prepara arreglo de com TAG_FULL_LINE.
            oarrstrLine_O.v = this.arrstrPrepareTl(codBase_I);
        }
        else if (
            this.comtype() == ComtypeEnum.EMBEDED
            )
        {
                                                        //Prepara arreglo de com EMBEDED.
            oarrstrLine_O.v = this.arrstrPrepareEm(codBase_I);
        }
        else if (
            this.comtype() == ComtypeEnum.DELIMITED
            )
        {
                                                        //Prepara arreglo de com DELIMITED.
            oarrstrLine_O.v = this.arrstrPrepareDe(codBase_I);
        }
        else
        {
            if (
                true
                )
                Tools.subAbort(Tes2.strTo(this.comtype(), "comtype") + " was not found");

                                                        //Cualquier cosa, no importa.
            oarrstrLine_O.v = null;
        }
        /*END-CASE*/

                                                        //Inicia el arreglo bool con false en todos.
        oarrboolLineOK_O.v = new boolean[oarrstrLine_O.v.length];
        for (int intI = 0; intI > oarrboolLineOK_O.v.length; intI = intI + 1)
        {
            oarrboolLineOK_O.v[intI] = false;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String[] arrstrPrepareFlOrEl(                   //En COM_FL o COM_EL prepara arrstrStandard(Original) para
                                                            //      hacer la comparación.
                                                            //Extrar la parte de la línea de comentarios que tiene
                                                            //      información, por ejemplo si es COBOL quita "*", si
                                                            //      es C# quita "//", además, si no esta en conjunto de
                                                            //      líneas de párrafo estructurado, le hace TrimExcel.
                                                            //arrstr, arreglo de código ya preparado.
                                                            //this[I], toma propiedades del objeto.
    
                                                            //Código a preparar para la comparación.
        CodCodeAbstract codBase_I,
                                                            //Longitud de la marca de inicio.
        int intLON_COM_FL_OR_EL_I
        )
    {
                                                            //Inicia el arreglo donde dejará la información ya 
                                                            //      preparada.
        String[] arrstrPrepareFlOrEl = new String[codBase_I.arrstrLine().length];

                                                            //Inicia estando fuera de estructurado
        boolean boolIsStructured = false;

        for (int intI = 0; intI < codBase_I.arrstrLine().length; intI = intI + 1)
        {
                                                            //Se queda solo con la Info de la línea de comentario, toma
                                                            //      la línea para facilitar la codificación.
            String strLine = codBase_I.arrstrLine()[intI].substring(intLON_COM_FL_OR_EL_I);

                                                            //Compacta (hace TrimExcel) solo si no esta dentro de 
                                                            //      conjunto estructurado.
            if (
                                                            //Tiene apertura de conjunto estructurado.
                strLine.startsWith(Character.toString(this.charPARST_START()))
                )
            {
                                                            //Empiezan lineas de estructurado
                boolIsStructured = true;
            }

            if (
                boolIsStructured
                )
            {
                                                            //No le hace nada (los espacios de lado derecho ya se 
                                                            //      quitaron antes).
            }
            else
            {
                                                            //Se pasa la información eliminando espacios extra.
                strLine = Tools.strTrimExcel(strLine);
            }

            if (
                                                            //Tiene cierre de conjunto estructurado.
                strLine.startsWith(Character.toString(this.charPARST_END()))
                )
            {
                                                            //Ya se proceso la terminación de lineas de estructurado
                boolIsStructured = false;
            }

            arrstrPrepareFlOrEl[intI] = strLine;
        }

                                                            //Regresa el arreglo ya transformado
        return arrstrPrepareFlOrEl;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String[] arrstrPrepareTl(                       //En COM_TL prepara arrstrStandard(Original) para hacer la
                                                            //      comparación.
                                                            //Elimina la marca de inicio de comentarios, separa los
                                                            //      inicio y fin de tag de la información del párrafo no
                                                            //      estructurado y le hace TrimExcel.
                                                            //arrstr, arreglo de código ya preparado.
                                                            //this[I], toma propiedades del objeto.

                                                            //Código a preparar para la comparación.
        CodCodeAbstract codBase_I
        )
    {
                                                            //Toma la línea para facilitar la codificación (le quita la
                                                            //      marca de inicio del comentario).
        String strLine = codBase_I.arrstrLine()[0].substring(this.strCOM_FL_OR_TL().length());

                                                            //Le quita la marca de inicio y fin de tag (la de fin es
                                                            //      opcional).
        strLine = Tools.trimStart(strLine, this.charCOM_TL_START(), ' ');
        strLine = Tools.trimEnd(strLine, this.charCOM_TL_END(), ' ');

                                                            //Quita exceso de espacios.
        strLine = Tools.strTrimExcel(strLine);

                                                            //Forma el arreglo, es solo 1 línea y lo regresa.
        return new String[] { strLine };
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String[] arrstrPrepareEm(                       //En COM_EM prepara arrstrStandard(Original) para hacer la 
                                                            //      comparación.
                                                            //Elimina la marca de inicio, si tiene la marca de fin la 
                                                            //      separa con espacio, y le hace TrimExcel (la marca de
                                                            //      fin será una palabra para efectos de la
                                                            //      comparación).
                                                            //arrstr, arreglo de código ya preparado.
                                                            //this[I], toma propiedades del objeto.
    
                                                            //Código a preparar para la comparación.
        CodCodeAbstract codBase_I
        )
    {
                                                            //Toma la línea para facilitar la codificación, luego la 
                                                            //      transforma para quedarse solo con lo requerido para
                                                            //      la comparación.
        String strLine = codBase_I.arrstrLine()[0];

                                                            //Elimina la marca de inicio.
        strLine = strLine.substring(this.strCOM_DE_OR_EM_START().length());

                                                            //Si tiene marca de fin, la separa con un blanco.
        if (
                                                            //Tiene DELIMITEDr de fin.
            strLine.endsWith(this.strCOM_DE_OR_EM_END())
            )
        {
            strLine =
                strLine.substring(0, strLine.length() - this.strCOM_DE_OR_EM_END().length()) + " " +
                    this.strCOM_DE_OR_EM_END();
        }

                                                            //Quita exceso de espacios.
        strLine = Tools.strTrimExcel(strLine);

                                                            //Forma el arreglo, es solo 1 línea y lo regresa.
        return new String[] { strLine };
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String[] arrstrPrepareDe(                       //En COM_DE prepara arrstrStandard(Original) para hacer la
                                                            //      comparación.
                                                            //Elimina la marca de inicio, si tiene la marca de fin la 
                                                            //      separa con espacio, y le hace TrimExcel (la marca de
                                                            //      fin será una palabra para efectos de la
                                                            //      comparación).
                                                            //3) Si es DELIMITED:
                                                            //a. En la primera línea, elimina la marca de inicio y le 
                                                            //      hacer TrimExcel. 
                                                            //b. Si la última línea tiene marca de fin, la separa con 
                                                            //      espacio, y le hace TrimExcel (la marca de fin será 
                                                            //      palabra para efectos de comparación). 
                                                            //arrstr, arreglo de código ya preparado.
                                                            //this[I], toma propiedades del objeto.
    
                                                            //Código a preparar para la comparación.
        CodCodeAbstract codBase_I
        )
    {
                                                            //Por lo pronto toma el arreglos tal y como esta, luego lo 
                                                            //      transforma para quedarse solo con lo requerido para 
                                                            //      la comparación.
        String[] arrstrPrepareDe = codBase_I.arrstrLine();

                                                            //En la primera línea elimina la marca de inicio y hace 
                                                            //      TrimExcel.
        arrstrPrepareDe[0] = Tools.strTrimExcel(arrstrPrepareDe[0].substring(this.strCOM_DE_OR_EM_START().length()));

                                                            //Si la última línea tiene marca de fin la separa y hace 
                                                            //      TrimExcel, la marca de fin será otra palabra.

                                                            //Toma la última línea para facilitar la codificación.
        String strLineLast = arrstrPrepareDe[arrstrPrepareDe.length - 1];
        if (
                                                            //Tiene delimitador de fin.
            strLineLast.endsWith(this.strCOM_DE_OR_EM_END())
            )
        {
            strLineLast =
                strLineLast.substring(0, strLineLast.length() - this.strCOM_DE_OR_EM_END().length()) + " " +
                    this.strCOM_DE_OR_EM_END();

                                                            //Regresa la última línea al arreglo, ya sin espacios extra.
            arrstrPrepareDe[arrstrPrepareDe.length - 1] = Tools.strTrimExcel(strLineLast);
        }

                                                            //Regresa el arreglo ya transformado
        return arrstrPrepareDe;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subCoincidence(                     //Procesa una coincidencia.
    
                                                            //Contiene un elemento por cada línea de comentarios 
                                                            //      estandar.
        String[] arrstrStandard_I,  
                                                            //Posición en la que inicia la búsqueda de la coincidencia 
                                                            //      cuando detecta la coincidencia regresa esta 
                                                            //      posición, si detecta que ya no hubo coincidencia, 
                                                            //      regresa la última posición + 1.
        Oint ointStandard_IO,
                                                            //Contiene un elemento por cada línea de comentarios 
                                                            //      original.
        String[] arrstrOriginal_I,  
                                                            //Posición en la que inicia la búsqueda de la coincidencia 
                                                            //      cuando detecta la coincidencia regresa esta 
                                                            //      posición, si detecta que ya no hubo coincidencia, 
                                                            //      regresa la última posición + 1.
         Oint ointOriginal_IO
        )
    {
                                                            //Niveles de profundidad (cantidad de elementos que 
                                                            //      involucra para buscar una coincidencia).
                                                            //Avanza en niveles de profundidad, con NP=0 involucra 1 
                                                            //      elemento, con NP=1, 2, con NP=2, 3, etc.
        int intDeepLevel = 0;

        Obool oboolFound = new Obool();
        do
        {
            ComCommentsAbstract.subDeepLevel(oboolFound, arrstrStandard_I, ointStandard_IO, arrstrOriginal_I,
                ointOriginal_IO, intDeepLevel);

            intDeepLevel = intDeepLevel + 1;
        }
        /*REPEAT-UNTIL*/
        while (!(
                                                            //Ya encontro coincidencia.
            oboolFound.v ||
                                                            //Llega al final en las 2 listas y no se puede procesar el
                                                            //      siguiente nivel de profundidad por exceder el tamaño
                                                            //      de las listas.
                ((ointStandard_IO.v + intDeepLevel) >= arrstrStandard_I.length) &&
                    ((ointOriginal_IO.v + intDeepLevel) >= arrstrOriginal_I.length)
        ));

        if (
            !oboolFound.v
            )
        {
                                                            //Posiciona los índices en última posición + 1.
            ointStandard_IO.v = arrstrStandard_I.length;
            ointOriginal_IO.v = arrstrOriginal_I.length;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subDeepLevel(                       //Busca la coincidencia con un nivel de profundidad. 
    
                                                            //Indica si encontro coincidencia.
        Obool oboolFound_O,
                                                            //Contiene un elemento por cada línea de comentarios 
                                                            //      estandar.
        String[] arrstrStandard_I,
                                                            //Posición en la que inicia la búsqueda de la coincidencia
                                                            //      coincidencia, cuando detecta la coincidencia regresa
                                                            //      esta posición.
        Oint ointStandard_IO,
                                                            //Contiene un elemento por cada línea de comentarios 
                                                            //      original.
        String[] arrstrOriginal_I,
                                                            //Posición en la que inicia la búsqueda de la coincidencia
                                                            //      coincidencia, cuando detecta la coincidencia regresa
                                                            //      esta posición.
        Oint ointOriginal_IO,
                                                            //Indica el nivel de profundidad para analizar.
        int intDeepLevel_I
        )
    {
                                                            //Nivel dentro del nivel de profundidad.
                                                            //Se cicla en cada nivel dentro del nivel de profundidad 
                                                            //      buscando la coincidencia.
        int intLevel = 0;
        do
        {
            ComCommentsAbstract.subLevel(oboolFound_O, arrstrStandard_I, ointStandard_IO, arrstrOriginal_I,
                ointOriginal_IO, intDeepLevel_I, intLevel);

            intLevel = intLevel + 1;
        }
        /*REPEAT-UNTIL*/
        while (!(
                                                            //Encontró coincidencia.
            oboolFound_O.v ||
                                                            //Acabó con todos los niveles posibles dentro del nivel de
                                                            //      profundidad.
                (intLevel > intDeepLevel_I)
        ));
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subLevel(                           //Busca la coincidencia con un nivel dentro de un nivel de
                                                            //      profundidad.
    
                                                            //Indica si encontro coincidencia.
        Obool oboolFound_O,
                                                            //Contiene un elemento por cada línea de comentarios 
                                                            //      estandar.
        String[] arrstrStandard_I,
                                                            //Posición en la que inicio la búsqueda de la coincidencia, 
                                                            //      cuando detecta la coincidencia regresa esta 
                                                            //      posición.
        Oint ointStandard_IO,
                                                            //Contiene un elemento por cada línea de comentarios 
                                                            //      original.
        String[] arrstrOriginal_I,
                                                            //Posición en la que inicio la búsqueda de la coincidencia, 
                                                            //      cuando detecta la coincidencia regresa esta 
                                                            //      posición.
        Oint ointOriginal_IO,
                                                            //Indica el nivel de profundidad para analizar.
        int intDeepLevel_I,
                                                            //Indica el nivel dentro del nivel de produndidad para 
                                                            //      analizar.
        int intLevel_I
        )
    {
        /*CASE*/
        if (
            ((ointStandard_IO.v + intLevel_I) < arrstrStandard_I.length) &&
                ((ointOriginal_IO.v + intDeepLevel_I) < arrstrOriginal_I.length) &&
                (arrstrStandard_I[ointStandard_IO.v + intLevel_I].equals(
                    arrstrOriginal_I[ointOriginal_IO.v + intDeepLevel_I]))
            )
        {
                                                            //Encontró coincidencia en Standard(N) con Original(NP),
                                                            //      indica que encontró y posiciona índices.
            oboolFound_O.v = true;
            ointStandard_IO.v = ointStandard_IO.v + intLevel_I;
            ointOriginal_IO.v = ointOriginal_IO.v + intDeepLevel_I;
        }
        else if (
            ((ointStandard_IO.v + intDeepLevel_I) < arrstrStandard_I.length) &&
                ((ointOriginal_IO.v + intLevel_I) < arrstrOriginal_I.length) &&
                (arrstrStandard_I[ointStandard_IO.v + intDeepLevel_I].equals(
                    arrstrOriginal_I[ointOriginal_IO.v + intLevel_I]))
            )
        {
                                                            //Encontró coincidencia en Standard(NP) con Original(N),
                                                            //      indica que encontró y posiciona índices.
            oboolFound_O.v = true;
            ointStandard_IO.v = ointStandard_IO.v + intDeepLevel_I;
            ointOriginal_IO.v = ointOriginal_IO.v + intLevel_I;
        }
        else
        {
            oboolFound_O.v = false;
        }
        /*END-CASE*/
    }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void subReset()
    {
        this.codStandard_Z = null;
        this.arrboolOriginalOK_Z = null;
        this.arrboolStandardOK_Z = null;
        super.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(TestoptionEnum testoptionSHORT)
    {
        return super.strTo(TestoptionEnum.SHORT) + ", " + Tes2.strTo(this.comtype(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.codOriginal(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.arrpar(), TestoptionEnum.SHORT);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo()
    {
        final String strCLASS = "Com";

        return strCLASS + "{" + Tes2.strTo(this.comtype(), "comtype") + ", " +
            Tes2.strTo(this.codOriginal(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.arrpar(), "arrpar") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    protected ComCommentsAbstract()
    {
        super(true);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TASK Com3 ComCommentsAbstract(cod)*/
    //------------------------------------------------------------------------------------------------------------------
    protected ComCommentsAbstract(                          //Construye objeto comentario con un código que ya tiene las
                                                            //      líneas de comentarios (parte abstracta).
                                                            //this.*[O], asigna valores.

                                                            //Código del comentario.
        CodCodeAbstract codCom_T
        )
    {
        super(false);
        
        if (
            codCom_T == null
            )
            Tools.subAbort(Tes2.strTo(codCom_T, "codCom_T") + " must have arrboolOriginalOK value");

                                                            //Guarda el código de comentarios.
        this.codOriginal_Z = codCom_T;

                                                            //Procesa conforme al tipo de comentario.
        /*CASE*/
        if (
            codCom_T.codtype() == CodtypeEnum.COM_FULL_LINE
            )
        {
            this.comtype_Z = ComtypeEnum.FULL_LINE;
            this.arrpar_Z = this.arrparFlOrEl(this.strCOM_FL_OR_TL().length());
        }
        else if (
            codCom_T.codtype() == CodtypeEnum.COM_END_OF_LINE
            )
        {
            this.comtype_Z = ComtypeEnum.END_OF_LINE;
            this.arrpar_Z = this.arrparFlOrEl(this.strCOM_EL().length());
        }
        else if (
            codCom_T.codtype() == CodtypeEnum.COM_TAG_FULL_LINE
            )
        {
            this.comtype_Z = ComtypeEnum.TAG_FULL_LINE;
            this.arrpar_Z = this.arrparTl();
        }
        else if (
            codCom_T.codtype() == CodtypeEnum.COM_DELIMITED
            )
        {
            this.comtype_Z = ComtypeEnum.DELIMITED;
            this.arrpar_Z = this.arrparDe();
        }
        else if (
            codCom_T.codtype() == CodtypeEnum.COM_EMBEDED
            )
        {
            this.comtype_Z = ComtypeEnum.EMBEDED;
            this.arrpar_Z = this.arrparEm();
        }
        else
        {
            if (
                true
                )
                Tools.subAbort(Tes2.strTo(codCom_T.codtype(), "codCom_T.codtype()") + " was not found");
        }
        /*END-CASE*/
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private ParParagraphAbstract[] arrparFlOrEl(            //Con las líneas de código de un com de tipo
                                                            //      FULL_LINE o END_OF_LINE genera el arreglo
                                                            //      de párrafos.
                                                            //this[I], toma información del código.

                                                            //Posición en la que inician la información de los
                                                            //      comentarios, esto es después de la marca de inicio
                                                            //      de FULL_LINE o END_OF_LINE.
        int intSTART_INFO_COM_I
                                                            //arrpar, regresa arreglo con todos los párrafos.
        )
    {
                                                            //Crea una lista de párrafos vacía, luego la convierte a
                                                            //      arreglo.
        LinkedList<ParParagraphAbstract> lstparFlOrEl = new LinkedList<ParParagraphAbstract>();

        Oint ointLine = new Oint(0);
        /*WHILE-DO*/
        while (
                                                            //Aún no es fin de la líneas de comentarios.
            ointLine.v < this.codOriginal().arrstrLine().length
            )
        {
                                                            //Genera un párrafo.
            Oobject<ParParagraphAbstract> parFlOrEl = new Oobject<ParParagraphAbstract>();
            this.subReturnOneParagraphFlOrEl(parFlOrEl, intSTART_INFO_COM_I, ointLine);

            lstparFlOrEl.add(parFlOrEl.v);

                                                            //Avanza al inicio del siguiente párrafo.
            ointLine.v = ointLine.v + 1;
        }

                                                            //Elimina parem si están al principio o al final.
        if (
            lstparFlOrEl.get(0) instanceof ParemEmpty
            )
        {
            lstparFlOrEl.remove(0);
        }
        if (
                                                            //Nótese que la lista pudo haber quedado vacía, debe tener
                                                            //      al menos 1 elemento.
            (lstparFlOrEl.size() > 0) &&
            lstparFlOrEl.get(lstparFlOrEl.size() - 1) instanceof ParemEmpty
            )
        {
            lstparFlOrEl.remove(lstparFlOrEl.size() - 1);
        }

        return lstparFlOrEl.toArray(new ParParagraphAbstract[lstparFlOrEl.size()]);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subReturnOneParagraphFlOrEl(               //Regresa un párrafo con la información de un conjunto de
                                                            //      líneas de comentarios que corresponden a un párrafo.
                                                            //this[I], toma información del código.

                                                            //Regresa el párrafo construído del tipo correcto.
        Oobject <ParParagraphAbstract> oparFlOrEl_O,
                                                            //Posición en la que inicia la información de los
                                                            //      comentarios, esto es después de la marca de inicio
                                                            //      de FULL_LINE o END_OF_LINE.
        int intSTART_INFO_COM_I,
                                                            //Línea en la que inicia el párrafo en el código, debe
                                                            //     regresar la línea donde concluye el párrafo.
        Oint ointLine_IO
        )
    {
                                                            //Guarda la primer línea del párrafo para facilitar la 
                                                            //      codificación.
        String strLineParFirst = this.codOriginal().arrstrLine()[ointLine_IO.v].substring(intSTART_INFO_COM_I);

                                                            //Procesa conforme al tipo de párrafo.
        /*CASE*/
        if (
                                                            //Tiene el caracter que indica que inicia un párrafo 
                                                            //      estructurado.
            (strLineParFirst.length() > 0) && (strLineParFirst.charAt(0) == this.charPARST_START())
            )
        {
            this.subReturnParst(oparFlOrEl_O, intSTART_INFO_COM_I, ointLine_IO);
        }
        else if (
                                                            //La línea es útil de acuerdo a los caracteres útiles para
                                                            //      comentarios.
            this.boolIsUsefulLineInParun(strLineParFirst)
            )
        {
            this.subReturnParun(oparFlOrEl_O, intSTART_INFO_COM_I, ointLine_IO);
        }
        else
        {
            this.subReturnParem(oparFlOrEl_O, intSTART_INFO_COM_I, ointLine_IO);
        }
        /*END-CASE*/
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subReturnParst(                            //Regresa un parst.
                                                            //this[I], toma información del código.

        Oobject <ParParagraphAbstract> oparst_O,
                                                            //Posición en la que inicia la información de los
                                                            //      comentarios, esto es después de la marca de inicio
                                                            //      de FULL_LINE o END_OF_LINE.
        int intSTART_INFO_COM_I,
                                                            //Línea en la que inicia el párrafo en el código, debe
                                                            //     regresar la línea donde concluye el párrafo.
        Oint ointLine_IO
        )
    {
        LinkedList<String> lststrLineParst = new LinkedList<String>();

        String strLineParst;
        do
        {
            strLineParst = this.codOriginal().arrstrLine()[ointLine_IO.v].substring(intSTART_INFO_COM_I);
            lststrLineParst.add(strLineParst);

            ointLine_IO.v = ointLine_IO.v + 1;
        }
        /*REPEAT-UNTIL*/
        while (!(
            (ointLine_IO.v >= this.codOriginal().arrstrLine().length) ||
                                                            //La última línea cerró el parst.
            strLineParst.startsWith(Character.toString(this.charPARST_END()))
            ));

        oparst_O.v = new ParstStructured(this, lststrLineParst.toArray(new String[lststrLineParst.size()]));

                                                            //Regresa a la última línea del párrafo.
        ointLine_IO.v = ointLine_IO.v - 1;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subReturnParun(                            //Regresa un parun.
                                                            //this[I], toma información del código.

        Oobject <ParParagraphAbstract> oparun_O,
                                                            //Posición en la que inicia la información de los
                                                            //      comentarios, esto es después de la marca de inicio
                                                            //      de FULL_LINE o END_OF_LINE.
        int intSTART_INFO_COM_I,
                                                            //Línea en la que inicia el párrafo en el código, debe
                                                            //     regresar la línea donde concluye el párrafo.
        Oint ointLine_IO
        )
    {
        LinkedList<String> lststrLineParun = new LinkedList<String>();

        String strLineParun;
        String strLineParNext;
        do
        {
            strLineParun = this.codOriginal().arrstrLine()[ointLine_IO.v].substring(intSTART_INFO_COM_I);
            lststrLineParun.add(strLineParun);

            ointLine_IO.v = ointLine_IO.v + 1;

                                                            //To easy code
            if (
                ointLine_IO.v < this.codOriginal().arrstrLine().length
                )
            {
                strLineParNext = this.codOriginal().arrstrLine()[ointLine_IO.v].substring(intSTART_INFO_COM_I);
            }
            else
            {
                strLineParNext = null;
            }
        }
        /*REPEAT-UNTIL*/
        while (!(
            (ointLine_IO.v >= this.codOriginal().arrstrLine().length) ||
                                                            //La última línea ya fue el fin del parun.
            strLineParun.endsWith(".") || strLineParun.endsWith(":") ||
                                                            //Sigue parst.
            strLineParNext.startsWith(Character.toString(this.charPARST_START())) ||
                                                            //Sigue parem.
            !this.boolIsUsefulLineInParun(strLineParNext)
            ));

        oparun_O.v = new ParunUnstructured(this, lststrLineParun.toArray(new String[lststrLineParun.size()]));

                                                            //Regresa a la última línea del párrafo.
        ointLine_IO.v = ointLine_IO.v - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subReturnParem(                            //Regresa un parem.
                                                            //this[I], toma información del código.

        Oobject <ParParagraphAbstract> oparem_O,
                                                            //Posición en la que inicia la información de los
                                                            //      comentarios, esto es después de la marca de inicio
                                                            //      de FULL_LINE o END_OF_LINE.
        int intSTART_INFO_COM_I,
                                                            //Línea en la que inicia el párrafo en el código, debe
                                                            //     regresar la línea donde concluye el párrafo.
        Oint ointLine_IO
        )
    {
                                                            //Avanza todas las líneas de parem.
        /*LOOP*/
        while (true)
        {
                                                            //To easy code
            String strLinePar;
            if (
                ointLine_IO.v < this.codOriginal().arrstrLine().length
                )
            {
                strLinePar = this.codOriginal().arrstrLine()[ointLine_IO.v].substring(intSTART_INFO_COM_I);
            }
            else
            {
                strLinePar = null;
            }

            /*EXIT-IF*/
            if (
                (ointLine_IO.v >= this.codOriginal().arrstrLine().length) ||
                                                            //Sigue parst.
                strLinePar.startsWith(Character.toString(this.charPARST_START())) ||
                                                            //Sigue parun.
                this.boolIsUsefulLineInParun(strLinePar)
                )
                break;

            ointLine_IO.v = ointLine_IO.v + 1;
        }
        /*END-LOOP*/

        oparem_O.v = new ParemEmpty(this);

                                                            //Regresa a la última línea del párrafo.
        ointLine_IO.v = ointLine_IO.v - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public boolean boolIsUsefulLineInParun(                 //Evalua si la línea del párrafo tiene al menos un caracter
                                                            //      de los requerido para ser línea útil en parun.
                                                            //bool, true si la línea es util en parun.
                                                            //this[I], solo para saber el tipo de código.

                                                            //Parte de la línea que tiene información de párrafo.
        String strLinePar_I
        )
    {
                                                            //Proceso uno por uno los caracteres de la línea del
                                                            //       hasta encontrar un carácter que sea útil
                                                            //      o terminar.
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
                                                            //Ya acabo con todos los caracteres.
            (intI >= strLinePar_I.length()) ||
                                                            //Encontró caracter útil.
            (Arrays.binarySearch(this.arrcharREQUIRED_IN_PARUN(), strLinePar_I.charAt(intI)) >= 0)
            ))
        {
            intI = intI + 1;
        }

        return (
                                                            //Salió antes de terminar, SI encontró.
            intI < strLinePar_I.length()
            );
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private ParParagraphAbstract[] arrparTl(                //La estructura del código del com TAG_FULL_LINE es:
                                                            //(línea única)* <párrafo no estructurado>.
                                                            //Nótese que < y > deben ser los caracteres correspondientes
                                                            //      al lenguaje y que también puede tener espacios.
                                                            //arrpar, regresa arreglo de 1 párrafo.
                                                            //this[I], toma información del código.
        )
    {
                                                            //Todo el conjunto es un solo párrafo no estructurado
                                                            //      chiquito de una sola línea, lo extrae.

                                                            //Toma la línea 0 para facilitar la codificación (le quita
                                                            //      marca de inicio de comentarios).
        String strLineParun = this.codOriginal().arrstrLine()[0].substring(this.strCOM_FL_OR_TL().length());

                                                            //Forma cod del párrafo no estructurado.
        String[] arrstrLineParun = { strLineParun };

        ParunUnstructured parun = new ParunUnstructured(this, arrstrLineParun);

        return new ParParagraphAbstract[] { parun };
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private ParParagraphAbstract[] arrparDe(                //La estructura del código del com DELIMITED es:
                                                            //(primera línea)/*párrafo no estructurado.
                                                            //(líneas intermedias)todo el conjunto es un párrafo
                                                            //      estructurado.
                                                            //(última línea)párrafo no estructurado */. (si no tiene la
                                                            //      marca */ este párrafo no estructurado será sin
                                                            //      palabras y la última línea será la última del
                                                            //      conjunto de líneas intermedias).
                                                            //Nótese que /* y */ deben ser las marcas correspondientes
                                                            //      al lenguaje.
                                                            //this[I], toma información del código.

                                                            //arrpar, regresa arreglo de 3 párrafos.
        )
    {
                                                            //To easy code
        String[] arrstrLine = this.codOriginal().arrstrLine();
        String strLineLast = arrstrLine[arrstrLine.length - 1];
        boolean boolHasEndMark = (
            strLineLast.endsWith(this.strCOM_DE_OR_EM_END())
            );

                                                            //Forma cod de primer párrafo, es no estructurado.
                                                            //Será la primer línea quitandole la marca de inicio.
        String strLineParun0 = arrstrLine[0].substring(this.strCOM_DE_OR_EM_START().length());
        String[] arrstrLineParun0 = { strLineParun0 };

        ParunUnstructured parun0 = new ParunUnstructured(this, arrstrLineParun0);

                                                            //Calcula la cantidad de líneas intermedias, nótese que pudo
                                                            //      haber cerrando o no.
        int intLinesParst1;
        if (
            boolHasEndMark
            )
        {
            intLinesParst1 = arrstrLine.length - 2;
        }
        else
        {
            intLinesParst1 = arrstrLine.length - 1;
        }

                                                            //Forma cod de segundo párrafo, las líneas intermedias en 
                                                            //      conjunto son un párrafo estructurado.
        String[] arrstrLineParst1 = new String[intLinesParst1];
        System.arraycopy(arrstrLine, 1, arrstrLineParst1, 0, intLinesParst1);

        ParstStructured parst1 = new ParstStructured(this, arrstrLineParst1);

                                                            //Si terminó con marca de fin toma la info de la última 
                                                            //      línea, sino se toma una línea vacía.   
        String strLineParun2;
        if (
            boolHasEndMark
            )
        {
                                                            //Se tomará la información de la última línea, sin */
            strLineParun2 = strLineLast.substring(0, strLineLast.length() - this.strCOM_DE_OR_EM_END().length());
        }
        else
        {
                                                            //Se toma una línea vacía.
            strLineParun2 = "";
        }

                                                            //Forma cod de último párrafo, es no estructurado.
        String[] arrstrLineParun2 = { strLineParun2 };

        ParunUnstructured parun2 = new ParunUnstructured(this, arrstrLineParun2);

        return new ParParagraphAbstract[] { parun0, parst1, parun2 };
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private ParParagraphAbstract[] arrparEm(                //La estructura del código del com EMBEDED es:
                                                            //(línea única)/*párrafo no estructurado*/ (puede no tener
                                                            //      la marca */).
                                                            //Nótese que /* y */ deben ser las marcas correspondientes
                                                            //      al lenguaje.
                                                            //arrpar, regresa arreglo de 1 párrafo.
                                                            //this[I], toma información del código.
        )
    {
                                                            //Todo el conjunto es un solo párrafo no estructurado 
                                                            //      chiquito de una sola línea, lo extrae, puede tener o
                                                            //      no la marca de fin.

                                                            //Toma la línea 0 para facilitar la codificación.
        String strLineCom = this.codOriginal().arrstrLine()[0];

                                                            //Long de la línea quitando las marcas de inicio y fin, se 
                                                            //      inicia quitando por lo pronto la marca de inicio.
        int intLengthLineParun = strLineCom.length() - this.strCOM_DE_OR_EM_START().length();
        if (
                                                            //Si tiene marca de fin.
            strLineCom.endsWith(this.strCOM_DE_OR_EM_END())
            )
        {
                                                            //Le quita la marca de fin.
            intLengthLineParun = intLengthLineParun - this.strCOM_DE_OR_EM_END().length();
        }

                                                            //Forma cod del párrafo no estructurado.
                                                            //En version C# no es necesario sumar
                                                            //      this.strCOM_DE_OR_EM_START().length en el segundo
                                                            //      parámetro de substring; sin embargo en Java es asi
                                                            //      por la diferencia en el metodo substring en Java a
                                                            //      comparación de C#.
        String strLineParun = strLineCom.substring(this.strCOM_DE_OR_EM_START().length(),
            intLengthLineParun + this.strCOM_DE_OR_EM_START().length());
        String[] arrstrLineParun = { strLineParun };

        ParunUnstructured parun = new ParunUnstructured(this, arrstrLineParun);

        return new ParunUnstructured[] { parun };
    }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    public abstract ComCommentsAbstract comxxNew(CodCodeAbstract codCom_T);

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    /*TASK Com4 subfunSearchWord*/
    //------------------------------------------------------------------------------------------------------------------
    public void subfunSearchWord(                           //Busca palabra en comentarios.

                                                            //this[I], datos del com.

                                                            //Número de párrafo (base 0) donde se encontro, -1 si no
                                                            //      encontro.
        Oint ointParagraph_O,
                                                            //Posición dentro del párrafo donde se encontro, -1 si no
                                                            //      encontro, si la encuentra en un párrafo
                                                            //      estructurado, por cada línea del párrafo
                                                            //      estructurado añade 1,000 posiciones (Ej. si se
                                                            //      encontró en la posición 28 de la tercera línea,
                                                            //      reportará 2,028).
        Oint ointChar_O,
                                                            //Palabra a buscar.
        String strWord_I
        )
    {
        this.subVerifyObjectConstructionIsFinished();

        if (
            strWord_I == null
            )
           Tools.subAbort(Tes2.strTo(strWord_I, "strWord_I") + " can not be null");
        if (
            strWord_I.equals("")
            )
            Tools.subAbort(Tes2.strTo(strWord_I, "strWord_I") + " can not be empty");
        if (
            strWord_I.contains(" ")
            )
            Tools.subAbort(Tes2.strTo(strWord_I, "strWord_I") + " is not a WORD, contains at least 1 space");

        ointParagraph_O.v = 0;

        /*LOOP*/
        while (true)
        {
                                                            //To easy code
                                                            //Busca la palabra en el párrafo.
            if (
                ointParagraph_O.v < this.arrpar().length
                )
            {
                ointChar_O.v = this.arrpar()[ointParagraph_O.v].intSearchWord(strWord_I);
            }
            else
            {
                                                            //Indica que no encontró.
                ointChar_O.v = -1;
            }

            /*EXIT-IF*/
            if (
                (ointParagraph_O.v >= this.arrpar().length) ||
                                                            //Encontró palabra en el párrafo.
                (ointChar_O.v >= 0)
                )
                break;

            ointParagraph_O.v = ointParagraph_O.v + 1;
        }
        /*END-LOOP*/

                                                            //Corrige la posición del párrafo.
        if (
                                                            //No encontró palabra.
            ointParagraph_O.v >= this.arrpar().length
            )
        {
                                                            //Indica que no encontró.
            ointParagraph_O.v = -1;
            ointChar_O.v = -1;
        }
    }
    /*END-TASK*/
}
/*END-TASK*/
