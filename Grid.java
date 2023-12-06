import java.util.Scanner;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;

public class Grid {
  private static int rows;
  private static int columns;
  private static Sprite spawn;
  private static Player plr;
  private static StringBuilder sb = new StringBuilder();
  private static PrintWriter pw = new PrintWriter(System.out);
  
  public static void createBorder(int r, int c) {
    rows = r;
    columns = c;
    topBottomBorder = "";
    for (int k = 0; k < columns + 2; k++) {
      topBottomBorder += borderIcon;
    }
    topBottomBorder += "\n";
  }

  public static void generate(int rows, int columns) {
    plr = Player.getPlayer();
    Rooms.setCurrentRoom("Lobby");
    
    //Collectibles testing
    /*for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        if ((int) (Math.random() * 8) == 0 && !wallFound(new vec2(x, y))) {
          SpriteTracker.setSpritePosition(new Bomb(), new vec2(x, y));
        } else if ((int) (Math.random() * 10) == 0 && !wallFound(new vec2(x, y))) {
            SpriteTracker.setSpritePosition(new Ticket(), new vec2(x, y));
        }
      }
    }*/
    render();
  }

  public static void moveToSpawn() {
    spawn = SpriteTracker.findFirstSpriteOfType("Spawn", rows, columns);
    SpriteTracker.setSpritePosition(plr, SpriteTracker.getSpritePosition(spawn));
  }

  public static void roomChanged(int newRows, int newColumns) {
    clearAllBombs();
    clearAllBeams();
    rows = newRows;
    columns = newColumns;
    createBorder(rows, columns);
    moveToSpawn();
  }
  
  //Grid.Render
  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  final static String[] renderingPriority = { // lower index = higher prioirty
    "Player",
    "Enemy",
    "Explosion",
    "FusedBomb",
    "Wall",
    "Portal",
    "EnemySpawner",
    "Bullet",
    "Spawn",
    "Salesman",
    "Collectible",
  };

  public static String[] getRenderingPriority() {
    return renderingPriority;
  }

  private static String borderIcon = "🟥";
  private static String topBottomBorder = "";
  
  public static void render() {
    //https://stackoverflow.com/questions/18766780/stringbuilder-reset-or-create-a-new
    sb.setLength(0);
    sb.trimToSize();
    sb.append(topBottomBorder);
    
    for (int y = 0; y < rows; y++) {
      sb.append(borderIcon);
      for (int x = 0; x < columns; x++) {
        boolean isBeam = false;
        for (HashMap.Entry<Sprite, Integer> entry : beams.entrySet()) {
          Pentagram pent = (Pentagram) entry.getKey();
          int beamX = pent.x;
          
          if (beamX - 1 == x || beamX == x || beamX + 1 == x) {
            isBeam = true;
            break;
          }
        }
        if (isBeam) {
          sb.append("🟪");
          continue;
        }
        Sprite sprite = SpriteTracker.getSpriteAt(x, y);
        sb.append(sprite == null ? "  " : sprite.getIcon());
      }
      sb.append(borderIcon + "\n");
    }
    sb.append(topBottomBorder);
    clear();
    pw.print(sb.toString());
    pw.flush();
    plr.printInfo();
    //Input.printInfo();
  }

  public static boolean withinBounds(int x, int y) {
    return x >= 0 && x < columns && y >= 0 && y < rows;
  }

  public static boolean withinBounds(vec2 pos) {
    return withinBounds(pos.x, pos.y);
  }

  public static boolean withinBounds(Sprite sprite) {
    return withinBounds(SpriteTracker.getSpritePosition(sprite));
  }

  public static void autoPickItems(vec2 newPos) {
    ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(newPos);
    int itemCounter = 0;
    String logString = "You picked up:";
    for (int i = 0; i < atPos.size(); i++) {
      if (atPos.get(i).getSpriteSuperClassId().equals("Collectible") && atPos.get(i).autoPickUp()) {
        itemCounter++;
        logString += "\n" + atPos.get(i).getIcon() + " >>> " + atPos.get(i).pickUp();
        SpriteTracker.removeSprite(atPos.get(i));
      }
    }
    if (itemCounter > 0) {
      render();
      Player.addToLog(logString);
    }
  }
  
