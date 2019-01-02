package buff;

public class Armor extends Item {

    public Armor(String title, String rarity, String gold, String effect) {
        super(title, rarity, gold, effect);
    }

    @Override protected String getSubtype() {
        return "Armor";
    }
}
