import java.util.concurrent.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Bullet extends Sprite {
  private vec2 vel;
  private static Thread t;
  private static HashSet<Bullet> bullets = new HashSet<>();
  private static ArrayList<Bullet> toRemove = new ArrayList<>();
  private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private static boolean doClear = false;
  private Entity owner;
  //TODO: make this work for more spreads
  private vec2 oneTileSpread;
  private static boolean inLoop = false;
  private int delay;
  /*private static final Runnable update = () -> {
    while (true) {
      Helper.wait(200);
      if (Game.isPaused) {
        continue;
      }
      boolean render = bullets.size() > 0;
      Iterator<Bullet> it = bullets.iterator();
      inLoop = true;
      while (it.hasNext()) {
        it.next().move();
      }
      inLoop = false;
      for (int i = 0; i < toRemove.size(); i++) {
        Bullet bullet = toRemove.get(i);
        bullets.remove(bullet);
        SpriteTracker.removeSprite(bullet);
        toRemove.remove(i);
      }
      if (render) {
        Grid.render();
      }
    }
  };*/

  public static boolean update() {
    boolean render = bullets.size() > 0;
    inLoop = true;
    for (Bullet bullet : bullets) {
      if (doClear) {
        break;
      }
      if (SpriteTracker.spriteExists(bullet)) {
        bullet.move(); 
      }
    }
    inLoop = false;
    if (doClear) {
      //Player.addToLog("" + bullets.size());
      for (Bullet bullet : bullets) {
        bullet.destroy();
      }
      //Player.addToLog(""+iter);
      doClear = false;
      render = true;
    }
    for (int i = 0; i < toRemove.size(); i++) {
      toRemove.get(i).destroy();
    }
    toRemove.clear();
    return render;
  }

  public static void roomChanged() {
    bullets = new HashSet<>();
  }

  public boolean exists() {
    return bullets.contains(this) && !toRemove.contains(this);
  }

  public void destroy() {
    if (inLoop) {
      toRemove.add(this);
    }
    if (!inLoop) {
      if (bullets.contains(this)) {
        bullets.remove(this);
      }
      SpriteTracker.removeSprite(this);
    }
  }

  public int getDamage() {
    return owner.getBulletDamage();
  }

  public static void clear() {
    for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
      Bullet bullet = iterator.next();
      SpriteTracker.removeSprite(bullet);
    }
    doClear = true;
    bullets = new HashSet<>();
  }

  public Bullet(Entity owner, vec2 pos, vec2 vel, String icon) {
    super("Bullet", "Bullet", icon);
    if (owner.getSpriteClassId().equals("Player")) {
      Player player = Player.getPlayer();
      if (player.getAmmo() > 0) {
        player.setAmmo(player.getAmmo() - 1);
      } else {
        Player.addToLog("No ammo😂");
        if (!Game.isDev) {
return;
        }
      }
    }
    SpriteTracker.setSpritePosition(this, pos);
    this.delay = 0;
    this.owner = owner;
    this.vel = vel;
    bullets.add(this);
    ArrayList<Sprite> atPos = SpriteTracker.getSpritesAt(pos);
    for (int i = 0; i < atPos.size(); i ++) {
      collide(atPos.get(i));
    }
    if (!exists()) {
      return;
    }
  }

  public void setDelay(int ticks) {
    this.delay = ticks;
  }

  public int getDelay() {
    return this.delay;
  }

  public void move() {
    vec2 pos = SpriteTracker.getSpritePosition(this);
    vec2 newPos = pos.move(vel);
    if (oneTileSpread != null) {
      newPos = newPos.move(oneTileSpread);
      oneTileSpread = null;
    }
    if (getDelay() > 0) {
      setDelay(getDelay() - 1);
      return;
    }
    if (!Grid.withinBounds(newPos)) {
      outOfBounds();
      return;
    }
    ArrayList<Sprite> atPos = SpriteTracker.getSpritesAt(newPos);
    for (int i = 0; i < atPos.size(); i ++) {
      Sprite other = atPos.get(i);
      collide(other);
      if (!exists()) {
        return;
      }
    }
    SpriteTracker.setSpritePosition(this, newPos);
  }

  public vec2 getVel() {
    return this.vel;
  }

  /*
    Dear james gosling,
    sorry for the collide method below
    I know oop features such as inheritance or polymorphism or whatever
    are supposed to be used
    to do different behavior on different objects
    but isn't comparing a string so much easier 😢
  */
  @Override
  public void collide(Sprite other) {
    if (other == owner) {
      return;
    }
    if (other.getSpriteSuperClassId().equals("Bullet")) {
      
    }
    if (other.getSpriteSuperClassId().equals("Enemy")) {
      Enemy enemy = (Enemy) other;
      //Player.addToLog("Dealt " + owner.getBulletDamage() + " damage to " + enemy.getIcon());
      enemy.damage(owner.getBulletDamage());
      destroy();
    }
    if (other.getSpriteClassId().equals("Player")) {
      Player player = (Player) other;
      //Player.addToLog("Received " + owner.getBulletDamage() + " damage");
      player.damage(owner.getBulletDamage());
      destroy();
    }
    if (other.getSpriteClassId().equals("Wall")) {
      destroy();
    }
    if (other.getSpriteSuperClassId().equals("EnemySpawner")) {
      EnemySpawner spawner = (EnemySpawner) other;
      spawner.damage(owner.getBulletDamage());
    }
  }

  public void setOneTileSpread(vec2 spread) {
    oneTileSpread = spread;
  }

  public void outOfBounds() {
    destroy();
  }
}

