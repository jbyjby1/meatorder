package com.htzg.meatorder.domain.WXWork;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RobotText {

    private String content;

    @JsonProperty("mentioned_list")
    private List<String> mentionedList;

    @JsonProperty("mentioned_mobile_list")
    private List<String> mentionedMobileList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMentionedList() {
        return mentionedList;
    }

    public void setMentionedList(List<String> mentionedList) {
        this.mentionedList = mentionedList;
    }

    public List<String> getMentionedMobileList() {
        return mentionedMobileList;
    }

    public void setMentionedMobileList(List<String> mentionedMobileList) {
        this.mentionedMobileList = mentionedMobileList;
    }
}
