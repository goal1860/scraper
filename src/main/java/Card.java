public class Card {

    String extId;
    String name;
    String series;
    String rarity;
    String type;
    String color;
    String attack;
    String armour;
    String health;
    String sigCard;
    String sigOf;
    String cost;
    String effect;
    String skill;
    String image = "images/ListOfItemsFiles/thumb_61.png";
    String sigCardId;

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getArmour() {
        return armour;
    }

    public void setArmour(String armour) {
        this.armour = armour;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getSigCard() {
        return sigCard;
    }

    public void setSigCard(String sigCard) {
        this.sigCard = sigCard;
    }

    public String getSigOf() {
        return sigOf;
    }

    public void setSigOf(String sigOf) {
        this.sigOf = sigOf;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSigCardId() {
        return sigCardId;
    }

    public void setSigCardId(String sigCardId) {
        this.sigCardId = sigCardId;
    }

    public String[] toArray() {
        return new String[] {type, extId, name, image, series, rarity, color, attack, armour, health, sigCard, sigOf,
                        cost, skill, effect, sigCardId};
    }
}
