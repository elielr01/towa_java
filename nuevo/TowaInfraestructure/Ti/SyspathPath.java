package Ti;

//                                                          //AUTHOR: Towa (GLG-Gerardo López).
//                                                          //CO-AUTHOR: Towa ().
//                                                          //DATE: 19-Febrero-2014.
//                                                          //PURPOSE:
//                                                          //Clase para manipular paths.

import java.io.File;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.util.Arrays;

public /*MUTABLE*/ class SyspathPath extends BclassBaseClassAbstract
//                                                          //Clase para manipular path.
//                                                          //Debe funcionar correctamente para archivos y directorios
//                                                          //      locales y en la red.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //                                                      //Nótese que estos objetos (syspath) solo son una referencia
    //                                                      //      a un archivo o directorio que están en disco (podría
    //                                                      //      ser una referencia que ni siquiera esta en el
    //                                                      //      disco).
    //                                                      //La información que recolecta solo es válida en el momento
    //                                                      //      de la construcción del objeto o en el momento en que
    //                                                      //      se hacer un "refresh" (Ej. en el momento de la
    //                                                      //      construcción podría ser una referencia a un archivo
    //                                                      //      que existe, sin embargo unos momento después
    //                                                      //      "alguién" borra del disco el archivo y el contenido
    //                                                      //      de este objeto syspath ya no es preciso).
    //                                                      //El "estado" del objeto solo cambia cuando se hace un
    //                                                      //      "refresh".
    @Override public BclassmutabilityEnum bclassmutability() { return BclassmutabilityEnum.MUTABLE; }

    //                                                      //En teoria el conjunto de caracteres válidos en un path es
    //                                                      //      muy extenso, sin embargo, en la realidad, cuando se
    //                                                      //      desea mover archivos y directorios entre diferentes
    //                                                      //      sistemas operativos (windows, unix, os de mac, etc.)
    //                                                      //      suelen suceder problemas.
    //                                                      //Por estándar Towa optamos por permitir un conjunto MUY
    //                                                      //      CONSERVADOR DE CARACTERES.
    //                                                      //CONFORME ENTENDAMOS MEJOR ESTA PROBLEMATICA, ESTA LISTA
    //                                                      //      DE CARACTERES SERÁ AMPLIADA O RECORTADA.
    private static String strCHAR_IN_SYSPATH =
        //                                                  //Dígitos y letras sin acentos.
        "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" +
        //                                                  //GLG 27Feb2017, opte por incluir como posibilidad acentos
        //                                                  //      en español y las Ñ ñ.
        "ÁÉÍÓÚÜ" + "áéíóúü" + "Ññ" +
        //                                                  //Espacio.
        " " +
        //                                                  //Algunos caracteres especiales.
        ",._()[]{}$@+-" + "%&#*" +
        //                                                  //Caracteres necesarios en un path.
        //                                                  //No incluí el Path.PathSeparator(;), no entiendo para que
        //                                                  //      se usa.
        /*File.pathSeparatorChar*/ ":" + File.separatorChar;
    //                                                      //Información anterior en un arreglo ordenado.
    private static char[] arrcharIN_SYSPATH;

    //                                                      //Inicio de un full path en red.
    private static String strINICIO_FULL_PATH_RED = Character.toString(File.separatorChar) + File.separatorChar;

    static
        //                                                  //Prepara las constantes.
        //                                                  //1. Prepara y ordena CHAR_IN_SYSPATH.
        //                                                  //2. Separa y ordena TO_CONVERT y CONVERSION.
    {
        //                                                  //1. Prepara y ordena CHAR_IN_SYSPATH.
        SyspathPath.arrcharIN_SYSPATH = strCHAR_IN_SYSPATH.toCharArray();
        Arrays.sort(arrcharIN_SYSPATH);

        Tools.subVerifyDuplicate(SyspathPath.arrcharIN_SYSPATH, "SyspathPath.arrcharIN_SYSPATH");
    }

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    //                                                      //DO_NOT_EXIST_ON_DISK, FILE o DIRECTORY.
    private /*MUTABLE*/ SyspathtypeEnum syspathtype_Z;
    public SyspathtypeEnum syspathtype() { return this.syspathtype_Z; }

    //                                                      //Si es válido y se tiene acceso, contiene el Full Path.
    //                                                      //En su defecto, contiene el Path tal se proporciono y no
    //                                                      //      se deben tomar sus propiedades (aborta).
    private /*MUTABLE*/ String strFullPath_Z;
    public String strFullPath() { return this.strFullPath_Z; }

    //                                                      //Determina si un Nombre, Path o Full Path (File or
    //                                                      //      Directory) es válido en su estructura de caracteres.
    //                                                      //Se utiliza Path.GetFullPath(strFileName_I) para
    //                                                      //      verificarlo.
    //                                                      //Si recibe un SecurityException no lo rechaza.
    private /*MUTABLE*/ boolean boolIsValid_Z;
    public boolean boolIsValid() { return this.boolIsValid_Z; }

    //                                                      //Determina si se tiene acceso a un Nombre, Path o Full Path
    //                                                      //      (File or Directory).
    //                                                      //Se utiliza Path.GetFullPath(strFileName_I) para
    //                                                      //      verificarlo.
    //                                                      //Si recibe un SecurityException indica que no tiene acceso.
    //                                                      //El path debe ser válido.
    private /*MUTABLE*/ boolean boolHaveAccessTo_Z;
    public boolean boolHaveAccessTo() { return this.boolHaveAccessTo_Z; }

    //                                                      //LOCAL o NETWORK.
    private SyspathwhereEnum syspathwhere_Z;
    public SyspathwhereEnum syspathwhere() { return this.syspathwhere_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //                                                      //Es un Full Path local (esta en este equipo).
    private Boolean boolIsLocal_Z = null;
    public boolean boolIsLocal()
    {
        if (
            this.boolIsLocal_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.boolIsLocal_Z =
            (
                this.syspathwhere() == SyspathwhereEnum.LOCAL
            );

            this.subSetIsResetOff();
        }

        return (boolean)this.boolIsLocal_Z;
    }

    //                                                      //Es un Full Path que esta en red.
    private Boolean boolIsNetwork_Z = null;
    public boolean boolIsNetwork()
    {
        if (
            this.boolIsNetwork_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.boolIsNetwork_Z =
            (
                this.syspathwhere() == SyspathwhereEnum.NETWORK
            );

            this.subSetIsResetOff();
        }

        return (boolean)this.boolIsNetwork_Z;
    }

    //                                                      //Corresponde a un File o Directory que existe.
    private Boolean boolExists_Z = null;
    public boolean boolExists()
    {
        if (
            this.boolExists_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.boolExists_Z =
            (
                //                                  //Si existe, es un File o Directory
                this.syspathtype() != SyspathtypeEnum.DO_NOT_EXIST_ON_DISK
            );

            this.subSetIsResetOff();
        }

        return (boolean)this.boolExists_Z;
    }

    //                                                      //Es un File.
    private Boolean boolIsFile_Z = null;
    public boolean boolIsFile()
    {
        if (
            this.boolIsFile_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.boolIsFile_Z =
            (
                this.syspathtype() == SyspathtypeEnum.FILE
            );

            this.subSetIsResetOff();
        }

        return (boolean)this.boolIsFile_Z;
    }

    //                                                      //Es un Directory.
    private Boolean boolIsDirectory_Z = null;
    public boolean boolIsDirectory()
    {
        if (
            this.boolIsDirectory_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.boolIsDirectory_Z =
            (
                this.syspathtype() == SyspathtypeEnum.DIRECTORY
            );

            this.subSetIsResetOff();
        }

        return (boolean)this.boolIsDirectory_Z;
    }

    //                                                      //SOLO SI EXISTE, PUEDE SER FILE O DIRECTORY.
    //                                                      //Si se solicitan cuando no existe va a abortar.

    //                                                      //Nombre del archivo o directorio (sin el directorio que lo
    //                                                      //      contiene).
    private String strName_Z = null;
    public String strName()
    {
        if (
            this.strName_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.strName_Z = Paths.get(this.strFullPath()).getFileName().toString();

            this.subSetIsResetOff();

        }

        return this.strName_Z;
    }
    //                                                      //syspath de la raíz.
    private SyspathPath syspathRoot_Z = null;
    public SyspathPath syspathRoot()
    {
        if (
            this.syspathRoot_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            //                                              //Extraigo raíz.
            String strRoot = Paths.get(this.strFullPath()).getRoot().toString();

            this.syspathRoot_Z = new SyspathPath(strRoot);

            this.subSetIsResetOff();
        }

        return this.syspathRoot_Z;
    }

    //                                                      //SOLO SI ES UN FILE.
    //                                                      //Si se solicitan cuando es un directorio va a abortar.

    //                                                      //File extension.
    private String strFileExtension_Z = null;
    public String strFileExtension()
    {
        if (
            this.strFileExtension_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            if (
                this.boolIsDirectory()
                )
                Tes3.subAbort(Tes3.strTo(this, "this") + " is a Directory, can not have File Extension");

            int intDot = Paths.get(this.strFullPath()).getFileName().toString().lastIndexOf('.');
            this.strFileExtension_Z = Paths.get(this.strFullPath()).getFileName().toString().substring(intDot);

            this.subSetIsResetOff();
        }

        return this.strFileExtension_Z;
    }

    //                                                      //Nombre sin el file extensión.
    private String strFileNameWithoutExtension_Z = null;
    public String strFileNameWithoutExtension()
    {
        if (
            this.strFileNameWithoutExtension_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            if (
                this.boolIsDirectory()
                )
                Tes3.subAbort(Tes3.strTo(this, "this") + " is a Directory, can not have File Extension");

            int intDot = Paths.get(this.strFullPath()).getFileName().toString().lastIndexOf('.');
            this.strFileNameWithoutExtension_Z =
                Paths.get(this.strFullPath()).getFileName().toString().substring(0, intDot);

            this.subSetIsResetOff();
        }

        return this.strFileNameWithoutExtension_Z;
    }

    //                                                      //Path del directorio del directorio/archivo que tenemos.
    private SyspathPath syspathDirectory_Z;
    public SyspathPath syspathDirectory()
    {
        if (
            this.syspathDirectory_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            //                                              //Extraigo directorio al que pertence el Name en el path.
            String strDirectory = Paths.get(this.strFullPath()).getParent().toString();

            this.syspathDirectory_Z = new SyspathPath(strDirectory);

            this.subSetIsResetOff();
        }

        return this.syspathDirectory_Z;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void subResetOneClass()
    {
        this.boolIsLocal_Z = null;
        this.boolIsNetwork_Z = null;
        this.boolExists_Z = null;
        this.boolIsFile_Z = null;
        this.boolIsDirectory_Z = null;

        this.strName_Z = null;

        this.syspathRoot_Z = null;

        this.strFileExtension_Z = null;
        this.strFileNameWithoutExtension_Z = null;
        this.syspathDirectory_Z = null;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(StrtoEnum testoptionSHORT_I)
    {
        String strObjId = Tes3.strGetObjId(this);

        return strObjId + "[" + super.strTo(StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.syspathtype(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strFullPath(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.boolIsValid(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.boolHaveAccessTo(), StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.syspathwhere(), StrtoEnum.SHORT) + "]";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        String strObjId = Tes3.strGetObjId(this);

        return strObjId + "{" + Tes3.strTo(this.syspathtype(), "syspathtype") + ", " +
            Tes3.strTo(this.strFullPath(), "strFullPath") + ", " +
            Tes3.strTo(this.boolIsValid(), "boolIsValid") + ", " +
            Tes3.strTo(this.boolHaveAccessTo(), "boolHaveAccessTo") + ", " +
            Tes3.strTo(this.syspathwhere(), "syspathwhere") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    public SyspathPath(
        //                                                  //Crea un objeto Path.
        //                                                  //this.*[O], asigna valores.

        //                                                  //Nombre, Path relativo o Full Path.
        String strPath_I
        )
    {
        super(false);

        if (
            strPath_I == null
            )
            Tes3.subAbort(Tes3.strTo(strPath_I, "strPath_I") + " should have a value");

        //                                                  //Por lo pronto asigna los valores que tendrá si no es
        //                                                  //      válido o no se tiene acceso.
        this.strFullPath_Z = strPath_I;

        //                                                  //De ser posible completa el FullPath.
        //                                                  //Asigna las otras 4 variables.
        this.subComputeStatusVariables();

        this.subSetObjectConstructionFinish();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public void subRefresh(
        //                                                  //Actualiza el status del Path o Full Path (puedes ser File,
        //                                                  //      Directory o no existir).
        //                                                  //Si es Path relativo lo cambia a Full Path.
        //                                                  //Se utiliza Path.GetFullPath().
        //                                                  //this[M], toma información y también refresca todo.
        )
    {
        this.subVerifyObjectConstructionIsFinished();
        this.subResetObject();

        this.subComputeStatusVariables();

        this.subVerifyIsReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public SyspathPath syspathAddName(
        //                                                  //Le añade un nombre a un  syspath de un directorio.
        //                                                  //this[I], debe ser un directorio, toma información.

        //                                                  //syspath, ya combinado.

        //                                                  //Nombre a combinar con el path del this.
        String strNameToCombine_I
        )
    {
        if (
            !this.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(this, StrtoEnum.SHORT) + " do not exist as directory");

        String strFullPathMasName;
        File fileFullPath = new File(this.strFullPath());
        strFullPathMasName = new File(fileFullPath, strNameToCombine_I).getAbsolutePath();

        //                                                  //Regresa la combinación.
        return new SyspathPath(strFullPathMasName);
    }
    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private void subComputeStatusVariables(
        //                                                  //Actualiza el status del Path o Full Path (puedes ser File,
        //                                                  //      Directory o no existir).
        //                                                  //Si es Path relativo lo cambia a Full Path.
        //                                                  //Se utiliza Path.GetFullPath().
        //                                                  //this[M], toma información y también refresca todo.
        )
    {
        //                                                  //Serán true a menos que se detecte alguna situación 
        //                                                  //      anormal.
        this.boolIsValid_Z = true;
        this.boolHaveAccessTo_Z = true;

        //                                                  //Trata de obtener el full path.
        try
        {
            this.strFullPath_Z = Paths.get(this.strFullPath()).toAbsolutePath().toString();
        }
        catch (AccessControlException sysexcepError)
        {
            //                                              //El nombre esta bien formado, pero no se tiene acceso.
            this.boolIsValid_Z = true;
            this.boolHaveAccessTo_Z = false;

            //                                              //ESTE MENSAJE SE DEBE ELIMINAR EN EL FUTURO.
            Tes3.subWarning(Tes3.strTo(this.strFullPath(), "this.strFullPath") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " do not have access permition");
        }
        catch (Exception sysexcepError)
        {
            //                                              //El nombre no esta bien formado.
            this.boolIsValid_Z = false;
            this.boolHaveAccessTo_Z = false;

            //                                              //ESTE MENSAJE SE DEBE ELIMINAR EN EL FUTURO.
            Tes3.subWarning(Tes3.strTo(this.strFullPath(), "this.strFullPath") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " is not a valid path");
        }

        //                                                  //Por estándar solo se aceptan como validos los caracteres
        //                                                  //      IN_SYSPATH
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            (intI >= this.strFullPath().length()) ||
            (Arrays.binarySearch(SyspathPath.arrcharIN_SYSPATH, this.strFullPath().charAt(intI)) < 0)
            ))
        {
            intI = intI + 1;
        }

        //                                                  //Si salió antes de terminar es que encontró char invalid
        if (
            intI < this.strFullPath().length()
            )
        {
            //                                              //Se marca.
            this.boolIsValid_Z = false;
            this.boolHaveAccessTo_Z = false;
        }

        //                                                  //Adicionalmente, si inicia con ftp:, http:, https: o file:
        //                                                  //      será inválido (y sin acceso).
        //                                                  //Aparentemente el Path los acepta pero FileInfo y
        //                                                  //      DirectoryInfo NO.

        //                                                  //Corta y pasa a minúsculas.
        //                                                  //Para cortar toma la longitud del "inicio" más largo.
        String strFullPathShort;
        if (
            this.strFullPath().length() <= "https:".length()
            )
        {
            strFullPathShort = this.strFullPath_Z;
        }
        else
        {
            strFullPathShort = this.strFullPath().substring(0, 10);
        }
        strFullPathShort = strFullPathShort.toLowerCase();

        if (
            strFullPathShort.startsWith("ftp:") || strFullPathShort.startsWith("http:") ||
                strFullPathShort.startsWith("https:") || strFullPathShort.startsWith("file:")
            )
        {
            //                                              //Se marca.
            this.boolIsValid_Z = false;
            this.boolHaveAccessTo_Z = false;
        }

        //                                                  //Si todo sigue correcto es que no se fue por catch y que
        //                                                  //      ya tiene un Full Path.

        if (
            this.boolIsValid_Z && boolHaveAccessTo()
            )
        {
            //                                              //Determina el where
            /*CASE*/
            if (
                //                                          //Es local (tiene x:...).
                //                                          //Usually on Windows local paths starts with "C:"
                this.strFullPath().charAt(1) == ':' ||
                //                                          //Usually in Unix (Mac OX for example) local paths starts
                //                                          //      with "//"
                this.strFullPath().charAt(0) == File.separatorChar
                )
            {
                this.syspathwhere_Z = SyspathwhereEnum.LOCAL;
            }
            else if (
                //                                          //Es red (tiene \\...).
                this.strFullPath().startsWith(strINICIO_FULL_PATH_RED)
                )
            {
                this.syspathwhere_Z = SyspathwhereEnum.NETWORK;
            }
            else
            {
                Tes3.subAbort(Tes3.strTo(this, StrtoEnum.SHORT) + " path start not valid");

                this.syspathwhere_Z = SyspathwhereEnum.Z_ERROR_NOT_DEFINED;
            }
            /*END-CASE*/

            //                                              //Determina tipo.
            /*CASE*/
            if (
                //                                          //Equivale a File.Exists() de C# lo reconoce como archivo.
                new File(this.strFullPath()).exists() && new File(this.strFullPath()).isFile()
                )
            {
                this.syspathtype_Z = SyspathtypeEnum.FILE;
            }
            else if (
                //                                          //Equivale a Directory.Exists() lo reconoce como directorio.
                new File(this.strFullPath()).exists() && new File(this.strFullPath()).isDirectory()
                )
            {
                this.syspathtype_Z = SyspathtypeEnum.DIRECTORY;
            }
            else
            {
                this.syspathtype_Z = SyspathtypeEnum.DO_NOT_EXIST_ON_DISK;
            }
            /*END-CASE*/
        }
    }

    //------------------------------------------------------------------------------------------------------------------
}
/*END-TASK*/
