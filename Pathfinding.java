import java.util.ArrayList;

public class Pathfinding {
  int columns;
  int rows;
  ArrayList <vec2> currentMovements;
  ArrayList <vec2> previousPositions;
  boolean[][] checkedTiles;
  vec2 targetTile;
  
  public Pathfinding(int columns, int rows) {
    this.columns = columns;
    this.rows = rows;
  }
  
  public ArrayList <vec2> findPath(vec2 start, vec2 targetTile) {
    currentMovements = new ArrayList<>();
    if (start.equals(targetTile)) {
      return currentMovements;
    }
    this.targetTile = targetTile;
    checkedTiles = new boolean[rows][columns];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        checkedTiles[y][x] = false;
      }
    }
    checkSurroundings(start);
    previousPositions = new ArrayList<>();
    vec2 prev = start;
    for (int i = 0; i < currentMovements.size(); i++) {
      prev = prev.add(currentMovements.get(i));
      previousPositions.add(prev);
    }
    return currentMovements;
  }

  public vec2 nextPos(vec2 start, vec2 targetTile) {
    //reverse calculation system to prevent repeated movements
    ArrayList <vec2> movements = findPath(targetTile, start);
    if (movements.size() <= 0) {
      return null;
    }
    vec2 nextPos = movements.get(movements.size() - 1).mult(new vec2(-1, -1)).add(start);
    return nextPos;
  }

  public boolean checkSurroundings(vec2 tile) {
    vec2[] surroundingTiles= {
      new vec2(tile.x + 1, tile.y),
      new vec2(tile.x - 1, tile.y),
      new vec2(tile.x, tile.y + 1),
      new vec2(tile.x, tile.y - 1),
    };
    ArrayList <vec2> orderedSurroundingTiles = new ArrayList <vec2>();
    orderedSurroundingTiles.add(surroundingTiles[0]);
    ArrayList <Double> distances = new ArrayList<>();
    distances.add(targetTile.distance(surroundingTiles[0]));
    
    for (int i = 1; i < surroundingTiles.length; i++) {
      boolean added = false;
      double currentDistance = targetTile.distance(surroundingTiles[i]);
      if (previousPositions != null) {
        for (int z = 0; z < previousPositions.size(); z++) {
          if (surroundingTiles[i].equals(previousPositions.get(z))) {
            currentDistance -= 1.2; //prioritize previous paths to prevent repeated movements
            break;
          }
        }
      }
        
      for (int a = 0; a < distances.size(); a++) {
        if (currentDistance < distances.get(a)) {
          added = true;
          orderedSurroundingTiles.add(a, surroundingTiles[i]);
          distances.add(a, targetTile.distance(surroundingTiles[i]));
          break;
        }
      }
      
      if (!added) {
        orderedSurroundingTiles.add(surroundingTiles[i]);
        distances.add(targetTile.distance(surroundingTiles[i]));
      }
    }

    for (int i = 0; i < orderedSurroundingTiles.size(); i++) {
      vec2 currentPoint = orderedSurroundingTiles.get(i);
      if (!Grid.withinBounds(currentPoint)) {
        continue;
      }
      if (!Grid.wallFound(new vec2(currentPoint.x, currentPoint.y)) && !checkedTiles[currentPoint.y][currentPoint.x]) {
        checkedTiles[currentPoint.y][currentPoint.x] = true;
        currentMovements.add(tile.subtract(currentPoint));
        if (targetTile.equals(currentPoint.x, currentPoint.y)) {
          return true;
        }
        boolean returnValue = checkSurroundings(currentPoint);
        if (!returnValue) {
          currentMovements.remove(currentMovements.size() - 1);
        } else {
          return true;
        }
      }
    }
    return false;
  }
}