package uj.jwzp.w4.launchers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import uj.jwzp.w4.logic.CSVMovieFinder;
import uj.jwzp.w4.logic.MovieFinder;
import uj.jwzp.w4.logic.MovieLister;
import uj.jwzp.w4.model.Movie;


@Slf4j
public class SpringMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = createContext(new SimpleCommandLinePropertySource(args));
        MovieFinder movieFinder = ctx.getBean(CSVMovieFinder.class);
        MovieLister lister = (MovieLister) ctx.getBean("movieLister", movieFinder);
        lister.moviesDirectedBy("Hoffman").stream()
                .map(Movie::toString)
                .forEach(log::info);
    }

    static AnnotationConfigApplicationContext createContext(CommandLinePropertySource propertySource){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(propertySource);
        ctx.scan("uj.jwzp.w4.logic");
        ctx.refresh();
        return ctx;
    }
}
