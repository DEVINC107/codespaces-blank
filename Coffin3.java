public class Coffin3 extends EnemySpawner {
  public Coffin3(int cooldown) {
    super("Coffin3", "⚰️ ", cooldown);
  }

  public @Override void spawn() {
    Enemy enemy = new Demon();
    SpriteTracker.setSpritePosition(enemy, SpriteTracker.getSpritePosition(this));
  }
}