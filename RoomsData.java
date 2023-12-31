import java.util.HashMap;

public class RoomsData {
  static HashMap<String, HashMap<Sprite, vec2>> roomsData = new HashMap<>();
  static HashMap<String, vec2> roomSizes = new HashMap<>();

  private static void addToRoomsData(String roomName, String[] wallPositions, HashMap <Sprite, vec2> otherSprites, int rows, int columns, vec2 spawnPoint) {
    HashMap<Sprite, vec2> sprites = new HashMap<>();
    for (int i = 0; i < wallPositions.length; i++) {
      int dashAt = wallPositions[i].indexOf("-");
      int x = Integer.parseInt(wallPositions[i].substring(0, dashAt));
      int y = Integer.parseInt(wallPositions[i].substring(dashAt + 1, wallPositions[i].length()));
      sprites.put(new Wall(), new vec2(x, y));
    }

    for (HashMap.Entry<Sprite, vec2> entry : otherSprites.entrySet()) {
      sprites.put(entry.getKey(), entry.getValue());
    }

    sprites.put(new Spawn(), spawnPoint);
    
    roomsData.put(roomName, sprites);

    roomSizes.put(roomName, new vec2(columns, rows));
  }

  public static HashMap<String, HashMap<Sprite, vec2>> getRoomsData() {
    return roomsData;
  }

  public static HashMap<String, vec2> getRoomSizes() {
    return roomSizes;
  }

  /*
  FORMAT
  
  roomsData = {
    ["name of the room"] = {
      ["Sprite here"] = position,
      ["Another Sprite here"] = AnotherPosition
    }
  }
  
  */

