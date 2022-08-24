package Instances;

import Enums.GameStatusEnum;

public class GameInstance {

    private int id;

    private String name;
    private String thumbnail;
    private String ip_address;
    private long start_date;
    private long end_date;
    private String repeat_date;
    private String website;
    private String location;
    private float price;
    private String type;
    private String description;

    private GameStatusEnum status;
    private long create_date;
    private long last_edit_user_id;
    private long last_edit_date;
    private GameStatusEnum last_edit_status;

    public GameInstance(int id, String name, String thumbnail, String ip_address, long start_date, long end_date, String repeat_date, String website, String location, float price, String type, String description, GameStatusEnum status, long last_edit_date, long last_edit_user_id, GameStatusEnum last_edit_status, long create_date){

        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.ip_address = ip_address;
        this.start_date = start_date;
        this.end_date = end_date;
        this.repeat_date = repeat_date;
        this.website = website;
        this.location = location;
        this.price = price;
        this.type = type;
        this.description = description;
        this.status = status;
        this.create_date = create_date;
        this.last_edit_user_id = last_edit_user_id;
        this.last_edit_date = last_edit_date;
        this.last_edit_status = last_edit_status;


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public String getName() {
        return name;
    }

    public long getLast_edit_user_id() {
        return last_edit_user_id;
    }

    public void setLast_edit_user_id(long last_edit_user_id) {
        this.last_edit_user_id = last_edit_user_id;
    }

    public long getLast_edit_date() {
        return last_edit_date;
    }

    public void setLast_edit_date(long last_edit_date) {
        this.last_edit_date = last_edit_date;
    }

    public GameStatusEnum getLast_edit_status() {
        return last_edit_status;
    }

    public void setLast_edit_status(GameStatusEnum last_edit_status) {
        this.last_edit_status = last_edit_status;
    }

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }

    public long getCreate_date() {
        return create_date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRepeat_date() {
        return repeat_date;
    }

    public void setRepeat_date(String repeat_date) {
        this.repeat_date = repeat_date;
    }

    public int getId() {
        return id;
    }

    public String getIp_address() {
        return ip_address;
    }
}
