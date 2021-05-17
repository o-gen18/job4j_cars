package cars.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_model", uniqueConstraints =
                {@UniqueConstraint(columnNames = {"cardrive", "steeringwheel", "name"})})
public class CarModel {
    public enum CarDrive {
        FRONT_WHEEL_DRIVE("Front-wheel drive"),
        REAR_WHEEL_DRIVE("Rear-wheel drive"),
        ALL_WHEEL_DRIVE("All-wheel drive");

        private final String carDrive;

        CarDrive(String carDrive) {
            this.carDrive = carDrive;
        }

        /**
         * Utility method for both Jackson and AttributeConverter
         * of Hibernate to allow them convert the string representation of CarDrive into
         * enum.
         * @param carDrive the car drive of the car.
         * @return CarDrive's enum representation.
         */
        @JsonCreator
        public static CarDrive fromString(String carDrive) {
            return switch (carDrive) {
                case "Front-wheel drive" -> FRONT_WHEEL_DRIVE;
                case "Rear-wheel drive" -> REAR_WHEEL_DRIVE;
                case "All-wheel drive" -> ALL_WHEEL_DRIVE;
                default -> throw new UnsupportedOperationException(
                        "The car drive " + carDrive + " is not supported!"
                );
            };
        }

        /**
         * Utility method for both Jackson and AttributeConverter
         * of Hibernate to allow them convert the enum representation of CarDrive into
         * the string.
         * @return CarDrive's String representation.
         */
        @JsonValue
        public String getCarDrive() {
            return carDrive;
        }

        /**
         * Converts enum into string when saving into the database
         * and visa versa when retrieving.
         */
        public static class CarDriveConverter
                implements AttributeConverter<CarDrive, String> {

            @Override
            public String convertToDatabaseColumn(CarDrive carDrive) {
                if (carDrive == null) {
                    return null;
                }
                return carDrive.getCarDrive();
            }

            @Override
            public CarDrive convertToEntityAttribute(String s) {
                if (s == null) {
                    return null;
                }
                return CarDrive.fromString(s);
            }
        }
    }

    public enum SteeringWheel {
        LEFT_HAND_DRIVE("Left-hand drive"), RIGHT_HAND_DRIVE("Right-hand drive");

        private final String steeringWheel;

        SteeringWheel(String steeringWheel) {
            this.steeringWheel = steeringWheel;
        }

        @JsonCreator
        public static SteeringWheel fromString(String steeringWheel) {
            return switch (steeringWheel) {
                case "Left-hand drive" -> LEFT_HAND_DRIVE;
                case "Right-hand drive" -> RIGHT_HAND_DRIVE;
                default -> throw new UnsupportedOperationException(
                        "The car drive " + steeringWheel + " is not supported!"
                );
            };
        }

        @JsonValue
        public String getSteeringWheel() {
            return steeringWheel;
        }

        /**
         * Works same as CarDriveConverter.
         */
        public static class SteeringWheelConverter
                implements AttributeConverter<SteeringWheel, String> {

            @Override
            public String convertToDatabaseColumn(SteeringWheel steeringWheel) {
                if (steeringWheel == null) {
                    return null;
                }
                return steeringWheel.getSteeringWheel();
            }

            @Override
            public SteeringWheel convertToEntityAttribute(String s) {
                if (s == null) {
                    return null;
                }
                return SteeringWheel.fromString(s);
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = CarDrive.CarDriveConverter.class)
    @Column(nullable = false)
    private CarDrive carDrive;

    @Convert(converter = SteeringWheel.SteeringWheelConverter.class)
    @Column(nullable = false)
    private SteeringWheel steeringWheel;

    public static CarModel of(String name, CarDrive carDrive, SteeringWheel steeringWheel) {
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
        return this.id == model.id
                && name.equals(model.name)
                && carDrive == model.carDrive
                && steeringWheel == model.steeringWheel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, carDrive, steeringWheel);
    }

    @Override
    public String toString() {
        return String.format(
                "CarModel{id=%d, name='%s', "
                        + "carDrive=%s, steeringWheel=%s}",
                id, name, carDrive, steeringWheel);
    }
}
