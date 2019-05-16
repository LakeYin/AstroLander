import java.io.File;

public class Goal extends GameSprite{
  public static final int SPRITE_X_SIZE = 16, SPRITE_Y_SIZE = 20; 
  
  public Goal(int xPos, int yPos, File[] sprites){
     super(xPos, yPos, 16, 20, sprites);
  }
}