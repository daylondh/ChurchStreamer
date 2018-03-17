package org.squareroots.churchstuff.SUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexh on 3/15/2018.
 */
public class OptionsForm {
    private JPanel panel1;
    private JButton modesButton;
    private JButton titleButton;
    private JButton OKButton;
    private JComboBox comboBox1;
    private JFrame frame = new JFrame();
    String objectSelected = String.valueOf(comboBox1.getSelectedItem());


    public OptionsForm() {

        comboBox1.setSelectedItem(objectSelected);


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);

            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                objectSelected = String.valueOf(comboBox1.getSelectedItem());
                System.out.println(objectSelected);
            }
        });

    }

    public void show() {

        panel1.setBounds(500, 500, 500, 500);
        frame.setBounds(panel1.getBounds());
        frame.add(panel1);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);



    }

}
