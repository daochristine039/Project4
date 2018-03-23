package assignment4;

/**
 * Critter2 is represented by "2" on the world map.
 * Critter2 only fights if the opponent's Critter type is different from its own Critter type.
 * During a doTimeStep(), Critter2 reproduces as long as it has enough energy.
 * @author Christine Dao
 * EID: cd33279
 */
public class Critter2 extends Critter{
    @Override
    public String toString() { return "2"; }

    public boolean fight(String opponent) {
        if(opponent.equals("Critter2")){             //If opponent is of the same kind,
            walk(Critter.getRandomInt(getEnergy())); //Walk away and return false
            return false;
        } else{
            return true;
        }
    }

    @Override
    public void doTimeStep() {
        for(int i = 0; this.getEnergy() >= Params.min_reproduce_energy; i++) {    //Reproduce as long as it has enough energy
            Critter2 child = new Critter2();
            reproduce(child, this.getEnergy() - 4);
        }
    }
}
