package Tsaz;


import Cod.*;
import Tech.Tech;
import Ti.*;
import Tss.*;

import java.io.File;
import java.io.PrintWriter;

public final class TestCod
{
    private static final boolean boolIsTestForAutomaticVerification = false;
    //                                                      //Developer information
    /*GLG* /
    private static final String strNAME = "Gerardo Lopez";
    private static final String strMY_DESKTOP = "\\\\psf\\Home\\Desktop";
    /**/
    /*AAH*/
    private static final String strNAME = "Alejandro arellano";
    private static final String strMY_DESKTOP = "C:\\Users\\Alejandro\\Documents\\Towa\\TowaDesktop";
    /**/

    private static final String strAPPLICATION = "SoftwareAutomation";
    private static final String strRELEVANT_PART = "Cod";
    //                                                      //DO NOT CHANGE
    //                                                      //Data a log should be in directories:
    private static final String strPATH = strMY_DESKTOP + "\\Test " + strAPPLICATION + "\\?\\" + strRELEVANT_PART;
    private static SyspathPath syspathDATA = new SyspathPath(strPATH.replace("?", "Data"));
    private static SyspathPath syspathLOGS = new SyspathPath(strPATH.replace("?", "Logs " + Std.strOO_INST));


    //--------------------------------------------------------------------------------------------------------------
    public static void subTestCod1()
    {
        char[] myChar = {'a', 'b'};
        char[][] doubleChar = {{}};

        System.out.println(Tes2.strTo(myChar, "myChar"));
        System.out.println(Tes3.strTo(myChar, "myChar"));


        String strTEST = "Cod1";
        Tes3.subInitializeLog(strNAME, syspathLOGS, strTEST, boolIsTestForAutomaticVerification);

        //                                                  //Prueba COBOL
        /*cb*/
        Tes3.subLog("");
        Tes3.subLog("##################### Tes3 COBOL ##########");
        Tes3.subInitializeLstobjPreviouslyProcessed();

        CodcbCobol codcb = CodcbCobol.codcbDUMMY_UNIQUE;

        Tes3.subLog(Tes3.strTo(codcb.codDUMMY(), "codcb.codDUMMY"));
        Tes3.subLog(Tes3.strTo(codcb.t4techINFO(), "codcb.t4techINFO"));
        Tes3.subLog(Tes3.strTo(codcb.arrcharUSEFUL(), "codcb.arrcharUSEFUL"));
        Tes3.subLog(Tes3.strTo(codcb.arrcharTO_CONVERT(), "codcb.arrcharTO_CONVERT"));
        Tes3.subLog(Tes3.strTo(codcb.arrcharCONVERSION(), "codcb.arrcharCONVERSION"));
        Tes3.subLog(Tes3.strTo(codcb.strSTART_FILLER(), "codcb.strSTART_FILLER"));
        Tes3.subLog(Tes3.strTo(codcb.boolIS_FIX_LENGTH(), "codcb.boolIS_FIX_LENGTH"));
        Tes3.subLog(Tes3.strTo(codcb.intLINE_MAX_LENGTH(), "codcb.intLINE_MAX_LENGTH"));
        /**/
        
        //                                              //Prueba Object Oriented
        /*oo*/
        Tes3.subLog("");
        Tes3.subLog("##################### Tes3 Object Oriented ##########");
        Tes3.subInitializeLstobjPreviouslyProcessed();

        CodooObjectOriented codoo = CodooObjectOriented.codooDUMMY_UNIQUE;

        Tes3.subLog(Tes3.strTo(codoo.codDUMMY(), "codoo.codDUMMY"));
        Tes3.subLog(Tes3.strTo(codoo.t4techINFO(), "codoo.t4techINFO"));
        Tes3.subLog(Tes3.strTo(new String(codoo.arrcharUSEFUL()), "new String(codoo.arrcharUSEFUL)"));
        Tes3.subLog(Tes3.strTo(codoo.arrcharTO_CONVERT(), "codoo.arrcharTO_CONVERT"));
        Tes3.subLog(Tes3.strTo(codoo.arrcharCONVERSION(), "codoo.arrcharCONVERSION"));
        Tes3.subLog(Tes3.strTo(codoo.strSTART_FILLER(), "codoo.strSTART_FILLER"));
        Tes3.subLog(Tes3.strTo(codoo.boolIS_FIX_LENGTH(), "codoo.boolIS_FIX_LENGTH"));
        Tes3.subLog(Tes3.strTo(codoo.intLINE_MAX_LENGTH(), "codoo.intLINE_MAX_LENGTH"));
        /**/

        /*
        SyspathPath syspathFileForTestLog = syspathLOGS.syspathAddName(strTEST + ".test");

        Oobject<PrintWriter> osysswLog = new Oobject<PrintWriter>();
        Tes3.subInitializeLog(strNAME, syspathFileForTestLog.strFullPath(), osysswLog,
            boolIsTestForAutomaticVerification);
            */

        //                                                  //Prueba COBOL
        /*cb* /
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### TEST COBOL ##########", osysswLog);
        Tes3.subInitializeLstobjPreviouslyProcessed();

        CodcbCobol codcb = CodcbCobol.codcbDUMMY_UNIQUE;

        Sys.subWriteLine(Tes3.strTo(codcb.codDUMMY(), "codcb.codDUMMY"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codcb.t4techINFO(), "codcb.t4techINFO"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codcb.arrcharUSEFUL(), "codcb.arrcharUSEFUL"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codcb.arrcharTO_CONVERT(), "codcb.arrcharTO_CONVERT"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codcb.arrcharCONVERSION(), "codcb.arrcharCONVERSION"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codcb.strSTART_FILLER(), "codcb.strSTART_FILLER"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codcb.boolIS_FIX_LENGTH(), "codcb.boolIS_FIX_LENGTH"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codcb.intLINE_MAX_LENGTH(), "codcb.intLINE_MAX_LENGTH"), osysswLog);
        /**/

        //                                                  //Prueba Object Oriented
        /*oo* /
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### TEST Object Oriented ##########", osysswLog);
        Tes3.subInitializeLstobjPreviouslyProcessed();

        CodooObjectOriented codoo = CodooObjectOriented.codooDUMMY_UNIQUE;

        Sys.subWriteLine(Tes3.strTo(codoo.codDUMMY(), "codoo.codDUMMY"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codoo.t4techINFO(), "codoo.t4techINFO"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(new String(codoo.arrcharUSEFUL()), "new String(codoo.arrcharUSEFUL)"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codoo.arrcharTO_CONVERT(), "codoo.arrcharTO_CONVERT"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codoo.arrcharCONVERSION(), "codoo.arrcharCONVERSION"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codoo.strSTART_FILLER(), "codoo.strSTART_FILLER"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codoo.boolIS_FIX_LENGTH(), "codoo.boolIS_FIX_LENGTH"), osysswLog);
        Sys.subWriteLine(Tes3.strTo(codoo.intLINE_MAX_LENGTH(), "codoo.intLINE_MAX_LENGTH"), osysswLog);
        /**/

        //                                                  //Prueba Xml
        /*xl*/
        //                                                  //Xml not ready in Java
        /**/

        //                                                  //Prueba Text
        /*tx*/
        //                                                  //Text not ready in Java
        /**/

        //                                                  //Resumen de objetos en todas las pruebas
//        BclassBaseClassAbstract.subWriteSummary(osysswLog.v);
        BclassBaseClassAbstract.subWriteSummary();
        Tes3.subFinalizeLog();
    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subTestCod2()
    {
        String strTEST = "Cod2";
        Tes3.subInitializeLog(strNAME, syspathLOGS, strTEST, boolIsTestForAutomaticVerification);

        /*
        SyspathPath syspathFileForTestLog = syspathLOGS.syspathAddName(strTEST + ".test");


        Oobject<PrintWriter> osysswLog = new Oobject<PrintWriter>();
        Tes3.subInitializeLog(strNAME, syspathFileForTestLog.strFullPath(), osysswLog,
            boolIsTestForAutomaticVerification);
        */

        //                                                  //Prueba COBOL
        /*cb*/
        Tes3.subLog("");
        Tes3.subLog("##################### Tes3 COBOL X ##########");
        Tes3.subInitializeLstobjPreviouslyProcessed();

        String strFileNameCbX = "Prog_cbX.cobol";
        SyspathPath syspathFileCbX = syspathDATA.syspathAddName(strFileNameCbX);
        SyspathPath syspathFileTes3 = syspathDATA.syspathAddName(strFileNameCbX);
        Tes3.subLog(Tes3.strTo(syspathFileCbX, StrtoEnum.SHORT));
        CodcbCobol codcbX = new CodcbCobol(null, syspathFileCbX);

        Tes3.subLog(Tes3.strTo(codcbX, "codcbX"));
        /**/

        //                                                  //Prueba Object Oriented
        /*oo*/
        Tes3.subLog("");
        Tes3.subLog("##################### Tes3 Object Oriented X ##########");
        Tes3.subInitializeLstobjPreviouslyProcessed();

        String strFileNameOoX = "Prog_ooX.cs";
        SyspathPath syspathFileOoX = syspathDATA.syspathAddName(strFileNameOoX);
        Tes3.subLog(Tes3.strTo(syspathFileOoX, StrtoEnum.SHORT));
        CodooObjectOriented codooX = new CodooObjectOriented(null, syspathFileOoX);

        Tes3.subLog(Tes3.strTo(codooX, "codooX"));
        /**/



        //                                                  //Prueba COBOL
        /*cb* /
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### TEST COBOL X ##########", osysswLog);
        Tes3.subInitializeLstobjPreviouslyProcessed();

        String strFileNameCbX = "Prog_cbX.cobol";
        SyspathPath syspathFileCbX = syspathDATA.syspathAddName(strFileNameCbX);
        Sys.subWriteLine(Tes3.strTo(syspathFileCbX, StrtoEnum.SHORT), osysswLog);
        CodcbCobol codcbX = new CodcbCobol(null, syspathFileCbX);

        Sys.subWriteLine(Tes3.strTo(codcbX, "codcbX"), osysswLog);
        /**/
        
        //                                                  //Prueba Object Oriented
        /*oo* /
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### Tes3 Object Oriented X ##########", osysswLog);
        Tes3.subInitializeLstobjPreviouslyProcessed();

        String strFileNameOoX = "Prog_ooX.cs";
        SyspathPath syspathFileOoX = syspathDATA.syspathAddName(strFileNameOoX);
        Sys.subWriteLine(Tes3.strTo(syspathFileOoX, StrtoEnum.SHORT), osysswLog);
        CodooObjectOriented codooX = new CodooObjectOriented(null, syspathFileOoX);

        Sys.subWriteLine(Tes3.strTo(codooX, "codooX"), osysswLog);
        /**/

        //                                                  //Prueba Xml
        /*xl*/
        //                                                  //Xml not ready in Java
        /**/

        //                                                  //Prueba Text
        /*tx*/
        //                                                  //Text not ready in Java
        /**/

        //                                                  //Resumen de objetos en todas las pruebas
//        BclassBaseClassAbstract.subWriteSummary(osysswLog.v);
        BclassBaseClassAbstract.subWriteSummary();

//        osysswLog.v.close();
        Tes3.subFinalizeLog();
    }
}
