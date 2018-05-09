package uj.jwzp.w4.logic;

import org.springframework.stereotype.Service;
import uj.jwzp.w4.model.Movie;

import java.util.List;


public interface MovieFinder {
    List<Movie> findAll();
}
