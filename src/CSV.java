import bagel.util.Point;
import bagel.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class will read the world file and record all actors into an ArrayList (allActors).
 * @author Quang Tri Le.
 */
public class CSV {
    private final String filePath;
    private String oneLineInput = ""; /* store all lines from test.csv to this variable, each line separated by "-" */
    private int numActor = 0;   /* store number of actors from test.csv */

    /**
     * The only constructor of class CSV.
     * @param filePath directory to the world file.
     */
    public CSV(String filePath) {
        this.filePath = filePath;
    }


    /**
     * This method will read the world file and record all actors.
     * @return ArrayList<actors> This returns an array of all actors from world file.
     */
    public ArrayList<actors> readCSV() {
        /* Read test.csv and load content to oneLineInput, each line separated by "-" */
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String text;
            while ((text = br.readLine()) != null) {
                oneLineInput += text;
                oneLineInput += "-";    /* adding separator "-" to split each line later */
                numActor += 1;
            }
        } catch (Exception e) {
            System.out.println("error: file " + "\"<"+ filePath + ">\" not found");
            System.exit(-1);
        }


        /* Store all actors read from test.csv */
        ArrayList<actors> allActors = new ArrayList<actors>();

        /* Split each line in oneLineInput by delimiter "-" to linesArray */
        /* Store discrete lines of actors */
        String[] linesArray = oneLineInput.split("-");

        /* For each element in linesArray, identify its actor type and coordinates */
        for (int i = 0; i < numActor; i++) {
            /* Check validity of each line */
            if (!checkLine(linesArray[i])){
                System.out.println("error: in file " + "\"<"+ filePath + ">\" at line <" + (i+1) + ">");
                System.exit(-1);
            }
            /* store each field for each line */
            String[] columns = linesArray[i].split(",");
            String actorType = columns[0];
            Point point = new Point(Double.parseDouble(columns[1]), Double.parseDouble(columns[2]));

            if (actorType.equals("Thief")){
                allActors.add(new thief(actorType, point, new Image("res/images/thief.png"), "UP"));
            }

            else if (actorType.equals("Gatherer")){
                allActors.add(new gatherer(actorType, point, new Image("res/images/gatherer.png"),
                        "LEFT"));
            }

            else {
                if (actorType.equals("Tree"))
                    allActors.add(new storage(actorType, point, new Image("res/images/tree.png"), 3));

                else if (actorType.equals("GoldenTree"))
                    allActors.add(new storage(actorType, point, new Image("res/images/gold-tree.png"),
                            3));

                else if (actorType.equals("Pad"))
                    allActors.add(new noLife(actorType, point, new Image("res/images/pad.png")));

                else if (actorType.equals("Fence"))
                    allActors.add(new noLife(actorType, point, new Image("res/images/fence.png")));

                else if (actorType.equals("SignRight"))
                    allActors.add(new noLife(actorType, point, new Image("res/images/right.png")));

                else if (actorType.equals("SignLeft"))
                    allActors.add(new noLife(actorType, point, new Image("res/images/left.png")));

                else if (actorType.equals("SignUp"))
                    allActors.add(new noLife(actorType, point, new Image("res/images/up.png")));

                else if (actorType.equals("SignDown"))
                    allActors.add(new noLife(actorType, point, new Image("res/images/down.png")));

                else if (actorType.equals("Hoard"))
                    allActors.add(new storage(actorType, point, new Image("res/images/hoard.png"),
                            0));

                else if (actorType.equals("Stockpile"))
                    allActors.add(new storage(actorType, point, new Image("res/images/cherries.png"),
                            0));

                else if (actorType.equals("Pool"))
                    allActors.add(new noLife(actorType, point, new Image("res/images/pool.png")));
            }
        }
        return allActors;
    }

    /* Check if each line is valid */
    private boolean checkLine(String line){
        ArrayList<String>  allName = new ArrayList<String>(Arrays.asList("SignRight", "SignLeft", "SignUp", "SignDown",
                "Pad", "Fence", "Stockpile", "Hoard", "Tree", "GoldenTree", "Gatherer", "Thief","Pool"));

        if (line.split(",").length != 3)
            return false;
        if (isNotValidNumber(line.split(",")[1]) ||  isNotValidNumber(line.split(",")[2]))
            return false;
        if (!allName.contains(line.split(",")[0]))
            return false;

        return true;
    }
    /* Check if the number from command line argument is valid */
    private boolean isNotValidNumber(String strNum){
        if (strNum == null) {
            return true;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return Integer.parseInt(strNum) < 0;
    }

}