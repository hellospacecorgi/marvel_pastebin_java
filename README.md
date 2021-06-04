# README for SOFT3202 Exam Pre Work

## Assigned APIs

Input: https://developer.marvel.com/

- **Entity**: Given a specified Marvel character name, information on that character including image. 
- **Report data**: data on a given character
- **Database caching**: info on characters (Image paths should be cached, the images themselves do not need to be).

Output: https://pastebin.com/doc_api
- Send the report as a saved pastebin and output the url.


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

### PASS requirements feature set

- Given entity derived from input API, display information about it on GUI in a way that would be useful to a user
- Produce report related to entity built from input API for ouput API

The version where this is fully completed and tested in tagged with version `<version-no>`.

### RED-GREEN-REFACTOR Commits

**RED** <URL for commit where tests for specific feature is finished, can be compiled and run but do not pass>

**GREEN** <URL for commit where features are implemented and tests passed>

**REFACTOR** <URL for commit where some of that implementation is refactored but tests still passing>


### Javadoc location
  
### Citations for code in previous unit

