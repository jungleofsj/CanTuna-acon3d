package CanTuna.CanTunaacon3d.Service;

import CanTuna.CanTunaacon3d.domain.User;
import CanTuna.CanTunaacon3d.repository.ItemRepository;
import CanTuna.CanTunaacon3d.repository.JdbcTempUserRepository;
import CanTuna.CanTunaacon3d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long joinUser(User user) {

        userRepository.createUser(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(User user) {
        userRepository.findUserByName(user.getUserName())
                .ifPresent(m -> {
                    throw new IllegalStateException("Duplicate User Name");
                });
    }

    public Optional<User> findUser(Long userId) {
        return userRepository.findUserById(userId);
    }
    public List<User> findAllUser() {
        return userRepository.findAllUser();
    }


}