  public static String move(int x, int y) {
    vec2 oldPos = SpriteTracker.getSpritePosition(plr);
    int newX = oldPos.x + x;
    int newY = oldPos.y + y;
    vec2 newPos = new vec2(newX, newY);
    
    if (withinBounds(newPos) && !wallFound(newPos)) {
      SpriteTracker.setSpritePosition(plr, newPos);
      autoPickItems(newPos);
      render();
      return "Moved";
    } else {
      return "There is a wall";
    }
  }

  public static String pickUp() {
    ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(plr);
    int itemCounter = 0;
    String returnString = "You picked up:";
    for (int i = atPos.size() - 1; i >= 0; i--) {
      if (atPos.get(i).getSpriteSuperClassId().equals("Collectible")) {
        itemCounter++;
        returnString += "\n" + atPos.get(i).getIcon() + " >>> " + atPos.get(i).pickUp();
        SpriteTracker.removeSprite(atPos.get(i));
      }
    }
    if (itemCounter > 0) {
      render();
      return returnString;
    }
    return "There is nothing to pick up...";
  }

  public static int getRows() {
    return rows;
  }

  public static int getColumns() {
    return columns;
  }

  public static HashMap<Sprite, Integer> bombs = new HashMap<>();
  public static HashMap<Sprite, Integer> explosions = new HashMap<>();
  final static int bombRadius = 1;

  public static void clearAllBombs() {
    bombs = new HashMap<>();
    explosions = new HashMap<>();
  }

  public static HashMap<Sprite, Integer> beams = new HashMap<>();
  final static int beamWidth = 3;

  public static void clearAllBeams() {
    beams = new HashMap<>();
  }

  public static void beam(Pentagram pentagram) {
    beams.put(pentagram, 4);
  }

  public static void useBomb(Entity entity, vec2 pos) {
    if (!Grid.withinBounds(pos)) {
      return;
    }
    String type = entity.getSpriteSuperClassId();
    if (type.equals("Player")) {
      if (plr.getBombs() > 0) {
        plr.setBombs(plr.getBombs() - 1);
        FusedBomb bomb = new FusedBomb(type);
        SpriteTracker.setSpritePosition(bomb, pos);
        bombs.put(bomb, 2000);
        render();
      } else {
        plr.addToLog("You don't have any bombs");
        render();
      }
    } else {
      FusedBomb bomb = new FusedBomb(type);
      SpriteTracker.setSpritePosition(bomb, pos);
      bombs.put(bomb, 2000);
      render();
    }
  }

  public static boolean updateBeams() {
    vec2 playerPos = SpriteTracker.getSpritePosition(Player.getPlayer());
    ArrayList<Pentagram> toRemove = new ArrayList<>();
    boolean render = false;
    
    for (HashMap.Entry<Sprite, Integer> entry : beams.entrySet()) {
      Pentagram pentagram = (Pentagram) entry.getKey();
      Integer remaining = entry.getValue();
      
      int beamX = pentagram.x;

      if (beamX - 1 == playerPos.x || beamX == playerPos.x || beamX + 1 == playerPos.x) {
        Player.getPlayer().damage(40);
      }

      if (remaining > 0) {
        beams.put(pentagram, remaining - 1);
      }

      if (remaining == 0) {
        toRemove.add(pentagram);
      }
      render = true;
    }

    for (int i = 0; i < toRemove.size(); i ++) {
      beams.remove(toRemove.get(i));
      SpriteTracker.removeSprite(toRemove.get(i));
    }

    return render;
  }

