package cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car")
public class Car {
    public enum BodyType {
        SEDAN, COUPE, SPORTS_CAR, STATION_WAGON,
        HATCHBACK, CONVERTIBLE, MINIVAN, PICKUP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK"))
    private Engine engine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id", foreignKey = @ForeignKey(name = "CAR_MODEL_ID_FK"))
    private CarModel carModel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_photo_id", foreignKey = @ForeignKey(name = "CAR_PHOTO_ID_FK"))
    private CarPhoto photo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyType bodyType;

    public static Car of(String name, Engine engine, CarModel model, CarPhoto photo, BodyType bodyType) {
        Car car = new Car();
        car.name = name;
        car.engine = engine;
        car.carModel = model;
        car.photo = photo;
        car.bodyType = bodyType;
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
                "Car{id=%d, %n name='%s', "
                        + "%n engine=%s, %n carModel=%s, "
                        + "%n photoId=%s, %n bodyType=%s}",
                id, name, engine, carModel, photoId, bodyType);
    }
}