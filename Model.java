package labb4;

import java.util.*;

public class Model {

    private Particle particles[];
    private double L = 1;
    private int dt = 100;
    private static final int regions = 80; //Dela rutan i ett 80x80-rutnät
    private static final int xmax = ((int) View.width) - 31; //21
    private static final int ymax = ((int) View.height) - 73;
    private static final int radiusSquared = (xmax > ymax ? ymax : xmax)
            * (xmax > ymax ? ymax : xmax) / 25;
    private ArrayList<ArrayList<Particle>> stuckParticles =
            new ArrayList<>();

    public Model(int n) {
        particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            particles[i] = new Particle();
        }
        for (int i = 0; i < regions * regions; i++) {
            stuckParticles.add(new ArrayList<Particle>());
        }

    }

    public ArrayList<Integer> limitRegions(int region) {
        ArrayList<Integer> limitRegions = new ArrayList<Integer>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int k = region + i + j * regions;
                if (k >= 0 && k < regions * regions) {
                    limitRegions.add(k);
                }
            }
        }
        return limitRegions;
    }

    public void updatePos() {
        for (Particle part : particles) {
            if (!part.isStuck()) {
                part.update();
                int region = getRegion(part);
                ArrayList<Integer> limitRegions = limitRegions(region);
                for (int reg : limitRegions) {
                    collide(part, stuckParticles.get(reg));
                }
            }
            if (part.isStuck()) {
                stuckParticles.get(getRegion(part)).add(part);
            }
        }
    }

    public double[] getPos() {
        double[] positions = new double[2 * particles.length];
        for (int i = 0; i < particles.length; i++) {
            positions[2 * i] = particles[i].getX();
            positions[2 * i + 1] = particles[i].getY();
        }
        return positions;
    }

    public Particle[] getParticles() {
        return particles;
    }

    //Vi räknar regionerna från vänster-höger och uppåt-nedåt
    public int getRegion(Particle p) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < regions; i++) {
            if (i * xmax / regions <= p.getX()
                    && p.getX() <= (i + 1) * xmax / regions) {
                x = regions * i;
            }
            if (i * ymax / regions <= p.getY()
                    && p.getY() <= (i + 1) * ymax / regions) {
                y = i;
            }
        }
        return x + y;
    }

    //Math.sqrt inte lika effektivt
    public double distanceSquared(Particle x, Particle y) {
        return (x.getX() - y.getX()) * (x.getX() - y.getX())
                + (x.getY() - y.getY()) * (x.getY() - y.getY());
    }

    public void collide(Particle p, ArrayList<Particle> ps) {
        for (Particle part : ps) {
            if (distanceSquared(p, part) < 3 * 3) {
                p.setStuck(true);
                break;
            }
        }
    }

    public void setL(double L) {
        this.L = L;
    }

    public double getL() {
        return L;
    }

    public void setdt(int dt) {
        this.dt = dt;
    }

    public int getdt() {
        return dt;
    }

    public class Particle {

        private double xPos;
        private double yPos;
        private boolean isStuck = false;

        public Particle(double xPos, double yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        public Particle() {
            this((double) (Math.random() * xmax),
                    (double) (Math.random() * ymax)); //x=?,y=?
        }

        public void update() { //Stil: Operatorer på rätt rad?
            if (!isStuck) {
                if (xPos + L * Math.cos(2 * Math.random() * Math.PI) >= xmax
                        || xPos >= xmax) {
                    xPos = xmax;
                    isStuck = true;
                } else if (xPos + L * Math.cos(2 * Math.random() * Math.PI) <= 0
                        || xPos <= 0) {
                    xPos = 0;
                    isStuck = true;
                } else {
                    xPos += L * Math.cos(2 * Math.random() * Math.PI);
                }
                if (yPos + L * Math.sin(2 * Math.random() * Math.PI) >= ymax
                        || yPos >= ymax) {
                    yPos = ymax;
                    isStuck = true;
                } else if (yPos + L * Math.sin(2 * Math.random() * Math.PI) <= 0
                        || yPos <= 0) {
                    yPos = 0;
                    isStuck = true;
                } else {
                    yPos += L * Math.sin(2 * Math.random() * Math.PI);
                }
                //Fastnar om den träffar cirkel i mitten
                if ((int) ((xPos - xmax / 2) * (xPos - xmax / 2)
                        + (yPos - ymax / 2) * (yPos - ymax / 2)) == radiusSquared) {
                    isStuck = true;
                }
            }
        }

        public double getX() {
            return xPos;
        }

        public double getY() {
            return yPos;
        }

        public boolean isStuck() {
            return isStuck;
        }

        public void setStuck(boolean b) {
            isStuck = b;
        }
    }
}
