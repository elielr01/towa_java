/*TASK Boxing Boxing of primitives*/
package TowaInfrastructure;

                                                          	//AUTHOR: Towa (GLG-Gerardo López).
                                                          	//CO-AUTHOR: Towa ().
                                                          	//DATE: February 19, 2016.
                                                          	//PURPOSE:
                                                          	//Base for all Bxxx.
//=====================================================================================================================
public /*OPEN*/ class Ochar extends BboxBaseBoxingAbstract 	//Boxing char.
{
	//-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLE*/

	public char /*NSTD*/v/*END-NSTD*/;

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
	public Ochar () {}
	
	public Ochar (											//Box a char.
    														//this.*[O], assign variable. 
    		
    		char char_I
    		)
    {
            this.v = char_I;
    }
}
//=====================================================================================================================
/*END-TASK*/