/*TASK Tools Miscellaneous support for all application*/
package TowaInfrastructure;

import java.lang.System;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import javax.swing.JOptionPane;

															//AUTHOR: Towa (GLG-Gerardo López).
															//CO-AUTHOR: Towa ().
															//DATE: 13-Mayo-2011.
															//PURPOSE:
															//Shared methods for all applications.
public final class Tools 
{
	//------------------------------------------------------------------------------------------------------------------
    static                                        			//Prepara las constantes para poder utilizarlas.
                                                      		//CADA VEZ QUE SE AÑADAN CONSTANTES QUE REQUIERAN SER
                                                      		//      INICIALIZADAS, SE AÑADE LA LLAMADA A OTRO MÉTODO.
        
    {
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    private Tools() {}										//No permite que se cree instancia de la clase.

    //==================================================================================================================
    /*TASK Tools.MinOrMax methods to compute min o max value*/
    //--------------------------------------------------------------------------------------------------------------
                                                            //En .net existen las funciones Math.Min() y Math.Max(), sin
                                                            //      embargo estas aceptan solo 2 valores.
                                                            //Adicionalmente se desea tener strMin(), enumMin(),
                                                            //      benumMin() y btupleMin().

    //------------------------------------------------------------------------------------------------------------------
    public static Integer intMin(
                                                            //Determina el valor mínimo.

                                                            //int, MIN value.

            Object... arguments
            )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Integer intMin = (Integer) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if (
                    (Integer)arguments[intI] < intMin
                    )
            {
                intMin = (Integer)arguments[intI];
            }
        }

        return intMin;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Long longMin(
            //                                              //Determina el valor mínimo.

            //                                              //long, MIN value.

            Object... arguments
            )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Long longMin = (Long) arguments[0];

