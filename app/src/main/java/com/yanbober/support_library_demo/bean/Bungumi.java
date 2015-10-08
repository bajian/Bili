package com.yanbober.support_library_demo.bean;

import java.util.List;

/**
 * Created by hgx on 2015/7/13.
 */
public class Bungumi {

    /**
     * ver : 2023
     * code : 0
     * count : 166
     * screen : xxhdpi
     * list : [{"weburl":"http://www.bilibili.com/html/bangumi201507.html","imagekey":"b882d702afa92487fce36c881032574c","imageurl":"http://i1.hdslb.com/468_467/u_user/717b9e398a5357a182951d424ef0a958.jpg","width":468,"remark":"bilibili正版7月新番","style":"medium","title":"bilibili正版7月新番","type":"weblink","remark2":"","height":467},{"spname":"噬神者","imagekey":"d80d8bcc686b1c370c00efea3a2ff100","imageurl":"http://i2.hdslb.com/480_674/u_user/82a7bc1c29dae3018387fdcadc904feb.jpg","width":480,"remark":"噬神者","style":"medium","title":"噬神者","type":"bangumi","spid":6249,"remark2":"","height":674}
     */
    private String ver;
    private int code;
    private int count;
    private String screen;
    private List<ListEntity> list;

    public void setVer(String ver) {
        this.ver = ver;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public String getVer() {
        return ver;
    }

    public int getCode() {
        return code;
    }

    public int getCount() {
        return count;
    }

    public String getScreen() {
        return screen;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public class ListEntity {
        /**
         * weburl : http://www.bilibili.com/html/bangumi201507.html
         * imagekey : b882d702afa92487fce36c881032574c
         * imageurl : http://i1.hdslb.com/468_467/u_user/717b9e398a5357a182951d424ef0a958.jpg
         * width : 468
         * remark : bilibili正版7月新番
         * style : medium
         * title : bilibili正版7月新番
         * type : weblink
         * remark2 :
         * height : 467
         */
        private String weburl;
        private String imagekey;
        private String imageurl;
        private int width;
        private String remark;
        private String style;
        private String title;
        private String type;
        private String remark2;
        private int height;

        public void setWeburl(String weburl) {
            this.weburl = weburl;
        }

        public void setImagekey(String imagekey) {
            this.imagekey = imagekey;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRemark2(String remark2) {
            this.remark2 = remark2;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getWeburl() {
            return weburl;
        }

        public String getImagekey() {
            return imagekey;
        }

        public String getImageurl() {
            return imageurl;
        }

        public int getWidth() {
            return width;
        }

        public String getRemark() {
            return remark;
        }

        public String getStyle() {
            return style;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public String getRemark2() {
            return remark2;
        }

        public int getHeight() {
            return height;
        }
    }
}
