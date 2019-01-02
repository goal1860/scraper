package checks;

import dataloader.BuffListLoader;
import dataloader.DbLoader;
import org.apache.commons.text.WordUtils;

import java.sql.SQLException;
import java.util.Map;
import static dataloader.BuffListLoader.*;

/**
 * Add introtext to k2 items if missing.
 */
public class IntroTextCheck {

    public static void main(String[] args) throws SQLException {
        //Map<String, Integer> catMap = DbLoader.getCategoryMap();
        DbLoader.openConnection();
        Map<String, String[]> buffMap = BuffListLoader.loadMap();
        Map<String, Integer> recMap = DbLoader.getEmptyIntroRecords();
        for (String title : recMap.keySet()) {
            if(!buffMap.containsKey(title)){
                System.out.println("Can't find data for: " + title);
                continue;
            }
            System.out.println("Updating: " + title);
            String[] line = buffMap.get(title);
            String type = line[COL_TYPE];
            int id = recMap.get(title);
            String introTmpl;
            String color;
            String intro = "";
            String mana;
            String sigOf;
            String effect;
            String rarity = line[COL_RARITY];
            String attack = line[COL_ATTACK];
            String armor = line[COL_ARMOR];
            String health = line[COL_HEALTH];
            String subType;
            switch (type){
                case "Hero":
                    introTmpl = "<aside class=\"card-stats\">" + "<div><small>Type</small>Hero</div>" +
                                    "<div><small>Rarity</small>%s</div>" +
                                    "<div><small>Color</small><span class=\"card-%s\">%s</span></div>" +
                                    "<div><small>Skill</small>%s</div>" + "<div><small>Attack</small>%s</div>" +
                                    "<div><small>Armor</small>%s</div>" + "<div><small>Health</small>%s</div>" +
                                    "<div><small>Sig. Card</small>%s</div></aside>";

                    color = line[COL_COLOR];
                    String skills = line[COL_SKILLS].isEmpty()?"-": line[COL_SKILLS];

                    String sig = line[COL_SIG];
                    intro = String.format(introTmpl, rarity, color, WordUtils.capitalize(color), skills, attack, armor, health, sig);
                    DbLoader.updateIntro(id, intro);
                    break;
                case "Item":
                    subType = line[COL_SUBTYPE];
                    String gold = line[COL_GOLD];
                    effect = line[COL_EFFECT];
                    introTmpl = "<aside class=\"card-stats\">" + "<div><small>Item Type</small>%s</div>\n" +
                                    "<div><small>Rarity</small>%s</div>" +
                                    "<div><small>Gold</small>%s</div>" +
                                    "<div><small>Effect</small>%s</div>" + "</aside>";
                    intro = String.format(introTmpl, subType, rarity, gold, effect);
                    DbLoader.updateIntro(id, intro);
                    break;
                case "Spell":
                    subType = line[COL_SUBTYPE];
//                    System.out.println(subType);
                    switch (subType){
                        case "Spell":
                            introTmpl = "<aside class=\"card-stats\">" +
                                            "<div><small>Spell Type</small>Spell</div>" +
                                            "<div><small>Rarity</small>%s</div>" +
                                            "<div><small>Mana</small>%s</div>" +
                                            "<div><small>Color</small><span class=\"card-%s\">%s</span></div>";


                            color = line[COL_COLOR];
                            mana = line[COL_MANA];
                            sigOf = line[COL_SIGOF];
                            intro = String.format(introTmpl, rarity, mana, color, WordUtils.capitalize(color));
                            if(!sigOf.isEmpty()) {
                                intro = intro +  "<div><small>Sig. Hero</small>" + sigOf + "</div>";
                            }
                            effect = line[COL_EFFECT];
                            if(!effect.isEmpty()) {
                                intro = intro +
                                                "<div><small>Effect</small>" + effect+"</div>" +

                                                "</aside>";
                            }
                            DbLoader.updateIntro(id, intro);
                            break;
                        case "Creep":
                            introTmpl ="<aside class=\"card-stats\">" +
                                            "<div><small>Spell Type</small>Creep</div>" +
                                            "<div><small>Rarity</small>%s</div>" +
                                            "<div><small>Mana</small>%s</div>" +
                                            "<div><small>Color</small><span class=\"card-%s\">%s</span></div>" +
                                            "<div><small>Attack</small>%s</div>" +
                                            "<div><small>Armor</small>%s</div>" +
                                            "<div><small>Health</small>%s</div>";

                            mana = line[COL_MANA];
                            color = line[COL_COLOR];
                            intro = String.format(introTmpl, rarity, mana, color, WordUtils.capitalize(color), attack, armor, health);
                            sigOf = line[COL_SIGOF];
                            if(!sigOf.isEmpty()) {
                                intro = intro +  "<div><small>Sig. Hero</small>" + sigOf + "</div>";
                            }
                            effect = line[COL_EFFECT];
                            if(!effect.isEmpty()) {
                                intro = intro +
                                                "<div><small>Effect</small>" + effect+"</div>" +

                                                "</aside>";
                            }
                            DbLoader.updateIntro(id, intro);
                            break;
                        case "Improvement":
                            introTmpl = "<aside class=\"card-stats\">" +
                                            "<div><small>Spell Type</small>Improvement</div>" +
                                            "<div><small>Rarity</small>%s</div>" +
                                            "<div><small>Mana</small>%s</div>" +
                                            "<div><small>Color</small><span class=\"card-%s\">%s</span></div>";


                            color = line[COL_COLOR];
                            mana = line[COL_MANA];
                            sigOf = line[COL_SIGOF];
                            intro = String.format(introTmpl, rarity, mana, color, WordUtils.capitalize(color));
                            if(!sigOf.isEmpty()) {
                                intro = intro +  "<div><small>Sig. Hero</small>" + sigOf + "</div>";
                            }
                            effect = line[COL_EFFECT];
                            if(!effect.isEmpty()) {
                                intro = intro +
                                                "<div><small>Effect</small>" + effect+"</div>" +

                                                "</aside>";
                            }
                            DbLoader.updateIntro(id, intro);
                            break;
                        default:
                            System.out.println("Can't update the record for subtype: " + subType);
                    }
                    break;

                default:
                    System.out.println("Can't update the record for type: " + type);
            }


        }
        DbLoader.closeConnection();
    }
}
