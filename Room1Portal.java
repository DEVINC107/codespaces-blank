public class Room1Portal extends Portal {
  public Room1Portal() {
    super("Room 1 Portal", "🌀");
  }

  public String teleportTo() {
    return "room1";
  }
}