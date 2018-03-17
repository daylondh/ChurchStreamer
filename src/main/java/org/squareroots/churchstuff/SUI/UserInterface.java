package org.squareroots.churchstuff.SUI;

import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.streamer.ObsHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;

public class UserInterface extends JPanel {
    LiturgicalCalendar _liturgicalCalendar;
    String _title = "BLC ~/~/~ The ~ day of ~.";
    boolean _isStreaming = false;
    JButton _buttonStartStream = new JButton("Start Streaming!");
    JButton _buttonAdvanced = new JButton("Advanced...");
    JButton _buttonName = new JButton("Name...");


    public UserInterface(LiturgicalCalendar lc) {
        _liturgicalCalendar = lc;
    }

    public void createAndShowGui() {

        BuildTitle();
        JPanel p = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(_title);
        JFrame f = new JFrame("Stream");

        _buttonStartStream.addActionListener(this::startStreamButtonClicked);
        _buttonStartStream.setSize(100, 100);
        _buttonStartStream.setBackground(Color.green);
        _buttonStartStream.setForeground(Color.blue);
        _buttonAdvanced.addActionListener(this::advancedButtonClicked);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;

        p.add(label);


        p.setVisible(true);

        c.gridx = 0;
        c.gridy = 1;

        p.add(_buttonStartStream, c);

        c.gridx = 0;
        c.gridy = 2;
        //p.add(_buttonAdvanced, c);

        p.setVisible(true);
        p.setBounds(100, 100, 1500, 2000);


        f.setBounds(500, 500, 500, 500);
        f.add(p);
        f.setLocationRelativeTo(null);
        f.setVisible(true);


    }

    private void BuildTitle() {
        String dateName = _liturgicalCalendar.LookupByDayInYear(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        String service = " Idunnoyet..."; // build a class to create or look this up
        _title = dateName + service;
    }

    public void startStreamButtonClicked(ActionEvent event) {

        ObsHandler stream = new ObsHandler();
        if (_isStreaming) {
            System.out.println("Stopping Stream...");
            _buttonStartStream.setText("Start Streaming");
            _buttonStartStream.setBackground(Color.green);
            stream.stop();


        } else {
            System.out.println("Starting stream...");
            _buttonStartStream.setText("Stop Streaming");
            _buttonStartStream.setBackground(Color.red);

            stream.start();
        }
        _isStreaming = !_isStreaming;

        System.out.println("Streaming = " + _isStreaming);
    }

    public void advancedButtonClicked(ActionEvent event) {
        JFrame options = new JFrame("options");
        JButton publicity = new JButton("Publicity...");
        JButton other = new JButton("Other...");
        JPanel optionLayout = new JPanel(new GridBagLayout());
        options.setVisible(true);
        options.setBounds(400, 400, 400, 400);
        options.setLocationRelativeTo(null);
        options.add(optionLayout);
        optionLayout.add(_buttonName);
        optionLayout.add(publicity);
        optionLayout.add(other);


    }


}
