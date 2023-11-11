package com.example.blogapi.microservice;

import com.example.blogapi.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("SERVICE2")
public interface CategoryMicroserviceProxy {
    @GetMapping("/api/v1/categories/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id);
}
