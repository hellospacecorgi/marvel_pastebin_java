# SOFT3202 Exam Pre-work README
** Simple Extension of 2 Days applied**
* [How to run application](#how-to-run-application)
* [Feature claim - PASS](#feature-claim)
* [RED-GREEN-REFACTOR commits](#red-green-refactor-commits)

## Assigned APIs

Input: https://developer.marvel.com/ Output: https://pastebin.com/doc_api

---

## How to run application

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
## Feature claim
### PASS requirements feature set

- Given specified Marvel character name, display information about it (information on character, including image) on GUI in a way that would be useful to a user

- Produce report related to character information entity built from input Marvel API for output Pastebin API

The version where this is fully completed and tested in tagged with version `<version-no>`.

---

## RED-GREEN-REFACTOR Commits

Note to marker: 
<p>1. While there was effort to keep track of TDD through commit messages, they do not reflect all the changes for each commit, 
please refer to <b>commit comments</b> where some commits might be used for both RED and REFACTOR 
(where new tests were added for feature to be implemented (RED for new commit set), and previous features were modified for better code structure (REFACTOR for previous commit set) )</p>

<p>2. While Git commit might show the ModelImplTest file as modified in a GREEN commit, changes might have been made to a test method that is different to what the commit set is referring to (GREEN for tests for feature A but tests for feature B was modified), I have added <b>commit comments<b> to help illustrate which tests and features are relevant to the commit set</p>

<p>3. Some RED-GREEN processes were submerged within a commit (as a mistake of not correctly tracking RED-GREEN-RED-GREEN-REFACTORs where modification to existing tests were done before refactoring), after consulting Josh, it was adviced to still list them, <b>Pass set 2 and 4<b> are clean RED-GREEN-REFACTOR sets and <b>Pass set 1, 6, 7<b> are clean RED-GREEN sets, and much cleaner commits were listed for the CREDIT feature implementations.</p>

<p>The issue was identified after implementing the PASS requirements, and more careful version control was adopted in the Credit implementation (hence credit TDD commits are more clean)</p>

* [Pass set 1 - Clean RED-GREEN](#pass-set-1) , [Pass set 2 - CLEAN](#pass-set-2) , [Pass set 3](#pass-set-3), [Pass set 4 - CLEAN](#pass-set-4), [Pass set 5](#pass-set-5), [Pass set 6](#pass-set-6), [Pass set 7 - Clean RED-GREEN](#pass-set-7)
* [Credit set](#credit-set)

### Pass set 1

Relevant tests : 

testValidCharacterName() [Mocked InputModel] 

testInvalidCharacterName() [Mocked InputModel]

<p>Methods: getCharacterInfo() method in ModelFacade and getInfoByName() method in InputModel</p>

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/34085ddc84013575a8313846ef4f753bdca4a33b">34085ddc84013575a8313846ef4f753bdca4a33b</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/62ae76d3050793e2555b3bb024b43f5bfe3f4f7f">62ae76d3050793e2555b3bb024b43f5bfe3f4f7f</a>
<p>Shows ModelImplTest as modified file but changes were not made to relevant tests.</p>
  
**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f50a188a001db8954dbbf479fb1fc5b0a4fed0f7">f50a188a001db8954dbbf479fb1fc5b0a4fed0f7</a>
<p>Submerged RED-GREEN process concerning change to CharacterInfo</p>

---
### Pass set 2
### CLEAN RED-GREEN-REFACTOR
Relevant tests : 

testInputModelGetInfoByNameInvalid() [Mocked MarvelApiHandler]

testInputModelGetInfoByNameValid() [Mocked MarvelApiHandler]

testInptModelGetInfoNullList() [Mocked MarvelApiHandler]

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

Relevant tests:

testGetImageViaModelFacade() [Mock InputModel]

testInputModelGetThumbnailImage() [Mock InputModel]

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/1b6354243320e8cfd76c6a9a8c367c3875f0761e">1b6354243320e8cfd76c6a9a8c367c3875f0761e</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/09ce9e19892a408ac674930df5a05861136ba28d">09ce9e19892a408ac674930df5a05861136ba28d</a>

GREEN for testGetImageViaModelFacade()

RED-GREEN for testInputModelGetThumbnailImage() submerged

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/446a133e9799091a8b42ea55c935c134396c54fa">446a133e9799091a8b42ea55c935c134396c54fa</a>

---

### Pass set 4

CLEAN RED-GREEN-FACTOR, please ignore commit messages and see commit comments for details.

Relevant tests:

testSendReportFacade() [Mocked OutputModel]

testGetReportUrlFacade() [Mocked OutputModel]

Methods: sendReport() in ModelFacade and sendReport() in OutputModel

Methods: getReportUrl() in ModelFacade and getReportUrl() in OutputModel

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/203c4799ebbcd2b9491625806ae2baee2429de37">203c4799ebbcd2b9491625806ae2baee2429de37</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/100599aa893b87d88373eb85e1f582cd54a83b2c">100599aa893b87d88373eb85e1f582cd54a83b2c</a>

See commit comments.

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/face90c6efe6c72344c380a214ad5130910626d6">face90c6efe6c72344c380a214ad5130910626d6</a>

---

### Pass set 5
Relevant tests:

testOutputModelSendReport()

testOutputModelGetReportUrl()

Methods: sendReport() in ModelFacade and sendReport() in OutputModel

Methods: getReportUrl() in ModelFacade, getReportUrl() in OutputModel and sendReport() in PastebinApiHandler

**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/face90c6efe6c72344c380a214ad5130910626d6">face90c6efe6c72344c380a214ad5130910626d6</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/77e5c8178db8e22bd51fc5640d1e0f3e5bf338c3">77e5c8178db8e22bd51fc5640d1e0f3e5bf338c3</a>

See commit comments - submerged RED-GREEN.

---

### Pass set 6

Relevant test: testInputModelGetThumbnailFullPath() [Mocked InputModel]

Methods: ModelFacade getImagePathByInfo() and getThumbnailFullPath() in InputModel

**REFACTOR RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/0524160fd5d7cdc3eac075d7d88981b9e9e1c25c">0524160fd5d7cdc3eac075d7d88981b9e9e1c25c</a>

**REFACTOR GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/bb1de09a047c9085c1ba9c4d490e11b0be09978f">bb1de09a047c9085c1ba9c4d490e11b0be09978f</a>

---

### Pass set 7

Relavant tests:

testRefactoredObserverUpdateGetCharacterInfoComplete()

testRefactoredObserverUpdateSendReportComplete()

Methods: 

ModelFacade's addObserver(), getCharacterInfo() and ModelObserver's updateCharacterInfo()

ModelFacade's addObserver(), sendReport() and ModelObserver's updateReportUrl()


**REFACTOR RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/46b740c05e072f4559b2bc60d3dc08bfbce35eb9">46b740c05e072f4559b2bc60d3dc08bfbce35eb9</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/bc6c545fed706da786ccfde5b8c647d502a97e6e">bc6c545fed706da786ccfde5b8c647d502a97e6e</a>

---

<p>The commits listed above are ones closely related to the interaction of ModelImpl with InputModel (mocked) and OutputModel (mocked),
the interaction of OnlineMarvelModel and MarvelApiHandler (mocked), and the interaction of OutputModel and PastebinApiHandler (mocked).</p>

The test suite also covered some unit testing of concrete model classes to assert for expected behaviours.

---

### Javadoc location

Javadoc documentation for the project is located under `docs` folder
  
### Citations for code in previous unit


### References

Data provided by Marvel. © 2021 MARVEL</a>

JavaDoc description for character package classes references description of Response Class attributes under <a href="https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0">Marvel API Interactive Documentation</a>

Dummy version thumbnail image made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/free-icon/kitty_763704?term=hero&related_id=763704" title="Flaticon">www.flaticon.com</a></div>

