package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import Controller.ExportRequirementController;
import Controller.GenerateRequirementController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class GenerateDialog extends JDialog{
	private final JPanel contentPanel = new JPanel();
	
	private GenerateRequirementController controller;
	
	public GenerateDialog(){
		setBounds(100, 100, 600, 900);
		
		controller = new GenerateRequirementController();
		String requirement = controller.generateRequirement();
		
		BoxLayout layout=new BoxLayout(contentPanel, BoxLayout.Y_AXIS); 
		contentPanel.setLayout(layout);

        JTextArea text = new JTextArea();
        text.setBounds(0, 0, 500, 500);
        text.setText(requirement);
        
        JScrollPane scrollPane = new JScrollPane(text);
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
		contentPanel.add(scrollPane);
		
		 JButton export = new JButton("Export");
	        export.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		JFileChooser fc = new JFileChooser();
	        		fc.showSaveDialog(null);
				FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt Files (*.txt)", "txt");
				//fc.setFileFilter(txtFilter);
				File file = fc.getSelectedFile();
				String filePath = file.getAbsolutePath();
				String fileName = file.getName();
				String extension = "";
				if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
					extension = fileName.substring(fileName.lastIndexOf(".")+1);
				}

				ExportRequirementController exp = new ExportRequirementController();
				exp.setExtension(extension);
				exp.setPath(filePath);
				exp.setRequirement(requirement);
				exp.exportRequirement();
	        	}
	        });
		contentPanel.add(export);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		

	    
	}
}
