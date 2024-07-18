package eu.gaminatorium.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestUser implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Temporary class to simulate user identity and authentication. Remove after implementing security.
     */

    public static User TEST_USER;
    public static User TEST_USER2;
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        TEST_USER = initTestUser();
        userRepository.save(TEST_USER);
        TEST_USER2 = initSecondTestUser();
        userRepository.save(TEST_USER2);
    }

    private static User initTestUser() {
        if (TEST_USER == null) {
            User user = new User();
            user.setUserName("Test User");
            user.setPassword("testpassword");
            user.setEmail("test@email.usr");
            return user;
        }
        else return TEST_USER;
    }

    private static User initSecondTestUser() {
        if (TEST_USER2 == null) {
            User user = new User();
            user.setUserName("Second Test User");
            user.setPassword("secondpassword");
            user.setEmail("second@email.usr");
            return user;
        }
        else return TEST_USER2;
    }
}
