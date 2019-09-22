package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockMapper;

    @InjectMocks
    UserService userService;

    @Test
    public void testSave() {

        when(mockEncoder.encode("password")).thenReturn("EncodedPassword");

        userService.save("myUser", "password");

        verify(mockMapper).save("myUser", "EncodedPassword");
    }

    @Test
    public void testGetUserByUsername() {

        userService.getUserByUsername("myUser");

        verify(mockMapper).findUserByUsername("myUser");
    }

    @Test
    public void throwExceptionWhenUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("myUser"));
    }

    @Test
    public void returnUserDetailsWhenUserFound() {
        when(mockMapper.findUserByUsername("myUser"))
                .thenReturn(new User(123, "myUser", "EncodedPassword"));

        UserDetails userDetails = userService.loadUserByUsername("myUser");

        Assertions.assertEquals("myUser", userDetails.getUsername());
        Assertions.assertEquals("EncodedPassword", userDetails.getPassword());

    }
}