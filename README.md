# SOFT3202 Exam Pre-work README

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

* [Pass set 1](#pass-set-1) , [Pass set 2](#pass-set-2) , [Pass set 3](#pass-set-3)
* [Credit set](#credit-set)
* <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/blob/master/RED-GREEN-REFACTOR.md">TDD Log [ RED-GREEN-REFACTOR.md ]</a>


### Pass set 1
**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/34085ddc84013575a8313846ef4f753bdca4a33b">34085ddc84013575a8313846ef4f753bdca4a33b</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/62ae76d3050793e2555b3bb024b43f5bfe3f4f7f">62ae76d3050793e2555b3bb024b43f5bfe3f4f7f</a>

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f50a188a001db8954dbbf479fb1fc5b0a4fed0f7">f50a188a001db8954dbbf479fb1fc5b0a4fed0f7</a>

---
### Pass set 2
**RED** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/5eb7094b1fc518177fb8599680991137255ff78e">5eb7094b1fc518177fb8599680991137255ff78e</a>

**GREEN** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/f6a9831731296ced3fe5456f86593b57e2ec0ef3">f6a9831731296ced3fe5456f86593b57e2ec0ef3</a>

**REFACTOR** Commit URL: <a href="https://github.sydney.edu.au/hcha8985/SCD2_2021_Exam/commit/51a4911b7c30bc9abda1a68a2a6916472e788b07">51a4911b7c30bc9abda1a68a2a6916472e788b07</a>

---

### Pass set 3
**RED** Commit URL: <a href=""></a>

**GREEN** Commit URL: <a href=""></a> 

**REFACTOR** Commit URL: <a href=""></a>  

---

The RED-GREEN-REFACTOR commits listed above are the major ones closely related to the ModelFacade and ModelImpl.

The test suite also covered some unit testing of concrete model classes to assert for expected behaviours.

---

### Javadoc location
  
### Citations for code in previous unit


### References

<a href=\"http://marvel.com\">Data provided by Marvel. © 2021 MARVEL</a>

JavaDoc description for character package classes references description of Response Class attributes under <a href="https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0">Marvel API Interactive Documentation</a>

Dummy version thumbnail image made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/free-icon/kitty_763704?term=hero&related_id=763704" title="Flaticon">www.flaticon.com</a></div>

