package buff;

public class Accessory extends Item {


    public Accessory(String title, String rarity, String gold, String effect) {
        super(title, rarity, gold, effect);
    }

    @Override protected String getSubtype() {
        return "Accessory";
    }


}
