    package Ti;
import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.*;


//                                                          //AUTHOR: Towa (EELR-Elí Linares).
//                                                          //CO-AUTHOR: Towa ().
//                                                          //DATE: 13-Mayo-2011.
//                                                          //PURPOSE:
//                                                          //Implementation of strTo method for all Java data types.

public class Tes3
{
    //==================================================================================================================
    /*TASK Tes3.subAbort subAbort and subWarnint*/
    //------------------------------------------------------------------------------------------------------------------
    public static void subAbort(                        	//Aborta ejecucion al detectar situación anormal. Puede ser
        //                                                  //      WinForms app o Console app.

        //                                                  //Mensaje descriptivo del aborto.
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
            //                                              //Aborto en WinForms app.
            JOptionPane.showMessageDialog(null, strFullMessage);
        }
        else
        {
            //                                              //Aborto en Console app.
            System.out.println(strFullMessage);

            System.out.println("");
            System.out.println("ENTER KEY TO END");
            Scanner scanner = new Scanner(System.in);
            String strReadLine = scanner.nextLine();
        }

        //                                                  //Existen 2 posibilidades para continuar o terminar
        if (
            Tes3.boolIsTestAbortOn
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
        //                                                  //NO ABORTA PARA PERMITIR UN DIAGNÓSTICO MAS COMPLETO, SIN
        //                                                  //      EMBARGO PUEDE TENER COMPORTAMIENTO IMPREDECIBLE.
        //                                                  //Puede ser WinForms app o Console app.

        //                                                  //Mensaje descriptivo del aborto.
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
            //                                              //Aborto en WinForms app.
            JOptionPane.showMessageDialog(null, strFullMessage);
        }
        else
        {
            //                                              //Aborto en Console app.
            System.out.println(strFullMessage);

            System.out.println("");
            System.out.println("ENTER KEY TO CONTINUE");
            Scanner scanner = new Scanner(System.in);
            String strReadLine = scanner.nextLine();
        }
    }

    /*END-TASK*/
    //=================================================================================================================
    /*TASK Tes3.PrepareStrTo Constants and initializer for strTo*/
    //------------------------------------------------------------------------------------------------------------------
    //                                                      //Set of methods strTo to analyse and format:
    //                                                      //a) Objects: bclass, btuple, enum or sysexcep.
    //                                                      //b) System objects: sysfile, sysdir, syssr & syssw.
    //                                                      //c) Primitives: int, long, num, char & bool.
    //                                                      //d) Simple objects like: str, date, time, ts & type.
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //                                                      //Si un String excede esta longitud, se muestra la longitud
    //                                                      //      ejemplo "abd def.... xyz"<88>.
    private static final int intLONG_STRING = 50;

    //                                                      //In methods strTo, an Item/Row/Matrix of this characters
    //                                                      //      size will be include in one long line.
    private static final int intLONG_ITEM_ROW_MATRIX = 80;

    //                                                      //This will be the maximun space reseved for key when strTo
    //                                                      //      display a dictionary, if we have longhest key the
    //                                                      //      content will not be aligned.
    private static final int intKEY_LEN_MAX = 50;

    //                                                      //Caracter que será usado como substituto cuando un caracter
    //                                                      //      no sea "visible".
    private static final char charSUBSTITUTE_NO_USEFUL_IN_TEXT = '●';

    //                                                      //Se incluyen todos los caracteres del mismo tamaño que A
    //                                                      //      (SizeF en font consolas 10 points).
    //                                                      //Para efectos de entendimiento y documentación se agrupan
    //                                                      //      por UccUnicodeCategory (ascencente), también los
    //                                                      //      caracteres están en orden ascendente.
    //                                                      //Nótese que blank no está en este conjunto dado que su
    //                                                      //      SizeF es diferente.
    //                                                      //ES PROBABLE QUE EN OTRO FONT (diferente de consolas) ESTOS
    //                                                      //      CARACTERES SE MUESTREN DISTINTO.
    //                                                      //DADO QUE EL ESTÁNDAR ES USAR consolas PARA VISUALIZAR EL
    //                                                      //      CÓDIGO TANTO EN PANTALLA COMO EN IMPRESORA SE
    //                                                      //      UTILIZO ESTE FONT.
    //                                                      //TODAS ESTAS CONSTANTES PRETENDEN SER UNA AYUDA PARA
    //                                                      //      CODIFICAR Y PROBAR EL CÓDIGO.
    //                                                      //NÓTESE QUE:
    //                                                      //1. Cualquier caracter de x0000-xFFFF puede ser utilizado.
    //                                                      //2. Solo los caracteres en este conjunto y el blank
    //                                                      //      pueden ser visualizado en pantalla o en texto
    //                                                      //3. Solo loc caracteres en este conjunto, el blank y los de
    //                                                      //      escape pueden ser escritos en archivos de texto.
    //                                                      //4. Los métodos strTo hacen lo necesario para poner mostrar
    //                                                      //      todos los caracteres x0000-xFFFF en font consolas.
    public static T2uccCategoryAndCharsTuple[] arrt2uccCHAR_USEFUL_IN_TEXT = {
        new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.UPPERCASE_LETTER,
            "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞĀĂĄĆĈĊČĎĐĒĔĖĘĚĜĞĠĢĤĦĨĪĬĮİĲĴĶĹĻĽĿŁŃŅŇŊŌŎŐŒŔŖŘŚŜŞŠ" +
            "ŢŤŦŨŪŬŮŰŲŴŶŸŹŻŽƁƂƄƆƇƉƊƋƎƏƐƑƓƔƖƗƘƜƝƟƠƢƤƦƧƩƬƮƯƱƲƳƵƷƸƼǄǇǊǍǏǑǓǕǗǙǛǞǠǢǤǦǨǪǬǮǱǴǶǷǸǺǼǾȀȂȄȆȈȊȌȎȐȒȔȖȘȚȜȞȠȢȤȦȨȪȬȮȰ" +
            "ȲȺȻȽȾɁɃɄɅɆɈɊɌɎΆΈΉΊΌΎΏΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩΪΫϒϓϔϘϚϜϞϠϢϤϦϨϪϬϮϴϷϹϺϽϾϿЀЁЂЃЄЅІЇЈЉЊЋЌЍЎЏАБВГДЕЖЗИЙКЛМНОПРСТ" +
            "УФХЦЧШЩЪЫЬЭЮЯѠѢѤѦѨѪѬѮѰѲѴѶѸѺѼѾҀҊҌҎҐҒҔҖҘҚҜҞҠҢҤҦҨҪҬҮҰҲҴҶҸҺҼҾӀӁӃӅӇӉӋӍӐӒӔӖӘӚӜӞӠӢӤӦӨӪӬӮӰӲӴӶӸӺӼӾԀԂԄԆԈԊԌԎԐԒḀḂḄḆ" +
            "ḈḊḌḎḐḒḔḖḘḚḜḞḠḢḤḦḨḪḬḮḰḲḴḶḸḺḼḾṀṂṄṆṈṊṌṎṐṒṔṖṘṚṜṞṠṢṤṦṨṪṬṮṰṲṴṶṸṺṼṾẀẂẄẆẈẊẌẎẐẒẔẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼẾỀỂỄỆ" +
            "ỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴỶỸἈἉἊἋἌἍἎἏἘἙἚἛἜἝἨἩἪἫἬἭἮἯἸἹἺἻἼἽἾἿὈὉὊὋὌὍὙὛὝὟὨὩὪὫὬὭὮὯᾸᾹᾺΆῈΈῊΉῘῙ" +
            "ῚΊῨῩῪΎῬῸΌῺΏΩℲↃ"),
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
            //                                              //Las siguiente ᾼῌῼ parecen iguales, sin embargo NO LO SON
            //                                              //      las primeras 8 de cada grupo tienen antes arriba un
            //                                              //      pequeño acento que las hace diferentes.
            //                                              //NO SE PORQUE AQUÍ NO SE VE, sin embargo al separarlas como
            //                                              //      char para forma sus t3fake aparecieron los acentos.
            "ǅǈǋǲᾈᾉᾊᾋᾌᾍᾎᾏᾘᾙᾚᾛᾜᾝᾞᾟᾨᾩᾪᾫᾬᾭᾮᾯᾼῌῼ"),
        new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.MODIFIER_LETTER,
            "ʰʱʲʳʴʵʶʷʸʹʺʻʼʽʾʿˀˁˆˇˈˉˊˋˌˍˎˏːˑˠˡˢˣˤˬˮʹͺⁱⁿₐₑₒₓₔ"),
        new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.OTHER_LETTER, "ƻǀǁǂǃʔ"),
        new T2uccCategoryAndCharsTuple(UccUnicodeCategoryEnum.NON_SPACING_MARK,
            //                                              //Se eliminarnos 7 caracteres que parecen tener el mismo
            //                                              //      tamaño que A, sin embargo se muestra hacía arriba.
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

    //                                                      //En fort consolas, estos caracteres se muestran IGUAL (o
    //                                                      //      muy parecidos) a otros.
    //                                                      //Esta información servira para que en strTo de proporcione
    //                                                      //      una buena descripción.
    public static T3fakecharTuple[] arrt3fakecharFAKE = {
        //                                                  //Uppercase Letter
        new T3fakecharTuple('Α', '\u0391', "Fake A(0x0041)"),
        new T3fakecharTuple('А', '\u0410', "Fake A(0x0041)"),
        new T3fakecharTuple('Ӑ', '\u04D0', "Fake Ă(0x0102)"),
        new T3fakecharTuple('Ӓ', '\u04D2', "Fake Ä(0x00C4)"),
        new T3fakecharTuple('Ᾰ', '\u1FB8', "Fake Ă(0x0102)"),
        new T3fakecharTuple('Ᾱ', '\u1FB9', "Fake Ā(0x0100)"),

        new T3fakecharTuple('Ε', '\u0395', "Fake E(0x0045)"),
        new T3fakecharTuple('Ѐ', '\u0400', "Fake È(0x00C8)"),
        new T3fakecharTuple('Ё', '\u0401', "Fake Ë(0x00CB)"),
        new T3fakecharTuple('Е', '\u0415', "Fake E(0x0045)"),
        new T3fakecharTuple('Ӗ', '\u04D6', "Fake Ĕ(0x0114)"),

        new T3fakecharTuple('Ι', '\u0399', "Fake I(0x0049)"),
        new T3fakecharTuple('Ϊ', '\u03AA', "Fake Ï(0x00CF)"),
        new T3fakecharTuple('І', '\u0406', "Fake I(0x0049)"),
        new T3fakecharTuple('Ї', '\u0407', "Fake Ï(0x00CF)"),
        new T3fakecharTuple('Ӏ', '\u04C0', "Fake I(0x0049)"),
        new T3fakecharTuple('Ῐ', '\u1FD8', "Fake Ĭ(0x012C)"),
        new T3fakecharTuple('Ῑ', '\u1FD9', "Fake Ī(0x012A)"),

        new T3fakecharTuple('Ο', '\u039F', "Fake O(0x004F)"),
        new T3fakecharTuple('О', '\u041E', "Fake O(0x004F)"),
        new T3fakecharTuple('Ӧ', '\u04E6', "Fake Ö(0x00D6)"),

        new T3fakecharTuple('Β', '\u0392', "Fake B(0x0042)"),
        new T3fakecharTuple('В', '\u0412', "Fake B(0x0042)"),

        new T3fakecharTuple('Ϲ', '\u03F9', "Fake C(0x0043)"),
        new T3fakecharTuple('С', '\u0421', "Fake C(0x0043)"),

        new T3fakecharTuple('Đ', '\u0110', "Fake Ð(0x00D0)"),
        new T3fakecharTuple('Ɖ', '\u0189', "Fake Ð(0x00D0)"),

        new T3fakecharTuple('Η', '\u0397', "Fake H(0x0048)"),
        new T3fakecharTuple('Н', '\u041D', "Fake H(0x0048)"),

        new T3fakecharTuple('Ј', '\u0408', "Fake J(0x004A)"),

        new T3fakecharTuple('Κ', '\u039A', "Fake K(0x004B)"),
        new T3fakecharTuple('К', '\u041A', "Fake K(0x004B)"),
        new T3fakecharTuple('Ḱ', '\u1E30', "Fake Ќ(0x040C)"),

        new T3fakecharTuple('Μ', '\u039C', "Fake M(0x004D)"),
        new T3fakecharTuple('М', '\u041C', "Fake M(0x004D)"),

        new T3fakecharTuple('Ν', '\u039D', "Fake N(0x004E)"),

        new T3fakecharTuple('Ρ', '\u03A1', "Fake P(0x0050)"),
        new T3fakecharTuple('Р', '\u0420', "Fake P(0x0050)"),

        new T3fakecharTuple('Ѕ', '\u0405', "Fake S(0x0053)"),

        new T3fakecharTuple('Τ', '\u03A4', "Fake T(0x0054)"),
        new T3fakecharTuple('Т', '\u0422', "Fake T(0x0054)"),

        new T3fakecharTuple('Χ', '\u03A7', "Fake X(0x0058)"),
        new T3fakecharTuple('Х', '\u0425', "Fake X(0x0058)"),

        new T3fakecharTuple('Υ', '\u03A5', "Fake Y(0x0059)"),
        new T3fakecharTuple('Ϋ', '\u03AB', "Fake Ÿ(0x0178)"),
        new T3fakecharTuple('Ү', '\u04AE', "Fake Y(0x0059)"),

        new T3fakecharTuple('Ζ', '\u0396', "Fake Z(0x005A)"),

        new T3fakecharTuple('Ȝ', '\u021C', "Fake Ʒ(0x01B7)"),
        new T3fakecharTuple('Λ', '\u039B', "Fake Ʌ(0x0245)"),
        new T3fakecharTuple('Σ', '\u03A3', "Fake Ʃ(0x01A9)"),
        new T3fakecharTuple('ϴ', '\u03F4', "Fake Ɵ(0x019F)"),
        new T3fakecharTuple('Ͻ', '\u03FD', "Fake Ɔ(0x0186)"),
        new T3fakecharTuple('П', '\u041F', "Fake Π(0x03A0)"),
        new T3fakecharTuple('Ѱ', '\u0470', "Fake Ψ(0x03A8)"),
        new T3fakecharTuple('Ѳ', '\u0472', "Fake Ɵ(0x019F)"),
        new T3fakecharTuple('Ә', '\u04D8', "Fake Ə(0x018F)"),
        new T3fakecharTuple('Ӡ', '\u04E0', "Fake Ʒ(0x01B7)"),
        new T3fakecharTuple('Ө', '\u04E8', "Fake Ɵ(0x019F)"),
        new T3fakecharTuple('Ԑ', '\u0510', "Fake Ɛ(0x0190)"),
        new T3fakecharTuple('Ω', '\u2126', "Fake Ω(0x03A9)"),
        new T3fakecharTuple('Ↄ', '\u2183', "Fake Ɔ(0x0186)"),
        //                                                  //Lowercase Letter
        new T3fakecharTuple('а', '\u0430', "Fake a(0x0061)"),
        new T3fakecharTuple('ӓ', '\u04D3', "Fake ä(0x00E4)"),
        new T3fakecharTuple('ѐ', '\u0450', "Fake è(0x00E8)"),
        new T3fakecharTuple('ё', '\u0451', "Fake ë(0x00EB)"),
        new T3fakecharTuple('і', '\u0456', "Fake i(0x0069)"),
        new T3fakecharTuple('ϲ', '\u03F2', "Fake c(0x0063)"),
        new T3fakecharTuple('ϳ', '\u03F3', "Fake j(0x006A)"),
        //                                                  //Space Separator
        new T3fakecharTuple(' ', '\u2000', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2001', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2002', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2003', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2004', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2005', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2006', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2007', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2008', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u2009', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u200A', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u202F', "Fake blank(0x0020)"),
        new T3fakecharTuple(' ', '\u205F', "Fake blank(0x0020)"),
        //                                                  //Dash Punctuation
        new T3fakecharTuple('‐', '\u2010', "Fake -(0x002D)"),
        new T3fakecharTuple('–', '\u2013', "Fake ‒(0x2012)"),
        new T3fakecharTuple('―', '\u2015', "Fake —(0x2014)"),
    };

    //                                                      //The following set of characters do not print rigth (print
    //                                                      //      ? in a box).
    public static T2charDescriptionTuple[] arrt2charNONPRINTABLE =
        {
            //                                                  //Modifier Letter
            new T2charDescriptionTuple('ˆ', "Nonprintable, accent â"),
            new T2charDescriptionTuple('ˇ', "Nonprintable, accent ň"),
            new T2charDescriptionTuple('ˉ', "Nonprintable, accent ā"),
            //                                                  //Initial Quote Punctuation
            new T2charDescriptionTuple('‘', "Nonprintable, open curved (')quote"),
            //                                                  //Final Quote Punctuation
            new T2charDescriptionTuple('’', "Nonprintable, close curved (')quote"),
            new T2charDescriptionTuple('”', "Nonprintable, close curved (\")double quote"),
            //                                                  //Modifier Symbol
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
            //                                                  //Other Punctuation
            new T2charDescriptionTuple('·', "Nonprintable, middle dot"),
        };

    //                                                      //Estos caracteres no deben existir en SIZE_A.
    public static T2charDescriptionTuple[] arrt2charESCAPE =
        {
            new T2charDescriptionTuple('\0', "\\0 Zero"),
            new T2charDescriptionTuple('\007', "\\a Bell (alert)"),
            new T2charDescriptionTuple('\b', "\\b Backspace"),
            new T2charDescriptionTuple('\f', "\\f Formfeed"),
            new T2charDescriptionTuple('\n', "\\n New Line"),
            new T2charDescriptionTuple('\r', "\\r Carriage Return"),
            new T2charDescriptionTuple('\t', "\\t Horizontal Tab"),
            new T2charDescriptionTuple('\u000B', "\\v Vertical Tab"),
        };

    //                                                      //Se incluyen el blank (al principio) y todos los caracteres
    //                                                      //      de la estructura arrt2uccCHAR_USEFUL_IN_TEXT, en el
    //                                                      //      mismo orden en que están.
    //                                                      //Al iniciar (antes de ejecutar el constructor estático)
    //                                                      //      debe contar con información mínima para ser
    //                                                      //      utilizada en los strTo que se usen antes de formar
    //                                                      //      estos 3 objetos en forma definitiva.
    public static String strCHAR_USEFUL_IN_TEXT = " " + "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "abcdefghijklmnopqrstuvwxyz" + "()[]{}";

    //                                                      //blank y todos los caracteres de la estructura
    //                                                      //      arrt2uccCHAR_USEFUL_IN_TEXT ordenados.
    //                                                      //Esta información, en forma provisional, debe ser preparada
    //                                                      //      con lo anterior AL INICIAR el constructor estático.
    public static char[] arrcharUSEFUL_IN_TEXT;

    //                                                      //Caracteres de los cuales no de desea mostrar
    //                                                      //      adicionalmente su hexadecimal.
    //                                                      //Inicialmente serán los mismos que están en
    //                                                      //      USEFUL_IN_TEXT, sin embargo, en el futuro este
    //                                                      //      podrá ser un conjunto reducido.
    public static char[] arrcharDO_NOT_SHOW_HEX;

    //                                                      //En la siguiente estructura se incluyen:
    //                                                      //1. Nonprintable.
    //                                                      //2. Fake, description "'\x????', Fake ?(x????)".
    //                                                      //3. Escape.
    public static T2charDescriptionTuple[] arrt2charDESCRIPTION;

    //                                                      //ESTA PENDIENTE ANALIZAR QUE CARACTERES DE LOS 256x256 QUE
    //                                                      //      SON POSIBLES, SON VISIBLES.

    //                                                      //Cantidad máxima de caracteres diagnosticados que se
    //                                                      //      mostrarán al final de un string
    private static final int intMAX_DIAGNOSTIC = 100;

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

    //                                                      //Object previously processed in other strTo execution.
    private static LinkedList<Object> lstobjPreviouslyProcessed;

    //------------------------------------------------------------------------------------------------------------------
    /*SUPPORT METHODS FOR STATIC CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsForStrTo(
        //                                                  //Método de apoyo llamado en constructor estático. 
        //                                                  //Prepara las constantes para poder utilizarlas.
        //                                                  //1. Inicializa proceso para evitar desplegar 2 veces el
        //                                                  //      mismo objeto
        //                                                  //2. arrt2uccCHAR_USEFUL_IN_TEXT:
        //                                                  //2a. Este ordenada por ucc, sin duplicados.
        //                                                  //2b. Dentro de cada ucc, este ordenada por la secuencia del
        //                                                  //      caracter, sin duplicados
        //                                                  //2c. Todos sean <= al caracter xD7FF.
        //                                                  //2d. En forma global, no haya caracteres duplicados.
        //                                                  //3. arrt3fakecharFAKE:
        //                                                  //3a. ordenar.
        //                                                  //3b. no duplicados
        //                                                  //3c. charFAKE debe estar en USEFUL.
        //                                                  //3d. charHEX y charFAKE debe ser el mismo.
        //                                                  //3e. strDESCRIPTION "..... ?(0x????)", el x???? debe ser la
        //                                                  //      correspondiente al caracter ?.
        //                                                  //4. arrt2charNONPRINTABLE.
        //                                                  //4a. ordenar.
        //                                                  //4b. no duplicados
        //                                                  //4c. debe estar en USEFUL.
        //                                                  //4d. tener descripción.
        //                                                  //5. arrt2charESCAPE.
        //                                                  //5a. ordenar.
        //                                                  //5b. no duplicados
        //                                                  //5c. NO debe estar en USEFUL.
        //                                                  //5d. tener descripción.

        //                                                  //Recibe caracteres básicos (por si hay un strTo antes de
        //                                                  //      formar el resultado deseado).
        //                                                  //Regresa, blank + caracteres en USEFUL en el orden que
        //                                                  //      están.
        Ostring ostrCHAR_USEFUL_IN_TEXT_O,
        //                                                  //Caracteres anteriores ordenados.
        Oobject <char[]> oarrcharUSEFUL_IN_TEXT_O,
        Oobject <char[]> oarrcharDO_NOT_SHOW_HEX_O,
        //                                                  //Se incluyen:
        //                                                  //1. Nonprintable.
        //                                                  //2. Fake, description "'\x????', Fake ?(x????)".
        //                                                  //3. Escape.
        //                                                  //Al juntarse los 3 conjuntos de descripciones no deben
        //                                                  //      resultar duplicados.
        Oobject <T2charDescriptionTuple[]> oarrt2charDESCRIPTION_O
        )
    {
        Tes3.subInitializeLstobjPreviouslyProcessed();

        Tes3.subPrepareConstantsArrt2uccCharUsefulInText(ostrCHAR_USEFUL_IN_TEXT_O, oarrcharUSEFUL_IN_TEXT_O);
        Tes3.strCHAR_USEFUL_IN_TEXT = ostrCHAR_USEFUL_IN_TEXT_O.v;
        Tes3.arrcharUSEFUL_IN_TEXT = oarrcharUSEFUL_IN_TEXT_O.v;

        oarrcharDO_NOT_SHOW_HEX_O.v = oarrcharUSEFUL_IN_TEXT_O.v;

        Tes3.subPrepareConstantsArrt3fakecharFake();
        Tes3.subPrepareConstantsArrt2charNonprintable();
        Tes3.subPrepareConstantsArrt2charEscape();

        Tes3.subPrepareConstantsArrt2charDescription(oarrt2charDESCRIPTION_O);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareConstantsArrt2uccCharUsefulInText(
        //                                                  //2. arrt2uccCHAR_USEFUL_IN_TEXT:
        //                                                  //2a. Este ordenada por ucc, sin duplicados.
        //                                                  //2b. Dentro de cada ucc, este ordenada por la secuencia del
        //                                                  //      caracter, sin duplicados
        //                                                  //2c. Todos sean <= al caracter xD7FF.
        //                                                  //2d. En forma global, no haya caracteres duplicados.

        //                                                  //blank + caracteres en USEFUL en el orden que están.
        Ostring ostrCHAR_USEFUL_IN_TEXT_O,
        //                                                  //Caracteres anteriores ordenados.
        Oobject <char[]> oarrcharUSEFUL_IN_TEXT_O
        )
    {
        //                                                  //To easy code
        T2uccCategoryAndCharsTuple[] arrt2ucc = Tes3.arrt2uccCHAR_USEFUL_IN_TEXT;

        //                                                  //Verifica secuencia de cada ucc
        for (int intUcc = 1; intUcc < arrt2ucc.length; intUcc = intUcc + 1)
        {
            if (
                //                                          //No estan en orden ascendente
                arrt2ucc[intUcc - 1].ucc.compareTo(arrt2ucc[intUcc].ucc) >= 0
                )
                Tes3.subAbort(Tes3.strTo(arrt2ucc[intUcc - 1].ucc, "arrt2ucc[" + (intUcc - 1) + "].ucc") + ", " +
                    Tes3.strTo(arrt2ucc[intUcc].ucc, "arrt2ucc[" + intUcc + "].ucc") +
                    " should be in ascending order");
        }

        //                                                  //Verifica chars en cada ucc
        for (int intUcc = 0; intUcc < arrt2ucc.length; intUcc = intUcc + 1)
        {
            String strChars = arrt2ucc[intUcc].strChars;

            //                                              //Verifica orden
            for (int intC = 1; intC < strChars.length(); intC = intC + 1)
            {
                if (
                    //                                      //No estan en orden ascendente
                    strChars.charAt(intC - 1) >= strChars.charAt(intC)
                    )
                    Tes3.subAbort(Tes3.strTo(strChars, "arrt2ucc[" + intUcc + "].strChars") + ", " +
                        Tes3.strTo(strChars.charAt(intC - 1), "strChars.charAt(" + (intC - 1) + ")") + ", " +
                        Tes3.strTo((int)strChars.charAt(intC - 1), "(int)strChars.charAt(" + (intC - 1) + ")") + ", " +
                        Tes3.strTo(strChars.charAt(intC), "strChars.charAt(" + intC + ")") + ", " +
                        Tes3.strTo((int)strChars.charAt(intC), "(int)strChars.charAt(" + intC + ")") +
                        " should be in ascending order");
            }
        }

        //                                                  //blank + junta todos los caracters
        ostrCHAR_USEFUL_IN_TEXT_O.v = " ";
        for (int intUcc = 0; intUcc < arrt2uccCHAR_USEFUL_IN_TEXT.length; intUcc = intUcc + 1)
        {
            ostrCHAR_USEFUL_IN_TEXT_O.v = ostrCHAR_USEFUL_IN_TEXT_O.v + arrt2ucc[intUcc].strChars;
        }

        //                                                  //Verifica que forma global no estén duplicados
        oarrcharUSEFUL_IN_TEXT_O.v = ostrCHAR_USEFUL_IN_TEXT_O.v.toCharArray();
        Arrays.sort(oarrcharUSEFUL_IN_TEXT_O.v);
        Tools.subVerifyDuplicate(oarrcharUSEFUL_IN_TEXT_O.v, "oarrcharUSEFUL_IN_TEXT_O.v");
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareConstantsArrt3fakecharFake(
        //                                                  //3. arrt3fakecharFAKE:
        //                                                  //3a. ordenar.
        //                                                  //3b. no duplicados
        //                                                  //3c. charFAKE debe estar en USEFUL.
        //                                                  //3d. charHEX y charFAKE debe ser el mismo.
        //                                                  //3e. strDESCRIPTION debe ser "Fake blank(0x0020" o
        //                                                  //      "Fake ?(0x????)" donde ? y ???? representan el
        //                                                  //      mismo caracter.
        )
    {
        Arrays.sort(Tes3.arrt3fakecharFAKE);

        //                                                  //Verifica no duplicados
        Tools.subVerifyDuplicate(Tes3.arrt3fakecharFAKE, "Tes3.arrt3fakecharFAKE");

        //                                                  //Verifica chars en tupla
        for (int intT3 = 0; intT3 < Tes3.arrt3fakecharFAKE.length; intT3 = intT3 + 1)
        {
            if (
                Arrays.binarySearch(Tes3.arrcharUSEFUL_IN_TEXT, Tes3.arrt3fakecharFAKE[intT3].charFAKE) < 0
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt3fakecharFAKE[intT3].charFAKE,
                        "Tes3.arrt3fakecharFAKE[" + intT3 + "].charFAKE") +
                        " do not exist in USEFUL_IN_TEXT");

            if (
                Tes3.arrt3fakecharFAKE[intT3].charFAKE != Tes3.arrt3fakecharFAKE[intT3].charHEX
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt3fakecharFAKE[intT3].charFAKE,
                        "Tes3.arrt3fakecharFAKE[" + intT3 + "].charFAKE") +
                        ", " +
                        Tes3.strTo(Tes3.arrt3fakecharFAKE[intT3].charHEX,
                            "Tes3.arrt3fakecharFAKE[" + intT3 + "].charHEX") +
                        " should be equal");

            //                                              //Verifica descripción

            //                                              //To easy code
            String strDESCRIPTION = Tes3.arrt3fakecharFAKE[intT3].strDESCRIPTION;

            if (
                strDESCRIPTION == null
                )
                Tes3.subAbort(
                    Tes3.strTo(strDESCRIPTION, "arrt3fakecharFAKE[" + intT3 + "].strDESCRIPTION") +
                        " can not be null");

            /*CASE*/
            if (
                strDESCRIPTION.equals("Fake blank(0x0020)")
                )
            {
                //                                          //Es una opción correcta, NO HACE NADA
            }
            else if (
                //                                          //Tiene la forma correcta
                (strDESCRIPTION.length() == "Fake ?(0x????)".length()) &&
                    strDESCRIPTION.startsWith("Fake ") &&
                    (strDESCRIPTION.substring("Fake ?".length(), "Fake ?".length() + "(0x".length()).equals("(0x")) &&
                    (strDESCRIPTION.charAt(strDESCRIPTION.length() - 1) == ')')
                )
            {
                //                                          //Verifica descripción
                char charFaked = strDESCRIPTION.charAt("Fake ".length());
                String strCharFaked = String.format("%04X", (int)charFaked);

                String strFakedHex = strDESCRIPTION.substring("Fake ?(0x".length(), "Fake ?(0x".length() + 4);
                if (
                    //                                      //? y ???? no representan el mismo caracter
                    !strCharFaked.equals(strFakedHex)
                    )
                    Tes3.subAbort(Tes3.strTo(charFaked, "charFaked") + ", " +
                        Tes3.strTo(strCharFaked, "strCharFaked") + ", " +
                        Tes3.strTo(strDESCRIPTION, "Tes3.arrt3fakecharFAKE[" + intT3 + "].strDESCRIPTION") +
                        " should include hexadecimal char code");

                //                                          //Es una opción correcta, NO HACE NADA
            }
            else
            {
                Tes3.subAbort(Tes3.strTo(strDESCRIPTION, "arrt3fakecharFAKE[" + intT3 + "].strDESCRIPTION") +
                    " invalid description");
            }
            /*END-CASE*/
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareConstantsArrt2charNonprintable(
        //                                                  //4. arrt2charNONPRINTABLE.
        //                                                  //4a. ordenar.
        //                                                  //4b. no duplicados
        //                                                  //4c. debe estar en USEFUL.
        //                                                  //4d. tener descripción.
        )
    {
        Arrays.sort(Tes3.arrt2charNONPRINTABLE);

        //                                                  //Verifica no duplicados
        Tools.subVerifyDuplicate(Tes3.arrt2charNONPRINTABLE, "Tes3.arrt2charNONPRINTABLE");

        //                                                  //Verifica chars en tupla
        for (int intT2 = 0; intT2 < arrt2charNONPRINTABLE.length; intT2 = intT2 + 1)
        {

            if (
                Arrays.binarySearch(Tes3.arrcharUSEFUL_IN_TEXT, Tes3.arrt2charNONPRINTABLE[intT2].charX) < 0
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt2charNONPRINTABLE[intT2].charX,
                        "Tes3.arrt2charNONPRINTABLE[" + intT2 + "].charX") +
                        " does not exist in USEFUL_IN_TEXT");

            if (
                Tes3.arrt2charNONPRINTABLE[intT2].strDESCRIPTION == null
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt2charNONPRINTABLE[intT2].strDESCRIPTION,
                        "Tes3.arrt2charNONPRINTABLE[" + intT2 + "].strDESCRIPTION") +
                        " can not be null");

            if (
                Tes3.arrt2charNONPRINTABLE[intT2].strDESCRIPTION == ""
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt2charNONPRINTABLE[intT2].strDESCRIPTION,
                        "Tes3.arrt2charNONPRINTABLE[" + intT2 + "].strDESCRIPTION") +
                        " should have a description");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareConstantsArrt2charEscape(
        //                                                  //5. arrt2charESCAPE.
        //                                                  //5a. ordenar.
        //                                                  //5b. no duplicados
        //                                                  //5c. NO debe estar en USEFUL.
        //                                                  //5d. tener descripción.
        )
    {
        Arrays.sort(Tes3.arrt2charESCAPE);

        //                                                  //Verifica no duplicados
        Tools.subVerifyDuplicate(Tes3.arrt2charESCAPE, "Tes3.arrt2charESCAPE");

        //                                                  //Verifica chars en tupla
        for (int intT2 = 0; intT2 < arrt2charESCAPE.length; intT2 = intT2 + 1)
        {
            if (
                Arrays.binarySearch(Tes3.arrcharUSEFUL_IN_TEXT, Tes3.arrt2charESCAPE[intT2].charX) >= 0
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt2charESCAPE[intT2].charX, "Tes3.arrt2charESCAPE[" + intT2 + "].charX") +
                        " exists in USEFUL_IN_TEXT");

            if (
                Tes3.arrt2charESCAPE[intT2].strDESCRIPTION == null
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt2charESCAPE[intT2].strDESCRIPTION,
                        "Tes3.arrt2charESCAPE[" + intT2 + "].strDESCRIPTION") +
                        " can not be null");

            if (
                Tes3.arrt2charESCAPE[intT2].strDESCRIPTION.equals("")
                )
                Tes3.subAbort(
                    Tes3.strTo(Tes3.arrt2charESCAPE[intT2].strDESCRIPTION,
                        "Tes3.arrt2charESCAPE[" + intT2 + "].strDESCRIPTION") +
                        " should have a description");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static void subPrepareConstantsArrt2charDescription(
        //                                                  //Se incluyen:
        //                                                  //1. Nonprintable.
        //                                                  //2. Fake, description "'\x????', Fake ?(x????)".
        //                                                  //3. Escape.
        //                                                  //Al juntarse los 3 conjuntos de descripciones no deben
        //                                                  //      resultar duplicados.
        Oobject <T2charDescriptionTuple[]> oarrt2charDESCRIPTION_O
        )
    {
        oarrt2charDESCRIPTION_O.v = new T2charDescriptionTuple[Tes3.arrt2charNONPRINTABLE.length +
            Tes3.arrt3fakecharFAKE.length + Tes3.arrt2charESCAPE.length];

        System.arraycopy(Tes3.arrt2charNONPRINTABLE, 0, oarrt2charDESCRIPTION_O.v, 0,
            Tes3.arrt2charNONPRINTABLE.length);

        //                                                  //Desplazamiento para mover arrt3
        int intDesp = Tes3.arrt2charNONPRINTABLE.length;

        for (int intT4 = 0; intT4 < Tes3.arrt3fakecharFAKE.length; intT4 = intT4 + 1)
        {
            oarrt2charDESCRIPTION_O.v[intDesp + intT4] = new T2charDescriptionTuple(
                Tes3.arrt3fakecharFAKE[intT4].charFAKE, Tes3.arrt3fakecharFAKE[intT4].strDESCRIPTION);
        }

        System.arraycopy(Tes3.arrt2charESCAPE, 0, oarrt2charDESCRIPTION_O.v,
            Tes3.arrt2charNONPRINTABLE.length + Tes3.arrt3fakecharFAKE.length, Tes3.arrt2charESCAPE.length);

        Arrays.sort(oarrt2charDESCRIPTION_O.v);

        Tools.subVerifyDuplicate(oarrt2charDESCRIPTION_O.v, "oarrt2charDESCRIPTION_O.v");
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subInitializeLstobjPreviouslyProcessed(
        //                                                  //Reset list of previously porcessed
        )
    {
        Tes3.lstobjPreviouslyProcessed = new LinkedList<Object>();
    }

    //==================================================================================================================
    /*TASK Tes3.Types Set of Methods to Analize Types*/
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/
                                                            //Towa's standard primitives
    //                                                      //Towa's standard primitives
    private static /*readonly*/ String[][] arr2strPRIMITIVE_TYPE_AND_PREFIX =
    {
        //                                                  //TO ADD NEW PRIMARY TYPES:
        //                                                  //a. Add an entry in this array (standard prefix xxxx).
        //                                                  //b. Add a method subfunConvertAndBoxXxxx, similar to
        //                                                  //      subfunConvertAndBoxTs).
        //                                                  //c. Add a method strAnalizeAndFormatXxxx, similar to
        //                                                  //      strAnalizeAndFormatTs).
        //                                                  //d. Add case branch in method
        //                                                  //      subfunConvertAndBoxPrimitive.
        //                                                  //e. Add case branch in method strAnalizeAndFormatBbox.
        //
        //                                                  //The java primitives, when passed as an argument that receives
        //                                                  //      an Object, are automatically boxed in their
        //                                                  //      corresponding Object (Integer for int, Double for
        //                                                  //      double, etc.). Because the methods in strTo declare
        //                                                  //      Object parameters, the "primitive" objects must be
        //                                                  //      added here, so that when receiving an Integer, it will
        //                                                  //      work as if it had received an int.
        //                                                  //A way to avoid this would be to do a method overload for each
        //                                                  //      kind of primitive for the methods that receive Objects,
        //                                                  //      for example, making 5 overloads to the method
        //                                                  //      strTo(Object, string) like this:
        //                                                  //      strTo(int, string); strTo(long, string);
        //                                                  //      strTo(char, string); strTo(double, string);
        //                                                  //      strTo(boolean, string).
        //                                                  //      However, because this method(strTo) also invokes other
        //                                                  //      methods that have Objects as parameters, the number of
        //                                                  //      method overloads would be too much. Because of this, the
        //                                                  //      structure with the Object parameters will be kept for
        //                                                  //      now, but may change in the future.
        { "int", "int" }, { "long", "long" }, { "boolean", "bool" }, { "char", "char" }, { "double", "num" },
        { "Integer", "int" }, { "Long", "long" }, { "Boolean", "bool" }, { "Character", "char" },
        { "Double", "num" },
        //                                                  //C# structures should be handled like primitives
        { "LocalDateTime", "ts" },
    };

          												    //Both arrays order by first.

    private static String[] arrstrPRIMITIVE_TYPE;
    private static String[] arrstrPRIMITIVE_PREFIX;

                                                            //Towa's standard system types
    private static /*readonly*/ String[][] arr2strSYSTEM_TYPE_AND_PREFIX = {
                                                            //TO ADD NEW SYSTEM TYPES:
                                                            //a. Add an entry in this array (standard prefix yyyyy).
                                                            //b. Add a method subfunConvertYyyyy, similar to
                                                            //      subfunConvertSysdir).
                                                            //c. Add a method strAnalizeAndFormatYyyyy, similar to
                                                            //      strAnalizeAndFormatSysdir).
                                                            //d. Add case branch in method subfunConvertSystemType.
                                                            //e. Add case branch in method
                                                            //      strAnalizeAndFormatSystemType.

                                                            //String should be handled like system tyes.
        { "String", "str" },
                                                            //System types
        { "Class", "type" }, { "Path", "Sysdir" }, { "File", "Sysfile" },
        { "Scanner", "Syssr" }, { "PrintWriter", "Syssw" },
    };


                                                            //Both arrays order by first.
    private static /*readonly*/ String[] arrstrSYSTEM_TYPE;
    private static /*readonly*/ String[] arrstrSYSTEM_PREFIX;

                                                            //Towa's standard other types
    private final static String strGENERIC_LIST_TYPE = "LinkedList";
    private final static String strGENERIC_QUEUE_TYPE = "ConcurrentLinkedDeque";
    private final static String strGENERIC_STACK_TYPE = "Stack";
    private final static String strGENERIC_DICTIONARY_TYPE = "LinkedHashMap";
    private final static String strGENERIC_KEYVALUEPAIR_TYPE = "Entry";
    private static /*readonly*/ String[][] arr2strGENERIC_TYPE_AND_PREFIX = {
        { strGENERIC_LIST_TYPE, "lst" }, { strGENERIC_QUEUE_TYPE, "queue" }, { strGENERIC_STACK_TYPE, "stack" },
        { strGENERIC_DICTIONARY_TYPE, "dic" }, { strGENERIC_KEYVALUEPAIR_TYPE, "kvp" },
    };

                                                            //Both arrays order by first.
    private static String[] arrstrGENERIC_TYPE;
    private static String[] arrstrGENERIC_PREFIX;


                                                            //Boxing classes used for boxing primitives when they must
                                                            //      be passed by reference (out, ref).
    private static String[][] arr2strBOXING_TYPE_AND_PREFIX = {
                                                            //TO ADD NEW BOXING TYPES:
                                                            //a. Add an entry in this array.
        { "Oint", "int" }, { "Olong", "long" }, { "Obool", "bool" }, { "Ochar","char" }, { "Onum","num" }
    };

                                                            //Both arrays order by first.
    public static String[] arrstrBOXING_TYPE;
    public static String[] arrstrBOXING_PREFIX;

    //------------------------------------------------------------------------------------------------------------------
    /*SUPPORT METHODS FOR STATIC CONSTRUCTORS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantTypes(
        //                                                  //Order and varify constants.

        Oobject <String[]> arrstrPRIMITIVE_TYPE_O,
        Oobject <String[]> arrstrPRIMITIVE_PREFIX_O,
        Oobject <String[]> arrstrSYSTEM_TYPE_O,
        Oobject <String[]> arrstrSYSTEM_PREFIX_O,
        Oobject <String[]> arrstrGENERIC_TYPE_O,
        Oobject <String[]> arrstrGENERIC_PREFIX_O,
        Oobject <String[]> arrstrBOXING_TYPE_O,
        Oobject <String[]> arrstrBOXING_PREFIX_O
        )
    {
        //                                                  //Order arr2strPRIMITIVE_TYPE_AND_PREFIX.
        arrstrPRIMITIVE_TYPE_O.v = new String[Tes3.arr2strPRIMITIVE_TYPE_AND_PREFIX.length];
        arrstrPRIMITIVE_PREFIX_O.v = new String[arrstrPRIMITIVE_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrPRIMITIVE_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrPRIMITIVE_TYPE_O.v[intI] = Tes3.arr2strPRIMITIVE_TYPE_AND_PREFIX[intI][0];
            arrstrPRIMITIVE_PREFIX_O.v[intI] = Tes3.arr2strPRIMITIVE_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrPRIMITIVE_TYPE_O.v, arrstrPRIMITIVE_PREFIX_O.v);

        for (int intI = 1; intI < arrstrPRIMITIVE_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                //                                          //Is duplicated.
                arrstrPRIMITIVE_TYPE_O.v[intI].equals(arrstrPRIMITIVE_TYPE_O.v[intI - 1])
                )
                Tes3.subAbort(Tes3.strTo(arrstrPRIMITIVE_TYPE_O, "arrstrPRIMITIVE_TYPE_O") + ", " +
                    Tes3.strTo(arrstrPRIMITIVE_TYPE_O.v[intI], "arrstrPRIMITIVE_TYPE_O[" + intI + "]") +
                    " is duplicated");
        }

        //                                                  //Order arr2strSYSTEM_TYPE_AND_PREFIX.
        arrstrSYSTEM_TYPE_O.v = new String[Tes3.arr2strSYSTEM_TYPE_AND_PREFIX.length];
        arrstrSYSTEM_PREFIX_O.v = new String[arrstrSYSTEM_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrSYSTEM_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrSYSTEM_TYPE_O.v[intI] = Tes3.arr2strSYSTEM_TYPE_AND_PREFIX[intI][0];
            arrstrSYSTEM_PREFIX_O.v[intI] = Tes3.arr2strSYSTEM_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrSYSTEM_TYPE_O.v, arrstrSYSTEM_PREFIX_O.v);

        for (int intI = 1; intI < arrstrSYSTEM_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                //                                          //Is duplicated.
                arrstrSYSTEM_TYPE_O.v[intI].equals(arrstrSYSTEM_TYPE_O.v[intI - 1])
                )
                Tes3.subAbort(Tes3.strTo(arrstrSYSTEM_TYPE_O, "arrstrSYSTEM_TYPE_O") + ", " +
                    Tes3.strTo(arrstrSYSTEM_TYPE_O.v[intI], "arrstrSYSTEM_TYPE_O[" + intI + "]") + " is duplicated");
        }

        //                                                  //Order arr2strGENERIC_TYPE_AND_PREFIX.
        arrstrGENERIC_TYPE_O.v = new String[Tes3.arr2strGENERIC_TYPE_AND_PREFIX.length];
        arrstrGENERIC_PREFIX_O.v = new String[arrstrGENERIC_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrGENERIC_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrGENERIC_TYPE_O.v[intI] = Tes3.arr2strGENERIC_TYPE_AND_PREFIX[intI][0];
            arrstrGENERIC_PREFIX_O.v[intI] = Tes3.arr2strGENERIC_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrGENERIC_TYPE_O.v, arrstrGENERIC_PREFIX_O.v);

        for (int intI = 1; intI < arrstrGENERIC_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                //                                          //Is duplicated.
                arrstrGENERIC_TYPE_O.v[intI].equals(arrstrGENERIC_TYPE_O.v[intI - 1])
                )
                Tes3.subAbort(Tes3.strTo(arrstrGENERIC_TYPE_O, "arrstrGENERIC_TYPE_O") + ", " +
                    Tes3.strTo(arrstrGENERIC_TYPE_O.v[intI], "arrstrGENERIC_TYPE_O[" + intI + "]") + " is duplicated");
        }

        arrstrBOXING_TYPE_O.v = new String[Tes3.arr2strBOXING_TYPE_AND_PREFIX.length];
        arrstrBOXING_PREFIX_O.v = new String[arrstrBOXING_TYPE_O.v.length];

        for (int intI = 0; intI < arrstrBOXING_TYPE_O.v.length; intI = intI + 1)
        {
            arrstrBOXING_TYPE_O.v[intI] = Tes3.arr2strBOXING_TYPE_AND_PREFIX[intI][0];
            arrstrBOXING_PREFIX_O.v[intI] = Tes3.arr2strBOXING_TYPE_AND_PREFIX[intI][1];
        }

        Tools.sort(arrstrBOXING_TYPE_O.v, arrstrBOXING_PREFIX_O.v);

        for (int intI = 1; intI < arrstrBOXING_TYPE_O.v.length; intI = intI + 1)
        {
            if (
                //                                          //Is duplicated.
                arrstrBOXING_TYPE_O.v[intI].equals(arrstrBOXING_TYPE_O.v[intI - 1])
                )
                Tes3.subAbort(Tes3.strTo(arrstrBOXING_TYPE_O, "arrstrBOXING_TYPE_O") + ", " +
                    Tes3.strTo(arrstrBOXING_TYPE_O.v[intI], "arrstrBOXING_TYPE_O[" + intI + "]") + " is duplicated");
        }
    }

    //Static constructor
    static
    //                                                      //Prepara las constantes para poder utilizarlas.
    //                                                      //CADA VEZ QUE SE AÑADAN CONSTANTES QUE REQUIERAN SER
    //                                                      //      INICIALIZADAS, SE AÑADE LA LLAMADA A OTRO MÉTODO.
    {
        //                                                  //Se requiere preparar la siguiente información que será
        //                                                  //      usada en forma provisional por los strTo
        Tes3.arrcharUSEFUL_IN_TEXT = Tes3.strCHAR_USEFUL_IN_TEXT.toCharArray();
        Arrays.sort(Tes3.arrcharUSEFUL_IN_TEXT);
        Tes3.arrcharDO_NOT_SHOW_HEX = Tes3.arrcharUSEFUL_IN_TEXT;
        Tes3.arrt2charDESCRIPTION = new T2charDescriptionTuple[] { };

        /*subPrepareConstantTypes*/
        Oobject<String[]> oarrstrPRIMITIVE_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrPRIMITIVE_PREFIX = new Oobject<String[]>();
        Oobject<String[]> oarrstrSYSTEM_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrSYSTEM_PREFIX = new Oobject<String[]>();
        Oobject<String[]> oarrstrGENERIC_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrGENERIC_PREFIX = new Oobject<String[]>();
        Oobject<String[]> oarrstrBOXING_TYPE = new Oobject<String[]>();
        Oobject<String[]> oarrstrBOXING_PREFIX = new Oobject<String[]>();

        Tes3.subPrepareConstantTypes(oarrstrPRIMITIVE_TYPE, oarrstrPRIMITIVE_PREFIX, oarrstrSYSTEM_TYPE,
            oarrstrSYSTEM_PREFIX, oarrstrGENERIC_TYPE, oarrstrGENERIC_PREFIX, oarrstrBOXING_TYPE, oarrstrBOXING_PREFIX);

        Tes3.arrstrPRIMITIVE_TYPE = oarrstrPRIMITIVE_TYPE.v;
        Tes3.arrstrPRIMITIVE_PREFIX = oarrstrPRIMITIVE_PREFIX.v;
        Tes3.arrstrSYSTEM_TYPE = oarrstrSYSTEM_TYPE.v;
        Tes3.arrstrSYSTEM_PREFIX = oarrstrSYSTEM_PREFIX.v;
        Tes3.arrstrGENERIC_TYPE = oarrstrGENERIC_TYPE.v;
        Tes3.arrstrGENERIC_PREFIX = oarrstrGENERIC_PREFIX.v;
        Tes3.arrstrBOXING_TYPE = oarrstrBOXING_TYPE.v;
        Tes3.arrstrBOXING_PREFIX = oarrstrBOXING_PREFIX.v;
        /**/

        /*subPrepareConstantsForStrTo*/
        Ostring ostrCHAR_USEFUL_IN_TEXT = new Ostring(Tes3.strCHAR_USEFUL_IN_TEXT);
        Oobject<char[]> oarrcharUSEFUL_IN_TEXT = new Oobject<char[]>(Tes3.arrcharUSEFUL_IN_TEXT);
        Oobject<char[]> oarrcharDO_NOT_SHOW_HEX = new Oobject<char[]>(Tes3.arrcharDO_NOT_SHOW_HEX);
        Oobject<T2charDescriptionTuple[]> oarrt2charDESCRIPTION =
            new Oobject<T2charDescriptionTuple[]>(Tes3.arrt2charDESCRIPTION);

        Tes3.subPrepareConstantsForStrTo(ostrCHAR_USEFUL_IN_TEXT, oarrcharUSEFUL_IN_TEXT, oarrcharDO_NOT_SHOW_HEX,
            oarrt2charDESCRIPTION);

        Tes3.strCHAR_USEFUL_IN_TEXT = ostrCHAR_USEFUL_IN_TEXT.v;
        Tes3.arrcharUSEFUL_IN_TEXT = oarrcharUSEFUL_IN_TEXT.v;
        Tes3.arrcharDO_NOT_SHOW_HEX = oarrcharDO_NOT_SHOW_HEX.v;
        Tes3.arrt2charDESCRIPTION = oarrt2charDESCRIPTION.v;
        /**/

        Tes3.subPrepareConstantsGetObjId();
        Tes3.subPrepareConstantsToBlockFormat();
        Tes3.subPrepareConstantsSubLogAndTrace();
        Tes3.subPrepareConstantsTestAbort();
    }

    //==================================================================================================================
    /*TASK Tes3.strTo Set of Methods for Primitive Java Types*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        int int_I,
                                                            //Option should be SHORT.
        StrtoEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != StrtoEnum.SHORT
            )
            Tes3.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatInt(int_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        int int_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tes3.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatInt(int_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatInt(           //Prepara un entero (long) para su despliege con información
                                                            //      adicional si es mínimo o máximo.
                                                            //Ejemplos:
                                                            //1 -3,835.
                                                            //2 -9,223,372,036,854,775,808<MinValue>.
                                                            //3 9,223,372,036,854,775,807<MaxValue>.
                                                            //str, String para despligue con información adicional.

                                                            //Entero a desplegar.
        int int_I
    )
    {
                                                            //It's prepare without additional information. It's separed
                                                            //      in thousands by commas ("1000" = "1,000").
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        String strAnalizeAndFormatInt = decimalFormatter.format(int_I);

                                                            //Add information in case it is the minimum or maximum Int.
        if (
            int_I == Integer.MIN_VALUE
            )
        {
            strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MinValue>";
        }
        else if (
            int_I == Integer.MAX_VALUE
            )
        {
            strAnalizeAndFormatInt = strAnalizeAndFormatInt + "<MaxValue>";
        }
        else
        {
                                                            //No additional information.
        }
        return strAnalizeAndFormatInt;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        long long_I,
                                                            //Option should be SHORT.
        StrtoEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != StrtoEnum.SHORT
            )
            Tes3.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatLong(long_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        long long_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tes3.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatLong(long_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatLong(          //Prepara un entero (long) para su despliege con información
                                                            //      adicional si es mínimo o máximo.
                                                            //Ejemplos:
                                                            //1 -3,835.
                                                            //2 -9,223,372,036,854,775,808<MinValue>.
                                                            //3 9,223,372,036,854,775,807<MaxValue>.
                                                            //str, String para despligue con información adicional.

                                                            //Entero a desplegar.
        long long_I
    )
    {
                                                            //It's prepare without additional information. It's separed
                                                            //      in thousands by commas ("1000" = "1,000").
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        String strAnalizeAndFormatLong = decimalFormatter.format(long_I);

                                                            //Añade información adicional si es mínimo o máximo.
        if (
            long_I == Long.MIN_VALUE
            )
        {
            strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MinValue>";
        }
        else if (
            long_I == Long.MAX_VALUE
            )
        {
            strAnalizeAndFormatLong = strAnalizeAndFormatLong + "<MaxValue>";
        }
        else
        {
                                                            //Sin información adicional.
        }

        return strAnalizeAndFormatLong;
    }
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        double num_I,
                                                            //Option should be SHORT.
        StrtoEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != StrtoEnum.SHORT
            )
            Tes3.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatNum(num_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        double num_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tes3.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatNum(num_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatNum(           //Prepara un número para su despliege con información
                                                            //      adicional si es mínimo, máximo, etc.
                                                            //Ejemplos:
                                                            //1 -1.23456789012345E+038.
                                                            //2 -1.79769313486232E+308<MinValue>;
                                                            //3 1.79769313486232E+308<MaxValue>;
                                                            //4 NaN<0/0>;
                                                            //5 -Infinity<-?/0>;
                                                            //6 Infinity<?/0>;
                                                            //str, String para despligue con información adicional.

                                                            //Entero a desplegar.
        double num_I
    )
    {
                                                            //Por lo pronto prepara sin información adicional.
        String strAnalizeAndFormatNum = String.format("%f", num_I);

                                                            //Añade información adicional si es mínimo o máximo.
        if (
            num_I == Double.MIN_VALUE
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MinValue>";
        }
        else if (
            num_I == Double.MAX_VALUE
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<MaxValue>";
        }
        else if (
            num_I == Double.NEGATIVE_INFINITY
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<-?/0.0>";
        }
        else if (
            num_I == Double.POSITIVE_INFINITY
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<?/0.0>";
        }
        else if (
                                                            //A number has 4 posibibilities:
                                                            //1. Beetwen MinValue and MaxValue
                                                            //2. NegativeInfinity, (-?/0.0).
                                                            //3. PositiveInfinity, (?/0.0).
                                                            //4. NaN, (0.0/0.0).
                                                            //num_I == Double.NaN, DO NOT FUNCTION AS EXPECTED
            !((num_I >= Double.MIN_VALUE) && (num_I <= Double.MAX_VALUE))
            )
        {
            strAnalizeAndFormatNum = strAnalizeAndFormatNum + "<0.0/0.0>";
        }
        else
        {
                                                            //Sin información adicional.
        }

        return strAnalizeAndFormatNum;
    }
    //------------------------------------------------------------------------------------------------------------------

    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        boolean bool_I,
                                                            //Option should be SHORT.
        StrtoEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != StrtoEnum.SHORT
            )
            Tes3.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatBool(bool_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        boolean bool_I,
                                                            //Variable name of the data to display
        String strText_I

    )
    {
        if (
            strText_I == null
            )
            Tes3.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatBool(bool_I) + ")";
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static String strAnalizeAndFormatBool(          //Prepara un booleno para su despliege.
                                                            //Ejemplos:
                                                            //1 true.
                                                            //2 false.
                                                            //str, String para despligue.
        boolean bool_I
    )
    {
                                                            //Se asigna true o false según corresponda.
                                                            //Se hace de esta forma dado que el bool_I.ToString()
                                                            //      produce "True" o  "False" (iniciando con mayúsculas
                                                            //      que es distinto a las literales true y false).
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
    //------------------------------------------------------------------------------------------------------------------

    public static String strTo(                             //Prepare for SHORT display

                                                            //Data to display
        char char_I,
                                                            //Option should be SHORT.
        StrtoEnum testoptionSHORT_I

    )
    {
        if (
            testoptionSHORT_I != StrtoEnum.SHORT
            )
            Tes3.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strAnalizeAndFormatChar(char_I);
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static String strTo(                             //Prepare for FULL display

                                                            //Data to display
        char char_I,
                                                            //Variable name of the data to display
        String strText_I

        )
    {
        if (
            strText_I == null
            )
            Tes3.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return strText_I + "(" + Tes3.strAnalizeAndFormatChar(char_I) + ")";
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatChar(          //Prepara un caracter para su despliege con información
                                                            //      adicional si el caracter es extraño.
                                                            //Ejemplos:
                                                            //1 'c'.
                                                            //2 '©'<0x00A9>.
                                                            //3 '^'<0x0009, \t, Horizontal Tab>.
                                                            //1) No tiene nada extraño, solo se añaden las comillas.
                                                            //2) El caracter © no aparece en el teclado, incluyo su
                                                            //      su hexadecimal.
                                                            //3) El caracter es un Horizonal Tab, no es visible, se
                                                            //      sustituye por ^ (el caracter en
                                                            //      charSUBSTITUTE_NONVISIBLE), incluyo su hexadecimal y
                                                            //      su descripción.

                                                            //Caracter a analizar.
        char char_I
        )
    {
        //                                                  //Para formar lo que va a regresar, esto depende del tipo de
        //                                                  //      caracter.
        String strAnalizeAndFormatChar;
        if (
            //                                              //Es USEFUL_IN_TEXT
            Arrays.binarySearch(Tes3.arrcharUSEFUL_IN_TEXT, char_I) >= 0
            )
        {
            //                                              //Es visible, solo pone entre comillas
            strAnalizeAndFormatChar = "'" + char_I + "'";
        }
        else
        {
            //                                              //No es visible, lo substitutye.
            strAnalizeAndFormatChar = "'" + charSUBSTITUTE_NO_USEFUL_IN_TEXT + "'";
        }

        //                                                  //Añade info de diagnóstico.
        if (
            //                                              //Se solicita mostrar su hex
            (Arrays.binarySearch(Tes3.arrcharDO_NOT_SHOW_HEX, char_I) < 0) ||
                //                                              //De solicita mostar su descripción
                (Arrays.binarySearch(Tes3.arrt2charDESCRIPTION, char_I) >= 0)
            )
        {
            //                                              //Formatea tupla con información de diagnóstico
            //                                              //      (primera parte).
            strAnalizeAndFormatChar = strAnalizeAndFormatChar + "<" + "0x" + String.format("{0:X4}", (int)char_I);

            if (
                //                                          //De solicita mostar su descripción
                Arrays.binarySearch(Tes3.arrt2charDESCRIPTION, char_I) >= 0
                )
            {
                //                                          //Completa la tupla cuando tiene descripción.
                int intT2 = Arrays.binarySearch(Tes3.arrt2charDESCRIPTION, char_I);
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + ", " +
                    Tes3.arrt2charDESCRIPTION[intT2].strDESCRIPTION + ">";
            }
            else
            {
                //                                          //Completa la tupla cuando NO tiene descripción.
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + ">";
            }
        }

        return strAnalizeAndFormatChar;




        //TODO: Esto es de la version anterio (not EWTraining)

        /*
                                                            //Determino tipo de caracter.
        TestchartypeEnum testchartype = Tes3.testchartypeKeyboardOrEtc(char_I);

                                                            //Para formar lo que va a regresar, esto depende del tipo de
                                                            //      caracter.
        String strAnalizeAndFormatChar;
        if (
                                                            //Es visible.
            (testchartype == TestchartypeEnum.KEYBOARD) || (testchartype == TestchartypeEnum.VISIBLE_NONKEYBOARD)
            )
        {
                                                            //Es visible, solo pone entre comillas
            strAnalizeAndFormatChar = "'" + char_I + "'";
        }
        else
        {
                                                            //Procesa cuando no es visible.
            strAnalizeAndFormatChar = "'" + charSUBSTITUTE_NONVISIBLE + "'";
        }

                                                            //Añade info de diagnóstico cuando no es del KEYBOARD.
        if (
            testchartype != TestchartypeEnum.KEYBOARD
            )
        {
                                                            //Añade info de diagnóstico.

                                                            //Formatea la tupla cuando no es visible (primera parte).
            strAnalizeAndFormatChar = strAnalizeAndFormatChar + "<" + "0x" + String.format("%04X", (int)char_I);

            if (
                testchartype == TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION
                )
            {
                                                            //Completa la tupla cuando tiene descripción.
                int intDesctiption = Arrays.binarySearch(arrcharNONVISIBLE, char_I);
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + ", " +
                        arrstrDESCRIPTION_NONVISIBLE[intDesctiption] + ">";
            }
            else
            {
                                                            //Completa la tupla cuando NO tiene descripción.
                strAnalizeAndFormatChar = strAnalizeAndFormatChar + ">";
            }
        }

        return strAnalizeAndFormatChar;
        */
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //                                                      //TODO: Not used in the version of Test that is trying to be
    //                                                      //      implemented (EWTraining version). It's going to be
    //                                                      //      kept commented just in case it's necessary in the
    //                                                      //      future.
    /*
    private static TestchartypeEnum testchartypeKeyboardOrEtc(
                                                            //Revisa un caracter para determinar su tipo.

                                                            //Caracter que debrerá ser revisado.
        char charARevise_I
        )
    {
        TestchartypeEnum testchartypeKeyboardOrEtc;

        /*CASE* /
        if (
                                                            //Es caracter del teclado.
            Arrays.binarySearch(Tes3.arrcharKEYBOARD, charARevise_I) >= 0
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.KEYBOARD;
        }
        else if (
            Arrays.binarySearch(Tes3.arrcharNONVISIBLE, charARevise_I) >= 0
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITH_DESCRIPTION;
        }
        else if (
            Tes3.boolIsNonVisibleWithoutDescription(charARevise_I)
            )
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.NONVISIBLE_WITHOUT_DESCRIPTION;
        }
        else
        {
            testchartypeKeyboardOrEtc = TestchartypeEnum.VISIBLE_NONKEYBOARD;
        }
        /*END-CASE* /

        return testchartypeKeyboardOrEtc;
    }
    */

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //                                                      //TODO: Not used in the version of Test that is trying to be
    //                                                      //      implemented (EWTraining version). It's going to be
    //                                                      //      kept commented just in case it's necessary in the
    //                                                      //      future.
    /*
    private static boolean boolIsNonVisibleWithoutDescription(
                                                            //Verifica si un caracter es no visible sin descripción.
                                                            //bool, true si es no visible sin descripción.

                                                            //Caracter que se desea verificar.
        char charAVerificar_I
    )
    {
                                                            //Extrae el número del caracter.
        int intChar = (int)charAVerificar_I;

                                                            //Busca el rango donde pudiera estar incluido.
        int intI = 0;
        /*UNTIL-DO* /
        while (!(
                                                            //Ya no hay rangos.
            (intI >= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) ||
                                                            //El caracter a verificar puede estar en este rango.
            (intChar <= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][ 1])
        ))
        {
            intI = intI + 1;
        }

        return (
                                                            //Esta posicionado en un rango.
            (intI < Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION.length) &&
                                                            //El caracter a verificar esta incluido en este rango.
            (intChar >= Tes3.arr2intRANGE_CHAR_NONVISIBLE_WITHOUT_DESCRIPTION[intI][0])
        );
    }
    */

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    /*TASK Tes3.strTo Set of Methods for Java Objects*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(                             //Prepare for SHORT display.
                                                            //The strategy is:
                                                            //1. 2 strTo methods (this and next with obj_I parameter)
                                                            //      will handle all types except generic containing
                                                            //      bclass, btuple, enum or sysexcep.
                                                            //2. 2 strTo methods with 3 paramenters will handle 1
                                                            //      argument generic containing bclass, btuple, enum or
                                                            //      excep.
                                                            //3. 2 strTo methods with 4 paramenters will handle
                                                            //      dicbclass, dicbtuple and dicenun.
                                                            //4. 2 strTo methods with 4 paramenters will handle
                                                            //      kvpbclass, kvpbtuple and kvpenun.
                                                            //5a. Each one of the pair of strTo methods call a
                                                            //      strToSupportXxxxx private method (4 methods) to
                                                            //      handle most checks needed, process null values and
                                                            //      call strToSharedYyyyy private methods to generate
                                                            //      the information requested.
                                                            //5b. "primitives" are not easy to process (they require an
                                                            //      specific method for each one), to solve this
                                                            //      problem, "primitives" will be boxed using Oint,
                                                            //      Olong, ... boxing clases, this will be done in the
                                                            //      strToSupportAnyType method.

                                                            //str, info to display

                                                            //Read strToSupportAnyType method for paramenters
                                                            //      description
        Object obj_I,
        StrtoEnum testoptionSHORT_I
    )
    {
        if (
            testoptionSHORT_I != StrtoEnum.SHORT
            )
            Tes3.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");

        return Tes3.strToSupportAnyType(obj_I, testoptionSHORT_I, null);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static String strTo(                             //Prepare for FULL display.

                                                            //str, info to display

                                                            //Read strToSupportAnyType method for paramenters
                                                            //      description
        Object obj_I,
        String strText_I
        )
    {
        if (
            strText_I == null
            )
            Tes3.subAbort(Tes3.strTo(strText_I, "strText_I") + " should have a value");

        return Tes3.strToSupportAnyType(obj_I, StrtoEnum.FULL, strText_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportAnyType(              //Continue preparation for display.

                                                            //str, info to display

                                                            //Any standard type except generic types containing bclass,
                                                            //      btuple or enum types (those require a transformation
                                                            //      before calling strTo method with 3 or 4 paramenters)
        Object obj_I,
                                                            //SHORT or FULL display
        StrtoEnum testoptionOption_I,
                                                            //Variable name of the object.
        String strText_I
        )
    {
        String strToSupportAnyType;
        if (
            obj_I == null
            )
        {
            if (
                testoptionOption_I == StrtoEnum.SHORT
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

            //TODO creo que esto se puede quitar
                                                            //Abort if not a valid type
            //Tes3.subVerifyAnyType(obj_I);

            //TODO forget about the boxing for a while. But check it out cuz objMain is sent to all the methods
                                                            //Do the boxing.
            //Object objMain;
            //Object objKey;
            //Tes3.subfunConvertAndBox(out objMain, out objKey, obj_I);

                                                            //Call required strToSharedYyyyy
            Class classObj = obj_I.getClass();
            /*CASE*/
            if (
                classObj.isArray()
                )
            {
                                                            //Is arrobj[], arrobj[,] or arrobj[, ,].
                                                            //All contents are boxed primitives, simple and
                                                            //      system types, bclass, btuple, enum or sysexcep

                int intRank = Tools.intArrRank(classObj);
                if(
                    Tools.boolIsPrimitiveStandardArray(classObj)
                    )
                {
                                                            //Treat the array as primitive
                    strToSupportAnyType = Tes3.strToPrimitiveArrays(obj_I, testoptionOption_I, strText_I);
                }
                else
                {
                                                            //Treat the array as obj array
                    /*CASE*/
                    if (
                        intRank == 1
                        )
                    {
                        //TODO Cambiar el primer argumento a objMain cuando sea posible
                                                            //Is arrobj[], call with 3 paramenters
                        strToSupportAnyType = Tes3.strFormatArrOrOneArgumentGeneric(
                                (Object[])obj_I, testoptionOption_I, strText_I, obj_I);
                    }
                    //TODO implementar arreglos bi y tridimensionales
//                else if (
//                    intRank == 2
//                    )
//                {
//                                                            //Is arrobj[,]
//                    strToSupportAnyType = Tes3.strFormatArr2Main((Object[,])objMain,
//                        testoptionOption_I, strText_I, obj_I);
//                }
//                else
//                {
//                                                            //Is arrobj[, ,]
//                    strToSupportAnyType = Tes3.strFormatArr3Main((Object[, ,])objMain,
//                        testoptionOption_I, strText_I, obj_I);
//                }
//                /*END-CASE*/
                    else {
                        strToSupportAnyType = "Bidimensional or Tridimensional Non-Primitive Array";
                    }
                }

            }
            else if (
                Tools.boolIsGenericType(classObj)
                )
            {
                                                            //Is 1 or 2 arguments.
                                                            //All contents are boxed primitives, simple and
                                                            //      system types

                if (
                                                            //Is List<Object>, ...
                    java.util.List.class.isAssignableFrom(classObj)
                    )
                {
                    //TODO implementar el objMain en lugar de obj_I
                                                            //lstobj, ... were converted to arrobj
                    strToSupportAnyType = Tes3.strFormatArrOrOneArgumentGeneric(
                            (Object[])obj_I, testoptionOption_I, strText_I, obj_I);
                }
                else
                {
                    //TODO implementar Dic y KVP
//                                                            //Is Dictionary<String, Object> or
//                                                            //      KeyValuePair<String,_Object>
//
//                    if (
//                        java.util.Dictionary.class.isAssignableFrom(classObj)
//                        )
//                    {
//                                                            //dicobj was converted to arrstr and arrobj
//                        strToSupportAnyType = Tes3.strFormatDicMain(
//                                (Object[])objMain, (String[])objKey, testoptionOption_I, strText_I,
//                                obj_I);
//                    }
//                    else
//                    {
//                                                            //kvpobj was converted to str and obj
//                        strToSupportAnyType = Tes3.strFormatKvpMain(objMain, (String)objKey, testoptionOption_I,
//                                strText_I, obj_I);
//                    }
                    strToSupportAnyType = "Dictionary or KeyValue Pair";
                }
            }
            else
            {
                //TODO Implementar que en vez de obj_I, sea objMain
                                                            //Is single type
                //strToSupportAnyType = Tes3.strFormatSingle(objMain, testoptionOption_I, strText_I);
                strToSupportAnyType = Tes3.strFormatSingle(obj_I, testoptionOption_I, strText_I);
            }
            /*END-CASE*/
        }

        return strToSupportAnyType;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    /*TASK Tes3.strTo Set of Methods for Singletons*/
    //------------------------------------------------------------------------------------------------------------------
    private static String strFormatSingle(                  //Format for display.

                                                            //str, formated info

                                                            //Any single type (no arrays or generic types)
        Object obj_I,
                                                            //FULL or SHORT display.
        StrtoEnum testoptionOption_I,
                                                            //Variable name of the single object.
        String strText_I
    )
    {
        Class classObj = obj_I.getClass();
        if (
            classObj.isArray() ||
            Tools.boolIsGenericType(classObj)
            )
            Tes3.subAbort(Tes3.strTo(classObj, "obj_I.GetType") +
                " only single types can be processed in this method");

        String strFormatSingle;
        if (
            testoptionOption_I == StrtoEnum.SHORT
            )
        {
            strFormatSingle = Tes3.strFormatSingleShort(obj_I);
        }
        else
        {
            strFormatSingle = Tes3.strFormatSingleFull(obj_I, strText_I);
        }

        return strFormatSingle;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatSingleShort(             //Format for display.

                                                            //str, formated info

                                                            //Read strFormatSingle method for paramenters description
        Object obj_I
    )
    {
        /*
        String strFormatSingleShort;
        if (
            (BclassBaseClassAbstract.class.isAssignableFrom(obj_I.getClass())) ||
            (BtupleBaseTupleAbstract.class.isAssignableFrom(obj_I.getClass()))
            )
        {
                                                            //Only id.
            strFormatSingleShort = Tes3.strGetObjId(obj_I);
        }
        else
        {
                                                            //Any other type shows a single object
            strFormatSingleShort = Tes3.strAnalizeAndFormatCheckNulls(obj_I, StrtoEnum.SHORT);
        }

        return strFormatSingleShort;
        */

        return Tes3.strAnalizeAndFormatCheckNulls(obj_I, StrtoEnum.SHORT);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatSingleFull(              //Format for display.

                                                            //str, formated info

                                                            //Read strFormatSingle method for paramenters description
        Object obj_I,
        String strText_I
    )
    {
                                                            //To display a bclass first time requires a block
        Ostring ostrFormatSingleFull = new Ostring();
        if (
                                                            //Bclass processed for first time.
            BclassBaseClassAbstract.class.isAssignableFrom(obj_I.getClass()) &&
            !Tes3.lstobjPreviouslyProcessed.contains(obj_I)
            )
        {
                                                            //A first time bclass should be display inside a block
            Ostring ostrNL = new Ostring();
            Ostring ostrLabel = new Ostring();
                                                            //The objId will be display before bclass, is not
                                                            //      in the block headings
            Tes3.subBlockStart(ostrNL, ostrLabel, ostrFormatSingleFull, strText_I, "");

            ostrFormatSingleFull.v = ostrFormatSingleFull.v + ostrNL.v +
                    Tes3.strAnalizeAndFormatCheckNulls((BclassBaseClassAbstract)obj_I, StrtoEnum.FULL);

            Tes3.subBlockEnd(ostrNL, ostrFormatSingleFull, ostrLabel.v);
        }
            else
        {
                                                            //No blocking requires, any single type
            ostrFormatSingleFull.v = strText_I + "(" +
                    Tes3.strAnalizeAndFormatCheckNulls(obj_I, StrtoEnum.FULL) +")";
        }

        return ostrFormatSingleFull.v;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tes3.Blocking Support blocking in the display Objects Info*/
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

                                                            //Si hay mas de 28 niveles, se les pone el úlltimo.
    private final static String strLETTERS_FOR_LEVEL = "?ABCDEFGHIJKLMNOPQRSTUVWXYZ*";

                                                            //Si hay mas de 25 niveles, se usa el último valor.
    private static int[] arrintLevelSpaces = {
        0, 4, 8, 12, 16, 20, 24, 27, 30, 33, 36, 39, 42, 44, 46, 48, 50, 52, 54, 55, 56, 57, 58, 59, 60
    };


    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

                                                            //Todas las clases no estaticas incluyen el método strTo
                                                            //      para mostrar el contenido de dicha clase, algunos de
                                                            //      estos métodos requieren block START-END para mostrar
                                                            //      el contenido de sus objetos cuando estos contienen
                                                            //      colecciones.

                                                            //Cada block START-END debe estar en un nivel superior a su
                                                            //      base, se incrementa al iniciar el block y se
                                                            //      decrementa al cerrarlo.
    private static int intLevel;

                                                            //Esta variable se usa para en cada block START-END
                                                            //      asignarle un número único (para esto, al tomar el
                                                            //      valor se incrementa en 1).
    private static int intStartEnd;

                                                            //Cada nivel, del 1 en adelane, tiene asociada una letra (A,
                                                            //      B, ...).
                                                            //Tambien, a cada nivel se le asocia una indentación al
                                                            //      inicio de cada línea (esto es una cantidad de
                                                            //      espacios).

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsToBlockFormat(
        //                                                  //Método de apoyo llamado en constructor estático.
        //                                                  //Inicia Nivel y StartEnd necesarios para indentar el log.
        )
    {
        Tes3.intLevel = 0;
        Tes3.intStartEnd = 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void subBlockStart(                      //Genera los parámetros requerido para subToBlockFormat.
                                                            //Solo se usa este método cuando block START-END.

                                                            //NL + caracteres indentación.
        Ostring strNL_O,
                                                            //Label for block START_??? y END_???. (this is ???).
        Ostring strLabel_O,
                                                            //String to start block information
        Ostring strTo_O,
                                                            //Text to describe the object
        String strText_I,
                                                            //Object Id, if this block is por a bclass should be ""
        String strObjId_I
    )
    {
        strNL_O.v = Tes3.strNL();

                                                            //Asigna el siguiente nivel (lo regresa al cerrar block).
        Tes3.intLevel = Tes3.intLevel + 1;

                                                            //Asigna una secuencia única.
        Tes3.intStartEnd = Tes3.intStartEnd + 1;

                                                            //Determina la etiqueta que corresponde al block.
        char charLettersStartEnd;
        if (
                                                            //El nivel excede las letras.
            intLevel >= strLETTERS_FOR_LEVEL.length()
            )
        {
                                                            //Cuando no alcance ni la "Z" usa "*".
            charLettersStartEnd = '*';
        }
        else
        {
            charLettersStartEnd = strLETTERS_FOR_LEVEL.charAt(intLevel);
        }

                                                            //Asigna la etiqueta StartEnd.
        strLabel_O.v = Character.toString(charLettersStartEnd) + intStartEnd;

                                                            //Append Start of block.
                                                            //If we are in level 1, is is the beginig of a Test (new
                                                            //      log or previously was a WriteLine), the NewLine
                                                            //      should not be include.
        String strNlForStart;
        if (
            intLevel == 1
            )
        {
                                                            //Remove NewLine Mark
            strNlForStart = strNL_O.v.substring(System.lineSeparator().length());
        }
        else
        {
            strNlForStart = strNL_O.v;
        }
        strTo_O.v = strNlForStart + "##########>>>>>START_" + strLabel_O.v;
        strTo_O.v = strTo_O.v + strNL_O.v + strText_I + "(" + strObjId_I;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void subBlockEnd(                        //Termina el block StartEnd (regresa el nivel).
                                                            //Solo se usa este método cuando block START-END.

                                                            //NL + caracteres indentación.
        Ostring strNL_IO,
                                                            //String to append information
        Ostring strTo_IO,
        String strLabel_I
    )
    {
                                                            //End of Block
        strTo_IO.v = strTo_IO.v + ")" + strNL_IO.v + "##########<<<<<END_" + strLabel_I;
                                                            //Regresa el nivel.
        Tes3.intLevel = Tes3.intLevel - 1;

        strNL_IO.v = strNL();
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strNL(                            //NL + caracteres indentación.
    )
    {
                                                            //Determina el NL+indentación que corresponde al block.
        if (
            Tes3.intLevel < 0
            )
            Tes3.subAbort(Tes3.strTo(Tes3.intLevel, "Tes3.intNivel") + " should be 0 or positive");

                                                            //Determina la cantidad de espacios para la indentación.
        int intSpaces;
        if (
                                                            //El nivel excede el arreglo.
            Tes3.intLevel >= Tes3.arrintLevelSpaces.length
            )
        {
                                                            //Cuando no alcance usa el último valor.
            intSpaces = Tes3.arrintLevelSpaces[Tes3.arrintLevelSpaces.length - 1];
        }
        else
        {
            intSpaces = Tes3.arrintLevelSpaces[intLevel];
        }

                                                            //Return NL with spacing required
        return System.lineSeparator() + Tools.strPadRight("", intSpaces);
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/
    //==================================================================================================================

    //==================================================================================================================
    /*TASK Tes3.ObjId set of methods to compute object id*/

                                                            //Implementación de apoyos apagar y pender la inclución del
                                                            //      HashCode en los ObjId (se substituye por ?).
                                                            //Esto es necesario para poder hacer pruebas dónde su log,
                                                            //      al repetirse la prueba, ES IDENTICO, esto será muy
                                                            //      útil en:
                                                            //Conversión del EW.Training Fase 0, 1 y 2 a otras
                                                            //      instancias de Object Oriented (Conversión de C# a
                                                            //      Java, Objective-C, Swift y otras en el futuro).
                                                            //Desarrollo de QEnabler, el User Aceptance Test del
                                                            //      QEnabler deberá ser convertirse a si mismo, la
                                                            //      nulificación de HashCode permitirá connfirmar que
                                                            //      en TODAS LAS PRUEBAS los resultados son identicos
                                                            //      pudiendo hacer una comparación automática.
                                                            //Se deberá confirmar que es ídentico el resultado de:
                                                            //a) Código C# desarrollado.
                                                            //b) Código C# generado por QEnabler.
                                                            //c) Código Java generado por QEnabler.
                                                            //d) Código Objective-C generado por QEnabler.
                                                            //e) Código Swift generado por QEnabler.
                                                            //Igualmente se deberá tomar el código Java, Objective-C y
                                                            //      Swift y confirmar que la generación de código a
                                                            //      partir de ellas en las 4 instancias producen
                                                            //      resultados identicos.

    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    private static final String strHASH_CODE_NULL = "?";


    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

    //                                                      //Indicador de se incluir el HashCode en los ObjId y otra
    //                                                      //      información que es útil pero que sin embargo impide
    //                                                      //      que los logs sean comparables en forma automática.
    private static boolean boolComparableLog;

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsGetObjId(        //Intialize para indicar ComparableLog.
    )
    {
        Tes3.boolComparableLog = false;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //TODO: Delete these methods (subSetHashCode/reset)
    //------------------------------------------------------------------------------------------------------------------
    public static void subSetHashCode(                      //Marca que desea incluir el HashCode en los ObjIn.
    )
    {
//        Tes3.boolIsHashCodeOn = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subResetHashCode(                    //Marca que desea NO incluir el HashCode en los ObjIn.
    )
    {
//        Tes3.boolIsHashCodeOn = false;
    }
    //------------------------------------------------------------------------------------------------------------------
    public static String strGetObjId(                       //Generate an object id.

                                                            //str, prefixSize:HashCode.
                                                            //prefix, data type prefix (int, arrint, lststr, etc.).
                                                            //Size, [l], [l,m], [l,m,n] or "".

        Object obj_I
        )
    {
        if (
            obj_I == null
            )
            Tes3.subAbort(Tes3.strTo(obj_I, "obj_I") + " should have a value");

        if (
            !Tes3.boolIsStandard(obj_I, false)
            )
            Tes3.subAbort(Tes3.strTo(obj_I.getClass(), "obj_I.GetType") + " is nonstandard type");

                                                            //El HashCode puede ser nulificado (cambiado a ?)
        String strHashCode;
        if (
            Tes3.boolComparableLog
            )
        {
            //                                              //Deja solo ?.
            strHashCode = Tes3.strHASH_CODE_NULL;
        }
        else
        {
            strHashCode = Integer.toString(obj_I.hashCode());
        }

        return Tes3.strPrefix(obj_I) + Tes3.strCollectionSize(obj_I) + ":" + strHashCode;
    }
    //------------------------------------------------------------------------------------------------------------------
    /*SHARE METHODS*/
    /*TASK Tes3.BoolIsStandard*/
    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolIsStandard(                  //Evaluate if object has a standard type.

                                                            //bool, is valid.

                                                            //object whose type will be tested to be standard.
        Object obj_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            obj_I == null
            )
            Tes3.subAbort(Tes3.strTo(obj_I, "obj_I") + " can not be null");

                                                            //A class may be received sometimes instead of the object
                                                            //      itself; in those cases, classObj = obj_I.
        Class classObj;
        if (
            obj_I instanceof Class
            )
        {
            classObj = (Class)obj_I;
        }
        else
        {
            classObj = obj_I.getClass();
        }

        boolean boolIsStandard;
        /*CASE*/
        if (
            classObj.isArray()
            )
        {
            boolIsStandard = Tes3.boolIsStandardArray(classObj, boolAbort_I);
        }
        else if (
            Tools.boolIsGenericType(classObj)
            )
        {
            boolIsStandard = Tes3.boolIsStandardGeneric(obj_I, boolAbort_I);
        }
        else
        {
            boolIsStandard = Tes3.boolIsStandardSingle(classObj, boolAbort_I);
        }
        /*END-CASE*/

        return boolIsStandard;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardSingle(           //Evaluate if type is standard single type.

                                                           //bool, is valid.

       Class class_I,
                                                           //true, abort if is not valid.
       boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardSingle;
        /*CASE*/
        if (
            Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardPrimitive(class_I, boolAbort_I);
        }
        else if (
            Arrays.binarySearch(Tes3.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardSystem(class_I, boolAbort_I);
        }
        else if (
            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardBclass(class_I, boolAbort_I);
        }
        else if (
            BtupleBaseTupleAbstract.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardBtuple(class_I, boolAbort_I);
        }
        else if (
            Enum.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardEnum(class_I, boolAbort_I);
        }
        else if (
            Exception.class.isAssignableFrom(class_I)
            )
        {
            boolIsStandardSingle = Tes3.boolIsStandardException(class_I, boolAbort_I);
        }
        else
        {
            boolIsStandardSingle = false;

            if (
                boolAbort_I && !boolIsStandardSingle
                )
                Tes3.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                    Tes3.strTo(class_I, "type_I") + " is not an standard primitive type");
        }
        /*END-CASE*/

        return boolIsStandardSingle;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardPrimitive(         //Evaluate if type is standard primitive type.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardPrimitive = (
                                                            //Is a primitive included in Towa Standard.
            (Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, class_I.getSimpleName()) >= 0)
        );

        if (
            boolAbort_I && !boolIsStandardPrimitive
            )
            Tes3.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "arrstrPRIMITIVE_TYPE") + ", " +
                Tes3.strTo(class_I, "type_I") + " is not an standard primitive type");

        return boolIsStandardPrimitive;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardSystem(            //Evaluate if type is standard system type.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        boolean boolX;
        boolX = Modifier.isAbstract(class_I.getModifiers());


        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardSimpleOrSystem = (
            Arrays.binarySearch(Tes3.arrstrSYSTEM_TYPE, class_I.getSimpleName()) >= 0
        );

        if (
            boolAbort_I && !boolIsStandardSimpleOrSystem
            )
            Tes3.subAbort(Tes3.strTo(Tes3.arrstrSYSTEM_TYPE, "Tes3.arrstrSYSTEM_TYPE") + ", " +
                Tes3.strTo(class_I, "type_I") + " is not an standard system type");

        return boolIsStandardSimpleOrSystem;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardBclass(            //Evaluate if type is standard Bclass.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardBclass = (
            BclassBaseClassAbstract.class.isAssignableFrom(class_I)
        );

        if (
            boolAbort_I && !boolIsStandardBclass
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " is not an standard bclass type");

        if (
                                                            //Is bclass (or subclass of)
            boolIsStandardBclass
            )
        {
                                                            //It could be abstract or concrete class

            if (
                Modifier.isAbstract(class_I.getModifiers())
                )
            {
                boolIsStandardBclass = (
                                                            //The name has the form: Prefix.....Abstract
                    class_I.getSimpleName().endsWith("Abstract") &&
                    (class_I.getSimpleName().length()> "Abstract".length()) &&
                    Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
                );

                if (
                    boolAbort_I && !boolIsStandardBclass
                    )
                    Tes3.subAbort(Tes3.strTo(class_I, "type_I") +
                        " an standard abstract bclass type should have the form 'Prefix....Abstract'");
            }
            else
            {
                                                            //It is concrete class

                String strNameLower = class_I.getSimpleName().toLowerCase();
                boolIsStandardBclass = (
                                                            //The name has de form: Prefix.... and do not end with
                                                            //      Abstract, Tuple, Enum or Interface
                    !(strNameLower.endsWith("abstract") || strNameLower.endsWith("enum") ||
                    strNameLower.endsWith("tuple") || strNameLower.endsWith("interface")) &&
                    Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
                );

                if (
                    boolAbort_I && boolIsStandardBclass
                    )
                    Tes3.subAbort(Tes3.strTo(class_I, "type_I") +
                        " an standard concrete bclass type should have the form 'Prefix....' and" +
                        " can not ends with Abstract, Tuple, Enum or Interface (upper or lower letters)");
            }
        }

        return boolIsStandardBclass;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardBtuple(            //Evaluate if type is standard Btuple.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardBtuple = (
            (BtupleBaseTupleAbstract.class.isAssignableFrom(class_I)) &&
                                                            //The name has the form: TNprefix...Tuple (at least 3 char
                                                            //      before "Tuple".
            class_I.getSimpleName().endsWith("Tuple") && (class_I.getSimpleName().length() >= ("Tuple".length() + 3)) &&
            (class_I.getSimpleName().charAt(0) == 'T') && (class_I.getSimpleName().charAt(1) >= '1') &&
            (class_I.getSimpleName().charAt(1) <= '9') && Tools.boolIsLetterLower(class_I.getSimpleName().charAt(2))
        );

        if (
            boolAbort_I && !boolIsStandardBtuple
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") +
                " is not an standard tuple type, also should have the form 'TNprefix...Tuple'");

        return boolIsStandardBtuple;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardEnum(              //Evaluate if type is standard enum type.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardEnum = (
            Enum.class.isAssignableFrom(class_I) &&
                                                            //Has the form Prefix...Enum (at least 1 char before
                                                            //      "Enum").
            class_I.getSimpleName().endsWith("Enum") && (class_I.getSimpleName().length() > "Enum".length()) &&
            Tools.boolIsLetterUpper(class_I.getSimpleName().charAt(0))
        );

        if (
            boolAbort_I && !boolIsStandardEnum
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") +
                " is not an standard Enum type, also should have the form 'Prefix...Enum'");

        return boolIsStandardEnum;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardException(         //Evaluate if type is standard Exception type.
                                                            //There is no much to check.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardException = (
            Exception.class.isAssignableFrom(class_I)
        );

        if (
            boolAbort_I && !boolIsStandardException
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " is not an standard Exception type");

        return boolIsStandardException;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardArray(             //Evaluate if type is standard array.

                                                            //bool, is valid.

        Class class_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            class_I == null
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " can not be null");

        boolean boolIsStandardArray = (
            class_I.isArray() &&
                                                            //Is obj[], obj[,] or obj[, ,]
            (Tools.intArrRank(class_I) <= 3)
        );

        if (
            boolAbort_I && !boolIsStandardArray
            )
            Tes3.subAbort(Tes3.strTo(class_I, "type_I") + " is not an standard array type");

        if (
            boolIsStandardArray
            )
        {
                                                            //The "element" of an array should be standard type, but
                                                            //      not array or generic

            Class classElement = class_I.getComponentType();
            boolIsStandardArray = (!(
                classElement.isArray() ||
                Tools.boolIsGenericType(classElement) ||
                !Tes3.boolIsStandard(classElement, false)
            ));

            if (
                boolAbort_I && !boolIsStandardArray
                )
                Tes3.subAbort(Tes3.strTo(class_I, "type_I") + ", " +
                    Tes3.strTo(classElement, "type_I.GetElementType") +
                    " the element of standard array type should be standard type, but not array or generic");
        }

        return boolIsStandardArray;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static boolean boolIsStandardGeneric(           //Evaluate if type is standard generic type.

                                                            //boolean, is valid.

        Object obj_I,
                                                            //true, abort if is not valid.
        boolean boolAbort_I
    )
    {
        if (
            obj_I == null
            )
            Tes3.subAbort(Tes3.strTo(obj_I, "obj_I") + " can not be null");

        Class classObj = obj_I.getClass();
        boolean boolIsStandardGeneric = (
            Tools.boolIsGenericType(classObj) &&
            (Arrays.binarySearch(Tes3.arrstrGENERIC_TYPE, classObj.getSimpleName()) >= 0)
        );

        if (
            boolAbort_I && !boolIsStandardGeneric
            )
            Tes3.subAbort(Tes3.strTo(classObj, "type_I") + " is not an standard generic Class");

        if (
            boolIsStandardGeneric
            )
        {
            if (
                Tools.boolGenericArgumentsPossible(obj_I)
                )
            {
                Class[] arrClassArgument = Tools.arrclassGetGenericArguments(obj_I);

                if (
                                                            //Generic arguments were obtained.
                    arrClassArgument.length > 0
                    )
                {
                                                            //The "main argument" of an generic should be standard type,
                                                            //      but not array or generic
                    Class classMainArgument = arrClassArgument[arrClassArgument.length - 1];

                    boolIsStandardGeneric = (!(
                        classMainArgument.isArray() ||
                        Tools.boolIsGenericType(classMainArgument) ||
                                                            //The method boolIsStandard usually receives the object and
                                                            //      then takes out the class, because the object itself
                                                            //      is needed for the method boolIsStandardGeneric (this
                                                            //      method), so normally the object must be passed to
                                                            //      boolIsStandard. However, in this case we know that
                                                            //      classMainArgument will not need to go to the
                                                            //      generic branch (boolIsStandardGeneric) on the
                                                            //      boolIsStandard method because it was previously
                                                            //      tested with Tools.boolIsGenericType(classMainArg..).
                        !Tes3.boolIsStandard(classMainArgument, false)
                    ));

                    if (
                        boolAbort_I && !boolIsStandardGeneric
                        )
                        Tes3.subAbort(Tes3.strTo(obj_I, "type_I") + ", " +
                            Tes3.strTo(classMainArgument, "typeMainArgument") +
                            " the main argument of standard generic type should be standard type," +
                            " but not array or generic");

                                                            //It could be a 2 arguments generic (Dictionary or
                                                            //      KeyValuePair).
                    if (
                        boolIsStandardGeneric &&
                        ((classObj.getSimpleName().equals(Tes3.strGENERIC_DICTIONARY_TYPE)) ||
                        (classObj.getSimpleName().equals(Tes3.strGENERIC_KEYVALUEPAIR_TYPE)))
                        )
                    {
                                                            //It should be dictionary or KeyValuePair, the first
                                                            //      argument should be String.
                        boolIsStandardGeneric = (
                            arrClassArgument[0] == String.class
                        );

                        if (
                            boolAbort_I && !boolIsStandardGeneric
                            )
                            Tes3.subAbort(Tes3.strTo(obj_I, "type_I") + ", " +
                                Tes3.strTo(arrClassArgument[0], "arrtypeArgument[0]") +
                                " the first argument of standard 2 arguments generic type should be String");
                    }
                }
            }
        }

        return boolIsStandardGeneric;
    }
    /*END-TASK*/


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strPrefix(                        //Generate the object prefix corresponding to type.
                                                            //Class Name has the structure:
                                                            //1. AaaaaBbbbbCcccc (a could be a digit).
                                                            //2. AaaaaBbbbbCcccc[], [,] or [,,] (array)
                                                            //3. Dictionary`2[String,other FullName], KeyValuePair.
                                                            //4. List`1[ns.FullName], Queue or Stack.
                                                            //AaaaaBbbbbCcccc could be:
                                                            //a. Bclass, Btuple or standard Enumerator.
                                                            //b. Primitive or system type (included in
                                                            //      arrstrPRIMITIVE_TYPE or arrstrSYSTEM_TYPE).

                                                            //str.
                                                            //1. prefix if is single type (prefix form PRIMITIVE or
                                                            //      SYSTEM, or aaaaa taken from class name if is a
                                                            //      Bclass, Btuple o Enum the name structure is
                                                            //      "AaaaaBbbbbbCcccc, 'a' could be a digit.
                                                            //2. arrprefix, arr1prefix or arr2prefix where "prefix" is
                                                            //      the prefix corresponding to element type.
                                                            //3. xxxarg, where "xxx" is prefix form GENERIC and "arg"
                                                            //      is the prefix corresponding to argument type (first
                                                            //      or second argument).
                                                            //(see type_I definition).

                                                            //1. Single type (not a array or generic type), Ex. str,
                                                            //      syspath, cod, codcb, sepoodt, ... .
                                                            //2. Array type ([], [,] or [, ,]), Ex. arrstr, arr2int,
                                                            //      arrarrstr, arrdicstr, ... .
                                                            //3. Generic type (1 or 2 arguments). Ex. dicstr, kvpint,
                                                            //      lsttok, queuecod, ... .

                                                            //Originally, a Class (Type) was received, but it was
                                                            //      changed in order to be able to get the arguments of
                                                            //      objects that are collections (it couldn't be done
                                                            //      with just the class).
        Object obj_I
    )
    {
                                                            //The class of the incoming object.
        Class classObj;
        if (
            obj_I instanceof Class
            )
        {
            classObj = (Class)obj_I;
        }
        else
        {
            classObj = obj_I.getClass();
        }

        String strPrefix;
        /*CASE*/
        if (
            classObj.isArray()
            )
        {
            strPrefix = "arr";


            int intRank = Tools.intArrRank(classObj);
            if (
                intRank > 1
                )
            {
                                                            //arr2 or arr3
                strPrefix = strPrefix + intRank;
            }

                                                            //arr?elem
            strPrefix = strPrefix + Tes3.strPrefix(Tes3.classOfArray(classObj));
        }
        else if (
            Tools.boolIsGenericType(classObj)
            )
        {
                                                            //dic, kvp, lst, queue or stack
            strPrefix = Tes3.arrstrGENERIC_PREFIX[
                Arrays.binarySearch(Tes3.arrstrGENERIC_TYPE, classObj.getSimpleName())];

            if (
                                                            //The generic arguments of the collection can be obtained.
                Tools.boolGenericArgumentsPossible(obj_I)
                )
            {
                Class[] arrClassArgument = Tools.arrclassGetGenericArguments(obj_I);

                if (
                                                            //Generic arguments were obtained.
                    arrClassArgument.length > 0
                    )
                {
                    strPrefix = strPrefix + Tes3.strPrefix(arrClassArgument[arrClassArgument.length - 1]);
                }
                else
                {
                    strPrefix = strPrefix + "EMPTY_COLLECTION";
                }
            }
            else
            {
                strPrefix = strPrefix + "EMPTY_COLLECTION";
            }
        }
        else
        {
                                                            //Single form (not an array or generic)
            int intPrimitive = Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, classObj.getSimpleName());
            if (
                                                            //Is standard primitive type
                intPrimitive >= 0
                )
            {
                strPrefix = Tes3.arrstrPRIMITIVE_PREFIX[intPrimitive];
            }
            else
            {
                int intSystem = Arrays.binarySearch(Tes3.arrstrSYSTEM_TYPE, classObj.getSimpleName());
                if (
                                                            //Is standard system type
                    intSystem >= 0
                    )
                {
                    strPrefix = Tes3.arrstrSYSTEM_PREFIX[intSystem];
                }
                else
                {
                    int intBoxing = Arrays.binarySearch(Tes3.arrstrBOXING_TYPE, classObj.getSimpleName());
                    if (
                                                            //Is boxing class.
                        intBoxing >= 0
                        )
                    {
                        strPrefix = Tes3.arrstrBOXING_PREFIX[intBoxing];
                    }
                    else
                    {
                                                            //Should be user defined type (bclass, btuple, enum or
                                                            //      excep)
                        if (!(
                            BclassBaseClassAbstract.class.isAssignableFrom(classObj) ||
                            BtupleBaseTupleAbstract.class.isAssignableFrom(classObj) ||
                            Enum.class.isAssignableFrom(classObj) ||
                            Exception.class.isAssignableFrom(classObj)
                        ))
                            Tes3.subAbort(Tes3.strTo(classObj, "classObj") +
                                    " SOMETHING IS WRONG!!! at this point it should be bclass, btuple, enum or sysexcep");


                                                            //Is a user define type (bclass, btuple, enum or sysexcep).
                                                            //Search for B in AaaaaBbbbbCcccc.
                                                            //Start after first character (after 'A')
                        int intI = 1;
                        /*WHILE-DO*/
                        while (
                            (intI < classObj.getSimpleName().length()) &&
                                                            //Between a-z or 0-9
                            Tools.boolIsDigitOrLetterLower(classObj.getSimpleName().charAt(intI))
                            )
                        {
                            intI = intI + 1;
                        }

                                                            //Subtract class prefix (aaaaa).
                                                            //substring in C# was originally (1, int -1), however, in
                                                            //      Java, the parameter is different than in C#. In C#
                                                            //      the parameters are ss(beginIndex, length), while in
                                                            //      Java the parameters are
                                                            //      ss(beginIndex, exclusiveEndIndex). Because of this,
                                                            //      to achieve the desired result, we must add the
                                                            //      begindIndex to the endIndex, in this case = 1).
                        strPrefix = Character.toString(classObj.getSimpleName().charAt(0)).toLowerCase() +
                            classObj.getSimpleName().substring(1, intI - 1 + 1);
                    }
                }
            }
        }
        return strPrefix;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strCollectionSize(
        Object obj_I
    )
    {
        Class classCollection = obj_I.getClass();

        String strCollectionSize;
        if (
            classCollection.isArray()
            )
        {
            if (!(
                                                            //The array is standard according to the multidimension
                                                            //      rules (Only arrays in form [], [,], [,,].
                Tes3.boolMultiDimensionArrayIsStandard(obj_I)
            ))
            {
                Tes3.subAbort(Tes3.strTo(obj_I, "obj_I") + " is not a standard multidimensional array. It's not in" +
                        " format [],[,] or [,,].");
            }
            int intRank = Tools.intArrRank(obj_I.getClass());

            /*CASE*/
            if (
                intRank == 1
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strArrSize(obj_I);
            }
            else if (
                intRank == 2
                )
            {
                                                            //Generate [l,m].
                strCollectionSize = Tes3.strArr2Size(obj_I);
            }
            else
            {
                                                            //Rank 3
                                                            //Generate [l,m,n].
                strCollectionSize = Tes3.strArr3Size(obj_I);
            }
            /*END-CASE*/
        }
        else if (
            Tools.boolIsGenericType(classCollection)
            )
        {
            /*CASE*/
            if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_DICTIONARY_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strDicSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_KEYVALUEPAIR_TYPE)
                )
            {
                                                            //This is not a collection.
                strCollectionSize = "";
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_LIST_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strLstSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_QUEUE_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strQueueSize(obj_I);
            }
            else if (
                classCollection.getSimpleName().equals(Tes3.strGENERIC_STACK_TYPE)
                )
            {
                                                            //Generate [l].
                strCollectionSize = Tes3.strStackSize(obj_I);
            }
            else
            {
                if (
                    true
                    )
                    Tes3.subAbort(Tes3.strTo(obj_I.getClass(), "obj_I.getClass()") +
                        " SOMETHING IS WRONG!!! is nonstandard generic collection");

                strCollectionSize = null;

            }
            /*END-CASE*/

        }
        else
        {
                                                            //It is not a collection
            strCollectionSize = "";
        }
        return strCollectionSize;
    }
    //------------------------------------------------------------------------------------------------------------------

    private static String strArrSize(                       //Get [l].

                                                            //str, [l].

        Object obj_I
    )
    {
        int intLength;
        /*CASE*/
        if (
            obj_I instanceof int[]
            )
        {
            intLength = ((int[])obj_I).length;
        }
        else if (
            obj_I instanceof long[]
            )
        {
            intLength = ((long[])obj_I).length;
        }
        else if (
            obj_I instanceof double[]
            )
        {
            intLength = ((double[])obj_I).length;
        }
        else if (
            obj_I instanceof boolean[]
            )
        {
            intLength = ((boolean[])obj_I).length;
        }
        else if (
            obj_I instanceof char[]
            )
        {
            intLength = ((char[])obj_I).length;
        }
        else if (
            obj_I instanceof LocalDateTime[]
            )
        {
            intLength = ((LocalDateTime[])obj_I).length;
        }
        else
        {
            Class classObj = obj_I.getClass();
            Class classElement = classObj.getComponentType();
            if (
                                                            //instanceof primitive type
                (Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, classElement.getSimpleName()) >= 0)
                )
                Tes3.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes3.arrstrPRIMITIVE_TYPE") + ", " +
                    Tes3.strTo(classObj, "obj_I.getClass()") +
                    " SOMETHING is WRONG!!! a branch in previous case is missing");

            intLength = ((Object[])obj_I).length;
        }
        /*END-CASE*/

        return "[" + intLength + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strArr2Size(                      //Get [l,m].

                                                            //str, [l,m].

        Object obj_I
    )
    {
        int intLength0;
        int intLength1;

        /*CASE*/
        if (
            obj_I instanceof int[][]
            )
        {
            int[][] arr2int = (int[][])obj_I;
            intLength0 = arr2int.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2int[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof long[][]
            )
        {
            long[][] arr2long = (long[][])obj_I;
            intLength0 = arr2long.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2long[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof double[][]
            )
        {
            double[][] arr2num = (double[][])obj_I;
            intLength0 = arr2num.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2num[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof boolean[][]
            )
        {
            boolean[][] arr2bool = (boolean[][])obj_I;
            intLength0 = arr2bool.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2bool[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof char[][]
            )
        {
            char[][] arr2char = (char[][])obj_I;
            intLength0 = arr2char.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2char[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else if (
            obj_I instanceof LocalDateTime[][]
            )
        {
            LocalDateTime[][] arr2ts = (LocalDateTime[][])obj_I;
            intLength0 = arr2ts.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2ts[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        else
        {
            Class typeObj = obj_I.getClass();
            Class typeElement = typeObj.getComponentType();
            if (
                                                            //instanceof primitive type
                (Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, typeElement.getSimpleName()) >= 0)
                )
                Tes3.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes3.arrstrPRIMITIVE_TYPE") + ", " +
                    Tes3.strTo(typeObj, "obj_I.GetType") +
                    " SOMETHING instanceof WRONG!!! a branch in previous case instanceof missing");


            Object[][] arr2obj = (Object[][])obj_I;
            intLength0 = arr2obj.length;
            if (
                intLength0 != 0
                )
            {
                intLength1 = arr2obj[0].length;
            }
            else
            {
                intLength1 = 0;
            }
        }
        /*END-CASE*/

        return "[" + intLength0 + "," + intLength1 + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strArr3Size(                      //Get [l,m,n].

                                                            //str, [l,m,n].
                                                            Object obj_I
    )
    {
        int intLength0;
        int intLength1;
        int intLength2;
        /*CASE*/
        if (
                obj_I instanceof int[][][]
                )
        {
            int[][][] arr3int = (int[][][])obj_I;
            intLength0 = arr3int.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3int[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3int[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof long[][][]
                )
        {
            long[][][] arr3long = (long[][][])obj_I;
            intLength0 = arr3long.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3long[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3long[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof double[][][]
                )
        {
            double[][][] arr3num = (double[][][])obj_I;
            intLength0 = arr3num.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3num[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3num[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof boolean[][][]
                )
        {
            boolean[][][] arr3bool = (boolean[][][])obj_I;
            intLength0 = arr3bool.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3bool[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3bool[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof char[][][]
                )
        {
            char[][][] arr3char = (char[][][])obj_I;
            intLength0 = arr3char.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3char[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3char[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else if (
                obj_I instanceof LocalDateTime[][][]
                )
        {
            LocalDateTime[][][] arr3ts = (LocalDateTime[][][])obj_I;
            intLength0 = arr3ts.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3ts[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3ts[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        else
        {
            Class typeObj = obj_I.getClass();
            Class typeElement = typeObj.getComponentType();
            if (
                //instanceof primitive type
                    (Arrays.binarySearch(Tes3.arrstrPRIMITIVE_TYPE, typeElement.getSimpleName()) >= 0)
                    )
                Tes3.subAbort(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes3.arrstrPRIMITIVE_TYPE") + ", " +
                        Tes3.strTo(typeObj, "obj_I.GetType") +
                        " SOMETHING instanceof WRONG!!! a branch in previous case instanceof missing");


            Object[][][] arr3obj = (Object[][][])obj_I;
            intLength0 = arr3obj.length;
            if (
                    intLength0 != 0
                    )
            {
                intLength1 = arr3obj[0].length;
                if (
                        intLength1 != 0
                        )
                {
                    intLength2 = arr3obj[0][0].length;
                }
                else
                {
                    intLength2 = 0;
                }
            }
            else
            {
                intLength1 = 0;
                intLength2 = 0;
            }
        }
        /*END-CASE*/

        return "[" + intLength0 + "," + intLength1 + "," + intLength2 + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strLstSize(                       //Get [l].

                                                            //str, [l].

        Object obj_I
        )
    {
        //TODO: GetGenericArguments is commented in this and subsequent methods.
        String strCount;
        if (obj_I instanceof LinkedList)
        {
            strCount = Integer.toString(((LinkedList)obj_I).size());
        }
        else
        {
//            Class classObj = obj_I.getClass();
//            if (
//            Tools.boolIsGenericType(classObj)
//            )
//        {
//                                                            dic, kvp, lst, queue or stack
//            strPrefix = Tes3.arrstrGENERIC_PREFIX[
//                Arrays.binarySearch(Tes3.arrstrGENERIC_TYPE, classObj.getSimpleName())];
//
//            if (
//                                                            The generic arguments of the collection can be obtained.
//                Tools.boolGenericArgumentsPossible(obj_I)
//                )
//            {
//                Class[] arrClassArgument = Tools.arrclassGetGenericArguments(obj_I);
//
//                if (
//                                                            Generic arguments were obtained.
//                    arrClassArgument.length > 0
//                    )
//                {
//                    strPrefix = strPrefix + Tes3.strPrefix(arrClassArgument[arrClassArgument.length - 1]);
//                }
//                else
//                {
//                    strPrefix = strPrefix + "EMPTY_COLLECTION";
//                }
//            }
//            else
//            {
//                strPrefix = strPrefix + "EMPTY_COLLECTION";
//            }
//        }
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[0];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes3.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes3.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes3.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrSYSTEM_TYPE, "Tes3.arrstrSYSTEM_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

            //Can note get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strQueueSize(                     //Get [l].

                                                            //str, [l].
        Object obj_I
        )
    {
        String strCount;
        if (obj_I instanceof Queue)
        {
            strCount = Integer.toString(((Queue)obj_I).size());
        }
        else
        {
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[0];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes3.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes3.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes3.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrSYSTEM_TYPE, "Tes3.arrstrSYSTEM_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

            //Can note get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strStackSize(                     //Get [l].

                                                            //str, [l].

        Object obj_I
        )
    {
        String strCount;
        if (
            obj_I instanceof Stack
            )
        {
            strCount = Integer.toString(((Stack)obj_I).size());
        }
        else
        {
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[0];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes3.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes3.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes3.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrSYSTEM_TYPE, "Tes3.arrstrSYSTEM_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

            //Can note get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static String strDicSize(                       //Get [l].

                                                            //str, [l].

        Object obj_I
        )
    {
        String strCount;
        if (
            obj_I instanceof LinkedHashMap
            )
        {
            strCount = Integer.toString(((LinkedHashMap)obj_I).size());
        }
        else
        {
            //TODO: GetGenericArguments cannot be done.
//            Type typeObj = obj_I.GetType();
//            Type typeArgument = typeObj.GetGenericArguments()[1];
//            if (
//                                                            //Is primitive type
//                (Array.BinarySearch(Tes3.arrstrPRIMITIVE_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrPRIMITIVE_TYPE, "Tes3.arrstrPRIMITIVE_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");
//            if (
//                                                            //Is system type
//                (Array.BinarySearch(Tes3.arrstrSYSTEM_TYPE, typeArgument.Name) >= 0)
//                )
//                Tools.subWarning(Tes3.strTo(Tes3.arrstrSYSTEM_TYPE, "Tes3.arrstrSYSTEM_TYPE") + ", " +
//                    Tes3.strTo(typeObj, "obj_I.GetType") +
//                    " SOMETHING IS WRONG!!! a branch in previous case is missing, you may continue");

                                                            //Can not get count.
            strCount = "?";
        }
        return "[" + strCount + "]";
    }

    //------------------------------------------------------------------------------------------------------------------
    private static Class classOfArray(                      //Get the class of the elements of an array. For example,
                                                            //      get int from int[], String from String[][], etc.
                                                            // This method will work in similar way to C#'s
                                                            //      type.GetElementType()
                                                            //Java's method class.getComponentType() will be used,
                                                            //      however, it works differently than C#'s
                                                            //      type.GetElementType(), because in Java, due to the
                                                            //      fact that a multidimensional array is
                                                            //      technically, which means that the elementType of a
                                                            //      3D array is actually a 2D array. Examples:
                                                            //Note that int[].getComponentType returns class int;
                                                            //      int[][].getComponentType returns class int[];
                                                            //      int[][][].getComponentType return class int[][].

                                                            //The class of an array from which the element type should
                                                            //      be extracted.

        Class class_I
        )
    {
        Class classOfArray;
        if (
            class_I == null
            )
        {
            Tes3.subAbort(Tes3.strTo(class_I, "class_I") + " should not be null.");
            classOfArray = null;
        }
        if (!(
            class_I.isArray()
            ))
        {
            Tes3.subAbort(Tes3.strTo(class_I, "class_I") + " is not the class of an array.");
            classOfArray = null;
        }
        /*CASE*/
        if (
            int[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            int[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            int[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            long[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            long[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            long[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            boolean[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            boolean[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            boolean[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            char[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            char[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            char[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            double[].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType();
        }
        else if (
            double[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            double[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            Object[][][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType().getComponentType();
        }
        else if (
            Object[][].class.isAssignableFrom(class_I)
            )
        {
            classOfArray = class_I.getComponentType().getComponentType();
        }
        else if (
            Object[].class.isAssignableFrom(class_I)
        )
        {
            classOfArray = class_I.getComponentType();
        }
        else
        {
                Tes3.subAbort(Tes3.strTo(class_I, "class_I") +
                    " is an array but is not of rank 1,2 or 3. It is not [], [,], or [,,]");
            classOfArray = null;
        }
        /*END-CASE*/
        return classOfArray;
    }

    //==================================================================================================================
    /*TASK Tools.boolMultiDimensionArrayIsStandard. Check if multidimension array is standard according to its size*/
    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolMultiDimensionArrayIsStandard(
                                                            //Check if the incoming array is standard for a
                                                            //      multidimensional arr. This means that even though,
                                                            //      technically, in Java, multidimensional arrays are
                                                            //      arrays of arrays, standard  arrays must be 2D o 3D.
                                                            //      This means that, an array like this cannot exist:
                                                            //[
                                                            //  arr[0] = elem1, elem2, elem3
                                                            //  arr[1] = elem1, elem2
                                                            //  arr[2] = elem1
                                                            //]
                                                            //It will also be checked that the arrays inside the arrays
                                                            //       (inner arrays) are not nulls. For example an
                                                            //        invalid 3D array:
                                                            //[
                                                            //   arr3d[][][] = new arr[2][1][1]
                                                            //   arr3d[0] = new arr[1][1]
                                                            //   arr3d[1] = null
                                                            //]
                                                            //Instead, the arrays must be like this (2D):
                                                            //[
                                                            //   arr[0] = elem1, elem2, elem3
                                                            //   arr[1] = elem1, elem2, elem3
                                                            //   arr[2] = elem1, elem2, elem3
                                                            //]
                                                            //In context of C#, the permitted multidimensional arrays
                                                            //      are: arr[], arr[,], and arr[,,].

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

                                                            //Object to be analyzed. Array.
        Object obj_I
    )
    {
        boolean boolMultiDimensionArrayIsStandard;
        if (
            obj_I == null
            )
        {
            Tes3.subAbort(Tes3.strTo(obj_I, "obj_I") + " can not be null");
            boolMultiDimensionArrayIsStandard = false;
        }


        if (!(
            obj_I.getClass().isArray()
        ))
        {
            Tes3.subAbort(Tes3.strTo(obj_I.getClass(), "obj_I.getClass()") + " is not the class of an array.");
            boolMultiDimensionArrayIsStandard = false;
        }
        else
        {
            /*CASE*/
            if (
                obj_I instanceof int[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof int[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr2DIsStandard((int[][]) obj_I);
            }
            else if (
                obj_I instanceof int[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr3DIsStandard((int[][][]) obj_I);
            }
            else if (
                obj_I instanceof long[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof long[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr2DIsStandard((long[][]) obj_I);
            }
            else if (
                obj_I instanceof long[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr3DIsStandard((long[][][]) obj_I);
            }
            else if (
                obj_I instanceof boolean[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof boolean[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr2DIsStandard((boolean[][]) obj_I);
            }
            else if (
                obj_I instanceof boolean[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr3DIsStandard((boolean[][][]) obj_I);
            }
            else if (
                obj_I instanceof char[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof char[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr2DIsStandard((char[][]) obj_I);
            }
            else if (
                obj_I instanceof char[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr3DIsStandard((char[][][]) obj_I);
            }
            else if (
                obj_I instanceof double[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else if (
                obj_I instanceof double[][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr2DIsStandard((double[][]) obj_I);
            }
            else if (
                obj_I instanceof double[][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr3DIsStandard((double[][][]) obj_I);
            }
            else if (
                obj_I instanceof Object [][][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr3DIsStandard((Object[][][])obj_I);
            }
            else if (
                obj_I instanceof Object [][]
                )
            {
                boolMultiDimensionArrayIsStandard = Tes3.boolArr2DIsStandard((Object[][])obj_I);
            }
            else if (
                obj_I instanceof Object[]
                )
            {
                boolMultiDimensionArrayIsStandard = true;
            }
            else
            {
                Tes3.subAbort(Tes3.strTo(obj_I, "obj_I") +
                    " is an array but is not of rank 1,2 or 3. It is not [], [,], or [,,]");
                boolMultiDimensionArrayIsStandard = false;
            }
            /*END-CASE*/
        }

        return boolMultiDimensionArrayIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of int's is standard.
        int [][] arr2int
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2int.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2int[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2int[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2int.length) ||
                (arr2int[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2int[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2int.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of long's is standard.
        long [][] arr2long
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2long.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2long[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2long[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2long.length) ||
                (arr2long[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2long[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2long.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of boolean's is standard.
        boolean [][] arr2boolean
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2boolean.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2boolean[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2boolean[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2boolean.length) ||
                (arr2boolean[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2boolean[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2boolean.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of char's is standard.
        char [][] arr2char
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2char.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2char[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2char[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2char.length) ||
                (arr2char[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2char[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2char.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of double's is standard.
        double [][] arr2num
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2num.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2num[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2num[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2num.length) ||
                (arr2num[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2num[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2num.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr2DIsStandard(             //Find if an arr2D of Object's is standard.
        Object [][] arr2Object
        )
    {
        boolean boolArr2DIsStandard;
        int intLength;
        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr2Object.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second dimension is also of size 0.
                                                            //      This will be a coding standard. So a bidimensional
                                                            //      array with its first dimension of size 0, shall have
                                                            //      its second dimension of size 0 as well ([0,0]).
            boolArr2DIsStandard = true;
        }
        else if (
                                                            //The array inside the array (inner array) is null.
            arr2Object[0] == null
            )
        {
            boolArr2DIsStandard = false;
        }
        else
        {
                                                            //The length of each array should be the same as the first.
            intLength = arr2Object[0].length;
            int intI = 1;
            /*UNTIL-DO*/
            while (!(
                (intI >= arr2Object.length) ||
                (arr2Object[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr2Object[intI].length != intLength)
            ))
            {
                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m])
            boolArr2DIsStandard = intI >= arr2Object.length;
        }
        /*END-CASE*/

        return boolArr2DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of int's is standard.
        int [][][] arr3int
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3int.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3int[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3int[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3int[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3int[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3int[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3int.length) ||
                (arr3int[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3int[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3int[intI].length) ||
                    (arr3int[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3int[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3int[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3int.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of long's is standard.
        long [][][] arr3long
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3long.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3long[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3long[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3long[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3long[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3long[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3long.length) ||
                (arr3long[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3long[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3long[intI].length) ||
                    (arr3long[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3long[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3long[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3long.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of boolean's is standard.
        boolean [][][] arr3bool
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3bool.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3bool[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3bool[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3bool[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3bool[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3bool[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3bool.length) ||
                (arr3bool[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3bool[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3bool[intI].length) ||
                    (arr3bool[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3bool[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3bool[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3bool.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of char's is standard.
        char [][][] arr3char
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3char.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3char[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3char[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3char[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3char[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3char[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3char.length) ||
                (arr3char[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3char[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3char[intI].length) ||
                    (arr3char[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3char[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3char[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3char.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of double's is standard.
        double [][][] arr3num
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3num.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3num[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3num[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3num[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3num[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3num[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3num.length) ||
                (arr3num[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3num[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3num[intI].length) ||
                    (arr3num[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3num[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3num[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3num.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean boolArr3DIsStandard(             //Find if an arr3D of Object's is standard.
        Object [][][] arr3obj
        )
    {
        boolean boolArr3DIsStandard = true;
        int intLength2D;
        int intLength3D;

        /*CASE*/
        if (
                                                            //The array is of length 0.
            arr3obj.length == 0
            )
        {
                                                            //The array is of length 0, so no element inside the array,
                                                            //      arr[0] f.e., can be checked. Nevertheless, it will
                                                            //      be treated as a standard array, and it will be
                                                            //      assumed that the second and third dimensions are
                                                            //      also of size 0. This will be a coding standard. So a
                                                            //      3d array with its first dimension of size 0, shall
                                                            //      have its second and third dimensions of size 0 as
                                                            //      well ([0,0,0]).
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The 2d array is null, meaning that it's being trated as an
                                                            //      array of arrays instead of a multidimensional arr.
            arr3obj[0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else if (
                                                            //2d is of size 0.
            arr3obj[0].length == 0
            )
        {
                                                            //It will be trated as standard multidimensional array. In
                                                            //      other methods it wil be assumed that the 3d is also
                                                            //      of size 0.
            boolArr3DIsStandard = true;
        }
        else if (
                                                            //The arrays inside the array (inner arrays) are null or
                                                            //      have nothing.
            arr3obj[0][0] == null
            )
        {
            boolArr3DIsStandard = false;
        }
        else
        {
                                                            //The length of the 2D array.
            intLength2D = arr3obj[0].length;
                                                            //The length of the 3D array.
            intLength3D = arr3obj[0][0].length;
            int intI = 0;
            int intJ = 0;
            /*UNTIL-DO*/
            while (!(
                                                            //It has been determined that it's not standard.
                !boolArr3DIsStandard ||
                (intI >= arr3obj.length) ||
                (arr3obj[intI] == null) ||
                                                            //The inner array is of different size than the others.
                (arr3obj[intI].length != intLength2D)
            ))
            {
                intJ = 0;
                /*UNTIL-DO*/
                while (!(
                    (intJ >= arr3obj[intI].length) ||
                    (arr3obj[intI][intJ] == null) ||
                                                            //The inner array is of different size than the others.
                    (arr3obj[intI][intJ].length != intLength3D)
                ))
                {
                    intJ = intJ + 1;
                }
                boolArr3DIsStandard = intJ >= arr3obj[intI].length;

                intI = intI + 1;
            }

                                                            //All inner arrays were of the same size ([l,m,n])
            boolArr3DIsStandard = intI >= arr3obj.length;
        }
        /*END-CASE*/

        return boolArr3DIsStandard;
    }
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Tes3.strAnalizeAndFormat set of private methods to format a single object*/
    //------------------------------------------------------------------------------------------------------------------
    private static String strAnalizeAndFormatCheckNulls(
                                                            //Produces an object in string format.
                                                            //Before calling strAnalizeAndFormatXxxx checks for null

                                                            //str, object in string format, could be null.

                                                            //Object to format
        Object obj_I,
                                                            //SHORT or FULL
        //TODO ESTO HAY QUE CAMBIARLO A STRTOENUM, EN VEZ DE StrtoEnum
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
            Class classObj = obj_I.getClass();
            /*CASE*/
            if (
                BboxBaseBoxingAbstract.class.isAssignableFrom(classObj)
                )
            {
                strAnalizeAndFormatCheckNulls = Tes3.strAnalizeAndFormatBbox((BboxBaseBoxingAbstract)obj_I);
            }
            else if (
                BclassBaseClassAbstract.class.isAssignableFrom(classObj)
                )
            {
                strAnalizeAndFormatCheckNulls =
                    Tes3.strAnalizeAndFormatBclass((BclassBaseClassAbstract)obj_I, strtoOption_I);
            }
            else if (
                BtupleBaseTupleAbstract.class.isAssignableFrom(classObj)
                )
            {
                strAnalizeAndFormatCheckNulls =
                    Tes3.strAnalizeAndFormatBtuple((BtupleBaseTupleAbstract)obj_I, strtoOption_I);
            }
            //TODO implementar las Benum
//            else if (
//                BenumBaseEnumAbstract.class.isAssignableFrom(classObj)
//                )
//            {
//                strAnalizeAndFormatCheckNulls =
//                    Tes3.strAnalizeAndFormatBenum((BenumBaseEnumAbstract)obj_I);
//            }
            else if (
                //TODO VER SI ESTO ESTA BIEN
                Enum.class.isAssignableFrom(classObj)
                )
            {
                strAnalizeAndFormatCheckNulls = Tes3.strAnalizeAndFormatEnum((Enum)obj_I);
            }
            else if (
                Exception.class.isAssignableFrom(classObj)
                )
            {
                strAnalizeAndFormatCheckNulls = Tes3.strAnalizeAndFormatSysexcep((Exception)obj_I);
            }
            else
            {
                //TODO Aqui no hay ningun casting asi que hay que checar el metodo dentro
                strAnalizeAndFormatCheckNulls = Tes3.strAnalizeAndFormatSystemType(obj_I, strtoOption_I);
            }
            /*END-CASE*/
        }

        return strAnalizeAndFormatCheckNulls;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatBbox(          //Produces an object in string format (boxed primitive)

                                                            //str, object in string format.

                                                            //bbox to format
        BboxBaseBoxingAbstract bbox_I
    )
    {
        String strAnalizeAndFormatBbox;
        Class classBbox = bbox_I.getClass();
        /*CASE*/
        if (
            //TODO Todos los Oint, Obool, etc... se les agrego la palabra Box al final. Cambiar los nombres.
            Oint.class.isAssignableFrom(classBbox)
            )
        {
            strAnalizeAndFormatBbox = Tes3.strAnalizeAndFormatInt(((Oint)bbox_I).v);
        }
        else if (
            Olong.class.isAssignableFrom(classBbox)
            )
        {
            strAnalizeAndFormatBbox = Tes3.strAnalizeAndFormatLong(((Olong)bbox_I).v);
        }
        else if (
            Onum.class.isAssignableFrom(classBbox)
            )
        {
            strAnalizeAndFormatBbox = Tes3.strAnalizeAndFormatNum(((Onum)bbox_I).v);
        }
        else if (
            Obool.class.isAssignableFrom(classBbox)
            )
        {
            strAnalizeAndFormatBbox = Tes3.strAnalizeAndFormatBool(((Obool)bbox_I).v);
        }
        else if (
            Ochar.class.isAssignableFrom(classBbox)
            )
        {
            strAnalizeAndFormatBbox = Tes3.strAnalizeAndFormatChar(((Ochar)bbox_I).v);
        }
        //TODO implementar la rama de timestamp
//        else if (
//            Ots.class.isAssignableFrom(classBbox)
//            )
//        {
//            strAnalizeAndFormatBbox = Tes3.strAnalizeAndFormatTs(((Ots)bbox_I).v);
//        }
        else
        {
            Tes3.subAbort(Tes3.strTo(classBbox, "bbox_I.GetType") +
                " SOMETHING IS WRONG!!!, method strAnalizeAndFormatXxxx to process this bbox type is missing");

            strAnalizeAndFormatBbox = null;
        }
        /*END-CASE*/

        return strAnalizeAndFormatBbox;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatBclass(        //Analize and format bclass (or subclass of bclass).
                                                            //A bclass object should be display only once per run.
                                                            //str, bclass formated to display.

                                                            //Bclass to be analized and format
        BclassBaseClassAbstract bclass_I,
                                                            //SHORT or FULL
        StrtoEnum testoptionOption_I
        )
    {
        String strAnalizeAndFormatBclass;

                                                            //Se verifica si es null o no
        if (
            bclass_I == null
            )
        {
            if (
                testoptionOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatBclass = "null";
            }
            else
            {
                //TODO Falta agregar el parámetro para cuando es FULL
                strAnalizeAndFormatBclass = "(null)";
            }
        }
        else {

            /*CASE*/
            if (
                                                            //Es un bclass DUMMY
                bclass_I.boolIsDummy()
                )
            {
                                                            //Include only objId + DUMMY
                strAnalizeAndFormatBclass = Tes3.strGetObjId(bclass_I) + "[DUMMY]";
            }
            else if (
                                                            //Was processed before
                Tes3.lstobjPreviouslyProcessed.contains(bclass_I)
                )
            {
                                                            //Include only objId
                strAnalizeAndFormatBclass = Tes3.strGetObjId(bclass_I) + "|look object up|";
            }
            else
            {
                                                            //Register as processed
                Tes3.lstobjPreviouslyProcessed.add(bclass_I);

                if (
                    testoptionOption_I == StrtoEnum.SHORT
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
        }

        return strAnalizeAndFormatBclass;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatBtuple(        //Analize and format btuple (or subclass of btuple).
                                                            //A btuple object should be display only once per run.
                                                            //str, btuple formated to display.

                                                            //Bclass to be analized and format
        BtupleBaseTupleAbstract btuple_I,
                                                            //SHORT or FULL
        StrtoEnum testoptionOption_I
        )
    {
        String strAnalizeAndFormatBtuple;
        if (
                                                            //Was processed before
            Tes3.lstobjPreviouslyProcessed.contains(btuple_I)
            )
        {
                                                            //Include only objId
            strAnalizeAndFormatBtuple = Tes3.strGetObjId(btuple_I) + "|look object up|";
        }
        else
        {
                                                            //Register as processed
            Tes3.lstobjPreviouslyProcessed.add(btuple_I);

            if (
                testoptionOption_I == StrtoEnum.SHORT
                )
            {
                strAnalizeAndFormatBtuple = btuple_I.strTo(StrtoEnum.SHORT);
            }
            else
            {
                strAnalizeAndFormatBtuple = btuple_I.strTo();
            }
        }

        return strAnalizeAndFormatBtuple;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatEnum(      //Analize and format enum (or subclass of enum).
                                                        // str, enum formated to display.

                                                        //Enum to be analized and format
        Enum enum_I
    )
    {
        return enum_I.name();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatSysexcep(      //Prepare a object to display.
                                                            //str, sysexcep_I prepared to display.

                                                            //Object to be analized and format
        Exception sysexcep_I
    )
    {
        return sysexcep_I.toString();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatSystemType(    //Produces an object in string format (system type)

                                                            //str, object in string format.

                                                            //Object to format
        Object obj_I,
                                                            //SHORT or FULL
        StrtoEnum testoptionOption_I
        )
    {
        String strAnalizeAndFormatSystemType;
        /*CASE*/
        if (
            obj_I instanceof String
            )
        {
            strAnalizeAndFormatSystemType = Tes3.strAnalizeAndFormatStr((String)obj_I);
        }
        //TODO FALTA IMPLEMENTAR TODO ESTE ROLLO
//        else if (
//            obj_I instanceof Type
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes3.strAnalizeAndFormatType((Type)obj_I, testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof DirectoryInfo
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes3.strAnalizeAndFormatSysdir((DirectoryInfo)obj_I,
//                testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof FileInfo
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes3.strAnalizeAndFormatSysfile((FileInfo)obj_I, testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof StreamReader
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes3.strAnalizeAndFormatSyssr((StreamReader)obj_I, testoptionOption_I);
//        }
//        else if (
//            obj_I instanceof StreamWriter
//            )
//        {
//            strAnalizeAndFormatSystemType = Tes3.strAnalizeAndFormatSyssw((StreamWriter)obj_I, testoptionOption_I);
//        }
        else
        {
            if (
                true
                )
//                Tes3.subAbort(Tes3.strTo(obj_I.GetType(), "obj_I.GetType") + " SOMETHING IS WRONG!!!," +
//                    " method strAnalizeAndFormatXxxx to process this system type is missing");

            strAnalizeAndFormatSystemType = null;
        }
        /*END-CASE*/

        return strAnalizeAndFormatSystemType;
    }

    //==================================================================================================================
    /*TASK Tes3.strTo(SHORT, 3 Arguments) strTo with only the needed parameters for Fase 0*/
    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(
        Object[] arrobj_I,
        StrtoEnum testoptionSHORT_I,
        Object objOneArgumentGeneric_I
        )
    {
        if (
            testoptionSHORT_I != StrtoEnum.SHORT
            )
            Tes3.subAbort(Tes3.strTo(testoptionSHORT_I, "testoptionSHORT_I") + " should be SHORT");


        return Tes3.strToSupportOneArgumentGeneric(arrobj_I, testoptionSHORT_I, null, objOneArgumentGeneric_I);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strToSupportOneArgumentGeneric(
        //
        //                                                  //Continue preparation for display.

        //                                                  //str, info to display

        //                                                  //arrstr, arrbclass, arrbtuple or arrenum.
        Object[] arrobj_I,
        //                                                  //SHORT or FULL display
        StrtoEnum testoptionOption_I,
        //                                                  //Variable name of the one argument generic.
        String strText_I,
        //                                                  //lstbclass, lstbtuple or lstenum, queuebclass, ...
        //                                                  //Main should contain str or the type (or subtype)
        //                                                  //      contained in one argument generic.
        Object objOneArgumentGeneric_I
        )
    {
        String strToSupportOneArgumentGeneric;
        if (
                objOneArgumentGeneric_I == null
                )
        {
            if (
                    testoptionOption_I == StrtoEnum.SHORT
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
            //                                              //TODO Validar este fusil y eta rama del if-else
            //                                              //Abort if both parameters are not consistent.
            //Tes3.subVerifyOneArgumentGeneric(arrobj_I, objOneArgumentGeneric_I);

            strToSupportOneArgumentGeneric = "";
            //Tes3.strFormatArrOrOneArgumentGeneric(arrobj_I, testoptionOption_I,
            //strText_I, objOneArgumentGeneric_I);
        }

        return strToSupportOneArgumentGeneric;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String strTo(
        BclassBaseClassAbstract[] arrbclass_I,
        StrtoEnum testoptionSHORT_I,
        LinkedList<BclassBaseClassAbstract> lstbclass_I
        )
    {
        return "BclassBaseClassAbstract[]-SHORT, LinkedList<BclassBaseClassAbstract>";
    }

    /*END-TASK*/

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strAnalizeAndFormatStr(           //Prepara un String para su despliege con información de
                                                            //      caracteres que no son del KEYBOARD.
                                                            //Ejemplos:
                                                            //1. "Esto es lo que se analizo"<25>.
                                                            //2. "©XYX"<4>{ <0, '©', 0x00A9> }.
                                                            //3. "^XYX"<4>{ <0, '^', 0x0001> }.
                                                            //4. "^XYX"<4>{ <0, '^', 0x0009, \t, Horizontal Tab> }.
                                                            //1) Todo es del KEYBOARD, solo se añaden las comillas y su
                                                            //      longitud.
                                                            //2) El primer caracter © no aparece en el KEYBOARD, incluyo
                                                            //      su hexadecimal.
                                                            //3) El primer caracter es NONVISIBLE_WITHOUT_DESCRIPTION,
                                                            //      se sustituye por _ (el caracter en
                                                            //      charSUBSTITUTE_NONVISIBLE) e incluyo su hexadecimal.
                                                            //4) El primer caracter es un Horizonal Tab, no es
                                                            //      visible, se sustituye por _ (el caracter en
                                                            //      charSUBSTITUTE_NONVISIBLE), incluyo su hexadecimal y
                                                            //      su descripción.
                                                            //Puede haber más de un caracter que no es del KEYBOARD, se
                                                            //      añade "{ <.....>, <.....>, ..., <......> }".
                                                            //Si no hay ningún caracter que no es del KEYBOARD, no se
                                                            //      añade nada en esta parte, esto es no se añade "{ }",
                                                            //      esto fue lo que sucedió en el ejemplo 1.
                                                            //str, String para despligue con diagnostico de caracteres
                                                            //      que no están en el KEYBOARD.

                                                            //String a analizar.
        String str_I
        )
    {
                                                            //Para formar lo que va a regresar.
        String strAnalizeAndFormatStr;
        if (
                                                            //No hay String.
            str_I == null
            )
        {
            strAnalizeAndFormatStr = "null";
        }
        else
        {
                                                            //Paso a arrchar para poder modificarlo.
            char[] arrcharToAnalize = str_I.toCharArray();

                                                            //Para conjunto de información de diagnóstico.
            LinkedList<String> lststrDiagnosticInfo = new LinkedList<String>();

                                                            //Reviso todos los caracteres.
            for (int intI = 0; intI < arrcharToAnalize.length; intI = intI + 1)
            {
                                                            //Paso un caracter a formato desplegable, el formato será:
                                                            //'c', KEYBOARD.
                                                            //'c'<0x1234>, VISIBLE_NONKEYBOARD.
                                                            //'^'<0x1234>, NONVISIBLE_WITHOUT_DESCRIPTION.
                                                            //'^'<0x1234, descripción>, NONVISIBLE_WITH_DESCRIPTION.
                String strCharAnalized = Tes3.strAnalizeAndFormatChar(arrcharToAnalize[intI]);

                                                            //Si tiene información de diagnóstico la proceso.
                if (
                                                            //Si tiene información de diagnóstico.
                    strCharAnalized.length() > 3
                    )
                {
                                                            //Cambio caracter, la pos. 1 tiene el caracter revisado.
                    arrcharToAnalize[intI] = strCharAnalized.charAt(1);

                                                            //Debo formar un String:
                                                            //<n, 'c', 0x1234>, VISIBLE_NONKEYBOARD.
                                                            //<n, '^', 0x1234>, NONVISIBLE_WITHOUT_DESCRIPTION.
                                                            //<n, '^', 0x1234, descripción>,
                                                            //      NONVISIBLE_WITH_DESCRIPTION.
                    String strDiagnosticInfo = "<" + intI + ", " + strCharAnalized.substring(0, 3) + ", " +
                        strCharAnalized.substring(4);

                                                            //Añade info a la lista.
                    lststrDiagnosticInfo.add(strDiagnosticInfo);
                }
            }

                                                            //Forma la longitud del String, solo de desea mostrar cuando
                                                            //      excede intLONG_STRING.
            String strLongString;
            if (
                str_I.length() > Tes3.intLONG_STRING
                )
            {
                strLongString = "<" + str_I.length() + ">";
            }
            else
            {
                strLongString = "";
            }

                                                            //Forma el String a desplegar.
            if (
                                                            //No tiene ningún caracter con información de diagnóstico.
                lststrDiagnosticInfo.size() == 0
                )
            {
                                                            //Formatea cuando NO tiene información de diagnóstico.
                strAnalizeAndFormatStr = "\"" + str_I + "\"" + strLongString;
            }
            else
            {
                                                            //Formatea cuando SI tiene información de diagnóstico.
                strAnalizeAndFormatStr = "\"" + new String(arrcharToAnalize) + "\"" + "<" + arrcharToAnalize.length +
                    ">" + "{ " + String.join(", ", lststrDiagnosticInfo.toArray(new String[0])) + " }";
            }
        }

        return strAnalizeAndFormatStr;
    }
    /*END-TASK*/

    /*TASK Tes3.strTo Set of Methods for Arrays*/
    //------------------------------------------------------------------------------------------------------------------

                                                                //PRIMITIVE ARRAYS
    strToSupportAnyType = Tes3.strToPrimitiveArrays(obj_I, testoptionOption_I, strText_I);
    private static String strToPrimitiveArrays(                 //Main method for treating arrays of primitive data

                                                                //Primitive Array
        Object objArray_I,
                                                                //Option of how it should be showed
        StrtoEnum testoptionOption_I,
                                                                //Name of the variable
        String strText_I
    )
    {
        String strPrimitiveArray;



        return strPrimitiveArray;
    }


    //==================================================================================================================
    /*TASK Tes2.strTo strTo(String[], strText) methods needed for Fase 0*/
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(
            //                                                  //Format for display, it could be:
            //                                                  //Set of Lines(Items) or One Line(Row).

            //                                                  //str, formated info.

            //                                                  //Read strFormatArrOrOneArgumentGeneric method for
            //                                                  //      paramenters description
            int[] arrint_I,
            String strText_I,
            String strObjId_I
    )
    {
        //                                                  //Need to look for long item
        boolean boolSetOfLinesItems = false;
        boolSetOfLinesItems = false;
        int intI = 0;
        /*UNTIL-DO*/
        while (!(
                boolSetOfLinesItems ||
                        (intI >= arrint_I.length)
        ))
        {
            String strItem = Tes2.strAnalizeAndFormatInt(arrint_I[intI]);
            boolSetOfLinesItems = (
                    strItem.length() > Tes2.intLONG_ITEM_ROW_MATRIX
            );

            intI = intI + 1;
        }

        Ostring strFormatArr = new Ostring();
        if (
                boolSetOfLinesItems
                )
        {
            Ostring strNL = new Ostring();
            Ostring strLabel = new Ostring();
            Tes2.subBlockStart(strNL, strLabel, strFormatArr, strText_I, strObjId_I);

            strFormatArr.v = strFormatArr.v + Tes2.strListItems(arrint_I, strNL.v);

            Tes2.subBlockEnd(strNL, strFormatArr, strLabel.v);
        }
        else
        {
            strFormatArr.v = strText_I + "(" + strObjId_I + Tes2.strLineRow(arrint_I) + ")";
        }

        return strFormatArr.v;
    }

    private static String strFormatArrOrOneArgumentGeneric(
                                                            //Format for display
                                                            //An arr or One Argument Generic object should be display
                                                            //      only once per run.

                                                            //str, formated info

                                                            //arr to format
        Object[] arrobj_I,
                                                            //SHORT or FULL display
        StrtoEnum strtoOption_I,
                                                            //Variable name of arr or one argument generic object
        String strText_I,
                                                            //this is needed to get objId.
        Object objOriginal_I
        )
    {
                                                            //Compute objId
        String strObjId = Tes3.strGetObjId(objOriginal_I);
        if (
            Tools.boolIsGenericType(objOriginal_I.getClass())
            )
        {
                                                            //Get size from arrobj
            strObjId = strObjId.replace("[?]", "[" + arrobj_I.length + "]");
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
                                                            //An Arr or One Argument Generic object should be display
                                                            //      only once per run.
            if (
                Tes3.lstobjPreviouslyProcessed.contains(objOriginal_I)
                )
            {
                strFormatArrOrOneArgumentGeneric = strText_I + "(" + strObjId + "|look object up|" + ")";
            }
            else
            {
                                                            //Register arr or one argument generic as processed
                Tes3.lstobjPreviouslyProcessed.add(objOriginal_I);

                strFormatArrOrOneArgumentGeneric = Tes3.strFormatArr(arrobj_I, strText_I, strObjId);
            }
        }

        return strFormatArrOrOneArgumentGeneric;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strFormatArr(                     //Format for display, it could be:
                                                            //Set of Lines(Items) or One Line(Row).

                                                            //str, formated info.

                                                            //Read strFormatArrOrOneArgumentGeneric method for
                                                            //      paramenters description
        Object[] arrobj_I,
        String strText_I,
        String strObjId_I
    )
    {
                                                            //Find if Set of Lines(Items) format is required.
                                                            //Need to look for long item

                                                            //Set of lines is always required for bclass[] and btuple[].
                                                            //(if not, it won't work properlly).

        boolean boolSetOfLinesItems;
        if (
            BclassBaseClassAbstract[].class.isAssignableFrom(arrobj_I.getClass()) ||
            BtupleBaseTupleAbstract[].class.isAssignableFrom(arrobj_I.getClass())
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
                (intI >= arrobj_I.length)
                ))
            {
                String strItem = Tes3.strAnalizeAndFormatCheckNulls(arrobj_I[intI], StrtoEnum.FULL);
                boolSetOfLinesItems = strItem.length() > Tes3.intLONG_ITEM_ROW_MATRIX;

                intI = intI + 1;
            }
        }

        Ostring ostrFormatArr = new Ostring();
        if (
            boolSetOfLinesItems
            )
        {
            Ostring ostrNL = new Ostring();
            Ostring ostrLabel = new Ostring();
            Tes3.subBlockStart(ostrNL, ostrLabel, ostrFormatArr, strText_I, strObjId_I);

            ostrFormatArr.v = ostrFormatArr.v + Tes3.strListItems(arrobj_I,ostrNL.v);

            Tes3.subBlockEnd(ostrNL, ostrFormatArr, ostrLabel.v);
        }
        else
        {
            ostrFormatArr.v = strText_I + "(" + strObjId_I + Tes3.strLineRow(arrobj_I) + ")";
        }

        return ostrFormatArr.v;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strListItems(                     //Format an array to a Set of Lines(Items) inside a block.
                                                            //Example:
                                                            //[
                                                            //{
                                                            //[0] item
                                                            //...
                                                            //[x] item
                                                            //}
                                                            //]

                                                            //str, set in block format

        Object[] arrobj_I,
        String strNL_I
    )
    {
                                                            //Chars required for longest index: "[x]"
        int intCharsInLongestIndex = ("[" + (arrobj_I.length - 1) + "]").length();

                                                            //Produces a Set of Lines(Items) ready to display.
        String[] arrstrIndexAndItem = new String[arrobj_I.length];
        for (int intI = 0; intI < arrobj_I.length; intI = intI + 1)
        {
            String strItem = Tes3.strAnalizeAndFormatCheckNulls(arrobj_I[intI], StrtoEnum.FULL);

                                                            //Format: NL [i]_ item
            arrstrIndexAndItem[intI] =
                strNL_I + Tools.strPadRight("[" + intI + "]", intCharsInLongestIndex) + " " + strItem;
        }


        return strNL_I + "{" + String.join("", arrstrIndexAndItem) + strNL_I + "}";
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String strLineRow(                       //Produces:
                                                            //{ item, ..., item }.

                                                            //str, arr in one line format.
        Object[] arrobj_I
    )
    {
                                                            //Convert arrobj to arrstr
        String[] arrstrItem = new String[arrobj_I.length];
        for (int intI = 0; intI < arrobj_I.length; intI = intI + 1)
        {
            arrstrItem[intI] = Tes3.strAnalizeAndFormatCheckNulls(arrobj_I[intI], StrtoEnum.FULL);
        }

                                                            //Format: { item, item, ..., item }
        return Tes3.strVectorFromSet(arrstrItem);
    }

    //--------------------------------------------------------------------------------------------------------------
    private static String strVectorFromSet(                 //Produces:
                                                            //{ stuff, ..., stuff }.
                                                            //Posibilities:
                                                            //Put a set of strItem in a vector (strRow).
                                                            //Put a set of strRow in a vector (strMatrix).
                                                            //Put a set of strMatrix in a vector (strCube).

                                                            //str, vector format.

                                                            //Stuff to be included in strVector.
        String[] arrstrStuff_I
    )
    {
        String strRowFormatBeforeAddingBrackets;
        if (
            arrstrStuff_I.length == 0
            )
        {
            strRowFormatBeforeAddingBrackets = " ";
        }
        else
        {
            strRowFormatBeforeAddingBrackets = " " + String.join(", ", arrstrStuff_I) + " ";
        }

        return "{" + strRowFormatBeforeAddingBrackets + "}";
    }

    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //==================================================================================================================
    /*TASK Test.Trace Support to implement a trace*/
    //------------------------------------------------------------------------------------------------------------------

    //                                                      //Implementación de apoyos para poder efectuar un Trece en
    //                                                      //      en una apliación.
    //                                                      //¿Cómo?.
    //                                                      //      puntos que se crea conveniente, añadir:
    //                                                      //Test.subTrace(true, strLabel, intNivel, String a trace);
    //                                                      //Imprimie el log que contendrá el trace y otra información
    //                                                      //      de la prueba
    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

    //                                                      //Log para mostrar información en un archivo.
    //                                                      //Este log se asigna al iniciar una prueba con el método
    //                                                      //      subInitializeLog().
    //                                                      //Se mostrará información en los siguientes casos:
    //                                                      //1. Al concluir una prueba, usando el método subLog().
    //                                                      //2. Al abortar, si sysswLog != null, usando el método
    //                                                      //3. Al enviar un Warning (similar a abortar).
    //                                                      //4. Al usar Trace (DEFINIR DESPUÉS).
    private static PrintWriter sysswTestLog;

    //                                                      //Cada Trace que se genere tendra un número único 1, 2, 3,
    //                                                      //      etc. (esto es, su secuencia).
    //                                                      //Antes de generar un nuevo trace se debe incrementar.
    private static int inTraceSequence;

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //TODO: Delete these methods
    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsSubLogAndTrace(
        //                                                  //Initialiaze trace state.
        )
    {
        //                                                  //Aún no hay log para trace definido.
        Tes3.sysswTestLog = null;

        //                                                  //Inicia la cuenta de Trace que se genera.
        Tes3.inTraceSequence = 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subInitializeLog(
        //                                                  //Inicializa el log para UNA prueba. (Al inicio de cada Test
        //                                                  //      se debe ejecutar este método).
        //                                                  //1. Genera syssw para el log y lo guarda.
        //                                                  //2. Escribe en log: Nombre Tester, ts y name del log.

        //                                                  //Nombre de Tester (quien esta a cargo de la prueba)
        String strNameTester_I,
        //                                                  //Path para los logs, debe ser un directorio
        SyspathPath syspathDirectoryForLogs_I,
        //                                                  //Clave de la prueba (Ej. Cod1, Cod4Com3Com4Com5Cod3Com2).
        //                                                  //Con esta clave se formará en nombre de archivo log de la
        //                                                  //      prueba añadiendole .test (Ej. Cod1.test,
        //                                                  //      Cod4Com3Com4Com5Cod3Com2.test).
        //                                                  //Si el path DirectoryForLogs\Test.test existe debe ser un
        //                                                  //      y se reescribe.
        String strTest_I,
        //                                                  //Indica si la prueba es para "comparación" automática (en
        //                                                  //      estos casos el ts y los hashcode se muestran como ?,
        //                                                  //      el tester name se muestra con "<Test for Automatic
        //                                                  //      Verification>")
        boolean boolIsTestForAutomaticVerification_I
        )
    {
        if (
            strNameTester_I == null
            )
            Tes3.subAbort(Tes3.strTo(strNameTester_I, "strNameTester_I") + " can not be null");
        if (
            strNameTester_I == ""
            )
            Tes3.subAbort(Tes3.strTo(strNameTester_I, "strNameTester_I") + " should have a value");
        if (
            syspathDirectoryForLogs_I == null
            )
            Tes3.subAbort(Tes3.strTo(syspathDirectoryForLogs_I, "syspathDirectoryForLogs_I") + " can not be null");
        if (
            !syspathDirectoryForLogs_I.boolIsValid()
            )
            Tes3.subAbort(Tes3.strTo(syspathDirectoryForLogs_I, "syspathDirectoryForLogs_I") + " is not valid");
        if (
            !syspathDirectoryForLogs_I.boolIsDirectory()
            )
            Tes3.subAbort(Tes3.strTo(syspathDirectoryForLogs_I, "syspathDirectoryForLogs_I") +
                " should be a directory");
        if (
            strTest_I == null
            )
            Tes3.subAbort(Tes3.strTo(strTest_I, "strFileForTestLog_I") + " can not be null");
        if (
            strTest_I == ""
            )
            Tes3.subAbort(Tes3.strTo(strTest_I, "strTest_I") + " should have a value");

        SyspathPath syspathFileForTestLog = syspathDirectoryForLogs_I.syspathAddName(strTest_I + ".test");

        if (
            !syspathFileForTestLog.boolIsValid()
            )
            Tes3.subAbort(Tes3.strTo(syspathFileForTestLog, "syspathFileForTestLog") + " is not valid");
        if (
            //                                              //Existe y NO es file
            syspathFileForTestLog.boolExists() && !syspathFileForTestLog.boolIsFile()
            )
            Tes3.subAbort(Tes3.strTo(syspathFileForTestLog, "syspathFileForTestLog") + " should be a file");

        //                                                  //Genera log
        File sysfileTestLog = Sys.sysfileNew(syspathFileForTestLog);
        Oobject<File> osysfileTestLog = new Oobject<File>(sysfileTestLog);
        if (
            syspathFileForTestLog.boolIsFile()
            )
        {
            Tes3.sysswTestLog = Sys.sysswNewRewriteTextFile(osysfileTestLog);
        }
        else
        {
            Tes3.sysswTestLog = Sys.sysswNewWriteTextFile(osysfileTestLog);
        }

        //                                                  //En ComparableLog, los HashCode serán ?, etc.
        Tes3.boolComparableLog = boolIsTestForAutomaticVerification_I;

        //                                                  //Escribe primera línea en log
        String strNameTester;
        String strTsNow;
        if (
            Tes3.boolComparableLog
            )
        {
            //                                              //En verificación automática se cancela el despliegue del Ts
            strNameTester = "<Tes3 for Automatic Verification>";
            strTsNow = "?";
        }
        else
        {
            strNameTester = strNameTester_I;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            strTsNow = LocalDateTime.now().format(formatter);
        }
        Tes3.subLog(strNameTester + ", Now(" + strTsNow + "), " + syspathFileForTestLog.strName());

        //                                                  //Cada Tes3 inicia la secuencia de los blocks An, Bn, ...
        Tes3.intStartEnd = 0;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static void subFinalizeLog(
        //                                                  //Cierra el log para UNA prueba. (Al concluir cada Test
        //                                                  //      se debe ejecutar este método).
        //                                                  //1. Dispose log.
        //                                                  //2. Lo asinga a null.
        )
    {
        //                                                  //Solo si esta en una prueba
        if (
            //                                              //Hay un log, estamos en prueba
            Tes3.sysswTestLog != null
            )
        {
            Tes3.sysswTestLog.close();
            Tes3.sysswTestLog = null;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static void subLog(
        //                                                  //Genera información en el log.

        String strInfoToLog_I
        )
    {
        //                                                  //Solo si esta en una prueba
        if (
            //                                              //Hay un log, estamos en prueba
            Tes3.sysswTestLog != null
            )
        {
            Oobject<PrintWriter> osysswTestLog = new Oobject<PrintWriter>(Tes3.sysswTestLog);
            Sys.subWriteLine(strInfoToLog_I, osysswTestLog);
            Tes3.sysswTestLog = osysswTestLog.v;
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static void subTrace(
        //                                                  //Genera un trace a writeline.
        //                                                  //true, se desea generar el trace.
        //                                                  //false, No se genera el trace.
        //                                                  //Se incluye este parámetro para sin tener que eliminar la
        //                                                  //      la ejecución del trace poder activarlo/desactivarlo.
        boolean boolIsTraceOn_I,
        //                                                  //Etiqueta para identificar el registro del trace en la
        //                                                  //      impresión. Cada instrucción trace que se agregue al
        //                                                  //      código debe tener una etiqueta distinta.
        String strLabel_I,
        //                                                  //Información a incluir en el trace, esta información se le
        //                                                  //      da forma similar a los strTo.
        String strInfoTrace_I
        )
    {
        if (
            Tes3.sysswTestLog == null
            )
            Tes3.subAbort(Tes3.strTo(Tes3.sysswTestLog, "Test.sysswLog") + " should be created and asigned");

        //                                                  //Solo se procesa el trace si esta en ON.
        if (
            boolIsTraceOn_I
            )
        {
            //                                              //Avanza una secuencia (esta es la secuencia única de este
            //                                              //      trace).
            inTraceSequence = inTraceSequence + 1;

            //                                              //Produce trace.
            Tes3.subLog(">>> " + inTraceSequence + " <<< " + strLabel_I);
            Tes3.subLog(strInfoTrace_I);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    /*END-TASK*/

    //==============================================================================================================
    /*TASK Tes3.Abort Support for testing subAbort*/

    //                                                      //Implementación de apoyos para poder efectuar pruebas de
    //                                                      //      subAbort y regitrar su información en un log.
    //                                                      //¿Cómo?.
    //                                                      //En el código "driver" para ejecutar la prueba (ej. en
    //                                                      //      Tes3 Sys01.cs), llamar al método:
    //                                                      //Tes3.subSetTestAbort(); o.
    //                                                      //Tes3.subResetTestAbort(); o.

    //------------------------------------------------------------------------------------------------------------------
    /*CONSTANTS*/

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC VARIABLES*/

    //                                                      //Indicador de se desea test.
    public static boolean boolIsTestAbortOn;

    //------------------------------------------------------------------------------------------------------------------
    /*STATIC CONSTRUCTOR SUPPORT METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    private static void subPrepareConstantsTestAbort(       //Intialize test state.
    )
    {
        Tes3.boolIsTestAbortOn = false;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*SHARED METHODS*/

    //------------------------------------------------------------------------------------------------------------------
    public static void subSetTestAbort(                     //Marca que desea test.
    )
    {
        Tes3.boolIsTestAbortOn = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subResetTestAbort(                   //Marca que desea concluir test.
    )
    {
        Tes3.boolIsTestAbortOn = false;
    }
    /*END-TASK*/

    //==================================================================================================================
}
/*END-TASK*/