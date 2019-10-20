package com.htzg.meatorder.domain.WXWork;

public class RobotSendMessage {

    private String msgtype;

    private RobotText text;

    private RobotMarkdown markdown;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public RobotText getText() {
        return text;
    }

    public void setText(RobotText text) {
        this.text = text;
    }

    public RobotMarkdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(RobotMarkdown markdown) {
        this.markdown = markdown;
    }
}
