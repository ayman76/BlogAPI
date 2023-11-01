package com.example.blogapi.service;

import com.example.blogapi.dto.TagDto;
import com.example.blogapi.model.Tag;
import com.example.blogapi.repository.TagRepo;
import com.example.blogapi.service.impls.TagServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    Long TAG_ID = 1L;
    @Mock
    private TagRepo tagRepo;

    @InjectMocks
    private TagServiceImpl tagService;

    private Tag tag;
    private TagDto tagDto;
    private List<Tag> tagList;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        tagService = new TagServiceImpl(modelMapper, tagRepo);
        tag = Tag.builder().id(1L).name("tag").build();
        tagDto = TagDto.builder().id(1L).name("tag").build();
        tagList = new ArrayList<>();
        tagList.addAll(Arrays.asList(tag, tag));
    }

    @Test
    public void TagService_CreateTag_ReturnsTagDto() {
        when(tagRepo.save(Mockito.any(Tag.class))).thenReturn(tag);

        TagDto savedTag = tagService.createTag(tagDto);

        Assertions.assertThat(savedTag).isNotNull();
    }

    @Test
    public void TagService_GetTagById_ReturnsTagDto() {
        when(tagRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tag));

        TagDto foundedTag = tagService.getTagById(TAG_ID);

        Assertions.assertThat(foundedTag).isNotNull();
    }

    @Test
    public void TagService_GetAllTags_ReturnsTagDtos() {
        when(tagRepo.findAll()).thenReturn(tagList);

        List<TagDto> tags = tagService.getAllTags();

        Assertions.assertThat(tags).isNotEmpty();
        Assertions.assertThat(tags.size()).isEqualTo(tagList.size());
    }

    @Test
    public void TagService_UpdateTag_ReturnsTagDto() {
        when(tagRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tag));
        when(tagRepo.save(Mockito.any(Tag.class))).thenReturn(tag);

        TagDto updatedTag = tagService.updateTag(TAG_ID, tagDto);

        Assertions.assertThat(updatedTag).isNotNull();
        Assertions.assertThat(updatedTag.getName()).isEqualTo(tagDto.getName());
    }

    @Test
    public void TagService_DeleteTag_ReturnsTagDto() {
        when(tagRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tag));

        int deletedTagId = tagService.deleteTag(TAG_ID);

        Assertions.assertThat(deletedTagId).isEqualTo(TAG_ID.intValue());
    }

}