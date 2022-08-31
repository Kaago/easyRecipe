package de.hsba.bi.grp3.service;

import de.hsba.bi.grp3.exceptionHandlers.ForbiddenException;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.repository.UserRepository;
import de.hsba.bi.grp3.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;




    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findCurrentUser() {
        if (User.getCurrentUsername() != null) {
            return userRepository.findByName(User.getCurrentUsername());
        }
        return null;
    }

    public Boolean isUserOwner (User owner){

        if (this.findCurrentUser() != null && this.findCurrentUser().getId() != null){

            return Objects.equals(owner.getId(), this.findCurrentUser().getId());
        }
        return false;
    }

    public Boolean isUserAuthenticated (){

        return this.findCurrentUser() != null;
    }

    public void changePassword(String newPassword){

       User user = this.findCurrentUser();
       if (user != null && newPassword != null){

           user.setPassword(newPassword);
       }

    }

    public Boolean isUsernameUnique(String username){

     Boolean isUniqe = userRepository.findByName(username) == null;

     return isUniqe;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }


    public void addFavourite(Recipe recipe) {

        this.findCurrentUser().addFavourite(recipe);

    }
}
