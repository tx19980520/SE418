var express = require('express');
var bodyParser = require('body-parser');
var WordLadder = require('./wordladder.js')
var app = express();
// parser set
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

// cors set
app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Content-Type");
    res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
    res.header("X-Powered-By",' 3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    if (req.method === 'OPTIONS') {
        res.sendStatus(200);
    }
    else {
        next();
    }
});

// get the search word add check in the dictionary
app.get('/wordladder/search', function (req, res) {
    let word = req.query.word;
    let words = new WordLadder("./dictionary.json");
    res.json({"has":words.Search(word)});
});


app.post('/wordladder/BFS', function (req, res) {
    // get the data from body
    let input = req.body.input;
    let output = req.body.output;
    // set data in the WordLadder
    let words = new WordLadder("./dictionary.json");
    words.SetInput(input);
    words.SetOutput(output);
    // search the word first, to avoid long-time search and bad result
    if(words.Search(input) && words.Search(output))
    {
        res.json({"result":words.BFS()});
        // return the shortest path
    }
    else {
        res.json({"result": ['']});
    }
});


// set the server
var server = app.listen(3000, function () {
    var host = server.address().address;
    var port = server.address().port;

    console.log('Example app listening at http://%s:%s', host, port);
});


