package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class View extends JPanel {

    private static final Dimension screenSize =
            Toolkit.getDefaultToolkit().getScreenSize();
    public static final double width = screenSize.getWidth() * 0.5;
    public static final double height = screenSize.getHeight() * 0.5;
    private final Model model;
    private final JFrame frame;
    private final MyButton button;

    public View(Model model) {
        this.model = model;
        button = new MyButton();
        button.addActionListener(button);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension((int) width, (int) height));
        frame.add(this);
        frame.add(BorderLayout.SOUTH, button);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        Model.Particle[] particles = model.getParticles();
        double[] positions = model.getPos();
        for (int i=0;i < particles.length;i++) {
            if (particles[i].isStuck()) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.YELLOW);
            }
            g2d.fill(new Ellipse2D.Double(positions[2 * i],
                    positions[2 * i + 1], 2, 2));
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public MyButton getButton() {
        return button;
    }
}
