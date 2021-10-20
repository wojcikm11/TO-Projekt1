package pl.edu.pw.entity;

import javax.persistence.*;

@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 256, nullable = false, unique = true)
    private String name;

    @Column(length = 128, nullable = false)
    private String address;

    @Column(length = 64, nullable = false)
    private String contactEmail;

    public Faculty(String name, String address, String contactEmail) {
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
    }

    public Faculty() {

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

}
