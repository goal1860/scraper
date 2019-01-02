package playartifact;

import buff.Card;
import buff.Creep;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayArtifactScraper {
    public static void main(String[] args) throws IOException {

        String[] setCodes = {"00", "01"};




        Map<String, Card> cardMap = new HashMap<>();
        List<String[]> lineList = new ArrayList<>();
//        for (CardObject cardObject : cardSetDetailResponse.getCard_set().getCard_list()) {
//            System.out.println(cardObject.getRarity());
//            System.out.println(cardObject.getCard_type());
//            Card card = composeCard(cardObject);
//            cardMap.put(card.getTitle(), card);
//        }
    }

    private static Card composeCard(CardObject cardObject) {
        Card card;
        if (cardObject.getCard_name().toString().equals("Melee Creep")){
            return null;
        }
        if (cardObject.getCard_name().toString().equals("Zombie")){
            return null;
        }
        switch (cardObject.getCard_type().toLowerCase()){
            case "stronghold":
                return null;
            case "pathing":
                return null;
            case "creep":
                String title = cardObject.getCard_name().getEnglish();
                String rarity = cardObject.getRarity() == null? "Basic" : cardObject.getRarity();
                String mana = String.valueOf(cardObject.getMana_cost());
                String attack = String.valueOf(cardObject.getAttack());
                String armor = String.valueOf(cardObject.getArmor());
                String health = String.valueOf(cardObject.getHit_point());
            case "passive ability":
//                int heroId = cardObject.references
//                card = new Creep();
        }
        return null;
    }
}
