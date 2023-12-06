import java.util.ArrayList;

public class Player extends Entity {
  private static Player player;
  private static Game game;
  private static ArrayList <String> infoLog = new ArrayList<String>();
  
  private static int maxAmmo = 20;
  private static int ammo;

  private static int maxShield = 100;
  private static int shield;

  private static int bombs;
  private static int coins;
  private static int tickets;

  public Player(Game game) {
    super("Player", "😎");
    setMaxHealth(100);
    maxHeal();
    player = this;
    this.game = game;
    shield = maxShield;
    coins = 0;
    bombs = 0;
    tickets = 0;
    ammo = maxAmmo/2;
  }

  public static Player getPlayer() {
    return player;
  }

  public int getCoins() {
    return coins;
  }

  public void setCoins(int coins) {
    this.coins = coins;
  }
  
  public static void printInfo() {
    System.out.println("---------- Player Info ----------");
    System.out.println("❤️  " + player.getHealth() + "/" + player.getMaxHealth());
    System.out.println("🛡️  " + shield + "/" + maxShield);
    System.out.println("🔥 " + ammo + "/" + maxAmmo);
    System.out.println("💣 " + bombs);
    System.out.println("🪙  " + coins);
    System.out.println("🎟️  " + tickets);
    String mode = Input.getMode();
    if (mode.equals("Shooting")) {
      System.out.println("⚙️  🎯");
    } else if (mode.equals("Walking")) {
      System.out.println("⚙️  🚶");
    }
    ArrayList <Sprite> atPos = SpriteTracker.getSpritesAt(SpriteTracker.getSpritePosition(player));
    String listOfItems = "";
    String listOfEnemies = "";
    String portalData = "";
    boolean salesmanGui = false;
    for (int i = 0; i < atPos.size(); i++) {
      Sprite currentSprite = atPos.get(i);
      if (currentSprite.getSpriteSuperClassId().equals("Collectible")) {
        listOfItems += " " + currentSprite.getIcon();
      } else if (currentSprite.getSpriteSuperClassId().equals("Enemy")) {
        listOfEnemies += " " + currentSprite.getIcon();
      } else if (currentSprite.getSpriteSuperClassId().equals("Portal")) {
        portalData = currentSprite.getIcon() + " (" + currentSprite.teleportTo() + ")";
      } else if (currentSprite.getSpriteClassId().equals("Salesman")) {
        salesmanGui = true;
      }
    }
    System.out.println("---------------------------------");
    if (listOfItems.length() > 0) {
      System.out.println("(" + Input.pickUp + ") to pick up" + listOfItems);
      System.out.println("---------------------------------");
    }
    if (listOfEnemies.length() > 0) {
      System.out.println("Enemies on you: " + listOfEnemies);
      System.out.println("---------------------------------");
    }
    if (portalData.length() > 0) {
      System.out.println("(" + Input.usePortal + ") to go into portal " + portalData);
      System.out.println("---------------------------------");
    }
    if (salesmanGui) {
      System.out.println("Hello, you can trade your tickets with me for stat boosts. Choose an option below: ");
      System.out.println("1. + 15 max health");
      System.out.println("2. + 25 max shield");
      System.out.println("3. + 2 damage");
      System.out.println("4. + 5 max ammo");
      System.out.println("---------------------------------");
    }
    if (infoLog.size() > 0) {
      for (int i = 0; i < infoLog.size(); i++) {
        System.out.println(infoLog.get(i));
      }
      System.out.println("---------------------------------");
    }
    System.out.println(String.format("Enter your next movement(%s, %s, %s, %s)", Input.up, Input.left, Input.down, Input.right));
  }

  public static void addToLog(String newString) {
    infoLog.add(newString);
    Grid.render();
  }

  public static void clearLog() {
    infoLog.clear();
  }

  public void buyFromSalesman(String input) {
    if (tickets > 0) {
      changeTickets(-1);
      addToLog("-1 ticket");
      if (input.equals("1")) {
        setMaxHealth(getMaxHealth() + 15);
        maxHeal();
        addToLog("+15 max health");
      } else if (input.equals("2")) {
        setMaxShield(getMaxShield() + 25);
        addToLog("+25 max shield");
      } else if (input.equals("3")) {
        setBulletDamage(getBulletDamage() + 2);
        addToLog("+2 bullet damage");
      } else if (input.equals("4")) {
        maxAmmo += 5;
        addToLog("+5 max Ammo");
      }
    } else {
      addToLog("You is broke 💀");
    }
    Grid.render();
  }
  
  public void giveBullets(int newBullets) {
    ammo = newBullets;
    if (ammo > maxAmmo) {
      ammo = maxAmmo;
    }
  }

  public void setAmmo(int newAmmo) {
    ammo = newAmmo;
    if (ammo > maxAmmo) {
      ammo = maxAmmo;
    }
  }

  public int getAmmo() {
    return ammo;
  }

  public void setBombs(int newBombs) {
    bombs = newBombs;
  }

  public int getBombs() {
    return bombs;
  }

  public void setShield(int newShield) {
    shield = newShield;
    if (shield > maxShield) {
      shield = maxShield;
    }
  }

  public int getShield() {
    return shield;
  }

  public int getMaxShield() {
    return maxShield;
  }

  public void setMaxShield(int amount) {
    this.maxShield = amount;
  }

  public static void changeTickets(int amount) {
    tickets += amount;
  }

  public void damage(int damage) {
    if (Game.godMode) {
      return;
    }
    if (shield > 0) {
      if (shield >= damage) {
        shield -= damage;
      } else {
        damage -= shield;
        shield = 0;
      }
    }
    if (getHealth() - damage <= 0) {
      setHealth(getMaxHealth());
      Rooms.setCurrentRoom("Lobby");
      Grid.moveToSpawn();
    } else {
      setHealth(getHealth() - damage);
    }
  }
}