# Marvel-Pastebin Java Application README

This application fetches character information from the <a href="https://developer.marvel.com/">Marvel API</a> based on user's input for character name, and displays the information (character description, featured comics/books/thumbnail etc); additionally, user can request to generate a report where the application will post the report on <a hre=" https://pastebin.com/doc_api">Pastebin</a> and display the URL on the GUI.

The application backend interacts with the Marvel API, Pastebin API, and also a SQLite database for caching response strings to be displayed in the offline version.

The application frontend was designed and developed with Scenebuilder, where a FXML file is generated and can be referenced in the respective controller class to handle UI events.

* [How to run application](#how-to-run-application)
* [Features](#features)
* [References](#reference)

## Developer's Note

<i>This was a project done for the SOFT3202 Software Design and Consturction unit in my final year of studies, it contains submitted and assessed components for the unit. The tutor has approved students to post our work as part of a portfolio and display it publicly. </i>

As part of the unit's focus on design patterns and principles, the MVP architecture and Facade pattern was applied to achieve maintanability, extensibility and modularity of the application components.

<i>If any future students who are doing SOFT2201/SOFT3203 is reading this - please double check with your unit coordinator on whether it is okay to reference this work, I am NOT promoting this work as a solution to the assessment in the unit.</i>

---

## How to run application

#### Configuration file for API keys

Marker's own API keys for both input and output APIs should be placed in the configuration JSON file `KeyConfig.json` under `src/main/resources/marvel` in order for the live versions to run.

Replace API keys as JSON strings in the following fields as annotated

```
{
"marvelKey": "<Marvel API public key>",
"marvelPrivateKey": "<Marvel API private key>",
"pastebinKey" : "<Pastebin API public key>"
}
```

NOTE: For Marvel API, **both public and private keys** need to be provided.

#### Gradle command

Option to use live or dummy versions of both the input and output APIs can be selected with 2 arguments in sequence, where the first specifies for the input API and the second for the output API.

For example,

`gradle run --args="offline online"`

will run the application using offline (dummy) input API and live (requests hitting the web) output API.

`gradle run --args="online offline"`

will run the application using  live (requests hitting the web) input API and offline (dummy) output API.

---
# Features

## Version 1.2.2

- Given specified Marvel character name, display information about it (information on character, including image) on GUI in a way that would be useful to a user

- Produce report related to character information entity built from input Marvel API for output Pastebin API

The version where this is fully completed and tested in tagged with version `v1.2.2`.

## Version 1.3

- Cache feature: The input model will automatically cache the JSON response body from a successful character data search request.

- MarvelCache.sqlite is a database with a Character table that has 2 columns "Name" and "Response" which is a key-value pair for caching search strings and their response strings.

- If the user hit search with a text in the text field that corresponds to a cached record, application will ask the user for option to load from cache or hit search again to request data from API.

- Both the online and offline input API models can save and load from cache, valid response saved in a previous session can be loaded in a offline version, and dummy response cached in a offline search will overwrite valid data from a previous live session.

The version where this is fully completed and tested in tagged with version `v1.3`.

- Basic concurrency using Task and Platform.runLater, GUI thread is separated out from database and API calls.

- This is done in the MainPresenter file where onSearch() and onLoadFromCache() uses Tasks to call model mutable methods

The version where this is fully completed and tested in tagged with version `v1.3`.

---

### Javadoc location

Javadoc documentation for the project is located under `docs` folder


### References

Data provided by Marvel. © 2021 MARVEL</a>

JavaDoc description for character package classes references description of Response Class attributes under <a href="https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0">Marvel API Interactive Documentation</a>

Dummy version thumbnail image made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/free-icon/kitty_763704?term=hero&related_id=763704" title="Flaticon">www.flaticon.com</a></div>

