/*TASK RPS.Tech Relevant Part Technologies*/
package SoftwareAutomation;

import TowaInfrastructure.*;
import java.lang.System;
import java.util.Arrays;

                                                            //AUTHOR: Towa (JLV-Jose Luis Vera).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo Lopez).
                                                            //DATE: 18-Enero-2014.
                                                            //PURPOSE:
                                                            //Definición de constantes requeridas.
                                                            //ESTA ES LA ÚNICA PARTE DEL CÓDIGO DEL CONJUNTO DE SISTEMAS
                                                            //      A DESARROLLAR EN LA ESTRATEGIA DE AUTOMATIZACIÓN DE
                                                            //      TOWA QUE CONOCE TODAS LAS TECNOLOGÍAS INVOLUCRADAS,
                                                            //      CUANDO SE AÑADA UNA NUEVA TECNOLOGÍA, SE DEBE AÑADIR
                                                            //      NUEVA INFORMACIÓN AQUÍ (BUSCAR <<AÑADIR>>).
                                                            //Integración de diversas tecnologías y de sus instancias,
                                                            //      el concepto "tecnología" se utilizará para referirse
                                                            //      a un "conjunto de tecnologías" que son similares y
                                                            //      que se tomo el reto de manejarlas como una sola
                                                            //      tecnología, por lo pronto tenemos:
                                                            //1) Cobol (prefijo cb), sus instancias son: COBOL IBM, 
                                                            //      COBOL Neutro (en todos los casos sus archivos de
                                                            //      código son: .cobol, .cob, .cbl, .copy, cpy, .include
                                                            //      e .incl), todos estos archivos se consideran de
                                                            //      tecnología COBOL, la diferencia entre las diferentes
                                                            //      instancias son menores, si embargo cuando integremos
                                                            //      en la tecnología el manejo de pantallas y base de
                                                            //      datos, las diferencias serán mayores (en el futuro
                                                            //      se incluirán otras instancias como COBOL HP)
                                                            //2) Object Oriented (prefijo oo), sus instancias son: C#
                                                            //      (sus archivos de código son .cs) y Java (sus
                                                            //      archivos de código son .java), los  archivos serán
                                                            //      de tenología C# y Java, las diferencias entre ellos
                                                            //      pueden ser significativas (en el futuro se incluirán
                                                            //      otras instancias como VB, C++ y Objective C).
                                                            //Se opto por incluir en Object Oriented todos los lenguajes
                                                            //      que comparten una misma estructura en sus códigos,
                                                            //      en general la estructura común es:
                                                            //a) Los componentes de código contienen namespaces o
                                                            //      paquetes.
                                                            //b) Los namespaces contienen clases, enumeradores e
                                                            //      interfaces. 
                                                            //c) Así sucesivamente, adicionalmente los estándares de
                                                            //      diseño y código de Towa buscarán hacerlos
                                                            //      "conceptualmente" similares. 

