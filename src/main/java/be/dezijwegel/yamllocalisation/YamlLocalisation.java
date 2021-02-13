package be.dezijwegel.yamllocalisation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class YamlLocalisation {

    public static void main (String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Please include the base file as an argument");
            return;
        }

        if (args[0].contains("/"))
        {
            System.out.println("Execute this project in the right directory, paths are not supported as file name");
            return;
        }

        SeedFileReader seedFile;
        try {
            seedFile = new SeedFileReader( args[0] );
        } catch (IOException e) {
            System.out.println("An error occurred while reading the seed file");
            e.printStackTrace();
            return;
        }

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
                if ( !name.equalsIgnoreCase( args[0] ) )
                {
                    try {
                        FileComparator yamlFile = new FileComparator(name, seedFile);
                        List<String> newLines = yamlFile.getnewFileContents();
                        for (String line : newLines)
                            System.out.println(line);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                System.out.println("Not a YAML file: " + name);
            }
        }
    }

}
