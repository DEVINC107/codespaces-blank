import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;

public class Wizard extends Enemy {
  String[] attacks;
  int attackIdx = 0;
  private static HashMap<Sprite, Integer> portalsLeft = new HashMap<>();
  private String[][] attackPattern = {
    {"shoot", "pass", "pass", "shoot", "bomb"},
    {"pass", "pass","pass","pass","pass", "ladder"},
    {"portal", "explode", "pass", "pass", "pass", "shoot", "shoot", "pass", "shoot", "shoot"}
  };/*{
    {"portal", "explode", "bomb", "pass", "pass", "pass", "pass","pass", "pass", "pass", "pass","pass", "pass", "pass", "pass"},
    {"portal", "explode", "shoot", "shoot", "shoot", "pass", "pass", "pass", "pass", "shoot", "shoot", "shoot"},
    {"portal", "portal", "portal", "explode", "bomb", "shoot", "shoot", "shoot", "bomb"},
    {"ladder", "pass"}
  };*/
  private String[][] blitzAttackPattern = {
    {"ladder", "ladder", "ladder", "ladder"},
    {"explode", "bombthrow", "bombthrow", "portal", "explode", "portal", "explode"},
    {"ladder", "portal", "ladder", "ladder", "pass", "pass", "pass", "pass", "pass", "pass", "pass", "pass", "pass"},
    {"ladder", "portal", "pass", "pass", "shoot", "shoot", "shoot", "shoot", "shoot", "shoot", "shoot", "shoot", "shoot", "shoot"},
    {"portal", "portal", "portal", "portal", "explode", "bomb", "shoot", "pass", "pass", "pass", "shoot"},
    {"portal", "pass", "pass", "pass", "shoot", "shoot", "shoot", "shoot", "shoot", "shoot"},
    {"portal", "pass", "shoot", "pass", "pass", "portal", "portal", "portal", "shoot", "shoot", "bomb"},
  };
  
  public Wizard() {
    super("Wizard", "🧙");
    attacks = attackPattern[0];
  }

  public static void cleanup() {
    for (HashMap.Entry<Sprite, Integer> entry : portalsLeft.entrySet()) {
      SpriteTracker.removeSprite(entry.getKey());
    }
    portalsLeft.clear();
  }

  public boolean enemyUpdate() {
    boolean render = false;
    String[][] pattern = getHealth() > (getMaxHealth()/2) ? attackPattern : blitzAttackPattern;
    String attack = attacks[attackIdx];
    vec2 pos = SpriteTracker.getSpritePosition(this);
    vec2 playerPos = SpriteTracker.getSpritePosition(Player.getPlayer());
    if (attack.equals("wall")) {
      int amount = ((int) Math.random() * 8) + 10;
      vec2 bounds = SpriteTracker.getBounds();
      for (int i = 1; i <= amount; i++) {
        vec2 placeAt = new vec2((int)(Math.random() * bounds.x), (int) (Math.random() * bounds.y));
        if (!Grid.withinBounds(placeAt)) {
          continue;
        }
        ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(placeAt);
        for (int a = 0; a < atPos.size(); a++) {
          if (!atPos.get(a).getSpriteClassId().equals("Wall")) {
            SpriteTracker.setSpritePosition(new Wall(), placeAt);
          }
        }
      }
      render = true;
    }
    if (attack.equals("explode")) {
      for (int x = -1; x < 2; x += 2) {
        for (int y = -1; y < 2; y += 2) {
          if (!Grid.withinBounds(new vec2(pos.x + x, pos.y + y))) {
            continue;
          }
          Grid.useBomb(this, pos.move(x, y));
        }
      }
    }
    if (attack.equals("shoot")) {
      int hor = playerPos.x - pos.x;
      int vert = playerPos.y - pos.y;
      if (Math.abs(hor) > Math.abs(vert)) {
        Bullet bullet = new Bullet(this, new vec2(pos.x, playerPos.y), new vec2(hor > 0 ? 1 : -1, 0), "🟢");
        bullet.setDelay(5);
      } else {
        Bullet bullet = new Bullet(this, new vec2(playerPos.x, pos.y), new vec2(0, vert > 0 ? 1 : -1), "🟢");
        bullet.setDelay(5);
      }
      render = true;
    }
    if (attack.equals("bomb")) {
      int amount = ((int) Math.random() * 8) + 10;
      vec2 bounds = SpriteTracker.getBounds();
      for (int i = 1; i <= amount; i++) {
        Grid.useBomb(this, new vec2((int)(Math.random() * bounds.x), (int) (Math.random() * bounds.y)));
      }
      render = true;
    }
    if (attack.equals("portal")) {
      boolean spawned = false;
      for (int x = -1; x < 2; x += 2) {
        if (spawned) {
          continue;
        }
        if (Grid.withinBounds(playerPos.move(x,0)) && !Grid.wallFound(playerPos.move(x, 0))) {
          Sprite portal = new Sprite("Portal", "Portal", "🌀");
          SpriteTracker.setSpritePosition(portal, pos);
          portalsLeft.put(portal, 5);
          SpriteTracker.setSpritePosition(this, playerPos.move(x, 0));
          spawned = true;
        }
      }
      for (int y = -1; y < 2; y += 2) {
        if (spawned) {
          continue;
        }
        if (Grid.withinBounds(playerPos.move(0,y)) && !Grid.wallFound(playerPos.move(0, y))) {
          Sprite portal = new Sprite("Portal", "Portal", "🌀");
          SpriteTracker.setSpritePosition(portal, pos);
          portalsLeft.put(portal, 5);
          SpriteTracker.setSpritePosition(this, playerPos.move(0, y));
          spawned = true;
        }
      }
      
      render = true;
    }
    if (attack.equals("ladder")) {
      boolean spawned = false;
      for (int x = -1; x < 2; x += 2) {
        if (spawned) {
          continue;
        }
        if (Grid.withinBounds(playerPos.move(x,0)) && !Grid.wallFound(playerPos.move(x, 0))) {
          Pentagram portal = new Pentagram();
          portal.x = playerPos.move(x, 0).x;
          portalsLeft.put(portal, 15);
          SpriteTracker.setSpritePosition(portal, playerPos.move(x, 0));
          spawned = true;
        }
      }
      for (int y = -1; y < 2; y += 2) {
        if (spawned) {
          continue;
        }
        if (Grid.withinBounds(playerPos.move(0,y)) && !Grid.wallFound(playerPos.move(0, y))) {
          Pentagram portal = new Pentagram();
          portal.x = playerPos.x;
          portalsLeft.put(portal, 15);
          SpriteTracker.setSpritePosition(portal, playerPos.move(0, y));
          spawned = true;
        }
      }

      render = true;
    }
    attackIdx ++;
    if (attackIdx > attacks.length - 1) {
      attacks = pattern[(int) (Math.random() * pattern.length)];
      attackIdx = 0;
    }
    ArrayList<Sprite> toRemove = new ArrayList<>();
    
    for (HashMap.Entry<Sprite, Integer> entry : portalsLeft.entrySet()) {
      Sprite sprite = entry.getKey();
      portalsLeft.put(sprite, entry.getValue() - 1);
      if (entry.getValue() == 0) {
        toRemove.add(sprite);
      }
      render = true;
    }
    for (int i = 0; i < toRemove.size(); i ++) {
      Sprite portal = toRemove.get(i);
      if (portal.getSpriteClassId().equals("Pentagram")) {
        Pentagram pent = (Pentagram) toRemove.get(i);
        Grid.beam(pent);
      }
      SpriteTracker.removeSprite(portal);
      portalsLeft.remove(portal);
    }
    return render;
  }
  
}