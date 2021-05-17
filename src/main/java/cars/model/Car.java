package cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car")
public class Car {
    public enum BodyType {
        Sedan, Coupe, Sports_car, Station_wagon,
        Hatchback, Convertible, Minivan, Pickup
    }

    public enum Transmission {
        Manual, Automatic, CVT, Semi_automatic
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int mileage;

    @Column(nullable = false)
    private int yearOfIssue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id",
            foreignKey = @ForeignKey(name = "ENGINE_ID_FK"), nullable = false)
    private Engine engine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id",
            foreignKey = @ForeignKey(name = "CAR_MODEL_ID_FK"), nullable = false)
    private CarModel carModel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_photo_id", foreignKey = @ForeignKey(name = "CAR_PHOTO_ID_FK"))
    private CarPhoto photo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transmission transmission;

    @SuppressWarnings("checkstyle:ParameterNumber")
    public static Car of(String name, int mileage, int yearOfIssue, Engine engine, CarModel model,
                         CarPhoto photo, BodyType bodyType, Transmission transmission) {
        Car car = new Car();
        car.name = name;
        car.mileage = mileage;
        car.yearOfIssue = yearOfIssue;
        car.engine = engine;
        car.carModel = model;
        car.photo = photo;
        car.bodyType = bodyType;
        car.transmission = transmission;
        return car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public CarPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(CarPhoto photo) {
        this.photo = photo;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return this.id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String photoId = photo == null ? "No photo" : String.valueOf(photo.getId());
        return String.format(
                "Car{id=%d, %n name='%s', %n mileage=%s, %n year of issue=%s"
                        + "%n engine=%s, %n carModel=%s, "
                        + "%n photoId=%s, %n bodyType=%s, "
                        + "%n transmission=%s}",
                id, name, mileage, engine, yearOfIssue, carModel, photoId, bodyType, transmission);
    }
}