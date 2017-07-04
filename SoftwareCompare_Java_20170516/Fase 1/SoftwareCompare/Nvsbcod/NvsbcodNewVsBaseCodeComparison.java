/*TASK RPS.Nvsbcod Relevant Part Code New vs. Base Comparison*/
package Nvsbcod;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import QEnablerBase.*;
import TowaInfrastructure.*;

                                                            //AUTHOR: Towa (GLG-Gerardo Lopez).
                                                            //CO-AUTHOR: Towa (Alejandro Arellano).
                                                            //DATE: 30-Noviembre-2016.
                                                            //PURPOSE:
                                                            //Funcionalidad util para comparar los entregables (codigo
                                                            //      y/o texto) de un producto de software que se está
                                                            //      desarrollando.
                                                            //ESTA FUNCIONALIDAD TIENE COMO FINALIDAD PODER ASEGURAR LA
                                                            //      CALIDAD DEL SOFTWARE QUE SE ESTA DESARROLLANDO.
                                                            //Son muchos los elementos que pueden ser analizados,
                                                            //      algunas ideas son:
                                                            //1. Código, codcb, codoo, ..., codxl(xml), etc.
                                                            //2. Archivos de texto (codtx).
                                                            //3. Logs de pruebas (codtx).
                                                            //En el futuro incluiremos posibilidades como:
                                                            //4. Directorios y nombres de archivos.
                                                            //5. Listas de tokens.
                                                            //6. Árbol de se (Standard Element).
                                                            //7. ....
                                                            //Cuando esto suceda (deberemos tener un mejor entendimiento
                                                            //      de este problema) esta clase se separara en:
                                                            //NvsbNewVsBaseComparison y NvsbcodCode, y se diseñaran
                                                            //      otras como NvsbdorfDirectoryOrFile, NvsbtokToken y
                                                            //      muchas otras Nvsb???.
