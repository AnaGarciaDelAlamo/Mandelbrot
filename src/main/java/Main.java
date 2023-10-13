import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mandelbrot Set");
            Mandelbrot mandelbrot = new Mandelbrot();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mandelbrot);
            frame.pack();
            frame.setVisible(true);

            JButton button = new JButton("Calculate");
            button.addActionListener(e -> mandelbrot.pintaMandelbrot());
            frame.add(button, BorderLayout.SOUTH);
        });
    }
}
