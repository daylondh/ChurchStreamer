package org.squareroots.churchstuff.SUI;

import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.streamer.ObsHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

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

    public UIForm(LiturgicalCalendar lc) {
        _liturgicalCalendar = lc;
        _defaultButtonColor = startStreamingButton.getBackground();
        startStreamingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObsHandler stream = new ObsHandler();
                if (_isStreaming) {
                    System.out.println("Stopping Stream...");
                    startStreamingButton.setText("Start Streaming");
                    startStreamingButton.setBackground(_defaultButtonColor);
                    stream.stop();


                } else {
                    System.out.println("Starting stream...");
                    startStreamingButton.setText("Stop Streaming");
                    startStreamingButton.setBackground(Color.red);

                    stream.start();
                }
                _isStreaming = !_isStreaming;
            }
        });


        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             OptionsForm of = new OptionsForm();
                of.show();
                String selection = of.objectSelected;

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
        String service = " Idunnoyet..."; // build a class to create or look this up
        String title = dateName + service;
        titleField.setText(title);
    }
}
