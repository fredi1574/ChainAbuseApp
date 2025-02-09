package ChainAbuse;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainController {

    private final ObservableList<BitcoinAddress> addressList = FXCollections.observableArrayList();
    @FXML
    TextArea logArea;
    @FXML
    private TableView<BitcoinAddress> table;
    @FXML
    private TableColumn<BitcoinAddress, String> addressColumn;
    @FXML
    private TableColumn<BitcoinAddress, String> statusColumn;
    @FXML
    private TableColumn<BitcoinAddress, String> detailsColumn;
    @FXML
    private TableColumn<BitcoinAddress, Integer> abuseCountColumn;
    @FXML
    private TableColumn<BitcoinAddress, String> linkColumn;
    @FXML
    private TableColumn<BitcoinAddress, Void> editColumn;
    @FXML
    private TableColumn<BitcoinAddress, Void> deleteColumn;
    @FXML
    private TextField addressInputField;

    private HostServices hostServices;

    @FXML
    public void initialize() {
        AppMediator.getInstance().registerController(this);

        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        detailsColumn.setCellValueFactory(cellData -> cellData.getValue().detailsProperty());
        abuseCountColumn.setCellValueFactory(cellData -> cellData.getValue().abuseCountProperty().asObject());

        linkColumn.setCellFactory(tc -> new BitcoinAddressLinkCell(hostServices));
        editColumn.setCellFactory(tc -> new EditButtonCell());
        deleteColumn.setCellFactory(tc -> new DeleteButtonCell());

        linkColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        table.setItems(addressList);
        loadLogs();
    }

    private void loadLogs() {
        List<String> logs = LogManager.getInstance().getLogs();
        logArea.clear();
        for (String logEntry : logs) {
            logArea.appendText(logEntry + "\n");
        }
    }

    @FXML
    private void addAddress() {
        String address = addressInputField.getText().trim();
        if (!address.isEmpty()) {
            BitcoinAddress addr = BitcoinAddressFactory.createBitcoinAddress(address);
            addressList.add(addr);
            AppMediator.getInstance().logMessage("Manually added address: " + address);
            addressInputField.clear();
        }
    }

    @FXML
    private void clearTable() {
        addressList.clear();
        AppMediator.getInstance().logMessage("Cleared all addresses from table.");
    }

    @FXML
    private void loadAddresses() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Address File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                addressList.clear();
                while ((line = reader.readLine()) != null) {
                    BitcoinAddress addr = BitcoinAddressFactory.createBitcoinAddress(line.trim());
                    addressList.add(addr);
                }
                AppMediator.getInstance().logMessage("Loaded addresses from file: " + file.getName());
            } catch (IOException ex) {
                AppMediator.getInstance().logMessage("Error reading file: " + ex.getMessage());
            }
        }
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    private void scanAddresses() {
        for (BitcoinAddress addr : addressList) {
            BitcoinScannerFacade.scanAddress(addr);
            AppMediator.getInstance().logMessage("Scanned address: " + addr.getAddress());
        }
        table.refresh();
        LogManager.getInstance().saveLogs();
    }

    @FXML
    private void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            ExcelExporter.exportToExcel(addressList, file.getAbsolutePath());
            AppMediator.getInstance().logMessage("Exported data to " + file.getAbsolutePath());
        } else {
            AppMediator.getInstance().logMessage("Export operation cancelled.");
        }
    }
}
