package presentacion.vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.*;

import logica.SistemaSAP;
import javax.swing.text.DefaultCaret;
import presentacion.controladores.ControlPanelCPU;
import presentacion.Modelo;

@SuppressWarnings("serial")

public class VistaPanelCPU extends JPanel{

    private Modelo modelo;
    private SistemaSAP sistema;
    private JLabel lblEstadoReloj;    
    private JTextArea txaLogArea;
    private JButton btnReset;
    private JButton btnClock;
    private JButton btnEjecutar;
    private VistaWidgetSAP sapWidget;
    private VistaWidgetRAM ramWidget;
    private JSlider sliVelocidad;
    private VistaDisplaySieteSeg display7Seg;
    private ControlPanelCPU control;   

    private GridBagConstraints c;      
    private int x=935, y=30, ancho=210, largo=20;

    // Constants
    public static final Color VIEW_BACKGROUND_COLOR = new Color(224, 255, 255);    
        
    public VistaPanelCPU(Modelo m) {
        this.modelo = m;
        this.sistema = modelo.getSistema();
        this.setBackground(VIEW_BACKGROUND_COLOR);
        
        // Set the Layout
        this.setLayout(null);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        // Add the SAP View Widget (Middle part)
        this.display7Seg = new VistaDisplaySieteSeg(this.sistema.getRegistroSalida().getValor());
        this.ramWidget = new VistaWidgetRAM(modelo, this);
        
        this.sapWidget = new VistaWidgetSAP(modelo, display7Seg, this.ramWidget);
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 8;
        this.sapWidget.setBounds(15, 30, 700, 600);
        this.sapWidget.setBackground(new Color(248, 248, 255));
        this.ramWidget.setBounds(720, 30, 210, 600);
        this.display7Seg.setBounds(x, 500, 210, 130);
        this.add(sapWidget, c);

        // Add the RAM View Widget
        c.gridx = 0;
        c.gridy = 0;
        this.add(ramWidget, c);

        // Display the status of the clock
        lblEstadoReloj = new JLabel("Reloj: " + (logica.Clock.getClock().getEstado() ? "ALTO" : "BAJO"));
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 1;
        lblEstadoReloj.setBounds(725, 30, 210, 20);
        this.add(lblEstadoReloj, c);

        // Add reset button
        btnReset = new JButton("Reset");
        btnReset.setActionCommand("resetButtonClicked");
        btnReset.addActionListener(getControl());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 1;
        c.gridheight = 1;
        btnReset.setBounds(x, y+largo+2, ancho, largo);
        this.add(btnReset, c);

        // Add toggle clock button
        /*c.gridx = 3;
        c.gridy = 2;
        c.gridheight = 1;
        this.btnClock = new JButton("Ejecutar 1 Paso");
        this.btnClock.addActionListener(getControl());
        this.btnClock.setActionCommand("clockButton");
        this.add(btnClock, c);*/

        // Add autoplay button
        c.gridx = 3;
        c.gridy = 3;
        c.gridheight = 1;
        this.btnEjecutar = new JButton("Ejecutar");
        this.btnEjecutar.setActionCommand("autoplay");
        this.btnEjecutar.addActionListener(getControl());
        this.btnEjecutar.setBounds(x, y + largo*2 + 2*2, ancho, largo);
        this.add(btnEjecutar, c);

        // Add speed slider label
        /*c.gridx = 3;
        c.gridy = 4;
        c.ipady += 5;
        JLabel t = new JLabel("  Velocidad  ");
        t.setHorizontalAlignment(JLabel.CENTER);
        c.insets = new Insets(0, 7, -1, 5);
        t.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(t, c);
        c.ipady -= 5;*/

        // Agregar slider velocidad
        /*c.gridx = 3;
        c.gridy = 5;
        this.sliVelocidad = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        sliVelocidad.setMajorTickSpacing(10);
        sliVelocidad.setBorder(BorderFactory.createLineBorder(Color.black));
        c.insets = new Insets(0, 7, 5, 5);

        sliVelocidad.setPaintTicks(true);*/

        // Create the label table
        /*Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(15, new JLabel("Lento"));
        labelTable.put(95, new JLabel("Rápido"));
        sliVelocidad.setLabelTable(labelTable);
        sliVelocidad.setPaintLabels(true);
        sliVelocidad.addChangeListener(getControl());
        this.add(this.sliVelocidad, c);*/

        // Agrega un espacio a la izquierda del registro; agregar visualizador de registros
        c.insets = new Insets(0, 6, 5, 0);
        txaLogArea = new JTextArea(1, 1);
        txaLogArea.setMaximumSize(new Dimension(20, 20));
        txaLogArea.setText(" =================\n NOP	0000\n LDA	0001\n ADD	0010\n SUB	0011\n STA	0100\n LDI	0101\n JMP	0110\n JC	0111\n JZ	1000\n OUT	1110\n HLT	1111\n =================\n");
        txaLogArea.setEditable(false);
        c.gridx = 3;
        c.gridy = 6;
        c.ipadx = 240;
        c.ipady = 150;
        c.gridheight = 1;
        c.fill = GridBagConstraints.VERTICAL;
        DefaultCaret caret = (DefaultCaret) txaLogArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane sv = new JScrollPane(txaLogArea);
        sv.setAutoscrolls(true);
        sv.setPreferredSize(new Dimension(20, 100));
        sv.setMaximumSize(new Dimension(20, 100));
        sv.setBounds(x, y + largo*5 + 2*5, ancho, 350);
        this.add(sv, c);

        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 3;
        c.gridy = 7;
        this.add(this.display7Seg, c);

        // Add the view as a log observer
        logica.EventLog.getEventLog().addObserver(getControl());

        // Add the view as a clock observer
        logica.Clock.getClock().addObserver(getControl());
    }

    public ControlPanelCPU getControl() {
        if (control == null) {
            control = new ControlPanelCPU(this);
        }
        return control;
    }    

    public SistemaSAP getSistema() {
        return sistema;
    }
    
    public Modelo getModelo() {
        return modelo;
    }

    public JSlider getSliVelocidad() {
        return sliVelocidad;
    }

    public JTextArea getTxaLogArea() {
        return txaLogArea;
    }

    public JLabel getLblClockStatus() {
        return lblEstadoReloj;
    }

    public JButton getBtnEjecutar() {
        return btnEjecutar;
    }
}
