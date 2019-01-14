package playartifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.List;

public class FeedReader {
    public static List<CardObject> loadCardInfo(String cardSetId) throws IOException {
        InputStream inputStream = null;
        ObjectMapper mapper = new ObjectMapper();
        if(fileExists("output/json/" + cardSetId + ".json")){
            File jsonFile = new File("output/json/" + cardSetId + ".json");
            inputStream = new FileInputStream(jsonFile);
        } else{
            String url = "http://playartifact.com/cardset/" + cardSetId;
//            HttpGet httpGet = new HttpGet(url);
//            HttpClient httpClient = HttpClientBuilder.create().build();
//            HttpResponse response = httpClient.execute(httpGet);
            inputStream = readHttp(url);
            CardSetUrlResponse cardSet = mapper.readValue(inputStream, CardSetUrlResponse.class);
            if(cardSet.getCdn_root().endsWith("/")){
                cardSet.setCdn_root(cardSet.getCdn_root().substring(0, cardSet.getCdn_root().length()-1));
            }
            System.out.println(cardSet.getCdn_root() + cardSet.getUrl());
            String seturl = cardSet.getCdn_root() + cardSet.getUrl();
//            response = httpClient.execute(new HttpGet(seturl));
//            inputStream = response.getEntity().getContent();
            inputStream = readHttp(seturl);
            writeFile(inputStream, "output/json/" + cardSetId + ".json");
        }

        CardSetDetailResponse cardSetDetailResponse = mapper.readValue(inputStream, CardSetDetailResponse.class);
        System.out.println(cardSetDetailResponse.getCard_set().getCard_list().size());
        IOUtils.closeQuietly(inputStream);
        return cardSetDetailResponse.getCard_set().getCard_list();
    }

    private static boolean fileExists(String filePath) {
        File f = new File(filePath);
        return f.exists() && !f.isDirectory();
    }

    private static InputStream readHttp(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpGet);
        return response.getEntity().getContent();
    }

    private static void writeFile(InputStream inputStream, String filePath) throws IOException {

        File targetFile = new File(filePath);
        OutputStream outStream = new FileOutputStream(targetFile);

        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        IOUtils.closeQuietly(outStream);
    }
}
