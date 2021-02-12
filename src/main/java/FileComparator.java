import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileComparator {

    private final Map<String, Object> contents;
    private final SeedFileReader seedFileReader;
    private final Yaml yaml;

    public FileComparator(String filePath, SeedFileReader seedFileReader) throws FileNotFoundException {

        this.seedFileReader = seedFileReader;

        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream( filePath );

        if (inputStream == null)
            throw new FileNotFoundException("File '" + filePath + "' not found!");

        // SnakeYAML options
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        this.yaml = new Yaml(options);
        this.contents = yaml.load(inputStream);
    }

    public List<String> getnewFileContents()
    {
        List<String> lines = new ArrayList<>();

        for ( String seedLine : this.seedFileReader.getLines() )
        {
            Map<String, Object> lineContents = this.yaml.load( seedLine );

            if (lineContents == null)
            {
                lines.add(seedLine);
            }
            else
            {
                Map<String, Object> newLineContents = new HashMap<>();

                for (Map.Entry<String, Object> entry : lineContents.entrySet())
                {
                    String key = entry.getKey();
                    Object value = (contents.containsKey( key )) ? this.contents.get( key ) : entry.getValue();
                    newLineContents.put( key, value );
                }

                String output = this.yaml.dump( newLineContents );
                lines.add( output );
            }
        }

        return lines;
    }

}
