package cars.persistence;

import cars.model.*;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AdRepositoryTest {
    private static final Date startOfTheTest = new Date();

    private static final Repository repository = AdRepository.instOf();

    //As repository object is singleton, each test is performed with the same database.
    //The majority of the fields of the objects in model package must have non-null values.
    //Also the field 'email' in seller's class must be unique, so in each test the new seller
    //with unique email has to be used.

    private static final AtomicInteger counter = new AtomicInteger(0);

    private Advert newTestAd() {
        int uniqueNumber = counter.incrementAndGet();
        Engine engine = Engine.of(
                Engine.FuelType.valueOf("Gasoline"),
                Double.parseDouble("2.2")
        );

        CarModel carModel = CarModel.of(
                "CarModel - Test №:" + uniqueNumber,
                CarModel.CarDrive.valueOf("FRONT_WHEEL_DRIVE"),
                CarModel.SteeringWheel.valueOf("RIGHT_HAND_DRIVE")
        );

        Car car = Car.of(
                "Car - Test №:" + uniqueNumber,
                99000,
                2015,
                engine,
                carModel,
                new CarPhoto(),
                Car.BodyType.valueOf("Station_wagon"),
                Car.Transmission.valueOf("Automatic")
        );

        Advert advert = Advert.of(
                "Advert - Test №:" + uniqueNumber,
                5000,
                "Used car for sale",
                "Paris, France",
                startOfTheTest,
                car,
                Seller.of("Test - seller №:" + uniqueNumber, "Test@Email №:" + uniqueNumber,
                        "123456789", "password")
        );
        return advert;
    }

    private int retrieveUniqueNumber(Advert ad) {
        int separator = ad.getName().indexOf(":");
        return Integer.parseInt(ad.getName().substring(separator + 1));
    }

    @Test
    public void whenSaveAdThenReturnSaved() {
        Advert testAd = newTestAd();
        repository.save(testAd.getSeller());
        repository.save(testAd.getCar().getPhoto());
        repository.saveCar(testAd.getCar());
        repository.save(testAd);

        int id = testAd.getId();

        Advert result = repository.getAdById(id);

        int uniqueNumber = retrieveUniqueNumber(testAd);

        assertThat(result.getName(), is("Advert - Test №:" + uniqueNumber));
        assertThat(result.getPrice(), is(5000));
        assertThat(result.getDescription(), is("Used car for sale"));
        assertThat(result.getAddress(), is("Paris, France"));
        assertThat(result.isSold(), is(false));
        assertThat(result.getCar().getName(), is("Car - Test №:" + uniqueNumber));
        assertThat(result.getCar().getMileage(), is(99000));
        assertThat(result.getCar().getYearOfIssue(), is(2015));
        assertThat(result.getCar().getEngine().getType().toString(), is("Gasoline"));
        assertThat(result.getCar().getEngine().getVolume(), is(2.2));
        assertThat(result.getCar().getCarModel().getName(), is("CarModel - Test №:" + uniqueNumber));
        assertThat(result.getCar().getCarModel().getCarDrive().name(), is("FRONT_WHEEL_DRIVE"));
        assertThat(result.getCar().getCarModel().getSteeringWheel().name(), is("RIGHT_HAND_DRIVE"));
        assertThat(result.getCar().getTransmission().name(), is("Automatic"));
        assertThat(result.getCar().getBodyType().name(), is("Station_wagon"));
        assertThat(result.getSeller().getName(), is("Test - seller №:" + uniqueNumber));
        assertThat(result.getSeller().getPassword(), is("password"));
        assertThat(result.getSeller().getPhone(), is("123456789"));
        assertThat(result.getSeller().getEmail(), is("Test@Email №:" + uniqueNumber));
    }

    @Test
    public void whenSaveCarThenGetAllCars() {
        Advert testAd = newTestAd();
        CarPhoto dummyPhoto = new CarPhoto();

        Car bmw = Car.of(
                "BMW",
                50000,
                2017,
                testAd.getCar().getEngine(),
                testAd.getCar().getCarModel(),
                dummyPhoto,
                Car.BodyType.valueOf("Station_wagon"),
                Car.Transmission.valueOf("Automatic")
        );

        Car testCar = testAd.getCar();
        testCar.setPhoto(dummyPhoto);

        repository.save(dummyPhoto);
        repository.saveCar(bmw);
        repository.saveCar(testCar);

        List<Car> cars = repository.getAllCars();

        int uniqueNumber = retrieveUniqueNumber(testAd);

        assertThat(cars.get(cars.indexOf(bmw)).getName(), is("BMW"));
        assertThat(cars.get(cars.indexOf(testAd.getCar())).getName(), is("Car - Test №:" + uniqueNumber));
    }

    @Test
    public void whenFindUserByEmailThenGetUser() {
        Advert testAd = newTestAd();
        repository.save(testAd.getSeller());
        int uniqueNumber = retrieveUniqueNumber(testAd);

        Seller result = repository.findSellerByEmail("Test@Email №:" + uniqueNumber);
        assertThat(result.getName(), is("Test - seller №:" + uniqueNumber));
    }

    @Test
    public void whenUpdateAdvertThenGetUpdated() {
        Advert testAd = newTestAd();
        Seller updatedSeller = Seller.of(
                "Updated Seller", "Updated@Email+","000", "123"
        );

        System.out.println(repository.getAllAds());
        repository.save(updatedSeller);

        repository.save(testAd.getSeller());
        repository.save(testAd.getCar().getPhoto());
        repository.saveCar(testAd.getCar());
        repository.save(testAd);

        testAd.setSold(true);
        testAd.setSeller(updatedSeller);
        testAd.getCar().setName("Updated Car");
        testAd.setDescription("Updated Description");

        testAd.getSeller().getAds().remove(testAd);
        repository.update(testAd.getSeller());
        repository.update(testAd.getCar());
        repository.update(testAd);

        Advert result = repository.getAdById(testAd.getId());

        assertThat(result.getSeller().getName(), is("Updated Seller"));
        assertThat(result.getCar().getName(), is("Updated Car"));
        assertThat(result.getDescription(), is("Updated Description"));
        assertThat(result.isSold(), is(true));
    }

    @Test
    public void whenDeleteAdThenDeletion() {
        Advert testAd = newTestAd();

        repository.save(testAd.getSeller());
        repository.save(testAd.getCar().getPhoto());
        repository.saveCar(testAd.getCar());
        repository.save(testAd);

        int size = repository.getAllAds().size();

        repository.deleteAdById(testAd.getId());

        assertThat(repository.getAllAds().size(), is(size - 1));
    }
}
