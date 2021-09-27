import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePlay extends JPanel implements ActionListener , KeyListener {
    private  boolean playGame = false;
    private int totalBrick = 21;
    private Timer timer;
    private int delay = 2;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private  int ballYdir = -2;
    private int playerX = 350;
    private int score = 0;
    private MapGenerator map;

    public GamePlay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay , this);
        timer.start();
        map = new MapGenerator(3,7);


    }
    public void paint(Graphics g){
        // Black convas
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        // Yello Border
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,692,3);
        g.fillRect(0,3,3,592);
        g.fillRect(690,3,3,592);

        // Bricks
        map.draw((Graphics2D)g);

        // Paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX , 550,100,8);

        //Ball.
        g.setColor(Color.RED);
        g.fillOval(ballPosX,ballPosY,20,20);

        // Score.
        g.setColor(Color.green);
        g.setFont(new Font("serif" , Font.BOLD ,20));
        g.drawString("Score : "+score , 550,30);

        // GameOver
        if(ballPosY >=570 ){
            playGame = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.green);
            g.setFont(new Font("serif" , Font.BOLD , 30));
            g.drawString("Game Over!! Score: "+score  , 200,250 );


            g.setFont(new Font("serif" , Font.BOLD , 20));
            g.drawString("Press Enter to Restart" , 250,300 );
        }

        if(totalBrick <=0){
            playGame = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.green);
            g.setFont(new Font("serif" , Font.BOLD , 30));
            g.drawString("You Won!!  Score: "+score , 200,250 );

            g.setFont(new Font("serif" , Font.BOLD , 20));
            g.drawString("Press Enter to Restart" , 250,300 );

        }


    }

   private void moveLeft(){
        playerX -=20;
   }
    private void moveRight(){
        playerX +=20;
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            playGame = true;
            if(playerX <=0)
                playerX = 0;
            else
                moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            playGame = true;
            if(playerX >= 600)
                playerX = 600;
            else
              moveRight();
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!playGame){
                score = 0;
                totalBrick = 21;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 320;

                map = new MapGenerator(3,7);
            }
        }
        repaint();

    }

    public void keyReleased(KeyEvent e) {

    }
    public void actionPerformed(ActionEvent e) {
        if(playGame){
            if(ballPosX <=0 ){
                ballXdir  = -ballXdir;
            }
            if(ballPosX >=670){
                ballXdir = - ballXdir;
            }
            if(ballPosY <=0){
                ballYdir = -ballYdir;
            }

            Rectangle ballRect = new Rectangle(ballPosX , ballPosY , 20,20);
            Rectangle padelRect = new Rectangle(playerX , 550,100,8);


          A:  for(int i = 0 ; i<map.map.length ; i++){
                for(int j = 0; j<map.map[0].length ; j++){
                    if(map.map[i][j] > 0 ){
                        int weidth   = map.brickWidth;
                        int hight = map.brickHeight;
                        int brickXpos = 80+j*weidth;
                        int brickYpos = 50+i*hight;

                        Rectangle brickRect = new Rectangle(brickXpos , brickYpos , weidth,hight);

                        if(ballRect.intersects(brickRect)){
                            map.setValue(0,i,j);
                            score += 5;
                            totalBrick--;

                            if(ballPosX+19 <=brickXpos || ballPosX +1 >=brickXpos+weidth){
                                ballXdir = -ballXdir;
                            }
                            else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }

                    }
                }
            }

            if(ballRect.intersects(padelRect)){
                ballYdir = -ballYdir;
            }
            ballPosX += ballXdir;
            ballPosY += ballYdir;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {}
}
