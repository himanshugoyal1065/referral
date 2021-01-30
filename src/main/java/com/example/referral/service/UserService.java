package com.example.referral.service;

import com.example.referral.model.Resume;
import com.example.referral.model.User;
import com.example.referral.repository.UserRepository;
import com.example.referral.utils.ResponseMessage;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @NotNull
    public ResponseMessage saveUser(@NotNull final User user) {
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

        final List<Resume> resumes = user.getResume();

        for (Resume resume : resumes) {
            if (newFileName.equals(resume.getName())) {
                return new ResponseMessage(false, "The name already exists");
            }
        }

        resumes.add(new Resume(newFileName, resumeFile.getBytes()));

        user.setResume(resumes);
        userRepository.save(user);

        return new ResponseMessage(true, "the resume " + newFileName + " is added successfully");
    }

    @Nullable
    public List<Resume> getResumeForUserByEmail(@NotNull final String email) {
        return userRepository.findById(email).get().getResume();
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
}
