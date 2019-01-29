package zmz.zhao.com.zmz.bean;

/**
 * date:2019/1/29 13:31
 * author:赵明珠(啊哈)
 * function:影评
 */
public class Comment {

    private int commentId;
    private long commentTime;
    private int greatNum;
    private int replyNum;
    private String commentHeadPic;
    private int hotComment;
    private int isGreat;
    private String movieComment;
    private int commentUserId;
    private String commentUserName;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public int getGreatNum() {
        return greatNum;
    }

    public void setGreatNum(int greatNum) {
        this.greatNum = greatNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public String getCommentHeadPic() {
        return commentHeadPic;
    }

    public void setCommentHeadPic(String commentHeadPic) {
        this.commentHeadPic = commentHeadPic;
    }

    public int getHotComment() {
        return hotComment;
    }

    public void setHotComment(int hotComment) {
        this.hotComment = hotComment;
    }

    public int getIsGreat() {
        return isGreat;
    }

    public void setIsGreat(int isGreat) {
        this.isGreat = isGreat;
    }

    public String getMovieComment() {
        return movieComment;
    }

    public void setMovieComment(String movieComment) {
        this.movieComment = movieComment;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
}
