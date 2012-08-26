/*
 * Program: Frame.java v0.5
 * Purpose: To start up the gui
 * Author: Luke Sieben
 * Date: August 25, 2012
 */

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Frame {
    public static void main(String args[]) {
    	JComponent newContentPane = new DownloaderGUI();
        JFrame frame = new JFrame("dl_0.5b(clean)");

        newContentPane.setOpaque(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(newContentPane);
        frame.setSize(270, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
