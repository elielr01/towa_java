package QEnablerBase;

import TowaInfrastructure.*;
import QEnablerObjectOriented.*;
import QEnablerCobol.*;
import javafx.print.Printer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.*;


public final class TestCod
{
	final static String strNAME = "Eli Linares";
//    final static String strPATH = "../../Testing/Java/Test 0 Cod" + "/";
                                                            //The first two new Files are created to get the parent
                                                            //  directory of this project. Then, the test directory
                                                            // path is added.
    final static String strPATH = new File(new File(System.getProperty("user.dir")).getParent()).getParent()+
        "/QEnabler Test/<Data or Logs ?>/QEnablerBase/Cod/";
    private static String strPATH_DATA = strPATH.replace("<Data or Logs ?>", "Data");
    private static String strPATH_LOGS = strPATH.replace("<Data or Logs ?>", "Logs Java");

    //--------------------------------------------------------------------------------------------------------------
    public static void subTestCod1()
    {
        Oobject<PrintWriter> osysswLog = new Oobject<PrintWriter>();
        Tes2.subInitializeLog(strNAME, strPATH_LOGS + "Test Cod1.log", osysswLog, true);

                                                            //Prueba COBOL
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### TEST COBOL ##########", osysswLog);
        Tes2.subInitializeLstobjPreviouslyProcessed();
        CodcbCobol codcbDUMMY = CodcbCobol.codcbDUMMY_UNIQUE_Z;
        Sys.subWriteLine(Tes2.strTo(codcbDUMMY.arrstrFILE_EXTENSION(), "codcbDUMMY.arrstrFILE_EXTENSION"), osysswLog);
        Sys.subWriteLine(Tes2.strTo(codcbDUMMY.arrcharUSEFUL(), "codcbDUMMY.arrcharUSEFUL"), osysswLog);
        Sys.subWriteLine(Tes2.strTo(codcbDUMMY.arrcharTO_CONVERT(), "codcbDUMMY.arrcharTO_CONVERT"), osysswLog);
        Sys.subWriteLine(Tes2.strTo(codcbDUMMY.arrcharCONVERSION(), "codcbDUMMY.arrcharCONVERSION"), osysswLog);

                                                  			//Prueba C#
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### TEST C# ##########", osysswLog);
        Tes2.subInitializeLstobjPreviouslyProcessed();
        CodooObjectOriented codooDUMMY = CodooObjectOriented.codooDUMMY_UNIQUE_Z;
        Sys.subWriteLine(Tes2.strTo(codooDUMMY.arrstrFILE_EXTENSION(), "codooDUMMY.arrstrFILE_EXTENSION"), osysswLog);
        Sys.subWriteLine(Tes2.strTo(codooDUMMY.arrcharUSEFUL(), "codooDUMMY.arrcharUSEFUL"), osysswLog);
        Sys.subWriteLine(Tes2.strTo(codooDUMMY.arrcharTO_CONVERT(), "codooDUMMY.arrcharTO_CONVERT"), osysswLog);
        Sys.subWriteLine(Tes2.strTo(codooDUMMY.arrcharCONVERSION(), "codooDUMMY.arrcharCONVERSION"), osysswLog);

                                                  			//Resumen de objetos en todas las pruebas
        BclassBaseClassAbstract.subWriteSummary(osysswLog.v);

        osysswLog.v.close();

    }

    //--------------------------------------------------------------------------------------------------------------
    public static void subTestCod2()
    {
        Oobject<PrintWriter> osysswLog = new Oobject<PrintWriter>();
        Tes2.subInitializeLog(strNAME, strPATH_LOGS + "Test Cod2.log", osysswLog, true);

                                                            //Prueba COBOL X
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### TEST COBOL X ##########", osysswLog);
        Tes2.subInitializeLstobjPreviouslyProcessed();
        String strFileNameCs = "Prog_cbX.cobol";
        SyspathPath syspathFile = new SyspathPath(strPATH_DATA + strFileNameCs);
        Sys.subWriteLine(Tes2.strTo(syspathFile, TestoptionEnum.SHORT), osysswLog);
        CodcbCobol codcbProg = new CodcbCobol(syspathFile);
        Sys.subWriteLine(Tes2.strTo(codcbProg, "codcbProg"), osysswLog);

                                                            //Prueba COBOL Y
        /*Y*/
        Sys.subWriteLine("", osysswLog);
        Sys.subWriteLine("##################### TEST COBOL Y ##########", osysswLog);
        Tes2.subInitializeLstobjPreviouslyProcessed();
        strFileNameCs = "Prog_cbY.cobol";
        syspathFile = new SyspathPath(strPATH_DATA + strFileNameCs);
        Sys.subWriteLine(Tes2.strTo(syspathFile, TestoptionEnum.SHORT), osysswLog);
        codcbProg = new CodcbCobol(syspathFile);
        Sys.subWriteLine(Tes2.strTo(codcbProg, "codcbProg"), osysswLog);
        /**/


                                                  			//Prueba COBOL A
        /*REAL* /
        sysswLog.println("");
        sysswLog.println("########## **********TEST COBOL A**********");
        strFileName = "Prog_cbA.cobol";
        syspathFile = new SyspathPath(strPATH + strFileName);
        sysswLog.println(Tes2.strTo(syspathFile, StrtoOptionEnum.SHORT));
        codcbProg = new CodcbCobol(syspathFile);
        sysswLog.println(Tes2.strTo(codcbProg, "codcbProg"));
        /**/

                                                  			//Prueba C# A
        /*REAL* /
        sysswLog.println("");
        sysswLog.println("########## **********TEST C# A**********");
        strFileName = "Prog_csA.cs";
        syspathFile = new SyspathPath(strPATH + strFileName);
        sysswLog.println(Tes2.strTo(syspathFile, StrtoOptionEnum.SHORT));
        CodooCode codooProg = new CodooCode(syspathFile);
        sysswLog.println("codooProg(" + Tes2.strTo(codooProg));
        /**/

                                                  			//Resumen de objetos en todas las pruebas
        BclassBaseClassAbstract.subWriteSummary(osysswLog.v);

        osysswLog.v.close();
    }
}
