package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Clue;
import uk.co.emg.entity.ClueComponent;
import uk.co.emg.entity.Emoji;
import uk.co.emg.entity.Mutation;
import uk.co.emg.enumeration.MutationType;

import java.util.*;

@Service
public class MutationService {

    private final EmojiService emojiService;

    public MutationService(EmojiService emojiService) {
        this.emojiService = emojiService;
    }

    public void mutate(Clue child, List<ClueComponent> clueComponents) {
        Random random = new Random();
        MutationType mutationType = Arrays.asList(MutationType.values()).get(random.nextInt(MutationType.values().length));
        child.setMutation(new Mutation(child, mutationType));
        switch (mutationType) {
            case POSITION_CHANGE:
                Collections.shuffle(clueComponents);
                break;
            case GROUP_CHANGE:
                ClueComponent groupMutationComponent = clueComponents.get(random.nextInt(clueComponents.size()));
                String emojiGroup = groupMutationComponent.getEmoji().getEmojiGroup();
                List<Emoji> emojiInSameGroup = emojiService.findAllByEmojiGroup(emojiGroup);
                groupMutationComponent.setEmoji(emojiInSameGroup.get(random.nextInt(emojiInSameGroup.size())));
                break;
            case SUB_GROUP_CHANGE:
                ClueComponent subGroupMutationComponent = clueComponents.get(random.nextInt(clueComponents.size()));
                String emojiSubGroup = subGroupMutationComponent.getEmoji().getSubGroup();
                List<Emoji> emojiInSameSubGroup = emojiService.findAllBySubGroup(emojiSubGroup);
                subGroupMutationComponent.setEmoji(emojiInSameSubGroup.get(random.nextInt(emojiInSameSubGroup.size())));
                break;
            case RANDOM_CHANGE:
                ClueComponent randomChangeMutationComponent = clueComponents.get(random.nextInt(clueComponents.size()));
                randomChangeMutationComponent.setEmoji(emojiService.getRandomEmoji());
                break;
            case RANDOM_ADDITION:
                clueComponents.add(new ClueComponent(child, emojiService.getRandomEmoji()));
        }
    }

}
