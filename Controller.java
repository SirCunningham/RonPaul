package labb4;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Controller extends JPanel implements ActionListener {

    private Timer timer;
    private Model model;
    private View view;
    private JFrame frame;
    private int time = 0;
    private static int numOfTimeSteps = 10000;  //Convention?
    private int i = 0;
    private BufferedWriter writer;

    public static void main(String[] args) {
        Model model = new Model(3000);
        new Controller(model, new View(model));

    }

    public Controller(Model model, View view) {

        this.model = model;
        this.view = view;
        try {
            writer = new BufferedWriter(new FileWriter("Positions.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JSlider changeL = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
        changeL.addChangeListener(new LengthListener());
        this.view.getFrame().add(BorderLayout.SOUTH, changeL);

        JSlider changeD = new JSlider(JSlider.VERTICAL, 0, 200, 20);
        changeD.addChangeListener(new TimeListener());
        this.view.getFrame().add(BorderLayout.WEST, changeD);
        timer = new Timer((int) (model.getdt()), this);
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
            timer.setDelay((int) model.getdt());
        }
    }

    public void actionPerformed(ActionEvent e) {
        model.updateAll();
        view.repaint();
        if (view.getButton().getState()) {
            StringBuilder str = new StringBuilder();
            time += model.getdt();
            str.append(time);
            double[] modelPos = model.getPos();
            for (int j = 0; j < modelPos.length / 2; j++) {
                str.append(",");
                str.append(modelPos[2 * j]);
                str.append(",");
                str.append(modelPos[2 * j + 1]);
            }
            str.append("\n");
            try {
                writer.write(str.toString());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            i += 1;
        }
        if (i == numOfTimeSteps) {
            try {
                writer.close();
                System.exit(0);
            } catch (IOException ex) {
                System.out.println("Filen kunde inte stängas");
                ex.printStackTrace();
            }
        }

    }
}
