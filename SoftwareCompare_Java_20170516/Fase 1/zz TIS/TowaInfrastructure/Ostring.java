/*TASK Boxing Boxing of primitives*/
package TowaInfrastructure;

                                                          	//AUTHOR: Towa (GLG-Gerardo López).
                                                          	//CO-AUTHOR: Towa ().
                                                          	//DATE: February 19, 2016.
                                                          	//PURPOSE:
                                                          	//Base for all Bxxx.

//=====================================================================================================================
public /*OPEN*/ class Ostring extends BboxBaseBoxingAbstract 
															//Boxing string.
{
	//-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLE*/

	public String /*NSTD*/v/*END-NSTD*/;

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
	public Ostring() {}
	
	public Ostring (										//Box a string.
    														//this.*[O], assign variable. 
			
			String string_I
    		)
    {
        this.v = string_I;
    }
}
//=====================================================================================================================
/*END-TASK*/