//======================================================================================================================
    public class NvsbcodNewVsBaseCodeComparison extends BclassBaseClassAbstract
                                                            //Clase que incluye funcionalidad para comparar objetos cod.
    {
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    public BclassmutabilityEnum bclassmutability() {  return BclassmutabilityEnum.INMUTABLE; }

    public static /*readonly*/ NvsbcodNewVsBaseCodeComparison basenewcodDUMMY_UNIQUE =
                new NvsbcodNewVsBaseCodeComparison();

                                                            //Cantidad de elementos consecutivos (líneas, tokens,
                                                            //      palabras, caracteres, etc.) que son requerido para
                                                            //      considerarlo igual.
                                                            //Ej. es 3 y tenemos la secuencia New: A-B-C-D-E-F-G y la
                                                            //      secuencia Base: A-X-B-C-Y-D-E-F-G serán
                                                            //      consideradas:
                                                            //(A) no son iguales porque es solo 1.
                                                            //(B-C) no son iguales porque son solo 2.
                                                            //(D-E-F-G) iguales porque son 3 ó más.
                                                            //El resultado será secuencia New: A-B-C-(D-E-F-G) y
                                                            //      secuencia Base: A-X-B-C-Y-(D-E-F-G).
                                                            //Dado lo anterior se reportará: Inserted A-B-C, Removed
                                                            //      A-X-B-C-Y.
                                                            //¡OJO! 12Dic2017, por lo pronto solo pensaremos en líneas
                                                            //      de código.
    private static final Integer intREQUIRED_FOR_EQUAL = 3;

    //------------------------------------------------------------------------------------------------------------------
    static
    {
        if (
                NvsbcodNewVsBaseCodeComparison.intREQUIRED_FOR_EQUAL < 1
                )
            Tools.subAbort(
                    Tes2.strTo(NvsbcodNewVsBaseCodeComparison.intREQUIRED_FOR_EQUAL,
                            "NvsbcodNewVsBaseCodeComparison.intREQUIRED_FOR_EQUAL") +
                            " should be >= 1");
    }
    //------------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT STATIC CONSTRUCTOR*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/
                                                            //Los 2 códigos deben ser de la misma TECHNOLOGY y del mismo
                                                            //      FILE EXTENSION.
                                                            //Full path del directorio New que ha evolucionado y se
                                                            //      desea visualizar las diferencias con su Base (esta
                                                            //      es la raíz del conjunto de archivos)
    private /*readonly*/ SyspathPath syspathDirectoryNew_Z;
    public SyspathPath syspathDirectoryNew() { return this.syspathDirectoryNew_Z; }

    //                                                      //Código (texto) que ha evolucionado y se desea visualizar
    //                                                      //      las diferencias con su Base.
    private /*readonly*/ CodCodeAbstract codNew_Z;
    public CodCodeAbstract codNew() {  return this.codNew_Z; }

    //                                                      //Full path del directorio Base contra el cual se desea
    //                                                      //      comparar (esta es la raíz del conjunto de archivos)
    private /*readonly*/ SyspathPath syspathDirectoryBase_Z;
    public SyspathPath syspathDirectoryBase() { return this.syspathDirectoryBase_Z; }

    //                                                      //Código (texto) Base contra el cual se desea comparar.
    private /*readonly*/  CodCodeAbstract codBase_Z;
    public CodCodeAbstract codBase() { return this.codBase_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

                                                            //El arrbool y arrstr de codNew son del mismo tamaño, tiene
                                                            //      true si esa línea del código nuevo tiene
                                                            //      coincidencia.
    private Boolean[] arrboolNewOk_Z = null;
    public Boolean[] arrboolNewOk()
    {
        if (
                this.arrboolNewOk_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Marca coincidencias.
            this.subNewVsBase(this.arrboolNewOk_Z, this.arrboolBaseOk_Z);

            this.subSetIsResetOff();
        }

        return this.arrboolNewOk_Z;
    }

                                                            //El arrbool y arrstr de codBase son del mismo tamaño, tiene
                                                            //      true si esa línea del código base tiene
                                                            //      coincidencia.
    private Boolean[] arrboolBaseOk_Z = null;
    public Boolean[] arrboolBaseOk()
    {
        if (
                this.arrboolBaseOk_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

            //                                      //Marca coincidencias.
            this.subNewVsBase(this.arrboolNewOk_Z, this.arrboolBaseOk_Z);

            this.subSetIsResetOff();
        }

        return this.arrboolBaseOk_Z;
    }

                                                            //Cantidad de líneas que coinciden.
    private Integer intEqual_Z = null;
    public int intEqual()
    {
        if (
                this.intEqual_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Calcula.
                                                            //Se puede contar los true en cualquiera de los 2 arrbool,
                                                            //      será el mismo total.
            this.intEqual_Z = 0;
            for (Boolean boolX : this.arrboolBaseOk_Z)
            {
                if (
                        boolX
                        )
                {
                    this.intEqual_Z = this.intEqual_Z + 1;
                }
            }

            this.subSetIsResetOff();
        }

        return (Integer) this.intEqual_Z;
    }

                                                            //Cantidad de líneas que estan en New y no coinciden en
                                                            //      Base, se asume que fueron insertadas (en realidad
                                                            //      pueden haber sido modificadas pero no lo sabemos).
    private Integer intInserted_Z = null;
    public Integer intInserted()
    {
        if (
                this.intInserted_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                        //Calcula.
            this.intInserted_Z = this.arrboolNewOk_Z.length - this.intEqual_Z;

            this.subSetIsResetOff();
        }

        return (Integer)this.intInserted_Z;
    }

                                                            //Cantidad de líneas que estaban en Base y no coinciden en
                                                            //      New, se asumé que fueron removidas (en realidad
                                                            //      puede haber sido modificada pero no lo sabemos).
    private  Integer intRemoved_Z = null;
    public Integer intRemoved()
    {
        if (
                this.intRemoved_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                        //Calcula.
            this.intRemoved_Z = this.arrboolBaseOk_Z.length - this.intEqual_Z;

            this.subSetIsResetOff();
        }

        return (Integer)this.intRemoved_Z;
    }

                                                            //Métrica de similitud entre New y Base, tomando la
                                                            //      cantidad de líneas que coincidieron (intEqual)
                                                            //      y dividiéndola entre el promedio del total de
                                                            //      líneas de New y Base. Esta métrica es un
                                                            //      valor arbitrario.
    private Double numSimilarity_Z = null;
    public Double numSimilarity()
    {
        if (
                this.numSimilarity_Z == null
                )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                        //Calcula.
            Double numAverageOfLines = (this.arrboolBaseOk_Z.length + this.arrboolNewOk_Z.length) / 2.0;
            this.numSimilarity_Z = this.intEqual_Z / numAverageOfLines;

            this.subSetIsResetOff();
        }

        return (Double)this.numSimilarity_Z;
    }

    //--------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT COMPUTED VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    protected void subResetOneClass()
    {
        this.arrboolNewOk_Z = null;
        this.arrboolBaseOk_Z = null;
        this.intEqual_Z = null;
        this.intInserted_Z = null;
        this.intRemoved_Z = null;
        this.numSimilarity_Z = null;
    }

    //------------------------------------------------------------------------------------------------------------------
    public String strTo(TestoptionEnum strtoSHORT_I)
    {
        String strObjId = Test.strGetObjId(this);

        return strObjId + "[" + super.strTo(TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.syspathDirectoryNew_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.codNew_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.syspathDirectoryBase_Z, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.codBase_Z, TestoptionEnum.SHORT) + "]";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String strTo()
    {
        String strObjId = Test.strGetObjId(this);

        return strObjId + "{" + Tes2.strTo(this.syspathDirectoryNew_Z, "syspathDirectoryNew_Z") + ", " +
                Tes2.strTo(this.codNew_Z, "codNew") + ", " +
                Tes2.strTo(this.syspathDirectoryBase_Z, "syspathDirectoryBase_Z") + ", " +
                Tes2.strTo(this.codBase_Z, "codBase") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    private NvsbcodNewVsBaseCodeComparison()  { super(true); }

    //------------------------------------------------------------------------------------------------------------------
    public NvsbcodNewVsBaseCodeComparison(
                                                            //Carga New y Base.
                                                            //this.*[O], asigna valores.

            SyspathPath syspathDirectoryNew_G,
            CodCodeAbstract codNew_G,
            SyspathPath syspathDirectoryBase_G,
            CodCodeAbstract codBase_G
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
                    codNew_G == null
                    )
                Tools.subAbort(Tes2.strTo(codNew_G, "codNew_G") + " can not be null");
            if (
                    codNew_G.syspathFile() == null
                    )
                Tools.subAbort(Tes2.strTo(codNew_G.syspathFile(), "codNew_G.syspathFile") +
                        " syspathFile can not be null");
            if (
                    !codNew_G.syspathFile().strFullPath().startsWith(syspathDirectoryNew_G.strFullPath())
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryNew_G, "syspathDirectoryNew_G") + ", " +
                        Tes2.strTo(codNew_G.syspathFile(), "codNew_G.syspathFile") +
                        " is not in DIRECTORY syspathDirectoryNew_G");
            if (
                    codNew_G.codtype() != CodtypeEnum.COMPONENT
                    )
                Tools.subAbort(Tes2.strTo(codNew_G.codtype(), "codNew_G.codtype") + " should be COMPONENT");

            if (
                    syspathDirectoryBase_G == null
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryBase_G, "syspathDirectoryBase_G") + " can not be null");
            if (
                    !syspathDirectoryBase_G.boolIsDirectory()
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryBase_G, "syspathDirectoryBase_G") + " should be a DIRECTORY");
            if (
                    codBase_G == null
                    )
                Tools.subAbort(Tes2.strTo(codBase_G, "codBase_G") + " can not be null");
            if (
                    codBase_G.syspathFile() == null
                    )
                Tools.subAbort(Tes2.strTo(codBase_G.syspathFile(), "codBase_G.syspathFile") +
                        " syspathFile can not be null");
            if (
                    !codBase_G.syspathFile().strFullPath().startsWith(syspathDirectoryBase_G.strFullPath())
                    )
                Tools.subAbort(Tes2.strTo(syspathDirectoryBase_G, "syspathDirectoryBase_G") + ", " +
                        Tes2.strTo(codBase_G.syspathFile(), "codBase_G.syspathFile") +
                        " is not in DIRECTORY syspathDirectoryBase_G");
            if (
                    codBase_G.codtype() != CodtypeEnum.COMPONENT
                    )
                Tools.subAbort(Tes2.strTo(codBase_G.codtype(), "codBase_G.codtype") + " should be COMPONENT");

            if (
                    codNew_G.codDUMMY_UNIQUE() != codBase_G.codDUMMY_UNIQUE()
                    )
                Tools.subAbort(Tes2.strTo(codNew_G.codDUMMY_UNIQUE(), "codNew_G.codDUMMY") + ", " +
                        Tes2.strTo(codBase_G.codDUMMY_UNIQUE(), "codBase_G.codDUMMY") +
                        " should be both the same technology");
