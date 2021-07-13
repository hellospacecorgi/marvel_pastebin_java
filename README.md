# SOFT3202 Examination README

** Note 19th June: Special Considerations approved - sitting replacement exam (<a href="https://edstem.org/courses/5417/discussion/513518?comment=1173705"> 10th June extension to be applied for pre-work state)</a> **

* [How to run application](#how-to-run-application)
* [Feature claim - DISTINCTION](#feature-claim)
* [RED-GREEN-REFACTOR commits](#red-green-refactor-commits)
* [TDD for exam extension features](#exam-set-1)

## Assigned APIs

Input: https://developer.marvel.com/ Output: https://pastebin.com/doc_api

## Assigned exam extension

Maintain a list of up to 3 characters - this list should be empty when the application starts. As the user searches for characters, add them to the list in order of when they were searched. When the list is full and the user searches for a character, ask them for an index from 0 to 2, and swap out the matching index with the new character. Display this list in your GUI. 

When the user requests a report, include the information on the last matched character, but also include the names of any other characters in the list in brackets at the end, eg if index 1 was matched then the brackets would be (name0) (name2), omitting any that are empty.

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
## Feature claim
### PASS requirements feature set

- Given specified Marvel character name, display information about it (information on character, including image) on GUI in a way that would be useful to a user

- Produce report related to character information entity built from input Marvel API for output Pastebin API

The version where this is fully completed and tested in tagged with version `v1.2.2`.

### CREDIT requirements feature set

- Cache feature: The input model will automatically cache the JSON response body from a successful character data search request.

- MarvelCache.sqlite is a database with a Character table that has 2 columns "Name" and "Response" which is a key-value pair for caching search strings and their response strings.

- If the user hit search with a text in the text field that corresponds to a cached record, application will ask the user for option to load from cache or hit search again to request data from API.

- Both the online and offline input API models can save and load from cache, valid response saved in a previous session can be loaded in a offline version, and dummy response cached in a offline search will overwrite valid data from a previous live session.

The version where this is fully completed and tested in tagged with version `v1.3`.

### DISTINCTION requirements feature set

- Basic concurrency using Task and Platform.runLater, GUI thread is separated out from database and API calls.

- This is done in the MainPresenter file where onSearch() and onLoadFromCache() uses Tasks to call model mutable methods

The version where this is fully completed and tested in tagged with version `v1.3`.

---

## RED-GREEN-REFACTOR Commits

Note to marker: 
<p>1. <b>Pass set 1-7</b> lists commits in early development stages, while there was effort to keep track of TDD through commit messages, commit history was unfortunately messy and require looking into files to compare changes (For example, ModelImplTest file might show up as modified but the change was done to a test method that is different to the ones added in the RED stage), to make marking easier I have added <b>commit comments</b> on the sets and individual commits to explain</p>

<p>1.2. Some RED-GREEN processes were submerged within a commit (as a mistake of not correctly tracking RED-GREEN-RED-GREEN-REFACTORs where modification to existing tests were done before refactoring), after consulting Josh, it was adviced to still list them. </p>
  
<p>3. <b>Pass set 6-10 and credit set 1-3</b> are much cleaner commits.</p>

### Commits during development of pass requirement features
* [Pass set 1](#pass-set-1) , [Pass set 2 - RED-GREEN-REFACTOR](#pass-set-2) , [Pass set 3](#pass-set-3), [Pass set 4](#pass-set-4), [Pass set 5](#pass-set-5), [Pass set 6 - Clean RED-GREEN](#pass-set-6), 

### Commits during refactoring of pass requirement version
* [Pass set 7 - Clean RED-GREEN](#pass-set-7), [Pass set 8 - Clean RED-GREEN](#pass-set-8), [Pass set 9 - Clean RED-GREEN-REFACTOR](#pass-set-9), [Pass set 10 - Clean RED-GREEN](#pass-set-10)

### Commits during development for credit requirement features
* [Credit set 1](#credit-set-1), [Credit set 2](#credit-set-2), [Credit set 3](#credit-set-3)

### Commits during Replacement Examination for extension features
* [Exam set 1](#exam-set-1), [Exam set 2](#exam-set-2)

### Pass set 1

Relevant tests : testValidCharacterName() [Mocked InputModel] , testInvalidCharacterName() [Mocked InputModel]

<p>Methods: getCharacterInfo() method in ModelFacade and getInfoByName() method in InputModel</p>

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/34085ddc84013575a8313846ef4f753bdca4a33b">34085ddc84013575a8313846ef4f753bdca4a33b</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/62ae76d3050793e2555b3bb024b43f5bfe3f4f7f">62ae76d3050793e2555b3bb024b43f5bfe3f4f7f</a>
<p>Shows ModelImplTest as modified file but changes were not made to relevant tests.</p>
  
**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f50a188a001db8954dbbf479fb1fc5b0a4fed0f7">f50a188a001db8954dbbf479fb1fc5b0a4fed0f7</a>
<p>Submerged RED-GREEN process concerning change to CharacterInfo</p>

---
### Pass set 2

RED-GREEN-REFACTOR

Relevant tests : testInputModelGetInfoByNameInvalid() [Mocked MarvelApiHandler], testInputModelGetInfoByNameValid() [Mocked MarvelApiHandler], testInptModelGetInfoNullList() [Mocked MarvelApiHandler]

<p>Methods: getCharacterInfo() in ModelFacade, getInfoByName() in InputModel and getCharacterInfoByName() in MarvelApiHandler</p>

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/5eb7094b1fc518177fb8599680991137255ff78e">5eb7094b1fc518177fb8599680991137255ff78e</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f6a9831731296ced3fe5456f86593b57e2ec0ef3">f6a9831731296ced3fe5456f86593b57e2ec0ef3</a>

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/51a4911b7c30bc9abda1a68a2a6916472e788b07">51a4911b7c30bc9abda1a68a2a6916472e788b07</a>

Modification to how MarvelApiHandler's access to public and private keys were done,
from setApiKeys to setting keys by Constructor arguments.

MarvelApiHandler constructor is refactored, uses injected strings to set keys rather than hard coded strings.

This leads to modification in method call inside ModelImpl to setApiHandler for input model, ModelImpl's line 29.

Tests for InputModel still passes after this refactoring (interaction between OnlineMarvelModel and MarvelApiHandler still behaves as before).

---

### Pass set 3

Relevant tests: testGetImageViaModelFacade() [Mock InputModel] testInputModelGetThumbnailImage() [Mock InputModel]

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/1b6354243320e8cfd76c6a9a8c367c3875f0761e">1b6354243320e8cfd76c6a9a8c367c3875f0761e</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/09ce9e19892a408ac674930df5a05861136ba28d">09ce9e19892a408ac674930df5a05861136ba28d</a>

-- GREEN for testGetImageViaModelFacade()

-- RED-GREEN for testInputModelGetThumbnailImage() submerged

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/446a133e9799091a8b42ea55c935c134396c54fa">446a133e9799091a8b42ea55c935c134396c54fa</a>

---

### Pass set 4

(relatively clean RED-GREEN), please ignore commit messages and see commit comments for details.

Relevant tests: testSendReportFacade() [Mocked OutputModel], testGetReportUrlFacade() [Mocked OutputModel]

Methods: sendReport() in ModelFacade and sendReport() in OutputModel

Methods: getReportUrl() in ModelFacade and getReportUrl() in OutputModel

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/203c4799ebbcd2b9491625806ae2baee2429de37">203c4799ebbcd2b9491625806ae2baee2429de37</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/100599aa893b87d88373eb85e1f582cd54a83b2c">100599aa893b87d88373eb85e1f582cd54a83b2c</a>

See commit comments.

**REFACTOR** (Relevant tests unchanged and passed - however test suite fails because commit) Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/face90c6efe6c72344c380a214ad5130910626d6">face90c6efe6c72344c380a214ad5130910626d6</a>

---

### Pass set 5
Relevant tests: testOutputModelSendReport(), testOutputModelGetReportUrl()

Methods: sendReport() in ModelFacade and sendReport() in OutputModel

Methods: getReportUrl() in ModelFacade, getReportUrl() in OutputModel and sendReport() in PastebinApiHandler

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/face90c6efe6c72344c380a214ad5130910626d6">face90c6efe6c72344c380a214ad5130910626d6</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/77e5c8178db8e22bd51fc5640d1e0f3e5bf338c3">77e5c8178db8e22bd51fc5640d1e0f3e5bf338c3</a>

See commit comments - submerged RED-GREEN.

---

### Pass set 6

Relevant test: testInputModelGetThumbnailFullPath() [Mocked InputModel]

Methods: ModelFacade getImagePathByInfo() and getThumbnailFullPath() in InputModel

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/0524160fd5d7cdc3eac075d7d88981b9e9e1c25c">0524160fd5d7cdc3eac075d7d88981b9e9e1c25c</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/bb1de09a047c9085c1ba9c4d490e11b0be09978f">bb1de09a047c9085c1ba9c4d490e11b0be09978f</a>

---

### Pass set 7

Relavant tests:

testRefactoredObserverUpdateGetCharacterInfoComplete()

testRefactoredObserverUpdateSendReportComplete()

Methods: 

ModelFacade's addObserver(), getCharacterInfo() and ModelObserver's updateCharacterInfo()

ModelFacade's addObserver(), sendReport() and ModelObserver's updateReportUrl()


**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/46b740c05e072f4559b2bc60d3dc08bfbce35eb9">46b740c05e072f4559b2bc60d3dc08bfbce35eb9</a>

(Test file shows one line modification to a previous test set)
**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/bc6c545fed706da786ccfde5b8c647d502a97e6e">bc6c545fed706da786ccfde5b8c647d502a97e6e</a>

---

### Pass set 8

Tests: (Mocked MarvelApiHandler and ResponseHandler to verify OnlineMarvelModel method calls)

testGetCharacterInfoErrorResponse()

testNullApiHandlerSet()

testNullResponseHandlerSet()

testInputModelGetInfoNullList()

testInputModelGetInfoByNameValid()

Feature done: 

<p>Added setResponseHandler() to InputModel, have InputModel coordinate passing response body string to ResponseHandler on getCharacterInfo(), return null on null handlers set.</p>

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/18a018a17ac8654ebd837cbcfb69605fa806ddd3">18a018a17ac8654ebd837cbcfb69605fa806ddd3</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/1b28bcac427811dddc9209860b92bd6c15e44f40">1b28bcac427811dddc9209860b92bd6c15e44f40</a>

### Pass set 9

***CLEAN RED-GREEN-REFACTOR***

Tests: testOfflineMarvelModelGetCharacter()

Feature: 

<p>Using dummy data loaded from JSON file to create more convincing dummy data instead of in method construction.</p>

<p>Method : OfflineMarvelModel getInfoByName()</p>

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/66b6b19362b41ff69dfe7f2de8cce623d6ac3c3f">66b6b19362b41ff69dfe7f2de8cce623d6ac3c3f</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/ccfdd36a5bcb4c0ebe3e32ffd2cf8ce2b8fc23a6">ccfdd36a5bcb4c0ebe3e32ffd2cf8ce2b8fc23a6</a>

Refactoring:

<p>Uses new method setResponseHandler in OutputModel, allow both online and offline model to use ResponseHandler as a JSON - CharacterInfo parser.</p>

<p>OfflineMarvelModel's getInfoByName() now delegates to the handler class to create CharacterInfo object.</p>

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/d43e63ca82f456f2842b2c91c0fb89381af0cb6f">d43e63ca82f456f2842b2c91c0fb89381af0cb6f</a>

### Pass set 10

Tests: (Mocked MarvelApiHandler and ResponseHandler to verify OnlineMarvelModel method calls)

testReportServiceGenerateReportNullHandler(), testReportServiceGenerateReportNullService(), testReportServiceGenerateReportValid()

testOutputModelSendReport() - modified existing test 10c90071dfdfb04669d25958fafadbc92cddb921

Feature done: Restructured how the report string is created - added new ReportService class and setReportService() in OutputModel interface.

Tests done using OnlinePastebinModel with mocked ReportService and mocked PastebinApiHandler to test generateReport() and sendReport() was called.

<p>Added setResponseHandler() to InputModel, have InputModel coordinate passing response body string to ResponseHandler on getCharacterInfo(), return null on null handlers set.</p>

**RED** Commits URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/ff8fa0cb6ec6f73fa4bc051bb7026733dc31e989">ff8fa0cb6ec6f73fa4bc051bb7026733dc31e989</a>
<a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/10c90071dfdfb04669d25958fafadbc92cddb921">10c90071dfdfb04669d25958fafadbc92cddb921</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/258d3d06826cdb97c2ac5b2f4ef109852b5fd624">258d3d06826cdb97c2ac5b2f4ef109852b5fd624</a>

---

### Credit set 1

Tests: testIsInfoInCacheExceptions(), testIsInfoInCacheNotFound(), testIsInfoInCacheFound(), testIsInfoInCacheExceptions()

**RED** Commits URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/8feccabae0ccf06b732ec6d9a624988a2e5a89a3">8feccabae0ccf06b732ec6d9a624988a2e5a89a3</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/39e906accc903c17d705e88cb580d5aa54d5d781">39e906accc903c17d705e88cb580d5aa54d5d781</a>

### Credit set 2

***CLEAN RED-GREEN-REFACTOR***

Tests: testLoadInfoFromCacheNoCache(), testLoadInfoFromCacheValid(), testLoadInfoFromCacheException() [Mocked InputModel]

Method: loadInfoFromCache() in ModelImpl interaction with isInfoInCache() and loadInfoByNameFromCache() in InputModel

**RED** Commits URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/1fabc210c92c81b3a63da7489fab4f388b5115dc">1fabc210c92c81b3a63da7489fab4f388b5115dc</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/378fae3011a2a51a276121efe7b3133f7ac08607">378fae3011a2a51a276121efe7b3133f7ac08607</a>

Refactor: Changing implementation of loadInfoFromCache() (return value changed) and using notifyObserversGetInfoComplete() to notify ModelObserver (presenter)

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/86acae014038ecfbbbed9648f7bcc47777ccf163">86acae014038ecfbbbed9648f7bcc47777ccf163</a>

### Credit set 3

Added new method setCacheHandler() in InputModel interface for refactoring how CacheHandler object is created (from inside class to injection with method). 

Tests: testInputModelGetInfoByNameValid(), testNullApiHandlerSet(), testNullCacheHandlerSet(), testNullResponseHandlerSet()[Mocked handler classes]

Methods: getCharacterInfoByName() in ModelImpl and setCacheHandler() in InputModel

**RED** Commits URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/4e61a525d35160dff01a444649bb7b2fc3017e78">4e61a525d35160dff01a444649bb7b2fc3017e78</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/e341c8fbe0d413963d85c903dea2ba643143dbe1">e341c8fbe0d413963d85c903dea2ba643143dbe1</a>

---

### Exam set 1

For index integer selection and searched list tracking feature,

Tests: testNotifySearchedListUpdate(), testSearchedListUpdateOne(), testSearchedListUpdateMoreThanOne(), testSearchedListReplace(), testSetIndexSelectedExceptions() in ModelImplTest

Methods: setIndexSelected(int index); notifyObserversSearchedListUpdated(); List<String> getSearchedList(); in ModelImpl

**RED** Commits URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/1921be24c698284db8aefdeeae2094ff55bde3a6">1921be24c698284db8aefdeeae2094ff55bde3a6</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/d40d0ba8bc71398eda660691e8cec003db3485bf">d40d0ba8bc71398eda660691e8cec003db3485bf</a>


### Exam set 2

For adding unmatched names at end of report feature,

Tests: Changed testSendReportFacade, added testSendReportWithNamesList() in ModelImplTest

Changed testOutputModelSendReport(), added testReportServiceGenerateReportArgsOneInList(), TwoInList(), ThreeInList(), ReplacedInList(), NullListExceptions() in OnlinePasteBinModelTest

Methods: Changed sendReport() in OutputModel and generateReport() in ReportService signatures to take in list of unmatched characters names

**RED** Commits URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/2109741168e136bee4ff3d54f9a0007b1818da398">2109741168e136bee4ff3d54f9a0007b1818da39</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/46987b6ff221a8af67f6683e494f2c85614f1afb">46987b6ff221a8af67f6683e494f2c85614f1afb</a>

---

### Javadoc location

Javadoc documentation for the project is located under `docs` folder
  
### Citations for code in previous unit

### References

Data provided by Marvel. © 2021 MARVEL</a>

JavaDoc description for character package classes references description of Response Class attributes under <a href="https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0">Marvel API Interactive Documentation</a>

Dummy version thumbnail image made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/free-icon/kitty_763704?term=hero&related_id=763704" title="Flaticon">www.flaticon.com</a></div>

