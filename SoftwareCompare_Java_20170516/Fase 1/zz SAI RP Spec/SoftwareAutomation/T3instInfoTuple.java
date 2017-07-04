package SoftwareAutomation;

import TowaInfrastructure.BtupleBaseTupleAbstract;
import TowaInfrastructure.Tes2;
import TowaInfrastructure.TestoptionEnum;

//==================================================================================================================
public class T3instInfoTuple extends BtupleBaseTupleAbstract
        //                                                      //Un arreglo de estas tuplas será usado para mapear una
        //                                                      //      instancia de tecnología con su información.
{
//--------------------------------------------------------------------------------------------------------------

        public /*KEY*/ TechinstEnum techinst;
        //                                                  //No debe tener caracteres [ ] |, solo 1 blanco entre
//                                                  //      palabras.
        public String strDESCRIPTION;
        //                                                  //Se definió incluyento la tupla dado que contiene más
//                                                  //      información útil
        public T4techInfoTuple t4tech;

        //--------------------------------------------------------------------------------------------------------------
        public String strTo(TestoptionEnum strtoSHORT_I) {
                return "<" + Tes2.strTo(this.techinst, TestoptionEnum.SHORT) + ", " +
                        Tes2.strTo(this.strDESCRIPTION, TestoptionEnum.SHORT) + ", " +
                        Tes2.strTo(this.t4tech, TestoptionEnum.SHORT) + ">";
        }

        //--------------------------------------------------------------------------------------------------------------
        public String strTo() {
                return "<" + Tes2.strTo(this.techinst, "techinst") + ", " +
                        Tes2.strTo(this.strDESCRIPTION, "strDESCRIPTION") + ", " + Tes2.strTo(this.t4tech, "t4tech") + ">";
        }

        //--------------------------------------------------------------------------------------------------------------
        public T3instInfoTuple(TechinstEnum techinst_I, String strDESCRIPTION_I, T4techInfoTuple t4tech_I)
        {
                super();
                this.techinst = techinst_I;
                this.strDESCRIPTION = strDESCRIPTION_I;
                this.t4tech = t4tech_I;
        }
}

