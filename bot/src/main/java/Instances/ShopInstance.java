package Instances;

import Enums.ShopStatusEnum;

public class ShopInstance {

    private int id;
    private String name;
    private String voucher;
    private String ip_address;
    private String user_id;
    private String thumbnail;
    private String website;
    private String location;
    private int zip;
    private String description;

    private ShopStatusEnum status;
    private long create_date;
    private long last_edit_user_id;
    private long last_edit_date;

    public ShopInstance(int id, String name, String voucher, String ip_address, String user_id, String website, String location, int zip, String description, ShopStatusEnum status, long create_date, long last_edit_user_id, long last_edit_date, ShopStatusEnum last_edit_status, String thumbnail) {
        this.id = id;
        this.name = name;
        this.voucher = voucher;
        this.ip_address = ip_address;
        this.user_id = user_id;
        this.website = website;
        this.location = location;
        this.zip = zip;
        this.description = description;
        this.status = status;
        this.create_date = create_date;
        this.last_edit_user_id = last_edit_user_id;
        this.last_edit_date = last_edit_date;
        this.thumbnail = thumbnail;
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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShopStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ShopStatusEnum status) {
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
