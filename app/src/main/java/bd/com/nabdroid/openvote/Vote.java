package bd.com.nabdroid.openvote;

public class Vote {

    private String voteCode, topic, creatorId, creatorName;
    private int endTime, yesVote, noVote;

    public Vote(String voteCode, String topic, String creatorId, String creatorName, int endTime, int yesVote, int noVote) {
        this.voteCode = voteCode;
        this.topic = topic;
        this.creatorId = creatorId;
        this.endTime = endTime;
        this.yesVote = yesVote;
        this.noVote = noVote;
        this.creatorName = creatorName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Vote() {

    }

    public String getVoteCode() {
        return voteCode;
    }

    public void setVoteCode(String voteCode) {
        this.voteCode = voteCode;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getYesVote() {
        return yesVote;
    }

    public void setYesVote(int yesVote) {
        this.yesVote = yesVote;
    }

    public int getNoVote() {
        return noVote;
    }

    public void setNoVote(int noVote) {
        this.noVote = noVote;
    }
}
