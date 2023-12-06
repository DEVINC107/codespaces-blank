import java.util.ArrayList;

public class Ghost extends LongRangeEnemy {
  
  public Ghost() {
    super("Ghost", "👻", 160, 1200, 600, 35, "⚪");

    ArrayList <Sprite> drops = new ArrayList<>();
    drops.add(new ChugJug());
    drops.add(new Donut());
    drops.add(new Coin());
    drops.add(new Coin());

    setDrops(drops);
  }
}