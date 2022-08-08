package Instances;

import Enums.BazaarStatusEnum;
import Enums.BazaarTypeEnum;

public class BazaarInstance {

    private int id;
    private String name;
    private long creator_id;
    private BazaarTypeEnum type;
    private int zip;
    private double price;
    private String images_dir;
    private String description;
    private BazaarStatusEnum status;
    private long create_date;
    private long last_edit_user_id;
    private long last_edit_date;
    private BazaarStatusEnum last_edit_status;

    private String creator_ping;

    public BazaarInstance(int id, String name, long creator_id, BazaarTypeEnum type, int zip, double price, String images_dir, String description, BazaarStatusEnum status, long create_date, long last_edit_user_id, long last_edit_date, BazaarStatusEnum last_edit_status, String creator_ping) {
        this.id = id;
        this.name = name;
        this.creator_id = creator_id;
        this.type = type;
        this.zip = zip;
        this.price = price;
        this.images_dir = images_dir;
        this.description = description;
        this.status = status;
        this.create_date = create_date;
        this.last_edit_user_id = last_edit_user_id;
        this.last_edit_date = last_edit_date;
        this.last_edit_status = last_edit_status;
        this.creator_ping = creator_ping;
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

    public long getCreator_id() {
        return creator_id;
    }

    public BazaarTypeEnum getType() {
        return type;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImages_dir() {
        return images_dir;
    }

    public void setImages_dir(String images_dir) {
        this.images_dir = images_dir;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BazaarStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BazaarStatusEnum status) {
        this.status = status;
    }

    public long getCreate_date() {
        return create_date;
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

    public BazaarStatusEnum getLast_edit_status() {
        return last_edit_status;
    }

    public void setLast_edit_status(BazaarStatusEnum last_edit_status) {
        this.last_edit_status = last_edit_status;
    }

    public String getCreator_ping() {
        return creator_ping;
    }
}
