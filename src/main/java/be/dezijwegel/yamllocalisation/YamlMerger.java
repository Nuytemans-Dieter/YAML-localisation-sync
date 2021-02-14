package be.dezijwegel.yamllocalisation;

import be.dezijwegel.yamllocalisation.representer.QuotedString;

import java.util.HashMap;
import java.util.Map;

public class YamlMerger {

    private final Map<String, String> defaults;
    private final Map<String, String> fileContents;

    public YamlMerger(YamlReader defaultFile, YamlReader file) {
        this.defaults = defaultFile.getContents();
        this.fileContents = file.getContents();
    }

    public Map<String, String> getNewFileContents()
    {
        Map<String, String> contents = new HashMap<>();

        for (Map.Entry<String, String> entry : defaults.entrySet())
        {
            String key = entry.getKey();
            String value = (this.fileContents.containsKey( key )) ? this.fileContents.get( key ) : entry.getValue();
            contents.put( key, value );
        }

        return  contents;
    }

}
