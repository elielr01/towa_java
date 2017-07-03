/*TASK Test Support for Testing*/
using System;
using System.IO;
using System.Text;
using System.Collections.Generic;
using System.Windows.Forms;

//                                                          //AUTHOR: Towa (GLG-Gerardo Lopez).
//                                                          //CO-AUTHOR: Towa ().
//                                                          //DATE: 13-Mayo-2011.
//                                                          //PURPOSE:
//                                                          //Implementación de clase estática para facilitar el Testing

namespace TowaInfrastructure
{
    //==================================================================================================================
    public static partial class Test
    //                                                      //Clase que incuye métodos estáticos (funciones o
    //                                                      //      subrutinas) de uso compartido en todos los sistemas.
    {
        //--------------------------------------------------------------------------------------------------------------
        static Test(
            //                                              //Prepara las constantes para poder utilizarlas.
            //                                              //CADA VEZ QUE SE AÑADAN CONSTANTES QUE REQUIERAN SER
            //                                              //      INICIALIZADAS, SE AÑADE LA LLAMADA A OTRO MÉTODO.
            )
        {
            //                                              //Se requiere preparar la siguiente información que será
            //                                              //      usada en forma provisional por los strTo
            Test.arrcharUSEFUL_IN_TEXT = Test.strCHAR_USEFUL_IN_TEXT.ToCharArray();
            Array.Sort(Test.arrcharUSEFUL_IN_TEXT);
            Test.arrcharDO_NOT_SHOW_HEX = Test.arrcharUSEFUL_IN_TEXT;
            Test.arrt2charDESCRIPTION = new T2charDescriptionTuple[] { };

            Test.subPrepareConstantTypes(out Test.arrstrPRIMITIVE_TYPE, out Test.arrstrPRIMITIVE_PREFIX,
                out Test.arrstrSYSTEM_TYPE, out Test.arrstrSYSTEM_PREFIX, out Test.arrstrGENERIC_TYPE,
                out Test.arrstrGENERIC_PREFIX);
            Test.subPrepareConstantsGetObjId();
            Test.subPrepareConstantsToBlockFormat();

            Test.subPrepareConstantsForStrTo(out Test.strCHAR_USEFUL_IN_TEXT, out Test.arrcharUSEFUL_IN_TEXT,
                out Test.arrcharDO_NOT_SHOW_HEX, out Test.arrt2charDESCRIPTION);

            Test.subPrepareConstantsSubLogAndTrace();
            Test.subPrepareConstantsTestAbort();
        }

        //==============================================================================================================
        /*TASK Test.subAbort subAbort and subWarnint*/
        //--------------------------------------------------------------------------------------------------------------
        public static void subAbort(                        //Aborta ejecucion al detectar situación anormal. Puede ser
            //                                              //      WinForms app o Console app.

            //                                              //Mensaje descriptivo del aborto.
            String strMessage_I
            )
        {
            String strMethodCallS = Test.strStackOnlyMethodCalls(Environment.StackTrace);

            String strFullMessage = "<<<ABNORMAL END>>>" + Environment.NewLine + strMessage_I + Environment.NewLine +
                "METHOD CALLS:" + Environment.NewLine + strMethodCallS;

            if (
                Application.MessageLoop
                )
            {
                //                                          //Aborto en WinForms app.
                MessageBox.Show(strFullMessage);
            }
            else
            {
                //                                          //Aborto en Console app.
                Console.WriteLine(strFullMessage);

                Console.WriteLine("");
                Console.WriteLine("ENTER KEY TO END");
                String strReadLine = Console.ReadLine();
            }

            //                                              //Si esta en Test, genera la información en el log y lo
            //                                              //      cierra.
            Test.subLog(strFullMessage);
            Test.subFinalizeLog();

            //                                              //Existen 2 posibilidades para continuar o terminar
            if (
                Test.boolIsTestAbortOn
                )
            {
                throw new SysexcepuserUserAbort(strFullMessage);
            }
            else
            {
                Environment.Exit(0);
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        public static void subWarning(                      //Ejecucion al detectar situación anormal.
            //                                              //NO ABORTA PARA PERMITIR UN DIAGNÓSTICO MAS COMPLETO, SIN
            //                                              //      EMBARGO PUEDE TENER COMPORTAMIENTO IMPREDECIBLE.
            //                                              //Puede ser WinForms app o Console app.

            //                                              //Mensaje descriptivo del aborto.
            String strMessage_I
            )
        {
            String strMethodCallS = Test.strStackOnlyMethodCalls(Environment.StackTrace);

            String strFullMessage = "<<<WARNING>>>" + Environment.NewLine + strMessage_I + Environment.NewLine +
                "METHOD CALLS:" + Environment.NewLine + strMethodCallS;

            //                                              //Solo muestra el mensaje si NO esta en "ComparableLog",
            //                                              //      en ComparableLog se deseea que la prueba corra si
            //                                              //      interacción humana.
            if (
                !Test.boolComparableLogGet()
                )
            {
                if (
                    Application.MessageLoop
                    )
                {
                    //                                      //Aborto en WinForms app.
                    MessageBox.Show(strFullMessage);
                }
                else
                {
                    //                                      //Aborto en Console app.
                    Console.WriteLine(strFullMessage);

                    Console.WriteLine("");
                    Console.WriteLine("ENTER KEY TO CONTINUE");
                    String strReadLine = Console.ReadLine();
                }
            }

            //                                              //Si esta en Test, genera la información en el log.

            //                                              //Si la prueba es ComparableLog no muestra la secuencia de
            //                                              //      Method Calls.
            if (
                Test.boolComparableLogGet()
                )
            {
                strFullMessage = "<<<WARNING>>>" + Environment.NewLine + strMessage_I;
            }

            Test.subLog(strFullMessage);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strStackOnlyMethodCalls(      //ESTE METODO DEBE SER REESCRITO PARA CADA TECNOLOGÍA O
            //                                              //      INSTANCIA DE LA TECNOLOGÍA.
            //
            //                                              //EN C# al igual que en otras tecnologías el stack
            //                                              //      contendrá mucha información.
            //                                              //Se extrae solo la parte necesaria para desplegar:
            //                                              //[
            //                                              //namespace/package.class.method(parameters):line nnn
            //                                              //namespace/package.class.method(parameters):line nnn
            //                                              //...
            //                                              //namespace/package.class.method(parameters):line nnn
            //                                              //]
            //                                              //Lo que se requiere es la secuencia de métodos desde el
            //                                              //      inicio, hasta antes de la ejecusicón del subAbort.
            //                                              //nótese que para desplegar la información como se muestra,
            //                                              //      en el String será necesario incluir los caracteres
            //                                              //      de NewLine entre cada una de estas líneas.
            //                                              //Ejemplo:
            //                                              //[
            //                                              //TowaInfrastructure.Sys.subCopyFileRewrite(...):line 741
            //                                              //QEnabler.TestSys.subSys01TestC():line 341
            //                                              //QEnabler.frmPrueba.btnPrueba_Click(...):line 25
            //                                              //]
            //                                              //Stack (como lo proporciona la tecnología)
            String strStack_I
            )
        {
            //                                              //La estrategia será:
            //                                              //1. Localizar "TowaInfrastructure.", esta será la línea en
            //                                              //      subAborta donde se obtiene la información de Stack
            //                                              //      (esto no se require).
            //                                              //2. Localiza el siguiente NewLine, esto será la
            //                                              //      información donde se ejecuta subAborta, esto es
            //                                              //      donde sucedió la causa del aborto, a partir de aquí
            //                                              //      no interesa (después del NewLine).
            //                                              //3. Localiza "System.", nos interesa hasta antes de esta
            //                                              //      línea (se tiene NewLine + "___at_" + "System.".
            //                                              //4. Convierte a arrstr de líneas para poder analizarlas.
            //                                              //5. De cada línea elimina el principio que es "___at_" y lo 
            //                                              //      que esta después del ) hasta antes de :line.
            //                                              //6. Vuelve a formar el String (con los NewLine entre
            //                                              //      líneas).
            //                                              //7. Chars '&' should be removed.

            //                                              //Localiza a partir de donde interesa.
            int intStart = strStack_I.IndexOf("TowaInfrastructure.");
            intStart = strStack_I.IndexOf(Environment.NewLine, intStart);
            intStart = intStart + Environment.NewLine.Length;

            //                                              //Localiza a partir de donde ya no interesa.
            int intEndPlusOne = strStack_I.IndexOf("System.", intStart);
            intEndPlusOne = intEndPlusOne - (Environment.NewLine + "___at_").Length;
            //                                              //Toma la parte que nos interesa (esto aún tiene información
            //                                              //      de los archivos que se require eliminar).
            String strStackOnlyMethodCalls = strStack_I.Substring(intStart, intEndPlusOne - intStart);

            String[] arrstrLineOnlyMethodCall =
                strStackOnlyMethodCalls.Split(new String[] { Environment.NewLine }, StringSplitOptions.None);

            for (int intI = 0; intI < arrstrLineOnlyMethodCall.Length; intI = intI + 1)
            {
                //                                          //To easy code
                String strLine = arrstrLineOnlyMethodCall[intI];

                //                                          //Find ')', end of method call y luego :
                int intParenthesis = strLine.IndexOf(')');

                //                                          //Find ':', start of ":line nn", some tows of informatión do
                //                                          //      contain this ":line nn"
                int intColon = strLine.IndexOf(':');

                //                                          //Extrat required information.
                String strLineRevised = strLine.Substring("___at_".Length, intParenthesis + 1 - "___at_".Length);
                strLineRevised = strLineRevised.Replace("&", "");

                if (
                    intColon >= 0
                    )
                {
                    strLineRevised = strLineRevised + strLine.Substring(intColon);
                }

                arrstrLineOnlyMethodCall[intI] = strLineRevised;
            }

            //                                              //Vuelve a formato String.
            strStackOnlyMethodCalls = String.Join(Environment.NewLine, arrstrLineOnlyMethodCall);

            return strStackOnlyMethodCalls;
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.PrepareStrTo Constants and initializer for strTo*/
        //--------------------------------------------------------------------------------------------------------------
        //                                                  //Set of methods strTo to analyse and format:
        //                                                  //a) Objects: bclass, btuple, enum or sysexcep.
        //                                                  //b) System objects: sysfile, sysdir, syssr & syssw.
        //                                                  //c) Primitives: int, long, num, char & bool.
        //                                                  //d) Simple objects like: str, date, time, ts & type.

        //--------------------------------------------------------------------------------------------------------------
        /*CONSTANTS*/

        //                                                  //Si un String excede esta longitud, se muestra la longitud
        //                                                  //      ejemplo "abd def.... xyz"<88>.
        private const int intLONG_STRING = 50; 

        //                                                  //In methods strTo, an Item/Row/Matrix of this characters
        //                                                  //      size will be include in one long line.
        private const int intLONG_ITEM_ROW_MATRIX = 80;

        //                                                  //This will be the maximun space reseved for key when strTo
        //                                                  //      display a dictionary, if we have longhest key the
        //                                                  //      content will not be aligned.
        private const int intKEY_LEN_MAX = 50;

        //                                                  //Caracter que será usado como substituto cuando un caracter
        //                                                  //      no sea "visible".
        private const char charSUBSTITUTE_NO_USEFUL_IN_TEXT = '●';

        //                                                  //Se incluyen todos los caracteres del mismo tamaño que A
        //                                                  //      (SizeF en font consolas 10 points).
        //                                                  //Para efectos de entendimiento y documentación se agrupan
        //                                                  //      por UccUnicodeCategory (ascencente), también los
        //                                                  //      caracteres están en orden ascendente.
        //                                                  //Nótese que blank no está en este conjunto dado que su
        //                                                  //      SizeF es diferente.
        //                                                  //ES PROBABLE QUE EN OTRO FONT (diferente de consolas) ESTOS
        //                                                  //      CARACTERES SE MUESTREN DISTINTO.
        //                                                  //DADO QUE EL ESTÁNDAR ES USAR consolas PARA VISUALIZAR EL
        //                                                  //      CÓDIGO TANTO EN PANTALLA COMO EN IMPRESORA SE
        //                                                  //      UTILIZO ESTE FONT.
        //                                                  //TODAS ESTAS CONSTANTES PRETENDEN SER UNA AYUDA PARA
        //                                                  //      CODIFICAR Y PROBAR EL CÓDIGO.
        //                                                  //NÓTESE QUE:
        //                                                  //1. Cualquier caracter de x0000-xFFFF puede ser utilizado.
        //                                                  //2. Solo los caracteres en este conjunto y el blank
        //                                                  //      pueden ser visualizado en pantalla o en texto
        //                                                  //3. Solo loc caracteres en este conjunto, el blank y los de
        //                                                  //      escape pueden ser escritos en archivos de texto.
        //                                                  //4. Los métodos strTo hacen lo necesario para poner mostrar
        //                                                  //      todos los caracteres x0000-xFFFF en font consolas.
        public static readonly T2uccCategoryAndCharsTuple[] arrt2uccCHAR_USEFUL_IN_TEXT = {
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.UPPERCASE_LETTER,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞĀĂĄĆĈĊČĎĐĒĔĖĘĚĜĞĠĢĤĦĨĪĬĮİĲĴĶĹĻĽĿŁŃŅŇŊŌŎŐŒŔŖŘ" +
                "ŚŜŞŠŢŤŦŨŪŬŮŰŲŴŶŸŹŻŽƁƂƄƆƇƉƊƋƎƏƐƑƓƔƖƗƘƜƝƟƠƢƤƦƧƩƬƮƯƱƲƳƵƷƸƼǄǇǊǍǏǑǓǕǗǙǛǞǠǢǤǦǨǪǬǮǱǴǶǷǸǺǼǾȀȂȄȆȈȊȌȎȐȒȔȖȘȚȜȞȠ" +
                "ȢȤȦȨȪȬȮȰȲȺȻȽȾɁɃɄɅɆɈɊɌɎΆΈΉΊΌΎΏΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩΪΫϒϓϔϘϚϜϞϠϢϤϦϨϪϬϮϴϷϹϺϽϾϿЀЁЂЃЄЅІЇЈЉЊЋЌЍЎЏАБВГДЕЖ" +
                "ЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯѠѢѤѦѨѪѬѮѰѲѴѶѸѺѼѾҀҊҌҎҐҒҔҖҘҚҜҞҠҢҤҦҨҪҬҮҰҲҴҶҸҺҼҾӀӁӃӅӇӉӋӍӐӒӔӖӘӚӜӞӠӢӤӦӨӪӬӮӰӲӴӶӸӺӼ" +
                "ӾԀԂԄԆԈԊԌԎԐԒḀḂḄḆḈḊḌḎḐḒḔḖḘḚḜḞḠḢḤḦḨḪḬḮḰḲḴḶḸḺḼḾṀṂṄṆṈṊṌṎṐṒṔṖṘṚṜṞṠṢṤṦṨṪṬṮṰṲṴṶṸṺṼṾẀẂẄẆẈẊẌẎẐẒẔẠẢẤẦẨẪẬẮẰẲẴẶẸẺ" +
                "ẼẾỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴỶỸἈἉἊἋἌἍἎἏἘἙἚἛἜἝἨἩἪἫἬἭἮἯἸἹἺἻἼἽἾἿὈὉὊὋὌὍὙὛὝὟὨὩὪὫὬὭὮὯᾸᾹᾺΆῈΈῊΉῘῙῚΊῨῩῪΎῬῸΌῺΏ" +
                "ΩℲↃ"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.LOWERCASE_LETTER,
                "abcdefghijklmnopqrstuvwxyzªµºßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿāăąćĉċčďđēĕėęěĝğġģĥħĩīĭįıĳĵķĸĺļľŀłńņňŉŋ" +
                "ōŏőœŕŗřśŝşšţťŧũūŭůűųŵŷźżžſƀƃƅƈƌƍƒƕƙƚƛƞơƣƥƨƪƫƭưƴƶƹƺƽƾƿǆǉǌǎǐǒǔǖǘǚǜǝǟǡǣǥǧǩǫǭǯǰǳǵǹǻǽǿȁȃȅȇȉȋȍȏȑȓȕȗșțȝȟȡȣȥ" +
                "ȧȩȫȭȯȱȳȴȵȶȷȸȹȼȿɀɂɇɉɋɍɏɐɑɒɓɔɕɖɗɘəɚɛɜɝɞɟɠɡɢɣɤɥɦɧɨɩɪɫɬɭɮɯɰɱɲɳɴɵɶɷɸɹɺɻɼɽɾɿʀʁʂʃʄʅʆʇʈʉʊʋʌʍʎʏʐʑʒʓʕʖʗʘʙʚʛʜʝʞ" +
                "ʟʠʡʢʣʤʥʦʧʨʩʪʫʬʭʮʯͻͼͽΐάέήίΰαβγδεζηθικλμνξοπρςστυφχψωϊϋόύώϐϑϕϖϗϙϛϝϟϡϣϥϧϩϫϭϯϰϱϲϳϵϸϻϼабвгдежзийклмнопрст" +
                "уфхцчшщъыьэюяѐёђѓєѕіїјљњћќѝўџѡѣѥѧѩѫѭѯѱѳѵѷѹѻѽѿҁҋҍҏґғҕҗҙқҝҟҡңҥҧҩҫҭүұҳҵҷҹһҽҿӂӄӆӈӊӌӎӏӑӓӕӗәӛӝӟӡӣӥӧөӫӭӯӱӳӵ" +
                "ӷӹӻӽӿԁԃԅԇԉԋԍԏԑԓḁḃḅḇḉḋḍḏḑḓḕḗḙḛḝḟḡḣḥḧḩḫḭḯḱḳḵḷḹḻḽḿṁṃṅṇṉṋṍṏṑṓṕṗṙṛṝṟṡṣṥṧṩṫṭṯṱṳṵṷṹṻṽṿẁẃẅẇẉẋẍẏẑẓẕẖẗẘẙẚẛạảấầ" +
                "ẩẫậắằẳẵặẹẻẽếềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹἀἁἂἃἄἅἆἇἐἑἒἓἔἕἠἡἢἣἤἥἦἧἰἱἲἳἴἵἶἷὀὁὂὃὄὅὐὑὒὓὔὕὖὗὠὡὢὣὤὥὦὧὰάὲέὴήὶ" +
                "ίὸόὺύὼώᾀᾁᾂᾃᾄᾅᾆᾇᾐᾑᾒᾓᾔᾕᾖᾗᾠᾡᾢᾣᾤᾥᾦᾧᾰᾱᾲᾳᾴᾶᾷιῂῃῄῆῇῐῑῒΐῖῗῠῡῢΰῤῥῦῧῲῳῴῶῷℓⅎↄﬁﬂ"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.TITLECASE_LETTER,
                //                                          //Las siguiente ᾼῌῼ parecen iguales, sin embargo NO LO SON
                //                                          //      las primeras 8 de cada grupo tienen antes arriba un
                //                                          //      pequeño acento que las hace diferentes.
                //                                          //NO SE PORQUE AQUÍ NO SE VE, sin embargo al separarlas como
                //                                          //      char para forma sus t3fake aparecieron los acentos.
                "ǅǈǋǲᾈᾉᾊᾋᾌᾍᾎᾏᾘᾙᾚᾛᾜᾝᾞᾟᾨᾩᾪᾫᾬᾭᾮᾯᾼῌῼ"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.MODIFIER_LETTER, 
                "ʰʱʲʳʴʵʶʷʸʹʺʻʼʽʾʿˀˁˆˇˈˉˊˋˌˍˎˏːˑˠˡˢˣˤˬˮʹͺⁱⁿₐₑₒₓₔ"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.OTHER_LETTER, "ƻǀǁǂǃʔ"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.NON_SPACING_MARK, 
                //                                          //Se eliminarnos 7 caracteres que parecen tener el mismo
                //                                          //      tamaño que A, sin embargo se muestra hacía arriba.
                ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.SPACING_COMBINING_MARK, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.ENCLOSING_MARK, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.DECIMAL_DIGIT_NUMBER, "0123456789"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.LETTER_NUMBER, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.OTHER_NUMBER, 
                "²³¹¼½¾⁰⁴⁵⁶⁷⁸⁹₀₁₂₃₄₅₆₇₈₉⅓⅔⅕⅖⅗⅘⅙⅚⅛⅜⅝⅞①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳⓫⓬⓭⓮⓯⓰⓱⓲⓳⓴⓿❶❷❸❹❺❻❼❽❾❿"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.SPACE_SEPARATOR, "             "),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.LINE_SEPARATOR, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.PARAGRAPH_SEPARATOR, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.CONTROL, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.FORMAT, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.SURROGATE, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.PRIVATE_USE, ""),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.CONNECTOR_PUNCTUATION, "_"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.DASH_PUNCTUATION, "-‐‒–—―"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.OPEN_PUNCTUATION, "([{‚„⁽₍"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.CLOSE_PUNCTUATION, ")]}⁾₎"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.INITIAL_QUOTE_PUNCTUATION, "«‘‛“‟‹"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.FINAL_QUOTE_PUNCTUATION, "»’”›"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.OTHER_PUNCTUATION, 
                "!\"#%&'*,./:;?@\\¡·¿;·‖‗†‡•…‰′″‴‼‽‾⁃⁞"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.MATH_SYMBOL,
                "+<=>|~¬±×÷϶⁄⁺⁻⁼₊₋₌←↑→↓↔∂∆∏∑−∕∙√∞∟∩∫≈≠≡≤≥⌠⌡"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.CURRENCY_SYMBOL, "$¢£¤¥₠₡₢₣₤₥₦₧₨₩₫€₭₮₯₰₱₲₳₴₵₸₹₺"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.MODIFIER_SYMBOL,
                "^`¨¯´¸˂˃˄˅˒˓˔˕˖˗˘˙˚˛˜˝˞˟˪˫˭˯˰˱˲˳˴˵˶˷˸˹˺˻˼˽˾˿͵΄΅᾽᾿῀῁῍῎῏῝῞῟῭΅`´῾"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.OTHER_SYMBOL,
                "¦§©®°¶҂℅№℗™℮⅍↕↨⌂⌐─│┌┐└┘├┤┬┴┼═║╒╓╔╕╖╗╘╙╚╛╜╝╞╟╠╡╢╣╤╥╦╧╨╩╪╫╬▀▄█▌▐░▒▓■□▪▫▬▲▴▸►▼▾◂◄◊○◌●◘◙◦☺☻☼♀♂♠♣♥♦♪♫✶"),
            new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.OTHER_NOT_ASSIGNED, "₼₽₾"),
            };

        //                                                  //En fort consolas, estos caracteres se muestran IGUAL (o
        //                                                  //      muy parecidos) a otros.
        //                                                  //Esta información servira para que en strTo de proporcione
        //                                                  //      una buena descripción.
        public static readonly T3fakecharTuple[] arrt3fakecharFAKE = {
            //                                              //Uppercase Letter
            new T3fakecharTuple('Α', '\x0391', "Fake A(0x0041)"),
            new T3fakecharTuple('А', '\x0410', "Fake A(0x0041)"),
            new T3fakecharTuple('Ӑ', '\x04D0', "Fake Ă(0x0102)"),
            new T3fakecharTuple('Ӓ', '\x04D2', "Fake Ä(0x00C4)"),
            new T3fakecharTuple('Ᾰ', '\x1FB8', "Fake Ă(0x0102)"),
            new T3fakecharTuple('Ᾱ', '\x1FB9', "Fake Ā(0x0100)"),

            new T3fakecharTuple('Ε', '\x0395', "Fake E(0x0045)"),
            new T3fakecharTuple('Ѐ', '\x0400', "Fake È(0x00C8)"),
            new T3fakecharTuple('Ё', '\x0401', "Fake Ë(0x00CB)"),
            new T3fakecharTuple('Е', '\x0415', "Fake E(0x0045)"),
            new T3fakecharTuple('Ӗ', '\x04D6', "Fake Ĕ(0x0114)"),
            
            new T3fakecharTuple('Ι', '\x0399', "Fake I(0x0049)"),
            new T3fakecharTuple('Ϊ', '\x03AA', "Fake Ï(0x00CF)"),
            new T3fakecharTuple('І', '\x0406', "Fake I(0x0049)"),
            new T3fakecharTuple('Ї', '\x0407', "Fake Ï(0x00CF)"),
            new T3fakecharTuple('Ӏ', '\x04C0', "Fake I(0x0049)"),
            new T3fakecharTuple('Ῐ', '\x1FD8', "Fake Ĭ(0x012C)"),
            new T3fakecharTuple('Ῑ', '\x1FD9', "Fake Ī(0x012A)"),
            
            new T3fakecharTuple('Ο', '\x039F', "Fake O(0x004F)"),
            new T3fakecharTuple('О', '\x041E', "Fake O(0x004F)"),
            new T3fakecharTuple('Ӧ', '\x04E6', "Fake Ö(0x00D6)"),
            
            new T3fakecharTuple('Β', '\x0392', "Fake B(0x0042)"),
            new T3fakecharTuple('В', '\x0412', "Fake B(0x0042)"),

            new T3fakecharTuple('Ϲ', '\x03F9', "Fake C(0x0043)"),
            new T3fakecharTuple('С', '\x0421', "Fake C(0x0043)"),
            
            new T3fakecharTuple('Đ', '\x0110', "Fake Ð(0x00D0)"),
            new T3fakecharTuple('Ɖ', '\x0189', "Fake Ð(0x00D0)"),
            
            new T3fakecharTuple('Η', '\x0397', "Fake H(0x0048)"),
            new T3fakecharTuple('Н', '\x041D', "Fake H(0x0048)"),
            
            new T3fakecharTuple('Ј', '\x0408', "Fake J(0x004A)"),
            
            new T3fakecharTuple('Κ', '\x039A', "Fake K(0x004B)"),
            new T3fakecharTuple('К', '\x041A', "Fake K(0x004B)"),
            new T3fakecharTuple('Ḱ', '\x1E30', "Fake Ќ(0x040C)"),
            
            new T3fakecharTuple('Μ', '\x039C', "Fake M(0x004D)"),
            new T3fakecharTuple('М', '\x041C', "Fake M(0x004D)"),
            
            new T3fakecharTuple('Ν', '\x039D', "Fake N(0x004E)"),
            
            new T3fakecharTuple('Ρ', '\x03A1', "Fake P(0x0050)"),
            new T3fakecharTuple('Р', '\x0420', "Fake P(0x0050)"),
            
            new T3fakecharTuple('Ѕ', '\x0405', "Fake S(0x0053)"),
            
            new T3fakecharTuple('Τ', '\x03A4', "Fake T(0x0054)"),
            new T3fakecharTuple('Т', '\x0422', "Fake T(0x0054)"),
            
            new T3fakecharTuple('Χ', '\x03A7', "Fake X(0x0058)"),
            new T3fakecharTuple('Х', '\x0425', "Fake X(0x0058)"),
            
            new T3fakecharTuple('Υ', '\x03A5', "Fake Y(0x0059)"),
            new T3fakecharTuple('Ϋ', '\x03AB', "Fake Ÿ(0x0178)"),
            new T3fakecharTuple('Ү', '\x04AE', "Fake Y(0x0059)"),

            new T3fakecharTuple('Ζ', '\x0396', "Fake Z(0x005A)"),

            new T3fakecharTuple('Ȝ', '\x021C', "Fake Ʒ(0x01B7)"),
            new T3fakecharTuple('Λ', '\x039B', "Fake Ʌ(0x0245)"),
            new T3fakecharTuple('Σ', '\x03A3', "Fake Ʃ(0x01A9)"),
            new T3fakecharTuple('ϴ', '\x03F4', "Fake Ɵ(0x019F)"),
            new T3fakecharTuple('Ͻ', '\x03FD', "Fake Ɔ(0x0186)"),
            new T3fakecharTuple('П', '\x041F', "Fake Π(0x03A0)"),
            new T3fakecharTuple('Ѱ', '\x0470', "Fake Ψ(0x03A8)"),
            new T3fakecharTuple('Ѳ', '\x0472', "Fake Ɵ(0x019F)"),
            new T3fakecharTuple('Ә', '\x04D8', "Fake Ə(0x018F)"),
            new T3fakecharTuple('Ӡ', '\x04E0', "Fake Ʒ(0x01B7)"),
            new T3fakecharTuple('Ө', '\x04E8', "Fake Ɵ(0x019F)"),
            new T3fakecharTuple('Ԑ', '\x0510', "Fake Ɛ(0x0190)"),
            new T3fakecharTuple('Ω', '\x2126', "Fake Ω(0x03A9)"),
            new T3fakecharTuple('Ↄ', '\x2183', "Fake Ɔ(0x0186)"),
            //                                              //Lowercase Letter
            new T3fakecharTuple('а', '\x0430', "Fake a(0x0061)"),
            new T3fakecharTuple('ӓ', '\x04D3', "Fake ä(0x00E4)"),
            new T3fakecharTuple('ѐ', '\x0450', "Fake è(0x00E8)"),
            new T3fakecharTuple('ё', '\x0451', "Fake ë(0x00EB)"),
            new T3fakecharTuple('і', '\x0456', "Fake i(0x0069)"),
            new T3fakecharTuple('ϲ', '\x03F2', "Fake c(0x0063)"),
            new T3fakecharTuple('ϳ', '\x03F3', "Fake j(0x006A)"),
            //                                              //Space Separator
            new T3fakecharTuple(' ', '\x2000', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2001', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2002', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2003', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2004', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2005', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2006', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2007', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2008', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x2009', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x200A', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x202F', "Fake blank(0x0020)"),
            new T3fakecharTuple(' ', '\x205F', "Fake blank(0x0020)"),
            //                                              //Dash Punctuation
            new T3fakecharTuple('‐', '\x2010', "Fake -(0x002D)"),
            new T3fakecharTuple('–', '\x2013', "Fake ‒(0x2012)"),
            new T3fakecharTuple('―', '\x2015', "Fake —(0x2014)"),
            };

        //                                                  //The following set of characters do not print rigth (print 
        //                                                  //      ? in a box).
        public static readonly T2charDescriptionTuple[] arrt2charNONPRINTABLE =
        { 
            //                                              //Modifier Letter
            new T2charDescriptionTuple('ˆ', "Nonprintable, accent â"),
            new T2charDescriptionTuple('ˇ', "Nonprintable, accent ň"),
            new T2charDescriptionTuple('ˉ', "Nonprintable, accent ā"),
            //                                              //Initial Quote Punctuation
            new T2charDescriptionTuple('‘', "Nonprintable, open curved (')quote"),
            //                                              //Final Quote Punctuation
            new T2charDescriptionTuple('’', "Nonprintable, close curved (')quote"),
            new T2charDescriptionTuple('”', "Nonprintable, close curved (\")double quote"),
            //                                              //Modifier Symbol
            new T2charDescriptionTuple('¯', "Nonprintable, accent ā"),
            new T2charDescriptionTuple('¸', "Nonprintable, lower accent ņ"),
            new T2charDescriptionTuple('˘', "Nonprintable, accent ă"),
            new T2charDescriptionTuple('˙', "Nonprintable, accent ċ"),
            new T2charDescriptionTuple('˚', "Nonprintable, accent å"),
            new T2charDescriptionTuple('˛', "Nonprintable, lower accent ę"),
            new T2charDescriptionTuple('˜', "Nonprintable, accent ã"),
            new T2charDescriptionTuple('˝', "Nonprintable, accent ő"),
            new T2charDescriptionTuple('῁', "Nonprintable, accent ῧ"),
            new T2charDescriptionTuple('῭', "Nonprintable, accent ῢ"),
            new T2charDescriptionTuple('΅', "Nonprintable, accent ΰ"),
            new T2charDescriptionTuple('´', "Nonprintable, accent ά"),    
            //                                              //Other Punctuation
            new T2charDescriptionTuple('·', "Nonprintable, middle dot"),
        };

        //                                                  //Estos caracteres no deben existir en SIZE_A.
        public static readonly T2charDescriptionTuple[] arrt2charESCAPE =
        { 
            new T2charDescriptionTuple('\0', @"\0 Zero"),
            new T2charDescriptionTuple('\a', @"\a Bell (alert)"),
            new T2charDescriptionTuple('\b', @"\b Backspace"),
            new T2charDescriptionTuple('\f', @"\f Formfeed"),
            new T2charDescriptionTuple('\n', @"\n New Line"),
            new T2charDescriptionTuple('\r', @"\r Carriage Return"),
            new T2charDescriptionTuple('\t', @"\t Horizontal Tab"),
            new T2charDescriptionTuple('\v', @"\v Vertical Tab"),
        };

        //                                                  //Se incluyen el blank (al principio) y todos los caracteres
        //                                                  //      de la estructura arrt2uccCHAR_USEFUL_IN_TEXT, en el
        //                                                  //      mismo orden en que están.
        //                                                  //Al iniciar (antes de ejecutar el constructor estático)
        //                                                  //      debe contar con información mínima para ser
        //                                                  //      utilizada en los strTo que se usen antes de formar
        //                                                  //      estos 3 objetos en forma definitiva.
        public static readonly String strCHAR_USEFUL_IN_TEXT = " " + "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" + "()[]{}";

        //                                                  //blank y todos los caracteres de la estructura
        //                                                  //      arrt2uccCHAR_USEFUL_IN_TEXT ordenados.
        //                                                  //Esta información, en forma provisional, debe ser preparada
        //                                                  //      con lo anterior AL INICIAR el constructor estático.
        public static readonly char[] arrcharUSEFUL_IN_TEXT;

        //                                                  //Caracteres de los cuales no de desea mostrar
        //                                                  //      adicionalmente su hexadecimal.
        //                                                  //Inicialmente serán los mismos que están en
        //                                                  //      USEFUL_IN_TEXT, sin embargo, en el futuro este
        //                                                  //      podrá ser un conjunto reducido.
        public static readonly char[] arrcharDO_NOT_SHOW_HEX;

        //                                                  //En la siguiente estructura se incluyen:
        //                                                  //1. Nonprintable.
        //                                                  //2. Fake, description "'\x????', Fake ?(x????)".
        //                                                  //3. Escape.
        public static readonly T2charDescriptionTuple[] arrt2charDESCRIPTION;

        //                                                  //ESTA PENDIENTE ANALIZAR QUE CARACTERES DE LOS 256x256 QUE
        //                                                  //      SON POSIBLES, SON VISIBLES.

        //                                                  //Cantidad máxima de caracteres diagnosticados que se
        //                                                  //      mostrarán al final de un string
        private const int intMAX_DIAGNOSTIC = 100;

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC VARIABLES*/

        //                                                  //Object previously processed in other strTo execution.
        private static List<Object> lstobjPreviouslyProcessed;

        //--------------------------------------------------------------------------------------------------------------
        /*SUPPORT METHODS FOR STATIC CONSTRUCTORS*/

        //--------------------------------------------------------------------------------------------------------------
        private static void subPrepareConstantsForStrTo(
            //                                              //Método de apoyo llamado en constructor estático. 
            //                                              //Prepara las constantes para poder utilizarlas.
            //                                              //1. Inicializa proceso para evitar desplegar 2 veces el
            //                                              //      mismo objeto
            //                                              //2. arrt2uccCHAR_USEFUL_IN_TEXT:
            //                                              //2a. Este ordenada por ucc, sin duplicados.
            //                                              //2b. Dentro de cada ucc, este ordenada por la secuencia del
            //                                              //      caracter, sin duplicados
            //                                              //2c. Todos sean <= al caracter xD7FF.
            //                                              //2d. En forma global, no haya caracteres duplicados.
            //                                              //3. arrt3fakecharFAKE:
            //                                              //3a. ordenar.
            //                                              //3b. no duplicados
            //                                              //3c. charFAKE debe estar en USEFUL.
            //                                              //3d. charHEX y charFAKE debe ser el mismo.
            //                                              //3e. strDESCRIPTION "..... ?(0x????)", el x???? debe ser la
            //                                              //      correspondiente al caracter ?.
            //                                              //4. arrt2charNONPRINTABLE.
            //                                              //4a. ordenar.
            //                                              //4b. no duplicados
            //                                              //4c. debe estar en USEFUL.
            //                                              //4d. tener descripción.
            //                                              //5. arrt2charESCAPE.
            //                                              //5a. ordenar.
            //                                              //5b. no duplicados
            //                                              //5c. NO debe estar en USEFUL.
            //                                              //5d. tener descripción.

            //                                              //Recibe caracteres básicos (por si hay un strTo antes de
            //                                              //      formar el resultado deseado).
            //                                              //Regresa, blank + caracteres en USEFUL en el orden que
            //                                              //      están.
            out String strCHAR_USEFUL_IN_TEXT_O,
            //                                              //Caracteres anteriores ordenados.
            out char[] arrcharUSEFUL_IN_TEXT_O,
            out char[] arrcharDO_NOT_SHOW_HEX_O,
            //                                              //Se incluyen:
            //                                              //1. Nonprintable.
            //                                              //2. Fake, description "'\x????', Fake ?(x????)".
            //                                              //3. Escape.
            //                                              //Al juntarse los 3 conjuntos de descripciones no deben
            //                                              //      resultar duplicados.
            out T2charDescriptionTuple[] arrt2charDESCRIPTION_O
            )
        {
            Test.subInitializeLstobjPreviouslyProcessed();

            Test.subPrepareConstantsArrt2uccCharUsefulInText(out strCHAR_USEFUL_IN_TEXT_O,
                out arrcharUSEFUL_IN_TEXT_O);

            arrcharDO_NOT_SHOW_HEX_O = arrcharUSEFUL_IN_TEXT_O;

            Test.subPrepareConstantsArrt3fakecharFake();
            Test.subPrepareConstantsArrt2charNonprintable();
            Test.subPrepareConstantsArrt2charEscape();

            Test.subPrepareConstantsArrt2charDescription(out arrt2charDESCRIPTION_O);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subPrepareConstantsArrt2uccCharUsefulInText(
            //                                              //2. arrt2uccCHAR_USEFUL_IN_TEXT:
            //                                              //2a. Este ordenada por ucc, sin duplicados.
            //                                              //2b. Dentro de cada ucc, este ordenada por la secuencia del
            //                                              //      caracter, sin duplicados
            //                                              //2c. Todos sean <= al caracter xD7FF.
            //                                              //2d. En forma global, no haya caracteres duplicados.

            //                                              //blank + caracteres en USEFUL en el orden que están.
            out String strCHAR_USEFUL_IN_TEXT_O,
            //                                              //Caracteres anteriores ordenados.
            out char[] arrcharUSEFUL_IN_TEXT_O
            )
        {
            //                                              //To easy code
            T2uccCategoryAndCharsTuple[] arrt2ucc = Test.arrt2uccCHAR_USEFUL_IN_TEXT;

            //                                              //Verifica secuencia de cada ucc
            for (int intUcc = 1; intUcc < arrt2ucc.Length; intUcc = intUcc + 1)
            {
                if (
                    //                                      //No estan en orden ascendente
                    arrt2ucc[intUcc - 1].ucc.CompareTo(arrt2ucc[intUcc].ucc) >= 0
                    )
                    Test.subAbort(Test.strTo(arrt2ucc[intUcc - 1].ucc, "arrt2ucc[" + (intUcc - 1) + "].ucc") + ", " +
                        Test.strTo(arrt2ucc[intUcc].ucc, "arrt2ucc[" + intUcc + "].ucc") + 
                        " should be in ascending order");
            }

            //                                              //Verifica chars en cada ucc
            for (int intUcc = 0; intUcc < arrt2ucc.Length; intUcc = intUcc + 1)
            {
                String strChars = arrt2ucc[intUcc].strChars;

                //                                          //Verifica orden
                for (int intC = 1; intC < strChars.Length; intC = intC + 1)
                {
                    if (
                        //                                  //No estan en orden ascendente
                        strChars[intC - 1] >= strChars[intC]
                        )
                        Test.subAbort(Test.strTo(strChars, "arrt2ucc[" + intUcc + "].strChars") + ", " +
                            Test.strTo(strChars[intC - 1], "strChars[" + (intC - 1) + "]") + ", " +
                            Test.strTo((int)strChars[intC - 1], "(int)strChars[" + (intC - 1) + "]") + ", " +
                            Test.strTo(strChars[intC], "strChars[" + intC + "]") + ", " +
                            Test.strTo((int)strChars[intC], "(int)strChars[" + intC + "]") +
                            " should be in ascending order");
                }
            }

            //                                              //blank + junta todos los caracters
            strCHAR_USEFUL_IN_TEXT_O = " ";
            for (int intUcc = 0; intUcc < arrt2uccCHAR_USEFUL_IN_TEXT.Length; intUcc = intUcc + 1)
            {
                strCHAR_USEFUL_IN_TEXT_O = strCHAR_USEFUL_IN_TEXT_O + arrt2ucc[intUcc].strChars;
            }

            //                                              //Verifica que forma global no estén duplicados
            arrcharUSEFUL_IN_TEXT_O = strCHAR_USEFUL_IN_TEXT_O.ToCharArray();
            Array.Sort(arrcharUSEFUL_IN_TEXT_O);
            Tools.subVerifyDuplicate(arrcharUSEFUL_IN_TEXT_O, "arrcharUSEFUL_IN_TEXT_O");
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subPrepareConstantsArrt3fakecharFake(
            //                                              //3. arrt3fakecharFAKE:
            //                                              //3a. ordenar.
            //                                              //3b. no duplicados
            //                                              //3c. charFAKE debe estar en USEFUL.
            //                                              //3d. charHEX y charFAKE debe ser el mismo.
            //                                              //3e. strDESCRIPTION debe ser "Fake blank(0x0020" o 
            //                                              //      "Fake ?(0x????)" donde ? y ???? representan el 
            //                                              //      mismo caracter.
            )
        {
            Array.Sort(Test.arrt3fakecharFAKE);

            //                                              //Verifica no duplicados
            Tools.subVerifyDuplicate(Test.arrt3fakecharFAKE, "Test.arrt3fakecharFAKE");

            //                                              //Verifica chars en tupla
            for (int intT3 = 0; intT3 < arrt3fakecharFAKE.Length; intT3 = intT3 + 1)
            {
                if (
                    Array.BinarySearch(Test.arrcharUSEFUL_IN_TEXT, Test.arrt3fakecharFAKE[intT3].charFAKE) < 0
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt3fakecharFAKE[intT3].charFAKE,
                            "Test.arrt3fakecharFAKE[" + intT3 + "].charFAKE") +
                        " do not exist in USEFUL_IN_TEXT");

                if (
                    Test.arrt3fakecharFAKE[intT3].charFAKE != Test.arrt3fakecharFAKE[intT3].charHEX
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt3fakecharFAKE[intT3].charFAKE,
                            "Test.arrt3fakecharFAKE[" + intT3 + "].charFAKE") +
                        ", " +
                        Test.strTo(Test.arrt3fakecharFAKE[intT3].charHEX,
                            "Test.arrt3fakecharFAKE[" + intT3 + "].charHEX") +
                        " should be equal");

                //                                          //Verifica descripción

                //                                          //To easy code
                String strDESCRIPTION = Test.arrt3fakecharFAKE[intT3].strDESCRIPTION;

                if (
                    strDESCRIPTION == null
                    )
                    Test.subAbort(
                        Test.strTo(strDESCRIPTION, "arrt3fakecharFAKE[" + intT3 + "].strDESCRIPTION") +
                        " can not be null");

                /*CASE*/
                if (
                    strDESCRIPTION == "Fake blank(0x0020)"
                    )
                {
                    //                                      //Es una opción correcta, NO HACE NADA
                }
                else if (
                    //                                      //Tiene la forma correcta
                    (strDESCRIPTION.Length == "Fake ?(0x????)".Length) &&
                    strDESCRIPTION.StartsWith("Fake ") &&
                    (strDESCRIPTION.Substring("Fake ?".Length, "(0x".Length) == "(0x") &&
                    (strDESCRIPTION[strDESCRIPTION.Length - 1] == ')')
                    )
                {
                    //                                      //Verifica descripción
                    char charFaked = strDESCRIPTION["Fake ".Length];
                    String strCharFaked = String.Format("{0:X4}", (int)charFaked);
                    String strFakedHex = strDESCRIPTION.Substring("Fake ?(0x".Length, 4);
                    if (
                        //                                  //? y ???? no representan el mismo caracter
                        strCharFaked != strFakedHex
                        )
                        Test.subAbort(Test.strTo(charFaked, "charFaked") + ", " +
                            Test.strTo(strCharFaked, "strCharFaked") + ", " +
                            Test.strTo(strDESCRIPTION, "Test.arrt3fakecharFAKE[" + intT3 + "].strDESCRIPTION") +
                            " should include hexadecimal char code");

                    //                                      //Es una opción correcta, NO HACE NADA
                }
                else
                {
                    Test.subAbort(Test.strTo(strDESCRIPTION, "arrt3fakecharFAKE[" + intT3 + "].strDESCRIPTION") +
                        " invalid description");
                }
                /*END-CASE*/
            }
        }
            
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subPrepareConstantsArrt2charNonprintable(
            //                                              //4. arrt2charNONPRINTABLE.
            //                                              //4a. ordenar.
            //                                              //4b. no duplicados
            //                                              //4c. debe estar en USEFUL.
            //                                              //4d. tener descripción.
            )
        {
            Array.Sort(Test.arrt2charNONPRINTABLE);

            //                                              //Verifica no duplicados
            Tools.subVerifyDuplicate(Test.arrt2charNONPRINTABLE, "Test.arrt2charNONPRINTABLE");

            //                                              //Verifica chars en tupla
            for (int intT2 = 0; intT2 < arrt2charNONPRINTABLE.Length; intT2 = intT2 + 1)
            {

                if (
                    Array.BinarySearch(Test.arrcharUSEFUL_IN_TEXT, Test.arrt2charNONPRINTABLE[intT2].charX) < 0
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt2charNONPRINTABLE[intT2].charX,
                            "Test.arrt2charNONPRINTABLE[" + intT2 + "].charX") +
                        " does not exist in USEFUL_IN_TEXT");

                if (
                    Test.arrt2charNONPRINTABLE[intT2].strDESCRIPTION == null
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt2charNONPRINTABLE[intT2].strDESCRIPTION,
                            "Test.arrt2charNONPRINTABLE[" + intT2 + "].strDESCRIPTION") + 
                        " can not be null");

                if (
                    Test.arrt2charNONPRINTABLE[intT2].strDESCRIPTION == ""
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt2charNONPRINTABLE[intT2].strDESCRIPTION,
                            "Test.arrt2charNONPRINTABLE[" + intT2 + "].strDESCRIPTION") +
                        " should have a description");
            }
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subPrepareConstantsArrt2charEscape(
            //                                              //5. arrt2charESCAPE.
            //                                              //5a. ordenar.
            //                                              //5b. no duplicados
            //                                              //5c. NO debe estar en USEFUL.
            //                                              //5d. tener descripción.
            )
        {
            Array.Sort(Test.arrt2charESCAPE);

            //                                              //Verifica no duplicados
            Tools.subVerifyDuplicate(Test.arrt2charESCAPE, "Test.arrt2charESCAPE");

            //                                              //Verifica chars en tupla
            for (int intT2 = 0; intT2 < arrt2charESCAPE.Length; intT2 = intT2 + 1)
            {
                if (
                    Array.BinarySearch(Test.arrcharUSEFUL_IN_TEXT, Test.arrt2charESCAPE[intT2].charX) >= 0
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt2charESCAPE[intT2].charX, "Test.arrt2charESCAPE[" + intT2 + "].charX") +
                        " exists in USEFUL_IN_TEXT");

                if (
                    Test.arrt2charESCAPE[intT2].strDESCRIPTION == null
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt2charESCAPE[intT2].strDESCRIPTION,
                            "Test.arrt2charESCAPE[" + intT2 + "].strDESCRIPTION") +
                        " can not be null");

