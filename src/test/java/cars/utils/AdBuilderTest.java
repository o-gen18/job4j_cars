package cars.utils;

import cars.model.Advert;
import cars.model.CarPhoto;
import cars.model.Seller;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AdBuilderTest {

    @Test
    public void whenPassParametersToBuilderThenBuildAdObject() {
        Map<String, String> formParams = new HashMap<>();
        AdBuilder builder = new AdBuilder();

        Seller seller = Seller.of(
                "Test - seller", "Test@Test", "123456789", "987654321");
        CarPhoto carPhoto = new CarPhoto();

        formParams.put("advert", "Advert - Test");
        formParams.put("car", "Car - Test");
        formParams.put("carModel", "CarModel - Test");
        formParams.put("carDrive", "FRONT_WHEEL_DRIVE");
        formParams.put("steeringWheel", "RIGHT_HAND_DRIVE");
        formParams.put("bodyType", "Station_wagon");
        formParams.put("transmission", "Automatic");
        formParams.put("fuelType", "Gasoline");
        formParams.put("volume", "2.2");
        formParams.put("price", "5000");
        formParams.put("mileage", "99000");
        formParams.put("yearOfIssue", "2015");
        formParams.put("description", "Used car for sale");
        formParams.put("address", "Paris, France");

        Advert result = builder.buildAd(
                formParams, carPhoto, seller);

        assertThat(result.getName(), is("Advert - Test"));
        assertThat(result.getPrice(), is(5000));
        assertThat(result.getDescription(), is("Used car for sale"));
        assertThat(result.getAddress(), is("Paris, France"));
        assertThat(result.isSold(), is(false));
        assertThat(result.getCar().getName(), is("Car - Test"));
        assertThat(result.getCar().getMileage(), is(99000));
        assertThat(result.getCar().getYearOfIssue(), is(2015));
        assertThat(result.getCar().getEngine().getType().toString(), is("Gasoline"));
        assertThat(result.getCar().getEngine().getVolume(), is(2.2));
        assertThat(result.getCar().getCarModel().getName(), is("CarModel - Test"));
        assertThat(result.getCar().getCarModel().getCarDrive().name(), is("FRONT_WHEEL_DRIVE"));
        assertThat(result.getCar().getCarModel().getSteeringWheel().name(), is("RIGHT_HAND_DRIVE"));
        assertThat(result.getCar().getTransmission().name(), is("Automatic"));
        assertThat(result.getCar().getBodyType().name(), is("Station_wagon"));
        assertThat(result.getSeller().getName(), is("Test - seller"));
        assertThat(result.getSeller().getPassword(), is("987654321"));
        assertThat(result.getSeller().getPhone(), is("123456789"));
        assertThat(result.getSeller().getEmail(), is("Test@Test"));
    }
}
