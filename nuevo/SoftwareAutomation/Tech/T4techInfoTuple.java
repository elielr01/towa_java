package Tech;

import Ti.*;

//======================================================================================================================
public class T4techInfoTuple extends BtupleBaseTupleAbstract
//                                                          //Un arreglo de estas tuplas será usado para mapear una
//                                                          //      tecnología con su información.
{
    //------------------------------------------------------------------------------------------------------------------

    public /*KEY*/ TechtechEnum techtech;
    //                                                      //No debe tener caracteres [ ] |, solo 1 blanco entre
    //                                                      //      palabras.
    public String strDESCRIPTION;
    //                                                      //Arreglo de instancias posibles en la tecnología
    public T3instInfoTuple[] arrt3instOPTION;
    //                                                      //Arreglo de file extensions posibles en la tecnología
    public T3fextInfoTuple[] arrt3fextOPTION;

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo(StrtoEnum strtoSHORT_I) {
        return "<" + Tes3.strTo(this.techtech, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strDESCRIPTION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.arrt3instOPTION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.arrt3fextOPTION, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo() {
        return "<" + Tes3.strTo(this.techtech, "techtech") + ", " +
            Tes3.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " +
            Tes3.strTo(this.arrt3instOPTION, "arrt3instOPTION") + ", " +
            Tes3.strTo(this.arrt3fextOPTION, "arrt3fextOPTION") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T4techInfoTuple(TechtechEnum techtech_I, String strDESCRIPTION_I, T3instInfoTuple[] arrt3instOPTION_I,
       T3fextInfoTuple[] arrt3fextOPTION_I)
    {
        super();
        this.techtech = techtech_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
        this.arrt3instOPTION = arrt3instOPTION_I;
        this.arrt3fextOPTION = arrt3fextOPTION_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
        /*CASE*/
        if (
            obj_I instanceof T4techInfoTuple
            )
        {
            intCompareTo = this.techtech.compareTo(((T4techInfoTuple)obj_I).techtech);
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
