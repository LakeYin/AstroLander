import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class GameSprite extends GameObject{
  protected BufferedImage[] sprites;
  protected BufferedImage activeSprite;
  
  public GameSprite(int xPos, int yPos, int xSize, int ySize, File[] images){
    super(xPos, yPos, xSize, ySize);
    sprites = new BufferedImage[images.length];
    
    for(int i = 0; i < images.length; i++){
      try {
        sprites[i] = ImageIO.read(images[i]);
      } catch (IOException e){
        System.out.println("Could not get picture");
      }
    }
    
    activeSprite = sprites[0];
  }
  
  public void draw(Graphics g){
    g.drawImage(activeSprite, getXPos(), getYPos(), null);
  }
  
  public void changeSprite(int i){
    activeSprite = sprites[i];
  }
}