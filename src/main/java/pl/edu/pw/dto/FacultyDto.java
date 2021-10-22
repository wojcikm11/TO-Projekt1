package pl.edu.pw.dto;

import javax.validation.constraints.Size;

public class FacultyDto {

    @Size(min = 12, max = 256)
    private String name;

    @Size(min = 4, max = 128)
    private String address;

    @Size(min = 10, max = 64)
    private String contactEmail;

    public FacultyDto() {
    }

    public FacultyDto(String name, String address, String contactEmail) {
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
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

    private boolean stringsEqual(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
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
