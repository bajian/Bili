package com.yanbober.support_library_demo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 新番放送
 * http://app.bilibili.com/bangumi/timeline_v2,URL_Time_Line
 * Created by hgx on 2015/10/16.
 */
public class TimeLineBean {

    private int code;
    private int count;
    private String message;
    private int results;
    private List<ListEntity> list;

    public void setCode(int code) {
        this.code = code;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public int getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }

    public int getResults() {
        return results;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        /**
         * area : 日本
         * arealimit : 0
         * attention : 167477
         * bangumi_id : 1844
         * bgmcount : 3
         * brief : null
         * cover : http://i0.hdslb.com/u_user/8fcb85c13671d5c234065f93c3e9781a.jpg
         * danmaku_count : 67659
         * favorites : 167477
         * is_finish : 0
         * lastupdate : 1444979400
         * lastupdate_at : 2015-10-16 15:10:00
         * new : true
         * play_count : 1408167
         * pub_time :
         * season_id : 2720
         * spid : 56676
         * square_cover : http://i1.hdslb.com/sp/b8/b83b0ec67b1161f86e9eb539fb856cb8.jpg
         * title : 青年黑杰克
         * url : /bangumi/i/2720/
         * weekday : 5
         */

        private String area;
        private int arealimit;
        private int attention;
        private int bangumi_id;
        private String bgmcount;
        private Object brief;
        private String cover;
        private int danmaku_count;
        private int favorites;
        private int is_finish;
        private int lastupdate;
        private String lastupdate_at;
        @SerializedName("new")
        private boolean newX;
        private int play_count;
        private String pub_time;
        private int season_id;
        private int spid;
        private String square_cover;
        private String title;
        private String url;
        private int weekday;

        public void setArea(String area) {
            this.area = area;
        }

        public void setArealimit(int arealimit) {
            this.arealimit = arealimit;
        }

        public void setAttention(int attention) {
            this.attention = attention;
        }

        public void setBangumi_id(int bangumi_id) {
            this.bangumi_id = bangumi_id;
        }

        public void setBgmcount(String bgmcount) {
            this.bgmcount = bgmcount;
        }

        public void setBrief(Object brief) {
            this.brief = brief;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setDanmaku_count(int danmaku_count) {
            this.danmaku_count = danmaku_count;
        }

        public void setFavorites(int favorites) {
            this.favorites = favorites;
        }

        public void setIs_finish(int is_finish) {
            this.is_finish = is_finish;
        }

        public void setLastupdate(int lastupdate) {
            this.lastupdate = lastupdate;
        }

        public void setLastupdate_at(String lastupdate_at) {
            this.lastupdate_at = lastupdate_at;
        }

        public void setNewX(boolean newX) {
            this.newX = newX;
        }

        public void setPlay_count(int play_count) {
            this.play_count = play_count;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }

        public void setSeason_id(int season_id) {
            this.season_id = season_id;
        }

        public void setSpid(int spid) {
            this.spid = spid;
        }

        public void setSquare_cover(String square_cover) {
            this.square_cover = square_cover;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setWeekday(int weekday) {
            this.weekday = weekday;
        }

        public String getArea() {
            return area;
        }

        public int getArealimit() {
            return arealimit;
        }

        public int getAttention() {
            return attention;
        }

        public int getBangumi_id() {
            return bangumi_id;
        }

        public String getBgmcount() {
            return bgmcount;
        }

        public Object getBrief() {
            return brief;
        }

        public String getCover() {
            return cover;
        }

        public int getDanmaku_count() {
            return danmaku_count;
        }

        public int getFavorites() {
            return favorites;
        }

        public int getIs_finish() {
            return is_finish;
        }

        public int getLastupdate() {
            return lastupdate;
        }

        public String getLastupdate_at() {
            return lastupdate_at;
        }

        public boolean getNewX() {
            return newX;
        }

        public int getPlay_count() {
            return play_count;
        }

        public String getPub_time() {
            return pub_time;
        }

        public int getSeason_id() {
            return season_id;
        }

        public int getSpid() {
            return spid;
        }

        public String getSquare_cover() {
            return square_cover;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public int getWeekday() {
            return weekday;
        }
    }
}
