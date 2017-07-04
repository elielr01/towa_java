package SoftwareAutomation;

import TowaInfrastructure.BtupleBaseTupleAbstract;
import TowaInfrastructure.Tes2;
import TowaInfrastructure.Test;
import TowaInfrastructure.TestoptionEnum;

//==================================================================================================================
public class T4techInfoTuple extends BtupleBaseTupleAbstract
        //                                                      //Un arreglo de estas tuplas será usado para mapear una
        //                                                      //      tecnología con su información.
{
//--------------------------------------------------------------------------------------------------------------

    public /*KEY*/ TechtechEnum techtech;
    //                                                  //No debe tener caracteres [ ] |, solo 1 blanco entre
//                                                  //      palabras.
    public String strDESCRIPTION;
    //                                                  //Arreglo de instancias posibles en la tecnología
    public T3instInfoTuple[] arrt3instOPTION;
    //                                                  //Arreglo de file extensions posibles en la tecnología
    public T3fextInfoTuple[] arrt3fextOPTION;

    //--------------------------------------------------------------------------------------------------------------
    public String strTo(TestoptionEnum strtoSHORT_I) {
        return "<" + Tes2.strTo(this.techtech, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.strDESCRIPTION, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.arrt3instOPTION, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.arrt3fextOPTION, TestoptionEnum.SHORT) + ">";
    }

    //--------------------------------------------------------------------------------------------------------------
    public String strTo() {
        return "<" + Tes2.strTo(this.techtech, "techtech") + ", " +
                Tes2.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " +
                Tes2.strTo(this.arrt3instOPTION, "arrt3instOPTION") + ", " +
                Tes2.strTo(this.arrt3fextOPTION, "arrt3fextOPTION") + ">";
    }

    //--------------------------------------------------------------------------------------------------------------
    public T4techInfoTuple(TechtechEnum techtech_I, String strDESCRIPTION_I, T3instInfoTuple[] arrt3instOPTION_I,
                           T3fextInfoTuple[] arrt3fextOPTION_I)
    {
        super();
        this.techtech = techtech_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
        this.arrt3instOPTION = arrt3instOPTION_I;
        this.arrt3fextOPTION = arrt3fextOPTION_I;
    }
}
//======================================================================================================================
/*END-TASK*/