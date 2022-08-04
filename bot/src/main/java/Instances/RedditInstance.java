package Instances;

import Utils.Bot;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Random;

public class RedditInstance {

    private ArrayList<RedditPostInstance> redditPosts;

    public RedditInstance(){

        Utils.LogSystem.log(Bot.getPrefix(), "initializing reddit posts", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        redditPosts = new ArrayList<>();
        loadRedditPosts();

    }

    public void loadRedditPosts(){

        JsonParser parser = new JsonParser();
        Utils.LogSystem.log(Bot.getPrefix(), "loading data from Reddit", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        JsonElement element = parser.parse(readHtmlContent());
        JsonArray children = element.getAsJsonObject().getAsJsonObject("data").getAsJsonArray("children");
        Utils.LogSystem.log(Bot.getPrefix(), "data from Reddit loaded. Looping and mapping it", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        for(int i = 0; i < children.size(); i++){

            JsonElement post = children.get(i);
            RedditPostInstance redditPost = new RedditPostInstance(
                    post.getAsJsonObject().getAsJsonObject("data").get("title").getAsString(),
                    post.getAsJsonObject().getAsJsonObject("data").get("author_fullname").getAsString(),
                    post.getAsJsonObject().getAsJsonObject("data").get("permalink").getAsString(),
                    ""
            );

            if(!post.getAsJsonObject().getAsJsonObject("data").get("is_video").getAsBoolean()){
                String content = post.getAsJsonObject().getAsJsonObject("data").get("url").getAsString();

                if(!content.substring(content.length() - 6, content.length() - 1).contains(".")){
                    content = post.getAsJsonObject().getAsJsonObject("data").get("thumbnail").getAsString();
                }
                redditPost.setContent(content);
                redditPosts.add(redditPost);
            }

        }
        Utils.LogSystem.log(Bot.getPrefix(), "Data successfully mapped", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }

    private String readHtmlContent(){

        URL url = null;
        String output = "";
        try {
            url = new URL("https://www.reddit.com/r/airsoft/.json?limit=100");
            URLConnection spoof = url.openConnection();


            spoof.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)" );
            BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
            String strLine = "";

            while ((strLine = in.readLine()) != null){

                output += strLine + "\n";

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.substring(0, output.length() -1);

    }

    public RedditPostInstance getRandomPost(){

        Random r = new Random();
        int rn = r.nextInt(redditPosts.size() - 1);

        return this.redditPosts.get(rn);

    }

    public ArrayList<RedditPostInstance> getRedditPosts() {
        return redditPosts;
    }

    public void setRedditPosts(ArrayList<RedditPostInstance> redditPosts) {
        this.redditPosts = redditPosts;
    }
}
