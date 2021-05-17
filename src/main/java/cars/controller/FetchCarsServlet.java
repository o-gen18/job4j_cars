package cars.controller;

import cars.persistence.AdRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FetchCarsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(
                AdRepository.instOf().getAllCars());
        resp.getWriter().write(json);
    }
}
