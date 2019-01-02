package buff;

public abstract class Item extends Card{
    private String gold;
    private String effect;

    @Override protected String[] asLine() {
        return new String[] {title, "Item", getSubtype(), rarity, "", revealed, "", "", "",
                        "", gold, "", effect, "", "", "", getCardSet()};
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    protected abstract String getSubtype();

    public Item(String title, String rarity, String gold, String effect) {
        this.title = title;
        this.rarity = rarity;
        this.gold = gold;
        this.effect = effect;
    }
}
