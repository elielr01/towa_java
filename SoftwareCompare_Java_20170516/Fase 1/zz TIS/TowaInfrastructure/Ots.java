/*TASK Boxing Boxing of primitives*/
package TowaInfrastructure;

                                                          	//AUTHOR: Towa (GLG-Gerardo López).
                                                          	//CO-AUTHOR: Towa ().
                                                          	//DATE: February 19, 2016.
                                                          	//PURPOSE:
                                                          	//Base for all Bxxx.

import java.time.LocalDateTime;

//=====================================================================================================================
public /*OPEN*/ class Ots extends BboxBaseBoxingAbstract 	//Boxing ts.
{
	//-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLE*/

	public LocalDateTime /*NSTD*/v/*END-NSTD*/;

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
	public Ots() {}
	
	public Ots (											//Box a ts.
    														//this.*[O], assign variable. 

			LocalDateTime ts_I
    		)
    {
        this.v = ts_I;
    }
}
//=====================================================================================================================
/*END-TASK*/