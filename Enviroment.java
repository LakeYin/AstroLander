import java.awt.*;
import java.util.ArrayList;

public class Enviroment extends ArrayList<TerrainBlock>{   
  public Enviroment(int blockCount, int width, int height){
    for(int i = 0; i < width - width / blockCount; i += width / blockCount){
      int blockHeight = (int)(Math.random() * 3 / 4 * height);
      
      add(new TerrainBlock(i, height - blockHeight, width / blockCount, blockHeight));
    }
    
    int lastHeight = this.get(this.size() - 1).getYSize() + (int)(Math.random() * 3 / 4 * height / 4);
    add(new TerrainBlock(width - width / blockCount, height - lastHeight, width / blockCount, lastHeight));
  }
  
  public Enviroment(int width, int height){
    this((int)(Math.random() * 5) + 5, width, height);
  }
  
  public void draw(Graphics g){
    for(GameObject terrain : this)
      terrain.draw(g); 
  }
}