package driver;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DuckDuckGoDriver {

    private static final Pattern VQD_ID = Pattern.compile("vqd=([\\d-]+)&", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36";

    public static List<String> searchForImages(String query) throws IOException, InterruptedException {
        var htmlQuery = query.replace(' ', '+');
        var searchSource = downloadUrlSource(htmlQuery);
        return extractPhotoUrls(searchSource, htmlQuery);
    }

    private static String downloadUrlSource(String searchQuery) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var uri = URI.create(String.format("https://duckduckgo.com/?q=%s", searchQuery));
        HttpResponse<String> response = sendRequest(client, uri);
        return response.body();
    }

    private static HttpResponse<String> sendRequest(HttpClient client, URI uri) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .header("User-Agent", USER_AGENT)
                .GET()
                .build();
        return client.send(request,
                HttpResponse.BodyHandlers.ofString());
    }

    private static List<String> extractPhotoUrls(String html, String searchQuery) throws IOException, InterruptedException {
        var matcher = VQD_ID.matcher(html);
        if (matcher.find()) {
            String vqdId = matcher.group(1);

            URI uri = URI.create(String.format("https://duckduckgo.com/i.js?q=%s&o=json&vqd=%s", searchQuery, vqdId));
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = sendRequest(client, uri);

            return parseImageResults(response.body());
        }
        throw new IOException("Not valid search source site:" + html);
    }

    static List<String> parseImageResults(String responseJson) {
        var jsonObject = new JSONObject(responseJson);
        var results = jsonObject.getJSONArray("results");
        return StreamSupport.stream(results.spliterator(), false)
                .filter(JSONObject.class::isInstance)
                .map(result -> ((JSONObject) result).getString("image"))
                .collect(Collectors.toList());
    }
}
