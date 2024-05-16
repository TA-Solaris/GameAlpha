
// Declaring the package
package Tools;

// Importing packets
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.Paths;

import java.util.LinkedList;

public class NameGenerator {

  // Creating objects
  LinkedList<String> myNames = new LinkedList<String>();
  RandomGenerator myGenerator = new RandomGenerator();
  
  public NameGenerator(String path) {

    try {

      File myFile = new File(Paths.get("").toAbsolutePath() + path);
      Scanner myScanner = new Scanner(myFile);

      while (myScanner.hasNextLine()) {
        myNames.add(myScanner.nextLine());
      }

      myScanner.close();

    } catch (FileNotFoundException e) {
      System.out.println("This shouldn't happen unless path is wrong. ");
      e.printStackTrace();
    }

  }

  public String getName() {
    
    int nameNum = myGenerator.randInt(myNames.size() - 1);
    String name = String.valueOf(myNames.get(nameNum));

    myNames.remove(nameNum);
    if (name.charAt(name.length() - 1) == 'I') {
      myNames.add(name + "I");
    } else {
      myNames.add(name + " I");
    }

    return name;
  }
}
