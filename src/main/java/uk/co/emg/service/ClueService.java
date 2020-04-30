package uk.co.emg.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import uk.co.emg.entity.*;
import uk.co.emg.enumeration.MutationType;
import uk.co.emg.repository.ClueRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClueService {
    private final EmojiService emojiService;
    private final ClueComponentService clueComponentService;
    private final GuessService guessService;
    private final ClueRepository clueRepository;
    private final MutationService mutationService;

    public ClueService(EmojiService emojiService, ClueComponentService clueComponentService, GuessService guessService, MutationService mutationService, ClueRepository clueRepository) {
        this.emojiService = emojiService;
        this.mutationService = mutationService;
        this.clueRepository = clueRepository;
        this.clueComponentService = clueComponentService;
        this.guessService = guessService;
    }

    public void createClue(Film film) {
        Clue clue = new Clue(film);
        List<Emoji> emojis = emojiService.getEmojiBasedOnFilm(film);
        ArrayList<ClueComponent> clueComponents = new ArrayList<>(emojis.size());
        for (Emoji emoji : emojis) {
            clueComponents.add(new ClueComponent(clue, emoji));
        }
        clue.setClueComponents(clueComponents);
        clueRepository.save(clue);
        clueComponentService.saveAll(clueComponents);
    }

    public Optional<Clue> getClue(Long clueId) {
        return clueRepository.findById(clueId);
    }

    public Clue getClue(List<Clue> alreadySeenClues) {
        Random random = new Random();
        List<Clue> currentGenerationClues = clueRepository.findAllByFitnessIsNull();
        List<Clue> cluesNotYetSeen = currentGenerationClues.stream()
                .filter(clue -> !alreadySeenClues.contains(clue))
                .collect(Collectors.toList());
        if (cluesNotYetSeen.isEmpty()) {
            return currentGenerationClues.get(random.nextInt(currentGenerationClues.size()));
        }
        return cluesNotYetSeen.get(random.nextInt(cluesNotYetSeen.size()));
    }

    public double calculateFitness(Clue clue) {
        return guessService.getGuesses(clue).stream().collect(Collectors.averagingDouble(guess -> guess.isCorrect() ? 1 : 0));
    }

    public Clue breed(Clue mother, Clue father) {
        Clue child = new Clue(mother.getFilm(), mother.getGeneration() + 1);
        child.setMother(mother);
        child.setFather(father);
        ArrayList<ClueComponent> clueComponents = new ArrayList<>();
        clueComponents.addAll(mother.getClueComponents().subList(0, mother.getClueComponents().size() / 2));
        clueComponents.addAll(father.getClueComponents().subList(father.getClueComponents().size() / 2, father.getClueComponents().size()));
        if (clueComponents.size() == 0) {
            clueComponents.addAll(mother.getClueComponents());
            clueComponents.addAll(father.getClueComponents());
        }
        for (int i = 0; i < clueComponents.size(); i++) {
            clueComponents.set(i, new ClueComponent(child, clueComponents.get(i).getEmoji()));
        }
        Random random = new Random();
        if (random.nextInt(100) < 200) {
            mutate(child, clueComponents);
        }
        child.setClueComponents(clueComponents);
        child = save(child);
        return child;
    }

    private void mutate(Clue child, ArrayList<ClueComponent> clueComponents) {
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
                String emojiSubGroup = subGroupMutationComponent.getEmoji().getEmojiGroup();
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

    public Clue save(Clue clue) {
        clue = clueRepository.save(clue);
        return clue;
    }

    public List<Clue> getAllClues(Film film) {
        return clueRepository.findAllByFilm(film);
    }

    @SuppressWarnings("unchecked")
    public JSONObject createClueFamilyTree(Clue clue) {
        if (clue.getMother() == null && clue.getFather() == null) {
            JSONObject text = new JSONObject();
            JSONObject name = new JSONObject();
            name.put("name", clue.getClueComponents().toString());
            text.put("text", name);
            return text;
        }
        if (clue.getMother() == null) {
            JSONObject name = new JSONObject();
            name.put("name", clue.getClueComponents().toString());

            JSONArray childArray = new JSONArray();
            childArray.add(createClueFamilyTree(clue.getFather()));

            JSONObject node = new JSONObject();
            node.put("children", childArray);
            node.put("text", name);
            return node;
        }
        if (clue.getFather() == null) {
            JSONObject name = new JSONObject();
            name.put("name", clue.getClueComponents().toString());

            JSONArray childArray = new JSONArray();
            childArray.add(createClueFamilyTree(clue.getMother()));

            JSONObject node = new JSONObject();
            node.put("children", childArray);
            node.put("text", name);
            return node;
        }
        JSONObject text = new JSONObject();
        text.put("name", clue.getClueComponents().toString());

        JSONArray childArray = new JSONArray();
        childArray.add(createClueFamilyTree(clue.getMother()));
        childArray.add(createClueFamilyTree(clue.getFather()));

        JSONObject node = new JSONObject();
        node.put("children", childArray);
        node.put("text", text);
        return node;
    }

}
