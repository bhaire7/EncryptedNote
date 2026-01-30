import database.AdminDB;
import ui.Mainframe;

public class Main {
    public static void main(String[] args) {
        AdminDB.initializeAdmin();
        new Mainframe();
    }
}