package Tech;

import Ti.*;

//======================================================================================================================
public class T3fextInfoTuple extends BtupleBaseTupleAbstract
//                                                          //Un arreglo de estas tuplas será usado para mapear un
//                                                          //      file extension con su información.
{
    //------------------------------------------------------------------------------------------------------------------

    //                                                      //Tiene la forma: .xxxx (xxxx deben ser minúsculas o
    //                                                      //      dígitos)
    public /*KEY*/ String strFILE_EXTENSION;
    //                                                      //No debe tener caracteres [ ] |, solo 1 blanco entre
    //                                                      //      palabras.
    public String strDESCRIPTION;
    //                                                      //Se definió incluyento la tupla dado que contiene más
    //                                                      //      información útil
    public T3instInfoTuple t3inst;

    //------------------------------------------------------------------------------------------------------------------
    @Override public  String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.strFILE_EXTENSION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strDESCRIPTION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.t3inst, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.strFILE_EXTENSION, "strFILE_EXTENSION") + ", " +
            Tes3.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " +
            Tes3.strTo(this.t3inst, "t3inst") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3fextInfoTuple(String strFILE_EXTENSION_I, String strDESCRIPTION_I, T3instInfoTuple t3inst_I)
    {
        super();
        this.strFILE_EXTENSION = strFILE_EXTENSION_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
        this.t3inst = t3inst_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
        /*CASE*/
        if (
            obj_I instanceof T3fextInfoTuple
            )
        {
            intCompareTo = this.strFILE_EXTENSION.compareTo(((T3fextInfoTuple)obj_I).strFILE_EXTENSION);
        }
        else if (
            obj_I instanceof String
            )
        {
            intCompareTo = this.strFILE_EXTENSION.compareTo((String)obj_I);
        }
        else
        {
            Tes3.subAbort(Tes3.strTo(this, "this") + ", " + Tes3.strTo(obj_I, "obj_I") +
                " is not compatible with this tuple");

            intCompareTo = -1;
        }
        /*CASE*/

        return intCompareTo;
    }

    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/