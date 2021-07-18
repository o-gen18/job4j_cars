package cars.controller;

import cars.model.*;
import cars.persistence.AdRepository;
import cars.utils.AdBuilder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAdServlet extends HttpServlet {
    private Map<String, String> formValues = new HashMap<>();
    private AdBuilder adBuilder = new AdBuilder();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        CarPhoto carPhoto = new CarPhoto();
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    formValues.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    if (item.getFieldName().equals("carPhoto")) {
                        carPhoto.setPhoto(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }

        Advert ad = adBuilder.buildAd(
                formValues,
                carPhoto,
                (Seller) req.getSession().getAttribute("Seller"));

        AdRepository.instOf().save(carPhoto);
        if (AdRepository.instOf().saveCar(ad.getCar()) != null) {
            AdRepository.instOf().save(ad);
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
