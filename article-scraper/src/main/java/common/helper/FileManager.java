package common.helper;

import common.model.NewspaperElement;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileManager {

    /**
     * Read file
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String ReadFile(String fileName) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (IOException ex) {
            return "";
        }
    }

    /**
     * Read newspaper elements from csv file
     * @param fileName
     * @return
     */
    public static List<NewspaperElement> ReadNewspaperElement(String fileName, String delimiter) {

        try {
            List<NewspaperElement> list_elements = new ArrayList<NewspaperElement>();

            // reader
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            // skip header
            br.readLine();

            // read line
            String line = br.readLine();

            // read line
            while (line != null) {
                String[] cols = line.split(delimiter);

                NewspaperElement element = new NewspaperElement();

                // domain
                element.homepage = new URL(cols[0]).getHost();
                element.title_element = cols[1];
                element.description_element = cols[2];
                element.author_element = cols[3];
                element.content_element = cols[4];
                element.posted_date_element = cols[5];
                element.list_tags_element = cols[6];
                element.status = cols[8];

                list_elements.add(element);

                line = br.readLine();
            }
            br.close();
            return list_elements;
        } catch (IOException ex) {
            return new ArrayList<NewspaperElement>();
        }
    }


    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static JSONArray ReadJsonArray(String fileName) throws IOException {
        String data = FileManager.ReadFile(fileName);
        // remove comments
        data = data.replaceAll("\\/\\*.*\\*\\/", "");

        // convert to json array
        JSONArray json_arr = new JSONArray(data);
        return json_arr;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static JSONObject ReadJsonObject(String fileName) throws IOException {
        String data = FileManager.ReadFile(fileName);
        // remove comments
        data = data.replaceAll("\\/\\*.*\\*\\/", "");

        // convert to json array
        JSONObject json_obj = new JSONObject(data);
        return json_obj;
    }

    /**
     * write log file
     * @param file
     * @param contents
     */
    public static void writeLogs(String file, String contents) {
        try {
            // time
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
            Date date = new Date();

            FileWriter fw = new FileWriter(file,true); //the true will append the new data
            // write
            // appends the string to the file
            /**
             *
             * dd/MM/yyyy HH:mm:ss
             * This is logs
             *
             */
            fw.write( dateFormat.format(date) + "\n" + contents + "\n\n");
            fw.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

}