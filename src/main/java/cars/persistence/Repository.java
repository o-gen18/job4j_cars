package cars.persistence;

import cars.model.*;

import java.util.List;

public interface Repository {
    <T> T save(T model);

    <T> T update(T model);

    <T> boolean deleteAdById(int id);

    List<Advert> getAllAds();

    Advert getAdById(int id);

    List<Car> getAllCars();

    Seller findSellerByEmail(String email);

    Car saveCar(Car car);
}
