
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vajira Samarasingha
 */
public class GamePanel extends JPanel implements ActionListener{
    
    //Game Data
    static final int screenWidth =600;
    static final int screenHeight =600;
    static final int unitSize = 25;
    static final int gameUnit = (screenWidth*screenWidth)/unitSize;
    static final int delay = 75;
    final int x[] = new int[gameUnit];
    final int y[] = new int[gameUnit];
    int bodyParts=6;
    int appleeaten;
    int appleX;
    int appleY;
    char directon = 'R';
    boolean running =false;
    Timer timer;
    Random random;
    
    //Game Constructor
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    
    //Game Method

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
//            for (int i = 0; i < screenHeight/unitSize; i++) {
//                g.drawLine(i*unitSize, 0, i*unitSize, screenHeight);
//                g.drawLine(0,i*unitSize, screenWidth,i*unitSize);
//            }
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,unitSize,unitSize);

            for (int i = 0; i < bodyParts; i++) {
                if(i==0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i],unitSize, unitSize);
                }
                else{
                    g.setColor(new Color(45,180,0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i],unitSize, unitSize);
                }
            }
            
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score "+appleeaten,(screenWidth - metrics.stringWidth("Score"+appleeaten))/2, g.getFont().getSize());

            
        }else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(screenWidth/unitSize))*unitSize;
        appleY = random.nextInt((int)(screenHeight/unitSize))*unitSize ;
        
    }
    public void move(){
        for (int i = bodyParts; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(directon){
            case 'U':
                y[0] = y[0]-unitSize;break;
            case 'D':
                y[0] = y[0]+unitSize;break;
            case 'R':
                x[0] = x[0]+unitSize;break;  
            case 'L':
                x[0] = x[0]-unitSize;break;
            
        }
    }
    
    public void checkApple(){
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyParts++;
            appleeaten++;
            newApple();
        }
    
    }
    public void checkCollision(){
        for (int i = bodyParts; i >0; i--) {
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        if(x[0]<0){
            running = false;
        }
        if(x[0]>screenWidth){
            running = false;
        }
        if(y[0]<0){
            running = false;
        }
        if(y[0]>screenHeight){
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score "+appleeaten,(screenWidth - metrics1.stringWidth("Score"+appleeaten))/2, g.getFont().getSize());
        
        
        
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",(screenWidth - metrics2.stringWidth("Game Over"))/2, screenHeight/2);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
           move();
           checkApple();
           checkCollision();
        }
        repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter{
       @Override
       public void keyPressed(KeyEvent e){
           switch(e.getKeyCode()){
               case KeyEvent.VK_LEFT:
                   if (directon != 'R') {
                       directon = 'L';
                   }
                   break;
                case KeyEvent.VK_RIGHT:
                   if (directon != 'L') {
                       directon = 'R';
                   }
                   break;
                case KeyEvent.VK_UP:
                   if (directon != 'D') {
                       directon = 'U';
                   }
                   break;
                case KeyEvent.VK_DOWN :
                   if (directon != 'U') {
                       directon = 'D';
                   }
                   break;
               
           }
       }
    }
    
}
