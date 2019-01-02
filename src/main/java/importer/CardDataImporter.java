package importer;

import dataloader.BuffListLoader;
import dataloader.DbLoader;
import entities.Chdg4ArtifactgameCard;
import entities.Chdg4K2Items;
import hibernate.HibernateUtil;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dataloader.BuffListLoader.COL_ARMOR;
import static dataloader.BuffListLoader.COL_ATTACK;
import static dataloader.BuffListLoader.COL_COLOR;
import static dataloader.BuffListLoader.COL_EFFECT;
import static dataloader.BuffListLoader.COL_EFFECTTYPE;
import static dataloader.BuffListLoader.COL_GOLD;
import static dataloader.BuffListLoader.COL_HEALTH;
import static dataloader.BuffListLoader.COL_MANA;
import static dataloader.BuffListLoader.COL_RARITY;
import static dataloader.BuffListLoader.COL_REVEALED;
import static dataloader.BuffListLoader.COL_SIG;
import static dataloader.BuffListLoader.COL_SIGOF;
import static dataloader.BuffListLoader.COL_SKILLS;
import static dataloader.BuffListLoader.COL_SUBTYPE;
import static dataloader.BuffListLoader.COL_TYPE;

public class CardDataImporter {
    public static void main(String[] args) {
        String env = "prod";
        Map<String, String[]> buffList = BuffListLoader.loadMap();
        if(env.equals("docker")) {

            List<Chdg4ArtifactgameCard> cardList = HibernateUtil.getEntityList("from Chdg4ArtifactgameCard");
            List<Chdg4K2Items> k2ItemsList = HibernateUtil.getEntityList("from Chdg4K2Items");

            for (String title : buffList.keySet()) {
                List<Chdg4ArtifactgameCard> searchList = cardList.stream().filter(c -> c.getName().equals(title)).collect(Collectors.toList());
                boolean inCardTable = !searchList.isEmpty();
                List<Chdg4K2Items> searchK2List = k2ItemsList.stream().filter(c -> c.getTitle().equals(title)).collect(Collectors.toList());
                boolean inK2Table = !searchK2List.isEmpty();
                if (!inK2Table) {
                    System.out.println(title + " is not found. Please add to K2 first.");
                    continue;
                }
                int k2Id = searchK2List.get(0).getId();
                if (!inCardTable) {
                    System.out.println(title + " is not found in Card table. Adding now:");
                    String[] line = buffList.get(title);
                    Chdg4ArtifactgameCard card =
                                    new Chdg4ArtifactgameCard(k2Id, title, line[COL_TYPE], line[COL_SUBTYPE], line[COL_RARITY],
                                                    line[COL_COLOR], line[COL_REVEALED].equals("true"),
                                                    line[COL_ATTACK].isEmpty() ? 0 : Integer.parseInt(line[COL_ATTACK]),
                                                    line[COL_ARMOR].isEmpty() ? 0 : Integer.parseInt(line[COL_ARMOR]),
                                                    line[COL_HEALTH].isEmpty() ? 0 : Integer.parseInt(line[COL_HEALTH]),
                                                    line[COL_MANA].isEmpty() ? 0 : Integer.parseInt(line[COL_MANA]),
                                                    line[COL_GOLD].isEmpty() ? 0 : Integer.parseInt(line[COL_GOLD]),
                                                    line[COL_SKILLS], processSpecialChars(line[COL_EFFECT]), line[COL_EFFECTTYPE], line[COL_SIG], line[COL_SIGOF],
                                                    new Date(new java.util.Date().getTime()));
                    card.setRarity(line[COL_RARITY]);
                    HibernateUtil.saveEntity(card);
                }
            }
        }else {
            DbLoader.openConnection();
            List<Chdg4ArtifactgameCard> cardList = DbLoader.getCardList();
            List<Chdg4K2Items> k2ItemsList = DbLoader.getK2ItemList();

            for (String title : buffList.keySet()) {
                List<Chdg4ArtifactgameCard> searchList = cardList.stream().filter(c -> c.getName().equals(title)).collect(Collectors.toList());
                boolean inCardTable = !searchList.isEmpty();
                List<Chdg4K2Items> searchK2List = k2ItemsList.stream().filter(c -> c.getTitle().equals(title)).collect(Collectors.toList());
                boolean inK2Table = !searchK2List.isEmpty();
                if (!inK2Table) {
                    System.out.println(title + " is not found. Please add to K2 first.");
                    continue;
                }
                int k2Id = searchK2List.get(0).getId();
                if (!inCardTable) {
                    System.out.println(title + " is not found in Card table. Adding now:");
                    String[] line = buffList.get(title);
                    Chdg4ArtifactgameCard card =
                                    new Chdg4ArtifactgameCard(k2Id, title, line[COL_TYPE], line[COL_SUBTYPE], line[COL_RARITY],
                                                    line[COL_COLOR], line[COL_REVEALED].equals("true"),
                                                    line[COL_ATTACK].isEmpty() ? 0 : Integer.parseInt(line[COL_ATTACK]),
                                                    line[COL_ARMOR].isEmpty() ? 0 : Integer.parseInt(line[COL_ARMOR]),
                                                    line[COL_HEALTH].isEmpty() ? 0 : Integer.parseInt(line[COL_HEALTH]),
                                                    line[COL_MANA].isEmpty() ? 0 : Integer.parseInt(line[COL_MANA]),
                                                    line[COL_GOLD].isEmpty() ? 0 : Integer.parseInt(line[COL_GOLD]),
                                                    line[COL_SKILLS], processSpecialChars(line[COL_EFFECT]), line[COL_EFFECTTYPE], line[COL_SIG], line[COL_SIGOF],
                                                    new Date(new java.util.Date().getTime()));
                    DbLoader.saveCard(card);
                }
            }
            DbLoader.closeConnection();
        }

    }

    private static String processSpecialChars(String origin) {
        return origin.replace("■", "<span class=\"rad-text\">■</span>")
                        .replace("▢", "<span class=\"rad-text\">▢</span>")
                        .replace("□", "<span class=\"rad-text\">□</span>");

    }
}
