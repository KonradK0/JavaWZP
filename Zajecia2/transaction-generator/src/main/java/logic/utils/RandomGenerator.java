package logic.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Component("RandomGenerator")
public class RandomGenerator {

    public Long getRandomLong(long start, long end) {
        return ThreadLocalRandom.current().nextLong(start, end);
    }
}