//              if (
//                    codNew_G.techinst != codBase_G.techinst
//                    )
//                Tools.subAbort(Tes2.strTo(codNew_G.techinst, "codNew_G.techinst") + ", " +
//                        Tes2.strTo(codBase_G.techinst, "codBase_G.techinst") + " should be both the same techinst");
            if (
                    codNew_G.syspathFile().strFileExtension() != codBase_G.syspathFile().strFileExtension()
                    )
                Tools.subAbort(Tes2.strTo(codNew_G.syspathFile(), "codNew_G.syspathFile") + ", " +
                        Tes2.strTo(codBase_G.syspathFile(), "codBase_G.syspathFile") +
                        " should be both the same field extension");

                                                            //Guarda info, no hay nada más que hacer.
            this.syspathDirectoryNew_Z = syspathDirectoryNew_G;
            this.codNew_Z = codNew_G;
            this.syspathDirectoryBase_Z = syspathDirectoryBase_G;
            this.codBase_Z = codBase_G;

            this.subSetObjectConstructionFinish();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*METHODS TO SUPPORT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*TASK Nvsbcod2 subNewVsBase*/

    //------------------------------------------------------------------------------------------------------------------
    private void subNewVsBase(
                                                            //Compara la información de los "elementos" (líneas, tokens,
                                                            //      palabras, caracteres, directorios, archivos, etc) de
                                                            //      New con Base.
                                                            //¡OJO! 12Dic2017, por lo pronto solo pensaremos en líneas
                                                            //      de código.
                                                            //Se marcan como OK (true) los "elementos" que coincidan.
                                                            //
                                                            //Se buscan los "elementos" que coincidan, que entre éstos
                                                            //      y el principio o entre éstos y la coicidencia
                                                            //      inmediata anterior haya la menor diferencia posible
                                                            //      (Ej. si no hay coincidencia entre los "elemento"
                                                            //      (New-Base) 1-1, 2-1, 1-2, etc., pero si hay
                                                            //      coincidencia entre los "elementos" 3-3 y 4-1, se
                                                            //      deberá reconocer la coicidencia 3-3 y no la 4-1 dado
                                                            //      que en la 3-3 la diferencia entre estas y el
                                                            //      principio es 3 y en 4-1, la diferencia entre 4 y el
                                                            //      principio es 4).
                                                            //Si hay coincidencia en 2(New)-1(Base) y en 1(New)-2(base),
                                                            //      se eligira la coincidencia 2-1, esto le da prioridad
                                                            //      a la menor diferencia en Base.
                                                            //Nótese que para que haya coincidenicia debe cumplirse con
                                                            //      lo establecido en la definición de la constante
                                                            //      intREQUIRED_FOR_EQUAL. Sin embargo, si alguno de los
                                                            //      arrstr es menor, se toma ese tamaño, mínimo 1.
                                                            //this[M], toma info del objeto y constante.

                                                            //Estos arreglos deben ser de la misma dimensión que los
                                                            //      arreglos de líneas, true indica que la línea
                                                            //      correspondiente tiene una línea que coincide.
            Boolean[] arrboolNewOK_O,
            Boolean[] arrboolBaseOK_O
            )
    {
                                                            //To easy code.
        String[] arrstrNew = this.codNew_Z.arrstrLine();
        String[] arrstrBase = this.codBase_Z.arrstrLine();

                                                            //Los arreglos son del mismo tamaño que líneas de código.
        arrboolNewOK_O = new Boolean[arrstrNew.length];
        arrboolBaseOK_O = new Boolean[arrstrBase.length];

                                                            //Se inicializan los arreglos en false.
        for (Integer intI = 0; intI < arrboolNewOK_O.length; intI = intI + 1)
        {
            arrboolNewOK_O[intI] = false;
        }
        for (Integer intI = 0; intI < arrboolBaseOK_O.length; intI = intI + 1)
        {
            arrboolBaseOK_O[intI] = false;
        }

                                                            //Compute revised quantity of elements required for equal
        Integer intRequiredForEqual = Tools.intMin(NvsbcodNewVsBaseCodeComparison.intREQUIRED_FOR_EQUAL,
                arrstrNew.length, arrstrBase.length);
        intRequiredForEqual = Tools.intMax(intRequiredForEqual, 1);

                                                            //Índices para recorrer arreglos.
                                                            //Nótese que se inicia con los primeros elementos que
                                                            //      pudieran coincidir.
        Oint ointNew = new Oint(0);
        ointNew.v = intRequiredForEqual - 1;
        Oint ointBase = new Oint();
        ointBase.v = ointNew.v;

                                                            //Se cicla para marcar todas las coincidencias, el final se
                                                            //      puede consider otra coincidencia en la que determina
                                                            //      que ya no la encontró.
        /*UNTIL-DO*/
        while (!(
                                                            //Ambos arreglos ya terminaron.
                (ointNew.v >= arrstrNew.length) && (ointBase.v >= arrstrBase.length)
                ))
        {
            this.subCoincidence(ointNew, ointBase, intRequiredForEqual);

            if (
                    ointNew.v < arrstrNew.length
                    )
            {
                                                            //Si está dentro del código significa que encontro una
                                                            //      coincidencia.
                                                            //Debe marcar las líneas actuales y las anteriores conforme
                                                            //      al parámetro intRequiredForEqual.
                                                            //La misma línea se va a marcar varias veces lo cual no es
                                                            //      problema.
                for (Integer intI = 0; intI < intRequiredForEqual; intI = intI + 1)
                {
                    this.arrboolNewOk_Z[ointNew.v - intI] = true;
                    this.arrboolBaseOk_Z[ointBase.v - intI] = true;
                }
            }

                                                            //Avanza al inicio de la posible siguiente coincidencia.
            ointNew.v = ointNew.v + 1;
            ointBase.v = ointBase.v + 1;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subCoincidence(
                                                            //Procesa una coincidencia.
                                                            //this[I], acceso a info.

                                                            //Posiciones en la que inicia la búsqueda de la coincidencia
                                                            //      cuando detecta la coincidencia regresa la posición
                                                            //      de la coincidencia.
            Oint ointNew_IO,
            Oint ointBase_IO,
                                                            //Revised quantity of elements required for equal
            Integer intRequiredForEqual_I
            )
    {
                                                            //To easy code.
        String[] arrstrNew = this.codNew().arrstrLine();
        String[] arrstrBase = this.codBase().arrstrLine();

                                                            //Niveles de profundidad (cantidad de elementos que
                                                            //      involucra para buscar una coincidencia).
                                                            //Avanza en niveles de profundidad, con NP=0 involucra 1
                                                            //      elemento, con NP=1, 2, con NP=2, 3, etc.
        Integer intDeepLevel = 0;

        Obool oboolFound = new Obool();
        do {
            this.subDeepLevel(oboolFound, ointNew_IO, ointBase_IO, intDeepLevel, intRequiredForEqual_I);

            intDeepLevel = intDeepLevel + 1;
        }
        /*REPEAT-UNTIL*/
        while (!(
                                                            //Ya encontro coincidencia.
                oboolFound.v ||
                                                            //Llega al final en los 2 arrstr y no se puede procesar el
                                                            //      siguiente nivel de profundidad por exceder el tamaño
                                                            //      de las listas.
                ((ointNew_IO.v + intDeepLevel) >= arrstrNew.length) && ((ointBase_IO.v + intDeepLevel) >=
                        arrstrBase.length)
                ));

        if (
                !oboolFound.v
                )
        {
                                                            //Posiciona los índices en última posición + 1.
            ointNew_IO.v = arrstrNew.length;
            ointBase_IO.v = arrstrBase.length;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subDeepLevel(
                                                            //Busca la coincidencia con un nivel de profundidad.
                                                            //this[I], acceso a info.

                                                            //Indica si encontro coincidencia.
            Obool oboolFound_O,
                                                            //Posiciones en la que inicia la búsqueda de la
                                                            //      coincidencia, cuando detecta la coincidencia regresa
                                                            //      la posición de la coincidencia.
            Oint ointNew_IO,
            Oint ointBase_IO,
                                                            //Indica el nivel de profundidad para analizar.
            Integer intDeepLevel_I,
                                                            //Revised quantity of elements required for equal
            Integer intRequiredForEqual_I
            )
    {
                                                            //To easy code.
        String[] arrstrNew = this.codNew().arrstrLine();
        String[] arrstrBase = this.codBase().arrstrLine();

                                                            //Nivel dentro del nivel de profundidad.
                                                            //Se cicla en cada nivel dentro del nivel de profundidad
                                                            //      buscando la coincidencia.
        Integer intLevel = 0;
        do
        {
            this.subLevel(oboolFound_O, ointNew_IO, ointBase_IO, intDeepLevel_I, intLevel, intRequiredForEqual_I);

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
    private void subLevel(
                                                            //Busca la coincidencia con un nivel dentro de un nivel de
                                                            //      profundidad.
                                                            //this[I], acceso a info.

                                                            //Indica si encontró coincidencia.
            Obool oboolFound_O,
                                                            //Posición en la que inicio la búsqueda de la coincidencia,
                                                            //      cuando detecta la coincidencia regresa la posición
                                                            //      de la coincidencia.
            Oint ointNew_IO,
            Oint ointBase_IO,
                                                            //Indica el nivel de profundidad para analizar.
            Integer intDeepLevel_I,
                                                            //Indica el nivel dentro del nivel de produndidad para
                                                            //      analizar.
            Integer intLevel_I,
                                                            //Revised quantity of elements required for equal
            Integer intRequiredForEqual_I
            )
    {
                                                            //To easy code.
        String[] arrstrNew = this.codNew().arrstrLine();
        String[] arrstrBase = this.codBase().arrstrLine();

        /*CASE*/
        if (
                ((ointNew_IO.v + intDeepLevel_I) < arrstrNew.length) &&
                ((ointBase_IO.v + intLevel_I) < arrstrBase.length) &&
                this.boolAreEqualEnough((ointNew_IO.v + intDeepLevel_I), ointBase_IO.v +
                        intLevel_I, intRequiredForEqual_I)
                )
        {
                                                            //Encontró coincidencia en New(NP) con Base(N),
                                                            //      indica que encontró y posiciona los índices.
            oboolFound_O.v = true;
            ointNew_IO.v = ointNew_IO.v + intDeepLevel_I;
            ointBase_IO.v = ointBase_IO.v + intLevel_I;
        } else if (
                ((ointNew_IO.v + intLevel_I) < arrstrNew.length) &&
                ((ointBase_IO.v + intDeepLevel_I) < arrstrBase.length) &&
                this.boolAreEqualEnough(ointNew_IO.v + intLevel_I, ointBase_IO.v + intDeepLevel_I,
                                intRequiredForEqual_I)
                )
        {
                                                            //Encontró coincidencia en New(N) con Base(NP),
                                                            //      indica que encontró y posiciona los índices.
                oboolFound_O.v = true;
                ointNew_IO.v = ointNew_IO.v + intLevel_I;
                ointBase_IO.v = ointBase_IO.v + intDeepLevel_I;
        } else
        {
            oboolFound_O.v = false;
        }
        /*END-CASE*/
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Boolean boolAreEqualEnough(
                                                            //To be equal, the elements pointed should be equal, and
                                                            //      previous elements should be equal also, at least
                                                            //      the specify quantity of elements.
                                                            //this[I], acceso a info.

                                                            //bool, true enough elements are equal

                                                            //Top elements to verify.
            Integer intNew_I,
            Integer intBase_I,
                                                            //Revised quantity of elements required for equal
            Integer intRequiredForEqual_I
            )
    {
        Integer intEqualCount = 0;
        /*UNTIL-DO*/
        while (!(
                                                            //Ya llego a la cantidad necesaria para ser iguales
                (intEqualCount >= intRequiredForEqual_I) ||
                                                            //Este elemento ya no es igual
                (this.codNew().arrstrLine()[intNew_I - intEqualCount] != this.codBase().arrstrLine()[intBase_I -

                    intEqualCount])
                ))
        {
            intEqualCount = intEqualCount + 1;
        }

        return (
                                                            //Fueron iguales la cantidad suficiente de elementos.
                intEqualCount >= intRequiredForEqual_I
        );
    }
        /*END-TASK*/

        //--------------------------------------------------------------------------------------------------------------
        /*TASK Nvsbcod3 subCompareReport*/
        public void subCompareReport(
                                                            //Produce un reporte del resultado de la comparación, de la
                                                            //      siguiente forma (ejemplo):

            /*Inicia ejemplo
                                                                                          QEnabler CS 1701220943.compare
{usar método subLogFileSummary}
(blank line)
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●● COMPARE REPORT ●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
    ++++++++++
  5 using System.Text;
  6 using System.Collections.Generic;
  7 using TowaInfrastructure;
  8↑using SoftwareAutomation;
  9
 10 //                                                          //AUTHOR: Towa (EOG-Eduardo Ojeda).
 11 //                                                          //CO-AUTHOR: Towa (GLG-Gerardo Lopez).
    ++++++++++
 62         //                                                  //      mensaje indicando línea, posicion, caracter y
 63         //                                                  //      caracter en hexadecimal para tener idea de donde
 64         //                                                  //      proviene y corregir la situación.
    ▸▸▸▸▸▸▸▸▸▸ Removed from Base: lines 64-65
   ↓        public abstract char[] arrcharToConvert { get; }
   ↓        public abstract char[] arrcharConversion { get; }
 65↑        public abstract char[] arrcharTO_CONVERT { get; }
 66↑        public abstract char[] arrcharCONVERSION { get; }
 67
 68         //                                                  //Un objeto cod puede se construído a partir de un sy...
 69         //                                                  //      directamente con un arrstr.
    ++++++++++
477         /*SHARED METHODS* /
478
479         //-------------------------------------------------------------------------------------------------------...
480↑        public void subVerifyUseful(
481↑            //                                              //Verifica que el caracteres sean USEFUL (aborta).
482↑            //                                              //this[I], to access arrcharUSEFUL.
483↑
484↑            //                                              //Caracter a analizar.
485↑            char charToAnalize_I,
486↑            //                                              //Ej. charCOM_TL_START
487↑            String strIdentifierCharToAnalize_I
488↑            )
489↑        {
490↑            if (
491↑                //                                          //Caracter NON USEFUL
492↑                Array.BinarySearch(this.arrcharUSEFUL, charToAnalize_I) < 0
493↑                )
494↑                Test.subAbort(Test.strTo(charToAnalize_I, strIdentifierCharToAnalize_I) + " in NON USEFULL chara...
495↑        }
496↑
497↑        //-------------------------------------------------------------------------------------------------------...
498     }
499
500     //===========================================================================================================...
    ++++++++++
528         COM_END_OF_LINE,
529         COM_DELIMITED,
530         COM_EMBEDED,
    ▸▸▸▸▸▸▸▸▸▸ Removed from Base: lines 512-517
   ↓
   ↓        //                                                  //El código de un párrafo puede ser de los siguientes
   ↓        //                                                  //      tipos:
   ↓        PAR_UNSTRUCTURED,
   ↓        PAR_STRUCTURED,
   ↓        PAR_EMPTY,
531     }
532
533     //===========================================================================================================...
    ++++++++++
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●● END OF REPORT ●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
            Termina ejemplo*/

                                                            //Notas:
                                                            //1. La línea 1 se muestra alineada en 120 caracteres,
                                                            //      muestra el nombre del .compare.
                                                            //2. Las "líneas" se reportan en base 1.
                                                            //3. 8↑ Indica que esa línea fue insertada, el 8 se alíneo a
                                                            //      derecha en un espacio de 3 caracteres dado que el
                                                            //      máximo podría hacer sido 535 (si el máximo fuera
                                                            //      2815 hubieran sido 4 caracteres, si el máximo 8,
                                                            //      fueran solo 1 caracter).
                                                            //4. ++++++++++ indica 1 ó más líneas que no están siendo
                                                            //      mostradas.
                                                            //5. Los encabezados ●●●...● {texto} ●●●...● se muestran
                                                            //      centrados en 120 caracteres.
                                                            //this[I], Objects info.

                                                            //Archivo sobre el cual se va a escribir. (???.compare).
                                                            //El directorio sobre el cual se encuentra debe existir, si
                                                            //      existe algo en el full path debe ser FILE y se
                                                            //      reescribe
                SyspathPath syspathCompareReport_I,
                                                            //Cantidad de líneas que deben ser incluídas antes y después
                                                            //      de las líneas insertadas y/o removidas.
                                                            //Puede ser 0 (no añade contexto), típicamente serán de 3 a
                                                            //      máximo 10), sin embargo podrían ser muchas.
                int intContextLines_I
                )
        {
            if (
                    !syspathCompareReport_I.syspathDirectory().boolIsDirectory()
                    )
                Tools.subAbort(
                        Tes2.strTo(syspathCompareReport_I.syspathDirectory(),
                                "syspathCompareReport_I.syspathDirectory()") + " is not a directory");
            if (
                //                                          //Existe pero no es un file
                    syspathCompareReport_I.boolExists() && !syspathCompareReport_I.boolIsFile()
                    )
                Tools.subAbort(Tes2.strTo(syspathCompareReport_I, "syspathCompareReport_I") + " should be a file");
            if (
                    syspathCompareReport_I.strFileExtension() != ".compare"
                    )
                Tools.subAbort(Tes2.strTo(syspathCompareReport_I, "syspathCompareReport_I") +
                        " should have File Extension .compare");

                                                            //Create writer
            //FileInfo sysfileCompareReport = Sys.sysfileNew(syspathCompareReport_I);
            File sysfileCompareReport = Sys.sysfileNew(syspathCompareReport_I);
            Oobject<File> osysfileCompareReport = new Oobject<File>(sysfileCompareReport);
            Oobject<PrintWriter> sysswCompareReport = new Oobject<PrintWriter>();
            //StreamWriter sysswCompareReport;
            if (
                    syspathCompareReport_I.boolExists()
                    )
            {
                sysswCompareReport.v = Sys.sysswNewRewriteTextFile(osysfileCompareReport);
            }
            else
            {
                sysswCompareReport.v = Sys.sysswNewWriteTextFile(osysfileCompareReport);
            }

                                                            //Log Headings.

            //String str10 = syspathCompareReport_I.strName().PadLeft(120);
            String str10 = Tools.strPadLeft(syspathCompareReport_I.strName(), 120);
            Sys.subWriteLine(str10, sysswCompareReport);

            this.subLogFileSummary(sysswCompareReport, "");

            this.subLogAllChanges(sysswCompareReport, intContextLines_I);

            //sysswCompareReport.Dispose();
            sysswCompareReport = null;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private void subLogAllChanges(
                                                            //Produce un reporte del resultado de la comparación.
                                                            //Puede no haber habido ningún cambio.

                                                            //this[I], Objects info.

                                                            //Archivo sobre el cual se va a escribir.
                //StreamWriter sysswCompareReport_M,
                Oobject<PrintWriter> sysswCompareReport_M,
                //                                          //Max number of lines to be included before and after
                int intContextLines_I
                )
        {
            Sys.subWriteLine("", sysswCompareReport_M);
            String str10 = Tools.strCenter(" COMPARE REPORT ", 120, '●', '●');
            Sys.subWriteLine(str10, sysswCompareReport_M);

            if (
                                                            //Was changed
                    this.numSimilarity_Z < 1.0
                    )
            {
                                                            //Compute chars required for line numbers
                //int intLineNumberTextLength = this.codNew.arrstrLine.Length.ToString().Length;
                //Integer.toString(int)                     //Java Convert Form
                Integer intLineNumberTextLength =  Integer.toString(this.codNew().arrstrLine().length).length();

                Oint ointNew = new Oint(0);
                Oint OintBase = new Oint(0);
                /*WHILE-DO*/
                while (
                        (ointNew.v < this.arrboolNewOk_Z.length) ||
                        (OintBase.v < this.arrboolBaseOk_Z.length)
                        )
                {
                                                            //Notice that last iteration may not have coincidence block
                    this.subRemovedBlockAndOrInsertedBlockAndOrCoincidenceBlock(ointNew, OintBase,
                            intContextLines_I, intLineNumberTextLength, sysswCompareReport_M);

                    ointNew.v = ointNew.v + 1;
                    OintBase.v = OintBase.v + 1;
                }
            }

            String str90 = Tools.strCenter(" END Of REPORT ", 120, '●', '●');
            Sys.subWriteLine(str90, sysswCompareReport_M);
        }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subRemovedBlockAndOrInsertedBlockAndOrCoincidenceBlock(
                                                            //Process:
                                                            //1. (optional) Removed Block, the info is in Base.
                                                            //2. (optional) Inserted Block, the info is in New.
                                                            //3. Coincidence Block, the info is in New (notice that same
                                                            //      info is in Base but we need to report New line
                                                            //      number). On last iteration this block is optional.
                                                            //Al least Removed or Inserted Block is present. (except
                                                            //      first time).
                                                            //this[I], Object info.

                                                            //Starts on begining of the set of blocks.
                                                            //Ends on last element of the set of blocks.
            Oint ointNew_IO,
            Oint ointBase_IO,
                                                            //Max number of lines to be included before and after
                                                            //      removed and inserted lines. Can be 0.
            int intContextLinesMax_I,
                                                            //Chars required for line numbers.
            int intLineNumberTextLength_I,
                                                            //On which to write.
            //StreamWriter sysswCompareReport_M
            Oobject<PrintWriter> sysswCompareReport_M
            )
    {
                                                            //1. (optional) Removed Block, the info is in Base.
        if (
                (ointBase_IO.v < this.arrboolBaseOk_Z.length) &&
                                                            //Start of removed block.
                (this.arrboolBaseOk_Z[ointBase_IO.v] == false)
                )
        {
            this.subRemovedBlock(ointBase_IO, intLineNumberTextLength_I, sysswCompareReport_M);

                                                            //Position after removed block
            ointBase_IO.v = ointBase_IO.v + 1;
        }

                                                            //2. (optional) Inserted Block, the info is in New.
        if (
                (ointNew_IO.v < this.arrboolNewOk_Z.length) &&
                                                            //Start of inserted block.
                (this.arrboolNewOk_Z[ointNew_IO.v] == false)
                )
        {
            this.subInsertedBlock(ointNew_IO, intLineNumberTextLength_I, sysswCompareReport_M);

                                                            //Position after inserted block
            ointNew_IO.v = ointNew_IO.v + 1;
        }

                                                            //3. Coincidence Block, the info is in New (notice that same
                                                            //      info is in Base but we need to report New line
                                                            //      number). On last iteration this block is optional.
        if (
                (ointNew_IO.v < this.arrboolNewOk_Z.length)
                )
        {
            this.subCoincidenceBlock(ointNew_IO, ointBase_IO, intContextLinesMax_I, intLineNumberTextLength_I,
                    sysswCompareReport_M);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subRemovedBlock(
                                                            //Process and log a block of removed lines.
                                                            //this[I], Object info.

                                                            //Starts at begining of removed block.
                                                            //Ends at last element of removed block.
            Oint ointBase_IO,
                                                            //Chars required for line numbers.
            int intLineNumberTextLength_I,
            //                                              //On which to write.
            //StreamWriter sysswCompareReport_M
            Oobject<PrintWriter> sysswCompareReport_M
            )
    {
                                                            //Compute length of removed block (at least 1)
        int intRemovedBlock = 0;
        /*WHILE-DO*/
        while (
                ((ointBase_IO.v + intRemovedBlock) < this.arrboolBaseOk_Z.length) &&
                                                            //It is in the removed block.
                (this.arrboolBaseOk_Z[ointBase_IO.v + intRemovedBlock] == false)
                )
        {
            intRemovedBlock = intRemovedBlock + 1;
        }

                                                            //Summary info
        String str10 = Tools.strPadLeft("", intLineNumberTextLength_I) + " " + Tools.strPadLeft("", 10, '▸') +
                " Removed from Base: ";
        if (
                                                            //Is only one removed line.
                intRemovedBlock == 1
                )
        {
            str10 = str10 + "line " + (ointBase_IO.v + 1);
        }
        else
        {
                                                            //The block of differences has an upper and lower bound.
                                                            //      +1 because the log is base 1.
            str10 = str10 + "lines " + (ointBase_IO.v + 1) + "-" + (ointBase_IO.v + intRemovedBlock);
        }
        Sys.subWriteLine(str10, sysswCompareReport_M);

        for (int intI = 0; intI < intRemovedBlock; intI = intI + 1)
        {
                                                            //Log the removed line.
            String str50 = Tools.strPadLeft("", intLineNumberTextLength_I) + "↓" +
                    this.codBase().arrstrLine()[ointBase_IO.v + intI];
            Sys.subWriteLine(str50, sysswCompareReport_M);
        }

                                                            //Place the index at the last removed line.
        ointBase_IO.v = ointBase_IO.v + intRemovedBlock - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subInsertedBlock(
                                                            //Process and log a block of inserted lines.
                                                            //this[I], Object info.

                                                            //Starts at begining of inserted block.
                                                            //Ends at last element of inserted block.
            Oint ointNew_IO,
                                                            //Chars required for line numbers.
            int intLineNumberTextLength_I,
                                                            //On which to write.
            //StreamWriter sysswCompareReport_M
            Oobject<PrintWriter> sysswCompareReport_M
            )
    {
        /*WHILE-DO*/
        while (
                (ointNew_IO.v < this.arrboolNewOk_Z.length) &&
                                                            //It is in the inserted block.
                (this.arrboolNewOk()[ointNew_IO.v] == false)
                )
        {
                                                            //Log the inserted line.
            //String str10 = (ointNew_IO.v + 1).toString().PadLeft(intLineNumberTextLength_I) + "↑" +
            //        this.codNew().arrstrLine()[ointNew_IO.v];
            String str10 = Tools.strPadLeft(Integer.toString(ointNew_IO.v + 1), intLineNumberTextLength_I) + "↑" +
                    this.codNew().arrstrLine()[ointNew_IO.v];
            Sys.subWriteLine(str10, sysswCompareReport_M);

            ointNew_IO.v = ointNew_IO.v + 1;
        }

                                                            //Place the index at the last inserted line.
        ointNew_IO.v = ointNew_IO.v - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void subCoincidenceBlock(
                                                            //Process and log whatever is necessary in a block of
                                                            //      coincidences. A block of coincidences has three
                                                            //      parts: an optional first part of context lines,
                                                            //      a second optional part of intermediate lines (lines
                                                            //      that are coincidences but are not context, to be
                                                            //      represented by a "++++++++++", and a third optional
                                                            //      part of context lines.
                                                            //this[I], Objects info.

                                                            //Starts at begining of coincidence block.
                                                            //Ends at last element of coincidence block.
            Oint ointNew_IO,
            Oint ointBase_IO,
                                                            //Max number of lines to be included before and after
                                                            //      removed and inserted lines. Can be 0.
            int intContextLinesMax_I,
                                                            //Chars required for line numbers.
            int intLineNumberTextLength_I,
                                                            //On which to write.
            //StreamWriter sysswCompareReport_M
            Oobject<PrintWriter> sysswCompareReport_M
            )
    {
                                                            //Define context lines to report.
                                                            //This array will have the size of coincidence block,
                                                            //The content of this array will be:
                                                            //true, ..., false, ..., true, ...
                                                            //each of the 3 part is optional, will log every true line
                                                            //      and only one ++++++++++ for the false set.
        Boolean[] arrboolContext = this.arrboolContextDefine(ointNew_IO.v, ointBase_IO.v, intContextLinesMax_I);

        Integer intContext = 0;

                                                            //Log start true set.
        /*WHILE-DO*/
        while (
                (intContext < arrboolContext.length) &&
                (arrboolContext[intContext] == true)
                )
        {
                                                            //Log context lines.
            //String str10 = (ointNew_IO.v + intContext + 1).ToString().PadLeft(intLineNumberTextLength_I) + " " +
            //        this.codNew().arrstrLine()[ointNew_IO.v + intContext];
            String str10 = Tools.strPadLeft(Integer.toString(ointNew_IO.v + intContext + 1),
                    intLineNumberTextLength_I) + " " + this.codNew().arrstrLine()[ointNew_IO.v + intContext];
            Sys.subWriteLine(str10, sysswCompareReport_M);

            intContext = intContext + 1;
        }

                                                            //Log optional ++++++++++ to represent coincidence line not
                                                            //      logged in context
                                                            //It in array, for sure is false
        if (
                intContext < arrboolContext.length
                )
        {
                                                            //There is at least one intermediate line that is not
                                                            //      reported, it must be replaced with "++++++++++"
            String str50 = Tools.strPadLeft("", intLineNumberTextLength_I) + " " + Tools.strPadLeft("", 10, '+');
            Sys.subWriteLine(str50, sysswCompareReport_M);

                                                            //Skip all intermediate lines
            /*WHILE-DO*/
            while (
                    (intContext < arrboolContext.length) &&
                    (arrboolContext[intContext] == false)
                    )
            {
                intContext = intContext + 1;
            }
        }

                                                            //Log end true set.
        /*WHILE-DO*/
        while (
                intContext < arrboolContext.length
                )
        {
                                                            //Log context lines.
            //String str90 = (ointNew_IO.v + intContext + 1).ToString().PadLeft(intLineNumberTextLength_I) + " " +
            //        this.codNew().arrstrLine()[ointNew_IO.v + intContext];
            String str90 = Tools.strPadLeft(Integer.toString(ointNew_IO.v + intContext + 1),
                    intLineNumberTextLength_I) + " " + this.codNew().arrstrLine()[ointNew_IO.v + intContext];
            Sys.subWriteLine(str90, sysswCompareReport_M);

            intContext = intContext + 1;
        }

                                                            //Place the index (both) at the last coincidence line.
        ointNew_IO.v = ointNew_IO.v + arrboolContext.length - 1;
        ointBase_IO.v = ointBase_IO.v + arrboolContext.length - 1;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Boolean[] arrboolContextDefine(
                                                            //Define lines that are context in this coincidence block.
                                                            //this[I], Objects info.

                                                            //arrbool, True indicates that it is a context line.


                                                            //Beginning of coincidence block.
            int intNew_I,
            int intBase_I,
                                                            //Max number of lines to be included before and after
                                                            //      removed and inserted lines. Can be 0.
            int intContextLinesMax_I
    )
    {
                                                            //Compute length of this coincidence block (al least 1)
        int intBlock = 0;
        /*WHILE-DO*/
        while (
                                                            //Both arrays have elements
                ((intNew_I + intBlock) < this.arrboolNewOk_Z.length) &&
                ((intBase_I + intBlock) < this.arrboolBaseOk_Z.length) &&
                                                            //Both have a coincidence
                (this.arrboolNewOk_Z[intNew_I + intBlock] == true) &&
                (this.arrboolBaseOk_Z[intBase_I + intBlock] == true)
                )
        {
            intBlock = intBlock + 1;
        }

        //                                                  //Initialize with False.
        Boolean[] arrboolContextDefine = new Boolean[intBlock];
        for (Integer intI = 0; intI < arrboolContextDefine.length; intI = intI + 1)
        {
            arrboolContextDefine[intI] = false;
        }

        Integer intContextLinesForThisBlock = Tools.intMin(intContextLinesMax_I, intBlock);

                                                            //Mark context at the start of block
        if (
                                                            //Both blocks, new and base are at first element, there is
                                                            //      no insertion or removal before
                (intNew_I == 0) && (intBase_I == 0)
                )
        {
                                                            //There is not start context.
                                                            //DO NOTHING
        }
        else
        {
                                                            //Mark start context
            int intI = 0;
            /*WHILE-DO*/
            while (
                    intI < intContextLinesForThisBlock
                    )
            {
                arrboolContextDefine[intI] = true;

                intI = intI + 1;
            }
        }

                                                            //Mark context at the end of block
        if (
                                                            //Both blocks, new and base are at the end, there is no
                                                            //      insertion or removal after
                ((intNew_I + intBlock) >= this.arrboolNewOk_Z.length) &&
                ((intBase_I + intBlock) >= this.arrboolBaseOk_Z.length)
                )
        {
                                                            //There is not end context.
                                                            //DO NOTHING
        }
        else
        {

            //                                              //Compute end position of block
            Integer intNewEndOfBlock = arrboolContextDefine.length - 1;

                                                            //Mark end context.
                                                            //Start and end context may overlap, no problem.
            Integer intI = 0;
            /*WHILE-DO*/
            while (
                    intI < intContextLinesForThisBlock
                    )
            {
                arrboolContextDefine[intNewEndOfBlock - intI] = true;

                intI = intI + 1;
            }
        }

        return arrboolContextDefine;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void subLogFileSummary(
                                                            //Produce en el log un resumen del archivo, de la siguiente
                                                            //      forma (ver ejemplos, la línea en blanco separa cada
                                                            //      uno de los ejemplos).
                                                            //Se implemento de esta forma dado que el reporte de la
                                                            //      solucion requerira esta misma información, pero
                                                            //      añadiendo un bullet al principio.

            /*Inician Ejemplos

 25 File: .\SoftwareAutomation (RP)\Cod RP.cs, 535 lines (unchanged)

 44 New: .\dirA\dirAA\fileG.cs 234 lines (unchanged, renamed and relocated)
    Base: .\dirA\dirBB\fileGX.cs

 87 File: .\SoftwareAutomation (RP)\Cod RPcs, 535 lines {Base: 522 lines}
    ↑21 lines inserted, ↓7 lines removed, 514 equal lines, similarity: 97.4%

132 New: .\SoftwareAutomation (RP)\Cod RP.cs, 535 lines (renamed and relocated)
    Base: .\QEnabler RP Specs\QZZ Cod RP Spec.cs, 522 lines

            Terminan ejemplos*/

                                                            //Notas:
                                                            //1. Los archivos pueden ser "changed" o "unchanged", en
                                                            //      "changed" se sumarizan los cambios.
                                                            //2. Los archivos pueden ser ranamed, relocated o permanecer
                                                            //      como estaban, donde ser reporta "renamed and
                                                            //      relocated" podria ser solo "renamed" o "relocated".
                                                            //3. La cantidad de líneas se reportan en formato #,##0.
                                                            //4. La similaridad se reportan en formato 0.0%.
                                                            //this[I], Objects info.

                                                            //Log sobre el cual se va a escribir.
                //StreamWriter sysswLog_M,
                Oobject<PrintWriter> sysswLog_M,
                                                            //Ver ejemplos: " 25 ", " 44 ", " 87 ", "132 ".
                                                            //Indica el texto que se va a incluir al inicio del resumen,
                                                            //      nótese que también indica la cantidad de espacios a
                                                            //      incluir en las líneas subsecuentes.
                                                            //Este texto podría ser "".
                String strBulletText_I
        )
        {
            if (
                    sysswLog_M == null
                    )
                Tools.subAbort(Tes2.strTo(sysswLog_M, "sysswLog_M") + " can not be null");

                                                            //The rest of the path name of each file after the root.
                                                            //Ej. \some directory\some file
            String strRestOfPathNameNew = this.codNew().syspathFile().strFullPath().
                    substring(this.syspathDirectoryNew().strFullPath().length());
            String strRestOfPathNameBase = this.codBase().syspathFile().strFullPath().
                    substring(this.syspathDirectoryBase().strFullPath().length());

            String strTextRenamedRelocated = NvsbcodNewVsBaseCodeComparison.strTextRenamedRelocated(
                    strRestOfPathNameNew, strRestOfPathNameBase);

            /*CASE*/
            if (
                    (this.numSimilarity_Z == 1.0) &&
                                                            //Was not renamed or relocated
                    (strTextRenamedRelocated == null)
                    )
            {
                //String str10 = strBulletText_I + "File: ." + strRestOfPathNameNew + " " +
                //        this.codNew().arrstrLine().length.ToString("#,##0") + " lines (unchanged)";
                //Sys.subWriteLine(str10, sysswLog_M);
                DecimalFormat dfStr10 = new DecimalFormat("#,##0");
                String str10 = strBulletText_I + "File: ." + strRestOfPathNameNew + " " +
                        dfStr10.format(this.codNew().arrstrLine().length) + " lines (unchanged)";
                Sys.subWriteLine(str10, sysswLog_M);
            }
            else if (
                    (this.numSimilarity_Z  == 1.0)
                    )
            {
                                                            //Renamed and/or relocated
                DecimalFormat dfStr20 = new DecimalFormat("#,##0");
                String str20 = strBulletText_I + "New: ." + strRestOfPathNameNew + " " +
                        dfStr20.format(this.codNew().arrstrLine().length) + " lines (unchanged, " +
                        strTextRenamedRelocated +
                        ")";
                Sys.subWriteLine(str20, sysswLog_M);

                String str25 = Tools.strPadLeft("", strBulletText_I.length()) + "Base: " + strRestOfPathNameBase;
                Sys.subWriteLine(str25, sysswLog_M);
            }
            else
            {
                                                            //Was changed

                if (
                                                            //Was NOT renamed or relocated
                        strTextRenamedRelocated == null
                        )
                {
                    DecimalFormat dfStr30 = new DecimalFormat("#,##0");
                    String str30 = strBulletText_I + "File: ." + strRestOfPathNameNew + " " +
                            dfStr30.format(this.codNew().arrstrLine().length) + " lines {Base: " +
                            dfStr30.format(this.codBase().arrstrLine().length) + " lines}";
                    Sys.subWriteLine(str30, sysswLog_M);
                }
                else
                {
                                                            //Renamed and/or relocated

                    DecimalFormat dfStr40 = new DecimalFormat("#,##0");
                    String str40 = strBulletText_I + "New: ." + strRestOfPathNameNew + " " +
                            dfStr40.format(this.codNew().arrstrLine().length) + " lines (" +
                            strTextRenamedRelocated + ")";
                    Sys.subWriteLine(str40, sysswLog_M);

                    DecimalFormat dfStr45 = new DecimalFormat("#,##0");
                    String str45 = Tools.strPadLeft("", strBulletText_I.length()) + "Base: ." + strRestOfPathNameBase +
                            " " + dfStr45.format(this.codBase().arrstrLine().length) + " lines";
                    Sys.subWriteLine(str45, sysswLog_M);
                }

                DecimalFormat dfStr80 = new DecimalFormat("#,##0");
                DecimalFormat dfStr80Percentage = new DecimalFormat("0.0%");
                String str80 = Tools.strPadLeft("", strBulletText_I.length()) + "↑" +
                        dfStr80.format(this.intInserted_Z) + " lines inserted, ↓" + dfStr80.format(this.intRemoved_Z) +
                        " lines removed, " +
                        dfStr80.format(this.intEqual_Z) + " equal lines, similarity: " +
                        dfStr80Percentage.format(this.numSimilarity_Z);
                Sys.subWriteLine(str80, sysswLog_M);
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strTextRenamedRelocated(
                                                            //Compute text:
                                                            //a. renamed and relocated.
                                                            //b. renamed.
                                                            //c. relocated.
                                                            //d. null.
                                                            //this[I], object info.

                                                            //str, text, null if not renamed or relocated

                                                            //New and Base partial paths.
                                                            //(Ex. \dirA\dirA2\name.cs, at least \name.cs)
                String strRestOfPathNameNew_I,
                String strRestOfPathNameBase_I
        )
        {
            //                                              //Split directory and name

            int intNewLastSlashPlus1 = strRestOfPathNameNew_I.lastIndexOf('\\') + 1;
            String strNewPath = strRestOfPathNameNew_I.substring(0, intNewLastSlashPlus1);
            String strNewName = strRestOfPathNameNew_I.substring(intNewLastSlashPlus1);

            int intBaseLastSlashPlus1 = strRestOfPathNameBase_I.lastIndexOf('\\') + 1;
            String strBasePath = strRestOfPathNameBase_I.substring(0, intBaseLastSlashPlus1);
            String strBaseName = strRestOfPathNameBase_I.substring(intBaseLastSlashPlus1);

            String strTextRenamedRelocated;
            /*CASE*/
            if (
                    strRestOfPathNameNew_I == strRestOfPathNameBase_I
                    )
            {
                                                            //It is not renamed or relocated
                strTextRenamedRelocated = null;
            }
            else if (
                                                            //Has been renamed AND relocated
                    (strNewName != strBaseName) && (strNewPath != strBasePath)
                    )
            {
                strTextRenamedRelocated = "renamed and relocated";
            }
            else if (
                                                            //Only has been renamed
                    (strNewName != strBaseName)
                    )
            {
                strTextRenamedRelocated = "renamed";
            }
            else
            {
                                                            //Only has been relocated
                strTextRenamedRelocated = "relocated";
            }
            /*END-CASE*/

            return strTextRenamedRelocated;
        }

        //--------------------------------------------------------------------------------------------------------------
    }
//======================================================================================================================
/*END-TASK*/