  private static String[] LobbyWalls = {};
  private static String[] room1Walls = {
  "13-5",
  "3-5",
  "2-3",
  "23-13",
  "23-6",
  "16-7",
  "1-10",
  "20-5",
  "16-9",
  "17-5",
  "10-3",
  "5-12",
  "14-3",
  "1-8",
  "15-5",
  "10-5",
  "21-5",
  "19-12",
  "1-13",
  "16-6",
  "8-10",
  "15-10",
  "1-7",
  "17-13",
  "8-9",
  "2-5",
  "1-3",
  "11-2",
  "1-6",
  "22-3",
  "13-0",
  "1-1",
  "10-13",
  "18-5",
  "1-5",
  "8-6",
  "7-3",
  "5-3",
  "6-1",
  "9-3",
  "8-5",
  "4-13",
  "4-3",
  "13-3",
  "5-5",
  "4-5",
  "18-1",
  "11-11",
  "19-10",
  "13-11",
  "7-13",
  "20-3",
  "7-1",
  "12-5",
  "9-10",
  "11-0",
  "16-5",
  "11-1",
  "11-3",
  "2-1",
  "7-2",
  "18-13",
  "24-3",
  "23-12",
  "9-11",
  "23-10",
  "23-1",
  "23-8",
  "23-7",
  "23-5",
  "14-11",
  "23-3",
  "15-11",
  "19-3",
  "5-10",
  "21-13",
  "19-11",
  "5-9",
  "8-8",
  "16-8",
  "23-9",
  "3-1",
  "17-2",
  "11-5",
  "15-3",
  "21-1",
  "14-5",
  "19-8",
  "4-1",
  "20-1",
  "19-13",
  "19-9",
  "14-13",
  "1-12",
  "22-1",
  "13-2",
  "19-1",
  "19-5",
  "1-9",
  "20-13",
  "9-5",
  "5-13",
  "21-3",
  "1-11",
  "6-5",
  "5-8",
  "5-11",
  "16-10",
  "16-3",
  "17-1",
  "0-3",
  "13-1",
  "6-13",
  "7-5",
  "5-1",
  "8-7",
  "3-13",
  "8-3",
  "22-5",
  "17-3",
  "10-11",
  "23-11",
  "3-3"
  };
  private static String[] room2Walls = {
  "14-2",
  "8-4",
  "4-14",
  "20-7",
  "9-9",
  "16-11",
  "4-3",
  "9-11",
  "6-1",
  "18-4",
  "24-7",
  "16-4",
  "23-4",
  "6-3",
  "14-1",
  "24-12",
  "6-7",
  "17-7",
  "0-10",
  "9-7",
  "11-3",
  "10-3",
  "18-11",
  "0-7",
  "4-12",
  "3-7",
  "11-13",
  "16-2",
  "8-2",
  "23-7",
  "15-7",
  "4-10",
  "7-1",
  "4-2",
  "7-9",
  "13-13",
  "20-4",
  "7-11",
  "10-2",
  "18-2",
  "17-11",
  "11-9",
  "4-4",
  "7-13",
  "10-0",
  "20-1",
  "18-1",
  "6-2",
  "24-4",
  "2-7",
  "13-11",
  "4-7",
  "14-3",
  "1-10",
  "24-11",
  "13-9",
  "23-11",
  "22-14",
  "18-7",
  "22-7",
  "21-14",
  "8-3",
  "21-13",
  "0-4",
  "21-7",
  "21-4",
  "16-3",
  "15-13",
  "20-3",
  "20-0",
  "4-0",
  "15-11",
  "3-4",
  "19-7",
  "16-7",
  "1-7",
  "18-3",
  "20-11",
  "20-2",
  "3-10",
  "17-1",
  "8-7",
  "16-1",
  "15-12",
  "15-10",
  "10-1",
  "7-7",
  "11-11",
  "4-13",
  "19-11",
  "4-1",
  "4-11",
  "1-4",
  "13-3",
  "8-1",
  "6-4",
  "9-13",
  "15-9",
  "5-7",
  "14-0"
  };
  private static String[] room3Walls = {
  "9-1",
  "19-3",
  "9-7",
  "6-1",
  "11-6",
  "5-1",
  "14-4",
  "0-9",
  "2-3",
  "11-4",
  "2-7",
  "5-9",
  "18-3",
  "8-3",
  "11-2",
  "5-7",
  "9-9",
  "16-0",
  "6-9",
  "11-7",
  "19-9",
  "1-5",
  "18-9",
  "14-3",
  "14-1",
  "9-0",
  "7-1",
  "13-1",
  "9-5",
  "1-1",
  "2-9",
  "7-5",
  "16-1",
  "4-1",
  "12-9",
  "17-9",
  "16-9",
  "15-9",
  "16-3",
  "11-1",
  "16-2",
  "11-5",
  "0-1",
  "8-7",
  "13-3",
  "7-9",
  "13-6",
  "2-5",
  "9-2",
  "14-6",
  "1-7",
  "14-5",
  "13-9",
  "13-5",
  "13-4",
  "9-3",
  "1-3",
  "12-1",
  "2-1",
  "11-9",
  "6-3",
  "3-5",
  "6-5",
  "3-1",
  "11-3",
  "10-9",
  "9-6",
  "15-1",
  "9-4",
  "14-9",
  "7-7",
  "7-3",
  "5-5",
  "3-9",
  "4-7",
  "5-3",
  "3-3",
  "8-9",
  "4-5",
  "4-3",
  "3-7",
  "1-9",
  "4-9",
  "6-7",
  "0-5"
  };
  private static String[] room4Walls = {
  "11-6",
  "11-3",
  "16-2",
  "6-7",
  "8-3",
  "12-1",
  "12-7",
  "20-3",
  "2-5",
  "14-1",
  "3-4",
  "18-2",
  "10-7",
  "7-1",
  "9-6",
  "8-1",
  "4-5",
  "10-8",
  "9-1",
  "14-6",
  "3-5",
  "9-3",
  "2-4",
  "9-7",
  "14-3",
  "18-6",
  "20-7",
  "13-3",
  "19-6",
  "8-2",
  "16-6",
  "19-2",
  "11-1",
  "13-6",
  "16-3",
  "6-4",
  "13-8",
  "17-2",
  "7-2",
  "5-7",
  "16-7",
  "21-3",
  "8-7",
  "12-3",
  "14-8",
  "15-6",
  "13-1",
  "16-1",
  "6-2",
  "20-2",
  "5-6",
  "19-7",
  "19-3",
  "6-5",
  "10-3",
  "13-7",
  "4-2",
  "18-3",
  "3-6",
  "11-2",
  "4-4",
  "6-6",
  "18-7",
  "7-8",
  "11-7",
  "17-8",
  "17-7",
  "17-6",
  "17-3",
  "17-1",
  "5-2",
  "16-8",
  "21-6",
  "12-6",
  "5-3",
  "15-8",
  "15-7",
  "20-6",
  "15-2",
  "5-5",
  "14-2",
  "13-2",
  "9-8",
  "4-7",
  "3-3",
  "15-3",
  "12-2",
  "11-8",
  "15-1",
  "10-1",
  "6-3",
  "10-6",
  "10-2",
  "14-7",
  "4-6",
  "9-2",
  "8-8",
  "8-6",
  "4-3",
  "7-7",
  "5-4",
  "12-8"
  };
  private static String[] room5Walls = {
  "23-0",
  "13-9",
  "5-0",
  "20-13",
  "14-8",
  "14-3",
  "13-12",
  "14-2",
  "1-2",
  "2-14",
  "22-3",
  "9-14",
  "22-8",
  "2-13",
  "19-10",
  "8-10",
  "3-14",
  "12-3",
  "14-7",
  "10-4",
  "0-0",
  "12-1",
  "9-0",
  "1-9",
  "2-3",
  "11-11",
  "7-5",
  "21-4",
  "6-11",
  "15-2",
  "17-0",
  "24-2",
  "23-12",
  "7-13",
  "23-6",
  "20-0",
  "11-4",
  "1-13",
  "4-0",
  "15-8",
  "23-8",
  "16-11",
  "20-12",
  "21-9",
  "10-6",
  "21-2",
  "5-3",
  "15-5",
  "3-11",
  "1-14",
  "2-6",
  "22-6",
  "24-10",
  "12-11",
  "24-8",
  "0-4",
  "10-9",
  "23-11",
  "23-7",
  "10-2",
  "21-10",
  "23-3",
  "18-13",
  "18-1",
  "22-11",
  "22-9",
  "21-6",
  "24-12",
  "13-0",
  "20-6",
  "22-5",
  "8-13",
  "22-0",
  "19-3",
  "0-14",
  "15-12",
  "20-14",
  "20-7",
  "23-5",
  "20-3",
  "18-7",
  "19-11",
  "7-7",
  "11-1",
  "18-12",
  "18-10",
  "7-6",
  "19-13",
  "13-4",
  "9-13",
  "11-6",
  "19-0",
  "4-10",
  "7-14",
  "3-12",
  "16-7",
  "16-6",
  "5-12",
  "16-0",
  "13-14",
  "15-4",
  "13-10",
  "14-0",
  "0-13",
  "20-4",
  "0-6",
  "21-1",
  "2-0",
  "18-3",
  "1-3",
  "12-9",
  "6-10",
  "0-11",
  "1-8",
  "3-1",
  "4-7",
  "6-8",
  "12-4",
  "13-7",
  "5-10",
  "4-3"
  };
  private static String[] room6Walls = {
       "7-9",
       "13-7",
       "14-9",
       "1-9",
       "20-5",
       "24-9",
       "24-7",
       "24-6",
       "24-5",
       "24-4",
       "24-3",
       "10-9",
       "15-9",
       "11-4",
       "23-9",
       "18-2",
       "5-9",
       "5-0",
       "11-9",
       "1-4",
       "8-9",
       "0-1",
       "9-6",
       "22-9",
       "15-4",
       "6-8",
       "16-6",
       "23-2",
       "6-6",
       "3-9",
       "19-6",
       "6-4",
       "22-2",
       "1-0",
       "21-9",
       "21-5",
       "21-2",
       "9-4",
       "6-5",
       "0-2",
       "9-5",
       "10-4",
       "2-9",
       "19-9",
       "19-2",
       "19-5",
       "18-9",
       "18-6",
       "17-6",
       "0-9",
       "0-4",
       "12-7",
       "16-9",
       "0-6",
       "9-9",
       "20-9",
       "16-5",
       "16-2",
       "5-1",
       "6-7",
       "15-2",
       "13-9",
       "0-8",
       "13-4",
       "14-3",
       "4-4",
       "14-4",
       "4-0",
       "12-9",
       "17-2",
       "12-8",
       "0-0",
       "12-4",
       "13-8",
       "6-9",
       "16-4",
       "17-9",
       "0-3",
       "5-4",
       "24-2",
       "2-0",
       "4-9",
       "0-5",
       "24-8",
       "0-7",
       "20-2",
       "14-2",
       "3-0"
    };
  private static String[] bossRoomWalls = {
  "18-18",
  "12-22",
  "16-7",
  "16-16",
  "16-11",
  "5-2",
  "16-9",
  "14-12",
  "8-15",
  "16-17",
  "5-25",
  "13-14",
  "18-20",
  "15-25",
  "2-20",
  "14-21",
  "13-9",
  "14-11",
  "3-9",
  "9-2",
  "13-1",
  "8-16",
  "9-25",
  "8-10",
  "3-16",
  "3-23",
  "17-23",
  "3-2",
  "7-20",
  "5-23",
  "18-10",
  "5-13",
  "0-25",
  "5-9",
  "2-25",
  "12-12",
  "12-25",
  "14-15",
  "16-25",
  "17-5",
  "1-7",
  "12-21",
  "9-3",
  "5-11",
  "7-3",
  "11-2",
  "18-15",
  "16-15",
  "18-11",
  "12-11",
  "18-21",
  "10-17",
  "14-14",
  "4-7",
  "11-20",
  "7-25",
  "11-1",
  "1-1",
  "3-26",
  "9-13",
  "1-9",
  "7-1",
  "7-17",
  "2-7",
  "10-9",
  "7-11",
  "4-23",
  "6-23",
  "0-16",
  "12-20",
  "1-16",
  "10-25",
  "10-20",
  "10-8",
  "7-4",
  "3-11",
  "3-17",
  "6-16",
  "0-4",
  "3-18",
  "19-5",
  "17-25",
  "1-11",
  "18-14",
  "5-7",
  "2-4",
  "7-13",
  "16-13",
  "3-15",
  "19-23",
  "10-10",
  "8-25",
  "8-11",
  "16-23",
  "16-21",
  "6-20",
  "10-14",
  "3-5",
  "16-19",
  "14-17",
  "4-11",
  "8-8",
  "7-10",
  "16-2",
  "0-23",
  "1-25",
  "18-25",
  "18-19",
  "8-13",
  "18-16",
  "18-13",
  "18-8",
  "18-7",
  "3-20",
  "16-12",
  "3-6",
  "12-9",
  "10-11",
  "6-14",
  "2-23",
  "17-3",
  "6-17",
  "16-22",
  "6-8",
  "16-20",
  "16-18",
  "16-14",
  "16-10",
  "6-25",
  "16-8",
  "16-3",
  "4-25",
  "15-23",
  "5-3",
  "1-4",
  "18-12",
  "10-12",
  "3-25",
  "18-5",
  "8-20",
  "14-25",
  "7-2",
  "14-20",
  "14-19",
  "14-10",
  "11-25",
  "14-16",
  "8-23",
  "4-20",
  "14-9",
  "13-25",
  "12-14",
  "6-13",
  "18-6",
  "1-23",
  "13-23",
  "5-8",
  "14-18",
  "5-10",
  "11-14",
  "16-26",
  "9-20",
  "3-4",
  "12-23",
  "3-1",
  "16-1",
  "12-13",
  "10-18",
  "5-20",
  "3-7",
  "12-10",
  "2-16",
  "9-23",
  "18-23",
  "18-9",
  "7-8",
  "11-17",
  "11-16",
  "2-11",
  "6-15",
  "7-23",
  "18-17",
  "10-23",
  "10-13",
  "4-9",
  "8-17",
  "14-23",
  "9-1",
  "0-9",
  "16-0",
  "5-1",
  "3-14"
  };
  
