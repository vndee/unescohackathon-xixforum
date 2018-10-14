package crawler.article;

import common.helper.ExceptionNotification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleCrawler {
    static String ignore_entensions = ".*\\.jpg|\\.png|\\.gif|\\.jpeg|\\.mp3|\\.mp4|\\.wav|\\.wma";

    public static Set<String> getLinks(String url) {
        try {

            String domain = new URL(url).getHost();

            Set<String> links = new HashSet<String>();

            Document document = Jsoup.connect(url).get();

            Elements linksOnPage = document.select("a[href]");
            for (Element page : linksOnPage) {
                String childURL = page.attr("abs:href")
                        .replaceAll("#.*", ""); // ignore uri


                Pattern pattern = Pattern.compile(ignore_entensions);
                Matcher matcher = pattern.matcher(childURL);

                if (matcher.find()) {
                    continue;
                }

                if (childURL.contains(domain)) {
                    links.add(childURL);
                }
            }

            return links;

        } catch (Exception ex) {
            ExceptionNotification.printException(ex);
            return new HashSet<String>();
        }
    }

}
