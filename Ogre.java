import java.util.ArrayList;

public class Ogre extends CloseRangeEnemy {

  public Ogre() {
    super("Ogre", "👹", 340, 800, 400, 50);

    ArrayList <Sprite> drops = new ArrayList<>();
    drops.add(new Donut());
    drops.add(new Donut());
    drops.add(new ChugJug());
    drops.add(new Coin());
    drops.add(new Coin());
    drops.add(new Coin());
    drops.add(new Ticket());

    setDrops(drops);
  }
}