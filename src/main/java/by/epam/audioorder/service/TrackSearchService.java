package by.epam.audioorder.service;

import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.exception.DAOException;

import java.util.ArrayList;
import java.util.List;

public class TrackSearchService {
    private final int ROWS_PER_PAGE = 30;

    public SearchResult<Track> searchTrack(String track, Genre genre, int page) {
        TrackDAO trackDAO = new TrackDAO();
        List<Track> tracks;
        try {
            if (track == null && genre == Genre.ANY) {
                tracks = trackDAO.findAll(page, ROWS_PER_PAGE);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
