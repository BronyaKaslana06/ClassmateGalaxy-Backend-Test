package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wyx20
 * @version 1.0
 * @title PostUpdateDto
 * @description
 * @create 2024/1/12 1:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {
    private Integer postId;
    private String title;
    private String content;
    private String photoUrl;
}
