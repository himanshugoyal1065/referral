package com.example.referral.service;

import com.example.referral.model.Company;
import com.example.referral.model.Resume;
import com.example.referral.model.User;
import com.example.referral.repository.UserRepository;
import com.example.referral.utils.Constants;
import com.example.referral.utils.ResponseMessage;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @NotNull
    public boolean userExists(@NotNull final String email) {
        return userRepository.existsById(email);
    }

    @NotNull
    public User saveNewUser(@NotNull final OAuth2AuthenticationToken token) {
        final String email = token.getPrincipal().getAttribute(Constants.EMAIL_SMALL);
        final String name = token.getPrincipal().getAttribute(Constants.NAME_SMALL);

        final User user = new User();
        user.setName(name);
        user.setEmail(email);

        saveUser(user);

        return getUserByEmail(email);
    }

    @NotNull
    private ResponseMessage saveUser(@NotNull final User user) {
        if (userRepository.existsById(user.getEmail())) {
            return new ResponseMessage(true, "The user with email " + user.getEmail() + " exists");
        }
        try {
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            return new ResponseMessage(false, e.toString());
        }
        return new ResponseMessage(true, "The user " + user.getEmail() + " is saved successfully");
    }

    @Nullable
    public User getUserByEmail(@NotNull final String email) {
        return userRepository.findById(email).get();
    }

    @NotNull
    public ResponseMessage uploadResume(@NotNull final String email, @NotNull final MultipartFile resumeFile) throws Exception {
        final User user = userRepository.findById(email).get();
        final String newFileName = StringUtils.cleanPath(resumeFile.getOriginalFilename());

        final Resume resume = user.getResume();


        //finding the logic to ask whether, "are you sure to delete this? " and then delete the current resume or not.
        // has to be done from the frontend.

        user.setResume(new Resume(newFileName, resumeFile.getBytes()));
        userRepository.save(user);

        return new ResponseMessage(true, "the resume " + newFileName + " is added successfully");
    }

    @Nullable
    public Resume getResumeForUserByEmail(@NotNull final String email) {
        return userRepository.findById(email).get().getResume();
    }

    @NotNull
    public boolean doesResumeExistsForUserByEmail(@NotNull final String email) {
        return userRepository.findById(email).get().getEmail() != null;
    }

    public boolean removeResumeForUserByEmail(@NotNull final String email) {
        try {
            final User user = userRepository.findById(email).get();
            user.setResume(null);
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }


    public void linkResumeToUserFromReferral(@NotNull final String user,@NotNull final MultipartFile resume) {
        try {
            final ResponseMessage responseMessage = uploadResume(user, resume);
            if (responseMessage.isSuccess()) {
                //TODO better way ?
                System.out.println("the resume is attached to the user");
            }
            else {
                System.out.println("ignoring the linking");
            }
        } catch (Exception e) {
            System.out.println("ignoring the linking");
        }
    }

    @NotNull
    public User putCompanyForUser(@NotNull final Company company, @NotNull final User user) {
        user.setCompany(company);
        userRepository.saveAndFlush(user);
        return getUserByEmail(user.getEmail());
    }

    @NotNull
    public boolean getCompanyExistsForUser(@NotNull final User user) {
        final Company company = user.getCompany();
        if (company == null) {
            return false;
        }
        return true;
    }


}