//======================================================================================================================
public final class Tech										//Clase que incluye métodos estáticos (funciones o
                                                            //      subrutinas) que sirven para permitir el uso de
                                                            //      diversas tecnologías en forma integrada, permitiendo
                                                            //      el desarrollo de muchas clases abstractas.
                                                            //Algunos de estos proyectos son QEnabler y Sense.
{
	//------------------------------------------------------------------------------------------------------------------
	/*CONSTANTS*/

	                                                        //<<AÑADIR>>SE REQUIERE UN CONJUNTO DE INFORMACIÓN PARA CADA
	                                                        //      TECNOLOGIA, CADA CONJUNTO ES SIMILAR EN ESTRUCTURA.

	                                                        //Para COBOL:

	                                                        //En COBOL se tiene 3 piezas diferentes de archivos de
	                                                        //      código; programas o rutinas completos, COPY's e
	                                                        //      INCLUDE's.
	                                                        //La primer columna (FILE_EXTENSION) deben ser minúsculas o
	                                                        //      dígitos.
	                                                        //La segunda y tercer columna (FILE_DESCRIPTION y
                                                            //      CODE_TECHNOLOGY) No debe tener caracteres en blanco
                                                            //      [ ] | (esto se requiere para identificar
                                                            //      correctamente las tecnologías en el contador de
                                                            //      líneas de código).
    public static String[][] arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_CB =

        { 
            { ".cobol", "COBOL", "COBOL" }, 
            { ".cob", "COBOL", "COBOL" }, 
            { ".cbl", "COBOL", "COBOL" }, 
            { ".copy", "COPY", "COBOL" }, 
            { ".cpy", "COPY", "COBOL" },  
            { ".include", "INCLUDE", "COBOL" }, 
            { ".incl", "INCLUDE", "COBOL" },  
        };
                                                            //Tecnología asociada a estas extensiones.
    private static SwpgAbstract swpgxxfiDUMMY_CB = new SwpgcbfiGroupOfSegmentsFile();
                                                            //Se tienen las posible instancias.
    public static TechinstInstanceEnum[] arrtechinst_CB =
    {
        TechinstInstanceEnum.CB_IBM,
        TechinstInstanceEnum.CB_NEUTRAL,
    };

                                                            //Se debe generar y ordenar el arreglo.
    public static String[] arrstrFILE_EXTENSION_CB;

                                                            //Para OBJECT ORIENTED:

                                                            //La primer columna (FILE_EXTENSION) deben ser minúsculas o
                                                            //      dígitos.
                                                            //La segunda y tercer columna (FILE_DESCRIPTION y
                                                            //      CODE_TECHNOLOGY) No debe tener caracteres en blanco
                                                            //      [ ] | (esto se requiere para identificar
                                                            //      correctamente las tecnologías en el contador de
                                                            //      líneas de código).

    private static String[][] arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_OO = 
        { 
            { ".cs", "C#", "C#" }, 
            { ".java", "Java", "Java" },  
        };
                                                            //Tecnología asociada a estas extensiones.
    private static SwpgAbstract swpgxxfiDUMMY_OO = new SwpgoofiGroupOfSegmentsFile();
                                                            //Se tienen las posible instancias.
    public static TechinstInstanceEnum[] arrtechinst_OO =
    {
        TechinstInstanceEnum.OO_CSHARP,
        TechinstInstanceEnum.OO_JAVA,
    };

                                                            //Se debe generar y ordenar el arreglo.
    public static String[] arrstrFILE_EXTENSION_OO;

                                                            //Para CONJUNTO DE TECNOLOGIAS INTEGRADAS.

                                                            //La información anterior debe ser pasada a 4 arreglos y
                                                            //      ordenados los 4 por el primero.

    /*internal*/ public static String[] arrstrFILE_EXTENSION;
    /*internal*/ public static String[] arrstrFILE_DESCRIPTION;
    /*internal*/ public static String[] arrstrCODE_TECHNOLOGY;
    /*internal*/ public static SwpgAbstract[] arrswpgxxfiDUMMY;

    //------------------------------------------------------------------------------------------------------------------
    static                                             		//Prepara las constantes para poder utilizarlas.
    	                                                    //CADA VEZ QUE SE AÑADAN CONJUNTOS DE CONSTANTES QUE
    	                                                    //      REQUIERAN SER INICIALIZADAS, SE AÑADE LA LLAMADA A 
    	                                                    //      OTRO MÉTODO subPrepareConstants....
        
    {
    	                                                    //<<AÑADIR>>SE REQUIERE UN PARAMETRO ADICIONAL EN LA PARTE
    	                                                    //      INICIAL PARA CADA TECNOLOGÍA.
    	Oarrstr oarrstrFILE_EXTENSION_CB = new Oarrstr(Tech.arrstrFILE_EXTENSION_CB);
    	Oarrstr oarrstrFILE_EXTENSION_OO = new Oarrstr(Tech.arrstrFILE_EXTENSION_OO);
    	Oarrstr oarrstrFILE_EXTENSION = new Oarrstr(Tech.arrstrFILE_EXTENSION);
    	Oarrstr oarrstrFILE_DESCRIPTION = new Oarrstr(Tech.arrstrFILE_DESCRIPTION);
    	Oarrstr oarrstrCODE_TECHNOLOGY = new Oarrstr(Tech.arrstrCODE_TECHNOLOGY);
    	OarrswpgAbstract oarrswpgxxfiDUMMY = new OarrswpgAbstract(Tech.arrswpgxxfiDUMMY);
        Tech.subPrepareConstants(oarrstrFILE_EXTENSION_CB, oarrstrFILE_EXTENSION_OO, oarrstrFILE_EXTENSION,
        		oarrstrFILE_DESCRIPTION, oarrstrCODE_TECHNOLOGY, oarrswpgxxfiDUMMY);
        Tech.arrstrFILE_EXTENSION_CB = oarrstrFILE_EXTENSION_CB.v;
        Tech.arrstrFILE_EXTENSION_OO = oarrstrFILE_EXTENSION_OO.v;
        Tech.arrstrFILE_EXTENSION = oarrstrFILE_EXTENSION.v;
        Tech.arrstrFILE_DESCRIPTION = oarrstrFILE_DESCRIPTION.v;
        Tech.arrstrCODE_TECHNOLOGY = oarrstrCODE_TECHNOLOGY.v;
        Tech.arrswpgxxfiDUMMY = oarrswpgxxfiDUMMY.v;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    private Tech() {}										//No permite que se cree instancia de la clase.

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstants(                //Método de apoyo llamado en constructor estático. 
                                                            //Prepara las constantes para poder utilizarlas.
                                                            //1. Prepara arrstrFILE_EXTENSION_XX y ordena
                                                            //      arrtechinst_XX para cada tecnología.
                                                            //2. Prepara arrstrFILE_EXTENSION, arrstrFILE_DESCRIPTION.
                                                            //      arrstrCODE_TECHNOLOGY y arrswpgxxfiDUMMY.

                                                            //Arreglo de file extension para cada una de las tecnolgías
                                                            //      (ordenado).
    	Oarrstr arrstrFILE_EXTENSION_CB_O,
    	Oarrstr arrstrFILE_EXTENSION_OO_O,
                                                            //Arreglos con la información consolidada (ordenados por
                                                            //      el primero).
    	Oarrstr arrstrFILE_EXTENSION_O,
    	Oarrstr arrstrFILE_DESCRIPTION_O,
    	Oarrstr arrstrCODE_TECHNOLOGY_O,
    	OarrswpgAbstract arrswpgxxfiDUMMY_O
        )
    {
                                                            //Construye los nuevos arreglos de información consolidada.
                                                            //<<AÑADIR>>CADA NUEVA TECNOLOGÍA INCREMENTA EL TAMAÑO DE 
                                                            //      ESTOS ARREGLOS.
        arrstrFILE_EXTENSION_O.v =
            new String[Tech.arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_CB.length +
                Tech.arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_OO.length];
        arrstrFILE_DESCRIPTION_O.v = new String[arrstrFILE_EXTENSION_O.v.length];
        arrstrCODE_TECHNOLOGY_O.v = new String[arrstrFILE_EXTENSION_O.v.length];
        arrswpgxxfiDUMMY_O.v = new SwpgAbstract[arrstrFILE_EXTENSION_O.v.length];

                                                            //Prepara y ordena la información específica para cada
                                                            //      tecnología.
                                                            //<<AÑADIR>>SE REQUIERE UN CONJUNTO DE LÍNEAS PARA CADA 
                                                            //      TECNOLOGÍA.
        Tech.subPrepareTechSpecificArrays(Tech.arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_CB,
            arrstrFILE_EXTENSION_CB_O, Tech.arrtechinst_CB);
        Tech.subPrepareTechSpecificArrays(Tech.arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_OO,
            arrstrFILE_EXTENSION_OO_O, Tech.arrtechinst_OO);

                                                            //Pasa la información a arreglos consolidado.
                                                            //<<AÑADIR>>SE REQUIERE UN CONJUNTO DE LÍNEAS PARA CADA 
                                                            //      TECNOLOGÍA.
        int intStartInNewArrays = 0;
        Tech.subMoveToNewArrays(Tech.arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_CB,
            Tech.swpgxxfiDUMMY_CB, intStartInNewArrays, arrstrFILE_EXTENSION_O.v, arrstrFILE_DESCRIPTION_O.v,
            arrstrCODE_TECHNOLOGY_O.v, arrswpgxxfiDUMMY_O.v);
        intStartInNewArrays = arrstrFILE_EXTENSION_CB_O.v.length;
        Tech.subMoveToNewArrays(Tech.arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_OO,
            Tech.swpgxxfiDUMMY_OO, intStartInNewArrays, arrstrFILE_EXTENSION_O.v, arrstrFILE_DESCRIPTION_O.v,
            arrstrCODE_TECHNOLOGY_O.v, arrswpgxxfiDUMMY_O.v);

                                                            //CONJUNTO DE TECNOLOGIAS INTEGRADAS.

                                                            //Para ordenar los 4 arreglos por el primero, requiereN 2
                                                            //      copia del primer arreglo.
        String[] arrstrFILE_EXTENSION_COPY_1 = new String[arrstrFILE_EXTENSION_O.v.length];
        String[] arrstrFILE_EXTENSION_COPY_2 = new String[arrstrFILE_EXTENSION_O.v.length];
        System.arraycopy(arrstrFILE_EXTENSION_O.v, 0, arrstrFILE_EXTENSION_COPY_1, 0, arrstrFILE_EXTENSION_O.v.length);
        System.arraycopy(arrstrFILE_EXTENSION_O.v, 0, arrstrFILE_EXTENSION_COPY_2, 0, arrstrFILE_EXTENSION_O.v.length);

                                                            //Ordena los 4 arreglos por el primero.
        Tools.sort(arrstrFILE_EXTENSION_O.v, arrstrFILE_DESCRIPTION_O.v);
        Tools.sort(arrstrFILE_EXTENSION_COPY_1, arrstrCODE_TECHNOLOGY_O.v);
        Tools.sort(arrstrFILE_EXTENSION_COPY_2, arrswpgxxfiDUMMY_O.v);

    	                                                    //Verifica duplicados.
        for (int intI = 1; intI < arrstrFILE_EXTENSION_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Esta duplicada.
                arrstrFILE_EXTENSION_O.v[intI] == arrstrFILE_EXTENSION_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrstrFILE_EXTENSION_O.v, "arrstrFILE_EXTENSION_O") + ", " +
                    Tes2.strTo(arrstrFILE_EXTENSION_O.v[intI], "arrstrFILE_EXTENSION_O[" + intI + "]") +
                    " is duplicated");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    static void subPrepareTechSpecificArrays(               //1. Genera y ordena arrstrFILE_EXTENSION_XX_O.
    	                                                    //2. Ordena arrtechinstINSTANCE_XX_IO.

    	                                                    //Información de una tecnología.
        String[][] arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I,
    	                                                    //Arreglo de file extension de una tecnología (ordenado).
        Oarrstr arrstrFILE_EXTENSION_XX_O,
    	                                                    //Ordena las instancias.
        TechinstInstanceEnum[] arrtechinst_XX_IO
        )
    {
        arrstrFILE_EXTENSION_XX_O.v =
            new String[arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I.length];

    	                                                    //Mueve a arreglo individual.
        for (int intI = 0; intI < arrstrFILE_EXTENSION_XX_O.v.length; intI = intI + 1)
        {
            String strFILE_EXTENSION = arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][0];
            if (
                                                            //FILE EXTENSION tiene mayúsculas.
                strFILE_EXTENSION != strFILE_EXTENSION.toLowerCase()
                )
                Tools.subAbort(
                    Tes2.strTo(arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][0],
                        "arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[" + intI + "][0]") +
                    ", FILE EXTENSION should be only lower letters");

            SyspathPath syspathFileA = new SyspathPath("A" + strFILE_EXTENSION);
            if (
                                                            //FILE EXTENSION inválida.
                !syspathFileA.boolIsValid()
                )
                Tools.subAbort(
                    Tes2.strTo(arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][0],
                        "arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[" + intI + "][0]") +
                    ", FILE EXTENSION is invalid");

            String strFILE_DESCRIPTION =
                arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][1];
            if (
                                                            //FILE EXTENSION tiene mayúsculas.
                strFILE_DESCRIPTION.contains(" ") || strFILE_DESCRIPTION.contains("[") ||
                    strFILE_DESCRIPTION.contains("]") || strFILE_DESCRIPTION.contains("|")
                )
                Tools.subAbort(
                    Tes2.strTo(arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][1],
                        "arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[" + intI + "][1]") +
                    ", FILE DESCRIPTION contains space, [, ] or |");

            String strCODE_TECHNOLOGY =
                arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][2];
            if (
                                                            //FILE EXTENSION tiene mayúsculas.
                strCODE_TECHNOLOGY.contains(" ") || strCODE_TECHNOLOGY.contains("[") ||
                    strCODE_TECHNOLOGY.contains("]") || strCODE_TECHNOLOGY.contains("|")
                )
                Tools.subAbort(
                    Tes2.strTo(arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][2],
                        "arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[" + intI + "][2]") +
                    ", contains space,, [, ] or |");

            arrstrFILE_EXTENSION_XX_O.v[intI] =
                arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][0];
        }

        Arrays.sort(arrstrFILE_EXTENSION_XX_O.v);

    	                                                    //Verifica duplicados.
        for (int intI = 1; intI < arrstrFILE_EXTENSION_XX_O.v.length; intI = intI + 1)
        {
            if (
                                                            //Esta duplicada.
                arrstrFILE_EXTENSION_XX_O.v[intI] == arrstrFILE_EXTENSION_XX_O.v[intI - 1]
                )
                Tools.subAbort(Tes2.strTo(arrstrFILE_EXTENSION_XX_O.v, "arrstrFILE_EXTENSION_XX_O") + ", " +
                    Tes2.strTo(arrstrFILE_EXTENSION_XX_O.v[intI], "arrstrFILE_EXTENSION_XX_O[" + intI + "]") +
                    ", is duplicated");
        }

    	                                                    //Ordena las instancias.
        Arrays.sort(arrtechinst_XX_IO);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    static void subMoveToNewArrays(                         //Añade información a los arreglos consolidados.

    	                                                    //Información de una tecnología.
        String[][] arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I,
        SwpgAbstract swpgxxfiDUMMY_XX_I,
    	                                                    //Desplazamiento para colocar la información en los arreglos
    	                                                    //      consolidados.
        int intStartInNewArrays_I,
    	                                                    //Acumula información a los 4 arreglos consolidados.
        String[] arrstrFILE_EXTENSION_M,
        String[] arrstrFILE_DESCRIPTION_M,
        String[] arrstrTECHNOLOGY_M,
        SwpgAbstract[] arrswpgxxfiDUMMY_M
        )
    {
    	                                                    //Mueve a arreglos consolidados.
        for (int intI = 0; intI < arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I.length;
            intI = intI + 1)
        {
            arrstrFILE_EXTENSION_M[intStartInNewArrays_I + intI] =
                arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][0];
            arrstrFILE_DESCRIPTION_M[intStartInNewArrays_I + intI] =
                arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][1];
            arrstrTECHNOLOGY_M[intStartInNewArrays_I + intI] =
                arr2strFILE_EXTENSION_AND_FILE_DESCRIPTION_AND_CODE_TECHNOLOGY_XX_I[intI][2];
            arrswpgxxfiDUMMY_M[intStartInNewArrays_I + intI] = swpgxxfiDUMMY_CB;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/