package TowaInfrastructure;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.util.Arrays;
															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: 19-Febrero-2014.
															//PURPOSE:
															//Clase para manipular paths.

public /*MUTABLE*/ class SyspathPath extends BclassBaseClassAbstract
	                                                      	//Clase para manipular path.
	                                                      	//Debe funcionar correctamente para archivos y directorios
	                                                      	//      locales y en la red.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    @Override protected BclassmutabilityEnum bclassmutability(){ return BclassmutabilityEnum.MUTABLE; }

                                                      		//En teoria el conjunto de caracteres válidos en un path es
                                                      		//      muy extenso, sin embargo, en la realidad, cuando se
                                                      		//      desea mover archivos y directorios entre diferentes
                                                      		//      sistemas operativos (windows, unix, os de mac, etc.)
                                                      		//      suelen suceder problemas.
                                                      		//Por estándar Towa optamos por permitir un conjunto MUY
                                                      		//      CONSERVADOR DE CARACTERES.
                                                      		//CONFORME ENTENDAMOS MEJOR ESTA PROBLEMATICA, ESTA LISTA
                                                      		//      DE CARACTERES SERÁ AMPLIADA O RECORTADA.
    private static /*readonly*/ String strCHAR_USEFUL =
                                                      		//Dígitos y letras sin acentos.
        "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" +
                                                      		//Espacio.
        " " +
                                                      		//Algunos caracteres especiales.
        ",._()[]{}$@+-" +
                                                            //TODO: Verificar Path.VolumeSeparator y posible equivalente
                                                      		//Caracteres necesarios en un path.
                                                      		//No incluí el Path.VolumeSeparator(:), no hay equivalente
                                                            //      directo, por lo que el valor fue "hardcoded" (:)
        /*Path.VolumeSeparatorChar*/ ":" + File.separatorChar;
                                                      		//Información anterior en un arreglo ordenado.
    private static /*readonly*/ char[] arrcharUSEFUL;

                                                      		//Adicionalmente, algunos problemas se pueden corregir
                                                      		//      fácilmente cambiando algunos caracteres.
                                                      		//EN EL FUTURO SE PODRÁN AÑADIR OTROS PARES DE CARACTERES.
    private final static String strCHAR_TO_CONVERT_AND_CONVERSION =
                                                      		//Caracteres acentuados, se eliminan los acentos.
        "ÁAÉEÍIÓOÚUÀAÈEÌIÒOÙUÄAËEÏIÖOÜUÂAÊEÎIÔOÛU" + "áaéeíióoúuàaèeìiòoùuäaëeïiöoüuâaêeîiôoûu" + "ÑNñn" +
                                                      		//También caracteres aceptados en mac os pero no en windows.
         "?_&_*_<_>_|_#_%_";

                                                      		//Información anterior separada y 2 arreglos y ordenada por
                                                      		//      el primero.
    private static /*readonly*/ char[] arrcharTO_CONVERT;
    private static /*readonly*/ char[] arrcharCONVERSION;

                                                      		//Inicio de un full path en red.
    private static /*readonly*/ String strINICIO_FULL_PATH_RED =
        "" + File.separatorChar + File.separatorChar;

    static                                  				//Prepara las constantes.
                                                      		//1. Prepara y ordena strCHAR_USEFUL en arrcharUSEFUL.
                                                      		//2. prepara y ordena strCHAR_TO_CONVERT_AND_CONVERSION en
                                                      		//      arrcharTO_CONVERT y arrcharCONVERSION.

    {
                                                      		//1. Ordena arrcharUSEFUL.
        arrcharUSEFUL = strCHAR_USEFUL.toCharArray();
        Arrays.sort(arrcharUSEFUL);

                                                      		//Verifica que no haya caracteres duplicados.
        for (int intI = 1; intI < arrcharUSEFUL.length; intI = intI + 1)
        {
            if (
                                                      		//Esta duplicado.
                arrcharUSEFUL[intI] == arrcharUSEFUL[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrcharUSEFUL, "arrcharUSEFUL") + ", " +
                    Tes2.strTo(arrcharUSEFUL[intI], "arrcharUSEFUL[" + intI + "]") +
                    " duplicated character");
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

                                                      		//2. Separa y ordena strCHAR_TO_CONVERT_AND_CONVERSION.
                                                      		//Convierte a 2 arreglos de caracteres y los ordena por el
                                                      		//      primero.
        arrcharTO_CONVERT = new char[strCHAR_TO_CONVERT_AND_CONVERSION.length() / 2];
        arrcharCONVERSION = new char[arrcharTO_CONVERT.length];
        for (int intI = 0; intI < arrcharTO_CONVERT.length; intI = intI + 1)
        {
            arrcharTO_CONVERT[intI] = strCHAR_TO_CONVERT_AND_CONVERSION.charAt(intI * 2);
            arrcharCONVERSION[intI] = strCHAR_TO_CONVERT_AND_CONVERSION.charAt(intI * 2 + 1);
        }
        Tools.sort(arrcharTO_CONVERT, arrcharCONVERSION);
                                                      		//Verifica que no haya caracteres duplicados.
        for (int intI = 1; intI < arrcharTO_CONVERT.length; intI = intI + 1)
        {
            if (
                                                      		//Esta duplicado.
                arrcharTO_CONVERT[intI] == arrcharTO_CONVERT[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrcharTO_CONVERT, "arrcharTO_CONVERT") + ", " +
                    Tes2.strTo(arrcharTO_CONVERT[intI], "arrcharTO_CONVERT[" + intI + "]") +
                    " duplicated character");
        }

                                                      		//Verifica que arrcharTO_CONVERT NO este en
                                                      		//      arrcharUSEFUL.
        for (int intI = 0; intI < arrcharTO_CONVERT.length; intI = intI + 1)
        {
            if (
                Arrays.binarySearch(arrcharUSEFUL, arrcharTO_CONVERT[intI]) >= 0
                )
                Tools.subAbort(Tes2.strTo(arrcharTO_CONVERT[intI], "arrcharTO_CONVERT[" + intI + "]") +
                    " exists in " + Tes2.strTo(arrcharUSEFUL, "arrcharUSEFUL"));
        }

                                                      		//Verifica que arrcharCONVERSION SI este en arrcharUSEFUL.
        for (int intI = 0; intI < arrcharCONVERSION.length; intI = intI + 1)
        {
            if (
                Arrays.binarySearch(arrcharUSEFUL, arrcharCONVERSION[intI]) < 0
                )
                Tools.subAbort(Tes2.strTo(arrcharCONVERSION[intI], "arrcharCONVERSION[" + intI + "]") +
                    " do not exist in " + Tes2.strTo(arrcharUSEFUL, "arrcharUSEFUL"));
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

                                                      		//Si es válido y se tiene acceso, contiene el Full Path.
                                                      		//En su defecto, contiene el Path tal se proporciono y no
                                                      		//      se deben tomar sus propiedades (aborta).
    private /*MUTABLE*/ String strFullPath_Z;
    public String strFullPath() { return this.strFullPath_Z; }

                                                      		//Determina si un Nombre, Path o Full Path (File or
                                                      		//      Directory) es válido en su estructura de caracteres.
                                                      		//Se utiliza Path.GetFullPath(strFileName_I) para
                                                      		//      verificarlo.
                                                      		//Si recibe un SecurityException no lo rechaza.
    private /*MUTABLE*/ boolean boolIsValid_Z;
    public boolean boolIsValid() { return this.boolIsValid_Z; }

                                                      		//Determina si se tiene acceso a un Nombre, Path o Full Path
                                                      		//      (File or Directory).
                                                      		//Se utiliza Path.GetFullPath(strFileName_I) para
                                                      		//      verificarlo.
                                                      		//Si recibe un SecurityException indica que no tiene acceso.
                                                      		//El path debe ser válido.
    private /*MUTABLE*/ boolean boolHaveAccessTo_Z;
    public boolean boolHaveAccessTo() { return this.boolHaveAccessTo_Z; }

                                                      		//LOCAL o NETWORK.
    private SyspathwhereEnum syspathwhere_Z;
    public SyspathwhereEnum syspathwhere() { return this.syspathwhere_Z; }

                                                      		//DO_NOT_EXIST_ON_DISK, FILE o DIRECTORY.
    private SyspathtypeEnum syspathtype_Z;
    public SyspathtypeEnum syspathtype() { return this.syspathtype_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

                                                      		//Es un Full Path local (esta en este equipo).
    private Boolean boolIsLocal_Z;
    public boolean boolIsLocal()
    {
        if (
            this.boolIsLocal_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.boolIsLocal_Z = (
                this.syspathwhere() == SyspathwhereEnum.LOCAL
                );

            this.subSetIsResetOff();
        }

        return (boolean)this.boolIsLocal_Z;
    }

                                                      		//Es un Full Path que esta en red.
    private Boolean boolIsNetwork_Z;
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

                                                      		//Corresponde a un File o Directory que existe.
    private Boolean boolExists_Z;
    public boolean boolExists()
    {
        if (
            this.boolExists_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            this.boolExists_Z =
                (
                                                 		    //Si existe, es un File o Directory
                this.syspathtype() != SyspathtypeEnum.DO_NOT_EXIST_ON_DISK
                );

            this.subSetIsResetOff();
        }

        return (boolean)this.boolExists_Z;
    }

                                                      		//Es un File.
    private Boolean boolIsFile_Z;
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

                                                      		//Es un Directory.
    private Boolean boolIsDirectory_Z;
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

                                                      		//SOLO SI EXISTE, PUEDE SER FILE O DIRECTORY.
                                                      		//Si se solicitan cuando no existe va a abortar.

                                                      		//Nombre del archivo (sin el directorio).
    private String strName_Z;
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
                                                      		//syspath de la raíz.
    private SyspathPath syspathRoot_Z;
    public SyspathPath syspathRoot()
    {
        if (
            this.syspathRoot_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                  		//Extraigo raíz.
            String strRoot = Paths.get(this.strFullPath()).getRoot().toString();

            this.syspathRoot_Z = new SyspathPath(strRoot);

            this.subSetIsResetOff();
        }

        return this.syspathRoot_Z;
    }

                                                      		//SOLO SI ES UN FILE.
                                                      		//Si se solicitan cuando es un directorio va a abortar.

                                                      		//File extension.
    private String strFileExtension_Z;
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
                Tools.subAbort(Tes2.strTo(this, "this") + " is a Directory, can not have File Extension");

            int intDot = Paths.get(this.strFullPath()).getFileName().toString().lastIndexOf('.');
            this.strFileExtension_Z = Paths.get(this.strFullPath()).getFileName().toString().substring(intDot);

            this.subSetIsResetOff();
        }

        return this.strFileExtension_Z;
    }

                                                      		//Nombre sin el file extensión.
    private String strFileNameWithoutExtension_Z;
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
                Tools.subAbort(Tes2.strTo(this, "this") + " is a Directory, can not have File Extension");

            int intDot = Paths.get(this.strFullPath()).getFileName().toString().lastIndexOf('.');
            this.strFileNameWithoutExtension_Z =
                    Paths.get(this.strFullPath()).getFileName().toString().substring(0, intDot);

            this.subSetIsResetOff();
        }

        return this.strFileNameWithoutExtension_Z;
    }
                                                            //SOLO SI ES UN FILE.
                                                            //Si se solicitan cuando no existe o es un directory va a
                                                            //      abortar.

                                                      		//Path del directorio del archivo que tenemos.
    private SyspathPath syspathDirectory_Z;
    public SyspathPath syspathDirectory()
    {
        if (
            this.syspathDirectory_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

            if (
                                                  		    //No es file.
                !this.boolIsFile()
                )
                Tools.subAbort(Tes2.strTo(this, "this") + " is not a file");

                                                  		    //Extraigo directorio.
            String strDirectory = Paths.get(this.strFullPath()).getParent().toString();

            this.syspathDirectory_Z = new SyspathPath(strDirectory);

            this.subSetIsResetOff();
        }

        return this.syspathDirectory_Z;
    }

                                                      		//SOLO SI ES UN DIRECTORY.
                                                      		//Si se solicitan cuando no existe o es un file va a
                                                      		//      abortar.

                                                      		//syspath del directorio padre.
    private SyspathPath syspathParent_Z;
    public SyspathPath syspathParent()
    {
        if (
            this.syspathParent_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();
            if (
                                                  		//No es directorio.
                !this.boolIsDirectory()
                )
                Tools.subAbort(Tes2.strTo(this, "this") + " is not a directory");

            if (
                                                  		//El directorio que ya tenemos es la raíz, no puede buscar
                                                  		//      el padre de esto.
                this.strFullPath_Z == this.syspathRoot().strFullPath()
                )
                Tools.subAbort(Tes2.strTo(this, "this") + " it is a root directory, it do not have a parent");

                                                  		//Extraigo directorio.
            /*String strDirectory = Path.GetDirectoryName(this.strFullPath_Z);*/
            String strDirectory = Paths.get(this.strFullPath()).getParent().toString();

            this.syspathParent_Z = new SyspathPath(strDirectory);

            this.subSetIsResetOff();
        }

        return this.syspathParent_Z;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void subReset()
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

        this.syspathParent_Z = null;

        super.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        String strObjId = Tes2.strGetObjId(this);

        return strObjId + "[" + super.strTo(TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.strName(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.boolIsValid(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.boolHaveAccessTo(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.syspathwhere(), TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.syspathtype(), TestoptionEnum.SHORT) + "]";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    @Override
    public String strTo()
{
        String strObjId = Tes2.strGetObjId(this);

        return strObjId + "{" + Tes2.strTo(this.strFullPath(), "strFullPath") + ", " +
            Tes2.strTo(this.boolIsValid(), "boolIsValid") + ", " +
            Tes2.strTo(this.boolHaveAccessTo(), "boolHaveAccessTo") + ", " +
            Tes2.strTo(this.syspathwhere(), "syspathwhere") + ", " +
            Tes2.strTo(this.syspathtype(), "syspathtype") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    public SyspathPath(                                 	//Crea un objeto Path.
                                                      		//this.*[O], asigna valores.

                                                      		//Nombre, Path relativo o Full Path.
        String strPath_I
        )
    {
        super(false);

        if (
            strPath_I == null
            )
            Tools.subAbort(Tes2.strTo(strPath_I, "strPath_I") + " should have a value");

                                                      		//Por lo pronto asigna los valores que tendrá si no es
                                                      		//      válido o no se tiene acceso.
        this.strFullPath_Z = strPath_I;

                                                            //Asigna las otras 4 variables.
        this.subRefresh();

        this.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public void subRefresh(                             	//Determina el Path o Full Path (File or Directory) es
                                                      		//      válido en su estructura de caracteres y si se tiene
                                                      		//      acceso.
                                                      		//Se utiliza Path.GetFullPath().
                                                      		//this[M], toma información y también refresca todo.
        )
    {
                                                      		//Asume que todo será correcto.
        boolean boolIsValid = true;
        boolean boolHaveAccessTo = true;
        SyspathwhereEnum syspathwhere = SyspathwhereEnum.Z_ERROR_NOT_DEFINED;
        SyspathtypeEnum syspathtype = SyspathtypeEnum.DO_NOT_EXIST_ON_DISK;

        try
        {
                                                      		//Trata de obtener el full path.
            this.strFullPath_Z = Paths.get(this.strFullPath()).toAbsolutePath().toString();
        }
        catch (AccessControlException sysexcepError)
        {
                                                      		//El nombre esta bien formado, pero no se tiene acceso.
            this.boolIsValid_Z = true;
            this.boolHaveAccessTo_Z = false;

                                                      		//ESTE MENSAJE SE DEBE ELIMINAR EN EL FUTURO.
            JOptionPane.showMessageDialog(null, Tes2.strTo(this.strFullPath(), "this.strFullPath") + ", " +
                Tes2.strTo(sysexcepError, "sysexcepError") + " do not have access permition");
        }
        catch (Exception sysexcepError)
        {
                                                      		//El nombre no esta bien formado.
            boolIsValid = false;
            boolHaveAccessTo = false;

                                                      		//ESTE MENSAJE SE DEBE ELIMINAR EN EL FUTURO.
            JOptionPane.showMessageDialog(null, Tes2.strTo(this.strFullPath(), "strFullPath") + ", " +
                Tes2.strTo(sysexcepError, "sysexcepError") + " is not a valid path");
        }
        /*END-NONSTANDAR*/

                                                      		//Por estándar solo se aceptan como validos los caracteres
                                                      		//      USEFUL y TO_CONVERT
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            (intI >= this.strFullPath().length()) ||
            (Arrays.binarySearch(SyspathPath.arrcharUSEFUL, this.strFullPath().charAt(intI)) < 0) &&
                (Arrays.binarySearch(SyspathPath.arrcharTO_CONVERT, this.strFullPath().charAt(intI)) < 0)
            ))
        {
            intI = intI + 1;
        }

                                                      		//Si salió antes de terminar es que encontró char invalid
        if (
            intI < this.strFullPath().length()
            )
        {
                                                      		//Se marca.
            boolIsValid = false;
            boolHaveAccessTo = false;
        }

                                                      		//Adicionalmente, si inicia con ftp:, http:, https: o file:
                                                      		//      será inválido (y sin acceso).
                                                      		//Aparentemente el Path los acepta pero FileInfo y
                                                      		//      DirectoryInfo NO.

                                                      		//Corta y pasa a minúsculas.
        String strInicioFullPath;
        final int intCORTA = 10;
        if (
            this.strFullPath().length() <= intCORTA
            )
        {
            strInicioFullPath = this.strFullPath_Z;
        }
        else
        {
            strInicioFullPath  = this.strFullPath().substring(0, 10);
        }
        strInicioFullPath = strInicioFullPath.toLowerCase();

        if (
            strInicioFullPath.startsWith("ftp:") || strInicioFullPath.startsWith("http:") ||
             strInicioFullPath.startsWith("https:") || strInicioFullPath.startsWith("file:")
            )
        {
                                                      		//Se marca.
            boolIsValid = false;
            boolHaveAccessTo = false;
        }

                                                      		//Si todo sigue correcto es que no se fue por catch y que
                                                      		//      ya tiene un Full Path.

        if (
            boolIsValid && boolHaveAccessTo
            )
        {
                                                      		//Determina el where

            if (
                                                            //TODO: No se usa Path.VolumeSeparatorChar. Original está
                                                            //      comentado.
                                                      		//Es local (tiene x:...).
                /*this.strFullPath().charAt(1) == Path.VolumeSeparatorChar*/

                                                            //Usually on Windows local paths starts with "C:"
                this.strFullPath().charAt(1) == ':' ||
                                                            //Usually in Unix (Mac OX for example) local paths starts
                                                            //      with "//"
                this.strFullPath().charAt(0) == '/'
                )
            {
                syspathwhere = SyspathwhereEnum.LOCAL;
            }
            else if (
                                                      		//Es red (tiene \\...).
                this.strFullPath().startsWith(strINICIO_FULL_PATH_RED)
                )
            {
                syspathwhere = SyspathwhereEnum.NETWORK;
            }
            else
            {
                if (
                    true
                    )
                    Tools.subAbort(Tes2.strTo(this, "this") + " path start not valid");
            }
            /*END-CASE*/

                                                      		//Determina tipo.

            if (
                                                      		//Equivale a File.Exists() de C# lo reconoce como archivo.
                new File(this.strFullPath()).exists() && new File(this.strFullPath()).isFile()
                )
            {
                syspathtype = SyspathtypeEnum.FILE;
            }

            if (
                                                      		//Equivale a Directory.Exists() lo reconoce como directorio.
                new File(this.strFullPath()).exists() && new File(this.strFullPath()).isDirectory()
                )
            {
                if (
                    syspathtype == SyspathtypeEnum.FILE
                    )
                    Tools.subAbort(Tes2.strTo(this, "this") +
                        " it can not be a file ana a directory at the same time");

                syspathtype = SyspathtypeEnum.DIRECTORY;
            }
        }

        this.boolIsValid_Z = boolIsValid;
        this.boolHaveAccessTo_Z = boolHaveAccessTo;
        this.syspathwhere_Z = syspathwhere;
        this.syspathtype_Z = syspathtype;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public boolean boolIsCharSetTowa(                      	//Verifica si esta de acuerdo con el conjunto de caracteres
                                                      		//      estándar Towa.
                                                      		//this[I], toma información.
        )
    {
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
            (intI >= this.strFullPath().length()) ||
            (Arrays.binarySearch(arrcharUSEFUL, this.strFullPath().charAt(intI)) < 0)
            ))
        {
            intI = intI + 1;
        }

                                                      		//Si llego al final es que todo esta correcto
        boolean boolIsCharSetTowa = (
            intI >= this.strFullPath().length()
            );

                                                      		//TEMPORALMENTE, SI ES INVALIDO DESPLIEGA LA INFORMACIÓN,
                                                      		//      ESTO TIENE COMO FIN AYUDARNOS A ENTENDER LA
                                                      		//      PROBLEMÁTICA.
        if (
            !boolIsCharSetTowa
            )
        {
            JOptionPane.showMessageDialog(null, Tes2.strTo(this, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.strFullPath().charAt(intI), "this.strFullPath[" + intI + "]") +
                 " is invalid, THIS IS A TEMPORARY MESSAGE");
        }

        return boolIsCharSetTowa;
    }

    //------------------------------------------------------------------------------------------------------------------
    public SyspathPath syspathConvertChars(             	//Convierte los caracteres que tienen conversión.
                                                      		//this[I], toma información.

                                                      		//syspath, ya convertido.
        )
    {
        char[] arrcharFullPath = this.strFullPath().toCharArray();

        for (int intI = 0; intI < arrcharFullPath.length; intI = intI + 1)
        {
                                                      		//Busca si esta en A CONVERTIR.
            int intChar = Arrays.binarySearch(arrcharTO_CONVERT, arrcharFullPath[intI]);

            if (
                                                      		//Si lo encontró
                intChar >= 0
                )
            {
                                                      		//Lo cambia.
                arrcharFullPath[intI] = arrcharCONVERSION[intChar];
            }
        }

                                                      		//Convierte a String.
        String strFullPath = new String(arrcharFullPath);

                                                      		//Regresa un nuevo Path.
        return new SyspathPath(strFullPath);
    }

    //------------------------------------------------------------------------------------------------------------------
    public SyspathPath syspathAddName(                  	//Le añade un nombre a un  syspath de un directorio.
                                                      		//this[I], debe ser un directorio, toma información.

                                                      		//syspath, ya combinado.

                                                      		//Nombre a combinar con el path del this.
        String strNameToCombine_I
        )
    {
        if (
            !this.boolIsDirectory()
            )
            Tools.subAbort(Tes2.strTo(this, "this") + " do not exist as directory");

        String strFullPathMasName;
        File fileFullPath = new File(this.strFullPath());
        strFullPathMasName = new File(fileFullPath, strNameToCombine_I).getAbsolutePath();

                                                      		//Regresa la combinación.
        return new SyspathPath(strFullPathMasName);
    }

    //------------------------------------------------------------------------------------------------------------------
}
/*END-TASK*/