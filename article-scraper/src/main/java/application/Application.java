package application;

import common.helper.FileManager;
import common.model.NewspaperElement;
import org.json.JSONObject;

import java.util.List;

public class Application {


    public static void start(String configPath) {
        try {
            JSONObject configs = FileManager.ReadJsonObject(configPath + "appconfig.json");
            Application.ENABLE_DEBUG = configs.getBoolean("ENABLE_DEBUG");
            Application.ENABLE_LOGS = configs.getBoolean("ENABLE_LOGS");
            Application.LOG_FILE_LOCATION = configs.getString("LOG_FILE_LOCATION");
            Application.STOP_CRAWL = configs.getInt("STOP_CRAWL");
            Application.ARTICLE_ELEMENT_FILE = configs.getString("ARTICLE_ELEMENT_FILE");


            Application.list_article_elements = FileManager.ReadNewspaperElement(Application.ARTICLE_ELEMENT_FILE, ",");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public static boolean ENABLE_DEBUG = true;
    public static boolean ENABLE_LOGS = false;
    public static String LOG_FILE_LOCATION = "log.txt";
    public static int STOP_CRAWL = 10;
    public static String ARTICLE_ELEMENT_FILE = "article_elements.json";

    public static List<NewspaperElement> list_article_elements;

}
