package com.example.categorymicroservice.dto.comment;

import com.example.categorymicroservice.dto.AppUserDto;
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
