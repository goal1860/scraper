package buff;

import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BuffScraper {
    private static WebDriver driver;

    private static boolean catchScreenshot = false;
    private static Map<String, String> sigMap = new HashMap<>();
    private static Map<String, String> detailMap = new HashMap<>();
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                        "/home/LOOKTOURS/lhe/Documents/hg/experiences/drivers/chromedriver_linux64-2.42");

        driver = new ChromeDriver();
        Writer writer = Files.newBufferedWriter(Paths.get("output/csv/buff.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);
        driver.get("https://www.artifactbuff.com/cards");
        driver.findElement(By.className("option-grid")).click();
        Thread.sleep(5000);
        driver.manage().window().maximize();
        List<WebElement> cardElementList = driver.findElements(By.className("card"));
        List<WebElement> elements = new ArrayList<>();
        List<String[]> lineList = new ArrayList<>();
        lineList.add(Card.getHeader());
        List<Card> cardList = new ArrayList<>();
        for (int i=0; i< cardElementList.size(); i++) {
            WebElement cardElement = cardElementList.get(i);
            Card card = composeCard(cardElement);
            elements.add(cardElement);
            if(i == cardElementList.size() - 1) { // Last one.
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardElement);
                catchPicture(elements, driver);
                lineList.add(card.asLine());
                break;
            }
            if(i %4 == 3) {// last one of the row.
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardElement);
                catchPicture(elements, driver);
                elements.clear();
            }
            cardList.add(card);
        }

        // Get Hero skills
        for (String name : detailMap.keySet()) {
            String url = detailMap.get(name);
            driver.get(url);
            List<WebElement> effectElements = driver.findElements(By.cssSelector(".related-effects .effect"));
            Card card = cardList.stream().filter(c -> c.getTitle().equals(name)).collect(Collectors.toList()).get(0);
            List<HeroSkill> heroSkills = new ArrayList<>();
            for (WebElement effectElement : effectElements) {
                String effectName = effectElement.findElement(By.className("name")).getText();
                String effectType = effectElement.findElement(By.className("type")).getText();
                String effectText = effectElement.findElement(By.className("text")).getText();
                heroSkills.add(new HeroSkill(effectName, effectType,effectText));
            }
            ((Hero)card).setSkills(heroSkills);
        }

        for (Card card : cardList) {
            lineList.add(card.asLine());
        }


        driver.quit();
        System.out.println("Scraped " + (lineList.size()-1) + " items.");
        System.out.println("Updating hero sigs.");
        for (String[] line : lineList){
            if(sigMap.containsKey(line[0])){
                line[14] = sigMap.get(line[0]);
            }
        }
        csvWriter.writeAll(lineList);
        csvWriter.close();
    }


    private static Card composeCard(WebElement element) {
        String artTypeImg = element.findElement(By.className("art-type")).findElement(By.tagName("img")).getAttribute("src");
        String typeStr = (artTypeImg.split("\\."))[2];
        String[] typeGroup = typeStr.split("-");
        String type;
        String subType;
        if(typeGroup.length >2){
            type = typeGroup[1];
            subType = typeGroup[2];
        }else {
            type = typeGroup[1];
            subType = "";
        }
        type = WordUtils.capitalize(type);
        String title = element.findElement(By.className("name")).getText();
        String url = element.getAttribute("href");
        String color = getColor(element.findElement(By.className("title")).getAttribute("class"));
        String rarityStr = element.findElement(By.className("artifact-icon")).getAttribute("class");
        String rarity = getRarity(rarityStr);
        String cardText = getCardText(element);
        String attack;
        String armor;
        String health;
        String mana;
        String sigOf;
        String effectType;

        switch (type) {
            case "Hero":
                attack = "0";
                armor = "0";
                if(!element.findElements(By.className("attack")).isEmpty()) {
                    attack = element.findElement(By.className("attack")).getText().trim();
                }
                if(!element.findElements(By.className("armor")).isEmpty()) {
                    armor = element.findElement(By.className("armor")).getText().trim();
                }
                health = element.findElement(By.className("health")).getText().trim();


                List<WebElement> skillElementList = element.findElements(By.className("child-effect"));
                if(!skillElementList.isEmpty()){
                    detailMap.put(title, url);
                }

//                for (WebElement skillElement : skillElementList) {
//                    mouseOver(skillElement);
//                    String skillName = skillElement.findElement(By.className("name")).getText();
//
////                                    name, type, text
//
//                    effectType = skillElement.findElement(By.className("type")).getText();
//
//                    String effect = skillElement.findElement(By.className("text")).getText();
//
//                    skills.add(new HeroSkill(skillName, effectType, effect));
//
//                }


                return new Hero(type, title, rarity, attack, armor, health, "", color);
            case "Creep":
                attack = "0";
                armor = "0";
                if(!element.findElements(By.className("attack")).isEmpty()) {
                    attack = element.findElement(By.className("attack")).getText().trim();
                }
                if(!element.findElements(By.className("armor")).isEmpty()) {
                    armor = element.findElement(By.className("armor")).getText().trim();
                }
                health = element.findElement(By.className("health")).getText().trim();
                mana = element.findElement(By.className("mana")).getText();

                effectType = getSpellEffectType(cardText);
                sigOf = getSigOf(element, title);
                return new Creep(title, rarity, mana, attack, armor, health, effectType, cardText, color, sigOf);
            case "Spell":
                mana = element.findElement(By.className("mana")).getText();
                effectType = getSpellEffectType(cardText);
                sigOf = getSigOf(element, title);

                return new Spell(title, rarity, mana, effectType, cardText, color, sigOf);

            case "Improvement":
                effectType = getSpellEffectType(cardText);
                sigOf = getSigOf(element, title);
                mana = element.findElement(By.className("mana")).getText();
                return new Improvement(title, rarity, mana, effectType, cardText, color, sigOf);
            case "Item":
                return getItem(element, subType, title, rarity, cardText);
            default:
                throw new RuntimeException("Unknown type is detected: " + type);
        }
    }

    private static String getCardText(WebElement element) {
        List<WebElement> elementList = element.findElements(By.className("CardText"));
        return elementList.isEmpty()? "" : elementList.get(0).getText().trim();
    }

    private static Card getItem(WebElement element, String subType, String title, String rarity, String cardText) {
        String gold = element.findElement(By.className("gold-cost")).getText().trim();
        switch (subType) {
            case "weapon":
                return new Weapon(title, rarity, gold, cardText);
            case "armor":
                return new Armor(title, rarity, gold, cardText);
            case "accessory":
                return new Accessory(title, rarity, gold, cardText);
            case "consumable":
                return new Consumable(title, rarity, gold, cardText);
            default:
                throw new RuntimeException("Unknown item type: " + subType);
        }
    }

    private static String getSigOf(WebElement element, String title) {
        List<WebElement> parentHero = element.findElements(By.className("parent-hero-circle"));
        String sigOf = "";
        if(!parentHero.isEmpty()) {
            mouseOver(parentHero.get(0));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String heroStr = element.findElement(By.className("TooltipPopup")).getText();
            if(heroStr.startsWith("Hero:")){
                sigOf = heroStr.substring(5).trim();
                sigMap.put(sigOf, title);
            }
        }
        return sigOf;
    }

    private static String getSpellEffectType(String effectStr) {
        if(effectStr.startsWith("Play Effect")) return "Play Effect";
        if(effectStr.startsWith("Death Effect")) return "Death Effect";
        if(effectStr.startsWith("Active")) return "Active Effect";
        return "Normal";
    }

    private static String getColor(String classStr) {
        if(classStr.contains("red")) return "red";
        if(classStr.contains("blue")) return "blue";
        if(classStr.contains("black")) return "black";
        if(classStr.contains("green")) return "green";
        return "";

    }

    private static String getRarity(String rarityStr) {
        if(rarityStr.contains("rare")) return "rare";
        if(rarityStr.contains("uncommon")) return "uncommon";
        if(rarityStr.contains("common")) return "common";
        if(rarityStr.contains("basic")) return "basic";
        return "";
    }

    private static void catchPicture(List<WebElement> elements, WebDriver driver) throws IOException {
        if(!catchScreenshot){
            System.out.println("Skipping screenshot");
            return;
        }
        File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        for (WebElement element : elements) {
            String imageName = getSefName(element) + ".png";
            System.out.println(imageName);
            String screenshot = "output/images/" + imageName;
            Point p = element.getLocation();
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            BufferedImage dest = img.getSubimage(p.getX(), 0, width, height);
            ImageIO.write(dest, "png", screen);
            FileUtils.copyFile(screen, new File(screenshot));
        }

    }

    private static String getSefName(WebElement element) {
        String name = element.findElement(By.className("name")).getText();
        name = convertSef(name);
        return name;
    }

    private static void mouseOver(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public static String convertSef(String original) {
        return original.trim()
                .toLowerCase()
                .replace(" ", "-")
                .replace("'", "-")
                .replace("!", "")
                .replace("...", "");
    }
}
