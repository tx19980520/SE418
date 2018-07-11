var fs = require('fs');
var express = require('express');
var bodyParser = require('body-parser');
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


function InitDict(dictPath){
    var data=fs.readFileSync(dictPath,'utf-8');
    return JSON.parse(data);
}

function WordLadder()
{
    this.dict = null;
    this.Input = null;
    this.Output = null;
    this._init();
}
WordLadder.prototype._init = function() {
    this.Input = "";
    this.Output = "";
    this.dict = InitDict("./dictionary.json");
}

WordLadder.prototype.SetInput = function(input) {
    this.Input = input;
}

WordLadder.prototype.SetOutput = function(output) {
    this.Output = output;
}

WordLadder.prototype.GetInput = function() {
    return this.Input;
}

WordLadder.prototype.GetOutput = function() {
    return this.Output;
}

WordLadder.prototype.Search = function(word) {
    let len = this.dict.length;
    for (let i = 0; i < len; ++i)
    {
        if (this.dict[i] === word)
        {
            return true;
        }
    }

    return false;
}

WordLadder.prototype.BFS = function() {
    let alphabet ="abcdefghijklmnopqrstuvwxyz";
    let SearchList = [];
    let searched = new Map();
    let init = {"word":this.GetInput(), "past":""};
    SearchList.push(init);
    while (SearchList.length !== 0)
    {
        let NowSearch = SearchList.shift();
        //change letter
        searched.set(NowSearch.word, NowSearch.past);
        let len = NowSearch.word.length;
        for (let i = 0; i < len; ++i)
        {
            for (let letter in alphabet)
            {
                let copy = new String();
                copy = copy.concat(NowSearch.word.substring(0,i),alphabet[letter],
                    NowSearch.word.substring(i+1));
                if (this.Search(copy) && !searched.has(copy))// has the word and hasn't been searched
                {
                    SearchList.push({"word":copy, "past":NowSearch.word});
                    searched.set(copy, NowSearch.word);
                    if (copy === this.GetOutput())// has get
                    {

                        let result = [];
                        result.push(copy)
                        let recursion = searched.get(copy);
                        while(recursion !== "")
                        {
                            result.push(recursion);
                            recursion = searched.get(recursion);
                        }
                        return result;
                    }
                }
            }
        }

    }
    return [""];
}


app.get('/wordladder/search', function (req, res) {
    let word = req.query.word;
    let words = new WordLadder();
    res.json({"has":words.Search(word)});
});


app.post('/wordladder/BFS', function (req, res) {
    let input = req.body.input;
    let output = req.body.output;
    let words = new WordLadder();
    words.SetInput(input);
    words.SetOutput(output);
    res.json({"result":words.BFS()});
});

var server = app.listen(3000, function () {
    var host = server.address().address;
    var port = server.address().port;

    console.log('Example app listening at http://%s:%s', host, port);
});


