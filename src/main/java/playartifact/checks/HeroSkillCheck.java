package playartifact.checks;

import dataloader.DbLoader;
import playartifact.CardObject;
import playartifact.FeedReader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class HeroSkillCheck {


    public static void main(String[] args) throws IOException {
        List<CardObject> cardObjectList = FeedReader.loadCardInfo("01");
        DbLoader.openConnection();
        Set<Integer> skillSet = DbLoader.getHeroSkillSet();
        for (CardObject cardObject : cardObjectList) {
            String type = cardObject.getCard_type();
//            System.out.println(type);
            if(type.contains("Ability")) {
                int id = cardObject.getCard_id();

                if(!skillSet.contains(id)) {

                    String name = cardObject.getCard_name().getEnglish();
                    System.out.println("Inserting " + name);
                    String text = cardObject.getCard_text().getEnglish();
                    DbLoader.saveHeroSkill(id, type, name, text);
                }
            }
        }
        DbLoader.closeConnection();
    }

}
