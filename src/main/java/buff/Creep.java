package buff;

public class Creep extends Spell {
    private String attack;
    private String armor;
    private String health;
    @Override protected String[] asLine() {
        return new String[] {title, "Spell", "Creep", rarity, color, revealed, attack, armor, health,
                        mana, "", "", effect, effectType, "", sigOf, getCardSet()};
    }

    public Creep(String title, String rarity, String mana, String attack, String armor, String health,
                    String effectType, String effect, String color, String sigOf) {
        this.rarity = rarity;
        this.mana = mana;
        this.title = title;
        this.attack = attack;
        this.armor = armor;
        this.health = health;
        this.effectType = effectType;
        this.effect = effect;
        this.color = color;
        this.sigOf = sigOf;
    }
}
