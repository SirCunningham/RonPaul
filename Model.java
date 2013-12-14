package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Model {

    private Particle particles[];
    private double L=1;
    private double dt=100;

    public Model(int n) {
        particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            particles[i] = new Particle();
        }
    }

    public void updateAll() {
        for (Particle part : particles) {
            part.update();
        }
    }

    public double[] getPos() {
        double[] positions = new double[2 * particles.length];
        for (int i = 0; i < particles.length; i += 1) {
            positions[2*i] = particles[i].getX();
            positions[2*i + 1] = particles[i].getY();
        }
        return positions;

    }

    public void setL(double L) {
        this.L = L;
    }

    public double getL() {
        return L;
    }
    public void setdt(double dt) {
        this.dt = dt;
    }
    public double getdt() {
        return dt;
    }

    public class Particle {

        private double xPos;
        private double yPos;

        public Particle(double xPos, double yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        public Particle() {
            this((int) (Math.random() * 250), (int) (Math.random() * 250));    //x=?,y=?
        }

        public void update() {
            xPos += L * Math.cos(2 * Math.random() * Math.PI);
            yPos += L * Math.sin(2 * Math.random() * Math.PI);
        }

        public double getX() {
            return xPos;
        }

        public double getY() {
            return yPos;
        }
    }
}
