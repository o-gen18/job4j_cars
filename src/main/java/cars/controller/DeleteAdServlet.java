package cars.controller;

import cars.model.Advert;
import cars.persistence.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAdServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        AdRepository.instOf().deleteAdById(Integer.parseInt(req.getParameter("adId")));
    }
}
