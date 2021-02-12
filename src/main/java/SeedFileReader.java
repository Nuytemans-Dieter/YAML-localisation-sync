import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SeedFileReader {

    private final List<String> fileLines;

    public SeedFileReader (String filePath)
    {

        fileLines = new ArrayList<>();

//        try (BufferedReader br = new BufferedReader(new FileReader( filePath ))) {
        InputStream in = getClass().getResourceAsStream("/" + filePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileLines.add( line );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets a copy
     *
     * @return the contents of a file
     */
    public List<String> getLines()
    {
        return new ArrayList<>( fileLines );
    }

}
