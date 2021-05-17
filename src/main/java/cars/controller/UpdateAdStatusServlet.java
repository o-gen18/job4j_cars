package cars.controller;

import cars.model.Advert;
import cars.persistence.AdRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateAdStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int adId = Integer.parseInt(req.getParameter("adId"));
        boolean status = Boolean.parseBoolean(req.getParameter("status"));
        Advert ad = AdRepository.instOf().getAdById(adId);
        ad.setSold(status);
        AdRepository.instOf().update(ad);
    }
}
