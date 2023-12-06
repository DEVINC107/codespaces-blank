import java.util.HashSet;
import java.util.ArrayList;

public class EnemySpawner extends Entity{
  private static int cooldown;
  private int ticksLeft;
  private static Thread t;
  private static boolean inLoop = false;
  private static ArrayList<EnemySpawner> toRemove = new ArrayList<>();
  private static HashSet<EnemySpawner> spawners = new HashSet<>();
  private static final int UPDATE_EVERY = 200;
  
  public EnemySpawner(String id, String icon, int cooldown) {
    super("EnemySpawner", id, icon);
    this.cooldown = cooldown;
    this.ticksLeft = cooldown;
    spawners.add(this);
  }

  public void destroy() {
    if (inLoop) {
      toRemove.add(this);
    } else {
      if (toRemove.contains(this)) {
        toRemove.remove(this);
      }
      spawners.remove(this);
      SpriteTracker.removeSprite(this);
    }
  }

  public void decrementTick() {
    ticksLeft --;
  }

  public void setCooldown(int cooldown) {
    this.cooldown = cooldown;
  }

  public void resetTicksLeft() {
    this.ticksLeft = cooldown;
  }

  public int getCooldown() {
    return cooldown;
  }

  public int getTicksLeft() {
    return ticksLeft;
  }

  public void spawn() {
    //TO BE OVERWRITTEN
  }

  public static int getUpdateEvery() {
    return UPDATE_EVERY;
  }

  private static final Runnable update = () -> {
    while (true) {
      Helper.wait(UPDATE_EVERY);
      if (Game.isPaused) {
        continue;
      }
      boolean render = false;
      inLoop = true;
      for (EnemySpawner spawner : spawners) {
        if (!SpriteTracker.spriteExists(spawner)) {
          continue;
        }
        spawner.decrementTick();
        if (spawner.getTicksLeft() == 0) {
          spawner.spawn();
          spawner.resetTicksLeft();
          render = true;
        }
      }
      inLoop = false;
      for (int i = 0; i < toRemove.size(); i++) {
        EnemySpawner enemySpawner = toRemove.get(i);
        spawners.remove(enemySpawner);
        SpriteTracker.removeSprite(enemySpawner);
        toRemove.remove(i);
      }
      boolean otherRender = Enemy.update();
      boolean bombsRender = Grid.updateBombs();
      boolean beamsRender = Grid.updateBeams();
      render = render || otherRender || bombsRender || beamsRender;
      if (render) {
        Grid.render();
      }
    }
  };

  public static void startSpawning() {
    t = new Thread(update);
    t.start();
  }
}