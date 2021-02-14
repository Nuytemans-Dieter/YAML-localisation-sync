package be.dezijwegel.yamllocalisation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class YamlLocalisation {

    public static void main (String[] args) throws FileNotFoundException {

        if (args.length == 0)
        {
            System.out.println("Please provide the name of the default file as an argument");
            System.out.println("java -jar YamlLocalisation.jar <default file>");
            return;
        }

        final Set<String> ignoredFilenames = new HashSet<String>(){{
            add( "template.yml" );
        }};

        if (args[0].contains("/"))
        {
            System.out.println("Execute this project in the right directory, paths are not supported as file name");
            return;
        }

        YamlReader defaultFile = new YamlReader( args[0] );

        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null)
        {
            System.out.println("No files were found in this directory");
            return;
        }

        for (File file : listOfFiles)
        {
            String name = file.getName();
            if (file.isFile() && name.endsWith(".yml"))
            {
                if ( !ignoredFilenames.contains( name ) )
                {
                    System.out.println("Handling file: " + name);
                    try {
                        YamlMerger merger = new YamlMerger(defaultFile, new YamlReader( name ));
                        Map<String, String> content = merger.getNewFileContents();
                        FileWriter writer = new FileWriter( content );
                        writer.write( name );
                        System.out.println("Processing " + name + " is now complete");
                    } catch (FileNotFoundException ignored) {} catch (IOException e) {
                        System.out.println("Error while writing to " + name + "!");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
