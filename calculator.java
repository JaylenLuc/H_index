package H_index_calc;

import java.util.*;
import java.util.stream.*;
import static java.util.Map.entry;    


//were swinging this cuz i dont wanna bother with JavaFx download
public class calculator {
	public static Integer[] h_nums;
	public final static HashMap<Integer, Double> percentile =
	Map.ofEntries(
		entry(0,0.0 ),
		entry(1, 0.0),
		entry(2, 0.0),
    	entry(3, 0.0),
    	entry(4, 34.0),
		entry(5, 52.0),
		entry(6, 65.0),
		entry(7, 73.0),
		entry(8, 78.0),
		entry(9, 82.0),
		entry(10, 85.0),
		entry(11, 88.0),
		entry(12, 89.0),
		entry(13, 90.558),
		entry(14, 91.776),
		entry(15, 92.776),
		entry(16, 93.614),
		entry(17, 94.317),
		entry(18, 94.908),
		entry(19, 95.428),
		//conditionals
		//20 - 40  98
		//41-80 99
		//81 - 120 99.5
		//121 - 132 99.8

		entry(133, 99.982),
		entry(134, 99.982),
		entry(135, 99.982),
		entry(136, 99.983),
		entry(137, 99.983),
		entry(138, 99.984),
		entry(139, 99.984),
		entry(140, 99.985),
		entry(141, 99.986),
		entry(142, 99.986),
		entry(143, 99.987),
		entry(144, 99.987),
		entry(145, 99.987),
		entry(146, 99.987),
		entry(147, 99.988),
		entry(148, 99.988),
		entry(149, 99.988),
		entry(150, 99.989)
	);
	//each h index score is seperated by a single ',' in a String
	// h index (f) = max{i ∈ N : f[i] >= i}


	public static int h_bomb(String ind){
		//takes in user input and outputs h index
		try{

			h_nums = Stream.of(ind.split(",")).filter(i -> i.length() < 9 && !i.equals(""))
			.map(Integer::valueOf).sorted(Comparator.reverseOrder())
			.toArray(size -> new Integer[size]);
			//  for (int i : h_nums){
			//  	System.out.println(i);
			//  }

		}catch(NumberFormatException e){
			System.out.println(e.getMessage());
			return -1;
		}
		int i;
		for (i = 0; i < h_nums.length; i++){
			if (h_nums[i]  < i+1){
				return i;
			}
			
		}
		return i;
		
	}

	public static Double h_percent(int h){
		Double d = percentile.get(h);
		if (d == null){
			switch(h){

				 
			}
		}else{
			System.out.println(d);
			return d;
		}
	}
    public static void main(String[] args)
	{

		h_percent(40);
		h_percent(19);

		System.out.println(h_bomb("3,0,6,1,5"));
		
	}
}
