package io.ggammu.study.tobyspringframework.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { TestDaoFactory.class })
//@DirtiesContext
//@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {
    private SummaryGeneratingListener listener = new SummaryGeneratingListener();
//    @Autowired
    private ApplicationContext context;
//    @Autowired
    private UserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDaoTest runner = new UserDaoTest();
        runner.runOne();
        TestExecutionSummary summary = runner.listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
    }

    private void runOne() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(UserDaoTest.class))
                .build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }

    @BeforeEach
    public void setUp() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

//        System.out.println("contexttest " + this.context);
//        System.out.println(this);
//        userDao = context.getBean("userDao", UserDao.class);

        userDao = new UserDao();
        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mysql://localhost:3306/toby_spring",
                "spring",
                "password",
                true
        );
        userDao.setDataSource(dataSource);

        user1 = new User("1", "user1", "password1");
        user2 = new User("2", "user2", "password2");
        user3 = new User("3", "user3", "password3");
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.deleteAll();

        // then
        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User userGet1 = userDao.get(user1.getId());
        assertThat(userGet1.getName()).isEqualTo(user1.getName());
        assertThat(userGet1.getPassword()).isEqualTo(user1.getPassword());

        User userGet2 = userDao.get(user2.getId());
        assertThat(userGet2.getName()).isEqualTo(user2.getName());
        assertThat(userGet2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        // then
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        // given
        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        // when
        Throwable thrown = catchThrowable(() -> userDao.get("unknown"));

        // then
        assertThat(thrown).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
