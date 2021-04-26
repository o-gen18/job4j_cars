package cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_model")
public class CarModel {
    public enum CarDrive {
        FRONT_WHEEL_DRIVE, REAR_WHEEL_DRIVE, ALL_WHEEL_DRIVE
    }

    public enum SteeringWheel {
        LEFT_HAND_DRIVE, RIGHT_HAND_DRIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarDrive carDrive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SteeringWheel steeringWheel;

    public static CarModel of(String name, CarDrive carDrive, SteeringWheel steeringWheel, Engine engine) {
        CarModel model = new CarModel();
        model.name = name;
        model.carDrive = carDrive;
        model.steeringWheel = steeringWheel;
        return model;
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

    public CarDrive getCarDrive() {
        return carDrive;
    }

    public void setCarDrive(CarDrive carDrive) {
        this.carDrive = carDrive;
    }

    public SteeringWheel getSteeringWheel() {
        return steeringWheel;
    }

    public void setSteeringWheel(SteeringWheel steeringWheel) {
        this.steeringWheel = steeringWheel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel model = (CarModel) o;
        return this.id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "CarModel{id=%d, name='%s', "
                        + "carDrive=%s, steeringWheel=%s}",
                id, name, carDrive, steeringWheel);
    }
}
