package pl.edu.pw.entity;

import pl.edu.pw.dto.FacultyDto;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FacultyDto)) {
            return false;
        }
        FacultyDto other = (FacultyDto) obj;
        return stringsEqual(this.name, other.getName()) &&
                stringsEqual(this.address, other.getAddress()) &&
                stringsEqual(this.contactEmail, other.getContactEmail());
    }

    // do poprawienia - wydzielić metodę w jakieś sensowne miejsce
    private boolean stringsEqual(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
    }
}
