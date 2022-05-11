package com.mole.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.mole.entity.User;
import com.mole.entity.UserDTO;
import com.mole.entity.UserProfile;
import com.mole.entity.UserProfileDTO;
import com.mole.exceptions.NotFoundException;
import com.mole.repository.UserProfileRepository;
import com.mole.repository.UserRepository;
import com.mole.utils.Argon2Encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@Controller("/api/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;
    private final UserProfileRepository repositoryUserProfile;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Secured({ "ADMIN" })
    @Get
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Secured({ "ADMIN" })
    @Get("/{id}")
    public User findById(@PathVariable long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id), "ID", "User"));
    }

    @Secured({ "ADMIN","USER","BUSINESS" })
    @Get("/email/{email}")
    public UserDTO findByEmail(@PathVariable String email) {
        return repository.findByEmailAndEnabled(email, true);
    }

    @Secured({ "ADMIN","USER" })
    @Post("/profile")
    public HttpResponse<Map<String, String>> updateCreate(@Body User user, @Body UserProfileDTO profile, Authentication authentication) {
        try {
            Map<String, String> result = new HashMap<String, String>();
            // log.info(profile.toString());
            // log.info(user.toString());
            Optional<User> userDB = repository.findByEmail(authentication.getName());
            if (userDB.isPresent()) {
                User u = userDB.get();
                if (user.getName() != null) {
                    u.setName(user.getName());
                }
                if (user.getSurname() != null) {
                    u.setSurname(user.getSurname());
                }
                if (user.getEmail() != null && u.getEmail() != user.getEmail()) {
                    u.setEmail(user.getEmail());
                }
                // log.info(u.toString());
                repository.update(u);
                Optional<UserProfile> up = repositoryUserProfile.findByUser(u);
                if (up.isPresent()) {
                    UserProfile userProfile = up.get();
                    userProfile = mergeProfiles(userProfile, profile);
                    repositoryUserProfile.update(userProfile);
                } else {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setUser(u);
                    userProfile = mergeProfiles(userProfile, profile);
                    repositoryUserProfile.save(userProfile);
                }
            } else {
                result.put("status", "error");
                result.put("message", "Invalid User!");
            }
            // log.info(result.toString());
            if (result.get("status") == "error") {
                return HttpResponse.serverError(result);
            } else {
                result.put("status", "success");
                return HttpResponse.ok(result);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            Map<String, String> result = new HashMap<String, String>();
            result.put("status", "error");
            result.put("message", ex.getMessage());
            return HttpResponse.serverError(result);
        }
    }

    @Secured({ "ADMIN","USER" })
    @Get("/profile")
    public HttpResponse<UserProfile> getProfile(Authentication authentication) {
        try {
            Optional<User> userDB = repository.findByEmail(authentication.getName());
            if (userDB.isPresent()) {
                Optional<UserProfile> up = repositoryUserProfile.findByUser(userDB.get());
                if (up.isPresent()) {
                    return HttpResponse.ok(up.get());
                } else {
                    return HttpResponse.notFound(new UserProfile());                  
                }
            } else {
                return HttpResponse.notFound(new UserProfile());                  
            }
        } catch(Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.serverError(new UserProfile());
        }
    }

    @Secured({ "ADMIN" })
    @Post("/update")
    public HttpResponse<Map<String, String>> update(@Body User user) {
        try {
            Map<String, String> result = new HashMap<String, String>();
            // log.info(user.toString());
            Optional<User> userDB = repository.findById(user.getId());
            if (userDB.isPresent()) {
                User u = userDB.get();
                if (user.getName() != null) {
                    u.setName(user.getName());
                }
                if (user.getSurname() != null) {
                    u.setSurname(user.getSurname());
                }
                if (user.getEmail() != null) {
                    u.setEmail(user.getEmail());
                }
                if (user.getEnabled() != null) {
                    u.setEnabled(user.getEnabled());
                }
                // log.info(u.toString());
                repository.update(u);
            } else {
                result.put("status", "error");
                result.put("message", "Invalid User!");
            }
            // log.info(result.toString());
            if (result.get("status") == "error") {
                return HttpResponse.serverError(result);
            } else {
                result.put("status", "success");
                return HttpResponse.ok(result);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            Map<String, String> result = new HashMap<String, String>();
            result.put("status", "error");
            result.put("message", ex.getMessage());
            return HttpResponse.serverError(result);
        }
    }

    // @Secured({ "ADMIN","BUSINESS" })
    // @Get("/business")
    // public HttpResponse<String> getBusiness(Authentication authentication) {
    //     try {
    //         Optional<User> userDB = repository.findByEmail(authentication.getName());
    //         if (userDB.isPresent()) {
    //             return HttpResponse.ok("{\"business\":\""+userDB.get().getBusiness()+"\"}");
    //         } else {
    //             return HttpResponse.notFound(null);                  
    //         }
    //     } catch(Exception ex) {
    //         log.error(ex.getMessage());
    //         return HttpResponse.serverError(null);
    //     }
    // }

    private UserProfile mergeProfiles(UserProfile userProfile, UserProfileDTO profile) {

        if (profile.getMobile() != null) {
            userProfile.setMobile(profile.getMobile());
        }

        if (profile.getGender() != null) {
            userProfile.setGender(profile.getGender());
        }

        if (profile.getBirthdate() != null) {
            userProfile.setBirthdate(LocalDate.parse(profile.getBirthdate()));
        }

        return userProfile;
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    public HttpResponse<Map<String, String>> register(@Body User user) {
        // log.info(user.toString());
        Map<String, String> result = new HashMap<String, String>();
        Optional<User> userDB = repository.findByEmail(user.getEmail());
        userDB.ifPresentOrElse(u -> {
            result.put("status", "error");
            result.put("message", "Error: Email Address already in use.");
        }, () -> {
            try {
                String pw = Argon2Encode.encrypt(user.getPassword());
                user.setPassword(pw);
                // Not Activated User
                // user.setLevel(0);
                // Not Activated Business
                // user.setLevel(10);
                // user.setEnabled(false);
                // if (user.getRoles() == null || user.getRoles() == "") {
                    // if (user.getBusiness().isEmpty()) {
                        // Activated
                        user.setLevel(1);
                        user.setEnabled(true);
                    // } else {
                    //     // Activated
                    //     user.setLevel(11);
                    //     user.setEnabled(true);
                    // }
                // }

                repository.save(user);
                result.put("status", "success");
                // TODO: Send Activation Email

            } catch (Exception err) {
                err.printStackTrace();
                log.error(err.toString());
                result.put("status", "error");
                result.put("message", "Error: " + err.getMessage());
            }
        });

        // log.info(result.toString());
        if (result.get("status") == "error") {
            return HttpResponse.serverError(result);
        } else {
            return HttpResponse.created(result);
        }
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/email/check")
    public String emailCheck(@Body String email) {
        email = email.replaceAll("\"", "");
        Map<String, String> result = new HashMap<String, String>();
        Optional<User> userDB = repository.findByEmail(email);
        userDB.ifPresentOrElse(u -> {
            result.put("status", "found");
        }, () -> {
            result.put("status", "notfound");
        });

        if (result.get("status") == "found") {
            return "true";
        } else {
            return "false";
        }
        
    }
}
