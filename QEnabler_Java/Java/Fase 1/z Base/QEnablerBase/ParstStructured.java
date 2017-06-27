package QEnablerBase;

import TowaInfrastructure.*;

                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 23-Julio-2012.
                                                            //PURPOSE:
															//Implementación de Paragraph.
public class ParstStructured extends ParParagraphAbstract
                                                            //Base para clases ParstxxXxxxx.
                                                            //Información de párrafo estructurado.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

                                                            //Se guarda la información como un arreglo de líneas de un
                                                            //      párrafo estructurado (es el código tal cual, solo se
                                                            //      eliminan los espacios a la derecha).
                                                            //Nótese que alguna línea del párrafo estructurado puede ser
                                                            //      demasiado grandes y deberá ser truncada al producir
                                                            //      el código estandarizado.
    private String[] arrstrLineParst_Z;
    public String[] arrstrLineParst() { return this.arrstrLineParst_Z; }

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

            //                                      //Asigna líneas del Código Estándar del parst.
            this.arrstrLineParStandard_Z = this.arrstrLineParst();

            this.subSetIsResetOff();
        }

        return arrstrLineParStandard_Z;
    }

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
        return super.strTo(TestoptionEnum.SHORT) + ", " + Tes2.strTo(this.arrstrLineParst(), TestoptionEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        final String strCLASS = "Parst";

        return strCLASS + "{" + Tes2.strTo(this.arrstrLineParst(), "arrstrLineParst") + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    /*TASK Com3 ComCommentsAbstract(cod)*/
    //------------------------------------------------------------------------------------------------------------------
    public ParstStructured(                                 //Construye párrafo estructurado, este puede pertenecer a
                                                            //      com FULL_LINE, END_OF_LINE o DELIMITED
                                                            //Para cada uno de los tipos existe un tamaño de línea
                                                            //      límite, si se excede el límite la línea se trunca.
                                                            //En el caso de FULL_LINE y END_OF_LINE la última
                                                            //      línea del código debe iniciar con la marca de cierre
                                                            //      (charPARST_END) si no la tiene se añade una línea
                                                            //      solo con esa marca.
                                                            //this.*[O], asigna valores.

                                                            //Objeto com del cual este párrafo es parte.
        ComCommentsAbstract comBelongsTo_T,
                                                            //Líneas para párrafo estructurado.
        String[] arrstrLineParst_I
        )
    {
        super(comBelongsTo_T);
        
                                                            //Saca el com para facilitar la codificación
        ComCommentsAbstract com = this.comBelongsTo();

        if (!(
            (com.comtype() == ComtypeEnum.FULL_LINE) ||
            (com.comtype() == ComtypeEnum.END_OF_LINE) ||
            (com.comtype() == ComtypeEnum.DELIMITED)
            ))
            Tools.subAbort(Tes2.strTo(com.comtype(), "com.comtype()") + " is not valid for a parst");

                                                            //Determina si falta la marca de cierre.
        boolean boolEndMissing;
        if (
            com.comtype() == ComtypeEnum.DELIMITED
            )
        {
                                                            //No requiere marca de fin.
            boolEndMissing = false;
        }
        else
        {
                                                            //Necesariamene tiene al menos 1 línea.
                                                            //Nótese que la última línea podría ser una linea que no
                                                            //      tiene nada.
            String strLineLast = arrstrLineParst_I[arrstrLineParst_I.length - 1];

            boolEndMissing = (!(
                //                                      //Tiene la marca de fin.
                (strLineLast.length() > 0) && (strLineLast.charAt(0) == com.charPARST_END())
                ));
        }

                                                            //Calcula la longitud del arreglo, será la que tiene y tal 
                                                            //      vez 1 más.
        int intArrayLength = arrstrLineParst_I.length;
        if (
                                                            //Falta la marca de cierre.
            boolEndMissing
            )
        {
            intArrayLength = intArrayLength + 1;
        }

                                                            //Creo el arreglo de la longitud correcta.
        String[] arrstrLineParst = new String[intArrayLength];

                                                            //Meto la información al arreglo.
        System.arraycopy(arrstrLineParst_I, 0, arrstrLineParst, 0, arrstrLineParst_I.length);

        if (
            boolEndMissing
            )
        {
                                                            //Requiere añadir una línea con la marca de fin de 
                                                            //      estructurado.
            arrstrLineParst[arrstrLineParst.length - 1] = Character.toString(com.charPARST_END());
        }

                                                            //Determina el tamaño máximo para cortar líneas que excedan,
                                                            //      esto será el total de caracteres que caben lo cual 
                                                            //      depende del tipo de comentario donde está el
                                                            //      párrafo.
        int intST_MAX_LINE_SIZE;
        /*CASE*/
        if (
            com.comtype() == ComtypeEnum.FULL_LINE
            )
        {
            intST_MAX_LINE_SIZE = com.intCOM_FL_OR_TL_LINE_SIZE() - com.strCOM_FL_OR_TL().length();
        }
        else if (
            com.comtype() == ComtypeEnum.END_OF_LINE
            )
        {
            intST_MAX_LINE_SIZE = com.intCOM_EL_LINE_SIZE() - com.strCOM_EL().length();
        }
        else
        {
                                                            //Debe ser DELIMITED
            intST_MAX_LINE_SIZE = com.intCOM_DE_LINE_SIZE();
        }
        /*END-CASE*/

                                                            //Corta las líneas que excenden el tamaño máximo.
        for (int intI = 0; intI < arrstrLineParst.length; intI = intI + 1)
        {
            if (
                arrstrLineParst[intI].length() > intST_MAX_LINE_SIZE
                )
            {
                                                            //Se excede el tamaño, la corta (le vuelve a quitar
                                                            //      espacios a la derecha).
                arrstrLineParst[intI] = Tools.trimEnd(arrstrLineParst[intI].substring(0,intST_MAX_LINE_SIZE), ' ');
            }
        }

        this.arrstrLineParst_Z = arrstrLineParst;

        this.subReset();
    }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    /*TASK Com4 subfunSearchWord*/
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public int intSearchWord(                               //Busca palabra en párrafo estructurado.

                                                            //int, posición donde encuentra la palabra, acumula 1,000
                                                            //      posiciones por cada línea anterior y la posición
                                                            //      donde encontró.

                                                            //this[I], define el objeto.

                                                            //Palabra a buscar.
        String strWord_I
        )
    {
        this.subVerifyObjectConstructionIsFinished();

        int intLineInParst = 0;

        int intChar;
        /*LOOP*/
        while (true)
        {
            //                                          //To easy code
            //                                          //Busca la palabra en la línea.
            if (
                intLineInParst < this.arrstrLineParst().length
                )
            {
                intChar = Tools.intSearchWordInString(strWord_I, this.arrstrLineParst()[intLineInParst]);
            }
            else
            {
                //                                      //Indica que no encontró.
                intChar = -1;
            }

            /*EXIT-IF*/
            if (
                (intLineInParst >= this.arrstrLineParst().length) ||
                //                                      //Encontró palabra en la línea.
                (intChar >= 0)
                )
                break;

            intLineInParst = intLineInParst + 1;
        }
        /*END-LOOP*/

        //                                              //Calcula la "posición" en el párrafo.
        int intSearchWord;
        if (
            //                                          //Si encontró palabra.
            intLineInParst >= this.arrstrLineParst().length
            )
        {
            intSearchWord = -1;
        }
        else
        {
            intSearchWord = intLineInParst * 1000 + intChar;
        }

        return intSearchWord;
    }
    /*END-TASK*/
}
