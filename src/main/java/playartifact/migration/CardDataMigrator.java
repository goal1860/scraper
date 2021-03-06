package playartifact.migration;

import buff.BuffScraper;
import dataloader.DbLoader;
import entities.Chdg4ArtifactgameCard;
import playartifact.CardObject;
import playartifact.FeedReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static playartifact.FeedReader.loadCardInfo;

public class CardDataMigrator {
    private static final boolean CLEAR_FIRST = false;
    private static final boolean DOWNLOAD_IMAGE = false;

    public static void main(String[] args) throws IOException {
        DbLoader.openConnection();
        if (CLEAR_FIRST) {
            DbLoader.clearCardV2();
        }
        List<Chdg4ArtifactgameCard> cardList = DbLoader.getCardList();
        List<CardObject> cardObjectList = FeedReader.loadCardInfo("00");
        Map<String, String> skillMap = DbLoader.loadHeroSkillMap();
        Map<String, CardObject> cardObjectMap = convertToMap(cardObjectList);
        for (Chdg4ArtifactgameCard cardv1 : cardList) {
            String name = cardv1.getName();
            if(name.startsWith("Far")) {
                int a = 0;
            }
            System.out.println("Migrating " + name + "...");
            if (!cardObjectMap.containsKey(name)) {
                System.out.println(name + " is not found in feed definition.");
                continue;
            }
            CardObject cardObject = cardObjectMap.get(name);
            String friendlyName = BuffScraper.convertSef(name);
            String miniImageUrl = cardObject.getMini_image().getDefaultLanguage();
            if (DOWNLOAD_IMAGE) {

                String largeImageUrl = cardObject.getLarge_image().getDefaultLanguage();
                downloadImage(largeImageUrl, "output/images/large/" + friendlyName + ".png");

                downloadImage(miniImageUrl, "output/images/mini/" + friendlyName + ".png");
            }
            int id = cardObject.getCard_id();
            String type = cardv1.getType();
            String subType = cardv1.getSubtype();
            String color = cardv1.getColor();
            String rarity = cardv1.getRarity();
            String information = "";
            String signature = cardv1.getSigCard();
            int attack = cardv1.getAttack();
            int armor = cardv1.getArmor();
            int health = cardv1.getHealth();
            String link = "";

            CardV2 cardV2;
            String informationTmpl = "";
            switch (type) {
                case "Hero":
                    informationTmpl = "<img src=\"/images/icons/stat-attack.png\" class=\"stat-icon\">%d" +
                            "<img src=\"/images/icons/stat-armor.png\" class=\"stat-icon\">%d" +
                            "<img src=\"/images/icons/stat-health.png\" class=\"stat-icon\">%d" +
                            "%s<span class=\"stat-label\">Sig:</span>%s";
                    String skillsStr = cardv1.getSkillString();
                    String[] skillArr;
                    if(skillsStr == null || skillsStr.equals("")) {
                        skillArr = new String[]{};
                    }else {
                        skillArr = skillsStr.split(",");
                    }
                    String skillString = "";
                    if (skillArr.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("<span class=\"stat-label\">Skill: </span>");
                        for (String skill : skillArr) {
                            skill = skill.trim();
                            if (!skillMap.containsKey(skill)) {
                                System.out.println("Hero skill is not found: " + skill);
                            } else {
                                String text = skillMap.get(skill);
                                sb.append("<a href=\"" + "/hero-skills/" + BuffScraper.convertSef(skill) + "\" title=\"" + text + "\">" + skill + "</a>");
                            }
                        }
                        skillString = sb.toString();

                    }
                    information = String.format(informationTmpl, attack, armor, health, skillString, signature);
                    link = "heroes/" + BuffScraper.convertSef(name);
                    cardV2 = new CardV2(id, name, type, subType, color, rarity,information, "mini/"+BuffScraper.convertSef(name) + ".png", link);
                    DbLoader.saveCardV2(cardV2);
                    break;
                case "Spell":
                    int manaCost;
                    String effect;
                    switch (subType){
                        case "Spell":
                            informationTmpl = "Mana Cost: %d. %s";
                            manaCost = cardv1.getMana();
                            effect = cardv1.getEffect();

                            information = String.format(informationTmpl, manaCost, effect);
                            link = "spells/" + BuffScraper.convertSef(name);
                            cardV2 = new CardV2(id, name, type, subType, color, rarity,information, "mini/"+BuffScraper.convertSef(name) + ".png", link);
                            DbLoader.saveCardV2(cardV2);
                            break;
                        case "Creep":
                            informationTmpl = "Mana cost: %d. %s. <img src=\"/images/icons/stat-attack.png\" class=\"stat-icon\">%d" +
                                    "<img src=\"/images/icons/stat-armor.png\" class=\"stat-icon\">%d" +
                                    "<img src=\"/images/icons/stat-health.png\" class=\"stat-icon\">%d";
                            manaCost = cardv1.getMana();
                            effect = cardv1.getEffect();
                            information = String.format(informationTmpl, manaCost, effect, cardv1.getAttack(), cardv1.getArmor(), cardv1.getHealth());
                            link = "spells/" + BuffScraper.convertSef(name);
                            cardV2 = new CardV2(id, name, type, subType, color, rarity,information, "mini/"+BuffScraper.convertSef(name) + ".png", link);
                            DbLoader.saveCardV2(cardV2);
                            break;
                        case "Improvement":
                            informationTmpl = "Mana Cost: %d. %s";
                            manaCost = cardv1.getMana();
                            effect = cardv1.getEffect();

                            information = String.format(informationTmpl, manaCost, effect);
                            link = "spells/" + BuffScraper.convertSef(name);
                            cardV2 = new CardV2(id, name, type, subType, color, rarity,information, "mini/"+BuffScraper.convertSef(name) + ".png", link);
                            DbLoader.saveCardV2(cardV2);
                            break;
                        default:
                            System.out.println("Type not recognized: " + subType);
                    }
                default:
                    System.out.println("Type not recognized: " + type);
            }

        }
        DbLoader.closeConnection();
    }

    private static Map<String, CardObject> convertToMap(List<CardObject> cardObjectList) {
        Map<String, CardObject> cardObjectMap = new HashMap<>();
        for (CardObject cardObject : cardObjectList) {
            if (
                    cardObject.getCard_type().equals("Hero") ||
                            cardObject.getCard_type().equals("Creep") ||
                            cardObject.getCard_type().equals("Spell") ||
                            cardObject.getCard_type().equals("Improvement") ||
                            cardObject.getCard_type().equals("Item")) {
                cardObjectMap.put(cardObject.getCard_name().getEnglish(), cardObject);
            }


        }
        return cardObjectMap;
    }

    private static void downloadImage(String urlStr, String localpath) throws IOException {
        URL url = new URL(urlStr);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream(localpath);
        fos.write(response);
        fos.close();
    }
}
