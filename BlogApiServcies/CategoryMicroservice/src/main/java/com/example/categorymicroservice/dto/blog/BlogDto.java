package com.example.categorymicroservice.dto.blog;

import com.example.categorymicroservice.dto.AppUserDto;
import com.example.categorymicroservice.dto.CategoryDto;
import com.example.categorymicroservice.dto.TagDto;
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
