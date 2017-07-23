package Tech;

import Ti.*;

//======================================================================================================================
public class T3dinstDefinitionT3instTuple extends BtupleBaseTupleAbstract
//                                                          //SE USA EXCLUSIVAMENTE PARA DEFINIR LAS RELACIONES.
//                                                          //Las definisiones se publican más competar con T3fext.
//                                                          //Un arreglo de estas tuplas será usado para mapear una
//                                                          //      instancia de tecnología con su información.
{
    //------------------------------------------------------------------------------------------------------------------

    public /*KEY*/ TechinstEnum techinst;
    //                                                      //No debe tener caracteres [ ] |, solo 1 blanco entre
    //                                                      //      palabras.
    public String strDESCRIPTION;

    //                                                      //Tecnología a la que pertenece esta instancia.
    public TechtechEnum techtech;

    //------------------------------------------------------------------------------------------------------------------
    @Override public  String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.techinst, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strDESCRIPTION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.techtech, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.techinst, "techinst") + ", " +
            Tes3.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " + Tes3.strTo(this.techtech, "techtech") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3dinstDefinitionT3instTuple(TechinstEnum techinst_I, String strDESCRIPTION_I, TechtechEnum techtech_I)
    {
        super();
        this.techinst = techinst_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
        this.techtech = techtech_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
            /*CASE*/
        if (
            obj_I instanceof T3dinstDefinitionT3instTuple
                )
        {
            intCompareTo = this.techinst.compareTo(((T3dinstDefinitionT3instTuple)obj_I).techinst);
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