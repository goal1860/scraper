package buff;

public class Improvement extends Spell {
    @Override protected String[] asLine() {
        return getLine("Improvement");
    }

    public Improvement(String title, String rarity, String mana, String effectType, String effect, String color, String sigOf) {
        super(title, rarity, mana, effectType, effect, color, sigOf);
    }
}
