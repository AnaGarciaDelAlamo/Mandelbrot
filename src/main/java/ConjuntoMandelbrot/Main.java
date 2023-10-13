package ConjuntoMandelbrot;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            button.addActionListener(e -> {
                // Crear un ExecutorService para ejecutar el cálculo en un hilo separado
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> mandelbrot.pintaMandelbrot());

                // Cerrar el ExecutorService después de que se complete el cálculo
                executor.shutdown();
            });
            frame.add(button, BorderLayout.SOUTH);
        });
    }

}
