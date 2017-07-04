/*TASK RPS.Nvsbsol Relevant Part Solution New vs. Base Comparison*/
package Nvsbsol;

import java.io.*;
import java.text.*;
import java.util.*;
import Nvsbcod.NvsbcodNewVsBaseCodeComparison;
import QEnablerBase.*;
import SoftwareAutomation.*;
import TowaInfrastructure.*;
import TowaStandardSupport.Std;

//AUTHOR: Towa (AZG-Alejandro Zamudio).
															//CO-AUTHOR: Towa (GLG-Gerardo Lopez).
															//DATE: 20-Febrero-2017.
															//PURPOSE:
															//Funcionalidad util para comparar soluciones de un
															//      producto de software que se está desarrollando.
															//ESTA FUNCIONALIDAD TIENE COMO FINALIDAD PODER ASEGURAR LA
															//      CALIDAD DEL SOFTWARE QUE SE ESTA DESARROLLANDO.

//======================================================================================================================
    public class NvsbsolNewVsBaseSolutionComparison extends BclassBaseClassAbstract
															//Clase que incluye funcionalidad para comparar soluciones
															//      completa (todos los archivos del directorio que
															//      integra la solución).
															//Nótese que dado que los archivos .test son incluidos en la
															//      clase Codtx, también pueden ser comparado el
															//      conjunto de logs de prueba (archivos .test) que
															//      generan las pruebas de la solución).
    {
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    public BclassmutabilityEnum bclassmutability() {  return BclassmutabilityEnum.INMUTABLE; }

                                                            //Cantidad arbitraria mínima de similitud entre el contenido
                                                            //      de una pareja determinada de archivos para que se
                                                            //      puedan considerar potencialmente el mismo archivo.
                                                            //(Debe ser entre 0.2 y 1.0).
    private static final double numMINIMUM_SIMILARITY_THRESHOLD = 0.2;

                                                            //En ocaciones se tienes archivos y/o subdirectorios que
                                                            //      NO SON PARTE DE LA SOLUCIÓN y simplemente debe ser
                                                            //      IGNORADOS, ejemplos de esto son:
                                                            //1. La operación del SubVersión (SVN) crea el directorio
                                                            //      .svn
                                                            //2. En la Mac (GLG: 12Abr2017, en realidad no se si es la
                                                            //      Mac o el "Parallels" que opera sobre la Mac, pero
                                                            //      esto en realidad no tiene importancia), en cada
                                                            //      directorio se tiene el archivo oculto ".DS_Store".
                                                            //El constructor estatico debe convertir a minúscular y
                                                            //      ordenar estos 2 arreglos
    private static String[] arrstrDIRECTORY_TO_IGNORE =
    {
                                                            //Incluido por SVN
            ".svn",
            ".git",
            };
    private static String[] arrstrFILE_TO_IGNORE = {
                                                            //Aparecen en Mac (o el Parallels bajo Mac)
            ".DS_Store",
            ".bash_profile",
            };

    //------------------------------------------------------------------------------------------------------------------
    static
    {
        if (
            (NvsbsolNewVsBaseSolutionComparison.numMINIMUM_SIMILARITY_THRESHOLD < 0.2) ||
            (NvsbsolNewVsBaseSolutionComparison.numMINIMUM_SIMILARITY_THRESHOLD > 1.0)
        )
        Tools.subAbort(
            Tes2.strTo(NvsbsolNewVsBaseSolutionComparison.numMINIMUM_SIMILARITY_THRESHOLD,
            "NvsbsolNewVsBaseSolutionComparison.numMINIMUM_SIMILARITY_THRESHOLD") +
            " should be in the range 0.2-1.0");

        NvsbsolNewVsBaseSolutionComparison.subPrepareToIgnore(
            NvsbsolNewVsBaseSolutionComparison.arrstrFILE_TO_IGNORE);
        NvsbsolNewVsBaseSolutionComparison.subPrepareToIgnore(
            NvsbsolNewVsBaseSolutionComparison.arrstrDIRECTORY_TO_IGNORE);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT STATIC CONSTRUCTOR*/

    private static void subPrepareToIgnore(
                                                            //1. Verifica que sea un path valido
                                                            //2. Pasa a minúsculas
                                                            //3. Ordena

            String[] arrstrNameToVerify_M
            )
    {
        for (Integer intI = 0; intI < arrstrNameToVerify_M.length; intI = intI + 1)
        {
            SyspathPath syspath = new SyspathPath(arrstrNameToVerify_M[intI]);
            if (
                    !syspath.boolIsValid()
            )
            Tools.subAbort(Tes2.strTo(arrstrNameToVerify_M[intI], "arrstrNameToVerify_M[" + intI + "])") +
                    ", is not a valid Name for a file or directory");

            arrstrNameToVerify_M[intI] = arrstrNameToVerify_M[intI].toLowerCase();
        }

        Arrays.sort(arrstrNameToVerify_M);
    }

    //--------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

                                                            //Full path del directorio New que ha evolucionado y se
                                                            //      desea visualizar las diferencias con su Base (esta
                                                            //      es la raíz del conjunto de archivos que integran la
                                                            //      solución)
    private /*readonly*/ SyspathPath syspathDirectoryNew_Z;
    public SyspathPath syspathDirectoryNew() { return this.syspathDirectoryNew_Z; }

                                                            //Colección de cod que representan todos los archivos con
                                                            //      extensión válida del directorio New.
                                                            //(son válidos todos los file extension incluidos en la
                                                            //      Tech de SoftwareAutomation).
                                                            //Ordenados por el syspath.
    private /*readonly*/ CodCodeAbstract[] arrcodNewToCompare_Z;
    public CodCodeAbstract[] arrcodNewToCompare() { return this.arrcodNewToCompare_Z; }

                                                            //Colección de syspath que representan todos los archivos
                                                            //      con extensión inválida del directorio New.
                                                            //Ordenados por el syspath.
    private /*readonly*/ SyspathPath[] arrsyspathNewExcluded_Z;
    public SyspathPath[] arrsyspathNewExcluded() { return this.arrsyspathNewExcluded_Z; }

                                                            //Full path del directorio Base contra el cual se desea
                                                            //      comparar (esta es la raiz del conjunto de archivos
                                                            //      que integran la solución))
    private /*readonly*/ SyspathPath syspathDirectoryBase_Z;
    public SyspathPath syspathDirectoryBase() { return this.syspathDirectoryBase_Z; }

                                                            //Colección de cod que representan todos los archivos con
                                                            //      extensión válida del directorio Base.
    private /*readonly*/ CodCodeAbstract[] arrcodBaseToCompare_Z;
    public CodCodeAbstract[] arrcodBaseToCompare() { return this.arrcodBaseToCompare_Z; }

                                                            //Colección de syspath que representan todos los archivos
                                                            //      con extensión inválida del directorio New.
                                                            //Ordenados por el syspath.
    private /*readonly*/ SyspathPath[] arrsyspathBaseExcluded_Z;
    public SyspathPath[] arrsyspathBaseExcluded() { return this.arrsyspathBaseExcluded_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

                                                            //Colección de objetos de comparación de cod que contiene
                                                            //      todos los archivos emparejados de New y Base,
                                                            //      tanto por que tienen el mismo nombre como por
                                                            //      que tienen un alto grado de similitud.
                                                            //Ordenados por el syspath de los files en New.
    private NvsbcodNewVsBaseCodeComparison[] arrnvsbcodMatchingFiles_Z = null;
    public NvsbcodNewVsBaseCodeComparison[] arrnvsbcodMatchingFiles()
    {
        if (
                this.arrnvsbcodMatchingFiles_Z == null
        )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Procesa la comparación (ver método).
            this.subNewVsBase(this.arrnvsbcodMatchingFiles_Z, this.arrcodNewAdded_Z,
            this.arrcodBaseDeleted_Z, this.arrsyspathNewDuplicateFileName_Z,
            this.arrsyspathBaseDuplicateFileName_Z);

            this.subSetIsResetOff();
        }

        return this.arrnvsbcodMatchingFiles_Z;
    }

                                                            //Colección de objetos cod que contiene todos los archivos
                                                            //      añadidos a new.
                                                            //Ordenados por el syspath.
    private CodCodeAbstract[] arrcodNewAdded_Z = null;
    public CodCodeAbstract[] arrcodNewAdded()
    {
        if (
                this.arrcodNewAdded_Z == null
                )
        {
                this.subVerifyObjectConstructionIsFinished();

                                                            //Procesa la comparación (ver método).
                this.subNewVsBase(this.arrnvsbcodMatchingFiles_Z, this.arrcodNewAdded_Z,
                this.arrcodBaseDeleted_Z, this.arrsyspathNewDuplicateFileName_Z,
                this.arrsyspathBaseDuplicateFileName_Z);

                this.subSetIsResetOff();
        }

        return this.arrcodNewAdded_Z;
    }

                                                            //Colección de objetos cod que contiene todos los archivos
                                                            //      eliminado de base.
                                                            //Ordenados por el syspath.
    private CodCodeAbstract[] arrcodBaseDeleted_Z = null;
    public CodCodeAbstract[] arrcodBaseDeleted()
    {
        if (
                this.arrcodBaseDeleted_Z == null
                )
        {
                this.subVerifyObjectConstructionIsFinished();

                this.subNewVsBase(this.arrnvsbcodMatchingFiles_Z, this.arrcodNewAdded_Z,
                this.arrcodBaseDeleted_Z, this.arrsyspathNewDuplicateFileName_Z,
                this.arrsyspathBaseDuplicateFileName_Z);

                this.subSetIsResetOff();
        }

        return this.arrcodBaseDeleted_Z;
    }

                                                            //Colección de syspath que representan todos los archivos
                                                            //      con nombres duplicados de New.
                                                            //Ordenados por el nombre del file y syspath (en ese orden).
    private SyspathPath[] arrsyspathNewDuplicateFileName_Z = null;
    public SyspathPath[] arrsyspathNewDuplicateFileName()
    {
    if (
            this.arrsyspathNewDuplicateFileName_Z == null
        )
    {
        this.subVerifyObjectConstructionIsFinished();

                                                            //Procesa la comparación (ver método).
        this.subNewVsBase(this.arrnvsbcodMatchingFiles_Z, this.arrcodNewAdded_Z,
        this.arrcodBaseDeleted_Z, this.arrsyspathNewDuplicateFileName_Z,
        this.arrsyspathBaseDuplicateFileName_Z);

        this.subSetIsResetOff();
    }

    return this.arrsyspathNewDuplicateFileName_Z;
    }

                                                            //Colección de syspath que representan todos los archivos
                                                            //      con nombres duplicados de Base.
                                                            //Ordenados por el nombre del file y syspath (en ese orden).
    private SyspathPath[] arrsyspathBaseDuplicateFileName_Z = null;
    public SyspathPath[] arrsyspathBaseDuplicateFileName()
    {
        if (
                this.arrsyspathBaseDuplicateFileName_Z == null
                )
        {
                this.subVerifyObjectConstructionIsFinished();

                                                            //Procesa la comparasión (ver método).
                this.subNewVsBase(this.arrnvsbcodMatchingFiles_Z, this.arrcodNewAdded_Z,
                        this.arrcodBaseDeleted_Z, this.arrsyspathNewDuplicateFileName_Z,
                        this.arrsyspathBaseDuplicateFileName_Z);

                this.subSetIsResetOff();
        }

        return this.arrsyspathBaseDuplicateFileName_Z;
    }

                                                            //Cantidad de comparaciones que el archivo no cambio nada.
                                                            //Pudo haber cambiado de nombre o de ubicación
    private Integer intFilesUnchanged_Z = null;
    public int intFilesUnchanged()
    {
        if (
                this.intFilesUnchanged_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Calcula.
            this.intFilesUnchanged_Z = 0;
            for (NvsbcodNewVsBaseCodeComparison nvsbcod : this.arrnvsbcodMatchingFiles())
            {
                if (
                    nvsbcod.numSimilarity() == 1.0
                    )
                {
                    this.intFilesUnchanged_Z = this.intFilesUnchanged_Z + 1;
                }
            }

            this.subSetIsResetOff();
        }

        return (int)this.intFilesUnchanged_Z;
    }

                                                            //Cantidad de líneas que coinciden en la solución.
                                                            //Esta cantidad se obtiene con la suma de todos los intEqual
                                                            //      de los archivos emparejados.
    private Integer intLinesEqual_Z = null;
    public int intLinesEqual()
    {
        if (
        this.intLinesEqual_Z == null
        )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Calcula.
            this.intLinesEqual_Z = 0;
            for (NvsbcodNewVsBaseCodeComparison nvsbcod : this.arrnvsbcodMatchingFiles())
            {
                this.intLinesEqual_Z = this.intLinesEqual_Z + nvsbcod.intEqual();
            }

            this.subSetIsResetOff();
        }

        return (int)this.intLinesEqual_Z;
    }

                                                            //Cantidad total de líneas añadidas en la solución.
                                                            //Estas serán todas las líneas insertadas en los archivos
                                                            //      que coinciden y todas las líneas incluídas en los
                                                            //      added.
    private Integer intLinesInserted_Z = null;
    public int intLinesInserted()
    {
        if (
                this.intLinesInserted_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Calcula.
            this.intLinesInserted_Z = 0;
            for (NvsbcodNewVsBaseCodeComparison nvsbcod : this.arrnvsbcodMatchingFiles())
            {
                this.intLinesInserted_Z = this.intLinesInserted_Z + nvsbcod.intInserted();
            }
            for (CodCodeAbstract cod : this.arrcodNewAdded())
            {
                this.intLinesInserted_Z = this.intLinesInserted_Z + cod.arrstrLine().length;
            }

            this.subSetIsResetOff();
        }

        return (Integer)this.intLinesInserted_Z;
    }

                                                            //Cantidad total de líneas eliminadas en la solución.
                                                            //Estas serán todas las líneas removidas en los archivos
                                                            //      que coinciden y todas las líneas incluídas en los
                                                            //      deleted.
    private Integer intLinesRemoved_Z = null;
    public int intLinesRemoved()
    {
        if (
                this.intLinesRemoved_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Calcula.
            this.intLinesRemoved_Z = 0;
            for (NvsbcodNewVsBaseCodeComparison nvsbcod : this.arrnvsbcodMatchingFiles())
            {
                this.intLinesRemoved_Z = this.intLinesRemoved_Z + nvsbcod.intRemoved();
            }
            for (CodCodeAbstract cod : this.arrcodBaseDeleted_Z)
            {
                this.intLinesRemoved_Z = this.intLinesRemoved_Z + cod.arrstrLine().length;
            }

            this.subSetIsResetOff();
        }

        return (int)this.intLinesRemoved_Z;
    }

                                                            //Métrica de similitud entre New y Base, tomando la cantidad
                                                            //      de líneas que coincidieron (intlinesEqual) y
                                                            //      dividiéndola entre el promedio del total de líneas
                                                            //      de New y Base. Esta métrica es un valor arbitrario.
                                                            //El total de líneas en New es: Equal + Inserted.
                                                            //El total de líneas en Base es: Equal + Removed.
    private Double numSimilarity_Z = null;
    public double numSimilarity()
    {
		if (
		    this.numSimilarity_Z == null
		    )
		{
            this.subVerifyObjectConstructionIsFinished();

                                                            //Calcula.
            int intNewTotalLines = this.intLinesEqual_Z + this.intLinesInserted_Z;
            int intBaseTotalLines = this.intLinesEqual_Z + this.intLinesRemoved_Z;
            double numAverageOfLines = (intNewTotalLines + intBaseTotalLines) / 2.0;
            this.numSimilarity_Z = this.intLinesEqual_Z / numAverageOfLines;

            this.subSetIsResetOff();
		}

		return (Double)this.numSimilarity_Z;
    }

//----------------------------------------------------------------------------------------------------------------------
/*METHODS TO SUPPORT COMPUTED VARIABLES*/

//----------------------------------------------------------------------------------------------------------------------
    protected void subResetOneClass()
    {
        this.arrnvsbcodMatchingFiles_Z = null;
        this.arrcodNewAdded_Z = null;
        this.arrcodBaseDeleted_Z = null;
        this.arrsyspathNewDuplicateFileName_Z = null;
        this.arrsyspathBaseDuplicateFileName_Z = null;
        this.intLinesEqual_Z = null;
        this.intLinesInserted_Z = null;
        this.intLinesRemoved_Z = null;
        this.numSimilarity_Z = null;
    }

    //------------------------------------------------------------------------------------------------------------------
    public String strTo(TestoptionEnum strtoSHORT_I)
    {
        String strObjId = Test.strGetObjId(this);

        return strObjId + "[" + super.strTo(TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.syspathDirectoryNew_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.arrcodNewToCompare_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.arrsyspathNewDuplicateFileName_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.syspathDirectoryBase_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.arrcodBaseToCompare_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.arrsyspathBaseDuplicateFileName_Z, TestoptionEnum.SHORT) + "]";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public  String strTo()
    {
        String strObjId = Test.strGetObjId(this);

                                                            //PENDIENTE, BUSCAR UNA FORMA DE REDUCIR EL VOLUMEN DE ESTA
                                                            //      INFORMACIÓN.
                                                            //1. MÉTODO ESPECIFICO AQUÍ ABAJO PARA SACAR arreglos de cod
                                                            //      SHORT.
                                                            //2. AÑADIR OTRA OPCIÓN a strTo que sea solo válida para
                                                            //      bclass y btuple.
                                                            //POR LO PRONTO TOME LA OPCIÓN 1.
        return strObjId + "{" + Tes2.strTo(this.syspathDirectoryNew_Z, "syspathDirectoryNew") + ", " +
                Tes2.strTo(this.arrstrArrcodTo(this.arrcodNewToCompare_Z), "arrcodNewToCompare") + ", " +
                Tes2.strTo(this.arrstrArrsyspathTo(this.arrsyspathNewExcluded_Z), "arrsyspathNewExcluded") + ", " +
                Tes2.strTo(this.syspathDirectoryBase_Z, "syspathDirectoryBase") + ", " +
                Tes2.strTo(this.arrstrArrcodTo(this.arrcodBaseToCompare_Z), "arrcodBaseToCompare") + ", " +
                Tes2.strTo(this.arrstrArrsyspathTo(this.arrsyspathBaseExcluded_Z), "arrsyspathBaseExcluded") + "}" +
                "==>" + super.strTo();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String[] arrstrArrcodTo(
            //                                              //arrstr, arrcod converted
            //                                              //this[I], no info required

            //                                              //Array que será procesado
            CodCodeAbstract[] arrcod_I
            )
    {
        String[] arrstrArrcodTo = new String[arrcod_I.length];

        for (int intI = 0; intI < arrcod_I.length; intI = intI + 1)
        {
            arrstrArrcodTo[intI] = Tes2.strTo(arrcod_I[intI], TestoptionEnum.SHORT);
        }

        return arrstrArrcodTo;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String[] arrstrArrsyspathTo(
		                                                    //arrstr, arrsyspath converted
		                                                    //this[I], no info required

		                                                    //Array que será procesado
		SyspathPath[] arrsyspath_I
		)
    {
        String[] arrstrArrsyspathTo = new String[arrsyspath_I.length];

        for (int intI = 0; intI < arrsyspath_I.length; intI = intI + 1)
        {
        arrstrArrsyspathTo[intI] = Tes2.strTo(arrsyspath_I[intI], TestoptionEnum.SHORT);
        }

        return arrstrArrsyspathTo;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    private NvsbsolNewVsBaseSolutionComparison() { super(true); }

    //------------------------------------------------------------------------------------------------------------------
    public NvsbsolNewVsBaseSolutionComparison(
                                                            //Carga New y Base.
                                                            //this.*[O], asigna valores.

		SyspathPath syspathDirectoryNew_G,
		SyspathPath syspathDirectoryBase_G
		)
    {
		super(false);
        {
            if (
                    syspathDirectoryNew_G == null
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryNew_G, "syspathDirectoryNew_G") + " can not be null");
            if (
                    !syspathDirectoryNew_G.boolIsDirectory()
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryNew_G, "syspathDirectoryNew_G") + " should be a DIRECTORY");
            if (
                    syspathDirectoryBase_G == null
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryBase_G, "syspathDirectoryBase_G") + " can not be null");
            if (
                    !syspathDirectoryBase_G.boolIsDirectory()
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryBase_G, "syspathDirectoryBase_G") + " should be a DIRECTORY");

                                                            //Guarda info.
            this.syspathDirectoryNew_Z = syspathDirectoryNew_G;
            this.syspathDirectoryBase_Z = syspathDirectoryBase_G;

                                                            //Procesa los syspath para acceder a los archivos que son
                                                            //      referenciados.
            this.subGetInfoFromDirectory(this.arrcodNewToCompare_Z, this.arrsyspathNewExcluded_Z,
                this.syspathDirectoryNew_Z);
            this.subGetInfoFromDirectory(this.arrcodBaseToCompare_Z, this.arrsyspathBaseExcluded_Z,
                this.syspathDirectoryBase_Z);

            this.subSetObjectConstructionFinish();
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    /*TASK Nvsbsol2 subNewVsBase*/
    //------------------------------------------------------------------------------------------------------------------
    private void subNewVsBase(
                                                            //Compare solutions New vs Base
                                                            //this[I], object info.

                                                            //Pair (file from New and file from base) that are selected
                                                            //      to correspond to each other.
                                                            //1. Identify New o Base files with duplicated file name
                                                            //      within New or Base (Ej. New contains 2 files named
                                                            //      X.xx, and Base contains 1 file named X.xx, all this
                                                            //      3 files will be marked as duplicated).
                                                            //2. Pair New and Base files with same file name.
                                                            //3. All remaning files from New should try to match all
                                                            //      ramaning files from Base.
                                                            //3a. Only match files with same file extension.
                                                            //3b. Select the best match (highest numSimilarity).
                                                            //3c. Only one match per file.
                                                            //3d. Only match files with numSimilarity >=
                                                            //      numMINIMUM_SIMILARITY_THRESHOLD.
                                                            //Order by syspath in New.
        NvsbcodNewVsBaseCodeComparison[] arrnvsbcodMatchingFiles_O,
                                                            //No matching New (and Base) files not include in duplicated
                                                            //      file name.
                                                            //Order by syspath.
        CodCodeAbstract[] arrcodNewAdded_O,
        CodCodeAbstract[] arrcodBaseDeleted_O,
                                                            //New (and Base) syspaths with duplicated file name.
                                                            //Order by file name and fullpath (in this order).
        SyspathPath[] arrsyspathNewDuplicateFileName_O,
        SyspathPath[] arrsyspathBaseDuplicateFileName_O
        )
    {
                                                            //Prepare sorted tuples (New and Base).
        T3codToSortTuple[] arrt3codNewToCompare = NvsbsolNewVsBaseSolutionComparison.arrt3codGet(
                this.arrcodNewToCompare_Z);
        T3codToSortTuple[] arrt3codBaseToCompare = NvsbsolNewVsBaseSolutionComparison.arrt3codGet(
                this.arrcodBaseToCompare_Z);

                                                            //The strategy will be:
                                                            //1. Process simultaneously both arrt3cod to:
                                                            //1a. Separate duplicated file names. (generate lists of
                                                            //      duplicated file name).
                                                            //1b. Identify file matching by name. (generate part of list
                                                            //      of mathcing files).
                                                            //1c. Generate lists of cod that require more analysis to
                                                            //      match.
                                                            //2. Process all possible combination of the lists of cod
                                                            //      that require more analysis to match.
                                                            //2a. Generate a list of ALL possible combination (only
                                                            //      files with same file extension can match).
                                                            //2b. Sort by Similarity (descending).
                                                            //3. Select the best match, add to list of matching files.
                                                            //3a. Only one match per file.
                                                            //3b. Only select matches with similarity above threshold.
                                                            //4. Generate lists of added and deleted files.

                                                            //Lists to store all information (we don't know length).
        LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodMatchingFiles =
                new LinkedList<NvsbcodNewVsBaseCodeComparison>();
        LinkedList<SyspathPath> lstsyspathNewDuplicateFileName = new LinkedList<SyspathPath>();
        LinkedList<SyspathPath> lstsyspathBaseDuplicateFileName = new LinkedList<SyspathPath>();

        //                                                  //To save pending files.
        LinkedList<CodCodeAbstract> lstcodNewPending = new LinkedList<CodCodeAbstract>();
        LinkedList<CodCodeAbstract> lstcodBasePending = new LinkedList<CodCodeAbstract>();

        Oint ointNew = new Oint(0);
        Oint ointBase = new Oint(0);
        /*WHILE-DO*/
        while (
                                                            //We have info to process
                (ointNew.v < arrt3codNewToCompare.length) || (ointBase.v < arrt3codBaseToCompare.length)
                )
        {
            this.subProcessOneFileName(arrt3codNewToCompare, arrt3codBaseToCompare, ointNew, ointBase,
                    lstnvsbcodMatchingFiles, lstsyspathNewDuplicateFileName, lstsyspathBaseDuplicateFileName,
                    lstcodNewPending, lstcodBasePending);

                                                            //Previous process returned both index on NEXT tuple to
                                                            //      process.
        }

        LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodPossibleMatch = this.lstnvsbcodPossibleMatchGet(
                lstcodNewPending, lstcodBasePending);

        NvsbsolNewVsBaseSolutionComparison.subMatchingBySimilarity(lstnvsbcodPossibleMatch, lstcodNewPending,
                lstcodBasePending, lstnvsbcodMatchingFiles);

                                                            //Prepare output variables.

        arrnvsbcodMatchingFiles_O = (NvsbcodNewVsBaseCodeComparison[]) lstnvsbcodMatchingFiles.toArray();

                                                            //Sort by syspath in New
        String[] arrstrNewFullPath = new String[arrnvsbcodMatchingFiles_O.length];
        for (int intI = 0; intI < arrnvsbcodMatchingFiles_O.length; intI = intI + 1)
        {
            arrstrNewFullPath[intI] =
                    arrnvsbcodMatchingFiles_O[intI].codNew().syspathFile().strFullPath().toLowerCase();
        }
        Tools.sort(arrstrNewFullPath, arrnvsbcodMatchingFiles_O);

                                                            //At this point, pending lists are added and deleted files.
                                                            //Sort each set by syspath
        arrcodNewAdded_O = NvsbsolNewVsBaseSolutionComparison.arrcodSortedByFullPath(lstcodNewPending);
        arrcodBaseDeleted_O = NvsbsolNewVsBaseSolutionComparison.arrcodSortedByFullPath(lstcodBasePending);

                                                            //This info was generated order by file name and syspath,
                                                            //      no additional sort required.
        arrsyspathNewDuplicateFileName_O = (SyspathPath[]) lstsyspathNewDuplicateFileName.toArray();
        arrsyspathBaseDuplicateFileName_O = (SyspathPath[]) lstsyspathBaseDuplicateFileName.toArray();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static T3codToSortTuple[] arrt3codGet(
                                                            //Prepare tuples and sort.

                                                            //arrt3cod, sorted tuple with arrcod info.

        CodCodeAbstract[] arrcodToSort_I
        )
    {
                                                            //To sort cods.
        T3codToSortTuple[] arrt3codGet = new T3codToSortTuple[arrcodToSort_I.length];

        for (int intI = 0; intI < arrcodToSort_I.length; intI = intI + 1)
        {
                                                            //To easy code.
            CodCodeAbstract cod = arrcodToSort_I[intI];

            //                                          //Add it to the arrt3cod.
            arrt3codGet[intI] = new T3codToSortTuple(cod.syspathFile().strName().toLowerCase(),
                    cod.syspathFile().strFullPath().toLowerCase(), cod);
        }

        Arrays.sort(arrt3codGet);

        return arrt3codGet;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subProcessOneFileName(
                                                            //Process ONE filename (this is first step of strategy).
                                                            //1. Process simultaneuly both arrt3cod to:
                                                            //1a. Separate duplicated file names. (generate lists of
                                                            //      duplicated file name).
                                                            //1b. Identify file matching by name. (generate part of list
                                                            //      of mathcing files).
                                                            //1c. Generate lists of cod that require more analysis to
                                                            //      match.
                                                            //this[I], syspathDirectories for New and Base required in
                                                            //      objects nvsbcom.

                                                            //Info to process, sorted by file name and full path
            T3codToSortTuple[] arrt3codNewToCompare_I,
            T3codToSortTuple[] arrt3codBaseToCompare_I,
                                                            //Both index are on NEXT file name, only ONE file name will
                                                            //      be processed.
                                                            //At least one index is not at the end of his array.
                                                            //Advance index (one or both) to NEXT file name after file
                                                            //      name processed.
            Oint ointNew_IO,
            Oint ointBase_IO,
                                                            //If there is a pair of Matching Files that match by name,
                                                            //      that pair is added to the list.
            LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodMatchingFiles_M,
                                                            //If file name to process is duplicated, add files to this
                                                            //      lists.
            LinkedList<SyspathPath> lstsyspathNewDuplicateFileName_M,
            LinkedList<SyspathPath> lstsyspathBaseDuplicateFileName_M,
                                                            //List of pending files.
                                                            //If a file name is not duplicaded and did not match by file
                                                            //      name, more analysis is required.
                                                            //Add this file to the corresponding list.
            LinkedList<CodCodeAbstract> lstcodNewPending_M,
            LinkedList<CodCodeAbstract> lstcodBasePending_M
            )
    {
                                                            //Save file name to process, should be first in order
                                                            //      sequence.
        String strFileNameToProcess = Std.strHIGH_VALUE;
        if (
                                                            //New is lower that selectec file name
                (ointNew_IO.v < arrt3codNewToCompare_I.length) &&
                //(String.Compare(arrt3codNewToCompare_I[ointNew_IO.v].strFileName, strFileNameToProcess) < 0)
                (arrt3codNewToCompare_I[ointNew_IO.v].strFileName.compareTo(strFileNameToProcess) < 0)
                )
        {
            strFileNameToProcess = arrt3codNewToCompare_I[ointNew_IO.v].strFileName;
        }
        if (
                                                            //Base is lower that selectec file name
                (ointBase_IO.v < arrt3codBaseToCompare_I.length) &&
                //(String.Compare(arrt3codBaseToCompare_I[ointBase_IO.v].strFileName, strFileNameToProcess) < 0)
                (arrt3codBaseToCompare_I[ointBase_IO.v].strFileName.compareTo(strFileNameToProcess) < 0)
                )
        {
            strFileNameToProcess = arrt3codBaseToCompare_I[ointBase_IO.v].strFileName;
        }

                                                            //Process NEXT file name, it can be in both sets or, only
                                                            //      in New or in Base.

                                                            //To easy code
        T3codToSortTuple[] arrt3codNew = arrt3codNewToCompare_I;
        T3codToSortTuple[] arrt3codBase = arrt3codBaseToCompare_I;

        /*CASE*/
        if (
                                                            //New exists and should be included in process
                (ointNew_IO.v < arrt3codNew.length) && (arrt3codNew[ointNew_IO.v].strFileName ==
                        strFileNameToProcess) &&
                                                            //Base exists and should be included in process
                        (ointBase_IO.v < arrt3codBase.length) && (arrt3codBase[ointBase_IO.v].strFileName ==
                        strFileNameToProcess)
                )
        {
            this.subProcessOneFileNameInNewAndBase(arrt3codNew, arrt3codBase, ointNew_IO, ointBase_IO,
                    lstnvsbcodMatchingFiles_M, lstsyspathNewDuplicateFileName_M, lstsyspathBaseDuplicateFileName_M);

                                                            //Advance AFTER processed file name
            ointNew_IO.v = ointNew_IO.v + 1;
            ointBase_IO.v = ointBase_IO.v + 1;
        }
        else if (
                                                            //New exists and should be included in process
                (ointNew_IO.v < arrt3codNew.length) && (arrt3codNew[ointNew_IO.v].strFileName ==
                        strFileNameToProcess)
                )
        {
            NvsbsolNewVsBaseSolutionComparison.subProcessOneFileNameInXxx(arrt3codNew, ointNew_IO,
                    lstsyspathNewDuplicateFileName_M, lstcodNewPending_M);

                                                            //Advance AFTER processed file name
            ointNew_IO.v = ointNew_IO.v + 1;
        }
        else
        {
                                                            //Base exists and should be included in process

            NvsbsolNewVsBaseSolutionComparison.subProcessOneFileNameInXxx(arrt3codBase, ointBase_IO,
                    lstsyspathBaseDuplicateFileName_M, lstcodBasePending_M);

                                                            //Advance AFTER processed file name
            ointBase_IO.v = ointBase_IO.v + 1;
        }
        /*END-CASE*/
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subProcessOneFileNameInNewAndBase(
                                                            //Process ONE filename that is both in New and Base.
                                                            //1. Process simultaneuly both arrt3cod to:
                                                            //1a. Separate duplicated file names. (add to lists of
                                                            //      duplicated file name).
                                                            //1b. Identify file matching by file name. (add to matching
                                                            //      files).
                                                            //this[I], syspathDirectories for New and Base required in
                                                            //      objects nvsbcom.

                                                            //Info to process, sorted by file name and full path
            T3codToSortTuple[] arrt3codNew_I,
            T3codToSortTuple[] arrt3codBase_I,
                                                            //Both index are on file name to be processed.
                                                            //Advance both index to LAST tuple in the file name
                                                            //      processed.
            Oint ointNew_IO,
            Oint ointBase_IO,
                                                            //If not duplicated, add pair of Matching Files.
            LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodMatchingFiles_M,
                                                            //If file name to process is duplicated, add files to this
                                                            //      lists.
            LinkedList<SyspathPath> lstsyspathNewDuplicateFileName_M,
            LinkedList<SyspathPath> lstsyspathBaseDuplicateFileName_M
    )
    {
                                                            //To easy code (both sides are the same file name)
        String strFileName = arrt3codNew_I[ointNew_IO.v].strFileName;

        if (
                                                            //Duplicated in New.
            ((ointNew_IO.v + 1) < arrt3codNew_I.length) && (arrt3codNew_I[ointNew_IO.v + 1].strFileName ==
                    strFileName) ||
                                                            //Duplicated in Base.
                    ((ointBase_IO.v + 1) < arrt3codBase_I.length) &&
                            (arrt3codBase_I[ointBase_IO.v + 1].strFileName == strFileName)
            )
        {
                                                            //One side has a duplicated file name, ALL files should be
                                                            //      identified as duplicated

            NvsbsolNewVsBaseSolutionComparison.subDuplicatedFileName(arrt3codNew_I, ointNew_IO,
                    lstsyspathNewDuplicateFileName_M);
            NvsbsolNewVsBaseSolutionComparison.subDuplicatedFileName(arrt3codBase_I, ointBase_IO,
                    lstsyspathBaseDuplicateFileName_M);
        }
        else
        {
                                                            //It is a matching pair

            NvsbcodNewVsBaseCodeComparison nvsbcod = new NvsbcodNewVsBaseCodeComparison(this.syspathDirectoryNew_Z,
                    arrt3codNew_I[ointNew_IO.v].cod, this.syspathDirectoryBase_Z,
                    arrt3codBase_I[ointBase_IO.v].cod);

            lstnvsbcodMatchingFiles_M.add(nvsbcod);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subProcessOneFileNameInXxx(
                                                            //Process ONE filename (in New or Base).
                                                            //Separate duplicated file names. (add to list of
                                                            //      duplicated file name).
                                                            //If not duplicates add to pending.

                                                            //Info to process, sorted by file name and full path
            T3codToSortTuple[] arrt3cod_I,
                                                            //Advance index to LAST tuple in the file name processed.
            Oint oint_IO,
                                                            //If file name to process is duplicated, add files to this
                                                            //      list.
            LinkedList<SyspathPath> lstsyspathDuplicateFileName_M,
                                                            //If not duplicated, add files to this list.
            LinkedList<CodCodeAbstract> lstcodPending_M
    )
    {
                                                            //To easy code
        String strFileName = arrt3cod_I[oint_IO.v].strFileName;

        if (
                                                            //Duplicated.
                ((oint_IO.v + 1) < arrt3cod_I.length) && (arrt3cod_I[oint_IO.v + 1].strFileName == strFileName)
                )
        {
            NvsbsolNewVsBaseSolutionComparison.subDuplicatedFileName(arrt3cod_I, oint_IO,
                    lstsyspathDuplicateFileName_M);
        }
        else
        {
            lstcodPending_M.add(arrt3cod_I[oint_IO.v].cod);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subDuplicatedFileName(
                                                            //Process ONE duplicated file name.
                                                            //Add ALL file with the same file name to duplicated list.

                                                            //Info to process, sorted by file name and full path
            T3codToSortTuple[] arrt3cod_I,
                                                            //Index is on file name to process.
                                                            //Advance index to LAST duplicated file name.
            Oint oint_IO,
                                                            //Add files to this list.
            LinkedList<SyspathPath> lstsyspathDuplicateFileName_M
    )
    {
                                                            //To easy code
        String strFileName = arrt3cod_I[oint_IO.v].strFileName;

        /*WHILE-DO*/
        while (
            (oint_IO.v < arrt3cod_I.length) &&
                    (arrt3cod_I[oint_IO.v].strFileName == strFileName)
            )
        {
                                                            //Duplicated file name
            lstsyspathDuplicateFileName_M.add(arrt3cod_I[oint_IO.v].cod.syspathFile());

            oint_IO.v = oint_IO.v + 1;
        }

                                                            //Return position to LAST in duplicated set
        oint_IO.v = oint_IO.v - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodPossibleMatchGet(
                                                            //Generate all possible matches.
                                                            //This is, the files that are not duplicated and don't have
                                                            //      a direct match between New and Base (by file name).
                                                            //this[I], syspathDirectories for New and Base required in
                                                            //      objects nvsbcom.

                                                            //Lists of the files that don't have a direct match by file
                                                            //      name.
            LinkedList<CodCodeAbstract> lstcodNewPending_M,
            LinkedList<CodCodeAbstract> lstcodBasePending_M
    )
    {
                                                            //Contains all the possible comparisons between remaining
                                                            //      cods in New and Base.
        LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodPossibleMatchGet =
                new LinkedList<NvsbcodNewVsBaseCodeComparison>();

                                                            //Make all the possible matches.
        for (CodCodeAbstract codNew : lstcodNewPending_M)
        {
            for (CodCodeAbstract codBase : lstcodBasePending_M)
            {
                if (
                                                            //They have the same file extension. Matches are only made
                                                            //      between cods with the same file extension.
                        codNew.syspathFile().strFileExtension().toLowerCase() ==
                                codBase.syspathFile().strFileExtension().toLowerCase()
                        )
                {
                    NvsbcodNewVsBaseCodeComparison nvsbcod = new NvsbcodNewVsBaseCodeComparison(
                            this.syspathDirectoryNew(), codNew, this.syspathDirectoryBase(), codBase);
                    lstnvsbcodPossibleMatchGet.add(nvsbcod);
                }
            }
        }

                                                            //Sort all the possible comparisons from the ones with the
                                                            //      most similarity to the ones with least similarity.
        NvsbcodNewVsBaseCodeComparison[] arrnvsbcodPossibleMatch =
                (NvsbcodNewVsBaseCodeComparison[]) lstnvsbcodPossibleMatchGet.toArray();
        Double[] arrnumSimilarityComplement = new Double[arrnvsbcodPossibleMatch.length];
        for (Integer intI = 0; intI < arrnvsbcodPossibleMatch.length; intI = intI + 1)
        {
            arrnumSimilarityComplement[intI] = 1.0 - arrnvsbcodPossibleMatch[intI].numSimilarity();
        }
        Tools.sort(arrnumSimilarityComplement, arrnvsbcodPossibleMatch);

                                                            //Back to a list in order to be able to remove elements.
        return new LinkedList<NvsbcodNewVsBaseCodeComparison>(Arrays.asList(arrnvsbcodPossibleMatch));
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subMatchingBySimilarity(
                                                            //Process Possible Matches to find similarities.

                                                            //All possible matches.
                                                            //After process, this list will be shorter, but this list
                                                            //      is not needed anymore.
            LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodPossibleMatch_M,
                                                            //All matched files will be removed from this lists.
                                                            //After this method, the only cods in this lists will be the
                                                            //      ones had not a match.
                                                            //These file will be New added and Base deleted.
            LinkedList<CodCodeAbstract> lstcodNewPending_M,
            LinkedList<CodCodeAbstract> lstcodBasePending_M,
                                                            //List of cods that have a match between New and Base.
                                                            //Matches will be added by code similarity.
            LinkedList<NvsbcodNewVsBaseCodeComparison> lstnvsbcodMatchingFiles_M
    )
    {
                                                            //Process all matches out of possible matches
        /*WHILE-DO*/
        while (
            //                                              //There are still elements.
                (lstnvsbcodPossibleMatch_M.size() > 0) &&
                                                            //It has at least the minimum similarity needed to be
                                                            //      considered a match.
                                                            //Note that it is the first item because it is sorted from
                                                            //      highest similarity to least.
                                                            //When an element does not have the necessary similarity, it
                                                            //      means that all the remaining ones will not have it
                                                            //      either.
                (lstnvsbcodPossibleMatch_M.get(0).numSimilarity() >=
                        NvsbsolNewVsBaseSolutionComparison.numMINIMUM_SIMILARITY_THRESHOLD)
                )
        {
                                                            //This is a match by similarity, add to list.
            lstnvsbcodMatchingFiles_M.add(lstnvsbcodPossibleMatch_M.get(0));

                                                            //Needs to clean up codNew and codBase that has already
                                                            //      match.
                                                            //1. Remove Matching Possibility not possible any more.
                                                            //2. Remove cods form pending lists.

                                                            //To easy code
            CodCodeAbstract codNewMatched = lstnvsbcodPossibleMatch_M.get(0).codNew();
            CodCodeAbstract codBaseMatched = lstnvsbcodPossibleMatch_M.get(0).codBase();

                                                            //Remove from list of possible matches.
                                                            //We process backwards to have a better undestanding of
                                                            //      whats going on as we remove element form list.
            int intToReview = lstnvsbcodPossibleMatch_M.size() - 1;
            /*WHILE-DO*/
            while (
                                                            //Exist elements to review
                    intToReview >= 0
                    )
            {
                                                            //To easy code
                NvsbcodNewVsBaseCodeComparison nvsbcod = lstnvsbcodPossibleMatch_M.get(intToReview);

                if (
                                                            //The comparison contains a cod that was matched.
                    (nvsbcod.codNew() == codNewMatched) || (nvsbcod.codBase() == codBaseMatched)
                    )
                {
                                                            //Remove the element that contains a cod that was matched.
                                                            //Notice that list will be shorter
                    lstnvsbcodPossibleMatch_M.remove(intToReview);
                }

                                                            //Advance (backward) to next element to review
                intToReview = intToReview - 1;
            }

                                                            //Remove from the list of pending.
            //lstcodNewPending_M.RemoveAt(lstcodNewPending_M.indexOf(codNewMatched));
            //lstcodBasePending_M.RemoveAt(lstcodBasePending_M.indexOf(codBaseMatched));
            lstcodNewPending_M.remove(lstcodNewPending_M.indexOf(codNewMatched));
            lstcodBasePending_M.remove(lstcodBasePending_M.indexOf(codBaseMatched));
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static CodCodeAbstract[] arrcodSortedByFullPath(
                                                            //Convert to array and sort by full path.

                                                            //arrcod, sorted by full path.

            LinkedList<CodCodeAbstract> lstcodToConvertAndSort_I
    )
    {
        CodCodeAbstract[] arrcodSortedByFullPath =
                lstcodToConvertAndSort_I.toArray(new CodCodeAbstract[lstcodToConvertAndSort_I.size()]);

                                                            //Sort by syspath
        String[] arrstrFullPath = new String[arrcodSortedByFullPath.length];
        for (int intI = 0; intI < arrcodSortedByFullPath.length; intI = intI + 1)
        {
            arrstrFullPath[intI] = arrcodSortedByFullPath[intI].syspathFile().strFullPath().toLowerCase();
        }
        Tools.sort(arrstrFullPath, arrcodSortedByFullPath);

        return arrcodSortedByFullPath;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT CONSTRUCTORS*/

    /*TASK Nvsbsol3 subGetInfoFromDirectory*/
    //------------------------------------------------------------------------------------------------------------------
    private void subGetInfoFromDirectory(
                                                            //Del diretorio accesa todos los file del arbol

                                                            //Files cuya technología se puede comparar. Ordenados por
                                                            //      el syspath.
        CodCodeAbstract[] arrcodToCompare_O,
                                                            //Files excluidos. Ordenados por el syspath.
        SyspathPath[] arrsyspathExcluded_O,
                                                            //Syspath del directorio (New or Base) que será procesado
        SyspathPath syspathDirectory_I
        )
    {
                                                            //The strategy will be:
                                                            //1. Extract all sysfile info from directory.
                                                            //2. From that info, determine ToCompare and Excluded info.


                                                            //1. Extract all sysfile info from directory.

                                                            //Get all the files from this directory and the possible
                                                            //      deeper levels of directories.
        //List<FileInfo> lstsysfileAllFiles = new List<FileInfo>();
        LinkedList<File> lstsysfileAllFiles = new LinkedList<File>();

        //DirectoryInfo sysdirToGetFiles = Sys.sysdirNew(syspathDirectory_I);
        File sysdirToGetFiles = Sys.sysdirNew(syspathDirectory_I);
        NvsbsolNewVsBaseSolutionComparison.subGetAllFilesFromDirectory(sysdirToGetFiles, lstsysfileAllFiles);

        //FileInfo[] arrsysfileAllFiles = lstsysfileAllFiles.toArray();
        File[] arrsysfileAllFiles = lstsysfileAllFiles.toArray(new File[lstsysfileAllFiles.size()]);
                                                            //Order array
        String[] arrstrFullPath = new String[arrsysfileAllFiles.length];
        for (Integer intI = 0; intI < arrsysfileAllFiles.length; intI = intI + 1)
        {
            arrstrFullPath[intI] = Sys.syspathGet(arrsysfileAllFiles[intI]).strFullPath();
        }
        Tools.sort(arrstrFullPath, arrsysfileAllFiles);

                                                            //2. From that info, determine ToCompare and Excluded info.

        LinkedList<CodCodeAbstract> lstcodToCompare = new LinkedList<CodCodeAbstract>();
        LinkedList<SyspathPath> lstsyspathExcluded = new LinkedList<SyspathPath>();

        for (File sysfile : arrsysfileAllFiles)
        {
                                                            //Get technology to this file
            SyspathPath syspathFile = Sys.syspathGet(sysfile);
            //T4techInfoTuple t4tech = Tech.t4techGet(syspathFile.strFileExtension());
            T4techInfoTuple t4tech = Tech.t4techGet(syspathFile.strFileExtension());

            if (
                                                            //It is a valid technology for a cod.
                    t4tech != null
                    )
            {
                                                            //Get codDUMMY to this technology
                CodCodeAbstract codDUMMY = Tech.codxxGet(t4tech.techtech);

                //CodCodeAbstract cod = codDUMMY.codxxNew(null, syspathFile);
                CodCodeAbstract cod = null;

                lstcodToCompare.add(cod);
            }
            else
            {
                                                            //Type of file not included in Software automation
                lstsyspathExcluded.add(syspathFile);
            }
        }

                                                            //These arrays are already order by syspath
        //File[] arrsysfileAllFiles = lstsysfileAllFiles.toArray(new File[lstsysfileAllFiles.size()]);
        arrcodToCompare_O = lstcodToCompare.toArray(new CodCodeAbstract[lstcodToCompare.size()]);
        arrsyspathExcluded_O = lstsyspathExcluded.toArray(new SyspathPath[lstsyspathExcluded.size()]);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subGetAllFilesFromDirectory(
                                                            //Gets all the files from a Directory, including those files
                                                            //      that are in a deeper level (directories inside the
                                                            //      Directory).
                                                            //Ignora archivos y directorios especificados para ser
                                                            //      ignorados.
                                                            //Recursive method.

                                                            //Directory from which to extract files.
            //DirectoryInfo sysdirToGetFiles_I,
            File sysdirToGetFiles_I,
                                                            //List on which all the files will be added.
            LinkedList<File> lstsysfileAllFiles_M
    )
    {
                                                            //Get and add all the files in this level of directory.
        File[] arrsysfileFilesInDirectory = Sys.arrsysfileGetFiles(sysdirToGetFiles_I);

                                                            //Reviso uno por uno para ignorar los archivos que sea
                                                            //      necesario ignorar
        for (File sysfile : arrsysfileFilesInDirectory)
        {
            if (
                                                            //No se debe ignorar
                    Arrays.binarySearch(NvsbsolNewVsBaseSolutionComparison.arrstrFILE_TO_IGNORE,
                            Sys.syspathGet(sysfile).strName().toLowerCase()) < 0
                    )
            {
                lstsysfileAllFiles_M.add(sysfile);
            }
        }

                                                            //Get and add all the files in the directories contained in
                                                            //      this level of directory.
        File[] arrsysdirContainedInDirectory = Sys.arrsysdirGetDirectories(sysdirToGetFiles_I);
        for (File sysdirContained : arrsysdirContainedInDirectory)
        {
                                                            //Reviso para ignorar los directorios que sea necesario
                                                            //      ignorar
            if (
                                                            //No se debe ignorar
                    Arrays.binarySearch(NvsbsolNewVsBaseSolutionComparison.arrstrDIRECTORY_TO_IGNORE,
                            Sys.syspathGet(sysdirContained).strName().toLowerCase()) < 0
                    )
            {
                                                            //"Enter" into deeper levels of the directories and get
                                                            //      files in that level. Recursive call.
                NvsbsolNewVsBaseSolutionComparison.subGetAllFilesFromDirectory(
                        sysdirContained, lstsysfileAllFiles_M);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    /*TASK Nvsbsol4 subCompareReport*/
    //------------------------------------------------------------------------------------------------------------------
    private void subCompareReport(
                                                            //Produce un log del resultado de la comparación de
                                                            //      soluciones y un directorio con todos los logs del
                                                            //      resultado de la comparación de cods. (solo cuando
                                                            //      hubo cambios).
                                                            //El log resumen tiene la siguiente forma (ejemplo):

     /*Inicia ejemplo
																	   (file)Summary: QEnabler CS 1703231522 Summary.log
															(directory)Compare Logs: QEnabler CS 1703231522 Compare logs
(blank line)
New: \\Mac\Home\Desktop\svn\QEnabler_CS, 100 files, 15,430 lines (and 5 excluded files and 8 duplicated files)
Base: \\Mac\Home\Desktop\svn\QEnabler_CS 170301, 97 files, 13,450 lines (and 7 excluded files and 6 duplicated files)
↑1,980 lines inserted or added, ↓580 lines removed or deleted, 12,870 equal lines, similarity: 78.0%
(blank line)
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●● WARNING: 14 DUPLICATED FILES ●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
   ▸▸▸▸▸▸▸▸▸▸ New (8 duplicated files):
 1 .\dirA\dirAA\fileAA.cs
 2 .\dirA\dirAB\fileAA.cs
 3 .\dirB\dirBB\fileBB.cs
 4 .\dirC\dirCC\fileCC.cs
 5 .\dirC\dirCB\fileCC.cs
 6 .\dirC\dirCCC\fileCC.cs
 7 .\dirD\dirDD\fileDD.cs
 8 .\dirD\dirDB\fileDD.cs
   ▸▸▸▸▸▸▸▸▸▸ Base (6 duplicated files):
 1 .\dirA\dirAA\fileAA.cs
 2 .\dirB\dirBB\fileBB.cs
 3 .\dirB\dirBA\fileBB.cs
 4 .\dirB\dirBC\fileBB.cs
 5 .\dirE\dirEE\fileEE.cs
 6 .\dirE\dirEB\fileEE.cs
(blank line)
●●●●●●●●●●●●●● COMPARISONS: 10 MODIFIED FILES and 80 unchanged files (20 unchanged, renamed or relocated) ●●●●●●●●●●●●●●
 1 {usar método subLogFileSummary}
..........
..........
..........
30 {usar método subLogFileSummary}

(blank line)
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●● 10 ADDED FILES (in New) ●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
 1 .\dirA\dirAA\fileAAA.cs
 2 .\dirA\dirAA\fileBBB.cs
 3 .\dirA\dirAA\fileCCC.cs
 4 .\dirA\dirAA\fileDDD.cs
 5 .\dirA\dirAA\fileEEE.cs
 6 .\dirA\dirAA\fileFFF.cs
 7 .\dirA\dirAA\fileGGG.cs
 8 .\dirA\dirAA\fileHHH.cs
 9 .\dirA\dirAA\fileIII.cs
10 .\dirA\dirAA\fileJJJ.cs
(blank line)
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●● 7 DELETED FILES (from Base) ●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
 1 .\dirA\dirAA\fileAAAA.cs
 2 .\dirA\dirAA\fileBBBB.cs
 3 .\dirA\dirAA\fileCCCC.cs
 4 .\dirA\dirAA\fileDDDD.cs
 5 .\dirA\dirAA\fileEEEE.cs
 6 .\dirA\dirAA\fileFFFF.cs
 7 .\dirA\dirAA\fileGGGG.cs
(blank line)
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●● 9 EXCLUDED FILES ●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
   ▸▸▸▸▸▸▸▸▸▸ New (4 excluded files):
 1 .\dirA\dirAA\fileA.ppt
 2 .\dirA\dirAA\fileB.ppt
 3 .\dirA\dirAA\fileC.docx
 4 .\dirA\dirAA\fileJ.ppt
   ▸▸▸▸▸▸▸▸▸▸ Base (5 excluded files):
 1 .\dirA\dirAA\fileA.ppt
 2 .\dirA\dirAA\fileB.ppt
 3 .\dirA\dirAA\fileC.docx
 4 .\dirA\dirAA\fileE.ppt
 5 .\dirA\dirAA\fileF.ppt
(blank line)
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●● END OF SUMMARY ●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
		Termina ejemplo*/

                                                            //Notas:
                                                            //1. Las líneas 1 y 2 se muestran alineadas en 120
                                                            //      caracteres.
                                                            //2. La cantidad de líneas y archivos se reportan en formato
                                                            //      #,##0.
                                                            //3. En New y Base, el número de files es la suma de los
                                                            //      archivos emparejados con los added o deleted
                                                            //      (dependiendo si es New o Base).
                                                            //4. Los encabezados ●●●...● {texto} ●●●...● se muestran
                                                            //      centrados en 120 caracteres.
                                                            //5. La sección de Warning solamente se despliega si existen
                                                            //      archivos duplicados.
                                                            //8. Todas las lista de información tiene la forma:
                                                            //#0 texto.
                                                            //(donde #0 es la numeración alineada a la derecha en un
                                                            //      espacio suficiente para la cantidad máxima en la
                                                            //      lista mayor (son 7 listas), en este ejemplo son 2
                                                            //      caracteres para contener el número 30).
                                                            //this[I], Objects info.

                                                            //Nombre de la aplicación (solución), Ej. "QEnabler CS".
                                                            //No debe tener blancos en exceso y deben ser caracteres que
                                                            //      puedan ser incluídos en un syspath.
			String strApplicationName_I,
			                                                //Full path del directorio donde se genera el reporte, de la
			                                                //      siguiente forma:
			                                                //Resumen: "{name} {ts} Summary.log" el cual contedrá el
			                                                //      reporte aquí descrito, donde {ts} tiene el formato
			                                                //      "yyMMddhhmm" y se tomará de Now.
			                                                //Directorio: "{name} {ts} Compare Logs", el cual contendra
			                                                //      un log por comparación que tiene diferencias.
			                                                //El nombre de estos logs sera:
			                                                //"{name} {file name} {ts}.compare", donde {file name} es el
			                                                //      nombre del archivo en New.
			                                                //Este directorio debe existir, puede tener o no
			                                                //      información.
			                                                //Si el archivo y/o directorio que de deben producir ya
			                                                //      exisen (lo cual es muy poco probable) al {ts} se
			                                                //      cambia a {ts}-n donde n será 1, 2, ..., hasta donde
			                                                //      ya no se dupliquen.
			SyspathPath syspathDirectoryForReport_I,
			                                                //Cantidad de líneas que deben ser incluídas antes y después
			                                                //      de las líneas insertadas y/o removidas.
			                                                //Puede ser 0 (no añade contexto), típicamente serán de 3 a
			                                                //      máximo 10), sin embargo podrían ser muchas.
			int intContextLines_I
			)
    {
        //String strTsNow = DateTime.Now.ToString("yyMMddHHmm");
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
        Date date = new Date();
        String strTsNow = dateFormat.format(date);

        //SyspathPath syspathFileSummary;
        //SyspathPath syspathDirectoryCompares;
        Oobject<SyspathPath> osyspathFileSummary = new Oobject<SyspathPath>();
        Oobject<SyspathPath> osyspathDirectoryCompares = new Oobject<SyspathPath>();
        NvsbsolNewVsBaseSolutionComparison.subfunGetSyspathsForResult(osyspathFileSummary,
            osyspathDirectoryCompares, strApplicationName_I, syspathDirectoryForReport_I, strTsNow);

                                                            //Generate the file with the summary.
        this.subGenerateSummary(osyspathFileSummary.v, osyspathDirectoryCompares.v);

                                                            //Generate the directory with the compare logs.
        this.subGenerateCompares(strApplicationName_I, strTsNow, osyspathDirectoryCompares.v, intContextLines_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subfunGetSyspathsForResult(
        //                                                  //Determina los syspath para los resultados.
        //                                                  //Verifica que no existan, si en necesario añade -1, -2, ...

        //                                                  //{name} {ts} Summary.log
        Oobject<SyspathPath> osyspathFileSummary_O,
        //                                                  //{name} {ts} Compare Logs
        Oobject<SyspathPath> osyspathDirectoryCompares_O,
        //                                                  //Nombre de la aplicación (solución). {name}.
        String strApplicationName_I,
        //                                                  //Full path del directorio donde se genera el reporte.
        SyspathPath syspathDirectoryForReport_I,
        //                                                  //Now. {ts}.
        String strTsNow_I
        )
    {
        if (
                                                            //Tiene blancos en exceso
            strApplicationName_I != Tools.strTrimExcel(strApplicationName_I)
            )
            Tools.subAbort(Tes2.strTo(strApplicationName_I, "strApplicationName_I") + " contains too many spaces");

        SyspathPath syspathApplicationName = new SyspathPath(strApplicationName_I);
        if (
                                                            //No puede ser parte de un syspath
            !syspathApplicationName.boolIsValid()
            )
            Tools.subAbort(Tes2.strTo(syspathApplicationName, TestoptionEnum.SHORT) +
                " contains characters not valid in a syspath");

        if (
            !syspathDirectoryForReport_I.boolIsDirectory()
            )
            Tools.subAbort(Tes2.strTo(syspathDirectoryForReport_I, "syspathDirectoryRoot_I") +
                    " is not a directory");

                                                            //File and Directory in which the result of the comparison
                                                            //      of the solutions will be.
        String strApplicationNameAndTs = strApplicationName_I + " " + strTsNow_I;
        osyspathFileSummary_O.v = syspathDirectoryForReport_I.syspathAddName(strApplicationNameAndTs + " Summary.log");
        osyspathDirectoryCompares_O.v = syspathDirectoryForReport_I.syspathAddName(
            strApplicationNameAndTs + " Compare Logs");

                                                            //If taken, generate {ts}-n
        int intN = 1;
        /*WHILE-DO*/
        while (
                osyspathFileSummary_O.v.boolExists() ||
                osyspathDirectoryCompares_O.v.boolExists()
                )
        {
            osyspathFileSummary_O.v = syspathDirectoryForReport_I.syspathAddName(
            strApplicationNameAndTs + "-" + intN + " Summary.log");
            osyspathDirectoryCompares_O.v = syspathDirectoryForReport_I.syspathAddName(
            strApplicationNameAndTs + "-" + intN + " Compare Logs");

            intN = intN + 1;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subGenerateSummary(
                                                            //Generate summary file (.logs).
                                                            //In this section, the only cods that are going to be
                                                            //      included are the ones that:
                                                            //1. Have differences (Similarity < 1.0).
                                                            //2. Were renamed and/or relocated.

                                                            //File to generate summary
        SyspathPath syspathFileSummary_I,
                                                            //Directory to contain compares (only to obtain its path)
        SyspathPath syspathDirectoryCompares_I
        )
    {
                                                            //Create writer for summary
        //FileInfo sysfileSummary = Sys.sysfileNew(syspathFileSummary_I);
        File sysfileSummary = Sys.sysfileNew(syspathFileSummary_I);
        Oobject<File> osysfileSummary = new Oobject<File>(sysfileSummary);
        Oobject<PrintWriter> osysswSummary = new Oobject<PrintWriter>();
        //StreamWriter sysswSummary = Sys.sysswNewWriteTextFile(sysfileSummary);

        this.subLogHeading(syspathFileSummary_I, syspathDirectoryCompares_I, osysswSummary);

                                                            //Count compared files:
                                                            //a. Changed.
                                                            //b. Unchanged but renamed and/or relocated.
        Oint ointChanged = new Oint();
        Oint ointUnchangedButRenamedAndOrRelocated = new Oint();
        this.subfunGetChangedAndUnchangedRenamedRelocatedCount(ointChanged, ointUnchangedButRenamedAndOrRelocated);

                                                            //Compute chars required for items number.
        int intMaxArrLength = Tools.intMax(this.arrsyspathNewDuplicateFileName().length,
        this.arrsyspathBaseDuplicateFileName().length, ointChanged.v + ointUnchangedButRenamedAndOrRelocated.v,
        this.arrcodNewAdded().length, this.arrcodBaseDeleted().length, this.arrsyspathNewExcluded().length,
        this.arrsyspathBaseExcluded().length);
        //int intItemNumberTextLength = intMaxArrLength.ToString().Length;
        int intItemNumberTextLength = Integer.toString(intMaxArrLength).length();

        this.subLogDuplicatedSection(osysswSummary, intItemNumberTextLength);

        this.subLogComparisonsSection(ointChanged.v, ointUnchangedButRenamedAndOrRelocated.v, osysswSummary,
                intItemNumberTextLength);

        this.subLogAddedSection(osysswSummary, intItemNumberTextLength);

        this.subLogDeletedSection(osysswSummary, intItemNumberTextLength);

        this.subLogExcludedSection(osysswSummary, intItemNumberTextLength);

                                                            //Log end of summary
        Sys.subWriteLine("", osysswSummary);
        String strEndInfo = Tools.strCenter(" END OF SUMMARY ", 120, '●', '●');
        Sys.subWriteLine(strEndInfo, osysswSummary);

                                                            //Should clean memory.
        //sysswSummary.Dispose();
        osysswSummary = null;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subLogHeading(
                                                            //Log Heading.
                                                            //this[I], object info

        SyspathPath syspathFileSummary_I,
        SyspathPath syspathDirectoryCompares_I,
        //StreamWriter sysswSummary_M
        Oobject<PrintWriter> sysswSummary_M
        )
    {
                                                            //First 2 lines identify solution comparison reports.

        //String strHeadingSummaryInfo = ("(file)Summary: " + syspathFileSummary_I.strName()).PadLeft(120);
        //Sys.subWriteLine(strHeadingSummaryInfo, sysswSummary_M);

        String strHeadingSummaryInfo =
                Tools.strPadLeft(("(file)Summary: " + syspathFileSummary_I.strName()), 120);
        Sys.subWriteLine(strHeadingSummaryInfo, sysswSummary_M);

        //String strHeadingCompareLogsInfo =
        //("(directory)Compare Logs: " + syspathDirectoryCompares_I.strName()).PadLeft(120);
        //Sys.subWriteLine(strHeadingCompareLogsInfo, sysswSummary_M);

        String strHeadingCompareLogsInfo =
                Tools.strPadLeft(("(directory)Compare Logs: " + syspathDirectoryCompares_I.strName()), 120);
        Sys.subWriteLine(strHeadingCompareLogsInfo, sysswSummary_M);

        Sys.subWriteLine("", sysswSummary_M);

                                                            //Next 3 lines of the report are a "summary" of the
                                                            //      comparison between the solutions.

        /*
        String strHeadingNewInfo = "Base: " + this.syspathDirectoryNew().strFullPath() + ", " +
        (this.arrnvsbcodMatchingFiles().length + this.arrcodNewAdded().length).ToString("#,##0") + " files, " +
        (this.intLinesEqual() + this.intLinesInserted).ToString("#,##0") + " lines (and " +
        this.arrsyspathNewExcluded().length.ToString("#,##0") + " excluded files and " +
        this.arrsyspathNewDuplicateFileName().length.ToString("#,##0") + " duplicated files)";
        Sys.subWriteLine(strHeadingNewInfo, sysswSummary_M); */

        DecimalFormat dsstrHeadingNewInfo = new DecimalFormat("#,##0");
        String strHeadingNewInfo = "Base: " + this.syspathDirectoryNew().strFullPath() + ", " +
                dsstrHeadingNewInfo.format(
                (this.arrnvsbcodMatchingFiles().length + this.arrcodNewAdded().length)) + " files, " +
                dsstrHeadingNewInfo.format(
                (this.intLinesEqual() + this.intLinesInserted())) + " lines (and " +
                dsstrHeadingNewInfo.format(this.arrsyspathNewExcluded().length) + " excluded files and " +
                dsstrHeadingNewInfo.format(this.arrsyspathNewDuplicateFileName().length) + " duplicated files)";

        Sys.subWriteLine(strHeadingNewInfo, sysswSummary_M);

        DecimalFormat dfstrHeadingBaseInfo = new DecimalFormat("#,##0");
        String strHeadingBaseInfo = "New: " + this.syspathDirectoryBase().strFullPath() + ", " +
                dfstrHeadingBaseInfo.format(
        (this.arrnvsbcodMatchingFiles().length + this.arrcodBaseDeleted().length)) + " files, " +
                dfstrHeadingBaseInfo.format((this.intLinesEqual() + this.intLinesRemoved())) + " lines (and " +
                dfstrHeadingBaseInfo.format(this.arrsyspathBaseExcluded().length) + " excluded files and " +
                dfstrHeadingBaseInfo.format(this.arrsyspathBaseDuplicateFileName().length) + " duplicated files)";
        Sys.subWriteLine(strHeadingBaseInfo, sysswSummary_M);

        DecimalFormat dfstrHeadingCompareInfo = new DecimalFormat("#,##0");
        DecimalFormat dfstrNumSimilarityPercentage = new DecimalFormat("0.0%");
        String strHeadingCompareInfo = "↑" +
                dfstrHeadingCompareInfo.format(this.intLinesInserted()) +
                dfstrHeadingCompareInfo.format(" lines inserted or added, ↓" + this.intLinesRemoved()) +
                dfstrHeadingCompareInfo.format(
                " lines removed or deleted, " + this.intLinesEqual()) + " equal lines, similarity: " +
                dfstrNumSimilarityPercentage.format(this.numSimilarity());
        Sys.subWriteLine(strHeadingCompareInfo, sysswSummary_M);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subfunGetChangedAndUnchangedRenamedRelocatedCount(
                                                            //Compute Files:
                                                            //a. Changed.
                                                            //b. Unchanged but renamed and/or relocated.
                                                            //this[I], object info.

                                                            //Count.
        Oint ointChanged_O,
        Oint ointUnchangedButRenamedAndOrRelocated_O
        )
    {
                                                            //To easy code
        int intNewLength = this.syspathDirectoryNew().strFullPath().length();
        int intBaseLength = this.syspathDirectoryBase().strFullPath().length();

        ointChanged_O.v = 0;
        ointUnchangedButRenamedAndOrRelocated_O.v = 0;

        for (NvsbcodNewVsBaseCodeComparison nvsbcod : this.arrnvsbcodMatchingFiles())
        {
            /*CASE*/
            if (
                nvsbcod.numSimilarity() < 1.0
                )
            {
                ointChanged_O.v = ointChanged_O.v + 1;
            }
            else if (
                                                            //Has been renamed or relocated
            nvsbcod.codNew().syspathFile().strFullPath().substring(intNewLength) !=
                    nvsbcod.codBase().syspathFile().strFullPath().substring(intBaseLength)
            )
            {
                ointUnchangedButRenamedAndOrRelocated_O.v = ointUnchangedButRenamedAndOrRelocated_O.v + 1;
            }
            else
            {
                                                            //DO NOT COUNT
            }
            /*END-CASE*/
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subLogDuplicatedSection(
                                                            //Log Duplicated files, optional.
                                                            //this[I], object info

        //StreamWriter sysswSummary_M,
        Oobject<PrintWriter>  sysswSummary_M,
        int intItemNumberTextLength_I
        )
    {
                                                            //WARNING section is only logged if there are duplicated
                                                            //      files.

        if (
                                                            //There are duplicated files.
            (this.arrsyspathNewDuplicateFileName().length > 0) ||
            (this.arrsyspathBaseDuplicateFileName().length > 0))
        {
            Sys.subWriteLine("", sysswSummary_M);

            int intDuplicated = this.arrsyspathNewDuplicateFileName().length +
            this.arrsyspathBaseDuplicateFileName().length;

            //String strTextToCenter = " WARNING: " + intDuplicated.ToString("#,##0") + " DUPLICATED FILES ";

            DecimalFormat dfintDuplicated = new DecimalFormat("#,##0");
            String strTextToCenter = " WARNING: " + dfintDuplicated.format(intDuplicated) + " DUPLICATED FILES ";

            String strHeadingInfo = Tools.strCenter(strTextToCenter, 120, '●', '●');
            Sys.subWriteLine(strHeadingInfo, sysswSummary_M);
            DecimalFormat dfarrsyspathNewDuplicateFileName = new DecimalFormat("#,##0");
            if (
                this.arrsyspathNewDuplicateFileName().length > 0
                )
            {
                //String strNewInfo = "".PadLeft(intItemNumberTextLength_I) + " " + "".PadLeft(10, '▸') + " New (" +
                //this.arrsyspathNewDuplicateFileName().length.ToString("#,##0") + " duplicated files):";

                String strNewInfo = Tools.strPadLeft(
                        (" " + Tools.strPadLeft(("▸"), 10) + " New (" +
                        dfarrsyspathNewDuplicateFileName.format(
                        this.arrsyspathNewDuplicateFileName().length) + " duplicated files):"),
                        intItemNumberTextLength_I );
                Sys.subWriteLine(strNewInfo, sysswSummary_M);

                NvsbsolNewVsBaseSolutionComparison.subLogItemsArrsyspath(this.arrsyspathNewDuplicateFileName(),
                this.syspathDirectoryNew(), sysswSummary_M, intItemNumberTextLength_I);
            }

            if (
                this.arrsyspathBaseDuplicateFileName().length > 0
                )
            {
                String strBaseInfo = Tools.strPadLeft(
                        (" " + Tools.strPadLeft(("▸"), 10) + " Base (" +
                                dfarrsyspathNewDuplicateFileName.format(
                this.arrsyspathBaseDuplicateFileName().length) + " duplicated files):"),
                        intItemNumberTextLength_I );

                Sys.subWriteLine(strBaseInfo, sysswSummary_M);

                NvsbsolNewVsBaseSolutionComparison.subLogItemsArrsyspath(this.arrsyspathBaseDuplicateFileName(),
                this.syspathDirectoryBase(), sysswSummary_M, intItemNumberTextLength_I);
            }
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subLogComparisonsSection(
                                                            //Log comparison.
                                                            //this[I], object info

                                                            //Count.
            int intChanged_I,
            int intUnchangedButRenamedAndOrRelocated_I,
            //StreamWriter sysswSummary_M,
            Oobject<PrintWriter>  sysswSummary_M,
            int intItemNumberTextLength_I
        )
    {
        Sys.subWriteLine("", sysswSummary_M);

        DecimalFormat dfstrTextToCenter = new DecimalFormat("#,##0");
        String strTextToCenter = " COMPARISONS: " +
                dfstrTextToCenter.format(intChanged_I) + " MODIFIED FILES and " +
                dfstrTextToCenter.format((this.arrnvsbcodMatchingFiles().length - intChanged_I)) +
                dfstrTextToCenter.format(" unchanged files (" + intUnchangedButRenamedAndOrRelocated_I) +
                " unchanged, renamed or relocated) ";

        String strHeadingInfo = Tools.strCenter(strTextToCenter, 120, '●', '●');
        Sys.subWriteLine(strHeadingInfo, sysswSummary_M);

                                                            //To easy code
        int intNewLength = this.syspathDirectoryNew().strFullPath().length();
        int intBaseLength = this.syspathDirectoryBase().strFullPath().length();

        int intItem = 0;
        for (NvsbcodNewVsBaseCodeComparison nvsbcod : this.arrnvsbcodMatchingFiles())
        {
            intItem = intItem + 1;

                                                            //The rest of the path name of each file after the root.
                                                            //Ej. \some directory\some file
            String strRestOfPathNameNew = nvsbcod.codNew().syspathFile().strFullPath().substring(intNewLength);
            String strRestOfPathNameBase = nvsbcod.codBase().syspathFile().strFullPath().substring(intBaseLength);

            if (
                (nvsbcod.numSimilarity() == 1.0) &&
                                                            //Was NOT renamed or relocated
            (strRestOfPathNameNew == strRestOfPathNameBase)
                )
            {
                                                            //The file is unchanged and is not renamed or relocated.
                                                            //Do nothing.
            }
            else
            {
                                                            //Needs to log comparison summary
                //nvsbcod.subLogFileSummary(sysswSummary_M,
                //intItem.ToString().PadLeft(intItemNumberTextLength_I) + " ");

                nvsbcod.subLogFileSummary(sysswSummary_M,
                Tools.strPadLeft(Integer.toString(intItem), intItemNumberTextLength_I) + " ");
            }
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subLogAddedSection(
                                                            //Logs the ADDED section into the log resulting of the
                                                            //      comparation of solutions.
                                                            //this[I], object info

        //StreamWriter sysswSummary_M,
        Oobject<PrintWriter>  sysswSummary_M,
        int intItemNumberTextLength_I
        )
    {
        Sys.subWriteLine("", sysswSummary_M);

        DecimalFormat dfarrcodNewAdded = new DecimalFormat("#,##0");
        String strTextToCenter = " " + dfarrcodNewAdded.format(this.arrcodNewAdded().length) + " ADDED FILES (in New) ";

        String strHeaderInfo = Tools.strCenter(strTextToCenter, 120, '●', '●');
        Sys.subWriteLine(strHeaderInfo, sysswSummary_M);

        NvsbsolNewVsBaseSolutionComparison.subLogItemsArrcod(this.arrcodNewAdded(), this.syspathDirectoryNew(),
                sysswSummary_M, intItemNumberTextLength_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subLogDeletedSection(
                                                            //Logs the DELETED section into the log resulting of the
                                                            //      comparation of solutions.
                                                            //this[I], object info

        //StreamWriter sysswSummary_M,
        Oobject<PrintWriter> sysswSummary_M,
        int intItemNumberTextLength_I
        )
    {
        Sys.subWriteLine("", sysswSummary_M);

        DecimalFormat dfarrcodBaseDeleted = new DecimalFormat("#,##0");
        String strTextToCenter = " " + dfarrcodBaseDeleted.format(this.arrcodBaseDeleted().length) +
            " DELETED FILES (from Base)";

        String strHeaderInfo = Tools.strCenter(strTextToCenter, 120, '●', '●');
        Sys.subWriteLine(strHeaderInfo, sysswSummary_M);

        NvsbsolNewVsBaseSolutionComparison.subLogItemsArrcod(this.arrcodBaseDeleted(), this.syspathDirectoryBase(),
        sysswSummary_M, intItemNumberTextLength_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subLogExcludedSection(
                                                            //Logs the EXCLUDED section into the log resulting of the
                                                            //      comparation of solutions.
                                                            //this[I], object info

        //StreamWriter sysswSummary_M,
        Oobject<PrintWriter> sysswSummary_M,
        int intItemNumberTextLength_I
        )
        {
            Sys.subWriteLine("", sysswSummary_M);

            int intExcluded = this.arrsyspathNewExcluded().length + this.arrsyspathBaseExcluded().length;

            DecimalFormat dfintExcluded = new DecimalFormat("#,##0");
            String strTextToCenter = " " + dfintExcluded.format(intExcluded) + " EXCLUDED FILES ";

            String strHeaderInfo = Tools.strCenter(strTextToCenter, 120, '●', '●');
            Sys.subWriteLine(strHeaderInfo, sysswSummary_M);

            //String strExcludedNewInfo = "".PadLeft(intItemNumberTextLength_I) + " " + "".PadLeft(10, '▸') +
            //       " New (" + this.arrsyspathNewExcluded().length.ToString("#,##0") + " excluded files):";

            DecimalFormat dfarrsyspathNewExcluded = new DecimalFormat("#,##0");
            String strExcludedNewInfo = Tools.strPadLeft(
                    (" " + Tools.strPadLeft(("▸"), 10) + " New (" +
                    dfarrsyspathNewExcluded.format(
                            this.arrsyspathNewExcluded().length) + " excluded files):"),
                    intItemNumberTextLength_I);
            Sys.subWriteLine(strExcludedNewInfo, sysswSummary_M);

            NvsbsolNewVsBaseSolutionComparison.subLogItemsArrsyspath(this.arrsyspathNewExcluded(),
            this.syspathDirectoryNew(), sysswSummary_M, intItemNumberTextLength_I);

            //String strExcludedBaseInfo = "".PadLeft(intItemNumberTextLength_I) + " " + "".PadLeft(10, '▸') +
            //        " Base (" + this.arrsyspathBaseExcluded().length.ToString("#,##0") + " excluded files):";

            DecimalFormat dfarrsyspathBaseExcluded = new DecimalFormat("#,##0");

            String strExcludedBaseInfo = Tools.strPadLeft(
                    (" " + Tools.strPadLeft(("▸"), 10) + " Base (" +
                            dfarrsyspathBaseExcluded.format(
                                    this.arrsyspathBaseExcluded().length) + " excluded files):"),
                    intItemNumberTextLength_I);

            Sys.subWriteLine(strExcludedBaseInfo, sysswSummary_M);

            NvsbsolNewVsBaseSolutionComparison.subLogItemsArrsyspath(this.arrsyspathBaseExcluded(),
            this.syspathDirectoryBase(), sysswSummary_M, intItemNumberTextLength_I);
        }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subGenerateCompares(
                                                            //Generate comparison logs.
                                                            //Logs are only going to be created from matching cods that
                                                            //      have differences (Similarity < 1.0).

                                                            //Name of the application (solution).
        String strApplicationName_I,
                                                            //Timestamp to report.
        String strTsNow_I,
                                                            //Directory to contain all code comparisons (files
                                                            //      {name} {ts} filename.compare
        SyspathPath syspathDirectoryCompares_I,
                                                            //Number of context lines that the log reports will have.
                                                            //      May be any number 0 or higher, however, typically it
                                                            //      will 3 (to max 10).
        int intContextLines_I
        )
    {
        //                                                  //Create the directory in the disk.
        //DirectoryInfo sysdirForCompareLogs = Sys.sysdirNew(syspathDirectoryCompares_I);
        File sysdirForCompareLogs = Sys.sysdirNew(syspathDirectoryCompares_I);
        Sys.subCreateDirectoryOnDisk(sysdirForCompareLogs);
        syspathDirectoryCompares_I.subRefresh();

        for (NvsbcodNewVsBaseCodeComparison nvsbcod : this.arrnvsbcodMatchingFiles())
        {
            if (
                                                            //The matching cods have differences.
            nvsbcod.numSimilarity() < 1.0
            )
            {
                                                            //Create syspath for compare
                                                            //{name} {file name} {ts}.compare
                String strFileName = nvsbcod.codNew().syspathFile().strName();
                SyspathPath syspathCompare = syspathDirectoryCompares_I.syspathAddName(
                strApplicationName_I + " " + strFileName + " " + strTsNow_I + ".compare");

                                                            //Generate .compare.
                nvsbcod.subCompareReport(syspathCompare, intContextLines_I);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED TASK METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subLogItemsArrsyspath(
                                                            //Logs a set of items (arrsyspath).

                                                            //Items to report
        SyspathPath[] arrsyspath_I,
                                                            //Directory that contains the set.
        SyspathPath syspathDirectory_I,
        //StreamWriter sysswSummary_M,
        Oobject<PrintWriter> sysswSummary_M,
        int intItemNumberTextLength_I
        )
    {
        int intDirctoryFullPathLength = syspathDirectory_I.strFullPath().length();

        for (int intI = 0; intI < arrsyspath_I.length; intI = intI + 1)
        {
                                                            //Format: ##0 ./dirA/dirAA/fileA.cs

            String strRestOfPathName = arrsyspath_I[intI].strFullPath().substring(intDirctoryFullPathLength);

           // String strItemInfo = (intI + 1).ToString().PadLeft(intItemNumberTextLength_I) + " ." +
           // strRestOfPathName;

            String strItemInfo = (Tools.strPadLeft(Integer.toString((intI + 1), intItemNumberTextLength_I),
                    intItemNumberTextLength_I) + " ." + strRestOfPathName);
            Sys.subWriteLine(strItemInfo, sysswSummary_M);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subLogItemsArrcod(
                                                            //Logs a set of items (arrcod).

                                                            //Items to report
        CodCodeAbstract[] arrcod_I,
                                                            //Directory that contains the set.
        SyspathPath syspathDirectory_I,
        //StreamWriter sysswSummary_M,
        Oobject<PrintWriter> sysswSummary_M,
        int intItemNumberTextLength_I
        )
    {
        int intDirectoryFullPathLenght = syspathDirectory_I.strFullPath().length();

        for (int intI = 0; intI < arrcod_I.length; intI = intI + 1)
        {
                                                            //Format: ##0 ./dirA/dirAA/fileA.cs

            String strRestOfPathName = arrcod_I[intI].syspathFile().strFullPath().substring(intDirectoryFullPathLenght);

            //String strItemInfo = (intI + 1).ToString().PadLeft(intItemNumberTextLength_I) + " ." +
            //strRestOfPathName;

            String strItemInfo = (Tools.strPadLeft(Integer.toString((intI + 1), intItemNumberTextLength_I),
                    intItemNumberTextLength_I) + " ." + strRestOfPathName);
            Sys.subWriteLine(strItemInfo, sysswSummary_M);
        }
    }
    //==================================================================================================================
    }
    /*END-TASK*/
