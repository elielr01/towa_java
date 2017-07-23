/*TASK RPS.Cod Relevant Part Code*/
package Cod;

import Tech.*;
import Ti.*;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

//															//AUTHOR: Towa (EOG-Eduardo Ojeda).
//															//CO-AUTHOR: Towa (GLG-Gerardo Lopez).
//															//DATE: 23-Julio-2012.
//															//PURPOSE:
//															//Especificación de clase Abstract para Código.


//======================================================================================================================
public abstract class CodCodeAbstract extends BclassBaseClassAbstract
//                                                          //Base para clases CodxxXxxxx. (xx será cb para COBOL, oo
//                                                          //      para Object-Oriented (C#, Java, etc.) sp para Stored
//                                                          //      Program, etc.).
//                                                          //Código para cualquier lenguaje.
//                                                          //Comunmente las columnas de código se numeran 1, 2, etc
//                                                          //      (base 1), sin embargo internamente la "posición" es
//                                                          //      0, 1, etc. (base 0), en toda la información que se
//                                                          //      deba recolectar respecto a esto deberá ser la
//                                                          //      POSICIÓN (base 0).
//                                                          //En forma similar, la información que se tenga que
//                                                          //      recolectar respecto a la línea, también será en base
//                                                          //      0.
//                                                          //Internamente en SoftwareAutomation (y otras aplicaciónes
//                                                          //      que se soportan en esta) TODO SERÁ EN BASE 0. Si
//                                                          //      para algún efecto externo se debe reportar en base
//                                                          //      1, hasta ese momento se convierte la base.

