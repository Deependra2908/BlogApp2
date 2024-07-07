package com.blog.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="comments") // isse table create ho jayegi comments name ki
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String body;
    private String email;
    private String name;

    @ManyToOne                    // isse bout sari comment post se connect ho jayengi
    @JoinColumn(name = "post_id") // isse table join hoti hai jaise postid ek dusri table me create ho jayegi
    private Post post;
}
