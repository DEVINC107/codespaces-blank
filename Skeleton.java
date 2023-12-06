import java.util.ArrayList;

public class Skeleton extends LongRangeEnemy {
  
  public Skeleton() {
    super("Skeleton", "💀", 100, 1600, 600, 25, "🦴");

    ArrayList <Sprite> drops = new ArrayList<>();
    drops.add(new ChugJug());
    drops.add(new Coin());

    setDrops(drops);
  }
}