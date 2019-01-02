package checks;

import dataloader.DbLoader;
import entities.Chdg4ArtifactgameCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MetaDataCheck {
    // Always generate metadata. Otherwise only generate when it is empty.
    private static final boolean ALWAYS_GENERATE = false;
    public static void main(String[] args) {
        DbLoader.openConnection();
        String where = null;
        if(!ALWAYS_GENERATE){
            where = "metadesc='' or metakey=''";
        }
        List<Chdg4ArtifactgameCard> cardList = DbLoader.getCardList(where);

        for (Chdg4ArtifactgameCard card : cardList) {
            int jid = card.getJid();
            String name = card.getName();
            if (card.getType().equals("Hero")) {
                String color = card.getColor();
                int attack = card.getAttack();
                int armor = card.getArmor();
                int health = card.getHealth();
                String rarity = card.getRarity();

                String descTmpl = "%s is a %s %s hero in Valve Dota Artifact Game. Stats: %d/%d/%d. Signature spell: %s.";
                String description = String.format(descTmpl, name, color, rarity, attack, armor, health, card.getSigCard());
                if(!card.getSkillString().isEmpty()){
                    description += " Skills: " + card.getSkillString();
                }
                String[] keywords = {"valve", "artifact", "dota", "hero", "card", name, color, rarity};
                String keywordString = String.join(",", keywords);
                if(!card.getSkillString().isEmpty()){
                    keywordString = keywordString + "," + card.getSkillString();
                }
                System.out.println("Check card: " + name);
                System.out.println(description);
                System.out.println(keywordString);
                try {
                    DbLoader.updateMetadata(jid, description, keywordString);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (card.getType().equals("Spell")) {
                String descTmpl = "%s is a %s %s %s spell in Valve Dota Artifact Game. Mana cost %d.";
                String color = card.getColor();
                String rarity = card.getRarity();
                String description = String.format(descTmpl, name, color, rarity, card.getSubtype(), card.getMana());
                if(!card.getSigOf().isEmpty()){
                    description = description + " It is the signature spell of hero " + card.getSigOf() + ".";
                }
                description =description + " " + card.getEffect();
                String[] keywords = {"valve", "artifact", "dota", "spell", "card", name, color, rarity, card.getSubtype()};
                String keywordString = String.join(",", keywords);
                if(!card.getSigOf().isEmpty()){
                    keywordString = keywordString + "," + card.getSigOf();
                }
                if(!card.getEffectType().isEmpty()){
                    keywordString = keywordString + "," + card.getEffectType();
                }
                System.out.println("Check card: " + name);
                System.out.println(description);
                System.out.println(keywordString);
                try {
                    DbLoader.updateMetadata(jid, description, keywordString);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (card.getType().equals("Item")) {
                String rarity = card.getRarity();
                String descTmpl = "%s is a %s %s item in Valve Dota Artifact Game. Gold cost %d.";
                String description = String.format(descTmpl, name, rarity, card.getSubtype(), card.getGold());
                description =description + " " + card.getEffect();
                String[] keywords = {"valve", "artifact", "dota", "item", "card", name, rarity, card.getSubtype()};
                String keywordString = String.join(",", keywords);
                System.out.println("Check card: " + name);
                System.out.println(description);
                System.out.println(keywordString);
                try {
                    DbLoader.updateMetadata(jid, description, keywordString);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Unknown card type: " + card.getType());
            }
        }
        DbLoader.closeConnection();
    }
}
