import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mandelbrot Set");
            Mandelbrot mandelbrotSet = new Mandelbrot();
            frame.add(mandelbrotSet);

            // Agregar un componente Spinner para seleccionar el número de trabajadores
            SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 8, 1); // Rango de 1 a 8 trabajadores
            JSpinner spinner = new JSpinner(spinnerModel);
            JButton startButton = new JButton("Start");

            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int numWorkers = (int) spinner.getValue();
                    mandelbrotSet.calculateMandelbrot(numWorkers);
                }
            });

            JPanel controlPanel = new JPanel();
            controlPanel.add(new JLabel("Number of Workers: "));
            controlPanel.add(spinner);
            controlPanel.add(startButton);

            frame.add(controlPanel, BorderLayout.NORTH);

            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

