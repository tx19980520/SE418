package com.wordLadder.wordLadder.wordLadder.controller;

import com.wordLadder.wordLadder.WordLadderApplication;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
/**
 * WorldLadderController Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar, 7, 2019</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= WordLadderApplication.class)
public class WordLadderControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Before
    public void before() throws Exception {
        // mocMVC init
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: getWordLadder(@RequestBody JSONObject words)
     *
     */
    @Test
    public void testGetWordLadder() throws Exception {
        JSONObject input = new JSONObject();
        input.put("input", "data");
        input.put("output", "code");
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/BFS").content(input.toString())
                        .header("Content-Type", "application/json")
        ).andReturn();
        JSONArray wordladder = JSONArray.fromObject(result.getResponse().getContentAsString());
        Assert.assertEquals("未能完成wordLadder", 5, wordladder.size());
    }

    /**
     *
     * Method: search(@RequestParam("word") String word)
     *
     */
    @Test
    public void testSearch() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/search")
                        .param("word", "data")
        ).andReturn();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("应当存在单词data", JSONObject.fromObject(result.getResponse().getContentAsString()).get("has"), true);
    }


}

