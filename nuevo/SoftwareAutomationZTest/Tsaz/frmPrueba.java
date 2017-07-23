package Tsaz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmPrueba extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public frmPrueba() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnPrueba = new JButton("Ejecuta Prueba");
        btnPrueba.setLocation(153, 105);
        contentPane.add(btnPrueba);
        btnPrueba.setSize(new Dimension(149, 23));

        btnPrueba.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //Tsaz.TestTest.subTest18SystemTypes();
                //Tsaz.TestTest.subTest01FormatArrN();
                //Tsaz.TestTest.subTest11Str();
                //Tsaz.TestTest.subTest12Int();
                //Tsaz.TestTest.subTest13Long();
                //Tsaz.TestTest.subTest14Num();
                //Tsaz.TestTest.subTest15Bool();
                //Tsaz.TestTest.subTest16Char();
                //Tsaz.TestTest.subTest17ts();
                //Tsaz.TestTest.subTest19Obj();

                //QEnablerBase.TestSyspath.subTestSyspathA();
                //QEnablerBase.TestSys.subTestSysH();

                Tsaz.TestCod.subTestCod1();
//                Tsaz.TestCod.subTestCod2();
                //TestNvsbcod nvbNvsbcod2 = new TestNvsbcod();
                //Tscz.TestNvsbcod.subTestNvsbcod2();
                //Tscz.TestNvsbcod.subTestNvsbcod3();
                //Tsaz.TestCom.subTestCom1();
                //Tsaz.TestCom.subTestCod4Com3Com4Com5Cod3Com2();
                System.exit(0);
            }
        });
    }
}
