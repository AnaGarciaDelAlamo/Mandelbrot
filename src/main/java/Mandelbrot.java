
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Mandelbrot extends JPanel {
    private static final int ANCHO = 800;
    private static final int ALTO = 800;
    private static final int MAX_ITER = 1000;
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

    private void calculateMandelbrotForWorker(BufferedImage image, int startX, int startY, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = startY; y < startY + height; y++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - ANCHO / 2.0) * 4.0 / ANCHO;
                double cY = (y - ALTO / 2.0) * 4.0 / ALTO;
                int iter = 0;

                while (zx * zx + zy * zy < 4 && iter < MAX_ITER) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter++;
                }

                if (iter < MAX_ITER) {
                    int color = Color.HSBtoRGB(iter / 256f, 1, iter / (iter + 8f));
                    image.setRGB(x, y, color);
                } else {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }

                // Imprime información sobre el progreso en la consola
                System.out.println("Thread ID: " + Thread.currentThread().getId() + ", Calculated line " + (startY + x - 1));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private class MandelbrotTask extends RecursiveTask<BufferedImage> {
        private final BufferedImage image;
        private final int startX;
        private final int startY;
        private final int width;
        private final int height;
        private final int chunkSize;

        public MandelbrotTask(BufferedImage image, int startX, int startY, int width, int height, int chunkSize) {
            this.image = image;
            this.startX = startX;
            this.startY = startY;
            this.width = width;
            this.height = height;
            this.chunkSize = chunkSize;
        }

        @Override
        protected BufferedImage compute() {
            if (height <= chunkSize) {
                calculateMandelbrotForWorker(image, startX, startY, width, height);
                return image;
            } else {
                int midY = height / 2;
                MandelbrotTask topHalf = new MandelbrotTask(image, startX, startY, width, midY, chunkSize);
                MandelbrotTask bottomHalf = new MandelbrotTask(image, startX, startY + midY, width, height - midY, chunkSize);

                topHalf.fork();
                BufferedImage result = bottomHalf.compute();
                topHalf.join();

                return result;
            }
        }
    }

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
