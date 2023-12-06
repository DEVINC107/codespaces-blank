public class FusedBomb extends Sprite {
  private String owner;
  
  public FusedBomb(String owner) {
    super("FusedBomb", "FusedBomb", "💣");
    this.owner = owner;
  }

  public String getBombOwner() {
    return owner;
  }
}