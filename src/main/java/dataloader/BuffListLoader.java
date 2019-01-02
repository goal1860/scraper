package dataloader;

import buff.HeroSkill;
import checks.HeroSkillMetadataCheck;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuffListLoader {
    public static final int COL_TITLE = 0;
    public static final int COL_TYPE = 1;
    public static final int COL_SUBTYPE = 2;
    public static final int COL_RARITY = 3;
    public static final int COL_COLOR = 4;
    public static final int COL_REVEALED = 5;
    public static final int COL_ATTACK = 6;
    public static final int COL_ARMOR = 7;
    public static final int COL_HEALTH = 8;
    public static final int COL_MANA = 9;
    public static final int COL_GOLD = 10;
    public static final int COL_SKILLS =11;
    public static final int COL_EFFECT =12;
    public static final int COL_EFFECTTYPE =13;
    public static final int COL_SIG =14;
    public static final int COL_SIGOF =15;
    public static List<String[]> loadCsv() {
        try {
            CSVReader reader = new CSVReader(new FileReader("output/csv/buff.csv"), ',', '"', 1);
            List<String[]> cardList = reader.readAll();
            return cardList;
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String[]> loadMap() {
        Map<String, String[]> map = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("output/csv/buff.csv"), ',', '"', 1);
            List<String[]> cardList = reader.readAll();
            for (String[] line : cardList) {
                map.put(line[COL_TITLE], line);
            }
            return map;
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, HeroSkill> loadSkillMap() {
        Map<String, HeroSkill> map = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("output/csv/buff.csv"), ',', '"', 1);
            List<String[]> cardList = reader.readAll();
            for (String[] line : cardList) {
                if(!line[COL_SKILLS].isEmpty()){
                    String[] names = line[COL_SKILLS].split("\\|");
                    String[] effects = line[COL_EFFECT].split("\\|");
                    String[] types = line[COL_EFFECTTYPE].split("\\|");
                    for (int i = 0; i < names.length; i++) {
                        HeroSkill skill = new HeroSkill(names[i], types[i], effects[i]);
                        skill.setHeroName(line[COL_TITLE]);
                        map.put(names[i], skill);
                    }
                }
            }
            return map;
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
