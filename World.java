
// Importing packets
import java.util.LinkedList;
import java.awt.Color;

// Importing packages
import Entities.*;
import Tools.RandomGenerator;
import Tools.NameGenerator;

// Declaring the world (manages entities)
public class World {

  // Declaring Game reference
  Game myGame;

  // Declaring the player and HUD
  Player myPlayer = new Player();
  HUD myHUD;

  // Declaring the lists
  public LinkedList<Entity> myEntities = new LinkedList<Entity>();
  public LinkedList<Entity> mySelectedEntities = new LinkedList<Entity>();
  private LinkedList<Color> planetColours = new LinkedList<Color>();
  private LinkedList<Color> starColours = new LinkedList<Color>();
  
  // Declaring Generators
  RandomGenerator myGenerator = new RandomGenerator();
  private NameGenerator planetNames = new NameGenerator("/Tools/data/planet_names.txt");
  private NameGenerator shipNames = new NameGenerator("/Tools/data/ship_names.txt");

  // Declaring Generated Data
  private int numPlanets = this.myGenerator.randInt(4, 6);
  private int[] numMoons = new int[numPlanets];

  //Declairing Constants
  private final double RADIAN_CIRCLE = Math.PI * 2;

  private final int STANDARD_ANGULAR_VELOCITY_DECREASER = 3000;
  private final int ADDITIONAL_ANGULAR_VELOCITY_DECREASER = 10;
  private final int MIN_PLANET_SIZE = 18;
  private final int MAX_PLANET_SIZE = 22;
  private final int MIN_STAR_SIZE = 60;
  private final int MAX_STAR_SIZE = 80;
  private final int MIN_MOON_SIZE = 7;
  private final int MAX_MOON_SIZE = 9;
  private final int MIN_PLANET_DISTANCE = 800;
  private final int MAX_PLANET_DISTANCE = MIN_PLANET_DISTANCE + 500 * numPlanets;
  private final int PLANET_SPACER = 350;
  private final int MIN_MOON_DISTANCE = 100;
  private final int MAX_MOON_DISTANCE_SCALER = 60;
  private final int MOON_SPACER = 40;
  private final int MIN_STAR_BINARY_DISTANCE = 100;
  private final int MAX_STAR_BINARY_DISTANCE = 120;
  private final int MIN_PLANET_BINARY_DISTANCE = 35;
  private final int MAX_PLANET_BINARY_DISTANCE = 50;
  private final double PLANET_BINARY_CHANCE = 0.2;
  private final double STAR_BINARY_CHANCE = 0.2;

  // Defining the constructor
  public World (Game game) {

    // Initialising game reference
    myGame = game;

    // Initialising the HUD
    initialiseHUD(myGame.myRenderer.myWindow.width, myGame.myRenderer.myWindow.height);

    // Initializing planet colours
    initialisePlanetsColours();
    initialiseStarsColours();

    // Generating number of moons for planets
    populateNumMoons();

    // Making the Star
    Entity myStar = addStar();

    //Adding Planets and Moons
    for (int i = 0; i < numPlanets; i++) {

      Entity thisPlanet = addPlanet(myStar);

      for (int j = 0; j < numMoons[i]; j++) {
        addMoon(thisPlanet, j);
      }

    }

    // Adding the players colony ship
    addStartColony();

  }

  // Defining methods
  public void tick () {

    // Linking through the entities and ticking them
    for (int i = 0; i < myEntities.size(); i++) {
      myEntities.get(i).tick();
    }

  }

  private Entity addStar() {

    // Adding the star (or choosing binary star)
    Entity myStar = null;
    if (this.myGenerator.random() < STAR_BINARY_CHANCE) {

      //Binary star
      myStar = new Ghost();
      double angle = this.myGenerator.random() * RADIAN_CIRCLE;
      double angularVelocity = this.myGenerator.random() / (STANDARD_ANGULAR_VELOCITY_DECREASER * ADDITIONAL_ANGULAR_VELOCITY_DECREASER);
      int distance = this.myGenerator.randInt(MIN_STAR_BINARY_DISTANCE, MAX_STAR_BINARY_DISTANCE);
      Color colour = starColours.get(this.myGenerator.randInt(starColours.size() - 1));
      int size = this.myGenerator.randInt(MIN_STAR_SIZE, MAX_STAR_SIZE);

      Star star1 = new Star(size, colour);
      star1.myAngle = angle;
      star1.myAngularVelocity = angularVelocity;
      star1.myDistance = distance;
      star1.myParent = myStar;
      star1.name = planetNames.getName();
      myEntities.add(star1);
      Star star2 = new Star(size, colour);
      star2.myAngle = angle + RADIAN_CIRCLE / 2;
      star2.myAngularVelocity = angularVelocity;
      star2.myDistance = distance;
      star2.myParent = myStar;
      star2.name = planetNames.getName();
      myEntities.add(star2);

    } else {

      // Primary star
      myStar = new Star(this.myGenerator.randInt(MIN_STAR_SIZE, MAX_STAR_SIZE), starColours.get(this.myGenerator.randInt(starColours.size() - 1)));
      myStar.name = planetNames.getName();

    }
    myEntities.add(myStar);

    return myStar;

  }

