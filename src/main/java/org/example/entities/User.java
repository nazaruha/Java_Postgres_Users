package org.example.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="tbl_users")
@Data // Підключає дефолтні методи: setId, getId, setEmail, getEmail etc.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // це означає, що буде авто інкремент в базі даних
    @Column(name="id")
    private int id;
    @Column(name="email", unique = true, nullable = false, length = 200)
    private String email;
    @Column(name="first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name="last_name", nullable = false, length = 100)
    private String lastName;
    @Column(name="phone", nullable = false, length = 20)
    private String phone;
    @Column(name="password", nullable = false, length = 150)
    private String password;
}
