/*TASK TechinstInstanceEnum*/
package SoftwareAutomation;

															//AUTHOR: Towa (JLV-Jose Luis Vera).
															//CO-AUTHOR: Towa (GLG-Gerardo Lopez).
															//DATE: 18-Enero-2014.
															//PURPOSE:
															//Enumerador utilizado para diferenciar las instancias
															//		dentro de la tecnología.

//======================================================================================================================
public enum TechinstInstanceEnum 							//Para cada tecnología incluida en TechtechTechnologyEnum se
															//      incluyen las diversas posibilidades de instancias.
{
	//##################################################################################################################
    Z_ERROR_NOT_DEFINED,
    //##################################################################################################################

                                                            //Para la tecnología COBOL.
    CB_IBM,
    CB_HP,
    CB_NEUTRAL,

                                                            //Para la tecnología OBJECT_NEUTRAL.
    OO_CSHARP,
    OO_JAVA,
}

//======================================================================================================================
/*END-TASK*/