  public static boolean updateBombs() {
    boolean render = false;
    int elapsedTime = EnemySpawner.getUpdateEvery();
    ArrayList <Sprite> toRemove = new ArrayList<>();
    
    //bomb stuff below
    for (HashMap.Entry<Sprite, Integer> entry : bombs.entrySet()) {
      Sprite sprite = entry.getKey();
      bombs.put(sprite, entry.getValue() - elapsedTime);
      if (entry.getValue() <= 0) {
        toRemove.add(sprite);
        render = true;
      }
    }
    for (int a = toRemove.size() - 1; a >= 0; a--) {
      int wallsBroken = 0;
      Sprite bomb = toRemove.get(a);
      
      for (int x = -bombRadius; x <= bombRadius; x++) {
        for (int y = -bombRadius; y <= bombRadius; y++) {
          if (!SpriteTracker.spriteExists(bomb)) {
            continue;
          }
          vec2 atPos = new vec2(x, y).add(SpriteTracker.getSpritePosition(bomb));
          
          if (!withinBounds(atPos)) {
            continue;
          }
          
          ArrayList <Sprite> spritesAtPos = SpriteTracker.getSpritesAt(atPos);
          for (int i = 0; i < spritesAtPos.size(); i++) {
            Sprite currentSprite = spritesAtPos.get(i);
            if (currentSprite.getSpriteSuperClassId().equals("Wall")) {
              SpriteTracker.removeSprite(spritesAtPos.get(i));
              wallsBroken++;
            } else if (!currentSprite.getSpriteSuperClassId().equals(bomb.getBombOwner())) {
              currentSprite.damage(25);
            }
          }

          if (!withinBounds(atPos)) {
            continue;
          }

          Sprite explosionEffect = new Explosion();
          SpriteTracker.setSpritePosition(explosionEffect, atPos);
          explosions.put(explosionEffect, 1000);
        }
      }

      if (bomb.getBombOwner().equals("Player")) {
        plr.addToLog("You broke " + wallsBroken + " walls with your bomb");
      }
      
      SpriteTracker.removeSprite(bomb);
      bombs.remove(bomb);
    }
    
    //explosion stuff below
    toRemove = new ArrayList<>();
    for (HashMap.Entry<Sprite, Integer> entry : explosions.entrySet()) {
      Sprite sprite = entry.getKey();
      explosions.put(sprite, entry.getValue() - elapsedTime);
      if (entry.getValue() <= 0) {
        toRemove.add(sprite);
        render = true;
      }
    }
    for (int i = toRemove.size() - 1; i >= 0; i--) {
      SpriteTracker.removeSprite(toRemove.get(i));
      explosions.remove(toRemove.get(i));
    }
    return render;
  }

  public static void usePortal() {
    boolean teleported = false;
    ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(plr);
    for (int i = 0; i < atPos.size(); i++) {
      Sprite portal = atPos.get(i);
      if (portal.getSpriteSuperClassId().equals("Portal")) {
        if (portal.getSpriteClassId().equals("Lobby Portal")) {
          SpriteTracker.removeSprite(portal);
        }
        if (portal.teleportTo().equals("bossRoom")) {
          clear();
          System.out.println("I've wanted to say this for a while now.");
          Helper.wait(2000);
          System.out.println("Stand proud.");
          Helper.wait(2000);
          System.out.println("You are strong.");
          Helper.wait(2000);
          System.out.println("Domain Expansion.");
          Helper.wait(2000);
          System.out.println("Absolute Portal Omnipresence");
          Helper.wait(2000);
        }
        Rooms.setCurrentRoom(portal.teleportTo());
        teleported = true;
        Player.addToLog("You teleported to " + portal.teleportTo());
        break;
      }
    }
    if (!teleported) {
      Player.addToLog("No portal found...");
    }
  }

  public static boolean wallFound(vec2 point) { // returns true if there is a wall at point
    ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(point);
    for (int i = 0; i < atPos.size(); i++) {
      if (atPos.get(i).getSpriteSuperClassId().equals("Wall")) {
        return true;
      }
    }
    return false;
  }

  public static vec2 findOnLine(String spriteToFind, vec2 start, vec2 direction) {
    vec2 currentPoint = start;
    while (withinBounds(currentPoint)) {
      ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(currentPoint);
      for (int i = 0; i < atPos.size(); i++) {
        if (atPos.get(i).getSpriteClassId().equals(spriteToFind)) {
          return currentPoint;
        } else if (atPos.get(i).getSpriteClassId().equals("Wall")) {
          return null;
        }
      }
      currentPoint = currentPoint.add(direction);
    }
    return null;
  }
}