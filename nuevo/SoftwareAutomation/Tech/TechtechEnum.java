package Tech;


import Ti.*;
import java.util.LinkedList;

//==================================================================================================================
public class TechtechEnum extends BenumBaseEnumAbstract
//                                                          //Posibles tecnologías.
{
    //##############################################################################################################
    private static LinkedList<String> lststrEnumSequence = new LinkedList<String>();
    private TechtechEnum(String str_I)
    {
        super(str_I);
        TechtechEnum.lststrEnumSequence.add(this.str());
    }
    @Override protected int intEnumSequence(String str_I) { return TechtechEnum.lststrEnumSequence.indexOf(str_I); }
    //##############################################################################################################

    //                                                  //Technologies requires for IBM Cobol
    public static TechtechEnum COBOL = new TechtechEnum("COBOL");
    public static TechtechEnum SQL = new TechtechEnum("SQL");
    public static TechtechEnum CICS = new TechtechEnum("CICS");

    public static TechtechEnum OBJECT_ORIENTED = new TechtechEnum("OBJECT_ORIENTED");

    public static TechtechEnum XML = new TechtechEnum("XML");

    public static TechtechEnum TEXT = new TechtechEnum("TEXT");
}

//==================================================================================================================
/*END-TASK*/