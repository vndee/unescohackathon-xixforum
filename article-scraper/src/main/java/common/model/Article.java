package common.model;

import common.helper.ExceptionNotification;
import common.helper.JsoupDocumentExtractor;
import org.jsoup.Jsoup;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Article {
    public int id;
    public String source;
    public String encoded_source;
    public String title;
    public String html_text;
    public long updated_date;

    public Article () {
        this.id = 0;
        this.source = "";
        this.encoded_source = "";
        this.title = "";
        this.html_text = "";
        this.updated_date = Calendar.getInstance().getTimeInMillis();;
    }

    public Article (Article clone) {
        this.id = clone.id;
        this.source = clone.source;
        this.encoded_source = clone.encoded_source;
        this.title = clone.title;
        this.html_text = clone.html_text;
        this.updated_date = clone.updated_date;
    }


    @Override
    public String toString() {
        // format
        DateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");


        StringBuilder sb_article = new StringBuilder();
        sb_article.append("Source post: ")
                .append(this.source)
                .append("\n");
        sb_article.append("Source post (encoded): ")
                .append(this.encoded_source)
                .append("\n");

        // short title
        sb_article.append("Title: ")
                .append(this.title)
                .append("\n");


        sb_article.append("HTML: ")
                .append(this.html_text.substring(0, Math.min(this.html_text.length(), 220)))
                .append("\n");

        return sb_article.toString();
    }

    /**
     * Get only text
     * keep end lines
     * @return
     */
    public String getPlainText() {
        // return this.html_text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "");
        return Jsoup.parse(this.html_text).prepend("\\n")
                .text()
                .replaceAll("\\\\n", "\n")
                .trim();
    }


    /**
     * Convert to Newspaper
     * Parse html to find title, abstract, posted day, contents,...
     * @return
     */
    public Newspaper toNewspaper() {
        Newspaper newspaper = new Newspaper();

        newspaper.is_auto = true;
        JsoupDocumentExtractor extractor = new JsoupDocumentExtractor(this.html_text);

        newspaper.updated_date = this.updated_date;
        newspaper.source = this.source;
        newspaper.hashed_source = this.encoded_source;
        try {
            newspaper.homepage = new URL(this.source).getHost();
        } catch (Exception ex) {
            ExceptionNotification.printException(ex);
        }

        // các thông tin rút từ html
        newspaper.title = extractor.getTitle().isEmpty() ? this.title : extractor.getTitle();
        newspaper.description = extractor.getDescription();
        newspaper.posted_date = extractor.getPostedDate().getTime();
        newspaper.author = extractor.getAuthor();
        newspaper.content = extractor.getMainContain().isEmpty() ? this.getPlainText() : extractor.getMainContain();

        // new object
        return newspaper;
    }


    public boolean isEmpty() {
        if (this.toNewspaper().content.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


}
