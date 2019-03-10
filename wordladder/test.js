var Wordladder = require("./wordladder.js");
var mocha = require('mocha')
var suite = mocha.suite;
var setup = mocha.setup;
var test = mocha.test;
var assert = require('chai').assert;
var webdriver = require('selenium-webdriver');
var By = webdriver.By;

var expect = require('chai').expect;

// TDD style Unit Test
suite('Wordladder', function() {
    setup(function() {
       let wd = new Wordladder("./test.json");
    });
    //hooks function, to init the Wordladder
    suite( 'Before all tests.', function () {
        wd = new Wordladder("./test.json");
    });

    // Search function test for two situations
    suite('#Search(word)', function() {
        test('should return true when it has the word.', function () {
            let check = wd.Search("data")
            assert.equal(check, true);
            test("should return false when it doesn't the word.", function () {
                let check = wd.Search("mu")
                assert.equal(check, false);
            });
        });
    });

    //  GetInput function, to get the input we set
    suite("#GetInput()",function() {
        test("getInput return the input", function() {
            wd.Input = "data";// setTheInput
            let input = wd.GetInput();
            assert.equal(input, "data", "==");
        });
    });

    //  GetOutput function, to get the output we set
    suite("#GetOutput()",function() {
        test("get the output", function() {
            wd.Output = "data";
            assert.equal(wd.GetOutput(), "data");
        });
    });

    // SetOutput function, to set the output after the user input
    suite("#SetOutput()",function() {
        test("getOutput return the input", function() {
            wd.Output = "data";// wrong output set
            wd.SetOutput("buff");// right output set
            assert.equal(wd.Output, "buff");
        });
    });

    // SetInput function, to set the input after the user input
    suite("#SetInput()",function() {
        test("getInput return the input", function() {
            wd.Input = "data";// raw set the wrong input
            wd.SetInput("buff");// set the right input
            assert.equal(wd.Input, "buff");
        });
    });

    // BFS function, search the dictionary for width first
    suite("#BFS()",function() {
        test("should return ['data', 'dota', 'cota', 'cote', 'code']", function() {
            wd.SetInput("data");
            wd.SetOutput("code");
            assert.includeOrderedMembers(wd.BFS(), [ 'code', 'cote', 'cota', 'dota', 'data' ]);
        });
        test("should return [''] if we don't have the path", function() {
            // set two wrong words
            wd.SetInput("fu");
            wd.SetOutput("mu");
            assert.includeOrderedMembers(wd.BFS(), ['']);
        });
    });
});


// E2E test, use web-driver
describe('UI', function() {
    it("valid test", function() {
        driver = new webdriver.Builder().forBrowser('chrome').build();//start chrome
        driver.get("http://127.0.0.1:8081").then(function() {// to simplify, we choose to test the page at server not in the development environment
            driver.findElement(By.id('input')).then(function(input){// get the input
                input.sendKeys("data").then(function(){// set the Input value to data
                    driver.findElement(By.id('output')).then(function(output){// get the output
                        output.click().then(function(){// click output to trigger word detection
                            driver.findElement(By.id("input-valid")).then(function(inputValid){
                                inputValid.getAttribute('class').then(function(ClassName){
                                    expect(ClassName).to.equal("valid-feedback");
                                    inputValid.getText().then(function(text){
                                        expect(text).to.equal("Looks good!");
                                        driver.quit();
                                    })
                                })
                            })
                        })
                    });
                })
            });
        })
    });
});


// BDD style Unit Test
describe('Wordladder BBD', function() {
	//hooks function, to init the Wordladder
    before( 'Before all tests.', function () {
        wd = new Wordladder("./test.json");
        wd.SetInput("data");
        wd.SetOutput("code");
    });

	
	// search the word user input
    describe('search whether the word is in the dictionary', function() {
        it('should return true when it has the word.(data is in)', function() {
            let check = wd.Search("data")
            expect(check).to.equal(true);
        });
        it("should return false when it doesn't the word.(mu is not in)", function() {
            let check = wd.Search("mu")
            expect(check).to.equal(false);
        });
    });
	
	// the core behaviour, to search the shortest path
	describe("the shortest path search",function() {
		it("should return the shortest path to appear in sequence ", function() {
            expect(wd.BFS()).to.include.deep.ordered.members([ 'code', 'cote', 'cota', 'dota', 'data' ]);
        });
	});
});