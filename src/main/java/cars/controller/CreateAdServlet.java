package cars.controller;

import cars.model.*;
import cars.persistence.AdRepository;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAdServlet extends HttpServlet {
    private Map<String, String> formValues = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
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

        Engine engine = Engine.of(
                Engine.FuelType.valueOf(formValues.get("fuelType")),
                Double.parseDouble(formValues.get("volume"))
        );

        CarModel carModel = CarModel.of(
                formValues.get("carModel"),
                CarModel.CarDrive.valueOf(formValues.get("carDrive")),
                CarModel.SteeringWheel.valueOf(formValues.get("steeringWheel"))
        );

        Car car = Car.of(
                formValues.get("car"),
                Integer.parseInt(formValues.get("mileage")),
                Integer.parseInt(formValues.get("yearOfIssue")),
                engine,
                carModel,
                carPhoto,
                Car.BodyType.valueOf(formValues.get("bodyType")),
                Car.Transmission.valueOf(formValues.get("transmission"))
        );

        Advert advert = Advert.of(
                formValues.get("advert"),
                Integer.parseInt(formValues.get("price")),
                formValues.get("description"),
                formValues.get("address"),
                new Date(),
                car,
                (Seller) req.getSession().getAttribute("Seller")
        );

        AdRepository.instOf().save(carPhoto);
        if (AdRepository.instOf().saveCar(car) != null) {
            AdRepository.instOf().save(advert);
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
