package cars.persistence;

import cars.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class AdRepository implements AutoCloseable {
    private static final AdRepository INSTANCE = new AdRepository();

    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();

    private final SessionFactory sf =
            new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private AdRepository() {
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
            throw(e);
        } finally {
            session.close();
        }
    }

    public static AdRepository instOf() {
        return INSTANCE;
    }

    public <T> void save(T model) {
        doTransaction(session -> session.save(model));
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
                                + "JOIN FETCH ad.seller s ", Advert.class
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

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
