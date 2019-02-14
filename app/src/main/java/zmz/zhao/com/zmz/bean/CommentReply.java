package zmz.zhao.com.zmz.bean;

/**
 * date:2019/2/14 15:34
 * author:赵明珠(啊哈)
 * function:评论回复
 */
public class CommentReply {

    private long commentTime;
    private String replyHeadPic;
    private String replyContent;
    private int replyUserId;
    private String replyUserName;

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public String getReplyHeadPic() {
        return replyHeadPic;
    }

    public void setReplyHeadPic(String replyHeadPic) {
        this.replyHeadPic = replyHeadPic;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }
}
