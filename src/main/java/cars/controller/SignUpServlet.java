package cars.controller;

import cars.model.Seller;
import cars.persistence.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone-number");
        Seller seller = AdRepository.instOf()
                .findSellerByEmail(email); //can be optimised by using exception
        if (seller != null) {
            req.setAttribute("signUpError", "User with this email already exists!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            seller = AdRepository.instOf().save(
                    Seller.of(name, email, phone, password)
            );
            req.getSession().setAttribute("Seller", seller);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
