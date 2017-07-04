package QEnablerBase;

import TowaInfrastructure.*;

//                                                          //AUTHOR: Towa (EOG-Eduardo Ojeda).
//                                                          //CO-AUTHOR: Towa (GLG-Gerardo López).
//                                                          //DATE: 23-Julio-2012.
//                                                          //PURPOSE:
//															//Especificación de clase Abstract para Paragraph.

//======================================================================================================================
public abstract class ParParagraphAbstract extends BclassBaseClassAbstract
                                                            //Base para clases ParyyYyyyyyyy.
                                                            //Información ya ordenada de un pequeño conjunto de líneas
                                                            //      de comentarios, esto es, de un párrafo.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

   @Override
   protected BclassmutabilityEnum bclassmutability() { return BclassmutabilityEnum.INMUTABLE; }

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

                                                            //com del cual es parte.
    private ComCommentsAbstract comBelongsTo_Z;
    public ComCommentsAbstract comBelongsTo() { return this.comBelongsTo_Z; }

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

                                                            //Líneas de Código Estandar correspondiente al par.
    public abstract String[] arrstrLineStandard();

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
        return super.strTo(TestoptionEnum.SHORT) + ", " + Tes2.strTo(this.comBelongsTo(), TestoptionEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   @Override
   public String strTo()
    {
        final String strCLASS = "Par";

        return strCLASS + "{" + Tes2.strTo(this.comBelongsTo(), TestoptionEnum.SHORT) + "}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    protected ParParagraphAbstract(                         //Se construye el párrafo (parte abstracta).
                                                            //this.*[O], asigna valores.

                                                            //Objeto com del cual este párrafo es parte.
        ComCommentsAbstract comBelongsTo_T
        )
    {
        super(false);
        this.comBelongsTo_Z = comBelongsTo_T;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public abstract int intSearchWord(                //Busca "palabra" en un párrafo y regresa su posición.
                                                            //int, posición de la palabra en el párrafo, -1 si no
                                                            //      encontro.
                                                            //this[I], define el objeto.

                                                            //Palabra a buscar.
        String strWord_I
        );

    //------------------------------------------------------------------------------------------------------------------
}