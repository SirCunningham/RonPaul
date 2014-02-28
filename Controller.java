package labb4;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Controller extends JPanel implements ActionListener,
        ChangeListener {

    private final Timer timer;
    private final Model model;
    private final View view;
    private final JSlider LSlider;
    private final JSlider deltaSlider;
    private static final int maxSteps = 200; //Convention?
    private int time = 0;
    private final int i = 0;
    private BufferedWriter writer;
    private boolean log;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        log = true;
        try {
            writer = new BufferedWriter(new FileWriter("Positions.txt"));
        } catch (IOException ex) {
            System.err.println("Kunde inte skapa fil.");
            ex.printStackTrace();
        }

        LSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
        LSlider.addChangeListener(this);
        deltaSlider = new JSlider(JSlider.VERTICAL, 0, 200, 20);
        deltaSlider.addChangeListener(this);
        this.view.getFrame().add(BorderLayout.NORTH, LSlider);
        this.view.getFrame().add(BorderLayout.WEST, deltaSlider);
        this.view.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                try {
                    writer.close();
                    System.exit(0);
                } catch (IOException ex) {
                    System.out.println("Filen kunde stängas");
                    ex.printStackTrace();
                }
            }
        });
        this.view.getFrame().pack();
        this.view.getFrame().setVisible(true);

        timer = new Timer((int) (model.getdt()), this);
        timer.start();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (source == LSlider) {
            model.setL(source.getValue());
        } else if (source == deltaSlider) {
            model.setdt(source.getValue());
            timer.setDelay((int) model.getdt());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.updatePos();
        view.repaint();
        if (view.getButton().getState() && log) {      //view.getButton().getState() //under 1000 med string
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
                System.err.println("Kunde inte redigera filen.");
                ex.printStackTrace();
            }
            //i++;
            if (i == maxSteps) {
                try {
                    writer.close();
                    log = false;
                } catch (IOException ex) {
                    System.err.println("Kunde inte stänga filen.");
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Model model = new Model(10000);
        new Controller(model, new View(model));
    }
}
