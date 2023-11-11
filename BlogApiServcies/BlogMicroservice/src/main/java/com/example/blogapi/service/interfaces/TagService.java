package com.example.blogapi.service.interfaces;

import com.example.blogapi.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> getAllTags();

    TagDto getTagById(Long id);

    TagDto createTag(TagDto tagDto);

    TagDto updateTag(Long id, TagDto tagDto);

    int deleteTag(Long id);
}
