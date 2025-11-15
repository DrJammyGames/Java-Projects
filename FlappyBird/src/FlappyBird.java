
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int windowWidth = 360;
    int windowHeight = 640;
    //Images
    Image backGroundImage;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird
    //1/8 from left side of screen
    int birdX = windowWidth/8;
    //1/2 from top of screen
    int birdY = windowHeight/2;

    int birdWidth = 34;
    int birdHeight = 24;

    //Constructor
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;

        Image img;

        Bird(Image img){
            this.img = img;
        }

    }
    //Pipes
    int pipeX = windowWidth;
    //Pipe starts at top of screem
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;

        }
    }
    //Game logic
    Bird bird;
    //Will move the pipe to the left, making it seem like the bird is moving to the right
    int velocityX = -4;
    //Only need y movement because the pipes move towards the bird rather than moving the bird x
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();
    
    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0;
    
    FlappyBird(){

        setPreferredSize(new Dimension(windowWidth, windowHeight));
        //setBackground(Color.blue);

        setFocusable(true);
        addKeyListener(this);
        //Load images
        backGroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //Bird stuffs
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>(); 
        //Add a new pipe every 1.5 seconds (1500 milliseconds)
        placePipesTimer = new Timer(1500,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();     
        //Game timer
        gameLoop = new Timer(1000/60, this); 
        //Starts the loop so it'll continue every frame
        gameLoop.start();
    }

    public void placePipes(){
        //Random between 0 and 1 and multiplied by pipeHeight/2 
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = windowHeight/4; //Space for flappy bird
        Pipe topPipe = new Pipe(topPipeImg);
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        
        topPipe.y = randomPipeY;
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(topPipe);
        pipes.add(bottomPipe);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backGroundImage, 0, 0, windowWidth, windowHeight, null);
        g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);

        //Pipes
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver){
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 36);
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 36);
        }
    }


    public void move(){
        //Bird movement
        velocityY += gravity;
        bird.y += velocityY;
        //Ensures the bird can't go past the top part of the screen
        bird.y = Math.max(bird.y, 0);

        //Pipes
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5; //There are two pipes, top and bottom, so split the points, adding 0.5 and 0.5, hence setting it as a double
            }
            if(collision(bird, pipe)){
                gameOver = true;
            }

        }

        if (bird.y > windowHeight){
            gameOver = true; 
        }

    }

    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
            a.x + a.width > b.x &&
            a.y < b.y + b.height &&
            a.y + a.height > b.y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //Calls the move function to update the birds y position including where it is drawn
        move();
        repaint(); //Calls paint component
        if (gameOver){

            placePipesTimer.stop(); //Stops adding pipes
            gameLoop.stop(); //Stops updating the game
        }
    }   

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;     
            if (gameOver){
                //Restart the game by resetting the conditions
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }       
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }
}
