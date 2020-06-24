package kr.ac.jejunu.netflix;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer review_id;
    private Integer user_id;
    private String netflix_title;
    private String review_title;
    private String review_content;
    private Integer stars;
}
