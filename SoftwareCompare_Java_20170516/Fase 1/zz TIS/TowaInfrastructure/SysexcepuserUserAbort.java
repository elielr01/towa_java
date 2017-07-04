/*TASK SysexcepuserUserAbort*/
package TowaInfrastructure;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.util.Arrays;
															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: 19-Febrero-2014.
															//PURPOSE:
															//Clase para manipular exceptions.
public class SysexcepuserUserAbort extends Exception
                                                            //Excepción generada por SubAbort.
{
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //------------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    public SysexcepuserUserAbort(                           //Crea objeto DUMMY.
                                                            //this.*[O], crea sin asignar nada.
        )
    {
    }

    //------------------------------------------------------------------------------------------------------------------
    public SysexcepuserUserAbort(                           //Crea un objeto Exception.
                                                            //this.*[O], asigna valores.

                                                            //Nombre, Path relativo o Full Path.
        String strMessage_I
        )
    {
        super(strMessage_I);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/
