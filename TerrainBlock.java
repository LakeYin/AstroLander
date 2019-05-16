import java.awt.*;

public class TerrainBlock extends GameObject{
   public TerrainBlock(int xPos, int yPos, int xSize, int ySize){
     super(xPos, yPos, xSize, ySize);
  }
  
  public boolean touchTop(GameObject x){
    return x.getYPos() + x.getYSize() >= getYPos() && 
           x.getXPos() >= getXPos() && x.getXPos() + x.getWidth() <= getXPos() + getWidth();
  }
  
  public boolean touchWall(GameObject x){
    return x.getXPos() + x.getXSize() > getXPos() && x.getXPos() < getXPos() + getXSize() && 
      x.getYPos() + x.getYSize() > getYPos() && !touchTop(x); 
  }
  
  public void draw(Graphics g){
    g.setColor(new Color(211, 211, 211));
    g.fillRect(getXPos(), getYPos(), getXSize(), getYSize()); 
  }
}