package ChainAbuse;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ChainAbuseClient {
    private static final String API_URL = "https://api.chainabuse.com/v0/reports";
    private static final String API_KEY = "Y2FfYlhsd1pYTnVXV1ZqZFRkRFEwaDBkVXN4V0VSWmFFMUNMa0p2WjFsQldVRk1VUzlsY21aUFNVTkpOR0ZJYlZFOVBROmNhX2JYbHdaWE51V1dWamRUZERRMGgwZFVzeFdFUlphRTFDTGtKdloxbEJXVUZNVVM5bGNtWlBTVU5KTkdGSWJWRTlQUQ==";

    public static String checkAddress(String bitcoinAddress) {
        String requestUrl = API_URL + "?includePrivate=false&page=1&perPage=50&address=" + bitcoinAddress;

        try {
            HttpResponse<String> response = Unirest.get(requestUrl)
                    .header("Accept", "application/json")
                    .header("Authorization", "Basic " + API_KEY)
                    .asString();

            System.out.println("Response Code: " + response.getStatus()); // Debugging
            System.out.println("Response Body: " + response.getBody());  // Debugging

            if (response.getStatus() == 200) {
                return response.getBody();
            } else {
                return "{\"status\":\"error\",\"details\":\"HTTP " + response.getStatus() + " - " + response.getStatusText() + "\"}";
            }
        } catch (Exception e) {
            return "{\"status\":\"error\",\"details\":\"" + e.getMessage() + "\"}";
        }
    }
}
