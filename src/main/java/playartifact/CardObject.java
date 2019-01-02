package playartifact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardObject {
    private int card_id;
    private String card_type;
    private MultiLanguage card_name;
    private MultiLanguage card_text;
    private MultiImage mini_image;
    private MultiImage large_image;
    private String rarity;
    private boolean is_red = false;
    private boolean is_blue = false;
    private boolean is_green = false;
    private boolean is_black = false;
    private int attack;
    private int armor;
    private int hit_point;
    private int gold_cost;
    private int mana_cost;
    private String sub_type;
    List<Reference> references;

    public int getMana_cost() {
        return mana_cost;
    }

    public void setMana_cost(int mana_cost) {
        this.mana_cost = mana_cost;
    }

    public int getGold_cost() {
        return gold_cost;
    }

    public void setGold_cost(int gold_cost) {
        this.gold_cost = gold_cost;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public MultiLanguage getCard_name() {
        return card_name;
    }

    public void setCard_name(MultiLanguage card_name) {
        this.card_name = card_name;
    }

    public MultiLanguage getCard_text() {
        return card_text;
    }

    public void setCard_text(MultiLanguage card_text) {
        this.card_text = card_text;
    }

    public MultiImage getMini_image() {
        return mini_image;
    }

    public void setMini_image(MultiImage mini_image) {
        this.mini_image = mini_image;
    }

    public MultiImage getLarge_image() {
        return large_image;
    }

    public void setLarge_image(MultiImage large_image) {
        this.large_image = large_image;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public boolean isIs_red() {
        return is_red;
    }

    public void setIs_red(boolean is_red) {
        this.is_red = is_red;
    }

    public boolean isIs_blue() {
        return is_blue;
    }

    public void setIs_blue(boolean is_blue) {
        this.is_blue = is_blue;
    }

    public boolean isIs_green() {
        return is_green;
    }

    public void setIs_green(boolean is_green) {
        this.is_green = is_green;
    }

    public boolean isIs_black() {
        return is_black;
    }

    public void setIs_black(boolean is_black) {
        this.is_black = is_black;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHit_point() {
        return hit_point;
    }

    public void setHit_point(int hit_point) {
        this.hit_point = hit_point;
    }
}
