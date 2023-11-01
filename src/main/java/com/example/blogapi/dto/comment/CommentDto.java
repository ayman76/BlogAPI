package com.example.blogapi.dto.comment;

import com.example.blogapi.dto.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String body;
    private AppUserDto appUser;
}
