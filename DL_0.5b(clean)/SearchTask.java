/*
 * Program: SearchTask.java
 * Purpose: To allow multiple threads to exist to search urls
 * Author: Luke Sieben
 * Date: August 25, 2012
 */

import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class SearchTask extends SwingWorker<Boolean, Void> {
	private String url;
	private String search;
	private JTextField field;

	public SearchTask(String url, String search, JTextField field) {
		this.url = url;
		this.search = search;
		this.field = field;
	}

	/*
	 * executed in the background thread
	 */
	@Override
	public Boolean doInBackground() {
		field.setText(UrlTool.findLine(url, search));

		return null;
	}

	/*
	 * executed when the background thread is done
	 */
	@Override
	public void done() {
		// TODO
	}
}
