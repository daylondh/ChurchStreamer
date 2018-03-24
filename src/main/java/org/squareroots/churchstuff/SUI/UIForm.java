package org.squareroots.churchstuff.SUI;

import javafx.application.Application;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.streamer.ObsHandler;
import org.squareroots.churchstuff.streamer.StreamManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton optionsButton;
    private boolean _isStreaming;
    private LiturgicalCalendar _liturgicalCalendar;
    StreamManager stream = new StreamManager();
    private String title;

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
                    Application.e

                } else {
                    System.out.println("Starting stream...");
                    startStreamingButton.setText("Stop Streaming");
                    startStreamingButton.setBackground(Color.red);
                    stream.init(title, false);
                    stream.Start();
                }
                _isStreaming = !_isStreaming;
            }
        });
    }

    public void Show()
    {
        JFrame frame = new JFrame();
        panel1.setBounds(700, 700, 700, 700);
        frame.setBounds(panel1.getBounds());
        frame.add(panel1);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        BuildTitle();
    }

    private void BuildTitle() {
        String dateName = _liturgicalCalendar.LookupByDayInYear(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        String service = " [Service Not yet Determined]"; // build a class to create or look this up
        title = dateName + service;
        titleField.setText(title);
    }
}
