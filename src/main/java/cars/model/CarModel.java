package cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "CAR_ID_FK"))
    private Car car;

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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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
        return "CarModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
