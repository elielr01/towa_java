/*TASK BtupleBaseTupleAbstract*/
package TowaInfrastructure;

															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: January 2, 2016.
															//PURPOSE:
															//Base for all tuples.

//=====================================================================================================================
public abstract /*INMUTABLE*/ class BtupleBaseTupleAbstract 
															//Base class for all tuple defined by user.
                                                    		//The purpose is to have a unique class to identify all
                                                      		//      tuples.
{
	//-----------------------------------------------------------------------------------------------------------------
    public abstract String strTo(                       	//THIS METHOD SHOULD BE IMPLEMENTED IN EVERY TUPLE.
                                                      		//Produces a SHORT version of information for each of its
                                                      		//      item.
                                                      		//Example:
                                                      		//<item, item, ..., item>
                                                      		//this[I], all its instance variables.

                                                      		//SHORT, produces a short versión of information (other
                                                      		//      values will be ignored).
        TestoptionEnum testoptionOption_I
        );

    //-----------------------------------------------------------------------------------------------------------------
    public abstract String strTo(                       	//THIS METHOD SHOULD BE IMPLEMENTED IN EVERY TUPLE.
                                                      		//Produces a FULL version of information for each of its
                                                      		//      item.
                                                      		//Example:
                                                      		//Class{item, item, ..., item}
                                                      		//this[I], all its instance variables.
        );

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
    protected BtupleBaseTupleAbstract(                     	//Inicializa la parte más abstracta de cada tuple.
                                                      		//this.*[O], nada. 
        )
    {
    }

}
//=====================================================================================================================
/*END-TASK*/
