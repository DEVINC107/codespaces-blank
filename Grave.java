public class Grave extends EnemySpawner {
  public Grave(int cooldown) {
    super("Grave", "🪦 ", cooldown);
  }

  public @Override void spawn() {
    Enemy enemy = new Zombie();
    SpriteTracker.setSpritePosition(enemy, SpriteTracker.getSpritePosition(this));
  }
}