# WordLadder 

## Display

This webpage is online, and you can directly access **bilibili.cqdulux.cn** to see the effect (it is not recommended to start locally). 

## Project Architecture 

Briefly introduce the important documents in the project

- app.js

  back end entry file, using Express and WordLadder class to achieve the back end 

- test.js

  test file, test API and page

- wordladder.js

  the implement of class WordLadder

- dictionary.json

  the raw dictionary data

- test.json

  the dictionary data for test

- private.css

  Custom CSS storage here

- wordshow.js

  the important component to show the ladder

- index.html

  The home page, which contains ajax and JS for page dynamic adjustment 

- mochawesome-report

  store the beautified test report

- coverage

  store the beautified coverage report

## Run Test

- Test correctness, run code `npm test`, then you can check the report at **mochawesome-report**
- Test coverage，run code`npm coverage`, then you can check the report at **coverage**

PS : E2E test may wrong because the production environment need special settings(web-driver)

## Feature

1. Express is used as the back end, jQuery library is used in the front end, and the back end is separated
2. It has a validate module at front end
3. Ajax make the page partial refresh