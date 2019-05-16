import java.awt.*;

public abstract class GameObject extends Rectangle{
  public GameObject(int xPos, int yPos, int xSize, int ySize){
     super(xPos, yPos, xSize, ySize);
  }
  
  public boolean isTouching(GameObject x){
    return intersects(x);
  }
  
  public abstract void draw(Graphics g);
  
  public int getXSize(){
    return (int)(super.getWidth());
  }
  
  public int getYSize(){
    return (int)(super.getHeight());
  }
  
  public int getXPos(){
    return (int)(super.getX());
  }
  
  public int getYPos(){
    return (int)(super.getY());
  }
}