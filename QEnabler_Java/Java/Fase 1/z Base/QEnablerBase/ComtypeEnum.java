/*TASK CodtypeEnum*/
package QEnablerBase;

                                                            //AUTHOR: Towa (EOG-Eduardo Ojeda).
                                                            //CO-AUTHOR: Towa (GLG-Gerardo López).
                                                            //DATE: 23-Julio-2012.
                                                            //PURPOSE:
                                                            //Enumerador utilizado para diferenciar los tipos de codigo.

//======================================================================================================================
public enum ComtypeEnum
                                                            //Posibles tipos de Comentarios.
{
    //##################################################################################################################
    Z_ERROR_NOT_DEFINED,
    //##################################################################################################################

                                                            //Cuando los comentarios son líneas que solo pueden tener 
                                                            //      comentarios (no son tag), (Ej. en COBOL así tiene
                                                            //      que ser).
    FULL_LINE,
                                                            //Cuando los comentarios son una sola línea que es un tag.
    TAG_FULL_LINE,

                                                            //Cuando los comentarios van al final de la línea con, 
                                                            //      opcionalmente otros tokens antes (Ej. en C# se tiene
                                                            //      comentarios de la forma "//comentarios", en Stored 
                                                            //      Programs se tienen comentarios de la forma 
                                                            //      "--comentarios".
    END_OF_LINE,

                                                            //Cuando los comentantarios pueden estar EMBEDED en el 
                                                            //      código junto con otros tokens antes o después por lo
                                                            //      cual deben estar estar DELIMITED (Ej. en C# y 
                                                            //      Stored Programs se tienen comentarios con la forma 
                                                            //      "/*comentarios*/").

                                                            //Cuando los DELIMITED y se extiendes en 2 ó mas líneas.
    DELIMITED,
                                                            //Cuando son DELIMITED, SON CHIQUITOS y todo esta en una 
                                                            //      línea.
    EMBEDED,
}

//======================================================================================================================
/*END-TASK*/