  private static HashMap <Sprite, vec2> LobbySprites = new HashMap<>();
  private static HashMap <Sprite, vec2> room1Sprites = new HashMap<>();
  private static HashMap <Sprite, vec2> testSprites = new HashMap<>();
  private static HashMap <Sprite, vec2> room2Sprites = new HashMap<>();
  private static HashMap <Sprite, vec2> room3Sprites = new HashMap<>();
  private static HashMap <Sprite, vec2> room4Sprites = new HashMap<>();
  private static HashMap <Sprite, vec2> room5Sprites = new HashMap<>();
  private static HashMap <Sprite, vec2> room6Sprites = new HashMap<>();
  private static HashMap <Sprite, vec2> bossRoomSprites = new HashMap<>();
  

  public static void setInitialRoomsData() {
    LobbySprites.put(new Room1Portal(), new vec2(0, 0));
    LobbySprites.put(new Room2Portal(), new vec2(5, 0));
    LobbySprites.put(new Room3Portal(), new vec2(10, 0));
    LobbySprites.put(new Room4Portal(), new vec2(15, 0));
    LobbySprites.put(new Room5Portal(), new vec2(20, 0));
    LobbySprites.put(new Room6Portal(), new vec2(25, 0));
    LobbySprites.put(new BossRoomPortal(), new vec2(25, 5));
    LobbySprites.put(new TestPortal(), new vec2(15, 4));
    LobbySprites.put(new Salesman(), new vec2(0, 9));
    addToRoomsData("Lobby", LobbyWalls, LobbySprites, 26, 10, new vec2(20, 8));

    room1Sprites.put(new Grave(16), new vec2(9, 1));
    room1Sprites.put(new Grave(15), new vec2(15, 1));
    room1Sprites.put(new Coffin(28), new vec2(12, 1));

    room2Sprites.put(new Coffin(26), new vec2(0, 0));
    room2Sprites.put(new Coffin(24), new vec2(24, 0));
    room2Sprites.put(new Coffin(22), new vec2(0, 14));
    room2Sprites.put(new Grave(12), new vec2(12, 0));

    room3Sprites.put(new Coffin2(18), new vec2(0, 0));
    room3Sprites.put(new Grave2(9), new vec2(1, 0));
    room3Sprites.put(new Grave2(16), new vec2(15, 0));

    room4Sprites.put(new Grave2(13), new vec2(24, 9));
    room4Sprites.put(new Grave2(14), new vec2(24, 0));
    room4Sprites.put(new Coffin2(22), new vec2(7, 3));
    room4Sprites.put(new Coffin2(21), new vec2(7, 6));

    room5Sprites.put(new Grave3(15), new vec2(12, 0));
    room5Sprites.put(new Grave3(1), new vec2(3, 13));
    room5Sprites.put(new Coffin3(27), new vec2(2, 12));
    room5Sprites.put(new Coffin3(26), new vec2(0, 3));

    room6Sprites.put(new Grave3(11), new vec2(1, 1));
    room6Sprites.put(new UFO(10), new vec2(15, 3));
    room6Sprites.put(new Coffin3(17), new vec2(23, 8));

    Wizard wizard = new Wizard();
    wizard.setMaxHealth(500);
    wizard.heal(999999);
    testSprites.put(wizard, new vec2(0, 0));
    addToRoomsData("test", new String[0], testSprites, 20, 20, new vec2(0, 0));
    addToRoomsData("room1", room1Walls, room1Sprites, 25, 15, new vec2(12, 7));
    addToRoomsData("room2", room2Walls, room2Sprites, 25, 15, new vec2(24, 14));
    addToRoomsData("room3", room3Walls, room3Sprites, 20, 10, new vec2(18, 1));
    addToRoomsData("room4", room4Walls, room4Sprites, 25, 10, new vec2(0, 4));
    addToRoomsData("room5", room5Walls, room5Sprites, 25, 15, new vec2(24, 14));
    addToRoomsData("room6", room6Walls, room6Sprites, 25, 10, new vec2(24, 0));
    addToRoomsData("bossRoom", bossRoomWalls, bossRoomSprites, 20, 27, new vec2(0, 26));
  }
}