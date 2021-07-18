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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AdRepository.class)
@PowerMockIgnore({"com.sun.*", "javax.xml.*", "org.xml.*", "javax.management.*",
        "org.apache.http.conn.ssl.*", "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*"})
public class SignUpServletTest {

    @Test
    public void whenSignUpThenUserIsPersisted() throws ServletException, IOException {
        Repository repository = new AdRepositoryStub();
        PowerMockito.mockStatic(AdRepository.class);
        when(AdRepository.instOf()).thenReturn(repository);

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        Seller seller = Seller.of(
                "New User", "Test@Email", "12345", "password"
        );

        req.setParameter("name", seller.getName());
        req.setParameter("email", seller.getEmail());
        req.setParameter("password", seller.getPassword());
        req.setParameter("phone", seller.getPhone());

        new SignUpServlet().doPost(req, resp);

        assertThat(repository.findSellerByEmail("Test@Email"),
                is(req.getSession().getAttribute("Seller")));
    }

    @Test
    public void whenUserAlreadyExistsThenError() throws ServletException, IOException {
        Repository repository = new AdRepositoryStub();
        PowerMockito.mockStatic(AdRepository.class);
        when(AdRepository.instOf()).thenReturn(repository);

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        Seller seller = Seller.of(
                "New User", "Test@Email", "12345", "password"
        );

        req.setParameter("name", seller.getName());
        req.setParameter("email", seller.getEmail());
        req.setParameter("password", seller.getPassword());
        req.setParameter("phone", seller.getPhone());

        repository.save(seller);

        new SignUpServlet().doPost(req, resp);

        assertThat(req.getAttribute("signUpError"), is("User with this email already exists!"));
    }
}
