/*TASK RPS.Cod Relevant Part Code*/
package QEnablerBase;

import TowaInfrastructure.*;
import java.util.Arrays;
import javax.swing.JOptionPane;
import SoftwareAutomation.*;
import java.io.File;
import java.util.LinkedList;

                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo Lopez).
                                                            //DATE: 23-Julio-2012.
                                                            //PURPOSE:
                                                            //Especificación de clase Abstract para Código.

//======================================================================================================================
public abstract class CodCodeAbstract extends BclassBaseClassAbstract
                                                            //Base para clases CodxxXxxxx. (xx será cb para COBOL, oo
                                                            //      para Object-Oriented (C#, Java, etc.) sp para Stored
                                                            //      Program, etc.).
                                                            //Código para cualquier lenguaje.
                                                            //Comunmente las columnas de código se numeran 1, 2, etc
                                                            //      (base 1), sin embargo internamente la "posición" es
                                                            //      0, 1, etc. (base 0), en toda la información que se
                                                            //      deba recolectar respecto a esto deberá ser la
                                                            //      POSICIÓN (base 0).
                                                            //En forma similar, la información que se tenga que
                                                            //      recolectar respecto a la línea, también será en base
                                                            //      0.
                                                            //Internamente en QEnabler TODO SERÁ EN BASE 0. Si para
                                                            //      algún efecto externo se debe reportar en base 1,
                                                            //      hasta ese momento se convierte la base.

