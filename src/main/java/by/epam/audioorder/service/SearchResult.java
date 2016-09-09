package by.epam.audioorder.service;

import java.util.List;

public class SearchResult<T> {
    private List<T> results;
    private int numberOfPages;

    public SearchResult(List<T> results, int numberOfPages) {
        this.results = results;
        this.numberOfPages = numberOfPages;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
