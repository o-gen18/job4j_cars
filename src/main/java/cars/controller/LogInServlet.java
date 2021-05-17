package cars.controller;

import cars.model.Seller;
import cars.persistence.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogInServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Seller seller = AdRepository.instOf()
                .findSellerByEmail(email);
        if (seller != null && seller.getPassword().equals(password)) {
            req.getSession().setAttribute("Seller", seller);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute("loginError", "Incorrect email or password!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
