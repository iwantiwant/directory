/*
 * Program: BeegTool.java
 * Purpose: To download videos from beeg.com with ease
 * Author: Luke Sieben
 * Date: August 25, 2012
 */

import javax.swing.JProgressBar;

public final class BeegTool {
	/*
	 * searches for the source url of the video file based on the given beeg url
	 * and returns that line if found, or null
	 */
	public static String findBeegSourceUrl(String url) {
		String sourceUrl = UrlTool.findLine(url, ".*mp4.*");
    	return sourceUrl.substring(sourceUrl.indexOf("http://") + 7, sourceUrl.indexOf(".mp4") + 4);
	}

    /*
	 * attempts to save given beeg url video to a file and updates a progress bar
	 * returns true on success, false on failure
	 */
	public static boolean saveBeegVideoPlusProgress(String url, JProgressBar progressBar) {
		return UrlTool.savePagePlusProgress(findBeegSourceUrl(url), UrlTool.toSimpleUrl(url).substring(9) + ".mp4", progressBar);
	}
}
