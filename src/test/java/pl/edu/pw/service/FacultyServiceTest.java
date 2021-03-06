package pl.edu.pw.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.edu.pw.dto.FacultyDto;

import java.sql.SQLException;

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


    @Test
    void add_emptyObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> service.add(null));
    }

    @Test
    void add_nullName_throwsDataIntegrityViolationException() {
        FacultyDto faculty = new FacultyDto(null, "address", "email@gmail.com");
        assertThrows(DataIntegrityViolationException.class,
                () -> service.add(faculty));
    }

    @Test
    void add_nullAddress_throwsDataIntegrityViolationException() {
        FacultyDto faculty = new FacultyDto("faculty", null, "email@gmail.com");
        assertThrows(DataIntegrityViolationException.class,
                () -> service.add(faculty));
    }

    @Test
    void add_nullEmail_throwsDataIntegrityViolationException() {
        FacultyDto faculty = new FacultyDto("faculty", "address", null);
        assertThrows(DataIntegrityViolationException.class,
                () -> service.add(faculty));
    }

    @Test
    void add_success() throws SQLException, NotFoundException {
        FacultyDto faculty = new FacultyDto("faculty", "address", "email@gmail.com");

        service.add(faculty);
        FacultyDto returned = service.findByName(faculty.getName());

        assertEquals(faculty, returned);
    }


    @Test
    @Sql({"/supply-many.sql"})
    void delete_incorrectName_throwsNotFoundException() {
        String name = "wrongName";
        assertThrows(NotFoundException.class,
                () -> service.deleteByName(name));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void delete_success() throws NotFoundException {
        String name = "Wydział Elektryczny";

        service.deleteByName(name);

        assertThrows(NotFoundException.class,
                () -> service.findByName(name));
    }

    @Test
    void delete_nullName_throwsNotFoundException() {
        assertThrows(NotFoundException.class,
                () -> service.deleteByName(null));
    }


    @Test
    void update_emptyDataBase_throwsNotFoundException() {
        String name = "Wydział Matematyki i Nauk Informacyjnych";
        FacultyDto updated = new FacultyDto(
                "Elektrotechniki i Technik Informacyjnych",
                "Starzyńskiego 10",
                "example@gmail.com"
        );

        assertThrows(NotFoundException.class,
                () -> service.updateByName(name, updated));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void update_wrongName_throwsNotFoundException() {
        String name = "wrong name";
        FacultyDto updated = new FacultyDto(
                "doesnt matter",
                "Chmielna 5",
                "example@example.com"
        );

        assertThrows(NotFoundException.class,
                () -> service.updateByName(name, updated));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void update_success() throws NotFoundException {
        String name = "Wydział Inżynierii Lądowej";
        FacultyDto expected = new FacultyDto(
                "Wydział Inżynierii Lądowej",
                "inna ulica",
                "dziekanat@pw.edu.pl"
        );

        service.updateByName(name, expected);
        FacultyDto actual = service.findByName(name);

        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/supply-many.sql"})
    void update_nullName_throwsDataIntegrityViolationException() {
        String name = "Wydział Elektryczny";
        FacultyDto updatedFaculty = new FacultyDto(
                null,
                "Chełmońskiego 1",
                "super-wydzial@pw.edu.pl"
        );

        assertThrows(DataIntegrityViolationException.class,
                () -> service.updateByName(name, updatedFaculty));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void update_nullAddress_throwsDataIntegrityViolationException() {
        String name = "Wydział Matematyki i Nauk Informacyjnych";
        FacultyDto updatedFaculty = new FacultyDto(
                "Wydział Inżynierii Lądowej",
                null,
                "dziekanat@pw.edu.pl"
        );

        assertThrows(DataIntegrityViolationException.class,
                () -> service.updateByName(name, updatedFaculty));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void update_nullContactEmail_throwsDataIntegrityViolationException() {
        String name = "Wydział Inżynierii Lądowej";
        FacultyDto updatedFaculty = new FacultyDto(
                "Wydział Chemiczny",
                null,
                "chemiczny@pw.edu.pl"
        );

        assertThrows(DataIntegrityViolationException.class,
                () -> service.updateByName(name, updatedFaculty));
    }

    @Test
    @Sql({"/supply-many.sql"})
    void update_name_already_in_database_throwsDataIntegrityViolationException() {
        String name = "Wydział Inżynierii Lądowej";
        FacultyDto updatedFaculty = new FacultyDto(
                "Wydział Elektryczny",
                "Noakowskiego 20",
                "ee@pw.edu.pl"
        );
        assertThrows(DataIntegrityViolationException.class,
                () -> service.updateByName(name, updatedFaculty));
    }
}
