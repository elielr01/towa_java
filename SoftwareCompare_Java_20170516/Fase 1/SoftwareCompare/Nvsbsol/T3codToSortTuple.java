package Nvsbsol;
import QEnablerBase.*;
import TowaInfrastructure.*;

//======================================================================================================================
public class T3codToSortTuple extends BtupleBaseTupleAbstract
                                                            //Para ordenar conjunto de cod por 2 llaves (filename y
                                                            //      fullpath).
                                                            //Nótese que el fullpath puede tener mayúsculas y minúsculas
                                                            //      lo cual no hace diferencia para identificar el path,
                                                            //      sin embargo si hace diferencia en el ordenamiento de
                                                            //      strings, dado esto es necesario que el filename y el
                                                            //      syspath se registren en esta tupla en minúsculas).
{
//----------------------------------------------------------------------------------------------------------------------

                                                            //La llave debe estar en minúsculas.
    public /*KEY*/ String strFileName;
    public /*KEY*/ String strFullPath;

    public CodCodeAbstract cod;

    //------------------------------------------------------------------------------------------------------------------
    public String strTo(TestoptionEnum strtoSHORT_I)
    {
        return "<" + Tes2.strTo(this.strFileName, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.strFullPath, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.cod, TestoptionEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public String strTo()
    {
        return "<" + Tes2.strTo(this.strFileName, "strFileName") + ", " +
                Tes2.strTo(this.strFullPath, "strFullPath") + ", " +
                Tes2.strTo(this.cod, TestoptionEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3codToSortTuple(String strFileName_I, String strFullPath_I, CodCodeAbstract cod_I)
     //       : base()
    {
        super();
        this.strFileName = strFileName_I;
        this.strFullPath = strFullPath_I;
        this.cod = cod_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3codToSortTuple(String strFileName_I, String strFullPath_I)
    //       : base()
    {
        super();
        this.strFileName = strFileName_I;
        this.strFullPath = strFullPath_I;

        this.cod = null;
    }

    //------------------------------------------------------------------------------------------------------------------
    public Integer CompareTo(Object obj_I)
    {
        if (
                !(obj_I.getClass() == T3codToSortTuple.class)
            )
        Tools.subAbort(Tes2.strTo(this, "this") + ", " + Tes2.strTo(obj_I, "obj_I") +
                " is not compatible with this tuple");

        T3codToSortTuple t3cod = (T3codToSortTuple)obj_I;


        int intCompareTo = this.strFileName.compareTo(t3cod.strFileName);
        if (
                intCompareTo == 0
                )
        {
            intCompareTo = this.strFullPath.compareTo(t3cod.strFullPath);
        }

        return intCompareTo;
    }

    //--------------------------------------------------------------------------------------------------------------
}

//==================================================================================================================