        //for (Object o : arguments)
        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if (
                    (Long)arguments[intI] < longMin
                    )
            {
                longMin = (Long)arguments[intI];
            }
        }

        return longMin;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Double numMin(
            //                                              //Determina el valor mínimo.

            //                                              //num, MIN value.

            Object... arguments
            )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Double numMin = (Double) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if (
                    (Double)arguments[intI] < numMin
                    )
            {
                numMin = (Double)arguments[intI];
            }
        }

        return numMin;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Character charMin(
            //                                              //Determina el valor mínimo.

            //                                              //char, MIN value.

            Object... arguments
            )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Character charMin = (Character) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if (
                    (Character)arguments[intI] < charMin
                    )
            {
                charMin = (Character)arguments[intI];
            }
        }

        return charMin;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static String strMin(
            //                                              //Determina el valor mínimo.
            //                                              //Nótese que 4,512 valores tienen .CompareTo() == 0 con
            //                                              //      "\x0000" que es uno de los strLOW_VALUE.
            //                                              //strLOW_VALUE es "\x0000".
            //                                              //Muchos valores puede ser iguales en .CompareTo(), se
            //                                              //      reconocera como MIN el primero que se encuentre.

            //                                              //str, MIN value ("A" es antes que "A ").

            Object... arguments
            )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        String strMin = (String) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if (
                    (((String)arguments[intI]).compareTo(strMin) < 0)
                    )
            {
                strMin = (String)arguments[intI];
            }
        }

        return strMin;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Enum enumMin(
            //                                              //Determina el valor mínimo.

            //                                              //enum, MIN value.

            Object... arguments
    )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Enum enumMin = (Enum) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if (
                    (((Enum)arguments[intI]).compareTo(enumMin) < 0)
                    )
            {
                enumMin = (Enum)arguments[intI];
            }
        }

        return enumMin;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static Integer intMax(
                                                            //Determina el valor máximo.

                                                            //int, MAX value.

            Object... arguments
    )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Integer intMax = (Integer) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if (
                    (Integer)arguments[intI] > intMax
                    )
            {
                intMax = (Integer)arguments[intI];
            }
        }

        return intMax;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static long longMax(
            //                                              //Determina el valor máximo.

            //                                              //long, MAX value.

            Object... arguments
    )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Long longMax = (Long) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if ((Long) arguments[intI] > longMax)
            {
                longMax = (Long) arguments[intI];
            }
        }

        return longMax;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Double numMax(
            //                                              //Determina el valor máximo.

            //                                              //num, MAX value.

            Object... arguments
    )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Double numMax = (Double) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if ((Double) arguments[intI] > numMax)
            {
                numMax = (Double) arguments[intI];
            }
        }

        return numMax;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Character charMax(
            //                                              //Determina el valor máximo.

            //                                              //char, MAX value.

            Object... arguments
    )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Character charMax = (Character) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if ((Character) arguments[intI] > charMax)
            {
                charMax = (Character) arguments[intI];
            }
        }

        return charMax;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static String strMax(
            //                                              //Determina el valor máximo.
            //                                              //Nótese que determinará el máximo conforme a la secuencia
            //                                              //      del mismo orden que se genera en Array.Sort(arrstr),
            //                                              //      en este orden "\xFFFF" no es el valor máximo.
            //                                              //strHIGH_VALUE es "\x3034".
            //                                              //Nótese que 15 valores tienen .CompareTo() == 0 con
            //                                              //      "\xFF70" que es uno de los strHIGH_VALUE.
            //                                              //strHIGH_VALUE es "\xFF70".
            //                                              //Muchos valores puede ser iguales en .CompareTo(), se
            //                                              //      reconocera como MAX el primero que se encuentre.

            //                                              //str, MAX value. ("A " es despues que "A").

            Object... arguments
    )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        String strMax = (String) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if ((((String) arguments[intI]).compareTo(strMax) > 0))
            {
                strMax = (String) arguments[intI];
            }
        }

        return strMax;
    }

    //--------------------------------------------------------------------------------------------------------------
    public static Enum enumMax(
            //                                              //Determina el valor máximo.

            //                                              //enum, MAX value.

            Object... arguments
    )
    {
        if (
                arguments.length == 0
                )
            Tools.subAbort(Tes2.strTo(arguments.length, "arguments") +
                    " should have at least 1 item");

        Enum enumMax = (Enum) arguments[0];

        for (Integer intI = 1; intI < arguments.length; intI = intI + 1)
        {
            if ((((Enum) arguments[intI]).compareTo(enumMax) > 0))
            {
                enumMax = (Enum) arguments[intI];
            }
        }

        return enumMax;
    }

    //==================================================================================================================
    /*TASK Tools.subAbort subAbort and subWarnint*/
    //------------------------------------------------------------------------------------------------------------------
    public static void subAbort(                        	//Aborta ejecucion al detectar situación anormal. Puede ser
                                                      		//      WinForms app o Console app.

                                                      		//Mensaje descriptivo del aborto.
        String strMessage_I
        )
    {
//        String strMethodCallS = Tools.strStackOnlyMethodCalls(Environment.StackTrace);
        String strMethodCallS = "Tools.strStackOnlyMethodCalls(Environment.StackTrace)";

        String strFullMessage = "<<<ABNORMAL END>>>" + "\n" + "MESSAGE:" + "\n" + strMessage_I + "\n" +
        		"METHOD CALLS:" + "\n" + strMethodCallS;

        if (
            false
//            Application.MessageLoop
            )
        {
                                                      		//Aborto en WinForms app.
        	JOptionPane.showMessageDialog(null, strFullMessage);
        }
        else
        {
                                                      		//Aborto en Console app.
        	System.out.println(strFullMessage);

            System.out.println("");
            System.out.println("ENTER KEY TO END");
            Scanner scanner = new Scanner(System.in);
            String strReadLine = scanner.nextLine();
        }

                                                      		//Existen 2 posibilidades para continuar o terminar
        if (
            Tes2.boolIsTestAbortOn
            )
        {
//            throw new SysexcepuserUserAbort(strFullMessage);
        }
        else
        {
            System.exit(0);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subWarning(                      	//Ejecucion al detectar situación anormal.
                                                      		//NO ABORTA PARA PERMITIR UN DIAGNÓSTICO MAS COMPLETO, SIN
                                                      		//      EMBARGO PUEDE TENER COMPORTAMIENTO IMPREDECIBLE.
                                                      		//Puede ser WinForms app o Console app.

                                                      		//Mensaje descriptivo del aborto.
        String strMessage_I
        )
    {
//        String strMethodCallS = Tools.strStackOnlyMethodCalls(Environment.StackTrace);
        String strMethodCallS = "Tools.strStackOnlyMethodCalls(Environment.StackTrace)";

        String strFullMessage = "<<<SHOULD ABORT EXECUTION AFTER ENDING DIAGNOSTIC>>>" + "\n" + 
            "MESSAGE:" + "\n" + strMessage_I + "\n" + "METHOD CALLS:" + 
            "\n" + strMethodCallS;
        if (
            false
//            Application.MessageLoop
            )
        {
                                                      		//Aborto en WinForms app.
            JOptionPane.showMessageDialog(null, strFullMessage);
        }
        else
        {
                                                      		//Aborto en Console app.
            System.out.println(strFullMessage);

            System.out.println("");
            System.out.println("ENTER KEY TO CONTINUE");
            Scanner scanner = new Scanner(System.in);
            String strReadLine = scanner.nextLine();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strStackOnlyMethodCalls(       	//ESTE MÉTODO DEBE SER REESCRITO PARA CADA TECNOLOGÍA O
                                                      		//      INSTANCIA DE LA TECNOLOGÍA.
        
                                                      		//EN C# al igual que en otras tecnologías el stack
                                                      		//      contendrá mucha información.
                                                      		//Se extrae solo la parte necesaria para desplegar:
                                                      		//[
                                                      		//namespace/package.class.method(parameters):line nnn
                                                      		//namespace/package.class.method(parameters):line nnn
                                                      		//...
                                                      		//namespace/package.class.method(parameters):line nnn
                                                      		//]
                                                      		//Lo que se requiere es la secuencia de métodos desde el
                                                      		//      inicio, hasta antes de la ejecución del subAbort.
                                                      		//nótese que para desplegar la información como se muestra,
                                                      		//      en el String será necesario incluir los caracteres
                                                      		//      de "\n" entre cada una de estas líneas.
                                                      		//Ejemplo:
                                                      		//[
                                                      		//TowaInfrastructure.Sys.subCopyFileRewrite(...):line 741
                                                      		//QEnablerBase.TestSys.subSys01TestC():line 341
                                                      		//QEnablerBase.frmPrueba.btnPrueba_Click(...):line 25
                                                      		//]
                                                      		//Stack (como lo proporciona la tecnología)
        String strStack_I
        )
    {
                                                      		//La estrategia será:
                                                      		//1. Localizar "TowaInfrastructure.", esta será la línea en
                                                      		//      subAborta donde se obtiene la información de Stack
                                                      		//      (esto no se require).
                                                      		//2. Localiza el siguiente "\n", esto será la
                                                      		//      información donde se ejecuta subAborta, esto es
                                                      		//      donde sucedió la causa del aborto, a partir de aquí
                                                      		//      no interesa (después del "\n").
                                                      		//3. Localiza "System.", nos interesa hasta antes de esta
                                                      		//      línea (se tiene "\n" + "___at_" + "System.".
                                                      		//4. Convierte a arrstr de líneas para poder analizarlas.
                                                      		//5. De cada línea elimina el principio que es "___at_" y lo 
                                                      		//      que esta después del ) hasta antes de :line.
                                                      		//6. Vuelve a formar el String (con los "\n" entre líneas).
                                                      		//7. Chars '&' should be removed.

                                                      		//Localiza a partir de donde interesa.
        int intStart = strStack_I.indexOf("TowaInfrastructure.");
        intStart = strStack_I.indexOf("\n", intStart);
        intStart = intStart + "\n".length();

                                                      		//Localiza a partir de donde ya no interesa.
        int intEndPlusOne = strStack_I.indexOf("System.", intStart);
        intEndPlusOne = intEndPlusOne - ("\n" + "___at_").length();
                                                      		//Toma la parte que nos interesa (esto aún tiene información
                                                      		//      de los archivos que se require eliminar).
        String strStackOnlyMethodCalls = strStack_I.substring(intStart, intEndPlusOne - intStart);

        String[] arrstrLineOnlyMethodCall =
            strStackOnlyMethodCalls.split("\n");

        for (int intI = 0; intI < arrstrLineOnlyMethodCall.length; intI = intI + 1)
        {
                                                      		//To facilitate coding.
            String strLine = arrstrLineOnlyMethodCall[intI];

                                                      		//Find ')', end of method call y luego :
            int intParenthesis = strLine.indexOf(')');

                                                      		//Find ':', start of ":line nn", some tows of informatión do
                                                      		//      contain this ":line nn"
            int intColon = strLine.indexOf(':');

                                                      		//Extract required information.
            String strLineRevised = strLine.substring("___at_".length(), intParenthesis + 1 - "___at_".length());
            strLineRevised = strLineRevised.replace("&", "");

            if (
                intColon >= 0
                )
            {
                strLineRevised = strLineRevised + strLine.substring(intColon);
            }

            arrstrLineOnlyMethodCall[intI] = strLineRevised;
        }

                                                      		//Vuelve a formato String.
        strStackOnlyMethodCalls = String.join("\n", arrstrLineOnlyMethodCall);

        return strStackOnlyMethodCalls;
    }
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tools.boolIsDigit methods to test digits and letters*/
    //--------------------------------------------------------------------------------------------------------------
                                                      		//En .net existen las funciones Char.IsDigits,
                                                      		//      Char.IsLetter y Char.IsLetterOrDigit, estas
                                                      		//      reconocen como válidos los dígitos y letras en
                                                      		//      TODOS los lenguajes implementados en .net.
                                                      		//310 dígitos y 47,672 letras.

                                                      		//Aquí se implementas funciones para reconocer dígito (solo
                                                      		//      0-9), letras (solo A-Z y a-z).

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsDigit(                     	//Valida.

                                                      		//bool, true si es 0-9.

                                                      		//Caracter a validar.
        char charAValidar_I
        )
    {
        return (
            (charAValidar_I >= '0') && (charAValidar_I <= '9')
            );
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsLetter(                    	//Valida.

                                                   		    //bool, true si es A-Z o a-z.

                                                   		    //Caracter a validar.
        char charAValidar_I
        )
    {
        return (
            (charAValidar_I >= 'A') && (charAValidar_I <= 'Z') ||
            (charAValidar_I >= 'a') && (charAValidar_I <= 'z')
            );
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsDigitOrLetter(             	//Valida.

                                                   		    //bool, true si es 0-9, A-Z o a-z.

                                                   		    //Caracter a validar.
        char charAValidar_I
        )
    {
        return (
            (charAValidar_I >= '0') && (charAValidar_I <= '9') ||
            (charAValidar_I >= 'A') && (charAValidar_I <= 'Z') ||
            (charAValidar_I >= 'a') && (charAValidar_I <= 'z')
            );
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsLetterUpper(               	//Valida.

                                                   		    //bool, true si es A-Z.

                                                   		    //Caracter a validar.
        char charAValidar_I
        )
    {
        return (
            (charAValidar_I >= 'A') && (charAValidar_I <= 'Z')
            );
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsLetterLower(               	//Valida.

                                                   		    //bool, true si es a-z.

                                                   		    //Caracter a validar.
        char charAValidar_I
        )
    {
        return (
            (charAValidar_I >= 'a') && (charAValidar_I <= 'z')
            );
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsDigitOrLetterUpper(        	//Valida.

                                                   		    //bool, true si es 0-9 o A-Z.

                                                   		    //Caracter a validar.
        char charAValidar_I
        )
    {
        return (
            (charAValidar_I >= '0') && (charAValidar_I <= '9') ||
            (charAValidar_I >= 'A') && (charAValidar_I <= 'Z')
            );
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsDigitOrLetterLower(        	//Valida.

                                                   		    //bool, true si es 0-9 o a-z.

                                                   		    //Caracter a validar.
        char charAValidar_I
        )
    {
        return (
            (charAValidar_I >= '0') && (charAValidar_I <= '9') ||
            (charAValidar_I >= 'a') && (charAValidar_I <= 'z')
            );
    }
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tools.boolIsDigit methods to test digits and letters*/
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    public static /*readonly*/ Calendar dateMIN_VALUE = new GregorianCalendar(0001, 01, 01);
    public static /*readonly*/ Calendar dateMAX_VALUE = new GregorianCalendar(9999, 12, 31);
    public static /*readonly*/ Calendar timeMIN_VALUE = new GregorianCalendar(0001, 01, 01, 0, 0, 0);
    public static /*readonly*/ Calendar timeMAX_VALUE = new GregorianCalendar(9999, 12, 31, 23, 59, 59);

                                                      		//En .net existen las funciones DateTime.MinValue y
                                                      		//      DateTime.MaxValue que nos sirven para el 
                                                            //       timestamp.

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsDate(                      	//Valida sea un fecha.

                                                   		    //bool, true si es fecha.

                                                   		    //Fecha a validar.
        Calendar date_I
        )
    {
        return (
                                                      		//Con excepción de la fecha todo esta en ceros.
            (date_I.get(Calendar.HOUR) == 0) && (date_I.get(Calendar.MINUTE) == 0) &&
            	(date_I.get(Calendar.SECOND) == 0) && (date_I.get(Calendar.MILLISECOND)== 0)
            );
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsTime(                      	//Valida sea una time.

                                                   		    //bool, true si es time.

                                                   		  	//Time a validar.
        Calendar time_I
        )
    {
        return (
                                                      		//Con excepción de la fecha y hora todo esta en ceros.
            time_I.get(Calendar.MILLISECOND) == 0
            );
    }
    /*END-TASK*/
   
    //*************************************CHECAR*************************************************
    
    public static String readToEnd(                         //Reads the rest of the contents of a file and returns them
                                                            //		in a string.

                                                            //File that will be read.
        Scanner sysfileFile
    	) 
    {
		
		String strRest = "";
		while (
				sysfileFile.hasNextLine()
				)
		{
            String strNextLine = sysfileFile.nextLine();
			strRest = strRest + strNextLine;
//			strRest = strRest + "\n";

            if (sysfileFile.hasNext() || sysfileFile.hasNextLine())
            {
                strRest = strRest + "\n";
            }

//            String nextLine = sysfileFile.nextLine();
//            strRest = strRest + nextLine;
//
//            if (sysfileFile.hasNextLine() ||
//                (!sysfileFile.hasNextLine()) && !nextLine.equals(""))
//            {
//                strRest = strRest + "\n";
//            }
		}
			
		return strRest;
	}
    /*END-TASK*/
    
    //==================================================================================================================
    /*TASK Tools.strPadRight and Tools.strPadLeft Pads a string*/
    //------------------------------------------------------------------------------------------------------------------
	public static String strPadLeft(String strToPad, int intLong, char charRight)
	{
		for(int intI = strToPad.length(); intI < intLong; intI = intI + 1)
		{
			strToPad = charRight + strToPad;
		}
		
		return strToPad;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public static String strPadLeft(String strToPad, int intLong)
	{
		for(int intI = strToPad.length(); intI < intLong; intI = intI + 1)
		{
			strToPad = ' ' + strToPad;
		}
		
		return strToPad;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public static String strPadRight(String strToPad, int intLong, char charRight)
	{
		for(int intI = strToPad.length(); intI < intLong; intI = intI + 1)
		{
			strToPad = strToPad + charRight;
		}
		
		return strToPad;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public static String strPadRight(String strToPad, int intLong)
	{
		for(int intI = strToPad.length(); intI < intLong; intI = intI + 1)
		{
			strToPad = strToPad + ' ';
		}
			
		return strToPad;
	}
    /*END-TASK*/
    
    //==================================================================================================================
    /*TASK Tools.trimStart and Tools.trimEnd Trim beginning and end of a string*/
    //------------------------------------------------------------------------------------------------------------------
    public static String trimStart(                         //Removes all leading occurrences of a set of characters
                                                            //      from a string. Uses varargs for the set of
                                                            //      characters, meaning that it can receive from 0 to N
                                                            //      different chars. Method may be called like:
                                                            //      trimStart("aabbcc", 'a'), which will return "bbcc",
                                                            //      or may be called like:
                                                            //      trimStart("aabbcc", 'a', 'b'), which will return
                                                            //      "cc". If the method is called with no chars, it will
                                                            //      return the same string that was sent as input (it
                                                            //      would have no sense to call the method with no
                                                            //      chars).

                                                            //The String to trim.
        String strToTrim_I,
                                                            //The set of leading chars to be trimmed.
        char... arrchartToTrim_I
    )
    {
        if (
                                                            //No chars were received.
            arrchartToTrim_I.length == 0
            )
        {
                                                            //DO NOTHING
        }
        else {
            while (
                (strToTrim_I.length() > 0) &&
                (boolStartsWithCharacters(strToTrim_I, arrchartToTrim_I))
                )
            {
                strToTrim_I = strToTrim_I.substring(1);
            }
        }
        return strToTrim_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String trimEnd(                           //Removes all trailing occurrences of a set of characters
                                                            //      from a string. Uses varargs for the set of
                                                            //      characters, meaning that it can receive from 0 to N
                                                            //      different chars. Method may be called like:
                                                            //      trimStart("aabbcc", 'a'), which will return "bbcc",
                                                            //      or may be called like:
                                                            //      trimStart("aabbcc", 'a', 'b'), which will return
                                                            //      "cc". If the method is called with no chars, it will
                                                            //      return the same string that was sent as input (it
                                                            //      would have no sense to call the method with no
                                                            //      chars).

                                                            //The string to trim.
        String strToTrim,
                                                            //The set of trailing chars to be trimmed.
        char... arrchartToTrim
    )
    {
        int intI = strToTrim.length() - 1;
        while (
            (intI >= 0) &&
            boolEndsWithCharacters(strToTrim, arrchartToTrim)
            )
        {
            strToTrim = strToTrim.substring(0, intI);

            intI = intI - 1;
        }

        return strToTrim;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolStartsWithCharacters(         //Analyze if a string starts with a set of characters. This
                                                            //      method uses varargs for the set of characters,
                                                            //      meaning that it can receive from 1 to N chars. For
                                                            //      example, this method can be called like:
                                                            //      boolStartsWithCharacters("myString", 'a'), but also
                                                            //      may be called like:
                                                            //      boolStartsWithCharacters("myString", 'a', 'b', 'c').
                                                            //Technically, the method may even be called without a
                                                            //      second parameter, but it would have no sense in this
                                                            //      method.

                                                            //The string to analyze.
        String str_I,
                                                            //varargs. The set of characters to which the beginning of
                                                            //      the word will be compared with.
        char... arrchar_I
    )
    {
        boolean boolStartsWithCharacters;

        if (
                                                            //Empty word
            str_I.length() == 0
            )
        {
            boolStartsWithCharacters = false;
        }
        else
        {
            int intI = 0;
            /*LOOP*/
            while (true)
            {
            /*EXIT-IF*/
                if (
                    (intI >= arrchar_I.length) ||
                        str_I.charAt(0) == arrchar_I[intI]
                    )
                    break;

                intI = intI + 1;
            }
            boolStartsWithCharacters = intI < arrchar_I.length;
        }


        return boolStartsWithCharacters;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolEndsWithCharacters(           //Analyze if a string starts with a set of characters. This
                                                            //      method uses varargs for the set of characters,
                                                            //      meaning that it can receive from 1 to N chars. For
                                                            //      example, this method can be called like:
                                                            //      boolStartsWithCharacters("myString", 'a'), but also
                                                            //      may be called like:
                                                            //      boolStartsWithCharacters("myString", 'a', 'b', 'c').
                                                            //Technically, the method may even be called without a
                                                            //      second parameter, but it would have no sense in this
                                                            //      method.

                                                            //The string to analyze.
        String str_I,
                                                            //varargs. The set of characters to which the beginning of
                                                            //      the word will be compared with.
        char... arrchar_I
    )
    {
        boolean boolEndsWithCharacters;

        if (
                                                            //Empty word
            str_I.length() == 0
            )
        {
            boolEndsWithCharacters = false;
        }
        else
        {
            int intI = 0;
            /*LOOP*/
            while (true)
            {
            /*EXIT-IF*/
                if (
                    (intI >= arrchar_I.length) ||
                        str_I.charAt(str_I.length() - 1) == arrchar_I[intI]
                    )
                    break;

                intI = intI + 1;
            }

            boolEndsWithCharacters = intI < arrchar_I.length;
        }

        return boolEndsWithCharacters;
    }
	/*END-TASK*/

    //==============================================================================================================
        /*TASK Tools.strCenter Center text*/
    //--------------------------------------------------------------------------------------------------------------
    public static String strCenter(
                                                            //Centra el texto y lo edita.

                                                            //str, texto centrado conforme a los parámetros, si excede
                                                            //      la longitud deseada se trunca y se deja como último
                                                            //      caracter '…'.

                                                            //Texto que debe ser alineado.
            String strText_I,
                                                            //Longitud del texto nuevo que se debe producir.
            int intLong_I,
                                                            //Caracter para relleno a la izquierda.
            char charLeft_I,
                                                            //Caracter para relleno a la derecha.
            char charRight_I
    )
    {
                                                            //Para formar el nuevo String.
        String strCenter = strText_I;

                                                            //Si excede el tamaño deseado lo trunca del lado derecho.
        if (
                                                            //Excede el tamaño
                strText_I.length() > intLong_I
                )
        {
                                                            //Corta la parte excedente.
            strCenter = strCenter.substring(0, intLong_I - 1) + '…';
        }

                                                            //Calcula la cantidad de caracteres de inicio y fin.
        int intFiller = intLong_I - strCenter.length();

                                                            //Si el valor en impar lo redondea hacia abajo.
        int intLeft = intFiller / 2;
        int intRigth = intFiller - intLeft;

                                                            //Genera el texto con los inicio y fin y el texto alineado.
                                                            //Nótese que es indistinto usar PadLeft o PadRight
        strCenter = strPadLeft("", intLeft, charLeft_I) + strCenter + strPadLeft("", intRigth, charRight_I);

        return strCenter;
    }

    //==================================================================================================================
    /*TASK Tools.strTrimExcel Trimming text like excel*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTrimExcel(                  	//Hace un Trim similar al lo que hace Excel, esto es,
                                                      		//      elimina los espacios al principio y al final y solo
                                                      		//      deja un espacio entre las palabras que contenga, una
                                                      		//      palabra es un conjunto de caracteres contiguos
                                                      		//      diferentes a espacio.

                                                      		//str, ya sin espacios en exceso (Trim EXCEL).

                                                      		//String para hacer el Trim EXCEL
        String str_I
        )
    {
                                                      		//Se cicla para buscar el inicio de la primera palabra, sale
                                                      		//      cuando
        int intIni = 0;
        /*UNTIL-DO*/
        while (!(
                                                      		//Llego al fin del String
            (intIni >= str_I.length()) ||
                                                      		//Encuentra caracter diferente de espacio
            (str_I.charAt(intIni) != ' ')
            ))
        {
            intIni = intIni + 1;
        }

        String strTrimExcel = "";

                                                      		//Se cicla para procesar cada palabra
        /*LOOP*/
        while (true)
        {
                                                      		//Extrae la siguiente palabra del String
            String strWord;
            Oint ointIni = new Oint(intIni);
            Ostring ostrWord = new Ostring();
            Tools.subWord(str_I, ointIni, ostrWord);
            intIni = ointIni.v;
            strWord = ostrWord.v;

                                                      		//Concatena la palabra
            strTrimExcel = strTrimExcel + strWord;

            /*EXIT-IF*/
            if (
                                                      		//sale cuando llega al fin del String
                intIni >= str_I.length()
                )
                break;

            strTrimExcel = strTrimExcel + " ";
        }
        /*END-LOOP*/

        return strTrimExcel;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    private static void subWord(                        	//Procesa una palabra del String.

                                                      		//String que contiene las palabras.
        String str_I,
                                                      		//Posición donde inicia la palabra que se procesará, regresa
                                                      		//      la posición del inicio de la siguiente palabra, o la
                                                      		//      posición inmediata al fin del String.
        Oint ointI_IO,
                                                      		//Palabra procesada.
        Ostring ostrWord_O
        )
    {
                                                      		//Se cicla buscando el fin de la palabra, sale cuando;
        int intFin = ointI_IO.v;
        /*UNTIL-DO*/
        while (!(
                                                      		//Llega al fin del String
            (intFin >= str_I.length()) ||
                                                      		//Encontró un espacio (fin de palabra)
            (str_I.charAt(intFin) == ' ')
            ))
        {
            intFin = intFin + 1;
        }

        ostrWord_O.v = str_I.substring(ointI_IO.v, intFin);

                                                      		//Se cicla buscando el inicio de la siguiente palabra, hasta
        ointI_IO.v = intFin;
        /*UNTIL-DO*/
        while (!(
                                                      		//Llega al fin del String
            (ointI_IO.v >= str_I.length()) ||
                                                      		//Encontró el inicio de la siguiente palabra
            (str_I.charAt(ointI_IO.v) != ' ')
            ))
        {
            ointI_IO.v = ointI_IO.v + 1;
        }
    }
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tools.intSearchWordInString Search word in a string*/
    //------------------------------------------------------------------------------------------------------------------
    public static int intSearchWordInString(            	//Busca una "palabra" en un String, una "palabra" es un 
                                                      		//      conjunto de caracteres (diferentes de espacios) 
                                                      		//      DELIMITED por el inicio o fin del String o por uno
                                                      		//      o varios espacios. Ej. en el String "__ABC___ZYZ" se
                                                      		//      tiene las palabras "ABC" y XYZ" (ojo en el String se
                                                      		//      uso _ como sustituto de espacio).

                                                      		//int, posición (base 0) donde encuentra la palabra, -1 si
                                                      		//      no encontró.

                                                      		//Palabra que se buscará. (Deben ser caracteres continuos 
                                                      		//      sin espacios).
        String strWord_I,
                                                      		//String sobre el cual se buscará la palabra.
        String str_I
        )
    {
                                                      		//Inicializa resultado a NO ENCONTRO
        int intSearchPalabraEnString = -1;

        int intIni = 0;

        /*LOOP*/
        while (true)
        {
                                                      		//Se cicla para buscar el incial de las siguiente palabra en
                                                      		//      String, termina cuando:
            /*UNTIL-DO*/
            while (!(
                                                      		//Llego al fin del String.
                (intIni >= str_I.length()) ||
                                                      		//Encuentra el inicio de una palabra
                (str_I.charAt(intIni) != ' ')
                ))
            {
                intIni = intIni + 1;
            }

            /*EXIT-IF*/
            if (
                                                      		//En el ciclo anterior encontro la palabra
                (intSearchPalabraEnString >= 0) ||
                                                      		//LLego al fin del String.
                (intIni >= str_I.length())
                )
                break;

                                                      		//Se cicla para buscar el fin de la palabra que inicia en
                                                      		//      intIni.
            int intFin = intIni;
            /*UNTIL-DO*/
            while (!(
                                                      		//Llego al fin del String
                (intFin >= str_I.length()) ||
                                                      		//Encontró el fin de la palabra
                (str_I.charAt(intFin) == ' ')
                ))
            {
                intFin = intFin + 1;
            }

            if (
                str_I.substring(intIni, intFin - intIni) == strWord_I
                )
            {
                                                      		//Pasa la posición de la palabra 
                intSearchPalabraEnString = intIni;
            }

            intIni = intFin + 1;
        }
        /*END-LOOP*/

        return intSearchPalabraEnString;
    }
    /*END-TASK*/
    
    //==================================================================================================================
    /*TASK Primitive and Object Arrays*/
    //------------------------------------------------------------------------------------------------------------------
    public static Oint[] convertToBoxingArray (				//Converts primitive array to object array.
    		
    														//Primitive array to convert.
    	int[] arrint_I
    	)
    {	
    	Oint[] arrointReturn = new Oint[arrint_I.length];
    	for (int intI = 0; intI < arrint_I.length; intI = intI + 1)
    	{
    		arrointReturn[intI] = new Oint(arrint_I[intI]);
    	}
    	
    	return arrointReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Obool[] convertToBoxingArray (			//Converts primitive array to object array.
    		
    														//Primitive array to convert.
    	boolean[] arrbool_I
    	)
    {	
    	Obool[] arroboolReturn = new Obool[arrbool_I.length];
    	for (int intI = 0; intI < arrbool_I.length; intI = intI + 1)
    	{
    		arroboolReturn[intI] = new Obool(arrbool_I[intI]);
    	}
    	
    	return arroboolReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Ochar[] convertToBoxingArray (			//Converts primitive array to object array.
    		
    														//Primitive array to convert.
    	char[] arrchar_I
    	)
    {	
    	Ochar[] arrocharReturn = new Ochar[arrchar_I.length];
    	for (int intI = 0; intI < arrchar_I.length; intI = intI + 1)
    	{
    		arrocharReturn[intI] = new Ochar(arrchar_I[intI]);
    	}
    	
    	return arrocharReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Olong[] convertToBoxingArray (			//Converts primitive array to object array.
    		
    														//Primitive array to convert.
    	long[] arrlong_I
    	)
    {
    	Olong[] arrolongReturn = new Olong[arrlong_I.length];
    	for (int intI = 0; intI < arrlong_I.length; intI = intI + 1)
    	{
    		arrolongReturn[intI] = new Olong(arrlong_I[intI]);
    	}
    	
    	return arrolongReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Onum[] convertToBoxingArray (				//Converts primitive array to object array.
    		
    														//Primitive array to convert.
    	double[] arrnum_I
    	)
    {
    	Onum[] arronumReturn = new Onum[arrnum_I.length];
    	for (int intI = 0; intI < arrnum_I.length; intI = intI + 1)
    	{
    		arronumReturn[intI] = new Onum(arrnum_I[intI]);
    	}
    	
    	return arronumReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Ostring[] convertToBoxingArray (			//Converts primitive array to object array.
    		
    														//Primitive array to convert.
    	String[] arrstr_I
    	)
    {
    	Ostring[] arrostrReturn = new Ostring[arrstr_I.length];
    	for (int intI = 0; intI < arrstr_I.length; intI = intI + 1)
    	{
    		arrostrReturn[intI] = new Ostring(arrstr_I[intI]);
    	}
    	
    	return arrostrReturn;
    }
    
    //------------------------------------------------------------------------------------------------------------------
    public static int[] convertToPrimitiveArray (			//Converts an object array to primitive data type array
    		
    														//Object array to convert
    	Oint[] arroint_I
    	)
    {
    	int[] arrintReturn = new int[arroint_I.length];
    	for (int intI = 0; intI < arroint_I.length; intI = intI + 1)
    	{
    		arrintReturn[intI] = arroint_I[intI].v;
    	}
    	
    	return arrintReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static boolean[] convertToPrimitiveArray (		//Converts an object array to primitive data type array
    		
    														//Object array to convert
    	Obool[] arrobool_I
    	)
    {
    	boolean[] arrboolReturn = new boolean[arrobool_I.length];
    	for (int intI = 0; intI < arrobool_I.length; intI = intI + 1)
    	{
    		arrboolReturn[intI] = arrobool_I[intI].v;
    	}
    	
    	return arrboolReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static char[] convertToPrimitiveArray (			//Converts an object array to primitive data type array
    		
    														//Object array to convert
    	Ochar[] arrochar_I
    	)
    {
    	char[] arrcharReturn = new char[arrochar_I.length];
    	for (int intI = 0; intI < arrochar_I.length; intI = intI + 1)
    	{
    		arrcharReturn[intI] = arrochar_I[intI].v;
    	}
    	
    	return arrcharReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static long[] convertToPrimitiveArray (			//Converts an object array to primitive data type array
    		
    														//Object array to convert
    	Olong[] arrolong_I
    	)
    {
    	long[] arrlongReturn = new long[arrolong_I.length];
    	for (int intI = 0; intI < arrolong_I.length; intI = intI + 1)
    	{
    		arrlongReturn[intI] = arrolong_I[intI].v;
    	}
    	
    	return arrlongReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static double[] convertToPrimitiveArray (		//Converts an object array to primitive data type array
    		
    														//Object array to convert
    	Onum[] arronum_I
   		)
    {
    	double[] arrnumReturn = new double[arronum_I.length];
    	for (int intI = 0; intI < arronum_I.length; intI = intI + 1)
    	{
    		arrnumReturn[intI] = arronum_I[intI].v;
    	}
    	
    	return arrnumReturn;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String[] convertToPrimitiveArray (		//Converts an object array to primitive data type array
    		
    														//Object array to convert
    	Ostring[] arrostr_I
   		)
    {
    	String[] arrstrReturn = new String[arrostr_I.length];
    	for (int intI = 0; intI < arrostr_I.length; intI = intI + 1)
    	{
    		arrstrReturn[intI] = arrostr_I[intI].v;
    	}
    	
    	return arrstrReturn;
    }
    /*END-TASK*/
    
    //==================================================================================================================
    /*TASK Sort*/
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	int[] arrintFirstArray_IO,
    														//Second primitive array
    	int[] arrintSecondArray_IO
   		)
    {
    	if (
    		arrintFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrintSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrintFirstArray_IO.length != arrintSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrintSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrintFirstArray_IO, arrintSecondArray_IO, 0, arrintFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrintFirst_IO, arrintSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrintFirst_IO, arrintSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrintFirst_IO, arrintSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		int[] arrintFirstTemp = new int[intEnd_I - intStart_I + 1];
		int[] arrintSecondTemp = new int[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrintFirst_IO[intFirstHalf] <= arrintFirst_IO[intSecondHalf]
				)
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrintFirstTemp.length
				)
		{
			arrintFirst_IO[intStart_I] = arrintFirstTemp[intI];
			arrintSecond_IO[intStart_I] = arrintSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	int[] arrintFirstArray_IO,
    														//Second primitive array
    	double[] arrnumSecondArray_IO
   		)
    {
    	if (
    		arrintFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrnumSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrintFirstArray_IO.length != arrnumSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrnumSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrintFirstArray_IO, arrnumSecondArray_IO, 0, arrintFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrintFirst_IO, arrnumSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrintFirst_IO, arrnumSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrintFirst_IO, arrnumSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		int[] arrintFirstTemp = new int[intEnd_I - intStart_I + 1];
		double[] arrnumSecondTemp = new double[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrintFirst_IO[intFirstHalf] <= arrintFirst_IO[intSecondHalf]
				)
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrintFirstTemp.length
				)
		{
			arrintFirst_IO[intStart_I] = arrintFirstTemp[intI];
			arrnumSecond_IO[intStart_I] = arrnumSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	int[] arrintFirstArray_IO,
    														//Second primitive array
    	long[] arrilongSecondArray_IO
   		)
    {
    	if (
    		arrintFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrilongSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrintFirstArray_IO.length != arrilongSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrilongSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrintFirstArray_IO, arrilongSecondArray_IO, 0, arrintFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrintFirst_IO, arrilongSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrintFirst_IO, arrilongSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrintFirst_IO, arrilongSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		int[] arrintFirstTemp = new int[intEnd_I - intStart_I + 1];
		long[] arrilongSecondTemp = new long[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrintFirst_IO[intFirstHalf] <= arrintFirst_IO[intSecondHalf]
				)
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrintFirstTemp.length
				)
		{
			arrintFirst_IO[intStart_I] = arrintFirstTemp[intI];
			arrilongSecond_IO[intStart_I] = arrilongSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	int[] arrintFirstArray_IO,
    														//Second primitive array
    	char[] arrcharSecondArray_IO
   		)
    {
    	if (
    		arrintFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrcharSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrintFirstArray_IO.length != arrcharSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrcharSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrintFirstArray_IO, arrcharSecondArray_IO, 0, arrintFirstArray_IO.length - 1);	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrintFirst_IO, arrcharSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrintFirst_IO, arrcharSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrintFirst_IO, arrcharSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		int[] arrintFirstTemp = new int[intEnd_I - intStart_I + 1];
		char[] arrcharSecondTemp = new char[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrintFirst_IO[intFirstHalf] <= arrintFirst_IO[intSecondHalf]
				)
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrintFirstTemp.length
				)
		{
			arrintFirst_IO[intStart_I] = arrintFirstTemp[intI];
			arrcharSecond_IO[intStart_I] = arrcharSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	int[] arrintFirstArray_IO,
    														//Second primitive array
    	boolean[] arrboolSecondArray_IO
   		)
    {
    	if (
    		arrintFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrboolSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrboolSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrintFirstArray_IO.length != arrboolSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrboolSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrintFirstArray_IO, arrboolSecondArray_IO, 0, arrintFirstArray_IO.length - 1);	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrintFirst_IO, arrboolSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrintFirst_IO, arrboolSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrintFirst_IO, arrboolSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	int[] arrintFirst_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		int[] arrintFirstTemp = new int[intEnd_I - intStart_I + 1];
		boolean[] arrboolSecondTemp = new boolean[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrintFirst_IO[intFirstHalf] <= arrintFirst_IO[intSecondHalf]
				)
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intFirstHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirst_IO[intSecondHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrintFirstTemp.length
				)
		{
			arrintFirst_IO[intStart_I] = arrintFirstTemp[intI];
			arrboolSecond_IO[intStart_I] = arrboolSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	double[] arrnumFirstArray_IO,
    														//Second primitive array
    	int[] arrintSecondArray_IO
   		)
    {
    	if (
    		arrnumFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrintSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrnumFirstArray_IO.length != arrintSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrintSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrnumFirstArray_IO, arrintSecondArray_IO, 0, arrnumFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrnumFirst_IO, arrintSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrnumFirst_IO, arrintSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrnumFirst_IO, arrintSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		double[] arrnumFirstTemp = new double[intEnd_I - intStart_I + 1];
		int[] arrintSecondTemp = new int[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrnumFirst_IO[intFirstHalf] <= arrnumFirst_IO[intSecondHalf]
				)
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrnumFirstTemp.length
				)
		{
			arrnumFirst_IO[intStart_I] = arrnumFirstTemp[intI];
			arrintSecond_IO[intStart_I] = arrintSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	double[] arrnumFirstArray_IO,
    														//Second primitive array
    	double[] arrnumSecondArray_IO
   		)
    {
    	if (
    		arrnumFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrnumSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrnumFirstArray_IO.length != arrnumSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrnumSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrnumFirstArray_IO, arrnumSecondArray_IO, 0, arrnumFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrnumFirst_IO, arrnumSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrnumFirst_IO, arrnumSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrnumFirst_IO, arrnumSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		double[] arrnumFirstTemp = new double[intEnd_I - intStart_I + 1];
		double[] arrnumSecondTemp = new double[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrnumFirst_IO[intFirstHalf] <= arrnumFirst_IO[intSecondHalf]
				)
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrnumFirstTemp.length
				)
		{
			arrnumFirst_IO[intStart_I] = arrnumFirstTemp[intI];
			arrnumSecond_IO[intStart_I] = arrnumSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	double[] arrnumFirstArray_IO,
    														//Second primitive array
    	long[] arrilongSecondArray_IO
   		)
    {
    	if (
    		arrnumFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrilongSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrnumFirstArray_IO.length != arrilongSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrilongSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrnumFirstArray_IO, arrilongSecondArray_IO, 0, arrnumFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrnumFirst_IO, arrilongSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrnumFirst_IO, arrilongSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrnumFirst_IO, arrilongSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		double[] arrnumFirstTemp = new double[intEnd_I - intStart_I + 1];
		long[] arrilongSecondTemp = new long[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrnumFirst_IO[intFirstHalf] <= arrnumFirst_IO[intSecondHalf]
				)
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrnumFirstTemp.length
				)
		{
			arrnumFirst_IO[intStart_I] = arrnumFirstTemp[intI];
			arrilongSecond_IO[intStart_I] = arrilongSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	double[] arrnumFirstArray_IO,
    														//Second primitive array
    	char[] arrcharSecondArray_IO
   		)
    {
    	if (
    		arrnumFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrcharSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrnumFirstArray_IO.length != arrcharSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrcharSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrnumFirstArray_IO, arrcharSecondArray_IO, 0, arrnumFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrnumFirst_IO, arrcharSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrnumFirst_IO, arrcharSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrnumFirst_IO, arrcharSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		double[] arrnumFirstTemp = new double[intEnd_I - intStart_I + 1];
		char[] arrcharSecondTemp = new char[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrnumFirst_IO[intFirstHalf] <= arrnumFirst_IO[intSecondHalf]
				)
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrnumFirstTemp.length
				)
		{
			arrnumFirst_IO[intStart_I] = arrnumFirstTemp[intI];
			arrcharSecond_IO[intStart_I] = arrcharSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	double[] arrnumFirstArray_IO,
    														//Second primitive array
    	boolean[] arrboolSecondArray_IO
   		)
    {
    	if (
    		arrnumFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrboolSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrboolSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrnumFirstArray_IO.length != arrboolSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrboolSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrnumFirstArray_IO, arrboolSecondArray_IO, 0, arrnumFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrnumFirst_IO, arrboolSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrnumFirst_IO, arrboolSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrnumFirst_IO, arrboolSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	double[] arrnumFirst_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		double[] arrnumFirstTemp = new double[intEnd_I - intStart_I + 1];
		boolean[] arrboolSecondTemp = new boolean[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrnumFirst_IO[intFirstHalf] <= arrnumFirst_IO[intSecondHalf]
				)
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intFirstHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirst_IO[intSecondHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrnumFirstTemp.length
				)
		{
			arrnumFirst_IO[intStart_I] = arrnumFirstTemp[intI];
			arrboolSecond_IO[intStart_I] = arrboolSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	long[] arrilongFirstArray_IO,
    														//Second primitive array
    	int[] arrintSecondArray_IO
   		)
    {
    	if (
    		arrilongFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrintSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrilongFirstArray_IO.length != arrintSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrintSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrilongFirstArray_IO, arrintSecondArray_IO, 0, arrilongFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrilongFirstArray_IO, arrintSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrilongFirstArray_IO, arrintSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrilongFirstArray_IO, arrintSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		long[] arrilongFirstTemp = new long[intEnd_I - intStart_I + 1];
		int[] arrintSecondTemp = new int[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrilongFirstArray_IO[intFirstHalf] <= arrilongFirstArray_IO[intSecondHalf]
				)
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrilongFirstTemp.length
				)
		{
			arrilongFirstArray_IO[intStart_I] = arrilongFirstTemp[intI];
			arrintSecond_IO[intStart_I] = arrintSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	long[] arrilongFirstArray_IO,
    														//Second primitive array
    	double[] arrnumSecondArray_IO
   		)
    {
    	if (
    		arrilongFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrnumSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrilongFirstArray_IO.length != arrnumSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrnumSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrilongFirstArray_IO, arrnumSecondArray_IO, 0, arrilongFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrilongFirstArray_IO, arrnumSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrilongFirstArray_IO, arrnumSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrilongFirstArray_IO, arrnumSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecondArray_IO,
    														//Index of the start position of a piece in the array
    	int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		long[] arrilongFirstTemp = new long[intEnd_I - intStart_I + 1];
		double[] arrnumSecondTemp = new double[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrilongFirstArray_IO[intFirstHalf] <= arrilongFirstArray_IO[intSecondHalf]
				)
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrilongFirstTemp.length
				)
		{
			arrilongFirstArray_IO[intStart_I] = arrilongFirstTemp[intI];
			arrnumSecondArray_IO[intStart_I] = arrnumSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	long[] arrilongFirstArray_IO,
    														//Second primitive array
    	long[] arrilongSecondArray_IO
   		)
    {
    	if (
    		arrilongFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrilongSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrilongFirstArray_IO.length != arrilongSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrilongSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrilongFirstArray_IO, arrilongSecondArray_IO, 0, arrilongFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrilongFirstArray_IO, arrilongSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrilongFirstArray_IO, arrilongSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrilongFirstArray_IO, arrilongSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		long[] arrilongFirstTemp = new long[intEnd_I - intStart_I + 1];
		long[] arrilongSecondTemp = new long[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrilongFirstArray_IO[intFirstHalf] <= arrilongFirstArray_IO[intSecondHalf]
				)
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrilongFirstTemp.length
				)
		{
			arrilongFirstArray_IO[intStart_I] = arrilongFirstTemp[intI];
			arrilongSecondArray_IO[intStart_I] = arrilongSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	long[] arrilongFirstArray_IO,
    														//Second primitive array
    	char[] arrcharSecondArray_IO
   		)
    {
    	if (
    		arrilongFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrcharSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrilongFirstArray_IO.length != arrcharSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrcharSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrilongFirstArray_IO, arrcharSecondArray_IO, 0, arrilongFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrilongFirstArray_IO, arrcharSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrilongFirstArray_IO, arrcharSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrilongFirstArray_IO, arrcharSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		long[] arrilongFirstTemp = new long[intEnd_I - intStart_I + 1];
		char[] arrcharSecondTemp = new char[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrilongFirstArray_IO[intFirstHalf] <= arrilongFirstArray_IO[intSecondHalf]
				)
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrilongFirstTemp.length
				)
		{
			arrilongFirstArray_IO[intStart_I] = arrilongFirstTemp[intI];
			arrcharSecondArray_IO[intStart_I] = arrcharSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	long[] arrilongFirstArray_IO,
    														//Second primitive array
    	boolean[] arrboolSecondArray_IO
   		)
    {
    	if (
    		arrilongFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrboolSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrboolSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrilongFirstArray_IO.length != arrboolSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrboolSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrilongFirstArray_IO, arrboolSecondArray_IO, 0, arrilongFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
    		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrilongFirstArray_IO, arrboolSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrilongFirstArray_IO, arrboolSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrilongFirstArray_IO, arrboolSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		long[] arrilongFirstTemp = new long[intEnd_I - intStart_I + 1];
		boolean[] arrboolSecondTemp = new boolean[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrilongFirstArray_IO[intFirstHalf] <= arrilongFirstArray_IO[intSecondHalf]
				)
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrilongFirstTemp.length
				)
		{
			arrilongFirstArray_IO[intStart_I] = arrilongFirstTemp[intI];
			arrboolSecondArray_IO[intStart_I] = arrboolSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	char[] arrcharFirstArray_IO,
    														//Second primitive array
    	int[] arrintSecondArray_IO
   		)
    {
    	if (
    		arrcharFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrintSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrcharFirstArray_IO.length != arrintSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrintSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrcharFirstArray_IO, arrintSecondArray_IO, 0, arrcharFirstArray_IO.length - 1);
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcharFirstArray_IO, arrintSecond_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcharFirstArray_IO, arrintSecond_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcharFirstArray_IO, arrintSecond_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecond_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		char[] arrcharFirstTemp = new char[intEnd_I - intStart_I + 1];
		int[] arrintSecondTemp = new int[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcharFirstArray_IO[intFirstHalf] <= arrcharFirstArray_IO[intSecondHalf]
				)
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecond_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcharFirstTemp.length
				)
		{
			arrcharFirstArray_IO[intStart_I] = arrcharFirstTemp[intI];
			arrintSecond_IO[intStart_I] = arrintSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	char[] arrcharFirstArray_IO,
    														//Second primitive array
    	double[] arrnumSecondArray_IO
   		)
    {
    	
    	if (
    		arrcharFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrnumSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrcharFirstArray_IO.length != arrnumSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrnumSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrcharFirstArray_IO, arrnumSecondArray_IO, 0, arrcharFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
    	)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcharFirstArray_IO, arrnumSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcharFirstArray_IO, arrnumSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcharFirstArray_IO, arrnumSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
    	int intMiddle_I, 
    														//Index of the end position of a piece of the array
    	int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		char[] arrcharFirstTemp = new char[intEnd_I - intStart_I + 1];
		double[] arrnumSecondTemp = new double[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcharFirstArray_IO[intFirstHalf] <= arrcharFirstArray_IO[intSecondHalf]
				)
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcharFirstTemp.length
				)
		{
			arrcharFirstArray_IO[intStart_I] = arrcharFirstTemp[intI];
			arrnumSecondArray_IO[intStart_I] = arrnumSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	char[] arrcharFirstArray_IO,
    														//Second primitive array
    	long[] arrilongSecondArray_IO
   		)
    {
    	if (
    		arrcharFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrilongSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrcharFirstArray_IO.length != arrilongSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrilongSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrcharFirstArray_IO, arrilongSecondArray_IO, 0, arrcharFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcharFirstArray_IO, arrilongSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcharFirstArray_IO, arrilongSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcharFirstArray_IO, arrilongSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		char[] arrcharFirstTemp = new char[intEnd_I - intStart_I + 1];
		long[] arrilongSecondTemp = new long[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcharFirstArray_IO[intFirstHalf] <= arrcharFirstArray_IO[intSecondHalf]
				)
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcharFirstTemp.length
				)
		{
			arrcharFirstArray_IO[intStart_I] = arrcharFirstTemp[intI];
			arrilongSecondArray_IO[intStart_I] = arrilongSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	char[] arrcharFirstArray_IO,
    														//Second primitive array
   		char[] arrcharSecondArray_IO
   		)
    {
    	if (
    		arrcharFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrcharSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrcharFirstArray_IO.length != arrcharSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrcharSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrcharFirstArray_IO, arrcharSecondArray_IO, 0, arrcharFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcharFirstArray_IO, arrcharSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcharFirstArray_IO, arrcharSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcharFirstArray_IO, arrcharSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		char[] arrcharFirstTemp = new char[intEnd_I - intStart_I + 1];
		char[] arrcharSecondTemp = new char[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcharFirstArray_IO[intFirstHalf] <= arrcharFirstArray_IO[intSecondHalf]
				)
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcharFirstTemp.length
				)
		{
			arrcharFirstArray_IO[intStart_I] = arrcharFirstTemp[intI];
			arrcharSecondArray_IO[intStart_I] = arrcharSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two primitive arrays based in the first one
    		
    														//Base primitive array
    	char[] arrcharFirstArray_IO,
    														//Second primitive array
    	boolean[] arrboolSecondArray_IO
   		)
    {
    	if (
    		arrcharFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrboolSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrboolSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrcharFirstArray_IO.length != arrboolSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrboolSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrcharFirstArray_IO, arrboolSecondArray_IO, 0, arrcharFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcharFirstArray_IO, arrboolSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcharFirstArray_IO, arrboolSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcharFirstArray_IO, arrboolSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		char[] arrcharFirstTemp = new char[intEnd_I - intStart_I + 1];
		boolean[] arrboolSecondTemp = new boolean[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcharFirstArray_IO[intFirstHalf] <= arrcharFirstArray_IO[intSecondHalf]
				)
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcharFirstTemp.length
				)
		{
			arrcharFirstArray_IO[intStart_I] = arrcharFirstTemp[intI];
			arrboolSecondArray_IO[intStart_I] = arrboolSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	int[] arrintFirstArray_IO,
    														//Second object array
    	Object[] arrobjSecondArray_IO
   		)
    {
    	if (
    		arrintFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrobjSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrintFirstArray_IO.length != arrobjSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrintFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrobjSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrintFirstArray_IO, arrobjSecondArray_IO, 0, arrintFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	int[] arrintFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrintFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrintFirstArray_IO, arrobjSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrintFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	int[] arrintFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		int[] arrintFirstTemp = new int[intEnd_I - intStart_I + 1];
		Object[] arrobjSecondTemp = new Object[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrintFirstArray_IO[intFirstHalf] <= arrintFirstArray_IO[intSecondHalf]
				)
			{
				arrintFirstTemp[intIndexTemp] = arrintFirstArray_IO[intFirstHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrintFirstTemp[intIndexTemp] = arrintFirstArray_IO[intSecondHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirstArray_IO[intFirstHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrintFirstTemp[intIndexTemp] = arrintFirstArray_IO[intSecondHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrintFirstTemp.length
				)
		{
			arrintFirstArray_IO[intStart_I] = arrintFirstTemp[intI];
			arrobjSecondArray_IO[intStart_I] = arrobjSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	double[] arrnumFirstArray_IO,
    														//Second object array
    	Object[] arrobjSecondArray_IO
   		)
    {
    	if (
    		arrnumFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrobjSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrnumFirstArray_IO.length != arrobjSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrnumFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrobjSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrnumFirstArray_IO, arrobjSecondArray_IO, 0, arrnumFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	double[] arrnumFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrnumFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrnumFirstArray_IO, arrobjSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrnumFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	double[] arrnumFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		double[] arrnumFirstTemp = new double[intEnd_I - intStart_I + 1];
		Object[] arrobjSecondTemp = new Object[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrnumFirstArray_IO[intFirstHalf] <= arrnumFirstArray_IO[intSecondHalf]
				)
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirstArray_IO[intFirstHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrnumFirstTemp[intIndexTemp] = arrnumFirstArray_IO[intSecondHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirstArray_IO[intFirstHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrnumFirstTemp[intIndexTemp] = arrnumFirstArray_IO[intSecondHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrnumFirstTemp.length
				)
		{
			arrnumFirstArray_IO[intStart_I] = arrnumFirstTemp[intI];
			arrobjSecondArray_IO[intStart_I] = arrobjSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	long[] arrilongFirstArray_IO,
    														//Second object array
    	Object[] arrobjSecondArray_IO
   		)
    {
    	if (
    		arrilongFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrobjSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrilongFirstArray_IO.length != arrobjSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrilongFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrobjSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrilongFirstArray_IO, arrobjSecondArray_IO, 0, arrilongFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
   		Object[] arrobjSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrilongFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrilongFirstArray_IO, arrobjSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrilongFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	long[] arrilongFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		long[] arrilongFirstTemp = new long[intEnd_I - intStart_I + 1];
		Object[] arrobjSecondTemp = new Object[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrilongFirstArray_IO[intFirstHalf] <= arrilongFirstArray_IO[intSecondHalf]
				)
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intFirstHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrilongFirstTemp[intIndexTemp] = arrilongFirstArray_IO[intSecondHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrilongFirstTemp.length
				)
		{
			arrilongFirstArray_IO[intStart_I] = arrilongFirstTemp[intI];
			arrobjSecondArray_IO[intStart_I] = arrobjSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	char[] arrcharFirstArray_IO,
    														//Second object array
    	Object[] arrobjSecondArray_IO
   		)
    {
    	if (
    		arrcharFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrobjSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrcharFirstArray_IO.length != arrobjSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrcharFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrobjSecondArray_IO.length, "arrintSecondArray_I.length"));
    	
		Tools.mergeSort(arrcharFirstArray_IO, arrobjSecondArray_IO, 0, arrcharFirstArray_IO.length - 1);
    	
    	
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcharFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcharFirstArray_IO, arrobjSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcharFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	char[] arrcharFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		char[] arrcharFirstTemp = new char[intEnd_I - intStart_I + 1];
		Object[] arrobjSecondTemp = new Object[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcharFirstArray_IO[intFirstHalf] <= arrcharFirstArray_IO[intSecondHalf]
				)
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intFirstHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcharFirstTemp[intIndexTemp] = arrcharFirstArray_IO[intSecondHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcharFirstTemp.length
				)
		{
			arrcharFirstArray_IO[intStart_I] = arrcharFirstTemp[intI];
			arrobjSecondArray_IO[intStart_I] = arrobjSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	Object[] arrobjFirstArray_IO,
    														//Second object array
    	Object[] arrobjSecondArray_IO
   		)
    {
    	if (
    		arrobjFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrobjSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrobjFirstArray_IO.length != arrobjSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrobjSecondArray_IO.length, "arrintSecondArray_I.length"));
    	if (
    		Tools.boolHasNullElements(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " contains null elements and " +
    				"can't be sorted");
    	if (
    		!Tools.boolImplementsComparable(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements that do not " +
    				"implement the Comparable interface. Therefore, it can't be sorted");
    	if (
    		!Tools.boolElementsAreSameClass(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements of different " +
    				"classes. Therefore, it can't be sorted.");
    		
		Tools.mergeSort((Comparable[])arrobjFirstArray_IO, arrobjSecondArray_IO, 0, arrobjFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolHasNullElements (			//Checks if there is at least one null element in an object
    														//		array.
    		
    														//Array to be validated
    		Object[] arrobjArray
    		)
    {
    	if (
    		arrobjArray == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjArray, "arrobjArray") + "can't be null");
    	
    	int intI = 0;
    	/*WHILE-DO*/
    	while (
    			(intI < arrobjArray.length) &&
    			(arrobjArray[intI] != null)
    			)
    	{
    		intI = intI + 1;
    	}
    	
    	return intI < arrobjArray.length;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolImplementsComparable (		//Checks if all the elements of the array implements the
    														//		comparable interface used to be sorted.
    		
    														//Array to be validated
    	Object[] arrobjArray
    	)
    {
    	if (
    		arrobjArray == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjArray, "arrobjArray") + "can't be null");
    	
    	int intI = 0;
    	/*WHILE-DO*/
    	while (
    			(intI < arrobjArray.length) &&
    			(arrobjArray[intI] instanceof Comparable<?>)
    			)
    	{
    		intI = intI + 1;
    	}
    	
    	return intI == arrobjArray.length;
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolElementsAreSameClass (		//Checks if all the elements of the array are from the same
    														//		class.
    		
    														//Array to be validated
    	Object[] arrobjArray
    	)
    {
    	if (
    		arrobjArray == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjArray, "arrobjArray") + "can't be null");
    	
    	boolean boolSameClass = true;
    	
    														//If it has more than 2 elements, we need to check if
															//		every element of the array is from the same class
    	if (
    		arrobjArray.length > 1
    		)
    	{
    		Class classFirstElement = arrobjArray[0].getClass();
    		
    		int intI = 1;
    		/*WHILE-DO*/
    		while (
    				(intI < arrobjArray.length) &&
    				(arrobjArray[intI].getClass() == classFirstElement)
    				)
    		{
    			intI = intI + 1;
    		}
    		
    		boolSameClass = (
    				intI == arrobjArray.length
    				);
    		
    	}
    	
    	return boolSameClass;
    }
    
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcompFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcompFirstArray_IO, arrobjSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcompFirstArray_IO, arrobjSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	Object[] arrobjSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		Comparable[] arrcompFirstTemp = new Comparable[intEnd_I - intStart_I + 1];
		Object[] arrobjSecondTemp = new Object[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcompFirstArray_IO[intFirstHalf].compareTo(arrcompFirstArray_IO[intSecondHalf]) <= 0
				)
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
				arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
			arrobjSecondTemp[intIndexTemp] = arrobjSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcompFirstTemp.length
				)
		{
			arrcompFirstArray_IO[intStart_I] = arrcompFirstTemp[intI];
			arrobjSecondArray_IO[intStart_I] = arrobjSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	Object[] arrobjFirstArray_IO,
    														//Second object array
   		int[] arrintSecondArray_IO
   		)
    {
    	if (
    		arrobjFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrintSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrintSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrobjFirstArray_IO.length != arrintSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrintSecondArray_IO.length, "arrintSecondArray_I.length"));
    	if (
    		Tools.boolHasNullElements(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " contains null elements and " +
    				"can't be sorted");
    	if (
    		!Tools.boolImplementsComparable(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements that do not " +
    				"implement the Comparable interface. Therefore, it can't be sorted");
    	if (
    		!Tools.boolElementsAreSameClass(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements of different " +
    				"classes. Therefore, it can't be sorted.");

		Tools.mergeSort((Comparable[])arrobjFirstArray_IO, arrintSecondArray_IO, 0, arrobjFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcompFirstArray_IO, arrintSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcompFirstArray_IO, arrintSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcompFirstArray_IO, arrintSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	int[] arrintSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		Comparable[] arrcompFirstTemp = new Comparable[intEnd_I - intStart_I + 1];
		int[] arrintSecondTemp = new int[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcompFirstArray_IO[intFirstHalf].compareTo(arrcompFirstArray_IO[intSecondHalf]) <= 0
				)
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
				arrintSecondTemp[intIndexTemp] = arrintSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
			arrintSecondTemp[intIndexTemp] = arrintSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcompFirstTemp.length
				)
		{
			arrcompFirstArray_IO[intStart_I] = arrcompFirstTemp[intI];
			arrintSecondArray_IO[intStart_I] = arrintSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	Object[] arrobjFirstArray_IO,
    														//Second object array
    	double[] arrnumSecondArray_IO
   		)
    {
    	if (
    		arrobjFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrnumSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrnumSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrobjFirstArray_IO.length != arrnumSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrnumSecondArray_IO.length, "arrintSecondArray_I.length"));
    	if (
    		Tools.boolHasNullElements(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " contains null elements and " +
    				"can't be sorted");
    	if (
    		!Tools.boolImplementsComparable(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements that do not " +
    				"implement the Comparable interface. Therefore, it can't be sorted");
    	if (
    		!Tools.boolElementsAreSameClass(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements of different " +
    				"classes. Therefore, it can't be sorted.");
    		
		Tools.mergeSort((Comparable[])arrobjFirstArray_IO, arrnumSecondArray_IO, 0, arrobjFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
   		double[] arrnumSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcompFirstArray_IO, arrnumSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcompFirstArray_IO, arrnumSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcompFirstArray_IO, arrnumSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	double[] arrnumSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		Comparable[] arrcompFirstTemp = new Comparable[intEnd_I - intStart_I + 1];
		double[] arrnumSecondTemp = new double[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcompFirstArray_IO[intFirstHalf].compareTo(arrcompFirstArray_IO[intSecondHalf]) <= 0
				)
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
				arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
			arrnumSecondTemp[intIndexTemp] = arrnumSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcompFirstTemp.length
				)
		{
			arrcompFirstArray_IO[intStart_I] = arrcompFirstTemp[intI];
			arrnumSecondArray_IO[intStart_I] = arrnumSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	Object[] arrobjFirstArray_IO,
    														//Second object array
    	long[] arrilongSecondArray_IO
   		)
    {
    	if (
    		arrobjFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrilongSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrilongSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrobjFirstArray_IO.length != arrilongSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrilongSecondArray_IO.length, "arrintSecondArray_I.length"));
    	if (
    		Tools.boolHasNullElements(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " contains null elements and " +
    				"can't be sorted");
    	if (
    		!Tools.boolImplementsComparable(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements that do not " +
    				"implement the Comparable interface. Therefore, it can't be sorted");
    	if (
    		!Tools.boolElementsAreSameClass(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements of different " +
    				"classes. Therefore, it can't be sorted.");
    		
		Tools.mergeSort((Comparable[])arrobjFirstArray_IO, arrilongSecondArray_IO, 0, arrobjFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	long[] arrilongSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcompFirstArray_IO, arrilongSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcompFirstArray_IO, arrilongSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcompFirstArray_IO, arrilongSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
   		long[] arrilongSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
    	)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		Comparable[] arrcompFirstTemp = new Comparable[intEnd_I - intStart_I + 1];
		long[] arrilongSecondTemp = new long[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcompFirstArray_IO[intFirstHalf].compareTo(arrcompFirstArray_IO[intSecondHalf]) <= 0
				)
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
				arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
			arrilongSecondTemp[intIndexTemp] = arrilongSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcompFirstTemp.length
				)
		{
			arrcompFirstArray_IO[intStart_I] = arrcompFirstTemp[intI];
			arrilongSecondArray_IO[intStart_I] = arrilongSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	Object[] arrobjFirstArray_IO,
    														//Second object array
    	char[] arrcharSecondArray_IO
   		)
    {
    	if (
    		arrobjFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrcharSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrcharSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrobjFirstArray_IO.length != arrcharSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrcharSecondArray_IO.length, "arrintSecondArray_I.length"));
    	if (
    		Tools.boolHasNullElements(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " contains null elements and " +
    				"can't be sorted");
    	if (
    		!Tools.boolImplementsComparable(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements that do not " +
    				"implement the Comparable interface. Therefore, it can't be sorted");
    	if (
    		!Tools.boolElementsAreSameClass(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements of different " +
    				"classes. Therefore, it can't be sorted.");
    		
		Tools.mergeSort((Comparable[])arrobjFirstArray_IO, arrcharSecondArray_IO, 0, arrobjFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcompFirstArray_IO, arrcharSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcompFirstArray_IO, arrcharSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcompFirstArray_IO, arrcharSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	char[] arrcharSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		Comparable[] arrcompFirstTemp = new Comparable[intEnd_I - intStart_I + 1];
		char[] arrcharSecondTemp = new char[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcompFirstArray_IO[intFirstHalf].compareTo(arrcompFirstArray_IO[intSecondHalf]) <= 0
				)
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
				arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
			arrcharSecondTemp[intIndexTemp] = arrcharSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcompFirstTemp.length
				)
		{
			arrcompFirstArray_IO[intStart_I] = arrcompFirstTemp[intI];
			arrcharSecondArray_IO[intStart_I] = arrcharSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    
    //------------------------------------------------------------------------------------------------------------------
    public static void sort(								//Sorts two arrays based in the first one. The first array
    														//		contains primitive data and the second contains any
															//		object.
    		
    														//Base primitive array
    	Object[] arrobjFirstArray_IO,
    														//Second object array
    	boolean[] arrboolSecondArray_IO
   		)
    {
    	if (
    		arrobjFirstArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrintFirstArray_IO") + " can't be null");
    	if (
    		arrboolSecondArray_IO == null
    		)
    		Tools.subAbort(Tes2.strTo(arrboolSecondArray_IO, "arrintSecondArray_IO") + " can't be null");
    	if (
    		arrobjFirstArray_IO.length != arrboolSecondArray_IO.length
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO.length, "arrintFirstArray_I.length") + " is not the same" +
    				" as " + Tes2.strTo(arrboolSecondArray_IO.length, "arrintSecondArray_I.length"));
    	if (
    		Tools.boolHasNullElements(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " contains null elements and " +
    				"can't be sorted");
    	if (
    		!Tools.boolImplementsComparable(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements that do not " +
    				"implement the Comparable interface. Therefore, it can't be sorted");
    	if (
    		!Tools.boolElementsAreSameClass(arrobjFirstArray_IO)
    		)
    		Tools.subAbort(Tes2.strTo(arrobjFirstArray_IO, "arrobjFirstArray_IO") + " has elements of different " +
    				"classes. Therefore, it can't be sorted.");
    		
		Tools.mergeSort((Comparable[])arrobjFirstArray_IO, arrboolSecondArray_IO, 0, arrobjFirstArray_IO.length - 1);
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void mergeSort(							//Sorting method used to sort 2 arrays, based on the
    														//		first one. O(nlogn)
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecondArray_IO, 
    														//Index of the start position of a piece of the array
   		int intStart_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    	
		if (
															//Verify if this part of the array can be divided into
															//		left part and right part.
			intStart_I < intEnd_I
			)
		{
															//Gets the middle of the piece of array you are at
			int intMiddle = (intStart_I + intEnd_I) / 2;
			
															//Calls recursively to get the first and second halves of
															//		this piece of array
			Tools.mergeSort(arrcompFirstArray_IO, arrboolSecondArray_IO, intStart_I, intMiddle);
			Tools.mergeSort(arrcompFirstArray_IO, arrboolSecondArray_IO, intMiddle + 1, intEnd_I);
			
															//Calls the merge function that joins and sorts both halves.
			Tools.merge(arrcompFirstArray_IO, arrboolSecondArray_IO, intStart_I, intMiddle, intEnd_I);	
		}
	}
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void merge(								//Function that sorts and merges two pieces of the same
    														//		array
    		
    														//Base array used for sorting
    	Comparable[] arrcompFirstArray_IO, 
    														//Second array sorted according to first array
    	boolean[] arrboolSecondArray_IO,
    														//Index of the start position of a piece in the array
   		int intStart_I,
    														//Index of the middle position of a piece in the array that
															//		indicates the end and beginning of the 2 halves
															//		that contains a piece of the array
   		int intMiddle_I, 
    														//Index of the end position of a piece of the array
   		int intEnd_I
   		)
	{
    														//Creates 2 temporal arrays that are going to have the
    														//		sorted values of this piece of the array.
		Comparable[] arrcompFirstTemp = new Comparable[intEnd_I - intStart_I + 1];
		boolean[] arrboolSecondTemp = new boolean[intEnd_I - intStart_I + 1];
		
															//Start indexes of the first and second halves.
		int intFirstHalf = intStart_I;
		int intSecondHalf = intMiddle_I + 1;
		
															//Index used for the temporal arrays.
		int intIndexTemp = 0;
		
		/*WHILE-DO*/
		while (
															//While none of the halves have reached their end
				intFirstHalf <= intMiddle_I && 
				intSecondHalf <= intEnd_I
				) 
		{
															//If the element in the first half is smaller or equal to
															//		the element of the second half, it goes first in
															//		the temporal array. Otherwise, the second element
															//		goes first. The second array only copies the moves
															//		of the first array.
			if (
				arrcompFirstArray_IO[intFirstHalf].compareTo(arrcompFirstArray_IO[intSecondHalf]) <= 0
				)
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intFirstHalf];
				
				intFirstHalf = intFirstHalf + 1;
			}
			else
			{
				arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
				arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intSecondHalf];
				
				intSecondHalf = intSecondHalf + 1;
			}
			
															//An element has been added to the temporal arrays
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the second half finished first, we copy the rest of
															//		the first half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intFirstHalf <= intMiddle_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intFirstHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intFirstHalf];
			
			intFirstHalf = intFirstHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//If the first half finished first, we copy the rest of
															//		the second half to the temporal arrays.
		/*WHILE-DO*/
		while (
				intSecondHalf <= intEnd_I
				)
		{
			arrcompFirstTemp[intIndexTemp] = arrcompFirstArray_IO[intSecondHalf];
			arrboolSecondTemp[intIndexTemp] = arrboolSecondArray_IO[intSecondHalf];
			
			intSecondHalf = intSecondHalf + 1;
			
			intIndexTemp = intIndexTemp + 1;
		}
		
															//With the temporal arrays sorted, we copy them to the
															//		corresponding piece of array
		int intI = 0;
		/*WHILE-DO*/
		while (
				intI < arrcompFirstTemp.length
				)
		{
			arrcompFirstArray_IO[intStart_I] = arrcompFirstTemp[intI];
			arrboolSecondArray_IO[intStart_I] = arrboolSecondTemp[intI];
			
			intI = intI + 1;
			
			intStart_I = intStart_I + 1;
		}
	}
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tools.getClass Method to get the class of any object (including primitives)*/
    //------------------------------------------------------------------------------------------------------------------
    public static Class getClass(                                  //Get the class of any object. This object will be invoked
                                                            //      whenever a non-primitipe type object is sent to the
                                                            //      getClass() method. For the primitives, there are
                                                            //      overloaded methods.

                                                            //The object from which the class will be returned.
        Object object_I
        )
    {
                                                            //Note that this method would fail if the received object
                                                            //      was a primitive, because instead of returning the
                                                            //      desired "int", "double", etc. classes for each
                                                            //      primitive, this method would return a
                                                            //      "java.lang.Integer", "java.lang.Double", etc. class.
                                                            //      This happens because when sending a primitive to a
                                                            //      method that receives an Object, the primitive is
                                                            //      automatically "wrapped" in its java Object class
                                                            //      (Integer for int, Double for double, etc). Because
                                                            //      of this behavior, there are overloaded methods that
                                                            //      receive each of the Towa standard primitive types
                                                            //      (int, long, boolean, char and double). If any more
                                                            //      primitive types were standardized, a getClass method
                                                            //      that receives this new primitive must be created.

                                                            //Get the class from the input object.
        return object_I.getClass();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Class getClass(                                  //Overloaded method to get the class of a primitive of type
                                                            //      int.

                                                            //The object from which the class will be returned is an
                                                            //      int (overloaded method).
        int object_I
        )
    {
        return int.class;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Class getClass(                                  //Overloaded method to get the class of a primitive of type
                                                            //      long.

                                                            //The object from which the class will be returned is a
                                                            //      long (overloaded method).
        long object_I
        )
    {
        return long.class;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Class getClass(                                  //Overloaded method to get the class of a primitive of type
                                                            //      boolean.

                                                            //The object from which the class will be returned is a
                                                            //      boolean (overloaded method).
        boolean object_I
        )
    {
        return boolean.class;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Class getClass(                                  //Overloaded method to get the class of a primitive of type
                                                            //      char.

                                                            //The object from which the class will be returned is a
                                                            //      char (overloaded method).
        char object_I
        )
    {
        return char.class;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static Class getClass(                                  //Overloaded method to get the class of a primitive of type
                                                            //      double.

                                                            //The object from which the class will be returned is a
                                                            //      double (overloaded method).
        double object_I
        )
    {
        return double.class;
    }
    /*END-TASK*/


    //==================================================================================================================
    /*TASK Tools.boolIsGenericType. Know if a class is generic (has generic arguments)*/
    //------------------------------------------------------------------------------------------------------------------
    public static boolean boolIsGenericType(                //Evaluate if the type received is generic. (It has at least
                                                            //      one generic Type Variable.
        Class class_I
        )
    {
                                                            //Get the Type Variables from the class. Each TypeVariable
                                                            //      represents a generic variable declared in the class.
                                                            //      If the class does not use type variable, the array
                                                            //      array will be of empty (length = 0).

        /*NSTD*/TypeVariable[] /*END-NSTD*/ arrTypeVariables = class_I.getTypeParameters();
        //The class has at least 1 Type variable.
        return (
            arrTypeVariables.length > 0
        );
    }
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tools.arrclassGetGenericArguments. Get the generic arguments of a collection.*/
    //------------------------------------------------------------------------------------------------------------------
    public static Class[] arrclassGetGenericArguments(      //get the generic arguments of a standard generic object. It
                                                            //      returns an array of Classes that represents the
                                                            //      generic arguments of the collection. If the input
                                                            //      is null, it will abort. If the input is not one of
                                                            //      the standard generic collections, the method will
                                                            //      return an empty array. If it is not possible to get
                                                            //      the generic arguments of the object (the collection
                                                            //      is empty, checked by boolGenericArgumentsPossible),
                                                            //      the returned array will also be empty. In this case,
                                                            //      the user will be told that it was not possible to
                                                            //      get the generic arguments because the collection is
                                                            //      empty.

                                                            //The collection to be analyzed.
                                                            Object objCollection
        )
    {
        if (objCollection == null)
        {
            Tools.subAbort(Tes2.strTo(objCollection, "objCollection") + " can not be null.");
        }

                                                            //It's still unknown how much arguments the collection will
                                                            //      have.
        LinkedList<Class> lstclassArgument = new LinkedList<Class>();

        /*CASE*/
        if (
            !boolGenericArgumentsPossible(objCollection)
            )
        {
            Tools.subWarning(Tes2.strTo(objCollection, "objCollection") +
                ". Is not possible to determine the types of generic arguments because the obkect is empty");
        }
        else if (objCollection instanceof LinkedList)
        {
            lstclassArgument.add(((LinkedList)objCollection).get(0).getClass());
        }
        else if (objCollection instanceof ConcurrentLinkedDeque)
        {
            lstclassArgument.add(((ConcurrentLinkedDeque)objCollection).peekFirst().getClass());
        }
        else if (objCollection instanceof Stack)
        {
            lstclassArgument.add(((Stack)objCollection).get(0).getClass());
        }
        else if (objCollection instanceof  LinkedHashMap)
        {
            lstclassArgument.add(((LinkedHashMap) objCollection).keySet().iterator().next().getClass());
            lstclassArgument.add(((LinkedHashMap) objCollection).values().iterator().next().getClass());
        }
        else if (objCollection instanceof Map.Entry)
        {
            lstclassArgument.add(((Map.Entry)objCollection).getKey().getClass());
            lstclassArgument.add(((Map.Entry)objCollection).getValue().getClass());
        }
        else
        {
            Tools.subWarning(Tes2.strTo(objCollection, "objCollection") + " is not a standard collection");
        }
        /*END-CASE*/

        Class[] arrclassArgument;

        if (
            lstclassArgument.size() > 0
            )
        {
            arrclassArgument = lstclassArgument.toArray(new Class[0]);
        }
        else
        {
            arrclassArgument = new Class[0];
        }
        return arrclassArgument;
    }

    public static boolean boolGenericArgumentsPossible(     //Check if a collection can be analyzed and get the generic
                                                            //      arguments out of it with the method
                                                            //      arrclassGetGenericArguments.
                                                            //This method will return false if the collection is not one
                                                            //      of the standard generic collections or if the
                                                            //      collection is empty.

                                                            //The collection to be analyzed.
        Object objCollection
        )
    {
        if (objCollection == null)
        {
            System.out.println("Is null, abort");
        }

        boolean boolArgumentGenericPossible;

        /*CASE*/
        if (objCollection instanceof LinkedList)
        {
            boolArgumentGenericPossible = !((LinkedList)objCollection).isEmpty();
        }
        else if (objCollection instanceof ConcurrentLinkedDeque)
        {
            boolArgumentGenericPossible = !((ConcurrentLinkedDeque)objCollection).isEmpty();
        }
        else if (objCollection instanceof Stack)
        {
            boolArgumentGenericPossible = !((Stack)objCollection).isEmpty();
        }
        else if (objCollection instanceof  LinkedHashMap)
        {
            boolArgumentGenericPossible = !((LinkedHashMap) objCollection).isEmpty();
        }
        else if (objCollection instanceof Map.Entry)
        {
            boolArgumentGenericPossible = true;
        }
        else
        {
            boolArgumentGenericPossible = false;
        }
        /*END-CASE*/

        return boolArgumentGenericPossible;
    }
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tools.intArrRank. Get the rank of an array. Equivalent to C#'s Array.Rank. Returns -1 in case of an error.*/
    //------------------------------------------------------------------------------------------------------------------
    public static int intArrRank(                           //Get the rank of an array. The incoming array must be
                                                            //      standard. This means that even though, technically,
                                                            //      in Java, multidimensional arrays are arrays of
                                                            //      arrays, standard arrays must be 2D o 3D arrays. For
                                                            //      this, before calling this method, use the
                                                            //      Tools.boolMultiDimensionArrayIsStandard method.

                                                            //Note that in the case in which it's decided if the obj is
                                                            //      an instance of int[], int[][], ... Object[], etc.,
                                                            //      the branch for "instanceof Object[][][]" is before
                                                            //      the bracnh for "instanceof Object[][]" and the
                                                            //      branch for "instanceof Object[]". This is because an
                                                            //      object which is instanceof Object[][][], will also
                                                            //      be instanceof Object[][] and instanceof Object[], so
                                                            //      this issue is solved by the priority in which the
                                                            //      object tries to enter the branches of the case. So,
                                                            //      if an object is instanceof Object[][][], it will not
                                                            //      try to enter the instanceof Object[][] and
                                                            //      instanceof Object[] branches.
                                                            //instanceof Object = Class.isAssignableFrom(obj_I.getClass)

                                                            //Class to be analyzed
        Class class_I
        )
    {
        int intRank;

        if (
            class_I == null
            )
        {
            Tools.subAbort(Tes2.strTo(class_I, "class_I") + " can not be null");
            intRank = -1;
        }


        if (!(
            class_I.isArray()
            ))
        {
            Tools.subAbort(Tes2.strTo(class_I, "class_I") + " is not the class of an array.");
            intRank = -1;
        }
        else
        {
            /*CASE*/
            if (
                int[].class.isAssignableFrom(class_I)
                )
            {
                intRank = 1;
            }
            else if (
                int[][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 2;
            }
            else if (
                int[][][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 3;
            }
            else if (
                long[].class.isAssignableFrom(class_I)
                )
            {
                intRank = 1;
            }
            else if (
                long[][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 2;
            }
            else if (
                long[][][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 3;
            }
            else if (
                boolean[].class.isAssignableFrom(class_I)
                )
            {
                intRank = 1;
            }
            else if (
                boolean[][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 2;
            }
            else if (
                boolean[][][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 3;
            }
            else if (
                char[].class.isAssignableFrom(class_I)
                )
            {
                intRank = 1;
            }
            else if (
                char[][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 2;
            }
            else if (
                char[][][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 3;
            }
            else if (
                double[].class.isAssignableFrom(class_I)
                )
            {
                intRank = 1;
            }
            else if (
                double[][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 2;
            }
            else if (
                double[][][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 3;
            }
            else if (
                Object[][][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 3;
            }
            else if (
                Object[][].class.isAssignableFrom(class_I)
                )
            {
                intRank = 2;
            }
            else if (
                Object[].class.isAssignableFrom(class_I)
            )
            {
                intRank = 1;
            }
            else
            {
                Tools.subAbort(Tes2.strTo(class_I, "class_I") +
                    " is an array but is not of rank 1,2 or 3. It is not [], [,], or [,,]");
                intRank = -1;
            }
            /*END-CASE*/
        }

        return intRank;
    }
    /*END-TASK*/

    //TODO Reacomodate this function
    public static String strStringArrayToString (               //Put all the elements of an array in C# format string

        String[] arrstr_I
        )
    {
        StringBuilder builder = new StringBuilder();
        for(String s : arrstr_I) {
            builder.append(s);
        }
        return builder.toString();
    }
}
/*END-TASK*/