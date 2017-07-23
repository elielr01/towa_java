/*TASK RPS.Cod Relevant Part Code*/
package Cod;

//                                                          //AUTHOR: Towa (EOG-Eduardo Ojeda).
//                                                          //CO-AUTHOR: Towa (GLG-Gerardo López).
//                                                          //DATE: 15-Febrero-2011.
//                                                          //PURPOSE:
//                                                          //Especificación de clases para Código Cobol.

import Tech.*;
import Ti.*;

//======================================================================================================================
public class CodcbCobol extends CodCodeAbstract
//                                                          //Código completo de un programa COBOL.
//                                                          //Las líneas en COBOL son exactamente de 80 columnas, sin
//                                                          //      embargo solo las cols. 7 a 72 (pos. 6 a 71) son
//                                                          //      código útil.
//                                                          //Si alguna línea tiene mas de 72 columnas, para procesarla
//                                                          //      se corta.
//                                                          //También se eliminan los blancos a la derecha.
//                                                          //También se eliminan las primeras 6 columnas.
//                                                          //Dado lo anterior, la línea del programa resultará de
//                                                          //      máximo 66 caracteres.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/
    public static CodcbCobol codcbDUMMY_UNIQUE = new CodcbCobol();

    @Override public CodCodeAbstract codDUMMY() { return CodcbCobol.codcbDUMMY_UNIQUE; }

    private static T4techInfoTuple t4techINFO_I = Tech.t4techGet(TechtechEnum.COBOL);
    @Override public T4techInfoTuple t4techINFO() { return CodcbCobol.t4techINFO_I; }

    //                                                      //El siguiente String son caracteres utiles en Cobol IBM
    //                                                      //      (codificación EBCDIC) que no requeriren ser
    //                                                      //      convertidos.
    private static final String strCHAR_USEFUL =
    //                                                      //Blanco y caracteres normales, no requieren conversión.
    " " + "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        //                                                  //Letras minúsculas.
        "abcdefghijklmnopqrstuvwxyz" +
        //                                                  //Caracteres especiales, no requiren conversión.
        "¢.<(+|&!$*);¬-/,%_>?:#@'=" +
        //                                                  //Caracteres especiales que requieren @ para su definción en
        //                                                  //      C#, no requieren conversión.
        "\"";
    //                                                      //Información anterior en un arreglo ordenado.
    private static char[] arrcharUSEFUL_Z;
    @Override public char[] arrcharUSEFUL() { return CodcbCobol.arrcharUSEFUL_Z; }

    //                                                      //El siguiente String son pares de caracteres, donde el
    //                                                      //      carácter en posición par (0, 2, etc.) deberá ser
    //                                                      //      convertido al siguiente carácter (1, 3, etc.) para
    //                                                      //      poder ser procesado. Los caracteresen posición par
    //                                                      //      no estan en arrcharUSEFUL y los caracteres en
    //                                                      //      posición impar si.
    private static final String strCHAR_TO_CONVERT_AND_CONVERSION =
    //                                                      //Comillas dobles y simples inclinadas a verticales.
    "“" + "\"" + "”" + "\"" + "‘'’'" +
        //                                                  //Caracteres acentuados, se eliminan los acentos.
        "ÁAÉEÍIÓOÚUÀAÈEÌIÒOÙUÄAËEÏIÖOÜUÂAÊEÎIÔOÛU" + "áaéeíióoúuàaèeìiòoùuäaëeïiöoüuâaêeîiôoûu" + "ÑNñn" +
        //                                                  //Caracteres especiales, que deseamos convertir.
        "[(]){(})©c¿?¡!" +
        //                                                  //Caracteres especiales que requieren @ para su definción en
        //                                                  //      C#, que deseamos convertir.
        "\\" +  "/" +
        //                                                  //Otros caracteres que aparecen en el teclado de la mac.
        "ºoªaÇCçc";
    //                                                      //Los caracteres en posición par convertidos al primer
    //                                                      //      arreglo, los caracteres en posición impar
    //                                                      //      convertidos al segundo arreglo.
    //                                                      //Ambos arreglos deben ordenarse por el primero.
    private static char[] arrcharTO_CONVERT_Z;
    @Override public char[] arrcharTO_CONVERT() { return CodcbCobol.arrcharTO_CONVERT_Z; }
    private static char[] arrcharCONVERSION_Z;
    @Override public char[] arrcharCONVERSION() { return CodcbCobol.arrcharCONVERSION_Z; }

    //                                                      //Para construir a partir de syspath.

    @Override public boolean boolCONSTRUCT_FROM_SYSPATH() { return true; }

    //                                                      //This also indicate intSTART_CODE is 6.
    @Override public String strSTART_FILLER() { return "......"; }

    @Override public boolean boolIS_FIX_LENGTH() { return true; }
    @Override public int intLINE_MAX_LENGTH() { return 66; }

    //------------------------------------------------------------------------------------------------------------------
    static
    //                                                      //A partir de las constantes definidas, genera otras
    //                                                      //      constantes para facilitar el proceso.
    {
        Oarrchar oarrcharUSEFUL_Z = new Oarrchar(CodcbCobol.arrcharUSEFUL_Z);
        Oarrchar oarrcharTO_CONVERT_Z = new Oarrchar(CodcbCobol.arrcharTO_CONVERT_Z);
        Oarrchar oarrcharCONVERSION_Z = new Oarrchar(CodcbCobol.arrcharCONVERSION_Z);

        CodCodeAbstract.subPrepareConstants(CodcbCobol.codcbDUMMY_UNIQUE, CodcbCobol.strCHAR_USEFUL, oarrcharUSEFUL_Z,
            CodcbCobol.strCHAR_TO_CONVERT_AND_CONVERSION, oarrcharTO_CONVERT_Z, oarrcharCONVERSION_Z);

        CodcbCobol.arrcharUSEFUL_Z = oarrcharUSEFUL_Z.v;
        CodcbCobol.arrcharTO_CONVERT_Z = oarrcharTO_CONVERT_Z.v;
        CodcbCobol.arrcharCONVERSION_Z = oarrcharCONVERSION_Z.v;

        //                                                  //Verificaciones que fueron sacadas del método
        //                                                  //      subPrepareConstants porque ahi no se pueden usar
        //                                                  //      las constantes de la parte concreta que se preparan
        //                                                  //      en el mismo metodo (en C# se pueden utilizar porque
        //                                                  //      se asignan dentro del mismo método, sin embargo
        //                                                  //      en Java se asignan hasta después de que termine la
        //                                                  //      ejecución del método por ser parámetro Out).

        //                                                  //Verifica que espacio . : ? sean USEFULL.
        final String strCHAR_REQUIRED_IN_CHAR_USEFUL = " .:?";
        CodcbCobol.codcbDUMMY_UNIQUE.subVerifyUseful(strCHAR_REQUIRED_IN_CHAR_USEFUL, "strCHAR_REQUIRED_IN_CHAR_USEFUL");

        //                                                  //Verifica que sean USEFUL.
        CodcbCobol.codcbDUMMY_UNIQUE.subVerifyUseful(CodcbCobol.codcbDUMMY_UNIQUE.arrcharCONVERSION(),
            "CodcbCobol.codcbDUMMY_UNIQUE.arrcharCONVERSION");

        CodcbCobol.codcbDUMMY_UNIQUE.subVerifyUseful(CodcbCobol.codcbDUMMY_UNIQUE.strSTART_FILLER(),
            "CodcbCobol.codcbDUMMY_UNIQUE.strSTART_FILLER");


    }

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/


    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void subResetOneClass()
    {
        super.subResetOneClass();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(StrtoEnum testoptionSHORT_I)
    {
        String strObjId = Tes3.strGetObjId(this);

        return strObjId + "[" + super.strTo(StrtoEnum.SHORT) + "]";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        String strObjId = Tes3.strGetObjId(this);

        return strObjId + "{}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/
    private CodcbCobol() {super();}

    //------------------------------------------------------------------------------------------------------------------
    public CodcbCobol(
        //                                                  //Crea código Cobol con arreglo de Strings.
        //                                                  //this.*[O], asigna valores.

        TechinstEnum techinst_I,
        CodtypeEnum codtype_I,
        SyspathPath syspathFile_G,
        String[] arrstrLine_G
        )
    {
        super(techinst_I, codtype_I, syspathFile_G, arrstrLine_G);

        //                                                  //Debe abortar si tiene un char invalido en pos. 0.
        this.subReviewLinesChar0(true);


        this.subSetObjectConstructionFinish();
    }

    //------------------------------------------------------------------------------------------------------------------
    public CodcbCobol(
        //                                                  //Crea objeto código COBOL con archivo.
        //                                                  //this.*[O], asigna valores.

        TechinstEnum techinst_I,
        SyspathPath syspathFile_G
        )
    {
        super(techinst_I, syspathFile_G);

        //                                                  //Si el char 0 de alguna línea no es válido, envía mensaje
        //                                                  //      (no aborta).
        this.subReviewLinesChar0(false);

        this.subSetObjectConstructionFinish();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT FACTORY*/

    //------------------------------------------------------------------------------------------------------------------
    @Override public CodCodeAbstract codxxNew(TechinstEnum techinst_I, CodtypeEnum codtype_I, SyspathPath syspathFile_G,
        String[] arrstrLine_G)
    {
        return new CodcbCobol(techinst_I, codtype_I, syspathFile_G, arrstrLine_G);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public CodCodeAbstract codxxNew(TechinstEnum techinst_I, SyspathPath syspahtFile_I)
    {
        return new CodcbCobol(techinst_I, syspahtFile_I);
    }

    /*TASK Cod2 CodAbstract(arrstr & file)*/
    //------------------------------------------------------------------------------------------------------------------
    private void subReviewLinesChar0(
        //                                                  //Los registros, en la columna 7 (posición 0) deben tener
        //                                                  //      "*"(indicador de comentarios), "/"(indicador de
        //                                                  //      brinco de hoja en el listado de compilación) o
        //                                                  //      espacio.
        //                                                  //Al concluir la verificación de todos los registros se
        //                                                  //      reporta la cantidad de registros inválidos con un
        //                                                  //      mensaje y se permite continuar.
        //                                                  //this[I], toma info del objeto

        //                                                  //true, aborta si hay un inicio invalido
        //                                                  //false, envía mensaje si hay un inicio invalido
        boolean boolAbort_I
        )
    {
        int intLinesInvalid = 0;

        //                                                  //Verifica primer caracter de cada línea.
        for (int intI = 0; intI < this.arrstrLine().length; intI = intI + 1)
        {
            //                                              //To easy code
            String strLine = this.arrstrLine()[intI];

            if (
                //                                          //La línea esta totalmente en espacios
                strLine.equals("")
                )
            {
                //                                          //No hace nada.
            }
            else
            {
                //                                          //To easy code
                char char0 = strLine.charAt(0);

                if (
                    //                                      //Es uno de los caracteres permitido en la primera
                    //                                      //      posición.
                    (char0 == ' ') || (char0 == '*') || (char0 == '/')
                    )
                {
                    //                                      //No hace nada.
                }
                else
                {
                    //                                      //En ocasiones (Ej. cuando se tiene un file) es normal que
                    //                                      //      este aquí, si hay algo invalido y deba continuar, en
                    //                                      //      otras (Ej. cuando se tiene un arrstr) todo debe
                    //                                      //      estar correcto.

                    if (
                        boolAbort_I
                        )
                        Tes3.subAbort(Tes3.strTo(this.codtype(), "codtype") + ", " +
                            Tes3.strTo(this.syspathFile(), StrtoEnum.SHORT) + ", " +
                            Tes3.strTo(strLine, "this.arrstrLine[" + intI + "]") +
                            " do not begin withn *, / or blank");

                    intLinesInvalid = intLinesInvalid + 1;

                    Tes3.subWarning(Tes3.strTo(this.codtype(), "codtype") + ", " +
                        Tes3.strTo(this.syspathFile(), StrtoEnum.SHORT) + ", " +
                        Tes3.strTo(strLine, "this.arrstrLine[" + intI + "]") +
                        " do not begin withn *, / or blank");
                }
            }
        }

        if (
            //                                              //Encontró al menos 1 línea inválida.
            intLinesInvalid >= 1
            )
        {
            Tes3.subWarning(Tes3.strTo(this.syspathFile().strFullPath(), StrtoEnum.SHORT) + ", " +
                Tes3.strTo(intLinesInvalid, "intLinesInvalid") + " at least one line is invalid");
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/


    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/
