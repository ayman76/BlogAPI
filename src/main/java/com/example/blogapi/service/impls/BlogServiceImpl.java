package com.example.blogapi.service.impls;

import com.example.blogapi.dto.blog.BlogDto;
import com.example.blogapi.dto.blog.BlogRequestDto;
import com.example.blogapi.model.AppUser;
import com.example.blogapi.model.Blog;
import com.example.blogapi.model.Category;
import com.example.blogapi.model.Tag;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.repository.BlogRepo;
import com.example.blogapi.repository.CategoryRepo;
import com.example.blogapi.repository.TagRepo;
import com.example.blogapi.service.interfaces.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final ModelMapper modelMapper;
    private final BlogRepo blogRepo;
    private final CategoryRepo categoryRepo;
    private final AppUserRepo appUserRepo;
    private final TagRepo tagRepo;

    @Override
    public BlogDto getBlogById(Long id) {
        Blog foundedBlog = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found BlogDto with id " + id));
        return modelMapper.map(foundedBlog, BlogDto.class);
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        return blogRepo.findAll().stream().map(b -> modelMapper.map(b, BlogDto.class)).collect(Collectors.toList());
    }

    @Override
    public BlogDto createBlog(BlogRequestDto blogDto) {
        Category category = categoryRepo.findById(blogDto.getCategoryId()).orElseThrow(() -> new RuntimeException("Not Founded Category with id " + blogDto.getCategoryId()));
        AppUser appUser = appUserRepo.findById(blogDto.getUserId()).orElseThrow(() -> new RuntimeException("Not Founded User with Id: " + blogDto.getUserId()));
        List<Tag> tags = blogDto.getTags().stream().map(t -> tagRepo.findById(t).orElseThrow(() -> new RuntimeException("Not Founded Tag with id " + t))).toList();
        Blog blog = modelMapper.map(blogDto, Blog.class);
        blog.setUser(appUser);
        blog.setCategory(category);
        blog.setTags(tags);
        Blog newBlog = blogRepo.save(blog);
        return modelMapper.map(newBlog, BlogDto.class);
    }

    @Override
    public BlogDto updateBlog(Long id, BlogRequestDto blogDto) throws RuntimeException {
        Category category = categoryRepo.findById(blogDto.getCategoryId()).orElseThrow(() -> new RuntimeException("Not Founded Category with id " + blogDto.getCategoryId()));
        Blog foundedBlog = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found BlogDto with id " + id));
        List<Tag> tags = blogDto.getTags().stream().map(t -> tagRepo.findById(t).orElseThrow(() -> new RuntimeException("Not Founded Tag with id " + t))).toList();
        foundedBlog.setTitle(blogDto.getTitle());
        foundedBlog.setSlug(blogDto.getSlug());
        foundedBlog.setDescription(blogDto.getDescription());
        foundedBlog.setCategory(category);
        foundedBlog.getTags().addAll(tags);
        Blog updatedBlog = blogRepo.save(foundedBlog);
        return modelMapper.map(updatedBlog, BlogDto.class);

    }

    @Override
    public int deleteBlogById(Long id) {
        Blog foundedBlog = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found BlogDto with id " + id));
        blogRepo.deleteById(foundedBlog.getId());
        return id.intValue();
    }

    @Override
    public BlogDto getBlogBySlug(String slug) {
        Blog foundedBlog = blogRepo.findBlogBySlug(slug).orElseThrow(() -> new RuntimeException("Not Found BlogDto with slug " + slug));
        return modelMapper.map(foundedBlog, BlogDto.class);
    }

}
