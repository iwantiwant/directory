/*
 * Program: DownloaderGUI.java
 * Purpose: To display a nice GUI for downloading beeg.com videos
 * Author: Luke Sieben
 * Date: August 25, 2012
 */

import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class DownloaderGUI extends JPanel implements ActionListener {
    private JTextField urlTextField;
    private JTextField filenameTextField;
    private JTextField searchTextField;
    private JTextField matchedTextField;

    public DownloaderGUI() {
    	/*
    	 * outer panel
    	 */
		setLayout(new GridLayout(0, 1));

		/*
		 * first inner panel
		 */
		JPanel urlPanel = new JPanel();
		JTextField urlField = new JTextField("url");
		urlTextField = new JTextField();

		urlField.setHorizontalAlignment(JTextField.CENTER);
		urlTextField.setHorizontalAlignment(JTextField.CENTER);

		urlField.setEditable(false);

		urlPanel.setLayout(new GridLayout(0, 2));

		urlPanel.add(urlField);
        urlPanel.add(urlTextField);

        add(urlPanel);

		/*
		 * second inner panel
		 */
		JPanel filenamePanel = new JPanel();
        JTextField filenameField = new JTextField("filename");
        filenameTextField = new JTextField();

        filenameField.setHorizontalAlignment(JTextField.CENTER);
        filenameTextField.setHorizontalAlignment(JTextField.CENTER);

		filenameField.setEditable(false);

        filenamePanel.setLayout(new GridLayout(0, 2));

        filenamePanel.add(filenameField);
        filenamePanel.add(filenameTextField);

        add(filenamePanel);

        /*
		 * third inner panel
		 */
		JPanel searchPanel = new JPanel();
        JButton searchButton = new JButton("search");
        searchTextField = new JTextField();

        searchTextField.setHorizontalAlignment(JTextField.CENTER);

        searchPanel.setLayout(new GridLayout(0, 2));

        searchPanel.add(searchButton);
        searchPanel.add(searchTextField);

        searchButton.addActionListener(this);

        add(searchPanel);

		/*
		 * fourth inner panel
		 */
		JPanel matchedPanel = new JPanel();
        JTextField matchedField = new JTextField("matched");
        matchedTextField = new JTextField();

        matchedField.setHorizontalAlignment(JTextField.CENTER);
        matchedTextField.setHorizontalAlignment(JTextField.CENTER);

		matchedField.setEditable(false);
		matchedTextField.setEditable(false);

        matchedPanel.setLayout(new GridLayout(0, 2));

        matchedPanel.add(matchedField);
        matchedPanel.add(matchedTextField);

        add(matchedPanel);

		/*
		 * fifth inner panel
		 */
		JPanel buttonsPanel = new JPanel();
		JButton downloadButton = new JButton("download");
        JButton cancelButton = new JButton("cancel...");

        downloadButton.addActionListener(this);
        cancelButton.addActionListener(this);

        buttonsPanel.setLayout(new GridLayout(0, 2));

        buttonsPanel.add(downloadButton);
        buttonsPanel.add(cancelButton);

        add(buttonsPanel);
    }

    /*
     * invoked when the user hits enter
     */
    public void actionPerformed(ActionEvent ae) {
    	String command = ae.getActionCommand();
		//System.out.println(command);
    	if(command.equals("download")) { // if action was acted upon the download button
    		/*
    		 * check for empty text fields
    		 */
    		if(!urlTextField.getText().equals("") && !filenameTextField.getText().equals("")) {
		    	/*
		    	 * additional bottom inner panels
		    	 */
				JPanel downloadPanel = new JPanel();
				JTextField urlField = new JTextField(urlTextField.getText());
				JTextField filenameField = new JTextField(filenameTextField.getText());
	    		JProgressBar downloadProgressBar = new JProgressBar(0, 100);

	        	urlField.setHorizontalAlignment(JTextField.CENTER);
	        	filenameField.setHorizontalAlignment(JTextField.CENTER);

	        	urlField.setEditable(false);
	        	filenameField.setEditable(false);

	        	downloadProgressBar.setStringPainted(true);

				downloadPanel.setLayout(new GridLayout(0, 3));

	        	downloadPanel.add(urlField);
	        	downloadPanel.add(filenameField);
	        	downloadPanel.add(downloadProgressBar);

	        	add(downloadPanel);

	        	downloadPanel.revalidate();

				/*
				 * additional thread to prevent the program from freezing when downloading an item
				 */
	        	DownloadTask downloadTask = new DownloadTask(urlTextField.getText(), filenameTextField.getText(), downloadProgressBar);
	        	downloadTask.execute();
    		}
    	}
    	else if(command.equals("search")) {
    		SearchTask searchTask = new SearchTask(urlTextField.getText(), searchTextField.getText(), matchedTextField);
    		searchTask.execute();
    	}
    	else {
    		// TODO
    	}
    }
}
