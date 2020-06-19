package uk.co.emg.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.emg.entity.Film;
import uk.co.emg.repository.FilmRepository;
import uk.co.emg.service.FilmService;

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
        assertEquals(filmRepository.count(), FilmService.FILM_GENERATION_SIZE);
    }

    @Test
    @Transactional
    public void cluesCountCorrectForEachFilmAfterPreload() {
        ArrayList<Film> films = (ArrayList<Film>) filmRepository.findAll();
        for (Film film : films) {
            assertEquals(film.getClues().size(), FilmService.INITIAL_CLUE_GENERATION_SIZE);
        }
    }

    @Test
    public void ensureThereIsOnlyOneGenerationAfterPreload() {
        ArrayList<Film> films = (ArrayList<Film>) filmRepository.findAll();
        Integer generation = films.get(0).getGeneration();
        for (Film film : films) {
            assertEquals(film.getGeneration(), generation);
        }
    }

}

