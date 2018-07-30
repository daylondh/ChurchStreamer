package org.squareroots.churchstuff.SUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.calendar.ServiceCalculator;
import org.squareroots.churchstuff.streamer.StreamManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.stream.Stream;

/**
 * Created by alexh on 3/14/2018.
 */
public class UIForm {
    private Color _defaultButtonColor;
    private JButton startStreamingButton;
    private JPanel panel1;
    private JTextField titleField;
    private JButton TOUButton;
    private JButton SetupGuide;
    private JComboBox comboBox1;
    private JCheckBox darkThemeCheckBox;
    private JLabel label2;
    private JLabel label1;
    private JLabel label3;

    private boolean _isStreaming;
    private LiturgicalCalendar _liturgicalCalendar;
    private ServiceCalculator _serviceCalculator = new ServiceCalculator();
    StreamManager stream = new StreamManager();
    private String title;
    private File file = new File(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt");

    private static String content(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private boolean isDark;


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
        startStreamingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (_isStreaming) {
                    System.out.println("Stopping Stream...");
                    startStreamingButton.setText("Start Streaming");
                    startStreamingButton.setBackground(_defaultButtonColor);
                    stream.Stop();
                    System.exit(0);
                } else {
                    System.out.println("Starting stream...");
                    startStreamingButton.setText("Stop Streaming");
                    startStreamingButton.setBackground(Color.red);
                    String privacy = String.valueOf(comboBox1.getSelectedItem());
                    if (privacy.equals("Public")) {                                 //U.D.S.P.
                        stream.init(title, true);
                        stream.Start();
                    } else {
                        stream.init(title, false);
                        stream.Start();
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

        darkThemeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDark = !isDark;
                if (isDark) {
                    panel1.setOpaque(true);
                    label1.setOpaque(true);
                    label2.setOpaque(true);
                    label3.setOpaque(true);
                    TOUButton.setOpaque(true);
                    SetupGuide.setOpaque(true);
                    comboBox1.setOpaque(true);

                    panel1.setBackground(Color.DARK_GRAY);
                    label1.setBackground(Color.white);
                    label2.setBackground(Color.white);
                    label3.setBackground(Color.white);
                    TOUButton.setBackground(Color.white);
                    SetupGuide.setBackground(Color.white);
                    comboBox1.setBackground(Color.white);
                    System.out.println("Dark theme on");
                    darkThemeCheckBox.setSelected(true);
                    try {
                        String darktheme = "true";
                        FileWriter fw = new FileWriter(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt");
                        fw.write(darktheme);
                        fw.close();

                    } catch (IOException e1) {
                        System.out.println("Error printing preference");
                    }

                }
                if (!isDark) {
                    panel1.setOpaque(false);
                    label1.setOpaque(false);
                    label2.setOpaque(false);
                    label3.setOpaque(false);
                    TOUButton.setOpaque(false);
                    SetupGuide.setOpaque(false);
                    comboBox1.setOpaque(false);
                    System.out.println("Dark theme off");

                    panel1.setBackground(Color.WHITE);
                    label1.setBackground(Color.white);
                    label2.setBackground(Color.white);
                    label3.setBackground(Color.white);
                    TOUButton.setBackground(Color.white);
                    SetupGuide.setBackground(Color.white);
                    comboBox1.setBackground(Color.white);
                    darkThemeCheckBox.setSelected(false);
                    try {
                        String darktheme = "false";
                        FileWriter fw = new FileWriter(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt");
                        fw.write(darktheme);
                        fw.close();

                    } catch (IOException e1) {
                        System.out.println("Error printing preference");
                    }

                }
            }
        });
    }

    public void Show() {
        JFrame frame = new JFrame();
        panel1.setBounds(700, 700, 700, 700);
        frame.setBounds(panel1.getBounds());
        frame.add(panel1);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        BuildTitle();
        comboBox1.addItem("Public");
        comboBox1.addItem("Private");
        checkForFile();
        panel1.setOpaque(true);
        stringToBool();
        applyThemes();


    }

    private void checkForFile() {
       boolean exists = file.exists();
        System.out.println(exists);

        if (!exists) {

            boolean success = new File(System.getProperty("user.home"), "\\ChurchStreamer\\preferences").mkdirs();
            System.out.println("File built.");
            System.out.println(success);
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt", "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            writer.close();
        } else {
            return;
        }


    }

    private void applyThemes() {
        if (isDark) {
            panel1.setOpaque(true);
            label1.setOpaque(true);
            label2.setOpaque(true);
            label3.setOpaque(true);
            TOUButton.setOpaque(true);
            SetupGuide.setOpaque(true);
            comboBox1.setOpaque(true);

            panel1.setBackground(Color.DARK_GRAY);
            label1.setBackground(Color.white);
            label2.setBackground(Color.white);
            label3.setBackground(Color.white);
            TOUButton.setBackground(Color.white);
            SetupGuide.setBackground(Color.white);
            comboBox1.setBackground(Color.white);
            System.out.println("Dark theme on");
            System.out.println(content(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt"));
            darkThemeCheckBox.setSelected(true);

        }
        if (!isDark) {
            panel1.setOpaque(false);
            label1.setOpaque(false);
            label2.setOpaque(false);
            label3.setOpaque(false);
            TOUButton.setOpaque(false);
            SetupGuide.setOpaque(false);
            comboBox1.setOpaque(false);
            System.out.println("Dark theme off");

            panel1.setBackground(Color.WHITE);
            label1.setBackground(Color.white);
            label2.setBackground(Color.white);
            label3.setBackground(Color.white);
            TOUButton.setBackground(Color.white);
            SetupGuide.setBackground(Color.white);
            comboBox1.setBackground(Color.white);
            System.out.println(content(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt"));
            darkThemeCheckBox.setSelected(false);
        }
    }

    private void stringToBool() {

        if (content(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt").contains("true")) {
            isDark = true;

        }
        if (content(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt").contains("false")) {
            isDark = false;

        }
    }

    private void BuildTitle() {
        String dateName = _liturgicalCalendar.LookupByDayInYear(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        String service = _serviceCalculator.CalculateService(); // build a class to create or look this up
        title = dateName + " - " + service;
        titleField.setText(title);
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
        panel1.setLayout(new GridLayoutManager(12, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setForeground(new Color(-1));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        startStreamingButton = new JButton();
        startStreamingButton.setBackground(new Color(-12566207));
        startStreamingButton.setEnabled(true);
        startStreamingButton.setForeground(new Color(-4476903));
        startStreamingButton.setText("Start Streaming");
        panel1.add(startStreamingButton, new GridConstraints(9, 1, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleField = new JTextField();
        titleField.setHorizontalAlignment(0);
        panel1.add(titleField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Enter Stream Title:");
        label1.setVerticalAlignment(3);
        label1.setVerticalTextPosition(3);
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label2 = new JLabel();
        label2.setText("If you have any issues with this software, please contact:");
        panel1.add(label2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label3 = new JLabel();
        label3.setHorizontalTextPosition(0);
        label3.setText("Alex Hooper, (937) 929-0939 or Daylond Hooper, (937) 270-9432");
        panel1.add(label3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(3, 0, 6, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        TOUButton = new JButton();
        TOUButton.setText("Terms Of Use");
        panel1.add(TOUButton, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SetupGuide = new JButton();
        SetupGuide.setText("Setup Guide");
        panel1.add(SetupGuide, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        comboBox1.setEditable(false);
        panel1.add(comboBox1, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        darkThemeCheckBox = new JCheckBox();
        darkThemeCheckBox.setSelected(false);
        darkThemeCheckBox.setText("Dark Theme");
        panel1.add(darkThemeCheckBox, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
