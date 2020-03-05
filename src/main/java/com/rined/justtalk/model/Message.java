package com.rined.justtalk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "message")
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "tag")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "filename")
    private String filename;

    public Message(String text, String tag, User user, String filename) {
        this.text = text;
        this.tag = tag;
        this.author = user;
        this.filename = filename;
    }

    public String getAuthorName() {
        return Objects.isNull(author) ? "<none>" : author.getUsername();
    }
}
