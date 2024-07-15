package eu.gaminatorium.user;

public class TestUser {

    /**
     * Temporary class to simulate user identity and authentication. Remove after implementing security.
     */

    public static final User TEST_USER = initTestUser();

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
}
