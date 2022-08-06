package Instances;

import Enums.BanStatusEnum;

public class BanInstance {

    private long discord_user_id;
    private long discord_admin_id;
    private String discord_admin_nick;
    private String reason;
    private BanStatusEnum status;
    private long last_edit_author;
    private long last_edit_date;
    private BanStatusEnum last_edit_status;
    private String last_edit_reason;
    private long create_date;

    public BanInstance(long discord_user_id, long discord_admin_id, String discord_admin_nick, String reason, BanStatusEnum status, long last_edit_author, long last_edit_date, BanStatusEnum last_edit_status, String last_edit_reason, long create_date) {
        this.discord_user_id = discord_user_id;
        this.discord_admin_id = discord_admin_id;
        this.discord_admin_nick = discord_admin_nick;
        this.reason = reason;
        this.status = status;
        this.last_edit_author = last_edit_author;
        this.last_edit_date = last_edit_date;
        this.last_edit_status = last_edit_status;
        this.last_edit_reason = last_edit_reason;
        this.create_date = create_date;
    }

    public long getDiscord_user_id() {
        return discord_user_id;
    }

    public long getDiscord_admin_id() {
        return discord_admin_id;
    }

    public String getDiscord_admin_nick() {
        return discord_admin_nick;
    }

    public String getReason() {
        return reason;
    }

    public BanStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BanStatusEnum status) {
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

    public BanStatusEnum getLast_edit_status() {
        return last_edit_status;
    }

    public void setLast_edit_status(BanStatusEnum last_edit_status) {
        this.last_edit_status = last_edit_status;
    }

    public String getLast_edit_reason() {
        return last_edit_reason;
    }

    public void setLast_edit_reason(String last_edit_reason) {
        this.last_edit_reason = last_edit_reason;
    }

    public long getCreate_date() {
        return create_date;
    }
}
