import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Cereal
{
    public String name;
    public int shelf;
    public double rating;
    public int sugar;

    public Cereal(String name, int shelf, double rating, int sugar)
    {
        this.name = name;
        this.shelf = shelf;
        this.rating = rating;
        this.sugar = sugar;
    }

    public static void main(String[] args) throws IOException
    {
        //Take in all data from csv
        ArrayList<Cereal> cerealList = new ArrayList();
        Scanner scanner = new Scanner(new File("Cereal.csv"));
        scanner.nextLine(); //Skips the first line because it has column details.
        while (scanner.hasNextLine())
        {
            String[] parts = scanner.nextLine().split(",");
            cerealList.add(new Cereal(parts[0], Integer.parseInt(parts[11]), Double.parseDouble(parts[14]), Integer.parseInt(parts[8])));
        }

        int shelf1Number = 0;
        int shelf2Number = 0;
        int shelf3Number = 0;
        double shelf1RatingTotal = 0;
        double shelf2RatingTotal = 0;
        double shelf3RatingTotal = 0;

        for(Cereal c : cerealList)
        {
            if((c.shelf)==1){
                shelf1RatingTotal += c.rating;
                shelf1Number++;
            }else if((c.shelf)==2){
                shelf2RatingTotal += c.rating;
                shelf2Number++;
            }else if((c.shelf)==3){
                shelf3RatingTotal += c.rating;
                shelf3Number++;
            }
        }

        double shelf1Average = shelf1RatingTotal/shelf1Number;
        double shelf2Average = shelf2RatingTotal/shelf2Number;
        double shelf3Average = shelf3RatingTotal/shelf3Number;

        System.out.println("Cereals on shelf 1 have an average rating of: " + shelf1Average);
        System.out.println("Cereals on shelf 2 have an average rating of: " + shelf2Average);
        System.out.println("Cereals on shelf 3 have an average rating of: " + shelf3Average);

        if (shelf1Average > shelf2Average && shelf1Average > shelf3Average)
        {
            System.out.println("Shelf 1 has the highest average rating.");
        }
        if (shelf2Average > shelf1Average && shelf2Average > shelf3Average)
        {
            System.out.println("Shelf 2 has the highest average rating.");
        }
        if (shelf3Average > shelf2Average && shelf3Average > shelf1Average)
        {
            System.out.println("Shelf 3 has the highest average rating.");
        }
    }

    public String getName()
    {
        return name;
    }

    public int getShelf()
    {
        return shelf;
    }

    public double getRating()
    {
        return rating;
    }

    public int getSugar()
    {
        return sugar;
    }

    public String toString()
    {
        return name;
    }
}
