package Ti;


//======================================================================================================================
public abstract class BenumBaseEnumAbstract implements Comparable
//                                                          //Base class for all enums defined by user.
//                                                          //The purpose is to have a unique class to identify all
//                                                          //      enums.
{
    //------------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    //                                                      //Nombre del enum (Ej. "COMPONENT")
    private /*readonly*/ String str_Z;
    protected String str() { return this.str_Z; }

    //------------------------------------------------------------------------------------------------------------------
    public String strTo(
        //                                                  //Produces the name of the enum (Ej. "COMPONENT").
        //                                                  //this[I], instance variable.

        //                                                  //Included just for compatibility with other objects.
        StrtoEnum strtoOption_I
        )
    {
        return this.str_Z;
    }

    //------------------------------------------------------------------------------------------------------------------
    public String strTo(
        //                                                  //Produces the name of the enum (Ej. "COMPONENT").
        //                                                  //this[I], instance variable.
        )
    {
        return this.str_Z;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String toString()
    {
        return this.str();
    }

    //------------------------------------------------------------------------------------------------------------------
    protected BenumBaseEnumAbstract(
        //                                                  //Inicializa la parte más abstracta de cada enum.
        //                                                  //this.*[O], valor.

        String str_I
        )
    {
        this.str_Z = str_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    protected abstract int intEnumSequence(
        //                                                  //Provide enum sequence.
        //                                                  //this[I], access to object funtionality.

        //                                                  //int, enum sequence

        String str_I
        );

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        return Integer.compare(this.intEnumSequence(this.str_Z),
            this.intEnumSequence(((BenumBaseEnumAbstract) obj_I).str_Z));
    }

//----------------------------------------------------------------------------------------------------------------------
}

//======================================================================================================================
/*END-TASK*/
