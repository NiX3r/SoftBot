package Instances;

import Commands.GameCommand;
import Enums.GameStatusEnum;

import java.time.LocalDateTime;

public class GameInstance {

    private String name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String website;
    private String location;
    private int price;
    private String type;
    private String description;

    private int id;
    private GameStatusEnum status;
    private boolean visibility;
    private long create_user_id;
    private LocalDateTime create_date;
    private long last_edit_user_id;
    private LocalDateTime last_edit_date;
    private GameStatusEnum last_edit_status;

    public GameInstance(String name, long user_id){

        this.name = name;

        this.status = this.last_edit_status = GameStatusEnum.CREATED;
        this.visibility = true;
        this.create_user_id = this.last_edit_user_id = user_id;
        this.create_date = this.last_edit_date = LocalDateTime.now();

        id = -1;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public String getName() {
        return name;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public long getLast_edit_user_id() {
        return last_edit_user_id;
    }

    public void setLast_edit_user_id(long last_edit_user_id) {
        this.last_edit_user_id = last_edit_user_id;
    }

    public LocalDateTime getLast_edit_date() {
        return last_edit_date;
    }

    public void setLast_edit_date(LocalDateTime last_edit_date) {
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

    public long getCreate_user_id() {
        return create_user_id;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public int getId() {
        return id;
    }
}
