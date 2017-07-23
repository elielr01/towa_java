/*TASK RPS.Tech Relevant Part Technologies*/
package Tech;

import Cod.CodCodeAbstract;
import Ti.*;
import java.util.Arrays;
import java.util.LinkedList;

//                                                          //AUTHOR: Towa (JLV-Jose Luis Vera).
//                                                          //CO-AUTHOR: Towa (GLG-Gerardo Lopez).
//                                                          //DATE: 18-Enero-2014.
//                                                          //PURPOSE:
//                                                          //ESTA ES LA ÚNICA PARTE DEL CÓDIGO DEL CONJUNTO DE SISTEMAS
//                                                          //      A DESARROLLAR EN LA ESTRATEGIA DE AUTOMATIZACIÓN DE
//                                                          //      TOWA QUE CONOCE TODAS LAS TECNOLOGÍAS INVOLUCRADAS,
//                                                          //      CUANDO SE AÑADA UNA NUEVA TECNOLOGÍA, SE DEBE AÑADIR
//                                                          //      NUEVA INFORMACIÓN EN LOS 3 ARREGLOS DE TUPLAS.
//                                                          //ADEMÁS, NUEVAS APLICACIONES PUEDEN REQUERIR AGREGAR
//                                                          //      MÉTODOS yyyyyGet(t4tech) (Ej. codxxGet que requiere
//                                                          //      el tokenizador y swpgxxfiGet que requiere el
//                                                          //      contador de línes de código).
//                                                          //
//                                                          //Se inició esta clase con: Cobol y ObjectOriented.
//                                                          //En Dic2016 se añadio: Xml y Text.

