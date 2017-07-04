/*TASK Boxing Boxing of primitives*/
package TowaInfrastructure;

															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: February 19, 2016.
															//PURPOSE:
															//Base for all Bxxx.
//=====================================================================================================================
public /*OPEN*/ class Oarrchar extends BboxBaseBoxingAbstract //Boxing char.
{
    //-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLE*/

    public char[] /*NSTD*/v/*END-NSTD*/;

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
    public Oarrchar() {}

    public Oarrchar(                                          //Box an array of chars.
                                                              //this.*[O], assign variable. 

        char[] arrchar_I
        )
    {
        this.v = arrchar_I;
    }
}
//=====================================================================================================================
/*END-TASK*/