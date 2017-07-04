package SoftwareAutomation;

import java.util.LinkedList;

//==================================================================================================================
public class TechtechEnum extends BenumBaseEnumAbstract
//                                                      //Posibles tecnologías.
{
    //##############################################################################################################
    private static LinkedList<String> lststrEnumSequence = new LinkedList<String>();
    private TechtechEnum(String str_I)
    {
        super(str_I);
        TechtechEnum.lststrEnumSequence.add(this.str());
    }
    protected  Integer intEnumSequence(String str_I) { return TechtechEnum.lststrEnumSequence.indexOf(str_I); }
    //##############################################################################################################

    //                                                  //Technologies requires for IBM Cobol
    public static final TechtechEnum COBOL = new TechtechEnum("COBOL");
    public static final TechtechEnum SQL = new TechtechEnum("SQL");
    public static final TechtechEnum CICS = new TechtechEnum("CICS");

    public static final TechtechEnum OBJECT_ORIENTED = new TechtechEnum("OBJECT_ORIENTED");

    public static final TechtechEnum XML = new TechtechEnum("XML");

    public static final TechtechEnum TEXT = new TechtechEnum("TEXT");

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
