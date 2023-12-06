import java.util.HashMap;
import java.util.ArrayList;

public class Rooms {
  private static HashMap<String, HashMap<Sprite, vec2>> rooms = new HashMap<>();
  private static HashMap<String, vec2> roomSizes = new HashMap<>();
  private static String currentRoomName;

  private static void onCurrentRoomChanged(int rows, int columns) { //used to set some stuff when room is changed
    Grid.roomChanged(rows, columns);
    Bullet.roomChanged();
  }

  public static void setInitialRooms() {
    rooms = RoomsData.getRoomsData();
    roomSizes = RoomsData.getRoomSizes();
    currentRoomName = null;
  }

  public static String getCurrentRoomName() {
    return currentRoomName;
  }

  public static void setCurrentRoom(String roomName) {
    if (currentRoomName != null) {
      Bullet.clear();
      ArrayList <Sprite> spritesInPreviousRoom = new ArrayList<>();
      rooms.put(currentRoomName, SpriteTracker.getCurrentRoom());
      for (HashMap.Entry<Sprite, vec2> entry : rooms.get(currentRoomName).entrySet()) {
        spritesInPreviousRoom.add(entry.getKey());
      }

      for(int i = spritesInPreviousRoom.size() - 1; i >= 0 ; i--) {
        Sprite sprite = spritesInPreviousRoom.get(i);
        String id = sprite.getSpriteSuperClassId();
        if (id.equals("Player")) {
          rooms.get(currentRoomName).remove(sprite);
        } else if (id.equals("FusedBomb") || id.equals("Explosion")) {
          rooms.get(currentRoomName).remove(sprite);
        }
      }
    }

    vec2 roomSize = roomSizes.get(roomName);
    SpriteTracker.setSpritesRoom(rooms.get(roomName), roomSize.x, roomSize.y);
    currentRoomName = roomName;
    onCurrentRoomChanged(roomSize.x, roomSize.y);
    Grid.render();
  }
}