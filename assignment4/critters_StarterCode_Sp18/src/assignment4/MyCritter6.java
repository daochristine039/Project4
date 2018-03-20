package assignment4;

import assignment4.Critter.TestCritter;

public class MyCritter6 extends TestCritter {
	
	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String opponent) {
	    if(!this.get_moved()) {
            run(getRandomInt(8));
        } else {
	    	this.setEnergy(this.getEnergy() - Params.run_energy_cost);
        }
        return false;
	}

	@Override
	public String toString () {
		return "6";
	}
}
