public class Grave2 extends EnemySpawner {
  public Grave2(int cooldown) {
    super("Grave2", "🪦 ", cooldown);
  }

  public @Override void spawn() {
    Enemy enemy = new Clown();
    SpriteTracker.setSpritePosition(enemy, SpriteTracker.getSpritePosition(this));
  }
}