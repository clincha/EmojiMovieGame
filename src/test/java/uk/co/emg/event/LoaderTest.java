package uk.co.emg.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.FilmRepository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LoaderTest {

    @Resource
    private FilmRepository filmRepository;

    @Test
    public void filmCountCorrectAfterPreload() {
        assertEquals(filmRepository.count(), 20);
    }

    @Test
    @Transactional
    public void cluesCountCorrectForEachFilmAfterPreload() {
        ArrayList<Film> films = (ArrayList<Film>) filmRepository.findAll();
        for (Film film : films) {
            assertEquals(film.getClues().size(), 10);
        }
    }



}
