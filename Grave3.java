public class Grave3 extends EnemySpawner {
  public Grave3(int cooldown) {
    super("Grave3", "🪦 ", cooldown);
  }

  public @Override void spawn() {
    Enemy enemy = new Ogre();
    SpriteTracker.setSpritePosition(enemy, SpriteTracker.getSpritePosition(this));
  }
}