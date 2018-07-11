var Wordladder = require("./Wordladder");

var mocha = require('mocha')
var suite = mocha.suite;
var setup = mocha.setup;
var test = mocha.test;
var assert = require('chai').assert;

var expect = require('chai').expect;

suite('Wordladder', function() {
    setup(function() {
       let wd = new Wordladder();
    });
    //hooks function, to init the Wordladder
    suite( 'Before all tests.', function () {
        wd = new Wordladder();
    });


    suite('#Search(word)', function() {
        test('should return true when it has the word.', function() {
            let check = wd.Search("data")
            assert.equal(check, true);
        test("should return false when it doesn't the word.", function() {
            let check = wd.Search("mu")
            assert.equal(check, false);
        });
    });

    suite("#GetInput()",function() {
        test("getInput return the input", function() {
            wd.Input = "data";
            let input = wd.GetInput();
            assert.equal(input, "data", "==");
        });
    });


    suite("#GetOutput()",function() {
        test("get the output", function() {
            wd.Output = "data";
            wd.SetOutput("buff");
            assert.equal(wd.Output, "buff");
        });
    });

    suite("#SetOutput()",function() {
        test("getOutput return the input", function() {
            wd.Output = "data";
            wd.SetOutput("buff");
            assert.equal(wd.Output, "buff");
        });
    });

    suite("#SetInput()",function() {
        test("getInput return the input", function() {
            wd.Input = "data";
            wd.SetInput("buff");
            assert.equal(wd.Input, "buff");
        });
    });

    suite("#BFS()",function() {
        test("should return ['data', 'dota', 'cota', 'cote', 'code']", function() {
            wd.SetInput("data");
            wd.SetOutput("code");
            console.log("here", wd.BFS());
            assert.includeOrderedMembers(wd.BFS(), [ 'code', 'cote', 'cota', 'dota', 'data' ]);
        });
    });


    });
});

describe('Wordladder BBD', function() {
	
	//hooks function, to init the Wordladder
    before( 'Before all tests.', function () {
        wd = new Wordladder();
        wd.SetInput("data");
        wd.SetOutput("code");
    } );
	
	
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
	
	
	describe("the shortest path search",function() {
		it("should return the shortest path to appear in sequence ", function() {
            expect(wd.BFS()).to.include.deep.ordered.members([ 'code', 'cote', 'cota', 'dota', 'data' ]);
        });
	});
});