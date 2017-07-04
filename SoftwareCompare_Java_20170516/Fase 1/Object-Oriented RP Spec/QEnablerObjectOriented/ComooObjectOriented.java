/*TASK RPS.Cod Relevant Part Code*/
package QEnablerObjectOriented;

import QEnablerBase.*;
import TowaInfrastructure.*; 
import javax.swing.*;

                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 15-Febrero-2011.
                                                            //PURPOSE:
                                                            //Especificación de clases para Comentarios Object-Oriented
                                                            //      (C#, Java, etc.).

//======================================================================================================================
/*internal*/ public class ComooObjectOriented extends ComCommentsAbstract
                                                            //Comentarios Object-Oriented (C#, Java, etc.).
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    public static ComooObjectOriented comooDUMMY_UNIQUE_Z = new ComooObjectOriented();
    @Override public ComCommentsAbstract comDUMMY_UNIQUE() { return ComooObjectOriented.comooDUMMY_UNIQUE_Z; }

                                                            //El siguiente String contiene los caracteres considerados 
                                                            //      "útiles" en una línea de comentarios, si una línea 
                                                            //      de comentarios contiene al menos 1 caracter de 
                                                            //      estos, es una línea útil.
    public final static String strCHAR_REQUIRED_IN_PARUN =
                                                            //Dígitos y letras.
        "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" +
                                                            //Caracteres acentuados.
         "ÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÂÊÎÔÛ" + "áéíóúàèìòùäëïöüâêîôû" + "Ññ";
                                                            //Este arreglo se debe ordenar.
    private static char[] arrcharREQUIRED_IN_PARUN_Z;
    @Override public char [] arrcharREQUIRED_IN_PARUN()
        { return ComooObjectOriented.arrcharREQUIRED_IN_PARUN_Z; }

                                                            //En C# no existe comentarios de tipo FULL_LINE.
    private static boolean boolCOM_FL_OR_TL_IS_ACCEPTED_Z = false;
    @Override public boolean boolCOM_FL_OR_TL_IS_ACCEPTED()
        { return ComooObjectOriented.boolCOM_FL_OR_TL_IS_ACCEPTED_Z; }

    private static String strCOM_FL_OR_TL_Z = null;
    @Override public String strCOM_FL_OR_TL()  { return ComooObjectOriented.strCOM_FL_OR_TL_Z; }
    private static int intCOM_FL_OR_TL_LINE_SIZE_Z = 0;
    @Override  public int intCOM_FL_OR_TL_LINE_SIZE()
        { return ComooObjectOriented.intCOM_FL_OR_TL_LINE_SIZE_Z; }
    private static String strCOM_FL_START_AND_END_LINE_Z = null;
    @Override public String strCOM_FL_START_AND_END_LINE()
        { return ComooObjectOriented.strCOM_FL_START_AND_END_LINE_Z; }
    private static char charCOM_TL_START_Z = ' ';
    @Override public char charCOM_TL_START() { return ComooObjectOriented.charCOM_TL_START_Z; }
    private static char charCOM_TL_END_Z = ' ';
    @Override public char charCOM_TL_END() { return ComooObjectOriented.charCOM_TL_END_Z; }

                                                            //Para END_OF_LINE.

    private static boolean boolCOM_EL_IS_ACCEPTED_Z = true;
    @Override public boolean boolCOM_EL_IS_ACCEPTED() { return ComooObjectOriented.boolCOM_EL_IS_ACCEPTED_Z; }

                                                            //Caracteres con los que inicia.
    private static String strCOM_EL_Z = "//";
    @Override public String strCOM_EL() { return ComooObjectOriented.strCOM_EL_Z; }
                                                            //Tamaño de la línea de comentarios (incluyendo caracteres 
                                                            //      de inicio).
    private static int intCOM_EL_LINE_SIZE_Z = 60;
    @Override public int intCOM_EL_LINE_SIZE() { return ComooObjectOriented.intCOM_EL_LINE_SIZE_Z; }

                                                            //Indica si se requiere adicionar otra marca strCOM_EL para 
                                                            //      mantener alineados los comentarios. Esto depende de
                                                            //      la tecnología y del FILE EXTENSION del file.
                                                            //Ordenar por strFileExtension. Este será el mismo orden que
                                                            //      tiene arrstrFILE_EXTENSION.
    private static T2alignmentRequiresAlignmentTuple[] arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_Z =
    {
        new T2alignmentRequiresAlignmentTuple(".cs", true), new T2alignmentRequiresAlignmentTuple(".java", false),
    };
    @Override public T2alignmentRequiresAlignmentTuple[] arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT()
        { return ComooObjectOriented.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_Z; }

                                                            //Representación estandar de comentario vacio.
    private static String strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS_Z = "<COMMENT_WITH_NO_PARAGRAPHS>";
    @Override public String strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS()
        { return ComooObjectOriented.strCOM_EL_COMMENT_WITH_NO_PARAGRAPHS_Z; }

                                                            //Para DELIMITED y EMBEDED.

    private static boolean boolCOM_DE_OR_EM_IS_ACCEPTED_Z = true;
    @Override public boolean boolCOM_DE_OR_EM_IS_ACCEPTED()
        { return ComooObjectOriented.boolCOM_DE_OR_EM_IS_ACCEPTED_Z; }
                                                            //Caracteres con los que inicia y termina.
    private static String strCOM_DE_OR_EM_START_Z = "/*";
    @Override public String strCOM_DE_OR_EM_START() { return ComooObjectOriented.strCOM_DE_OR_EM_START_Z; }
    private static String strCOM_DE_OR_EM_END_Z = "*/";
    @Override public String strCOM_DE_OR_EM_END() { return ComooObjectOriented.strCOM_DE_OR_EM_END_Z; }
                                                            //Tamaño de la línea del comentario DELIMITED.
    private static int intCOM_DE_LINE_SIZE_Z = 120;
    @Override public int intCOM_DE_LINE_SIZE() { return ComooObjectOriented.intCOM_DE_LINE_SIZE_Z; }

                                                            //Para PÁRRAFOS ESTRUCTURADOS.
                                                            //Carácteres de apertura y cierre, después de los carácteres
                                                            //      de inicio del comentario.
    private static char charPARST_START_Z = '[';
    @Override public char charPARST_START() { return ComooObjectOriented.charPARST_START_Z; }
    private static char charPARST_END_Z = ']';
    @Override public char charPARST_END() { return ComooObjectOriented.charPARST_END_Z; }

                                                            //Para PÁRRAFOS NO ESTRUCTURADOS.

                                                            //No hay FULL_LINE.
    private static String strPARUN_INDENT_FL_FIRST_LINE_Z = null;
    @Override public String strPARUN_INDENT_FL_FIRST_LINE()
        { return ComooObjectOriented.strPARUN_INDENT_FL_FIRST_LINE_Z; }
    private static String strPARUN_INDENT_FL_OTHER_LINES_Z = null;
    @Override public String strPARUN_INDENT_FL_OTHER_LINES()
        { return ComooObjectOriented.strPARUN_INDENT_FL_OTHER_LINES_Z; }

                                                            //Identación de 0 blancos y 6 blancos.
    private static String strPARUN_INDENT_EL_FIRST_LINE_Z = "";
    @Override public String strPARUN_INDENT_EL_FIRST_LINE()
        { return ComooObjectOriented.strPARUN_INDENT_EL_FIRST_LINE_Z; }
    private static String strPARUN_INDENT_EL_OTHER_LINES_Z = Tools.strPadRight("", 6);
    @Override public String strPARUN_INDENT_EL_OTHER_LINES()
        { return ComooObjectOriented.strPARUN_INDENT_EL_OTHER_LINES_Z; }

    /*TO ACCESS CLASS CONSTANTS AND METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    static                                                  //A partir de las constantes definidas, genera otras
                                                            //      constantes para facilitar el proceso.
    {
        Oobject<char[]> oarrcharREQUIRED_IN_PARUN_Z = new Oobject<char[]>();
        ComCommentsAbstract.subPrepareConstants(CodooObjectOriented.codooDUMMY_UNIQUE_Z,
            ComooObjectOriented.strCHAR_REQUIRED_IN_PARUN, oarrcharREQUIRED_IN_PARUN_Z,
            ComooObjectOriented.charPARST_START_Z, charPARST_END_Z,
            ComooObjectOriented.boolCOM_FL_OR_TL_IS_ACCEPTED_Z, ComooObjectOriented.strPARUN_INDENT_FL_FIRST_LINE_Z,
            ComooObjectOriented.strPARUN_INDENT_FL_OTHER_LINES_Z, ComooObjectOriented.charCOM_TL_START_Z,
            ComooObjectOriented.charCOM_TL_END_Z, ComooObjectOriented.boolCOM_EL_IS_ACCEPTED_Z,
            ComooObjectOriented.strPARUN_INDENT_EL_FIRST_LINE_Z,
            ComooObjectOriented.strPARUN_INDENT_EL_OTHER_LINES_Z,
            ComooObjectOriented.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT_Z);

        ComooObjectOriented.arrcharREQUIRED_IN_PARUN_Z = oarrcharREQUIRED_IN_PARUN_Z.v;
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
        String strObjId = Test.strGetObjId(this);

        return strObjId + "{}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    private ComooObjectOriented() { super(); }

    //------------------------------------------------------------------------------------------------------------------
    public ComooObjectOriented(                             //Crea objeto comentario con extracto de código C#.
                                                            //this.*[O], asigna valores.

        CodooObjectOriented codooCom_I
        )
    {
        super(codooCom_I);
        this.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    //Para construir objetos del tipo correcto desde clases
    //      abstractas.

    //------------------------------------------------------------------------------------------------------------------
    @Override public ComCommentsAbstract comxxNew(CodCodeAbstract codCom_T)
    {
        return new ComooObjectOriented((CodooObjectOriented) codCom_T);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/