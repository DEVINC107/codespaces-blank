import java.util.Scanner;
import java.util.ArrayList;

public class Input {
  static Scanner scan;
  static String mode = "Walking";
  static String left;
  static String right;
  static String up;
  static String down;
  static String changeMode;
  static String pickUp;
  static String useBomb;
  static String usePortal;
  static vec2 direction;
  static String previousDirectionKey = "";
  static public String info;
  private static Thread t;

  static {
    left = "a";
    right = "d";
    up = "w";
    down = "s";
    changeMode = "f";
    pickUp = "e";
    useBomb = "b";
    usePortal = "p";
    scan = Game.scan;
  }
  private static final Runnable update = () -> {
    Player player = Player.getPlayer();
    while (true) {
      if (Game.isPaused) {
        continue;
      }
      String input = scan.nextLine();
      if (input.equals("") && !previousDirectionKey.equals("")) {
        input = previousDirectionKey;
      } else {
        previousDirectionKey = "";
      }
      Player.getPlayer().clearLog();
      int x = 0;
      int y = 0;
      if (Game.isDev && input.equals("god")) {
        Game.godMode = true;
        Player.addToLog("God mode enabled");
      } else if (input.equals(up)) {
        y = -1;
        previousDirectionKey = up;
      } else if (input.equals(left)) {
        x = -1;
        previousDirectionKey = left;
      } else if (input.equals(down)) {
        y = 1;
        previousDirectionKey = down;
      } else if (input.equals(right)) {
        x = 1;
        previousDirectionKey = right;
      } else if (input.equals(useBomb)) {
        Grid.useBomb(Player.getPlayer(), SpriteTracker.getSpritePosition(Player.getPlayer()));
      } else if (input.equals(changeMode)) {
        if (mode.equals("Shooting")) {
          mode = "Walking";
        } else if (mode.equals("Walking")) {
          mode = "Shooting";
        }
        Grid.render();
        Player.getPlayer().addToLog("Mode changed to: " + mode);
      } else if (input.equals(pickUp)) {
        Player.getPlayer().addToLog(Grid.pickUp());
      } else if (input.equals("g")) {
        //testing rooms
        if (!Rooms.getCurrentRoomName().equals("Lobby")) {
          Rooms.setCurrentRoom("Lobby");
        }
      } else if (input.equals(usePortal)) {
        Grid.usePortal();
      } else if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
        boolean salesmanFound = false;
        ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(SpriteTracker.getSpritePosition(Player.getPlayer()));
        for (int i = 0; i < atPos.size(); i++) {
          if (atPos.get(i).getSpriteClassId().equals("Salesman")) {
            Player.getPlayer().buyFromSalesman(input);
            salesmanFound = true;
            break;
          }
        }
        if (!salesmanFound) {
          Player.getPlayer().addToLog("Invalid input, try again");
        }
      } else if (input.equals("o") && Game.isDev) {
        Shop.start();
      } else if (input.equals("o") && !Game.isDev) {
        Player.addToLog("Can't open shop, dev mode not on");
      } else {
        Player.getPlayer().addToLog("Invalid input, try again");
      }
      if (mode == "Walking" && (x != 0 || y != 0)) {
        Player.getPlayer().addToLog(Grid.move(x, y));
      } else if (mode == "Shooting" && (x !=0 || y !=0)) {
        vec2 shootDirection = new vec2(x, y);
        new Bullet(Player.getPlayer(), SpriteTracker.getSpritePosition(Player.getPlayer()).move(shootDirection), shootDirection, "🔥");
        if ((Player.getPlayer().hasPerk("Double shot"))) {
          new Bullet(Player.getPlayer(), SpriteTracker.getSpritePosition(Player.getPlayer()).move(shootDirection), shootDirection, "🔥");
        }
        if (player.hasPerk("Buckshot")) {
          Bullet spreadBulletA = new Bullet(Player.getPlayer(), SpriteTracker.getSpritePosition(Player.getPlayer()).move(shootDirection), shootDirection, "🔥");
          Bullet spreadBulletB = new Bullet(Player.getPlayer(), SpriteTracker.getSpritePosition(Player.getPlayer()).move(shootDirection), shootDirection, "🔥");
          boolean vert = Math.abs(y) > 0;
          boolean horz = Math.abs(x) > 0;
          spreadBulletA.setOneTileSpread(new vec2(vert ? 1 : 0, horz ? 1 : 0));
          spreadBulletB.setOneTileSpread(new vec2(vert ? -1 : 0, horz ? -1 : 0));
        }
      }
    }
  };
  public static void getKeybinds() {
    System.out.println("______________");
    System.out.println("SET LEFT: ");
    left = scan.nextLine();
    System.out.println("SET RIGHT: ");
    right = scan.nextLine();
    System.out.println("SET UP: ");
    up = scan.nextLine();
    System.out.println("SET DOWN: ");
    down = scan.nextLine();
    System.out.println("SET CHANGE MODE: ");
    changeMode = scan.nextLine();
    System.out.println("SET PICK UP: ");
    pickUp = scan.nextLine();
    System.out.println("SET BOMB: ");
    useBomb = scan.nextLine();
    System.out.println("SET PORTAL: ");
    usePortal = scan.nextLine();
    System.out.println("KEYBINDS SET");
    System.out.println(String.format("LEFT: %s\nRIGHT: %s\nUP: %s\nDOWN: %s\nCHANGED MODE: %s\nPICK UP: %s\nUSE BOMB: %s\nUSE PORTAL: %s", left, right, up, down, changeMode, pickUp, useBomb, usePortal));
  }

  public static void printKeybinds() {
    System.out.println("LEFT: A\nRIGHT: D\n UP: W\n DOWN: S\n CHANGE MODE: ");
  }
  /*
  public printInfo() {
    System.out.println(info);
  }
  */

  public static void start() {
    t = new Thread(update);
    t.start();
  }

  public static void stop() {
    t.interrupt();
    t = null;
  }

  public static String getMode() {
    return mode;
  }
}
