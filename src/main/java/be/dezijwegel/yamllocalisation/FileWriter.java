package be.dezijwegel.yamllocalisation;


import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileWriter {

    private final Map<String, String> content;

    public FileWriter(Map<String, String> content)
    {
        this.content = content;
    }

    public void write( String filePath ) throws IOException
    {
        // Empty any existing files
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
        BufferedReader reader = new BufferedReader( new FileReader("template.yml")  );

        String line;
        while ((line = reader.readLine()) != null) {

            // Replace placeholders
            String[] replaceThis = StringUtils.substringsBetween(line, "{", "}");
            if (replaceThis != null)
            {
                for (String tag : replaceThis)
                {
                    String placeholder = "{" + tag + "}";
                    String newString = content.get( tag ) != null ? content.get( tag ) : "";
                    line = line.replace( placeholder, "\"" + newString + "\"" );
                }
            }

            // Write to file
            writer.append(line);
            writer.newLine();
        }

        writer.close();
    }

}
