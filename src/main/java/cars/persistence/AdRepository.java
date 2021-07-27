package cars.persistence;

import cars.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.*;
import java.util.function.Function;

public class AdRepository implements Repository, AutoCloseable {
    private static final Repository INSTANCE = new AdRepository();

    private final StandardServiceRegistry registry;

    private final SessionFactory sf;

    private AdRepository() {
        Map<String, String> jdbcUrlSettings = new HashMap<>();
        String jdbcDbUrl = System.getenv("JDBC_DATABASE_URL");
        if (null != jdbcDbUrl) {
            jdbcUrlSettings.put("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        }

        registry = new StandardServiceRegistryBuilder().
                configure("hibernate.cfg.xml").
                applySettings(jdbcUrlSettings).
                build();

        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private <T> T doTransaction(final Function<Session, T> query) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = query.apply(session);
            transaction.commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw (e);
        } finally {
            session.close();
        }
    }

    public static Repository instOf() {
        return INSTANCE;
    }

    public <T> T save(T model) {
        doTransaction(session -> session.save(model));
        return model;
    }

    public <T> T update(T model) {
        return this.doTransaction(session -> {
            session.update(model);
            return model;
        });
    }

    public <T> boolean delete(T model) {
        return this.doTransaction(session -> {
            session.delete(model);
            return true;
        });
    }

    public <T> boolean deleteAdById(int id) {
        return doTransaction(session -> { session
                    .createQuery("DELETE FROM cars.model.Advert WHERE id =:ID")
                    .setParameter("ID", id).executeUpdate();
            return true;
        });
    }

    public List<Advert> getTodayAds() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date midnight = cal.getTime();
        return doTransaction(session ->
                session.createQuery(
                        "SELECT DISTINCT ad FROM cars.model.Advert ad "
                                + "JOIN FETCH ad.car c "
                                + "FULL JOIN FETCH c.photo "
                                + "JOIN FETCH c.carModel "
                                + "JOIN FETCH c.engine "
                                + "JOIN FETCH ad.seller s "
                                + "where ad.created > :midnight", Advert.class)
                        .setParameter("midnight", midnight)
                        .list()
        );
    }

    public List<Advert> getAllAds() {
        return doTransaction(session ->
                session.createQuery(
                        "SELECT DISTINCT ad FROM cars.model.Advert ad "
                                + "JOIN FETCH ad.car c "
                                + "FULL JOIN FETCH c.photo " //'FULL' to allow retrieve cars without photos
                                + "JOIN FETCH c.carModel "
                                + "JOIN FETCH c.engine "
                                + "JOIN FETCH ad.seller s ORDER BY ad.created DESC", Advert.class
                ).list()
        );
    }

    public List<Advert> getAdsWithPhoto() {
        return doTransaction(session ->
                session.createQuery(
                        "SELECT DISTINCT ad FROM cars.model.Advert ad "
                                + "JOIN FETCH ad.car c "
                                + "JOIN FETCH c.photo "
                                + "JOIN FETCH c.carModel "
                                + "JOIN FETCH c.engine "
                                + "JOIN FETCH ad.seller s ", Advert.class
                ).list()
        );
    }

    public List<Advert> getAdsByCarModel(CarModel model) {
        return doTransaction(session ->
                session.createQuery(
                        "SELECT DISTINCT ad FROM cars.model.Advert ad "
                                + "JOIN FETCH ad.car c "
                                + "FULL JOIN FETCH c.photo "
                                + "JOIN FETCH c.carModel model "
                                + "JOIN FETCH c.engine "
                                + "JOIN FETCH ad.seller s "
                                + "where model.name = :carModel", Advert.class)
                        .setParameter("carModel", model.getName())
                        .list()
        );
    }

    public Advert getAdById(int id) {
        return doTransaction(session ->
                session.createQuery(
                        "SELECT DISTINCT ad FROM cars.model.Advert ad "
                                + "JOIN FETCH ad.car c "
                                + "FULL JOIN FETCH c.photo "
                                + "JOIN FETCH c.carModel model "
                                + "JOIN FETCH c.engine "
                                + "JOIN FETCH ad.seller s "
                                + "where ad.id = :ID", Advert.class)
                        .setParameter("ID", id)
                        .uniqueResult());
    }

    public List<Car> getAllCars() {
        return doTransaction(session ->
                session.createQuery(
                        "SELECT DISTINCT car FROM cars.model.Car car "
                                + "JOIN FETCH car.engine "
                                + "FULL JOIN FETCH car.photo "
                                + "JOIN FETCH car.carModel", Car.class)
                        .list()
        );
    }

    public Seller findSellerByEmail(String email) {
        return doTransaction(session -> session
                .createQuery("SELECT seller FROM cars.model.Seller as seller where email=:sEmail", Seller.class)
                .setParameter("sEmail", email)
                .uniqueResult());
    }

    /**
     * Returns the persistent Engine objects by given properties.
     *
     * @param fuelType the fuel type of the engine.
     * @param volume   the volume of the engine.
     * @return an Engine.
     */
    private Engine findEngineByParams(Engine.FuelType fuelType, double volume) {
        return doTransaction(session -> session
                .createQuery("FROM Engine WHERE fuelType =:FT AND volume =:VOL", Engine.class)
                .setParameter("FT", fuelType)
                .setParameter("VOL", volume)
                .uniqueResult());
    }

    /**
     * If a CarModel with the given properties already exists in the database, returns it;
     *
     * @param name          CarModel name.
     * @param carDrive      the car drive of the CarModel.
     * @param steeringWheel the steering wheel of the CarModel.
     * @return a CarModel.
     */
    private CarModel findModelByParams(String name,
                                       CarModel.CarDrive carDrive,
                                       CarModel.SteeringWheel steeringWheel) {
        return doTransaction(session -> session
                .createQuery("FROM cars.model.CarModel WHERE name =:n "
                        + "AND carDrive =:cd AND steeringWheel =:sw", CarModel.class)
                .setParameter("n", name)
                .setParameter("cd", carDrive)
                .setParameter("sw", steeringWheel)
                .uniqueResult());
    }

    /**
     * Saves a car into the database. Before, checks if any of the inner objects
     * are already present in the database, and if so, assigns the id value
     * of persistent objects to those that compound the Car object whereby avoiding
     * unique constraints violations.
     *
     * @param car the car to be saved.
     */
    public Car saveCar(Car car) {
        Engine engine = findEngineByParams(
                car.getEngine().getType(),
                car.getEngine().getVolume());
        CarModel model = findModelByParams(
                car.getCarModel().getName(),
                car.getCarModel().getCarDrive(),
                car.getCarModel().getSteeringWheel()
        );
        if (engine == null) {
            save(car.getEngine());
        } else {
            car.getEngine().setId(engine.getId());
        }
        if (model == null) {
            save(car.getCarModel());
        } else {
            car.getCarModel().setId(model.getId());
        }
        return save(car);
    }

    public CarModel findCarModelByName(String name) {
        return doTransaction(session -> session
                .createQuery("FROM CarModel WHERE name =:N", CarModel.class)
                .setParameter("N", name)
                .uniqueResult());
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}