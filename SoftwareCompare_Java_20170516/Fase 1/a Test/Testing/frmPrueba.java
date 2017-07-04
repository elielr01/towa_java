package Testing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

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
				//QEnablerBase.TestTest.subTest18SystemTypes();
	            //QEnablerBase.TestTest.subTest01FormatArrN();
	            //QEnablerBase.TestTest.subTest11Str();
	            //QEnablerBase.TestTest.subTest12Int();
	            //QEnablerBase.TestTest.subTest13Long();
	            //QEnablerBase.TestTest.subTest14Num();
	            //QEnablerBase.TestTest.subTest15Bool();
	            //QEnablerBase.TestTest.subTest16Char();
	            //QEnablerBase.TestTest.subTest17ts();
	            //QEnablerBase.TestTest.subTest19Obj();

	            //QEnablerBase.TestSyspath.subTestSyspathA();
	            //QEnablerBase.TestSys.subTestSysH();

				QEnablerBase.TestCod.subTestCod1();
				//QEnablerBase.TestCod.subTestCod2();
				//QEnablerBase.TestCom.subTestCom1();
				//QEnablerBase.TestCom.subTestCod4Com3Com4Com5Cod3Com2();
				System.exit(0);
			}
		});
	}
}
