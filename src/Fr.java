/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * 
 * @author tsoglani
 */
public class Fr extends JFrame {

	private boolean willClose = true;
	private Thread usingthread;
	private Wind w;

	public Fr() {
		setLayout(new GridLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel2 = new JPanel();
		add(panel);

		final JTextField txtField = new JTextField();
		txtField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				w = new Wind(null, Fr.this,Integer
						.parseInt(txtField.getText().replace(" ", "")));	
				setVisible(false);
			}
		});
		txtField.setColumns(10);
		JLabel timeToCLose = new JLabel("Shutdown time (minutes) ");

		panel2.add(timeToCLose);
		panel2.add(txtField);
		// panel.add(timelabel);

		JButton ok = new JButton("Ok");
		panel.add(panel2);
		panel.add(ok);
		// panel.add(label);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					w = new Wind(null, Fr.this,Integer
							.parseInt(txtField.getText().replace(" ", "")));	
					setVisible(false);
				} catch (Exception ex) {
					Logger.getLogger(Fr.class.getName()).log(Level.SEVERE,
							null, ex);
				}

			}
		});
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	
	public void setWind(Wind w){
		this.w=w;
	}
	
}
