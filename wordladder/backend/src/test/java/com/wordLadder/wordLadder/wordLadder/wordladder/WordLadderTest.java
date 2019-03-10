package com.wordLadder.wordLadder.wordLadder.wordladder;

import com.wordLadder.wordLadder.wordladder.WordLadder;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.core.io.ClassPathResource;

/** 
* WordLadder Tester. 
* 
* @author <Authors name> 
* @since <pre>March, 7, 2019</pre>
* @version 1.0 
*/ 
public class WordLadderTest { 

    private WordLadder wl;
@Before
public void before() throws Exception {
    ClassPathResource dict = new ClassPathResource("static/small.json");
    wl = new WordLadder(dict.getFile().getAbsolutePath());
}

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: find(String word) 
* 
*/ 
@Test
public void testSearch() throws Exception {
    Boolean answer_false = wl.find("hello");
    Boolean answer_true = wl.find("data");
    Assert.assertEquals("The word hello should not be founded.",answer_false, false);
    Assert.assertEquals("The word data should be founded.",answer_true, true);
//TODO: Test goes here... 
} 

/** 
* 
* Method: BFS(String start_word, String end_word) 
* 
*/ 
@Test
public void testBFS() throws Exception {
    Assert.assertEquals("data -> code should return array(5).",wl.BFS("code", "data").size(), 5);
    Assert.assertNull("data -> dom should return null.",wl.BFS("data", "dom"));
} 





} 
