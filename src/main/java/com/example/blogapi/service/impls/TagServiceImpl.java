package com.example.blogapi.service.impls;

import com.example.blogapi.model.Tag;
import com.example.blogapi.repository.TagRepo;
import com.example.blogapi.service.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;

    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepo.findById(id).orElseThrow();
    }

    @Override
    public Tag createTag(Tag tag) {
        return tagRepo.save(tag);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag foundedTag = tagRepo.findById(id).orElseThrow();
        foundedTag.setName(tag.getName());
        return tagRepo.save(foundedTag);
    }

    @Override
    public int deleteTag(Long id) {
        Tag foundedTag = tagRepo.findById(id).orElseThrow();
        tagRepo.deleteById(foundedTag.getId());
        return 1;
    }
}
