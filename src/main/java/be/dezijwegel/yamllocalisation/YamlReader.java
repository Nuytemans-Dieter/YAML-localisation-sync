package be.dezijwegel.yamllocalisation;

import be.dezijwegel.yamllocalisation.representer.StringRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class YamlReader {

    private final Map<String, String> contents;

    public YamlReader(  String filename ) throws FileNotFoundException
    {
        FileInputStream fis = new FileInputStream( filename );

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(new StringRepresenter(), options);
        this.contents = yaml.load( fis );
    }


    public Map<String, String> getContents()
    {
        return this.contents;
    }
}