                if (
                    Test.arrt2charESCAPE[intT2].strDESCRIPTION == ""
                    )
                    Test.subAbort(
                        Test.strTo(Test.arrt2charESCAPE[intT2].strDESCRIPTION,
                            "Test.arrt2charESCAPE[" + intT2 + "].strDESCRIPTION") +
                        " should have a description");
            }
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subPrepareConstantsArrt2charDescription(
            //                                              //Se incluyen:
            //                                              //1. Nonprintable.
            //                                              //2. Fake, description "'\x????', Fake ?(x????)".
            //                                              //3. Escape.
            //                                              //Al juntarse los 3 conjuntos de descripciones no deben
            //                                              //      resultar duplicados.
            out T2charDescriptionTuple[] arrt2charDESCRIPTION_O
            )
        {
            arrt2charDESCRIPTION_O = new T2charDescriptionTuple[Test.arrt2charNONPRINTABLE.Length +
                Test.arrt3fakecharFAKE.Length + Test.arrt2charESCAPE.Length];

            Array.Copy(Test.arrt2charNONPRINTABLE, 0, arrt2charDESCRIPTION_O, 0, Test.arrt2charNONPRINTABLE.Length);

            //                                              //Desplazamiento para mover arrt3
            int intDesp = Test.arrt2charNONPRINTABLE.Length;

            for (int intT4 = 0; intT4 < Test.arrt3fakecharFAKE.Length; intT4 = intT4 + 1)
            {
                arrt2charDESCRIPTION_O[intDesp + intT4] = new T2charDescriptionTuple(
                    Test.arrt3fakecharFAKE[intT4].charFAKE, Test.arrt3fakecharFAKE[intT4].strDESCRIPTION);
            }

            Array.Copy(Test.arrt2charESCAPE, 0, arrt2charDESCRIPTION_O,
                Test.arrt2charNONPRINTABLE.Length + Test.arrt3fakecharFAKE.Length, Test.arrt2charESCAPE.Length);

            Array.Sort(arrt2charDESCRIPTION_O);

            Tools.subVerifyDuplicate(arrt2charDESCRIPTION_O, "arrt2charDESCRIPTION_O");
        }

        //--------------------------------------------------------------------------------------------------------------
        /*SHARED METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        public static void subInitializeLstobjPreviouslyProcessed(
            //                                              //Reset list of previously porcessed
            )
        {
            Test.lstobjPreviouslyProcessed = new List<Object>();
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.strTo Set of Methods to Display Object Info*/
        //--------------------------------------------------------------------------------------------------------------
        public static String strTo(
            //                                              //Prepare for SHORT display.
            //                                              //The strategy is:
            //                                              //1. 2 strTo methods (this and next with obj_I parameter)
            //                                              //      will handle all types except generic containing
            //                                              //      bclass, btuple, enum or sysexcep.
            //                                              //2. 2 strTo methods with 3 paramenters will handle 1
            //                                              //      argument generic containing bclass, btuple, enum or
            //                                              //      excep.
            //                                              //3. 2 strTo methods with 4 paramenters will handle
            //                                              //      dicbclass, dicbtuple and dicenun.
            //                                              //4. 2 strTo methods with 4 paramenters will handle
            //                                              //      kvpbclass, kvpbtuple and kvpenun.
            //                                              //5a. Each one of the pair of strTo methods call a
            //                                              //      strToSupportXxxxx private method (4 methods) to
            //                                              //      handle most checks needed, process null values and
            //                                              //      call strToSharedYyyyy private methods to generate
            //                                              //      the information requested.
            //                                              //5b. "primitives" are not easy to process (they require an
            //                                              //      specific method for each one), to solve this
            //                                              //      problem, "primitives" will be boxed using Oint,
            //                                              //      Olong, ... boxing clases, this will be done in the
            //                                              //      strToSupportAnyType method.

            //                                              //str, info to display

            //                                              //Read strToSupportAnyType method for paramenters
            //                                              //      description
            Object obj_I,
            StrtoEnum strtoSHORT_I
            )
        {
            if (
                strtoSHORT_I != StrtoEnum.SHORT
                )
                Test.subAbort(Test.strTo(strtoSHORT_I, "strtoSHORT_I") + " should be SHORT");

            return Test.strToSupportAnyType(obj_I, strtoSHORT_I, null);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        public static String strTo(
            //                                              //Prepare for FULL display.

            //                                              //str, info to display

            //                                              //Read strToSupportAnyType method for paramenters
            //                                              //      description
            Object obj_I,
            String strText_I
            )
        {
            if (
                strText_I == null
                )
                Test.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");

            return Test.strToSupportAnyType(obj_I, StrtoEnum.FULL, strText_I);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strToSupportAnyType(
            //                                              //Continue preparation for display.

            //                                              //str, info to display

            //                                              //Any standard type except generic types containing bclass,
            //                                              //      btuple or enum types (those require a transformation
            //                                              //      before calling strTo method with 3 or 4 paramenters)
            Object obj_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of the object.
            String strText_I
            )
        {
            String strToSupportAnyType;
            if (
                obj_I == null
                )
            {
                if (
                    strtoOption_I == StrtoEnum.SHORT
                    )
                {
                    strToSupportAnyType = "null";
                }
                else
                {
                    strToSupportAnyType = strText_I + "(null)";
                }
            }
            else
            {
                //                                          //Abort if not a valid type
                Test.subVerifyAnyType(obj_I);

                //                                          //Do the boxing.
                Object objMain;
                Object objKey;
                Test.subfunConvertAndBox(out objMain, out objKey, obj_I);

                //                                          //Call required strToSharedYyyyy
                Type typeObj = obj_I.GetType();
                /*CASE*/
                if (
                    typeObj.IsArray
                    )
                {
                    //                                      //Is arrobj[], arrobj[,] or arrobj[, ,].
                    //                                      //All contents are boxed primitives and system types,
                    //                                      //      bclass, btuple, enum or sysexcep

                    int intRank = typeObj.GetArrayRank();
                    /*CASE*/
                    if (
                        intRank == 1
                        )
                    {
                        //                                  //Is arrobj[], call with 3 paramenters
                        strToSupportAnyType = Test.strFormatArrOrOneArgumentGeneric((Object[])objMain,
                            strtoOption_I, strText_I, obj_I);
                    }
                    else if (
                        intRank == 2
                        )
                    {
                        //                                  //Is arrobj[,]
                        strToSupportAnyType = Test.strFormatArr2Main((Object[,])objMain,
                            strtoOption_I, strText_I, obj_I);
                    }
                    else
                    {
                        //                                  //Is arrobj[, ,]
                        strToSupportAnyType = Test.strFormatArr3Main((Object[, ,])objMain,
                            strtoOption_I, strText_I, obj_I);
                    }
                    /*END-CASE*/
                }
                else if (
                    typeObj.IsGenericType
                    )
                {
                    //                                      //Is 1 or 2 arguments.
                    //                                      //All contents are boxed primitives and system types

                    if (
                        //                                  //Is List<Object>, ...
                        typeObj.Name.EndsWith("`1")
                        )
                    {
                        //                                  //lstobj, ... were converted to arrobj
                        strToSupportAnyType = Test.strFormatArrOrOneArgumentGeneric(
                            (Object[])objMain, strtoOption_I, strText_I, obj_I);
                    }
                    else
                    {
                        //                                  //Is Dictionary<String, Object> or
                        //                                  //      KeyValuePair<String,_Object>

                        if (
                            typeObj.Name == Test.strGENERIC_DICTIONARY_TYPE
                            )
                        {
                            //                              //dicobj was converted to arrstr and arrobj
                            strToSupportAnyType = Test.strFormatDicMain(
                                (Object[])objMain, (String[])objKey, strtoOption_I, strText_I,
                                obj_I);
                        }
                        else
                        {
                            //                              //kvpobj was converted to str and obj
                            strToSupportAnyType = Test.strFormatKvpMain(objMain, (String)objKey, strtoOption_I,
                                strText_I, obj_I);
                        }
                    }
                }
                else
                {
                    //                                      //Is single type
                    strToSupportAnyType = Test.strFormatSingle(objMain, strtoOption_I, strText_I);
                }
                /*END-CASE*/
            }

            return strToSupportAnyType;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subVerifyAnyType(
            //                                              //Verify it is standard type except a generic type
            //                                              //      containing bclass, btuple, benum or sysexcep type

            //                                              //Read strToSupportAnyType method for paramenters
            //                                              //      description
            Object obj_I
            )
        {
            Type typeObj = obj_I.GetType();
            if (
                !Test.boolIsStandard(typeObj, false)
                )
                Test.subAbort(Test.strTo(typeObj, "obj_I.GetType") + " is not an standard type");

            //                                              //If generic type, other verifications are required
            if (
                typeObj.IsGenericType
                )
            {
                //                                          //More verifications are needed

                //                                          //Get argument type contained
                Type[] arrtypeArgument = typeObj.GetGenericArguments();
                Type typeArgument = arrtypeArgument[arrtypeArgument.Length - 1];

                if (
                    //                                      //Is bclass
                    typeArgument == typeof(BclassBaseClassAbstract) ||
                        typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
                    //                                      //Is btuple
                    typeArgument == typeof(BtupleBaseTupleAbstract) ||
                        typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
                    //                                      //Is benum
                    typeArgument == typeof(BenumBaseEnumAbstract) ||
                        typeArgument.IsSubclassOf(typeof(BenumBaseEnumAbstract)) ||
                    //                                      //Is Exception
                    typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
                    )
                    Test.subAbort(Test.strTo(typeObj, "obj_I.GetType") +
                        " generic types containing bclass, btuple, enum or sysexcep are not valid in ths method, " +
                        "a 3 or 4 paramenters strTo method should be called instead");
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        public static String strTo(
            //                                              //Prepare for SHORT display.

            //                                              //str, info to display

            //                                              //Read strToSupportOneArgumentGeneric method for paramenters
            //                                              //      description
            Object[] arrobj_I,
            StrtoEnum strtoSHORT_I,
            //                                              //lstobjXxxxxx, queueobjXxxxxx or stackobjXxxxxx.
            Object objOneArgumentGeneric_I
            )
        {
            if (
                strtoSHORT_I != StrtoEnum.SHORT
                )
                Test.subAbort(Test.strTo(strtoSHORT_I, "strtoSHORT_I") + " should be SHORT");

            return Test.strToSupportOneArgumentGeneric(arrobj_I, strtoSHORT_I, null, objOneArgumentGeneric_I);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        public static String strTo(
            //                                              //Prepare for FULL display.

            //                                              //str, info to display

            //                                              //Read strToSupportOneArgumentGeneric method for paramenters
            //                                              //      description
            Object[] arrobj_I,
            String strText_I,
            //                                              //lstobjXxxxxx, queueobjXxxxxx or stackobjXxxxxx.
            Object objOneArgumentGeneric_I
            )
        {
            if (
                strText_I == null
                )
                Test.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");

            return Test.strToSupportOneArgumentGeneric(arrobj_I, StrtoEnum.FULL, strText_I,
                objOneArgumentGeneric_I);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strToSupportOneArgumentGeneric(
            //                                              //Continue preparation for display.

            //                                              //str, info to display

            //                                              //arrstr, arrbclass, arrbtuple or arrenum.
            Object[] arrobj_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of the one argument generic.
            String strText_I,
            //                                              //lstbclass, lstbtuple or lstenum, queuebclass, ...
            //                                              //Main should contain str or the type (or subtype)
            //                                              //      contained in one argument generic.
            Object objOneArgumentGeneric_I
            )
        {
            String strToSupportOneArgumentGeneric;
            if (
                objOneArgumentGeneric_I == null
                )
            {
                if (
                    strtoOption_I == StrtoEnum.SHORT
                    )
                {
                    strToSupportOneArgumentGeneric = "null";
                }
                else
                {
                    strToSupportOneArgumentGeneric = strText_I + "(null)";
                }
            }
            else
            {
                //                                          //Abort if both parameters are not consistent.
                Test.subVerifyOneArgumentGeneric(arrobj_I, objOneArgumentGeneric_I);

                strToSupportOneArgumentGeneric = Test.strFormatArrOrOneArgumentGeneric(arrobj_I, strtoOption_I,
                    strText_I, objOneArgumentGeneric_I);
            }

            return strToSupportOneArgumentGeneric;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subVerifyOneArgumentGeneric(
            //                                              //Verify it is a standard one argument generic type
            //                                              //      containing bclass, btuple, enum or sysexcep.

            //                                              //Read strToSupportOneArgumentGeneric method for paramenters
            //                                              //      description
            Object[] arrobj_I,
            Object objOneArgumentGeneric_I
            )
        {
            Type typeOneArgumentGeneric = objOneArgumentGeneric_I.GetType();
            //                                              //The generic should be List, ... (any standard 1
            //                                              //      argument generic type).
            if (!(
                typeOneArgumentGeneric.IsGenericType &&
                typeOneArgumentGeneric.Name.EndsWith("`1")
                ))
                Test.subAbort(Test.strTo(typeOneArgumentGeneric, "objOneArgumentGeneric_I.GetType") +
                    " should be standard one argument generic type");

            Type typeArgument = typeOneArgumentGeneric.GetGenericArguments()[0];
            if (!(
                //                                          //Is bclass
                typeArgument == typeof(BclassBaseClassAbstract) ||
                    typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
                //                                          //Is btuple
                typeArgument == typeof(BtupleBaseTupleAbstract) ||
                    typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
                //                                          //Is benum
                typeArgument == typeof(BenumBaseEnumAbstract) || 
                    typeArgument.IsSubclassOf(typeof(BenumBaseEnumAbstract)) ||
                //                                          //Is Exception
                    typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
                ))
                Test.subAbort(Test.strTo(typeOneArgumentGeneric, "objOneArgumentGeneric_I.GetType") +
                    " should be generic type containing bclass, btuple, enum or sysexcep");

            if (
                arrobj_I == null
                )
                Test.subAbort(Test.strTo(arrobj_I, "arrobj_I") + " should have a value");

            Type typeElement = arrobj_I.GetType().GetElementType();
            if (!(
                //                                          //Array and generic are compatible
                (typeElement == typeof(String)) || (typeElement == typeArgument)
                ))
                Test.subAbort(Test.strTo(typeElement, "arrobj_I.GetType.GetElementType") + ", " +
                    Test.strTo(typeArgument, "objOneArgumentGeneric_I.GetType.GetGenericArguments[0]") + ", " +
                    " array and collection are not compatible");
        }

        //--------------------------------------------------------------------------------------------------------------
        public static String strTo(
            //                                              //Prepare for SHORT display.

            //                                              //str, info to display

            //                                              //Read strToSupportDic method for paramenters description
            //                                              //arrobjXxxxxxDicTo, dictionary content in array format.
            Object[] arrobjValue_I,
            //                                              //arrstrKeyXxxxxxDicTo, dictionary keys.
            String[] arrstrKey_I,
            StrtoEnum strtoSHORT_I,
            //                                              //dicobjXxxxxx.
            Object objDicobj_I
            )
        {
            if (
                strtoSHORT_I != StrtoEnum.SHORT
                )
                Test.subAbort(Test.strTo(strtoSHORT_I, "strtoSHORT_I") + " should be SHORT");

            return Test.strToSupportDic(arrobjValue_I, arrstrKey_I, strtoSHORT_I, null, objDicobj_I);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        public static String strTo(
            //                                              //Prepare for FULL display.

            //                                              //str, info to display

            //                                              //Read strToSupportDic method for paramenters description
            //                                              //arrobjXxxxxxDicTo, dictionary content in array format.
            Object[] arrobjValue_I,
            //                                              //arrstrKeyXxxxxxDicTo, dictionary keys.
            String[] arrstrKey_I,
            String strText_I,
            //                                              //dicobjXxxxxx.
            Object objDicobj_I
            )
        {
            if (
                strText_I == null
                )
                Test.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");

            return Test.strToSupportDic(arrobjValue_I, arrstrKey_I, StrtoEnum.FULL, strText_I, objDicobj_I);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strToSupportDic(
            //                                              //Continue preparation for display.

            //                                              //str, info to display

            //                                              //arrstr, arrbclass, arrbtuple or arrenum
            Object[] arrobjValue_I,
            //                                              //dic.Keys
            String[] arrstrKey_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of the dic.
            String strText_I,
            //                                              //dicbclass, dicbtuple or dicenum.
            //                                              //Value should contain str or be the same type (or subtype)
            //                                              //      contained in dic.
            Object objDicobj_I
            )
        {
            String strToSupportDic;
            if (
                objDicobj_I == null
                )
            {
                strToSupportDic = "null";
                if (
                    strtoOption_I == StrtoEnum.SHORT
                    )
                {
                    strToSupportDic = "null";
                }
                else
                {
                    strToSupportDic = strText_I + "(null)";
                }
            }
            else
            {
                //                                          //Abort if not a valid dic
                Test.subVerifyDic(arrobjValue_I, arrstrKey_I, objDicobj_I);

                strToSupportDic = Test.strFormatDicMain(arrobjValue_I, arrstrKey_I, strtoOption_I, strText_I,
                    objDicobj_I);
            }

            return strToSupportDic;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subVerifyDic(
            //                                              //Verify it is a valid dic.

            //                                              //Read strToSupportDic method for paramenters description
            Object[] arrobjValue_I,
            String[] arrstrKey_I,
            Object objDicobj_I
            )
        {
            Type typeDicobj = objDicobj_I.GetType();
            if (
                typeDicobj.Name != Test.strGENERIC_DICTIONARY_TYPE
                )
                Test.subAbort(Test.strTo(typeDicobj, "objDicobj_I.GetType") + " should be dictionary");

            Type typeArgument = typeDicobj.GetGenericArguments()[1];
            if (!(
                //                                          //Is bclass
                typeArgument == typeof(BclassBaseClassAbstract) ||
                    typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
                //                                          //Is btuple
                typeArgument == typeof(BtupleBaseTupleAbstract) ||
                    typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
                //                                          //Is benum
                typeArgument == typeof(BenumBaseEnumAbstract) || 
                    typeArgument.IsSubclassOf(typeof(BenumBaseEnumAbstract)) ||
                //                                          //Is Exception
                    typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
                ))
                Test.subAbort(Test.strTo(typeDicobj, "typeDicobj.GetType") +
                    " should be dictionary type containing bclass, btuple, enum or sysexcep");

            if (
                arrstrKey_I == null
                )
                Test.subAbort(Test.strTo(arrstrKey_I, "arrstrKey_I") + " should have a value");

            if (
                arrobjValue_I == null
                )
                Test.subAbort(Test.strTo(arrobjValue_I, "arrobjValue_I") + " should have a value");

            //                                              //Array should contains str or the same type of Directory
            Type typeValueElement = arrobjValue_I.GetType().GetElementType();
            if (!(
                (typeValueElement == typeof(String)) || (typeValueElement == typeArgument)
                ))
                Test.subAbort(Test.strTo(arrobjValue_I.GetType(), "arrobjValue_I.GetType") + ", " +
                    Test.strTo(typeDicobj, "objDicobj_I.GetType") + " array and dictionary are not compatible");

            if (
                arrstrKey_I.Length != arrobjValue_I.Length
                )
                Test.subAbort(Test.strTo(Test.strGetObjId(arrstrKey_I), "arrstrKey_I.strGetObjId") + ", " +
                    Test.strTo(Test.strGetObjId(arrobjValue_I), "arrobjValue_I.strGetObjId") +
                    " both arrays should be the same size");
        }

        //--------------------------------------------------------------------------------------------------------------
        public static String strTo(
            //                                              //Prepare for SHORT display.

            //                                              //str, info to display

            //                                              //Read strToSupportKey method for paramenters description
            Object objValue_I,
            String strKey_I,
            StrtoEnum strtoSHORT_I,
            Object objKvpobj_I
            )
        {
            if (
                strtoSHORT_I != StrtoEnum.SHORT
                )
                Test.subAbort(Test.strTo(strtoSHORT_I, "strtoSHORT_I") + " should be SHORT");

            return Test.strToSupportKvp(objValue_I, strKey_I, strtoSHORT_I, null, objKvpobj_I);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        public static String strTo(
            //                                              //Prepare for FULL display.

            //                                              //str, info to display

            //                                              //Read strToSupportKvp method for paramenters description
            Object objValue_I,
            String strKey_I,
            String strText_I,
            Object objKvpobj_I
            )
        {
            if (
                strText_I == null
                )
                Test.subAbort(Test.strTo(strText_I, "strText_I") + " should have a value");

            return Test.strToSupportKvp(objValue_I, strKey_I, StrtoEnum.FULL, strText_I, objKvpobj_I);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strToSupportKvp(
            //                                              //Continue preparation for display.

            //                                              //str, info to display

            //                                              //kvp.Value (can be null)
            Object objValue_I,
            //                                              //kvp.Key
            String strKey_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of the kvp.
            String strText_I,
            //                                              //kvpbclass, kvpbtuple or kvpenum.
            Object objKvpobj_I
            )
        {
            String strToSupportKvp;
            if (
                objKvpobj_I == null
                )
            {
                if (
                    strtoOption_I == StrtoEnum.SHORT
                    )
                {
                    strToSupportKvp = "null";
                }
                else
                {
                    strToSupportKvp = strText_I + "(null)";
                }
            }
            else
            {
                //                                          //Abort if not a valid kvp
                Test.subVerifyKvp(objValue_I, strKey_I, StrtoEnum.FULL, strText_I, objKvpobj_I);

                strToSupportKvp = Test.strFormatKvpMain(objValue_I, strKey_I, strtoOption_I, strText_I,
                    objKvpobj_I);
            }

            return strToSupportKvp;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subVerifyKvp(
            //                                              //Verify it is a valid kvp.

            //                                              //Read strToSupportKvp method for paramenters description
            Object objValue_I,
            String strKey_I,
            StrtoEnum strtoOption_I,
            String strText_I,
            Object objKvpobj_I
            )
        {
            Type typeKvpobj = objKvpobj_I.GetType();
            if (
                typeKvpobj.Name != Test.strGENERIC_KEYVALUEPAIR_TYPE
                )
                Test.subAbort(Test.strTo(typeKvpobj, "objKvpobj_I.GetType") + " should be KeyValuePair");

            Type typeArgument = typeKvpobj.GetGenericArguments()[1];
            if (!(
                //                                          //Is bclass
                typeArgument == typeof(BclassBaseClassAbstract) ||
                    typeArgument.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
                //                                          //Is btuple
                typeArgument == typeof(BtupleBaseTupleAbstract) ||
                    typeArgument.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
                //                                          //Is benum
                typeArgument == typeof(BenumBaseEnumAbstract) || 
                    typeArgument.IsSubclassOf(typeof(BenumBaseEnumAbstract)) ||
                //                                          //Is Exception
                typeArgument == typeof(Exception) || typeArgument.IsSubclassOf(typeof(Exception))
                ))
                Test.subAbort(Test.strTo(typeKvpobj, "typeKvpobj.GetType") +
                    " should be dictionary type containing bclass, btuple, enum or sysexcep");

            //                                              //Value could be null.
            if (
                //                                          //Value exists
                objValue_I != null
                )
            {
                Type typeValue = objValue_I.GetType();
                if (!(
                    //                                      //Value and KeyValuePair are compatible
                    (typeValue == typeArgument) || typeValue.IsSubclassOf(typeArgument)
                    ))
                    Test.subAbort(Test.strTo(typeValue, "objValue_I.GetType") + ", " +
                        Test.strTo(typeKvpobj, "objKvpobj_I.GetType") + " value and KeyValuePair are not compatible");
            }
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.Types Set of Methods to Analize Types*/
        //--------------------------------------------------------------------------------------------------------------
        /*CONSTANTS*/

        //                                                  //Towa's standard primitives
        private static readonly String[,] arr2strPRIMITIVE_TYPE_AND_PREFIX = {
            //                                              //TO ADD NEW PRIMARY TYPES:
            //                                              //a. Add an entry in this array (standard prefix xxxx).
            //                                              //b. Add a method subfunConvertAndBoxXxxx, similar to
            //                                              //      subfunConvertAndBoxTs).
            //                                              //c. Add a method strAnalizeAndFormatXxxx, similar to
            //                                              //      strAnalizeAndFormatTs).
            //                                              //d. Add case branch in method
            //                                              //      subfunConvertAndBoxPrimitive.
            //                                              //e. Add case branch in methodstrAnalizeAndFormatBbox.
 
            { "Int32", "int" }, { "Int64", "long" }, { "Boolean", "bool" }, { "Char", "char" }, { "Double", "num" },
            //                                              //C# structures should be handled like primitives
            { "DateTime", "ts" },
            };
        //                                                  //Both arrays order by first.
        private static readonly String[] arrstrPRIMITIVE_TYPE;
        private static readonly String[] arrstrPRIMITIVE_PREFIX;

        //                                                  //Towa's standard system types
        private static readonly String[,] arr2strSYSTEM_TYPE_AND_PREFIX = { 
            //                                              //TO ADD NEW SYSTEM TYPES:
            //                                              //a. Add an entry in this array (standard prefix yyyyy).
            //                                              //b. Add a method subfunConvertYyyyy, similar to
            //                                              //      subfunConvertSysdir).
            //                                              //c. Add a method strAnalizeAndFormatYyyyy, similar to
            //                                              //      strAnalizeAndFormatSysdir).
            //                                              //d. Add case branch in method subfunConvertSystemType.
            //                                              //e. Add case branch in method
            //                                              //      strAnalizeAndFormatSystemType.

            //                                              //String should be handled like system tyes.
            { "String", "str" },
            //                                              //System types, NO TENGO CLARO PORQUE pero a veces aparece
            //                                              //      RuntimeType y a veces solo Type
            { "RuntimeType", "type" }, { "Type", "type" }, 
            { "DirectoryInfo", "sysdir" }, { "FileInfo", "sysfile" },
            { "StreamReader", "syssr" }, { "StreamWriter", "syssw" },
            };

        //                                                  //Both arrays order by first.
        private static readonly String[] arrstrSYSTEM_TYPE;
        private static readonly String[] arrstrSYSTEM_PREFIX;

        //                                                  //Towa's standard other types
        private const String strGENERIC_LIST_TYPE = "List`1";
        private const String strGENERIC_QUEUE_TYPE = "Queue`1";
        private const String strGENERIC_STACK_TYPE = "Stack`1";
        private const String strGENERIC_DICTIONARY_TYPE = "Dictionary`2";
        private const String strGENERIC_KEYVALUEPAIR_TYPE = "KeyValuePair`2";
        private static readonly String[,] arr2strGENERIC_TYPE_AND_PREFIX = { 
            { strGENERIC_LIST_TYPE, "lst" }, { strGENERIC_QUEUE_TYPE, "queue" }, { strGENERIC_STACK_TYPE, "stack" },
            { strGENERIC_DICTIONARY_TYPE, "dic" }, { strGENERIC_KEYVALUEPAIR_TYPE, "kvp" }, 
            };

        //                                                  //Both arrays order by first.
        private static readonly String[] arrstrGENERIC_TYPE;
        private static readonly String[] arrstrGENERIC_PREFIX;

        //--------------------------------------------------------------------------------------------------------------
        /*SUPPORT METHODS FOR STATIC CONSTRUCTORS*/

        //--------------------------------------------------------------------------------------------------------------
        private static void subPrepareConstantTypes(        //Order and varify constants. 

            out String[] arrstrPRIMITIVE_TYPE_O,
            out String[] arrstrPRIMITIVE_PREFIX_O,
            out String[] arrstrSYSTEM_TYPE_O,
            out String[] arrstrSYSTEM_PREFIX_O,
            out String[] arrstrGENERIC_TYPE_O,
            out String[] arrstrGENERIC_PREFIX_O
            )
        {
            //                                              //Order arr2strPRIMITIVE_TYPE_AND_PREFIX.
            arrstrPRIMITIVE_TYPE_O = new String[Test.arr2strPRIMITIVE_TYPE_AND_PREFIX.GetLength(0)];
            arrstrPRIMITIVE_PREFIX_O = new String[arrstrPRIMITIVE_TYPE_O.Length];

            for (int intI = 0; intI < arrstrPRIMITIVE_TYPE_O.Length; intI = intI + 1)
            {
                arrstrPRIMITIVE_TYPE_O[intI] = Test.arr2strPRIMITIVE_TYPE_AND_PREFIX[intI, 0];
                arrstrPRIMITIVE_PREFIX_O[intI] = Test.arr2strPRIMITIVE_TYPE_AND_PREFIX[intI, 1];
            }

            Array.Sort(arrstrPRIMITIVE_TYPE_O, arrstrPRIMITIVE_PREFIX_O);

            Tools.subVerifyDuplicate(arrstrPRIMITIVE_TYPE_O, "arrstrPRIMITIVE_TYPE_O");

            //                                              //Order arr2strSYSTEM_TYPE_AND_PREFIX.
            arrstrSYSTEM_TYPE_O = new String[Test.arr2strSYSTEM_TYPE_AND_PREFIX.GetLength(0)];
            arrstrSYSTEM_PREFIX_O = new String[arrstrSYSTEM_TYPE_O.Length];

            for (int intI = 0; intI < arrstrSYSTEM_TYPE.Length; intI = intI + 1)
            {
                arrstrSYSTEM_TYPE_O[intI] = Test.arr2strSYSTEM_TYPE_AND_PREFIX[intI, 0];
                arrstrSYSTEM_PREFIX_O[intI] = Test.arr2strSYSTEM_TYPE_AND_PREFIX[intI, 1];
            }

            Array.Sort(arrstrSYSTEM_TYPE_O, arrstrSYSTEM_PREFIX_O);

            Tools.subVerifyDuplicate(arrstrSYSTEM_TYPE_O, "arrstrSYSTEM_TYPE_O");

            //                                              //Order arr2strGENERIC_TYPE_AND_PREFIX.
            arrstrGENERIC_TYPE_O = new String[Test.arr2strGENERIC_TYPE_AND_PREFIX.GetLength(0)];
            arrstrGENERIC_PREFIX_O = new String[arrstrGENERIC_TYPE_O.Length];

            for (int intI = 0; intI < arrstrGENERIC_TYPE.Length; intI = intI + 1)
            {
                arrstrGENERIC_TYPE_O[intI] = Test.arr2strGENERIC_TYPE_AND_PREFIX[intI, 0];
                arrstrGENERIC_PREFIX_O[intI] = Test.arr2strGENERIC_TYPE_AND_PREFIX[intI, 1];
            }

            Array.Sort(arrstrGENERIC_TYPE_O, arrstrGENERIC_PREFIX_O);

            Tools.subVerifyDuplicate(arrstrGENERIC_TYPE_O, "arrstrGENERIC_TYPE_O");
        }

        //--------------------------------------------------------------------------------------------------------------
        /*SHARE METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        private static bool boolIsStandard(                 //Evaluate if type is standard type.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandard;
            /*CASE*/
            if (
                type_I.IsArray
                )
            {
                boolIsStandard = Test.boolIsStandardArray(type_I, boolAbort_I);
            }
            else if (
                type_I.IsGenericType
                )
            {
                boolIsStandard = Test.boolIsStandardGeneric(type_I, boolAbort_I);
            }
            else
            {
                boolIsStandard = Test.boolIsStandardSingle(type_I, boolAbort_I);
            }
            /*END-CASE*/

            return boolIsStandard;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardSingle(           //Evaluate if type is standard single type.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardSingle;
            /*CASE*/
            if (
                Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, type_I.Name) >= 0
                )
            {
                boolIsStandardSingle = Test.boolIsStandardPrimitive(type_I, boolAbort_I);
            }
            else if (
                Array.BinarySearch(Test.arrstrSYSTEM_TYPE, type_I.Name) >= 0
                )
            {
                boolIsStandardSingle = Test.boolIsStandardSystem(type_I, boolAbort_I);
            }
            else if (
                (type_I == typeof(Enum)) || type_I.IsSubclassOf(typeof(Enum))
                )
            {
                boolIsStandardSingle = Test.boolIsStandardEnum(type_I, boolAbort_I);
            }
            else if (
                (type_I == typeof(BclassBaseClassAbstract)) || type_I.IsSubclassOf(typeof(BclassBaseClassAbstract))
                )
            {
                boolIsStandardSingle = Test.boolIsStandardBclass(type_I, boolAbort_I);
            }
            else if (
                (type_I == typeof(BtupleBaseTupleAbstract)) || type_I.IsSubclassOf(typeof(BtupleBaseTupleAbstract))
                )
            {
                boolIsStandardSingle = Test.boolIsStandardBtuple(type_I, boolAbort_I);
            }
            else if (
                (type_I == typeof(BenumBaseEnumAbstract)) || type_I.IsSubclassOf(typeof(BenumBaseEnumAbstract))
                )
            {
                boolIsStandardSingle = Test.boolIsStandardBenum(type_I, boolAbort_I);
            }
            else if (
                (type_I == typeof(Exception)) || type_I.IsSubclassOf(typeof(Exception))
                )
            {
                boolIsStandardSingle = Test.boolIsStandardException(type_I, boolAbort_I);
            }
            else
            {
                boolIsStandardSingle = false;

                if (
                    boolAbort_I && !boolIsStandardSingle
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(type_I, "type_I") + " is not an standard primitive type");
            }
            /*END-CASE*/

            return boolIsStandardSingle;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardPrimitive(        //Evaluate if type is standard primitive type.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardPrimitive = (
                //                                          //Is a primitive included in Towa Standard.
                (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, type_I.Name) >= 0)
                );

            if (
                boolAbort_I && !boolIsStandardPrimitive
                )
                Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                    Test.strTo(type_I, "type_I") + " is not an standard primitive type");

            return boolIsStandardPrimitive;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardSystem(           //Evaluate if type is standard system type.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            bool boolX;
            boolX = type_I.IsAbstract;


            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardSimpleOrSystem = (
                Array.BinarySearch(Test.arrstrSYSTEM_TYPE, type_I.Name) >= 0
                );

            if (
                boolAbort_I && !boolIsStandardSimpleOrSystem
                )
                Test.subAbort(Test.strTo(Test.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
                    Test.strTo(type_I, "type_I") + " is not an standard system type");

            return boolIsStandardSimpleOrSystem;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardEnum(             //Evaluate if type is standard enum type.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardEnum = (
                ((type_I == typeof(Enum)) || type_I.IsSubclassOf(typeof(Enum))) &&
                //                                          //Has the form Prefix...Enum (at least 1 char before
                //                                          //      "Enum").
                type_I.Name.EndsWith("Enum") && (type_I.Name.Length > "Enum".Length) &&
                    Tools.boolIsLetterUpper(type_I.Name[0])
                );

            if (
                boolAbort_I && !boolIsStandardEnum
                )
                Test.subAbort(Test.strTo(type_I, "type_I") +
                    " is not an standard Enum type, also should have the form 'Prefix...Enum'");

            return boolIsStandardEnum;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardBclass(           //Evaluate if type is standard Bclass.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardBclass = (
                (type_I == typeof(BclassBaseClassAbstract)) || type_I.IsSubclassOf(typeof(BclassBaseClassAbstract))
                );

            if (
                boolAbort_I && !boolIsStandardBclass
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " is not an standard bclass type");

            if (
                //                                          //Is bclass (or subclass of)
                boolIsStandardBclass
                )
            {
                //                                          //It could be abstract or concrete class

                if (
                    type_I.IsAbstract
                    )
                {
                    boolIsStandardBclass = (
                        //                                  //The name has the form: Prefix.....Abstract
                        type_I.Name.EndsWith("Abstract") && (type_I.Name.Length > "Abstract".Length) &&
                            Tools.boolIsLetterUpper(type_I.Name[0])
                        );

                    if (
                        boolAbort_I && !boolIsStandardBclass
                        )
                        Test.subAbort(Test.strTo(type_I, "type_I") +
                            " an standard abstract bclass type should have the form 'Prefix....Abstract'");
                }
                else
                {
                    //                                      //It is concrete class

                    String strNameLower = type_I.Name.ToLower();
                    boolIsStandardBclass = (
                        //                                  //The name has de form: Prefix.... and do not end with
                        //                                  //      Abstract, Tuple, Enum or Interface
                        !(strNameLower.EndsWith("abstract") || strNameLower.EndsWith("enum") ||
                            strNameLower.EndsWith("tuple") || strNameLower.EndsWith("interface")) &&
                        Tools.boolIsLetterUpper(type_I.Name[0])
                        );

                    if (
                        boolAbort_I && boolIsStandardBclass
                        )
                        Test.subAbort(Test.strTo(type_I, "type_I") +
                            " an standard concrete bclass type should have the form 'Prefix....' and" +
                            " can not ends with Abstract, Tuple, Enum or Interface (upper or lower letters)");
                }
            }

            return boolIsStandardBclass;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardBtuple(           //Evaluate if type is standard Btuple.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardBtuple = (
                ((type_I == typeof(BtupleBaseTupleAbstract)) || type_I.IsSubclassOf(typeof(BtupleBaseTupleAbstract))) &&
                //                                          //The name has the form: TNprefix...Tuple (at least 3 char
                //                                          //      before "Tuple".
                type_I.Name.EndsWith("Tuple") && (type_I.Name.Length >= ("Tuple".Length + 3)) &&
                    (type_I.Name[0] == 'T') &&
                    ((type_I.Name[1] >= '1') && (type_I.Name[1] <= '9') || (type_I.Name[1] == 'z')) &&
                    Tools.boolIsLetterLower(type_I.Name[2])
                );

            if (
                boolAbort_I && !boolIsStandardBtuple
                )
                Test.subAbort(Test.strTo(type_I, "type_I") +
                    " is not an standard tuple type, also should have the form 'TNprefix...Tuple'");

            return boolIsStandardBtuple;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardBenum( 
            //                                              //Evaluate if type is standard benum type.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardBenum = (
                ((type_I == typeof(BenumBaseEnumAbstract)) || type_I.IsSubclassOf(typeof(BenumBaseEnumAbstract))) &&
                //                                          //Has the form Prefix...Enum (at least 1 char before
                //                                          //      "Enum").
                type_I.Name.EndsWith("Enum") && (type_I.Name.Length > "Enum".Length) &&
                    Tools.boolIsLetterUpper(type_I.Name[0])
                );

            if (
                boolAbort_I && !boolIsStandardBenum
                )
                Test.subAbort(Test.strTo(type_I, "type_I") +
                    " is not an standard enum type, also should have the form 'Prefix...Enum'");

            return boolIsStandardBenum;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardException(        //Evaluate if type is standard Exception type.
            //                                              //There is no much to check.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardException = (
                ((type_I == typeof(Exception)) || type_I.IsSubclassOf(typeof(Exception)))
                );

            if (
                boolAbort_I && !boolIsStandardException
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " is not an standard Exception type");

            return boolIsStandardException;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardArray(            //Evaluate if type is standard array.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardArray = (
                type_I.IsArray &&
                //                                          Is obj[], obj[,] or obj[, ,]
                (type_I.GetArrayRank() <= 3)
                );

            if (
                boolAbort_I && !boolIsStandardArray
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " is not an standard array type");

            if (
                boolIsStandardArray
                )
            {
                //                                          //The "element" of an array should be standard type, but
                //                                          //      not array or generic

                Type typeElement = type_I.GetElementType();
                boolIsStandardArray = (!(
                    typeElement.IsArray ||
                    typeElement.IsGenericType ||
                    !Test.boolIsStandard(typeElement, false)
                    ));

                if (
                    boolAbort_I && !boolIsStandardArray
                    )
                    Test.subAbort(Test.strTo(type_I, "type_I") + ", " +
                        Test.strTo(typeElement, "type_I.GetElementType") +
                        " the element of standard array type should be standard type, but not array or generic");
            }

            return boolIsStandardArray;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static bool boolIsStandardGeneric(          //Evaluate if type is standard generic type.

            //                                              //bool, is valid.

            Type type_I,
            //                                              //true, abort if is not valid.
            bool boolAbort_I
            )
        {
            if (
                type_I == null
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " can not be null");

            bool boolIsStandardGeneric = (
                type_I.IsGenericType &&
                (Array.BinarySearch(Test.arrstrGENERIC_TYPE, type_I.Name) >= 0)
                );

            if (
                boolAbort_I && !boolIsStandardGeneric
                )
                Test.subAbort(Test.strTo(type_I, "type_I") + " is not an standard generic type");

            if (
                boolIsStandardGeneric
                )
            {
                //                                          //The "main argument" of an generic should be standard type,
                //                                          //      but not array or generic

                Type[] arrtypeArgument = type_I.GetGenericArguments();
                Type typeMainArgument = arrtypeArgument[arrtypeArgument.Length - 1];

                boolIsStandardGeneric = (!(
                    typeMainArgument.IsArray ||
                    typeMainArgument.IsGenericType ||
                    !Test.boolIsStandard(typeMainArgument, false)
                    ));

                if (
                    boolAbort_I && !boolIsStandardGeneric
                    )
                    Test.subAbort(Test.strTo(type_I, "type_I") + ", " +
                        Test.strTo(typeMainArgument, "typeMainArgument") +
                        " the main argument of standard generic type should be standard type," +
                        " but not array or generic");

                //                                          //It could be a 2 arguments generic (Dictionary or
                //                                          //      KeyValuePair).

                if (
                    boolIsStandardGeneric && type_I.Name.EndsWith("`2")
                    )
                {
                    //                                      //It should be dictionary or KeyValuePair, the first
                    //                                      //      argument should be String.

                    boolIsStandardGeneric = (
                        arrtypeArgument[0] == typeof(String)
                        );

                    if (
                        boolAbort_I && !boolIsStandardGeneric
                        )
                        Test.subAbort(Test.strTo(type_I, "type_I") + ", " +
                            Test.strTo(arrtypeArgument[0], "arrtypeArgument[0]") +
                            " the first argument of standard 2 arguments generic type should be String");
                }
            }

            return boolIsStandardGeneric;
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.strFormatYyyyy Set of Private Methods to Format Object to Display*/
        //--------------------------------------------------------------------------------------------------------------
        private static String strFormatSingle(              //Format for display.

            //                                              //str, formated info

            //                                              //Any single type (no arrays or generic types)
            Object obj_I,
            //                                              //FULL or SHORT display.
            StrtoEnum strtoOption_I,
            //                                              //Variable name of the single object.
            String strText_I
            )
        {
            Type typeObj = obj_I.GetType();
            if (
                typeObj.IsArray ||
                typeObj.IsGenericType
                )
                Test.subAbort(Test.strTo(typeObj, "obj_I.GetType") +
                    " only single types can be processed in this method");

            String strFormatSingle;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strFormatSingle = Test.strFormatSingleShort(obj_I);
            }
            else
            {
                strFormatSingle = Test.strFormatSingleFull(obj_I, strText_I);
            }

            return strFormatSingle;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strFormatSingleShort(         //Format for display.

            //                                              //str, formated info

            //                                              //Read strFormatSingle method for paramenters description
            Object obj_I
            )
        {
            return Test.strAnalizeAndFormatCheckNulls(obj_I, StrtoEnum.SHORT);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strFormatSingleFull(          //Format for display.

            //                                              //str, formated info

            //                                              //Read strFormatSingle method for paramenters description
            Object obj_I,
            String strText_I
            )
        {
            //                                              //To display a bclass first time requires a block
            String strFormatSingleFull;
            if (
                //                                          //Bclass processed for first time.
                (obj_I is BclassBaseClassAbstract) && !Test.lstobjPreviouslyProcessed.Contains(obj_I)
                )
            {
                //                                          //A first time bclass should be display inside a block    
                String strNL;
                String strLabel;
                //                                          //The objId will be display before bclass, is not
                //                                          //      in the block headings
                Test.subBlockStart(out strNL, out strLabel, out strFormatSingleFull, strText_I, "");

                strFormatSingleFull = strFormatSingleFull + strNL +
                    Test.strAnalizeAndFormatCheckNulls((BclassBaseClassAbstract)obj_I, StrtoEnum.FULL);

                Test.subBlockEnd(ref strNL, ref strFormatSingleFull, strLabel);
            }
            else
            {
                //                                          //No blocking requires, any single type
                strFormatSingleFull = strText_I + "(" +
                    Test.strAnalizeAndFormatCheckNulls(obj_I, StrtoEnum.FULL) +")";
            }

            return strFormatSingleFull;
        }

        //--------------------------------------------------------------------------------------------------------------
        private static String strFormatArrOrOneArgumentGeneric(
            //                                              //Format for display
            //                                              //An arr or One Argument Generic object should be display
            //                                              //      only once per run.

            //                                              //str, formated info

            //                                              //arr to format
            Object[] arrobj_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of arr or one argument generic object
            String strText_I,
            //                                              //this is needed to get objId.
            Object objOriginal_I
            )
        {
            //                                              //Compute objId
            String strObjId = Test.strGetObjId(objOriginal_I);
            if (
                objOriginal_I.GetType().IsGenericType
                )
            {
                //                                          //Get size from arrobj
                strObjId = strObjId.Replace("[?]", "[" + arrobj_I.Length + "]");
            }

            String strFormatArrOrOneArgumentGeneric;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strFormatArrOrOneArgumentGeneric = strObjId;
            }
            else
            {
                //                                          //An Arr or One Argument Generic object should be display
                //                                          //      only once per run.

                if (
                    Test.lstobjPreviouslyProcessed.Contains(objOriginal_I)
                    )
                {
                    strFormatArrOrOneArgumentGeneric = strText_I + "(" + strObjId + "|look object up|" + ")";
                }
                else
                {
                    //                                      //Register arr or one argument generic as processed
                    Test.lstobjPreviouslyProcessed.Add(objOriginal_I);

                    strFormatArrOrOneArgumentGeneric = Test.strFormatArr(arrobj_I, strText_I, strObjId);
                }
            }

            return strFormatArrOrOneArgumentGeneric;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strFormatArr(                 //Format for display, it could be:
            //                                              //Set of Lines(Items) or One Line(Row).

            //                                              //str, formated info.

            //                                              //Read strFormatArrOrOneArgumentGeneric method for
            //                                              //      paramenters description
            Object[] arrobj_I,
            String strText_I,
            String strObjId_I
            )
        {
            //                                              //Find if Set of Lines(Items) format is required.
            //                                              //Need to look for long item

            //                                              //Set of lines is always required for bclass[] and btuple[].
            //                                              //(if not, it won't work properlly).

            bool boolSetOfLinesItems;
            if (
                (arrobj_I is BclassBaseClassAbstract[]) || (arrobj_I is BtupleBaseTupleAbstract[])
                )
            {
                boolSetOfLinesItems = true;
            }
            else
            {
                boolSetOfLinesItems = false;
                int intI = 0;
                /*UNTIL-DO*/
                while (!(
                    boolSetOfLinesItems ||
                    (intI >= arrobj_I.Length)
                    ))
                {
                    String strItem = Test.strAnalizeAndFormatCheckNulls(arrobj_I[intI], StrtoEnum.FULL);
                    boolSetOfLinesItems = (
                        strItem.Length > Test.intLONG_ITEM_ROW_MATRIX
                        );

                    intI = intI + 1;
                }
            }

            String strFormatArr;
            if (
                boolSetOfLinesItems
                )
            {
                String strNL;
                String strLabel;
                Test.subBlockStart(out strNL, out strLabel, out strFormatArr, strText_I, strObjId_I);

                strFormatArr = strFormatArr + Test.strListItems(arrobj_I, strNL);

                Test.subBlockEnd(ref strNL, ref strFormatArr, strLabel);
            }
            else
            {
                strFormatArr = strText_I + "(" + strObjId_I + Test.strLineRow(arrobj_I) + ")";
            }

            return strFormatArr;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strListItems(                 //Format an array to a Set of Lines(Items) inside a block.
            //                                              //Example:
            //                                              //[
            //                                              //{
            //                                              //[0] item
            //                                              //...
            //                                              //[x] item
            //                                              //}
            //                                              //]

            //                                              //str, set in block format

            Object[] arrobj_I,
            String strNL_I
            )
        {
            //                                              //Chars required for longest index: "[x]"
            int intCharsInLongestIndex = ("[" + (arrobj_I.Length - 1) + "]").Length;

            //                                              //Produces a Set of Lines(Items) ready to display.
            String[] arrstrIndexAndItem = new String[arrobj_I.Length];
            for (int intI = 0; intI < arrobj_I.Length; intI = intI + 1)
            {
                String strItem = strAnalizeAndFormatCheckNulls(arrobj_I[intI], StrtoEnum.FULL);

                //                                          //Format: NL [i]_ item
                arrstrIndexAndItem[intI] = strNL_I + ("[" + intI + "]").PadRight(intCharsInLongestIndex) + " " +
                    strItem;
            }

            return strNL_I + "{" + String.Concat(arrstrIndexAndItem) + strNL_I + "}";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strLineRow(                   //Produces:
            //                                              //{ item, ..., item }.

            //                                              //str, arr in one line format.

            Object[] arrobj_I
            )
        {
            //                                              //Convert arrobj to arrstr
            String[] arrstrItem = new String[arrobj_I.Length];
            for (int intI = 0; intI < arrobj_I.Length; intI = intI + 1)
            {
                arrstrItem[intI] = Test.strAnalizeAndFormatCheckNulls(arrobj_I[intI], StrtoEnum.FULL);
            }

            //                                          //Format: { item, item, ..., item }
            return Test.strVectorFromSet(arrstrItem);
        }

        //--------------------------------------------------------------------------------------------------------------
        private static String strFormatArr2Main(            //Format for display.
            //                                              //An arr2 object should be display only once per run.

            //                                              //str, formated info.

            //                                              //arr2 to format
            Object[,] arr2obj_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of arr2
            String strText_I,
            //                                              //this is needed to get objId.
            Object objOriginal_I
            )
        {
            String strObjId = Test.strGetObjId(objOriginal_I);

            String strFormatArr2Main;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strFormatArr2Main = strObjId;
            }
            else
            {
                //                                          //An arr2 object should be display only once per run.
                if (
                    Test.lstobjPreviouslyProcessed.Contains(objOriginal_I)
                    )
                {
                    strFormatArr2Main = strText_I + "(" + strObjId + "|look object up|" + ")";
                }
                else
                {
                    //                                      //Register arr2 as processed
                    Test.lstobjPreviouslyProcessed.Add(objOriginal_I);

                    strFormatArr2Main = Test.strFormatArr2(arr2obj_I, strText_I, strObjId);
                }
            }

            return strFormatArr2Main;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strFormatArr2(                //Format for display, it could be:
            //                                              //Set of Lines(Items), Set of Lines(Rows) or One
            //                                              //      Line(Matrix).

            //                                              //str, formated info.

            //                                              //Read strFormatArr2Main method for paramenters description
            Object[,] arr2obj_I,
            String strText_I,
            String strObjId_I
            )
        {
            //                                              //Find if Set of Lines(Items) or Set of Lines(Rows) format
            //                                              //      is required.

            bool boolSetOfLinesItems;
            bool boolSetOfLinesRows;
            if (
                (arr2obj_I is BclassBaseClassAbstract[,]) || (arr2obj_I is BtupleBaseTupleAbstract[,])
                )
            {
                boolSetOfLinesItems = true;
                boolSetOfLinesRows = true;
            }
            else
            {
                //                                          //Need to look for long row and item
                boolSetOfLinesItems = false;
                boolSetOfLinesRows = false;
                int intI = 0;
                /*UNTIL-DO*/
                while (!(
                    boolSetOfLinesRows ||
                    (intI >= arr2obj_I.GetLength(0))
                    ))
                {
                    int intRowLength = 0;

                    int intJ = 0;
                    /*UNTIL-DO*/
                    while (!(
                        boolSetOfLinesItems ||
                        (intJ >= arr2obj_I.GetLength(1))
                        ))
                    {
                        String strItem = Test.strAnalizeAndFormatCheckNulls(arr2obj_I[intI, intJ],
                            StrtoEnum.FULL);
                        boolSetOfLinesItems = (
                            strItem.Length > Test.intLONG_ITEM_ROW_MATRIX
                            );

                        intRowLength = intRowLength + strItem.Length;

                        intJ = intJ + 1;
                    }

                    //                                      //Add formating chars { item, item, item }
                    intRowLength = intRowLength + 2 * arr2obj_I.GetLength(1) + 2;
                    boolSetOfLinesRows = (
                        intRowLength > Test.intLONG_ITEM_ROW_MATRIX
                        );

                    intI = intI + 1;
                }
            }

            String strFormatArr2;
            if (
                //                                          //Row or Item requires block (if item requires => row also)
                boolSetOfLinesRows
                )
            {
                String strNL;
                String strLabel;
                Test.subBlockStart(out strNL, out strLabel, out strFormatArr2, strText_I, strObjId_I);

                if (
                    boolSetOfLinesItems
                    )
                {
                    strFormatArr2 = strFormatArr2 + Test.strListItems(arr2obj_I, strNL);
                }
                else
                {
                    strFormatArr2 = strFormatArr2 + Test.strListRows(arr2obj_I, strNL);
                }

                Test.subBlockEnd(ref strNL, ref strFormatArr2, strLabel);
            }
            else
            {
                strFormatArr2 = strText_I + "(" + strObjId_I + Test.strLineMatrix(arr2obj_I) + ")";
            }

            return strFormatArr2;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strListItems(                 //Format a matrix to a Set of Lines(Items) inside a block.
            //                                              //Example:
            //                                              //[
            //                                              //{
            //                                              //  {
            //                                              //[0, 0] item
            //                                              //...
            //                                              //[0, y] item
            //                                              //  }
            //                                              //.....
            //                                              //  {
            //                                              //[x, 0] item
            //                                              //...
            //                                              //[x, y] item
            //                                              //  }
            //                                              //}
            //                                              //]
            //                                              //str, matrix in block format

            //                                              //Read strFormatArr2Main method for paramenters description
            Object[,] arr2obj_I,
            String strNL_I
            )
        {
            //                                              //Chars required for longest index: "[x, y]"
            int intCharsInLargestIndex = 
                ("[" + (arr2obj_I.GetLength(0) - 1) + ", " + (arr2obj_I.GetLength(1) - 1) + "]").Length;

            String[] arrstrBlockForRow = new String[arr2obj_I.GetLength(0)];
            for (int intI = 0; intI < arr2obj_I.GetLength(0); intI = intI + 1)
            {
                //                                          //Produces a Set of Lines(Items) ready to display.
                String[] arrstrIndexAndItem = new String[arr2obj_I.GetLength(1)];
                for (int intJ = 0; intJ < arr2obj_I.GetLength(1); intJ = intJ + 1)
                {
                    String strItem = Test.strAnalizeAndFormatCheckNulls(arr2obj_I[intI, intJ], StrtoEnum.FULL);

                    //                                      //Format: NL [i,j]_ item
                    arrstrIndexAndItem[intJ] = strNL_I +
                        ("[" + intI + ", " + intJ + "]").PadRight(intCharsInLargestIndex) + " " + strItem;
                }

                arrstrBlockForRow[intI] = strNL_I + "  {" + String.Concat(arrstrIndexAndItem) + strNL_I + "  }";
            }

            return strNL_I + "{" + String.Concat(arrstrBlockForRow) + strNL_I + "}";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strListRows(                  //Format a matrix to a Set of Lines(Rows) inside a block.
            //                                              //Example:
            //                                              //[
            //                                              //{
            //                                              //[0, *] { item, ..., item }
            //                                              //.......
            //                                              //[x, *] { item, ..., item }
            //                                              //}
            //                                              //]

            //                                              //str, matrix in block format

            //                                              //Read strFormatArr2Main method for paramenters description
            Object[,] arr2Obj_I,
            String strNL_I
            )
        {
            //                                              //Chars required for longest index: "[x, *]"
            int intCharsInLongestIndex = ("[" + (arr2Obj_I.GetLength(0) - 1) + ", *]").Length;

            String[] arrstrIndexAndRow = new String[arr2Obj_I.GetLength(0)];
            for (int intI = 0; intI < arr2Obj_I.GetLength(0); intI = intI + 1)
            {
                //                                          //I need to separate a row
                String[] arrstrItemInRow = new String[arr2Obj_I.GetLength(1)];
                for (int intJ = 0; intJ < arr2Obj_I.GetLength(1); intJ = intJ + 1)
                {
                    arrstrItemInRow[intJ] = Test.strAnalizeAndFormatCheckNulls(arr2Obj_I[intI, intJ],
                        StrtoEnum.FULL);
                }

                //                                          //Format: { item, item, ..., item }
                String strRow = Test.strVectorFromSet(arrstrItemInRow);

                //                                          //Format: NL [x, *]_ row
                arrstrIndexAndRow[intI] = strNL_I + ("[" + intI + ", *]").PadRight(intCharsInLongestIndex) + " " +
                    strRow;
            }

            return strNL_I + "{" + String.Concat(arrstrIndexAndRow) + strNL_I + "}";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strLineMatrix(                //Produces:
            //                                              //[
            //                                              //{ { item, ..., item }, ....., { item, ..., item } }
            //                                              //]

            //                                              //str, matrix in one long line format.

            //                                              //Read strFormatArr2Main method for paramenters description
            Object[,] arr2obj_I
            )
        {
                //                                          //Format rows
                String[] arrstrRow = new String[arr2obj_I.GetLength(0)];
                for (int intI = 0; intI < arr2obj_I.GetLength(0); intI = intI + 1)
                {
                    //                                      //I need to separate a row
                    String[] arrstrItemInRow = new String[arr2obj_I.GetLength(1)];
                    for (int intJ = 0; intJ < arr2obj_I.GetLength(1); intJ = intJ + 1)
                    {
                        arrstrItemInRow[intJ] = Test.strAnalizeAndFormatCheckNulls(arr2obj_I[intI, intJ],
                            StrtoEnum.FULL);
                    }

                    //                                       //Format: { item, item, ..., item }
                    arrstrRow[intI] = Test.strVectorFromSet(arrstrItemInRow);
                }

                //                                          //Format: { row, row, ..., row }
                return Test.strVectorFromSet(arrstrRow);
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strFormatArr3Main(            //Format for display.
            //                                              //An arr3 object should be display only once per run.

            //                                              //str, formated info.

            //                                              //arr3 to format
            Object[, ,] arr3obj_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of arr3
            String strText_I,
            //                                              //this is needed to get objId.
            Object objOriginal_I
            )
        {
            //                                              //To easy code
            String strObjId = Test.strGetObjId(objOriginal_I);

            String strFormatArr3Main;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strFormatArr3Main = strObjId;
            }
            else
            {
                //                                          //An arr3 object should be display only once per run.
                if (
                    Test.lstobjPreviouslyProcessed.Contains(objOriginal_I)
                    )
                {
                    strFormatArr3Main = strText_I + "(" + strObjId + "|look object up|" + ")";
                }
                else
                {
                    //                                      //Register arr3 as processed
                    Test.lstobjPreviouslyProcessed.Add(objOriginal_I);

                    strFormatArr3Main = Test.strFormatArr3(arr3obj_I, strText_I, strObjId);
                }
            }

            return strFormatArr3Main;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strFormatArr3(                //Format for display, it could be:
            //                                              //Set of Lines(Items), Set of Lines(Rows), Set of
            //                                              //      Lines(Matrixes) or One Line(Cube).

            //                                              //str, formated info.

            //                                              //Read strFormatArr2Main method for paramenters description
            Object[, ,] arr3obj_I,
            String strText_I,
            String strObjId_I
            )
        {
            //                                              //Find if Set of Lines(Matrixes), Set of Lines(Items) or Set
            //                                              //      of Lines(Rows) format is required.
            bool boolSetOfLinesItems;
            bool boolSetOfLinesRows;
            bool boolSetOfLinesMatrixes;
            if (
                (arr3obj_I is BclassBaseClassAbstract[, ,]) || (arr3obj_I is BtupleBaseTupleAbstract[, ,])
                )
            {
                boolSetOfLinesItems = true;
                boolSetOfLinesRows = true;
                boolSetOfLinesMatrixes = true;
            }
            else
            {
                //                                          //Need to look for long matrix, row and item
                boolSetOfLinesItems = false;
                boolSetOfLinesRows = false;
                boolSetOfLinesMatrixes = false;
                int intI = 0;
                while (!(
                    boolSetOfLinesMatrixes ||
                    (intI >= arr3obj_I.GetLength(0))
                    ))
                {
                    int intMatrixLength = 0;

                    int intJ = 0;
                    /*UNTIL-DO*/
                    while (!(
                        boolSetOfLinesRows ||
                        (intJ >= arr3obj_I.GetLength(1))
                        ))
                    {
                        int intRowLength = 0;

                        int intK = 0;
                        /*UNTIL-DO*/
                        while (!(
                            boolSetOfLinesItems ||
                            (intK >= arr3obj_I.GetLength(2))
                            ))
                        {
                            String strItem = Test.strAnalizeAndFormatCheckNulls(arr3obj_I[intI, intJ, intK],
                                StrtoEnum.FULL);
                            boolSetOfLinesItems = (
                                strItem.Length > Test.intLONG_ITEM_ROW_MATRIX
                                );

                            intRowLength = intRowLength + strItem.Length;

                            intK = intK + 1;
                        }

                        //                                  //Add formating chars { item, item, item }
                        intRowLength = intRowLength + 2 * arr3obj_I.GetLength(1) + 2;
                        boolSetOfLinesRows = (
                            intRowLength > Test.intLONG_ITEM_ROW_MATRIX
                            );

                        intMatrixLength = intMatrixLength + intRowLength;

                        intJ = intJ + 1;
                    }

                    //                                      //Add formating chars { row, row, row }
                    intMatrixLength = intMatrixLength + 2 * arr3obj_I.GetLength(2) + 2;
                    boolSetOfLinesMatrixes = (
                        intMatrixLength > Test.intLONG_ITEM_ROW_MATRIX
                        );

                    intI = intI + 1;
                }
            }

            String strFormatArr3;
            if (
                //                                          //Matrix, Row or Item requires block (if item requires =>
                //                                          //      row also => matrix also)
                boolSetOfLinesMatrixes
                )
            {
                String strNL;
                String strLabel;
                Test.subBlockStart(out strNL, out strLabel, out strFormatArr3, strText_I, strObjId_I);

                /*CASE*/
                if (
                    boolSetOfLinesItems
                    )
                {
                    strFormatArr3 = strFormatArr3 + Test.strListItems(arr3obj_I, strNL);
                }
                else if (
                    boolSetOfLinesRows
                    )
                {
                    strFormatArr3 = strFormatArr3 + Test.strListRows(arr3obj_I, strNL);
                }
                else
                {
                    strFormatArr3 = strFormatArr3 + Test.strListMatrixes(arr3obj_I, strNL);
                }
                /*END-CASE*/

                Test.subBlockEnd(ref strNL, ref strFormatArr3, strLabel);
            }
            else
            {
                strFormatArr3 = strText_I + "(" + strObjId_I + Test.strLineCube(arr3obj_I) + ")";
            }

            return strFormatArr3;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strListItems(                 //Format a cube to a set of items inside a block.
            //                                              //Example:
            //                                              //[
            //                                              //{
            //                                              //  {
            //                                              //    {
            //                                              //[0, 0, 0] item
            //                                              //...
            //                                              //[0, 0, z] item
            //                                              //    }
            //                                              //.....
            //                                              //    {
            //                                              //[0, y, 0] item
            //                                              //...
            //                                              //[0, y, z] item
            //                                              //    }
            //                                              //  }
            //                                              //.......
            //                                              //  {
            //                                              //    {
            //                                              //[x, 0, 0] item
            //                                              //...
            //                                              //[x, 0, z] item
            //                                              //    }
            //                                              //.....
            //                                              //    {
            //                                              //[x, y, 0] item
            //                                              //...
            //                                              //[x, y, z] item
            //                                              //    }
            //                                              //  }
            //                                              //}
            //                                              //]
            //                                              //str, cube in block format

            Object[, ,] arr3obj_I,
            String strNL_I
            )
        {
            //                                              //Chars required for longest index: "[x, y, z]"
            int intCharsInLargestIndex =
                ("[" + (arr3obj_I.GetLength(0) - 1) + ", " + (arr3obj_I.GetLength(1) - 1) + ", " + 
                    (arr3obj_I.GetLength(2) - 1) + "]").Length;

            String[] arrstrBlockForMatrix = new String[arr3obj_I.GetLength(0)];
            for (int intI = 0; intI < arr3obj_I.GetLength(0); intI = intI + 1)
            {
                String[] arrstrBlockForRow = new String[arr3obj_I.GetLength(1)];
                for (int intJ = 0; intJ < arr3obj_I.GetLength(1); intJ = intJ + 1)
                {
                    //                                      //Produces a set of lines ready to display.
                    String[] arrstrIndexAndItem = new String[arr3obj_I.GetLength(2)];
                    for (int intK = 0; intK < arr3obj_I.GetLength(2); intK = intK + 1)
                    {
                        String strItem = Test.strAnalizeAndFormatCheckNulls(arr3obj_I[intI, intJ, intK],
                            StrtoEnum.FULL);

                        //                                  //Format: NL [i, j, k]_ item
                        arrstrIndexAndItem[intK] = strNL_I +
                            ("[" + intI + ", " + intJ + ", " + intK + "]").PadRight(intCharsInLargestIndex) + " " +
                            strItem;
                    }

                    arrstrBlockForRow[intJ] = strNL_I + "    {" + string.Concat(arrstrIndexAndItem) + strNL_I + "    }";
                }

                arrstrBlockForMatrix[intI] = strNL_I + "  {" + String.Concat(arrstrBlockForRow) + strNL_I + "  }";
            }

            return strNL_I + "{" + String.Concat(arrstrBlockForMatrix) + strNL_I + "}";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strListRows(                  //Format a cube to a set of rows inside a block.
            //                                              //Example:
            //                                              //[
            //                                              //{
            //                                              //  {
            //                                              //[0, 0, *] { item, ..., item }
            //                                              //.....
            //                                              //[0, y, *] { item, ..., item }
            //                                              //  }
            //                                              //.......
            //                                              //  {
            //                                              //[x, 0, *] { item, ..., item }
            //                                              //.....
            //                                              //[x, y, *] { item, ..., item }
            //                                              //  }
            //                                              //}
            //                                              //]

            //                                              //str, cube in block format

            Object[, ,] arr3obj_I,
            String strNL_I
            )
        {
            //                                              //Chars required for longest index: "[x, y, *]"
            int intCharsInLongestIndex =
                ("[" + (arr3obj_I.GetLength(0) - 1) + ", " + (arr3obj_I.GetLength(1) - 1) + ", *]").Length;

            String[] arrstrBlockForMatrix = new String[arr3obj_I.GetLength(0)];
            for (int intI = 0; intI < arr3obj_I.GetLength(0); intI = intI + 1)
            {
                String[] arrstrIndexAndRow = new String[arr3obj_I.GetLength(1)];
                for (int intJ = 0; intJ < arr3obj_I.GetLength(1); intJ = intJ + 1)
                {
                    //                                      //I need to separate a row
                    String[] arrstrItemInRow = new String[arr3obj_I.GetLength(2)];
                    for (int intK = 0; intK < arr3obj_I.GetLength(2); intK = intK + 1)
                    {
                        String strItem = Test.strAnalizeAndFormatCheckNulls(arr3obj_I[intI, intJ, intK],
                            StrtoEnum.FULL);
                        arrstrItemInRow[intK] = strItem;
                    }

                    //                                  //Format: { item, item, ..., item }
                    String strRow = Test.strVectorFromSet(arrstrItemInRow);

                    //                                  //Format: NL [i, j, *]_ row
                    arrstrIndexAndRow[intJ] = strNL_I +
                        ("[" + intI + ", " + intJ + ", *]").PadRight(intCharsInLongestIndex) + " " + strRow;
                }

                arrstrIndexAndRow[intI] = strNL_I + "  {" + String.Concat(arrstrIndexAndRow) + strNL_I + "  }";
            }

            return strNL_I + "{" + String.Concat(arrstrBlockForMatrix) + strNL_I + "}";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strListMatrixes(              //Format a cube to a set of matrixes inside a block.
            //                                              //Example:
            //                                              //[
            //                                              //{
            //                                              //[0, *, *] { { item, ..., item }, .....,
            //                                              //{ item, ..., item } }
            //                                              //.......
            //                                              //[x, *, *] { { item, ..., item }, .....,
            //                                              //{ item, ..., item } }
            //                                              //}
            //                                              //]

            //                                              //str, cube in block format

            Object[, ,] arr3obj_I,
            String strNL_I
            )
        {
            //                                              //Chars required for longest index: "[x, *, *]"
            int intCharsInLongestIndex = ("[" + (arr3obj_I.GetLength(0) - 1) + ", *, *]").Length;

            String[] arrstrIndexAndMatrix = new String[arr3obj_I.GetLength(0)];
            for (int intI = 0; intI < arr3obj_I.GetLength(0); intI = intI + 1)
            {
                //                                          //I need to separate a matrix (array containing rows)
                String[] arrstrRowInMatrix = new String[arr3obj_I.GetLength(1)];
                for (int intJ = 0; intJ < arr3obj_I.GetLength(1); intJ = intJ + 1)
                {
                    //                                          //I need to separate a row
                    String[] arrstrItemInRow = new String[arr3obj_I.GetLength(2)];
                    for (int intK = 0; intK < arr3obj_I.GetLength(2); intK = intK + 1)
                    {
                        arrstrItemInRow[intK] = Test.strAnalizeAndFormatCheckNulls(arr3obj_I[intI, intJ, intK], 
                            StrtoEnum.FULL);
                    }

                    //                                      //Format: { item, item, ..., item }
                    arrstrRowInMatrix[intJ] = Test.strVectorFromSet(arrstrItemInRow);
                }

                //                                          //Format: { row, row, ..., row }
                String strMatrix = Test.strVectorFromSet(arrstrRowInMatrix);

                //                                          //Format: NL [i, *, *]_ matrix
                arrstrIndexAndMatrix[intI] = strNL_I + ("[" + intI + ", *, *]").PadRight(intCharsInLongestIndex) + " " +
                    strMatrix;
            }

            return strNL_I + "{" + String.Concat(arrstrIndexAndMatrix) + strNL_I + "}";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strLineCube(                  //Produces:
            //                                              //[
            //                                              //{ { { item, ..., item }, ....., { item, ..., item } },
            //                                              // .......,
            //                                              //{ { item, ..., item }, ....., { item, ..., item } } }
            //                                              //]

            //                                              //str, array in one long line format.

            //                                              //Array of formated items
            Object[, ,] arr3obj_I
            )
        {
            //                                              //Format matrixes
            String[] arrstrMatrixInCube = new String[arr3obj_I.GetLength(0)];
            for (int intI = 0; intI < arr3obj_I.GetLength(0); intI = intI + 1)
            {
                //                                          //I need to separate a matrix
                String[] arrstrRowInMatrix = new String[arr3obj_I.GetLength(1)];
                for (int intJ = 0; intJ < arr3obj_I.GetLength(1); intJ = intJ + 1)
                {
                    //                                      //I need to separate a row
                    String[] arrstrItemInRow = new String[arr3obj_I.GetLength(2)];
                    for (int intK = 0; intK < arr3obj_I.GetLength(2); intK = intK + 1)
                    {
                        arrstrItemInRow[intK] = Test.strAnalizeAndFormatCheckNulls(arr3obj_I[intI, intJ, intK],
                            StrtoEnum.FULL);
                    }

                    //                                      //Format: { item, item, ..., item }
                    arrstrRowInMatrix[intJ] = Test.strVectorFromSet(arrstrItemInRow);
                }

                //                                          //Format: { row, row, ..., row }
                arrstrMatrixInCube[intI] = Test.strVectorFromSet(arrstrRowInMatrix);
            }

            //                                              //Format: { matriz, matriz, ..., matriz }
            return Test.strVectorFromSet(arrstrMatrixInCube);
        }

        //--------------------------------------------------------------------------------------------------------------
        private static String strVectorFromSet(             //Produces:
            //                                              //{ stuff, ..., stuff }.
            //                                              //Posibilities:
            //                                              //Put a set of strItem in a vector (strRow).
            //                                              //Put a set of strRow in a vector (strMatrix).
            //                                              //Put a set of strMatrix in a vector (strCube).

            //                                              //str, vector format.

            //                                              //Stuff to be included in strVector.
            String[] arrstrStuff_I
            )
        {
            String strRowFormatBeforeAddingBrackets;
            if (
                arrstrStuff_I.Length == 0
                )
            {
                strRowFormatBeforeAddingBrackets = " ";
            }
            else
            {
                strRowFormatBeforeAddingBrackets = " " + String.Join(", ", arrstrStuff_I) + " ";
            }

            return "{" + strRowFormatBeforeAddingBrackets + "}";
        }

        //--------------------------------------------------------------------------------------------------------------
        private static String strFormatDicMain(             //Format for display
            //                                              //A dic object should be display only once per run.

            //                                              //str, formated info

            //                                              //dic.Keys and dic.Values to format
            Object[] arrobjValue_I,
            String[] arrstrKey_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of dic
            String strText_I,
            //                                              //dic, any type (this is needed to get objId).
            Object objDic_I
            )
        {
            //                                              //Get size from arrkey
            String strObjId = Test.strGetObjId(objDic_I).Replace("[?]", "[" + arrstrKey_I.Length + "]");

            String strFormatDicMain;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strFormatDicMain = strObjId;
            }
            else
            {
                if (
                    Test.lstobjPreviouslyProcessed.Contains(objDic_I)
                    )
                {
                    strFormatDicMain = strText_I + "(" + strObjId + "|look object up|" + ")";
                }
                else
                {
                    //                                      //Register dic as processed
                    Test.lstobjPreviouslyProcessed.Add(objDic_I);

                    strFormatDicMain = Test.strFormatDic(arrobjValue_I, arrstrKey_I, strText_I, strObjId);
                }
            }

            return strFormatDicMain;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strFormatDic(                 //Format for display.

            //                                              //str, formated info.

            //                                              //Read strFormatDicMain method for paramenters description
            Object[] arrobjValue_I,
            String[] arrstrKey_I,
            String strText_I,
            String strObjId_I
            )
        {
            Array.Sort(arrstrKey_I, arrobjValue_I);

            //                                              //Compute [key] size.
            int intLonghestKey = 0;
            foreach (String str in arrstrKey_I)
            {
                intLonghestKey = Tools.intMax(intLonghestKey, str.Length);
            }
            intLonghestKey = intLonghestKey + "[]".Length;
            intLonghestKey = Tools.intMin(intKEY_LEN_MAX, intLonghestKey);

            String strFormatDic;
            String strNL;
            String strLabel;
            Test.subBlockStart(out strNL, out strLabel, out strFormatDic, strText_I, strObjId_I);


            //                                              //Produces lines to include in block.
            String[] arrstrEntry = new String[arrobjValue_I.Length];
            for (int intI = 0; intI < arrobjValue_I.Length; intI = intI + 1)
            {
                //                                          //Confirm key has only visible chars
                String strKeyAnalized = Test.strAnalizeAndFormatStr(arrstrKey_I[intI]);
                String strKey;
                /*CASE*/
                if (
                    //                                      //Has just string info ("string information")
                    strKeyAnalized.EndsWith("\"")
                    )
                {
                    //                                      //Take key without ""
                    strKey = strKeyAnalized.Substring(1, strKeyAnalized.Length - 2);
                }
                else if (
                    //                                      //Has lenght info and no diagnotic info ("string info"<nn>)
                    strKeyAnalized.EndsWith(">")
                    )
                {
                    int intMinorThanMark = strKeyAnalized.LastIndexOf("<");

                    //                                      //Take key without ""<nn>
                    strKey = strKeyAnalized.Substring(1, intMinorThanMark - 2);
                }
                else
                {
                    //                                      //Include disgnostic info (or is null).
                    strKey = strKeyAnalized;
                }
                /*END-CASE*/

                arrstrEntry[intI] = strNL + ("[" + strKey + "]").PadRight(intLonghestKey) + " " +
                    Test.strAnalizeAndFormatCheckNulls(arrobjValue_I[intI], StrtoEnum.FULL);
            }

            strFormatDic = strFormatDic + strNL + "{" + String.Concat(arrstrEntry) + strNL + "}";

            Test.subBlockEnd(ref strNL, ref strFormatDic, strLabel);

            return strFormatDic;
        }

        //--------------------------------------------------------------------------------------------------------------
        private static String strFormatKvpMain(             //Format for display

            //                                              //str, formated info

            //                                              //kvp.Key and kvp.Value to format (Value could be null)
            Object objValue_I,
            String strKey_I,
            //                                              //SHORT or FULL display
            StrtoEnum strtoOption_I,
            //                                              //Variable name of kvp
            String strText_I,
            //                                              //kvp, any type (this is needed to get objId).
            Object objKvp_I
            )
        {
            //                                              //Confirm key has only visible chars
            String strKeyAnalized = Test.strAnalizeAndFormatStr(strKey_I);
            String strKey;
            if (
                //                                          //Has no diagnotic info (ends with "), if happen to be null
                //                                          //      (should not) do not ends with "
                strKeyAnalized.EndsWith("\"")
                )
            {
                //                                          //Take key without " "
                strKey = strKeyAnalized.Substring(1, strKeyAnalized.Length - 2);
            }
            else
            { 
                //                                          //Include disgnostic info (or is null).
                strKey = strKeyAnalized;
            }

            String strFormatKvpMain;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strFormatKvpMain = "<" + strKey + ", " + 
                    Test.strAnalizeAndFormatCheckNulls(objValue_I, StrtoEnum.SHORT) + ">";
            }
            else
            {
                strFormatKvpMain = "<" + strKey + ", " +
                    Test.strAnalizeAndFormatCheckNulls(objValue_I, StrtoEnum.FULL) + ">";
            }

            return strFormatKvpMain;
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.Blocking Support blocking in the display Objects Info*/
        //--------------------------------------------------------------------------------------------------------------
        /*CONSTANTS*/

        //                                                  //Si hay mas de 28 niveles, se les pone el úlltimo.
        private const String strLETTERS_FOR_LEVEL = "?ABCDEFGHIJKLMNOPQRSTUVWXYZ*";

        //                                                  //Si hay mas de 25 niveles, se usa el último valor.
        private static int[] arrintLevelSpaces = { 
            0, 4, 8, 12, 16, 20, 24, 27, 30, 33, 36, 39, 42, 44, 46, 48, 50, 52, 54, 55, 56, 57, 58, 59, 60 
            };

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC VARIABLES*/

        //                                                  //Todas las clases no estaticas incluyen el método strTo
        //                                                  //      para mostrar el contenido de dicha clase, algunos de
        //                                                  //      estos métodos requieren block START-END para mostrar
        //                                                  //      el contenido de sus objetos cuando estos contienen
        //                                                  //      colecciones.

        //                                                  //Cada block START-END debe estar en un nivel superior a su
        //                                                  //      base, se incrementa al iniciar el block y se 
        //                                                  //      decrementa al cerrarlo.
        private static int intLevel;

        //                                                  //Esta variable se usa para en cada block START-END 
        //                                                  //      asignarle un número único (para esto, al tomar el 
        //                                                  //      valor se incrementa en 1).
        private static int intStartEnd;

        //                                                  //Cada nivel, del 1 en adelane, tiene asociada una letra (A,
        //                                                  //      B, ...).
        //                                                  //Tambien, a cada nivel se le asocia una indentación al 
        //                                                  //      inicio de cada línea (esto es una cantidad de
        //                                                  //      espacios).

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC CONSTRUCTOR SUPPORT METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        private static void subPrepareConstantsToBlockFormat(
            //                                              //Método de apoyo llamado en constructor estático. 
            //                                              //Inicia Nivel y StartEnd necesarios para indentar el log.
            )
        {
            Test.intLevel = 0;

            //                                              //intStartEnd, numeración consecutiva de los blocks An, Bn,
            //                                              //      ...., debe ser inicializado en la inicialización del
            //                                              //      log (subInitializeLog) dado que la secuencia debe
            //                                              //      ser independiente en cada Test.
        }

        //--------------------------------------------------------------------------------------------------------------
        private static String strNL(                        //NL + caracteres indentación.
            )
        {
            //                                              //Determina el NL+indentación que corresponde al block.
            if (
                intLevel < 0
                )
                Test.subAbort(Test.strTo(intLevel, "intNivel") + " should be 0 or positive");

            //                                              //Determina la cantidad de espacios para la indentación.
            int intSpaces;
            if (
                //                                          //El nivel excede el arreglo.
                intLevel >= arrintLevelSpaces.Length
                )
            {
                //                                          //Cuando no alcance usa el último valor.
                intSpaces = arrintLevelSpaces[arrintLevelSpaces.Length - 1];
            }
            else
            {
                intSpaces = arrintLevelSpaces[intLevel];
            }

            //                                              //Return NL with spacing required
            return Environment.NewLine + "".PadLeft(intSpaces);
        }

        //--------------------------------------------------------------------------------------------------------------
        private static void subBlockStart(                  //Genera los parámetros requerido para subToBlockFormat.
            //                                              //Solo se usa este método cuando block START-END.

            //                                              //NL + caracteres indentación.
            out String strNL_O,
            //                                              //Label for block START_??? y END_???. (this is ???).
            out String strLabel_O,
            //                                              //String to start block information
            out String strTo_O,
            //                                              //Text to describe the object
            String strText_I,
            //                                              //Object Id, if this block is por a bclass should be ""
            String strObjId_I
            )
        {
            strNL_O = strNL();

            //                                              //Asigna el siguiente nivel (lo regresa al cerrar block).
            intLevel = intLevel + 1;

            //                                              //Asigna una secuencia única.
            intStartEnd = intStartEnd + 1;

            //                                              //Determina la etiqueta que corresponde al block.
            char charLettersStartEnd;
            if (
                //                                          //El nivel excede las letras.
                intLevel >= strLETTERS_FOR_LEVEL.Length
                )
            {
                //                                          //Cuando no alcance ni la "Z" usa "*".
                charLettersStartEnd = '*';
            }
            else
            {
                charLettersStartEnd = strLETTERS_FOR_LEVEL[intLevel];
            }

            //                                              //Asigna la etiqueta StartEnd.
            strLabel_O = charLettersStartEnd.ToString() + intStartEnd;

            //                                              //Append Start of block.
            //                                              //If we are in intStartEnd 1, is is the beginig of a test
            //                                              //      (new log or previously was a WriteLine), the NewLine
            //                                              //      should not be include.
            String strNlForStart;
            if (
                intStartEnd == 1
                )
            {
                //                                          //Remove NewLine Mark
                strNlForStart = strNL_O.Substring(Environment.NewLine.Length);
            }
            else
            {
                strNlForStart = strNL_O;
            }
            strTo_O = strNlForStart + "##########>>>>>START_" + strLabel_O;
            strTo_O = strTo_O + strNL_O + strText_I + "(" + strObjId_I;
        }

        //--------------------------------------------------------------------------------------------------------------
        private static void subBlockEnd(                    //Termina el block StartEnd (regresa el nivel).
            //                                              //Solo se usa este método cuando block START-END.

            //                                              //NL + caracteres indentación.
            ref String strNL_IO,
            //                                              //String to append information
            ref String strTo_IO,
            String strLabel_I
            )
        {
            //                                          //End of Block
            strTo_IO = strTo_IO + ")" + strNL_IO + "##########<<<<<END_" + strLabel_I + Environment.NewLine;
            //                                              //Regresa el nivel.
            intLevel = intLevel - 1;

            strNL_IO = strNL();
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.ObjId set of methods to compute object id*/

        //                                                  //Implementación de apoyos apagar y pender la inclución del
        //                                                  //      HashCode en los ObjId (se substituye por ?).
        //                                                  //Esto es necesario para poder hacer pruebas dónde su log,
        //                                                  //      al repetirse la prueba, ES IDENTICO, esto será muy
        //                                                  //      útil en:
        //                                                  //Conversión del EW.Training Fase 0, 1 y 2 a otras
        //                                                  //      instancias de Object Oriented (Conversión de C# a
        //                                                  //      Java, Objective-C, Swift y otras en el futuro).
        //                                                  //Desarrollo de QEnabler, el User Aceptance Test del
        //                                                  //      QEnabler deberá ser convertirse a si mismo, la
        //                                                  //      nulificación de HashCode permitirá connfirmar que
        //                                                  //      en TODAS LAS PRUEBAS los resultados son identicos
        //                                                  //      pudiendo hacer una comparación automática.
        //                                                  //Se deberá confirmar que es ídentico el resultado de:
        //                                                  //a) Código C# desarrollado.
        //                                                  //b) Código C# generado por QEnabler.
        //                                                  //c) Código Java generado por QEnabler.
        //                                                  //d) Código Objective-C generado por QEnabler.
        //                                                  //e) Código Swift generado por QEnabler.
        //                                                  //Igualmente se deberá tomar el código Java, Objective-C y
        //                                                  //      Swift y confirmar que la generación de código a
        //                                                  //      partir de ellas en las 4 instancias producen
        //                                                  //      resultados identicos.

        //--------------------------------------------------------------------------------------------------------------
        /*CONSTANTS*/

        private const String strHASH_CODE_NULL = "?";


        //--------------------------------------------------------------------------------------------------------------
        /*STATIC VARIABLES*/

        //                                                  //Indicador de se incluir el HashCode en los ObjId y otra
        //                                                  //      información que es útil pero que sin embargo impide
        //                                                  //      que los logs sean comparables en forma automática.
        private static bool boolComparableLog;

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC CONSTRUCTOR SUPPORT METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        private static void subPrepareConstantsGetObjId(
            //                                              //Intialize para indicar ComparableLog.
            )
        {
            Test.boolComparableLog = false;
        }

        //--------------------------------------------------------------------------------------------------------------
        /*SHARED METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        public static bool boolComparableLogGet(
            )
        {
            return Test.boolComparableLog;
        }

        //--------------------------------------------------------------------------------------------------------------
        public static String strGetObjId( 
                                                            //Generate an object id.

            //                                              //str, prefixSize:HashCode.
            //                                              //prefix, data type prefix (int, arrint, lststr, etc.).
            //                                              //Size, [l], [l,m], [l,m,n] or "".

            Object obj_I
            )
        {
            if (
                obj_I == null
                )
                Test.subAbort(Test.strTo(obj_I, "obj_I") + " should have a value");

            Type typeObj = obj_I.GetType();
            if (
                !Test.boolIsStandard(typeObj, false)
                )
                Test.subAbort(Test.strTo(typeObj, "obj_I.GetType") + " is nonstandard type");

            //                                              //El HashCode puede ser nulificado (cambiado a ?)
            String strHashCode;
            if (
                boolComparableLog
                )
            {
                //                                          //Deja solo ?.
                strHashCode = Test.strHASH_CODE_NULL;
            }
            else
            {
                strHashCode = obj_I.GetHashCode().ToString();
            }

            return Test.strPrefix(obj_I.GetType()) + Test.strCollectionSize(obj_I) + ":" + strHashCode;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strPrefix(
            //                                              //Generate the object prefix corresponding to type.
            //                                              //Class Name has the structure:
            //                                              //1. AaaaaBbbbbCcccc (a could be a digit).
            //                                              //2. AaaaaBbbbbCcccc[], [,] or [,,] (array)
            //                                              //3. Dictionary`2[String,other FullName], KeyValuePair.
            //                                              //4. List`1[ns.FullName], Queue or Stack.
            //                                              //AaaaaBbbbbCcccc could be:
            //                                              //a. Bclass, Btuple or standard Enumerator.
            //                                              //b. Primitive or system type (included in
            //                                              //      arrstrPRIMITIVE_TYPE or arrstrSYSTEM_TYPE).

            //                                              //str.
            //                                              //1. prefix if is single type (prefix form PRIMITIVE or
            //                                              //      SYSTEM, or aaaaa taken from class name if is a
            //                                              //      Bclass, Btuple o Enum the name structure is
            //                                              //      "AaaaaBbbbbbCcccc, 'a' could be a digit.
            //                                              //2. arrprefix, arr1prefix or arr2prefix where "prefix" is
            //                                              //      the prefix corresponding to element type.
            //                                              //3. xxxarg, where "xxx" is prefix form GENERIC and "arg"
            //                                              //      is the prefix corresponding to argument type (first
            //                                              //      or second argument).
            //                                              //(see type_I definition).

            //                                              //1. Single type (not a array or generic type), Ex. str,
            //                                              //      syspath, cod, codcb, sepoodt, ... .
            //                                              //2. Array type ([], [,] or [, ,]), Ex. arrstr, arr2int,
            //                                              //      arrarrstr, arrdicstr, ... .
            //                                              //3. Generic type (1 or 2 arguments). Ex. dicstr, kvpint,
            //                                              //      lsttok, queuecod, ... .
            Type type_I
            )
        {
            String strPrefix;
            /*CASE*/
            if (
                type_I.IsArray
                )
            {
                strPrefix = "arr";

                int intRank = type_I.GetArrayRank();
                if (
                    intRank > 1
                    )
                {
                    //                                      //arr2 or arr3
                    strPrefix = strPrefix + intRank;
                }

                //                                          //arr?elem
                strPrefix = strPrefix + Test.strPrefix(type_I.GetElementType());
            }
            else if (
                type_I.IsGenericType
                )
            {
                //                                          //dic, kvp, lst, queue or stack
                strPrefix = Test.arrstrGENERIC_PREFIX[Array.BinarySearch(Test.arrstrGENERIC_TYPE, type_I.Name)];

                //                                          //Use the last argument
                Type[] arrtypeArgument = type_I.GetGenericArguments();
                strPrefix = strPrefix + Test.strPrefix(arrtypeArgument[arrtypeArgument.Length - 1]);
            }
            else
            {
                //                                          //Single form (not an array or generic)

                int intPrimitive = Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, type_I.Name);
                if (
                    //                                      //Is standard primitive type
                    intPrimitive >= 0
                    )
                {
                    strPrefix = Test.arrstrPRIMITIVE_PREFIX[intPrimitive];
                }
                else
                {
                    int intSystem = Array.BinarySearch(Test.arrstrSYSTEM_TYPE, type_I.Name);
                    if (
                        //                                  //Is standard system type
                        intSystem >= 0
                        )
                    {
                        strPrefix = Test.arrstrSYSTEM_PREFIX[intSystem];
                    }
                    else
                    {
                        //                                  //Should be user defined type (bclass, btuple, benum or
                        //                                  //      excep)
                        //                                  //Or a user defined enum

                        if (!(
                            type_I == typeof(BclassBaseClassAbstract) || 
                                type_I.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
                            type_I == typeof(BtupleBaseTupleAbstract) ||
                                type_I.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
                            type_I == typeof(BenumBaseEnumAbstract) || 
                                type_I.IsSubclassOf(typeof(BenumBaseEnumAbstract)) ||
                            type_I == typeof(Exception) || type_I.IsSubclassOf(typeof(Exception)) ||
                            type_I == typeof(Enum) || type_I.IsSubclassOf(typeof(Enum))
                            ))
                            Test.subAbort(Test.strTo(type_I, "type_I") +
                                " SOMETHING IS WRONG!!! at this point it should be bclass, btuple, enum or sysexcep");


                        //                                  //Is a user define type (bclass, btuple, enum or sysexcep).
                        //                                  //Search for B in AaaaaBbbbbCcccc.
                        //                                  //Start after first character (after 'A')
                        int intI = 1;
                        /*WHILE-DO*/
                        while (
                            (intI < type_I.Name.Length) &&
                            //                              //Between a-z or 0-9
                            Tools.boolIsDigitOrLetterLower(type_I.Name[intI])
                            )
                        {
                            intI = intI + 1;
                        }

                        //                                  //Subtract class prefix (aaaaa).
                        strPrefix = type_I.Name[0].ToString().ToLower() + type_I.Name.Substring(1, intI - 1);
                    }
                }
            }

            return strPrefix;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strCollectionSize(            //Generate the collection size.

            //                                              //str, "", [l], [l,m] o [l,m,n].

            Object obj_I
            )
        {
            Type typeCollection = obj_I.GetType();

            String strCollectionSize;
            /*CASE*/
            if (
                typeCollection.IsArray
                )
            {
                int intRank = typeCollection.GetArrayRank();
                /*CASE*/
                if (
                    intRank == 1
                    )
                {
                    //                                      //Generate [l].
                    strCollectionSize = Test.strArrSize(obj_I);
                }
                else if (
                    intRank == 2
                    )
                {
                    //                                      //Generate [l,m].
                    strCollectionSize = Test.strArr2Size(obj_I);
                }
                else
                {
                    //                                      //Rank 3
                    //                                      //Generate [l,m,n].
                    strCollectionSize = Test.strArr3Size(obj_I);
                }
                /*END-CASE*/
            }
            else if (
                typeCollection.IsGenericType
                )
            {
                if (
                    typeCollection.Name == Test.strGENERIC_DICTIONARY_TYPE
                    )
                {
                    //                                      //Generate [l].
                    strCollectionSize = Test.strDicSize(obj_I);
                }
                else if (
                    typeCollection.Name == Test.strGENERIC_KEYVALUEPAIR_TYPE
                    )
                {
                    //                                      //This is not a collection.
                    strCollectionSize = "";
                }
                else if (
                    typeCollection.Name == Test.strGENERIC_LIST_TYPE
                    )
                {
                    //                                      //Generate [l].
                    strCollectionSize = Test.strLstSize(obj_I);
                }
                else if (
                    typeCollection.Name == Test.strGENERIC_QUEUE_TYPE
                    )
                {
                    //                                      //Generate [l].
                    strCollectionSize = Test.strQueueSize(obj_I);
                }
                else if (
                    typeCollection.Name == Test.strGENERIC_STACK_TYPE
                    )
                {
                    //                                      //Generate [l].
                    strCollectionSize = Test.strStackSize(obj_I);
                }
                else
                {
                    Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! is nonstandard generic collection");

                    strCollectionSize = null;

                }
                /*END-CASE*/
            }
            else
            {
                //                                          //It is not a collection
                strCollectionSize = "";
            }
            /*END-CASE*/

            return strCollectionSize;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strArrSize(                   //Get [l].

            //                                              //str, [l].

            Object obj_I
            )
        {
            int intLength;
            /*CASE*/
            if (
                obj_I is int[]
                )
            {
                intLength = ((int[])obj_I).Length;
            }
            else if (
                obj_I is long[]
                )
            {
                intLength = ((long[])obj_I).Length;
            }
            else if (
                obj_I is double[]
                )
            {
                intLength = ((double[])obj_I).Length;
            }
            else if (
                obj_I is bool[]
                )
            {
                intLength = ((bool[])obj_I).Length;
            }
            else if (
                obj_I is char[]
                )
            {
                intLength = ((char[])obj_I).Length;
            }
            else if (
                obj_I is DateTime[]
                )
            {
                intLength = ((DateTime[])obj_I).Length;
            }
            else
            {
                Type typeObj = obj_I.GetType();
                Type typeElement = typeObj.GetElementType();
                if (
                    //                                      //Is primitive type
                    (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeElement.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing");

                intLength = ((Object[])obj_I).Length;
            }
            /*END-CASE*/

            return "[" + intLength + "]";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strArr2Size(                  //Get [l,m].

            //                                              //str, [l,m].

            Object obj_I
            )
        {
            int intLength0;
            int intLength1;
            /*CASE*/
            if (
                obj_I is int[,]
                )
            {
                int[,] arr2int = (int[,])obj_I;
                intLength0 = arr2int.GetLength(0);
                intLength1 = arr2int.GetLength(1);
            }
            else if (
                obj_I is long[,]
                )
            {
                long[,] arr2long = (long[,])obj_I;
                intLength0 = arr2long.GetLength(0);
                intLength1 = arr2long.GetLength(1);
            }
            else if (
                obj_I is double[,]
                )
            {
                double[,] arr2num = (double[,])obj_I;
                intLength0 = arr2num.GetLength(0);
                intLength1 = arr2num.GetLength(1);
            }
            else if (
                obj_I is bool[,]
                )
            {
                bool[,] arr2bool = (bool[,])obj_I;
                intLength0 = arr2bool.GetLength(0);
                intLength1 = arr2bool.GetLength(1);
            }
            else if (
                obj_I is char[,]
                )
            {
                char[,] arr2char = (char[,])obj_I;
                intLength0 = arr2char.GetLength(0);
                intLength1 = arr2char.GetLength(1);
            }
            else if (
                obj_I is DateTime[,]
                )
            {
                DateTime[,] arr2ts = (DateTime[,])obj_I;
                intLength0 = arr2ts.GetLength(0);
                intLength1 = arr2ts.GetLength(1);
            }
            else
            {
                Type typeObj = obj_I.GetType();
                Type typeElement = typeObj.GetElementType();
                if (
                    //                                      //Is primitive type
                    (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeElement.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing");

                Object[,] arr2obj = (Object[,])obj_I;
                intLength0 = arr2obj.GetLength(0);
                intLength1 = arr2obj.GetLength(1);
            }
            /*END-CASE*/

            return "[" + intLength0 + "," + intLength1 + "]";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strArr3Size(                  //Get [l,m,n].

            //                                              //str, [l,m,n].
            Object obj_I
            )
        {
            int intLength0;
            int intLength1;
            int intLength2;
            /*CASE*/
            if (
                obj_I is int[, ,]
                )
            {
                int[, ,] arr3int = (int[, ,])obj_I;
                intLength0 = arr3int.GetLength(0);
                intLength1 = arr3int.GetLength(1);
                intLength2 = arr3int.GetLength(2);
            }
            else if (
                obj_I is long[, ,]
                )
            {
                long[, ,] arr3long = (long[, ,])obj_I;
                intLength0 = arr3long.GetLength(0);
                intLength1 = arr3long.GetLength(1);
                intLength2 = arr3long.GetLength(2);
            }
            else if (
                obj_I is double[, ,]
                )
            {
                double[, ,] arr3num = (double[, ,])obj_I;
                intLength0 = arr3num.GetLength(0);
                intLength1 = arr3num.GetLength(1);
                intLength2 = arr3num.GetLength(2);
            }
            else if (
                obj_I is bool[, ,]
                )
            {
                bool[, ,] arr3bool = (bool[, ,])obj_I;
                intLength0 = arr3bool.GetLength(0);
                intLength1 = arr3bool.GetLength(1);
                intLength2 = arr3bool.GetLength(2);
            }
            else if (
                obj_I is char[, ,]
                )
            {
                char[, ,] arr3char = (char[, ,])obj_I;
                intLength0 = arr3char.GetLength(0);
                intLength1 = arr3char.GetLength(1);
                intLength2 = arr3char.GetLength(2);
            }
            else if (
                obj_I is DateTime[, ,]
                )
            {
                DateTime[, ,] arr3ts = (DateTime[, ,])obj_I;
                intLength0 = arr3ts.GetLength(0);
                intLength1 = arr3ts.GetLength(1);
                intLength2 = arr3ts.GetLength(2);
            }
            else
            {
                Type typeObj = obj_I.GetType();
                Type typeElement = typeObj.GetElementType();
                if (
                    //                                      //Is primitive type
                    (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeElement.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") + 
                        " SOMETHING IS WRONG!!! a branch in previous case is missing");

                Object[, ,] arr3obj = (Object[, ,])obj_I;
                intLength0 = arr3obj.GetLength(0);
                intLength1 = arr3obj.GetLength(1);
                intLength2 = arr3obj.GetLength(2);
            }
            /*END-CASE*/

            return "[" + intLength0 + "," + intLength1 + "," + intLength2 + "]";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strLstSize(                   //Get [l].

            //                                              //str, [l].

            Object obj_I
            )
        {
            String strCount;
            /*CASE*/
            if (
                obj_I is List<int>
                )
            {
                strCount = ((List<int>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<long>
                )
            {
                strCount = ((List<long>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<double>
                )
            {
                strCount = ((List<double>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<bool>
                )
            {
                strCount = ((List<bool>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<char>
                )
            {
                strCount = ((List<char>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<String>
                )
            {
                strCount = ((List<String>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<DateTime>
                )
            {
                strCount = ((List<DateTime>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<Type>
                )
            {
                strCount = ((List<Type>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<DirectoryInfo>
                )
            {
                strCount = ((List<DirectoryInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<FileInfo>
                )
            {
                strCount = ((List<FileInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<StreamReader>
                )
            {
                strCount = ((List<StreamReader>)obj_I).Count.ToString();
            }
            else if (
                obj_I is List<StreamWriter>
                )
            {
                strCount = ((List<StreamWriter>)obj_I).Count.ToString();
            }
            else
            {
                Type typeObj = obj_I.GetType();
                Type typeArgument = typeObj.GetGenericArguments()[0];
                if (
                    //                                      //Is primitive type
                    (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
                if (
                    //                                      //Is system type
                    (Array.BinarySearch(Test.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

                //                                          /Can note get count.
                strCount = "?";
            }
            /*END-CASE*/

            return "[" + strCount + "]";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strQueueSize(                 //Get [l].

            //                                              //str, [l].

            Object obj_I
            )
        {
            String strCount;
            /*CASE*/
            if (
                obj_I is Queue<int>
                )
            {
                strCount = ((Queue<int>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<long>
                )
            {
                strCount = ((Queue<long>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<double>
                )
            {
                strCount = ((Queue<double>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<bool>
                )
            {
                strCount = ((Queue<bool>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<char>
                )
            {
                strCount = ((Queue<char>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<String>
                )
            {
                strCount = ((Queue<String>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<DateTime>
                )
            {
                strCount = ((Queue<DateTime>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<Type>
                )
            {
                strCount = ((Queue<Type>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<DirectoryInfo>
                )
            {
                strCount = ((Queue<DirectoryInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<FileInfo>
                )
            {
                strCount = ((Queue<FileInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<StreamReader>
                )
            {
                strCount = ((Queue<StreamReader>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Queue<StreamWriter>
                )
            {
                strCount = ((Queue<StreamWriter>)obj_I).Count.ToString();
            }
            else
            {
                Type typeObj = obj_I.GetType();
                Type typeArgument = typeObj.GetGenericArguments()[0];
                if (
                    //                                      //Is primitive type
                    (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
                if (
                    //                                      //Is system type
                    (Array.BinarySearch(Test.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

                //                                          /Can note get count.
                strCount = "?";
            }
            /*END-CASE*/

            return "[" + strCount + "]";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strStackSize(                 //Get [l].

            //                                              //str, [l].

            Object obj_I
            )
        {
            String strCount;
            /*CASE*/
            if (
                obj_I is Stack<int>
                )
            {
                strCount = ((Stack<int>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<long>
                )
            {
                strCount = ((Stack<long>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<double>
                )
            {
                strCount = ((Stack<double>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<bool>
                )
            {
                strCount = ((Stack<bool>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<char>
                )
            {
                strCount = ((Stack<char>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<String>
                )
            {
                strCount = ((Stack<String>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<DateTime>
                )
            {
                strCount = ((Stack<DateTime>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<Type>
                )
            {
                strCount = ((Stack<Type>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<DirectoryInfo>
                )
            {
                strCount = ((Stack<DirectoryInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<FileInfo>
                )
            {
                strCount = ((Stack<FileInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<StreamReader>
                )
            {
                strCount = ((Stack<StreamReader>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Stack<StreamWriter>
                )
            {
                strCount = ((Stack<StreamWriter>)obj_I).Count.ToString();
            }
            else
            {
                Type typeObj = obj_I.GetType();
                Type typeArgument = typeObj.GetGenericArguments()[0];
                if (
                    //                                      //Is primitive type
                    (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
                if (
                    //                                      //Is system type
                    (Array.BinarySearch(Test.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

                //                                          /Can note get count.
                strCount = "?";
            }
            /*END-CASE*/

            return "[" + strCount + "]";
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private static String strDicSize(                   //Get [l].

            //                                              //str, [l].

            Object obj_I
            )
        {
            String strCount;
            /*CASE*/
            if (
                obj_I is Dictionary<String, int>
                )
            {
                strCount = ((Dictionary<String, int>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, long>
                )
            {
                strCount = ((Dictionary<String, long>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, double>
                )
            {
                strCount = ((Dictionary<String, double>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, bool>
                )
            {
                strCount = ((Dictionary<String, bool>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, char>
                )
            {
                strCount = ((Dictionary<String, char>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, String>
                )
            {
                strCount = ((Dictionary<String, String>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, DateTime>
                )
            {
                strCount = ((Dictionary<String, DateTime>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, Type>
                )
            {
                strCount = ((Dictionary<String, Type>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, DirectoryInfo>
                )
            {
                strCount = ((Dictionary<String, DirectoryInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, FileInfo>
                )
            {
                strCount = ((Dictionary<String, FileInfo>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, StreamReader>
                )
            {
                strCount = ((Dictionary<String, StreamReader>)obj_I).Count.ToString();
            }
            else if (
                obj_I is Dictionary<String, StreamWriter>
                )
            {
                strCount = ((Dictionary<String, StreamWriter>)obj_I).Count.ToString();
            }
            else
            {
                Type typeObj = obj_I.GetType();
                Type typeArgument = typeObj.GetGenericArguments()[1];
                if (
                    //                                      //Is primitive type
                    (Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrPRIMITIVE_TYPE, "Test.arrstrPRIMITIVE_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") + 
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
                if (
                    //                                      //Is system type
                    (Array.BinarySearch(Test.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
                    )
                    Test.subAbort(Test.strTo(Test.arrstrSYSTEM_TYPE, "Test.arrstrSYSTEM_TYPE") + ", " +
                        Test.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

                //                                          //Can not get count.
                strCount = "?";
            }
            /*END-CASE*/

            return "[" + strCount + "]";
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.Boxing set of private methods to box primitives*/
        //--------------------------------------------------------------------------------------------------------------
        private static void subfunConvertAndBox(            //Convert and box if required.
            //                                              //Parameters have the following posibilities:
            //                                              //[ objMain_O       objKey_O        obj_I
            //                                              //  oppp              -             ppp-primitive(int, ...) 
            //                                              //  arr?oppp          -             arr?ppp    
            //                                              //  arroppp           -             lstppp    
            //                                              //  arropppValue    arrstrKey       dicppp    
            //                                              //  opppValue       strKey          kvpppp    
            //                                              //
            //                                              //  yyy               -             yyy-system(str,ts,...)
            //                                              //  arr?yyy           -             arr?yyy
            //                                              //  arryyy            -             lstyyy
            //                                              //  arryyyValue     arrstrKey       dicyyy
            //                                              //  yyyValue        strKey          kvpyyy
            //                                              //
            //                                              //  eee               -             eee-enum(all Enum's)
            //                                              //  (do not work)                   arr?eee
            //                                              //  (do not work)                   lsteee
            //                                              //  (do not work)                   diceee
            //                                              //  (do not work)                   kvpeee
            //                                              //
            //                                              //  ccc               -             ccc-bclass(+subclasses)
            //                                              //  arr?ccc           -             arr?ccc
            //                                              //  (do not work)                   lstccc
            //                                              //  (do not work)                   dicccc
            //                                              //  (do not work)                   kvpccc
            //                                              //
            //                                              //  ttt               -             ttt-btuple(+subclasses)
            //                                              //  arr?ttt           -             arr?ttt
            //                                              //  (do not work)                   lstttt
            //                                              //  (do not work)                   dicttt
            //                                              //  (do not work)                   kvpttt
            //                                              //
            //                                              //  eee               -             eee-benum(+subclasses)
            //                                              //  arr?eee           -             arr?ttt
            //                                              //  (do not work)                   lstttt
            //                                              //  (do not work)                   dicttt
            //                                              //  (do not work)                   kvpttt
            //                                              //
            //                                              //  xxx               -             xxx-excep(all Exception)
            //                                              //  arr?xxx           -             arr?xxx
            //                                              //  (do not work)                   lstxxx
            //                                              //  (do not work)                   dicxxx
            //                                              //  (do not work)                   kvpxxx
            //                                              //]
            //                                              //lst, could be queue or stack.

            //                                              //obj or arrobj
            out Object objMain_O,
            //                                              //arrstrKey or strKey, only for dic or kvp (2 atribute
            //                                              //      generic), other will return null
            out Object objKey_O,
            //                                              //Any standard object, except generic with atribute enum,
            //                                              //      bclass, btuple or excep.
            Object obj_I
            )
        {
            //                                              //To easy code
            Type typeObj = obj_I.GetType();
            Type typeContent;
            /*CASE*/
            if (
                typeObj.IsArray
                )
            {
                typeContent = typeObj.GetElementType();
            }
            else if (
                typeObj.IsGenericType
                )
            {
                //                                          //Get needed argument type, 1 or 2 arguments (takes last
                //                                          //      argument)
                Type[] arrtypeArguments = typeObj.GetGenericArguments();
                typeContent = arrtypeArguments[arrtypeArguments.Length - 1];
            }
            else
            {
                //                                          //Single type
                typeContent = typeObj;
            }
            /*END-CASE*/

            //                                              //Select process.
            //                                              //Primitive and system types will process any structure
            //                                              //      (single, array or generic).
            //                                              //Enum will process only single structures.
            //                                              //bclass, btuple, benum and sysexcep will process only
            //                                              //      single and array structures.
            /*CASE*/
            if (
                //                                          //Is any form (single, array or generic) of primiteve type
                Array.BinarySearch(Test.arrstrPRIMITIVE_TYPE, typeContent.Name) >= 0
                )
            {
                Test.subfunConvertAndBoxPrimitive(out objMain_O, out objKey_O, obj_I, typeContent);
            }
            else if (
                //                                          //Is a any form (single, array or generic) of system type
                Array.BinarySearch(Test.arrstrSYSTEM_TYPE, typeContent.Name) >= 0
                )
            {
                //                                          //Only generic form need to be converted

                if (
                    typeObj.IsGenericType
                    )
                {
                    Test.subfunConvertSystemType(out objMain_O, out objKey_O, obj_I, typeContent);
                }
                else
                {
                    //                                      //No convertion is required for single or array form

                    objMain_O = obj_I;
                    objKey_O = null;
                }
            }
            else if (
                //                                          //Any user defined Enum
                (typeContent == typeof(Enum)) || typeContent.IsSubclassOf(typeof(Enum))
                )
            {
                //                                          //Generic form can not be processed
                //                                          //This type can not be processed, please convert 1 argument
                //                                          //      generic to array, dic to 2 arrays and kvp to its 2
                //                                          //      components and call the correct strTo method

                if (
                    typeObj.IsArray || typeObj.IsGenericType
                    )
                    Test.subAbort(Test.strTo(typeObj, "obj_I.GetType") +
                        " array or generic type with argument enum should be implemented as a class  " +
                        "(BenumBaseEnumAbstract)");

                //                                          //No convertion is required for single or array form
                objMain_O = obj_I;
                objKey_O = null;
            }
            else if (
                //                                          //The agrument is any user defined class (bclass, btuple,
                //                                          //      enum or sysexcep)
                (typeContent == typeof(BclassBaseClassAbstract)) ||
                    typeContent.IsSubclassOf(typeof(BclassBaseClassAbstract)) ||
                (typeContent == typeof(BtupleBaseTupleAbstract)) ||
                    typeContent.IsSubclassOf(typeof(BtupleBaseTupleAbstract)) ||
                (typeContent == typeof(BenumBaseEnumAbstract)) || 
                    typeContent.IsSubclassOf(typeof(BenumBaseEnumAbstract)) ||
                (typeContent == typeof(Exception)) || typeContent.IsSubclassOf(typeof(Exception))
                )
            {
                //                                          //Generic form can not be processed
                //                                          //This type can not be processed, please convert 1 argument
                //                                          //      generic to array, dic to 2 arrays and kvp to its 2
                //                                          //      components and call the correct strTo method

                if (
                    typeObj.IsGenericType
                    )
                    Test.subAbort(Test.strTo(typeObj, "obj_I.GetType") +
                        " generic type with argument bclass, btuple or sysexcep can not be processed in this " +
                        "method, you should produce a arrobjValue and arrstrKey and call the correct strTo method");

                //                                          //No convertion is required for single or array form
                objMain_O = obj_I;
                objKey_O = null;
            }
            else
            {
                Test.subAbort(Test.strTo(typeObj, "obj_I.GetType") + Test.strTo(typeContent, "typeContent") + 
                    " SOMETHING IS WRONG!!!, the content seems nonstandard type");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertAndBoxPrimitive(   //Convert and box primitives.
            //                                              //Parameters have the following posibilities:
            //                                              //[ Main            Key             Object
            //                                              //  oppp              -             appp-primitive(int, ...) 
            //                                              //  arr?oppp          -             arr?ppp    
            //                                              //  arroppp           -             lstppp    
            //                                              //  arropppValue    arrstrKey       dicppp    
            //                                              //  opppValue       strKey          kvpppp
            //                                              //]

            //                                              //obj or arrobj
            out Object objMain_O,
            //                                              //arrstrKey or strKey, only for 2 atribute generic, other
            //                                              //      will return null
            out Object objKey_O,
            //                                              //Any standard object, except generic with atribute enum,
            //                                              //      bclass or btuple.
            Object obj_I,
            Type typeContent_I
            )
        {
            //                                              //Select process.
            /*CASE*/
            if (
                typeContent_I == typeof(int)
                )
            {
                Test.subfunConvertAndBoxInt(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(long)
                )
            {
                Test.subfunConvertAndBoxLong(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(double)
                )
            {
                Test.subfunConvertAndBoxNum(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(bool)
                )
            {
                Test.subfunConvertAndBoxBool(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(char)
                )
            {
                Test.subfunConvertAndBoxChar(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(DateTime)
                )
            {
                Test.subfunConvertAndBoxTs(out objMain_O, out objKey_O, obj_I);
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") + " SOMETHING IS WRONG!!!," +
                    " it seems to be any form (single, array or generic) of nonstandard primitive");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertAndBoxInt(        //Convert and box int.

            //                                              //oint, oint[], oint[,] or oint[, ,].
            //                                              //lstint, ... (one atribute generic) will convert to
            //                                              //      oint[].
            //                                              //dicint will convert to ointValues[] and strKeys[].
            //                                              //kvpint will convert to ointValue and strKey.
            out Object objMain_O,
            //                                              //null, str[] (keys from dic) or or str (key form kvp).
            out Object objKey_O,
            //                                              //int type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is int
                )
            {
                //                                          //Box primitive
                OintBox oint = new OintBox((int)obj_I);

                objMain_O = oint;
                objKey_O = null;
            }
            else if (
                obj_I is int[]
                )
            {
                int[] arrint = (int[])obj_I;

                //                                          //Box primitives
                OintBox[] arroint = new OintBox[arrint.Length];
                for (int intI = 0; intI < arrint.Length; intI = intI + 1)
                {
                    arroint[intI] = new OintBox(arrint[intI]);
                }

                objMain_O = arroint;
                objKey_O = null;
            }
            else if (
                obj_I is List<int>
                )
            {
                List<int> lstint = (List<int>)obj_I;

                //                                          //Box primitives
                OintBox[] arroint = new OintBox[lstint.Count];
                for (int intI = 0; intI < lstint.Count; intI = intI + 1)
                {
                    arroint[intI] = new OintBox(lstint[intI]);
                }

                objMain_O = arroint;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<int>
                )
            {
                Queue<int> queueint = (Queue<int>)obj_I;

                //                                          //Box primitives
                OintBox[] arroint = new OintBox[queueint.Count];
                int intI = 0;
                foreach (int intX in queueint)
                {
                    arroint[intI] = new OintBox(intX);

                    intI = intI + 1;
                }

                objMain_O = arroint;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<int>
                )
            {
                Stack<int> stackint = (Stack<int>)obj_I;

                //                                          //Box primitives
                OintBox[] arroint = new OintBox[stackint.Count];
                int intI = 0;
                foreach (int intX in stackint)
                {
                    arroint[intI] = new OintBox(intX);

                    intI = intI + 1;
                }

                objMain_O = arroint;
                objKey_O = null;
            }
            else if (
                obj_I is int[,]
                )
            {
                int[,] arr2int = (int[,])obj_I;

                //                                          //Box primitives
                OintBox[,] arr2oint = new OintBox[arr2int.GetLength(0), arr2int.GetLength(1)];
                for (int intI = 0; intI < arr2int.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr2int.GetLength(1); intJ = intJ + 1)
                    {
                        arr2oint[intI, intJ] = new OintBox(arr2int[intI, intJ]);
                    }
                }

                objMain_O = arr2oint;
                objKey_O = null;
            }
            else if (
                obj_I is int[, ,]
                )
            {
                int[, ,] arr3int = (int[, ,])obj_I;

                //                                          //Box primitives
                OintBox[, ,] arr3oint = new OintBox[arr3int.GetLength(0), arr3int.GetLength(1), arr3int.GetLength(2)];
                for (int intI = 0; intI < arr3int.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr3int.GetLength(1); intJ = intJ + 1)
                    {
                        for (int intK = 0; intK < arr3int.GetLength(2); intK = intK + 1)
                        {
                            arr3oint[intI, intJ, intK] = new OintBox(arr3int[intI, intJ, intK]);
                        }
                    }
                }

                objMain_O = arr3oint;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, int>
                )
            {
                Dictionary<String, int> dicint = (Dictionary<String, int>)obj_I;

                //                                          //Convert to arrays.
                int[] arrintValues = new int[dicint.Count];
                dicint.Values.CopyTo(arrintValues, 0);
                String[] arrstrKeys = new String[dicint.Count];
                dicint.Keys.CopyTo(arrstrKeys, 0);

                //                                          //Box primitives
                OintBox[] arrointValues = new OintBox[arrintValues.Length];
                for (int intI = 0; intI < arrintValues.Length; intI = intI + 1)
                {
                    arrointValues[intI] = new OintBox(arrintValues[intI]);
                }

                objMain_O = arrointValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, int>
                )
            {
                KeyValuePair<String, int> kvpint = (KeyValuePair<String, int>)obj_I;

                //                                          //Extract attributes.
                int intValue = kvpint.Value;
                String strKey = kvpint.Key;

                //                                          //Box primitive
                OintBox ointValue = new OintBox(intValue);

                objMain_O = ointValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other int types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertAndBoxLong(        //Convert and box long.

            //                                              //olong, olong[], olong[,] or olong[, ,].
            //                                              //lstlong, ... (one atribute generic) will convert to
            //                                              //      olong[].
            //                                              //diclong will convert to olongValues[] and strKeys[].
            //                                              //kvplong will convert to olongValue and strKey.
            out Object objMain_O,
            //                                              //null, str[] (keys from dic) or or str (key form kvp).
            out Object objKey_O,
            //                                              //long type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is long
                )
            {
                //                                          //Box primitive
                OlongBox olong = new OlongBox((long)obj_I);

                objMain_O = olong;
                objKey_O = null;
            }
            else if (
                obj_I is long[]
                )
            {
                long[] arrlong = (long[])obj_I;

                //                                          //Box primitives
                OlongBox[] arrolong = new OlongBox[arrlong.Length];
                for (int intI = 0; intI < arrlong.Length; intI = intI + 1)
                {
                    arrolong[intI] = new OlongBox(arrlong[intI]);
                }

                objMain_O = arrolong;
                objKey_O = null;
            }
            else if (
                obj_I is List<long>
                )
            {
                List<long> lstlong = (List<long>)obj_I;

                //                                          //Box primitives
                OlongBox[] arrolong = new OlongBox[lstlong.Count];
                for (int intI = 0; intI < lstlong.Count; intI = intI + 1)
                {
                    arrolong[intI] = new OlongBox(lstlong[intI]);
                }

                objMain_O = arrolong;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<long>
                )
            {
                Queue<long> queuelong = (Queue<long>)obj_I;

                //                                          //Box primitives
                OlongBox[] arrolong = new OlongBox[queuelong.Count];
                int intI = 0;
                foreach (long longX in queuelong)
                {
                    arrolong[intI] = new OlongBox(longX);

                    intI = intI + 1;
                }

                objMain_O = arrolong;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<long>
                )
            {
                Stack<long> stacklong = (Stack<long>)obj_I;

                //                                          //Box primitives
                OlongBox[] arrolong = new OlongBox[stacklong.Count];
                int intI = 0;
                foreach (long longX in stacklong)
                {
                    arrolong[intI] = new OlongBox(longX);

                    intI = intI + 1;
                }

                objMain_O = arrolong;
                objKey_O = null;
            }
            else if (
                obj_I is long[,]
                )
            {
                long[,] arr2long = (long[,])obj_I;

                //                                          //Box primitives
                OlongBox[,] arr2olong = new OlongBox[arr2long.GetLength(0), arr2long.GetLength(1)];
                for (int intI = 0; intI < arr2long.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr2long.GetLength(1); intJ = intJ + 1)
                    {
                        arr2olong[intI, intJ] = new OlongBox(arr2long[intI, intJ]);
                    }
                }

                objMain_O = arr2olong;
                objKey_O = null;
            }
            else if (
                obj_I is long[, ,]
                )
            {
                long[, ,] arr3long = (long[, ,])obj_I;

                //                                          //Box primitives
                OlongBox[, ,] arr3olong = 
                    new OlongBox[arr3long.GetLength(0), arr3long.GetLength(1), arr3long.GetLength(2)];
                for (int intI = 0; intI < arr3long.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr3long.GetLength(1); intJ = intJ + 1)
                    {
                        for (int intK = 0; intK < arr3long.GetLength(2); intK = intK + 1)
                        {
                            arr3olong[intI, intJ, intK] = new OlongBox(arr3long[intI, intJ, intK]);
                        }
                    }
                }

                objMain_O = arr3olong;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, long>
                )
            {
                Dictionary<String, long> diclong = (Dictionary<String, long>)obj_I;

                //                                          //Convert to arrays.
                long[] arrlongValues = new long[diclong.Count];
                diclong.Values.CopyTo(arrlongValues, 0);
                String[] arrstrKeys = new String[diclong.Count];
                diclong.Keys.CopyTo(arrstrKeys, 0);

                //                                          //Box primitives
                OlongBox[] arrolongValues = new OlongBox[arrlongValues.Length];
                for (int intI = 0; intI < arrlongValues.Length; intI = intI + 1)
                {
                    arrolongValues[intI] = new OlongBox(arrlongValues[intI]);
                }

                objMain_O = arrolongValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, long>
                )
            {
                KeyValuePair<String, long> kvplong = (KeyValuePair<String, long>)obj_I;

                //                                          //Extract attributes.
                long longValue = kvplong.Value;
                String strKey = kvplong.Key;

                //                                          //Box primitive
                OlongBox olongValue = new OlongBox(longValue);

                objMain_O = olongValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other long types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertAndBoxNum(         //Convert and box num.

            //                                              //onum, onum[], onum[,] or onum[, ,].
            //                                              //lstnum, ... (one atribute generic) will convert to onum[].
            //                                              //dicnum will convert to onumValues[] and strKeys[].
            //                                              //kvpnum will convert to onumValue and strKey.
            out Object objMain_O,
            //                                              //null, str[] (keys from dic) or or str (key form kvp).
            out Object objKey_O,
            //                                              //num type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is double
                )
            {
                //                                          //Box primitive
                OnumBox onum = new OnumBox((double)obj_I);

                objMain_O = onum;
                objKey_O = null;
            }
            else if (
                obj_I is double[]
                )
            {
                double[] arrnum = (double[])obj_I;

                //                                          //Box primitives
                OnumBox[] arronum = new OnumBox[arrnum.Length];
                for (int intI = 0; intI < arrnum.Length; intI = intI + 1)
                {
                    arronum[intI] = new OnumBox(arrnum[intI]);
                }

                objMain_O = arronum;
                objKey_O = null;
            }
            else if (
                obj_I is List<double>
                )
            {
                List<double> lstnum = (List<double>)obj_I;

                //                                          //Box primitives
                OnumBox[] arronum = new OnumBox[lstnum.Count];
                for (int intI = 0; intI < lstnum.Count; intI = intI + 1)
                {
                    arronum[intI] = new OnumBox(lstnum[intI]);
                }

                objMain_O = arronum;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<double>
                )
            {
                Queue<double> queuenum = (Queue<double>)obj_I;

                //                                          //Box primitives
                OnumBox[] arronum = new OnumBox[queuenum.Count];
                int intI = 0;
                foreach (double num in queuenum)
                {
                    arronum[intI] = new OnumBox(num);

                    intI = intI + 1;
                }

                objMain_O = arronum;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<double>
                )
            {
                Stack<double> stacknum = (Stack<double>)obj_I;

                //                                          //Box primitives
                OnumBox[] arronum = new OnumBox[stacknum.Count];
                int intI = 0;
                foreach (double num in stacknum)
                {
                    arronum[intI] = new OnumBox(num);

                    intI = intI + 1;
                }

                objMain_O = arronum;
                objKey_O = null;
            }
            else if (
                obj_I is double[,]
                )
            {
                double[,] arr2num = (double[,])obj_I;

                //                                          //Box primitives
                OnumBox[,] arr2onum = new OnumBox[arr2num.GetLength(0), arr2num.GetLength(1)];
                for (int intI = 0; intI < arr2num.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr2num.GetLength(1); intJ = intJ + 1)
                    {
                        arr2onum[intI, intJ] = new OnumBox(arr2num[intI, intJ]);
                    }
                }

                objMain_O = arr2onum;
                objKey_O = null;
            }
            else if (
                obj_I is double[, ,]
                )
            {
                double[, ,] arr3num = (double[, ,])obj_I;

                //                                          //Box primitives
                OnumBox[, ,] arr3onum = new OnumBox[arr3num.GetLength(0), arr3num.GetLength(1), arr3num.GetLength(2)];
                for (int intI = 0; intI < arr3num.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr3num.GetLength(1); intJ = intJ + 1)
                    {
                        for (int intK = 0; intK < arr3num.GetLength(2); intK = intK + 1)
                        {
                            arr3onum[intI, intJ, intK] = new OnumBox(arr3num[intI, intJ, intK]);
                        }
                    }
                }

                objMain_O = arr3onum;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, double>
                )
            {
                Dictionary<String, double> dicnum = (Dictionary<String, double>)obj_I;

                //                                          //Convert to arrays.
                double[] arrnumValues = new double[dicnum.Count];
                dicnum.Values.CopyTo(arrnumValues, 0);
                String[] arrstrKeys = new String[dicnum.Count];
                dicnum.Keys.CopyTo(arrstrKeys, 0);

                //                                          //Box primitives
                OnumBox[] arronumValues = new OnumBox[arrnumValues.Length];
                for (int intI = 0; intI < arrnumValues.Length; intI = intI + 1)
                {
                    arronumValues[intI] = new OnumBox(arrnumValues[intI]);
                }

                objMain_O = arronumValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, double>
                )
            {
                KeyValuePair<String, double> kvpnum = (KeyValuePair<String, double>)obj_I;

                //                                          //Extract attributes.
                double numValue = kvpnum.Value;
                String strKey = kvpnum.Key;

                //                                          //Box primitive
                OnumBox onumValue = new OnumBox(numValue);

                objMain_O = onumValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other num types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertAndBoxBool(        //Convert and box bool.

            //                                              //obool, obool[], obool[,] or obool[, ,].
            //                                              //lstbool, ... (one atribute generic) will convert to
            //                                              //      obool[].
            //                                              //dicbool will convert to oboolValues[] and strKeys[].
            //                                              //kvpbool will convert to oboolValue and strKey.
            out Object objMain_O,
            //                                              //null, str[] (keys from dic) or or str (key form kvp).
            out Object objKey_O,
            //                                              //bool type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is bool
                )
            {
                //                                          //Box primitive
                OboolBox obool = new OboolBox((bool)obj_I);

                objMain_O = obool;
                objKey_O = null;
            }
            else if (
                obj_I is bool[]
                )
            {
                bool[] arrbool = (bool[])obj_I;

                //                                          //Box primitives
                OboolBox[] arrobool = new OboolBox[arrbool.Length];
                for (int intI = 0; intI < arrbool.Length; intI = intI + 1)
                {
                    arrobool[intI] = new OboolBox(arrbool[intI]);
                }

                objMain_O = arrobool;
                objKey_O = null;
            }
            else if (
                obj_I is List<bool>
                )
            {
                List<bool> lstbool = (List<bool>)obj_I;

                //                                          //Box primitives
                OboolBox[] arrobool = new OboolBox[lstbool.Count];
                for (int intI = 0; intI < lstbool.Count; intI = intI + 1)
                {
                    arrobool[intI] = new OboolBox(lstbool[intI]);
                }

                objMain_O = arrobool;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<bool>
                )
            {
                Queue<bool> queuebool = (Queue<bool>)obj_I;

                //                                          //Box primitives
                OboolBox[] arrobool = new OboolBox[queuebool.Count];
                int intI = 0;
                foreach (bool boolX in queuebool)
                {
                    arrobool[intI] = new OboolBox(boolX);

                    intI = intI + 1;
                }

                objMain_O = arrobool;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<bool>
                )
            {
                Stack<bool> stackbool = (Stack<bool>)obj_I;

                //                                          //Box primitives
                OboolBox[] arrobool = new OboolBox[stackbool.Count];
                int intI = 0;
                foreach (bool boolX in stackbool)
                {
                    arrobool[intI] = new OboolBox(boolX);

                    intI = intI + 1;
                }

                objMain_O = arrobool;
                objKey_O = null;
            }
            else if (
                obj_I is bool[,]
                )
            {
                bool[,] arr2bool = (bool[,])obj_I;

                //                                          //Box primitives
                OboolBox[,] arr2obool = new OboolBox[arr2bool.GetLength(0), arr2bool.GetLength(1)];
                for (int intI = 0; intI < arr2bool.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr2bool.GetLength(1); intJ = intJ + 1)
                    {
                        arr2obool[intI, intJ] = new OboolBox(arr2bool[intI, intJ]);
                    }
                }

                objMain_O = arr2obool;
                objKey_O = null;
            }
            else if (
                obj_I is bool[, ,]
                )
            {
                bool[, ,] arr3bool = (bool[, ,])obj_I;

                //                                          //Box primitives
                OboolBox[, ,] arr3obool = 
                    new OboolBox[arr3bool.GetLength(0), arr3bool.GetLength(1), arr3bool.GetLength(2)];
                for (int intI = 0; intI < arr3bool.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr3bool.GetLength(1); intJ = intJ + 1)
                    {
                        for (int intK = 0; intK < arr3bool.GetLength(2); intK = intK + 1)
                        {
                            arr3obool[intI, intJ, intK] = new OboolBox(arr3bool[intI, intJ, intK]);
                        }
                    }
                }

                objMain_O = arr3obool;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, bool>
                )
            {
                Dictionary<String, bool> dicbool = (Dictionary<String, bool>)obj_I;

                //                                          //Convert to arrays.
                bool[] arrboolValues = new bool[dicbool.Count];
                dicbool.Values.CopyTo(arrboolValues, 0);
                String[] arrstrKeys = new String[dicbool.Count];
                dicbool.Keys.CopyTo(arrstrKeys, 0);

                //                                          //Box primitives
                OboolBox[] arroboolValues = new OboolBox[arrboolValues.Length];
                for (int intI = 0; intI < arrboolValues.Length; intI = intI + 1)
                {
                    arroboolValues[intI] = new OboolBox(arrboolValues[intI]);
                }

                objMain_O = arroboolValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, bool>
                )
            {
                KeyValuePair<String, bool> kvpbool = (KeyValuePair<String, bool>)obj_I;

                //                                          //Extract attributes.
                bool boolValue = kvpbool.Value;
                String strKey = kvpbool.Key;

                //                                          //Box primitive
                OboolBox oboolValue = new OboolBox(boolValue);

                objMain_O = oboolValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other bool types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertAndBoxChar(        //Convert and box char.

            //                                              //ochar, ochar[], ochar[,] or ochar[, ,].
            //                                              //lstchar, ... (one atribute generic) will convert to
            //                                              //      ochar[].
            //                                              //dicchar will convert to ocharValues[] and strKeys[].
            //                                              //kvpchar will convert to ocharValue and strKey.
            out Object objMain_O,
            //                                              //null, str[] (keys from dic) or or str (key form kvp).
            out Object objKey_O,
            //                                              //char type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is char
                )
            {
                //                                          //Box primitive
                OcharBox ochar = new OcharBox((char)obj_I);

                objMain_O = ochar;
                objKey_O = null;
            }
            else if (
                obj_I is char[]
                )
            {
                char[] arrchar = (char[])obj_I;

                //                                          //Box primitives
                OcharBox[] arrochar = new OcharBox[arrchar.Length];
                for (int intI = 0; intI < arrchar.Length; intI = intI + 1)
                {
                    arrochar[intI] = new OcharBox(arrchar[intI]);
                }

                objMain_O = arrochar;
                objKey_O = null;
            }
            else if (
                obj_I is List<char>
                )
            {
                List<char> lstchar = (List<char>)obj_I;

                //                                          //Box primitives
                OcharBox[] arrochar = new OcharBox[lstchar.Count];
                for (int intI = 0; intI < lstchar.Count; intI = intI + 1)
                {
                    arrochar[intI] = new OcharBox(lstchar[intI]);
                }

                objMain_O = arrochar;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<char>
                )
            {
                Queue<char> queuechar = (Queue<char>)obj_I;

                //                                          //Box primitives
                OcharBox[] arrochar = new OcharBox[queuechar.Count];
                int intI = 0;
                foreach (char charX in queuechar)
                {
                    arrochar[intI] = new OcharBox(charX);

                    intI = intI + 1;
                }

                objMain_O = arrochar;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<char>
                )
            {
                Stack<char> stackchar = (Stack<char>)obj_I;

                //                                          //Box primitives
                OcharBox[] arrochar = new OcharBox[stackchar.Count];
                int intI = 0;
                foreach (char charX in stackchar)
                {
                    arrochar[intI] = new OcharBox(charX);

                    intI = intI + 1;
                }

                objMain_O = arrochar;
                objKey_O = null;
            }
            else if (
                obj_I is char[,]
                )
            {
                char[,] arr2char = (char[,])obj_I;

                //                                          //Box primitives
                OcharBox[,] arr2ochar = new OcharBox[arr2char.GetLength(0), arr2char.GetLength(1)];
                for (int intI = 0; intI < arr2char.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr2char.GetLength(1); intJ = intJ + 1)
                    {
                        arr2ochar[intI, intJ] = new OcharBox(arr2char[intI, intJ]);
                    }
                }

                objMain_O = arr2ochar;
                objKey_O = null;
            }
            else if (
                obj_I is char[, ,]
                )
            {
                char[, ,] arr3char = (char[, ,])obj_I;

                //                                          //Box primitives
                OcharBox[, ,] arr3ochar = 
                    new OcharBox[arr3char.GetLength(0), arr3char.GetLength(1), arr3char.GetLength(2)];
                for (int intI = 0; intI < arr3char.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr3char.GetLength(1); intJ = intJ + 1)
                    {
                        for (int intK = 0; intK < arr3char.GetLength(2); intK = intK + 1)
                        {
                            arr3ochar[intI, intJ, intK] = new OcharBox(arr3char[intI, intJ, intK]);
                        }
                    }
                }

                objMain_O = arr3ochar;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, char>
                )
            {
                Dictionary<String, char> dicchar = (Dictionary<String, char>)obj_I;

                //                                          //Convert to arrays.
                char[] arrcharValues = new char[dicchar.Count];
                dicchar.Values.CopyTo(arrcharValues, 0);
                String[] arrstrKeys = new String[dicchar.Count];
                dicchar.Keys.CopyTo(arrstrKeys, 0);

                //                                          //Box primitives
                OcharBox[] arrocharValues = new OcharBox[arrcharValues.Length];
                for (int intI = 0; intI < arrcharValues.Length; intI = intI + 1)
                {
                    arrocharValues[intI] = new OcharBox(arrcharValues[intI]);
                }

                objMain_O = arrocharValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, char>
                )
            {
                KeyValuePair<String, char> kvpchar = (KeyValuePair<String, char>)obj_I;

                //                                          //Extract attributes.
                char charValue = kvpchar.Value;
                String strKey = kvpchar.Key;

                //                                          //Box primitive
                OcharBox ocharValue = new OcharBox(charValue);

                objMain_O = ocharValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other char types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertAndBoxTs(          //Convert and box ts.

            //                                              //ots, ots[], ots[,] or ots[, ,].
            //                                              //lstts, ... (one atribute generic) will convert to ots[].
            //                                              //dicts will convert to otsValues[] and strKeys[].
            //                                              //kvpts will convert to otsValue and strKey.
            out Object objMain_O,
            //                                              //null, str[] (keys from dic) or or str (key form kvp).
            out Object objKey_O,
            //                                              //ts type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is DateTime
                )
            {
                //                                          //Box primitive
                OtsBox ots = new OtsBox((DateTime)obj_I);

                objMain_O = ots;
                objKey_O = null;
            }
            else if (
                obj_I is DateTime[]
                )
            {
                DateTime[] arrts = (DateTime[])obj_I;

                //                                          //Box primitives
                OtsBox[] arrots = new OtsBox[arrts.Length];
                for (int intI = 0; intI < arrts.Length; intI = intI + 1)
                {
                    arrots[intI] = new OtsBox(arrts[intI]);
                }

                objMain_O = arrots;
                objKey_O = null;
            }
            else if (
                obj_I is List<DateTime>
                )
            {
                List<DateTime> lstts = (List<DateTime>)obj_I;

                //                                          //Box primitives
                OtsBox[] arrots = new OtsBox[lstts.Count];
                for (int intI = 0; intI < lstts.Count; intI = intI + 1)
                {
                    arrots[intI] = new OtsBox(lstts[intI]);
                }

                objMain_O = arrots;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<DateTime>
                )
            {
                Queue<DateTime> queuets = (Queue<DateTime>)obj_I;

                //                                          //Box primitives
                OtsBox[] arrots = new OtsBox[queuets.Count];
                int intI = 0;
                foreach (DateTime ts in queuets)
                {
                    arrots[intI] = new OtsBox(ts);

                    intI = intI + 1;
                }

                objMain_O = arrots;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<DateTime>
                )
            {
                Stack<DateTime> stackts = (Stack<DateTime>)obj_I;

                //                                          //Box primitives
                OtsBox[] arrots = new OtsBox[stackts.Count];
                int intI = 0;
                foreach (DateTime ts in stackts)
                {
                    arrots[intI] = new OtsBox(ts);

                    intI = intI + 1;
                }

                objMain_O = arrots;
                objKey_O = null;
            }
            else if (
                obj_I is DateTime[,]
                )
            {
                DateTime[,] arr2ts = (DateTime[,])obj_I;

                //                                          //Box primitives
                OtsBox[,] arr2ots = new OtsBox[arr2ts.GetLength(0), arr2ts.GetLength(1)];
                for (int intI = 0; intI < arr2ts.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr2ts.GetLength(1); intJ = intJ + 1)
                    {
                        arr2ots[intI, intJ] = new OtsBox(arr2ts[intI, intJ]);
                    }
                }

                objMain_O = arr2ots;
                objKey_O = null;
            }
            else if (
                obj_I is DateTime[, ,]
                )
            {
                DateTime[, ,] arr3ts = (DateTime[, ,])obj_I;

                //                                          //Box primitives
                OtsBox[, ,] arr3ots = new OtsBox[arr3ts.GetLength(0), arr3ts.GetLength(1), arr3ts.GetLength(2)];
                for (int intI = 0; intI < arr3ts.GetLength(0); intI = intI + 1)
                {
                    for (int intJ = 0; intJ < arr3ts.GetLength(1); intJ = intJ + 1)
                    {
                        for (int intK = 0; intK < arr3ts.GetLength(2); intK = intK + 1)
                        {
                            arr3ots[intI, intJ, intK] = new OtsBox(arr3ts[intI, intJ, intK]);
                        }
                    }
                }

                objMain_O = arr3ots;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, DateTime>
                )
            {
                Dictionary<String, DateTime> dicts = (Dictionary<String, DateTime>)obj_I;

                //                                          //Convert to arrays.
                DateTime[] arrtsValues = new DateTime[dicts.Count];
                dicts.Values.CopyTo(arrtsValues, 0);
                String[] arrstrKeys = new String[dicts.Count];
                dicts.Keys.CopyTo(arrstrKeys, 0);

                //                                          //Box primitives
                OtsBox[] arrotsValues = new OtsBox[arrtsValues.Length];
                for (int intI = 0; intI < arrtsValues.Length; intI = intI + 1)
                {
                    arrotsValues[intI] = new OtsBox(arrtsValues[intI]);
                }

                objMain_O = arrotsValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, DateTime>
                )
            {
                KeyValuePair<String, DateTime> kvpts = (KeyValuePair<String, DateTime>)obj_I;

                //                                          //Extract attributes.
                DateTime tsValue = kvpts.Value;
                String strKey = kvpts.Key;

                //                                          //Box primitive
                OtsBox otsValue = new OtsBox(tsValue);

                objMain_O = otsValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other ts types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertSystemType(        //Convert system type.
            //                                              //Parameters have the following posibilities:
            //                                              //[ Main            Key             Object
            //                                              //  yyy               -             yyy-system(str,ts,...)
            //                                              //  arr?yyy           -             arr?yyy
            //                                              //  arryyy            -             lstyyy
            //                                              //  arryyyValue     arrstrKey       dicyyy
            //                                              //  yyyValue        strKey          kvpyyy
            //                                              //]

            //                                              //obj or arrobj
            out Object objMain_O,
            //                                              //arrstrKey or strKey, only for 2 atribute generic, other
            //                                              //      will return null
            out Object objKey_O,
            //                                              //Any standard object, except generic with atribute enum,
            //                                              //      bclass or btuple.
            Object obj_I,
            Type typeContent_I
            )
        {
            //                                              //Select process.
            /*CASE*/
            if (
                typeContent_I == typeof(String)
                )
            {
                Test.subfunConvertStr(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(Type)
                )
            {
                Test.subfunConvertStr(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(DirectoryInfo)
                )
            {
                Test.subfunConvertSysdir(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(FileInfo)
                )
            {
                Test.subfunConvertSysfile(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(StreamReader)
                )
            {
                Test.subfunConvertSyssr(out objMain_O, out objKey_O, obj_I);
            }
            else if (
                typeContent_I == typeof(StreamWriter)
                )
            {
                Test.subfunConvertSyssw(out objMain_O, out objKey_O, obj_I);
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") + " SOMETHING IS WRONG!!!," +
                    " a case branch to process any form (single, array or generic)" +
                    " of this system type is missing");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertStr(               //Convert str.

            //                                              //str, str[], str[,] or str[, ,].
            //                                              //lststr, ... (one atribute generic) will convert to
            //                                              //      str[].
            //                                              //dicstr will convert to strValues[] and strKeys[].
            //                                              //kvpstr will convert to strValue and strKey.
            out Object objMain_O,
            //                                              //null, str[] (keys from dic) or or str (key form kvp).
            out Object objKey_O,
            //                                              //str type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is List<String>
                )
            {
                List<String> lststr = (List<String>)obj_I;
                String[] arrstr = lststr.ToArray();

                objMain_O = arrstr;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<String>
                )
            {
                Queue<String> queuestr = (Queue<String>)obj_I;
                String[] arrstr = queuestr.ToArray();

                objMain_O = arrstr;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<String>
                )
            {
                Stack<String> stackstr = (Stack<String>)obj_I;
                String[] arrstr = stackstr.ToArray();

                objMain_O = arrstr;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, String>
                )
            {
                Dictionary<String, String> dicsstr = (Dictionary<String, String>)obj_I;

                //                                          //Convert to arrays.
                String[] arrstrValues = new String[dicsstr.Count];
                dicsstr.Values.CopyTo(arrstrValues, 0);
                String[] arrstrKeys = new String[dicsstr.Count];
                dicsstr.Keys.CopyTo(arrstrKeys, 0);

                objMain_O = arrstrValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, String>
                )
            {
                KeyValuePair<String, String> kvpstr = (KeyValuePair<String, String>)obj_I;

                //                                          //Extract attributes.
                String strValue = kvpstr.Value;
                String strKey = kvpstr.Key;

                objMain_O = strValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other str types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertSysdir(            //Convert sysdir.

            //                                              //sysdir, sysdir[], sysdir[,] or sysdir[, ,].
            //                                              //lstsysdir, ... (one atribute generic) will convert to
            //                                              //      sysdir[].
            //                                              //dicsysdir will convert to sysdirValues[] and sysdirKeys[].
            //                                              //kvpsysdir will convert to sysdirValue and sysdirKey.
            out Object objMain_O,
            //                                              //null, sysdir[] (keys from dic) or or sysdir (key form
            //                                              //      kvp).
            out Object objKey_O,
            //                                              //sysdir type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is List<DirectoryInfo>
                )
            {
                List<DirectoryInfo> lstsysdir = (List<DirectoryInfo>)obj_I;
                DirectoryInfo[] arrsysdir = lstsysdir.ToArray();

                objMain_O = arrsysdir;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<DirectoryInfo>
                )
            {
                Queue<DirectoryInfo> queuesysdir = (Queue<DirectoryInfo>)obj_I;
                DirectoryInfo[] arrsysdir = queuesysdir.ToArray();

                objMain_O = arrsysdir;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<DirectoryInfo>
                )
            {
                Stack<DirectoryInfo> stacksysdir = (Stack<DirectoryInfo>)obj_I;
                DirectoryInfo[] arrsysdir = stacksysdir.ToArray();

                objMain_O = arrsysdir;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<DirectoryInfo, DirectoryInfo>
                )
            {
                Dictionary<String, DirectoryInfo> dicssysdir = (Dictionary<String, DirectoryInfo>)obj_I;

                //                                          //Convert to arrays.
                DirectoryInfo[] arrsysdirValues = new DirectoryInfo[dicssysdir.Count];
                dicssysdir.Values.CopyTo(arrsysdirValues, 0);
                String[] arrstyKeys = new String[dicssysdir.Count];
                dicssysdir.Keys.CopyTo(arrstyKeys, 0);

                objMain_O = arrsysdirValues;
                objKey_O = arrstyKeys;
            }
            else if (
                obj_I is KeyValuePair<String, DirectoryInfo>
                )
            {
                KeyValuePair<String, DirectoryInfo> kvpsysdir = (KeyValuePair<String, DirectoryInfo>)obj_I;

                //                                          //Extract attributes.
                DirectoryInfo sysdirValue = kvpsysdir.Value;
                String strKey = kvpsysdir.Key;

                objMain_O = sysdirValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other sysdir types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertSysfile(           //Convert strfile.

            //                                              //strfile, strfile[], strfile[,] or strfile[, ,].
            //                                              //lststrfile, ... (one atribute generic) will convert to
            //                                              //      strfile[].
            //                                              //dicstrfile will convert to strfileValues[] and
            //                                              //      strfileKeys[].
            //                                              //kvpstrfile will convert to strfileValue and strfileKey.
            out Object objMain_O,
            //                                              //null, strfile[] (keys from dic) or or strfile (key form
            //                                              //      kvp).
            out Object objKey_O,
            //                                              //strfile type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is List<FileInfo>
                )
            {
                List<FileInfo> lststrfile = (List<FileInfo>)obj_I;
                FileInfo[] arrstrfile = lststrfile.ToArray();

                objMain_O = arrstrfile;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<FileInfo>
                )
            {
                Queue<FileInfo> queuestrfile = (Queue<FileInfo>)obj_I;
                FileInfo[] arrstrfile = queuestrfile.ToArray();

                objMain_O = arrstrfile;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<FileInfo>
                )
            {
                Stack<FileInfo> stackstrfile = (Stack<FileInfo>)obj_I;
                FileInfo[] arrstrfile = stackstrfile.ToArray();

                objMain_O = arrstrfile;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, FileInfo>
                )
            {
                Dictionary<String, FileInfo> dicssysfile = (Dictionary<String, FileInfo>)obj_I;

                //                                          //Convert to arrays.
                FileInfo[] arrsysfileValues = new FileInfo[dicssysfile.Count];
                dicssysfile.Values.CopyTo(arrsysfileValues, 0);
                String[] arrstrKeys = new String[dicssysfile.Count];
                dicssysfile.Keys.CopyTo(arrstrKeys, 0);

                objMain_O = arrsysfileValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, FileInfo>
                )
            {
                KeyValuePair<String, FileInfo> kvpstrfile = (KeyValuePair<String, FileInfo>)obj_I;

                //                                          //Extract attributes.
                FileInfo sysfileValue = kvpstrfile.Value;
                String strKey = kvpstrfile.Key;

                objMain_O = sysfileValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other strfile types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertSyssr(             //Convert syssr.

            //                                              //syssr, syssr[], syssr[,] or syssr[, ,].
            //                                              //lstsyssr, ... (one atribute generic) will convert to
            //                                              //      syssr[].
            //                                              //dicsyssr will convert to syssrValues[] and syssrKeys[].
            //                                              //kvpsyssr will convert to syssrValue and syssrKey.
            out Object objMain_O,
            //                                              //null, syssr[] (keys from dic) or or syssr (key form kvp).
            out Object objKey_O,
            //                                              //syssr type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is List<StreamReader>
                )
            {
                List<StreamReader> lstsyssr = (List<StreamReader>)obj_I;
                StreamReader[] arrsyssr = lstsyssr.ToArray();

                objMain_O = arrsyssr;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<StreamReader>
                )
            {
                Queue<StreamReader> queuesyssr = (Queue<StreamReader>)obj_I;
                StreamReader[] arrsyssr = queuesyssr.ToArray();

                objMain_O = arrsyssr;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<StreamReader>
                )
            {
                Stack<StreamReader> stacksyssr = (Stack<StreamReader>)obj_I;
                StreamReader[] arrsyssr = stacksyssr.ToArray();

                objMain_O = arrsyssr;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, StreamReader>
                )
            {
                Dictionary<String, StreamReader> dicssyssr = (Dictionary<String, StreamReader>)obj_I;

                //                                          //Convert to arrays.
                StreamReader[] arrsyssrValues = new StreamReader[dicssyssr.Count];
                dicssyssr.Values.CopyTo(arrsyssrValues, 0);
                String[] arrstrKeys = new String[dicssyssr.Count];
                dicssyssr.Keys.CopyTo(arrstrKeys, 0);

                objMain_O = arrsyssrValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, StreamReader>
                )
            {
                KeyValuePair<String, StreamReader> kvpsyssr = (KeyValuePair<String, StreamReader>)obj_I;

                //                                          //Extract attributes.
                StreamReader syssrValue = kvpsyssr.Value;
                String strKey = kvpsyssr.Key;

                objMain_O = syssrValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other syssr types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static void subfunConvertSyssw(             //Convert syssw.

            //                                              //syssw, syssw[], syssw[,] or syssw[, ,].
            //                                              //lstsyssw, ... (one atribute generic) will convert to
            //                                              //      syssw[].
            //                                              //dicsyssw will convert to sysswValues[] and sysswKeys[].
            //                                              //kvpsyssw will convert to sysswValue and sysswKey.
            out Object objMain_O,
            //                                              //null, syssw[] (keys from dic) or or syssw (key form kvp).
            out Object objKey_O,
            //                                              //syssw type (single, array or generic).
            Object obj_I
            )
        {
            /*CASE*/
            if (
                obj_I is List<StreamWriter>
                )
            {
                List<StreamWriter> lstsyssw = (List<StreamWriter>)obj_I;
                StreamWriter[] arrsyssw = lstsyssw.ToArray();

                objMain_O = arrsyssw;
                objKey_O = null;
            }
            else if (
                obj_I is Queue<StreamWriter>
                )
            {
                Queue<StreamWriter> queuesyssw = (Queue<StreamWriter>)obj_I;
                StreamWriter[] arrsyssw = queuesyssw.ToArray();

                objMain_O = arrsyssw;
                objKey_O = null;
            }
            else if (
                obj_I is Stack<StreamWriter>
                )
            {
                Stack<StreamWriter> stacksyssw = (Stack<StreamWriter>)obj_I;
                StreamWriter[] arrsyssw = stacksyssw.ToArray();

                objMain_O = arrsyssw;
                objKey_O = null;
            }
            else if (
                obj_I is Dictionary<String, StreamWriter>
                )
            {
                Dictionary<String, StreamWriter> dicssyssw = (Dictionary<String, StreamWriter>)obj_I;

                //                                          //Convert to arrays.
                StreamWriter[] arrsysswValues = new StreamWriter[dicssyssw.Count];
                dicssyssw.Values.CopyTo(arrsysswValues, 0);
                String[] arrstrKeys = new String[dicssyssw.Count];
                dicssyssw.Keys.CopyTo(arrstrKeys, 0);

                objMain_O = arrsysswValues;
                objKey_O = arrstrKeys;
            }
            else if (
                obj_I is KeyValuePair<String, StreamWriter>
                )
            {
                KeyValuePair<String, StreamWriter> kvpsyssw = (KeyValuePair<String, StreamWriter>)obj_I;

                //                                          //Extract attributes.
                StreamWriter sysswValue = kvpsyssw.Value;
                String strKey = kvpsyssw.Key;

                objMain_O = sysswValue;
                objKey_O = strKey;
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") +
                    " SOMETHING IS WRONG!!!, this type could not be processed with other syssw types");

                objMain_O = null;
                objKey_O = null;
            }
            /*END-CASE*/
        }

        //==============================================================================================================
        /*TASK Test.strAnalizeAndFormat set of private methods to format a single object*/
        //--------------------------------------------------------------------------------------------------------------
        private static String strAnalizeAndFormatCheckNulls(
            //                                              //Produces an object in string format.
            //                                              //Before calling strAnalizeAndFormatXxxx checks for null

            //                                              //str, object in string format, could be null.

            //                                              //Object to format
            Object obj_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatCheckNulls;
            if (
                obj_I == null
                )
            {
                strAnalizeAndFormatCheckNulls = "null";
            }
            else
            {
                /*CASE*/
                if (
                    obj_I is BboxBaseBoxingAbtract
                    )
                {
                    strAnalizeAndFormatCheckNulls = Test.strAnalizeAndFormatBbox((BboxBaseBoxingAbtract)obj_I);
                }
                else if (
                    obj_I is BclassBaseClassAbstract
                    )
                {
                    strAnalizeAndFormatCheckNulls =
                        Test.strAnalizeAndFormatBclass((BclassBaseClassAbstract)obj_I, strtoOption_I);
                }
                else if (
                    obj_I is BtupleBaseTupleAbstract
                    )
                {
                    strAnalizeAndFormatCheckNulls =
                        Test.strAnalizeAndFormatBtuple((BtupleBaseTupleAbstract)obj_I, strtoOption_I);
                }
                else if (
                    obj_I is BenumBaseEnumAbstract
                    )
                {
                    strAnalizeAndFormatCheckNulls =
                        Test.strAnalizeAndFormatBenum((BenumBaseEnumAbstract)obj_I);
                }
                else if (
                    obj_I is Enum
                    )
                {
                    strAnalizeAndFormatCheckNulls = Test.strAnalizeAndFormatEnum((Enum)obj_I);
                }
                else if (
                    obj_I is Exception
                    )
                {
                    strAnalizeAndFormatCheckNulls = Test.strAnalizeAndFormatSysexcep((Exception)obj_I);
                }
                else
                {
                    strAnalizeAndFormatCheckNulls = Test.strAnalizeAndFormatSystemType(obj_I, strtoOption_I);
                }
                /*END-CASE*/
            }

            return strAnalizeAndFormatCheckNulls;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatBbox(      //Produces an object in string format (boxed primitive)

            //                                              //str, object in string format.

            //                                              //bbox to format
            BboxBaseBoxingAbtract bbox_I
            )
        {
            String strAnalizeAndFormatBbox;
            /*CASE*/
            if (
                bbox_I is OintBox
                )
            {
                strAnalizeAndFormatBbox = Test.strAnalizeAndFormatInt(((OintBox)bbox_I).v);
            }
            else if (
                bbox_I is OlongBox
                )
            {
                strAnalizeAndFormatBbox = Test.strAnalizeAndFormatLong(((OlongBox)bbox_I).v);
            }
            else if (
                bbox_I is OnumBox
                )
            {
                strAnalizeAndFormatBbox = Test.strAnalizeAndFormatNum(((OnumBox)bbox_I).v);
            }
            else if (
                bbox_I is OboolBox
                )
            {
                strAnalizeAndFormatBbox = Test.strAnalizeAndFormatBool(((OboolBox)bbox_I).v);
            }
            else if (
                bbox_I is OcharBox
                )
            {
                strAnalizeAndFormatBbox = Test.strAnalizeAndFormatChar(((OcharBox)bbox_I).v);
            }
            else if (
                bbox_I is OtsBox
                )
            {
                strAnalizeAndFormatBbox = Test.strAnalizeAndFormatTs(((OtsBox)bbox_I).v);
            }
            else
            {
                Test.subAbort(Test.strTo(bbox_I.GetType(), "bbox_I.GetType") +
                    " SOMETHING IS WRONG!!!, method strAnalizeAndFormatXxxx to process this bbox type is missing");

                strAnalizeAndFormatBbox = null;
            }
            /*END-CASE*/

            return strAnalizeAndFormatBbox;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatInt(       //Prepara un entero (long) para su despliege con información
            //                                              //      adicional si es mínimo o máximo.
            //                                              //Ejemplos:
            //                                              //1 -3,835.
            //                                              //2 -9,223,372,036,854,775,808<MinValue>.
            //                                              //3 9,223,372,036,854,775,807<MaxValue>.
            //                                              //str, String para despligue con información adicional.

            //                                              //Entero a desplegar.
            int int_I
            )
        {
            //                                              //Por lo pronto prepara sin información adicional.
            String strAnalizeAndFormatInt = int_I.ToString("#,##0");

            //                                              //Añade información adicional si es mínimo o máximo.
            if (
                int_I == Int32.MinValue
                )
            {
                strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MinValue>";
            }
            else if (
                int_I == Int32.MaxValue
                )
            {
                strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MaxValue>";
            }
            else
            {
                //                                          //Sin información adicional.
            }

            return strAnalizeAndFormatInt;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatLong(      //Prepara un entero (long) para su despliege con información
            //                                              //      adicional si es mínimo o máximo.
            //                                              //Ejemplos:
            //                                              //1 -3,835.
            //                                              //2 -9,223,372,036,854,775,808<MinValue>.
            //                                              //3 9,223,372,036,854,775,807<MaxValue>.
            //                                              //str, String para despligue con información adicional.

            //                                              //Entero a desplegar.
            long long_I
            )
        {
            //                                              //Por lo pronto prepara sin información adicional.
            String strAnalizeAndFormatLong = long_I.ToString("#,##0");

            //                                              //Añade información adicional si es mínimo o máximo.
            if (
                long_I == Int64.MinValue
                )
            {
                strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MinValue>";
            }
            else if (
                long_I == Int64.MaxValue
                )
            {
                strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MaxValue>";
            }
            else
            {
                //                                          //Sin información adicional.
            }

            return strAnalizeAndFormatLong;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatNum(       //Prepara un número para su despliege con información
            //                                              //      adicional si es mínimo, máximo, etc.
            //                                              //Ejemplos:
            //                                              //1 -1.23456789012345E+038.
            //                                              //2 -1.79769313486232E+308<MinValue>;
            //                                              //3 1.79769313486232E+308<MaxValue>;
            //                                              //4 NaN<0/0>;
            //                                              //5 -Infinity<-?/0>;
            //                                              //6 Infinity<?/0>;
            //                                              //str, String para despligue con información adicional.

            //                                              //Entero a desplegar.
            double num_I
            )
        {
            //                                              //Por lo pronto prepara sin información adicional.
            String strAnalizeAndFormatNum = num_I.ToString();

            //                                              //Añade información adicional si es mínimo o máximo.
            if (
                num_I == Double.MinValue
                )
            {
                strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MinValue>";
            }
            else if (
                num_I == Double.MaxValue
                )
            {
                strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MaxValue>";
            }
            else if (
                num_I == Double.NegativeInfinity
                )
            {
                strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<-?/0.0>";
            }
            else if (
                num_I == Double.PositiveInfinity
                )
            {
                strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<?/0.0>";
            }
            else if (
                //                                          //A number has 4 posibibilities:
                //                                          //1. Beetwen MinValue and MaxValue
                //                                          //2. NegativeInfinity, (-?/0.0).
                //                                          //3. PositiveInfinity, (?/0.0).
                //                                          //4. NaN, (0.0/0.0).
                //                                          //num_I == Double.NaN, DO NOT FUNCTION AS EXPECTED
                !((num_I >= Double.MinValue) && (num_I <= Double.MaxValue))
                )
            {
                strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<0.0/0.0>";
            }
            else
            {
                //                                          //Sin información adicional.
            }

            return strAnalizeAndFormatNum;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatBool(      //Prepara un booleno para su despliege.
            //                                              //Ejemplos:
            //                                              //1 true.
            //                                              //2 false.
            //                                              //str, String para despligue.

            //                                              //Booleano a desplegar.
            bool bool_I
            )
        {
            //                                              //Se asigna true o false según corresponda.
            //                                              //Se hace de esta forma dado que el bool_I.ToString()
            //                                              //      produce "True" o  "False" (iniciando con mayúsculas
            //                                              //      que es distinto a las literales true y false).
            String strAnalizeAndFormatBool;
            if (
                bool_I
                )
            {
                strAnalizeAndFormatBool = "true";
            }
            else
            {
                strAnalizeAndFormatBool = "false";
            }

            return strAnalizeAndFormatBool;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatChar(
            //                                              //Prepara un caracter para su despliege con información
            //                                              //      adicional si es caracter extraño.
            //                                              //Ejemplos:
            //                                              //1 'c'.
            //                                              //2 '©'<0x00A9>.
            //                                              //3 '●'<0x0009, \t, Horizontal Tab>.
            //                                              //1) No tiene nada extraño, solo se añaden las comillas.
            //                                              //2) El caracter © no esta en DO_NOT_SHOW_HEX, incluyo su
            //                                              //      su hexadecimal.
            //                                              //3) El caracter esta en arrt2charDESCRITPTION, incluyo su
            //                                              //      hexadecimal y su descripción.
            //                                              //Adicionalmente, si el caracter no esta en USEFUL_IN_TEXT
            //                                              //      se substituye por '●'.
            //                                              //str, String para despligue con diagnostico.

            //                                              //Caracter a analizar.
            char char_I
            )
        {
            //                                              //Para formar lo que va a regresar, esto depende del tipo de
            //                                              //      caracter.
            String strAnalizeAndFormatChar;
            if (
                //                                          //Es USEFUL_IN_TEXT
                Array.BinarySearch(Test.arrcharUSEFUL_IN_TEXT, char_I) >= 0
                )
            {
                //                                          //Es visible, solo pone entre comillas
                strAnalizeAndFormatChar = "'" + char_I + "'";
            }
            else
            {
                //                                          //No es visible, lo substitutye.
                strAnalizeAndFormatChar = "'" + charSUBSTITUTE_NO_USEFUL_IN_TEXT + "'";
            }

            //                                              //Añade info de diagnóstico.
            if (
                //                                          //Se solicita mostrar su hex
                (Array.BinarySearch(Test.arrcharDO_NOT_SHOW_HEX, char_I) < 0) ||
                //                                          //De solicita mostar su descripción
                (Array.BinarySearch(Test.arrt2charDESCRIPTION, char_I) >= 0)
                )
            {
                //                                          //Formatea tupla con información de diagnóstico
                //                                          //      (primera parte).
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + "<" + "0x" + String.Format("{0:X4}", (int)char_I);

                if (
                    //                                      //De solicita mostar su descripción
                    Array.BinarySearch(Test.arrt2charDESCRIPTION, char_I) >= 0
                    )
                {
                    //                                      //Completa la tupla cuando tiene descripción.
                    int intT2 = Array.BinarySearch(Test.arrt2charDESCRIPTION, char_I);
                    strAnalizeAndFormatChar = strAnalizeAndFormatChar + ", " +
                        Test.arrt2charDESCRIPTION[intT2].strDESCRIPTION + ">";
                }
                else
                {
                    //                                      //Completa la tupla cuando NO tiene descripción.
                    strAnalizeAndFormatChar = strAnalizeAndFormatChar + ">";
                }
            }

            return strAnalizeAndFormatChar;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatTs(        //Prepara un date, time o ts para su despliege.
            //                                              //Ejemplos:
            //                                              //1 2013-12-28.
            //                                              //2 2013-12-28 21:30:16.
            //                                              //3 2013-12-28T21:31:25.7030000-06:00.
            //                                              //str, String para despligue.

            //                                              //ts (date o time) a desplegar.
            //                                              //ts, si tiene milisegundos se asume que es ts.
            //                                              //time, si tiene hora, minuto y/o segundos se asume que es
            //                                              //      time.
            //                                              //date, si no fue ts o time.      
            DateTime ts_I
            )
        {
            //                                              //Se determina el formato que corresponde.
            String strAnalizeAndFormatTs;
            if (
                //                                          //Tiene milisegundos.
                ts_I.Millisecond != 0
                )
            {
                strAnalizeAndFormatTs = ts_I.ToString("o");
            }
            else if (
                //                                          //Tiene hora, minutos o segundos.
                (ts_I.Hour != 0) || (ts_I.Minute != 0) || (ts_I.Second != 0)
                )
            {
                strAnalizeAndFormatTs = ts_I.ToString("yyyy-MM-dd HH:mm:ss");
            }
            else
            {
                //                                          //Solo tiene fecha.
                strAnalizeAndFormatTs = ts_I.ToString("yyyy-MM-dd");
            }

            return strAnalizeAndFormatTs;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatEnum(      //Analize and format enum (or subclass of enum).
            //                                              //str, enum formated to display.

            //                                              //Enum to be analized and format
            Enum enum_I
            )
        {
            return enum_I.ToString();
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatBclass(
            //                                              //Analize and format bclass (or subclass of bclass).
            //                                              //A bclass object should be display only once per run.
            //                                              //str, bclass formated to display.

            //                                              //bclass to be analized and format
            BclassBaseClassAbstract bclass_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatBclass;
            /*CASE*/
            if (
                //                                          //Es un bclass DUMMY
                bclass_I.boolIsDummy
                )
            {
                //                                          //Include only objId + DUMMY
                strAnalizeAndFormatBclass = Test.strGetObjId(bclass_I) + "[DUMMY]";
            }
            else if (
                //                                          //Was processed before
                Test.lstobjPreviouslyProcessed.Contains(bclass_I)
                )
            {
                //                                          //Include only objId
                strAnalizeAndFormatBclass = Test.strGetObjId(bclass_I) + "|look object up|";
            }
            else
            {
                //                                          //Register as processed
                Test.lstobjPreviouslyProcessed.Add(bclass_I);

                if (
                    strtoOption_I == StrtoEnum.SHORT
                )
                {
                    strAnalizeAndFormatBclass = bclass_I.strTo(StrtoEnum.SHORT);
                }
                else
                {
                    strAnalizeAndFormatBclass = bclass_I.strTo();
                }
            }
            /*END-CASE*/

            return strAnalizeAndFormatBclass;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatBclassBEFORE(
            //                                              //Analize and format bclass (or subclass of bclass).
            //                                              //A bclass object should be display only once per run.
            //                                              //str, bclass formated to display.

            //                                              //bclass to be analized and format
            BclassBaseClassAbstract bclass_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatBclass;
            /*CASE*/
            if (
                //                                          //Es un bclass DUMMY
                bclass_I.boolIsDummy
                )
            {
                //                                          //Include only objId + DUMMY
                strAnalizeAndFormatBclass = Test.strGetObjId(bclass_I) + "[DUMMY]";
            }
            else if (
                //                                          //Was processed before
                Test.lstobjPreviouslyProcessed.Contains(bclass_I)
                )
            {
                //                                          //Include only objId
                strAnalizeAndFormatBclass = Test.strGetObjId(bclass_I) + "|look object up|";
            }
            else
            {
                //                                          //Register as processed
                Test.lstobjPreviouslyProcessed.Add(bclass_I);

                if (
                    strtoOption_I == StrtoEnum.SHORT
                    )
                {
                    strAnalizeAndFormatBclass = bclass_I.strTo(StrtoEnum.SHORT);
                }
                else
                {
                    strAnalizeAndFormatBclass = bclass_I.strTo();
                }
            }
            /*END-CASE*/

            return strAnalizeAndFormatBclass;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatBtuple(    //Analize and format btuple (or subclass of btuple).
            //                                              //A btuple object should be display only once per run.
            //                                              //str, btuple formated to display.

            //                                              //benum to be analized and format
            BtupleBaseTupleAbstract btuple_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatBtuple;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatBtuple = btuple_I.strTo(StrtoEnum.SHORT);
            }
            else
            {
                strAnalizeAndFormatBtuple = btuple_I.strTo();
            }

            return strAnalizeAndFormatBtuple;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatBenum(
            //                                              //Analize and format benum (or subclass of benum).
            //                                              //str, benum formated to display.

            //                                              //benum to be analized and format
            BenumBaseEnumAbstract benum_I
            )
        {
            return benum_I.strTo();
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatSysexcep(  //Prepare a object to display.
            //                                              //str, sysexcep_I prepared to display.

            //                                              //Object to be analized and format
            Exception sysexcep_I
            )
        {
            return sysexcep_I.ToString();
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatSystemType(
            //                                              //Produces an object in string format (system type)

            //                                              //str, object in string format.

            //                                              //Object to format
            Object obj_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatSystemType;
            /*CASE*/
            if (
                obj_I is String
                )
            {
                strAnalizeAndFormatSystemType = Test.strAnalizeAndFormatStr((String)obj_I);
            }
            else if (
                obj_I is Type
                )
            {
                strAnalizeAndFormatSystemType = Test.strAnalizeAndFormatType((Type)obj_I, strtoOption_I);
            }
            else if (
                obj_I is DirectoryInfo
                )
            {
                strAnalizeAndFormatSystemType = Test.strAnalizeAndFormatSysdir((DirectoryInfo)obj_I,
                    strtoOption_I);
            }
            else if (
                obj_I is FileInfo
                )
            {
                strAnalizeAndFormatSystemType = Test.strAnalizeAndFormatSysfile((FileInfo)obj_I, strtoOption_I);
            }
            else if (
                obj_I is StreamReader
                )
            {
                strAnalizeAndFormatSystemType = Test.strAnalizeAndFormatSyssr((StreamReader)obj_I, strtoOption_I);
            }
            else if (
                obj_I is StreamWriter
                )
            {
                strAnalizeAndFormatSystemType = Test.strAnalizeAndFormatSyssw((StreamWriter)obj_I, strtoOption_I);
            }
            else
            {
                Test.subAbort(Test.strTo(obj_I.GetType(), "obj_I.GetType") + " SOMETHING IS WRONG!!!," +
                    " method strAnalizeAndFormatXxxx to process this system type is missing");

                strAnalizeAndFormatSystemType = null;
            }
            /*END-CASE*/

            return strAnalizeAndFormatSystemType;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatStr(       //Prepara un String para su despliege con información de
            //                                              //      caracteres que no son del visibles (NONVISIBLE) o
            //                                              //      que no son del KEYBOARD.
            //                                              //La constante strSHOW_NONVISIBLE indica los caracteres que
            //                                              //      se desea mostrar:
            //                                              //true, solo muestra los NONVISIBLE (caracteres visibles que
            //                                              //      no están en el KEYBOARD no se muestran).
            //                                              //false, muestra todos los caracters que no están en el
            //                                              //      KEYBOARD.
            //                                              //true, es la opción default.
            //                                              //Ejemplos:
            //                                              //1. "Esto es lo que se analizo"<25>.
            //                                              //2. "©XYX"<4>{ <0, '©', 0x00A9> }.
            //                                              //3. "^XYX"<4>{ <0, '^', 0x0001> }.
            //                                              //4. "^XYX"<4>{ <0, '^', 0x0009, \t, Horizontal Tab> }.
            //                                              //1) Todo son VISIBLE o del KEYBOARD, solo se añaden las
            //                                              //      comillas y su longitud.
            //                                              //2) El primer caracter © no aparece en el KEYBOARD, incluyo
            //                                              //      su hexadecimal.
            //                                              //3) El primer caracter es NONVISIBLE_WITHOUT_DESCRIPTION,
            //                                              //      se substituye por _ (el caracter en
            //                                              //      charSUBSTITUTE_NONVISIBLE) e incluyo su hexadecimal.
            //                                              //4) El primer caracter es un Horizonal Tab, no es
            //                                              //      visible, se substituye por _ (el caracter en
            //                                              //      charSUBSTITUTE_NONVISIBLE), incluyo su hexadecimal y
            //                                              //      su descripción.
            //                                              //Puede haber más de un caracter que no es VISIBLE o del
            //                                              //      KEYBOARD, se añade "{ <.....>, <.....>, ...,
            //                                              //      <......> }".
            //                                              //Si no hay ningún caracter que no es VISIBLE o no es del
            //                                              //      KEYBOARD, no se añade nada en esta parte, esto es no
            //                                              //      se añade "{ }", esto fue lo que sucedió en el
            //                                              //      ejemplo 1.
            //                                              //str, String para despligue con diagnostico de caracteres
            //                                              //      que no son VISIBLE o no están en el KEYBOARD.

            //                                              //String a analizar.
            String str_I
            )
        {
            //                                              //Para formar lo que va a regresar.
            String strAnalizeAndFormatStr;
            if (
                //                                          //No hay String.
                str_I == null
                )
            {
                strAnalizeAndFormatStr = "null";
            }
            else
            {
                //                                          //Paso a arrchar para poder modificarlo.
                char[] arrcharToAnalize = str_I.ToCharArray();

                //                                          //Para conjunto de información de diagnóstico.
                List<String> lststrDiagnosticInfo = new List<String>();

                //                                          //Reviso todos los caracteres.
                for (int intI = 0; intI < arrcharToAnalize.Length; intI = intI + 1)
                {
                    //                                      //Paso un caracter a formato desplegable, el formato será:
                    //                                      //'c', KEYBOARD.
                    //                                      //'c'<0x1234>, VISIBLE_NONKEYBOARD.
                    //                                      //'^'<0x1234>, NONVISIBLE_WITHOUT_DESCRIPTION.
                    //                                      //'^'<0x1234, descripción>, NONVISIBLE_WITH_DESCRIPTION.
                    String strCharAnalized = Test.strAnalizeAndFormatChar(arrcharToAnalize[intI]);

                    //                                      //Si tiene información de diagnóstico la proceso.
                    if (
                        //                                  //Si tiene información de diagnóstico.
                        strCharAnalized.Length > 3
                        )
                    {
                        //                                  //Cambio caracter, la pos. 1 tiene el caracter revisado.
                        arrcharToAnalize[intI] = strCharAnalized[1];

                        //                                  //Debo formar un String:
                        //                                  //<n, 'c', 0x1234>, VISIBLE_NONKEYBOARD.
                        //                                  //<n, '^', 0x1234>, NONVISIBLE_WITHOUT_DESCRIPTION.
                        //                                  //<n, '^', 0x1234, descripción>,
                        //                                  //      NONVISIBLE_WITH_DESCRIPTION.
                        String strDiagnosticInfo = "<" + intI + ", " + strCharAnalized.Substring(0, 3) + ", " +
                            strCharAnalized.Substring(4);

                        //                                  //Añade info a la lista.
                        lststrDiagnosticInfo.Add(strDiagnosticInfo);
                    }
                }

                //                                          //Forma la longitud del String, solo de desea mostrar cuando
                //                                          //      excede intLONG_STRING.
                String strLongString;
                if (
                    str_I.Length > intLONG_STRING
                    )
                {
                    strLongString = "<" + str_I.Length + ">";
                }
                else
                {
                    strLongString = "";
                }

                //                                          //Forma el String a desplegar.
                if (
                    //                                      //No tiene ningún caracter con información de diagnóstico.
                    lststrDiagnosticInfo.Count == 0
                    )
                {
                    //                                      //Formatea cuando NO tiene información de diagnóstico.
                    strAnalizeAndFormatStr = "\"" + str_I + "\"" + strLongString;
                }
                else
                {
                    //                                      //Limito la cantidad de diagnóstico a mostrar.
                    if (
                        lststrDiagnosticInfo.Count > Test.intMAX_DIAGNOSTIC
                        )
                    {
                        //                                  //Si lo limitó, mostrará al final ... +N more chars)
                        int intRemove = lststrDiagnosticInfo.Count - Test.intMAX_DIAGNOSTIC;
                        lststrDiagnosticInfo.RemoveRange(Test.intMAX_DIAGNOSTIC, intRemove);
                        lststrDiagnosticInfo.Add("... +" + intRemove + " more chars");
                    }

                    //                                      //Formatea cuando SI tiene información de diagnóstico.
                    strAnalizeAndFormatStr = "\"" + new String(arrcharToAnalize) + "\"" + "<" + 
                        arrcharToAnalize.Length + ">" + "{ " + String.Join(", ", lststrDiagnosticInfo.ToArray()) + " }";
                }
            }

            return strAnalizeAndFormatStr;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatType(      //Prepare a object to display.
            //                                              //str, type prepared to display.

            //                                              //Object to be analized and format
            Type type_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatType;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatType = "<" + type_I.Name + ">";
            }
            else
            {
                strAnalizeAndFormatType = "<Name(" + type_I.Name + ")>";
            }

            return strAnalizeAndFormatType;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatSysdir(    //Prepare a object to display.
            //                                              //str, sysdir prepared to display.

            //                                              //Object to be analized and format
            DirectoryInfo sysdir_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatSysdir;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatSysdir = "<" + sysdir_I.FullName + ", " + sysdir_I.Exists;
                if (
                    sysdir_I.Exists
                    )
                {
                    strAnalizeAndFormatSysdir = strAnalizeAndFormatSysdir + ", " + sysdir_I.CreationTimeUtc + ", " +
                        sysdir_I.LastAccessTimeUtc + ", " + sysdir_I.LastWriteTimeUtc;
                }
                strAnalizeAndFormatSysdir = strAnalizeAndFormatSysdir + ">";
            }
            else
            {
                strAnalizeAndFormatSysdir = "<FullName(" + sysdir_I.FullName + "), Exists(" + sysdir_I.Exists;
                if (
                    sysdir_I.Exists
                    )
                {
                    strAnalizeAndFormatSysdir = strAnalizeAndFormatSysdir + "), CreationTimeUtc(" +
                        sysdir_I.CreationTimeUtc + "), LastAccessTimeUtc(" + sysdir_I.LastAccessTimeUtc +
                        "), LastWriteTimeUtc(" + sysdir_I.LastWriteTimeUtc;
                }
                strAnalizeAndFormatSysdir = strAnalizeAndFormatSysdir + ")>";
            }

            return strAnalizeAndFormatSysdir;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatSysfile(   //Prepare a object to display.
            //                                              //str, sysfile prepared to display.

            //                                              //Object to be analized and format
            FileInfo sysfile_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatSysfile;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatSysfile = "<" + sysfile_I.FullName + ", " + sysfile_I.Exists;
                if (
                    sysfile_I.Exists
                    )
                {
                    strAnalizeAndFormatSysfile = strAnalizeAndFormatSysfile + ", " + sysfile_I.Length + ", " +
                        sysfile_I.CreationTimeUtc + ", " + sysfile_I.LastAccessTimeUtc + ", " +
                        sysfile_I.LastWriteTimeUtc;
                }
                strAnalizeAndFormatSysfile = strAnalizeAndFormatSysfile + ">";
            }
            else
            {
                strAnalizeAndFormatSysfile = "<FullName(" + sysfile_I.FullName + "), Exists(" + sysfile_I.Exists;
                if (
                    sysfile_I.Exists
                    )
                {
                    strAnalizeAndFormatSysfile = strAnalizeAndFormatSysfile + "), Length(" + sysfile_I.Length + 
                        "), CreationTimeUtc(" + sysfile_I.CreationTimeUtc + "), LastAccessTimeUtc(" +
                        sysfile_I.LastAccessTimeUtc + "), LastWriteTimeUtc(" + sysfile_I.LastWriteTimeUtc;
                }
                strAnalizeAndFormatSysfile = strAnalizeAndFormatSysfile + ")>";
            }

            return strAnalizeAndFormatSysfile;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatSyssr(     //Prepare a object to display.
            //                                              //str, syssr prepared to display.

            //                                              //Object to be analized and format
            StreamReader syssr_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatSyssr;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatSyssr = "<" + syssr_I.CurrentEncoding + ", " + syssr_I.EndOfStream + ">";
            }
            else
            {
                strAnalizeAndFormatSyssr = "<CurrentEncoding(" + syssr_I.CurrentEncoding + "), EndOfStream(" +
                    syssr_I.EndOfStream + ")>";
            }

            return strAnalizeAndFormatSyssr;
        }

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        private static String strAnalizeAndFormatSyssw(     //Prepare a object to display.
            //                                              //str, syssw prepared to display.

            //                                              //Object to be analized and format
            StreamWriter syssw_I,
            //                                              //SHORT or FULL
            StrtoEnum strtoOption_I
            )
        {
            String strAnalizeAndFormatSyssw;
            if (
                strtoOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatSyssw = "<" + syssw_I.Encoding + ">";
            }
            else
            {
                strAnalizeAndFormatSyssw = "<Encoding(" + syssw_I.Encoding + ")>";
            }

            return strAnalizeAndFormatSyssw;
        }
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.Trace Support to implement a trace*/
        //--------------------------------------------------------------------------------------------------------------

        //                                                  //Implementación de apoyos para poder efectuar un Trece en
        //                                                  //      en una apliación.
        //                                                  //¿Cómo?.
        //                                                  //      puntos que se crea conveniente, añadir:
        //                                                  //Test.subTrace(true, strLabel, intNivel, String a trace);
        //                                                  //Imprimie el log que contendrá el trace y otra información
        //                                                  //      de la prueba 

        //--------------------------------------------------------------------------------------------------------------
        /*CONSTANTS*/

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC VARIABLES*/

        //                                                  //Log para mostrar información en un archivo.
        //                                                  //Este log se asigna al iniciar una prueba con el método
        //                                                  //      subInitializeLog().
        //                                                  //Se mostrará información en los siguientes casos:
        //                                                  //1. Al concluir una prueba, usando el método subLog().
        //                                                  //2. Al abortar, si sysswLog != null, usando el método
        //                                                  //3. Al enviar un Warning (similar a abortar).
        //                                                  //4. Al usar Trace (DEFINIR DESPUÉS).
        private static StreamWriter sysswTestLog;

        //                                                  //Cada Trace que se genere tendra un número único 1, 2, 3,
        //                                                  //      etc. (esto es, su secuencia).
        //                                                  //Antes de generar un nuevo trace se debe incrementar.
        private static int inTraceSequence;

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC CONSTRUCTOR SUPPORT METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        private static void subPrepareConstantsSubLogAndTrace(
            //                                              //Initialiaze trace state.
            )
        {
            //                                              //Aún no hay log para trace definido.
            sysswTestLog = null;

            //                                              //Inicia la cuenta de Trace que se genera.
            inTraceSequence = 0;
        }

        //--------------------------------------------------------------------------------------------------------------
        /*SHARED METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        public static void subInitializeLog(
            //                                              //Inicializa el log para UNA prueba. (Al inicio de cada Test
            //                                              //      se debe ejecutar este método).
            //                                              //1. Genera syssw para el log y lo guarda.
            //                                              //2. Escribe en log: Nombre Tester, ts y name del log.

            //                                              //Nombre de Tester (quien esta a cargo de la prueba)
            String strNameTester_I,
            //                                              //Path para los logs, debe ser un directorio
            SyspathPath syspathDirectoryForLogs_I,
            //                                              //Clave de la prueba (Ej. Cod1, Cod4Com3Com4Com5Cod3Com2).
            //                                              //Con esta clave se formará en nombre de archivo log de la
            //                                              //      prueba añadiendole .test (Ej. Cod1.test, 
            //                                              //      Cod4Com3Com4Com5Cod3Com2.test).
            //                                              //Si el path DirectoryForLogs\Test.test existe debe ser un
            //                                              //      y se reescribe.
            String strTest_I,
            //                                              //Indica si la prueba es para "comparación" automática (en
            //                                              //      estos casos el ts y los hashcode se muestran como ?,
            //                                              //      el tester name se muestra con "<Test for Automatic
            //                                              //      Verification>")
            bool boolIsTestForAutomaticVerification_I
            )
        {
            if (
                strNameTester_I == null
                )
                Test.subAbort(Test.strTo(strNameTester_I, "strNameTester_I") + " can not be null");
            if (
                strNameTester_I == ""
                )
                Test.subAbort(Test.strTo(strNameTester_I, "strNameTester_I") + " should have a value");
            if (
                syspathDirectoryForLogs_I == null
                )
                Test.subAbort(Test.strTo(syspathDirectoryForLogs_I, "syspathDirectoryForLogs_I") + " can not be null");
            if (
                !syspathDirectoryForLogs_I.boolIsValid
                )
                Test.subAbort(Test.strTo(syspathDirectoryForLogs_I, "syspathDirectoryForLogs_I") + " is not valid");
            if (
                !syspathDirectoryForLogs_I.boolIsDirectory
                )
                Test.subAbort(Test.strTo(syspathDirectoryForLogs_I, "syspathDirectoryForLogs_I") +
                    " should be a directory");
            if (
                strTest_I == null
                )
                Test.subAbort(Test.strTo(strTest_I, "strFileForTestLog_I") + " can not be null");
            if (
                strTest_I == ""
                )
                Test.subAbort(Test.strTo(strTest_I, "strTest_I") + " should have a value");

            SyspathPath syspathFileForTestLog = syspathDirectoryForLogs_I.syspathAddName(strTest_I + ".test");

            if (
                !syspathFileForTestLog.boolIsValid
                )
                Test.subAbort(Test.strTo(syspathFileForTestLog, "syspathFileForTestLog") + " is not valid");
            if (
                //                                          //Existe y NO es file
                syspathFileForTestLog.boolExists && !syspathFileForTestLog.boolIsFile
                )
                Test.subAbort(Test.strTo(syspathFileForTestLog, "syspathFileForTestLog") + " should be a file");

            //                                              //Genera log
            FileInfo sysfileTestLog = Sys.sysfileNew(syspathFileForTestLog);
            if (
                syspathFileForTestLog.boolIsFile
                )
            {
                Test.sysswTestLog = Sys.sysswNewRewriteTextFile(sysfileTestLog);
            }
            else
            {
                Test.sysswTestLog = Sys.sysswNewWriteTextFile(sysfileTestLog);
            }

            //                                              //En ComparableLog, los HashCode serán ?, etc.
            Test.boolComparableLog = boolIsTestForAutomaticVerification_I;

            //                                              //Escribe primera línea en log
            String strNameTester;
            String strTsNow;
            if (
                Test.boolComparableLog
                )
            {
                //                                          //En verificación automática se cancela el despliegue del Ts
                strNameTester = "<Test for Automatic Verification>";
                strTsNow = "?";
            }
            else
            {
                strNameTester = strNameTester_I;
                strTsNow = DateTime.Now.ToString("yyyy-MM-dd HH:mm");
            }
            Test.subLog(strNameTester + ", Now(" + strTsNow + "), " + syspathFileForTestLog.strName);

            //                                              //Cada Test inicia la secuencia de los blocks An, Bn, ...
            Test.intStartEnd = 0;
        }

        //--------------------------------------------------------------------------------------------------------------
        public static void subFinalizeLog(
            //                                              //Cierra el log para UNA prueba. (Al concluir cada Test
            //                                              //      se debe ejecutar este método).
            //                                              //1. Dispose log.
            //                                              //2. Lo asinga a null.
            )
        {
            //                                              //Solo si esta en una prueba
            if (
                //                                          //Hay un log, estamos en prueba
                Test.sysswTestLog != null
                )
            {
                Test.sysswTestLog.Dispose();
                Test.sysswTestLog = null;
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        public static void subLog(
            //                                              //Genera información en el log.

            String strInfoToLog_I
            )
        {
            //                                              //Solo si esta en una prueba
            if (
                //                                          //Hay un log, estamos en prueba
                Test.sysswTestLog != null
                )
            {
                Sys.subWriteLine(strInfoToLog_I, Test.sysswTestLog);
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        public static void subTrace(                        //Genera un trace a writeline.

            //                                              //true, se desea generar el trace.
            //                                              //false, No se genera el trace.
            //                                              //Se incluye este parámetro para sin tener que eliminar la
            //                                              //      la ejecución del trace poder activarlo/desactivarlo.
            bool boolIsTraceOn_I,
            //                                              //Etiqueta para identificar el registro del trace en la
            //                                              //      impresión. Cada instrucción trace que se agregue al
            //                                              //      código debe tener una etiqueta distinta.
            String strLabel_I,
            //                                              //Información a incluir en el trace, esta información se le
            //                                              //      da forma similar a los strTo.
            String strInfoTrace_I
            )
        {
            if (
                Test.sysswTestLog == null
                )
                Test.subAbort(Test.strTo(Test.sysswTestLog, "Test.sysswLog") + " should be created and asigned");

            //                                              //Solo se procesa el trace si esta en ON.
            if (
                boolIsTraceOn_I
                )
            {
                //                                          //Avanza una secuencia (esta es la secuencia única de este
                //                                          //      trace).
                inTraceSequence = inTraceSequence + 1;

                //                                          //Produce trace.
                Test.subLog(">>> " + inTraceSequence + " <<< " + strLabel_I);
                Test.subLog(strInfoTrace_I);
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        /*END-TASK*/

        //==============================================================================================================
        /*TASK Test.Abort Support for testing subAbort*/

        //                                                  //Implementación de apoyos para poder efectuar pruebas de
        //                                                  //      subAbort y regitrar su información en un log.
        //                                                  //¿Cómo?.
        //                                                  //En el código "driver" para ejecutar la prueba (ej. en
        //                                                  //      Test Sys01.cs), llamar al método:
        //                                                  //Test.subSetTestAbort(); o.
        //                                                  //Test.subResetTestAbort(); o.

        //--------------------------------------------------------------------------------------------------------------
        /*CONSTANTS*/

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC VARIABLES*/

        //                                                  //Indicador de se desea test.
        internal static bool boolIsTestAbortOn;

        //--------------------------------------------------------------------------------------------------------------
        /*STATIC CONSTRUCTOR SUPPORT METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        private static void subPrepareConstantsTestAbort(   //Intialize test state.
            )
        {
            Test.boolIsTestAbortOn = false;
        }

        //--------------------------------------------------------------------------------------------------------------
        /*SHARED METHODS*/

        //--------------------------------------------------------------------------------------------------------------
        public static void subSetTestAbort(                 //Marca que desea test.
            )
        {
            Test.boolIsTestAbortOn = true;
        }

        //--------------------------------------------------------------------------------------------------------------
        public static void subResetTestAbort(               //Marca que desea concluir test.
            )
        {
            Test.boolIsTestAbortOn = false;
        }
        /*END-TASK*/

        //--------------------------------------------------------------------------------------------------------------
    }

    //==================================================================================================================
}
/*END-TASK*/
