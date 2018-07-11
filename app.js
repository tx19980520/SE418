var express = require('express');
var bodyParser = require('body-parser');
var WordLadder = require('./wordladder.js')
var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Content-Type");
    res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
    res.header("X-Powered-By",' 3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    if (req.method === 'OPTIONS') {
        res.sendStatus(200); // 让options请求快速返回
    }
    else {
        next();
    }
});


app.get('/wordladder/search', function (req, res) {
    let word = req.query.word;
    let words = new WordLadder("./dictionary.json");
    res.json({"has":words.Search(word)});
});


app.post('/wordladder/BFS', function (req, res) {
    let input = req.body.input;
    let output = req.body.output;
    let words = new WordLadder("./dictionary.json");
    words.SetInput(input);
    words.SetOutput(output);
    res.json({"result":words.BFS()});
});

var server = app.listen(3000, function () {
    var host = server.address().address;
    var port = server.address().port;

    console.log('Example app listening at http://%s:%s', host, port);
});


