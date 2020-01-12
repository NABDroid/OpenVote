package bd.com.nabdroid.openvote;

public class Vote {

    private String topic;
    private int lifeTime;

    public Vote(String topic, int lifeTime) {
        this.topic = topic;
        this.lifeTime = lifeTime;
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

    public Vote() {
    }
}
