import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Scraper {
    public static final String HOST = "https://artifactcards.info";
    public static void main(String[] args)
                    throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<Card> cardList = new ArrayList<>();
        List<String[]> lineList = new ArrayList<>();
        try (Writer writer = Files.newBufferedWriter(Paths.get("output/csv/list.csv"))) {
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(getHeader());
            csvWriter.writeNext(getLabels());
            String url = HOST + "/card/list";
            System.out.println("Fetching " + url + " ...");
            Document doc = Jsoup.connect(url).get();
            Elements cards = doc.getElementsByClass("cardlistitem");
            System.out.println(cards.size());
            for (Element cardElement : cards) {
                Card card = new Card();
                String href = cardElement.getElementsByTag("a").first().attr("href");
                String src = cardElement.getElementsByTag("img").first().attr("src");
                src = HOST + src;
//                download(src);
                card.setImage("images/ListOfItemsFiles/" + getFilename(src));
                String path = (href.split("/"))[2];
                String exIdStr = (path.split("-"))[0];
                String exId = exIdStr;
                if (exId.equals("50")) {
                    int a = 0;
                }
                card.setExtId(exId);
                Element parent = cardElement.parent().parent();
                String name = parent.getElementsByTag("h4").first().ownText().trim();
                String type = parent.getElementsByTag("h4").first().getElementsByTag("span").first().text().trim();
                card.setName(name);
                card.setType(type);
                Element rarityLabel = parent.select("div:containsOwn(Rarity)").first();
                String rarity = rarityLabel.nextElementSibling().text().trim();
                card.setRarity(rarity);
                Element colorLabel = parent.select("div:containsOwn(Color)").first();
                String color = colorLabel == null? "Unknown": colorLabel.nextElementSibling().text().trim();
                card.setColor(color);
                Element statsLabel = parent.select("div:containsOwn(Stats)").first();
                if (statsLabel == null){
                    card.setAttack("");
                    card.setArmour("");
                    card.setHealth("");
                }else {
                    String statsStr = statsLabel.nextElementSibling().text().trim();
                    String[] statsArr = statsStr.split("/");
                    String attack = statsArr[0].trim().equals("Unknown") ? "" : statsArr[0].trim();
                    String defend = statsArr[1].trim().equals("Unknown") ? "" : statsArr[1].trim();
                    String health = statsArr[2].trim().equals("Unknown") ? "" : statsArr[2].trim();
                    card.setAttack(attack);
                    card.setArmour(defend);
                    card.setHealth(health);
                }
                Element seriesLabel = parent.select("div:containsOwn(Series)").first();
                String series = colorLabel == null? "Unknown": seriesLabel.nextElementSibling().text().trim();
                card.setSeries(series);
                Element sigOfLabel = parent.select("div:containsOwn(Sig. of)").first();
                String sigOf = sigOfLabel == null? "Unknown": sigOfLabel.nextElementSibling().text().trim();
                card.setSigOf(sigOf);
                Element sigCardLabel = parent.select("div:containsOwn(Sig. Card:)").first();
                String sigCard = sigCardLabel == null? "Unknown": sigCardLabel.nextElementSibling().text().trim();
                card.setSigCard(sigCard);
                Element effectLabel = parent.select("div:containsOwn(Effect)").first();
                String effect = effectLabel == null? "Unknown": effectLabel.nextElementSibling().text().trim();
                card.setEffect(effect);
                Element costLabel = parent.select("div:containsOwn(Cost)").first();
                String cost = effectLabel == null? "Unknown": costLabel.nextElementSibling().text().trim();
                card.setCost(cost);
                Element skillLabel = parent.select("div:containsOwn(Skill)").first();
                String skill = skillLabel == null? "Unknown": skillLabel.nextElementSibling().text().trim();
                card.setSkill(skill);
                System.out.println(name);
                System.out.println(color);
                cardList.add(card);

            }
            for (Card card : cardList) {
                if (card.type.equals("Hero")){
                    List<Card> filtered = cardList.stream().filter(c -> c.getName().equals(card.getSigCard())).collect(
                                    Collectors.toList());
                    if (filtered.isEmpty()) {
                        card.setSigCardId("-1");
                    }else {
                        card.setSigCardId(filtered.get(0).getExtId());
                    }
                }
                lineList.add(card.toArray());
            }
            csvWriter.writeAll(lineList);
        }

    }

    private static String[] getHeader() {
        return new String[] {"category", "code", "title", "image", "feature", "feature", "feature", "feature", "feature",
                        "feature", "feature", "feature", "feature", "feature", "feature", "feature"};
    }

    private static String[] getLabels() {
        return new String[] {"", "Ex. Id", "Name", "", "Series", "Rarity", "Colour", "Attack", "Amour",
                        "Health", "Sig. Card", "Sig. Of", "Cost", "Skill", "Effect", "SigCard ID"};
    }

    private static void download(String src) throws IOException {
        String filename = getFilename(src);
        try(InputStream in = new URL(src).openStream()){
            Files.copy(in, Paths.get("output/images/" + filename));
        }
    }

    private static String  getFilename(String src) {
        String[] filenameArr = src.split("/");
        String filename = filenameArr[filenameArr.length - 1];
        return filename;
    }
}
