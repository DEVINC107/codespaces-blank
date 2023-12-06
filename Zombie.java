import java.util.ArrayList;

public class Zombie extends CloseRangeEnemy {

  public Zombie() {
    super("Zombie", "🧟", 100, 1000, 400, 25);

    ArrayList <Sprite> drops = new ArrayList<>();
    drops.add(new Donut());
    drops.add(new Coin());

    setDrops(drops);
  }
}