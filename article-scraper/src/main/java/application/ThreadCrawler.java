package application;


import common.helper.ExceptionNotification;
import common.helper.FileManager;
import common.model.Article;
import common.model.ArticleSummarize;
import crawler.article.ArticleScraper;

import java.util.List;

public class ThreadCrawler extends Thread {


    private Thread thread;
    private String thread_name;
    private List<String> crawl_urls;

    public ThreadCrawler(String thread_name, List<String> crawl_urls) {

        if (Application.ENABLE_LOGS) {
            FileManager.writeLogs(Application.LOG_FILE_LOCATION, "Creating " + thread_name);
        }

        if (Application.ENABLE_DEBUG) {
            System.out.println("Creating " + thread_name);
        }

        this.thread_name = thread_name;
        this.crawl_urls = crawl_urls;
    }

    public void run () {
        for (String url : crawl_urls) {
            Article article = ArticleScraper.scrape(url);
            ArticleSummarize summarize = ArticleScraper.Summary(article);
        }

        // scrape here


    }

    public void start () {


        if (this.thread == null) {
            this.thread = new Thread (this, this.thread_name);
            this.thread.start ();
        }
    }
}