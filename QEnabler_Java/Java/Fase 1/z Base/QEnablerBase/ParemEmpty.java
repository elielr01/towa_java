package QEnablerBase;

import TowaInfrastructure.*;

                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 23-Julio-2012.
                                                            //PURPOSE:
															//Implementación de Paragraph.
public class ParemEmpty extends ParParagraphAbstract
                                                            //Base para clases ParemxxXxxxx
                                                            //Información (nada) de párrafo vacío.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    private String[] arrstrLineParStandard_Z;
    @Override
    public String[] arrstrLineStandard()
    {
        //                                                  //Recalcula si el par es nuevo o ya fue reseteado.
        if (
            this.arrstrLineParStandard_Z == null
            )
        {
            this.subVerifyObjectConstructionIsFinished();

                                                            //Construye líneas de Código Estándar de un parem.
                                                            //El código de parem SIEMPRE será 1 línea con "nada", aún no
                                                            //      tiene la marca de inicio, la marca corresponde al
                                                            //      código del com no al código del párrafo.
            this.arrstrLineParStandard_Z = new String[] { "" };

            this.subSetIsResetOff();
        }

        return arrstrLineParStandard_Z;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void subReset()
    {
        this.arrstrLineParStandard_Z = null;

        super.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        return super.strTo(TestoptionEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public String strTo()
    {
        final String strCLASS = "Parem";

        return strCLASS + "{}" + "==>" + super.strTo();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    public ParemEmpty(                                      //Se construye el párrafo vacío (parte abstracta).
                                                            //this.*[O], asigna valores.

        ComCommentsAbstract comBelongsTo_T                  //Objeto com del cual este párrafo es parte.
        )
    {
        super(comBelongsTo_T);
        this.subReset();
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public int intSearchWord(                               //Busca palabra en párrafo vacío (no escuentra).
                                                            //int, siempre indica que no encontro con -1.
                                                            //this[I], define el objeto.

                                                            //Palabra a buscar.
        String strWord_I
        )
    {
        this.subVerifyObjectConstructionIsFinished();

        return -1;
    }
}
