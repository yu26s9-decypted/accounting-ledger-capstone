package com.pluralsight.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;

import java.awt.*;

public class AppGUI {
    public static void main(String[] arg){
        JLabel label = new JLabel();
        JFrame frame = new JFrame();
        JTextField textField = new JTextField();

        frame.setTitle("Stash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label.setText("Everything you need to do with business money. Banking for ambitious companies.");
        textField.setText("opentalententerprise001");
        label.setFont(new Font("Arial", Font.PLAIN,16));


        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.add(textField);
        frame.setSize(800, 500);
        frame.setVisible(true);

    }
}
