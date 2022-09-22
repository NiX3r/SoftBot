package Instances;

public class ServerOptionInstance {

    private long server_id;
    private long announcement_channel_id;
    private long game_announcement_channel_id;
    private long bazaar_announcement_channel_id;
    private long team_announcement_channel_id;
    private long team_member_role_id;

    public ServerOptionInstance(long server_id, long announcement_channel_id, long game_announcement_channel_id, long bazaar_announcement_channel_id, long team_announcement_channel_id, long team_member_role_id) {
        this.server_id = server_id;
        this.announcement_channel_id = announcement_channel_id;
        this.game_announcement_channel_id = game_announcement_channel_id;
        this.bazaar_announcement_channel_id = bazaar_announcement_channel_id;
        this.team_announcement_channel_id = team_announcement_channel_id;
        this.team_member_role_id = team_member_role_id;
    }

    public long getServer_id() {
        return server_id;
    }

    public long getTeam_member_role_id() {
        return team_member_role_id;
    }

    public void setTeam_member_role_id(long team_member_role_id) {
        this.team_member_role_id = team_member_role_id;
    }

    public long getAnnouncement_channel_id() {
        return announcement_channel_id;
    }

    public void setAnnouncement_channel_id(long announcement_channel_id) {
        this.announcement_channel_id = announcement_channel_id;
    }

    public long getGame_announcement_channel_id() {
        return game_announcement_channel_id;
    }

    public void setGame_announcement_channel_id(long game_announcement_channel_id) {
        this.game_announcement_channel_id = game_announcement_channel_id;
    }

    public long getBazaar_announcement_channel_id() {
        return bazaar_announcement_channel_id;
    }

    public void setBazaar_announcement_channel_id(long bazaar_announcement_channel_id) {
        this.bazaar_announcement_channel_id = bazaar_announcement_channel_id;
    }

    public long getTeam_announcement_channel_id() {
        return team_announcement_channel_id;
    }

    public void setTeam_announcement_channel_id(long team_announcement_channel_id) {
        this.team_announcement_channel_id = team_announcement_channel_id;
    }
}
