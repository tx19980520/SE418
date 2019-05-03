package ty0207.wordladder.auth.service;

import net.sf.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WordladderService {

    public JSONObject requestWordladder(String start, String end)
    {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://wordladder:8080/api/BFS?input=" + start+ "&output=" + end;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<JSONObject> result = restTemplate.exchange(uri, HttpMethod.GET, entity, JSONObject.class);
        if(result.getStatusCode().isError())
        {
            return null;
        }
        return result.getBody();
    }


    public  JSONObject requestSearch(String word)
    {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://wordladder:8080/api/search?word="+ word;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<JSONObject> result = restTemplate.exchange(uri, HttpMethod.GET, entity, JSONObject.class);
        if(result.getStatusCode().isError())
        {
            return null;
        }
        return result.getBody();
    }
}
