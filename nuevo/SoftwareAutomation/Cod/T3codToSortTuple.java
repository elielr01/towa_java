/*TASK RPS.Cod Relevant Part Code*/
package Cod;

import Ti.*;

//                                                          //AUTHOR: Towa (EOG-Eduardo Ojeda).
//                                                          //CO-AUTHOR: Towa (GLG-Gerardo López).
//                                                          //DATE: 15-Febrero-2017.
//                                                          //PURPOSE:
//                                                          //Especificación de clase Abstract para Código.
public class T3codToSortTuple extends BtupleBaseTupleAbstract
//                                                          //Para ordenar conjunto de cod por 2 llaves (filename y
//                                                          //      fullpath).
//                                                          //Nótese que el fullpath puede tener mayúsculas y minúsculas
//                                                          //      lo cual no hace diferencia para identificar el path,
//                                                          //      sin embargo si hace diferencia en el ordenamiento de
//                                                          //      strings, dado esto es necesario que el filename y el
//                                                          //      syspath se registren en esta tupla en minúsculas).
{
    //------------------------------------------------------------------------------------------------------------------


    //                                                      //La llave debe estar en minúsculas.
    public /*KEY*/ String strFileName;
    public /*KEY*/ String strFullPath;

    public CodCodeAbstract cod;

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo(StrtoEnum strtoSHORT_I)
    {
        return "<" + Tes3.strTo(this.strFileName, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.strFullPath, StrtoEnum.SHORT) + ", " +
            Tes3.strTo(this.cod, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public String strTo()
    {
        return "<" + Tes3.strTo(this.strFileName, "strFileName") + ", " +
            Tes3.strTo(this.strFullPath, "strFullPath") + ", " +
            Tes3.strTo(this.cod, StrtoEnum.SHORT) + ">";
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3codToSortTuple(String strFileName_I, String strFullPath_I, CodCodeAbstract cod_I)
    {
        super();
        this.strFileName = strFileName_I;
        this.strFullPath = strFullPath_I;
        this.cod = cod_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    public T3codToSortTuple(String strFileName_I, String strFullPath_I)
    {
        super();
        this.strFileName = strFileName_I;
        this.strFullPath = strFullPath_I;

        this.cod = null;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override public int compareTo(Object obj_I)
    {
        if (
            !(obj_I instanceof T3codToSortTuple)
            )
            Tes3.subAbort(Tes3.strTo(this, "this") + ", " + Tes3.strTo(obj_I, "obj_I") +
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

    //------------------------------------------------------------------------------------------------------------------
}
/*END-TASK*/