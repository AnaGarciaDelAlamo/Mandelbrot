package ConjuntoMandelbrot;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Mandelbrot extends JPanel {
    private static final int ANCHO = 800;
    private static final int ALTO = 800;
    //private static final int MAX_ITER = 1000;
    private JSpinner spinner;
    private ForkJoinPool forkJoinPool;


    public Mandelbrot() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        add(spinner);
        forkJoinPool = new ForkJoinPool();
    }

    public void pintaMandelbrot() {
        BufferedImage image = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);

        int numWorkers = (Integer) spinner.getValue();
        int chunkSize = ALTO / numWorkers;
        MandelbrotTask task = new MandelbrotTask(image, 0, 0, ANCHO, ALTO, chunkSize);
        image = forkJoinPool.invoke(task);

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
