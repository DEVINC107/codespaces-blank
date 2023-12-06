import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class SpriteTracker {
  static HashMap<Sprite, vec2> sprites = new HashMap<>(); // contains all objects
  static ArrayList<Sprite>[][] lookup;
  private static final String[] renderingPriority = Grid.getRenderingPriority();
  private static int r;
  private static int c;
  
  public static ArrayList<Sprite> getSpritesAt(vec2 pos) {
    return lookup[pos.y][pos.x];
  }

  public static Sprite getSpriteAt(int x, int y) {
    if (lookup[y][x].size() == 0) {
      return null;
    }
    return lookup[y][x].get(0);
  }

  public static void setBounds(int rows, int columns) {
    r = rows;
    c = columns;
    lookup = new ArrayList[r][c];
    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {
        lookup[i][j] = new ArrayList();
      }
    }
  }

  public static vec2 getBounds() {
    return new vec2(c, r);
  }

  public static void setSpritePosition(Sprite sprite, vec2 pos) {
    vec2 oldPos = sprites.get(sprite);
    if (oldPos != null) {
      lookup[oldPos.y][oldPos.x].remove(sprite);
    }
    ArrayList<Sprite> atPos = lookup[pos.y][pos.x];
    atPos.add(sprite);
    sprites.put(sprite, pos);
    //insertion sort:https://www.youtube.com/watch?v=0lOnnd50cGI
    for (int i = 1; i < atPos.size(); i ++) {
      Sprite cur = atPos.get(i);
      int spriteSuperClassIdx = Helper.indexOf(renderingPriority, cur.getSpriteSuperClassId());
      int j = i - 1;
      while (j >= 0 && spriteSuperClassIdx < Helper.indexOf(renderingPriority, atPos.get(j).getSpriteSuperClassId())) {
        atPos.set(j + 1, atPos.get(j));
        j --;
      }
      atPos.set(j + 1, cur);
    }
  }

  public static void removeSprite(Sprite sprite) {
    vec2 oldPos = sprites.get(sprite);
    if (oldPos != null) {
      lookup[oldPos.y][oldPos.x].remove(sprite);
    }
    sprites.remove(sprite);
  }

  public static vec2 getSpritePosition(Sprite sprite) {
    return sprites.get(sprite);
  }

  public static ArrayList<Sprite> getSpritesAt(Sprite sprite) {
    return getSpritesAt(getSpritePosition(sprite));
  }

  public static Sprite findFirstSpriteOfType(String type, int rows, int columns) {
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < columns; y++) {
        for (int i = 0; i < lookup[x][y].size(); i++) {
          if (lookup[x][y].get(i).getSpriteSuperClassId().equals(type)) {
            return lookup[x][y].get(i);
          }
        }
      }
    }
    return null;
  }
  
  public static boolean spriteExists(Sprite sprite) {
    return sprites.containsKey(sprite);
  }

  public static HashMap <Sprite, vec2> getCurrentRoom() {
    return sprites;
  }

  public static void setSpritesRoom(HashMap<Sprite, vec2> newSpritesRoom, int rows, int columns) {
    sprites = newSpritesRoom;
    setBounds(rows, columns);
    //https://sentry.io/answers/iterate-hashmap-java/#:~:text=Perhaps%20the%20most%20straightforward%20approach,or%20entries%20in%20the%20HashMap.
    for (HashMap.Entry<Sprite, vec2> entry : sprites.entrySet()) {
      Sprite key = entry.getKey();
      vec2 value = entry.getValue();
      int x = value.x;
      int y = value.y;
      lookup[y][x].add(key);
    }
  }
}