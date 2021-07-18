package cars.controller;

import cars.model.Advert;
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
public class FetchAdsServletTest {

    @Test
    public void whenFetchAdsThenReturnJson() throws IOException {
        Repository repository = new AdRepositoryStub();
        PowerMockito.mockStatic(AdRepository.class);
        when(AdRepository.instOf()).thenReturn(repository);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(printWriter);

        Advert ad = new Advert();
        ad.setName("Test Ad");
        repository.save(ad);

        new FetchAdsServlet().doGet(req, resp);
        printWriter.flush();

        String expected = new ObjectMapper().writeValueAsString(repository.getAllAds());

        assertThat(stringWriter.toString(), is(expected));
    }
}