package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SessionService {


    public List<Clue> getUsedClues(HttpSession session) {
        return null;
    }
}
