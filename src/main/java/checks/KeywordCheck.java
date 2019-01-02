package checks;

import dataloader.BuffListLoader;
import dataloader.DbLoader;
import entities.Chdg4K2Items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static dataloader.BuffListLoader.*;

public class KeywordCheck {
    public static void main(String[] args) {
        DbLoader.openConnection();
        Map<String, String[]> buffMap = BuffListLoader.loadMap();
        Set<String> keywordSet = new HashSet<>();
        for (String title : buffMap.keySet()) {
            keywordSet.add(title);
        }
        List<String> dbKeywordList = DbLoader.loadKeywords();
        for (String keyword : dbKeywordList) {
            if (keywordSet.contains(keyword)) {
                keywordSet.remove(keyword);
            }
        }
        if (!keywordSet.isEmpty()) {
            List<Chdg4K2Items> k2ItemsList = DbLoader.getK2ItemList();
            System.out.println("There are keywords missing. Preparing to add...");
            int maxOrder = DbLoader.getMaxKeywordOrdering();
            if(maxOrder == -1){
                throw new RuntimeException("Failed to retrieve max ordering of keywords.");
            }
            for (String k : keywordSet) {
                System.out.println("Missing keyword: " + k + ",  adding...");
                String[] cardLine = buffMap.get(k);
                String type = cardLine[COL_TYPE];
                List<Chdg4K2Items> searchList = k2ItemsList.stream().filter(item -> item.getTitle().equals(k)).collect(Collectors.toList());
                if(searchList.isEmpty()){
                    System.out.println("Card is not found: " + k);
                    continue;
                }
                String alias = searchList.get(0).getAlias();

                String link = "";
                int catid = 0;
                String tooltip;
                if(type.equals("Hero")){
                    link = "/heroes/" + alias;
                    catid = 95;
                    tooltip = k;

                }else if(type.equals("Spell")) {
                    link = "/spells/" + alias;
                    catid = 96;
                    tooltip = cardLine[COL_EFFECT];
                } else {
                    link = "/items/" + alias;
                    catid = 99;
                    tooltip = cardLine[COL_EFFECT];
                }
                maxOrder ++;
                DbLoader.saveKeyword(cardLine[COL_TITLE], link, catid, tooltip, maxOrder);
            }
        }else {
            System.out.println("Keywords are up to date.");
        }
        DbLoader.closeConnection();
    }
}
