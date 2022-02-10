

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

public class VentanaPrincipal extends JFrame implements KeyListener {
    //Variable glovales
    public Timer t, frequencyEnemy;
    public int xnave = 240, enemy_ini = 50, enemy_increase_x = 0, enemy_increase_y = 0, moveEnemy = 10;
    public Rectangle2D nave;
    public boolean R_nave = false, L_nave = true, fila2;
    public Graphics2D g2;
    public Rectangle2D[][] navesEnemigas = new Rectangle2D[2][5];
    boolean R_enemy = true, L_enemy = false;
    public int[][] enemy_y = new int[2][5];
    public int aleatory1, aleatory2, counterDown;
    public boolean down =false;


    VentanaPrincipal() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        this.setVisible(true);
        this.addKeyListener(this);

        start_altitude();
        Timernave();
        t.start();
        timerEnemy();
        frequencyEnemy.start();
    }

    public void attack(){
        if (down == false) {
            while (enemy_y[aleatory1][aleatory2] >= 550) {
                aleatory1 = (int)(Math.random()*2);
                aleatory2 = (int)(Math.random()*5);
            }
        }
        enemy_y [aleatory1][aleatory2]+=7;
        if (enemy_y[aleatory1][aleatory2] >= 550) {
            down = false;
            counterDown= 0;
        }
    }



    public void start_altitude() {
        for (int fila = 0; fila < 2; fila++) {
            for (int columns = 0; columns < 5; columns++) {
                enemy_y[fila][columns] = 20;
                if (fila2) {
                    enemy_y[fila][columns] = 120;
                }
            }
            fila2 = true;
        }

    }


    public void Timernave() {
        t = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (R_nave && xnave <= 440) {
                    xnave += 5;
                }
                if (L_nave && xnave >= 0) {
                    xnave -= 5;
                }
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        nave = new Rectangle2D.Double(xnave, 410, 60, 90);
        g2.fill(nave);
        start_enemy();
    }

    public void timerEnemy() {
        frequencyEnemy = new Timer(90, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (R_enemy) {
                    moveEnemy += 5;
                    if (moveEnemy >= 45) {
                        R_enemy = false;
                        L_enemy = true;
                        counterDown++;
                    }
                }

                if (L_enemy) {
                    moveEnemy -= 5;
                    if (moveEnemy <= 20) {
                        R_enemy = true;
                        L_enemy = false;
                    }
                }
                if (counterDown >= 3) {
                    attack();

                }

                repaint();
            }
        });

    }

    public void start_enemy() {
        enemy_increase_x = 0;
        enemy_increase_y = 0;
        for (int rows = 0; rows < 2; rows++) {
            for (int columns = 0; columns < 5; columns++) {
                navesEnemigas[rows][columns] = new Rectangle2D.Double
                        (enemy_ini + enemy_increase_x + moveEnemy, enemy_y[rows][columns], 60, 90);
                enemy_increase_x += 80;
                g2.fill(navesEnemigas[rows][columns]);
            }
            enemy_increase_x = 0;
            enemy_increase_y = 100;


        }

        //repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            R_nave = true;
            L_nave = false;

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            L_nave = true;
            R_nave = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Shooting");

        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Recharge");

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
