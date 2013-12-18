package labb4;

import java.util.*;

public class Model {

    private Particle particles[];
    private double L = 1;
    private int dt = 100;
    private int regions = 100; //Dela rutan i ett 10x10-rutnät
    private int xmax = ((int) View.width) - 100;
    private int ymax = ((int) View.height) - 100;
    private ArrayList<ArrayList<Particle>> stuckParticles =
            new ArrayList<ArrayList<Particle>>();

    public Model(int n) {
        particles = new Particle[n]; //Skapar bara hälften så många!!
        for (int i = 0; i < n; i++) {
            particles[i] = new Particle();
        }
        for (int i = 0; i < regions * regions; i++) {
            stuckParticles.add(new ArrayList<Particle>());
        }
    }

    public void updatePos() {
        for (Particle part : particles) {
            if (!part.isStuck()) {
                part.update();
                int region = getRegion(part);
                collide(part, stuckParticles.get(region));
                if (region == 0) {
                    collide(part, stuckParticles.get(1));
                    collide(part, stuckParticles.get(regions));
                    collide(part, stuckParticles.get(regions + 1));
                } else if (region == regions - 1) {
                    collide(part, stuckParticles.get(region - 1));
                    collide(part, stuckParticles.get(region + regions));
                    collide(part, stuckParticles.get(region + regions - 1));
                } else if (region == regions * regions - regions) {
                    collide(part, stuckParticles.get(region + 1));
                    collide(part, stuckParticles.get(region - regions));
                    collide(part, stuckParticles.get(region - regions + 1));
                } else if (region == regions * regions - 1) {
                    collide(part, stuckParticles.get(region - 1));
                    collide(part, stuckParticles.get(region - regions));
                    collide(part, stuckParticles.get(region - regions - 1));
                } else if (0 < region && region < regions) {
                    collide(part, stuckParticles.get(region - 1));
                    collide(part, stuckParticles.get(region + 1));
                    collide(part, stuckParticles.get(region + regions - 1));
                    collide(part, stuckParticles.get(region + regions));
                    collide(part, stuckParticles.get(region + regions + 1));

                } else if (region % regions == 0) {
                    collide(part, stuckParticles.get(region - regions));
                    collide(part, stuckParticles.get(region - regions + 1));
                    collide(part, stuckParticles.get(region + 1));
                    collide(part, stuckParticles.get(region + regions));
                    collide(part, stuckParticles.get(region + regions + 1));
                } else if (region % regions == regions - 1) {
                    collide(part, stuckParticles.get(region - regions));
                    collide(part, stuckParticles.get(region - regions - 1));
                    collide(part, stuckParticles.get(region - 1));
                    collide(part, stuckParticles.get(region + regions));
                    collide(part, stuckParticles.get(region + regions - 1));
                } else if (regions * regions - regions < region
                        && region < regions * regions) {
                    collide(part, stuckParticles.get(region - 1));
                    collide(part, stuckParticles.get(region + 1));
                    collide(part, stuckParticles.get(region - regions));
                    collide(part, stuckParticles.get(region - regions - 1));
                    collide(part, stuckParticles.get(region - regions + 1));
                } else {
                    collide(part, stuckParticles.get(region - regions - 1));
                    collide(part, stuckParticles.get(region - regions));
                    collide(part, stuckParticles.get(region - regions + 1));
                    collide(part, stuckParticles.get(region - 1));
                    collide(part, stuckParticles.get(region + 1));
                    collide(part, stuckParticles.get(region + regions - 1));
                    collide(part, stuckParticles.get(region + regions));
                    collide(part, stuckParticles.get(region + regions + 1));
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
            if (distanceSquared(p, part) < 2 * 2) {
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
                        + (yPos - ymax / 2) * (yPos - ymax / 2)) == 50 * 50) {
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
