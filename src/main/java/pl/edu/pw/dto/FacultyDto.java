package pl.edu.pw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
public class FacultyDto {

    @Size(min = 12, max = 256)
    private String name;

    @Size(min = 4, max = 128)
    private String address;

    @Size(min = 10, max = 64)
    private String contactEmail;

}
