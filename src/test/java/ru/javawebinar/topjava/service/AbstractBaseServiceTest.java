package ru.javawebinar.topjava.service;

import gigadot.rebound.Rebound;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.Profiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(Profiles.ACTIVE_DB)
public abstract class AbstractBaseServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // get number of test subclasses with Rebound lib (https://bitbucket.org/gigadot/rebound/wiki/Home)
//    private static Rebound r = new Rebound(AbstractBaseServiceTest.class.getPackage().getName());
//    private static int TESTS_COUNT = r.getSubClassesOf(AbstractBaseServiceTest.class).size();
//
//    private final static StringBuilder testResults = new StringBuilder();
//    private static final Map<String, Long> testDurations = new HashMap<>();
//    private static boolean needToSetHeader = false;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseServiceTest.class);
//
//    @Rule
//    public Stopwatch stopwatch = new Stopwatch() {
//        @Override
//        protected void finished(long nanos, Description description) {
//            String testName = description.getMethodName();
//            long duration = TimeUnit.NANOSECONDS.toMillis(nanos);
//            testDurations.put(testName, duration);
//        }
//    };
//
//    @BeforeClass
//    public static void init() {
//        needToSetHeader = true;
//    }
//
//    public AbstractBaseServiceTest() {
//        // Set the header of the results table
//        if (needToSetHeader) {
//            testResults
//                    .append(String.format("%n%s%n", getClass().getSimpleName()))
//                    .append(String.format("===============================%n"))
//                    .append(String.format("Test               Duration, ms%n"))
//                    .append(String.format("-------------------------------%n"));
//        }
//        needToSetHeader = false;
//    }
//
//    @AfterClass
//    public static void print() {
//        TESTS_COUNT--;
//
//        testResults
//                .append(testDurations.entrySet().stream()
//                        .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
//                        .map(e -> String.format("%-25s %5d%n", e.getKey(), e.getValue()))
//                        .collect(Collectors.joining("")))
//                .append(String.format("-------------------------------%n"));
//
//        testDurations.clear();
//
//        if (TESTS_COUNT == 0) {
//            LOGGER.info(testResults.toString());
//        }
//    }
}
