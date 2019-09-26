package hello.service;

import hello.dao.BlogDao;
import hello.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @InjectMocks
    BlogService blogService;
    @Mock
    BlogDao blogDao;

    @Test
    public void getBlogsFormDb() {
        List<Blog> blogs = Arrays.asList(mock(Blog.class), mock(Blog.class));
        when(blogDao.getBlogs(1, 2, null)).thenReturn(blogs);
        when(blogDao.count(null)).thenReturn(3);

        BlogListResult result = blogService.getBlogs(1, 2, null);

        verify(blogDao).count(null);
        verify(blogDao).getBlogs(1, 2, null);

        Assertions.assertEquals(1, result.getPage());
        Assertions.assertEquals(3, result.getTotal());
        Assertions.assertEquals(2, result.getTotalPage());
        Assertions.assertEquals("ok", result.getStatus());


    }

    @Test
    public void returnFailureExceptionThrown() {
        when(blogDao.getBlogs(anyInt(), anyInt(), any())).thenThrow(new RuntimeException());

        Result result = blogService.getBlogs(1, 10, null);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("系统异常", result.getMsg());
    }

    @Test
    public void returnFailureWhenBlogNotFound() {
        when(blogDao.selectBlogById(1)).thenReturn(null);

        BlogResult result = blogService.deleteBlog(1, mock(User.class));

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("博客不存在", result.getMsg());
    }

    @Test
    public void returnFailureWhenBlogUserIdNotMatch() {
        User blogAuthor = new User(123, "blogAuthor", "");
        User operator = new User(456, "operator", "");

        Blog targetBlog = new Blog();
        targetBlog.setId(1);
        targetBlog.setUser(operator);

        Blog blogInDb = new Blog();
        blogInDb.setUser(blogAuthor);

        when(blogDao.selectBlogById(1)).thenReturn(blogInDb);

        BlogResult result = blogService.updateBlog(1, targetBlog);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("无法修改别人的博客", result.getMsg());
    }
}
