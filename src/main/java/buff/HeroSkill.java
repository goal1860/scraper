package buff;

public class HeroSkill {
    private String name;
    private String type;
    private String effect;
    private String heroName;

    public HeroSkill(String name, String type, String effect) {

        this.name = name;
        this.type = type;
        this.effect = effect;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
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

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
