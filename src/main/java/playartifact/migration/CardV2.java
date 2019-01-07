package playartifact.migration;

public class CardV2 {
    private int id;

    public CardV2(int id, String name, String type, String subtype, String color, String rarity, String information, String miniImgUrl, String link) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.subtype = subtype;
        this.color = color;
        this.rarity = rarity;
        this.information = information;
        this.miniImgUrl = miniImgUrl;
        this.link = link;
    }

    private String name;
    private String type;
    private String subtype;
    private String color;
    private String rarity;
    private String information;
    private String miniImgUrl;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getMiniImgUrl() {
        return miniImgUrl;
    }

    public void setMiniImgUrl(String miniImgUrl) {
        this.miniImgUrl = miniImgUrl;
    }



}
