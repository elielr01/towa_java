package SoftwareAutomation;

import TowaInfrastructure.BtupleBaseTupleAbstract;
import TowaInfrastructure.Tes2;
import TowaInfrastructure.TestoptionEnum;
import TowaInfrastructure.Tools;

//==================================================================================================================
public class T3fextInfoTuple extends BtupleBaseTupleAbstract
        //                                                      //Un arreglo de estas tuplas será usado para mapear un
        //                                                      //      file extension con su información.
{
//--------------------------------------------------------------------------------------------------------------

    //                                                  //Tiene la forma: .xxxx (xxxx deben ser minúsculas o
//                                                  //      dígitos)
    public /*KEY*/ String strFILE_EXTENSION;
    //                                                  //No debe tener caracteres [ ] |, solo 1 blanco entre
//                                                  //      palabras.
    public String strDESCRIPTION;
    //                                                  //Se definió incluyento la tupla dado que contiene más
//                                                  //      información útil
    public T3instInfoTuple t3inst;

    //--------------------------------------------------------------------------------------------------------------
    public String strTo(TestoptionEnum strtoSHORT_I) {
        return "<" + Tes2.strTo(this.strFILE_EXTENSION, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.strDESCRIPTION, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.t3inst, TestoptionEnum.SHORT) + ">";
    }

    //--------------------------------------------------------------------------------------------------------------
    public String strTo() {
        return "<" + Tes2.strTo(this.strFILE_EXTENSION, "strFILE_EXTENSION") + ", " +
                Tes2.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " +
                Tes2.strTo(this.t3inst, "t3inst") + ">";
    }

    //--------------------------------------------------------------------------------------------------------------
    public T3fextInfoTuple(String strFILE_EXTENSION_I, String strDESCRIPTION_I, T3instInfoTuple t3inst_I)
    {
        super();
        this.strFILE_EXTENSION = strFILE_EXTENSION_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
        this.t3inst = t3inst_I;
    }
}

//--------------------------------------------------------------------------------------------------------------
