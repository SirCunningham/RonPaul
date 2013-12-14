
package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;

public class View extends JPanel {

    private Model model;
    private JFrame frame;

    public View(Model model) {
        this.model = model;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(this);
        frame.setPreferredSize(new Dimension(300,300));
        frame.pack();
        frame.setVisible(true);

    }
    public void add2(JSlider slider) {
        this.add(slider);
    }
    public JFrame getFrame() {
        return frame;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        int i = 0;
        while (i < model.getPos().length) {
            g2d.fill(new Ellipse2D.Double(model.getPos()[i],model.getPos()[i+1],
                    10,10));
            i+=2;
        }

    }
}
