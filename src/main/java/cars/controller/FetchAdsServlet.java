package cars.controller;

import cars.persistence.AdRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FetchAdsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(AdRepository.instOf().getAllAds());
        resp.getWriter().write(json);
    }
}
