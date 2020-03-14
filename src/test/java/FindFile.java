import org.squareroots.churchstuff.Misc.FileHandler;

import javax.swing.*;

public class FindFile {
    String[] arrA = {"Arnold", "Joe", "Jason", "Alex", "Matt"};
    String[] arrB = {"Bob", "Joe", "Jason", "Alex", "Matt"};
   static JFrame frame = new JFrame();
   static JPanel panel = new JPanel();
   static JButton scanBUtton = new JButton("Scan");
   static JButton findNewBUtton = new JButton("Find new");
    static FileHandler csfh = new FileHandler();


    public static void main(String[] args) { // TODO: 10/25/2018 USE USER INPUT TO SCAN THEN COMPARE.
        // File f = new File( System.getProperty("user.home") + "\\Videos");
        // ArrayList<String> smallerSet = new ArrayList<String>(Arrays.asList(f.list()));
    }

            public String findDifferenceBetweenArrays(String[] largeArray, String[] smallArray) {
        int indexOfLargeArray = 0;
        while(indexOfLargeArray < largeArray.length) {
            boolean containsString = smallArrayContains(largeArray[indexOfLargeArray], smallArray);

            if(!containsString) {
                return largeArray[indexOfLargeArray];}
            else {indexOfLargeArray++;}

        }
        return null;
    }
    boolean smallArrayContains(String whatToCheckFor, String[] smallArray) {
        int indexOfSmallArray = 0;
        while (indexOfSmallArray < smallArray.length) {


            if (indexOfSmallArray >= smallArray.length) {
                System.out.println(smallArray[indexOfSmallArray] + " Contains" + whatToCheckFor + " = false. From smallArrayContains function.");return false; }
            if (smallArray[indexOfSmallArray].contains(whatToCheckFor)) {return true;}
            else {indexOfSmallArray++;}

        }

        return false;
    }
}

