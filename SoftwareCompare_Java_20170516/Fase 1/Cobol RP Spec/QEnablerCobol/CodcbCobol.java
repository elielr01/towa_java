/*TASK RPS.Cod Relevant Part Code*/
package QEnablerCobol;

import QEnablerBase.*;
import TowaInfrastructure.*; 
import javax.swing.*;

                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 15-Febrero-2011.
                                                            //PURPOSE:
                                                            //Especificación de clases para Código Cobol.

//======================================================================================================================
/*internal*/ public class CodcbCobol extends CodCodeAbstract
                                                            //Código completo de un programa COBOL.
                                                            //Las líneas en COBOL son exactamente de 80 columnas, sin
                                                            //      embargo solo las cols. 7 a 72 (pos. 6 a 71) son
                                                            //      código útil.
                                                            //Si alguna línea tiene mas de 72 columnas, para procesarla
                                                            //      se corta.
                                                            //También se eliminan los blancos a la derecha.
                                                            //También se eliminan las primeras 6 columnas.
                                                            //Dado lo anterior, la línea del programa resultará de
                                                            //      máximo 66 caracteres.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    public static CodcbCobol codcbDUMMY_UNIQUE_Z = new CodcbCobol();
    @Override
    public CodCodeAbstract codDUMMY_UNIQUE() { return CodcbCobol.codcbDUMMY_UNIQUE_Z;  }

                                                            //File extension valida en COBOL.
    @Override
    public String[] arrstrFILE_EXTENSION()
    { return SoftwareAutomation.Tech.arrstrFILE_EXTENSION_CB;}

                                                            //El siguiente String son caracteres utiles en Cobol IBM
                                                            //      (codificación EBCDIC) que no requeriren ser
                                                            //      convertidos.
    private static final String strCHAR_USEFUL =
                                                            //Blanco y caracteres normales, no requieren conversión.
        " " + "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                                                            //Letras minúsculas.
        "abcdefghijklmnopqrstuvwxyz" +
                                                            //Caracteres especiales, no requiren conversión.
        "¢.<(+|&!$*);¬-/,%_>?:#@'=" +
                                                            //Caracteres especiales que requieren @ para su definción en
                                                            //      C#, no requieren conversión.
        "\"";

                                                            //Información anterior en un arreglo ordenado.
    private static /*readonly*/ char[] arrcharUSEFUL_Z;
    @Override
    public char[] arrcharUSEFUL() {return CodcbCobol.arrcharUSEFUL_Z;}

                                                            //El siguiente String son pares de caracteres, donde el
                                                            //      caracter en posición par (0, 2, etc.) deberá ser
                                                            //      convertido al siguiente carácter (1, 3, etc.) para
                                                            //      poder ser procesado. Los caracteresen posición par
                                                            //      no estan en arrcharUSEFUL y los caracteres en
                                                            //      posición impar si.

    private static final String strCHAR_TO_CONVERT_AND_CONVERSION =
                                                            //Comillas dobles y simples inclinadas a verticales.
        "“" + "\"" + "”" + "\"" + "‘'’'" +
                                                            //Caracteres acentuados, se eliminan los acentos.
        "ÁAÉEÍIÓOÚUÀAÈEÌIÒOÙUÄAËEÏIÖOÜUÂAÊEÎIÔOÛU" + "áaéeíióoúuàaèeìiòoùuäaëeïiöoüuâaêeîiôoûu" + "ÑNñn" +
                                                            //Caracteres especiales, que deseamos convertir.
        "[(]){(})©c¿?¡!" +
                                                            //Caracteres especiales que requieren @ para su definción en
                                                            //      C#, que deseamos convertir. En java se utiliza "\"
                                                            //      para definirlas.
        "\\" +  "/" +
                                                            //Otros caracteres que aparecen en el teclado de la mac.
        "ºoªaÇCçc";

                                                            //Los caracteres en posición par convertidos al primer
                                                            //      arreglo, los caracteres en posición impar
                                                            //      convertidos al segundo arreglo.
                                                            //Ambos arreglos deben ordenarse por el primero.
    private static /*readonly*/ char[] arrcharTO_CONVERT_Z;
    @Override
    public char[] arrcharTO_CONVERT() {return CodcbCobol.arrcharTO_CONVERT_Z;}
    private static /*readonly*/ char[] arrcharCONVERSION_Z;
    @Override
    public char[] arrcharCONVERSION() {return CodcbCobol.arrcharCONVERSION_Z;}

                                                            //DEFINICIONES GENERALES.

    private static /*readonly*/ int intSTART_CODE_Z = 6;
    @Override
    public int intSTART_CODE() {return CodcbCobol.intSTART_CODE_Z;}

    private static /*readonly*/ boolean boolIS_FIX_LENGTH_Z = true;
    @Override
    public boolean boolIS_FIX_LENGTH() {return CodcbCobol.boolIS_FIX_LENGTH_Z;}

    private static /*readonly*/ int intLINE_MAX_LENGTH_Z = 66;
    @Override
    public int intLINE_MAX_LENGTH() {return CodcbCobol.intLINE_MAX_LENGTH_Z;}

    /*TO ACCESS CONSTANTS FROM OTHER CLASSES*/

    /*TASK RPS.Com Relevant Part Comments*/
    @Override
    public ComCommentsAbstract comDUMMY() { return ComcbCobol.comcbDUMMY_UNIQUE_Z; }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    static                                                  //A partir de las constantes definidas, genera otras
                                                            //      constantes para facilitar el proceso.
    {
    	Oarrchar oarrcharUSEFUL_Z = new Oarrchar(CodcbCobol.arrcharUSEFUL_Z);
    	Oarrchar oarrcharTO_CONVERT_Z = new Oarrchar(CodcbCobol.arrcharTO_CONVERT_Z);
    	Oarrchar oarrcharCONVERSION_Z = new Oarrchar(CodcbCobol.arrcharCONVERSION_Z);

        CodCodeAbstract.subPrepareConstants(CodcbCobol.strCHAR_USEFUL, oarrcharUSEFUL_Z,
                CodcbCobol.strCHAR_TO_CONVERT_AND_CONVERSION, oarrcharTO_CONVERT_Z, oarrcharCONVERSION_Z);

        CodcbCobol.arrcharUSEFUL_Z = oarrcharUSEFUL_Z.v;
        CodcbCobol.arrcharTO_CONVERT_Z = oarrcharTO_CONVERT_Z.v;
        CodcbCobol.arrcharCONVERSION_Z = oarrcharCONVERSION_Z.v;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/


    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void subReset()
    {
        super.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        String strObjId = Tes2.strGetObjId(this);

        return strObjId + "[" + super.strTo(TestoptionEnum.SHORT) + "]";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        String strObjId = Tes2.strGetObjId(this);

        return strObjId + "{}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    public CodcbCobol() { super(); }

    //------------------------------------------------------------------------------------------------------------------
    public CodcbCobol(                                      //Crea código Cobol con arreglo de Strings.
                                                            //this.*[O], asigna valores.

        CodtypeEnum codtype_I,
        SyspathPath syspathFile_G,
        String[] arrstrLine_G
        )
    {
        super(codtype_I, syspathFile_G, arrstrLine_G);

                                                            //Debe abortar si tiene un char invalido en pos. 0.
        this.subReviewLinesChar0(true);


        this.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    public CodcbCobol(                                      //Crea objeto código COBOL con archivo.
                                                            //this.*[O], asigna valores.

        SyspathPath syspathFile_I
        )
    {
        super(syspathFile_I);

                                                            //Si el char 0 de alguna línea no es válido, envía mensaje
                                                            //      (no aborta).
        this.subReviewLinesChar0(false);

        this.subReset();
    }

    /*TASK Cod2 CodAbstract(arrstr & file)*/
    //------------------------------------------------------------------------------------------------------------------
    private void subReviewLinesChar0(                       //Los registros, en la columna 7 (posición 0) deben tener
                                                            //      "*"(indicador de comentarios), "/"(indicador de
                                                            //      brinco de hoja en el listado de compilación) o
                                                            //      espacio.
                                                            //Al concluir la verificación de todos los registros se
                                                            //      reporta la cantidad de registros inválidos con un
                                                            //      mensaje y se permite continuar.
                                                            //this[I], toma info del objeto

                                                            //true, aborta si hay un inicio invalido
                                                            //false, envía mensaje si hay un inicio invalido
        boolean boolAbort
        )
    {
        int intLinesInvalid = 0;

                                                            //Verifica primer caracter de cada línea.
        for (int intI = 0; intI < this.arrstrLine().length; intI = intI + 1)
        {
                                                            //To easy code
            String strLine = this.arrstrLine()[intI];

            if (
                                                            //La línea esta totalmente en espacios
                strLine.equals("")
                )
            {
                                                            //No hace nada.
            }
            else
            {
                                                            //To easy code
                char char0 = strLine.charAt(0);

                if (
                                                            //Es uno de los caracteres permitido en la primera
                                                            //      posición.
                    (char0 == ' ') || (char0 == '*') || (char0 == '/')
                    )
                {
                                                            //No hace nada.
                }
                else
                {
                                                            //En ocasiones (Ej. cuando se tiene un file) es normal que
                                                            //      este aquí, si hay algo invalido y deba continuar, en
                                                            //      otras (Ej. cuando se tiene un arrstr) todo debe
                                                            //      estar correcto.

                    if (
                        boolAbort
                        )
                        Tools.subAbort(Tes2.strTo(this.codtype(), "codtype") + ", " +
                            Tes2.strTo(this.syspathFile(), "syspathFile") + ", " +
                            Tes2.strTo(strLine, "this.arrstrLine[" + intI + "]") +
                            " do not begin withn *, / or blank");

                    intLinesInvalid = intLinesInvalid + 1;

                    JOptionPane.showMessageDialog(null, Tes2.strTo(this.codtype(), "codtype") + ", " +
                        Tes2.strTo(this.syspathFile(), "syspathFile") + ", " +
                        Tes2.strTo(strLine, "this.arrstrLine[" + intI + "]") +
                        " do not begin withn *, / or blank");
                }
            }
        }

        if (
                                                            //Encontró al menos 1 línea inválida.
            intLinesInvalid >= 1
            )
        {
            JOptionPane.showMessageDialog(null,
                Tes2.strTo(this.syspathFile().strFullPath(), "this.syspathFile.strFullPath") + ", " +
                Tes2.strTo(intLinesInvalid, "intLinesInvalid") + " at least one line is invalid");
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //TODO está redefinido de nuevo
    /*
    /*TASK Cod2 CodAbstract(file)* /
    //--------------------------------------------------------------------------------------------------------------
    public CodcbCobol(                                      //Crea objeto código COBOL con archivo.
                                                            //Los registros, en la columna 7 (posición 0) deben tener
                                                            //      "*"(indicador de comentarios), "/"(indicador de
                                                            //      brinco de hora en el listado de compilación) o
                                                            //      espacio, en caso de tener algo diferente se reporta
                                                            //      con un mensaje y se acepta el registro.
                                                            //Al concluir la verificación de todos los registros se
                                                            //      reporta la cantidad de registros inválidos con un
                                                            //      mensaje y se permite continuar.
                                                            //this.*[O], asigna valores.

        SyspathPath syspathFile_I
        )
    {
        super(syspathFile_I);

        int intLinesInvalid = 0;

                                                            //Verifica primer caracter de cada línea.
        for (int intI = 0; intI < this.arrstrLine().length; intI = intI + 1)
        {
                                                            //To easy code
            String strLine = this.arrstrLine()[intI];

            if (
                                                            //La línea esta totalmente en espacios
                strLine == ""
                )
            {
                                                            //No hace nada.
            }
            else
            {
                                                            //To easy code
                char char0 = strLine.charAt(0);

                if (
                                                            //Es uno de los caracteres permitido en la primera
                                                            //      posición.
                    (char0 == ' ') || (char0 == '*') || (char0 == '/')
                    )
                {
                                                            //No hace nada.
                }
                else
                {
                    intLinesInvalid = intLinesInvalid + 1;

                    JOptionPane.showMessageDialog(null,
                        Tes2.strTo(this.syspathFile().strFullPath(), "syspathFile.strFullPath") + ", " +
                        Tes2.strTo(strLine, "arrstrLine[" + intI + "]") + " do not begin withn *, / or blank");
                }
            }
        }

        if (
                                                            //Encontró al menos 1 línea inválida.
            intLinesInvalid >= 1
            )
        {
            JOptionPane.showMessageDialog(null,
                Tes2.strTo(this.syspathFile().strFullPath(), "syspathFile.strFullPath") + ", " +
                Tes2.strTo(intLinesInvalid, "intLinesInvalid") + " at least one line is invalid");
        }

        this.subReset();
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK* /
    */

    /*TASK RPS.Com Relevant Part Comments*/
    //------------------------------------------------------------------------------------------------------------------
    public CodcbCobol(                                      //Crea código estándar de comentarios.
                                                            //this.*[O], asigna valores.

        ComCommentsAbstract com_I
        )
    {
        super(com_I);

        this.subReset();
    }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
                                                            //Para construir objetos del tipo correcto desde clases
                                                            //      abstractas.

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public CodCodeAbstract codxxNew(CodtypeEnum codtype_I, SyspathPath syspathFile_I, String[] arrstrLine_I)
    {
        return new CodcbCobol(codtype_I, syspathFile_I, arrstrLine_I);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public CodCodeAbstract codxxNew(SyspathPath syspathFile_I)
    {
        return new CodcbCobol(syspathFile_I);
    }
    //------------------------------------------------------------------------------------------------------------------

    /*TASK RPS.Com Relevant Part Comments*/
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public CodCodeAbstract codxxNew(ComCommentsAbstract com_I)
    {
        return new CodcbCobol((ComcbCobol)com_I);
    }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/


    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/