{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    @Override
    public BclassmutabilityEnum bclassmutability() { return BclassmutabilityEnum.INMUTABLE; }

    public abstract  CodCodeAbstract codDUMMY();

    public abstract T4techInfoTuple t4techINFO();

    //                                                      //strCHAR_USEFUL, caracteres útiles en el lenguaje que no
    //                                                      //      deberán ser convertidos.
    //                                                      //Los blancos tampoco debrán ser convertidos.
    //                                                      //
    //                                                      //arrcharUSEFUL_Z, lo anterior convertido a arreglo y
    //                                                      //      ordenado.
    public abstract char[] arrcharUSEFUL();

    //                                                      //strCHAR_TO_CONVERT_AND_CONVERSION, pares de caracteres,
    //                                                      //      donde el carácter en posición par (0, 2, etc.)
    //                                                      //      deberá ser convertido al siguiente carácter (1, 3,
    //                                                      //      etc.), para poder ser procesado.
    //                                                      //arrcharTO_CONVERT_Z, caracteres en posición par
    //                                                      //      ordenados.
    //                                                      //arrcharCONVERSION_Z, caracteres en posición impar
    //                                                      //      ordenados por el anterior.
    //                                                      //Si un caracter no se encuentra en arrcharUSEFUL ni
    //                                                      //      en arrcharTO_CONVERT se considera un CARACTER NO
    //                                                      //      PREVISTO, se debe cambiar a blanco y desplegar un
    //                                                      //      mensaje indicando línea, posicion, caracter y
    //                                                      //      caracter en hexadecimal para tener idea de donde
    //                                                      //      proviene y corregir la situación.
    public abstract char[] arrcharTO_CONVERT();
    public abstract char[] arrcharCONVERSION();

    //                                                      //Un objeto cod puede se construído a partir de un sysfile o
    //                                                      //      directamente con un arrstr.
    //                                                      //Para ser construido con un sysfile requiere las siguientes
    //                                                      //      constantes.

    //                                                      //true indica que si se podrán construir objetos a partir de
    //                                                      //      syspath.
    public boolean boolCONSTRUCT_FROM_SYSPATH() { return false; }

    //                                                      //If useful code start after position 0, a filler is
    //                                                      //      requires (Example, Cobol code requires a filler of
    //                                                      //      6 characters).
    //                                                      //This will be used to reproduce a code file.
    //                                                      //This filler also indicate where useful code start.
    //                                                      //This filler should be at most 10 characters.
    //                                                      //COBOL start after 6 chars and object oriented after 0.
    public String strSTART_FILLER() { return null; }

    //                                                      //Indica si de acuerdo a las reglas del lenguaje las líneas
    //                                                      //      de código deben ser de longitud fija (Ej. en COBOL
    //                                                      //      es true, en Object Oriented es false).
    public boolean boolIS_FIX_LENGTH() { return false; }

    //                                                      //Longitud máxima de la línea de acuerdo al estándar
    //                                                      //      establecido, nótese que en realidad QEnabler
    //                                                      //      aceptará que las líneas se hayan capturado en
    //                                                      //      registros de cualquier longitúd, sin embargo, si el
    //                                                      //      lenguaje requiere que sean de longitud fija, la
    //                                                      //      información posterior a la longitud máxima será
    //                                                      //      ignorada <posiciones >= a intINI_POS_CODIGO +
    //                                                      //      intLONG_LIN_MAX> (Ej. en COBOL es 66, y dado que el
    //                                                      //      lenguaje no acepta longitud variable, las posiciones
    //                                                      //      72 en adelante <6 + 66 = 72> serán ignoradas, en
    //                                                      //      Object Oriented es 120, sin embargo el lenguaje
    //                                                      //      acepta longitud variable por lo cual no se ignora
    //                                                      //      ninguna posición al final de las líneas).
    //                                                      //Debe ser entre 50 y 150.
    public int intLINE_MAX_LENGTH() { return 0; }

    //--------------------------------------------------------------------------------------------------------------
    /*SUPPORT METHODS FOR CONSTANTS*/

    //--------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT CONSTRUCTORS*/

    /*TASK Cod1 subPrepareConstants*/
    //------------------------------------------------------------------------------------------------------------------
    protected static void subPrepareConstants(
        //                                                  //LOS CONSTRUCTORES ESTÁTICOS DE LAS CLASES CodxxXxxxx
        //                                                  //      DEBEN LLAMAR A ESTE MÉTODO PARA PREPARAR SU
        //                                                  //      INFORMACIÓN.
        //                                                  //1. Ordena caracteres útiles.
        //                                                  //2. Separa y ordena caracteres a convertir.
        //                                                  //3. Valida constantes asociadas a la construcción con
        //                                                  //      syspath.
        //                                                  //4. Valida comDUMMY.
        //                                                  //5. Valida ltokDUMMY.
        //                                                  //Se debe verificar que los parámetros sean consistentes.
        //                                                  //strCHAR_USEFUL debe incluir blank, . : and ?

        //                                                  //Para tomar las constantes de la clase concreta.
        CodCodeAbstract codDUMMY_I,

        //                                                  //Caracteres útiles en el lenguage.
        String strCHAR_USEFUL_I,
        //                                                  //Caracteres USEFUL ordenados.
        Oarrchar oarrcharUSEFUL_O,
        //                                                  //Pares de caracteres, donde el carácter en posición par (0,
        //                                                  //      2, etc.) deberá ser convertido al siguiente carácter
        //                                                  //      (1, 3, etc.), para poder ser procesado. Los
        //                                                  //      caracteres en posición par no están en arrcharUSEFUL
        //                                                  //      y los caracteres en posición impar si están en
        //                                                  //      arrcharUSEFUL.
        String strCHAR_TO_CONVERT_AND_CONVERSION_I,
        //                                                  //Arreglos de caracteres TO_CONVERT y CONVERSION
        //                                                  //      ordenados por el primer arreglo.
        Oarrchar oarrcharTO_CONVERT_O,
        Oarrchar oarrcharCONVERSION_O
        )
    {
        if (
            codDUMMY_I == null
            )
            Tes3.subAbort(Tes3.strTo(codDUMMY_I, "codDUMMY_I") + " can not be null");
        if (
            !codDUMMY_I.boolIsDummy()
            )
            Tes3.subAbort(Tes3.strTo(codDUMMY_I, "codDUMMY_I") + " should be a DUMMY object");

        CodCodeAbstract.subPrepareUseful(codDUMMY_I, strCHAR_USEFUL_I, oarrcharUSEFUL_O);
        CodCodeAbstract.subPrepareToConvertAndConversion(codDUMMY_I, strCHAR_TO_CONVERT_AND_CONVERSION_I,
            oarrcharTO_CONVERT_O, oarrcharCONVERSION_O);
        CodCodeAbstract.subPrepareFromSyspath(codDUMMY_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareUseful(
        //                                                  //Ordena caracteres útiles.

        //                                                  //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I,
        //                                                  //Caracteres útiles en el lenguage.
        String strCHAR_USEFUL_I,
        //                                                  //Caracteres USEFUL ordenados.
        Oarrchar oarrcharUSEFUL_O
        )
    {
        if (
            strCHAR_USEFUL_I == null
            )
            Tes3.subAbort(Tes3.strTo(strCHAR_USEFUL_I, "strCHAR_USEFUL_I") + " can not be null");
        if (
            strCHAR_USEFUL_I == ""
            )
            Tes3.subAbort(Tes3.strTo(strCHAR_USEFUL_I, "strCHAR_USEFUL_I") + " must have information");

        oarrcharUSEFUL_O.v = strCHAR_USEFUL_I.toCharArray();
        Arrays.sort(oarrcharUSEFUL_O.v);

        Tools.subVerifyDuplicate(oarrcharUSEFUL_O.v, "oarrcharUSEFUL_O.v");

        //                                                  //Verifica que espacio . : ? sean USEFULL.
        final String strCHAR_REQUIRED_IN_CHAR_USEFUL = " .:?";
//        codDUMMY_I.subVerifyUseful(strCHAR_REQUIRED_IN_CHAR_USEFUL, "strCHAR_REQUIRED_IN_CHAR_USEFUL");
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareToConvertAndConversion(
        //                                                  //Separa y ordena caracteres a convertir.

        //                                                  //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I,
        //                                                  //Pares de caracteres.
        String strCHAR_TO_CONVERT_AND_CONVERSION_I,
        //                                                  //Arreglos de caracteres TO_CONVERT y CONVERSION
        //                                                  //      ordenados por el primer arreglo.
        Oarrchar oarrcharTO_CONVERT_O,
        Oarrchar oarrcharCONVERSION_O
        )
    {
        if (
            strCHAR_TO_CONVERT_AND_CONVERSION_I == null
            )
            Tes3.subAbort(Tes3.strTo(strCHAR_TO_CONVERT_AND_CONVERSION_I, "strCHAR_TO_CONVERT_AND_CONVERSION_I") +
                " can not be null");
        if (
            //                                              //Longitud del String es impar (este String debe contener
            //                                              //      pares de caracteres).
            (strCHAR_TO_CONVERT_AND_CONVERSION_I.length() % 2) != 0
            )
            Tes3.subAbort(Tes3.strTo(strCHAR_TO_CONVERT_AND_CONVERSION_I, "strCHAR_TO_CONVERT_AND_CONVERSION_I") +
                " should be even length");

        //                                                  //Convierte a 2 arreglos de caracteres y los ordena por el
        //                                                  //      primero.
        oarrcharTO_CONVERT_O.v = new char[strCHAR_TO_CONVERT_AND_CONVERSION_I.length() / 2];
        oarrcharCONVERSION_O.v = new char[oarrcharTO_CONVERT_O.v.length];
        for (int intI = 0; intI < oarrcharTO_CONVERT_O.v.length; intI = intI + 1)
        {
            oarrcharTO_CONVERT_O.v[intI] = strCHAR_TO_CONVERT_AND_CONVERSION_I.charAt(intI * 2);
            oarrcharCONVERSION_O.v[intI] = strCHAR_TO_CONVERT_AND_CONVERSION_I.charAt(intI * 2 + 1);
        }
        Tools.sort(oarrcharTO_CONVERT_O.v, oarrcharCONVERSION_O.v);

        Tools.subVerifyDuplicate(oarrcharTO_CONVERT_O.v, "oarrcharTO_CONVERT_O.v");

        //                                                  //Verifica que NO SEAN USEFUL.
        for (int intI = 0; intI < oarrcharTO_CONVERT_O.v.length; intI = intI + 1)
        {
            if (
                Arrays.binarySearch(oarrcharCONVERSION_O.v, oarrcharTO_CONVERT_O.v[intI]) >= 0
                )
                Tes3.subAbort(
                    Tes3.strTo(new String(codDUMMY_I.arrcharUSEFUL()), "new String(codDUMMY_I.arrcharUSEFUL)") +
                        ", " + Tes3.strTo(oarrcharTO_CONVERT_O.v[intI], "arrcharTO_CONVERT_O[" + intI + "]") +
                        " has USEFUL characters");
        }

        //                                                  //Verifica que sean USEFUL.
//        codDUMMY_I.subVerifyUseful(oarrcharCONVERSION_O.v, "oarrcharCONVERSION_O.v");
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareFromSyspath(
        //                                                  //Verifica constantes.

        //                                                  //Para tomar las constantes del cod de la clase concreta.
        CodCodeAbstract codDUMMY_I
        )
    {
        //                                                  //To easy code
        boolean boolCONSTRUCT_FROM_SYSPATH = codDUMMY_I.boolCONSTRUCT_FROM_SYSPATH();
        String strSTART_FILLER = codDUMMY_I.strSTART_FILLER();
        int intLINE_MAX_LENGTH = codDUMMY_I.intLINE_MAX_LENGTH();

        if (
            boolCONSTRUCT_FROM_SYSPATH
            )
        {
            if (
                strSTART_FILLER == null
                )
                Tes3.subAbort(Tes3.strTo(strSTART_FILLER, "strSTART_FILLER") + " can not be null");
            if (
                strSTART_FILLER.length() > 10
                )
                Tes3.subAbort(Tes3.strTo(strSTART_FILLER, "strSTART_FILLER") + " can not be longer than 10 chars");

//            codDUMMY_I.subVerifyUseful(strSTART_FILLER, "strSTART_FILLER");

            if (
                (intLINE_MAX_LENGTH < 50) || (intLINE_MAX_LENGTH > 150)
                )
                Tes3.subAbort(Tes3.strTo(intLINE_MAX_LENGTH, "intLINE_MAX_LENGTH") +
                    " should be in the range 50-150");
        }
        else
        {
            if (
                strSTART_FILLER != null
                )
                Tes3.subAbort(Tes3.strTo(strSTART_FILLER, "strSTART_FILLER") + " should be null");
            if (
                intLINE_MAX_LENGTH != 0
                )
                Tes3.subAbort(Tes3.strTo(intLINE_MAX_LENGTH, "intLINE_MAX_LENGTH") + " should be 0");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    public void subVerifyUseful(
        //                                                  //Verifica que los caracteres sean USEFUL (aborta).
        //                                                  //this[I], to access arrcharUSEFUL.

        //                                                  //int, primer caracter NON USEFUL, -1 si no encontró

        //                                                  //Caracteres a analizar.
        String strToAnalize_I,
        //                                                  //Ej. strCOM_EL
        String strIdentifierstrToAnalize_I
        )
    {
        if (
            strToAnalize_I == null
            )
            Tes3.subAbort(Tes3.strTo(strToAnalize_I, strIdentifierstrToAnalize_I) + " can not be null");

        for (int intI = 0; intI < strToAnalize_I.length(); intI = intI + 1)
        {
            if (
                //                                          //Caracter NON USEFUL
                Arrays.binarySearch(this.arrcharUSEFUL(), strToAnalize_I.charAt(intI)) < 0
                )
                Tes3.subAbort(Tes3.strTo(strToAnalize_I, strIdentifierstrToAnalize_I) + ", " +
                    Tes3.strTo(intI, "intI") + " has NON USEFULL character");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void subVerifyUseful(
        //                                                  //Verifica que los caracteres sean USEFUL (aborta).
        //                                                  //this[I], to access arrcharUSEFUL.

        //                                                  //int, primer caracter NON USEFUL, -1 si no encontró

        //                                                  //Caracteres a analizar.
        char[] arrcharToAnalize_I,
        //                                                  //Ej. 
        String strIdentifierArrcharToAnalize_I
        )
    {
        if (
            arrcharToAnalize_I == null
            )
            Tes3.subAbort(Tes3.strTo(arrcharToAnalize_I, strIdentifierArrcharToAnalize_I) + " can not be null");

        for (int intI = 0; intI < arrcharToAnalize_I.length; intI = intI + 1)
        {
            if (
                //                                          //Caracter NON USEFUL
                Arrays.binarySearch(this.arrcharUSEFUL(), arrcharToAnalize_I[intI]) < 0
                )
                Tes3.subAbort(Tes3.strTo(arrcharToAnalize_I, strIdentifierArrcharToAnalize_I) + ", " +
                    Tes3.strTo(intI, "intI") + " has NON USEFULL character");
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    //                                                      //Indica la instancia de la tecnología.
    //                                                      //El valor puede ser null.
    //                                                      //Ej. Object Oriented, tiene (actualmente) las opciones
    //                                                      //      C#, Java, Objective-C y Swift.
    //                                                      //Normalmente este dato se puede deducir de nombre del file,
    //                                                      //      sin embargo, en ocasiones el mismo file extension
    //                                                      //      esta asociado a varias instancias por lo cual debe
    //                                                      //      proporcionado.
    //                                                      //Si se proporciona el dato, lo toma.
    //                                                      //Si no se proporcionao el dato, y tiene syspath lo deduce
    //                                                      //      del file.
    //                                                      //Si no tiene si syspath ni este dato, será null,

    private TechinstEnum techinst_Z;
    public TechinstEnum techinst() { return this.techinst_Z; }

    private CodtypeEnum codtype_Z;
    public CodtypeEnum codtype() { return this.codtype_Z; }

    //                                                      //Full path del archivo de código (Ej,
    //                                                      //      C:\Programas\MiPrograma.cobol).
    //                                                      //null for STANDARD_ELEMENT.
    private SyspathPath syspathFile_Z;
    public SyspathPath syspathFile() { return this.syspathFile_Z; }

    //                                                      //Un programa consta de 0 ó varias líneas.
    private String[] arrstrLine_Z;
    public String[] arrstrLine() { return this.arrstrLine_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void subResetOneClass()
    {
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(StrtoEnum testoptionSHORT_I)
    {
        return super.strTo(StrtoEnum.SHORT) + ", " + Tes3.strTo(this.techinst(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.codtype(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.syspathFile(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.arrstrLine(), StrtoEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        final String strCLASS = "Cod";

        return strCLASS + "{" + Tes3.strTo(this.techinst(), "techinst") + ", " + Tes3.strTo(this.codtype(), "codtype") +
            ", " + Tes3.strTo(this.syspathFile(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.arrstrLine(), "arrstrLine") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(                              //Crea objeto DUMMY.
    //                                                      //this.*[O], crea sin asignar nada.
    )
    {
        super(true);
    }

    /*TASK Cod2 CodAbstract(arrstr & file)*/
    //------------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(
        //                                                  //A partir de un arreglo de String genera el objeto (parte
        //                                                  //      abstracta).
        //                                                  //Aún cuando en alguna tecnologías se requiere que los
        //                                                  //      registros sean de longitud fija, en este constructor
        //                                                  //      pueden ser de cualquier longitud.
        //                                                  //Tambien, si la tecnología tiene una posición de inicio
        //                                                  //      mayor que cero, la información que aquí se recibe no
        //                                                  //      tiene los caracteres iniciales que existirían en el
        //                                                  //      archivo. (Ej. en COBOL ya no se tendrían los
        //                                                  //      primeros 6 chars).
        //                                                  //this.*[O], asigna valores.

        //                                                  //(Ej. C#) Instancia de la tecnología a la cual corresponde
        //                                                  //      este código.
        //                                                  //Puede ser null, si es null DEBE existir el syspath y de
        //                                                  //      ahí toma está información.
        //                                                  //El syspath puede corresponder a otra instancia y con este
        //                                                  //      parámetro se le estaría dando override.
        TechinstEnum techinst_I,
        //                                                  //(Ej. EMBEDED)
        CodtypeEnum codtype_I,
        //                                                  //Path del archivo al cual pertenecen las líneas de código,
        //                                                  //      podrá ser null.
        //                                                  //En COMPONENT y EMBEDED se requiere.
        //                                                  //En otros comtype, debe ser null.
        //                                                  //Este es un path valido, sin embargo puede referirse o no
        //                                                  //      a un FILE que exista. No importa esto es solo una
        //                                                  //      referencia documental.
        SyspathPath syspathFile_G,
        //                                                  //Las líneas pueden ser de cualquier longitud, ya no se
        //                                                  //      tienen los caracteres antes de la posición de
        //                                                  //      inicio.
        String[] arrstrLine_G
        )
    {
        super(false);

        if (
            techinst_I != null
            )
        {
            //                                              //Parámetro opcional, si existe se verifica

            if (
                //                                          //La instancia no esta en las opciones
                Arrays.binarySearch(this.t4techINFO().arrt3instOPTION, techinst_I) < 0
                )
                Tes3.subAbort(Tes3.strTo(this.t4techINFO(), StrtoEnum.SHORT) + ", " +
                    Tes3.strTo(techinst_I, "techinst_I") + " is not a valid option for the technology");
        }

        if (
            codtype_I == CodtypeEnum.Z_ERROR_NOT_DEFINED
            )
            Tes3.subAbort(Tes3.strTo(codtype_I, "codtype_I") + " can not be NOT DEFINED");

        if (
            syspathFile_G != null
            )
        {
            //                                              //Parámetro opcional, si existe se verifica

            if (
                //                                          //File extension no esta en las opciones
                Arrays.binarySearch(this.t4techINFO().arrt3fextOPTION, syspathFile_G.strFileExtension()) < 0
                )
                Tes3.subAbort(Tes3.strTo(this.t4techINFO(), StrtoEnum.SHORT) + ", " +
                    Tes3.strTo(syspathFile_G, StrtoEnum.SHORT) +
                    " does not have a valid file extension for the technology required");
        }

        if (
            (codtype_I == CodtypeEnum.COMPONENT) || (codtype_I == CodtypeEnum.EMBEDED)
            )
        {
            //                                              //Requiere syspath
            if (
                syspathFile_G == null
                )
                Tes3.subAbort(Tes3.strTo(techinst_I, "techinst_I") + ", " + Tes3.strTo(codtype_I, "codtype_I") +
                    ", " + Tes3.strTo(syspathFile_G, StrtoEnum.SHORT) +
                    " syspath can not be null for this codtype");
        }
        else
        {
            //                                              //NO debe tener syspath
            if (
                syspathFile_G != null
                )
                Tes3.subAbort(Tes3.strTo(techinst_I, "techinst_I") + ", " + Tes3.strTo(codtype_I, "codtype_I") +
                    ", " + Tes3.strTo(syspathFile_G, StrtoEnum.SHORT) + " syspath should be null for this comtype");
        }

        if (
            arrstrLine_G == null
            )
            Tes3.subAbort(Tes3.strTo(arrstrLine_G, "arrstrLine_G") + " can not be null");

        //                                                  //Verifica relación entre techinst y syspath
        if (
            (techinst_I == null) && (syspathFile_G == null)
            )
            Tes3.subAbort(Tes3.strTo(techinst_I, "techinst_I") + ", " +
                Tes3.strTo(syspathFile_G, StrtoEnum.SHORT) + " techinst and syspath can not both be null");

        if (
            techinst_I != null
            )
        {
            this.techinst_Z = techinst_I;
        }
        else
        {
            //                                              //Se toma del syspath
            this.techinst_Z = Tech.t3instGet(syspathFile_G.strFileExtension()).techinst;
        }

        this.codtype_Z = codtype_I;
        this.syspathFile_Z = syspathFile_G;

        //                                                  //Este arreglo NO debe ser modificado.
        this.arrstrLine_Z = arrstrLine_G;

        //                                                  //Revisa todas las líneas. Estas líneas no tienen los
        //                                                  //      caracteres antes de la pos. de inicio.
        for (int intLine = 0; intLine < this.arrstrLine().length; intLine = intLine + 1)
        {
            //                                              //Si hay algún caracter NO útil se abortará.
            Ostring ostrLine = new Ostring(this.arrstrLine()[intLine]);
            this.subCorrectCharsInLine(ostrLine, intLine, true);
            this.arrstrLine()[intLine] = ostrLine.v;

            if (
                this.arrstrLine()[intLine].endsWith(" ")
                )
                Tes3.subAbort(Tes3.strTo(this.codtype(), "this.codtype") + ", " +
                    Tes3.strTo(this.syspathFile(),  StrtoEnum.SHORT) + ", " +
                    Tes3.strTo(this.arrstrLine()[intLine], "this.arrstrLine[" + intLine + "]") +
                    " ends with one or more blanks");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(
        //                                                  //A partir de un archivo secuencial que contiene todas las
        //                                                  //      línea de un componente, genera el objeto.
        //                                                  //Los registros pueden ser de longitud fija o variable (0
        //                                                  //      caracteres o más).
        //                                                  //Los registros pueden contener caracteres que no son
        //                                                  //      procesables en el lenguaje lo cual se debe reportar
        //                                                  //      con un mensaje y dichos caracteres se cambian a
        //                                                  //      espacios.
        //                                                  //Adicionalmente, si después de limpiar la línea hay
        //                                                  //      caracteres en espacios a la derecha, estos deben
        //                                                  //      eliminarse.
        //                                                  //El type será COMPONENT.
        //                                                  //this.*[O], asigna valores.

        //                                                  //(Ej. C#) Instancia de la tecnología a la cual corresponde
        //                                                  //      este código.
        //                                                  //El syspath puede corresponder a otra instancia y con este
        //                                                  //      parámetro se le estaría dando override.
        TechinstEnum techinst_I,
        //                                                  //Path del archivo. (Ej C:\Programas\MiPrograma.cobol).
        SyspathPath syspathFile_G
        )
    {
        super(false);

        if (
            techinst_I != null
            )
        {
            //                                              //Parámetro opcional, si existe se verifica

            if (
                //                                          //La instancia no esta en las opciones
                Arrays.binarySearch(this.t4techINFO().arrt3instOPTION, techinst_I) < 0
                )
                Tes3.subAbort(Tes3.strTo(this.t4techINFO(), StrtoEnum.SHORT) + ", " +
                    Tes3.strTo(techinst_I, "techinst_I") + " is not a valid option for the technology");
        }

        if (
            syspathFile_G == null
            )
            Tes3.subAbort(Tes3.strTo(techinst_I, "techinst_I") + ", " +
                Tes3.strTo(syspathFile_G, StrtoEnum.SHORT) + " syspath can not be null");
        if (
            Arrays.binarySearch(this.t4techINFO().arrt3fextOPTION, syspathFile_G.strFileExtension()) < 0
            )
            Tes3.subAbort(Tes3.strTo(this.t4techINFO(), StrtoEnum.SHORT) + ", " +
                Tes3.strTo(syspathFile_G, StrtoEnum.SHORT) +
                " does not have a valid file extension for the technology required");
        if (
            !syspathFile_G.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathFile_G,  StrtoEnum.SHORT) + "should be a FILE");

        if (
            techinst_I != null
            )
        {
            this.techinst_Z = techinst_I;
        }
        else
        {
            //                                              //Se toma del syspath
            this.techinst_Z = Tech.t3instGet(syspathFile_G.strFileExtension()).techinst;
        }

        this.codtype_Z = CodtypeEnum.COMPONENT;
        this.syspathFile_Z = syspathFile_G;

        //                                                  //Carga el archivo.
        File file = Sys.sysfileNew(this.syspathFile());
        Oobject<File> oobjFile = new Oobject<File>(file);
        this.arrstrLine_Z = Sys.arrstrReadAll(oobjFile);

        //                                                  //Corrige todas las líneas del archivo.
        for (int intLine = 0; intLine < this.arrstrLine().length; intLine = intLine + 1)
        {
            Ostring ostrLine = new Ostring(this.arrstrLine()[intLine]);

            //                                              //Aún cuando posiblemente solo parte de la línea puede ser
            //                                              //      utilizada, corrige toda la línea para poder detectar
            //                                              //      caracteres inválidos.
            this.subCorrectCharsInLine(ostrLine, intLine, false);

            this.subReviewLineSize(ostrLine);

            this.arrstrLine_Z[intLine] = ostrLine.v;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subCorrectCharsInLine(
        //                                                  //Corrige caracteres en la línea.
        //                                                  //this[I], acceso a info. y a constantes.

        //                                                  //Línea de programa que debe ser corregida.
        Ostring ostrLineToCorrect_IO,
        //                                                  //Info. necesaria para mensajes de diagnóstico.
        int intLine_I,
        //                                                  //True, aborta si algún caracter no es útil
        boolean boolAbort
    )
    {
      	//													//Para corregir la línea se requiere un arrchar, sin embargo
      	//													//      esto solo sucederá por excepción, se hace la
      	//													//      conversión solo cuando se requiere.
      	//													//null indica que no ha sido requerida la conversión.
        char[] arrcharLineToCorrect = null;

        //													//Procesa todos lo caracteres.
        for (int intChar = 0; intChar < ostrLineToCorrect_IO.v.length(); intChar = intChar + 1)
        {
            //												//Corrige 1 caracter.
            Ochar ocharConverted = new Ochar();
            this.subCorrectChar(ostrLineToCorrect_IO.v.charAt(intChar), ocharConverted, intLine_I, intChar, boolAbort);

            //												//Se cambia el caracter en arrchar (strLine) solo si
            //												//      realmente cambió.
            if (
                    //										//Sí fue cambiado.
                    ocharConverted.v != ostrLineToCorrect_IO.v.charAt(intChar)
                    )
            {
                //											//Con el primer caracter cambiado de la línea se genera el
                //											//      arrchar.
                if (
                        //									//Estamos en el primer char a convertir de la línea.
                        arrcharLineToCorrect == null
                        )
                {
                    arrcharLineToCorrect = ostrLineToCorrect_IO.v.toCharArray();
                }

                arrcharLineToCorrect[intChar] = ocharConverted.v;
            }
        }

        //													//Solo si hubo cambios se regresa el arrchar a str.
        if (
                //                                          //Hubo correcciones a al menos 1 char.
                arrcharLineToCorrect != null
                )
        {
            ostrLineToCorrect_IO.v = new String(arrcharLineToCorrect);
        }
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subCorrectChar(
        //                                                  //Corrige 1 caracter.
        //                                                  //1. Hace conversión TO_CONVERT a CONVERSION.
        //                                                  //2. Si un caracter no es USEFUL y tampoco está TO_CONVERT,
        //                                                  //      es un caracter "extraño" que se debe reportar en un
        //                                                  //      mensaje y convertir a espacio para seguir el
        //                                                  //      proceso.
        //                                                  //Nótese que este método se diseño con un char_I y otro
        //                                                  //      char_O, la inteción es que se revise el strLine y
        //                                                  //      solo se genere el arrchar cuando se encuentra un
        //                                                  //      char lo cual sucedera eventualmente.
        //                                                  //this[I], acceso a info. y a constantes.

        //                                                  //Caracter que debe ser corregido
        char charToCorrect_I,
        //                                                  //Regresa el caracter como debe quedar, puede haber cambiado
        //                                                  //      o no.
        Ochar ocharConverted_O,
        //                                                  //Info. necesaria para mensajes de diagnóstico.
        int intLine_I,
        int intChar_I,
        //                                                  //True, aborta si algún caracter no es útil
        boolean boolAbort
        )
    {
        //                                                  //Debe verificar si requiere corrección. 
        if (
            //                                              //Es útil.
            Arrays.binarySearch(this.arrcharUSEFUL(), charToCorrect_I) >= 0
            )
        {
            //                                              //Lo deja igual.
            ocharConverted_O.v = charToCorrect_I;
        }
        else
        {
            //                                              //En ocasiones (Ej. cuando se tiene un file) es normal que
            //                                              //      este aquí y haga algo con estos caracteres, en otras
            //                                              //      (Ej. cuando se tiene un arrstr) solo es válido tener
            //                                              //      caracteres útiles.

            if (
                boolAbort
                )
                Tes3.subAbort(Tes3.strTo(this.codtype(), "this.codtype") + ", " +
                    Tes3.strTo(this.syspathFile(), StrtoEnum.SHORT) + ", " +
                    Tes3.strTo(this.arrstrLine()[intLine_I], "this.arrstrLine[" + intLine_I + "]") + ", " +
                    Tes3.strTo(this.arrstrLine()[intLine_I].charAt(intChar_I),
                        "this.arrstrLine[" + intLine_I + "][" + intChar_I + "]") +
                    " it is not a valid character");

            int intChar = Arrays.binarySearch(this.arrcharTO_CONVERT(), charToCorrect_I);
            if (
                //                                          //Se requiere conversión.
                intChar >= 0
                )
            {
                ocharConverted_O .v= this.arrcharCONVERSION()[intChar];
            }
            else
            {
                //                                          //Este es un caracter "extraño".
                Tes3.subWarning(Tes3.strTo(this.codtype(), "this.codtype") + ", " +
                    Tes3.strTo(this.syspathFile(), StrtoEnum.SHORT) + ", " +
                    Tes3.strTo(this.arrstrLine()[intLine_I], "this.arrstrLine[" + intLine_I + "]") + ", " +
                    Tes3.strTo(this.arrstrLine()[intLine_I].charAt(intChar_I),
                        "this.arrstrLine[" + intLine_I + "][" + intChar_I + "]") +
                    " it is not a valid character, it will be changed to a blank");

                ocharConverted_O.v = ' ';
            }
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subReviewLineSize(                         //Recorta la línea si es necesario.
        //                                                  //this[I], acceso a info. y a constantes.

        //                                                  //Línea de programa que debe ser revisada.
        Ostring ostrLineToReview_IO
        )
    {
        //                                                  //Selecciona de la línea solo la parte útil (Ej. en COBOL 
        //                                                  //      posiciones 6 a 71).

        //                                                  //Corta en la parte inicial.
        if (
            //                                              //La línea es más corta que donde inicia la información 
            //                                              //      útil.
            ostrLineToReview_IO.v.length() < this.strSTART_FILLER().length()
            )
        {
            //                                              //No queda nada.
            ostrLineToReview_IO.v = "";
        }
        else
        {
            ostrLineToReview_IO.v = ostrLineToReview_IO.v.substring(this.strSTART_FILLER().length());
        }

        //                                                  //Corta en la parte final.
        if (
            //                                              //Es de longitud fija y la línea excede el máximo.
            this.boolIS_FIX_LENGTH() && (ostrLineToReview_IO.v.length() > this.intLINE_MAX_LENGTH())
            )
        {
            //                                              //Toma solo la parte útil de la línea.
            ostrLineToReview_IO.v = ostrLineToReview_IO.v.substring(0, this.intLINE_MAX_LENGTH());
        }

        //                                                  //En todos los casos (longitud fija o variable), se quitan 
        //                                                  //      los espacios a la derecha.
        ostrLineToReview_IO.v = Tools.trimEnd(ostrLineToReview_IO.v,' ');
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT FACTORY*/

    //------------------------------------------------------------------------------------------------------------------
    public abstract CodCodeAbstract codxxNew(TechinstEnum techinst_I, CodtypeEnum codtype_I, SyspathPath syspathFile_G,
         String[] arrstrLine_I);

    //------------------------------------------------------------------------------------------------------------------
    public CodCodeAbstract codxxNew(
        //                                                  //Solo se requiere si el codxx permite construir objetos a
        //                                                  //      partir de un syspath.
        //                                                  //this[I], no usa nada
        TechinstEnum techinst_I,
        SyspathPath syspahtFile_G
        )
    {
        Tes3.subAbort(Tes3.strTo(this.getClass(), "this.getClass()") + " virtual codxxNew needs to be override");

        return null;
    }

    //--------------------------------------------------------------------------------------------------------------
    /*SUPPORT METHODS FOR CONSTRUCTORS*/

    //--------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //--------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public void subVerifyUseful(
        //                                                  //Verifica que el caracteres sean USEFUL (aborta).
        //                                                  //this[I], to access arrcharUSEFUL.

        //                                                  //Caracter a analizar.
        char charToAnalize_I,
        //                                                  //Ej. charCOM_TL_START
        String strIdentifierCharToAnalize_I
        )
    {
        if (
            //                                              //Caracter NON USEFUL
            Arrays.binarySearch(this.arrcharUSEFUL(), charToAnalize_I) < 0
            )
            Tes3.subAbort(Tes3.strTo(charToAnalize_I, strIdentifierCharToAnalize_I) + " in NON USEFULL character");
    }
    /*END-TASK*/
}
//======================================================================================================================
/*END-TASK*/