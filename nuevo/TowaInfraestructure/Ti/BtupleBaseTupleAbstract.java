/*TASK BtupleBaseTupleAbstract*/
package Ti;

//													        //AUTHOR: Towa (GLG-Gerardo López).
//													        //CO-AUTHOR: Towa ().
//													        //DATE: January 2, 2016.
//													        //PURPOSE:
//													        //Base for all tuples.

//=====================================================================================================================
public abstract /*INMUTABLE*/ class BtupleBaseTupleAbstract implements Comparable
//													        //Base class for all tuple defined by user.
//                                                          //The purpose is to have a unique class to identify all
//                                                          //      tuples.
//                                                          //"key" puede ser simple o múltiple (varias "key" en orden
//                                                          //      de jerarquía son la llave).
//                                                          //En ambos casos Array.Sort(arrtnxxxx) o
//                                                          //      Array.Sort(arrtnxxxx, arrobj).
//                                                          //1. Cuando "key" es simple:
//                                                          //1a. Ver 3 ejemplos en SAI Tech.cs.
//                                                          //1b. Array.BinarySearch(arrtnxxx, objKey).
//                                                          //2. Cuando "key" es múltiple:
//                                                          //2a. Ver ejemplo T6mkeMultipleKeyExampleTuple.cs en
//                                                          //      TowaInfrastructure.
//                                                          //2b. Array.BinarySearch(arrtnxxx, tnxxxKey), con las keys
//                                                          //      se debe construir un tnxxx usando el constructor de
//                                                          //      "key".
{
	//-----------------------------------------------------------------------------------------------------------------
    public abstract String strTo(
        //                                                  //THIS METHOD SHOULD BE IMPLEMENTED IN EVERY TUPLE.
        //                                            		//Produces a SHORT version of information for each of its
        //                                            		//      item.
        //                                            		//Example:
        //                                            		//<item, item, ..., item>
        //                                            		//this[I], all its instance variables.

        //                                            		//SHORT, produces a short versión of information (other
        //                                            		//      values will be ignored).
        StrtoEnum testoptionOption_I
        );

    //-----------------------------------------------------------------------------------------------------------------
    public abstract String strTo(
        //                                                  //THIS METHOD SHOULD BE IMPLEMENTED IN EVERY TUPLE.
        //                                            		//Produces a FULL version of information for each of its
        //                                            		//      item.
        //                                            		//Example:
        //                                            		//Class{item, item, ..., item}
        //                                            		//this[I], all its instance variables.
        );

    //-----------------------------------------------------------------------------------------------------------------
    protected BtupleBaseTupleAbstract(
        //                                                  //Inicializa la parte más abstracta de cada tuple.
        //                                            		//this.*[O], nada.
        )
    {
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public int compareTo(Object o)
    {
        //                                                  //Este método se debe implementar en todas las tuplas
        //                                                  //      que tienen "key" la cual se requiere para poder
        //                                                  //      usar los métodos Array.Sort y Array.BinarySearch.
        Tes3.subAbort(Tes3.strTo(this, "this") + " this tuple does not implement compareTo()");

        return 0;
    }

    //-----------------------------------------------------------------------------------------------------------------
}
//=====================================================================================================================
/*END-TASK*/
