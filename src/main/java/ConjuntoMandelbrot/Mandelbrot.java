package ConjuntoMandelbrot;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Mandelbrot extends JPanel {
    private static final int ANCHO = 800;
    private static final int ALTO = 800;
    private static final int MAX_ITER = 1000;
    private JSpinner spinner;
    private ExecutorService executorService;

    public Mandelbrot() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        add(spinner);
        executorService = Executors.newFixedThreadPool(4); // Cambia el número de hilos según tus necesidades
    }

    public void pintaMandelbrot() {
        BufferedImage image = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);

        int numWorkers = (Integer) spinner.getValue();
        int chunkSize = ALTO / numWorkers;

        BufferedImage finalImage1 = image;
        Future<BufferedImage> future = executorService.submit(() -> {
            MandelbrotTask task = new MandelbrotTask(finalImage1, 0, 0, ANCHO, ALTO, chunkSize);
            return task.compute();
        });

        try {
            image = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BufferedImage finalImage = image;
        SwingUtilities.invokeLater(() -> {
            Graphics g = getGraphics();
            g.drawImage(finalImage, 0, 0, this);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
