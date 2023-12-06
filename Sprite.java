// type
public class Sprite {
  private String icon;
  private String spriteSuperClassId;
  private String spriteClassId;

  public String getSpriteSuperClassId() {
    return spriteSuperClassId;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public void setSpriteSuperClassId(String spriteSuperClassId) {
    this.spriteSuperClassId = spriteSuperClassId;
  }

  public String getSpriteClassId() {
    return spriteClassId;
  }

  public void setSpriteClassId(String spriteClassId) {
    this.spriteClassId = spriteClassId;
  }

  public Sprite(String spriteSuperClassId, String spriteClassId, String icon) {
    this.spriteSuperClassId = spriteSuperClassId;
    this.spriteClassId = spriteClassId;
    this.icon = icon;
  }

  public void collide(Sprite other) {
    
  }

  public void outOfBounds() {
    
  }
  
  // sets empty methods so no errors :D
  public String pickUp() {
    return "";
  }

  public boolean autoPickUp() {
    return false;
  }

  public String teleportTo() {
    return "";
  }

  public void damage(int i) {
    
  }

  public String getBombOwner() {
    return "";
  }
}