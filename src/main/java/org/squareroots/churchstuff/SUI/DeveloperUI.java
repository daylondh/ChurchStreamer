package org.squareroots.churchstuff.SUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.squareroots.churchstuff.Misc.FileHandler;
import org.squareroots.churchstuff.Misc.Preferences;
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
import java.util.Calendar;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

/**
 * Created by alexh on 3/14/2018.
 *
 * @author Alex Hooper, Daylond Hooper
 */
public class DeveloperUI {
    final String OAuthDirectory = System.getProperty("user.home") + "/" + ".oauth-credentials";

    private FileHandler csfh = new FileHandler();
    private StreamManager stream = new StreamManager();
    private LiturgicalCalendar _liturgicalCalendar;
    private ServiceCalculator _serviceCalculator = new ServiceCalculator();
    private UploadVideo uploadVideo = new UploadVideo();
    ObsHandler obshandler = new ObsHandler();

    // Colors
    private Color _defaultButtonColor;

    // Strings
    private String privacy = "Public";
    private String title;

    // Booleans
    private boolean mayStream;
    private boolean isDark;
    private boolean _isStreaming;
    private boolean _isRecording;
    private boolean uploadWhenComplete = true;

    // UI Components
    private JButton startStreamingButton;
    private JPanel panel1;
    private JTextField titleField;
    private JComboBox privacyComboBox;
    private JCheckBox darkThemeCheckBox;
    private JLabel label2;
    private JLabel label1;
    private JLabel label3;
    private JButton StartRecordingButton;
    private JCheckBox UploadToYTCheckBox;
    private JButton openLogsButton;
    private JLabel noticeLabel;
    private JCheckBox StreamingEnabled;
    JFrame frame = new JFrame();

    private Object publicStream = "Public";
    private Object privateStream = "Private";


