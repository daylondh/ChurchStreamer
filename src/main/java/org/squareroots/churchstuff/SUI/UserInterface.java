package org.squareroots.churchstuff.SUI;

import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.streamer.ObsHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserInterface extends JPanel implements ActionListener  {
    LiturgicalCalendar lc;
    String title = "BLC ~/~/~ The ~ day of ~."; // TODO: 3/11/2018 RENAME THE STREAM HERE!!!
    boolean isStreaming = false;
    JButton startStream = new JButton("Start Streaming!");
    JButton advanced = new JButton("Advanced...");





    public void createAndShowGui() { //JPanel
        GregorianCalendar gc = new GregorianCalendar();
        int year = gc.get(GregorianCalendar.YEAR);
        System.out.println(year);
        lc = new LiturgicalCalendar(year);
        title = lc.LookupByDayInYear(gc.get(Calendar.DAY_OF_YEAR));
        JPanel p = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(title);
        JFrame f = new JFrame("Stream");

        startStream.addActionListener(this::actionPerformed1);
        startStream.setSize(100,100);
        startStream.setBackground(Color.green);
        startStream.setForeground(Color.blue);
        advanced.addActionListener(this::actionPerformed);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;

        p.add(label);


        p.setVisible(true);

        c.gridx = 0;
        c.gridy = 1;

        p.add(startStream,c);

        c.gridx = 0;
        c.gridy = 2;
        p.add(advanced,c);

        p.setVisible(true);
        p.setBounds(100,100,1500,2000);


        f.setBounds(500,500,500,500);
        f.add(p);
        f.setLocationRelativeTo(null);
        f.setVisible(true);








    }


    public void actionPerformed1(ActionEvent event) {

        ObsHandler stream = new ObsHandler();
        if (isStreaming) {
            System.out.println("Stopping Stream...");
            startStream.setText("Start Streaming");
            startStream.setBackground(Color.green);
            stream.stop();


        } else {
            System.out.println("Starting stream...");
            startStream.setText("Stop Streaming");
            startStream.setBackground(Color.red);

            stream.start();
        }
        isStreaming = !isStreaming;

        System.out.println("Streaming = " + isStreaming);
    }
    public void actionPerformed(ActionEvent event) {
        JFrame options = new JFrame("options");
        JButton name = new JButton("Name...");
        JButton publicity = new JButton("Publicity...");
        JButton other = new JButton("Other...");
        JPanel optionLayout = new JPanel(new GridBagLayout());


        options.setVisible(true);
        options.setBounds(400,400,400,400);
        options.setLocationRelativeTo(null);
        options.add(optionLayout);
        optionLayout.add(name);
        optionLayout.add(publicity);
        optionLayout.add(other);

    }
}
