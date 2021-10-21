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
