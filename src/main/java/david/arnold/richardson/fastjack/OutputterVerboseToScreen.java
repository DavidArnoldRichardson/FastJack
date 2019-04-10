package david.arnold.richardson.fastjack;

public class OutputterVerboseToScreen extends Outputter {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
