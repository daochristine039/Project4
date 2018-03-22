package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Christine Dao>
 * <cd33279>
 * <15470>
 * Slip days used: <0>
 * Fall 2016
 */



import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean moved;
	private List<Critter> encounters = new java.util.ArrayList<Critter>();

    /**
     * Move the critter in the direction indicated by "int direction" by one spot
     * Remove the cost of energy required for walking from the critter
     * Make "moved = true" since the critter moves
     * @param direction
     */
	protected final void walk(int direction) {
			energy -= Params.walk_energy_cost;  //Remove the cost of energy for walking from the critter
			moved = true;                       //The critter moves


		switch(direction){
			case 0:
				x_coord += 1;
				break;
			case 1:
				x_coord += 1;
				y_coord -= 1;
				break;
			case 2:
				y_coord -= 1;
				break;
			case 3:
				x_coord -= 1;
				y_coord -= 1;
				break;
			case 4:
				x_coord -= 1;
				break;
			case 5:
				x_coord -= 1;
				y_coord += 1;
				break;
			case 6:
				y_coord += 1;
				break;
			case 7:
				x_coord += 1;
				y_coord += 1;
				break;
			default:
				break;
		}
	}

    /**
     * move the critter in the direction indicated by "int direction" by two spots
     * Remove the cost of energy required for running from the critter
     * Make "moved = true" since the critter moves
     * @param direction
     */
	protected final void run(int direction) {
			energy -= Params.run_energy_cost;   //Remove cost of energy from running
			moved = true;                       //Critter moves


		switch(direction){
			case 0:
				x_coord += 2;
				break;
			case 1:
				x_coord += 2;
				y_coord -= 2;
				break;
			case 2:
				y_coord -= 2;
				break;
			case 3:
				x_coord -= 2;
				y_coord -= 2;
				break;
			case 4:
				x_coord -= 2;
				break;
			case 5:
				x_coord -= 2;
				y_coord += 2;
				break;
			case 6:
				y_coord += 2;
				break;
			case 7:
				x_coord += 2;
				y_coord += 2;
				break;
			default:
				break;
		}
	}

    /**
     * Create a new critter based on the parent
     * @param direction
     * @param offspring : new child of the parent
     */
	protected final void reproduce(Critter offspring, int direction) {
	    if(this.energy < Params.min_reproduce_energy){     //If the energy level of the parent is lower than the minimum energy to reproduce,
	        return;                                        //Return
        }

        offspring.energy = this.energy/2;                  //Energy of offpsring = the parent energy divided by 2 (rounded down)
	    this.energy = Math.round(this.energy/2);           //Lower energy of parent by half (rounded up_

        offspring.x_coord = this.x_coord;                  //Set offspring's x coordinate to parent's x coordinate
        offspring.y_coord = this.y_coord;                  //Set offspring's y coordinate to parent's y coordinate
        offspring.walk(direction);                         //Call walk method to place the offspring in a call adjacent to the parent

		if(offspring.x_coord > Params.world_width - 1){    //Update x coordinate if off the top of the world
			offspring.x_coord -= Params.world_width;
		} else if(offspring.x_coord < 0){
			offspring.x_coord += Params.world_width;
		}

		if(offspring.y_coord > Params.world_height - 1){   //Update y coordinate if off the top of the world
			offspring.y_coord -= Params.world_height;
		} else if(offspring.y_coord < 0){
			offspring.y_coord += Params.world_height;
		}

        offspring.energy += Params.rest_energy_cost;      //Re-establish the lost rest energy cost
        offspring.moved = false;                          //Re-establish "moved = false"
        babies.add(offspring);                            //Add the newborn to babies list
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Critter newCritter = (Critter) Class.forName("assignment4." + critter_class_name).newInstance();    //Creating a new Critter of the subclass indicated by critter_class_name
            newCritter.energy = Params.start_energy;                                    //Set critter's instance variables to standard ones
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);
			newCritter.moved = false;

			Critter.population.add(newCritter);                   //Add the new critter to the population list
		}catch(InstantiationException | ClassNotFoundException | IllegalAccessException | NoClassDefFoundError event){  //If "critter_class_name is not valid,
			throw new InvalidCritterException(critter_class_name);                                                   //Error
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();


		try{
			Class<?> critterClass = Class.forName("assignment4." + critter_class_name);
			for(int i = 0; i < population.size(); i++){
				if(critterClass.isInstance(population.get(i))) {            //If the critter from population list is an instance of "critterClass",
					result.add(population.get(i));                          //Add the critter to the result list
				}
			}

			critterClass.getMethod("runStats", List.class).invoke(null, result);    //Run the stats for the result list

		}catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException event){  //If "critter_class_name" is not valid,
			throw new InvalidCritterException(critter_class_name);                                                //Error
		}


		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}

		protected boolean get_moved() {return super.moved;}

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}

    /**
     * Run all the methods of each critter in population
     */
	public static void worldTimeStep() {
		for (int i = 0; i < population.size(); i++) {
			population.get(i).doTimeStep();                //Run doTimeStep of each critter in population list

			if (population.get(i).x_coord > Params.world_width - 1) {     //Update x_coord if off the top of the world
				population.get(i).x_coord -= Params.world_width;
			} else if (population.get(i).x_coord < 0) {
				population.get(i).x_coord += Params.world_width;
			}

			if (population.get(i).y_coord > Params.world_height - 1) {    //Update y_coord if off the top of the world
				population.get(i).y_coord -= Params.world_height;
			} else if (population.get(i).y_coord < 0) {
				population.get(i).y_coord += Params.world_height;
			}
		}


		for (int i = 0; i < population.size(); i++) {                     //Resolve encounters
			Critter fighter1 = population.get(i);                         //Holds one critter
			boolean fight1, fight2;                                       //Hold whether the critters want to fight or not
			int dice1 = 0, dice2 = 0;                                     //Hold the number thrown by dice when resolving a fight

			if (fighter1.energy <= 0) {                                   //If the fighter1 is dead,
				continue;                                                 //Skip iteration
			}

			for (int k = 0; k < population.size(); k++) {                 //Find the critter with the same coordinates as fighter1
				if (k != i) {                                             //If it's not the same index as fighter1,
					Critter fighter2 = population.get(k);                 //Hold it into fighter2

					if (fighter2.energy <= 0) {                           //If dead, skip iteration
						continue;
					}

					//If they both have the same coordinates
					if ((fighter1.x_coord == fighter2.x_coord) && (fighter1.y_coord == fighter2.y_coord)) {
						fight1 = fighter1.fight(fighter2.toString());      //fight1, fight2 hold whether they want to fight or not
						fight2 = fighter2.fight(fighter1.toString());

						if (fighter1.energy <= 0) {                        //If dead after fight method is called, leave the loop
							break;
						}
						if (fighter2.energy <= 0) {
							continue;
						}
                        //If the critters are still in the same position after they fight, resolve the fight
						if ((fighter1.x_coord == fighter2.x_coord) && (fighter1.y_coord == fighter2.y_coord)) {
							if (fight1) {                                           //If fighter1 wants to fight,
								dice1 = Critter.getRandomInt(fighter1.energy);      //Get a random number up to fighter's energy level
							}
							if (fight2) {
								dice2 = Critter.getRandomInt(fighter2.energy);
							}

							if (dice1 < dice2) {      //If fighter2 throws a bigger number,
                                fighter2.energy += (fighter1.energy / 2);   //Fighter2 gets 1/2 of fighter1's energy
                                fighter1.energy = 0;                        //Set fighter1's energy to 0 to indicate its death
							} else {                  //If fighter1 throws a bigger number,
                                fighter1.energy += (fighter2.energy / 2);   //Fighter1 gets 1/2 of fighter2's energy
                                fighter2.energy = 0;                        //Set fighter2's energy to 0 to indicate its death
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < population.size(); i++) {              //Remove energy cost of resting for each critter
			population.get(i).energy -= Params.rest_energy_cost;
		}

		for (int i = 0; i < population.size(); ) {                 //Remove all dead critters
			if (population.get(i).energy <= 0) {
				population.remove(population.get(i));
			} else {
				i++;
			}
		}

		for (int i = 0; i < population.size(); i++) {             //Set moved back to false
			population.get(i).moved = false;
		}

		for (int i = 0; i < Params.refresh_algae_count; i++) {    //Create new Algae critters as many times as the refresh_algae_count
			try {
				makeCritter("Algae");
			}catch(InvalidCritterException event){
				System.out.println("Invalid critter exception");
			}
		}

		population.addAll(babies);              //Add all the babies to the population
		babies.clear();                         //Clear all the babies from babies list
	}

    /**
     * Print out the entire world
     */
	public static void displayWorld() {
		System.out.print("+");                              //Printing out the borders
		for(int i = 0; i < Params.world_width; i++){
		    System.out.print("-");
        }
        System.out.println("+");

		for(int i = 0; i < Params.world_height; i++){       //Go through each row
			boolean found;
		    System.out.print("|");
		    for(int j = 0; j < Params.world_width; j++){
		    	found = false;
		        for(int k = 0; k < population.size(); k++){     //Go through entire population to find critters with coordinates [i,j]
		            if(population.get(k).y_coord == i && population.get(k).x_coord == j){   //If the current critter has the same coordinates,
		                System.out.print(population.get(k).toString());                   //Print it out
		                found = true;                                                     //Critter is found
		                break;                                                            //Break the loop and go to next coordinate
                    } else{
						for(int m = 0; m < babies.size(); m++) {            //If a critter is not found in population list for the current coordinates,
							if (babies.get(k).y_coord == i && babies.get(k).x_coord == j) { //Check in babies list
								System.out.print(babies.get(k).toString());
								found = true;
								break;
							}
						}
					}
                }

				if(!found) {                    //If a critter is not found at all in both lists,
					System.out.print(" ");      //Print out a blank space
				}
            }
			System.out.println("|");            //Print this out to finish up the current row
        }

        System.out.print("+");                  //Print borders
        for(int i = 0; i < Params.world_width; i++){
            System.out.print("-");
        }
        System.out.println("+");
	}

}
