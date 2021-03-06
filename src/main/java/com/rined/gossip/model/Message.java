package com.rined.gossip.model;

import com.rined.gossip.utils.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity(name = "message")
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "text")
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message to long")
    private String text;

    @Column(name = "tag")
    @Length(max = 255, message = "Tag to long")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "filename")
    private String filename;

    @JoinTable(
            name = "message_likes",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> likes = new HashSet<>();

    public Message(String text, String tag, User user, String filename) {
        this.text = text;
        this.tag = tag;
        this.author = user;
        this.filename = filename;
    }

    public String getAuthorName() {
        return Utils.getAuthorName(author);
    }
}
