package pl.edu.pw.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.edu.pw.dto.FacultyDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FacultyServiceTest {

    @Autowired
    FacultyService service;

    @Test
    void findByName_noFacultiesInTable_throwNotFoundException() {
        String name = "Wydział Elektryczny";

        assertThrows(NotFoundException.class,
                () -> service.findByName(name));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void findByName_noFacultiesMatching_throwNotFoundException() {
        String name = "Wydział Elektroniki i Nauk Informacyjnych";

        assertThrows(NotFoundException.class,
                () -> service.findByName(name));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void findByName_success() throws NotFoundException {
        String name = "Wydział Inżynierii Lądowej";
        FacultyDto expected = new FacultyDto(
                "Wydział Inżynierii Lądowej",
                "Politechniczna 19",
                "le.contact@pw.edu.pl"
        );

        FacultyDto actual = service.findByName(name);

        assertEquals(expected, actual);
    }

}
