package fixes;

import dataloader.DbLoader;
import entities.Chdg4ArtifactgameCard;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Remap the deck_card table by using new id (id in artifactgame_card, not jid.)
 */
public class DeckCardCheck {
    public static void main(String[] args) {
        Map<Integer, String> deckCardMap = DbLoader.getDeckCardList();
        List<Chdg4ArtifactgameCard> carList = DbLoader.getCardList();
        for (int deckCardId : deckCardMap.keySet()) {
            String title = deckCardMap.get(deckCardId);
            List<Chdg4ArtifactgameCard> searchResult = carList.stream().filter(c -> c.getName().equals(title)).collect(
                            Collectors.toList());
            if(searchResult.isEmpty()){
                System.out.println("Card is not found: " + title);
                continue;
            }
            int newId = searchResult.get(0).getId();
            DbLoader.updateDeckCardMap(deckCardId, newId);
        }

    }
}
