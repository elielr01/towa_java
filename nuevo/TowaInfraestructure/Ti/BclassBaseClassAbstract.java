/*TASK Bclass Base Class*/
package Ti;


//                                                          //AUTHOR: Towa (GLG-Gerardo López).
//                                                          //CO-AUTHOR: Towa ().
//                                                          //DATE: June 24, 2015.
//                                                          //PURPOSE:
//                                                          //Base for all classes.

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

//=====================================================================================================================
public abstract /*MUTABLE*/ class BclassBaseClassAbstract
//                                                          //Clase base para todos los objetos, conforme al estandar
//                                                          //      Towa, TODOS los objetos que diseñemos deben heredar
//                                                          //      de esta clase.
//                                                          //Entre otras cosas, esta clase provee facilidades para
//                                                          //      evaluar el desempeño de una aplicación desarrollada
//                                                          //      conforme a estos estándares.
//                                                          //(algo ya esta aquí, sin embargo en el futuro se puede
//                                                          //      añadir más capacidades, ojo se debe SER CAUTELOSO
//                                                          //      dado que todo esto afectará la eficiencia).
{
    //-----------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //                                                       //Define si el objeto es INMUTABLE, MUTABLE o OPEN.
    //                                                       //Solo los MUTABLE recolectan información en UsedIn.
    //                                                       //Nótese que un objeto es MUTABLE si al menos UNA de sus
    //                                                       //      varibles es MUTABLE, esta variable puede estar en
    //                                                       //      la clase concreta o en cualquiera de las clases
    //                                                       //      abstractas que le dan forma (de esto se excluye
    //                                                       //      Bclass que es un caso especial).
    protected abstract BclassmutabilityEnum bclassmutability();

	/*STATIC VARIABLES*/

    //                                                      //Diccionario para registrar la cantidad de todos los
    //                                                      //      objetos que contruye la aplicación al estar
    //                                                      //      operando.
    //                                                      //Llave: Type, será el Name de la clase concreta del
    //                                                      //      objeto.
    //                                                      //Info: Cantidad de objetos que se han creado durante la
    //                                                      //      operación de la aplicación.
    //                                                      //No se contabilizan los objetos DUMMY.
    private static LinkedHashMap<String, Integer> dicintObjectCount;

    //                                                      //Total de UsedIn en TODOS los objetos de la aplicación.
    private static int intUsedInAddTotalCount;
    private static int intUsedInRemoveTotalCount;

    //                                                      //Conjunto de Type de objectos DUMMY.
    //                                                      //Por ESTÁNDARD solo se permite construír un objeto DUMMY
    //                                                      //      (objDUMMY_UNIQUE) para cada clase concreta.
    //                                                      //Esta lista permite asegurar que se cumpla esté estándar,
    //                                                      //      se abortara si no se cumple.
    private static LinkedList<Class> lsttypeDummyUnique;

    static
    //                                                      //Inicializa información estática.
    {
        //                                                  //Inicializa el diccionario para la cantidad de objetos que
        //                                                  //      construye la aplicación.
        BclassBaseClassAbstract.dicintObjectCount = new LinkedHashMap<String, Integer>();

        BclassBaseClassAbstract.intUsedInAddTotalCount = 0;
        BclassBaseClassAbstract.intUsedInRemoveTotalCount = 0;

        //                                                  //Inicializa el lista de DUMMY_UNIQUE.
        BclassBaseClassAbstract.lsttypeDummyUnique = new LinkedList<Class>();
    }

    //-----------------------------------------------------------------------------------------------------------------
    /*INSTANCE VARIABLES*/

    //                                                      //Indica si este objeto es DUMMY
    private boolean boolIsDummy_Z;
    public boolean boolIsDummy() { return this.boolIsDummy_Z; }

    //                                                      //Indica si el objeto ha sido reseteado.
    //                                                      //Saber esto es necesario para no propagar el reset con la
    //                                                      //      lista UsedIn lo cual podría causar un ciclo.
    //                                                      //Esto funciona de la siguiente forma:
    //                                                      //a. Al iniciar la contrucción del objeto se establece este
    //                                                      //      valor en true. El objeto nace reseteado.
    //                                                      //b. Cada vez que se ejecuta subResetObject(), esto se hará:
    //                                                      //b1. Al concluir la construcción del objeto concreto.
    //                                                      //b2. Al iniciar un método de transformación.
    //                                                      //b3. Cuando se modifique un objeto.
    //                                                      //c. Al calcular cualquier variable calculada se debe
    //                                                      //      ejecutar el método subSetIsResetOff() el cual pondrá
    //                                                      //      este valor en false, lo cual indica que al menos una
    //                                                      //      variable calculada ya tiene valor.
    private /*MUTABLE*/ boolean boolIsReset;

    //                                                      //Indica si la construcción del objecto ya fue concluída.
    //                                                      //Saber esto es conveniente para poder proteger el código
    //                                                      //      de manera que se diagnostique (ABORTE) si antes de
    //                                                      //      que este construído completamente el objeto se
    //                                                      //      pretende hacer referencia a:
    //                                                      //1. Alguna variable calculada.
    //                                                      //2. Algún método de transformación.
    //                                                      //3. Algún método de consulta.
    //                                                      //Esto funciona de la siguiente forma:
    //                                                      //a. Al iniciar la contrucción del objeto (en el constructor
    //                                                      //      de esta clase que es la más abstracta de todos los
    //                                                      //      objetos) se establece este valor en false.
    //                                                      //b. Al concluír la construcción del objeto, al hacer el
    //                                                      //      subResetObject() se establece este valor en true.
    //                                                      //c. Nótese que cada vez que se haga el subResetObject() se
    //                                                      //      vuelve a establecer en true, para efectos de lo que
    //                                                      //      se busca esto no es necesario, sin embargo no
    //                                                      //      afecta.
    //                                                      //d. Al iniciar el proceso de: Variable Calculada, Método de
    //                                                      //      Transformación y Método Acceso se ejecuta el método
    //                                                      //      subVerifyObjectConstructionFinished() el cuál
    //                                                      //      abortará si aún no esta concluída la construcción
    //                                                      //      del objeto.
    private /*MUTABLE*/ boolean boolIsObjectConstructionFinished;

    //                                                      //Registra objetos (concretos) que "usan" la información de
    //                                                      //      "este" objeto.
    //                                                      //Esto es necesario, dado que si este objeto es modificado,
    //                                                      //      por lo cual requiere ser reseteado, el reseteo debe
    //                                                      //      propagarse a todos los objetos que "usan" "este
    //                                                      //      objeto.
    //                                                      //Ejemplo, un objeto Journal Entry esta en USD y hace
    //                                                      //      referencia a un objeto Currency para tomar de ahí
    //                                                      //      los tipos de cambio, este objeto Journal Entry debe
    //                                                      //      añadirse a la lista de "used in" del objeto currency
    //                                                      //      para que al cambiar algo en currency le pueda avisar
    //                                                      //      a Journal Entry que cambio.
    //                                                      //Nótese que el añadir una referencia de "uso" NO SIGNIFICA
    //                                                      //      que este objeto fue modificado (no se resetea).
    //                                                      //Solo los objetos MUTABLE recolectan esta información, en
    //                                                      //      los otros objetos, este valor debe ser null.
    //                                                      //Solo los objetos MUTABLE pueden ser contenidos en esta
    //                                                      //      lista.
    private /*MUTABLE*/ LinkedList<BclassBaseClassAbstract> lstbclassThisIsUsedIn;

    //-----------------------------------------------------------------------------------------------------------------
    /*COMPUTED VARIABLES*/

    //-----------------------------------------------------------------------------------------------------------------

    protected abstract void subResetOneClass(
        //                                                  //ESTE MÉTODO SE DEBE INCLUIR EN TODAS LAS CLASES.
        //                                                  //Este método resetea solo las variables calculadas para una
        //                                                  //      clase, esto es:
        //                                                  //1. Inicia con la clase concreta, el método
        //                                                  //      subResetObject(), al final ejecuta el método
        //                                                  //      this.subResetOneClass().
        //                                                  //2. Al concluir la ejecución de cada uno de lo métodos
        //                                                  //      subResetOneClass() ejecuta el método
        //                                                  //      base.subResetOneClass() para resetear las variables
        //                                                  //      calculadas que están en la clase inmediata
        //                                                  //      abstracta, hasta llegar a la clase que hereda de
        //                                                  //      bclassBaseClass en dónde el método
        //                                                  //      subResetOneClass() ya no vuelve a resetear.
        );

    //-----------------------------------------------------------------------------------------------------------------
    public String strTo(
        //                                                  //SHORT display.
        //                                                  //THIS METHOD SHOULD BE IMPLEMENTED IN EVERY CLASS (ABSTRACT
        //                                                  //      OR CONCRETE).
        //                                                  //The final format of the string will be:
        //                                                  //ObjId[BclassVariables, AbstractVariables, ...,
        //                                                  //      AbstractVariables, ConcreteVariables].
        //                                                  //To produce this string:
        //                                                  //1. Concrete class produces:
        //                                                  //ObjId[base.testoption(S) + Variable + ... + Variable].
        //                                                  //2. All abstract classes (except Bclass) produce:
        //                                                  //base.testoption(S) + Variable + ... + Variable.
        //                                                  //3. Bclass produces:
        //                                                  //Variable + ... + Variable, (see below).
        //                                                  //4. Variable is:.
        //                                                  //4a. Tes3.strTo(Variable, TestoptionEnum.SHORT).
        //                                                  //4b. When variable is lstobj, queueobj or stackobj you need
        //                                                  //      to call strTo with 3 parameters, this method is an
        //                                                  //      example (see support methods below).
        //                                                  //4c. When variable is dirobj you need to call strTo with 4
        //                                                  //      parameters (see example in class
        //                                                  //      SemsolooObjectOriented).
        //                                                  //4d. When variable is vkpobj you need to call strTo with 4
        //                                                  //      parameters (no example included, should be similar
        //                                                  //      to 4c but simpler).
        //                                                  //4e. obj is class, tuple, enum or Exception (other object
        //                                                  //      should use 2 paramenter methods.
        //                                                  //(see examples).
        //                                                  //this[I], all its instance variables.

        //                                                  //str, display information

        //                                                  //SHORT option (other options will be ignored).
        StrtoEnum testoptionSHORT
        )
    {
        //                                                  //En la versión corta se decidió no agragar nada.
        return "*";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String strTo (
        //                                                  //THIS METHOD SHOULD BE IMPLEMENTED IN EVERY CLASS (ABSTRACT
        //                                                  //      OR CONCRETE).
        //                                                  //The final format of the string will be:
        //                                                  //ObjId{Variables}==>Class{Variables}==>...==>
        //                                                  //      Class{Variables}==>Bclass{Variables}.
        //                                                  //To produce this string:
        //                                                  //1. Concrete class produces:
        //                                                  //ObjId{Variable + ... + Variable}==>base.strto().
        //                                                  //2. All abstract classes (except Bclass) produce:
        //                                                  //ClassPrefix{Variable + ... + Variable}==>base.strTo().
        //                                                  //3. Bclass produces:
        //                                                  //Bclass{Variable + ... + Variable}.
        //                                                  //4. Variable is:.
        //                                                  //4a. Test.strTo(Variable, "Variable").
        //                                                  //4b-e (see method description above).
        //                                                  //this[I], all its instance variables.

        //                                                  //str, display information
    )
    {
        final String strCLASS = "Bclass";

        //                                                  //Will report only prefix of the objects in
        //                                                  //      lstbclassThisIsUsedIn (can be null)

        return strCLASS + "{" + Tes2.strTo(this.arrstrPrefix(), StrtoEnum.SHORT, this.lstbclassThisIsUsedIn) +
                "}";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private BclassBaseClassAbstract[] arrbclassThisIsUsedInLstTo(
        //                                                  //The name of this method should be:
        //                                                  //lstobjXxxxx ==> arrobjXxxxxLstTo().
        //                                                  //queueobjXxxxx ==> arrobjXxxxxQueueTo().
        //                                                  //stackobjXxxxx ==> arrobjXxxxxStackTo().
        //                                                  //For dic convertion, the name of the methods should be:
        //                                                  //dicobjXxxxx ==> arrobjXxxxxDicTo() &
        //                                                  //      arrstrKeyXxxxxDicTo().
        //                                                  //
        //                                                  //lst, queue and stack of bclass, btuple, enum or Exception
        //                                                  //      need to be converted to arrobj before calling strTo
        //                                                  //      method with 3 paramenters.
        //                                                  //This method is an example and should be coded after strTo
        //                                                  //      methods.
        //                                                  //
        //                                                  //To call this method:
        //                                                  //(see examples above).
        //                                                  //If lst, ... is static, paramenter or local variable, you
        //                                                  //      need an static method and pass lst,... as
        //                                                  //      paramenter.
        //                                                  //arrbclass, lstbclass converted

        //                                                  //this[I], lstbclassThisIsUsedIn
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
    private String[] arrstrPrefix(
        //                                                  //Sometimes content is convertes to str values.
        //                                                  //
        //                                                  //To call this method:
        //                                                  //(see examples above).
        //                                                  //arrbclass, lstbclass converted

        //                                                  //this[I], lstbclassThisIsUsedIn
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
                String strObjId = Tes3.strGetObjId(this.lstbclassThisIsUsedIn.get(intI));

                //                                          //ObjId has the form Prefix:HashCode
                arrstrPrefix[intI] = strObjId.substring(0, strObjId.lastIndexOf(':'));
            }
        }

        return arrstrPrefix;
    }

    //-----------------------------------------------------------------------------------------------------------------
    /*OBJECT CONSTRUCTORS*/

    //-----------------------------------------------------------------------------------------------------------------
    protected BclassBaseClassAbstract(                     	//Inicializa la parte más abstracta de cada objeto, y.
        //                                                  //Acumula a la parte estática la creación de un objeto de
        //                                                  //      cierto type.
        //                                                  //this.*[O], Asigna lstbclass vacía.

        //                                                  //true es DUMMY, false tiene info.
        boolean boolIsDummy_I
        )
    {
        //                                                  //INSTANCE PART.

        //                                                  //Un objeto "nace" reseteado.
        this.boolIsReset = true;

        //                                                  //This is THE ONLY value asigned to a DUMMY object
        this.boolIsDummy_Z = boolIsDummy_I;

        if (
            //                                              //Estamos en un objeto DUMMY
            boolIsDummy_I
            )
        {
            //                                              //STATIC PART (ONE SET OF INFORMATION FOR THE APPLICATION).
            if (
                //                                          //Ya se tiene un DUMMY de este tipo.
                BclassBaseClassAbstract.lsttypeDummyUnique.contains(this.getClass())
                )
                Tes3.subAbort(Tes3.strTo(this, "this") + ", " + Tes3.strTo(this.getClass(), "this.GetType()") +
                    ", " +
                    Tes3.strTo(BclassBaseClassAbstract.lsttypeDummyUnique.toArray(),
                        "BclassBaseClassAbstract.lsttypeDummyUnique.ToArray()") +
                        " a DUMMY object already exists");

            //                                              //Registra objeto DUMMY en la lista de DUMMYs
            BclassBaseClassAbstract.lsttypeDummyUnique.add(this.getClass());

            //                                              //El objeto DUMMY no se contabiliza en el diccionario.
        }

        //                                                  //Indica que AÚN NO ESTA CONCLUÍDA la construcción del
        //                                                  //      objeto.
        //                                                  //Al concluir la construcción, en la clase concreta se
        //                                                  //      ejecuta subSetObjectConstructionFinished() que
        //                                                  //      cambiara esto a true.
        //                                                  //La asignación de false al principio ES NECESARIA para
        //                                                  //      evitar que la funcionalidad del método se utilizada
        //                                                  //      ANTES de concluir la construcción.
        this.boolIsObjectConstructionFinished = false;


        //                                                  //Inicializa lista de UsedIn
        if (
            this.bclassmutability() == BclassmutabilityEnum.MUTABLE
            )
        {
            this.lstbclassThisIsUsedIn = new LinkedList<BclassBaseClassAbstract>();
        }
        else
        {
            //                                              //Solo los objetos MUTABLE recolectan esta información.
            this.lstbclassThisIsUsedIn = null;
        }

        //                                                  //STATIC PART (ONE SET OF INFORMATION FOR THE APPLICATION).

        //                                                  //Solo contabiliza los objeto NO DUMMY's
        if (
            //                                              //Es un objeto con información (NO DUMMY)
            !this.boolIsDummy()
            )
        {
            String strTypeThisFullNameAndMutability = this.getClass().getSimpleName() + "|" + this.bclassmutability();

            //                                              //Create dictionary entry if needed.
            if (
                 BclassBaseClassAbstract.dicintObjectCount.containsKey(strTypeThisFullNameAndMutability)
                )
            {
                //                                          //Do nothing
            }
            else
            {
                BclassBaseClassAbstract.dicintObjectCount.put(strTypeThisFullNameAndMutability, 0);
            }

            //                                              //Add count
            BclassBaseClassAbstract.dicintObjectCount.put(strTypeThisFullNameAndMutability,
                BclassBaseClassAbstract.dicintObjectCount.get(strTypeThisFullNameAndMutability) + 1);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*TRANSFORMATION METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public void subSetObjectConstructionFinish(
        //                                                  //Este método indica que el objeto ya esta completamente
        //                                                  //      construído.
        //                                                  //Este método deberá ser ejecutado al concluir la
        //                                                  //      construcción de un objeto en su clase concreta.
        )
    {

        //                                                  //Indica que YA ESTA CONCLUÍDA la construcción del objeto.
        //                                                  //A partir de esto ya será posible accesar la funcionalidad
        //                                                  //      de este objeto.
        this.boolIsObjectConstructionFinished = true;

        //                                                  //LOG TEMPORAL PARA ENTENDER LA SECUENCIA DE RESETEO.
        //Test.subLog("    <<<CONSTRUCCIÓN>>> " + this.strTo(TestoptionEnum.SHORT));
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void subResetObject(
        //                                                  //Este método inicia el proceso de resetaer todas las
        //                                                  //      variables calculadas de un objeto:
        //                                                  //1. Indica que el objeto esta reseteado, en realidad apenas
        //                                                  //      esta iniciando pero lo hará ejecutando al final de
        //                                                  //      este método this.subResetOneClass().
        //                                                  //2. Se debe ejecutar al principio de cada método de
        //                                                  //      transformación, esto es necesario para:
        //                                                  //2a. Si en el proceso algo detona algún reset que se
        //                                                  //      a este objeto, no se vuelve a resetear (lo cual
        //                                                  //      causaría un ciclo.
        //                                                  //2b. El método de transformación NO USA variables
        //                                                  //      calculadas.
        //                                                  //2c. Al concluir el método de transformación se debe
        //                                                  //      ejecutar subVerifyIsReset(), aborta si algo sucedió.
        //                                                  //3. Propaga el reseteo a todos los objetos que usan este
        //                                                  //      objeto.
        //                                                  //Este método deberá ser ejecutado al inicio de un método de
        //                                                  //      transformación.
        )
    {
        //                                                  //LOG TEMPORAL PARA ENTENDER LA SECUENCIA DE RESETEO.
        /*Test.subLog("    <<<RESET UP>>> " + Test.strTo(this.boolIsReset, "Reset") + ", " +
            this.strTo(TestoptionEnum.SHORT));*/

        if (
            this.boolIsReset
            )
        {
            //                                              //YA ESTA RESETEANO, NO HACE NADA
        }
        else
        {
            //                                              //Indica que el objeto esta reseteado reseteado. En realidad
            //                                              //      apenas se esta iniciando el reseteo pero se hará al
            //                                              //      final de este método.
            this.boolIsReset = true;

            if (
                //                                          //Tiene "used in", es MUTABLE.
                this.lstbclassThisIsUsedIn != null
                )
            {
                //                                          //LOG TEMPORAL PARA ENTENDER LA SECUENCIA DE RESETEO.
                //Test.subLog("    <<<USES_IN>>>");

                //                                          //Propaga el reseteo a los objetos que usan este objeto.
                for (BclassBaseClassAbstract bclassThisObjectIsUsedIn : this.lstbclassThisIsUsedIn)
                {
                    //                                      //En su ejecución, si ya está reseteado no se hace nada.
                    bclassThisObjectIsUsedIn.subResetObject();
                }
            }

            //                                              //Ahora si, INICIA el reseteo de este objeto.
            //                                              //INICIA por la clase concreta.
            this.subResetOneClass();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void subSetIsResetOff(
        //                                                  //Indica que no esta reseteado.
        //                                                  //Este métodos se debe ejecutar cada vez que se calcula una
        //                                                  //      Variable Calculada.
        //                                                  //this[M], modifica reset.
        )
    {
        //                                                  //Indica que no esta reseteado.
        this.boolIsReset = false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void subAddUsedIn(
        //                                                  //Añade una referencia UsedIn.
        //                                                  //this[M], Añade referencia UsedIn.

        //                                                  //Objeto MUTABLE que usa this.
        //                                                  //Ejemplo, Journal Entry que usa Currency (se pasa un
        //                                                  //      Journal Entryy el this que recibe este método es un
        //                                                  //      Currency.
        BclassBaseClassAbstract bclassToAdd_T
        )
    {
        //                                                  //Solo los objetos MUTABLE pueden tener UsedIn
        if (
            //                                              //Este objeto (this) no es MUTABLE
            this.bclassmutability() != BclassmutabilityEnum.MUTABLE
            )
            Tes3.subAbort(Tes3.strTo(this, StrtoEnum.SHORT) + ", " +
                Tes3.strTo(this.bclassmutability(), "this.bclassmutability") +
                " DO NOT have lstbclassThisIsUsedIn, DO NOT call subAddUsedIn()");

        //                                                  //Solo los objetos MUTABLE pueden ser incluídos en esta
        //                                                  //      lista.
        if (
            //                                              //El objeto (bclassToAdd_T) no es MUTABLE
            bclassToAdd_T.bclassmutability() != BclassmutabilityEnum.MUTABLE
            )
            Tes3.subAbort(Tes3.strTo(bclassToAdd_T, StrtoEnum.SHORT) + ", " +
                Tes3.strTo(bclassToAdd_T.bclassmutability(), "bclassToAdd_T.bclassmutability") +
                " CAN NOT be included in lstbclassThisIsUsedIn, DO NOT call subAddUsedIn()");

        this.lstbclassThisIsUsedIn.add(bclassToAdd_T);
            BclassBaseClassAbstract.intUsedInAddTotalCount = BclassBaseClassAbstract.intUsedInAddTotalCount + 1;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void subRemoveUsedIn(
        //                                                  //Remueve una referencia UsedIn.
        //                                                  //this[M], Remueve referencia UsedIn.

        //                                                  //Objeto que usaba this y que será removido.
        //                                                  //Ejemplo, Journal Entry que usa Currency (se pasa un
        //                                                  //      Journal Entryy el this que recibe este método es un
        //                                                  //      Currency.
        BclassBaseClassAbstract bclassToRemove_T
        )
    {
        //                                                  //Solo los objetos MUTABLE pueden tener UsedIn
        if (
            //                                              //Este objeto (this) no es MUTABLE
            this.bclassmutability() != BclassmutabilityEnum.MUTABLE
            )
            Tes3.subAbort(Tes3.strTo(this, StrtoEnum.SHORT) + ", " +
                Tes3.strTo(this.bclassmutability(), "this.bclassmutability") +
                " DO NOT have lstbclassThisIsUsedIn, DO NOT call subRemoveUsedIn()");

        //                                                  //Localiza el objeto y lo elimina.
        int intPos = this.lstbclassThisIsUsedIn.indexOf(bclassToRemove_T);
        if (
            intPos < 0
            )
            Tes3.subAbort(Tes3.strTo(bclassToRemove_T.getClass(), "bclassToRemove_T.getClass()") +
                " IS NOT in lstbclassThisIsUsedIn");

        this.lstbclassThisIsUsedIn.remove(intPos);
        BclassBaseClassAbstract.intUsedInRemoveTotalCount = BclassBaseClassAbstract.intUsedInRemoveTotalCount +
            1;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*ACCESS METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    protected void subVerifyObjectConstructionIsFinished(
        //                                                  //Si el objeto aún no ha concluído su construcción se
        //                                                  //      aborta.
        //                                                  //this[I], consulta boolObjectConstructionFinished.
        )
    {
        if (
            this.boolIsDummy()
            )
            Tes3.subAbort(Tes3.strTo(this.getClass(), "this.getClass()") + ", " +
                Tes3.strTo(boolIsDummy(), "boolIsDummy") + " can not be DUMMY for this méthod");
        if (
            //                                              //El objeto aún no esta listo
            !this.boolIsObjectConstructionFinished
            )
            Tes3.subAbort(Tes3.strTo(this.getClass(), "this.getClass()") + ", " +
                Tes3.strTo(this.boolIsObjectConstructionFinished, "this.boolIsObjectConstructionFinished") +
                " object construction IS NOT FINISHED, its functionality can not be used yet");
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void subVerifyIsReset(
        //                                                  //Un método de tranforamción NO DEBE USAR variables
        //                                                  //      calculadas, si se usaron se cancelo el reset.
        //                                                  //Si sucede lo anterior ES INCORRECTO y debe abortar..
        //                                                  //this[I], base object info.
        )
    {
        if (
            this.boolIsDummy()
            )
            Tes3.subAbort(Tes3.strTo(this.getClass(), "this.getClass") + ", " +
                Tes3.strTo(boolIsDummy(), "boolIsDummy") + " can not be DUMMY for this méthod");
        if (
            //                                              //El objeto marca como usaras las variables calculadas
            !this.boolIsReset
            )
            Tes3.subAbort(Tes3.strTo(this.getClass(), "this.getClass") + ", " +
                Tes3.strTo(this.boolIsReset, "this.boolIsReset") +
                " object IS NOT RESET, transformation methods should not use compued variables");
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subWriteSummary(
        //                                                  //Escribe en el log de pruebas la información de la
        //                                                  //      aplicación que se encuentra en la parte estática de
        //                                                  //      esta clase.
        )
    {
        Tes3.subLog("");
        Tes3.subLog("#################### Bclass SUMMARY ##########");
        Tes3.subLog(
            Tes3.strTo(BclassBaseClassAbstract.dicintObjectCount, "BclassBaseClassAbstract.dicintObjectCount"));

        //                                                  //Cantidad total de objetos construidos
        int intObjectsCount = 0;
        for (Map.Entry<String, Integer> kvpint : BclassBaseClassAbstract.dicintObjectCount.entrySet())
        {
            intObjectsCount = intObjectsCount + kvpint.getValue();
        }

        Tes3.subLog(Tes3.strTo(intObjectsCount, "intObjectsCount") + ", " +
            Tes3.strTo(BclassBaseClassAbstract.intUsedInAddTotalCount,
                "BclassBaseClassAbstract.intUsedInAddTotalCount") +
            ", " +
            Tes3.strTo(BclassBaseClassAbstract.intUsedInRemoveTotalCount,
                "BclassBaseClassAbstract.intUsedInRemoveTotalCount"));

        //                                                      //Estima memoria utilizada en apuntadores UdedIn, asumimos
        //                                                      //      que cada apuntador ocupa 8 bytes.
        int intMemoryUsedMB =
            (BclassBaseClassAbstract.intUsedInAddTotalCount - BclassBaseClassAbstract.intUsedInRemoveTotalCount) *
                8 / (1024 * 1024);

        Tes3.subLog(Tes3.strTo(intMemoryUsedMB, "intMemoryUsedMB"));
    }
}

//=====================================================================================================================
/*END-TASK*/
