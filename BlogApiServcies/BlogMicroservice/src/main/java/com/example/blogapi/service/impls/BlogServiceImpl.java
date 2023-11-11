package com.example.blogapi.service.impls;

import com.example.blogapi.dto.CategoryDto;
import com.example.blogapi.dto.blog.BlogDto;
import com.example.blogapi.dto.blog.BlogRequestDto;
import com.example.blogapi.microservice.CategoryMicroserviceProxy;
import com.example.blogapi.model.AppUser;
import com.example.blogapi.model.Blog;
import com.example.blogapi.model.Category;
import com.example.blogapi.model.Tag;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.repository.BlogRepo;
import com.example.blogapi.repository.TagRepo;
import com.example.blogapi.service.interfaces.BlogService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final ModelMapper modelMapper;
    private final BlogRepo blogRepo;
    //    private final CategoryRepo categoryRepo;
    private final AppUserRepo appUserRepo;
    private final TagRepo tagRepo;
    @Autowired
    private CategoryMicroserviceProxy categoryMicroserviceProxy;

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
        CategoryDto foundedCategory = categoryMicroserviceProxy.findById(blogDto.getCategoryId()).getBody();
        AppUser appUser = appUserRepo.findById(blogDto.getUserId()).orElseThrow(() -> new RuntimeException("Not Founded User with Id: " + blogDto.getUserId()));
        Set<Tag> tags = blogDto.getTags().stream().map(t -> tagRepo.findById(t).orElseThrow(() -> new RuntimeException("Not Founded Tag with id " + t))).collect(Collectors.toSet());
        Blog blog = modelMapper.map(blogDto, Blog.class);
        blog.setUser(appUser);
        blog.setCategory(modelMapper.map(foundedCategory, Category.class));
        blog.setTags(tags);
        Blog newBlog = blogRepo.save(blog);
        return modelMapper.map(newBlog, BlogDto.class);
    }

    @Override
    public BlogDto updateBlog(Long id, BlogRequestDto blogDto) throws RuntimeException {
        CategoryDto foundedCategory = categoryMicroserviceProxy.findById(blogDto.getCategoryId()).getBody();
        Blog foundedBlog = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found BlogDto with id " + id));
        Set<Tag> tags = blogDto.getTags().stream().map(t -> tagRepo.findById(t).orElseThrow(() -> new RuntimeException("Not Founded Tag with id " + t))).collect(Collectors.toSet());
        foundedBlog.setTitle(blogDto.getTitle());
        foundedBlog.setSlug(blogDto.getSlug());
        foundedBlog.setDescription(blogDto.getDescription());
        foundedBlog.setCategory(modelMapper.map(foundedCategory, Category.class));
        foundedBlog.setTags(tags);
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
