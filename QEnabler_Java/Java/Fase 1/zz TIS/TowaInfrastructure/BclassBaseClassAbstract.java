/*TASK Bclass Base Class*/
package TowaInfrastructure;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.io.PrintWriter;

															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: June 24, 2015.
															//PURPOSE:
															//Base for all classes.

//=====================================================================================================================
public abstract /*MUTABLE*/ class BclassBaseClassAbstract
		                                                    //Clase base para todos los objetos, conforme al estandar
		                                                    //      Towa, TODOS los objetos que diseñemos deben heredar
		                                                    //      de esta clase.
		                                                    //Entre otras cosas, esta clase provee facilidades para
		                                                    //      evaluar el desempeño de una aplicación desarrollada
		                                                    //      conforme a estos estándares.
		                                                    //(algo ya esta aquí, sin embargo en el futuro se puede
		                                                    //      añadir mas capacidades, ojo se debe SER CAUTELOSO
		                                                    //      dado que todo esto afectará la eficiencia).
{
    //-----------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

                                                      		//Define si el objeto es INMUTABLE, MUTABLE o OPEN.
                                                      		//Solo los MUTABLE recolectar información en UsedIn.
                                                      		//Nótese que un objeto es MUTABLE si al menos UNA de sus
                                                      		//      varibles es MUTABLE, esta variable puede estár en
                                                      		//      la clase concreta o en cualquiera de las clases
															//      abstractas que le dan forma (de esto se excluye
                                                      		//      Bclass que es un caso especial).
	protected abstract BclassmutabilityEnum bclassmutability();

	/*STATIC VARIABLES*/

                                                      		//Diccionario para registrar la cuenta de todos los objetos
															//      que contruye la aplicación al estar operando.
		                                                    //Llave: Type (será el Fulname de la clase concreta) del
		                                                    //      objeto.
		                                                    //Info: Cantidad de objetos que se han creado durante la
		                                                    //      operación de la aplicación.
	private static LinkedHashMap<String, Integer> dicintObjectCount;

															//Total de UsedIn en TODOS los objetos de la aplicación.
	private static int intUsedInAddTotalCount;
	private static int intUsedInRemoveTotalCount;

                                                            //Conjunto de Type de objectos DUMMY.
                                                            //Por ESTÁNDARD solo se permite construír un objeto DUMMY
                                                            //      (objDUMMY_UNIQUE) para cada clase concreta.
                                                            //Esta lista permite asegurar que se cumpla esté estándar,
                                                            //      se abortara si no se cumple.
    private static LinkedList<Class> lsttypeDummyUnique;

	static                   								//Inicializa información estática.
    {
                                                 			//Inicializa el diccionario para la cuenta de objetos que
                                                    		//      construye la aplicación.
        BclassBaseClassAbstract.dicintObjectCount = new LinkedHashMap<String, Integer>();

        BclassBaseClassAbstract.intUsedInAddTotalCount = 0;
        BclassBaseClassAbstract.intUsedInRemoveTotalCount = 0;

                                                            //Inicializa el lista de DUMMY.
        BclassBaseClassAbstract.lsttypeDummyUnique = new LinkedList<Class>();
    }

	//-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/
    
                                                            //Indica si este objeto es DUMMY
        private boolean boolIsDummy_Z;
        public boolean boolIsDummy() { return this.boolIsDummy_Z; }

                                                            //Indica si el objeto ha sido reseteado.
                                                            //Saber esto es necesario para no propagar el reset con la
                                                            //      lista UsedIn lo cual podría causar un ciclo.
                                                            //Esto funciona de la siguiente forma:
                                                            //a. Al iniciar la contrucción del objeto (en el constructor
                                                            //      de esta clase que es la más abstracta de todos los
                                                            //      objetos) se establece este valor en false.
                                                            //b. Al concluír la construcción del objeto, al hacer el
                                                            //      subReset() se establece este valor en true, esto se
                                                            //      hará también en esta clase que es la parte más
                                                            //      profunda del subReset().
                                                            //c. Al calcular cualquier variable calculada se debe
                                                            //      ejecutar el método subSetIsResetOff() el cual pondrá
                                                            //      este valor en false.
        private /*MUTABLE*/ boolean boolIsReset;

                                                            //Indica si la construcción del objecto ya fue concluída.
                                                            //Saber esto es conveniente para poder proteger el código
                                                            //      de manera que se diagnostique (ABORTE) si antes de
                                                            //      que este construído completamente el objeto se
                                                            //      pretende hacer referencia a:
                                                            //1. Alguna variable calculada.
                                                            //2. Algún método de transformación.
                                                            //3. Algúm método de consulta.
                                                            //Esto funciona de la siguiente forma:
                                                            //a. Al iniciar la contrucción del objeto (en el constructor
                                                            //      de esta clase que es la más abstracta de todos los
                                                            //      objetos) se establece este valor en false.
                                                            //b. Al concluír la construcción del objeto, al hacer el
                                                            //      subReset() se establece este valor en true, esto se
                                                            //      hará también en esta clase que es la parte más
                                                            //      profunda del subReset().
                                                            //c. Nótese que cada vez que se haga el subReset() se vuelve
                                                            //      a establecer en true, para efectos de lo que se
                                                            //      busca esto no es necesario, sin embargo no afecta.
                                                            //d. Al iniciar el proceso de: Variable Calculada, Método de
                                                            //      Transformación y Método Acceso de  se ejecuta el
                                                            //      método subVerifyObjectConstructionFinished() el cuál
                                                            //      abortará si aún no esta concluída la construcción
                                                            //      del objeto.
        private /*MUTABLE*/ boolean boolIsObjectConstructionFinished;

                                                      		//Registra objetos (concretos) que "usan" la información de
                                                      		//      "este" objeto.
                                                      		//Esto es necesario, dado que si este objeto es modificado,
                                                      		//      por lo cual requiere ser reseteado, el reseteo debe
                                                      		//      propagarse a todos los objetos que "usan" "este
                                                      		//      objeto.
                                                      		//Ejemplo, un objeto Journal Entry esta en USD y hace
                                                      		//      referencia a un objeto Currency para tomar de ahí
                                                      		//      los tipos de cambio, este objeto Journal Entry debe
                                                      		//      debe añadirse a la lista de "used in" del objeto
                                                      		//      currency para que al cambiar algo en currency le
                                                      		//      pueda avisar a Journal Entry que cambio.
                                                      		//Nótese que el añadir una referencia de "uso" NO SIGNIFICA
                                                      		//      que este objeto fue modificado (no se resetea).
                                                      		//Para evitar que esta información sea usada indebidamente
                                                      		//      se declara "private".
                                                      		//Solo los objetos MUTABLE recolectan esta información, en
                                                      		//      objetos, este valor debe ser null.
	private /*MUTABLE*/ LinkedList<BclassBaseClassAbstract> lstbclassThisIsUsedIn;

	//-----------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //-----------------------------------------------------------------------------------------------------------------

    public void subReset()
    {
                                                            //AQUÍ NO SE TIENE NINGUNA VARIABLE CALCULADA.
                                                            //SE REQUIEREN ALGUNAS ACCIONES DE PREPARACIÓN DEL OBJETO.

                                                            //Indica que el objeto ha sido reseteado.
            this.boolIsReset = true;

                                                            //Indica que YA ESTA CONCLUÍDA la construcción del objeto.
                                                            //A partir de esto ya será posible accesar la funcionalidad
                                                            //      de este objeto.
            this.boolIsObjectConstructionFinished = true;

            if (
                                                            //null significa que ese tipo de objeto no tiene UsedIn dado
                                                            //      que no es MUTABLE.
                this.lstbclassThisIsUsedIn == null
                )
            {
                                                            //NO HACE NADA.
            }
            else
            {
                                                            //Propaga el reseteo a los objetos que usan este objeto.
                for (BclassBaseClassAbstract bclassThisObjectIsUsedIn: this.lstbclassThisIsUsedIn)
                {
                    if (
                                                            //YA ESTA reseteado
                        this.boolIsReset
                        )
                    {
                                                            //NO HACE NADA.
                    }
                    else
                    {
                                                            //Propaga el reset
                        bclassThisObjectIsUsedIn.subReset();
                    }
                }
            }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public String strTo(                        			//SHORT display.
                                                            //THIS METHOD SHOULD BE IMPLEMENTED IN EVERY CLASS (ABSTRACT
                                                            //      OR CONCRETE).
                                                            //The final format of the string will be:
                                                            //ObjId[BclassVariables, AbstractVariables, ...,
                                                            //      AbstractVariables, ConcreteVariables].
                                                            //To produce this string:
                                                            //1. Concrete class produces:
                                                            //ObjId[base.testoption(S) + Variable + ... + Variable].
                                                            //2. All abstract classes (except Bclass) produce:
                                                            //base.testoption(S) + Variable + ... + Variable.
                                                            //3. Bclass produces:
                                                            //Variable + ... + Variable, (see below).
                                                            //4. Variable is:.
                                                            //4a. Tes2.strTo(Variable, TestoptionEnum.SHORT).
                                                            //4b. When variable is lstobj, queueobj or stackobj you need
                                                            //      to call strTo with 3 parameters, this method is an
                                                            //      example (see support methods below).
                                                            //4c. When variable is dirobj you need to call strTo with 4
                                                            //      parameters (see example in class 
                                                            //      SemsolooObjectOriented).
                                                            //4d. When variable is vkpobj you need to call strTo with 4
                                                            //      parameters (no example included, should be similar
                                                            //      to 4c but simpler).
                                                            //4e. obj is class, tuple, enum or Exception (other object
                                                            //      should use 2 paramenter methods.
		                                                    //(see examples).
		                                                    //this[I], all its instance variables.

		                                                    //str, display information

		                                                    //SHORT option (other options will be ignored).
        TestoptionEnum testoptionSHORT
        )
    {
                                                      		//En la versión corta se decidió no agregar nada.
        return "";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String strTo (                        			//FULL display.
                                                             //THIS METHOD SHOULD BE IMPLEMENTED IN EVERY CLASS (ABSTRACT
                                                            //      OR CONCRETE).
                                                            //The final format of the string will be:
                                                            //ObjId{Variables}==>Class{Variables}==>...==>
                                                            //      Class{Variables}==>Bclass{Variables}. 
                                                            //To produce this string:
                                                            //1. Concrete class produces:
                                                            //ObjId{Variable + ... + Variable}==>base.testoption().
                                                            //2. All abstract classes (except Bclass) produce:
                                                            //ClassPrefix{Variable + ... + Variable}==>base.strTo().
                                                            //3. Bclass produces:
                                                            //Bclass{Variable + ... + Variable}.
                                                            //4. Variable is:.
                                                            //4a. Tes2.strTo(Variable, "Variable").
                                                            //4b-e (see method description above).
                                                            //this[I], all its instance variables.

                                                      		//str, display information
        )
    {
        final String strCLASS = "Bclass";

                                                      		//Will report only prefix of the objects in
                                                      		//      lstbclassThisIsUsedIn (can be null)

        return strCLASS + "{" + Tes2.strTo(this.arrstrPrefix(), TestoptionEnum.SHORT, this.lstbclassThisIsUsedIn) +
            "}";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private BclassBaseClassAbstract[] arrbclassThisIsUsedInLstTo(
		                                                    //The name of this method should be:
                                                            //lstobjXxxxx ==> arrobjXxxxxLstTo().
                                                            //queueobjXxxxx ==> arrobjXxxxxQueueTo().
                                                            //stackobjXxxxx ==> arrobjXxxxxStackTo().
                                                            //For dic convertion, the name of the methods should be:
                                                            //dicobjXxxxx ==> arrobjXxxxxDicTo() & arrstrKeyXxxxxDicTo().
                                                            //
                                                            //lst, queue and stack of bclass, btuple, enum or Exception
                                                            //      need to be converted to arrobj before calling strTo
                                                            //      method with 3 paramenters.
                                                            //This method is an example and should be coded after strTo
                                                            //      methods.
                                                            //
                                                            //To call this method:
                                                            //(see examples above).
                                                            //If lst, ... is static, paramenter or local variable, you
                                                            //      need an static method and pass lst,... as
                                                            //      paramenter.
                                                            //arrbclass, lstbclass converted

		                                                    //this[I], lstbclassThisIsUsedIn
        )
    {
        BclassBaseClassAbstract[] arrbclassThisIsUsedInLstTo;
        if (
            this.lstbclassThisIsUsedIn == null
            )
        {
            arrbclassThisIsUsedInLstTo = null;
        }
        else
        {
            arrbclassThisIsUsedInLstTo =
                this.lstbclassThisIsUsedIn.toArray(new BclassBaseClassAbstract[lstbclassThisIsUsedIn.size()]);
        }

        return arrbclassThisIsUsedInLstTo;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String[] arrstrPrefix(                      	//Sometimes content is convertes to str values.
    														//
	                                                      	//To call this method:
	                                                      	//(see examples above).
	                                                      	//arrbclass, lstbclass converted

	                                                  		//this[I], lstbclassThisIsUsedIn
        )
    {
        String[] arrstrPrefix;
        if (
            this.lstbclassThisIsUsedIn == null
            )
        {
            arrstrPrefix = null;
        }
        else
        {
            arrstrPrefix = new String[this.lstbclassThisIsUsedIn.size()];
            for (int intI = 0; intI < this.lstbclassThisIsUsedIn.size(); intI = intI + 1)
            {
                String strObjId = Tes2.strGetObjId(this.lstbclassThisIsUsedIn.get(intI));

                                                      		//ObjId has the form Prefix:HashCode
                arrstrPrefix[intI] = strObjId.substring(0, strObjId.lastIndexOf(':'));
            }
        }

        return arrstrPrefix;
    }

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
    protected BclassBaseClassAbstract(                     	//Inicializa la parte más abstracta de cada objeto, y.
                                                            //Acumula a la parte estática la creación de un objeto de
                                                            //      cierto type.
                                                            //this.*[O], Asigna lstbclass vacía.

                                                            //Debe ser true
        boolean boolIsDummy_I
        )
    {
                                                            //INSTANCE PART.

                                                            //This is THE ONLY value asigned to a DUMMY object
            this.boolIsDummy_Z = boolIsDummy_I;

            if (
                                                            //Estamos en un objeto DUMMY
                boolIsDummy_I
                )
            {
                                                            //STATIC PART (ONE SET OF INFORMATION FOR THE APPLICATION).

                if (
                                                            //Ya se tiene un DUMMY de este tipo.
                    BclassBaseClassAbstract.lsttypeDummyUnique.contains(this.getClass())
                    )
                    Tools.subAbort(
                        Tes2.strTo(BclassBaseClassAbstract.lsttypeDummyUnique.toArray(),
                            "BclassBaseClassAbstract.lsttypeDummyUnique.ToArray()") + " has already a DUMMY of " +
                        Tes2.strTo(this.getClass(), "this.GetType()"));

                                                            //Registra objeto DUMMY en la lista de DUMMYs
                BclassBaseClassAbstract.lsttypeDummyUnique.add(this.getClass());

                                                            //El objeto DUMMY no se contabiliza en el diccionario.
            }
            else
            {
                                                            //INSTANCE PART.

                                                            //Al concluir la construcción, en la clase concreta se
                                                            //      ejecuta el subReset() que cambiara esto a true.
                this.boolIsReset = false;

                                                            //Indica que AÚN NO ESTA CONCLUÍDA la construcción del
                                                            //      objeto.
                                                            //Al concluir la construcción, en la clase concreta se
                                                            //      ejecuta el subReset() que cambiara esto a true.
                                                            //La asignación de false al principio ES NECESARIA para
                                                            //      evitar que la funcionalidad del método se utilizada
                                                            //      ANTES de concluir la construcción.
                this.boolIsObjectConstructionFinished = false;

                                                            //Inicializa lista de UsedIn
                if (
                    this.bclassmutability() == BclassmutabilityEnum.MUTABLE
                    )
                {
                    this.lstbclassThisIsUsedIn = new LinkedList<BclassBaseClassAbstract>();
                }
                else
                {
                                                            //Solo los objetos MUTABLE recolectan esta información.
                    this.lstbclassThisIsUsedIn = null;
                }

                                                            //STATIC PART (ONE SET OF INFORMATION FOR THE APPLICATION).

                String strTypeThisFullNameAndMutability = this.getClass().getName() + "|" + this.bclassmutability();

                                                            //Create dictionary entry if needed.
                if (
                     BclassBaseClassAbstract.dicintObjectCount.containsKey(strTypeThisFullNameAndMutability)
                    )
                {
                    //                                     //Do nothing
                }
                else
                {
                    BclassBaseClassAbstract.dicintObjectCount.put(strTypeThisFullNameAndMutability,0);
                }
                //TODO checar si esto funciona correctamente
                                                            //Add count
                BclassBaseClassAbstract.dicintObjectCount.put(strTypeThisFullNameAndMutability,
                        BclassBaseClassAbstract.dicintObjectCount.get(strTypeThisFullNameAndMutability) + 1);
                /*BclassBaseClassAbstract.dicintObjectCount.get(strTypeThisFullNameAndMutability) =
                    BclassBaseClassAbstract.dicintObjectCount.get(strTypeThisFullNameAndMutability) + 1;*/
            }
    }

    //-----------------------------------------------------------------------------------------------------------------
                                                      		//MÉTODOS DE CONSULTA.

    //-----------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //-----------------------------------------------------------------------------------------------------------------
    protected void subSetIsResetOff(                        //Indica que no esta reseteado.
                                                            //Este métodos se debe ejecutar cada vez que se calcula una
                                                            //      Variable Calculada.
                                                            //this[M], modifica reset.
        )
    {
                                                            //Indica que no esta reseteado.
        this.boolIsReset = false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void subAddUsedIn(                           	//Añade una referencia UsedIn.
                                                      		//this[M], Añade referencia UsedIn.

                                                      		//Objeto que usa this.
                                                      		//Ejemplo, Journal Entry que usa Currency (se pasa un
                                                      		//      Journal Entryy el this que recibe este método es un
                                                      		//      Currency.
        BclassBaseClassAbstract bclassToAdd_T
        )
    {
                                                            //Solo los objetos MUTABLE pueden tener UsedIn
        if (
                                                            //Este objeto (this) no es MUTABLE
            this.lstbclassThisIsUsedIn == null
            )
            Tools.subAbort(Tes2.strTo(this, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(this.bclassmutability(), "this.bclassmutability") + " DO NOT have lstbclassThisIsUsedIn");

                                                            //Los objetos INMUTABLE no requieren ser registrados en
                                                            //      UsedIn
        if (
            bclassToAdd_T.bclassmutability() == BclassmutabilityEnum.INMUTABLE
            )
            Tools.subAbort(Tes2.strTo(bclassToAdd_T, TestoptionEnum.SHORT) + ", " +
                Tes2.strTo(bclassToAdd_T.bclassmutability(), "bclassToAdd_T.bclassmutability") +
                " SHOULD NOT be registed in lstbclassThisIsUsedIn");

        this.lstbclassThisIsUsedIn.add(bclassToAdd_T);
        BclassBaseClassAbstract.intUsedInAddTotalCount = BclassBaseClassAbstract.intUsedInAddTotalCount + 1;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void subRemoveUsedIn(                        	//Remueve una referencia UsedIn.
                                                      		//this[M], Remueve referencia UsedIn.

                                                      		//Objeto que usaba this y que será removido.
                                                      		//Ejemplo, Journal Entry que usa Currency (se pasa un
                                                      		//      Journal Entryy el this que recibe este método es un
                                                      		//      Currency.
        BclassBaseClassAbstract bclassToRemove_T
        )
    {
                                                            //Solo los objetos MUTABLE pueden tener UsedIn
        if (
                                                            //Este objeto (this) no es MUTABLE
            this.lstbclassThisIsUsedIn != null
            )
        {
                                                            //Localiza el objeto y lo elimina.
            int intPos = this.lstbclassThisIsUsedIn.indexOf(bclassToRemove_T);
            if (
                intPos < 0
                )
                Tools.subAbort(Tes2.strTo(bclassToRemove_T.getClass(), "bclassToRemove_T.GetType") +
                        " IS NOT in lstbclassThisIsUsedIn");

            this.lstbclassThisIsUsedIn.remove(intPos);
            BclassBaseClassAbstract.intUsedInRemoveTotalCount = BclassBaseClassAbstract.intUsedInRemoveTotalCount +
                1;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    protected void subVerifyObjectConstructionIsFinished(
                                                            //Si el objeto aún no ha concluído su construcción se
                                                            //      aborta.
                                                            //this[I], consulta boolObjectConstructionFinished.
        )
    {
        if (
                                                            //El objeto aún no esta listo
            !this.boolIsObjectConstructionFinished
            )
            Tools.subAbort(Tes2.strTo(boolIsObjectConstructionFinished, "boolIsObjectConstructionFinished") +
                " object construction IS NOT FINISHED, its functionality can not be used yet");
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subWriteSummary(                 	//Escribe en el log de pruebas la información de la
                                                      		//      aplicación que se encuentra en la parte estática de
                                                      		//      esta clase.

                                                      		//Log en el cual se escribe la información.
    	PrintWriter syssrLog_M
        )
    {
        syssrLog_M.println("");
        syssrLog_M.println("#################### Bclass SUMMARY ##########");
        syssrLog_M.println(
            Tes2.strTo(BclassBaseClassAbstract.dicintObjectCount, "BclassBaseClassAbstract.dicintObjectCount"));

                                                            //Cantidad total de objetos construidos
        int intObjectsCount = 0;
        for (Entry<String, Integer> kvpint : BclassBaseClassAbstract.dicintObjectCount.entrySet())
        {
            intObjectsCount = intObjectsCount + kvpint.getValue();
        }

        syssrLog_M.println(Tes2.strTo(intObjectsCount, "intObjectsCount") + ", " +
            Tes2.strTo(BclassBaseClassAbstract.intUsedInAddTotalCount,
                "BclassBaseClassAbstract.intUsedInAddTotalCount") +
            ", " +
            Tes2.strTo(BclassBaseClassAbstract.intUsedInRemoveTotalCount,
                "BclassBaseClassAbstract.intUsedInRemoveTotalCount"));

                                                            //Estima memoria utilizada en apuntadores UdedIn, asumimos
                                                            //      que cada apuntador ocupa 8 bytes.
        int intMemoryUsedMB =
            (BclassBaseClassAbstract.intUsedInAddTotalCount - BclassBaseClassAbstract.intUsedInRemoveTotalCount) *
                8 / (1024 * 1024);

        syssrLog_M.println(Tes2.strTo(intMemoryUsedMB ,"intMemoryUsedMB"));
    }
}

//=====================================================================================================================
/*END-TASK*/