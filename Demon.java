import java.util.ArrayList;

public class Demon extends LongRangeEnemy {
  
  public Demon() {
    super("Demon", "👿", 250, 1400, 600, 60, "🔴");

    ArrayList <Sprite> drops = new ArrayList<>();
    drops.add(new ChugJug());
    drops.add(new ChugJug());
    drops.add(new Donut());
    drops.add(new Ticket());
    drops.add(new Coin());
    drops.add(new Coin());
    drops.add(new Coin());

    setDrops(drops);
  }
}