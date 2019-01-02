package fixes;

import dataloader.BuffListLoader;
import dataloader.DbLoader;

import java.util.Map;

import static dataloader.BuffListLoader.COL_RARITY;

public class RarityFix {
    public static void main(String[] args) {
        DbLoader.openConnection();
        Map<String, String[]> buffMap = BuffListLoader.loadMap();
        Map<String, Integer> itemMap = DbLoader.getEmptyRarityRecords();
        for(String name : itemMap.keySet()){
            System.out.println("Updating " + name);
            if(!buffMap.containsKey(name)){
                System.out.println("Can't find data: " + name);
                continue;
            }
            String rarity = buffMap.get(name)[COL_RARITY];
            DbLoader.updateRarity(itemMap.get(name), rarity);
        }
        DbLoader.closeConnection();
    }


}
