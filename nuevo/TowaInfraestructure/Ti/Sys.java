package Ti;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Scanner;

public final class Sys
{
    //------------------------------------------------------------------------------------------------------------------
    static                                        			//Prepara las constantes para poder utilizarlas.
    //                                                      //CADA VEZ QUE SE AÑADAN CONSTANTES QUE REQUIERAN SER
    //                                                      //      INICIALIZADAS, SE AÑADE LA LLAMADA A OTRO MÉTODO.

    {
    }
    private Sys() {}
    //                                                      //No permite que se cree instancia de la clase.

    //==================================================================================================================
    /*TASK Sys.Dir+File Directories and Files*/
    //------------------------------------------------------------------------------------------------------------------
    public static File sysdirNew(
        //                                                  //Crea un File (es directorio), el directorio puede
        //                                                  //     no existir.

        //                                                  //sysdir, File creado.

        //                                                  //Path (completo y válido) del directorio a crear.
        SyspathPath syspathDirectory_I
        )
    {
        //                                                  //Creo el File.
        File sysdirNew;
        try
        {
            sysdirNew = new File(syspathDirectory_I.strFullPath());
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathDirectory_I, "syspathDirectory_I") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") +
                " error in new File(syspathDirectory_I.strFullPath)");

            sysdirNew = null;
        }

        //                                                  //Regresa el File creado.
        return sysdirNew;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static File sysfileNew(
        //                                                  //Crea un File, el archivo puede no existir.

        //                                                  //sysfile, File creado.

        //                                                  //Path (completo y válido) del archivo a crear.
        SyspathPath syspathFile_I
        )
    {
        //                                                  //Creo el File.
        File sysfileNew;
        try
        {
            sysfileNew = new File(syspathFile_I.strFullPath());
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathFile_I, "syspathFile_I") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") +
                " error in new File(syspathFile_I.strFullPath)");

            sysfileNew = null;
        }

        //                                                  //Regresa el File creado.
        return sysfileNew;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static File sysdirGetCurrentDirectory(
        //                                                  //Localiza el directorio sobre el que se encuentra
        //                                                  //      posicionada la aplicación.

        //                                                  //sysdir, Current Directory.
        )
    {
        //                                                  //Busco el current directory.
        String strCurrentDirectory;
        try
        {
            strCurrentDirectory = System.getProperty("user.dir");
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(sysexcepError, "sysexcepError") +
                " error in System.getProperty(\"user.dir\")");

            strCurrentDirectory = null;
        }

        //                                                  //Regresa un File.
        SyspathPath syspathCurrentDirectory = new SyspathPath(strCurrentDirectory);
        return Sys.sysdirNew(syspathCurrentDirectory);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subSetCurrentDirectory(
        //                                                  //Establece el Current Durectory.

        //                                                  //DirectroyInfo sobre el que se desea posicionar.
        File sysdirToSet_I
        )
    {
        //                                                  //Crea el syspath del directorio, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathToSet = Sys.syspathGet(sysdirToSet_I);

        if (
            !syspathToSet.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathToSet, "syspathToSet") + " do not exist as directory");

        //                                                  //Establece el Current Directory a partir de un path.
        try
        {
            System.setProperty("user.dir", syspathToSet.strFullPath());
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToSet, "syspathToSet") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") +
                " error in System.setProperty(\"user.dir\", syspathToSet.strFullPath())");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public static SyspathPath syspathGet(
        //                                                  //Extrae el syspath correspondiente al directorio.

        //                                                  //syspath, similar al que se uso para crear el sysdir con su
        //                                                  //      estado actualizado.

       //                                                   //DirectoryInfo del cual se quiere información.
       File sysdirToGetPath_I
        )
    {
        //                                                  //Extrae el directory full path.
        String strFullPath;
        try
        {
            strFullPath = sysdirToGetPath_I.getAbsolutePath();
        }
        catch (Exception sysexcepError)
        {
            //                                              //Nótese que no puedo desplegar el objeto syspath como lo
            //                                              //      estoy haciento en la mayoría de los diagnósticos.
            Tes3.subAbort(Tes3.strTo(sysdirToGetPath_I, "sysdirToGetPath_I") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysdirToGetPath_I.FullName");

            strFullPath = null;
        }

        //                                                  //Regresa el path con su estado actualizado.
        return new SyspathPath(strFullPath);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static File sysdirGetParent(
        //                                                  //Busca el directorio padre inmediato superior.

        //                                                  //sysdir, correspondiente al directorio padre, o null si
        //                                                  //      estaba en la raíz.

        //                                                  //File del cual se quiere información.
        File sysdirToGetParent_I
        )
    {
        //                                                  //Crea el syspath del directorio, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathDirectoryToGetParent = Sys.syspathGet(sysdirToGetParent_I);

        //                                                  //Puede no existir, pero si existe debe ser directorio
        if (
            //                                              //Es un FILE.
            syspathDirectoryToGetParent.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToGetParent, "syspathDirectoryToGetParent") +
                " it is not a directory, this is a file");

        //                                                  //Extrae el padre.
        File sysdirGetParent;
        try
        {
            sysdirGetParent = new File(Paths.get(sysdirToGetParent_I.getParent()).getFileName().toString());
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToGetParent, "syspathDirectoryToGetParent") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysdirToGetParent_I.getParentFile()");

            sysdirGetParent = null;
        }

        //                                                  //Regresa el padre.
        return sysdirGetParent;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static File sysdirGetDirectory(
        //                                                  //Busca el directorio en el que esta el archivo.

        //                                                  //sysdir, File del archivo.

        //                                                  //File del cual se quiere información.
        File sysfileToGetDirectory_I
        )
    {
        //                                                  //Crea el syspath del archivo, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathDirectoryToGetDirectory = Sys.syspathGet(sysfileToGetDirectory_I);

        //                                                  //Puede no existir, pero si existe debe ser archivo
        if (
            //                                              //Es un directorio.
            syspathDirectoryToGetDirectory.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToGetDirectory, "syspathDirectoryToGetDirectory") +
                " it is not a file, this is a directory");

        //                                                  //Extrae el directorio del archivo.
        File sysdirGetDirectory;
        try
        {
            sysdirGetDirectory = sysfileToGetDirectory_I.getParentFile();
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToGetDirectory, "syspathDirectoryToGetDirectory") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileToGetDirectory_I.Directory");

            sysdirGetDirectory = null;
        }

        //                                                  //Regresa el directorio del archivo.
        return sysdirGetDirectory;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static long longGetFileLength(
        //                                                  //Extrae DEL DISCO la longitud del archivo.

        //                                                  //long, longitud del archivo (la toma del DISCO).

        //                                                  //File del cual se quiere información.
        File sysfileToGetFileLength_I
        )
    {
        //                                                  //Crea el syspath del archivo, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathFileToGetFileLength = Sys.syspathGet(sysfileToGetFileLength_I);

        if (
            !syspathFileToGetFileLength.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathFileToGetFileLength, "syspathFileToGetFileLength") +
                " it is not a file");

        //                                                  //Extrae la longitud del archivo.
        long longGetFileLength;
        try
        {
            //                                              //Toma la longitud DEL DISCO.
            longGetFileLength = sysfileToGetFileLength_I.length();
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathFileToGetFileLength, "syspathFileToGetFileLength") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileToGetFileLength_I.Length");

            longGetFileLength = -1;
        }

        //                                                  //Regresa longitud.
        return longGetFileLength;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subSetReadOnly(
        //                                                  //Set ReadOnly del archivo a true.

        //                                                  //File del archivo que se quiere modificar.
        Oobject<File> sysfileToUpdate_M
        )
    {
        //                                                   //Crea el syspath del archivo, esto solo para confirmar
        //                                                   //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                   //      caso de problemas.
        SyspathPath syspathToUpdate = Sys.syspathGet(sysfileToUpdate_M.v);

        if (
            !syspathToUpdate.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathToUpdate, "syspathToUpdate") + " it is not a file");

        //                                                  //Modifica propiedad.
        try
        {
            sysfileToUpdate_M.v.setWritable(false);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToUpdate, "syspathToUpdate") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileToUpdate_M.IsReadOnly = true");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subResetReadOnly(
        //                                                  //Permite que se puede escribir en el archivo.

        //                                                  //File del archivo que se quiere modificar.
        Oobject<File> sysfileToUpdate_M
        )
    {
        //                                                  //Crea el syspath del archivo, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathToUpdate = Sys.syspathGet(sysfileToUpdate_M.v);

        if (
            !syspathToUpdate.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathToUpdate, "syspathToUpdate") + " it is not a file");

        //                                                  //Modifica propiedad.
        try
        {
            sysfileToUpdate_M.v.setWritable(true);

        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToUpdate, "syspathToUpdate") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileToUpdate_M.IsReadOnly = false");
        }
    }

    //--------------------------------------------------------------------------------------------------------------
    //                                                      //TODO: Encontrar equialwnte a Refresh en Java.
    public static void subRefresh(
        //                                                  //Refresca la información de un File.

        //                                                  //File que se desea refrescar.
        Oobject <File> sysfileToRefresh_M
        )
    {
        //                                                  //Crea el syspath del archivo, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathToRefresh = Sys.syspathGet(sysfileToRefresh_M.v);

        if (
            !syspathToRefresh.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathToRefresh, "syspathToRefresh") + " it is not a file");

        //                                                  //Hace el refresh.
        try
        {
            //                                              //TODO: Equivalent to C# refresh (it may not be necessary).
            //                                              //            sysfileToRefresh_M.Refresh();
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToRefresh, "syspathToRefresh") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileToRefresh_I.Refresh()");
        }
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subCreateDirectoryOnDisk(
        //                                                  //A partir de un DirectoryInfo CREA el directorio en disco.

        //                                                  //DirectroyInfo con el cual se desea crear directorio en
        //                                                  //      disco.
        File sysdirToCreateOnDisk_M
        )
    {
        //                                                  //Necesito un syspath para verificar la existencia ya sea
        //                                                  //      como directorio o archivo.
        SyspathPath syspathToCreateOnDisk = Sys.syspathGet(sysdirToCreateOnDisk_M);

        if (
            //                                              //Ya existe como directorio o como archivo.
            syspathToCreateOnDisk.boolExists()
            )
            Tes3.subAbort(Tes3.strTo(syspathToCreateOnDisk, "syspathToCreateOnDisk") +
                " can not create a directory, already exist as a directory o as a file");

        //                                                  //Crea el directorio en disco.
        try
        {
            sysdirToCreateOnDisk_M.mkdir();
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToCreateOnDisk, "syspathToCreateOnDisk") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysdirToCreateOnDisk_M.mkdir()");
        }
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subRename(
        //                                                  //Modifica el nombre de un directorio (último
        //                                                  //      subdirectorio), usará MoveTo, pero para que solo
        //                                                  //      cambie el nombre del último subdirectorio.

        //                                                  //DirectoryInfo del directorio que se quiere renombrar.
        Oobject<File> osysdirToRename_M,
                                                            //Nuevo nombre del subdirectorio (sin el path).
        String strNewSubdirectoryName_I
        )
    {
        //                                                  //Crea el syspath del directorio, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathToRename = Sys.syspathGet(osysdirToRename_M.v);

        if (
            !syspathToRename.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathToRename, "syspathToRename") + " do not exist as directory");

        if (
            //                                              //El nuevo nombre es el mismo.
            strNewSubdirectoryName_I.equals(osysdirToRename_M.v.getName())
            )
            Tes3.subAbort(Tes3.strTo(syspathToRename, "syspathToRename") + ", " +
                Tes3.strTo(strNewSubdirectoryName_I, "strNewSubdirectoryName_I") +
                " can not rename");

        //                                                  //Crea el nuevo path para confirmar que su forma es válida.
        SyspathPath syspathRanamed = syspathToRename.syspathDirectory().syspathAddName(strNewSubdirectoryName_I);

        if (
            //                                              //Ya existe un archivo o directorio con el mismo nombre.
            syspathRanamed.boolExists()
            )
            Tes3.subAbort(Tes3.strTo(syspathRanamed, "syspathRanamed") +
                " can not rename, already exists as directory o file");

        //                                                  //Renombra el subdirectorio usando el MoveTo.
        //                                                  //Nótese que ya se hicieron muchas verificaciones para hacer
        //                                                  //      esto en forma segura.
        try
        {
            //                                              //Nótese que se mueve SOBRE el directorio renombrado (NO
            //                                              //      queda abajo como un subdirectorio).
            osysdirToRename_M.v.renameTo(new File(syspathRanamed.strFullPath()));
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToRename, "syspathToRename") + ", " +
                Tes3.strTo(syspathRanamed, "syspathRanamed") + ", " + Tes3.strTo(sysexcepError, "sysexcepError") +
                " error in sysdirToRename_M.MoveTo(syspathRanamed.strFullPath)");
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    //TODO: Todo lo siguiente está comentado porque la mayoría tiene parámetros _O o _M y en Java no se puede hacer
    //TODO:     eso. Falta hacer una clase wrapper de Files para poder hacer ese _O o _M
    //------------------------------------------------------------------------------------------------------------------
    public static void subCopyDirectoryAllBranch(
        //                                                  //Copia el directorio (con todo su contenido) a otro
        //                                                  //      directorio, se copia con el mismo nombre.
        //                                                  //No debe existir el nombre en el nuevo padre.

        //                                                  //DirectoryInfo del directorio que se quiere copiar.
        File sysdirToCopy_I,
        //                                                  //DirectoryInfo del directorio al cual se desea copiar, este
        //                                                  //      será el nuevo padre.
        Oobject <File> sysdirNewParent_M,
        //                                                  //Regresa el DirectoryInfo del directorio nuevo (lo que
        //                                                  //      quedo ya copiado).
        Oobject <File> sysdirCopied_O
        )
    {
        //                                                  //Verifica que ambos directorios existan.
        SyspathPath syspathToCopy = Sys.syspathGet(sysdirToCopy_I);
        if (
            //                                              //No es un directorio.
            !syspathToCopy.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathToCopy, "syspathToCopy") + " do not exist as directory");
        SyspathPath syspathNewParent = Sys.syspathGet(sysdirNewParent_M.v);
        if (
            //                                              //No es un directorio.
            !syspathNewParent.boolIsDirectory()
                )
            Tes3.subAbort(Tes3.strTo(syspathNewParent, "syspathNewParent") + " do not exist as directory");

        //                                                  //Verifica que el nuevo directorio no exista.
        SyspathPath syspathCopied = syspathNewParent.syspathAddName(syspathToCopy.strName());
        if (
            //                                              //El nuevo syspath, ya existe.
            syspathCopied.boolExists()
                )
            Tes3.subAbort(Tes3.strTo(syspathCopied, "syspathCopied") +
                    " can not copy, already exists a directory or file with same name");

        //                                                  //Crea el nuevo sysdir y el directorio en el disco.
        sysdirCopied_O.v = Sys.sysdirNew(syspathCopied);
        Sys.subCreateDirectoryOnDisk(sysdirCopied_O.v);

        //                                                  //Copia todos los archivos que se encuentran en el nivel
        //                                                  //      inmediato.
        File[] arrsysfileToCopy = Sys.arrsysfileGetFiles(sysdirCopied_O.v);
        for (File sysfileF : arrsysfileToCopy)
        {
            //                                              //Copia cada uno, obviamente no habra remplazos.
            Oobject<File> sysfile = new Oobject<File>();
            Sys.subCopyFileWrite(sysfileF, sysdirCopied_O, sysfile);
        }

        //                                                  //Copia todos los subdirectorios que se encuentran en el
        //                                                  //      nivel inmediato.
        File[] arrsysdirToCopy = Sys.arrsysdirGetDirectories(sysdirCopied_O.v);
        for (File sysdirD : arrsysdirToCopy)
        {
            //                                              //Copia cada uno de los subdirectorios (llamada recursiva).
            Oobject <File> sysdir = new Oobject<File>();
            Sys.subCopyDirectoryAllBranch(sysdirD, sysdirCopied_O, sysdir);
        }
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subCopyFileWrite(
        //                                                  //Copia un archivo a otro directorio donde no existe.

        //                                                 //File del archivo que se quiere copiar.
        File sysfileToCopy_I,
        //                                                 //DirectoryInfo del directorio al cual se desea copiar el
        //                                                 //      archivo, este será el nuevo padre.
        Oobject<File> sysdirNewParent_M,
        //                                                 //File del archivo donde se va a regresar.
        Oobject<File> sysfileWrited_O
        )
    {
        //                                                  //Verifica que el archivo y el directorio existan.
        SyspathPath syspathToCopy = Sys.syspathGet(sysfileToCopy_I);
        if (
            //                                              //No es un archivo.
            !syspathToCopy.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathToCopy, "syspathToCopy") + " file do not exist");

        SyspathPath syspathNewParent = Sys.syspathGet(sysdirNewParent_M.v);
        if (
            //                                              //No es un directorio.
            !syspathNewParent.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathNewParent, "syspathNewParent") + " is not a directory");

        //                                                  //Verifica que el archivo receptor no exista.
        SyspathPath syspathFileToWrite = syspathNewParent.syspathAddName(syspathToCopy.strName());
        if (
            //                                              //El nuevo syspatth existe como DIRECTORIO o FILE.
            syspathFileToWrite.boolExists()
            )
            Tes3.subAbort(Tes3.strTo(syspathFileToWrite, "syspathFileToWrite") +
                    " already exists as directory or file");

        try
        {
            Files.copy(Paths.get(sysfileToCopy_I.getPath()), Paths.get(syspathFileToWrite.strFullPath()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToCopy, "syspathToCopy") + ", " +
                    Tes3.strTo(syspathFileToWrite, "syspathFileToWrite") + ", " +
                    Tes3.strTo(sysexcepError, "sysexcepError") +
                    " error in sysfileToCopy_I.CopyTo(syspathFileToRewrite.strFullPath, false)");
        }

        //                                                  //Regresa el nuevo File.
        sysfileWrited_O.v = Sys.sysfileNew(syspathFileToWrite);

        if (!(
            (sysfileWrited_O.v.length() == sysfileToCopy_I.length()) &&
            (sysfileWrited_O.v.canWrite() == sysfileToCopy_I.canWrite())
            ))
            Tes3.subAbort(Tes3.strTo(syspathToCopy, "syspathToCopy") + ", " +
                Tes3.strTo(syspathFileToWrite, "syspathFileToWrite") + ", " +
                Tes3.strTo(sysfileWrited_O.v, "sysfileWrited_O") + ", " +
                Tes3.strTo(sysfileToCopy_I, "sysfileToCopy_I") +
                " VERIFY VERIFY, after write the file seams to be different");
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subCopyFileRewrite(
        //                                                  //Copia un archivo a otro directorio donde ya existe y se
        //                                                  //      debe reescribir.
        //                                                  //No se permite reescribir el archivo si el receptor es
        //                                                  //      ReadOnly.

        //                                                  //File del archivo que se quiere copiar.
        File sysfileToCopy_I,
        //                                                  //DirectoryInfo del directorio al cual se desea copiar el
        //                                                  //      archivo, este será el nuevo padre.
        Oobject<File> sysdirNewParent_M,
        //                                                  //File del archivo donde se va a regresar.
        Oobject<File> sysfileRewrited_O
        )
    {
        //                                                  //Verifica que el archivo y el directorio existan.
        SyspathPath syspathToCopy = Sys.syspathGet(sysfileToCopy_I);
        if (
            //                                              //No es un archivo.
            !syspathToCopy.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathToCopy, "syspathToCopy") + " file do not exist");

        SyspathPath syspathNewParent = Sys.syspathGet(sysdirNewParent_M.v);
        if (
            //                                              //No es un directorio.
            !syspathNewParent.boolIsDirectory()
                )
            Tes3.subAbort(Tes3.strTo(syspathNewParent, "syspathNewParent") + " directory do not exist");

        //                                                  //Verifica que el archivo receptor exista y se pueda
        //                                                  //      reescribir.
        SyspathPath syspathFileToRewrite = syspathNewParent.syspathAddName(syspathToCopy.strName());
        if (
            //                                              //El nuevo syspatth no existe como FILE.
            !syspathFileToRewrite.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathFileToRewrite, "syspathFileToRewrite") +
                " does not exist, you can try subCopyFileWrite");

        Oobject<File> sysfileToRewrite = new Oobject<File>();
        sysfileToRewrite.v = Sys.sysfileNew(syspathFileToRewrite);
        if (
                sysfileToRewrite.v.canWrite()
                )
            Tes3.subAbort(Tes3.strTo(syspathFileToRewrite, "syspathFileToRewrite") + ", " +
                Tes3.strTo(sysfileToRewrite.v, "sysfileToRewrite") + " is ReadOnly, can not rewrite");

        try
        {
            Files.copy(Paths.get(sysfileToCopy_I.getPath()), Paths.get(syspathFileToRewrite.strFullPath()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToCopy, "syspathToCopy") + ", " +
                    Tes3.strTo(syspathFileToRewrite, "syspathFileToRewrite") + ", " +
                    Tes3.strTo(sysexcepError, "sysexcepError") +
                    " error in sysfileToCopy_I.CopyTo(syspathFileToRewrite.strFullPath, true)");
        }

        //                                                  //Regresa el nuevo File.
        Sys.subRefresh(sysfileToRewrite);
        sysfileRewrited_O.v = sysfileToRewrite.v;

        if (!(
                (sysfileRewrited_O.v.length() == sysfileToCopy_I.length()) &&
                        (sysfileRewrited_O.v.canWrite() == sysfileToCopy_I.canWrite())
        ))
            Tes3.subAbort(Tes3.strTo(syspathToCopy, "syspathToCopy") + ", " +
                    Tes3.strTo(syspathFileToRewrite, "syspathFileToRewrite") + ", " +
                    Tes3.strTo(sysfileRewrited_O.v, "sysfileRewrited_O") + ", " +
                    Tes3.strTo(sysfileToCopy_I, "sysfileToCopy_I") +
                    " ALGO EXTRAÑO PASO, el archivo al reescribirse no conservo sus mismas características");
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subMoveTo(
        //                                                  //Mueve un directorio o archivo a ser parte de otro
        //                                                  //      directorio, no debe existir en el nuevo padre.
        //                                                  //Este move solo puede ser al mismo dispositivo.

        //                                                  //File del archivo o directorio que se quiere mover, lo
        //                                                  //      actualiza a la nueva ubicación.
        Oobject<File> sysdirToMove_M,
        //                                                  //DirectoryInfo del directorio al cual se desea mover el
        //                                                  //      directorio anterior, este será el nuevo padre.
        Oobject<File> sysdirNewParent_M
        )
    {
        //                                                  //Verifica que ambos directorios existan.
        SyspathPath syspathToMove = Sys.syspathGet(sysdirToMove_M.v);
        if (
            //                                              //No es un directorio.
            !syspathToMove.boolIsDirectory()
                )
            Tes3.subAbort(Tes3.strTo(syspathToMove, "syspathToMove") + " directory do not exist");
        SyspathPath syspathNewParent = Sys.syspathGet(sysdirNewParent_M.v);
        if (
            //                                              //No es un directorio.
            !syspathNewParent.boolIsDirectory()
                )
            Tes3.subAbort(Tes3.strTo(syspathNewParent, "syspathNewParent") + " directory do not exist");

        if (
            //                                              //Están en raices distintas.
            syspathToMove.syspathRoot().strFullPath() != syspathNewParent.syspathRoot().strFullPath()
            )
            Tes3.subAbort(Tes3.strTo(syspathToMove, "syspathToMove") + ", " +
                Tes3.strTo(syspathNewParent, "syspathNewParent") + " can not move, they are not in the same root");

        //                                                  //Forma el syspath del directorio ya movido.
        SyspathPath syspathMoved = syspathNewParent.syspathAddName(syspathToMove.strName());

        //                                                  //Aborta si existe otro directorio o archivo con el mismo
        //                                                  //      nombre en el el disco.
        if (
            //                                              //El nuevo syspath, ya existe.
            syspathMoved.boolExists()
            )
            Tes3.subAbort(Tes3.strTo(syspathMoved, "syspathMoved") +
                " can not move, already exists a directory or file with same name");

        //                                                  //Mueve el directorio al nuevo directorio ("YaMOvido").
        try
        {
            //                                              //Nótese que se mueve abajo del NewParent y que conserva el
            //                                              //      nombre (última parte) que tenía.
            Files.move(Paths.get(sysdirNewParent_M.v.getPath()), Paths.get(syspathMoved.strFullPath()));
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToMove, "syspathToMove") + ", " +
                Tes3.strTo(syspathMoved, "syspathMoved") + ", " + Tes3.strTo(sysexcepError, "sysexcepError") +
                " error in sysdirToMove_I.MoveTo(syspathMoved.strFullPath)");
        }

        //                                                  //Actualiza a la nueva ubicación.
        sysdirToMove_M.v = Sys.sysdirNew(syspathMoved);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subDelete(
        //                                                  //Elimina un directorio del disco.

        //                                                  //DirectoryInfo que se desea eliminar.
        File sysdirToDelete_I,
        //                                                  //Si es true, también borra su contenido, si es false, solo
        //                                                  //      puede borrar si está vacío, en su defecto aborta.
        boolean boolDeleteSubdirectoriesAndFiles_I
        )
    {
        //                                                  //Crea el syspath del directorio, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathToDelete = Sys.syspathGet(sysdirToDelete_I);

        if (
            !syspathToDelete.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathToDelete, "syspathToDelete") + " do not exist as directory");

        //                                                  //Hace el delete.
        try
        {
            if (
                boolDeleteSubdirectoriesAndFiles_I
                )
            {
                //                                          //Delete the contents of the directory.
                Sys.subDeleteRecursively(sysdirToDelete_I);
                //                                          //Delete the directory itself.
                Files.delete(Paths.get(sysdirToDelete_I.getPath()));
            }
            else
            {
                //                                          //Delete only the directory. It will fail if it has contents
                Files.delete(Paths.get(sysdirToDelete_I.getPath()));
            }
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToDelete, "syspathToDelete") + ", " +
                Tes3.strTo(boolDeleteSubdirectoriesAndFiles_I, "boolDeleteSubdirectoriesAndFiles_I") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") +
                " error in sysdirToDelete_I.Delete(boolDeleteSubdirectoriesAndFiles_I)");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void subDeleteRecursively(
        //                                                  //Delete a directory recursively (deleting all of its
        //                                                  //      contents. It will not delete the "root directory".

        //                                                  //The folder that must be deleted recursively.
                                                            File sysdirToDelete_I
    ) throws IOException
    {
        for (File sysdirOrFile : sysdirToDelete_I.listFiles())
        {
            if (
                sysdirOrFile.isDirectory()
                )
            {
                //Recursive method.
                Sys.subDeleteRecursively(sysdirOrFile);
            }
            Files.delete(Paths.get(sysdirOrFile.getPath()));
        }
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subDelete(
        //                                                  //Elimina un archivo del disco.

        //                                                  //File que se desea eliminar.
        File sysfileToDelete_I
        )
    {
        //                                                  //Crea el syspath del directorio, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathToDelete = Sys.syspathGet(sysfileToDelete_I);

        if (
            !syspathToDelete.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathToDelete, "syspathToDelete") + " do not exists as file");

        //                                                  //Hace el delete.
        try
        {
            sysfileToDelete_I.delete();
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathToDelete, "syspathToDelete") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileToDelete_I.Delete()");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public static File[] arrsysdirGetDirectories(
        //                                                  //Extrae el conjunto de subdirectorios de un directorio.

        //                                                  //DirectoryInfo del cual se quiere información.
        File sysdirToSearch_I
        )
    {
        //                                                  //Crea el syspath del directorio, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathDirectoryToSearch = Sys.syspathGet(sysdirToSearch_I);

        if (
            !syspathDirectoryToSearch.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToSearch, "syspathDirectoryToSearch") +
                " no existe como directorio");

        //                                                  //Extrae subdirectorios.
        File[] arrsysdirGetDirectories;
        try
        {
            LinkedList<File> lstsysfileGetDirectories = new LinkedList<File>();
            for (File sysfileEntry : sysdirToSearch_I.listFiles())
            {
                if (
                    sysfileEntry.isDirectory()
                       )
                {
                    lstsysfileGetDirectories.add(sysfileEntry);
                }

            }
            arrsysdirGetDirectories = lstsysfileGetDirectories.toArray(new File[0]);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToSearch, "syspathDirectoryToSearch") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysdirToSearch_I.GetDirectories()");

            arrsysdirGetDirectories = null;
        }

        //                                                  //Regresa el conjunto de subdirectorios.
        return arrsysdirGetDirectories;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static File[] arrsysfileGetFiles(
        //                                                  //Extrae el conjunto de archivos de un directorio.

        //                                                  //DirectoryInfo del cual se quiere información.
        File sysdirToSearch_I
        )
    {
        //                                                  //Crea el syspath del directorio, esto solo para confirmar
        //                                                  //      que todo sigue bien y tener un mejor diagnóstico en
        //                                                  //      caso de problemas.
        SyspathPath syspathDirectoryToSearch = Sys.syspathGet(sysdirToSearch_I);

        if (
            !syspathDirectoryToSearch.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToSearch, "syspathDirectoryToSearch") +
                " does not exists");


        //                                                  //Extrae Archivos.
        File[] arrsysfileGetFiles;
        try
        {
            LinkedList<File> lstsysfileGetFiles = new LinkedList<File>();
            for (File sysfileEntry : sysdirToSearch_I.listFiles())
            {
                if (
                    sysfileEntry.isFile()
                    )
                {
                    lstsysfileGetFiles.add(sysfileEntry);
                }

            }
            arrsysfileGetFiles = lstsysfileGetFiles.toArray(new File[0]);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathDirectoryToSearch, "syspathDirectoryToSearch") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysdirToSearch_I.GetFiles()");

            arrsysfileGetFiles = null;
        }

        //                                                  //Regresa el conjunto de subdirectorios.
        return arrsysfileGetFiles;
    }

    /*END-TASK*/

    //==================================================================================================================
    /*TASK Sys.Text Text Files*/
    //--------------------------------------------------------------------------------------------------------------
    public static String[] arrstrReadAll(
        //                                                  //Carga la totalidad de un archivo de texto a memoria.

        //                                                  //arrstr, archivo de texto en formato de arreglo de Strings.

        //                                                  //File del archivo a cargar a memoría.
        Oobject<File> osysfileInputTextFile_M
        )
    {
        //                                                  //Se crea el Scanner (si no existe aborta).
        Scanner syssr = Sys.syssrNewTextFile(osysfileInputTextFile_M);

        //                                                  //Paso el archivo a memoria (un String).

        String strTextFile = "";
        try
        {
            //                                              //Lee TODO un archivo.

            //******************CHECAR**************************

            //                                              //strTextFile = syssr.readToEnd();		<== Version C#
            strTextFile = Tools.readToEnd(syssr);

        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(osysfileInputTextFile_M.v.getName(), "sysfileInputTextFile_M.Name") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in syssr.readToEnd()");

            strTextFile = null;

            syssr.close();
        }

        //                                                  //Es necesaro cerrar el syssr
        syssr.close();

        //                                                  //Formo arreglo con lo leído.
        String[] arrstrLine;
        //                                                  //Overloaded version of split.
        //                                                  //Explanation: if the negative parameter was not included,
        //                                                  //      trailing space would be eliminated, meaning that,
        //                                                  //      empty lines would be eliminated.

        arrstrLine = strTextFile.split("\n", -1);

        return arrstrLine;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subWriteAll(
        //                                                  //Sube la totalidad de un arreglo en memoria a un archivo
        //                                                  //      de texto (no debe existir el archivo).

        //                                                  //arrstr, archivo de texto en formato de arreglo de Strings.
        String[] arrstrLine_I,
        //                                                  //File del archivo a al cual se sube lo que se tiene en
                                                            //      memoría.
        Oobject<File> osysfileOutputTextFile_M
        )
    {
        //                                                  //Tomo el path para analizarlo y poder dar un mejor
        //                                                  //      diagnostico.
        SyspathPath syspathFile = Sys.syspathGet(osysfileOutputTextFile_M.v);
        if (
            //                                              //Ya existe como DIRECTORY o FILE
            syspathFile.boolExists()
            )
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspathFile") + " exists as DIRECTORY o FILE");

        //                                                  //Creo el stream writer.
        Oobject<PrintWriter> syssw = new Oobject<PrintWriter>();
        syssw.v = Sys.sysswNewWriteTextFile(osysfileOutputTextFile_M);

        //                                                  //Paso todo el arreglo a un solo String de líneas.
        String strTextFile = String.join("\n", arrstrLine_I);

        //                                                  //Escribo el String al archivo (un solo WriteLine).
        Sys.subWriteLine(strTextFile, syssw);

        //                                                  //Es necesaro cerrar el syssw
        syssw.v.close();
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subRewriteAll(
        //                                                  //Sube la totalidad de un arreglo en memoria a un archivo
        //                                                  //      de texto que ya existe (será reescritura).

        //                                                  //arrstr, archivo de texto en formato de arreglo de Strings.
        String[] arrstrLine_I,
        //                                                  //File del archivo a al cual se sube lo que se tiene en
        //                                                  //      memoría.
        Oobject<File> osysfileOutputTextFile_M
        )
    {
        //                                                  //Tomo el path para analizarlo y poder dar un mejor
        //                                                  //      diagnostico.
        SyspathPath syspathFile = Sys.syspathGet(osysfileOutputTextFile_M.v);
        if (
            //                                              //NO existe como FILE
            !syspathFile.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspathFile") + " file do not exist");

        //                                                  //Creo el stream writer.
        Oobject<PrintWriter> syssw = new Oobject<PrintWriter>();
        syssw.v = sysswNewRewriteTextFile(osysfileOutputTextFile_M);

        //                                                  //Paso todo el arreglo a un solo String de líneas.
        String strTextFile = String.join("\n", arrstrLine_I);

        //                                                  //Escribo el String al archivo (un solo WriteLine).
        Sys.subWriteLine(strTextFile, syssw);

        syssw.v.close();
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Scanner   syssrNewTextFile(
        //                                                  //Genera el Scanner para un archivo de texto, si no
        //                                                  //      existe abortará.

        //                                                  //syssr, Scanner listo.

        //                                                  //File del archivo.
        Oobject<File> osysfileInputTextFile_M
        )
    {
        //                                                  //Confirma la existencia el archivo.
        SyspathPath syspathInput = Sys.syspathGet(osysfileInputTextFile_M.v);
        if (
            //                                              //No existe como archivo.
            !syspathInput.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathInput, "syspathInput") + " file does not exist");

        //                                                  //Creo el acceso al archivo.
        Scanner syssrTextFile;
        try
        {
            //                                              //Creo el stream reader con el charset necesario de acuerdo
            //                                              //      a su encoding.
            syssrTextFile = new Scanner(new File(osysfileInputTextFile_M.v.getAbsolutePath()),
                Sys.strCharsetName(osysfileInputTextFile_M));
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathInput, "syspathInput") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileInput_M.OpenText()");

            syssrTextFile = null;
        }

        return syssrTextFile;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strCharsetName (
        //                                                  //Gets the adequate charset for a certain file by testing
        //                                                  //      them one by one. Right now only 3 charset are
        //                                                  //      tested.
        //                                                  //The file from which the correct encoding must be found.
        Oobject<File> osysfileInputTextFile_M
        )
    {
        String strCharsetName;
        //                                                  //The charset to be tested.
        String[] arrstrCharsetToBeTested = {"UTF-8", "windows-1252", "ISO-8859-7"};

        int intI = 0;
        /*REPEAT-WHILE*/
        while (
            //                                              //There are more charsets to be tested
            (intI < arrstrCharsetToBeTested.length) &&
                //                                          //The charset is not the correct one
                (!boolCharsetIsCorrect(osysfileInputTextFile_M.v, arrstrCharsetToBeTested[intI]))
            )
        {
            intI = intI + 1;
        }

        if (
            //                                              //A correct charset was found.
            intI < arrstrCharsetToBeTested.length
            )
        {
            strCharsetName = new String(arrstrCharsetToBeTested[intI]);
        }
        else
        {
            SyspathPath syspathFile = Sys.syspathGet(osysfileInputTextFile_M.v);
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspath") + " has an unrecognized charset");
            strCharsetName = null;
        }

        return strCharsetName;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolCharsetIsCorrect(
        //                                                  //Test if a file has a specific charset.
        //                                                  //The file from on which the charset will be tested.
        File sysfileInputTextFile_I,
        //                                                  //Name of the charset to be tested.
        String strCharsetToTest_I
        )
    {
        //                                                  //Indicate if the charset to be tested is the correct one
        //                                                  //      for the file.
        boolean boolCharsetIsCorrect = false;
        try {
            //                                              //Get the charset with the name.
            Charset charsetToTest = Charset.forName(strCharsetToTest_I);
            BufferedInputStream buffInput = new BufferedInputStream(new FileInputStream(sysfileInputTextFile_I));

            CharsetDecoder charsetDecoder = charsetToTest.newDecoder();
            charsetDecoder.reset();

            byte[] buffer = new byte[512];

            /*REPEAT-WHILE*/
            while (
                (buffInput.read(buffer) != -1) &&
                    !boolCharsetIsCorrect
                )
            {
                try
                {
                    charsetDecoder.decode(ByteBuffer.wrap(buffer));
                    boolCharsetIsCorrect = true;
                }
                catch (CharacterCodingException e)
                {
                    boolCharsetIsCorrect = false;
                }
            }
            buffInput.close();
        }
        catch (Exception e)
        {
            boolCharsetIsCorrect = false;
        }

        return boolCharsetIsCorrect;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static PrintWriter sysswNewWriteTextFile(
        //                                                  //Genera el FileWriter para un archivo de texto para
        //                                                  //      escritura, no debe existir el archivo.

        //                                                  //syssw, FileWriter listo.

        //                                                  //File del archivo.
        Oobject<File> osysfileOutputTextFile_M
        )
    {
        //                                                  //Confirma la no existencia de algo en el path.
        SyspathPath syspathFile = Sys.syspathGet(osysfileOutputTextFile_M.v);
        if (
            //                                              //Ya existe algo en el path, puede ser DIRECTORY o FILE.
            syspathFile.boolExists()
            )
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspathFile") + " exists as a DIRECTORY or FILE");

        //                                                  //Creo el acceso al archivo.
        PrintWriter sysswWriteTextFile;
        try
        {
            //                                              //Creo el stream reader.
            sysswWriteTextFile = new PrintWriter(osysfileOutputTextFile_M.v);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspathFile") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysfileOutput_M.CreateText()");

            sysswWriteTextFile = null;
        }

        return sysswWriteTextFile;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static PrintWriter sysswNewRewriteTextFile(
        //                                                  //Genera el FileWriter para un archivo de texto para
        //                                                  //      reescritura.

        //                                                  //syssw, FileWriter listo.

        //                                                  //File del archivo.
        Oobject<File> osysfileOutputTextFile_M
        )
    {
        //                                                  //Confirma la existencia de algo en el path y que se le
        //                                                  //      pueda reescribir
        SyspathPath syspathFile = Sys.syspathGet(osysfileOutputTextFile_M.v);
        if (
            //                                              //NO existe el FILE.
            !syspathFile.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspathFile") +
                    " file do not exist, to write use sysswWriteTextFile");
        if (
            !osysfileOutputTextFile_M.v.canWrite()
            )
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspathFile") +
                ", " + Tes3.strTo(!osysfileOutputTextFile_M.v.canWrite(), "sysfileOutputTextFile_M.IsReadOnly") +
                " is ReadOnly");

        //                                                  //Creo el acceso al archivo.
        PrintWriter sysswRewriteTextFile;
        try
        {
            //                                              //Creo el stream reader.
            sysswRewriteTextFile = new PrintWriter(osysfileOutputTextFile_M.v);
            //sysswRewriteTextFile = new FileWriter(sysfileOutputTextFile_M.FullName, false, Encoding.Default);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(syspathFile, "syspathFile") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in new PrintWriter(sysfileOutputTextFile_M)");

            sysswRewriteTextFile = null;
        }

        return sysswRewriteTextFile;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static String strReadLine(
        //                                                  //Leer una línea de texto.

        //                                                  //str, Línea leída.

        //                                                  //Scanner del archivo.
        Oobject<Scanner> syssrInputTextFile_M
        )
    {
        //                                                  //Leo una línea del archivo.
        String strReadLine;
        try
        {
            //                                              //Leo una línea.
            strReadLine = syssrInputTextFile_M.v.nextLine();
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(sysexcepError, "sysexcepError") + " error in syssrTextFile_M.ReadLine()");

            strReadLine = null;

            syssrInputTextFile_M.v.close();
        }

        return strReadLine;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subWriteLine(
        //                                                  //Escribe una línea de texto.

        //                                                  //Linea que se va a escribir.
        String strLine_I,
        //                                                  //FileWriter del archivo.
        Oobject<PrintWriter> osysswOutputTextFile_M
        )
    {
        //                                                  //Escribe una línea en el archivo.
        try
        {
            //                                              //Escribo una línea.
            osysswOutputTextFile_M.v.println(strLine_I);
        }
        catch (Exception sysexcepError)
        {
            Tes3.subAbort(Tes3.strTo(strLine_I, "strLine_I") + ", " +
                Tes3.strTo(sysexcepError, "sysexcepError") + " error in sysswTextFile_M.WriteLine(strLine_I)");

            osysswOutputTextFile_M.v.close();
        }
    }
    /*END-TASK*/
    //--------------------------------------------------------------------------------------------------------------
}
