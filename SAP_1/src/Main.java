import java.awt.EventQueue;

import javax.swing.JFrame;

import logica.SistemaSAP;
import presentacion.Modelo;
import presentacion.vistas.VistaPanelCPU;
import java.awt.Color;

public class Main {

	private JFrame frame;
	private String memoria = "";
	private SistemaSAP sistema;
    private Modelo modelo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(248, 248, 255));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		/*frame.modelo = m;
        this.sistema = modelo.getSistema();

        this.valorMAR = 0;
        this.parentPanel = parentPanel;

        this.view = (VistaPanelCPU) parentPanel;
        this.debeResaltarMAR = true;*/

        // Agregar manejador del observador 
        //this.sistema.getRAM().addRAMObserver(getControl());
		
		
		//botones
		for(int i=0; i<16000000; i++) {
			memoria = memoria + "[ " + Integer.toHexString(i) + " ]";
			for(int j=0; j<8; j++) {
				//memoria = memoria + " " + buscarEnRAM(i, 7 - j);
			}
		}
		
		//System.out.println(Integer.toHexString(16000000));
	}

}
