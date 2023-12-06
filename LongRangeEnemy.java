import java.util.ArrayList;

public class LongRangeEnemy extends Enemy {

  private int shootDelay;
  private int walkDelay;
  private int loopsLeft;

  private ArrayList <Sprite> drops = new ArrayList<>();
  private String bulletIcon;
  private Pathfinding pathfind;
  private ArrayList <vec2> shootDirections = new ArrayList<>();


  public LongRangeEnemy(String name, String icon, int health, int shootDelay, int walkDelay, int damage, String bulletIcon) {
    super(name, icon);
    setMaxHealth(health);
    maxHeal();
    this.shootDelay = shootDelay;
    this.walkDelay = walkDelay;
    this.bulletIcon = bulletIcon;
    setBulletDamage(damage);

    shootDirections.add(new vec2(1, 0));
    shootDirections.add(new vec2(-1, 0));
    shootDirections.add(new vec2(0, 1));
    shootDirections.add(new vec2(0, -1));
    loopsLeft = 0;
  }

  public void setDrops(ArrayList<Sprite> drops) {
    this.drops = drops;
  }

  public @Override boolean enemyUpdate() {
    if (loopsLeft > 0) {
      loopsLeft--;
      return false;
    }

    // attack
    for (int i = 0; i < shootDirections.size(); i++) {
      vec2 at = Grid.findOnLine("Player", SpriteTracker.getSpritePosition(this), shootDirections.get(i));
      if (at != null) {
        new Bullet(this, SpriteTracker.getSpritePosition(this), shootDirections.get(i), bulletIcon);
        loopsLeft = getLoopsLeft(shootDelay);
        return true;
      }
    }

    // move
    if (pathfind == null) {
      vec2 bounds = SpriteTracker.getBounds();
      pathfind = new Pathfinding(bounds.x, bounds.y);
    }
    vec2 pos = SpriteTracker.getSpritePosition(this);
    vec2 plrPos = SpriteTracker.getSpritePosition(Player.getPlayer());

    vec2 nextPos = pathfind.nextPos(pos, plrPos);
    if (nextPos != null) {
      SpriteTracker.setSpritePosition(this, nextPos);
      loopsLeft = getLoopsLeft(walkDelay);
      return true;
    }
    return false;
  }
}