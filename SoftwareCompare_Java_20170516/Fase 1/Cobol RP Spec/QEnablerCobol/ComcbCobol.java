/*TASK RPS.Cod Relevant Part Comments*/
package QEnablerCobol;

import QEnablerBase.*;
import TowaInfrastructure.*;

                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 15-Febrero-2011.
                                                            //PURPOSE:
                                                            //Especificación de clases para Comentarios Cobol.
//======================================================================================================================
public class ComcbCobol extends ComCommentsAbstract
                                                            //Comentarios COBOL.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/
    public static ComcbCobol comcbDUMMY_UNIQUE_Z = new ComcbCobol();
    @Override public ComCommentsAbstract comDUMMY_UNIQUE() { return ComcbCobol.comcbDUMMY_UNIQUE_Z; }

                                                            //El siguiente String contiene los caracteres considerados
                                                            //      "útiles" en una línea de comentarios, si una línea
                                                            //      de comentarios contiene al menos 1 caracter de
                                                            //      estos, es una línea útil.
    public static final String strCHAR_REQUIRED_IN_PARUN =
        "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz";
                                                            //Este arreglo se debe ordenar.
    private static char[] arrcharREQUIRED_IN_PARUN_Z;
    @Override public char[] arrcharREQUIRED_IN_PARUN() { return ComcbCobol.arrcharREQUIRED_IN_PARUN_Z; }

                                                            //En COBOL solo existen los comentarios de tipo
                                                            //      FULL_LINE.

    private static boolean boolCOM_FL_OR_TL_IS_ACCEPTED_Z = true;
    @Override public boolean boolCOM_FL_OR_TL_IS_ACCEPTED() { return ComcbCobol.boolCOM_FL_OR_TL_IS_ACCEPTED_Z; }
                                                            //Caracteres con los que inicia.
    private static String strCOM_FL_OR_TL_Z = "*";
    @Override public String strCOM_FL_OR_TL() { return ComcbCobol.strCOM_FL_OR_TL_Z; }
                                                            //Tamaño de la línea de comentarios.
    private static int intCOM_FL_OR_TL_LINE_SIZE_Z = 66;
    @Override public int intCOM_FL_OR_TL_LINE_SIZE() { return ComcbCobol.intCOM_FL_OR_TL_LINE_SIZE_Z; }
                                                            //Línea estándar con la que inicia y termina un conjunto de
                                                            //      comentarios de tipo FULL_LINE.
    private static String strCOM_FL_START_AND_END_LINE_Z = "*----";
    @Override public String strCOM_FL_START_AND_END_LINE() { return ComcbCobol.strCOM_FL_START_AND_END_LINE_Z; }
                                                            //Inicio y fin de tag.
    private static char charCOM_TL_START_Z = '<';
    @Override public char charCOM_TL_START() { return ComcbCobol.charCOM_TL_START_Z; }
    private static char charCOM_TL_END_Z = '>';
    @Override public char charCOM_TL_END() { return ComcbCobol.charCOM_TL_END_Z; }

                                                            //En COBOL no existen comentarios de tipo END_OF_LINE,
                                                            //      DELIMITED ni EMBEDED.
    private static boolean boolCOM_EL_IS_ACCEPTED_Z = false;
    @Override public boolean boolCOM_EL_IS_ACCEPTED() { return ComcbCobol.boolCOM_EL_IS_ACCEPTED_Z; }
    private static String strCOM_EL_Z = null;
    @Override public String strCOM_EL() { return ComcbCobol.strCOM_EL_Z; }
    private static int intCOM_EL_LINE_SIZE_Z = 0;
    @Override public int intCOM_EL_LINE_SIZE() { return ComcbCobol.intCOM_EL_LINE_SIZE_Z; }

    private static T2alignmentRequiresAlignmentTuple[] arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_Z = null;
    @Override public T2alignmentRequiresAlignmentTuple[] arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT()
        { return ComcbCobol.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_Z; }

    private static String strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS_Z = null;
    @Override public String strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS()
        { return ComcbCobol.strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS_Z; }
    private static boolean boolCOM_DE_OR_EM_IS_ACCEPTED_Z = false;
    @Override public boolean boolCOM_DE_OR_EM_IS_ACCEPTED() { return ComcbCobol.boolCOM_DE_OR_EM_IS_ACCEPTED_Z; }
    private static String strCOM_DE_OR_EM_START_Z = null;
    @Override public String strCOM_DE_OR_EM_START() { return ComcbCobol.strCOM_DE_OR_EM_START_Z; }
    private static String strCOM_DE_OR_EM_END_Z = null;
    @Override public String strCOM_DE_OR_EM_END() { return ComcbCobol.strCOM_DE_OR_EM_END_Z; }
    private static int intCOM_DE_LINE_SIZE_Z = 0;
    @Override public int intCOM_DE_LINE_SIZE() { return ComcbCobol.intCOM_DE_LINE_SIZE_Z; }

                                                            //Para PÁRRAFOS ESTRUCTURADOS.
                                                            //Carácteres de apertura y cierre, después de los carácteres
                                                            //      de inicio del comentario.
    private static char charPARST_START_Z = '(';
    @Override public char charPARST_START() { return ComcbCobol.charPARST_START_Z; }
    private static char charPARST_END_Z = ')';
    @Override public char charPARST_END() { return ComcbCobol.charPARST_END_Z; }

                                                            //Para PÁRRAFOS NO ESTRUCTURADOS.

                                                            //Identación de 4 blancos y 8 blancos.
    private static String strPARUN_INDENT_FL_FIRST_LINE_Z = Tools.strPadRight("", 4);
    @Override public String strPARUN_INDENT_FL_FIRST_LINE() { return ComcbCobol.strPARUN_INDENT_FL_FIRST_LINE_Z; }
    private static String strPARUN_INDENT_FL_OTHER_LINES_Z = Tools.strPadRight("", 8);
    @Override public String strPARUN_INDENT_FL_OTHER_LINES() { return ComcbCobol.strPARUN_INDENT_FL_OTHER_LINES_Z; }

                                                            //No hay END_OF_LINE.
    private static String strPARUN_INDENT_EL_FIRST_LINE_Z = null;
    @Override public String strPARUN_INDENT_EL_FIRST_LINE() { return ComcbCobol.strPARUN_INDENT_EL_FIRST_LINE_Z; }
    private static String strPARUN_INDENT_EL_OTHER_LINES_Z = null;
    @Override public String strPARUN_INDENT_EL_OTHER_LINES() { return ComcbCobol.strPARUN_INDENT_EL_OTHER_LINES_Z; }

    /*TO ACCESS CLASS CONSTANTS AND METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    static                                                  //Ordena arrcharREQUIRED_IN_PARUN.
    {
        Oobject<char[]> oarrcharREQUIRED_IN_PARUN_Z = new Oobject<char[]>();
        ComCommentsAbstract.subPrepareConstants(CodcbCobol.codcbDUMMY_UNIQUE_Z, ComcbCobol.strCHAR_REQUIRED_IN_PARUN,
            oarrcharREQUIRED_IN_PARUN_Z, ComcbCobol.charPARST_START_Z, charPARST_END_Z,
            ComcbCobol.boolCOM_FL_OR_TL_IS_ACCEPTED_Z, ComcbCobol.strPARUN_INDENT_FL_FIRST_LINE_Z,
            ComcbCobol.strPARUN_INDENT_FL_OTHER_LINES_Z, ComcbCobol.charCOM_TL_START_Z,
            ComcbCobol.charCOM_TL_END_Z, ComcbCobol.boolCOM_EL_IS_ACCEPTED_Z,
            ComcbCobol.strPARUN_INDENT_EL_FIRST_LINE_Z, ComcbCobol.strPARUN_INDENT_EL_OTHER_LINES_Z,
            ComcbCobol.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_Z);

        ComcbCobol.arrcharREQUIRED_IN_PARUN_Z = oarrcharREQUIRED_IN_PARUN_Z.v;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    @Override public void subReset()
    {
        super.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        String strObjId = Test.strGetObjId(this);

        return strObjId + "[" + super.strTo(TestoptionEnum.SHORT) + "]";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override public String strTo()
    {
        String strObjId = Tes2.strGetObjId(this);

        return strObjId + "{}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    private ComcbCobol() { super(); }

    //------------------------------------------------------------------------------------------------------------------
    public ComcbCobol(                                    //Crea objeto comentario con extracto de código Cobol.
                                                            //this.*[O], asigna valores.

        CodcbCobol codcbCom_I
        )
    {
        super(codcbCom_I);
        this.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
                                                            //Para construir objetos del tipo correcto desde clases
                                                            //      abstractas.

    //------------------------------------------------------------------------------------------------------------------
    @Override public ComCommentsAbstract comxxNew(CodCodeAbstract codCom_T)
    {
        return new ComcbCobol((CodcbCobol)codCom_T);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/