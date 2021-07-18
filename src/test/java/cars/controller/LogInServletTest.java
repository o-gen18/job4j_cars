package cars.controller;

import cars.model.Seller;
import cars.persistence.AdRepository;
import cars.persistence.AdRepositoryStub;
import cars.persistence.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AdRepository.class)
@PowerMockIgnore({"com.sun.*", "javax.xml.*", "org.xml.*", "javax.management.*",
        "org.apache.http.conn.ssl.*", "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*"})
public class LogInServletTest {

    @Test
    public void whenLoginThenUserInSession() throws ServletException, IOException {
        Repository repository = new AdRepositoryStub();
        PowerMockito.mockStatic(AdRepository.class);
        when(AdRepository.instOf()).thenReturn(repository);

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        Seller testSeller = new Seller();
        testSeller.setEmail("Test@Email");
        testSeller.setPassword("password");

        LogInServlet logInServlet = new LogInServlet();
        logInServlet.doPost(req, resp);

        assertThat(req.getAttribute("loginError"), is("Incorrect email or password!"));

        repository.save(testSeller);
        req.setParameter("email", testSeller.getEmail());
        req.setParameter("password", testSeller.getPassword());

        logInServlet.doPost(req, resp);

        assertThat(req.getSession().getAttribute("Seller"), is(testSeller));
    }
}
