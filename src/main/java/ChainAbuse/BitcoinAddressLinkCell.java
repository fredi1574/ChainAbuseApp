package ChainAbuse;

import javafx.application.HostServices;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;

public class BitcoinAddressLinkCell extends TableCell<BitcoinAddress, String> {
    private final Hyperlink link = new Hyperlink();

    public BitcoinAddressLinkCell(HostServices hostServices) {

        link.setOnAction(event -> {
            if (getItem() != null && hostServices != null) {
                String url = "https://www.chainabuse.com/address/" + getItem();
                hostServices.showDocument(url);
            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            link.setText(item);
            setGraphic(link);
        }
    }
}
