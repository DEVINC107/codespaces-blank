import java.util.ArrayList;

public class Alien extends CloseRangeEnemy {
  
  public Alien() {
    super("Alien", "👽", 1, 50000000, 200, 400);
    
    ArrayList <Sprite> drops = new ArrayList<>();
    drops.add(new ChugJug());
    drops.add(new Donut());
    drops.add(new Ticket());
    drops.add(new Ticket());
    drops.add(new Coin());
    drops.add(new Coin());
    drops.add(new Coin());
    drops.add(new Coin());
    drops.add(new Coin());
    
    setDrops(drops);
  }
}