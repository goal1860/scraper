package buff;

public class Spell extends Card{
    protected String mana;
    protected String effect;
    protected String effectType;
    protected String color;
    protected String sigOf;

    @Override protected String[] asLine() {
        return getLine("Spell");
    }

    public String getMana() {
        return mana;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffectType() {
        return effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    protected String[] getLine(String subType) {
        return new String[] {title, "Spell", subType, rarity, color, revealed, "", "", "",
                        mana, "", "", effect, effectType, "", sigOf, getCardSet()};
    }

    public Spell(){

    }

    public Spell(String title, String rarity, String mana, String effectType, String effect, String color, String sigOf) {
        this.rarity = rarity;
        this.title = title;
        this.mana = mana;
        this.effectType = effectType;
        this.effect = effect;
        this.color = color;
        this.sigOf = sigOf;
    }
}
