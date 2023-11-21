package presentacion.vistas;

import presentacion.controladores.ControladorWidgetRAM;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import presentacion.Modelo;
import logica.SistemaSAP;

@SuppressWarnings("serial")
public class VistaWidgetRAM extends JPanel {

    private VistaPanelCPU view;
    private GridBagConstraints c;
    private JButton[][] btnArrayBotones;
    
    private JButton btnLimpiarMemoria;
    private JButton btnMostrarOpcodes;
    private JButton btnCargarProgramaDemo;
    private JButton btnAnalizarPrograma;
    private JButton btnAssembler;
    private JButton btnResaltarMAR;
    private JPanel parentPanel;
    private byte valorMAR;
    private boolean debeResaltarMAR;

    private ControladorWidgetRAM control;

    // Constantes
    private static final Dimension buttonSize = new Dimension(22, 22);
    private static final Dimension WIDGET_SIZE = new Dimension(220, 550);
    public static final Color COLOR_ON = new Color(246, 203, 225);
    public static final Color COLOR_OFF = new Color(246, 213, 203);
    public static final Color COLOR_MAR = Color.gray;
    public static final Border BOTTOM_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
    public static final Border RIGHT_BORDER = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK);
    public static final Border BOTTOM_RIGHT_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK);
    public static final Border TOP_LEFT_RIGHT_BORDER = BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK);
    public static final Border LEFT_RIGHT_BORDER = BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK);
    public static final Border FULL_BORDER = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
    public static final String MAR_ON_LABEL = "[ON] Mostrar MAR en RAM";
    public static final String MAR_OFF_LABEL = "[OFF] Mostrar MAR en RAM";
    private static JScrollPane scrollPane = new JScrollPane();
    private static JTextPane txtLista, txtEtiquetas;
    private SistemaSAP sistema;
    private Modelo modelo;
    private String memoria = "", etiqueta = "";
    private int bx = 0, by = 0, bancho = 210, blargo = 20;

    public VistaWidgetRAM(Modelo m, JPanel parentPanel) {
    	this.setLayout(null);
        this.modelo = m;
        this.sistema = modelo.getSistema();

        this.valorMAR = 0;
        this.parentPanel = parentPanel;

        this.view = (VistaPanelCPU) parentPanel;
        this.debeResaltarMAR = true;

        // Agregar manejador del observador 
        this.sistema.getRAM().addRAMObserver(getControl());

        // Crea el array de botones que representan cada bit en cada posición de memory
        /*btnArrayBotones = new JButton[16][8]; // 16 posiciones de 8 bit cada una
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 8; j++) {
                this.btnArrayBotones[i][j] = new JButton("" + getControl().buscarEnRAM(i, 7 - j));
                this.btnArrayBotones[i][j].setPreferredSize(buttonSize);
                this.btnArrayBotones[i][j].setActionCommand(i + "," + j);
                this.btnArrayBotones[i][j].addActionListener(getControl());
                this.btnArrayBotones[i][j].setBorder(null);
                this.btnArrayBotones[i][j].setBackground(btnArrayBotones[i][j].getText().equals("1") ? COLOR_ON : COLOR_OFF);
                this.btnArrayBotones[i][j].setOpaque(true);
            }
        }*/
        
		
		scrollPane.setBounds(bx, by + blargo*5 + 2*5, bancho, 490);
		this.add(scrollPane);
		
		txtLista = new JTextPane();
		txtEtiquetas = new JTextPane();
		
		scrollPane.setViewportView(txtLista);
		scrollPane.setRowHeaderView(txtEtiquetas);
		
		
		System.out.println(Integer.toHexString(16000000));
        memoria = "";
        etiqueta = "";
        //Indicadores de Memoria
        for(int i=0; i<160; i++) {
        	etiqueta = etiqueta + "[" + String.format("%6s", Integer.toHexString(i)).replace(" ", "0") + "]\n";
        	
			//etiqueta = etiqueta + "[" + etiqueta + "]";
			
			for(int j=0; j<8; j++) {
				
				memoria = memoria + " " + getControl().buscarEnRAM(i, 7 - j);
			}
			memoria = memoria + "\n";
		}
        
        
        System.out.println();
        txtEtiquetas.setFont(new Font("Consolas",Font.ROMAN_BASELINE,13));
        txtLista.setFont(new Font("Consolas",Font.ROMAN_BASELINE,13));
        
        txtEtiquetas.setText(etiqueta);
        txtLista.setText(memoria);
        
        
        // size
        this.setPreferredSize(WIDGET_SIZE);
        this.setBackground(VistaPanelCPU.VIEW_BACKGROUND_COLOR);

        // Layout
        this.setLayout(null);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // Botón borrar RAM 
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 9;
        this.btnLimpiarMemoria = new JButton("Limpiar Memoria");
        this.btnLimpiarMemoria.setActionCommand("clearmem");
        this.btnLimpiarMemoria.addActionListener(getControl());
        this.btnLimpiarMemoria.setBounds(bx, by, bancho, blargo);
        this.add(this.btnLimpiarMemoria, c);

        // Botón OPCodes 
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 9;
        this.btnMostrarOpcodes = new JButton("Codigo de Operacion");
        this.btnMostrarOpcodes.setActionCommand("showopcodes");
        this.btnMostrarOpcodes.addActionListener(getControl());
        this.btnMostrarOpcodes.setBounds(bx, by + blargo + 2, bancho, blargo);
        this.add(this.btnMostrarOpcodes, c);

        // Botón carga programa demo
        /*c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 9;
        this.btnCargarProgramaDemo = new JButton("Cargar Programa demo");
        this.btnCargarProgramaDemo.setActionCommand("loadcountprogram");
        this.btnCargarProgramaDemo.addActionListener(getControl());
        this.add(this.btnCargarProgramaDemo, c);*/

        // Botón analizar programa 
        /*c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 9;
        this.btnAnalizarPrograma = new JButton("Analizar Programa");
        this.btnAnalizarPrograma.setActionCommand("analyzeProgram");
        this.btnAnalizarPrograma.addActionListener(getControl());
        this.btnAnalizarPrograma.setBounds(bx, by + blargo*2 + 2*2, bancho, blargo);
        this.add(this.btnAnalizarPrograma, c);*/

        // Botón assembler 
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 9;
        this.btnAssembler = new JButton("Assembler");
        this.btnAssembler.setActionCommand("openAssembler");
        this.btnAssembler.addActionListener(getControl());
        this.btnAssembler.setBounds(bx, by + blargo*2 + 2*2, bancho, blargo);
        this.add(this.btnAssembler, c);

        // Botón resaltar posición de memoria respecto al MAR 
        /*c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 9;
        this.btnResaltarMAR = new JButton(this.debeResaltarMAR ? MAR_ON_LABEL : MAR_OFF_LABEL);
        this.btnResaltarMAR.setActionCommand("toggleMAR");
        this.btnResaltarMAR.addActionListener(getControl());
        this.btnResaltarMAR.setBounds(bx, by + blargo*3 + 2*3, bancho, blargo);
        this.add(this.btnResaltarMAR, c);*/

        // Contenido RAM 
        c.gridx = 1;
        c.gridheight = 1;
        c.gridwidth = 9;
        c.gridy = 6;
        JLabel tmp = new JLabel("Contenido de Memoria");
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setBorder(FULL_BORDER);
        this.add(tmp, c);

        // El contenido de la memoria
        c.gridx = 4;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        /*for (int i = 1; i <= 16; i++) {
            c.gridx = 1;
            c.gridy = i + 5 + 1;

            String n = String.format("%4s", Integer.toBinaryString(i - 1)).replace(" ", "0");
            JLabel tmp1 = new JLabel(" [" + n + "] ");
            tmp1.setBorder(FULL_BORDER);
            this.add(tmp1, c);

            for (int j = 2; j < 10; j++) {
                c.gridx = j;
                //this.add(btnArrayBotones[c.gridy - 1 - 5 - 1][j - 2], c);
            }
        }*/

        // Agregue los bordes derechos a la visualización de RAM
        //for (int i = 0; i < this.btnArrayBotones.length; i++) {
            //this.btnArrayBotones[i][this.btnArrayBotones[0].length - 1].setBorder(RIGHT_BORDER);
        //}

        // Agregue el borde inferior a la visualización de RAM
       /* for (int i = 0; i < this.btnArrayBotones[0].length; i++) {
            // La pieza inferior derecha tiene un borde especial
            if (i == 7) {
                this.btnArrayBotones[this.btnArrayBotones.length - 1][i]
                        .setBorder(BOTTOM_RIGHT_BORDER);
            } else {
                this.btnArrayBotones[this.btnArrayBotones.length - 1][i]
                        .setBorder(BOTTOM_BORDER);
            }
        }
        getControl().cambioMAR(this.valorMAR);
        repaint();*/
    }

    public ControladorWidgetRAM getControl() {
        if (control == null) {
            control = new ControladorWidgetRAM(this);
        }
        return control;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public VistaPanelCPU getView() {
        return view;
    }

    public JButton[][] getBtnArrayBotones() {
        return btnArrayBotones;
    }

    public JButton getHighlightMarButton() {
        return btnResaltarMAR;
    }

    public void setHighlightMarButton(JButton highlightMarButton) {
        this.btnResaltarMAR = highlightMarButton;
    }

    public byte getValorMAR() {
        return valorMAR;
    }

    public void setValorMAR(byte v) {
        this.valorMAR = v;
    }

    public boolean isDebeResaltarMAR() {
        return debeResaltarMAR;
    }

    public void setDebeResaltarMAR(boolean shouldHighlightMAR) {
        this.debeResaltarMAR = shouldHighlightMAR;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public SistemaSAP getSistema() {
        return sistema;
    }

	public String getMemoria() {
		return memoria;
	}

	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}

	public static JTextPane getTxtLista() {
		return txtLista;
	}

	public static void setTxtLista(JTextPane txtLista) {
		VistaWidgetRAM.txtLista = txtLista;
	}
    
    

}
