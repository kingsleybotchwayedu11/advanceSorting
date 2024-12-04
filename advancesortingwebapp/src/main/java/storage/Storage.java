//using map for storage
package storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Storage {

     static int count = 0;
     static ArrayList<Integer> dataset = new ArrayList<>(Arrays.asList(53, 53, 4, 89));

    public static void addToDataSet(int number) {
       dataset.add(number);
     }

    public static void addToDataSet(List<Integer> numbers) {
            dataset.addAll(numbers);
    }

   public static boolean changeNuber(int index, int number){
        if(index < 0 || index >= dataset.size()) {
            return false;
        } else {
            dataset.set(index, number);
            return true;
        }
   }
   public static List<Integer> getNumbers() {
    return dataset;
   }

   public static boolean deleteNumber(int index) {
        if(index < 0 || index >= dataset.size())
            return false;
        else {
            dataset.remove(index);
            return true;
        }
   }
}
