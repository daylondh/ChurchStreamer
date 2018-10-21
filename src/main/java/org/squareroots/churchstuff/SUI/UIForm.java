package org.squareroots.churchstuff.SUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.calendar.ServiceCalculator;
import org.squareroots.churchstuff.streamer.StreamManager;
import org.squareroots.churchstuff.youtube.data.UploadVideo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.Calendar;

/**
 * Created by alexh on 3/14/2018.
 *
 * @author Alex Hooper, Daylond Hooper
 */
public class UIForm {
    // Classes
    private CSFileHandler csfh = new CSFileHandler();
    private StreamManager stream = new StreamManager();
    private LiturgicalCalendar _liturgicalCalendar;
    private ServiceCalculator _serviceCalculator = new ServiceCalculator();
    private UploadVideo uploadVideo = new UploadVideo();

    // Files
    private File uiPrefFile = new File(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt");
    private File streamPrivFile = new File(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\StreamPrivacy.txt");

    // Objects
    private Object publicStream = "Public";
    private Object privateStream = "Private";


    // Colors
    private Color _defaultButtonColor;

    // Strings
    private String privacy;
    private String title;
    public String content = csfh.fileToString(uiPrefFile.getPath());

    // Booleans
    private boolean isDark;
    private boolean _isStreaming;
    private boolean _isRecording;
    private boolean canStream = true; // TODO: 9/29/2018 Make boolean useful.

    // UI Components
    private JButton startStreamingButton;
    private JPanel panel1;
    private JTextField titleField;
    private JButton TOUButton;
    private JButton SetupGuide;
    private JComboBox privacyComboBox;
    private JCheckBox darkThemeCheckBox;
    private JLabel label2;
    private JLabel label1;
    private JLabel label3;
    private JButton StartRecordingButton;
    private JCheckBox UploadToYTButton;


    public UIForm(LiturgicalCalendar lc) {
        _liturgicalCalendar = lc;
        _defaultButtonColor = startStreamingButton.getBackground();
        titleField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                title = titleField.getText();
            }

            public void removeUpdate(DocumentEvent e) {
                title = titleField.getText();
            }

            public void insertUpdate(DocumentEvent e) {
                title = titleField.getText();
            }

            ;
        });

        /**
         * ACTION LISTENERS
         */

        startStreamingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (_isStreaming) {
                    System.out.println("Stopping Stream...");
                    startStreamingButton.setText("StartStreaming Streaming");
                    startStreamingButton.setBackground(_defaultButtonColor);
                    stream.StopStreaming();
                    System.exit(0);
                } else {
                    System.out.println("Starting stream...");
                    startStreamingButton.setText("StopStreaming Streaming");
                    startStreamingButton.setBackground(Color.red);
                    privacy = String.valueOf(privacyComboBox.getSelectedItem());
                    if (privacy.equals("Public")) {                                 //U.D.S.P.
                        stream.init(title, true);
                        stream.StartStreaming();
                    } else {
                        stream.init(title, false);
                        stream.StartStreaming();
                    }

                }
                _isStreaming = !_isStreaming;
            }
        });
        TOUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI oURL = new URI("https://alexhooper.github.io/termsofuse.html");
                    desktop.browse(oURL);

                } catch (Exception e1) {
                    System.out.println("Error.");

                }
            }
        });
        SetupGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI oURL = new URI("https://alexhooper.github.io/streamerguide.html");
                    desktop.browse(oURL);
                } catch (Exception e1) {
                    System.out.println("Error.");
                }
            }
        });

        darkThemeCheckBox.addActionListener(new ActionListener() {  //Makes themes determined by the user.
            @Override
            public void actionPerformed(ActionEvent e) {
                isDark = !isDark;
                if (isDark) {
                    applyThemes();
                    darkThemeCheckBox.setSelected(true);
                    String darktheme = "true";
                    csfh.writeToFile(uiPrefFile, darktheme);

                }

                if (!isDark) {
                    applyThemes();
                    darkThemeCheckBox.setSelected(false);
                    String darktheme = "false";
                    csfh.writeToFile(uiPrefFile, darktheme);
                }
            }
        });
        privacyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (privacyComboBox.getSelectedItem().equals(privateStream)) {
                    csfh.writeToFile(streamPrivFile, "Private");
                }
                if (privacyComboBox.getSelectedItem().equals(publicStream)) {
                    csfh.writeToFile(streamPrivFile, "Public");
                }
            }
        });
        StartRecordingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (_isRecording) {
                    System.out.println("Stopping Recording...");
                    StartRecordingButton.setText("Start Recording");
                    StartRecordingButton.setBackground(_defaultButtonColor);
                    stream.StopRecording();
                    // TODO: 10/3/2018 Upload Video. 
                    System.exit(0);
                } else {
                    System.out.println("Starting Recording...");
                    StartRecordingButton.setText("Recording");
                    StartRecordingButton.setBackground(Color.red);
                    stream.StartRecording();
                    // TODO: 9/25/2018 Add a way to upload to youtube once complete..... Make FileHandler Take care of it.


                }
                _isRecording = !_isRecording;
            }

        });
    }


    /**
     * FUNCTIONS
     */

    public void Show() {   //Makes all components show, and runs init method.

        JFrame frame = new JFrame();
        panel1.setBounds(700, 700, 700, 700);
        frame.setBounds(panel1.getBounds());
        frame.add(panel1);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        init();

    }

    public void init() {
        csfh.checkForFile(streamPrivFile, true);

        csfh.checkForFile(uiPrefFile, true);

        BuildTitle();
        recallComboboxPrefrences();
        privacyComboBox.addItem(privateStream);
        privacyComboBox.addItem(publicStream);


        panel1.setOpaque(true);
        stringToBool();
        applyThemes();
        ping();

    }


    private void applyThemes() { //Applies the theme corresponding with the boolean isDark.
        if (isDark) {
            panel1.setOpaque(true);
            label1.setOpaque(true);
            label2.setOpaque(true);
            label3.setOpaque(true);
            TOUButton.setOpaque(true);
            SetupGuide.setOpaque(true);
            privacyComboBox.setOpaque(true);

            panel1.setBackground(Color.DARK_GRAY);
            label1.setBackground(Color.gray);
            label2.setBackground(Color.gray);
            label3.setBackground(Color.gray);
            TOUButton.setBackground(Color.gray);
            SetupGuide.setBackground(Color.gray);
            privacyComboBox.setBackground(Color.gray);
            StartRecordingButton.setBackground(Color.gray);
            UploadToYTButton.setBackground(Color.gray);
            darkThemeCheckBox.setBackground(Color.gray);
            titleField.setBackground(Color.gray);
            startStreamingButton.setBackground(Color.gray);
            System.out.println("Dark theme on");
            System.out.println(content);
            darkThemeCheckBox.setSelected(true);

        }
        if (!isDark) {
            panel1.setOpaque(false);
            label1.setOpaque(false);
            label2.setOpaque(false);
            label3.setOpaque(false);
            TOUButton.setOpaque(false);
            SetupGuide.setOpaque(false);
            privacyComboBox.setOpaque(false);


            panel1.setBackground(Color.WHITE);
            label1.setBackground(Color.white);
            label2.setBackground(Color.white);
            label3.setBackground(Color.white);
            TOUButton.setBackground(Color.white);
            SetupGuide.setBackground(Color.white);
            privacyComboBox.setBackground(Color.white);
            StartRecordingButton.setBackground(Color.white);
            UploadToYTButton.setBackground(Color.white);
            darkThemeCheckBox.setBackground(Color.white);
            titleField.setBackground(Color.white);
            startStreamingButton.setBackground(Color.DARK_GRAY);
            System.out.println(content);
            System.out.println("Dark theme off");
            darkThemeCheckBox.setSelected(false);
        }
    }

    private void stringToBool() { //Take the String from uiPrefFile and turn it into a boolean.

        if (content.contains("true")) {
            isDark = true;

        }
        if (content.contains("false")) {
            isDark = false;

        }
    }

    private void BuildTitle() {         //Places calendar data into JTextField
        String dateName = _liturgicalCalendar.LookupByDayInYear(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        String service = _serviceCalculator.CalculateService(); // build a class to create or look this up
        title = dateName + " - " + service;
        titleField.setText(title);
    }

    private void recallComboboxPrefrences() {
        if (csfh.fileToString(streamPrivFile).contains("Private")) {
            System.out.println("Private Stream");
            privacyComboBox.getModel().setSelectedItem(privateStream);

        }
        if (csfh.fileToString(streamPrivFile).contains("Public")) {
            System.out.println("Public stream");
            privacyComboBox.getModel().setSelectedItem(publicStream);

        }
    }

    private void ping() {

        long currentTime = System.currentTimeMillis();
        boolean isPinged = false; // 2 seconds
        try {
            isPinged = InetAddress.getByName("www.youtube.com").isReachable(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentTime = System.currentTimeMillis() - currentTime;
        if (isPinged) {
            System.out.println("Pinged successfully in " + currentTime + "milliseconds");
            if (currentTime > 200) {
                canStream = false;
            }

        } else {
            System.out.println("Ping failed.");
        }

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(14, 7, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setForeground(new Color(-1));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        startStreamingButton = new JButton();
        startStreamingButton.setBackground(new Color(-12566207));
        startStreamingButton.setEnabled(true);
        startStreamingButton.setForeground(new Color(-4476903));
        startStreamingButton.setText("Start Streaming");
        panel1.add(startStreamingButton, new GridConstraints(11, 1, 3, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleField = new JTextField();
        titleField.setHorizontalAlignment(0);
        panel1.add(titleField, new GridConstraints(1, 1, 1, 6, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Enter Stream Title:");
        label1.setVerticalAlignment(3);
        label1.setVerticalTextPosition(3);
        panel1.add(label1, new GridConstraints(0, 1, 1, 6, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label2 = new JLabel();
        label2.setText("If you have any issues with this software, please contact:");
        panel1.add(label2, new GridConstraints(2, 1, 1, 6, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label3 = new JLabel();
        label3.setHorizontalTextPosition(0);
        label3.setText("Alex Hooper, (937) 929-0939 or Daylond Hooper, (937) 270-9432");
        panel1.add(label3, new GridConstraints(3, 1, 1, 6, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(3, 0, 6, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        TOUButton = new JButton();
        TOUButton.setText("Terms Of Use");
        panel1.add(TOUButton, new GridConstraints(8, 1, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SetupGuide = new JButton();
        SetupGuide.setText("Setup Guide");
        panel1.add(SetupGuide, new GridConstraints(6, 1, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(4, 1, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        privacyComboBox = new JComboBox();
        privacyComboBox.setEditable(false);
        panel1.add(privacyComboBox, new GridConstraints(7, 1, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        darkThemeCheckBox = new JCheckBox();
        darkThemeCheckBox.setSelected(false);
        darkThemeCheckBox.setText("Dark Theme");
        panel1.add(darkThemeCheckBox, new GridConstraints(5, 1, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel1.add(spacer6, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        StartRecordingButton = new JButton();
        StartRecordingButton.setText("Start Recording");
        panel1.add(StartRecordingButton, new GridConstraints(9, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(0, -1), null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel1.add(spacer7, new GridConstraints(9, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        UploadToYTButton = new JCheckBox();
        UploadToYTButton.setText("Upload To Youtube Once Complete");
        panel1.add(UploadToYTButton, new GridConstraints(9, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Make sure that OBS is set to record in FLV format!");
        panel1.add(label4, new GridConstraints(10, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
