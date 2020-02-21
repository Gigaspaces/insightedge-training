package model;

import com.gigaspaces.analytics_xtreme.internal.TimeAdapter;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@SpaceClass
public class Person {
    private Integer id;
    private Timestamp joinDate;
    private String name;
    private String country;
    private String city;
    private Integer age;
    private Integer salary;

    public Person() {
    }

    public Person(Integer id, Timestamp joinDate, String name, String country, String city, Integer age, Integer salary) {
        this.id = id;
        this.joinDate = joinDate;
        this.name = name;
        this.country = country;
        this.city = city;
        this.age = age;
        this.salary = salary;
    }

    public static Person[] generate(int startId, int count, Duration startBefore) {
        Instant now = Clock.systemUTC().instant();
        String[] names = new String[]{"Yael", "Alon", "Niv", "Ayelet", "Meshi"};
        String[] countries = new String[]{"France", "England", "Israel"};
        String[] cities = new String[]{"Paris", "London", "Tel Aviv", "Marseille", "Brighton", "Jerusalem"};
        TimeAdapter<Timestamp> timeProvider = (TimeAdapter<Timestamp>) TimeAdapter.of(Timestamp.class);
        Person[] res = new Person[count];
        int index = 0;
        for (int i = startId; i < startId + count; i++) {
            Timestamp time = timeProvider.fromInstant(now.minus(startBefore).plus(index, ChronoUnit.SECONDS));
            res[index++] = new Person(i, time, names[index % 5], countries[index % 3], cities[index % 6], 20 + i, 5000 + (index * new Random().nextInt(5000)));
        }
        return res;
    }

    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", joinDate=" + joinDate +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}

