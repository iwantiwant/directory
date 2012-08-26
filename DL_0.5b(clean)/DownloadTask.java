/*
 * Program: DownloadTask.java
 * Purpose: To allow multiple threads to exist to download files
 * Author: Luke Sieben
 * Date: August 25, 2012
 */

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class DownloadTask extends SwingWorker<Boolean, Void> {
	private String url;
	private String file;
	private JProgressBar bar;

	public DownloadTask(String url, String file, JProgressBar bar) {
		this.url = url;
		this.file = file;
		this.bar = bar;
	}

	/*
	 * executed in the background thread
	 */
	@Override
	public Boolean doInBackground() {
		UrlTool.savePagePlusProgress(url, file, bar);

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
