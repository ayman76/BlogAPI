package com.example.blogapi.service.interfaces;

import com.example.blogapi.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();

    Tag getTagById(Long id);

    Tag createTag(Tag tag);

    Tag updateTag(Long id, Tag tag);

    int deleteTag(Long id);
}
