package ChainAbuse;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class DeleteButtonCell extends TableCell<BitcoinAddress, Void> {
    private final Button deleteButton = new Button("Delete");

    public DeleteButtonCell() {
        deleteButton.setOnAction(event -> {
            BitcoinAddress address = getTableView().getItems().get(getIndex());
            getTableView().getItems().remove(address);
            getTableView().refresh();
            AppMediator.getInstance().logMessage("Deleted address: " + address.getAddress());
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : deleteButton);
    }
}
