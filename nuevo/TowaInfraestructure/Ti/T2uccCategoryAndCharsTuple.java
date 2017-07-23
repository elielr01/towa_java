package Ti;

//======================================================================================================================
public class T2uccCategoryAndCharsTuple extends BtupleBaseTupleAbstract
//                                                          //Map Unicode Category to characters.
{
    //------------------------------------------------------------------------------------------------------------------

    public /*KEY*/ UccUnicodeCategoryEnum ucc;
    public String strChars;

    //------------------------------------------------------------------------------------------------------------------
    @Override public  String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.ucc, StrtoEnum.SHORT) + ", " + Tes3.strTo(this.strChars, StrtoEnum.SHORT) +
            ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.ucc, "ucc") + ", " + Tes3.strTo(this.strChars, "strChars") + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum ucc_I, String strChars_I)
    {
        super();
        this.ucc = ucc_I;
        this.strChars = strChars_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        int intCompareTo;
        /*CASE*/
        if (
            obj_I instanceof T2uccCategoryAndCharsTuple
            )
        {
            intCompareTo = this.ucc.compareTo(((T2uccCategoryAndCharsTuple)obj_I).ucc);
        }
        else if (
            obj_I instanceof UccUnicodeCategoryEnum
            )
        {
            intCompareTo = this.ucc.compareTo(((UccUnicodeCategoryEnum)obj_I));
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