/*TASK      EWT01          (Cod)subPreparaConstantes*/
using System;
using System.Collections.Generic;
using System.Text;
using TowaInfrastructure;

//                                                          //AUTHOR: Towa (Gerardo López).
//                                                          //CO-AUTHOR: Towa (Adrian Garza).
//                                                          //DATE: 23-Julio-2012.
//                                                          //PURPOSE:
//                                                          //Implementación de clases Abstract para Código.

namespace QEnablerBase
{
    //[  1         2         3         4         5         6         7         8         9         0         1         2         3
    //789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789]
    public abstract partial class CodAbstract
    {
        //------------------------------------------------------------------------------------------------------------------------
        public static void subPreparaConstantes(            //LOS CONSTRUCTORES ESTÁTICOS DE LAS CLASES CodxxCodigo
            //                                              //   DEBEN LLAMAR A   ESTE MÉTODO PARA PREPARAR SU INFORMACIÓN.
            //                                              //PREPARA MUCHAS CONSTANTES PARA FACILITAR EL PROCESO
            //                                              //ESTOESUNAPALABRAMUYLARGAQUESEDEBECORTAR>>>>>>>>>AQUÍ<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<ytambién___aquí<<<<<<.
            //                                              //2.           Separa y ordena strCHAR_A_CONVERTIR_Y_CONVERSION.
            //                                              //[            3. Ordena arrcharUTIL_EN_COM.
            //                                              //). Ordena arrcharEN_NOMBRE y arrcharEN_NOMBRE_CHAR_1. ESTO EXCEDE      (blancos para verificar corte) EL TAMAÑO DE LA LINEA ESTRUCTURADA,+++++++ Ordena arrcharEN_NOMBRE y arrcharEN_NOMBRE_CHAR_1. ESTO EXCEDE EL TAMAÑO DE LA LINEA ESTRUCTURADA,+++++++

            string strCHAR_UTIL_I,                          //Caracteres útiles en el lenguage.
            out char[] arrcharUTIL_O,                       //Caracteres en strCHAR_UTIL_I ordenados.

            string strCHAR_A_CONVERTIR_Y_CONVERSION_I,      //Pares de caracteres, donde el carácter en posición par (0, 2, etc.)
            //                                              //      deberá ser convertido al siguiente carácter (1, 3, etc.), para
            //                                              //      poder ser procesado. Los caracteres en posición par no están
            //                                              //      en arrcharUTIL y los caracteres en posición impar si están en
            //                                              //      arrcharUTIL.
            out char[] arrcharA_CONVERTIR_O,                //Caracteres en posición par ordenados.
            out char[] arrcharCONVERSION_O,                 //Caracteres en posición impar ordenados por arrcharA_CONVERTIR_O.

            string strCHAR_UTIL_EN_COM_I,                   //Caracteres considerados "útiles" en una línea de comentarios, si una
            //                                              //      línea de comentarios contiene al menos 1 caracter de estos, la
            //                                              //      línea se considera que es útil.
            out char[] arrcharUTIL_EN_COM_O,                //Caracteres ordenados.

            string strCHAR_EN_NOMBRE_I,                     //Caracteres aceptados en una palabra reservada o identificador.
            string strCHAR_EN_NOMBRE_CHAR_1_ADICIONAL_I,    //Caracteres adicionales que solo son aceptados en el primer caracter.
            out char[] arrcharEN_NOMBRE_CHAR_1_O,           //Caracteres aceptados en el primer caracter de palabra reservada o  
            //                                              //      identificador.
            out char[] arrcharEN_NOMBRE_O                   //Caracteres aceptados en siguientes caracteres de palabra reservada 
            //                                              //      o identificador.

            )
        {
            arrcharUTIL_O = /*Un comentario*/null;
            arrcharA_CONVERTIR_O = null;
            arrcharCONVERSION_O = null;
            arrcharUTIL_EN_COM_O = null;
            arrcharEN_NOMBRE_CHAR_1_O = null;
            arrcharEN_NOMBRE_O = null;                      //--------------------------
        }
        /* AÑADÍ ESTO PARA PODER HACER UNA PRUEBA
           esto  serán 3 líneas SERÁ MUY LARGA PARA VERIFICAR QUE LA CORTE BIEN, SERÁ MUY LARGA PARA VERIFICAR QUE CORTA<<<ESTO YA NO APARECE, FUE CORTADO>>>
           esta es        la última línea*/
    }
}      
/*END-TASK*/
/*POR ULTIMO      AÑADI ESTO PARA VER QUE PASA           SI NO CIERRA2