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
public class CodooObjectOriented extends CodCodeAbstract
//                                                          //Código completo de un programa en Object Oriented (C#,
//                                                          //      Java, etc.).
//                                                          //Las líneas en Object Oriented son de longitud variable, en
//                                                          //      nuestro estándar se generarán líneas de máximo 120
//                                                          //      caracteres.
//                                                          //Se eliminan los blancos a la derecha.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/
    public static CodooObjectOriented codooDUMMY_UNIQUE = new CodooObjectOriented();

    @Override public CodCodeAbstract codDUMMY() { return CodooObjectOriented.codooDUMMY_UNIQUE; }

    private static T4techInfoTuple t4techINFO_I = Tech.t4techGet(TechtechEnum.OBJECT_ORIENTED);
    @Override public T4techInfoTuple t4techINFO() { return CodooObjectOriented.t4techINFO_I; }

    //                                                      //El siguiente String son caracteres utiles en Object
    //                                                      //      Oriented (C#, Java, Objective-C, swift) que no
    //                                                      //      requieren ser convertidos.
    private static String strCHAR_USEFUL = Tes3.strCHAR_USEFUL_IN_TEXT;
    //                                                      //Este arreglo se debe ordenar.
    private static char[] arrcharUSEFUL_Z;
    @Override public char[] arrcharUSEFUL() { return CodooObjectOriented.arrcharUSEFUL_Z; }

    //                                                      //El siguiente String son pares de caracteres, donde el
    //                                                      //      carácter en posición par (0, 2, etc.) deberá ser
    //                                                      //      convertido al siguiente carácter (1, 3, etc.), para
    //                                                      //      poder ser procesado. Los caracteres en posición par
    //                                                      //      no estan en arrcharUSEFUL y los caracteres en
    //                                                      //      posición impar si.
    private static final String strCHAR_TO_CONVERT_AND_CONVERSION = "";
    //                                                      //Los caracteres en posición par convertidos al primer
    //                                                      //      arreglo, los caracteres en posición impar
    //                                                      //      convertidos al segundo arreglo,
    //                                                      //Ambos arreglos deben ordenarse por el primero.
    private static char[] arrcharTO_CONVERT_Z;
    @Override public char[] arrcharTO_CONVERT() { return CodooObjectOriented.arrcharTO_CONVERT_Z; }
    private static char[] arrcharCONVERSION_Z;
    @Override public char[] arrcharCONVERSION() { return CodooObjectOriented.arrcharCONVERSION_Z; }

    //                                                      //Para construir a partir de syspath.

    @Override public boolean boolCONSTRUCT_FROM_SYSPATH() { return true; }

    //                                                      //This also indicate intSTART_CODE is 0.
    @Override public String strSTART_FILLER() { return ""; }

    @Override public boolean boolIS_FIX_LENGTH() { return false; }
    @Override public int intLINE_MAX_LENGTH() { return 120; }

    //------------------------------------------------------------------------------------------------------------------
    static
    //                                                      //A partir de las constantes definidas, genera otras
    //                                                      //      constantes para facilitar el proceso.
    {
        Oarrchar oarrcharUSEFUL_Z = new Oarrchar(CodooObjectOriented.arrcharUSEFUL_Z);
        Oarrchar oarrcharTO_CONVERT_Z = new Oarrchar(CodooObjectOriented.arrcharTO_CONVERT_Z);
        Oarrchar oarrcharCONVERSION_Z = new Oarrchar(CodooObjectOriented.arrcharCONVERSION_Z);

        CodCodeAbstract.subPrepareConstants(CodooObjectOriented.codooDUMMY_UNIQUE, CodooObjectOriented.strCHAR_USEFUL,
            oarrcharUSEFUL_Z, CodooObjectOriented.strCHAR_TO_CONVERT_AND_CONVERSION, oarrcharTO_CONVERT_Z,
            oarrcharCONVERSION_Z);

        CodooObjectOriented.arrcharUSEFUL_Z = oarrcharUSEFUL_Z.v;
        CodooObjectOriented.arrcharTO_CONVERT_Z = oarrcharTO_CONVERT_Z.v;
        CodooObjectOriented.arrcharCONVERSION_Z = oarrcharCONVERSION_Z.v;

        //                                                  //Verificaciones que fueron sacadas del método
        //                                                  //      subPrepareConstants porque ahi no se pueden usar
        //                                                  //      las constantes de la parte concreta que se preparan
        //                                                  //      en el mismo metodo (en C# se pueden utilizar porque
        //                                                  //      se asignan dentro del mismo método, sin embargo
        //                                                  //      en Java se asignan hasta después de que termine la
        //                                                  //      ejecución del método por ser parámetro Out).

        //                                                  //Verifica que espacio . : ? sean USEFULL.
        final String strCHAR_REQUIRED_IN_CHAR_USEFUL = " .:?";
        CodooObjectOriented.codooDUMMY_UNIQUE.subVerifyUseful(strCHAR_REQUIRED_IN_CHAR_USEFUL,
            "strCHAR_REQUIRED_IN_CHAR_USEFUL");

        //                                                  //Verifica que sean USEFUL.
        CodooObjectOriented.codooDUMMY_UNIQUE.subVerifyUseful(CodooObjectOriented.codooDUMMY_UNIQUE.arrcharCONVERSION(),
            "CodooObjectOriented.codooDUMMY_UNIQUE.arrcharCONVERSION");

        CodooObjectOriented.codooDUMMY_UNIQUE.subVerifyUseful(CodooObjectOriented.codooDUMMY_UNIQUE.strSTART_FILLER(),
            "CodooObjectOriented.codooDUMMY_UNIQUE.strSTART_FILLER");
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

    //------------------------------------------------------------------------------------------------------------------
    private CodooObjectOriented() { super(); }

    //------------------------------------------------------------------------------------------------------------------
    public CodooObjectOriented(
        //                                                  //Crea código C# con arreglo de Strings.
        //                                                  //this.*[O], asigna valores.

        TechinstEnum techinst_I,
        CodtypeEnum codtype_I,
        SyspathPath syspathFile_G,
        String[] arrstrLine_G
        )
    {
        super(techinst_I, codtype_I, syspathFile_G, arrstrLine_G);

        this.subSetObjectConstructionFinish();
    }

    //------------------------------------------------------------------------------------------------------------------
    public CodooObjectOriented(
        //                                                  //Crea código C# con archivo.
        //                                                  //this.*[O], asigna valores

        TechinstEnum techinst_I,
        SyspathPath syspathFile_G
        )
    {
        super(techinst_I, syspathFile_G);

        this.subSetObjectConstructionFinish();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT FACTORY*/

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public CodCodeAbstract codxxNew(TechinstEnum techinst_I, CodtypeEnum codtype_I, SyspathPath syspathFile_G,
        String[] arrstrLine_G)
    {
        return new CodooObjectOriented(techinst_I, codtype_I, syspathFile_G, arrstrLine_G);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public CodCodeAbstract codxxNew(TechinstEnum techinst_I, SyspathPath syspahtFile_G)
    {
        return new CodooObjectOriented(techinst_I, syspahtFile_G);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/