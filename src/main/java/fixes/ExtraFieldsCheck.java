package fixes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataloader.BuffListLoader;
import dataloader.DbLoader;
import entities.IdValuePair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * Add extra fields to K2 items. It is not needed right now.
 */
public class ExtraFieldsCheck {
    private static Map<String, Integer> catMap = new HashMap<>();
    private static Map<String, String[]> buffMap = BuffListLoader. loadMap();
    private static Map<String, Integer> fieldMap = DbLoader.getFieldMap("card");
    public static void main(String[] args) {
        catMap = DbLoader.getCategoryMap();
        checkCards("hero-cards");
        checkCards("spell-cards");
        checkCards("items");
    }

    private static void checkCards(String category) {
        System.out.println("Updating spells:");
        Integer catId = catMap.get(category);
        Map<String, Integer> itemMap;

        if(null != catId) {
            itemMap = DbLoader.loadItemsByCategory(catId);
        }else{
            throw new RuntimeException("Category is not found: " + category);
        }

        for (String title : itemMap.keySet()) {
            List<IdValuePair> pairList = new ArrayList<>();
            int id = itemMap.get(title);
            System.out.println("Updating " + title);
            if(!buffMap.containsKey(title)) {
                System.out.println("Data not found: " + title);
                continue;
            }

            String[] line = buffMap.get(title);
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("type")), line[COL_TYPE]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("subtype")), line[COL_SUBTYPE]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("rarity")), line[COL_RARITY]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("color")), line[COL_COLOR]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("revealed")), line[COL_REVEALED]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("attack")), line[COL_ATTACK]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("armor")), line[COL_ARMOR]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("health")), line[COL_HEALTH]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("mana")), line[COL_MANA]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("gold")), line[COL_GOLD]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("skills")), line[COL_SKILLS]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("effect")), line[COL_EFFECT]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("effectType")), line[COL_EFFECTTYPE]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("sigCard")), line[COL_SIG]));
            pairList.add(new IdValuePair(String.valueOf(fieldMap.get("sigOf")), line[COL_SIGOF]));
            Map<String, String> map = new HashMap<>();
            map.put("key", "value");
            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonResult = mapper.writeValueAsString(pairList);
                System.out.println(jsonResult);
                DbLoader.updateExtraFields(id, jsonResult);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
