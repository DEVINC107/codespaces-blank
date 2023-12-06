public class ChugJug extends Collectible {
  public ChugJug() {
    super("ChugJug", "🛡️ ");
  }

  public String pickUp() {
    Player plr = Player.getPlayer();
    plr.setShield(plr.getShield() + 5);
    return " + 5 Shield";
  }

  public boolean autoPickUp() {
    return false;
  }
}  