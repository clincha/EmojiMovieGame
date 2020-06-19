package uk.co.emg.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.enumeration.MutationType;
import uk.co.emg.utils.ClueUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class MutationServiceTest {

    private final EmojiService emojiService = Mockito.mock(EmojiService.class);
    private final MutationService mutationService;
    private final Emoji emoji = new Emoji("mutationSlug", "mutationCharacter", "mutationUnicodeName", "mutationCodePoint", "mutationEmojiGroup", "mutationSubGroup");
    private Clue clue;

    public MutationServiceTest() {
        mutationService = new MutationService(emojiService);
    }

    @BeforeEach
    void setUp() {
        clue = ClueUtils.getClue();
    }

    @Test
    void mutatePositionChange() {
        Clue mutated = mutationService.mutate(clue, MutationType.POSITION_CHANGE);

        assertEquals(mutated.getClueComponents().size(), ClueUtils.getClue().getClueComponents().size());
        for (int i = 0; i < ClueUtils.getClue().getClueComponents().size(); i++) {
            assertNotEquals(ClueUtils.getClue().getClueComponents().get(i), mutated.getClueComponents().get(i));
        }
    }

    @Test
    void mutateGroupChange() {
        when(emojiService.findAllByEmojiGroup(Mockito.anyString())).thenReturn(List.of(emoji));
        Clue mutated = mutationService.mutate(clue, MutationType.GROUP_CHANGE);

        assertEquals(ClueUtils.getClue().getClueComponents().size(), mutated.getClueComponents().size());
        assertTrue(mutated.getClueComponents().stream().map(ClueComponent::getEmoji).anyMatch(emoji1 -> emoji1.equals(emoji)));
    }

    @Test
    void mutateSubGroupChange() {
        when(emojiService.findAllBySubGroup(Mockito.anyString())).thenReturn(List.of(emoji));
        Clue mutated = mutationService.mutate(clue, MutationType.SUB_GROUP_CHANGE);

        assertEquals(ClueUtils.getClue().getClueComponents().size(), mutated.getClueComponents().size());
        assertTrue(mutated.getClueComponents().stream().map(ClueComponent::getEmoji).anyMatch(emoji1 -> emoji1.equals(emoji)));
    }

    @Test
    void mutateRandomChange() {
        when(emojiService.getRandomEmoji()).thenReturn(emoji);
        Clue mutated = mutationService.mutate(clue, MutationType.RANDOM_CHANGE);

        assertEquals(ClueUtils.getClue().getClueComponents().size(), mutated.getClueComponents().size());
        assertTrue(mutated.getClueComponents().stream().map(ClueComponent::getEmoji).anyMatch(emoji1 -> emoji1.equals(emoji)));
    }

    @Test
    void mutateRandomAddition() {
        when(emojiService.getRandomEmoji()).thenReturn(emoji);
        Clue mutated = mutationService.mutate(clue, MutationType.RANDOM_ADDITION);

        assertEquals(ClueUtils.getClue().getClueComponents().size() + 1, mutated.getClueComponents().size());
        assertTrue(mutated.getClueComponents().stream().map(ClueComponent::getEmoji).anyMatch(emoji1 -> emoji1.equals(emoji)));

    }
}