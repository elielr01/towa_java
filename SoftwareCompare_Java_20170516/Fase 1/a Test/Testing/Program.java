package Testing;

import TowaInfrastructure.Oobject;

import java.awt.EventQueue;
import java.util.LinkedHashMap;
import java.util.Map;

public class Program {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					frmPrueba frame = new frmPrueba();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
