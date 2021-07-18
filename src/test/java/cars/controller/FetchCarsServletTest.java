package cars.controller;

import cars.model.Advert;
import cars.model.Car;
import cars.persistence.AdRepository;
import cars.persistence.AdRepositoryStub;
import cars.persistence.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AdRepository.class)
@PowerMockIgnore({"com.sun.*", "javax.xml.*", "org.xml.*", "javax.management.*",
        "org.apache.http.conn.ssl.*", "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*"})
public class FetchCarsServletTest {

    @Test
    public void whenFetchCarsThenReturnList() throws IOException {
        Repository repository = new AdRepositoryStub();
        PowerMockito.mockStatic(AdRepository.class);
        when(AdRepository.instOf()).thenReturn(repository);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(printWriter);

        Car car1 = new Car();
        car1.setName("Test1");

        Car car2 = new Car();
        car2.setName("Test2");
        repository.save(car1);
        repository.save(car2);

        new FetchCarsServlet().doGet(req, resp);
        printWriter.flush();

        String expected = new ObjectMapper().writeValueAsString(repository.getAllCars());

        assertThat(stringWriter.toString(), is(expected));
    }
}
