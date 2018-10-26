package org.squareroots.churchstuff.SUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.squareroots.churchstuff.Misc.CSFileHandler;
import org.squareroots.churchstuff.Misc.CSLogger;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.calendar.ServiceCalculator;
import org.squareroots.churchstuff.streamer.ObsHandler;
import org.squareroots.churchstuff.streamer.StreamManager;
import org.squareroots.churchstuff.youtube.data.UploadVideo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.Calendar;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

/**
 * Created by alexh on 3/14/2018.
 *
 * @author Alex Hooper, Daylond Hooper
 */
public class UIForm {
    String OAuthDirectory = System.getProperty("user.home") + "/" + ".oauth-credentials";
    // Classes
    private CSFileHandler csfh = new CSFileHandler();
    private StreamManager stream = new StreamManager();
    private LiturgicalCalendar _liturgicalCalendar;
    private ServiceCalculator _serviceCalculator = new ServiceCalculator();
    private UploadVideo uploadVideo = new UploadVideo();
    ObsHandler obshandler = new ObsHandler();

    // Files
    String path = System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\logs.txt";
    File file = new File(path);
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
    private boolean uploadWhenComplete = false;

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
    private JCheckBox UploadToYTCheckBox;
    private JButton openLogsButton;
    private JLabel noticeLabel;


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
                    CSLogger.logData("Stopping Stream...");
                    System.out.println("Stopping Stream...");
                    startStreamingButton.setText("StartStreaming Streaming");
                    startStreamingButton.setBackground(_defaultButtonColor);
                    stream.StopStreaming();
                    System.exit(0);
                } else {
                    CSLogger.logData("Starting stream...");
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
                    CSLogger.logData("Error opening webpage.");
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
                    CSLogger.logData("Error connecting to https://alexhooper.github.io/streamerguide.html.");
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
                    CSLogger.logData("Stopping recording....");
                    System.out.println("Stopping Recording...");
                    StartRecordingButton.setText("Start Recording");
                    StartRecordingButton.setBackground(_defaultButtonColor);
                    stream.StopRecording();
                    CSLogger.logData("Recording stopped.");
                    System.out.println("Recording stopped");
                    if (uploadWhenComplete) {
                        String directory = csfh.getNewFilePath(System.getProperty("user.home") + "\\Videos");
                        UploadVideo.go(directory, title, privacy);
                    }


                    System.exit(0);
                } else {
                    CSLogger.logData("Starting recording...");
                    System.out.println("Starting Recording...");
                    StartRecordingButton.setText("Recording");
                    StartRecordingButton.setBackground(Color.red);
                    stream.StartRecording();
                    csfh.scanDirectory(System.getProperty("user.home") + "\\Videos");
                    CSLogger.logData("Recording started.");
                    System.out.println("Recording started.");


                }
                _isRecording = !_isRecording;
            }

        });
        openLogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        UploadToYTCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadWhenComplete = UploadToYTCheckBox.isSelected();
            }
        });

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Yes", "No"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to exit?", "Online Examination System",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        ObjButtons, ObjButtons[1]);
                if (PromptResult == 0) {
                    System.exit(0);
                }
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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Exit", "Cancel"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit? If you are recording, streaming, or uploading, there might be errors.", "Confirm exit", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    if (_isRecording || _isStreaming) {
                    obshandler.StopRecording();
                    }
                    System.exit(0);
                }
            }
        });
        frame.setLocationRelativeTo(null);

        init();

    }

    public void init() {
        csfh.checkForFile(streamPrivFile, true);

        csfh.checkForFile(uiPrefFile, true);

        BuildTitle();
        recallComboboxPrefrences();
        privacyComboBox.addItem(privateStream);
        privacyComboBox.addItem(publicStream);

        UploadToYTCheckBox.setSelected(true);

        panel1.setOpaque(true);
        stringToBool();
        applyThemes();
        ping();

    }


    private void applyThemes() { //Applies the theme corresponding with the boolean isDark.
        // TODO: 10/23/2018 Change color of log button and mke main buttons more prominent.
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
            StartRecordingButton.setBackground(Color.blue);
            UploadToYTCheckBox.setBackground(Color.gray);
            darkThemeCheckBox.setBackground(Color.gray);
            titleField.setBackground(Color.blue);
            startStreamingButton.setBackground(Color.blue);
            openLogsButton.setBackground(Color.gray);
            noticeLabel.setBackground(Color.gray);
            System.out.println("Dark theme on");
            CSLogger.logData("Applied dark theme.");
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
            StartRecordingButton.setBackground(Color.blue);
            UploadToYTCheckBox.setBackground(Color.white);
            darkThemeCheckBox.setBackground(Color.white);
            titleField.setBackground(Color.blue);
            startStreamingButton.setBackground(Color.blue);
            openLogsButton.setBackground(Color.white);
            noticeLabel.setBackground(Color.gray);
            System.out.println(content);
            CSLogger.logData("Applied light theme.");
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
            System.out.println("Pinged successfully in " + currentTime + " milliseconds");
            CSLogger.logData("Pinged successfully in " + currentTime + " milliseconds");
            if (currentTime > 200) {
                canStream = false;
            }

        } else {
            CSLogger.logData("Could not ping www.youtube.com");
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
        panel1.setLayout(new GridLayoutManager(18, 7, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setForeground(new Color(-1));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(17, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        startStreamingButton = new JButton();
        startStreamingButton.setBackground(new Color(-12566207));
        startStreamingButton.setEnabled(true);
        Font startStreamingButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, startStreamingButton.getFont());
        if (startStreamingButtonFont != null) startStreamingButton.setFont(startStreamingButtonFont);
        startStreamingButton.setForeground(new Color(-1));
        startStreamingButton.setText("Start Streaming");
        panel1.add(startStreamingButton, new GridConstraints(15, 1, 3, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleField = new JTextField();
        titleField.setForeground(new Color(-1));
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
        label2.setText("If you have any problems with this software, please contact:");
        panel1.add(label2, new GridConstraints(2, 1, 1, 6, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label3 = new JLabel();
        label3.setHorizontalTextPosition(0);
        label3.setText("Alex Hooper, (937) 929-0939 or Daylond Hooper, (937) 270-9432");
        panel1.add(label3, new GridConstraints(3, 1, 1, 6, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(3, 0, 10, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        SetupGuide = new JButton();
        SetupGuide.setText("Setup Guide");
        panel1.add(SetupGuide, new GridConstraints(9, 1, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(4, 1, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        darkThemeCheckBox = new JCheckBox();
        darkThemeCheckBox.setSelected(false);
        darkThemeCheckBox.setText("Dark Theme");
        panel1.add(darkThemeCheckBox, new GridConstraints(5, 1, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel1.add(spacer6, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        StartRecordingButton = new JButton();
        StartRecordingButton.setForeground(new Color(-1));
        StartRecordingButton.setHideActionText(true);
        StartRecordingButton.setText("Start Recording");
        panel1.add(StartRecordingButton, new GridConstraints(13, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(0, -1), null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel1.add(spacer7, new GridConstraints(13, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        UploadToYTCheckBox = new JCheckBox();
        UploadToYTCheckBox.setText("Upload To Youtube Once Complete");
        panel1.add(UploadToYTCheckBox, new GridConstraints(13, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noticeLabel = new JLabel();
        noticeLabel.setForeground(new Color(-65515));
        noticeLabel.setText("Make sure that OBS is set to record in FLV format!");
        panel1.add(noticeLabel, new GridConstraints(14, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openLogsButton = new JButton();
        openLogsButton.setText("Open Logs");
        panel1.add(openLogsButton, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        TOUButton = new JButton();
        TOUButton.setText("Terms Of Use");
        panel1.add(TOUButton, new GridConstraints(8, 1, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        privacyComboBox = new JComboBox();
        privacyComboBox.setEditable(false);
        panel1.add(privacyComboBox, new GridConstraints(10, 1, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
