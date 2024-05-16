
// Declaring the package
package Tools;

// Declaring the random generator class
public class RandomGenerator {

  // Declaring methods
  public double random () {
    return Math.random();
  }

  public int randInt (int end) {
    return (int) (random() * (end + 1));
  }

  public int randInt(int start, int end) {
    return randInt(end) + start;
  }

}
