package QEnablerBase;

//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: 13-Mayo-2011.
															//PURPOSE:
															//Implementación de funciones o subrutinas de uso compartido
															//      en todos los sistemas.

import TowaInfrastructure.*;

public class T2alignmentRequiresAlignmentTuple extends BtupleBaseTupleAbstract
                                                            //Map file extension and the requirent to align END_OF_LINE
                                                            //      comments.
{
    //--------------------------------------------------------------------------------------------------------------
    public String strFileExtension;
    public boolean boolComElRequiresAlignment;

    //-----------------------------------------------------------------------------------------------------------------
    @Override public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        return "<" + Tes2.strTo(this.strFileExtension, TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.boolComElRequiresAlignment, TestoptionEnum.SHORT) + ">";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override public String strTo()
    {
        return "<" + Tes2.strTo(this.strFileExtension, "strFileExtension") + ", " +
            Tes2.strTo(this.boolComElRequiresAlignment, "boolComElRequiresAlignment") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T2alignmentRequiresAlignmentTuple(String strFileExtension_I, boolean boolComElRequiresAlignment_I)
    {
        super();
        this.strFileExtension = strFileExtension_I;
        this.boolComElRequiresAlignment = boolComElRequiresAlignment_I;
    }

    //------------------------------------------------------------------------------------------------------------------
}
