import java.util.ArrayList;

public class CloseRangeEnemy extends Enemy {

  private int shootDelay;
  private int walkDelay;
  private int loopsLeft;

  private Pathfinding pathfind;
  private ArrayList <vec2> shootDirections = new ArrayList<>();
  private ArrayList <Sprite> drops = new ArrayList<>();
  private int damage;


  public CloseRangeEnemy(String name, String icon, int health, int shootDelay, int walkDelay, int damage) {
    super(name, icon);
    setMaxHealth(health);
    maxHeal();
    this.shootDelay = shootDelay;
    this.walkDelay = walkDelay;
    this.damage = damage;

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
    Sprite plr = Player.getPlayer();
    vec2 plrAt = SpriteTracker.getSpritePosition(plr);
    vec2 zombieAt = SpriteTracker.getSpritePosition(this);
    if (Math.abs(plrAt.x - zombieAt.x) <= 1 && Math.abs(plrAt.y - zombieAt.y) <= 1) {
      plr.damage(damage);
      loopsLeft = getLoopsLeft(shootDelay);
      return true;
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