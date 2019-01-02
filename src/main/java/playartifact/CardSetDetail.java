package playartifact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardSetDetail {
    private String version;
    private List<CardObject> card_list;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<CardObject> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<CardObject> card_list) {
        this.card_list = card_list;
    }
}
