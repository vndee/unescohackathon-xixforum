package crawler.article;

import application.Application;
import common.helper.ExceptionNotification;
import common.helper.FileManager;
import common.helper.TextHelper;
import common.model.Article;
import common.model.ArticleSummarize;
import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.TextBlock;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import de.l3s.boilerpipe.sax.HTMLHighlighter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;


public class ArticleScraper {




    public static Article scrapeArticle(String url) {
        try {
            HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(url));
            TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
            String content = ArticleExtractor.INSTANCE.getText(doc);

            Article article = new Article();

            article.title = doc.getTitle();
            article.source = url;
            article.encoded_source = TextHelper.toSHA1(url);
            article.updated_date = Calendar.getInstance().getTimeInMillis();

            // find posted date


            return article;

        } catch (Exception ex) {
            if (Application.ENABLE_LOGS) {
                FileManager.writeLogs(Application.LOG_FILE_LOCATION, ex.getMessage());
            }

            if (Application.ENABLE_DEBUG) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }

            return new Article();
        }
    }

    /**
     * scrape article from url
     * extract title, content,...
     * @param string_url
     * @return
     */
    public static Article scrape (String string_url) {
        try {

            Article article = new Article();

            URL url = new URL(string_url);

            final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);

            final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;

            final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance();
            hh.setOutputHighlightOnly(true);

            TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
            extractor.process(doc);
            final InputSource is = htmlDoc.toInputSource();
            article.html_text = hh.process(doc, is);
            article.source = string_url;
            article.title = doc.getTitle();
            article.encoded_source = TextHelper.toSHA1(string_url);
            return article;
        } catch (Exception ex) {
            ExceptionNotification.printException(ex);
            return new Article();
        }
    }


    public static ArticleSummarize Summary(Article article) {
        Summarizer summarizer = new Summarizer("en"); // initialize summarizer with specific language
        String result = summarizer.summarize(title, text, 5, "\n");

        return new ArticleSummarize();
    }



}
