package buff;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hero extends Card {
    private String attack;
    private String armor;
    private String health;
    private String sigCard;
    private String color;
    private List<HeroSkill> skills = new ArrayList<>();
    private Map<String, String> skillMap = new HashMap<>();

    @Override protected String[] asLine() {
        concatKills();
        return new String[] {title, "Hero", "", rarity, color, revealed, attack, armor, health,
                        "", "", skillMap.get("name"), skillMap.get("effect"), skillMap.get("type"), sigCard, "", getCardSet()};
    }

    public Hero(String type, String title, String rarity, String attack, String armor, String health, String sigCard,
                    String color) {
        this.type = type;
        this.title = title;
        this.rarity = rarity;

        this.attack = attack;
        this.armor = armor;
        this.health = health;
        this.sigCard = sigCard;
        this.color = color;
    }

    public List<HeroSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<HeroSkill> skills) {
        this.skills = skills;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getSigCard() {
        return sigCard;
    }

    public void setSigCard(String sigCard) {
        this.sigCard = sigCard;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    private void concatKills() {
        String skillNames = skills.stream().map(HeroSkill::getName).collect(Collectors.joining("|"));
        String skillTypes = skills.stream().map(HeroSkill::getType).collect(Collectors.joining("|"));
        String skillEffects = skills.stream().map(HeroSkill::getEffect).collect(Collectors.joining("|"));
        skillMap.put("name", skillNames);
        skillMap.put("type", skillTypes);
        skillMap.put("effect", skillEffects);
    }
}
