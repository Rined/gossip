package com.rined.justtalk.model.dto;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.utils.Utils;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"id", "author", "likes", "meLiked"})
public class MessageDto {
    private final long id;
    private final String text;
    private final String tag;
    private final User author;
    private final String filename;
    private final Long likes;
    private final Boolean meLiked;

    public MessageDto(Message msg, Long likes, Boolean meLiked) {
        this.id = msg.getId();
        this.text = msg.getText();
        this.tag = msg.getTag();
        this.author = msg.getAuthor();
        this.filename = msg.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return Utils.getAuthorName(author);
    }
}
