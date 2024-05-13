import ui.LoginScreen;
public class APP {
	public static void main(String[] ars) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new LoginScreen();
			}
		});
	}

}
