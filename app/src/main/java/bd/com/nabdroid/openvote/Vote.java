package bd.com.nabdroid.openvote;

public class Vote {

    private String title, topic;
    private int lifeTime;

    public Vote() {
    }

    public Vote(String title, String topic, int lifeTime) {
        this.title = title;
        this.topic = topic;
        this.lifeTime = lifeTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }


}
