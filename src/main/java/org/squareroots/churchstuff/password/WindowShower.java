package org.squareroots.churchstuff.password;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




/**
 * Created by alexh on 3/11/2018.
 */
public class WindowShower {

    final JFrame frame = new JFrame("Login.");
    final JButton btnLogin = new JButton("Click to login");



    public WindowShower() {



        btnLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        MyDialog loginDlg = new MyDialog(frame);
                        loginDlg.setVisible(true);
                        // if logon successfully
                        if (loginDlg.isSucceeded()) {
                            btnLogin.setText("Hi " + loginDlg.getUsername() + "!");
                        }
                    }
                });


        frame.setSize(500, 250);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);





    }
}




