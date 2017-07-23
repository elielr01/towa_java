package Cod;
//                                                          //AUTHOR: Towa (GLG-Gerardo López).
//                                                          //CO-AUTHOR: Towa ().
//                                                          //DATE: 13-Mayo-2011.
//                                                          //PURPOSE:
//                                                          //Implementación de funciones o subrutinas de uso compartido
//                                                          //      en todos los sistemas.

import Ti.BtupleBaseTupleAbstract;
import Ti.Tes3;
import Ti.StrtoEnum;

public class T2alignmentRequiresAlignmentTuple extends BtupleBaseTupleAbstract
//                                                          //Map file extension and the requirent to align END_OF_LINE
//                                                          //      comments.
{
    //------------------------------------------------------------------------------------------------------------------
    public String strFileExtension;
    public boolean boolComElRequiresAlignment;

    //-----------------------------------------------------------------------------------------------------------------
    public String strTo(StrtoEnum testoptionSHORT_I)
    {
        return "<" + Tes3.strTo(this.strFileExtension, StrtoEnum.SHORT) + ", " +
                Tes3.strTo(this.boolComElRequiresAlignment, StrtoEnum.SHORT) + ">";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.strFileExtension, "strFileExtension") + ", " +
                Tes3.strTo(this.boolComElRequiresAlignment, "boolComElRequiresAlignment") + ">";
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

