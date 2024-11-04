package model;

public class Compatibility {
    private Integer userId;
    private Integer friendId;
    private Integer compatibilityScore;

    public Compatibility(Integer compatibilityScore, Integer friendId, Integer userId) {
        this.compatibilityScore = compatibilityScore;
        this.friendId = friendId;
        this.userId = userId;
    }

    public Integer getCompatibilityScore() {
        return compatibilityScore;
    }

    public void setCompatibilityScore(Integer compatibilityScore) {
        this.compatibilityScore = compatibilityScore;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Compatibility{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", compatibilityScore=" + compatibilityScore +
                '}';
    }
}
