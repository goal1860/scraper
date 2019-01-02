package checks;

import dataloader.DbLoader;
import entities.Chdg4ArtifactgameCard;

import java.util.List;
import java.util.stream.Collectors;

public class SignatureStatCheck {
    public static void main(String[] args) {
        DbLoader.openConnection();
        List<Chdg4ArtifactgameCard> cardList =  DbLoader.getCardList();
        for (Chdg4ArtifactgameCard card : cardList) {
            if(!card.getType().equals("Hero")) continue;
            if(card.getSigId() == 0 || card.getSigMana()==0) {

                System.out.println("Card signature stat is incorrect: " + card.getName() + " Correcting...");
                String sigName = card.getSigCard();
                List<Chdg4ArtifactgameCard> searchResult =
                                cardList.stream().filter(c -> c.getName().equals(sigName)).collect(Collectors.toList());
                if(searchResult.isEmpty()) {
                    System.out.println("Failed to find sig card of hero " + card.getName() + ": " + sigName);
                    continue;
                }
                Chdg4ArtifactgameCard sigCard = searchResult.get(0);

                DbLoader.updateSigInfo(card.getId(), sigCard.getJid(), sigCard.getMana());
            }
            if(card.getSigCardId() == 0){
                System.out.println("Card signature reference is missing: " + card.getName() + " Correcting...");
                String sigName = card.getSigCard();
                List<Chdg4ArtifactgameCard> searchResult =
                                cardList.stream().filter(c -> c.getName().equals(sigName)).collect(Collectors.toList());
                if(searchResult.isEmpty()) {
                    System.out.println("Failed to find sig card of hero " + card.getName() + ": " + sigName);
                    continue;
                }
                Chdg4ArtifactgameCard sigCard = searchResult.get(0);

                DbLoader.updateSigCardId(card.getId(), sigCard.getId());
            }
        }
        DbLoader.closeConnection();
    }
}
