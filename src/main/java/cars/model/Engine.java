package cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "engines", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"fueltype", "volume"})})
public class Engine {
    public enum FuelType {
        Gasoline, Diesel, Hybrid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(nullable = false)
    private double volume;

    public static Engine of(FuelType fuelType, double volume) {
        Engine engine = new Engine();
        engine.fuelType = fuelType;
        engine.volume = volume;
        return engine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FuelType getType() {
        return fuelType;
    }

    public void setType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Engine engine = (Engine) o;
        return id == engine.id
                && Double.compare(engine.volume, volume) == 0
                && fuelType == engine.fuelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fuelType, volume);
    }

    @Override
    public String toString() {
        return String.format("Engine{id=%d, fuelType=%s, volume=%s}", id, fuelType, volume);
    }
}
