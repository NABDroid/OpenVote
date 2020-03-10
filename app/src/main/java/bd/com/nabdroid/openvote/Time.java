package bd.com.nabdroid.openvote;

class VoteTimes {
    private Long endTime;
    private int voteId;

    public VoteTimes() {
    }

    public VoteTimes(Long endTime, int voteId) {
        this.endTime = endTime;
        this.voteId = voteId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }
}
