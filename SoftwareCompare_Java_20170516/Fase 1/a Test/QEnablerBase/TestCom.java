package QEnablerBase;

import TowaInfrastructure.*;
import QEnablerObjectOriented.*;
import QEnablerCobol.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.*;

public final class TestCom {
                                                            //Empty private constructor so the class can't be
                                                            //      instantiated.
    private TestCom(){}

    //------------------------------------------------------------------------------------------------------------------
    final static String strNAME = "Eli Linares";
    //final static String strPATH = "./Testing/Java/<Data or Logs ?>/QEnablerBase/Com" + "\\";

    final static String strPATH = new File(new File(System.getProperty("user.dir")).getParent()).getParent()+
            "/QEnabler Test/<Data or Logs ?>/QEnablerBase/Com/";
    private static String strPATH_DATA = strPATH.replace("<Data or Logs ?>", "Data");
    private static String strPATH_LOGS = strPATH.replace("<Data or Logs ?>", "Logs Java");

    //------------------------------------------------------------------------------------------------------------------
    public static void subTestCom1() 
    {
        Oobject<PrintWriter> osysswLog = new Oobject<PrintWriter>();
        Tes2.subInitializeLog(strNAME, strPATH_LOGS + "Test Com1.log", osysswLog, true);

                                                        //Prueba COBOL
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("#################### TEST COBOL ##########", osysswLog);
        Tes2.subInitializeLstobjPreviouslyProcessed();
        ComcbCobol comcbDUMMY = ComcbCobol.comcbDUMMY_UNIQUE_Z;
        Sys.subWriteLine(Tes2.strTo(comcbDUMMY.arrcharREQUIRED_IN_PARUN(), "comcbDUMMY.arrcharREQUIRED_IN_PARUN"),
            osysswLog);
        Sys.subWriteLine(
            Tes2.strTo(comcbDUMMY.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT(),
                "comcbDUMMY.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT"), osysswLog);

                                                        //Prueba C#
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("#################### TEST C# ##########", osysswLog);
        Tes2.subInitializeLstobjPreviouslyProcessed();
        ComooObjectOriented comooDUMMY = ComooObjectOriented.comooDUMMY_UNIQUE_Z;
        Sys.subWriteLine(Tes2.strTo(comooDUMMY.arrcharREQUIRED_IN_PARUN(), "comooDUMMY.arrcharREQUIRED_IN_PARUN"),
            osysswLog);
        Sys.subWriteLine(
            Tes2.strTo(comooDUMMY.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT(),
                "comooDUMMY.arrt2alignmentCOM_EL_REQUIRES_ALIGNMENT"), osysswLog);

        //                                          //Resumen de objetos en todas las pruebas
        BclassBaseClassAbstract.subWriteSummary(osysswLog.v);

        osysswLog.v.close();
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void subTestCod4Com3Com4Com5Cod3Com2()
        {
            Oobject<PrintWriter> osysswLog = new Oobject<PrintWriter>();
            Tes2.subInitializeLog(strNAME, strPATH_LOGS + "Test Cod4Com3Com4Com5Cod3Com2.log", osysswLog, true);

                                                            //Prueba COBOL A1
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST COBOL A1 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            String strFileName = "Prog_cbA.cobol";
            SyspathPath syspathFile = new SyspathPath(strPATH_DATA + strFileName);
            Sys.subWriteLine(Tes2.strTo(syspathFile, TestoptionEnum.SHORT), osysswLog);
            CodcbCobol codcbProg = new CodcbCobol(syspathFile);
            /*Cod4*/
            Oint ointLine =  new Oint(1);
            Oint ointChar = new Oint(0);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            Oobject<CodCodeAbstract> ocodComCb = new Oobject<CodCodeAbstract>();
            codcbProg.subSubtractComAndAdvance(ocodComCb, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCb, "codComCb"), osysswLog);
            /**/
            /*Com3*/
            ComcbCobol comcb = new ComcbCobol((CodcbCobol)ocodComCb.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb, "comcb"), osysswLog);
            /**/
            /*

            /*Com4* /
            Sys.subWriteLine("########## RESULTADOS subfunBuscaPalabra", osysswLog);
            Oint ointInPar = new Oint();
            Oint ointInChar = new Oint();
            String strWord = "UGDTSMD";              //En parst
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "UGDTSMd";                     //No encuentra, tiene d en lugar de D
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "UGDTSM";                      //No encuentra, le falta D al final
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "GDTSMD";                      //No encuentra, le falta U al principio
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "DESCRIPCION";                 //No encuentra, le falta :
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "DESCRIPCION:";                //Al inicio de parun
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "DESCRIPCIÓN:";                //No la encuentra dado que tiene acento
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "algo";                        //En medio del parun
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "2009";                        //2009 no es palabra
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "2009-04-04";                  //En parst
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            strWord = "DIVISION";                    //Al final del parun
            comcb.subfunSearchWord(ointInPar, ointInChar, strWord);
            Sys.subWriteLine(Tes2.strTo(strWord, "strWord") + ", " + Tes2.strTo(ointInPar, "intInPar") + ", " +
                Tes2.strTo(ointInChar, "intInChar"), osysswLog);
            /** /
            /*Com5* /
            Sys.subWriteLine("########## RESULTADOS arrstrParunToStandard", osysswLog);
            for (int intI = 0; intI < comcb.arrpar().length; intI = intI + 1)
            {
                Sys.subWriteLine(
                    Tes2.strTo(comcb.arrpar()[intI].arrstrLineStandard(), "comcb.arrpar[" + intI + "].arrstrLinePar"),
                    osysswLog);
            }
            /** /
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.codStandard(), "comcb.codStandard"), osysswLog);
            /**/
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.arrboolOriginalOK(), "comcb.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.arrboolStandardOK(), "comcb.arrboolStandardOK"), osysswLog);
            /** /

                                                            //Prueba COBOL A2
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST COBOL A2 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            Sys.subWriteLine(Tes2.strTo(strFileName, "strFileName"), osysswLog);
            /*Cod4* /
            ointLine.v = 149;
            ointChar.v = 0;
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            codcbProg.subSubtractComAndAdvance(ocodComCb, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCb, "codComCb"), osysswLog);
            /** /
            /*Com3* /
            comcb = new ComcbCobol((CodcbCobol)ocodComCb.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb, "comcb"), osysswLog);
            /**/
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.codStandard(), "comcb.codStandard"), osysswLog);
            /** /
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.arrboolOriginalOK(), "comcb.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.arrboolStandardOK(), "comcb.arrboolStandardOK"), osysswLog);
            /** /

                                                            //Prueba COBOL A3
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST COBOL A3 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            Sys.subWriteLine(Tes2.strTo(strFileName, "strFileName"), osysswLog);
            /*Cod4* /
            ointLine.v = 0;
            ointChar.v = 0;
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            codcbProg.subSubtractComAndAdvance(ocodComCb, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCb, "codComCb"), osysswLog);
            /** /
            /*Com3* /
            comcb = new ComcbCobol((CodcbCobol)ocodComCb.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb, "comcb"), osysswLog);
            /** /
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.codStandard(), "comcb.codStandard"), osysswLog);
            /** /
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.arrboolOriginalOK(), "comcb.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comcb.arrboolStandardOK(), "comcb.arrboolStandardOK"), osysswLog);
            /** /

                                                            //Prueba C# A1
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST C# A1 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            strFileName = "Prog_csA.cs";
            syspathFile = new SyspathPath(strPATH_DATA + strFileName);
            Sys.subWriteLine(Tes2.strTo(syspathFile, TestoptionEnum.SHORT), osysswLog);
            CodooObjectOriented codooProg = new CodooObjectOriented(syspathFile);
            Oobject<CodCodeAbstract> ocodComCs = new Oobject<CodCodeAbstract>();
            Sys.subWriteLine(Tes2.strTo(strFileName, "strFileName"), osysswLog);
            /*Cod4* /
            ointLine.v = 19;
            ointChar.v = 60;
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            codooProg.subSubtractComAndAdvance(ocodComCs, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCs, "codComCs"), osysswLog);
            /** /
            /*Com3* /
            ComooObjectOriented comoo = new ComooObjectOriented((CodooObjectOriented)ocodComCs.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo, "comoo"), osysswLog);
            /** /
            /*Com5* /
            Sys.subWriteLine("########## RESULTADOS arrstrParunToStandard", osysswLog);
            for (int intI = 0; intI < comoo.arrpar().length; intI = intI + 1)
            {
                Sys.subWriteLine(
                    Tes2.strTo(comoo.arrpar()[intI].arrstrLineStandard(), "comoo.arrpar[" + intI + "].arrstrLinePar"),
                    osysswLog);
            }
            /** /
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.codStandard(), "comoo.codStandard"), osysswLog);
            /** /
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolOriginalOK(), "comoo.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolStandardOK(), "comoo.arrboolStandardOK"), osysswLog);
            /** /

            
                                                            //Prueba C# A2
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST C# A2 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            Sys.subWriteLine(Tes2.strTo(syspathFile, "syspathFile"), osysswLog);
            /*Cod4* /
            ointLine.v = 0;
            ointChar.v = 0;
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            codooProg.subSubtractComAndAdvance(ocodComCs, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCs, "codComCs"), osysswLog);
            /** /
            /*Com3* /
            comoo = new ComooObjectOriented((CodooObjectOriented)ocodComCs.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo, "comoo"), osysswLog);
            /** /
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.codStandard(), "comoo.codStandard"), osysswLog);
            /** /
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolOriginalOK(), "comoo.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolStandardOK(), "comoo.arrboolStandardOK"), osysswLog);
            /** /

                                                            //Prueba C# A3
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST C# A3 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            Sys.subWriteLine(Tes2.strTo(syspathFile, "syspathFile"), osysswLog);
            /*Cod4* /
            ointLine.v = 59;
            ointChar.v = 8;
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            codooProg.subSubtractComAndAdvance(ocodComCs, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCs, "codComCs"), osysswLog);
            /** /
            /*Com3* /
            comoo = new ComooObjectOriented((CodooObjectOriented)ocodComCs.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo, "comoo"), osysswLog);
            /** /
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.codStandard(), "comoo.codStandard"), osysswLog);
            /** /
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolOriginalOK(), "comoo.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolStandardOK(), "comoo.arrboolStandardOK"), osysswLog);
            /** /

                                                            //Prueba C# A4
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST C# A4 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            Sys.subWriteLine(Tes2.strTo(syspathFile, "syspathFile"), osysswLog);
            /*Cod4* /
            ointLine.v = 65;
            ointChar.v = 0;
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            codooProg.subSubtractComAndAdvance(ocodComCs, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCs, "codComCs"), osysswLog);
            /** /
            /*Com3* /
            comoo = new ComooObjectOriented((CodooObjectOriented)ocodComCs.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo, "comoo"), osysswLog);
            /**/
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.codStandard(), "comoo.codStandard"), osysswLog);
            /** /
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolOriginalOK(), "comoo.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolStandardOK(), "comoo.arrboolStandardOK"), osysswLog);
            /** /

                                                            //Prueba C# A5
            Sys.subWriteLine("", osysswLog);
            Sys.subWriteLine("#################### TEST C# A5 ##########", osysswLog);
            Tes2.subInitializeLstobjPreviouslyProcessed();
            Sys.subWriteLine(Tes2.strTo(strFileName, "strFileName"), osysswLog);
            /*Cod4* /
            ointLine.v = 57;
            ointChar.v = 60;
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar"), osysswLog);
            codooProg.subSubtractComAndAdvance(ocodComCs, ointLine, ointChar);
            Sys.subWriteLine("########## RESULTADO subExtraeComYAvanza", osysswLog);
            Sys.subWriteLine(Tes2.strTo(ointLine, "intLine") + ", " + Tes2.strTo(ointChar, "intChar") + ", " +
                Tes2.strTo(ocodComCs, "codComCs"), osysswLog);
            /** /
            /*Com3* /
            comoo = new ComooObjectOriented((CodooObjectOriented)ocodComCs.v);
            Sys.subWriteLine("########## RESULTADO ComCommentsAbstract(cod)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo, "comoo"), osysswLog);
            /** /
            /*Cod3* /
            Sys.subWriteLine("########## RESULTADO CodCodeAbstract(com)", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.codStandard(), "comoo.codStandard"), osysswLog);
            /** /
            /*Com2* /
            Sys.subWriteLine("########## RESULTADO (Com)subStandardVsOriginal", osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolOriginalOK(), "comoo.arrboolOriginalOK"), osysswLog);
            Sys.subWriteLine(Tes2.strTo(comoo.arrboolStandardOK(), "comoo.arrboolStandardOK"), osysswLog);
            /**/
             
                                                            //Resumen de objetos en todas las pruebas
            BclassBaseClassAbstract.subWriteSummary(osysswLog.v);

            osysswLog.v.close();
        }

}
