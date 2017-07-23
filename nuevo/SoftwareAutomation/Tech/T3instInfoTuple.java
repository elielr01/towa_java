package Tech;

import Ti.*;

//======================================================================================================================
public class T3instInfoTuple extends BtupleBaseTupleAbstract
//                                                          //Un arreglo de estas tuplas será usado para mapear una
//                                                          //      instancia de tecnología con su información.
{
    //------------------------------------------------------------------------------------------------------------------

    public /*KEY*/ TechinstEnum techinst;
    //                                                      //No debe tener caracteres [ ] |, solo 1 blanco entre
    //                                                      //      palabras.
    public String strDESCRIPTION;
    //                                                      //Se definió incluyento la tupla dado que contiene más
    //                                                      //      información útil
    public T4techInfoTuple t4tech;

    //------------------------------------------------------------------------------------------------------------------
    @Override public  String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.techinst, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strDESCRIPTION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.t4tech, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.techinst, "techinst") + ", " +
            Tes3.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " + Tes3.strTo(this.t4tech, "t4tech") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3instInfoTuple(TechinstEnum techinst_I, String strDESCRIPTION_I, T4techInfoTuple t4tech_I)
    {
        super();
        this.techinst = techinst_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
        this.t4tech = t4tech_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
            /*CASE*/
        if (
            obj_I instanceof T3instInfoTuple
                )
        {
            intCompareTo = this.techinst.compareTo(((T3instInfoTuple)obj_I).techinst);
        }
            else if (
        obj_I instanceof TechinstEnum
                )
        {
            intCompareTo = this.techinst.compareTo((TechinstEnum)obj_I);
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