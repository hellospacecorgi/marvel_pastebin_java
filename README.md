# README for SOFT3202 Exam Pre Work

### How to run application

#### Configuration file for API keys

Marker's own API keys for both input and output APIs should be placed in the configuration JSON file `KeyConfig.json` under `src/main/resources/marvel` in order for the live versions to run.

NOTE: For Marvel API, **both public and private keys** need to be provided.

#### Gradle command

Option to use live or dummpy versions of both the input and output APIs can be selected with 2 arguments in sequence, where the first specifies for the input API and the second for the output API.

For example,

`gradle run --args="offline online"`

will run the application using offline (dummy) input API and live (requests hitting the web) output API.

`gradle run --args="online offline"`

will run the application using  live (requests hitting the web) input API and offline (dummy) output API.

---
### Assigned APIs

Input: https://developer.marvel.com/

- **Entity**: Given a specified Marvel character name, information on that character including image. 
- **Report data**: data on a given character
- **Database caching**: info on characters (Image paths should be cached, the images themselves do not need to be).

Output: https://pastebin.com/doc_api
- Send the report as a saved pastebin and output the url.

---

### PASS requirements feature set

- Given entity derived from input API, display information about it on GUI in a way that would be useful to a user
- Produce report related to entity built from input API for ouput API

The version where this is fully completed and tested in tagged with version `<version-no>`.

### RED-GREEN-REFACTOR Commits

**RED** 

URL to commit: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/34085ddc84013575a8313846ef4f753bdca4a33b">34085ddc84013575a8313846ef4f753bdca4a33b</a>

Tests: 

- testInvalidCharacterName(), testValidCharacterName(), testGetSubModels()

- Testing with a instance of ModelImpl, which implements ModelFacade; mocking InputModel and OutputModel

- Testing for interaction that ModelImpl's getCharacterInfo() trigger's InputModel's getInfoByName()

Features: 

-  ModelFacade's getCharacterInfo() , getInputSubModel(), getOutputSubModel() functionality, not fully implemented by ModelImpl.

-  Facade feature set to enable retrieving character information based on searched string.

Test status: Methods declared in ModelFacade interface, actual functionality in not yet implemented, tests do not pass.

**GREEN** 

URL to commit:<a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/62ae76d3050793e2555b3bb024b43f5bfe3f4f7f">62ae76d3050793e2555b3bb024b43f5bfe3f4f7f</a>

Features: ModelImpl's getCharacterInfo(), getInputSubModel(), getOutputSubModel() implemented

Test status: Interactions with mocks for valid and invalid character names passed tests.

**REFACTOR** 

URL to commit: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f50a188a001db8954dbbf479fb1fc5b0a4fed0f7">f50a188a001db8954dbbf479fb1fc5b0a4fed0f7</a>

Refactor: Oranised model package structure, added null and empty string checks in ModelImpl getCharacterInfo(), added setApiKey() to InputModel which ModelImpl calls upon initialisation, implemented OfflineMarvelModel getInfoByName() and getThumbnalImage().

Test status: Previous tests still Green.

---
**RED** 

URL to commit: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/5eb7094b1fc518177fb8599680991137255ff78e">51a4911b7c30bc9abda1a68a2a6916472e788b07</a>

Tests: testInputModelGetInfoByNameValid(), testInputModelGetInfoByNameInvalid(), testInputModelGetInfoNullList()

Using instance of OnlineMarvelModel with a mock for MarvelApiHandler, test for interactions between online model and the API handler, 
testing setApiHandler() correctly ties a handler to the model, and model's getInfoByName() triggers handler's getCharacterInfoByName()

Features:

- Methods setApiHandler() and getInfoByName() are declared in InputModel interface but not fully implemented by OnlineMarvelModel

Test status: Functionality and coordination between model classes not fully implemented (lacking smooth communication between MarvelApiHandler and ResponseHandler; request authorisation / API keys not correctly configured), tests do not pass.

**GREEN** 

URL to commit:<a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f6a9831731296ced3fe5456f86593b57e2ec0ef3">f6a9831731296ced3fe5456f86593b57e2ec0ef3</a>

Implemented Features: 

- Sending API GET request to Marvel API with authentication parameters generated from hash.

- Parsing of JSON response to CharacterInfo object in ResponseHandler.

- Implemented parsing of CharacterInfo returned from MarvelApiHandler's getCharacterInfoByName()

Test status: Interactions with mocks for valid and invalid character names passed tests.

**REFACTOR** 

URL to commit: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/51a4911b7c30bc9abda1a68a2a6916472e788b07">51a4911b7c30bc9abda1a68a2a6916472e788b07</a>

Refactor (InputModel methods): 

- Can successfully send search request to online input API, retrieve response and parse as CharacterInfo object to Presenter

- Refactored MarvelApiHandler constructor, injecting public and private keys instead of using setKeys() method (as keys are final and won't be changed in same session).

- added getCurrentCharacter() method in ModelFacade for Presenter to retrieve CharacterInfo in memory for switching TableView for different lists.

(GUI features):

- Presenter uses getCurrentCharacter() and getCharacterInfo() to display summary (in bottom display text area) and lists of resources (Events, Comics, Series, Stories, URLs etc)

- Can TableView lists by GUI buttons.

Test status: Previous tests still Green.

---

**RED** 


URL to commit: <a href=""></a>

Tests: Testing successful Image object creation from request response and handler proccess. testInputModelGetThumbnailImage()

Features: getThumbnailImage()

Test status: Previous tests still Green.

**GREEN** 

URL to commit: <a href=""></a>

Features:

Test status: Previous tests still Green.

**REFACTOR** 

URL to commit: <a href=""></a>

Refactor: 

Test status: Previous tests still Green.

---

The RED-GREEN-REFACTOR commits listed above are the major ones closely related to the ModelFacade and ModelImpl.

The test suite also covered some unit testing of concrete model classes to assert for expected behaviours.

---

### Javadoc location
  
### Citations for code in previous unit


Dummy version thumbnail image made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

