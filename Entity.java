import java.util.HashSet;

public class Entity extends Sprite {
  private int maxHealth = 100;
  private int health = 99;
  private int bulletDamage = 20;
  private HashSet<String> perks = new HashSet<>();

  public void addPerk(String perk) {
    perks.add(perk);
  }

  public boolean hasPerk(String perk) {
    return perks.contains(perk);
  }
  
  public void setBulletDamage(int amount) {
    this.bulletDamage = amount;
  }

  public int getBulletDamage() {
    return this.bulletDamage;
  }
  
  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }
  public void maxHeal() {
    this.health = this.maxHealth;
  }
  
  public Entity(String id, String icon) {
    super("Entity", id, icon);
  }

  public Entity(String entityType, String id, String icon) {
    super(entityType, id, icon);
  }

  public void setHealth(int newHealth) {
    health = newHealth;
    if (health > maxHealth) {
      health = maxHealth;
    }
  }

  public int getHealth() {
    return health;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public void heal(int amount) {
    setHealth(getHealth() + amount);
  }

  public void damage(int damageDealt) {
    setHealth(health - damageDealt);
  }
}