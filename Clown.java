import java.util.ArrayList;

public class Clown extends CloseRangeEnemy {

  public Clown() {
    super("Clown", "🤡", 200, 800, 400, 35);

    ArrayList <Sprite> drops = new ArrayList<>();
    drops.add(new Donut());
    drops.add(new Donut());
    drops.add(new Coin());
    drops.add(new Coin());

    setDrops(drops);
  }
}