public class UFO extends EnemySpawner {
  public UFO(int cooldown) {
    super("UFO", "🛸", cooldown);
  }

  public @Override void spawn() {
    Enemy enemy = new Alien();
    SpriteTracker.setSpritePosition(enemy, SpriteTracker.getSpritePosition(this));
  }
}