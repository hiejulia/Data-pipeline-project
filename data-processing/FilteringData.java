import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class FilteringData {

    private static final int MIN_AREA = 1000000;  

    public static void main(String[] args) {

        File file = new File("data/Countries.dat");

        Set<Country> dataset = readDataset(file);
        
        for (Country country : dataset) {
            if (country.landlocked && country.area >= MIN_AREA) {
                System.out.println(country);
            }
        }
    }
    
    public static Set readDataset(File file) {
        Set<Country> set = new HashSet();
        try {
            Scanner input = new Scanner(file);
            input.nextLine();  // read past headers
            while (input.hasNextLine()) {
                set.add(new Country(input));
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return set;
    }
}

/*
run:
Niger        21,563,607    1,268,447 true
Mali         18,689,966    1,221,566 true
Chad         14,965,482    1,257,604 true
Bolivia      11,052,864    1,083,614 true
*/