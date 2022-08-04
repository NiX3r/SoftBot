package Instances;

public class AdminInstance {

    private String nick;
    private long discord_id;
    private long create_date;

    public AdminInstance(String nick, long discord_id, long create_date){
        this.nick = nick;
        this.discord_id = discord_id;
        this.create_date = create_date;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getDiscord_id() {
        return discord_id;
    }

    public long getCreate_date() {
        return create_date;
    }
}
