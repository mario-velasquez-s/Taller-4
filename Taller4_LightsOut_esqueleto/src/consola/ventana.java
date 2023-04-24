package consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ventana extends JFrame implements ActionListener {

	private boolean[][] tablero;
    private int tamanio;
    private JButton[][] botones;
    private JComboBox<Integer> tamanioSeleccionado;
    private JTextField rowField, colField;
    private JRadioButton  botonBaja, botonMedia, botonAlta;
    
    private Color colorPrendido = new Color(225, 200, 75);
    private Color colorApagado = new Color(50, 50, 50);

	public ventana() {
		super("Light Out Game");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());

		JLabel etiquetaTamanio = new JLabel("Tamaño:");
		topPanel.add(etiquetaTamanio);

		Integer[] tamanios = { 3, 4, 5, 6, 7, 8 };
		tamanioSeleccionado = new JComboBox<>(tamanios);
		tamanioSeleccionado.setSelectedIndex(2); // Se selecciona el 5
		tamanioSeleccionado.addActionListener(this);
		topPanel.add(tamanioSeleccionado);
		
		JLabel etiquetaDificultad = new JLabel("Dificultad:");
		topPanel.add(etiquetaDificultad);

		botonBaja = new JRadioButton("baja");
		botonBaja.addActionListener(this);
		topPanel.add(botonBaja);

		botonMedia = new JRadioButton("media");
		botonMedia.addActionListener(this);
		topPanel.add(botonMedia);
		
		botonAlta = new JRadioButton("Alta");
		botonAlta.addActionListener(this);
		topPanel.add(botonAlta);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(botonBaja);
		buttonGroup.add(botonMedia);
		buttonGroup.add(botonBaja);

		add(topPanel, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel();
		int n = tamanioSeleccionado.getSelectedIndex();
		int x = tamanios[n];
		panelCentro.setLayout(new GridLayout(x , x + 10, 7, 7));

		tamanio = (int) tamanioSeleccionado.getSelectedItem();
		tablero = new boolean[tamanio][tamanio];
		botones = new JButton[tamanio][tamanio];

		for (int i = 0; i < tamanio; i++) {
			for (int j = 0; j < tamanio; j++) {
				botones[i][j] = new JButton();
				botones[i][j].setPreferredSize(new Dimension(40, 40));
				botones[i][j].setBackground(colorPrendido);
				botones[i][j].addActionListener(this);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = i; 
				gbc.gridy = j; 
				panelCentro.add(botones[i][j], gbc);
			}
		}

		add(panelCentro, BorderLayout.CENTER);
		
		
		
		
		JPanel panelDerecha = new JPanel();
		panelDerecha.setPreferredSize(new Dimension(200, 300));
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
		
		JButton opcionNuevo = new JButton("NUEVO");
		opcionNuevo.setPreferredSize(new Dimension(200, 50));
		panelDerecha.add(opcionNuevo);

		JButton opcionReiniciar = new JButton("REINICIAR");
		opcionReiniciar.setPreferredSize(new Dimension(200, 50));
		panelDerecha.add(opcionReiniciar);
		
		JButton opcionTop10 = new JButton("TOP 10");
		opcionTop10.setPreferredSize(new Dimension(200, 50));
		panelDerecha.add(opcionTop10);
		
		JButton opcionCambiarJugador = new JButton("CAMBIAR JUGADOR");
		opcionCambiarJugador.setPreferredSize(new Dimension(200, 50));
		panelDerecha.add(opcionCambiarJugador);
				
		add(panelDerecha, BorderLayout.EAST);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tamanioSeleccionado) {
			tamanio = (int) tamanioSeleccionado.getSelectedItem();
			tablero = new boolean[tamanio][tamanio];
			botones = new JButton[tamanio][tamanio];
			getContentPane().remove(1); 
			JPanel panelCentro = new JPanel();
			panelCentro.setLayout(new GridLayout(tamanio, tamanio, 7, 7));
			for (int i = 0; i < tamanio; i++) {
				for (int j = 0; j < tamanio; j++) {
					botones[i][j] = new JButton();
					botones[i][j].setPreferredSize(new Dimension(40, 40));
					botones[i][j].setBackground(colorPrendido);
					botones[i][j].addActionListener(this);
					panelCentro.add(botones[i][j]);
				}
			}
			add(panelCentro, BorderLayout.CENTER);
			revalidate();
		} else if (e.getSource() == botonAlta) {
			//TO DO
		} else if (e.getSource() == botonMedia) {
			//TO DO
		} else {
			JButton boton = (JButton) e.getSource();
			int fila = -1, columna = -1;
			for (int i = 0; i < tamanio; i++) {
				for (int j = 0; j < tamanio; j++) {
					if (botones[i][j] == boton) {
						fila = i;
						columna = j;
						break;
					}
				}
			}
			// Toggle the button and its neighbors
			tablero[fila][columna] = !tablero[fila][columna];
			sleccionarBombillo(boton);
			if (fila > 0) {
				tablero[fila - 1][columna] = !tablero[fila - 1][columna];
				sleccionarBombillo(botones[fila - 1][columna]);
			}
			if (fila < tamanio - 1) {
				tablero[fila + 1][columna] = !tablero[fila + 1][columna];
				sleccionarBombillo(botones[fila + 1][columna]);
			}
			if (columna > 0) {
				tablero[fila][columna - 1] = !tablero[fila][columna - 1];
				sleccionarBombillo(botones[fila][columna - 1]);
			}
			if (columna < tamanio - 1) {
				tablero[fila][columna + 1] = !tablero[fila][columna + 1];
				sleccionarBombillo(botones[fila][columna + 1]);
			}
		}
	}

	private void sleccionarBombillo(JButton button) {
		if (button.getBackground() == colorApagado) {
			button.setBackground(colorPrendido);
		} else {
			button.setBackground(colorApagado);
		}
	}

	public static void main(String[] args) {
		new ventana();
	}
}
