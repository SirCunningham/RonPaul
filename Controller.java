
package labb4;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class Controller extends JPanel implements ActionListener {
    
    private Timer timer;
    private Model model;
    private View view;
    
    public static void main(String[] args) {
        Model model = new Model(10);
        new Controller(model, new View(model));
        
    }
    
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        
        JSlider changeL = new JSlider(JSlider.HORIZONTAL,0,10,5);
        changeL.addChangeListener(new LengthListener());
        
        JSlider changeD = new JSlider(JSlider.HORIZONTAL,0,10,1);
        changeD.addChangeListener(new TimeListener());
        
        timer = new Timer((int)(model.getdt()),this);
        timer.start();
    }
    
    public class LengthListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            model.setL(source.getValue());
        }
    }
    public class TimeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            model.setdt(source.getValue());
            timer.setDelay((int)model.getdt());
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        model.updateAll();
        view.repaint();
    }
    
}
