package assignment4;

public class Cannibal extends Critter {
    @Override
    public String toString() { return "A"; }

    public boolean fight(String opponent) {
        return(opponent.equals("Cannibal"));
    }

    @Override
    public void doTimeStep() {
        if(getEnergy() > 15) {
            run(Critter.getRandomInt(this.getEnergy()));
        }
    }
}
