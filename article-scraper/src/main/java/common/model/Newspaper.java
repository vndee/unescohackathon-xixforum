package common.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Newspaper {
    public int id;
    public String homepage;
    public String source;
    public String hashed_source;
    public String title;
    public String description;
    public String content;
    public String author;
    public long posted_date;
    public long updated_date;
    public List<String> hash_tags;
    public String category;
    public boolean is_auto;

    public Newspaper () {
        this.id = 0;
        this.posted_date = Calendar.getInstance().getTimeInMillis();
        this.updated_date = Calendar.getInstance().getTimeInMillis();
        this.hashed_source = "";
        this.title = "";
        this.description = "";
        this.author = "";
        this.source = "";
        this.homepage = "";
        this.content = "";
        this.hash_tags = new ArrayList<String>();
        this.category = "";
        this.is_auto = true;
    }

    public Newspaper (Newspaper clone) {
        this.id = clone.id;
        this.author = clone.author;
        this.description = clone.description;
        this.posted_date = clone.posted_date;
        this.updated_date = clone.updated_date;
        this.title = clone.title;
        this.hashed_source = clone.hashed_source;
        this.homepage = clone.homepage;
        this.source = clone.source;
        this.content = clone.content;
        this.hash_tags = clone.hash_tags;
        this.category = clone.category;
        this.is_auto = clone.is_auto;
    }

    @Override
    public String toString() {
        // format
        DateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");


        StringBuilder sb_article = new StringBuilder();
        sb_article.append("Homepage: ")
                .append(this.homepage)
                .append("\n");
        sb_article.append("Source post: ")
                .append(this.source)
                .append("\n");
        sb_article.append("Source post (hashed): ")
                .append(this.hashed_source)
                .append("\n");

        // short title
        sb_article.append("Title: ")
                .append(this.title)
                .append("\n");
        sb_article.append("Description: ")
                .append(this.description, 0, Math.min(this.description.length(), 70))
                .append("\n");
        sb_article.append("Content: ")
                .append(this.content, 0, Math.min(this.content.length(), 220))
                .append("\n");

        // author and datetime
        sb_article.append("Author: ")
                .append(this.author)
                .append("\n");
        sb_article.append("Posted: ")
                .append(date_format.format(new Date(this.posted_date)))
                .append("\n");

        // add hash tag
        sb_article.append("Tags: ");

        // add hash tags
        for (String tag : this.hash_tags) {
            sb_article.append(tag).append(";");
        }

        // end line
        sb_article.append("\n");

        // list categories
        sb_article.append("Category: ")
                .append(this.category);

        // end line
        sb_article.append("\n");

        sb_article.append("Automatic extractor: ")
                .append(this.is_auto);
        return sb_article.toString();
    }
}
