# README for SOFT3202 Exam Pre Work

### Assigned APIs

Input: https://developer.marvel.com/

- **Entity**: Given a specified Marvel character name, information on that character including image. 
- **Report data**: data on a given character
- **Database caching**: info on characters (Image paths should be cached, the images themselves do not need to be).

Output: https://pastebin.com/doc_api
- Send the report as a saved pastebin and output the url.

---

### How to run application

#### Configuration file for API keys

Marker's own API keys for both input and output APIs should be placed in the configuration JSON file `<file-name>` in order for the live versions to run.

#### Gradle command

Option to use live or dummpy versions of both the input and output APIs can be selected with 2 arguments in sequence, where the first specifies for the input API and the second for the output API.

For example,

`gradle run --args="offline online"`

will run the application using offline (dummy) input API and live (requests hitting the web) output API.

`gradle run --args="online offline"`

will run the application using  live (requests hitting the web) input API and offline (dummy) output API.

---

### PASS requirements feature set

- Given entity derived from input API, display information about it on GUI in a way that would be useful to a user
- Produce report related to entity built from input API for ouput API

The version where this is fully completed and tested in tagged with version `<version-no>`.

### RED-GREEN-REFACTOR Commits

**RED** 

<a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/34085ddc84013575a8313846ef4f753bdca4a33b">34085ddc84013575a8313846ef4f753bdca4a33b</a>

Added tests with mocks for ModelImpl, which implements ModelFacade, mocked InputModel and OutputModel, tests for getCharacterInfo() functionality, methods decalred in interface, actual functionality not yet implemented.

**GREEN** 

<a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/62ae76d3050793e2555b3bb024b43f5bfe3f4f7f">62ae76d3050793e2555b3bb024b43f5bfe3f4f7f</a>

ModelImpl's getCharacterInfo() implemented, interactions with mocks for valid and invalid character names passed tests.

**REFACTOR** 

<a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f50a188a001db8954dbbf479fb1fc5b0a4fed0f7">f50a188a001db8954dbbf479fb1fc5b0a4fed0f7</a>

Oranised model package structure, added null and empty string checks in ModelImpl getCharacterInfo(), added setApiKey() to InputModel, implemented OfflineMarvelModel getInfoByName() and getThumbnalImage(). Tests still Green.

---

### Javadoc location
  
### Citations for code in previous unit


Dummy version thumbnail image made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

