package assignment4;

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
        for(int i = 0; this.getEnergy() >= 0; i++) {             //Reproduce as long as it has energy
            Critter2 child = new Critter2();
            reproduce(child, this.getEnergy() - 4);
        }
    }
}
