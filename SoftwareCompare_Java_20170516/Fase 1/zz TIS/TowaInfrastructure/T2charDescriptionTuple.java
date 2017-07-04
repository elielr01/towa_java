/*TASK T2CharDescriptionTuple*/
package TowaInfrastructure;

															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: 13-Mayo-2011.
															//PURPOSE:
															//Implementación de funciones o subrutinas de uso compartido
															//      en todos los sistemas.

//=====================================================================================================================
public class T2charDescriptionTuple extends BtupleBaseTupleAbstract
															//Map special character description.
{
	//-----------------------------------------------------------------------------------------------------------------
    public char charChar;
    public String strDescription;

	//-----------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo(TestoptionEnum testoptionSHORT_I)
    {
        return "<" + Tes2.strTo(this.charChar, TestoptionEnum.SHORT) + ", " +
            Tes2.strTo(this.strDescription, TestoptionEnum.SHORT) + ">";
    }

	//-----------------------------------------------------------------------------------------------------------------
    @Override
    public String strTo()
    {
        return "<" + Tes2.strTo(this.charChar, "charChar") + ", " +
            Tes2.strTo(this.strDescription, "strDescription") + ">";
    }

	//-----------------------------------------------------------------------------------------------------------------
    public T2charDescriptionTuple(char charChar_I, String strDescription_I)
    {
        super();
        this.charChar = charChar_I;
        this.strDescription = strDescription_I;
    }

	//-----------------------------------------------------------------------------------------------------------------
}
//=====================================================================================================================
/*END-TASK*/