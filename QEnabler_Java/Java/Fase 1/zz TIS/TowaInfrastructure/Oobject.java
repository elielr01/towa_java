/*TASK Boxing*/
package TowaInfrastructure;

//AUTHOR: Towa (GLG-Gerardo López).
//CO-AUTHOR: Towa ().
//DATE: February 19, 2016.
//PURPOSE:
//Base for all Bxxx.
//=====================================================================================================================
public /*OPEN*/ class Oobject<T> extends BboxBaseBoxingAbstract
                                                            //Boxing of an object of any type. This class is used for
                                                            //      passing any object by reference, to simulate the
                                                            //      the out and ref parameters of C#. When using this,
                                                            //      instantiate a new Oobject of the class that is going
                                                            //      to be used. For example, to pass an array of ints by
                                                            //      reference:
                                                            //      Oobject<int[]> arrint = new Oobject<>();
                                                            //      And the method that will modify its value:
                                                            //      public void change(Oobject<int[]> arrint_O)
                                                            //      {
                                                            //          arrint.v = new int[]{x,x}
                                                            //      }
{
    //-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLE*/

    public T /*NSTD*/v/*END-NSTD*/;

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
    public Oobject() {}

    public Oobject(                                        //Box an object.
                                                           //this.*[O], assign variable.
        T t
        )
    {
        this.v = t;
    }
}
//=====================================================================================================================
/*END-TASK*/