package org.micromanager.zprojector;

import javax.swing.JFrame;

import org.micromanager.utils.MMFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ZProjectorConfiguration extends MMFrame {

	private static final long serialVersionUID = 1L;

	private final ZProjectorProcessor processor_;
	private final String[] projectionMethods = { "Average Intensity",
			"Max Intensity", "Min Intensity", "Sum Slices",
			"Standard Deviation", "Median" };
			
	JComboBox projectionMethodComboBox;
	
	public ZProjectorConfiguration(ZProjectorProcessor processor) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				setVisible(false);
			}
		});
		
		processor_ = processor;

		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Projection type : ");
		panel_2.add(lblNewLabel_1);
		
		projectionMethodComboBox = new JComboBox();
		projectionMethodComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String projectionType = (String) projectionMethodComboBox.getSelectedItem();
			      if (projectionType != null && projectionMethodComboBox.getSelectedItem() != null) {			         
			         if (processor_ != null) {
			            processor_.setProjectionMethod(projectionMethodComboBox.getSelectedIndex());
			         }
			      }
			}
		});
		projectionMethodComboBox.setModel(new DefaultComboBoxModel(projectionMethods));
		projectionMethodComboBox.setSelectedIndex(1);
		panel_2.add(projectionMethodComboBox);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JButton btnNewButcloseton = new JButton("Close");
		btnNewButcloseton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		btnNewButcloseton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnNewButcloseton);

	}
	
	
}
