package driver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DummyDuckDuckGoDriver {

    public static List<String> searchForImages(String query) throws IOException, URISyntaxException {
        String localSearchResultsFile = getLocalSearchResultsFile(query);
        String localSearchResultsJson = Files.readString(Paths.get(DummyDuckDuckGoDriver.class.getResource(localSearchResultsFile).toURI()));

        return DuckDuckGoDriver.parseImageResults(localSearchResultsJson);
    }

    private static String getLocalSearchResultsFile(String query) {
        return "/search/" + query.toLowerCase()
                .replaceAll(" ", "_") + ".json";
    }
}
