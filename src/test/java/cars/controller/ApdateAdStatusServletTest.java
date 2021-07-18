package cars.controller;

import cars.model.Advert;
import cars.persistence.AdRepository;
import cars.persistence.AdRepositoryStub;
import cars.persistence.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AdRepository.class)
@PowerMockIgnore({"com.sun.*", "javax.xml.*", "org.xml.*", "javax.management.*",
        "org.apache.http.conn.ssl.*", "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*"})
public class ApdateAdStatusServletTest {

    @Test
    public void whenUpdateAdStatusThenUpdate() {
        Repository repository = new AdRepositoryStub();
        PowerMockito.mockStatic(AdRepository.class);
        HttpServletRequest mockReq = mock(HttpServletRequest.class);
        HttpServletResponse mockResp = mock(HttpServletResponse.class);

        Mockito.when(AdRepository.instOf()).thenReturn(repository);
        when(mockReq.getParameter("adId")).thenReturn("1");
        when(mockReq.getParameter("status")).thenReturn("true");

        Advert ad = new Advert();

        repository.save(ad);

        assertThat(repository.getAllAds().size(), is(1));
        assertThat(repository.getAllAds().get(0).isSold(), is(false));

        new UpdateAdStatusServlet().doPost(mockReq, mockResp);

        assertThat(repository.getAllAds().get(0).isSold(), is(true));
    }
}
