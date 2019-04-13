package com.wordLadder.wordLadder.wordLadder.controller;

import com.wordLadder.wordLadder.WordLadderApplication;
import com.wordLadder.wordLadder.controller.WorldLadderController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WorldLadderController Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar, 7, 2019</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= WordLadderApplication.class)
@ContextConfiguration(classes = WorldLadderController.class)
public class WordLadderControllerTest {

    protected MockMvc mockMvc;
    private MockHttpSession session;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    protected WebApplicationContext wac;
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).addFilters(this.springSecurityFilterChain).build();
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login").content("username=admin&password=admin")
                        .header("Content-Type", "application/x-www-form-urlencoded")
        ).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        session = (MockHttpSession)result.getRequest().getSession();
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
                MockMvcRequestBuilders.post("/api/BFS").content(input.toString()).session(session)
                        .header("Content-Type", "application/json")
        )
                .andExpect(status().isOk())
                .andReturn();
        JSONObject wordladder = JSONObject.fromObject(result.getResponse().getContentAsString());
        Assert.assertEquals("未能完成wordLadder", 5, ((JSONArray)wordladder.get("result")).size());
    }

    /**
     *
     * Method: search(@RequestParam("word") String word)
     *
     */
    @Test
    public void testSearch() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/search").session(session)
                        .param("word", "data")
        )
                .andExpect(status().isOk())
                .andReturn();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("应当存在单词data", JSONObject.fromObject(result.getResponse().getContentAsString()).get("has"), true);
    }


}

