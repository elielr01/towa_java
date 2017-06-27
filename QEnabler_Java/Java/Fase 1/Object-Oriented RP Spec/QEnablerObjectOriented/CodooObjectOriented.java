/*TASK RPS.Cod Relevant Part Code*/
package QEnablerObjectOriented;

import QEnablerBase.*;
import TowaInfrastructure.*;


                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 15-Febrero-2011.
                                                            //PURPOSE:
                                                            //Especificación de clases para Código Cobol.

//======================================================================================================================
public class CodooObjectOriented extends CodCodeAbstract
                                                            //Código completo de un programa en Object-Oriented (C#,
                                                            //      Java, etc.).
                                                            //Las líneas en C# son de longitud variable, en nuestro
                                                            //      estandar se generarán líneas de máximo 120
                                                            //      caracteres.
                                                            //Se eliminan los blancos a la derecha.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    public static CodooObjectOriented codooDUMMY_UNIQUE_Z = new CodooObjectOriented();
    @Override
    public CodCodeAbstract codDUMMY_UNIQUE() { return CodooObjectOriented.codooDUMMY_UNIQUE_Z; }

                                                            //File extension valida en Object-Oriented.
    @Override
    public String[] arrstrFILE_EXTENSION()
    { return SoftwareAutomation.Tech.arrstrFILE_EXTENSION_OO; }

                                                            //El siguiente String son caracteres utiles en C# que no
                                                            //      requieren ser convertidos.
    private static final String strCHAR_USEFUL =
                                                            //Blanco y caracteres normales, no requieren conversión.
        " " + "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                                                            //Letras minúsculas.
        "abcdefghijklmnopqrstuvwxyz" +
                                                            //Caracteres acentuados.
        "ÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÂÊÎÔÛ" + "áéíóúàèìòùäëïöüâêîôû" + "Ññ" +

                                                            //Caracteres especiales, que aparecen en teclado de Mac, no
                                                            //      requiren conversión.

                                                            //Teclas de números.
        "ºª" + "\\" + "!|" + "\"" + "@" + "·#" + "$" + "%" + "&¬" + "/" + "(" + ")" + "=" + "'?" + "¡¿" +
                                                            //Teclas QW.....
        "€^`[*+]" +
                                                            //Teclas AS.....
        "¨´{çÇ}" +
                                                            //Teclas ZX.....
        "<>,;.:-_" +

                                                            //Otros caracteres que no aparecen en teclado de Mac.
        "¢©";
                                                            //Este arreglo se debe ordenar.
    private static /*readonly*/ char[] arrcharUSEFUL_Z;
    @Override
    public char[] arrcharUSEFUL() {return CodooObjectOriented.arrcharUSEFUL_Z;}

                                                            //El siguiente String son pares de caracteres, donde el
                                                            //      caracter en posición par (0, 2, etc.) deberá ser
                                                            //      convertido al siguiente carácter (1, 3, etc.) para
                                                            //      poder ser procesado. Los caracteresen posición par
                                                            //      no estan en arrcharUSEFUL y los caracteres en
                                                            //      posición impar si.
    private static final String strCHAR_TO_CONVERT_AND_CONVERSION =
                                                            //Comillas dobles y simples inclinadas a verticales.
        "“" + "\"" + "”" + "\"" + "‘'’'";


                                                            //Los caracteres en posición par convertidos al primer
                                                            //      arreglo, los caracteres en posición impar
                                                            //      convertidos al segundo arreglo.
                                                            //Ambos arreglos deben ordenarse por el primero.
    private static /*readonly*/ char[] arrcharTO_CONVERT_Z;
    @Override
    public char[] arrcharTO_CONVERT() {return CodooObjectOriented.arrcharTO_CONVERT_Z;}
    private static /*readonly*/ char[] arrcharCONVERSION_Z;
    @Override
    public char[] arrcharCONVERSION() {return CodooObjectOriented.arrcharCONVERSION_Z;}

                                                            //DEFINICIONES GENERALES.
    private static /*readonly*/ int intSTART_CODE_Z = 0;
    @Override
    public int intSTART_CODE() {return CodooObjectOriented.intSTART_CODE_Z;}

    private static /*readonly*/ boolean boolIS_FIX_LENGTH_Z = false;
    @Override
    public boolean boolIS_FIX_LENGTH() {return CodooObjectOriented.boolIS_FIX_LENGTH_Z;}

    private static /*readonly*/ int intLINE_MAX_LENGTH_Z = 120;
    @Override
    public int intLINE_MAX_LENGTH() {return CodooObjectOriented.intLINE_MAX_LENGTH_Z;}

    /*TO ACCESS CLASS CONSTANTS AND METHODS*/

    /*TASK RPS.Com Relevant Part Comments*/
    @Override
    public ComCommentsAbstract comDUMMY() { return ComooObjectOriented.comooDUMMY_UNIQUE_Z; }
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    static                                                  //A partir de las constantes definidas, genera otras
                                                            //      constantes para facilitar el proceso.
    {
    	Oarrchar oarrcharUSEFUL_Z = new Oarrchar(CodooObjectOriented.arrcharUSEFUL_Z);
    	Oarrchar oarrcharTO_CONVERT_Z = new Oarrchar(CodooObjectOriented.arrcharTO_CONVERT_Z);
    	Oarrchar oarrcharCONVERSION_Z = new Oarrchar(CodooObjectOriented.arrcharCONVERSION_Z);

        CodCodeAbstract.subPrepareConstants(CodooObjectOriented.strCHAR_USEFUL,
        		oarrcharUSEFUL_Z, CodooObjectOriented.strCHAR_TO_CONVERT_AND_CONVERSION, oarrcharTO_CONVERT_Z,
        		oarrcharCONVERSION_Z);

        CodooObjectOriented.arrcharUSEFUL_Z = oarrcharUSEFUL_Z.v;
        CodooObjectOriented.arrcharTO_CONVERT_Z = oarrcharTO_CONVERT_Z.v;
        CodooObjectOriented.arrcharCONVERSION_Z = oarrcharCONVERSION_Z.v;
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
    public CodooObjectOriented() { super(); }

    //------------------------------------------------------------------------------------------------------------------
    public CodooObjectOriented(                             //Crea código C# con arreglo de Strings.
                                                            //this.*[O], asigna valores.
        CodtypeEnum codtype_I,
        SyspathPath syspathFile_I,
        String[] arrstrLine_I
        )
    {
        super(codtype_I, syspathFile_I, arrstrLine_I);

        this.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    public CodooObjectOriented(                             //Crea código C# con archivo.
                                                            //this.*[O], asigna valores
        SyspathPath syspathFile_I
        )
    {
        super(syspathFile_I);

        this.subReset();
    }
    //------------------------------------------------------------------------------------------------------------------

    /*TASK RPS.Com Relevant Part Comments*/
    //------------------------------------------------------------------------------------------------------------------
    public CodooObjectOriented(                             //Crea código estándar de comentario C#.
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
        return new CodooObjectOriented(codtype_I, syspathFile_I, arrstrLine_I);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public CodCodeAbstract codxxNew(SyspathPath syspathFile_I)
    {
        return new CodooObjectOriented(syspathFile_I);
    }

    /*TASK RPS.Com Relevant Part Comments*/
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public CodCodeAbstract codxxNew(ComCommentsAbstract com_I)
    {
        return new CodooObjectOriented((ComooObjectOriented)com_I);
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
