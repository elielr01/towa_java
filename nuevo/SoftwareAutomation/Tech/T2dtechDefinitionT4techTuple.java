package Tech;

import Ti.*;

//======================================================================================================================
public class T2dtechDefinitionT4techTuple extends BtupleBaseTupleAbstract
//                                                          //SE USA EXCLUSIVAMENTE PARA DEFINIR LAS RELACIONES.
//                                                          //Las definisiones se publican más competar con T3fext.
//                                                          //Un arreglo de estas tuplas será usado para mapear una
//                                                          //      tecnología con su información.
{
    //------------------------------------------------------------------------------------------------------------------

    public /*KEY*/ TechtechEnum techtech;
    //                                                      //No debe tener caracteres [ ] |, solo 1 blanco entre
    //                                                      //      palabras.
    public String strDESCRIPTION;

    //------------------------------------------------------------------------------------------------------------------
    @Override public  String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.techtech, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strDESCRIPTION, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.techtech, "techtech") + ", " +
            Tes3.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T2dtechDefinitionT4techTuple(TechtechEnum techtech_I, String strDESCRIPTION_I)
    {
        super();
        this.techtech = techtech_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
        /*CASE*/
        if (
            obj_I instanceof T2dtechDefinitionT4techTuple
            )
        {
            intCompareTo = this.techtech.compareTo(((T2dtechDefinitionT4techTuple)obj_I).techtech);
        }
        else if (
            obj_I instanceof TechtechEnum
            )
        {
            intCompareTo = this.techtech.compareTo(((TechtechEnum)obj_I));
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