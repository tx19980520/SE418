package com.wordLadder.wordLadder.wordLadder.wordladder;

import com.wordLadder.wordLadder.wordladder.WordLadder;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.core.io.ClassPathResource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * WordLadder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>March, 7, 2019</pre>
 */
public class WordLadderTest {

    private WordLadder wl;

    @Before
    public void before() throws Exception {
        ClassPathResource dict = new ClassPathResource("static/small.json");
        wl = new WordLadder(dict.getInputStream());
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: find(String word)
     */
    @Test
    public void testFind() throws Exception {
        Boolean answer_false = wl.find("hello");
        Boolean answer_true = wl.find("data");
        Assert.assertEquals("The word hello should not be founded.", answer_false, false);
        Assert.assertEquals("The word data should be founded.", answer_true, true);
//TODO: Test goes here... 
    }

    /**
     * Method: BFS(String start_word, String end_word)
     */
    @Test
    public void testBFS() throws Exception {
        Assert.assertEquals("data -> code should return array(5).", wl.BFS("code", "data").size(), 5);
    }

    /**
     * Method: search(int s_idx, String end_word)
     */
    @Test
    public void testSearch() throws Exception {
        Method _search = WordLadder.class.getDeclaredMethod("search", int.class, String.class);
        _search.setAccessible(true);
        int s_idx = 0;
        ArrayList<Integer> result  = (ArrayList<Integer>) _search.invoke(wl, s_idx, "date");
        Assert.assertEquals("size",result.size(), 4);
    }


    /**
     * Method: isNear(String a, String b)
     */
    @Test
    public void testIsNear() throws Exception {
        Method _near = WordLadder.class.getDeclaredMethod("isNear", String.class, String.class);
        _near.setAccessible(true);

        Assert.assertTrue((Boolean)_near.invoke(wl, "data", "date"));
        Assert.assertTrue((Boolean)_near.invoke(wl, "teacher", "teachers"));
        Assert.assertFalse((Boolean)_near.invoke(wl, "foo", "bar"));
    }


}
