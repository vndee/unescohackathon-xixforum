package common.model;


public class NewspaperElement {
    public String homepage;
    public String title_element;
    public String description_element;
    public String author_element;
    public String posted_date_element;
    public String content_element;
    public String list_tags_element;
    public String status;

    public NewspaperElement () {
        this.homepage = "";
        this.posted_date_element = "";
        this.title_element = "";
        this.description_element = "";
        this.author_element = "";
        this.content_element = "";
        this.list_tags_element = "";
        this.status = "";
    }

    public NewspaperElement (NewspaperElement clone) {
        this.homepage = clone.homepage;
        this.author_element = clone.author_element;
        this.description_element = clone.description_element;
        this.posted_date_element = clone.posted_date_element;
        this.title_element = clone.title_element;
        this.content_element = clone.content_element;
        this.list_tags_element = clone.list_tags_element;
        this.status = clone.status;
    }

    public static NewspaperElement getElements (String url) {
        return null;
    }
}
