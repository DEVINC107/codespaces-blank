public class Coin extends Collectible {
  
  public Coin() {
    super("Coin", "🪙 ");
  }

  public String pickUp() {
    Player plr = Player.getPlayer();
    plr.setCoins(plr.getCoins() + 1);
    return " + 1 coin";
  }

  public boolean autoPickUp() {
    return true;
  }
}