//======================================================================================================================
public final class Tech
//                                                          //Clase que incluye constantes y métodos estáticos para
//                                                          //      facilitar el desarrollo de aplicaciones en la
//                                                          //      estrátegia de automatización de Towa.
//                                                          //Algunos de estos proyectos son QEnabler (Desarrollo),
//                                                          //      QEnabler (Analisis), contador de líneas de código.
{
    //------------------------------------------------------------------------------------------------------------------
    private Tech() {}										//No permite que se cree instancia de la clase. Static class

    //------------------------------------------------------------------------------------------------------------------
	/*CONSTANTS*/

    //                                                      //Las siguientes 3 tablas (arreglos de tuplas) son
    //                                                      //      EXCLUSIVAMENTE para DEFINIR la relación entre
    //                                                      //      tecnologias (tech), instancias de tecnología (inst)
    //                                                      //      y file extensions (fext).
    //                                                      //1. SoftwareAutomation implementa un "conjunto" de
    //                                                      //      tecnologías, es de esperar que en el futuro se
    //                                                      //      incluyan otras (Ej. Fortan, o Pascal, o tal vez
    //                                                      //      "procedural oriented" donde Fortran y Pascal son
    //                                                      //      solo 2 instancias).
    //                                                      //2. Una "tecnología" (Tech) puede ser implementada en
    //                                                      //      diferentes "instancias", por ejemplo "object
    //                                                      //      oriented" es implementada en C# y Java, aún cuando
    //                                                      //      las diferencias pueden ser significativas,
    //                                                      //      "elegimos" manejarlas como "instancias" de una misma
    //                                                      //      tecnología dado que comparten el mismo paradigma
    //                                                      //      (OO) y CREEMOS poder "uniformizar" y "estandarizar"
    //                                                      //      estás diferencias, algo similar nos va a pasar
    //                                                      //      cuando enfrentemos el tema de "capa de presentación
    //                                                      //       en donde probalmentente busquemos manejar en una
    //                                                      //      misma tecnología ("presentation layer") tanto WPF
    //                                                      //      (.net) como ZK (Java).
    //                                                      //3. Una "instancia de tecnología" es una alternativa para
    //                                                      //      implementar una "tecnología" (C# y Java) son
    //                                                      //      alternativas para implementar "object oriented".
    //                                                      //4. Un "file extensión" (Ej .cs) identifica la "instancia
    //                                                      //      de tecnología" de una pieza de código, varias "file
    //                                                      //      extensión" pueden corresponder a la misma "instancia
    //                                                      //      de tecnología (.cbl, .cob y .cobol se asignan a la
    //                                                      //      "instancia de tecnologíca" CB-NEUTRAL).

    //                                                      //Debe ser ordenada por el constructor estático
    private static T2dtechDefinitionT4techTuple[] arrt2dtechDEFINITION =
    {
        //                                              //Required for IBM-COBOL (Sql y Cics son EMBEDED)
        new T2dtechDefinitionT4techTuple(TechtechEnum.COBOL, "Cobol"),
        new T2dtechDefinitionT4techTuple(TechtechEnum.SQL, "Sql"),
        new T2dtechDefinitionT4techTuple(TechtechEnum.CICS, "Cics"),

        new T2dtechDefinitionT4techTuple(TechtechEnum.OBJECT_ORIENTED, "Object-Oriented"),

        new T2dtechDefinitionT4techTuple(TechtechEnum.XML, "Xml"),

        new T2dtechDefinitionT4techTuple(TechtechEnum.TEXT, "Text"),
    };

    //                                                      //Debe ser ordenada por el constructor estático
    private static T3dinstDefinitionT3instTuple[] arrt3dinstDEFINITION =
    {
        //                                                  //Cobol.
        new T3dinstDefinitionT3instTuple(TechinstEnum.CB_NEUTRAL, "Neutral Cobol", TechtechEnum.COBOL),
        new T3dinstDefinitionT3instTuple(TechinstEnum.CB_IBM, "IBM Cobol", TechtechEnum.COBOL),
        new T3dinstDefinitionT3instTuple(TechinstEnum.CB_HP, "HP Cobol", TechtechEnum.COBOL),
        //                                                  //Sql.
        new T3dinstDefinitionT3instTuple(TechinstEnum.SQ_EMBEDED_IN_COBOL, "Sql embeded in Cobol",
            TechtechEnum.SQL),
        //                                                  //Cics.
        new T3dinstDefinitionT3instTuple(TechinstEnum.CC_EMBEDED_IN_COBOL, "Cics embeded in Cobol",
            TechtechEnum.CICS),

        //                                                  //ObjectOriented
        new T3dinstDefinitionT3instTuple(TechinstEnum.OO_NEUTRAL, "OO Neutral", TechtechEnum.OBJECT_ORIENTED),
        new T3dinstDefinitionT3instTuple(TechinstEnum.OO_CSHARP, "C#", TechtechEnum.OBJECT_ORIENTED),
        new T3dinstDefinitionT3instTuple(TechinstEnum.OO_JAVA, "Java", TechtechEnum.OBJECT_ORIENTED),
        new T3dinstDefinitionT3instTuple(TechinstEnum.OO_SWIFT, "Swift", TechtechEnum.OBJECT_ORIENTED),

        //                                                  //Xml
        new T3dinstDefinitionT3instTuple(TechinstEnum.XL_XML, "Xml", TechtechEnum.XML),
        new T3dinstDefinitionT3instTuple(TechinstEnum.XL_DRAWIO, "draw.io", TechtechEnum.XML),
        new T3dinstDefinitionT3instTuple(TechinstEnum.XL_MANIFEST, "manifest", TechtechEnum.XML),
        new T3dinstDefinitionT3instTuple(TechinstEnum.XL_CSPROJ, "csproj", TechtechEnum.XML),
        new T3dinstDefinitionT3instTuple(TechinstEnum.XL_CONFIG, "config", TechtechEnum.XML),

        //                                                  //Text
        new T3dinstDefinitionT3instTuple(TechinstEnum.TX_TEXT, "text", TechtechEnum.TEXT),
        new T3dinstDefinitionT3instTuple(TechinstEnum.TX_LOG, "log", TechtechEnum.TEXT),
        new T3dinstDefinitionT3instTuple(TechinstEnum.TX_SLN, "sln", TechtechEnum.TEXT),
    };

    //                                                      //Debe ser ordenada por el constructor estático
    private static T3dfextDefinitionT3fextTuple[] arrt3dfextDEFINITION =
    {
        //                                                  //Cobol.
        new T3dfextDefinitionT3fextTuple(".cobol", "Cobol", TechinstEnum.CB_NEUTRAL),
        new T3dfextDefinitionT3fextTuple(".cob", "Cobol", TechinstEnum.CB_NEUTRAL),
        new T3dfextDefinitionT3fextTuple(".cbl", "Cobol", TechinstEnum.CB_NEUTRAL),
        new T3dfextDefinitionT3fextTuple(".copy", "Copy", TechinstEnum.CB_NEUTRAL),
        new T3dfextDefinitionT3fextTuple(".cpy", "Copy", TechinstEnum.CB_NEUTRAL),
        new T3dfextDefinitionT3fextTuple(".include", "Include", TechinstEnum.CB_IBM),
        new T3dfextDefinitionT3fextTuple(".incl", "Include", TechinstEnum.CB_IBM),

        //                                                  //ObjectOriented
        new T3dfextDefinitionT3fextTuple(".oo", "OO.Neutral", TechinstEnum.OO_NEUTRAL),
        new T3dfextDefinitionT3fextTuple(".cs", "C#", TechinstEnum.OO_CSHARP),
        new T3dfextDefinitionT3fextTuple(".java", "Java", TechinstEnum.OO_JAVA),
        new T3dfextDefinitionT3fextTuple(".swift", "Swift", TechinstEnum.OO_SWIFT),

        //                                                  //Xml
        new T3dfextDefinitionT3fextTuple(".xml", "Xml", TechinstEnum.XL_XML),
        new T3dfextDefinitionT3fextTuple(".drawio", "draw.io", TechinstEnum.XL_DRAWIO),
        new T3dfextDefinitionT3fextTuple(".manifest", "manifest", TechinstEnum.XL_MANIFEST),
        new T3dfextDefinitionT3fextTuple(".csproj", "csproj", TechinstEnum.XL_CSPROJ),
        new T3dfextDefinitionT3fextTuple(".config", "config", TechinstEnum.XL_CONFIG),

        //                                                  //Text
        new T3dfextDefinitionT3fextTuple(".txt", "text", TechinstEnum.TX_TEXT),
        new T3dfextDefinitionT3fextTuple(".log", "log", TechinstEnum.TX_LOG),
        new T3dfextDefinitionT3fextTuple(".sln", "sln", TechinstEnum.TX_SLN),
        //                                                  //Text (requiered for SoftwareCompare)
        new T3dfextDefinitionT3fextTuple(".test", "test", TechinstEnum.TX_TEXT),
        new T3dfextDefinitionT3fextTuple(".compare", "compare", TechinstEnum.TX_LOG),
    };

    //                                                      //Las siguientes 3 tablas (arreglos de tuplas) son para
    //                                                      //      PUBLICAR la relación entre tecnologias (tech),
    //                                                      //      instancias de tecnología (inst) y file extensions
    //                                                      //      (fext).
    //                                                      //ESTAS 3 TABLAS CONTIENEN MÁS INFORMACIÓN QUE LAS
    //                                                      //      ANTERIORES, por ejemplo, una "fext" en ves de
    //                                                      //      PROPORCIONAR la "instancia de tecnología", no
    //                                                      //      porporciona la tupla (t3inst) que define la
    //                                                      //      "instancia de tecnologia".

    //                                                      //Deben ser formadas por el constructor estático
    private static T4techInfoTuple[] arrt4techTABLE;
    private static T3instInfoTuple[] arrt3instTABLE;
    private static T3fextInfoTuple[] arrt3fextTABLE;

    //------------------------------------------------------------------------------------------------------------------
    static
        //                                                  //Ordena y verifica que la información sea correcta y
        //                                                  //      consistente:
        //                                                  //1. Verifica (y ordena) la información definida.
        //                                                  //1a. Tecnologias.
        //                                                  //1b. Instancias de tecnología.
        //                                                  //1c. File extension.
        //                                                  //2. Forma (y completa) la información a ser publicada.
        //                                                  //2a. Tecnologias.
        //                                                  //2b. Instancias de tecnología.
        //                                                  //2c. File extension.
    {
        Oobject<T4techInfoTuple[]> oarrt4techTABLE = new Oobject<T4techInfoTuple[]>(Tech.arrt4techTABLE);
        Oobject<T3instInfoTuple[]> oarrt3instTABLE = new Oobject<T3instInfoTuple[]>(Tech.arrt3instTABLE);
        Oobject<T3fextInfoTuple[]> oarrt3fextTABLE = new Oobject<T3fextInfoTuple[]>(Tech.arrt3fextTABLE);

        Tech.subPrepareArrt4tech(oarrt4techTABLE);
        Tech.arrt4techTABLE = oarrt4techTABLE.v;
        Tech.subPrepareArrt3inst(oarrt3instTABLE);
        Tech.arrt3instTABLE = oarrt3instTABLE.v;
        Tech.subPrepareArrt3fext(oarrt3fextTABLE);
        Tech.arrt3fextTABLE = oarrt3fextTABLE.v;


        Tech.subPrepareArrt4techComplete();
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareArrt4tech(
        //                                                  //1a. Verifica (y ordena) tecnologías.
        //                                                  //2a. Forma t4tech a ser publicada (aún no podrá
        //                                                  //      completarla)

        //                                                  //Tabla que deberá ser formada.
        Oobject <T4techInfoTuple[]> oarrt4techTABLE_O
        )
    {
        for (int intT2 = 0; intT2 < Tech.arrt2dtechDEFINITION.length; intT2 = intT2 + 1)
        {
            //                                              //To easy code
            T2dtechDefinitionT4techTuple t2dtech = Tech.arrt2dtechDEFINITION[intT2];

            if (
                t2dtech.techtech == null
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt2dtechDEFINITION, "Tech.arrt2dtechDEFINITION") + ", " +
                    Tes3.strTo(t2dtech.techtech, "Tech.arrt2dtechDEFINITION[" + intT2 + "].techtech") +
                    " can not be null");

            Tech.subVerifyDescription(t2dtech.strDESCRIPTION,
                "Tech.arrt2dtechDEFINITION[" + intT2 + "].strDESCRIPTION");
        }

        Arrays.sort(Tech.arrt2dtechDEFINITION);

        Tools.subVerifyDuplicate(Tech.arrt2dtechDEFINITION, "Tech.arrt2dtechDEFINITION");

        //                                                  //Crea arrt4tech, posteriormente, ya que contemos con la
        //                                                  //      información de las instancias y file extensions
        //                                                  //      será completada.
        oarrt4techTABLE_O.v = new T4techInfoTuple[Tech.arrt2dtechDEFINITION.length];
        for (int intT2 = 0; intT2 < Tech.arrt2dtechDEFINITION.length; intT2 = intT2 + 1)
        {
            //                                              //To easy code
            T2dtechDefinitionT4techTuple t2dtech = Tech.arrt2dtechDEFINITION[intT2];

            oarrt4techTABLE_O.v[intT2] = new T4techInfoTuple(t2dtech.techtech, t2dtech.strDESCRIPTION, null, null);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareArrt3inst(
        //                                                  //2. Ordena y verifica instancias.
        //                                                  //1b. Verifica (y ordena) instancias.
        //                                                  //2b. Forma t3inst a ser publicada

        //                                                  //Tabla que deberá ser formada.
        Oobject <T3instInfoTuple[]> oarrt3instTABLE_O
        )
    {
        for (int intT3 = 0; intT3 < Tech.arrt3dinstDEFINITION.length; intT3 = intT3 + 1)
        {
            //                                              //To easy code
            T3dinstDefinitionT3instTuple t3dinst = Tech.arrt3dinstDEFINITION[intT3];

            if (
                t3dinst == null
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dinstDEFINITION, "Tech.arrt3dinstDEFINITION") + ", " +
                    Tes3.strTo(t3dinst.techinst, "Tech.arrt3dinstDEFINITION[" + intT3 + "].techinst") +
                    " can not be null");

            Tech.subVerifyDescription(t3dinst.strDESCRIPTION,
                "Tech.arrt3dinstDEFINITION[" + intT3 + "].strDESCRIPTION");

            if (
                t3dinst.techtech == null
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dinstDEFINITION, "Tech.arrt3dinstDEFINITION") + ", " +
                    Tes3.strTo(t3dinst.techtech, "Tech.arrt3dinstDEFINITION[" + intT3 + "].techtech") +
                    " can not be null");
            if (
                Tech.t4techGet(t3dinst.techtech) == null 
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dinstDEFINITION, "Tech.arrt3dinstDEFINITION") + ", " +
                    Tes3.strTo(Tech.arrt4techTABLE, "Tech.arrt4techTABLE") + ", " +
                    Tes3.strTo(t3dinst.techtech, "Tech.arrt3dinstDEFINITION[" + intT3 + "].techtech") +
                    " do not exist in Tech.arrt4techTABLE");
        }

        Arrays.sort(Tech.arrt3dinstDEFINITION);

        Tools.subVerifyDuplicate(Tech.arrt3dinstDEFINITION, "Tech.arrt3dinstDEFINITION");

        //                                                  //Crea arrt3inst completa.
        oarrt3instTABLE_O.v = new T3instInfoTuple[Tech.arrt3dinstDEFINITION.length];
        for (int intT3 = 0; intT3 < Tech.arrt3dinstDEFINITION.length; intT3 = intT3 + 1)
        {
            //                                              //To easy code
            T3dinstDefinitionT3instTuple t3dinst = Tech.arrt3dinstDEFINITION[intT3];

            oarrt3instTABLE_O.v[intT3] = new T3instInfoTuple(t3dinst.techinst, t3dinst.strDESCRIPTION,
                Tech.t4techGet(t3dinst.techtech));
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareArrt3fext(
        //                                                  //1c. Verifica (y ordena) instancias.
        //                                                  //2c. Forma t3inst a ser publicada

        //                                                  //Tabla que deberá ser formada.
        Oobject <T3fextInfoTuple[]> oarrt3fextTABLE_O
        )
    {
        for (int intT3 = 0; intT3 < Tech.arrt3dfextDEFINITION.length; intT3 = intT3 + 1)
        {
            //                                              //To easy code
            T3dfextDefinitionT3fextTuple t3dfext = Tech.arrt3dfextDEFINITION[intT3];
            String strFILE_EXTENSION = Tech.arrt3dfextDEFINITION[intT3].strFILE_EXTENSION;

            if (
                t3dfext.strFILE_EXTENSION == null
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dfextDEFINITION, "Tech.arrt3dfextDEFINITION") + ", " +
                    Tes3.strTo(t3dfext.strFILE_EXTENSION,
                        "Tech.arrt3dfextDEFINITION[" + intT3 + "].strFILE_EXTENSION") +
                    " can not be null");
            if (
                //                                          //FILE EXTENSION tiene mayúsculas.
                !strFILE_EXTENSION.equals(strFILE_EXTENSION.toLowerCase())
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dfextDEFINITION, "Tech.arrt3dfextDEFINITION") + ", " +
                    Tes3.strTo(strFILE_EXTENSION, "Tech.arrt3dfextDEFINITION[" + intT3 + "].strFILE_EXTENSION") +
                    "  should be only lower case letters");

            SyspathPath syspathFileA = new SyspathPath("A" + strFILE_EXTENSION);
            if (
                //                                          //FILE EXTENSION invalida.
                !syspathFileA.boolIsValid()
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dfextDEFINITION, "Tech.arrt3dfextDEFINITION") + ", " +
                    Tes3.strTo(strFILE_EXTENSION, "Tech.arrt3dfextDEFINITION[" + intT3 + "].strFILE_EXTENSION") +
                    " is invalid");

            Tech.subVerifyDescription(t3dfext.strDESCRIPTION,
                "Tech.arrt3dfextDEFINITION[" + intT3 + "].strDESCRIPTION");

            if (
                t3dfext.techinst == null
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dfextDEFINITION, "Tech.arrt3dfextDEFINITION") + ", " +
                    Tes3.strTo(t3dfext.techinst, "Tech.arrt3dfextDEFINITION[" + intT3 + "].techinst") +
                    " can not be null");
            if (
                Tech.t3instGet(t3dfext.techinst) == null
                )
                Tes3.subAbort(Tes3.strTo(Tech.arrt3dfextDEFINITION, "Tech.arrt3dfextDEFINITION") + ", " +
                    Tes3.strTo(Tech.arrt3instTABLE, "Tech.arrt3instTABLE") + ", " +
                    Tes3.strTo(t3dfext.techinst, "Tech.arrt3dfextDEFINITION[" + intT3 + "].techinst") +
                    " do not exist in Tech.arrt3instTABLE");
        }

        Arrays.sort(Tech.arrt3dfextDEFINITION);

        Tools.subVerifyDuplicate(Tech.arrt3dfextDEFINITION, "Tech.arrt3dfextDEFINITION");

        //                                                  //Crea arrt3fext completa.
        oarrt3fextTABLE_O.v = new T3fextInfoTuple[Tech.arrt3dfextDEFINITION.length];
        for (int intT3 = 0; intT3 < Tech.arrt3dfextDEFINITION.length; intT3 = intT3 + 1)
        {
            //                                              //To easy code
            T3dfextDefinitionT3fextTuple t3dfext = Tech.arrt3dfextDEFINITION[intT3];

            oarrt3fextTABLE_O.v[intT3] = new T3fextInfoTuple(t3dfext.strFILE_EXTENSION, t3dfext.strDESCRIPTION,
                Tech.t3instGet(t3dfext.techinst));
        }
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subVerifyDescription(
        //                                                  //Verifica:
        //                                                  //1. Que tenga solo caracteres USEFUL_IN_TEXT.
        //                                                  //2. Que no incluya [ ] |
        //                                                  //3. Máximo 1 blanco entre palabras.

        String strDescriptionToVerify_I,
        String strIdentifier_I
        )
    {
        if (
            strDescriptionToVerify_I == null
            )
            Tes3.subAbort(Tes3.strTo(strDescriptionToVerify_I, strIdentifier_I) + " can not by null");

        //                                                  //Verifica que los caracteres sean visibles
        for (int intC = 0; intC < strDescriptionToVerify_I.length(); intC = intC + 1)
        {
            if (
                Arrays.binarySearch(Tes3.arrcharUSEFUL_IN_TEXT, strDescriptionToVerify_I.charAt(intC)) < 0
                )
                Tes3.subAbort(Tes3.strTo(strDescriptionToVerify_I, strIdentifier_I) + ", " +
                    Tes3.strTo(intC, "intC") + " contains a NON_VISIBLE character");
            if (
                (strDescriptionToVerify_I.charAt(intC) == '[') || (strDescriptionToVerify_I.charAt(intC) == ']') ||
                (strDescriptionToVerify_I.charAt(intC) == '|')
                )
                Tes3.subAbort(Tes3.strTo(strDescriptionToVerify_I, strIdentifier_I) + ", " +
                    Tes3.strTo(intC, "intC") + " contains space, [, ] or |");
        }

        if (
            //                                              //Hay mas de 1 blanco entre palabras
            !Tools.strTrimExcel(strDescriptionToVerify_I).equals(strDescriptionToVerify_I)
            )
            Tes3.subAbort(Tes3.strTo(strDescriptionToVerify_I, strIdentifier_I) +
                " contains more than 1 space between words");
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareArrt4techComplete(
        //                                                  //4. Completa tecnologías.
        )
    {
        //                                                  //Agrega los arreglos (inst y fext) en cada una de las
        //                                                  //      tecnologías
        for (int intT4 = 0; intT4 < arrt4techTABLE.length; intT4 = intT4 + 1)
        {
            arrt4techTABLE[intT4].arrt3instOPTION = Tech.arrt3instGet(arrt4techTABLE[intT4].techtech);
            arrt4techTABLE[intT4].arrt3fextOPTION = Tech.arrt3fextGet(arrt4techTABLE[intT4].techtech);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static T3instInfoTuple[] arrt3instGet(
        //                                                  //Get set of t3inst.
        //                                                  //arrt3inst, set (order).

        TechtechEnum techtechTO_SEARCH_I
        )
    {

        LinkedList<T3instInfoTuple> lstt3instGet = new LinkedList<T3instInfoTuple>();

        for (T3instInfoTuple t3inst : Tech.arrt3instTABLE)
        {
            if (
                t3inst.t4tech.techtech == techtechTO_SEARCH_I
                )
            {
                lstt3instGet.add(t3inst);
            }
        }

        return lstt3instGet.toArray(new T3instInfoTuple[lstt3instGet.size()]);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static T3fextInfoTuple[] arrt3fextGet(
        //                                                  //Get set of t3fext.
        //                                                  //arrt3fext, set (order).

        TechtechEnum techtechTO_SEARCH_I
        )
    {
        LinkedList<T3fextInfoTuple> lstt3fextGet = new LinkedList<T3fextInfoTuple>();

        for (T3fextInfoTuple t3fext : Tech.arrt3fextTABLE)
        {
            int intT3fext = Arrays.binarySearch(Tech.arrt3instTABLE, t3fext.t3inst);

            if (
                Tech.arrt3instTABLE[intT3fext].t4tech.techtech == techtechTO_SEARCH_I
                )
            {
                lstt3fextGet.add(t3fext);
            }
        }

        return lstt3fextGet.toArray(new T3fextInfoTuple[lstt3fextGet.size()]);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC SHARED METHODS*/

    //--------------------------------------------------------------------------------------------------------------
    public static T4techInfoTuple t4techGet(
        //                                                  //Get t4tech from technology.
        //                                                  //t4tech, one t4tech, null if not exist.

        TechtechEnum techtechToSearch_I
        )
    {
        if (
            techtechToSearch_I == null
            )
            Tes3.subAbort(Tes3.strTo(techtechToSearch_I, "techtechToSearch_I") + " can not be null");

        int intT4tech = Arrays.binarySearch(Tech.arrt4techTABLE, techtechToSearch_I);

        T4techInfoTuple t4techGet;
        if (
            intT4tech < 0
            )
        {
            //                                          //No t4tech for this technology
            t4techGet = null;
        }
        else
        {
            t4techGet = Tech.arrt4techTABLE[intT4tech];

        }

        return t4techGet;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static T4techInfoTuple t4techGet(
        //                                              //Get t4tech from instance.
        //                                              //t4tech, one t4tech, null if not exist.

        TechinstEnum techinstToSearch_I
        )
    {
        if (
            techinstToSearch_I == null
            )
            Tes3.subAbort(Tes3.strTo(techinstToSearch_I, "techinstToSearch_I") + " can not be null");

        int intT3inst = Arrays.binarySearch(Tech.arrt3instTABLE, techinstToSearch_I);

        T4techInfoTuple t4techGet;
        if (
            intT3inst < 0
            )
        {
            //                                          //No t4tech for this instance
            t4techGet = null;
        }
        else
        {
            int intT4tech = Arrays.binarySearch(Tech.arrt4techTABLE, Tech.arrt3instTABLE[intT3inst].t4tech.techtech);

            t4techGet = Tech.arrt4techTABLE[intT4tech];
        }

        return t4techGet;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static T4techInfoTuple t4techGet(
        //                                              //Get t4tech from file extension.
        //                                              //t4tech, one t4tech, null if not exist.

        String strFileExtensionToSearch_I
        )
    {
        if (
            strFileExtensionToSearch_I == null
            )
            Tes3.subAbort(Tes3.strTo(strFileExtensionToSearch_I, "strFileExtensionToSearch_I") +
                " can not be null");

        int intT3fext = Arrays.binarySearch(Tech.arrt3fextTABLE, strFileExtensionToSearch_I);

        T4techInfoTuple t4techGet;
        if (
            intT3fext < 0
            )
        {
            //                                          //No t4tech for this file extension
            t4techGet = null;
        }
        else
        {
            int intT3inst = Arrays.binarySearch(Tech.arrt3instTABLE, Tech.arrt3fextTABLE[intT3fext].t3inst.techinst);

            int intT4tech = Arrays.binarySearch(Tech.arrt4techTABLE, Tech.arrt3instTABLE[intT3inst].t4tech.techtech);

            t4techGet = Tech.arrt4techTABLE[intT4tech];
        }

        return t4techGet;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static T3instInfoTuple t3instGet(
        //                                              //Get t3inst from instance.
        //                                              //t4tech, one t3inst, null if not exist.

        TechinstEnum techinstToSearch_I
        )
    {
        if (
            techinstToSearch_I == null
            )
            Tes3.subAbort(Tes3.strTo(techinstToSearch_I, "techinstToSearch_I") + " can not be null");

        int intT3inst = Arrays.binarySearch(Tech.arrt3instTABLE, techinstToSearch_I);

        T3instInfoTuple t3instGet;
        if (
            intT3inst < 0
            )
        {
            //                                          //No t3inst for this instance
            t3instGet = null;
        }
        else
        {
            t3instGet = Tech.arrt3instTABLE[intT3inst];
        }

        return t3instGet;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static T3instInfoTuple t3instGet(
        //                                              //Get t3inst from file extension.
        //                                              //t4tech, one t3inst, null if not exist.

        String strFileExtensionToSearch_I
        )
    {
        if (
            strFileExtensionToSearch_I == null
            )
            Tes3.subAbort(Tes3.strTo(strFileExtensionToSearch_I, "strFileExtensionToSearch_I") +
                " can not be null");

        int intT3fext = Arrays.binarySearch(Tech.arrt3fextTABLE, strFileExtensionToSearch_I);

        T3instInfoTuple t3instGet;
        if (
            intT3fext < 0
            )
        {
            //                                          //No t3inst for this file extension
            t3instGet = null;
        }
        else
        {
            int intT3inst = Arrays.binarySearch(Tech.arrt3instTABLE, Tech.arrt3fextTABLE[intT3fext].t3inst.techinst);

            t3instGet = Tech.arrt3instTABLE[intT3inst];
        }

        return t3instGet;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static T3fextInfoTuple t3fextGet(
        //                                              //Get t3fext from file extension.
        //                                              //t4tech, one t3fext, null if not exist.

        String strFileExtensionToSearch_I
        )
    {
        if (
            strFileExtensionToSearch_I == null
            )
            Tes3.subAbort(Tes3.strTo(strFileExtensionToSearch_I, "strFileExtensionToSearch_I") +
                " can not be null");

        int intT3fext = Arrays.binarySearch(Tech.arrt3fextTABLE, strFileExtensionToSearch_I);

        T3fextInfoTuple t3fextGet;
        if (
            intT3fext < 0
            )
        {
            //                                          //No t3fext for this file extension
            t3fextGet = null;
        }
        else
        {
            t3fextGet = Tech.arrt3fextTABLE[intT3fext];
        }

        return t3fextGet;
    }

    //--------------------------------------------------------------------------------------------------------------
    //                                                      //The following sequence of method (methods to get DUMMY are
    //                                                      //      are implemented using a CASE staterment.
    //                                                      //IT IS NOT POSSIBLE TO ADD THIS DUMMY TO T4tech, THIS WILL
    //                                                      //      CAUSE TO CALL INITIALIZATION OF THOSE CLASSES
    //                                                      //      BEFORE THIS CLASS HAS FINISH INITIALIZATION.
    //                                                      //This implementation defere initialization of those classes
    //                                                      //      until they are really needed.

    //--------------------------------------------------------------------------------------------------------------
    public static CodCodeAbstract codxxGet(
        //                                                  //Get codDUMMY from t4tech.
        //                                                  //codDUMMY, correspondint to technology.

        TechtechEnum techtechToSearch_I
        )
    {
        CodCodeAbstract codxxGet;
        //                                                  //The case branches that give a codxxGet = null as a result
        //                                                  //      are not yet implemented in Java or do not exist.
        /*CASE*/
        if (
            techtechToSearch_I == TechtechEnum.CICS
            )
        {
//            codxxGet = SoftwareAutomationCics.CodccCics.codccDUMMY_UNIQUE;
            codxxGet = null;
        }
        else if (
            techtechToSearch_I == TechtechEnum.COBOL
            )
        {
            codxxGet = Cod.CodcbCobol.codcbDUMMY_UNIQUE;
        }
        else if (
            techtechToSearch_I == TechtechEnum.OBJECT_ORIENTED
            )
        {
            codxxGet = Cod.CodooObjectOriented.codooDUMMY_UNIQUE;
        }
        else if (
            techtechToSearch_I == TechtechEnum.SQL
            )
        {
//            codxxGet = SoftwareAutomationSql.CodsqSql.codsqDUMMY_UNIQUE;
            codxxGet = null;
        }
        else if (
            techtechToSearch_I == TechtechEnum.TEXT
            )
        {
//            codxxGet = SoftwareAutomationText.CodtxText.codtxDUMMY_UNIQUE;
            codxxGet = null;
        }
        else if (
            techtechToSearch_I == TechtechEnum.XML
            )
        {
//            codxxGet = SoftwareAutomationXml.CodxlXml.codxlDUMMY_UNIQUE;
            codxxGet = null;
        }
        else
        {
            Tes3.subAbort(Tes3.strTo(techtechToSearch_I, "techtechToSearch_I") +
                " CodxxXxxxxx class not yet implemented for this technology");

            codxxGet = null;
        }
        /*END-CASE*/

        return codxxGet;
    }

    //--------------------------------------------------------------------------------------------------------------
}

