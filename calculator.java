package H_index_calc;

import java.util.*;
import java.util.stream.*;


//were swinging this cuz i dont wanna bother with JavaFx download
public class calculator {
	public static Integer[] h_nums;
	//each h index score is seperated by a single ',' in a String
	// h index (f) = max{i ∈ N : f[i] >= i}


	public static int h_bomb(String ind){
		//takes in user input and outputs h index
		try{

			h_nums = Stream.of(ind.split(","))
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
    public static void main(String[] args)
	{
		System.out.println(h_bomb("3,0,6,1,5"));
		
	}
}
