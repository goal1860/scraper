package buff;

public class Weapon extends Item {
    public Weapon(String title, String rarity, String gold, String effect) {
        super(title, rarity, gold, effect);
    }

    @Override protected String getSubtype() {
        return "Weapon";
    }
}
