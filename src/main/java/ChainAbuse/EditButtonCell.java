package ChainAbuse;

import ChainAbuse.BitcoinAddress;
import ChainAbuse.AppMediator;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextInputDialog;

public class EditButtonCell extends TableCell<BitcoinAddress, Void> {
    private final Button editButton = new Button("Edit");

    public EditButtonCell() {
        editButton.setOnAction(event -> {
            BitcoinAddress address = getTableView().getItems().get(getIndex());

            TextInputDialog dialog = new TextInputDialog(address.getAddress());
            dialog.setTitle("Edit Address");
            dialog.setHeaderText("Modify Address");
            dialog.setContentText("Enter new address:");

            dialog.showAndWait().ifPresent(newAddress -> {
                if (!newAddress.trim().isEmpty()) {
                    address.setAddress(newAddress.trim());
                    getTableView().refresh();
                    AppMediator.getInstance().logMessage("Updated address: " + address.getAddress());
                }
            });
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : editButton);
    }
}
