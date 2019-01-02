package checks;

import buff.HeroSkill;
import dataloader.BuffListLoader;
import dataloader.DbLoader;
import entities.Chdg4K2Items;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HeroSkillMetadataCheck {
    public static void main(String[] args) {
        DbLoader.openConnection();
        List<Chdg4K2Items> itemList = DbLoader.getK2ItemList("catid=19 and metadesc=''");
        Map<String, HeroSkill> skillMap = BuffListLoader.loadSkillMap();
        for (Chdg4K2Items item : itemList) {
            String name = item.getTitle();
            System.out.println("Check " + name);
            if(skillMap.containsKey(name)){
                HeroSkill skill = skillMap.get(name);
                String descTmpl = "%s is a skill of hero %s in Valve Dota Artifact Card Game. %s: %s";
                String description = String.format(descTmpl, name, skill.getHeroName(), skill.getType(), skill.getEffect());
                String[] keywords = {"valve", "artifact", "dota", "skill", "card", name, skill.getHeroName(), skill.getType()};
                String keywordString = String.join(",", keywords);
                System.out.println(description);
                System.out.println(keywordString);
                try {
                    int jid = item.getId();
                    DbLoader.updateMetadata(jid, description, keywordString);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println(name + " is not found in skill map.");
            }
        }
        DbLoader.closeConnection();
    }
}
