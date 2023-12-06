import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Game {
  static Scanner scan;
  static boolean isDev = false;
  static boolean isPaused = true;
  static boolean godMode = false;
  static {
    scan = new Scanner(System.in);
  }
  public Game() {
    RoomsData.setInitialRoomsData();
    Rooms.setInitialRooms();
    new Player(this);
    menu();
    EnemySpawner.startSpawning();//starts updating everything
    Input.start();
    startLoops();
  }

  public static void startLoops() {
    isPaused = false;
  }

  public static void pauseLoops() {
    isPaused = true;
  }

  public static void printMenuInfo() {
    System.out.println("Menu");
    System.out.println("1. Play");
    System.out.println("2. How to play 😭");
    System.out.println("3. Set keybinds");
    System.out.println("4. Dev mode (skill issue)");
  }

  public static void menu() {
    printMenuInfo();
    
    int option = Integer.parseInt(scan.nextLine());
    while (true) {
      if (option == 1) {
        Grid.generate(10, 25);
        startLoops();
        break;
      } else if (option == 2) {
        new GameInfo(scan);
      } else if (option == 3) {
        Input.getKeybinds();
      } else if (option == 4 && !isDev) {
        Player.getPlayer().setBombs(9999);
        Player.getPlayer().setCoins(9999);
        isDev = true;
      } else if (option == 4 && isDev) {
        Grid.clear();
        System.out.println("Dev mode is already on");
        Helper.wait(1000);
      }
      Grid.clear();
      printMenuInfo();
      option = Integer.parseInt(scan.nextLine());
    }
  }
}