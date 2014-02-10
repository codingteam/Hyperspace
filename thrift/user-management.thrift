namespace ru.org.codingteam.hyperspace

typedef string SessionId

/**
 * User management service.
 */
service UserManagementService {
    /**
     * Logs user in returning the session id.
     */
    SessionId login(string login, string password),

    /**
     * Registers new user.
     */
    bool register(string login, string password)
}