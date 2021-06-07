## RED-GREEN-REFACTOR Commits (IGNORE - INCOMPLETE AND NOT REQUIRED)

NOTE: Refer to README.md for complete listing of commits, this is not a fully updated logging of the TDD process.

* [Pass set 1](#pass-set-1) , [Pass set 2](#pass-set-2) , [Pass set 3](#pass-set-3)
* [Credit set](#credit-set)

### Pass set 1
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
### Pass set 2
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
### Pass set 3
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

