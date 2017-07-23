package Tech;

import Ti.*;

//======================================================================================================================
public class T3dfextDefinitionT3fextTuple extends BtupleBaseTupleAbstract
//                                                          //SE USA EXCLUSIVAMENTE PARA DEFINIR LAS RELACIONES.
//                                                          //Las definisiones se publican más competar con T3fext.
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
    //                                                      //Instancia a la que pertenece este FILE EXTENSION
    public TechinstEnum techinst;

    //------------------------------------------------------------------------------------------------------------------
    @Override public  String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.strFILE_EXTENSION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strDESCRIPTION, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.techinst, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.strFILE_EXTENSION, "strFILE_EXTENSION") + ", " +
            Tes3.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " + Tes3.strTo(this.techinst, "techinst") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3dfextDefinitionT3fextTuple(String strFILE_EXTENSION_I, String strDESCRIPTION_I, TechinstEnum techinst_I)
    {
        super();
        this.strFILE_EXTENSION = strFILE_EXTENSION_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
        this.techinst = techinst_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
            /*CASE*/
        if (
            obj_I instanceof T3dfextDefinitionT3fextTuple
            )
        {
            intCompareTo = this.strFILE_EXTENSION.compareTo(
                ((T3dfextDefinitionT3fextTuple)obj_I).strFILE_EXTENSION);
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