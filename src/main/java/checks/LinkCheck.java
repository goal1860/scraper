package checks;

import dataloader.DbLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Check if the keywords links are all valid.
 */
public class LinkCheck {
    public static void main(String[] args) throws IOException {
        DbLoader.openConnection();
        List<String> linkList = DbLoader.loadKeywordLinks();
        int total=0;
        int notFound=0;
        int others=0;
        for (String link : linkList) {
            System.out.println(link);
            int code = getHttpCode(link);
            total ++;
            if(code == 404){
                notFound++;
            }else{
                if (code != 200){
                    others ++;
                }
            }

            System.out.println(code);

        }
        DbLoader.closeConnection();
        System.out.println("" + total + " links checked. 404: " + notFound + ", Other bad links: " + others);

    }

    private static int getHttpCode(String link) throws IOException {
        URL url = new URL("https://artifact.game" + link);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();
        return code;
    }
}
