public class Ammo extends Collectible {
  public Ammo() {
    super("Ammo", "📦");
  }

  public String pickUp() {
    Player plr = Player.getPlayer();
    plr.setAmmo(plr.getAmmo() + 5);
    return " + 5 ammo";
  }

  public boolean autoPickUp() {
    return false;
  }
}