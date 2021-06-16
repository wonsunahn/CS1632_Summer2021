
public class DeathStar {
  public String shoot(Planet planet) {
    planet.damage(100);
    return planet.toString() + " was hit by the superlaser!";
  }
}