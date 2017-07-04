/*TASK RPS.Cod Relevant Part Code*/
package QEnablerObjectOriented;

import QEnablerBase.CodCodeAbstract;
import QEnablerBase.CodtypeEnum;
import QEnablerBase.ComCommentsAbstract;
import QEnablerCobol.CodcbCobol;
import SoftwareAutomation.T4techInfoTuple;
import SoftwareAutomation.Tech;
import SoftwareAutomation.TechinstEnum;
import SoftwareAutomation.TechtechEnum;
import TowaInfrastructure.*;


//                                                          //AUTHOR: Towa (AAH-Alejandro Arellano).
//                                                          //CO-AUTHOR: Towa (GLG-Gerardo Lopez).
//                                                          //DATE: 4-Enero-2017.
//                                                          //PURPOSE:
//                                                          //Especificación de clases para Código txt.

//======================================================================================================================
public class CodtxText extends CodCodeAbstract
    //
    //                                                      //Texto completo que realmente no es un programa pero que
    //                                                      //      pero que podrá ser tratado como un archivo de Código
    //                                                      //      (.txt, .log, .sln, etc.).
    //                                                      //Las líneas en txt son de longitud variable, en nuestro
    //                                                      //      estandar se generarán líneas de máximo 150
    //                                                      //      caracteres.
    //                                                      //Se eliminan los blancos a la derecha.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    public static CodtxText codtxDUMMY_UNIQUE_Z = new CodtxText();
    public CodCodeAbstract codDUMMY_UNIQUE() { return CodtxText.codtxDUMMY_UNIQUE_Z; }

    @Override
    public String[] arrstrFILE_EXTENSION() {
        return new String[0];
    }

    private static T4techInfoTuple t4techINFO_I = Tech.t4techGet(TechtechEnum.TEXT);
    public T4techInfoTuple t4techINFO() { return CodtxText.t4techINFO_I; }

    //                                                      //Todos los caracteres útiles en TEXTO.
    private static String strCHAR_USEFUL = Tes2.strCHAR_USEFUL_IN_TEXT;
    private static char[] arrcharUSEFUL_Z;
    public char[] arrcharUSEFUL() { return CodtxText.arrcharUSEFUL_Z; }

    //                                                      //Todos los caracteres visibles estan en arrcharUSEFUL, por
    //                                                      //      lo que no se necesita convertir ninguno.
    private static final String strCHAR_TO_CONVERT_AND_CONVERSION = "";
   //                                                       //Los caracteres en posición par convertidos al primer
   //                                                       //      arreglo, los caracteres en posición impar
   //                                                       //      convertidos al segundo arreglo,
   //                                                       //Ambos arreglos deben ordenarse por el primero.
    private static /*readonly*/ char[] arrcharTO_CONVERT_Z;
    public char[] arrcharTO_CONVERT() { return CodtxText.arrcharTO_CONVERT_Z; }
    private static /*readonly*/ char[] arrcharCONVERSION_Z;
    public char[] arrcharCONVERSION() { return CodtxText.arrcharCONVERSION_Z; }

    @Override
    public int intSTART_CODE() {
        return 0;
    }

    @Override
    public boolean boolIS_FIX_LENGTH() {
        return false;
    }

    @Override
    public int intLINE_MAX_LENGTH() {
        return 0;
    }

    @Override
    public ComCommentsAbstract comDUMMY() {
        return null;
    }

    //                                                      //Para construir a partir de syspath.

    public Boolean boolCONSTRUCT_FROM_SYSPATH() { return true; }

    //                                                      //This also indicate intSTART_CODE is 0.
    public String strSTART_FILLER() { return ""; }

    private static /*readonly*/ int intLINE_MAX_LENGTH_Z = 150;
    /*END-TASK*/

    //------------------------------------------------------------------------------------------------------------------
    static
    //                                                      //A partir de las constantes definidas, genera otras
    //                                                      //      constantes para facilitar el proceso.
    {
        Oarrchar oarrcharUSEFUL_Z = new Oarrchar(CodtxText.arrcharUSEFUL_Z);
        Oarrchar oarrcharTO_CONVERT_Z = new Oarrchar(CodtxText.arrcharTO_CONVERT_Z);
        Oarrchar oarrcharCONVERSION_Z = new Oarrchar(CodtxText.arrcharCONVERSION_Z);
        //CodCodeAbstract.subPrepareConstants(CodtxText.codtxDUMMY_UNIQUE_Z, CodtxText.strCHAR_USEFUL,
        //        oarrcharUSEFUL_Z, CodtxText.strCHAR_TO_CONVERT_AND_CONVERSION,
        //        oarrcharTO_CONVERT_Z, oarrcharCONVERSION_Z);
        CodCodeAbstract.subPrepareConstants(CodtxText.strCHAR_USEFUL,
                oarrcharUSEFUL_Z, CodtxText.strCHAR_TO_CONVERT_AND_CONVERSION,
                oarrcharTO_CONVERT_Z, oarrcharCONVERSION_Z);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/


    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    public void subResetOneClass() { super.subResetOneClass(); }

    //--------------------------------------------------------------------------------------------------------------
    public String strTo(TestoptionEnum strtoSHORT_I)
            {
            String strObjId = Tes2.strGetObjId(this);

            return strObjId + "[" + super.strTo(TestoptionEnum.SHORT) + "]";
            }

    //--------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //--------------------------------------------------------------------------------------------------------------
    public CodtxText() { super(); }

    //--------------------------------------------------------------------------------------------------------------
    public CodtxText(

        //                                                  //Crea código xml con arreglo de Strings.
        //                                                  //this.*[O], asigna valores.
        //TechinstEnum techinst_I,
        CodtypeEnum codtype_I,
        SyspathPath syspathFile_G,
        String[] arrstrLine_G
        )
    {
        //super(techinst_I, codtype_I, syspathFile_G, arrstrLine_G)
        super(codtype_I, syspathFile_G, arrstrLine_G);

        //this.subResetObject();
        this.subReset();
    }

    @Override
    public CodCodeAbstract codxxNew(CodtypeEnum codtype_I, SyspathPath syspathFile_I, String[] arrstrLine_I) {
        return null;
    }

    @Override
    public CodCodeAbstract codxxNew(SyspathPath syspahtFile_I) {
        return null;
    }

    @Override
    public CodCodeAbstract codxxNew(ComCommentsAbstract com_I) {
        return null;
    }

    //--------------------------------------------------------------------------------------------------------------
    public CodtxText(
    //                                              //Crea código xml con archivo.
    //                                              //this.*[O], asigna valores.

    TechinstEnum techinst_I,
    SyspathPath syspathFile_I
    )
    {
        //super(techinst_I, syspathFile_I)
        super(syspathFile_I);

        //this.subResetObject();
        this.subReset();
    }

    //--------------------------------------------------------------------------------------------------------------
        /*OBJECT FACTORY*/

    //--------------------------------------------------------------------------------------------------------------
    public CodCodeAbstract codxxNew(TechinstEnum techinst_I, CodtypeEnum codtype_I,
        SyspathPath syspathFile_G, String[] arrstrLine_G)
        {
            //return new CodtxText(techinst_I, codtype_I, syspathFile_G, arrstrLine_G);
            return new CodtxText(codtype_I, syspathFile_G, arrstrLine_G);
        }

    //--------------------------------------------------------------------------------------------------------------
    public CodCodeAbstract codxxNew(TechinstEnum techinst_I, SyspathPath syspahtFile_I)
        {
        return new CodtxText(techinst_I, syspahtFile_I);
        }

        //--------------------------------------------------------------------------------------------------------------
        /*TRANSFORMATION METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        /*ACCESS METHODS*/

        //--------------------------------------------------------------------------------------------------------------
}
        //==============================================================================================================
/*END-TASK*/