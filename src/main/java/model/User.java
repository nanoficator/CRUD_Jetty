package model;

import javax.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "second_name")
    private String secondName;

    @Column (name = "user_name")
    private String userName;

    @Column
    private Long age;

    @Column
    private String gender;

    public User(){
    }

    public User(Long id, String firstName, String secondName, String userName, Long age, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.id = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}