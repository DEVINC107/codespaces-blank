public class Donut extends Collectible {
  
  public Donut() {
    super("Donut", "🍩");
  }

  public String pickUp() {
    Player plr = Player.getPlayer();
    plr.setHealth(plr.getHealth() + 20);
    return " + 20 hp";
  }

  public boolean autoPickUp() {
    return false;
  }
}