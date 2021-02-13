package be.dezijwegel.yamllocalisation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    private final List<String> lines;

    public FileWriter(List<String> lines)
    {
        this.lines = lines;
    }

    public void write( String filePath ) throws IOException
    {
        // Empty any existing files
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));

        for (String line : lines)
        {
            writer.append(line);
            writer.newLine();
        }

        writer.close();
    }

}
