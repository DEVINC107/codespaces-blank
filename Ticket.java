public class Ticket extends Collectible {
  
  public Ticket() {
    super("Ticket", "🎟️ ");
  }

  public String pickUp() {
    Player.changeTickets(1);
    return " + 1 ticket";
  }

  public boolean autoPickUp() {
    return true;
  }
}