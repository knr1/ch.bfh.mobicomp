/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.timer.toggler;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

/**
 *
 * @author reto
 */
public class Toggler extends JFrame {

    private JToggleButton toggleButton;

    public Toggler(ActionListener l) {
        toggleButton = new JToggleButton();
        toggleButton.addActionListener(l);
        this.getContentPane().add(toggleButton);
        this.pack();
        this.setVisible(true);
    }

    public void setText(String text) {
        if (text == null) {
            return;
        }
        toggleButton.setText(text);
        this.pack();
    }

    public boolean isSelected(){
        return toggleButton.isSelected();
    }
    public void setSelected(boolean isSelected) {
        if (toggleButton.isSelected() == isSelected) {
            return;
        }
        toggleButton.setSelected(isSelected);
    }

}
