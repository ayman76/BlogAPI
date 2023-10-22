package com.example.blogapi.service.impls;

import com.example.blogapi.model.Blog;
import com.example.blogapi.repository.BlogRepo;
import com.example.blogapi.service.interfaces.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepo blogRepo;

    @Override
    public Blog getBlogById(Long id) {
        Optional<Blog> foundedBlog = blogRepo.findById(id);
        if (foundedBlog.isPresent()) {
            return foundedBlog.get();
        }
        throw new RuntimeException("Not Found Blog with id " + id);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepo.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) throws RuntimeException {
        Optional<Blog> foundedBlog = blogRepo.findById(id);
        if (foundedBlog.isPresent()) {
            Blog updatedBlog = foundedBlog.get();
            updatedBlog.setTitle(blog.getTitle());
            updatedBlog.setSlug(blog.getSlug());
            updatedBlog.setDescription(blog.getDescription());
            updatedBlog.setUser(blog.getUser());
            updatedBlog.setCategory(blog.getCategory());

            return blogRepo.save(updatedBlog);
        }
        throw new RuntimeException("Not Found Blog with id " + id);

    }

    @Override
    public int deleteBlogById(Long id) {
        Optional<Blog> foundedBlog = blogRepo.findById(id);
        if (foundedBlog.isPresent()) {
            blogRepo.deleteById(id);
            return id.intValue();
        }
        throw new RuntimeException("Not Found Blog with id " + id);
    }
}
