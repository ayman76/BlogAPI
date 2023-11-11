package com.example.blogapi.service.impls;

import com.example.blogapi.dto.TagDto;
import com.example.blogapi.model.Tag;
import com.example.blogapi.repository.TagRepo;
import com.example.blogapi.service.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final ModelMapper modelMapper;
    private final TagRepo tagRepo;

    @Override
    public List<TagDto> getAllTags() {
        return tagRepo.findAll().stream().map(t -> modelMapper.map(t, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto getTagById(Long id) {
        Tag foundedTag = tagRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Tag with Id: " + id));
        return modelMapper.map(foundedTag, TagDto.class);
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        Tag newTag = tagRepo.save(tag);
        return modelMapper.map(newTag, TagDto.class);
    }

    @Override
    public TagDto updateTag(Long id, TagDto tagDto) {
        Tag foundedTag = tagRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Tag id: " + id));
        foundedTag.setName(tagDto.getName());
        Tag updatedTag = tagRepo.save(foundedTag);
        return modelMapper.map(updatedTag, TagDto.class);
    }

    @Override
    public int deleteTag(Long id) {
        Tag foundedTag = tagRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Tag id: " + id));
        tagRepo.deleteById(foundedTag.getId());
        return id.intValue();
    }

}
