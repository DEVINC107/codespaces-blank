public class Bomb extends Collectible {
  public Bomb() {
    super("Bomb", "💣");
  }

  public String pickUp() {
    Player plr = Player.getPlayer();
    plr.setBombs(plr.getBombs() + 1);
    return " + 1 bomb";
  }

  public boolean autoPickUp() {
    return true;
  }
}