package uj.jwzp.w4.launchers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.CommandLinePropertySource;
import uj.jwzp.w4.logic.CSVMovieFinder;
import uj.jwzp.w4.logic.MovieFinder;
import uj.jwzp.w4.logic.MovieLister;
import uj.jwzp.w4.model.Movie;

import java.util.Properties;

@Slf4j
public class SpringMain {

    private static final String fileName = "movies.txt";
    private static MovieFinder movieFinder;

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("fileName", fileName);
        ApplicationContext ctx = new AnnotationConfigApplicationContext("uj.jwzp.w4"/*.logic"*/);
        movieFinder = (CSVMovieFinder) ctx.getBean("CSVMovieFinder");
        MovieLister lister = (MovieLister) ctx.getBean("movieLister", movieFinder);
        lister.moviesDirectedBy("Hoffman").stream()
                .map(Movie::toString)
                .forEach(log::info);
    }

    ApplicationContext createContext(CommandLinePropertySource propertySource){
        ApplicationContext ctx = new AnnotationConfigApplicationContext("uj.jwzp.w4.logic");


        return ctx;

    }
}
