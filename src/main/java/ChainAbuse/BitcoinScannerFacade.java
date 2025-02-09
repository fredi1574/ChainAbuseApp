package ChainAbuse;

import org.json.JSONArray;

public class BitcoinScannerFacade {
    public static void scanAddress(BitcoinAddress address) {
        String result = ChainAbuseClient.checkAddress(address.getAddress());

        try {
            if (result.trim().startsWith("[")) {
                JSONArray reportsArray = new JSONArray(result);
                if (!reportsArray.isEmpty()) {
                    StringBuilder categories = new StringBuilder();
                    for (int i = 0; i < reportsArray.length(); i++) {
                        String category = reportsArray.getJSONObject(i).optString("scamCategory", "Unknown");
                        if (!categories.toString().contains(category)) {
                            if (!categories.isEmpty()) categories.append(", ");
                            categories.append(category);
                        }
                    }
                    address.setStatus(categories.toString());
                    address.setDetails("Total Reports: " + reportsArray.length());
                    address.setAbuseCount(reportsArray.length());
                } else {
                    address.setStatus("Not Found");
                    address.setDetails("No reports available.");
                    address.setAbuseCount(0);
                }
            }
        } catch (Exception ex) {
            address.setStatus("Error");
            address.setDetails("Error fetching data: " + ex.getMessage());
            address.setAbuseCount(0);
        }
    }
}
