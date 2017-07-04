package SoftwareAutomation;

import java.util.LinkedList;
import java.util.List;

//==================================================================================================================
public class TechinstEnum extends BenumBaseEnumAbstract
//                                                      //Para cada tecnología se incluyen las diversas
//                                                      //      posibilidades de instancias.
{
        //##############################################################################################################
        private static LinkedList<String> lststrEnumSequence = new LinkedList<String>();
        private TechinstEnum(String str_I)
        {
                super(str_I);
                TechinstEnum.lststrEnumSequence.add(this.str());
        }
        protected Integer intEnumSequence(String str_I) { return TechinstEnum.lststrEnumSequence.indexOf(str_I); }
        //##############################################################################################################

        //                                                  //Para la tecnología COBOL.
        public static final TechinstEnum CB_HP = new TechinstEnum("CB_HP");
        public static final TechinstEnum CB_IBM = new TechinstEnum("CB_IBM");
        public static final TechinstEnum CB_NEUTRAL = new TechinstEnum("CB_NEUTRAL");

        //                                                  //Para la tecnología SQL.
        public static final TechinstEnum SQ_EMBEDED_IN_COBOL = new TechinstEnum("SQ_EMBEDED_IN_COBOL");
        public static final TechinstEnum SQ_EMBEDED_IN_FORTRAN = new TechinstEnum("SQ_EMBEDED_IN_FORTRAN");
        public static final TechinstEnum SQ_EMBEDED_IN_PL_I = new TechinstEnum("SQ_EMBEDED_IN_PL_I");

        //                                                  //Para la tecnología CICS.
        public static final TechinstEnum CC_EMBEDED_IN_COBOL = new TechinstEnum("CC_EMBEDED_IN_COBOL");
        public static final TechinstEnum CC_EMBEDED_IN_FORTRAN = new TechinstEnum("CC_EMBEDED_IN_FORTRAN");
        public static final TechinstEnum CC_EMBEDED_IN_PL_I = new TechinstEnum("CC_EMBEDED_IN_PL_I");

        //                                                  //Para la tecnología OBJECT_ORIENTED.
        public static final TechinstEnum OO_CSHARP = new TechinstEnum("OO_CSHARP");
        public static final TechinstEnum OO_JAVA = new TechinstEnum("OO_JAVA");
        public static final TechinstEnum OO_NEUTRAL = new TechinstEnum("OO_NEUTRAL");
        public static final TechinstEnum OO_SWIFT = new TechinstEnum("OO_SWIFT");

        //                                                  //Para la tecnología XML.
        public static final TechinstEnum XL_XML = new TechinstEnum("XL_XML");
        public static final TechinstEnum XL_CSPROJ = new TechinstEnum("XL_CSPROJ");
        public static final TechinstEnum XL_DRAWIO = new TechinstEnum("XL_DRAWIO");
        public static final TechinstEnum XL_CONFIG = new TechinstEnum("XL_CONFIG");
        public static final TechinstEnum XL_MANIFEST = new TechinstEnum("XL_MANIFEST");

        //                                                  //Para la tecnología TEXT.

        //                                                  //Required for SoftwareCompare, test logs (this king of logs
        //                                                  //      are produced with Towa's standard form of test).
        public static final TechinstEnum TX_TEST = new TechinstEnum("TX_TEST");

        //                                                  //Required for SoftwareCompare, produced by subCompareReport
        //                                                  //      in Nvsbdcod
        public static final TechinstEnum TX_COMPARE = new TechinstEnum("TX_COMPARE");

        public static final TechinstEnum TX_LOG = new TechinstEnum("TX_LOG");

        public static final TechinstEnum TX_TEXT = new TechinstEnum("TX_TEXT");
        public static final TechinstEnum TX_SLN = new TechinstEnum("TX_SLN");

        @Override
        public int compareTo(Object o) {
                return 0;
        }
}

//==================================================================================================================

/*END-TASK*/
