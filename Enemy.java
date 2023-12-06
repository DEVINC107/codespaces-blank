import java.util.HashSet;
import java.util.ArrayList;

public class Enemy extends Entity {
  private static HashSet<Enemy> enemies = new HashSet<>();
  private static ArrayList<Enemy> toRemove = new ArrayList<>();
  private static ArrayList<Sprite> drops = new ArrayList<>();
  private static final int UPDATE_EVERY = 200;
  private static boolean inLoop = true;
  private static Thread t;

  public Enemy(String id, String icon) {
    super("Enemy", id, icon);
    enemies.add(this);
  }
  
  public void destroy() {
    if (inLoop) {
      toRemove.add(this);
    } else {
      if (toRemove.contains(this)) {
        toRemove.remove(this);
      }
      enemies.remove(this);
      SpriteTracker.removeSprite(this);
    }
  }

  /*private static final Runnable update = () -> {
    while (true) {
      Helper.wait(UPDATE_EVERY);
      if (Game.isPaused) {
        continue;
      }
      inLoop = true;
      for (Enemy enemy : enemies) {
        enemy.update();
      }
      inLoop = false;
      for (int i = 0; i < toRemove.size(); i ++) {
        toRemove.get(i).destroy();
      }
    }
  };*/

  public static boolean update() {
    inLoop = true;
    boolean render = false;
    for (Enemy enemy : enemies) {
      if (!SpriteTracker.spriteExists(enemy)) {
        continue;
      }
      if (enemy.enemyUpdate()) {
        if (!SpriteTracker.spriteExists(enemy)) {
          continue;
        }
        render = true;
        //POSSIBLE BUG, YOLO
        ArrayList<Sprite> atPos = SpriteTracker.getSpritesAt(SpriteTracker.getSpritePosition(enemy));
        for (int i = 0; i < atPos.size(); i ++) {
          Sprite other = atPos.get(i);
          if (other.equals(enemy)) {
            continue;
          }
          enemy.collide(other);
        }
      }
    }
    inLoop = false;
    for (int i = 0; i < toRemove.size(); i ++) {
      toRemove.get(i).destroy();
    }
    boolean otherRender = Bullet.update();
    return render || otherRender;
  }

  public void damage(int damageDealt) {
    if (getSpriteClassId().equals("Wizard")) {
      Player.addToLog("Wizard has health " + this.getHealth());
    }
    setHealth(getHealth() - damageDealt);
    if (getHealth() <= 0) {
      for (int i = 0; i < drops.size(); i++) {
        SpriteTracker.setSpritePosition(drops.get(i), SpriteTracker.getSpritePosition(this));
      }
      if (getSpriteClassId().equals("Wizard")) {
        Wizard.cleanup();
        Player.addToLog("Wizard died");
      }
      destroy();
    }
  }

  public void setDrops(ArrayList<Sprite> drops) {
    this.drops = drops;
  }


  public static int getLoopsLeft(int time) {
    return time/UPDATE_EVERY - 1;
  }

  public boolean enemyUpdate() {//to be overriden
    return false;
  }
}