/*
import java.util.concurrent.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;
import java.util.HashSet;

public class Bullet extends Sprite {
  private vec2 vel;
  private static HashSet<Bullet> bullets = new HashSet<>();
  private static ArrayList<Bullet> toRemove = new ArrayList<>();
  private static boolean iterBullets = true;
  private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private Entity owner;
  private static boolean inLoop = false;
  
  private static final Runnable update = () -> {
    while (true) {
      Helper.wait(200);
      boolean render = bullets.size() > 0;
      Iterator<Bullet> it = bullets.iterator();
      inLoop = true;
      while (it.hasNext()) {
        it.next().move();
      }
      inLoop = false;
      for (int i = 0; i < toRemove.size(); i++) {
        Bullet bullet = toRemove.get(i);
        bullets.remove(bullet);
        SpriteTracker.removeSprite(bullet);
        toRemove.remove(i);
      }
      if (render) {
        Grid.render();
      }
    }
  };

  public boolean exists() {
    return bullets.contains(this) && !toRemove.contains(this);
  }

  public void destroy() {
    toRemove.add(this);
    if (!inLoop) {
      bullets.remove(this);
      SpriteTracker.removeSprite(this);
    }
  }
  
  public Bullet(Entity owner, vec2 pos, vec2 vel) {
    super("Bullet", "🔥");
    SpriteTracker.setSpritePosition(this, pos);
    this.vel = vel;
    bullets.add(this);
    Grid.render();
  }

  public void move() {
    vec2 pos = SpriteTracker.getSpritePosition(this);
    vec2 newPos = pos.move(vel);
    if (!Grid.withinBounds(newPos)) {
      outOfBounds();
      return;
    }
    ArrayList<Sprite> atPos = SpriteTracker.getSpritesAt(newPos);
    for (int i = 0; i < atPos.size(); i ++) {
      Sprite other = atPos.get(i);
      collide(other);
      if (!exists()) {
        return;
      }
    }
    SpriteTracker.setSpritePosition(this, newPos);
  }

  public vec2 getVel() {
    return this.vel;
  }

  public @Override void collide(Sprite other) {
    destroy();
  }

  public @Override void outOfBounds() {
    destroy();
  }

  public static void startUpdating () {
    Thread t = new Thread(update);
    t.start();
  }
}
*/