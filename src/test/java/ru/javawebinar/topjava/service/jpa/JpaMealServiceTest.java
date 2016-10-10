package ru.javawebinar.topjava.service.jpa;

import org.junit.AfterClass;

import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import java.util.concurrent.TimeUnit;

@ActiveProfiles(Profiles.JPA)
public class JpaMealServiceTest extends MealServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);

    private static StringBuffer results = new StringBuffer();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-20s %8d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            LOG.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResult() {
        System.out.printf("%nJpaMealTest     Duration, ms%n");
        System.out.println("-----------------------------");
        System.out.println(results);
        System.out.printf("-----------------------------%n%n");
    }
}
