package model;

import java.util.List;

/**
 * Created by mac on 2018/4/3.
 */

public class GithubBean {

    /**
     * _id : 5a967b41421aa91071b838f7
     * createdAt : 2018-02-28T17:49:53.265Z
     * desc : MusicLibrary-一个丰富的音频播放SDK
     * publishedAt : 2018-03-12T08:44:50.326Z
     * source : web
     * type : Android
     * url : https://github.com/lizixian18/MusicLibrary
     * used : true
     * who : lizixian
     * images : ["http://img.gank.io/90db2f35-2e9d-4d75-b5a9-53ee1719b57b"]
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    private GithubBean(Builder builder) {
        set_id(builder._id);
        setCreatedAt(builder.createdAt);
        setDesc(builder.desc);
        setPublishedAt(builder.publishedAt);
        setSource(builder.source);
        setType(builder.type);
        setUrl(builder.url);
        setUsed(builder.used);
        setWho(builder.who);
        setImages(builder.images);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public static final class Builder {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public Builder() {
        }

        public Builder _id(String val) {
            _id = val;
            return this;
        }

        public Builder createdAt(String val) {
            createdAt = val;
            return this;
        }

        public Builder desc(String val) {
            desc = val;
            return this;
        }

        public Builder publishedAt(String val) {
            publishedAt = val;
            return this;
        }

        public Builder source(String val) {
            source = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder used(boolean val) {
            used = val;
            return this;
        }

        public Builder who(String val) {
            who = val;
            return this;
        }

        public Builder images(List<String> val) {
            images = val;
            return this;
        }

        public GithubBean build() {
            return new GithubBean(this);
        }
    }
}
