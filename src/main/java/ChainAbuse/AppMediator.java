package ChainAbuse;

public class AppMediator {
    private static AppMediator instance;
    private MainController controller;

    private AppMediator() {
    }

    public static AppMediator getInstance() {
        if (instance == null) {
            instance = new AppMediator();
        }
        return instance;
    }

    public void registerController(MainController controller) {
        this.controller = controller;
    }

    public void logMessage(String message) {
        if (controller != null) {
            controller.logArea.appendText(message + "\n");
            LogManager.getInstance().addLog(message);
        }
    }
}
