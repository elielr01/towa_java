/*TASK Boxing Boxing of primitives*/
package TowaInfrastructure;

															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: February 19, 2016.
															//PURPOSE:
															//Base for all Bxxx.
//=====================================================================================================================
public /*OPEN*/ class Oarrstr extends BboxBaseBoxingAbstract //Boxing bool.
{
    //-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLE*/

    public String[] /*NSTD*/v/*END-NSTD*/;

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
    public Oarrstr () {}

    public Oarrstr (										  //Box an array of strings.
                                                              //this.*[O], assign variable. 
    		
        String[] arrstr_I
    )
    {
        this.v = arrstr_I;
    }
}
//=====================================================================================================================
/*END-TASK*/