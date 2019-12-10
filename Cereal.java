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

    public static void main(String[] args)
    {
        Cereal yogurtIsBetter = new Cereal("yogurtIsBetter", 1, 100, 10);
        System.out.println(yogurtIsBetter + " " + yogurtIsBetter.getName() + " " + yogurtIsBetter.shelf + " " + yogurtIsBetter.rating + " " + yogurtIsBetter.sugar);
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
