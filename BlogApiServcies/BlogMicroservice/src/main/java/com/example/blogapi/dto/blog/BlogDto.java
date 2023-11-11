package com.example.blogapi.dto.blog;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.dto.CategoryDto;
import com.example.blogapi.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private Long id;
    private String title;
    private String description;
    private String slug;
    private AppUserDto appUser;
    private CategoryDto category;
    private List<TagDto> tags;
}