  private Entity addPlanet(Entity parent) {
    while (true) {

      // Picking planet type
      Entity thisPlanet = null;
      boolean twin = false;
      if (this.myGenerator.random() < PLANET_BINARY_CHANCE) {

        // Twin planets
        twin = true;
        Ghost thisGhost = new Ghost();
        thisGhost.myDistance = this.myGenerator.randInt(MIN_PLANET_DISTANCE, MAX_PLANET_DISTANCE);
        thisGhost.myAngle = this.myGenerator.random() * RADIAN_CIRCLE;
        thisGhost.myAngularVelocity = this.myGenerator.random() / STANDARD_ANGULAR_VELOCITY_DECREASER;
        thisGhost.myParent = parent;

        thisPlanet = thisGhost;

      } else {

        // For primary planet
        thisPlanet = new Planet(this.myGenerator.randInt(MIN_PLANET_SIZE, MAX_PLANET_SIZE), planetColours.get(this.myGenerator.randInt(planetColours.size() - 1)), this.myGenerator.randInt(MIN_PLANET_DISTANCE, MAX_PLANET_DISTANCE), this.myGenerator.random() * RADIAN_CIRCLE, this.myGenerator.random() / STANDARD_ANGULAR_VELOCITY_DECREASER, parent);
        thisPlanet.name = planetNames.getName();

      }

      // Checking how close it is to other planets
      boolean planetPassed = true;
      for (int i = 0; i < myEntities.size(); i++) {
        if (myEntities.get(i).myParent == thisPlanet.myParent) {
          if (Math.abs(myEntities.get(i).myDistance - thisPlanet.myDistance) < PLANET_SPACER) {
            planetPassed = false;
          }
        }
      }
      if (planetPassed) {

        myEntities.add(thisPlanet);

        // For if the planet is binary
        if (twin) {

          int size = this.myGenerator.randInt(MIN_PLANET_SIZE, MAX_PLANET_SIZE);
          Color colour = planetColours.get(this.myGenerator.randInt(planetColours.size() - 1));
          int distance = this.myGenerator.randInt(MIN_PLANET_BINARY_DISTANCE, MAX_PLANET_BINARY_DISTANCE);
          double angle = this.myGenerator.random() * RADIAN_CIRCLE;
          double angularVelocity = this.myGenerator.random() / STANDARD_ANGULAR_VELOCITY_DECREASER;

          Planet planet1 = new Planet(size, colour, distance, angle, angularVelocity, thisPlanet);
          planet1.name = planetNames.getName();
          myEntities.add(planet1);
          Planet planet2 = new Planet(size, colour, distance, angle + RADIAN_CIRCLE / 2, angularVelocity, thisPlanet);
          planet2.name = planetNames.getName();
          myEntities.add(planet2);

        }

        return thisPlanet;
      }
    }
  }

  private void addMoon (Entity parent, int numMoon) {
    while (true) {
      Planet thisMoon = new Moon(this.myGenerator.randInt(MIN_MOON_SIZE, MAX_MOON_SIZE), planetColours.get(this.myGenerator.randInt(planetColours.size() - 1)), this.myGenerator.randInt(MIN_MOON_DISTANCE, MIN_MOON_DISTANCE + MAX_MOON_DISTANCE_SCALER * numMoons[numMoon]), this.myGenerator.random() * RADIAN_CIRCLE, this.myGenerator.random() / (STANDARD_ANGULAR_VELOCITY_DECREASER * ADDITIONAL_ANGULAR_VELOCITY_DECREASER), parent);

      // Checking how close it is to other moons
      boolean moonPassed = true;
      for (int i = 0; i < myEntities.size(); i++) {
        if (!(myEntities.get(i).myParent == null)) {
          if (myEntities.get(i).myParent == parent) {
            if (Math.abs(myEntities.get(i).myDistance - thisMoon.myDistance) < MOON_SPACER) {
              moonPassed = false;
            }
          }
        }
      }

      if (moonPassed) {
        thisMoon.name = planetNames.getName();
        myEntities.add(thisMoon);
        break;
      }
    }
  }

  // Method for making the begining colony ship
  private void addStartColony() {

    double angle = this.myGenerator.random() * RADIAN_CIRCLE;
    int distance = 350;

    ColonyShip myShip = new ColonyShip(myPlayer, myPlayer.myColour, (int) (distance * Math.sin(angle)), (int) (distance * Math.cos(angle)));
    myShip.name = shipNames.getName();
    myEntities.add(myShip);

  }

  private void populateNumMoons () {
    for (int i = 0; i < numPlanets; i++) {
      numMoons[i] = this.myGenerator.randInt(0, 2);
    }
  }

  private void initialisePlanetsColours () {
    planetColours.add(Color.BLUE);
    planetColours.add(Color.GRAY);
    planetColours.add(Color.CYAN);
    //planetColours.add(Color.DARK_GRAY);
    planetColours.add(Color.GREEN);
    //planetColours.add(Color.LIGHT_GRAY);
    planetColours.add(Color.MAGENTA);
    planetColours.add(Color.PINK);
  }

  private void initialiseStarsColours () {
    starColours.add(Color.YELLOW);
    starColours.add(Color.ORANGE);
    starColours.add(Color.RED);
  }

  public void initialiseHUD(int width, int height) {
    myHUD = new HUD(this, width, height);
  }
  
}
