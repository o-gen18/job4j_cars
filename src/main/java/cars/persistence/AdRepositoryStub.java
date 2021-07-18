package cars.persistence;

import cars.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for stubbing Repository and being used in tests.
 */
public class AdRepositoryStub implements Repository {
    private final Map<Integer, Advert> adverts = new HashMap<>();
    private int advertIds;

    private final Map<Integer, Car> cars = new HashMap<>();
    private int carIds;

    private final Map<String, Seller> sellers = new HashMap<>();
    private int sellerIds;

    public <T> T save(T model) {
        if (model instanceof Advert) {
            Advert ad = (Advert) model;
            int adId = ++advertIds;
            ad.setId(adId);
            adverts.put(adId, ad);
            return (T) ad;
        } else if (model instanceof Car) {
            Car car = (Car) model;
            int carId = ++carIds;
            car.setId(carId);
            cars.put(carId, (Car) model);
            return (T) car;
        } else if (model instanceof Seller) {
            Seller s = (Seller) model;
            int sId = ++sellerIds;
            s.setId(sId);
            sellers.put(s.getEmail(), s);
            return (T) s;
        } else {
            return null;
        }
    }

    @Override
    public <T> T update(T model) {
        if (model instanceof Advert) {
            Advert ad = (Advert) model;
            adverts.put(ad.getId(), ad);
            return (T) ad;
        } else if (model instanceof Car) {
            Car car = (Car) model;
            cars.put(car.getId(), (Car) model);
            return (T) car;
        } else if (model instanceof Seller) {
            Seller s = (Seller) model;
            sellers.put(s.getEmail(), s);
            return (T) s;
        } else {
            return null;
        }
    }

    @Override
    public <T> boolean deleteAdById(int id) {
        return adverts.remove(id) != null;
    }

    @Override
    public List<Advert> getAllAds() {
        return new ArrayList<>(adverts.values());
    }

    @Override
    public Advert getAdById(int id) {
        return adverts.get(id);
    }

    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    @Override
    public Seller findSellerByEmail(String email) {
        return sellers.get(email);
    }

    @Override
    public Car saveCar(Car car) {
        return save(car);
    }
}
