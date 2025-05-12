package Api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GeoLocalisationApi {
    private static final String API_KEY = "a1f1ce28ac384b93852dcffe8416bbd1";

    private static final String BASE_URL = "https://api.opencagedata.com/geocode/v1/json?q=";

    public static String getCoordinates(String location) {
        try {
            String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
            String url = BASE_URL + encodedLocation + "&key=" + API_KEY;

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(url);
                try (CloseableHttpResponse response = client.execute(request)) {
                    String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

                    JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

                    if (jsonObject.getAsJsonArray("results").isEmpty()) {
                        return "Aucun résultat trouvé pour cet emplacement.";
                    }

                    JsonObject geometry = jsonObject
                            .getAsJsonArray("results")
                            .get(0)
                            .getAsJsonObject()
                            .getAsJsonObject("geometry");

                    return "Latitude: " + geometry.get("lat").getAsDouble()
                            + ", Longitude: " + geometry.get("lng").getAsDouble();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la géolocalisation.";
        }
    }
}
