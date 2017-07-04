package QEnablerBase;

import TowaInfrastructure.*;

import java.util.Arrays;
import java.util.LinkedList;

//AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 23-Julio-2012.
                                                            //PURPOSE:
															//Implementación de Paragraph.
public class ParunUnstructured extends ParParagraphAbstract
                                                            //Base para clases ParunxxXxxxx.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

                                                            //Se guarda la información como un conjunto de palabras
                                                            //      consecutivas separadas por un espacio.
                                                            //Nótese que un párrafo puede contener palabras demasiado
                                                            //      grandes que debrán ser truncadas al producir el
                                                            //      código estandarizado.
    private String strParagraph_Z;
    public String strParagraph() { return this.strParagraph_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    private String[] arrstrLineParStandard_Z;
    @Override
    public String[] arrstrLineStandard()
    {
                                                            //Recalcula si el par es nuevo o ya fue reseteado.
        if (
            this.arrstrLineParStandard_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Construye líneas del Código Estándar del parun.
            this.arrstrLineParStandard_Z = this.arrstrParunToStandard();

            this.subSetIsResetOff();
        }

        return arrstrLineParStandard_Z;
    }
    
    /*TASK Com5 arrstrParunToStandard*/
    //------------------------------------------------------------------------------------------------------------------
    private String[] arrstrParunToStandard(                 //Construye líneas de Código Estandar de un parun.
                                                            //El código estándar de un párrafo no incluye los caracteres
                                                            //      de inicio del comentario ni los delimitadores.
                                                            //Este tipo de párrafo es posible en todos los tipos de 
                                                            //      comentarios (FULL_LINE, TAG_FULL_LINE, END_OF_LINE, 
                                                            //      DELIMITED y EMBEDED).
                                                            //Nótese que el párrafo contendrá solo palabras que SÍ caben
                                                            //      correctamente en la ínea de código, si había palabra
                                                            //      grandes estas fueron cortadas al construir el 
                                                            //      párrafo.
                                                            //this.*[O], asigna valores. 
        )
    {
                                                            //Saco el com para facilitar la codificación.
        ComCommentsAbstract com = this.comBelongsTo();

        String[] arrstrParunToStandard;
                                                            //El parun puede ser de un com de varios tipos.
        /*CASE*/
        if (
            com.comtype() == ComtypeEnum.FULL_LINE
            )
        {
                                                            //La palabras del párrafo deben ser "puestas" en varías 
                                                            //      líneas de código conforme al formato requerido por 
                                                            //      FULL_LINE.
            arrstrParunToStandard = ParunUnstructured.arrstrCodStandardParunFlOrEl(this.strParagraph(), 
                    com.intCOM_FL_OR_TL_LINE_SIZE() - com.strCOM_FL_OR_TL().length(),
                    com.strPARUN_INDENT_FL_FIRST_LINE(), com.strPARUN_INDENT_FL_OTHER_LINES());
        }
        else if (
            com.comtype() == ComtypeEnum.END_OF_LINE
            )
        {
                                                            //La palabras del párrafo deben ser "puestas" en varías 
                                                            //      líneas de código conforme al formato requerido por 
                                                            //      END_OF_LINE.
            arrstrParunToStandard = ParunUnstructured.arrstrCodStandardParunFlOrEl(this.strParagraph(),
                    com.intCOM_EL_LINE_SIZE() - com.strCOM_EL().length(), com.strPARUN_INDENT_EL_FIRST_LINE(),
                    com.strPARUN_INDENT_EL_OTHER_LINES());
        }
        else if (
            com.comtype() == ComtypeEnum.TAG_FULL_LINE
            )
        {
                                                            //En TAG_FULL_LINE será: <parrafo>

            String strLineOnlyOne = com.charCOM_TL_START() + this.strParagraph() + com.charCOM_TL_END();

                                                            //Le quita la marca > si no cabe.
            int intLengMax = com.intCOM_FL_OR_TL_LINE_SIZE() - com.strCOM_FL_OR_TL().length();
            if (
                strLineOnlyOne.length() > intLengMax
                )
            {
                strLineOnlyOne = strLineOnlyOne.substring(0, intLengMax);
            }

            arrstrParunToStandard = new String[] { strLineOnlyOne };
        }
        else if (
            (com.comtype() == ComtypeEnum.EMBEDED) || (com.comtype() == ComtypeEnum.DELIMITED)
            )
        {
                                                            //En EMBEDED o DELIMITED todo el código del párrafo es la
                                                            //      información del párrafo tal y como esta, en una sola
                                                            //      línea (sin añadir nada antes o después).
            arrstrParunToStandard = new String[] { this.strParagraph() };
        }
        else
        {
            arrstrParunToStandard = null;
            if (
                true
                )
                Tools.subAbort(Tes2.strTo(this.strParagraph(), "this.strParagraph()") + ", " +
                    Tes2.strTo(com.comtype(), "com.comtype()") + " should not include parun");
        }
        /*END-CASE*/

        return arrstrParunToStandard;
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
        Oint ointPiece = new Oint(0);
        /*WHILE_DO*/
        while (
            ointPiece.v < strParagraph_I.length()
            )
        {
            Ostring ostrLineParun = new Ostring();
            ParunUnstructured.subGenerateLineFromPieceAndAdvance(ostrLineParun, ointPiece, strParagraph_I,
                intPARUN_LINE_SIZE_I, strPARUN_INDENT_FIRST_LINE_I, strPARUN_INDENT_OTHER_LINES_I);

            lststrLineParun.add(ostrLineParun.v);

                                                            //Avanza al inicio del siguiente pedazo, nótese que también
                                                            //      debe avanzar un espacio. (obviamente no hay espacio
                                                            //      si ya estoy al final de párrafo).
            ointPiece.v = ointPiece.v + 2;
        }

        return lststrLineParun.toArray(new String[lststrLineParun.size()]);
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
                                                            //substring de java diferente de substring de C#, de ahi la
                                                            //      resta y despues suma de ointPiece_IO.v
        String strPiece = strParagraph_I.substring(ointPiece_IO.v, intPieceEndPlus1 - ointPiece_IO.v + ointPiece_IO.v);

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
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void subReset()
    {
        this.arrstrLineParStandard_Z = null;

        super.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        return super.strTo(TestoptionEnum.SHORT) + ", " + Tes2.strTo(this.strParagraph(), TestoptionEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        final String strCLASS = "Parun";

        return strCLASS + "{" + Tes2.strTo(this.strParagraph(), "strParagraph") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    /*TASK Com3 ComCommentsAbstract(cod)*/
    //------------------------------------------------------------------------------------------------------------------
    public ParunUnstructured(                               //Construye párrafo no estructurado, este puede pertenecer a
                                                            //      com FULL_LINE, TAG_FULL_LINE,
                                                            //      END_OF_LINE, EMBEDED o DELIMITED
                                                            //Junta todas las líneas y elimina espacios en exceso
                                                            //      (Trim).
                                                            //Si es FULL_LINE o END_OF_LINE, si hay palabras
                                                            //      que exceden el tamaño máximo se cortan, el tamaño
                                                            //      máximo es el que cabe en la línea segunda o
                                                            //      subsecuentes del código (Ej. en Cobol es 66 - 1 - 8
                                                            //      = 57, en C# es 60 - 2 - 6 = 52).
                                                            //Si esta en TAG_FULL_LINE, elimina las marcas delimitadoras
                                                            //      del tag (<>) incluyendo blancos intercalados.
                                                            //this.*[O], asigna valores.

                                                            //Objeto com del cual este párrafo es parte.
        ComCommentsAbstract comBelongsTo_T,
                                                            //Líneas para párrafo no estructurado, ya sin caracteres de
                                                            //      inicio o delimitadores.
        String[] arrstrLineParun_I
        )
    {
        super(comBelongsTo_T);
                                                            //Saca el com para facilitar la codificación
        ComCommentsAbstract com = this.comBelongsTo();

        String strParagraph;
                                                            //El código de un párrafo no estructurado se tiene en varios
                                                            //      tipos de comentarios.
        /*CASE*/
        if (
            (com.comtype() == ComtypeEnum.FULL_LINE) || (com.comtype() == ComtypeEnum.END_OF_LINE)
            )
        {
                                                            //Se tienes varias líneas consecutivas, pueden tener blancos
                                                            //      en exceso.

                                                            //Concatena todas las líneas con " " como separador.
            strParagraph = String.join(" ", arrstrLineParun_I);

            strParagraph = Tools.strTrimExcel(strParagraph);

                                                            //Hace lo necesario para cortar las palabras grandes.
            strParagraph = this.strParagraphRevised(strParagraph);
        }
        else if (
            com.comtype() == ComtypeEnum.TAG_FULL_LINE
            )
        {
                                                            //La información esta en una sola línea, tiene marca de tag
                                                            //      al inicio y posiblemente al final, también puede
                                                            //      tener blancos en exceso.

                                                            //Toma la única línea de información que tiene.
            strParagraph = arrstrLineParun_I[0];

                                                            //Quita marca de tag al principio y al final si la tiene.
            strParagraph = Tools.trimStart(strParagraph, com.charCOM_TL_START(), ' ');
            strParagraph = Tools.trimEnd(strParagraph, com.charCOM_TL_END(), ' ');

            strParagraph = Tools.strTrimExcel(strParagraph);

            if (
                com.codOriginal().boolIS_FIX_LENGTH()
                )
            {
                                                            //Si es de longitud fija puede requerir ser recortado.

                                                            //Nótese que este párrafo pudiera exceder el tamaño posible,
                                                            //      sucede cuando la línea estaba completa, no tenía
                                                            //      blancos en exceso y no incluía la marca >.
                                                            //Si la información original estaba en un arrstr (y no en un
                                                            //      file), la longítud puede excederse aún más.

                                                            //Long max = 66 - "*" - '<' - '>'
                int intLengthMaxPar = com.intCOM_FL_OR_TL_LINE_SIZE() - com.strCOM_FL_OR_TL().length() - 2;

                if (
                                                            //El párrafo resultante no cabe entre las marcas < >
                    strParagraph.length() > intLengthMaxPar
                    )
                {
                                                            //Lo recorta para que quepa.
                    strParagraph = strParagraph.substring(0, intLengthMaxPar);
                }
            }
        }
        else if (
            (com.comtype() == ComtypeEnum.EMBEDED) || (com.comtype() == ComtypeEnum.DELIMITED)
            )
        {
                                                            //La información esta en una sola línea, puede tener blancos
                                                            //      en exceso.

                                                            //Toma la única línea de información que tiene.
            strParagraph = arrstrLineParun_I[0];

            strParagraph = Tools.strTrimExcel(strParagraph);
        }
        else
        {
            if (
                true
                )
                Tools.subAbort(Tes2.strTo(com.comtype(), "com.comtype") + " was not found");

            strParagraph = null;
        }
        /*END-CASE*/

        this.strParagraph_Z = strParagraph;

        this.subReset();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String strParagraphRevised(                     //Corta palabras que excedan el tamaño deseado.
                                                            //this[I], para tomar constantes.
                                                            //str, regresa el párrafo con las palabras cortadas.

                                                            //Párrafo a revisar.
        String strParagraph_I
        )
    {
                                                            //Saca el com para facilitar la codificación
        ComCommentsAbstract com = this.comBelongsTo();

                                                            //Determina el tamaño máximo de palabra
        int intWORD_MAX_SIZE;
        if (
            com.comtype() == ComtypeEnum.FULL_LINE
            )
        {
            intWORD_MAX_SIZE = com.intCOM_FL_OR_TL_LINE_SIZE() - com.strCOM_FL_OR_TL().length() -
                com.strPARUN_INDENT_FL_OTHER_LINES().length();
        }
        else
        {
            intWORD_MAX_SIZE = com.intCOM_EL_LINE_SIZE() - com.strCOM_EL().length() -
                com.strPARUN_INDENT_EL_OTHER_LINES().length();
        }

                                                            //Determino si existe al menos 1 palabra que exede la
                                                            //      longitud máxima.
                                                            //Nótese que si es FL o EL al menos se tiene 1 palabra.
        int intStartWord = 0;
        /*LOOP*/
        while (true)
        {
                                                            //Determina donde termina la palabra donde esta posicionado.
            int intEndWordPlus1;
            if (
                intStartWord < strParagraph_I.length()
                )
            {
                intEndWordPlus1 = strParagraph_I.indexOf(' ', intStartWord);

                //                                      //Si no encontró es que era la última palabra.
                if (
                    //                                  //No encontro espacio.
                    intEndWordPlus1 < 0
                    )
                {
                    intEndWordPlus1 = strParagraph_I.length();
                }
            }
            else
            {
                //                                      //No usará este valor.
                intEndWordPlus1 = 9999;
            }

            /*EXIT-IF*/
            if (
                (intStartWord >= strParagraph_I.length()) ||
                //                                      //Esta en una palabra que excede el máximo.
                ((intEndWordPlus1 - intStartWord) > intWORD_MAX_SIZE)
                )
                break;

                                                            //Se posiciona en el inicio de la siguiente palabra, nótese
                                                            //      que end esta sobre el espacio inmediato anterior.70
            intStartWord = intEndWordPlus1 + 1;
        }

        boolean boolExistLongWord = (
                                                            //No llego al fin, significa que encontro palabra larga.
            intStartWord < strParagraph_I.length()
            );

                                                            //Nótese que en el ciclo podía haber arreglado las palabras
                                                            //      largas, sin embargo la lógica sería poco clara, dado
                                                            //      que esta situación solo sucede eventualmente, se
                                                            //      opto por una lógica más elegante aun cuando sea un 
                                                            //      poco ineficiente.

        String strParagraphRevised;
        if (
            boolExistLongWord
            )
        {
                                                            //Existe al menos 1 palabra larga, revisa para cortarla

                                                            //Genero una lista con todas las palabra que integran el
                                                            //      párrafo, necesito que sea lista para poder separar
                                                            //      las palabras grandes.
            String[] arrstrWord = strParagraph_I.split(" ");
            LinkedList<String> lststrWord = new LinkedList<String>(Arrays.asList(arrstrWord));

                                                            //Nótese que dentro del ciclo el tamaño de la lista se 
                                                            //      incrementa cada ves que se corta una palabra.
            for (int intI = 0; intI < lststrWord.size(); intI = intI + 1)
            {
                if (
                    lststrWord.get(intI).length() > intWORD_MAX_SIZE
                    )
                {
                                                            //Separa la palabra en 2
                    lststrWord.add(intI + 1, lststrWord.get(intI).substring(intWORD_MAX_SIZE));
                    lststrWord.set(intI, lststrWord.get(intI).substring(0, intWORD_MAX_SIZE));
                }

                                                            //Nótese que la siguiente I podrá ser la segunda parte de la
                                                            //      palabra que se recorto y fue insertada, esta a su
                                                            //      vez pudiera ser una palabra larga (esto solo puede
                                                            //      suceder en lenguages con líneas que longitud
                                                            //      variable cuando se codificaron líneas muy largas).
            }

            strParagraphRevised = String.join(" ", lststrWord.toArray(new String[lststrWord.size()]));
        }
        else
        {
            strParagraphRevised = strParagraph_I;
        }

        return strParagraphRevised;
    }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public int intSearchWord(                               //Busca palabra en párrafo no estructurado.
                                                            //int, posición donde encuentra la palabra, -1 si no
                                                            //      encontró.
                                                            //this[I], define el objeto.

                                                            //Palabra a buscar.
        String strWord_I
        )
    {
        this.subVerifyObjectConstructionIsFinished();

        return Tools.intSearchWordInString(strWord_I, this.strParagraph());
    }

    //------------------------------------------------------------------------------------------------------------------
}
