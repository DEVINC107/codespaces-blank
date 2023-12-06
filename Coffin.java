public class Coffin extends EnemySpawner {
  public Coffin(int cooldown) {
    super("Coffin", "⚰️ ", cooldown);
  }

  public @Override void spawn() {
    Enemy enemy = new Skeleton();
    SpriteTracker.setSpritePosition(enemy, SpriteTracker.getSpritePosition(this));
  }
}