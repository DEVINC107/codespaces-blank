import java.util.Scanner;

public class GameInfo {
  static final private String[][] texts = {
    {
      "Welcome to Dungeon Crawler RPG!",
      
    },
    {
      "How to play",
      "Enter letters to move and shoot",
      "Defeat enemies and get items to grow stronger",
      "Challenge and defeat the wizard boss to beat the game!"
    },
    {
      "Collectible Items",
      "Collectible items are items that can be picked up and used",
      "Picking up items trigger effects depending on the item",
      "Some items are picked up automatically",
      "While others can be picked up using the pickup keybind",
      "Below are a few of these items and their effects:",
      "🍩 - replenishes your health",
      "🛡️  - replenishes your shield",
      "📦 - refills your ammo",
      "💣 - gives you 1 bomb which can blow up walls"
    },
    {
      "Player UI",
      "Player stats are displayed below the game screen and provide data about your character",
      "❤️  - shows your current health over your maximum health",
      "Your health is a key part of the game. You will die if your health is below 0",
      "🛡️  - shows your current shield over your maximum shield",
      "Shields are used to absorb damage for your health",
      "🔥 - shows your current ammo over your maximum ammo",
      "Ammo is used to shoot at your enemies",
      "🪙  - shows your coin amount",
      "Coins are used to purchase items from the shop to grow stronger",
      "⚙️  - shows your current mode",
      "Mode determines what your character is doing. 🎯 is attacking and 🚶 is walking"
    }
  };
  
  static final int menuLength = 60;

  static public void welcome() {
    
  }
  
  public GameInfo(Scanner scan) {
    for (int i = 0; i < texts.length; i++) {
      int currentLength = (menuLength - texts[i][0].length() - 2) / 2;
      String dashes = "";
      for (int a = 1; a <= currentLength; a++) {
        dashes += "-";
      }
      texts[i][0] = dashes + " " + texts[i][0] + " " + dashes;
      for (int x = 0; x < texts[i].length; x++) {
        Helper.typeWrite(texts[i][x], 20);
        Helper.wait(400);
        System.out.println();
      }
      String endingDashes = "";
      for (int k = 1; k <= texts[i][0].length(); k++) {
        endingDashes += "-";
      }
      Helper.typeWrite(endingDashes, 10);
      System.out.println();
      Helper.wait(800);
      if (texts.length - 1 == i) {
        System.out.println("Press enter to start: ");
      } else {
        System.out.println("Press enter to continue: ");
      }
      scan.nextLine();
      Grid.clear();
    }
  }
}