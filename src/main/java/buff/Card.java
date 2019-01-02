package buff;

public abstract class Card {
    protected String type;
    protected String title;
    protected String imageName;
    protected String revealed = "true";
    protected String rarity;
    private String sefName;
    private String cardSet;

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getSefName() {
        return sefName;
    }

    public void setSefName(String sefName) {
        this.sefName = sefName;
    }

    protected String getRarity() {
        return rarity;
    }

    protected void setRarity(String rarity) {
        this.rarity = rarity;
    }

    protected String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected String getImageName() {
        return imageName;
    }

    protected void setImageName(String imageName) {
        this.imageName = imageName;
    }

    protected String getRevealed() {
        return revealed;
    }

    protected void setRevealed(String revealed) {
        this.revealed = revealed;
    }



    static String[] getHeader(){
        return new String[] {"title", "type", "subtype", "rarity", "color", "revealed", "attack", "armor", "health",
                        "mana", "gold", "skills", "effect", "effectType", "sigCard", "sigOf", "cardSet"};
    }

    protected abstract String[] asLine();
}
