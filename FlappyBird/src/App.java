import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        int windowWidth = 360;
        int windowHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");

        frame.setVisible(true);
        frame.setLocationRelativeTo(frame);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setBackground(null);
        

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setSize(windowWidth, windowHeight);
    }
}
