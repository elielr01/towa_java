package Tech;

import Ti.*;
import java.util.LinkedList;

//==================================================================================================================
public class TechinstEnum extends BenumBaseEnumAbstract
//                                                          //Para cada tecnología se incluyen las diversas
//                                                          //      posibilidades de instancias.
{
    //##############################################################################################################
    private static LinkedList<String> lststrEnumSequence = new LinkedList<String>();
    private TechinstEnum(String str_I)
    {
        super(str_I);
        TechinstEnum.lststrEnumSequence.add(this.str());
    }
    @Override protected int intEnumSequence(String str_I) { return TechinstEnum.lststrEnumSequence.indexOf(str_I); }
    //##############################################################################################################

    //                                                      //Para la tecnología COBOL.
    public static TechinstEnum CB_HP = new TechinstEnum("CB_HP");
    public static TechinstEnum CB_IBM = new TechinstEnum("CB_IBM");
    public static TechinstEnum CB_NEUTRAL = new TechinstEnum("CB_NEUTRAL");

    //                                                      //Para la tecnología SQL.
    public static TechinstEnum SQ_EMBEDED_IN_COBOL = new TechinstEnum("SQ_EMBEDED_IN_COBOL");
    public static TechinstEnum SQ_EMBEDED_IN_FORTRAN = new TechinstEnum("SQ_EMBEDED_IN_FORTRAN");
    public static TechinstEnum SQ_EMBEDED_IN_PL_I = new TechinstEnum("SQ_EMBEDED_IN_PL_I");

    //                                                      //Para la tecnología CICS.
    public static TechinstEnum CC_EMBEDED_IN_COBOL = new TechinstEnum("CC_EMBEDED_IN_COBOL");
    public static TechinstEnum CC_EMBEDED_IN_FORTRAN = new TechinstEnum("CC_EMBEDED_IN_FORTRAN");
    public static TechinstEnum CC_EMBEDED_IN_PL_I = new TechinstEnum("CC_EMBEDED_IN_PL_I");

    //                                                      //Para la tecnología OBJECT_ORIENTED.
    public static TechinstEnum OO_CSHARP = new TechinstEnum("OO_CSHARP");
    public static TechinstEnum OO_JAVA = new TechinstEnum("OO_JAVA");
    public static TechinstEnum OO_NEUTRAL = new TechinstEnum("OO_NEUTRAL");
    public static TechinstEnum OO_SWIFT = new TechinstEnum("OO_SWIFT");

    //                                                      //Para la tecnología XML.
    public static TechinstEnum XL_XML = new TechinstEnum("XL_XML");
    public static TechinstEnum XL_CSPROJ = new TechinstEnum("XL_CSPROJ");
    public static TechinstEnum XL_DRAWIO = new TechinstEnum("XL_DRAWIO");
    public static TechinstEnum XL_CONFIG = new TechinstEnum("XL_CONFIG");
    public static TechinstEnum XL_MANIFEST = new TechinstEnum("XL_MANIFEST");

    //                                                      //Para la tecnología TEXT.

    //                                                      //Required for SoftwareCompare, test logs (this king of logs
    //                                                      //      are produced with Towa's standard form of test).
    public static TechinstEnum TX_TEST = new TechinstEnum("TX_TEST");

    //                                                      //Required for SoftwareCompare, produced by subCompareReport
    //                                                      //      in Nvsbdcod
    public static TechinstEnum TX_COMPARE = new TechinstEnum("TX_COMPARE");

    public static TechinstEnum TX_LOG = new TechinstEnum("TX_LOG");

    public static TechinstEnum TX_TEXT = new TechinstEnum("TX_TEXT");
    public static TechinstEnum TX_SLN = new TechinstEnum("TX_SLN");
}

//==================================================================================================================
/*END-TASK*/
