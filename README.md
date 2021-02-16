# YAML localisation synchronisation

## Introduction
This GitHub Action uses a template YAML file to **sync changes** to all other files.
It allows having multiple (language) config files and makes you only have to change one file in order to propagate these changes to the other files.
It is possible to add/remove config options or comments. When using this action, you only have to edit `template.yml` and commit the changes, this action will edit all other files accordingly (and automatically). When adding new options, it is required to add this option to the default config file as well so that missing options in the other files can revert to their default.

## My use case
I am using **several files for localisation** (one per language) that all contain comments to explain each message and more info on specific customisation such as placeholders. Whenever I added a new message, removed a message, changed a comment, removed a comment, added a comment, ... I would have to add it to all language files. For my specific use case, I made a GitHub workflow.
Note: This is a classic case of rather spending 5 hours to automate something rather than spend 5 minutes every now and then, but it's simply worth it!
Note 2: An alternative solution is using a template file that contains all comments and options, while the regular files solely contain the key-value pairs. However, that would still require you to add missing options manually which I did not desire.

## Requirements
### Restrictions
 - Any file to be synced may only contain String values
 - Any file to be synced may not contain multi-line values
 - All files to be synced must be in the same directory
 - The specified folder must not contain any file that should not be synced (eg. a config.yml file among localisation files. Folders or other extensions than .yml are allowed)
### Required files
 - A template.yml file is required. This file will be used to build all other files. For more info on this template file, follow the scenario below.
 - A default file is required to fill in any missing config options in the other files. When using this to sync localisation files, it is advised to use the most common (probably english) language file as default. The default file will be synced with the template as well.

This action can take two inputs:
- `lang_path` which specifies the path to all language files.
- `default_file` specifies which file contains all default options and is used to fill in missing options in other files. It will be synced as well so it can serve as a localisation file itself too.

Let's examine a simple scenario below. Imagine a repo with following structure:

````
| - .git/
| - src/
|    | - <redacted>
| - readme.md
| - resources
|    | - lang/
|         | - en-us.yml
|         | - template.yml
|         | - nl-be.yml
````

`template.yml` must contain all desired config options and comments as follows:
Note that we don't specify any default values in template.yml, it is merely a template to build our other files.
It is important to utilise matching keys and values, but the values must be wrapped in curly brackets (eg. these_must_be_equal: {these_must_be_equal}).

````
# This comment explains the setting below
some_lang_setting: {some_lang_setting}

# A random comment
# And another one!

another_setting: {another_setting}
````


Our default file must contain all options (or more) of `template.yml`. It does not matter which file is used as default, keep in mind that the default file is only used to fill in missing options of the other files. For this scenario, let's go with `en-us.yml`, which may look like this:
````
some_lang_setting: "This is the default of this setting"
another_setting: "This is the default of another setting"
````

The contents of all remaining files does not matter, as they will be completely overwritten. Let's assume that `nl-be.yml` contains a single value:
`some_lang_setting: "Imagine a Dutch String here"`

Once the file requirements have been met, we can add a workflow to the repository. Go to the actions tab and press `new workflow`. Choose to set up a workflow yourself and copy/paste the code below. Note that the variables in the code below are set to work in our simple example.
We see two options under env: `LANG_PATH` and `DEFAULTS`. The former should point to the folder containing your language files and the latter should be the file name of your default language file. Make sure to edit these variables to suit your project.

````
name: Yaml autosync

on: [push]

jobs:
  build:
    runs-on: ubuntu-latest
    env:
      LANG_PATH: resources/lang
      DEFAULTS: en-us.yml
    steps:
      - uses: actions/checkout@v2
        with:
          persist-credentials: false
          fetch-depth: 0
      - name: Set up JDK 1.8
        uses: actions/setup-java@v1
        with:
          java-version: 1.8
      - name: Clone YAML-localisation-sync
        run: git clone https://github.com/Nuytemans-Dieter/YAML-localisation-sync.git
      - name: Building the project
        run: |
          cd YAML-localisation-sync
          mvn package
      - name: Moving JAR file
        run: cp YAML-localisation-sync/target/YamlLocalisation.jar ${LANG_PATH}
      - name: Running JAR file
        run: |
          cd ${LANG_PATH}
          java -jar YamlLocalisation.jar ${DEFAULTS}
      - name: Repo cleanup
        run: |
          rm -rf YAML-localisation-sync
          cd ${LANG_PATH}
          rm YamlLocalisation.jar
      - name: Commit changes
        run: |
          git config --local user.email "41898282+github-actions[bot]@users.noreply.github.com"
          git config --local user.name "YAML sync bot"
          git add .
          git commit -m "Sync YAML localisation files"
          git fetch
        continue-on-error: true
      - name: Push changes
        uses: ad-m/github-push-action@master
        with:
          github_token: ${{ secrets.GITHUB_TOKEN }}
          force: true
````

Once this workflow is set up and we commit a change, our files will look like this:
(Naturally, `template.yml` remains unchanged).

**en-us.yml**
````
# This comment explains the setting below
some_lang_setting: "This is the default of this setting"

# A random comment
# And another one!

another_setting: "This is the default of another setting"
````

**nl-be.yml**
````
# This comment explains the setting below
some_lang_setting: "Imagine a Dutch String here"

# A random comment
# And another one!

another_setting: "This is the default of another setting"
````
