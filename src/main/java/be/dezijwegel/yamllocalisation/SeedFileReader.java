package be.dezijwegel.yamllocalisation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SeedFileReader {

    private final List<String> fileLines;

    public SeedFileReader (String filePath) throws IOException {

        fileLines = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader( filePath ));
        String line;
        while ((line = br.readLine()) != null) {
            fileLines.add( line );
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
