

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Objects;

public class VentanaPrincipal extends JFrame implements KeyListener {
    //Variable glovales
    public Timer t, frequencyEnemy;
    public int xnave = 240, enemy_ini = 10, enemy_increase_x = 0, enemy_increase_y = 0, moveEnemy = 10;
    public Rectangle2D nave;
    public boolean R_nave = false, L_nave = true, fila2;
    public Graphics2D g2;
    public Rectangle2D[][] navesEnemigas = new Rectangle2D[2][5];
    boolean R_enemy = true, L_enemy = false;
    public int[][] enemy_y = new int[2][5];
    public int aleatory1, aleatory2, counterDown;
    public boolean down = false;
    public static int score =0;

    //disparos
    public Ellipse2D[] disparos = new Ellipse2D[20];
    public int disparadas = 0, contadorrecarga = 0;
    public int[] xdisparos = new int[20];
    public int[] ydisparos = new int[20];

    //image
    public Image naveImage;
    public Image[][] naveEnemy = new Image[2][5];


    VentanaPrincipal() throws IOException {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(520, 520);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.red);
        this.setVisible(true);
        this.addKeyListener(this);
        naveImage = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("nave.png")));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                naveEnemy[i][j] = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("nave_enemy1.png")));

            }

        }


        start_altitude();
        Timernave();
        t.start();
        timerEnemy();
        frequencyEnemy.start();
        start_disparos();

    }

    public void attack_enemy() {
        if (!down) {
            while (enemy_y[aleatory1][aleatory2] >= 550) {
                aleatory1 = (int) (Math.random() * 2);
                aleatory2 = (int) (Math.random() * 5);
            }
        }
        enemy_y[aleatory1][aleatory2] += 7;
        if (enemy_y[aleatory1][aleatory2] >= 550) {
            down = false;
            counterDown = 0;
        }
    }

    public void start_disparos() {
        for (int i = 0; i < 20; i++) {
            ydisparos[i] = 410;
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
        t = new Timer(10, e -> {
            if (R_nave && xnave <= 440) {
                xnave += 5;
            }
            if (L_nave && xnave >= 0) {
                xnave -= 3;
            }
            //timer balas
            for (int i = 0; i < disparadas; i++) {
                if (ydisparos[i] >= -20) {
                    ydisparos[i] -= 3;
                }
            }


            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g2 = (Graphics2D) g;
        nave = new Rectangle2D.Double(xnave, 410, 60, 90);
        g2.drawImage(naveImage, xnave - 5, 410, 80, 90, this);

        //elipse balas
        g2.setColor(Color.yellow);
        for (int i = 0; i < disparadas; i++) {
            disparos[i] = new Ellipse2D.Double(xdisparos[i], ydisparos[i], 10, 20);
            g2.fill(disparos[i]);
        }

        start_enemy();
        writhe_screen();
    }

    public void writhe_screen() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                if (nave.intersects(navesEnemigas[i][j])) {
                    g2.setFont(new Font("Tahoma", Font.BOLD, 72));
                    g2.drawString("Perdiste  ", 102, 300);

                    t.stop();
                    frequencyEnemy.stop();

                }

            }

        }


        g2.setFont(new Font("Tahoma", Font.ITALIC, 12));
        g2.drawString("Vidas  ", 20, 515);
        g2.drawString("Level  ", 150, 515);
        g2.drawString("Score-"+score, 260, 515);
        if (contadorrecarga <= 4) {
            g2.drawString("Balas"+(contadorrecarga+-5), 370, 515);
        }else{
            g2.drawString("Balas"+("-Recargar"), 370, 515);
        }
        //g2.drawString("Balas"+(contadorrecarga+-5), 370, 515);
    }

    public void timerEnemy() {
        frequencyEnemy = new Timer(90, e -> {
            //mover izquierda derecha
            if (R_enemy) {
                moveEnemy += 5;
                if (moveEnemy >= 110) {
                    R_enemy = false;
                    L_enemy = true;
                    counterDown++;
                }
            }

            if (L_enemy) {
                moveEnemy -= 5;
                if (moveEnemy <= -5) {
                    R_enemy = true;
                    L_enemy = false;
                }
            }
            if (counterDown >= 3) {
                attack_enemy();
            }

            //intercepcion de balas con naves
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < disparadas; k++) {

                        if (disparos[k].intersects(navesEnemigas[i][j])) {
                            ydisparos[k] = -40;
                            enemy_y[i][j] = 600;
                            score ++;
                            System.out.println(score);
                            System.out.println("interception  ");
                        }
                    }

                }

            }

            repaint();
        });
    }


    public void start_enemy() {
        enemy_increase_x = 0;
        enemy_increase_y = 0;
        for (int rows = 0; rows < 2; rows++) {
            for (int columns = 0; columns < 5; columns++) {
                navesEnemigas[rows][columns] = new Rectangle2D.Double(enemy_ini + enemy_increase_x + moveEnemy, 19 + enemy_y[rows][columns], 60, 90);
                g2.drawImage(naveEnemy[rows][columns], enemy_ini + enemy_increase_x + moveEnemy, 19 + enemy_y[rows][columns], 85, 85, this);
                enemy_increase_x += 80;
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
            //contador de disparos
            if (contadorrecarga < 5 && disparadas < 20) {
                xdisparos[disparadas] = (int) nave.getCenterX();
                disparadas++;
                contadorrecarga++;
                System.out.println("Shooting");
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Recharge");
            contadorrecarga = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
