package ChainAbuse;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BitcoinAddress {
    private final SimpleStringProperty address;
    private final SimpleStringProperty status;
    private final SimpleStringProperty details;
    private final SimpleIntegerProperty abuseCount;

    public BitcoinAddress(String address) {
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty("Not Scanned");
        this.details = new SimpleStringProperty("");
        this.abuseCount = new SimpleIntegerProperty(0);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String value) {
        address.set(value);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public String getDetails() {
        return details.get();
    }

    public void setDetails(String value) {
        details.set(value);
    }

    public int getAbuseCount() {
        return abuseCount.get();
    }

    public void setAbuseCount(int value) {
        abuseCount.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty detailsProperty() {
        return details;
    }

    public IntegerProperty abuseCountProperty() {
        return abuseCount;
    }

    public String getReportLink() {
        return "https://www.chainabuse.com/address/" + getAddress();
    }
}
