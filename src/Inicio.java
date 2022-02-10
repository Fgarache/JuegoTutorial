import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inicio extends JFrame {
    private JButton jugarButton;
    private JPanel Panelhome;
    private  JTextField Nombre;
    public   String NAME = Nombre.getText();

    public Inicio(){
        super("Game");
        setContentPane(Panelhome);
        setLocationRelativeTo(null);
        jugarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("prueba");
                System.out.println(""+ NAME);
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setBackground(Color.black);
                ventana.setVisible(true);

            }
        });
    }
}
