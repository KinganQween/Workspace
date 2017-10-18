package com.edu.nbl.myjson;

/**
 * Created by Administrator on 17-9-19.
 */

public class News1 {
    @Override
    public String toString() {
        return "News1{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", url='" + url + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", caption='" + caption + '\'' +
                ", avatar='" + avatar + '\'' +
                ", plays_count=" + plays_count +
                ", comments_count=" + comments_count +
                ", likes_count=" + likes_count +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    /**
     * id : 855591957
     * user_id : 57717896
     * url : http://www.meipai.com/media/855591957
     * cover_pic : http://mvimg1.meitudata.com/59bcc921bcd196928.jpg!thumb320
     * screen_name : 喵星人の喵
     * caption : 我们家的樱花比较挑剔！这么多衣服就喜欢这个😂还有更多衣服秀！敬请期待呦😊
     * avatar : http://mvavatar1.meitudata.com/56f79d4d4fd3b2011.jpg
     * plays_count : 5616
     * comments_count : 17
     * likes_count : 173
     * created_at : 1505544556
     */

    private int id;
    private int user_id;
    private String url;
    private String cover_pic;
    private String screen_name;
    private String caption;
    private String avatar;
    private int plays_count;
    private int comments_count;
    private int likes_count;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPlays_count() {
        return plays_count;
    }

    public void setPlays_count(int plays_count) {
        this.plays_count = plays_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