    public DeveloperUI(LiturgicalCalendar lc) {
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

        });

        /**
         * ACTION LISTENERS
         */

        startStreamingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (_isStreaming) {
                    System.out.println("Stopping Stream...");
                    startStreamingButton.setText("Click Here To Start Streaming");
                    startStreamingButton.setBackground(_defaultButtonColor);
                    stream.StopStreaming();
                    System.exit(0);
                } else {
                    System.out.println("Starting stream...");
                    startStreamingButton.setText("Click Here To Stop Streaming");
                    startStreamingButton.setBackground(Color.red);
                    privacy = String.valueOf(privacyComboBox.getSelectedItem()); // FIXME: 12/29/2018
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


        //Makes themes determined by the user.
        darkThemeCheckBox.addActionListener(e -> {
            isDark = !isDark;
            if (isDark) {
                applyThemes();
                darkThemeCheckBox.setSelected(true);
                String darktheme = "true";


            }

            if (!isDark) {
                applyThemes();
                darkThemeCheckBox.setSelected(false);
                String darktheme = "false";
                Preferences.setDarkTheme(false);
            }
        });

        privacyComboBox.addActionListener(e -> {

            if (privacyComboBox.getSelectedItem().equals(privateStream)) {
                Preferences.setPrivacy("Private");
            }
            if (privacyComboBox.getSelectedItem().equals(publicStream)) {
                Preferences.setPrivacy("Public");
            }
        });

        StartRecordingButton.addActionListener(e -> {

            if (_isRecording) {
                System.out.println("Stopping Recording...");
                StartRecordingButton.setText("Recording Complete. This Button Will Be Re-Enabled Once The Service Is Uploaded");
                StartRecordingButton.setBackground(_defaultButtonColor);
                StartRecordingButton.setEnabled(false);
                stream.StopRecording();
                System.out.println("Recording stopped");
                if (uploadWhenComplete) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    String directory = csfh.getNewFilePath(System.getProperty("user.home") + "\\Videos");
                    UploadVideo.go(directory, title, privacy);
                }
                StartRecordingButton.setText("Click To Start Recording");
                StartRecordingButton.setEnabled(true);


            } else {
                StartRecordingButton.setText("Click To Stop Recording");
                StartRecordingButton.setBackground(Color.red);
                stream.StartRecording();
                csfh.scanDirectory(System.getProperty("user.home") + "\\Videos");


            }
            _isRecording = !_isRecording;
        });

        openLogsButton.addActionListener(e -> {

        });

        UploadToYTCheckBox.addActionListener(e -> uploadWhenComplete = UploadToYTCheckBox.isSelected());

        StreamingEnabled.addActionListener(e -> {
            if (StreamingEnabled.isSelected()) {
                Preferences.setStreamEnabled(true);
            } else {
                Preferences.setStreamEnabled(false);
            }
        });

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                frame.setAlwaysOnTop(false);
                String ObjButtons[] = {"Yes", "No"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        ObjButtons, ObjButtons[1]);
                if (PromptResult == 0) {
                    System.exit(0);
                }
                else {
                    frame.setAlwaysOnTop(true);
                }
            }
        });
    }


    /**
     * METHODS
     */

    private boolean isFirstTime(boolean promptIfTrue) {
        File f = new File(System.getProperty("user.home") + "\\AppData\\Local\\ChurchStreamer");

        if (f.exists()) {
            return false;
        }

        if (promptIfTrue && !f.exists()) {
            String objButtons[] = {"Yes", "No."};
            int promptResult = JOptionPane.showOptionDialog(null, "Welcome to ChurchStreamer! In order for ChurchStreamer to work" +
                    "correctly, it must restart. Would you like to restart now?", "Welcome!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, objButtons, objButtons[1]);
            if (promptResult == JOptionPane.YES_OPTION) {

                System.exit(0);
            }
        }

        return true;
    }

    public void showDeveloper() {   //Makes all components show, and runs init method.

        JFrame frame = new JFrame();
        panel1.setBounds(700, 700, 700, 700);
        frame.setBounds(panel1.getBounds());
        frame.add(panel1);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                frame.setAlwaysOnTop(false);
                String ObjButtons[] = {"Exit", "Cancel"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit? If you are recording, streaming, or uploading, there might be errors.", "Confirm exit", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    if (_isRecording || _isStreaming) {
                        obshandler.stopRecording();
                    }
                    System.exit(0);
                }
                else {
                    frame.setAlwaysOnTop(true);
                }
            }
        });
        frame.setLocationRelativeTo(null);

        init();

    }

    public void showSimple() {

        frame.setAlwaysOnTop(true);
        panel1.setBounds(700, 700, 700, 700);
        frame.setBounds(panel1.getBounds());
        frame.add(panel1);
        frame.setVisible(true);
        openLogsButton.setVisible(false);
        darkThemeCheckBox.setVisible(false);
        privacyComboBox.setVisible(false);
        UploadToYTCheckBox.setVisible(false);
        noticeLabel.setVisible(false);
        StreamingEnabled.setVisible(false);


        startStreamingButton.setFont(new Font("Arial", Font.PLAIN, 40));
        StartRecordingButton.setFont(new Font("Arial", Font.PLAIN, 35));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                frame.setAlwaysOnTop(false);
                String ObjButtons[] = {"Exit", "Cancel"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit? If you are recording, streaming, or uploading, there might be errors.", "Confirm exit", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    if (_isRecording || _isStreaming) {
                        obshandler.stopRecording();
                    }
                    System.exit(0);
                }
                else {
                    frame.setAlwaysOnTop(true);
                }
            }
        });
        frame.setLocationRelativeTo(null);

        init();
    }


    private void init() {
        boolean isfirsttime = isFirstTime(false);
        getMayStream();


        BuildTitle();
        recallComboboxPrefrences();
        privacyComboBox.addItem(privateStream);
        privacyComboBox.addItem(publicStream);

        UploadToYTCheckBox.setSelected(true);

        panel1.setOpaque(true);
        stringToBool();
        applyThemes();
        if (isfirsttime) {
            frame.setAlwaysOnTop(false);
            String objButtons[] = {"Yes", "No."};
            int promptResult = JOptionPane.showOptionDialog(null, "Welcome to ChurchStreamer! In order for ChurchStreamer to work" +
                    " correctly, it must restart. Would you like to restart now?", "Welcome!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, objButtons, objButtons[1]);
            if (promptResult == JOptionPane.YES_OPTION) {

                System.exit(0);
            }


        }
        if (!mayStream) {
            startStreamingButton.setVisible(false);
        }

    }


    private void applyThemes() { //Applies the theme corresponding with the boolean isDark.
        if (isDark) {
            panel1.setOpaque(true);
            label1.setOpaque(true);
            label2.setOpaque(true);
            label3.setOpaque(true);
            privacyComboBox.setOpaque(true);

            panel1.setBackground(Color.darkGray);
            label1.setBackground(Color.darkGray);
            label1.setForeground(Color.gray);
            label1.setFont(new Font("Arial", Font.PLAIN, 25));

            label2.setBackground(Color.darkGray);
            label2.setForeground(Color.gray);
            label2.setFont(new Font("Arial", Font.PLAIN, 25));

            label3.setBackground(Color.darkGray);
            label3.setForeground(Color.gray);
            label3.setFont(new Font("Arial", Font.PLAIN, 25));
            privacyComboBox.setBackground(Color.gray);
            StartRecordingButton.setBackground(new Color(0, 0, 80));
            UploadToYTCheckBox.setBackground(Color.gray);
            darkThemeCheckBox.setBackground(Color.gray);
            titleField.setBackground(Color.gray);
            titleField.setForeground(Color.black);
            titleField.setFont(new Font("Arial", Font.PLAIN, 25));
            startStreamingButton.setBackground(new Color(0, 0, 80));
            openLogsButton.setBackground(Color.gray);
            noticeLabel.setBackground(Color.gray);
            darkThemeCheckBox.setSelected(true);

        }
        if (!isDark) {
            panel1.setOpaque(false);
            label1.setOpaque(false);
            label2.setOpaque(false);
            label3.setOpaque(false);
            privacyComboBox.setOpaque(false);


            panel1.setBackground(Color.WHITE);
            label1.setBackground(Color.white);
            label1.setFont(new Font("Arial", Font.PLAIN, 25));
            label2.setBackground(Color.white);
            label2.setFont(new Font("Arial", Font.PLAIN, 25));
            label3.setBackground(Color.white);
            label3.setFont(new Font("Arial", Font.PLAIN, 25));

            privacyComboBox.setBackground(Color.white);
            StartRecordingButton.setBackground(new Color(0, 0, 80));
            UploadToYTCheckBox.setBackground(Color.white);
            darkThemeCheckBox.setBackground(Color.white);
            titleField.setBackground(Color.gray);
            titleField.setFont(new Font("Arial", Font.PLAIN, 25));
            startStreamingButton.setBackground(new Color(0, 0, 80));
            openLogsButton.setBackground(Color.white);
            noticeLabel.setBackground(Color.gray);
            darkThemeCheckBox.setSelected(false);
        }
    }

    private void stringToBool() { //Take the String from uiPrefFile and turn it into a boolean.

        if (Preferences.getIsDarkTheme()) {
            isDark = true;

        }
        if (!Preferences.getIsDarkTheme()) {
            isDark = false;

        }
    }

    private void getMayStream() {
        if (Preferences.streamEnabled()) {
            mayStream = true;
        } else {
            StreamingEnabled.setSelected(this.mayStream);
        }
    }

    private void BuildTitle() {         //Places calendar data into JTextField
        String dateName = _liturgicalCalendar.LookupByDayInYear(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        String service = _serviceCalculator.CalculateService(); // build a class to create or look this up
        title = dateName + " - " + service;
        titleField.setText(title);
    }

    private void recallComboboxPrefrences() {
        if (Preferences.getPrivacy().equals("Private")) {
            privacyComboBox.getModel().setSelectedItem(privateStream);

        }
        if (Preferences.getPrivacy().equals("Private")) {
            privacyComboBox.getModel().setSelectedItem(publicStream);

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
        startStreamingButton.setSelected(false);
        startStreamingButton.setText("Click To Start Streaming");
        startStreamingButton.setVisible(true);
        panel1.add(startStreamingButton, new GridConstraints(15, 1, 3, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleField = new JTextField();
        titleField.setForeground(new Color(-1));
        titleField.setHorizontalAlignment(0);
        panel1.add(titleField, new GridConstraints(1, 1, 1, 6, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Enter Service Title:");
        label1.setVerticalAlignment(3);
        label1.setVerticalTextPosition(3);
        panel1.add(label1, new GridConstraints(0, 1, 1, 6, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label2 = new JLabel();
        label2.setText("If you have any problems, please contact:");
        panel1.add(label2, new GridConstraints(2, 1, 1, 6, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label3 = new JLabel();
        label3.setHorizontalTextPosition(0);
        label3.setText("Alex or Daylond Hooper, (937) 929-0939 or (937) 270-9432");
        panel1.add(label3, new GridConstraints(3, 1, 1, 6, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(3, 0, 8, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        StartRecordingButton.setText("Click To Start Recording");
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
        panel1.add(openLogsButton, new GridConstraints(6, 1, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        privacyComboBox = new JComboBox();
        privacyComboBox.setEditable(false);
        panel1.add(privacyComboBox, new GridConstraints(10, 1, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        StreamingEnabled = new JCheckBox();
        StreamingEnabled.setText("Enable Streaming");
        panel1.add(StreamingEnabled, new GridConstraints(12, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
