/*
 * Program: UrlTool.java
 * Purpose: To assist in user's use of urls in a given program
 * Author: Luke Sieben
 * Date: August 25, 2012
 */

import java.awt.Desktop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URI;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JProgressBar;

public final class UrlTool {
	/*
	 * returns properly formatted url of the given url
	 */
	public static String toProperUrl(String url) {
		if(url.matches("http://www\\..*")) {
			return url;
		}
		else if(url.matches("http://.*")) {
			return "http://www." + url.substring(7);
		}
		else if(url.matches("www\\..*")) {
			return "http://" + url;
		}
		else {
			return "http://www." + url;
		}
	}

	/*
	 * returns a simplified version of the given url
	 */
	public static String toSimpleUrl(String url) {
		if(url.matches("http://www\\..*")) {
			return url.substring(11);
		}
		else if(url.matches("http://.*")) {
			return url.substring(7);
		}
		else if(url.matches("www\\..*")) {
			return url.substring(4);
		}
		else {
			return url;
		}
	}

	/*
	 * returns the size of a page in bytes, -1 if any error occurs
	 */
	public static int getPageSize(String url) {
		try {
			return new URL(toProperUrl(url)).openConnection().getContentLength();
		}
		catch(MalformedURLException mue) {
			System.out.println(url + " is not a valid url --> " + mue.getMessage());
		}
		catch(IOException ioe) {
			System.out.println(url + " failed to be read --> " + ioe.getMessage());
		}
		return -1;
	}

	/*
	 * searches for the first line on the given url containing the given regex
	 * and returns that line if found, or null
	 */
	public static String findLine(String url, String regex) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(toProperUrl(url)).openStream()));
			String inputLine;

			while((inputLine = in.readLine()) != null) {
				if(inputLine.matches(regex)) {
					in.close();
					return inputLine;
				}
			}
		}
		catch(IOException ioe) {
			System.out.println(url + " failed to be read --> " + ioe.getMessage());
		}
		catch(NullPointerException npe) {
			System.out.println(url + " was a null pointer --> " + npe.getMessage());
		}
    	return null;
    }

	/*
	 * attempts to open a given url in the user's default browser
	 * return true on success, false on failure
	 */
    public static boolean openPage(String url) {
		try {
	        Desktop.getDesktop().browse(URI.create(toProperUrl(url)));
	        return true;
		}
		catch (IOException e) {
			System.out.println(url + " failed to open --> " + e.getMessage());
		}
		return false;
	}

	/*
	 * attempts to save a given url into a given file
	 * return true on success, false on failure
	 */
	public static boolean savePage(String url, String file) {
		try {
			InputStream is = new URL(toProperUrl(url)).openStream();
			OutputStream os = new FileOutputStream(file);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();

			return true;
		}
		catch(IOException e) {
			System.out.println(url + " failed to save --> " + e.getMessage());
		}
		return false;
	}

	/*
	 * attempts to save a given url into a given file and updates a progress bar
	 * return true on success, false on failure
	 */
	public static boolean savePagePlusProgress(String url, String file, JProgressBar progressBar) {
		try {
			InputStream is = new URL(toProperUrl(url)).openStream();
			OutputStream os = new FileOutputStream(file);
			File f = new File(file);
			float totalSize = getPageSize(url);
			byte[] b = new byte[2048];
			int length = 0;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
				progressBar.setValue((int)(f.length() / totalSize * 100));
				System.out.println((int)(f.length() / totalSize * 100));
			}
			is.close();
			os.close();

			return true;
		}
		catch(IOException e) {
			System.out.println(url + " failed to save --> " + e.getMessage());
		}

		progressBar.setString("Failed");
		return false;
	}

	/*
	 * attempts to save a given url into a given file more efficiently than the above method
	 * return true on success, false on failure
	 */
	public static boolean savePageFaster(String url, String file) {
		try {
			URL website = new URL(toProperUrl(url));
		    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		    FileOutputStream fos = new FileOutputStream(file);
		    fos.getChannel().transferFrom(rbc, 0, website.openConnection().getContentLength());
		    rbc.close();
		    fos.close();
		    return true;
		}
		catch(MalformedURLException mue) {
			System.out.println(url + " is not a valid url --> " + mue.getMessage());
		}
		catch(IOException ioe) {
			System.out.println(url + " failed to save --> " + ioe.getMessage());
		}
		return false;
	}
}
