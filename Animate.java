import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animate extends JPanel implements ActionListener {

        Timer timer;
        // Point array for drawing a star
        private final double points[][] = {

                        { 0, 85 }, { 75, 75 }, { 100, 10 }, { 125, 75 }, { 200, 85 }, { 150, 125 }, { 160, 190 }, { 100, 150 },

                        { 40, 190 }, { 50, 125 }, { 0, 85 } };

        // Variables to change size of the star
        private double scale = 1;
        private double delta = 0.01;

        // Constructor

        public Animate() {
                // create a timer, used 10ms for delay
                timer = new Timer(10, this);
                // start timer …
                timer.start();
        }

        public void paint(Graphics g)

        {
                // creating gradient paint
                Paint p = new GradientPaint(0.0f, 0.0f, Color.getHSBColor(0.75f, 0.8f, 0.3f), 0, getHeight(),
                                Color.getHSBColor(0.65f, 0.8f, 0.5f));
                // fetching Graphics2D object
                Graphics2D g2 = (Graphics2D) g;
                // using given paint
                g2.setPaint(p);
                // filling rectangle as background
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Create a general path, Instantiate the GeneralPath class
                GeneralPath path = new GeneralPath();
                // moving to first point
                path.moveTo(points[0][0], points[0][1]);

                // Use lineTo() to initialize coordinate of the GeneralPath
                // Connect all the coordinates of the star
                for (int i = 1; i < points.length; i++) {
                        path.lineTo(points[i][0], points[i][1]);
                }
                // closing path
                path.closePath();

                // Scale star for animation
                // first, creating a AffineTransform object
                AffineTransform tx = new AffineTransform();
                // translating tx to be always at center of the window, otherwise after scaling
                // it would be moved towards and away from top left of the star.
                tx.translate((getWidth() / 2) - (path.getBounds().getWidth() * (scale)) / 2,
                                (getHeight() / 2) - (path.getBounds().getHeight() * (scale)) / 2);
                // scaling
                tx.scale(scale, scale);

                // fetching transformed shape
                Shape s = tx.createTransformedShape(path);

                // Set color and fill star ….
                g2.setColor(Color.YELLOW);
                g2.fill(s);
        }

        public static void main(String[] args) {
                // Initialize frame
                JFrame f = new JFrame("Shooting Star");
                // adding an instance of Animate to frame
                f.add(new Animate());
                // Set frame size to 400x400
                f.setSize(400, 400);
                // Set visibility
                f.setVisible(true);
                // Set default close operation …
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void actionPerformed(ActionEvent e) {

                // Change the scale variable by using delta
                scale += delta;

                // If scale < 0.01 or 0.99 < scale you can change the sign of delta
                if (scale < 0.01 || scale > 0.99) {
                        delta *= -1;
                        scale += delta;
                }
                // repainting
                repaint();
        }
}

