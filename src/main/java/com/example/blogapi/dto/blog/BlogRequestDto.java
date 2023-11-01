package com.example.blogapi.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequestDto {
    private String id;
    private String title;
    private String description;
    private String slug;
    private Long categoryId;
    private Long userId;
    private List<Long> tags;
}
