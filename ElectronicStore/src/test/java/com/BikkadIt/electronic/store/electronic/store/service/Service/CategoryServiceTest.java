package com.BikkadIt.electronic.store.electronic.store.service.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.BikkadIt.electronic.store.dtos.CategoryDto;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.UserDto;
import com.BikkadIt.electronic.store.entities.Category;
import com.BikkadIt.electronic.store.entities.User;
import com.BikkadIt.electronic.store.repository.CategoryRepo;
import com.BikkadIt.electronic.store.repository.UserRepo;
import com.BikkadIt.electronic.store.service.CategoryService;
import com.BikkadIt.electronic.store.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepo categoryRepo;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;
    Category category;

    @BeforeEach
    public void init()
    {
        category = Category.builder()
                .categoryId("123")
                .tittle("Electronic")
                .description("Domestic")
                .build();
    }

    @Test
    public void createCategoryTest()
    {
        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = categoryService.create(modelMapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getTittle());
        Assertions.assertEquals("Electronic",categoryDto.getTittle());
    }

    @Test
    public void   updateCategoryTest()
    {
        String catId="";
      CategoryDto  category1 = CategoryDto.builder()
                .categoryId("123")
                .tittle("Mechanic")
                .description("Domestic")
                .build();

     Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
     Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);
        CategoryDto update = categoryService.update(category1, catId);
        System.out.println(category1.getTittle());
        Assertions.assertEquals(category1.getTittle(),category.getTittle());

    }
    @Test
    public void deleteCatByIdTest()
    {
        String catid="asdf";
        Mockito.when(categoryRepo.findById("asdf")).thenReturn(Optional.of(category));
        categoryService.delete(catid);
        Mockito.verify(categoryRepo,Mockito.times(1)).delete(category);
        System.out.println("Delete Test Passed");
    }
    @Test
    public void getAllCategoryTest()
    {
        Category  category2 = Category.builder()
                .categoryId("1234")
                .tittle("Machine")
                .description("Domestic")
                .build();
        Category  category3 = Category.builder()
                .categoryId("12345")
                .tittle("Working")
                .description("Domestic")
                .build();
        Category category4 = Category.builder()
                .categoryId("123456")
                .tittle("Domestic")
                .description("Domestic")
                .build();

        List<Category> list = Arrays.asList(category2, category3, category4);
        Page<Category> page=new PageImpl<>(list);
        Mockito.when(categoryRepo.findAll((Pageable)Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> all = categoryService.getAll(1, 2, "tittle", "Dec");

        Assertions.assertEquals(3,all.getContent().size());

    }

    @Test
    public void getCatByIdTest(){
        String cat="asdf";
        Mockito.when(categoryRepo.findById(cat)).thenReturn(Optional.of(category));
        CategoryDto byId = categoryService.getById(cat);
        Assertions.assertEquals(byId.getCategoryId(),category.getCategoryId());

    }





}