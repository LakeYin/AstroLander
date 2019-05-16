import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

//UserPanel inherits from JPanel and uses the KeyListener and ActionListener interfaces

public class UserPanel extends JPanel implements KeyListener, ActionListener, JavaArcade
{
  private boolean running = false, start = false, flight = false, win = false, lose = false, thrust = false;
  
  private int points;
  private ArrayList<Integer> scores = new ArrayList<Integer>();
  
  private Lander lander;
  private Goal goal;
  private Enviroment enviroment;
  
  private int height, width;
  
  File landerSprites[];
  File goalSprites[];
  
  SoundPlayer music;
  SoundPlayer thrustSound;
  SoundPlayer explosionSound;
  
  private javax.swing.Timer timer; //controls how often we updated the x, y pos of enemies and how often we repaint
  
  Color text = Color.green;
   
  public UserPanel (int width, int height) {
    
    // set panel stuff
    Color backColor = Color.black;
    
    this.width = width;
    this.height = height;
    
    points = 0;
    
    // set initial enviroment
    enviroment = new Enviroment(width, height);
    
    // load in sprites
    landerSprites = new File[] {new File("lander.png"), new File("landerThrust.png"), new File("explosion.png")};
    goalSprites = new File[] {new File("goal.png")};
    
    // set game objects
    lander = new Lander(0, height - enviroment.get(0).getYSize() - Lander.SPRITE_Y_SIZE, width / 4, 1, 2, landerSprites);
    goal = new Goal(width - 3 * Goal.SPRITE_X_SIZE, height - enviroment.get(enviroment.size() - 1).getYSize() - Goal.SPRITE_Y_SIZE, goalSprites);
    
    // load sound
    music = new SoundPlayer("music.mp3");
    thrustSound = new SoundPlayer("thrust.mp3");
    explosionSound = new SoundPlayer("explosion.mp3");
    
    // start timer
    timer = new javax.swing.Timer(50, this);
    
    addKeyListener(this);
    
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);      
    setBackground(backColor);
    
    setPreferredSize(new Dimension(width, height));
  }
  
  
  public void actionPerformed (ActionEvent e){ 
    boolean touchingTop = false;
    boolean touchSide = false;
    
    for(TerrainBlock block : enviroment){
      if(!touchingTop)
        touchingTop = block.touchTop(lander);
      
      if(!touchSide)
        touchSide = block.touchWall(lander);
    }
    
    lose = lander.getXPos() > width || touchSide;
    win = lander.isTouching(goal);
      
    lander.updatePosition();
    
    if(flight && !touchingTop)
      lander.decend();
    else{
      lander.land();
      flight = false;
    }
    
    if(lander.getFuel() <= 0)
      thrustSound.stop();
    
    points = lander.getFuel();
    repaint(); 
  }  
  
  public void keyTyped(KeyEvent e) { }
  public void keyReleased(KeyEvent e) { 
    if(e.getKeyCode() == KeyEvent.VK_UP && !lose)
      lander.changeSprite(0); 
    
    thrust = false;
    try{
      thrustSound.stop();
    } catch (java.lang.NullPointerException f){
    }
  }  
  
  public void keyPressed(KeyEvent e){
    
    switch(e.getKeyCode())
    {
      case KeyEvent.VK_ENTER: {
        if(!running)
          startGame();
        
        break;
      }
      
      case KeyEvent.VK_UP: {
        if(running)
          lander.thrust();
        if(lander.getHorizontalVelocity() == 0 && lander.getFuel() > 0)
          lander.setHorizontalVelocity(1);
        
        if(!thrust && lander.getFuel() > 0)
          thrustSound.playSound(true);
        
        flight = true;
        thrust = lander.getFuel() > 0;
        
        break;  
      }
         
      case KeyEvent.VK_ESCAPE: {
        if(running){
          pauseGame();
          music.pause();
        }
        
        break;
      }
      default:   
    }     
  }
  
  //draws everything
  
  public void paintComponent(Graphics g){
    
    super.paintComponent(g); //a call to JPanel's paintComponent        
    
    enviroment.draw(g);
    
    lander.draw(g);
    goal.draw(g);
    
    g.setColor(text);
    
    if(!start){
      g.drawString("Press the up arrow key to propel the lander up.", (getWidth() / 2) - 100, getHeight() / 2 - 120);
      g.drawString("It will move right automatically and be affected by gravity.", (getWidth() / 2) - 100, getHeight() / 2 - 100);
      g.drawString("Navigate the lander to the goal/flag.", (getWidth() / 2) - 100, getHeight() / 2 - 80);
    }
    
    updateFuelBar(g);
    
    if(lose){
      loseScreen(g);
      lander.land();
      flight = false;
    }
    else if(win){
      winScreen(g);
      lander.land();
      flight = false;
    }
  }   
  
  public void updateFuelBar(Graphics g){
    if(lander.getFuel() <= 0)
      g.setColor(Color.red);
    
    g.drawString("Fuel: ", 5, 20);
      
    g.fillRect(0, 30, lander.getFuel(), 10);
  }
   
  public void loseScreen(Graphics g){
    lander.changeSprite(2); 
    lander.draw(g);
    explosionSound.playSound(false);
      
    g.drawString("You lose!", getWidth() / 2, getHeight() / 2);
    stopGame();
  }
  
  public void winScreen(Graphics g){
    scores.add((Integer)points);
    g.drawString("You win!", (getWidth() / 2), getHeight() / 2);
    stopGame();
  }
  
   public boolean running(){
     return running;
   }
   
   public void startGame(){
     running = true;
     start = true;
     music.playSound(true);
     timer.start();
   }
   
   public String getGameName(){
     return "AstroLander";
   }
   
   public void pauseGame(){
     running = false;
     
     timer.stop();
   }
   
   public String getInstructions(){
     return "Press the up arrow key to propel the lander up. \nIt will move right automatically and be affected by gravity. \nNavigate the lander to the goal.";
   }
   
   public String getCredits(){
     return "By Lake Yin";
   }
   
   public String getHighScore(){
     int highScore = 0;
     
     for(Integer score : scores)
       highScore = Math.max(score, highScore);
     
     return Integer.toString(highScore);
   }
   
   public void stopGame(){
     running = false;
     
     timer.stop();
     
     points = 0;
   }
   
   public int getPoints(){
     return points;
   }
                            
}