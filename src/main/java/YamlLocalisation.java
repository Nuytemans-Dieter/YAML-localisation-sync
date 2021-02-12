import java.io.FileNotFoundException;

public class YamlLocalisation {

    public static void main (String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Please include the base file as an argument");
            return;
        }

        SeedFileReader seedFile = new SeedFileReader( args[0] );
        try {
            FileComparator file = new FileComparator( "nl.yml", seedFile );
            file.getnewFileContents();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
