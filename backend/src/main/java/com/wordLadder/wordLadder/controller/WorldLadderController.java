package com.wordLadder.wordLadder.controller;
import com.wordLadder.wordLadder.wordladder.WordLadder;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ClassUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
@RestController
public class WorldLadderController {

    @RequestMapping(value = "/BFS", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONArray getWordLadder(@RequestBody JSONObject words) throws URISyntaxException
    {
        WordLadder wl = new WordLadder(this.getClass().getResource("").getPath()+"small.json");
        ArrayList<String> list = wl.BFS(words.get("input").toString(), words.get("output").toString());
        System.out.println(list);
        JSONArray result = JSONArray.fromObject(list);
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject search(@RequestParam("word") String word)
    {

        WordLadder wl = new WordLadder(this.getClass().getResource("").getPath()+"small.json");
        JSONObject result = new JSONObject();
        result.put("has", wl.find(word));
        return result;
    }
}
