//package uj.jwzp.w4.launchers;
//
//import lombok.extern.slf4j.Slf4j;
//import uj.jwzp.w4.logic.CSVMovieFinder;
//import uj.jwzp.w4.logic.MovieFinder;
//import uj.jwzp.w4.logic.MovieLister;
//import uj.jwzp.w4.model.Movie;
//
//@Slf4j
//public class ManualMain {
//
//    private static final String fileName = "movies.txt";
//    private static final MovieFinder movieFinder = new CSVMovieFinder();
//
//    public static void main(String[] args) {
//        MovieLister lister = new MovieLister(movieFinder);
//        lister.moviesDirectedBy("Hoffman").stream()
//            .map(Movie::toString)
//            .forEach(log::info);
//    }
//}
