/*TASK CodtypeEnum*/
package QEnablerBase;

															//AUTHOR: Towa (EOG-Eduardo Ojeda).
															//CO-AUTHOR: Towa (GLG-Gerardo López).
															//DATE: 23-Julio-2012.
															//PURPOSE:
															//Enumerador utilizado para diferenciar los tipos de codigo.

//======================================================================================================================
public enum CodtypeEnum 
															//Posibles tipos de Código.
{
	//##################################################################################################################
	Z_ERROR_NOT_DEFINED,
	//##################################################################################################################

															//Cuando se tiene todo el archivo completo del componente.
	COMPONENT,
															//Cuando se tiene código EMBEDED de otra tecnología.
															//Ej. En COBOL en la secuencia EXEC SQL embeded sql
															//      END-EXEC, el código "embeded sql" será un EMBEDED
															//      donde el padre es el COMPONENT COBOL que lo
															//      contiene que es de donde se extrae este EMBEDED.
	EMBEDED,
															//Cuando se tiene una parte de un COMPONENT o EMBEDED que
															//      se tiene estandarizado.
															//Ej. DATA_DIVISION de un programa COBOL, o solo un método
															//      de una clase C#.
	STANDARD_ELEMENT,

	/*TASK RPS.Com Relevant Part Comments*/
															//El código de un comentario puede ser de los siguientes
															//     tipos.

															//Comentarios del tipo FULL_LINE.
	COM_FULL_LINE,
	                                                    	//Comentarios del tipo TAG_FULL_LINE.
	COM_TAG_FULL_LINE,
	                                                    	//Comentarios del tipo END_OF_LINE.
	COM_END_OF_LINE,
	                                                    	//Comentarios del tipo DELIMITED.
	COM_DELIMITED,
	                                                    	//Comentarios del tipo EMBEDED.
	COM_EMBEDED,
	/*END-TASK*/
}

//======================================================================================================================
/*END-TASK*/