{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //TODO No sé por qué está esto aquí. Se utiliza en subPrepareUseful y en C# se define diferente. = " .:"
	//private final static String strCHAR_REQUIRED_IN_CHAR_USEFUL = " .:<>";

    @Override
    protected BclassmutabilityEnum bclassmutability() { return BclassmutabilityEnum.INMUTABLE; }

    public abstract  CodCodeAbstract codDUMMY_UNIQUE();
    
                                                            //File extension valida en la tecnología que corresponda.
    public abstract String[] arrstrFILE_EXTENSION();
                                                            //strCHAR_USEFUL, caracteres útiles en el lenguaje que no 
                                                            //      deberán ser convertidos.
                                                            //Los blancos tampoco debrán ser convertidos.
                                                            //      
                                                            //arrcharUSEFUL_Z, lo anterior convertido a arreglo y 
                                                            //      ordenado.
    public abstract char[] arrcharUSEFUL();
                                                            //strCHAR_TO_CONVERT_AND_CONVERSION, pares de caracteres, 
                                                            //      donde el carácter en posición par (0, 2, etc.) 
                                                            //      deberá ser convertido al siguiente carácter (1, 3, 
                                                            //      etc.), para poder ser procesado.
                                                            //arrcharTO_CONVERT_Z, caracteres en posición par 
                                                            //      ordenados.
                                                            //arrcharCONVERSION_Z, caracteres en posición impar 
                                                            //      ordenados por el anterior.
                                                            //Si un caracter no se encuentra en arrcharUSEFUL ni
                                                            //      en arrcharTO_CONVERT se considera un CARACTER NO 
                                                            //      PREVISTO, se debe cambiar a blanco y desplegar un 
                                                            //      mensaje indicando línea, posicion, caracter y 
                                                            //      caracter en hexadecimal para tener idea de donde 
                                                            //      proviene y corregir la situación.
    public abstract char[] arrcharTO_CONVERT();
    public abstract char[] arrcharCONVERSION();

                                                            //Posición donde inicia el código, las posiciones anteriores
                                                            //      a esta serán ignoradas y no serán incluidas en el 
                                                            //      objeto cod (Ej. en COBOL es 6 <columna 7>, por lo 
                                                            //      cual las primeras 6 columnas son ignoradas).
    public abstract int intSTART_CODE();

                                                            //Indica si de acuerdo a las reglas del lenguaje las líneas 
                                                            //      de código deben ser de longitud fija (Ej. en COBOL 
                                                            //      es true, en Java es false).
    public abstract boolean boolIS_FIX_LENGTH();

                                                            //Longitud máxima de la línea de acuerdo al estandar 
                                                            //      establecido, nótese que en realidad QEnabler 
                                                            //      aceptará que las líneas se hayan capturado en 
                                                            //      registros de cualquier longitúd, sin embargo, si el 
                                                            //      lenguaje requiere que sean de longitud fija, la
                                                            //      información posterior a la longitud máxima será 
                                                            //      ignorada <posiciones >= a intINI_POS_CODIGO + 
                                                            //      intLONG_LIN_MAX> (Ej. en COBOL es 66, y dado que el 
                                                            //      lenguaje no acepta longitud variable, las posiciones
                                                            //      72 en adelante <6 + 66 = 72> serán ignoradas, en 
                                                            //      Java es 120, sin embargo el lenguaje acepta longitud
                                                            //      variable por lo cual no se ignora ninguna posición 
                                                            //      al final de las líneas).
    public abstract int intLINE_MAX_LENGTH();

    /*TO ACCESS CONSTANT FROM OTHER CLASSES*/

    /*TASK RPS.Com Relevant Part Comments*/
    public abstract ComCommentsAbstract comDUMMY();
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT CONSTRUCTORS*/

    /*TASK Cod1 subPrepareConstants*/
    //------------------------------------------------------------------------------------------------------------------
    protected static void subPrepareConstants(              //LOS CONSTRUCTORES ESTÁTICOS DE LAS CLASES CodxxXxxxx 
                                                            //      DEBEN LLAMAR A ESTE MÉTODO PARA PREPARAR SU 
                                                            //      INFORMACIÓN.
                                                            //1. Ordena caracteres útiles.
                                                            //2. Separa y ordena caracteres a convertir.
                                                            //Se debe verificar que los parámetros sean consistentes.
                                                            //strCHAR_USEFUL debe incluir blank, . and :

                                                            //Caracteres útiles en el lenguage.
        String strCHAR_USEFUL_I,
                                                            //Caracteres USEFUL ordenados.
        Oarrchar oarrcharUSEFUL_O,
                                                            //Pares de caracteres, donde el carácter en posición par (0,
                                                            //      2, etc.) deberá ser convertido al siguiente carácter
                                                            //      (1, 3, etc.), para poder ser procesado. Los 
                                                            //      caracteres en posición par no están en arrcharUSEFUL
                                                            //      y los caracteres en posición impar si están en
                                                            //      arrcharUSEFUL.
        String strCHAR_TO_CONVERT_AND_CONVERSION_I,
                                                            //Arreglos de caracteres TO_CONVERT y CONVERSION
                                                            //      ordenados por el primer arreglo.
        Oarrchar oarrcharTO_CONVERT_O,
        Oarrchar oarrcharCONVERSION_O
        )
    {
                                                            //1. Ordena caracteres útiles.
        CodCodeAbstract.subPrepareUseful(strCHAR_USEFUL_I, oarrcharUSEFUL_O);

                                                            //2. Separa y ordena caracteres a convertir.
        CodCodeAbstract.subPrepareToConvertAndConversion(strCHAR_TO_CONVERT_AND_CONVERSION_I,
            oarrcharTO_CONVERT_O, oarrcharCONVERSION_O, oarrcharUSEFUL_O.v);    
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareUseful(                   //Ordena caracteres útiles.

                                                            //Caracteres útiles en el lenguage.
        String strCHAR_USEFUL_I,
                                                            //Caracteres USEFUL ordenados.
        Oarrchar oarrcharUSEFUL_O
        )
    {
        if (
            strCHAR_USEFUL_I == null
            )
            Tools.subAbort(Tes2.strTo(strCHAR_USEFUL_I, "strCHAR_USEFUL_I") + " can not be null");
        if (
            strCHAR_USEFUL_I == ""
            )
            Tools.subAbort(Tes2.strTo(strCHAR_USEFUL_I, "strCHAR_USEFUL_I") + " must have information");

        oarrcharUSEFUL_O.v = strCHAR_USEFUL_I.toCharArray();
        Arrays.sort(oarrcharUSEFUL_O.v);

                                                            //Verifica que no haya caracteres duplicados.
        for (int intI = 1; intI < oarrcharUSEFUL_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Esta duplicado.
                oarrcharUSEFUL_O.v[intI] == oarrcharUSEFUL_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(oarrcharUSEFUL_O, "oarrcharUSEFUL_O") + ", " +
                    Tes2.strTo(oarrcharUSEFUL_O.v[intI], "oarrcharUSEFUL_O[" + intI + "]") +
                    " is duplicated character");
        }

        //TODO Checar si esta variable no es parte de la variabla declarada al principio
                                                            //Los siguientes caracteres deben estar incluídos.
        final String strCHAR_REQUIRED_IN_CHAR_USEFUL = " .:";
                                                            //Verifica que estos caracteres estén incluídos.
        for (int intI = 0; intI < strCHAR_REQUIRED_IN_CHAR_USEFUL.length(); intI = intI + 1)
        {
            if (
                                                            //Caracter no incluido.
                Arrays.binarySearch(oarrcharUSEFUL_O.v, strCHAR_REQUIRED_IN_CHAR_USEFUL.charAt(intI)) < 0
                )
                Tools.subAbort(
                    Tes2.strTo(strCHAR_REQUIRED_IN_CHAR_USEFUL.charAt(intI),
                        "strCHAR_REQUIRED_IN_CHAR_USEFUL[" + intI + "]") +
                    " does not exist in " + Tes2.strTo(oarrcharUSEFUL_O.v, "oarrcharUSEFUL_O"));
        }


    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareToConvertAndConversion(
                                                            //Separa y ordena caracteres a convertir.

                                                            //Pares de caracteres.
        String strCHAR_TO_CONVERT_AND_CONVERSION_I,
                                                            //Arreglos de caracteres TO_CONVERT y CONVERSION
                                                            //      ordenados por el primer arreglo.
        Oarrchar oarrcharTO_CONVERT_O,
        Oarrchar oarrcharCONVERSION_O,
                                                            //Caracteres útiles (para verificación).
        char[] arrcharUSEFUL_I
        )
    {
        if (
            strCHAR_TO_CONVERT_AND_CONVERSION_I == null
            )
            Tools.subAbort(Tes2.strTo(strCHAR_TO_CONVERT_AND_CONVERSION_I, "strCHAR_TO_CONVERT_AND_CONVERSION_I") +
                " can not be null");
        if (
                                                            //Longitud del String es impar (este String debe contener
                                                            //      pares de caracteres).
            (strCHAR_TO_CONVERT_AND_CONVERSION_I.length() % 2) != 0
            )
            Tools.subAbort(Tes2.strTo(strCHAR_TO_CONVERT_AND_CONVERSION_I, "strCHAR_TO_CONVERT_AND_CONVERSION_I") +
                ", " +
                Tes2.strTo(strCHAR_TO_CONVERT_AND_CONVERSION_I.length(),
                    "strCHAR_TO_CONVERT_AND_CONVERSION_I.length()") +
                " should be even length");

                                                            //Convierte a 2 arreglos de caracteres y los ordena por el
                                                            //      primero.
        oarrcharTO_CONVERT_O.v = new char[strCHAR_TO_CONVERT_AND_CONVERSION_I.length() / 2];
        oarrcharCONVERSION_O.v = new char[oarrcharTO_CONVERT_O.v.length];
        for (int intI = 0; intI < oarrcharTO_CONVERT_O.v.length; intI = intI + 1)
        {
            oarrcharTO_CONVERT_O.v[intI] = strCHAR_TO_CONVERT_AND_CONVERSION_I.charAt(intI * 2);
            oarrcharCONVERSION_O.v[intI] = strCHAR_TO_CONVERT_AND_CONVERSION_I.charAt(intI * 2 + 1);
        }
        Tools.sort(oarrcharTO_CONVERT_O.v, oarrcharCONVERSION_O.v);

                                                            //Verifica que no haya caracteres duplicados.
        for (int intI = 1; intI < oarrcharTO_CONVERT_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Esta duplicado.
                oarrcharTO_CONVERT_O.v[intI] == oarrcharTO_CONVERT_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(oarrcharTO_CONVERT_O.v, "oarrcharTO_CONVERT_O.v") + ", " +
                    Tes2.strTo(oarrcharTO_CONVERT_O.v[intI], "oarrcharTO_CONVERT_O.v[" + intI + "]") +
                    " is duplicated character");
        }

                                                            //Verifica que oarrcharTO_CONVERT_O NO este en útiles.
        for (int intI = 0; intI < oarrcharTO_CONVERT_O.v.length; intI = intI + 1)
        {
            if (
                Arrays.binarySearch(arrcharUSEFUL_I, oarrcharTO_CONVERT_O.v[intI]) >= 0
                )
                Tools.subAbort(Tes2.strTo(oarrcharTO_CONVERT_O.v[intI], "oarrcharTO_CONVERT_O.v[" + intI + "]") +
                    " exists in " + Tes2.strTo(arrcharUSEFUL_I, "arrcharUSEFUL_I"));
        }

                                                            //Verifica que oarrcharCONVERSION_O SI este en útiles.
        for (int intI = 0; intI < oarrcharCONVERSION_O.v.length; intI = intI + 1)
        {
            if (
                Arrays.binarySearch(arrcharUSEFUL_I, oarrcharCONVERSION_O.v[intI]) < 0
                )
                Tools.subAbort(Tes2.strTo(oarrcharCONVERSION_O.v[intI], "oarrcharCONVERSION_O.v[" + intI + "]") +
                    " does not exist in " + Tes2.strTo(arrcharUSEFUL_I, "arrcharUSEFUL_I"));
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    private /*readonly*/ CodtypeEnum codtype_Z;
    public CodtypeEnum codtype() { return this.codtype_Z; }

                                                            //Full path del archivo de código (Ej.
                                                            //      C:\Programas\MiPrograma.cobol).
    private /*readonly*/ SyspathPath syspathFile_Z;
    public SyspathPath syspathFile() { return this.syspathFile_Z; }

    private String[] arrstrLine_Z;
    public String[] arrstrLine()  { return this.arrstrLine_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    /*TASK RPS.Cod(2) Relevant Part Code Tree (Mod.2)*/
                                                            //Code Tree merged into arrstr.
    /*END-TASK*/

                                                            //Descripción del archivo de código conforme a su tecnología                                                 
    														//      (Ej. "COBOL", "C#", "Java").
    private String strFILE_DESCRIPTION_Z;
    public String strFILE_DESCRIPTION()
    {
                                                            //Recalcula si es nuevo o ya fue reseteado.
        if (
            this.strFILE_DESCRIPTION_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //ESTO NO ES VÁLIDO EN STANDARD_ELEMENTE.
            if (
                this.codtype() != CodtypeEnum.STANDARD_ELEMENT
                )
                Tools.subAbort(Tes2.strTo(this.codtype(), "codtype") + " can not be STANDARD_ELEMENT");

                                                            //Busca la descripción del archivo.
            int intI = Arrays.binarySearch(Tech.arrstrFILE_EXTENSION, syspathFile().strFileExtension());

            if (
                intI < 0
                )
                Tools.subAbort(Tes2.strTo(this.syspathFile(), "syspathFile()") +
                    " file extension not valid");

                                                            //Extrae la descripción.
            this.strFILE_DESCRIPTION_Z = Tech.arrstrFILE_DESCRIPTION[intI];

            this.subSetIsResetOff();
        }

        return strFILE_DESCRIPTION_Z;
    }

    /*TASK RPS.Com Relevant Part Comments*/
    private Boolean boolCOM_EL_REQUIRES_ALIGNMENT_Z;
    public Boolean boolCOM_EL_REQUIRES_ALIGNMENT()
        {

                                                            //Recalcula si es nuevo o ya fue reseteado.
            if (
                this.boolCOM_EL_REQUIRES_ALIGNMENT_Z == null
                )
            {
                this.subVerifyObjectConstructionIsFinished();

                //                                      //Localiza el valor correspondiente al FILE EXTENSION.
                int intX = Arrays.binarySearch(this.arrstrFILE_EXTENSION(), this.syspathFile().strFileExtension());
                this.boolCOM_EL_REQUIRES_ALIGNMENT_Z =
                        this.comDUMMY().arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT()[intX].boolComElRequiresAlignment;
            }

            return boolCOM_EL_REQUIRES_ALIGNMENT_Z;
        }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void subReset()
    {
        this.strFILE_DESCRIPTION_Z = null;
        this.boolCOM_EL_REQUIRES_ALIGNMENT_Z = null;

        super.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        return super.strTo(TestoptionEnum.SHORT) + ", " + ", " + Tes2.strTo(this.codtype(), TestoptionEnum.SHORT) +
            ", " + Tes2.strTo(this.syspathFile(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.arrstrLine(), TestoptionEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        final String strCLASS = "Cod";

        return strCLASS + "{" + Tes2.strTo(this.codtype(), "codtype") + ", " +
            Tes2.strTo(this.syspathFile(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.arrstrLine(), "arrstrLine") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(                              //Crea objeto DUMMY.
                                                            //this.*[O], crea sin asignar nada. 
        )
    {
    	super(true);
    }

    /*TASK Cod2 CodAbstract(arrstr & file)*/
    //------------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(                              //A partir de un arreglo de String genera el objeto (parte
                                                            //      abstracta).
                                                            //Aún cuando en alguna tecnologías se requiere que los
                                                            //      registros sean de longitud fija, en este constructor
                                                            //      pueden ser de cualquier longitud.
                                                            //Tambien, si la tecnología tiene una posición de inicio
                                                            //      mayor que cero, la información que aquí se recibe no
                                                            //      tiene los caracteres iniciales que existirían en el
                                                            //      archivo. (Ej. en COBOL ya no se tendrían los
                                                            //      primeros 6 chars).
                                                            //this.*[O], asigna valores.

                                                            //Tipo de código.
        CodtypeEnum codtype_I,
                                                            //Path del archivo al cual pertenecen las líneas de código,
                                                            //      podrá ser null.
                                                            //En COMPONENT se requiere.
                                                            //Se debe verificar que el path es un archivo válido y que
                                                            //      el file extensión corresponde a la tecnología.
                                                            //En otro tipo no debe tener syspath
        SyspathPath syspathFile_G,
                                                            //Las líneas pueden ser de cualquier longitud, ya no se
                                                            //      tienen los caracteres antes de la posición de
                                                            //      inicio.
        String[] arrstrLine_G
        )
    {
        super(false);

        if (
            codtype_I == CodtypeEnum.COMPONENT
            )
        {
                                                            //Requiere que el path sea válido

            if (
                syspathFile_G == null
                )
                Tools.subAbort(Tes2.strTo(codtype_I, "codtype_I") + ", " +
                    Tes2.strTo(syspathFile_G, "syspathFile_G") + " can not be null");
            if (
                !syspathFile_G.boolIsFile()
                )
                Tools.subAbort(Tes2.strTo(codtype_I, "codtype_I") + ", " +
                    Tes2.strTo(syspathFile_G, "syspathFile_G") + " is not a file");
            if (
                Arrays.binarySearch(this.arrstrFILE_EXTENSION(), syspathFile_G.strFileExtension()) < 0
                )
                Tools.subAbort(Tes2.strTo(this.arrstrFILE_EXTENSION(), "arrstrFILE_EXTENSION") + ", " +
                    Tes2.strTo(codtype_I, "codtype_I") + ", " + Tes2.strTo(syspathFile_G, "syspathFile_G") +
                    " does not have a valid file extension for the technology required");
        }
        else
        {
                                                            //NO debe tener path.

            if (
                syspathFile_G != null
                )
                Tools.subAbort(Tes2.strTo(codtype_I, "codtype_I") + ", " +
                        Tes2.strTo(syspathFile_G, "syspathFile_G") + " should be null");
        }

        this.codtype_Z = codtype_I;
        this.syspathFile_Z = syspathFile_G;

                                                            //Este arreglo NO debe ser modificado.
        this.arrstrLine_Z = arrstrLine_G;

                                                            //Revisa todas las líneas. Estas líneas no tienen los
                                                            //      caracteres antes de la pos. de inicio.
        for (int intLine = 0; intLine < this.arrstrLine().length; intLine = intLine + 1)
        {
                                                            //Si hay algún caracter NO útil se abortará.
            Ostring ostrLine = new Ostring(this.arrstrLine()[intLine]);
            this.subCorrectCharsInLine(ostrLine, intLine, true);
            this.arrstrLine()[intLine] = ostrLine.v;

            if (
                this.arrstrLine()[intLine].endsWith(" ")
                )
                Tools.subAbort(Tes2.strTo(this.codtype(), "this.codtype") + ", " +
                    Tes2.strTo(this.syspathFile(), "this.syspathFile") + ", " +
                    Tes2.strTo(this.arrstrLine()[intLine], "this.arrstrLine[" + intLine + "]") +
                    " ends with one or more blanks");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(                              //A partir de un archivo secuencial que contiene todas las
                                                            //      línea de un componente, genera el objeto.
                                                            //Los registros pueden ser de longitud fija o variable (0
                                                            //      caracteres o más).
                                                            //Los registros pueden contener caracteres que no
                                                            //      son procesables en el lenguaje lo cual se debe
                                                            //      reportar con un mensaje y dichos caracteres se
                                                            //      convierten o cambian a espacios.
                                                            //Adicionalmente, si después de limpiar la línea hay
                                                            //      caracteres en espacios a la derecha, estos deben
                                                            //      eliminarse.
                                                            //El type será COMPONENT.
                                                            //this.*[O], asigna valores.

                                                            //Path del archivo. (Ej C:\Programas\MiPrograma.cobol).
                                                            //Se debe verificar que el path es un archivo válido y que
                                                            //      el file extensión corresponde a la tecnología.
        SyspathPath syspathFile_G
        )
    {
        super(false);

        if (
            syspathFile_G == null
            )
            Tools.subAbort(Tes2.strTo(syspathFile_G, "syspathFile_G") + " can not be null");
        if (
            !syspathFile_G.boolIsFile()
            )
            Tools.subAbort(Tes2.strTo(syspathFile_G, "syspathFile_G") + " is not a file");
        if (
            Arrays.binarySearch(this.arrstrFILE_EXTENSION(), syspathFile_G.strFileExtension()) < 0
            )
            Tools.subAbort(Tes2.strTo(this.arrstrFILE_EXTENSION(), "arrstrFILE_EXTENSION") +
                ", " + Tes2.strTo(syspathFile_G, "syspathFile_G") +
                " does not have a valid file extension for the technology required");

        this.codtype_Z = CodtypeEnum.COMPONENT;
        this.syspathFile_Z = syspathFile_G;

                                                            //Carga el archivo.
        File file = Sys.sysfileNew(this.syspathFile());
        Oobject<File> oobjFile = new Oobject<File>(file);
        this.arrstrLine_Z = Sys.arrstrReadAll(oobjFile);

                                                            //Corrige todas las líneas del archivo.
        for (int intLine = 0; intLine < this.arrstrLine().length; intLine = intLine + 1)
        {
            Ostring ostrLine = new Ostring(this.arrstrLine()[intLine]);

                                                            //Aún cuando posiblemente solo parte de la línea puede ser
                                                            //     utilizada, corrige toda la línea para poder detectar
                                                            //      caracteres inválidos.
            this.subCorrectCharsInLine(ostrLine, intLine, false);

            this.subReviewLineSize(ostrLine);

                                                            //En todos los casos (longitud fija o variable), se quitan
                                                            //      los espacios a la derecha.
            this.arrstrLine()[intLine] = Tools.trimEnd(ostrLine.v,' ');

        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subCorrectCharsInLine(                     //Corrige caracteres en la línea.
                                                            //this[I], acceso a info. y a constantes.

                                                            //Línea de programa que debe ser corregida.
        Ostring ostrLineToCorrect_IO,
                                                            //Info. necesaria para mensajes de diagnóstico.
        int intLine_I,
                                                            //True, aborta si algún caracter no es útil
        boolean boolAbort
        )
    {
                                                            //Para corregir la línea se requiere un arrchar, sin embargo
                                                            //      esto solo sucederá por excepción, se hace la
                                                            //      conversión solo cuando se requiere.
                                                            //null indica que no ha sido requerida la conversión.
        char[] arrcharLineToCorrect = null;

                                                            //Procesa todos lo caracteres.
        for (int intChar = 0; intChar < ostrLineToCorrect_IO.v.length(); intChar = intChar + 1)
        {
                                                            //Corrige 1 caracter.
            Ochar ocharConverted = new Ochar();
            this.subCorrectChar(ostrLineToCorrect_IO.v.charAt(intChar), ocharConverted, intLine_I, intChar, boolAbort);

                                                            //Se cambia el caracter en arrchar (strLine) solo si
                                                            //      realmente cambió.
            if (
                                                            //Sí fue cambiado.
                ocharConverted.v != ostrLineToCorrect_IO.v.charAt(intChar)
                )
            {
                                                            //Con el primer caracter cambiado de la línea se genera el
                                                            //      arrchar.
                if (
                                                            //Estamos en el primer char a convertir de la línea.
                    arrcharLineToCorrect == null
                    )
                {
                    arrcharLineToCorrect = ostrLineToCorrect_IO.v.toCharArray();
                }

                arrcharLineToCorrect[intChar] = ocharConverted.v;
            }
        }

                                                            //Solo si hubo cambios se regresa el arrchar a str.
        if (
                                                            //Hubo correcciones a al menos 1 char.
            arrcharLineToCorrect != null
            )
        {
            ostrLineToCorrect_IO.v = new String(arrcharLineToCorrect);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subReviewLineSize(                         //Recorta la línea si es necesario.
                                                            //this[I], acceso a info. y a constantes.

                                                            //Línea de programa que debe ser revisada.
        Ostring ostrLineToReview_IO
        )
    {
                                                            //Selecciona de la línea solo la parte útil (Ej. en COBOL
                                                            //      posiciones 6 a 71).

                                                            //Corta en la parte inicial.
        if (
                                                            //La línea es más corta que donde inicia la información
                                                            //      útil.
            ostrLineToReview_IO.v.length() < this.intSTART_CODE()
            )
        {
                                                            //No queda nada.
            ostrLineToReview_IO.v = "";
        }
        else
        {
            ostrLineToReview_IO.v = ostrLineToReview_IO.v.substring(this.intSTART_CODE());
        }

                                                            //Corta en la parte final.
        if (
                                                            //Es de longitud fija y la línea excede el máximo.
            this.boolIS_FIX_LENGTH() && (ostrLineToReview_IO.v.length() > this.intLINE_MAX_LENGTH())
            )
        {
                                                            //Toma solo la parte útil de la línea.
            ostrLineToReview_IO.v = ostrLineToReview_IO.v.substring(0, this.intLINE_MAX_LENGTH());
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subCorrectChar(                            //Corrige 1 caracter.
                                                            //1. Hace conversión TO_CONVERT a CONVERSION.
                                                            //2. Si un caracter no es USEFUL y tampoco está TO_CONVERT,
                                                            //      es un caracter "extraño" que se debe reportar en un
                                                            //      mensaje y convertir a espacio para seguir el
                                                            //      proceso.
                                                            //Nótese que este método se diseño con un char_I y otro
                                                            //      char_O, la inteción es que se revise el strLine y
                                                            //      solo se genere el arrchar cuando se encuentra un
                                                            //      char lo cual sucedera eventualmente.
                                                            //this[I], acceso a info. y a constantes.

                                                            //Caracter que debe ser corregido
        char charToCorrect_I,
                                                            //Regresa el caracter como debe quedar, puede haber cambiado
                                                            //      o no.
        Ochar ocharConverted_O,
                                                            //Info. necesaria para mensajes de diagnóstico.
        int intLine_I,
        int intChar_I,
                                                            //True, aborta si algún caracter no es útil
        boolean boolAbort
        )
    {
                                                            //Debe verificar si requiere corrección.
        if (
                                                            //Es útil.
            Arrays.binarySearch(this.arrcharUSEFUL(), charToCorrect_I) >= 0
            )
        {
                                                            //Lo deja igual.
            ocharConverted_O.v = charToCorrect_I;
        }
        else
        {
                                                            //En ocasiones (Ej. cuando se tiene un file) es normal que
                                                            //      este aquí y haga algo con estos caracteres, en otras
                                                            //      (Ej. cuando se tiene un arrstr) solo es válido tener
                                                            //      caracteres útiles.

            if (
                boolAbort
                )
                Tools.subAbort(Tes2.strTo(this.codtype(), "this.codtype") + ", " +
                        Tes2.strTo(this.syspathFile(), "this.syspathFile") + ", " +
                        Tes2.strTo(this.arrstrLine()[intLine_I], "this.arrstrLine[" + intLine_I + "]") + ", " +
                        Tes2.strTo(this.arrstrLine()[intLine_I].charAt(intChar_I),
                                "this.arrstrLine[" + intLine_I + "][" + intChar_I + "]") +
                        " it is not a valid character");

            int intChar = Arrays.binarySearch(this.arrcharTO_CONVERT(), charToCorrect_I);
            if (
                                                            //Se requiere conversión.
                intChar >= 0
                )
            {
                ocharConverted_O.v = this.arrcharCONVERSION()[intChar];
            }
            else
            {
                                                            //Este es un caracter "extraño".
                JOptionPane.showMessageDialog(null, Tes2.strTo(this.codtype(), "this.codtype") + ", " +
                        Tes2.strTo(this.syspathFile(), "this.syspathFile") + ", " +
                        Tes2.strTo(this.arrstrLine()[intLine_I], "this.arrstrLine[" + intLine_I + "]") + ", " +
                        Tes2.strTo(this.arrstrLine()[intLine_I].charAt(intChar_I),
                                "this.arrstrLine[" + intLine_I + "][" + intChar_I + "]") +
                        " it is not a valid character, it will be changed to a blank");

                ocharConverted_O.v = ' ';
            }
        }
    }

    /*
    //------------------------------------------------------------------------------------------------------------------

    //TODO EN ESTA TASK SE REDEFINE EL MÉTODO
    /*TASK Cod2 CodAbstract(file)* /
    //------------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(                              //A partir de un archivo secuencial que contiene todas las
                                                            //      línea de un componente, genera el objeto.
                                                            //Los registros pueden ser de longitud fija o variable (0 
                                                            //      caracteres o más).
                                                            //Los registros pueden contener caracteres que no
                                                            //      son procesables en el lenguaje lo cual se debe
                                                            //      reportar con un mensaje y dichos caracteres se
                                                            //      cambian a espacios.
                                                            //Adicionalmente, si después de limpiar la línea hay
                                                            //      caracteres en espacios a la derecha, estos deben
                                                            //      eliminarse.
                                                            //El type será COMPONENT.
                                                            //this.*[O], asigna valores. 

                                                            //Path del archivo. (Ej C:\Programas\MiPrograma.cobol).
                                                            //Se debe verificar que el path es un archivo válido y que
                                                            //      el file extensión corresponde a la tecnología.
        SyspathPath syspathFile_T
        )
    {
    	super(false);

        if (
            syspathFile_T == null
            )
            Tools.subAbort(Tes2.strTo(syspathFile_T, "syspathFile_T") + " can not be null");
        if (
            !syspathFile_T.boolIsFile()
            )
            Tools.subAbort(Tes2.strTo(syspathFile_T, "syspathFile_T") + " is not a file");
        if (
            Arrays.binarySearch(this.arrstrFILE_EXTENSION(), syspathFile_T.strFileExtension()) < 0
            )
            Tools.subAbort(Tes2.strTo(this.arrstrFILE_EXTENSION(), "arrstrFILE_EXTENSION()") +
                ", " + Tes2.strTo(syspathFile_T, "syspathFile_T") +
                " does not have a valid file extension for the technology required");

        this.codtype_Z = CodtypeEnum.COMPONENT;

        this.syspathFile_Z = syspathFile_T;
        this.syspathFile().subAddUsedIn(this);

                                                            //Carga el archivo.
        Oobject<File> sysfile = new Oobject<File>();
        sysfile.v = Sys.sysfileNew(this.syspathFile());
        String[] arrstrLine = Sys.arrstrReadAll(sysfile);

                                                            //Corrige todas la líneas del archivo.
        for (int intLine = 0; intLine < arrstrLine.length; intLine = intLine + 1)
        {
                                                            //Aún cuando posiblemente solo parte de la línea puede será
                                                            //      utilizada, corrige toda la línea para poder detectar
                                                            //      caracteres inválidos.
        	Ostring ostrLine = new Ostring(arrstrLine[intLine]);
            this.subCorrectLine(ostrLine, intLine);
            arrstrLine[intLine] = ostrLine.v;
        }

        this.arrstrLine_Z = arrstrLine;
    }*/

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    private void subCorrectLine(                            //Corrige caracteres en la línea.
                                                            //this[I], acceso a info. y a constantes.

                                                            //Línea de programa que debe ser corregida.
        Ostring ostrLineToCorrect_IO,
                                                            //Info. necesaria para mensajes de diagnóstico.
        int intLine_I
        )
    {
                                                            //Para corregir la línea se requiere un arrchar, sin embargo
                                                            //      esto solo sucederá por excepción, se hace la
                                                            //      conversión solo cuando se requiere.
                                                            //null indica que no ha sido requerida la conversión.
        char[] arrcharLineToCorrect = null;

                                                            //Procesa todos lo caracteres.
        for (int intChar = 0; intChar < ostrLineToCorrect_IO.v.length(); intChar = intChar + 1)
        {
                                                            //Corrige 1 caracter.
            Ochar ocharConverted = new Ochar();
            this.subCorrectChar(ostrLineToCorrect_IO.v.charAt(intChar), ocharConverted, ostrLineToCorrect_IO.v,
                intLine_I, intChar);
            char charConverted = ocharConverted.v;
            

                                                            //Se cambia el caracter en arrchar (strLine) solo si
                                                            //      realmente cambió.
            if (
                                                            //Sí fue cambiado.
                charConverted != ostrLineToCorrect_IO.v.charAt(intChar)
                )
            {
                                                            //Con el primer caracter cambiado de la línea se genera el
                                                            //      arrchar.
                if (
                                                            //Estamos en el primer char a convertir de la línea.
                    arrcharLineToCorrect == null
                    )
                {
                    arrcharLineToCorrect = ostrLineToCorrect_IO.v.toCharArray();
                }

                arrcharLineToCorrect[intChar] = charConverted;
            }
        } 

                                                            //Solo si hubo cambios se regresa el arrchar a str.
        if (
                                                            //Hubo correcciones a al menos 1 char.
            arrcharLineToCorrect != null
            )
        {
            ostrLineToCorrect_IO.v = new String(arrcharLineToCorrect);
        }

                                                            //Selecciona de la línea solo la parte útil (Ej. en COBOL 
                                                            //      posiciones 6 a 71).

                                                            //Corta en la parte inicial.
        if (
                                                            //La línea es más corta que donde inicia la información 
                                                            //      útil.
            ostrLineToCorrect_IO.v.length() < this.intSTART_CODE()
            )
        {
                                                            //No queda nada.
            ostrLineToCorrect_IO.v = "";
        }
        else
        {
            ostrLineToCorrect_IO.v = ostrLineToCorrect_IO.v.substring(this.intSTART_CODE());
        }

                                                            //Corta en la parte final.
        if (
                                                            //Es de longitud fija y la línea excede el máximo.
            this.boolIS_FIX_LENGTH() && (ostrLineToCorrect_IO.v.length() > this.intLINE_MAX_LENGTH())
            )
        {
                                                            //Toma solo la parte útil de la línea.
        	ostrLineToCorrect_IO.v = ostrLineToCorrect_IO.v.substring(0, this.intLINE_MAX_LENGTH());
        }

                                                            //En todos los casos (longitud fija o variable), se quitan 
                                                            //      los espacios a la derecha.
        ostrLineToCorrect_IO.v = Tools.trimEnd(ostrLineToCorrect_IO.v, ' ');
    }

    //TODO EN ESTA DESCRIPCIÓN HABÍA MENOS PUNTOS A CONSIDERAR
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subCorrectChar(                            //Corrige 1 caracter.
                                                            //1. Hace conversión TO_CONVERT a CONVERSION.
                                                            //2. Si un caracter no es USEFUL y tampoco está TO_CONVERT,
                                                            //      es un caracter "extraño" que se debe reportar en un
                                                            //      mensaje y convertir a espacio para seguir el
                                                            //      proceso.
                                                            //Nótese que este método se diseño con un char_I y otro
                                                            //      char_O, la inteción es que se revise el strLine y
                                                            //      solo se genere el arrchar cuando se encuentra un
                                                            //      char lo cual sucedera eventualmente.
                                                            //this[I], acceso a info. y a constantes.

                                                            //Caracter que debe ser corregido
        char charToCorrect_I,
                                                            //Regresa el caracter como debe quedar, puede haber cambiado
                                                            //      o no.
        Ochar ocharConverted_O,
                                                            //Info. necesaria para mensajes de diagnóstico.
        String strLineToCorrect_I,
        int intLine_I,
        int intChar_I
        )
    {
        //TODO LA CONDICIÓN DEL IF ES DIFERENTE, ESTA BIEN?
                                                            //Debe verificar si requiere corrección. 
        if (
                                                            //Es útil.
                Arrays.binarySearch(this.arrcharUSEFUL(), charToCorrect_I) >= 0
                )
        {
                                                            //Lo deja igual.
            ocharConverted_O.v = charToCorrect_I;
        }
        else
        {
            int intChar = Arrays.binarySearch(this.arrcharTO_CONVERT(), charToCorrect_I);
            if (
                                                            //Se requiere conversión.
                intChar >= 0
                )
            {
            	ocharConverted_O.v = this.arrcharCONVERSION()[intChar];
            }
            else
            {
                                                            //Este es un caracter "extraño".
                JOptionPane.showMessageDialog(null, Tes2.strTo(this.syspathFile().strFullPath(),
                		"syspathFile.strFullPath()") + ", " + Tes2.strTo(strLineToCorrect_I, "strLineToCorrect_I") +
                		", " + Tes2.strTo(strLineToCorrect_I.charAt(intChar_I), "strLineToCorrect_I.charAt(" +
                		intChar_I + ")") + ", " + ", it is not a valid character, it will be changed to a blank");

                ocharConverted_O.v = ' ';
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    /*TASK Cod3 CodAbstract(com)*/
    //--------------------------------------------------------------------------------------------------------------
    protected CodCodeAbstract(                              //Construye código estandarizado de un objeto com.
                                                            //this.*[O], asigna valores.

                                                            //Comentarios con el cual se construye el código estándar.
        ComCommentsAbstract com_I
        )
    {
        super(false);

        if (
            com_I == null
            )
            Tools.subAbort(Tes2.strTo(com_I, "com_I") + " should have a value");

                                                            //Deberá ser del mismo type y del mismo file que el
                                                            //      CodOriginal del com.
        this.codtype_Z = com_I.codOriginal().codtype();
        this.syspathFile_Z = com_I.codOriginal().syspathFile();

        String[] arrstrLine;
                                                            //Procesa de acuerdo al tipo de comentarios.
        /*CASE*/
        if (
            com_I.comtype() == ComtypeEnum.FULL_LINE
            )
        {
            arrstrLine = CodCodeAbstract.arrstrCodStandardForComFlOrEl(com_I.strCOM_FL_OR_TL(), com_I);
        }
        else if (
            com_I.comtype() == ComtypeEnum.TAG_FULL_LINE
            )
        {
            arrstrLine = CodCodeAbstract.arrstrCodStandardForComTl(com_I);
        }
        else if (
            com_I.comtype() == ComtypeEnum.END_OF_LINE
            )
        {
            arrstrLine = CodCodeAbstract.arrstrCodStandardForComFlOrEl(com_I.strCOM_EL(), com_I);
        }
        else if (
            com_I.comtype() == ComtypeEnum.EMBEDED
            )
        {
            arrstrLine = CodCodeAbstract.arrstrCodStandardForComEm(com_I);
        }
        else if (
            com_I.comtype() == ComtypeEnum.DELIMITED
            )
        {
            arrstrLine = CodCodeAbstract.arrstrCodStandardForComDe(com_I);
        }
        else
        {
            arrstrLine = null;
            if (
                true
                )
                Tools.subAbort(Tes2.strTo(com_I.comtype(), "com_I.comtype") + " was not found");
        }
        /*END-CASE*/

        this.arrstrLine_Z = arrstrLine;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String[] arrstrCodStandardForComFlOrEl(
                                                            //Genera las líneas de comentarios FULL_LINE o
                                                            //      END_OF_LINE ya estandarizado.

                                                            //arrstr, arreglo de líneas ya estandarizado.

                                                            //Inicio correspondiente a FULL_LINE o END_OF_LINE.
        String strCOM_I,
                                                            //Comentarios con el cual se construye el código estándar.
        ComCommentsAbstract com_I
        )
    {
        LinkedList<String> lststrCodStandardForComFlOrEl = new LinkedList<String>();

                                                            //Si es FULL_LINE y tiene al menos un párrafo no
                                                            //      estructurado, deberá añadir línea de inicio y fin
                                                            //      (Ej. en COBOL es "*----").
        boolean boolHasAtLeastOnParun = false;

                                                            //Procesa todos los párrafos.
        for (ParParagraphAbstract par : com_I.arrpar())
        {
                                                            //Obtiene las líneas de código estandar de un párrafo, estas
                                                            //      aún no tiene la marca de inicio. Nótese que la
                                                            //      generación de las líneas estándar para cada párrafo
                                                            //      estará a cargo la cada una de las clases
                                                            //      para cada tipo de párrafo.

                                                            //Añade las líneas del párrafo a las líneas del comentario,
                                                            //      también le antepone la marca de inicio que
                                                            //      corresponda.
            for (String str : par.arrstrLineStandard())
            {
                lststrCodStandardForComFlOrEl.add(strCOM_I + str);
            }

                                                            //Prende el indicador si el párrafo es no estructurado.
            boolHasAtLeastOnParun = (
                    boolHasAtLeastOnParun || (par instanceof ParunUnstructured)
                    );
        }

        if (
            com_I.comtype() == ComtypeEnum.FULL_LINE
            )
        {
                                                            //Determina si requiere iniciar y terminar con una línea.
            if (
                                                            //Si hubo al menos 1 parun, o no hubo ningún párrafo en la
                                                            //      lista.
                (boolHasAtLeastOnParun || (com_I.arrpar().length == 0))
                )
            {
                                                            //Añade "*----" a inicio y al fin de comentarios.
                lststrCodStandardForComFlOrEl.addFirst(com_I.strCOM_FL_START_AND_END_LINE());
                lststrCodStandardForComFlOrEl.add(com_I.strCOM_FL_START_AND_END_LINE());
            }
        }
        else
        {
                                                            //Estamos en EL

                                                            //Determina si requiere añalir línea de "comentario vacío".
            if (
                (com_I.comtype() == ComtypeEnum.END_OF_LINE) &&
                                                            //No hubo ningún párrafo.
                (com_I.arrpar().length == 0)
                )
            {
                                                            //Añade COMENTARIO_SIN_LINEAS, con esto el código tendrá al
                                                            //      menos 1 línea.
                lststrCodStandardForComFlOrEl.add(com_I.strCOM_EL() + com_I.strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS());
            }
        }

        return (String[])lststrCodStandardForComFlOrEl.toArray();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String[] arrstrCodStandardForComTl(      //Genera las líneas de comentarios TAG_FULL_LINE ya
                                                            //      estandarizado.

                                                            //arrstr, arreglo de 1 línea ya estandarizado.

                                                            //Comentarios con el cual se construye el código estándar.
        ComCommentsAbstract com_I
        )
    {
                                                            //El comentario tiene un solo párrafo, el cual será 1 sola
                                                            //      línea.
        String strLineParunTl = com_I.arrpar()[0].arrstrLineStandard()[0];

        String strLineComTl = com_I.strCOM_FL_OR_TL() + strLineParunTl;

        return new String[] { strLineComTl };
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String[] arrstrCodStandardForComEm(      //Genera las líneas de comentarios EMBEDED ya
                                                            //      estandarizado.

                                                            //arrstr, arreglo de 1 línea ya estandarizado.

                                                            //Comentarios con el cual se construye el código estándar.
        ComCommentsAbstract com_I
        )
    {
                                                            //El comentario tiene un solo párrafo, el cual será 1 sola
                                                            //      línea.
        String strLineParunEm = com_I.arrpar()[0].arrstrLineStandard()[0];

        String strLineComEm = com_I.strCOM_DE_OR_EM_START() + strLineParunEm + com_I.strCOM_DE_OR_EM_END();

        return new String[] { strLineComEm };
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String[] arrstrCodStandardForComDe(      //Genera las líneas de comentarios DELIMITED ya
                                                            //      estandarizado.

                                                            //arrstr, arreglo de líneas ya estandarizado.

                                                            //Comentarios con el cual se construye el código estándar.
        ComCommentsAbstract com_I
        )
    {
                                                            //El comentario tiene:
                                                            //Un parun al inicio de 1 sola línea.
                                                            //Un parst enmedio que pueden ser de 0 a n líneas.
                                                            //Y un parun al final de 1 sola línea.
        String strLineParun0 = com_I.arrpar()[0].arrstrLineStandard()[0];
        String[] arrstrLineParst1 = com_I.arrpar()[1].arrstrLineStandard();
        String strLineParun2 = com_I.arrpar()[2].arrstrLineStandard()[0];

                                                            //Construye el arreglo de líneas de código con el tamaño
                                                            //      correcto.
        String[] arrstrCodStandardForComDe = new String[arrstrLineParst1.length + 2];

                                                            //Pasa las líneas de los párrafos a las líneas del
                                                            //      comentario, le añade los delimitadores en la primera
                                                            //      línea y la última.
        arrstrCodStandardForComDe[0] = com_I.strCOM_DE_OR_EM_START() + strLineParun0;
        System.arraycopy(arrstrLineParst1, 0, arrstrCodStandardForComDe, 1, arrstrLineParst1.length);
        arrstrCodStandardForComDe[arrstrCodStandardForComDe.length - 1] = strLineParun2 + com_I.strCOM_DE_OR_EM_END();

        return arrstrCodStandardForComDe;
    }

    //TODO checar lo de internal a public
    //------------------------------------------------------------------------------------------------------------------
    public String[] arrstrParunTo(                          //Construye código estandarizado de un objeto parun.
                                                            //El código estándar de un párrafo no incluye los caracteres
                                                            //      de inicio del comentario ni los delimitadores.
                                                            //Este tipo de párrafo es posible en todos los tipos de
                                                            //      comentarios (FULL_LINE, TAG_FULL_LINE, END_OF_LINE,
                                                            //      DELIMITED y EMBEDED).
                                                            //this.*[O], asigna valores.

                                                            //Párrafo no estructurado con el cual se construye el código
                                                            //      estándar.
                                                            //Nótese que el párrafo contendrá solo palabras que SÍ caben
                                                            //      correctamente en la ínea de código, si había palabra
                                                            //      grandes estas fueron cortadas al construir el
                                                            //      párrafo.
        ParunUnstructured parun_I
        )
    {
                                                            //Saco el com para facilitar la codificación.
        ComCommentsAbstract com = parun_I.comBelongsTo();

        String[] arrstrParunTo;
                                                            //El parun puede ser de un com de varios tipos./*CASE*/
        if (
            com.comtype() == ComtypeEnum.FULL_LINE
            )
        {
                                                            //La palabras del párrafo deben ser "puestas" en varías
                                                            //      líneas de código conforme al formato requerido por
                                                            //      FULL_LINE.
            arrstrParunTo = CodCodeAbstract.arrstrCodStandardParunFlOrEl(parun_I.strParagraph(),
                    com.intCOM_FL_OR_TL_LINE_SIZE() - com.strCOM_FL_OR_TL().length(), com.strPARUN_INDENT_FL_FIRST_LINE(),
                    com.strPARUN_INDENT_FL_OTHER_LINES());
        }
        else if (
            com.comtype() == ComtypeEnum.END_OF_LINE
            )
        {
                                                            //La palabras del párrafo deben ser "puestas" en varías
                                                            //      líneas de código conforme al formato requerido por
                                                            //      END_OF_LINE.
            arrstrParunTo = CodCodeAbstract.arrstrCodStandardParunFlOrEl(parun_I.strParagraph(),
                    com.intCOM_EL_LINE_SIZE() - com.strCOM_EL().length(), com.strPARUN_INDENT_EL_FIRST_LINE(),
                    com.strPARUN_INDENT_EL_OTHER_LINES());
        }
        else if (
            com.comtype() == ComtypeEnum.TAG_FULL_LINE
            )
        {
                                                            //En TAG_FULL_LINE será: <parrafo>, la marca > solo se
                                                            //      incluye si cabe.

            String strLineOnlyOne = com.charCOM_TL_START() + parun_I.strParagraph() + com.charCOM_TL_END();

                                                            //Le quita la marca > si no cabe.
            int intLengMax = com.intCOM_FL_OR_TL_LINE_SIZE() - com.strCOM_FL_OR_TL().length();
            if (
                strLineOnlyOne.length() > intLengMax
                )
            {
                strLineOnlyOne = strLineOnlyOne.substring(0, intLengMax) ;
            }

            arrstrParunTo = new String[] { strLineOnlyOne };
        }
        else if (
            (com.comtype() == ComtypeEnum.EMBEDED) || (com.comtype() == ComtypeEnum.DELIMITED)
            )
        {
                                                            //En EMBEDED o DELIMITED todo el código del párrafo es la
                                                            //      información del párrafo tal y como esta, en una sola
                                                            //      línea (sin añadir nada antes o después).
            arrstrParunTo = new String[] { parun_I.strParagraph() };
        }
        else
        {
            arrstrParunTo = null;
            if (
                true
                )
                Tools.subAbort(Tes2.strTo(parun_I.strParagraph(), "parun_I.strParagraph") + ", " +
                        Tes2.strTo(com.comtype(), "com.comtype") + " should not include parun");
        }
        /*END-CASE*/

        return arrstrParunTo;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String[] arrstrCodStandardParunFlOrEl(
                                                            //Formatea líneas de un párrafo no estructurado en
                                                            //      comentarios de tipo FULL_LINE o END_OF_LINE (no
                                                            //      incluye los caracteres de inicio).

                                                            //arrstr, arreglo de líneas estandarizada para parun.

                                                            //Párrafo que va a ser formateado.
        String strParagraph_I,
                                                            //Cantidad de caracteres máximo en una línea de párrafo no
                                                            //      estructurado.
                                                            //(Ej. en Cobol son 66 - 1 = 65, en C# son 60 - 2 = 58).
        int intPARUN_LINE_SIZE_I,
                                                            //Indentación de la primera línea del párrafo. (Ej. en Cobol
                                                            //      son 4 blancos, en C# es "" nada).
        String strPARUN_INDENT_FIRST_LINE_I,
                                                            //Indentación de las siguientes líneas del párrafo. (EJ. en
                                                            //      Cobol son 8 blancos, en C# son 6 blancos).
        String strPARUN_INDENT_OTHER_LINES_I
        )
    {
        LinkedList<String> lststrLineParun = new LinkedList<String>();

                                                            //Se cicla para generar líneas con pedazos de párrafo hasta
                                                            //      terminar todo el párrafo NO estructurado.
        int intPiece = 0;
        /*WHILE_DO*/
        while (
            intPiece < strParagraph_I.length()
            )
        {
            String strLineParun;

            Ostring ostrLineParun = new Ostring();
            Oint ointPiece = new Oint();
            ointPiece.v = intPiece;
            CodCodeAbstract.subGenerateLineFromPieceAndAdvance(ostrLineParun, ointPiece, strParagraph_I,
                    intPARUN_LINE_SIZE_I, strPARUN_INDENT_FIRST_LINE_I, strPARUN_INDENT_OTHER_LINES_I);
            intPiece = ointPiece.v;
            strLineParun = ostrLineParun.v;

            lststrLineParun.add(strLineParun);

                                                            //Avanza al inicio del siguiente pedazo, nótese que también
                                                            //      debe avanzar un espacio. (obviamente no hay espacio
                                                            //      si ya estoy al final de párrafo).
            intPiece = intPiece + 2;
        }

        return (String[])lststrLineParun.toArray();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subGenerateLineFromPieceAndAdvance(
                                                            //Con la información del siguiente pedazo genera 1 línea y
                                                            //      avanza en el párrafo.

                                                            //Línea formateada con el pedazo del párrafo.
        Ostring ostrLineParun_O,
                                                            //Posición dónde inicia el pedazo, regresa la posición donde
                                                            //      termina el pedazo.
        Oint ointPiece_IO,
                                                            //Párrafo del cual se tomará un pedazo.
        String strParagraph_I,
                                                            //Cantidad de caracteres máximo.
        int intPARUN_LINE_SIZE_I,
                                                            //Indentación de la primera línea del párrafo.
        String strPARUN_INDENT_FIRST_LINE_I,
                                                            //Indentación de las siguientes líneas del párrafo.
        String strPARUN_INDENT_OTHER_LINES_I
        )
    {
                                                            //La primera línea y las subsecuentes son iguales, con
                                                            //      excepción de la indentación.
        String strPARUN_INDENT;
        if (
                                                            //Estamos en el pedazo que dará forma a la primer línea del
                                                            //      párrafo.
            ointPiece_IO.v == 0
            )
        {
            strPARUN_INDENT = strPARUN_INDENT_FIRST_LINE_I;
        }
        else
        {
            strPARUN_INDENT = strPARUN_INDENT_OTHER_LINES_I;
        }

        int intPIECE_MAX_SIZE = intPARUN_LINE_SIZE_I - strPARUN_INDENT.length();

                                                            //Para seleccionar el pedazo a procesar, primero debo
                                                            //      localizar el fin del pedazo, en realidad localizaré
                                                            //      la posición DESPUÉS del último caracter del pedazo.
        int intPieceEndPlus1;
        if (
                                                            //Ya estamos en el último pedazo del párrafo.
            (ointPiece_IO.v + intPIECE_MAX_SIZE) >= strParagraph_I.length()
            )
        {
                                                            //Está en el final de párrafo, esto será DESPUÉS del
                                                            //      último caracter.
            intPieceEndPlus1 = strParagraph_I.length();
        }
        else
        {
                                                            //A partir del siguiente caracter al máximo que cabe se
                                                            //      busca en reversa el primer espacio para tomar un
                                                            //      "pedazo" de párrafo hasta antes de ese espacio.
            intPieceEndPlus1 = strParagraph_I.lastIndexOf(' ', ointPiece_IO.v + intPIECE_MAX_SIZE);
        }

                                                            //Extrae el pedazo de párrafo.
        String strPiece = strParagraph_I.substring(ointPiece_IO.v, intPieceEndPlus1);

                                                            //Se posiciona al final del pedazo.
        ointPiece_IO.v = intPieceEndPlus1 - 1;

                                                            //Fomratea la línea, si estamos en una la línea intermedia y
                                                            //      está termina con . o : debe ser corregida.
        ostrLineParun_O.v = strPARUN_INDENT + strPiece;
        if (
                                                            //Estamos en una línea intermedia.
            ((ointPiece_IO.v + strPiece.length()) < strParagraph_I.length()) &&
                                                            //La linea ya estandarizada termina con . o :
            (ostrLineParun_O.v.endsWith(".") || ostrLineParun_O.v.endsWith(":"))
            )
        {
                                                            //Sustituye el . o : para no indicar fin de párrafo.
            ostrLineParun_O.v = ostrLineParun_O.v.substring(0, ostrLineParun_O.v.length() - 1) + "?";
        }
    }

    //------------------------------------------------------------------------------------------------------------------
                                                            //Para construir objetos del tipo correcto desde clases
                                                            //      abstractas.

    //------------------------------------------------------------------------------------------------------------------
    public abstract CodCodeAbstract codxxNew(CodtypeEnum codtype_I, SyspathPath syspathFile_I,
        String[] arrstrLine_I);

    //------------------------------------------------------------------------------------------------------------------
    public abstract CodCodeAbstract codxxNew(SyspathPath syspahtFile_I);

    /*TASK RPS.Com Relevant Part Comments*/
    //--------------------------------------------------------------------------------------------------------------
    public abstract CodCodeAbstract codxxNew(ComCommentsAbstract com_I);
    /*END-TASK*/

    //--------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT CONSTRUCTORS*/

    //--------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //--------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    /*TASK RPS.Com Relevant Part Comments*/
    /*TASK Cod4 subSubtractComAndAdvance*/
    //--------------------------------------------------------------------------------------------------------------
    public void subSubtractComAndAdvance(                   //Extrae pedazo de código de comentarios y avanza la
                                                            //      posición en el codigo.
                                                            //En esta descripción utilizaré:
                                                            //"*" o * para referirme a la constante strCOM_FL_OR_TL.
                                                            //'<' o < para charCOM_TL_START.
                                                            //'>' o > para charCOM_TL_END.
                                                            //"//" o // para strCOM_EL.
                                                            //"/*" o /* para strCOM_DE_OR_EM_START.
                                                            //"+/" o +/ para strCOM_DE_OR_EM_END (nótese + debe ser *).
                                                            //Se tienen 5 tipos de comentarios:
                                                            //FULL_LINE, son un conjunto de líneas consecutivas
                                                            //      que inician (pos.0) con "*".
                                                            //[Ej. en COBOL se puede tener:
                                                            //*    ESTO ES LO QUE SE PUEDE TENER EN UN COMENTARIO COBOL,
                                                            //*        ESTE COMENTARIO ES DE 2 LINEAS.
                                                            //     MOVE A TO B
                                                            //]
                                                            //El conjunto termina al encontrar una línea que no inicia
                                                            //      con * o una linea que inicia con * pero corresponde
                                                            //      a un comentario TAG-FULL_LINE.
                                                            //TAG_FULL_LINE, es una sola línea, inicia similar a
                                                            //      FULL_LINE, sin embargo, después del primer
                                                            //      caracter distinto de espacio se tiene '<'.
                                                            //[Ej. en COBOL se puede tener:
                                                            //*    <LOOP>
                                                            //     MOVE '0' TO WSS100-1-EXIT-LOOP
                                                            //     PERFORM TEST AFTER UNTIL WSS100-1-EXIT-LOOP = '1'
                                                            //]
                                                            //END_OF_LINE, son un conjunto de líneas consecutivas que
                                                            //      tienen la información de comentarios al final, a
                                                            //      partir de "//", la primera línea del conjunto puede
                                                            //      contener codigo entes de "//".
                                                            //Algunos lenguajes requieren una doble marca "//" para
                                                            //      mantener alineada la información de comentarios (C#
                                                            //      requiere esta doble marca, Java no la requiere).
                                                            //[Ej. en C# se puede tener:
                                                            //      String strA;    //Esto es un comentario en C# que se
                                                            //      //              //      extiende 3 lineas,
                                                            //      //              //      esta es la última linea.
                                                            //      String strB:    //Aquí inicia otro comentario.
                                                            //]
                                                            //El conjunto termina a encontrar una línea que no inicia
                                                            //      con //, posiblemente con algunos espacios antes.
                                                            //El código que se extrae incluye todas las líneas del
                                                            //      conjunto, a partir de // (si se tiene una primer
                                                            //      marca de alineación, se incluye solo a partir de la
                                                            //      segunda marca //).
                                                            //EMBEDED, son conjuntos de caracteres en una misma línea,
                                                            //      pueden o no tener código antes y/o después.
                                                            //[Ej. en C# se puede tener:
                                                            //      String strA;/*Comentario EMBEDED+/String strB;
                                                            //]
                                                            //Si no "cierra" (estamos en la última línea, termino el
                                                            //      componente y no encontro +/), de todos modos esto,
                                                            //      incluyendo hasta el último caracter de la línea se
                                                            //      reconocerá como comentario EMBEDED.
                                                            //DELIMITED, son similares a los anteriores, sin embargo se
                                                            //      extienden por 2 ó más líneas.
                                                            //[Ej. en C# se puede tener:
                                                            //      String strA;/*Comentario DELIMITED que se extiende
                                                            //                  tres líneas, esta es la segunda,
                                                            //                  la tercer línea termina+/String strB;
                                                            //]
                                                            //Si no "cierra" (se avanzo 2 ó más líneas, termino el
                                                            //      componente y no encontro +/), de todos modos esto
                                                            //      se reconocerá como comentario DELIMITED.
                                                            //this[I], programa.

                                                            //Código de comentarios, las líneas incluirán los caracteres
                                                            //      de inicio y fin de comentarios.
        Oobject<CodCodeAbstract> ocodCom_O,
                                                            //Línea donde están los caracteres de inicio de comentarios,
                                                            //      regresa la última línea del conjunto, pudiendo ser
                                                            //      esta la línea donde están los caracteres de fin de
                                                            //      comentarios.
        Oint ointLine_IO,
                                                            //Posición donde inician los caracteres de inicio de
                                                            //      comentarios, regresa la posición donde está el
                                                            //      último caracter de los comentarios, pudiendo ser
                                                            //      este el último caracter de los comentarios o el
                                                            //      último caracters de fin de comentarios.
        Oint ointChar_IO
        )
    {
        this.subVerifyObjectConstructionIsFinished();

        if (
                                                            //La línea esta fuera del programa
            (ointLine_IO.v < 0) || (ointLine_IO.v >= this.arrstrLine().length)
            )
            Tools.subAbort(Tes2.strTo(this.arrstrLine().length , "arrstrLine.Length") + ", " +
                    Tes2.strTo(ointLine_IO.v, "intLine_IO") + " can not be outside this.arrstrLine");
        if (
                                                            //La posición esta fuera de la línea.
            (ointChar_IO.v < 0) || (ointChar_IO.v >= this.arrstrLine()[ointLine_IO.v].length())
            )
            Tools.subAbort(Tes2.strTo(this.arrstrLine()[ointLine_IO.v], "arrstrLine[" + ointLine_IO.v + " ]") + ", " +
                    Tes2.strTo(ointChar_IO.v, "intChar_IO") + " can not be outside the line");

                                                            //Guarda el pedazo de línea (a partir de donde está) para
                                                            //      facilitar la codificación.
        String strLineFromComStart = this.arrstrLine()[ointLine_IO.v].substring(ointChar_IO.v);

        /*CASE*/
        if (
            this.comDUMMY().boolCOM_FL_OR_TL_IS_ACCEPTED() &&
                                                            //FULL_LINE y TAG_FULL_LINE deben iniciar en la pos. 0.
            (ointChar_IO.v == 0) &&
            strLineFromComStart.startsWith(this.comDUMMY().strCOM_FL_OR_TL())
            )
        {
                                                            //Puede ser LINEAS-COMPLETAS o TAG_FULL_LINE

            if (
                                                            //Es un TAG_FULL_LINE, tiene la forma *  <algo
                Tools.trimStart(this.arrstrLine()[ointLine_IO.v].substring(this.comDUMMY().strCOM_FL_OR_TL().length()),
                    ' ').startsWith(Character.toString(this.comDUMMY().charCOM_TL_START()) )
                )
            {
                this.subSubtractComTlAndAdvance(ocodCom_O, ointLine_IO.v, ointChar_IO);
            }
            else
            {

                this.subSubtractComFlAndAdvance(ocodCom_O, ointLine_IO, ointChar_IO);
            }
        }
        else if (
            this.comDUMMY().boolCOM_EL_IS_ACCEPTED() &&
            strLineFromComStart.startsWith(this.comDUMMY().strCOM_EL())
            )
        {
            this.subSubtractComElAndAdvance(ocodCom_O, ointLine_IO, ointChar_IO);
        }
        else if (
            this.comDUMMY().boolCOM_DE_OR_EM_IS_ACCEPTED() &&
            strLineFromComStart.startsWith(this.comDUMMY().strCOM_DE_OR_EM_START())
            )
        {
                                                            //Puede ser DELIMITED o EMBEDED.

            if (
                                                            //EMBEDED que cierra, tiene la forma /*algo*/
                strLineFromComStart.substring(this.comDUMMY().strCOM_DE_OR_EM_START().length()).
                contains(this.comDUMMY().strCOM_DE_OR_EM_END()) ||
                                                            //EMBEDES que no cierre y esta en la última línea.
                (ointLine_IO.v == (this.arrstrLine().length - 1))
                )
            {
                this.subSubtractComEmAndAdvance(ocodCom_O, ointLine_IO.v, ointChar_IO);
            }
            else
            {
                this.subSubtractComDeAndAdvance(ocodCom_O, ointLine_IO, ointChar_IO);
            }
        }
        else
        {
            if (
                true
                )
                Tools.subAbort(Tes2.strTo(this.arrstrLine()[ointLine_IO.v], "arrstrLine[" + ointLine_IO.v + "]") +
                        ", " + Tes2.strTo(ointChar_IO.v, "intChar_IO") + ", " +
                        Tes2.strTo(this.comDUMMY().strCOM_FL_OR_TL(), "comDUMMY.strCOM_FL_OR_TL") + ", " +
                        Tes2.strTo(this.comDUMMY().strCOM_EL(), "comDUMMY.strCOM_EL") + ", " +
                        Tes2.strTo(this.comDUMMY().strCOM_DE_OR_EM_START(), "comDUMMY.strCOM_DE_OR_EM_START") +
                        " was not recognized has the start of a comment");

                                                            //Asigna cualquier cosa, no importa, va a abortar
            ocodCom_O.v = null;
        }
        /*END-CASE*/
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subSubtractComFlAndAdvance(                //Extrae pedazo de código de comentarios tipo FULL_LINE y
                                                            //      avanza la posición en el código.
                                                            //this[I], programa.

                                                            //Código de comentarios, todas las líneas consecutivas que
                                                            //      inicien con * son de este conjunto (posiblemente
                                                            //      hasta antes un TAG_FULL_LINE).
        Oobject<CodCodeAbstract> ocodCom_O,
                                                            //Línea donde inician los comentarios, regresa la última
                                                            //      línea del conjunto.
        Oint ointLine_IO,
                                                            //Regresa la última posición de la última línea del
                                                            //      conjunto.
        Oint ointChar_O
        )
    {
                                                            //Para guardar las líneas que extrae.
        LinkedList<String> lststrLineCom = new LinkedList<String>();

                                                            //Toma todas las líneas consecutivas que inician con
                                                            //      strCOM_FL_OR_TL siempre y cuando no encuentre un
                                                            //      comentario TAG_FULL_LINE.
        /*UNTIL-DO*/
        while (!(
                                                            //Llego al fin.
            (ointLine_IO.v >= this.arrstrLine().length) ||
                                                            //Ya no es una línea de este conjunto.
            !this.arrstrLine()[ointLine_IO.v].startsWith(this.comDUMMY().strCOM_FL_OR_TL()) ||
                                                            //Encontró un comentario es un TAG
            Tools.trimStart(this.arrstrLine()[ointLine_IO.v].substring(this.comDUMMY().strCOM_FL_OR_TL().length()),
                ' ').startsWith(Character.toString(this.comDUMMY().charCOM_TL_START()))
        ))
        {
            lststrLineCom.add(this.arrstrLine()[ointLine_IO.v]);

            ointLine_IO.v = ointLine_IO.v + 1;
        }

                                                            //Genera el objeto código del comentario.
        ocodCom_O.v = this.codxxNew(CodtypeEnum.COM_FULL_LINE, null, lststrLineCom.toArray(new String[0]));

                                                            //Regresa a la última línea del comentario.
        ointLine_IO.v = ointLine_IO.v - 1;

                                                            //Calcula la posición final (al final de la última línea).
        ointChar_O.v = this.arrstrLine()[ointLine_IO.v].length() - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subSubtractComTlAndAdvance(                //Extrae pedazo de código de comentarios tipo TAG_FULL_LINE
                                                            //      y avanza la posición en el código.
                                                            //this[I], programa.

                                                            //Código de comentarios, es de una sola línea.
        Oobject<CodCodeAbstract> ocodCom_O,
                                                            //Línea donde están los comentarios.
        int intLine_I,
                                                            //regresa la posición del último caracter de la línea.
        Oint ointChar_O
        )
    {
                                                            //Construye el código comentario TAG_FULL_LINE con solo
                                                            //      una línea.
        String strLineTl = this.arrstrLine()[intLine_I];
        String[] arrstrLineTl = { this.arrstrLine()[intLine_I] };
        ocodCom_O.v = this.codxxNew(CodtypeEnum.COM_TAG_FULL_LINE, null, arrstrLineTl);

                                                            //Toma la última posición de la línea.
        ointChar_O.v = strLineTl.length() - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subSubtractComElAndAdvance(                //Extrae pedazo de código de comentarios tipo END_OF_LINE
                                                            //      y avanza la posición en el código.
                                                            //this[I], programa.

                                                            //Código de comentarios, esto será un conjunto de pedazos
                                                            //      de líneas, todos a partir de //.
        Oobject<CodCodeAbstract> ocodCom_O,
                                                            //Posición donde inicia el comentario (en un //), regresa la
                                                            //      posición del último caracter de comentarios.
        Oint ointLine_IO,
        Oint ointChar_IO
        )
    {
                                                            //Se podría verificar que ésta fuera la primer línea del
                                                            //      conjunto, sin embargo esto no es trivial, juzgue
                                                            //      conveniente no incluir esta verificación dado que
                                                            //      este método solo se usa en el tokenizador.

                                                            //Para guardar las líneas que extrae.
        LinkedList<String> lststrLineEl = new LinkedList<String>();

                                                            //Si la primer línea tiene código antes de // se toma a
                                                            //      a partir de //.
        if (
                                                            //La línea tiene información antes de la marca
            Tools.trimStart(this.arrstrLine()[ointLine_IO.v].substring(0, ointChar_IO.v), ' ').length() > 0
            )
        {
                                                            //Extrae el comentario (esta a partir de la marca)
            String strLineElWithoutCodeBefore = this.arrstrLine()[ointLine_IO.v].substring(ointChar_IO.v);
            lststrLineEl.add(strLineElWithoutCodeBefore);

            ointLine_IO.v = ointLine_IO.v + 1;
        }

                                                            //Procesa el resto del conjunto de líneas de comentarios EL.
        /*LOOP*/
        while (true)
        {
                                                            //Saca el posible comentario para facilitar el código y
                                                            //      hacerlo eficiente.
            String strLineEl;
            if (
                ointLine_IO.v < this.arrstrLine().length
                )
            {
                strLineEl = Tools.trimStart(this.arrstrLine()[ointLine_IO.v], ' ');
            }
            else
            {
                                                            //Esto no se usará nunca.
                strLineEl = null;
            }

            /*DO-NOT-EXIT-IF*/
            if (!(
                (ointLine_IO.v < this.arrstrLine().length) &&
                                                            //Lo primero diferente de blanco en la línea es //.
                strLineEl.startsWith(this.comDUMMY().strCOM_EL())
            ))
                break;

                                                            //Si la instancia de la tecnología lo requiere, busca una
                                                            //      posible segunda marca.
                                                            //Nótese que esta información corresponde a la INSTANCIA (C#
                                                            //      si lo requiere, pero java no).
            if (
                                                            //La variable calculada indica que si requiere alineación.
                this.boolCOM_EL_REQUIRES_ALIGNMENT()
                )
            {
                                                            //Analiza si tiene una segunda marca para quitar la primera.

                                                            //Quita la primer marca.
                String strLineWithoutFirstMark = strLineEl.substring(this.comDUMMY().strCOM_EL().length()).trim();

                                                            //Si existe la segunda marca, quita la primera.
                if (
                                                            //Encontró la segunda marca.
                    strLineWithoutFirstMark.startsWith(this.comDUMMY().strCOM_EL())
                    )
                {
                    strLineEl = strLineWithoutFirstMark;
                }
            }

            lststrLineEl.add(strLineEl);

            ointLine_IO.v = ointLine_IO.v + 1;
        }
        /*LOOP*/

                                                            //Genera el objeto código del comentario.
        ocodCom_O.v = this.codxxNew(CodtypeEnum.COM_END_OF_LINE, null, (String[])lststrLineEl.toArray());

                                                            //Se posiciona al final del comentario.
        ointLine_IO.v = ointLine_IO.v - 1;
        ointChar_IO.v = this.arrstrLine()[ointLine_IO.v].length() - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subSubtractComEmAndAdvance(                //Extrae pedazo de código de comentarios tipo EMBEDED y
                                                            //      avanza la posición en el código.
                                                            //this[I], programa.

                                                            //Código de comentarios, será solo un pedazo de línea.
        Oobject<CodCodeAbstract> ocodCom_O,
                                                            //Línea donde están los comentarios.
        int intLine_I,
                                                            //Posición donde inicia /*, regresa la posición donde
                                                            //      termina +/.
        Oint ointChar_IO
        )
    {
                                                            //To easy code
        String strLineResto = this.arrstrLine()[intLine_I].substring(ointChar_IO.v);

                                                            //Busca la posición de la marca de fin.
        int intCharMarcaFin = strLineResto.indexOf(this.comDUMMY().strCOM_DE_OR_EM_END(),
                this.comDUMMY().strCOM_DE_OR_EM_START().length());

                                                            //Toma la línea de comentarios según corresponda.
        String strLineComEM;
        if (
                                                            //Sí tiene marca de fin.
            intCharMarcaFin >= 0
            )
        {
                                                            //Si encontro la marca, toma de /* hasta */
            strLineComEM = strLineResto.substring(0, intCharMarcaFin + this.comDUMMY().strCOM_DE_OR_EM_END().length());
        }
        else
        {
                                                            //No encontro la marca, estamos en el caso especial de ser
                                                            //      la última del código y debe tomar el resto de la
                                                            //      línea.
                                                            //
            strLineComEM = strLineResto;
        }

                                                            //Construye el código comentario EMBEDED con solo esta
                                                            //      línea.
        String[] arrstrLineComEM = { strLineComEM };
        ocodCom_O.v = this.codxxNew(CodtypeEnum.COM_EMBEDED, null, arrstrLineComEM);

                                                            //Se posiciona al final del comentario.
        ointChar_IO.v = ointChar_IO.v + strLineComEM.length() - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subSubtractComDeAndAdvance(                //Extrae pedazo de código de comentarios tipo DELIMITED y
                                                            //      avanza la posición en el código.
                                                            //this[I], programa.

                                                            //Código de comentarios, todas las líneas consecutivas desde
                                                            //      /* hasta +/, la primera y la última son solo pedazos
                                                            //      de línea.
        Oobject<CodCodeAbstract> ocodCom_O,
                                                            //Posición donde inicia el comentario (/*), regresa la
                                                            //      posición del último caracter de comentarios (fin de
                                                            //      +/).
        Oint ointLine_IO,
        Oint ointChar_IO
        )
    {
                                                            //Para guardar las líneas que extrae.
        LinkedList<String> lststrLineCom = new LinkedList<String>();

                                                            //Añade la primera línea del comentario DELIMITED.
        String strLine1Com = this.arrstrLine()[ointLine_IO.v].substring(ointChar_IO.v);
        lststrLineCom.add(strLine1Com);

                                                            //Avanza a la primera línea de la intermedias que serán
                                                            //      tomada
        ointLine_IO.v = ointLine_IO.v + 1;

                                                            //Añade todas las líneas intermedias.
        /*UNTIL-DO*/
        while (!(
                                                            //Se salió del código.
            (ointLine_IO.v >= this.arrstrLine().length) ||
                                                            //Encontró marca de fin.
            this.arrstrLine()[ointLine_IO.v].contains(this.comDUMMY().strCOM_DE_OR_EM_END())
        ))
        {
                                                            //Estamos en una línea intermedia, la toma completa.

            lststrLineCom.add(this.arrstrLine()[ointLine_IO.v]);

            ointLine_IO.v = ointLine_IO.v + 1;
        }

                                                            //Verifico si tengo la línea correcta de cierre de com
                                                            //      DELIMITED.
        if (
                                                            //No ha llegado al fin, esto es si encontró la marca de
                                                            //      fin.
            ointLine_IO.v < this.arrstrLine().length
            )
        {
                                                            //Tiene marca de fin, debe tomar esta última línea del
                                                            //      comentario DELIMITED hasta incluyendo su marca de
                                                            //      fin.

                                                            //Calcula la longitud de la linea de com, incluyendo */.
            int intLongLineLastCom =  this.arrstrLine()[ointLine_IO.v].indexOf(this.comDUMMY().strCOM_DE_OR_EM_END()) +
                    this.comDUMMY().strCOM_DE_OR_EM_END().length();

                                                            //Añade la última línea del comentario DELIMITED.
            String strLineLastCom = this.arrstrLine()[ointLine_IO.v].substring(0, intLongLineLastCom);
            lststrLineCom.add(strLineLastCom);

            ointLine_IO.v = ointLine_IO.v + 1;
        }

                                                            //Genera el objeto código del comentario.
        ocodCom_O.v = this.codxxNew(CodtypeEnum.COM_DELIMITED, null, (String[])lststrLineCom.toArray());

                                                            //Se posiciona en el último caracter del com.
                                                            //Nótese que el char será el último char de la lista.
        ointLine_IO.v = ointLine_IO.v - 1;
        ointChar_IO.v = lststrLineCom.get(lststrLineCom.size() - 1).length() - 1;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/
    /*END-TASK*/
}
//======================================================================================================================
/*END-TASK*/