package playartifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class FeedReader {
    public static List<CardObject> loadCardInfo(String cardSetId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://playartifact.com/cardset/" + cardSetId;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpGet);
        CardSetUrlResponse cardSet = mapper.readValue(response.getEntity().getContent(), CardSetUrlResponse.class);
        if(cardSet.getCdn_root().endsWith("/")){
            cardSet.setCdn_root(cardSet.getCdn_root().substring(0, cardSet.getCdn_root().length()-1));
        }
        System.out.println(cardSet.getCdn_root() + cardSet.getUrl());
        String seturl = cardSet.getCdn_root() + cardSet.getUrl();
        response = httpClient.execute(new HttpGet(seturl));
        CardSetDetailResponse cardSetDetailResponse = mapper.readValue(response.getEntity().getContent(), CardSetDetailResponse.class);
        System.out.println(cardSetDetailResponse.getCard_set().getCard_list().size());
        return cardSetDetailResponse.getCard_set().getCard_list();
    }
}
