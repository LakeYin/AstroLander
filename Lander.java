import java.io.File;

public class Lander extends GameSprite implements PhysicsObject{
  public static final int SPRITE_X_SIZE = 20, SPRITE_Y_SIZE = 20; 
  
  private int fuel;
  
  private int vertVelocity;
  private int horiVelocity;
  
  private final int GRAVITY;
  private final int ACCELERATION;
  private final int TERMINAL_VELOCITY = 1;
  
  public Lander(int xPos, int yPos, int startFuel, int gravity, int thrustPower, File[] sprites){
    super(xPos, yPos, 20, 20, sprites);
    
    fuel = startFuel;
    ACCELERATION = thrustPower;
    GRAVITY = gravity;
  }
  
  public void thrust(){
    if(fuel > 0){
      fuel--;
      vertVelocity = Math.max(vertVelocity - ACCELERATION, -5);
      changeSprite(1);
    }
  }
  
  public void decend(){
    vertVelocity = Math.min(vertVelocity + GRAVITY, TERMINAL_VELOCITY);
  }
  
  public void land(){
    vertVelocity = 0;
    horiVelocity = 0;
  }
  
  public void updatePosition(int x, int y){
    setLocation(getXPos() + x , getYPos() + y);
  }
  
  public void updatePosition(){
    setLocation(getXPos() + horiVelocity , getYPos() + vertVelocity);
  }
  
  public int getFuel(){
    return fuel;
  }
  
  public int getVerticalVelocity(){
    return vertVelocity;
  }
  
  public int getHorizontalVelocity(){
    return horiVelocity;
  }
  
  public void setVerticalVelocity(int v){
    vertVelocity = v;
  }
  
  public void setHorizontalVelocity(int v){
    horiVelocity = v;
  }
}