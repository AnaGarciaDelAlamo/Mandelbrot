import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandelbrot extends JPanel {
    private static final int ANCHO = 800;
    private static final int ALTO = 800;
    private static final int MAX_ITER = 1000;

    public Mandelbrot() {
        setPreferredSize(new java.awt.Dimension(ANCHO, ALTO));
    }

    public void calculateMandelbrot(int numWorkers) {
        BufferedImage image = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);

        int chunkSize = ALTO / numWorkers;

        for (int i = 0; i < numWorkers; i++) {
            int startY = i * chunkSize;
            int endY = (i + 1) * chunkSize;

            calculateMandelbrotForWorker(image, startY, endY);
        }

        // Pintar la imagen en el panel
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, this);
    }

    private void calculateMandelbrotForWorker(BufferedImage image, int startY, int endY) {
        for (int x = 0; x < ANCHO; x++) {
            for (int y = startY; y < endY; y++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - ANCHO / 2.0) * 4.0 / ANCHO;
                double cY = (y - ALTO / 2.0) * 4.0 / ANCHO;
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
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
