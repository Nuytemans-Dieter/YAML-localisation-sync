package be.dezijwegel.yamllocalisation;

import be.dezijwegel.yamllocalisation.representer.StringRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class YamlReader {

    private final Map<String, String> contents;

    public YamlReader(  String filename ) throws FileNotFoundException
    {
        FileInputStream fis = new FileInputStream( filename );

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(new StringRepresenter(), options);
        Map<String, String> yamlContent = yaml.load( fis );
        this.contents = yamlContent != null ? yamlContent : new HashMap<>();
    }


    public Map<String, String> getContents()
    {
        return this.contents;
    }
}
