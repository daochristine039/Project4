package assignment4;

public class Critter1 extends Critter{
    @Override
    public String toString() { return "1"; }

    public boolean fight(String opponent) {
        return(opponent.equals("Critter1"));    //If the opponent is of its kind, return true
    }

    @Override
    public void doTimeStep() {
        if(getEnergy() > 15) {                  //If energy level is lower than 15,
            run(Critter.getRandomInt(this.getEnergy()));    //Run
        }
    }
}
