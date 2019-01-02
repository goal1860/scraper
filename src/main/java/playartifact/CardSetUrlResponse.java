package playartifact;

public class CardSetUrlResponse {
    private String cdn_root;
    private String url;
    private String expire_time;

    public String getCdn_root() {
        return cdn_root;
    }

    public void setCdn_root(String cdn_root) {
        this.cdn_root = cdn_root;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }
}
