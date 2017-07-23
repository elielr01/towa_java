package Ti;

//======================================================================================================================
public class T2charDescriptionTuple extends BtupleBaseTupleAbstract
//                                                          //Map special character description.
{
    //------------------------------------------------------------------------------------------------------------------
    public /*KEY*/ char charX;
    public String strDESCRIPTION;

    //------------------------------------------------------------------------------------------------------------------
    @Override public  String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.charX, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strDESCRIPTION, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.charX, "charX") + ", " + Tes3.strTo(this.strDESCRIPTION, "strDESCRIPTION") +
            ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T2charDescriptionTuple(char charCHAR_I, String strDESCRIPTION_I)
    {
        super();
        this.charX = charCHAR_I;
        this.strDESCRIPTION = strDESCRIPTION_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
        /*CASE*/
        if (
            obj_I instanceof T2charDescriptionTuple
            )
        {
            intCompareTo = Character.compare(this.charX, ((T2charDescriptionTuple)obj_I).charX);
        }
        else if (
            obj_I instanceof Character
            )
        {
            intCompareTo = Character.compare(this.charX, (char) obj_I);
        }
        else
        {
            Tes3.subAbort(Tes3.strTo(this, "this") + ", " + Tes3.strTo(obj_I, "obj_I") +
                " is not compatible with this tuple");

            intCompareTo = -1;
        }
        /*CASE*/

        return intCompareTo;
    }

    //------------------------------------------------------------------------------------------------------------------
}
//======================================================================================================================
/*END-TASK*/