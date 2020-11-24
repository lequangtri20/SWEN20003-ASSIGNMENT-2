import bagel.*;
import java.util.ArrayList;

/**
 * This class includes main function (along with some utilities) and is used to regulate all other classes.
 * @author Quang Tri Le.
 */
public class ShadowLife extends AbstractGame{

    private final Image backgroundImg;
    private static long start; /* Starting point to count the time */
    private static long end; /* End point of every tick */
    private static int maxTick;
    private static int totalTick = 0;
    private static String filePath;

    /**
     * allActors is used to store all actors which is read from world file.
     */
    public static ArrayList<actors> allActors;

    /**
     * diff is used to store the difference between "start" and "end", used to trigger movements.
     */
    public static long diff;

    /**
     * tickRate is the amount of time within a tick, will be assigned according to commandline arguments.
     */
    public static int tickRate;


    /**
     * This method is the only constructor of ShadowLife class.
     */
    public ShadowLife(){
        super(1024, 768, "ShadowLife");
        backgroundImg = new Image("res/images/background.png");
        start = System.currentTimeMillis(); /* Time starts to count when background image is drawn */
    }

    /**
     * This method will periodically update the state of all actors in allActors, check for timed out and check if
     * all gatherers/thieves are still active.
     * @param input This is used to take input from keyboard.
     */
    @Override
    public void update(Input input){
        backgroundImg.drawFromTopLeft(0,0);
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        end = System.currentTimeMillis();   /* end milestone is checked every time update is called */
        diff = end - start;

        if ((diff >= tickRate) && (diff < (tickRate+50))){  /* when a tick is achieved,  diff is rest*/
            start = end;
            totalTick += 1;
        }

        /* Check for Timed out */
        if (totalTick > maxTick){
            System.out.println("Timed out");
            System.exit(-1);
        }

        /* Update allActors with any just created/destroyed life object (gather and thief) */
        deadOrAlive();

        /* Check if all life actors still active */
        if (!checkActive()){
            System.out.println(totalTick+ " ticks");
            printFruit();
            System.exit(0);
        }

        /* Update all actors */
        for (actors act: allActors){
            act.update();
        }
    }

    /**
     * This is the main method of the whole project, it will be used to take in command line arguments.
     * @param args This is commandline argument that includes tick rate, maximum number of ticks and world file.
     */
    public static void main(String[] args){
        if (args.length != 3 || isNotValidNumber(args[0]) || isNotValidNumber(args[1])){
            System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>");
            System.exit(-1);
        }
        ShadowLife test = new ShadowLife();
        filePath = args[2];
        maxTick = Integer.parseInt(args[1]);
        tickRate = Integer.parseInt(args[0]);

        /* Read the csv file and return a list of actors */
        CSV csv = new CSV(filePath);
        allActors = csv.readCSV();

        test.run();
    }


    /* Check if the number from command line argument is valid */
    private static boolean isNotValidNumber(String strNum){
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

    /* Check if all gatherers/thieves are still active */
    private static boolean checkActive(){
        boolean active = false;
        for (actors actor : allActors){
            if (actor.getActorType().equals("Gatherer") || actor.getActorType().equals("Thief")){
                life actorLife = (life) actor;
                if (actorLife.active)
                    active = true;
            }
        }
        return active;
    }

    /* Print number at all storages when all gatherers and thieves are deactivated */
    private static void printFruit(){
        for(actors actor : allActors){
            if (actor.getActorType().equals("Hoard") || actor.getActorType().equals("Stockpile") ){
                storage storer = (storage) actor;
                System.out.println(storer.getNumFruit());
            }
        }
    }

    /* Check if there is any gatherer/thief is made or destroyed */
    private static void deadOrAlive(){
        for (int del = 0; del < life.lifeToDel.size(); del ++ ){
            allActors.remove(life.lifeToDel.get(del));
        }

        allActors.addAll(life.lifeToAdd);

        life.lifeToDel.clear();
        life.lifeToAdd.clear();
    }

}
