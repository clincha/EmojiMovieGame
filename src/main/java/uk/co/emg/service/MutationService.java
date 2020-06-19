package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Mutation;
import uk.co.emg.enumeration.MutationType;

import java.util.List;
import java.util.Random;

@Service
public class MutationService {

    private final EmojiService emojiService;

    public MutationService(EmojiService emojiService) {
        this.emojiService = emojiService;
    }

    public Clue mutate(Clue child, MutationType mutationType) {
        Random random = new Random();
        child.setMutation(new Mutation(child, mutationType));
        switch (mutationType) {
            case POSITION_CHANGE:
                ClueComponent clueComponent = child.getClueComponents().remove(child.getClueComponents().size() - 1);
                child.getClueComponents().add(0, clueComponent);
                break;
            case GROUP_CHANGE:
                ClueComponent groupMutationComponent = child.getClueComponents().get(random.nextInt(child.getClueComponents().size()));
                String emojiGroup = groupMutationComponent.getEmoji().getEmojiGroup();
                List<Emoji> emojiInSameGroup = emojiService.findAllByEmojiGroup(emojiGroup);
                groupMutationComponent.setEmoji(emojiInSameGroup.get(random.nextInt(emojiInSameGroup.size())));
                break;
            case SUB_GROUP_CHANGE:
                ClueComponent subGroupMutationComponent = child.getClueComponents().get(random.nextInt(child.getClueComponents().size()));
                String emojiSubGroup = subGroupMutationComponent.getEmoji().getSubGroup();
                List<Emoji> emojiInSameSubGroup = emojiService.findAllBySubGroup(emojiSubGroup);
                subGroupMutationComponent.setEmoji(emojiInSameSubGroup.get(random.nextInt(emojiInSameSubGroup.size())));
                break;
            case RANDOM_CHANGE:
                ClueComponent randomChangeMutationComponent = child.getClueComponents().get(random.nextInt(child.getClueComponents().size()));
                randomChangeMutationComponent.setEmoji(emojiService.getRandomEmoji());
                break;
            case RANDOM_ADDITION:
                child.getClueComponents().add(new ClueComponent(child, emojiService.getRandomEmoji()));
        }
        return child;
    }

}
