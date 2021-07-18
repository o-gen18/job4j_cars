package cars.utils;

import cars.model.*;

import java.util.Date;
import java.util.Map;

public class AdBuilder {

    /**
     * Builds an ad using values being inputted on the front-end
     * and a photo that was uploaded.
     * @param formValues A map with parameters from the input form.
     * @param carPhoto A CarPhoto object.
     * @param seller The particular user retrieved from the session.
     * @return The fully compiled Advert object.
     */
    public Advert buildAd(Map<String, String> formValues, CarPhoto carPhoto, Seller seller) {
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
                seller
        );
        return  advert;
    }
}
