package pruebaapx;

import java.util.ArrayList;
import java.util.List;

public class SolutionMov {
    
    public static void main(String[] args){
        List<Integer> avg = new ArrayList<>();
        avg.add(1);
        avg.add(0);
        avg.add(1);
        avg.add(0);
        avg.add(0);
        avg.add(0);
        avg.add(0);
        avg.add(1);
        int result = Result.minMoves(avg);
        System.out.println("RSULT: " + String.valueOf(result));

    }
        
}

class Result {
    public static int minMoves(List<Integer> avg){
        int mvs = 0;
        int[] arrayOrdered = new int[avg.size()];
        int[] ordered = new int[avg.size()];

        for (int i = 0; i < avg.size(); i++) {
            arrayOrdered[i] = avg.get(i);
        }

        for (int x = 0; x < arrayOrdered.length; x++) {

            for (int i = 0; i < (arrayOrdered.length-x-1); i++) {
                if(arrayOrdered[i] < arrayOrdered[i+1]){
                    int tmp = arrayOrdered[i+1];
                    arrayOrdered[i+1] = arrayOrdered[i];
                    arrayOrdered[i] = tmp;
                    mvs ++;
                }

            }

        }

        /*int j=1;

        for (int x = 0; x < arrayOrdered.length; x++) {
            ordered[x] = arrayOrdered[(arrayOrdered.length-j)];
            j++;
        }

        for(int i = 0; i <= (arrayOrdered.length-1); i++){
            System.out.println("ORDER: " + ordered[i]);
        }*/

        return mvs;
    }
}