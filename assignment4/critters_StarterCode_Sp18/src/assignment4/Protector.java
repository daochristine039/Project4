package assignment4;

public class Protector extends Critter{
    @Override
    public String toString() { return "P"; }

    public boolean fight(String opponent) {
        if(opponent.equals("Protector")){
            walk(Critter.getRandomInt(getEnergy()));
            return false;
        } else{
            return true;
        }
    }

    @Override
    public void doTimeStep() {
        for(int i = 0; i < this.getEnergy(); i++) {
            Protector child = new Protector();
            reproduce(child, this.getEnergy() - 4);
        }
    }
}
