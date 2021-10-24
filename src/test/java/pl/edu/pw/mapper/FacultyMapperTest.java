package pl.edu.pw.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FacultyMapperTest {

    // equals dziwnie działa i dlatego nie przechodzi, dla equals wygenerowanego przez IntelliJ przechodzi normalnie
    @Test
    void facultyDto_to_faculty_success() {
        String name = "Wydział Architektury";
        String address = "Kwiatowa 3";
        String contactEmail = "arch@pw.edu.pl";
        FacultyDto facultyDto = new FacultyDto(name, address, contactEmail);

        Faculty expected = new Faculty(name, address, contactEmail);
        Faculty actual = FacultyMapper.map(facultyDto);
        assertEquals(expected, actual);
    }

    @Test
    void faculty_to_facultyDto_success() {
        String name = "Wydział Ogrodnictwa";
        String address = "Majora 82";
        String contactEmail = "major@pw.edu.pl";
        Faculty faculty = new Faculty(name, address, contactEmail);

        FacultyDto expected = new FacultyDto(name, address, contactEmail);
        FacultyDto actual = FacultyMapper.map(faculty);
        assertEquals(expected, actual);
    }
}
