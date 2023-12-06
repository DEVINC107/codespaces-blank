public class Coffin2 extends EnemySpawner {
  public Coffin2(int cooldown) {
    super("Coffin2", "⚰️ ", cooldown);
  }

  public @Override void spawn() {
    Enemy enemy = new Ghost();
    SpriteTracker.setSpritePosition(enemy, SpriteTracker.getSpritePosition(this));
  }
}