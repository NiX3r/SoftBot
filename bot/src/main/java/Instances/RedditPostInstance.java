package Instances;

public class RedditPostInstance {

    private String topic;
    private String author;
    private String link;
    private String content;

    public RedditPostInstance(String topic, String author, String link, String content){

        this.topic = topic;
        this.author = author;
        this.link = link;
        this.content = content;

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
