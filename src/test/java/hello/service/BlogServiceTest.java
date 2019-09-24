package hello.service;

import hello.dao.BlogDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @InjectMocks
    BlogService blogService;
    @Mock
    BlogDao blogDao;

    @Test
    public void getBlogsFormDb() {
        blogService.getBlogs(1, 10, null);
        Mockito.verify(blogDao).getBlogs(1, 10, null);
    }
}
