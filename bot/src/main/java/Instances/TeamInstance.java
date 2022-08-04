package Instances;

import Enums.TeamStatusEnum;
import Utils.Bot;

public class TeamInstance {

    private int id;
    private String name;
    private String thumbnail;
    private String website;
    private String type;
    private long discord_server_id;
    private long discord_creator_id;
    private long discord_server_member_role_id;
    private String description;
    private TeamStatusEnum status;
    private long last_edit_author;
    private long last_edit_date;
    private TeamStatusEnum last_status;
    private long create_date;

    private int member_count;

    public TeamInstance(int id, String name, String thumbnail, String website, String type, long discord_server_id, long discord_creator_id, long discord_server_member_role_id, String description, TeamStatusEnum status, long last_edit_author, long last_edit_date, TeamStatusEnum last_status, long create_date){

        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.website = website;
        this.type = type;
        this.discord_server_id = discord_server_id;
        this.discord_creator_id = discord_creator_id;
        this.discord_server_member_role_id = discord_server_member_role_id;
        this.description = description;
        this.status = status;
        this.last_edit_author = last_edit_author;
        this.last_edit_date = last_edit_date;
        this.last_status = last_status;
        this.create_date = create_date;

        this.member_count = -1;

    }

    public void recalculateMemberCount(){

        if(discord_server_id != 0 && discord_server_member_role_id != 0){

            Bot.getBot().getServerById(discord_server_id).ifPresent(server -> {

                server.getRoleById(discord_server_member_role_id).ifPresent(role -> {

                    member_count = role.getUsers().size();

                });

            });

        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDiscord_server_id() {
        return discord_server_id;
    }

    public void setDiscord_server_id(long discord_server_id) {
        this.discord_server_id = discord_server_id;
    }

    public long getDiscord_creator_id() {
        return discord_creator_id;
    }

    public void setDiscord_creator_id(long discord_creator_id) {
        this.discord_creator_id = discord_creator_id;
    }

    public long getDiscord_server_member_role_id() {
        return discord_server_member_role_id;
    }

    public void setDiscord_server_member_role_id(long discord_server_member_role_id) {
        this.discord_server_member_role_id = discord_server_member_role_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TeamStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TeamStatusEnum status) {
        this.status = status;
    }

    public long getLast_edit_author() {
        return last_edit_author;
    }

    public void setLast_edit_author(long last_edit_author) {
        this.last_edit_author = last_edit_author;
    }

    public long getLast_edit_date() {
        return last_edit_date;
    }

    public void setLast_edit_date(long last_edit_date) {
        this.last_edit_date = last_edit_date;
    }

    public TeamStatusEnum getLast_status() {
        return last_status;
    }

    public void setLast_status(TeamStatusEnum last_status) {
        this.last_status = last_status;
    }

    public long getCreate_date() {
        return create_date;
    }

    public int getMember_count() {
        return member_count;
    }
}
