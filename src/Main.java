import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Inicio();
                frame.setSize(250, 250);
                frame.setVisible(true);
            }
        });
    }
}
