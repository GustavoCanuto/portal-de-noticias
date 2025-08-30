package br.com.magportal.apimagspnews.infra;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> {

    @JsonProperty("empty")
    private boolean isEmpty;
    
    @JsonProperty("content")
    private List<T> content;
    
    
    public List<T> getContent() {
        return content;
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }

  
}
