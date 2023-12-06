public class BossRoomPortal extends Portal {
  public BossRoomPortal() {
    super("Boss Room Portal", "🌀");
  }

  public String teleportTo() {
    return "bossRoom";
  }
}