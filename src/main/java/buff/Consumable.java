package buff;

public class Consumable extends Item{

    public Consumable(String title, String rarity, String gold, String effect) {
        super(title, rarity, gold, effect);
    }

    @Override protected String getSubtype() {
        return "Consumable";
    }
}
