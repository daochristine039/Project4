package assignment4;

/**
 * Critter1 is represented by "1" on the world map.
 * Critter1 only fights when its opponent is of Critter1 type as well.
 * During a doTimeStep(), Critter1 runs if its energy level is bigger than 15.
 * @author Christine Dao
 * EID: cd33279
 */
public class Critter1 extends Critter{
    @Override
    public String toString() { return "1"; }

    public boolean fight(String opponent) {
        return(opponent.equals("Critter1"));    //If the opponent is of its kind, return true
    }

    @Override
    public void doTimeStep() {
        if(getEnergy() > 15) {                  //If energy level is bigger than 15,
            run(Critter.getRandomInt(this.getEnergy()));    //Run
        }
    }
}
