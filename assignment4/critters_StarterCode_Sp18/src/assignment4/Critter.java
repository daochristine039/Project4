package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


//import org.omg.CORBA.DynAnyPackage.Invalid;

import java.lang.reflect.Constructor;
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
	
	protected final void walk(int direction) {
			energy -= Params.walk_energy_cost;
			moved = true;


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
	
	protected final void run(int direction) {
			energy -= Params.run_energy_cost;
			moved = true;


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
	
	protected final void reproduce(Critter offspring, int direction) {
	    if(this.energy < Params.min_reproduce_energy){
	        return;
        }

        offspring.energy = this.energy/2;
	    this.energy = Math.round(this.energy/2);

        offspring.x_coord = this.x_coord;
        offspring.y_coord = this.y_coord;
        offspring.walk(direction);

		if(offspring.x_coord > Params.world_width - 1){     //Update position if off the top of the world
			offspring.x_coord -= Params.world_width;
		} else if(offspring.x_coord < 0){
			offspring.x_coord += Params.world_width;
		}

		if(offspring.y_coord > Params.world_height - 1){
			offspring.y_coord -= Params.world_height;
		} else if(offspring.y_coord < 0){
			offspring.y_coord += Params.world_height;
		}

        offspring.energy += Params.rest_energy_cost;
        offspring.moved = false;
        babies.add(offspring);
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
	@Deprecated
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Critter newCritter = (Critter) Class.forName("assignment4." + critter_class_name).newInstance();
            newCritter.energy = Params.start_energy;
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);
			newCritter.moved = false;

			Critter.population.add(newCritter);


		}catch(InstantiationException | ClassNotFoundException | IllegalAccessException event){
			throw new InvalidCritterException(critter_class_name);
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

		/*for(int i = 0; i < population.size(); i++){
			try{
				if(population.get(i).)
			}catch{

			}
		}*/

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
		// Complete this method.
	}

	@Deprecated
	public static void worldTimeStep() {
		List<Critter> encounters = new java.util.ArrayList<Critter>();

		for (int i = 0; i < population.size(); i++) {
			/*int tempX = 0, tempY = 0;
			tempX = current.x_coord;			//Saved to compare with new updates
			tempY = current.y_coord;
			*/

			population.get(i).doTimeStep();                //Update energy and position

			if (population.get(i).x_coord > Params.world_width - 1) {     //Update position if off the top of the world
				population.get(i).x_coord -= Params.world_width;
			} else if (population.get(i).x_coord < 0) {
				population.get(i).x_coord += Params.world_width;
			}

			if (population.get(i).y_coord > Params.world_height - 1) {
				population.get(i).y_coord -= Params.world_height;
			} else if (population.get(i).y_coord < 0) {
				population.get(i).y_coord += Params.world_height;
			}


			/*if(population.get(i).energy <= 0){
				population.remove(i);		//Remove dead critters
			}*/
		}

		/*for(int i = 0; i < population.size(); i++){                 //Update to no move
		    population.get(i).moved = false;
        }*/


		for (int i = 0; i < population.size(); i++) {                 //Resolve encounters
			Critter lastSurvivor = population.get(i);
			boolean fight1, fight2;
			boolean dead1 = false, dead2 = false;
			int dice1 = 0, dice2 = 0;
			int tempX = lastSurvivor.x_coord, tempY = lastSurvivor.y_coord;

			if (lastSurvivor.energy <= 0) {       //Dead?
				continue;
			}

			for (int k = 0; k < population.size(); k++) {
				if (k != i) {
					Critter potentialOpponent = population.get(k);

					if (potentialOpponent.energy <= 0) {  //Dead?
						continue;
					}


                    /*if (lastSurvivor.energy <= 0) {                                   //Dead?
                        if (encounters.contains(lastSurvivor)) {
                            encounters.remove(lastSurvivor);
                        }
                        population.remove(lastSurvivor);
                        dead1 = true;
                    }

                    if (opponent.energy <= 0) {
                        encounters.remove(k);
                        population.remove(opponent);
                        dead2 = true;
                    }*/

					//If they are both not dead, haven't moved, and are in the same position
					if ((lastSurvivor.x_coord == potentialOpponent.x_coord) && (lastSurvivor.y_coord == potentialOpponent.y_coord)) {
						fight1 = lastSurvivor.fight(potentialOpponent.toString());      //Do they want to fight? Are they running away?
						fight2 = potentialOpponent.fight(lastSurvivor.toString());

						if (lastSurvivor.energy <= 0) {
							break;
						}
						if (potentialOpponent.energy <= 0) {
							continue;
						}

						if ((lastSurvivor.x_coord == potentialOpponent.x_coord) && (lastSurvivor.y_coord == potentialOpponent.y_coord)) {
							if (fight1) {
								dice1 = Critter.getRandomInt(lastSurvivor.energy);
							}
							if (fight2) {
								dice2 = Critter.getRandomInt(potentialOpponent.energy);
							}

							if (dice1 < dice2) {      //If dice2 is bigger, opponent wins
								potentialOpponent.energy += (lastSurvivor.energy / 2);
								lastSurvivor.energy = 0;
                                /*for (int m = 0; m < population.size(); m++) {
                                    if (population.get(m).equals(potentialOpponent)) {
                                        population.get(m).energy += (lastSurvivor.energy / 2);
                                        lastSurvivor = population.get(m);
                                        encounters.remove(lastSurvivor);
                                        break;
                                    }
                                }*/
							} else {
								lastSurvivor.energy += (potentialOpponent.energy / 2);
								potentialOpponent.energy = 0;
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < population.size(); i++) {
			population.get(i).energy -= Params.rest_energy_cost;
		}

		for (int i = 0; i < population.size(); ) {
			if (population.get(i).energy <= 0) {
				population.remove(population.get(i));
			} else {
				i++;
			}
		}

		for (int i = 0; i < population.size(); i++) {
			population.get(i).moved = false;
		}

		for (int i = 0; i < Params.refresh_algae_count; i++) {
			try {
				makeCritter("Algae");
			}catch(InvalidCritterException event){
				System.out.println("Invalid critter exception");
			}
		}

		population.addAll(babies);
		babies.clear();
	}
	
	public static void displayWorld() {
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
		    System.out.print("-");
        }
        System.out.println("+");

		for(int i = 0; i < Params.world_height; i++){
		    System.out.print("|");
		    for(int j = 0; j < Params.world_width; j++){
		        for(int k = 0; k < population.size(); k++){
		            if(population.get(k).y_coord == i && population.get(k).x_coord == j){
		                System.out.print(population.get(k).toString());
                    }
                }
            }
            System.out.println("|");
        }

        System.out.print("+");
        for(int i = 0; i < Params.world_width; i++){
            System.out.print("-");
        }
        System.out.println("+");
	}

}
