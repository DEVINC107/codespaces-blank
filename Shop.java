import java.util.HashMap;
import java.util.Map;

class ShopItem {
  private String name;
  private String description;
  private int price;
  private ShopItem upgrade;
  
  public ShopItem(String name, String description, int price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public String info() {
    return String.format("$%d %s (%s) ", this.price, this.name, this.description);
  }

  public boolean canAfford(int money) {
    return money >= price;
  }

  public int getPrice() {
    return price;
  }

  public void upgrade(ShopItem upgrade) {
    this.upgrade = upgrade;
  }

  public boolean is(String id) {
    return name.equals(id);
  }

  public ShopItem getUpgrade() {
    return this.upgrade;
  }
}

public class Shop {
  private final static HashMap<Integer, ShopItem> items = new HashMap<>();
  static {
    ShopItem milk = new ShopItem("carton of milk", "+20 health", 1);
    ShopItem doubleShot = new ShopItem("double shot", "+1 bullet", 2);
    ShopItem devildeal = new ShopItem("devil deal", "fear no monster", 0);
    ShopItem smite = new ShopItem("smite", "smite your enemies (z)", 0);
    doubleShot.upgrade(new ShopItem("buckshot", "+2 bullets", 25));
    items.put(1, milk);
    items.put(2, doubleShot);
    items.put(1, smite);
    items.put(99, devildeal);
    
  }
  public static void start() {
    Game.pauseLoops();
    Grid.clear();
    System.out.println("Coins: " + Player.getPlayer().getCoins());
    System.out.println("---------- items ----------");
    for (Map.Entry<Integer, ShopItem> entry : items.entrySet()) {
      System.out.println(entry.getKey() + entry.getValue().info());
    }
    System.out.print("Choose an item (-1 to quit)");
    int option = Integer.parseInt(Game.scan.nextLine());
    if (items.get(option)==null) {
      Grid.clear();
      System.out.println("Not an item, get out");
      Helper.wait(2000);
      stop();
      return;
    }
    ShopItem item = items.get(option);
    Player player = Player.getPlayer();
    int coins = player.getCoins();
    if (!item.canAfford(coins)) {
      Grid.clear();
      System.out.println("Get out brokie");
      Helper.wait(2000);
      stop();
      return;
    }
    if (item.getUpgrade()!= null) {
      items.put(option, item.getUpgrade());
    }
    if (item.is("full heal")) {
      player.heal(99999);
    }
    if (item.is("shield upgrade")) {
      player.setMaxShield(player.getMaxShield() + 20);
    }
    if (item.is("double shot")) {
      player.addPerk("doubleshot");
    }
    if (item.is("smite")) {
      player.addPerk("smite");
    }
    if (item.is("buckshot")) {
      player.addPerk("Buckshot");
    }
    
    if (item.is("devil deal")) {
      player.addPerk("devildeal");
    }
    player.setCoins(coins - item.getPrice());
    stop();
  }

  public static void stop() {
    Grid.render();
    Game.startLoops();
  }
}