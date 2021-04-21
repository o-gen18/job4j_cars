package cars.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name ="body_type")
public class Body {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "car_body_type", joinColumns = {
            @JoinColumn(name = "car_id", nullable = false, updatable = false)},
            inverseJoinColumns = { @JoinColumn(name = "body_type_id", nullable = false, updatable = false)})
    private Set<Car> cars = new HashSet<>();

    public Body(){}

    public static Body of(String name) {
        Body body = new Body();
        body.name = name;
        return body;
    }

    public void addCar(Car car) {
        cars.add(car);
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

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body body = (Body) o;
        return this.id == body.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Body{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
