package com.myproject.blog.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myproject.blog.Model.Account;
import com.myproject.blog.Service.AccountService;
import com.myproject.blog.Service.EmailService;
import com.myproject.blog.Until.AppUtils;
import com.myproject.blog.Until.RandomString;
import com.myproject.blog.Until.Constants.Json;
import com.myproject.blog.Until.Constants. ReturnStatus;
import com.myproject.blog.Until.Email.EmailData;
import com.myproject.blog.Until.Role.Roles;

@Controller
public class AccountController {
    @Value("${spring.mvc.static-path-pattern}")
    private String uploadDir;

    @Value("${password.token.reset.timeout.minute}")
    private int tokenTimeOut;

    @Value("${site.domain}")
    private String siteDomain;

    @Autowired 
    private EmailService emailService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    @ResponseBody
    public Account register() {
        Account account = new Account();
        return account;
    }

    @PostMapping("/register")
    @ResponseBody
    public String getRegister(@RequestBody Account account, BindingResult result, RedirectAttributes attributes) {
        System.out.println("***" + account.getEmail() + " " + account.getFirstName() + "   " + account.getLastName());
        if (result.hasErrors()) {
             ReturnStatus sr =  ReturnStatus.ERROR;
            sr.chargeMessage("Check your input and try again.");
            return Json.toJson(sr.getObject());
        }
        System.out.println("***Save account:");
        Optional<Account> accouOptional = accountService.getByEmail(account.getEmail());
        if (accouOptional.isPresent()) {
            ReturnStatus sr =  ReturnStatus.ERROR;
            sr.chargeMessage("This account was register.");
            return Json.toJson(sr.getObject());
        }
        account.setRole(Roles.USER.getRole());
        accountService.save(account);
        SecurityContextHolder.clearContext();

        // Message to register
        return  ReturnStatus.SUCCESS.toString();
    }

    @GetMapping("/login")
    @ResponseBody
    public String menu() {
        return Json.toJson(ReturnStatus.SENT.chargeMessage("This is my login page"));
    }

    /**
     * This is controller to get and edit profile
     * @author: Quang Minh
     */
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public Optional<Account> profile(Principal principal) {
        System.out.println("Page profile is process...");
        String auth = "email";
        if (principal != null) {
            auth = principal.getName();
        }
        System.out.println("Load principal: "+auth);
        Optional<Account> accountOptional = accountService.getByEmail(auth);
        if (accountOptional.isPresent()) {
            System.out.println("*** Load account is: "+accountOptional.get().getFirstName());

            return accountOptional;
        }
        return accountOptional;
    }

    @PostMapping("/profile")
    @ResponseBody
    public String profileHandler(@RequestBody Account account, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return  ReturnStatus.ERROR.toString();
        }
        String email = "email";
        if (principal != null) {
            email = principal.getName();    
        }
        if (account.getEmail().compareToIgnoreCase(email) < 0) {
            return ReturnStatus.ERROR.toString();
        }
        Optional<Account> optionalAccount = accountService.getById(account.getId());
        if (optionalAccount.isPresent()) {
            Account editAccount = optionalAccount.get();
            editAccount.setEmail(account.getEmail());
            editAccount.setPassword(account.getPassword());
            editAccount.setFirstName(account.getFirstName());
            editAccount.setLastName(account.getLastName());
            editAccount.setAge(account.getAge());
            editAccount.setBirthDate(account.getBirthDate());
            editAccount.setGender(account.getGender());
            accountService.save(editAccount);
        }
        System.out.println("***Update account:");
        accountService.save(account);
        return Json.toJson(ReturnStatus.SUCCESS.getObject());
    }
    
    @PostMapping("/profile/photo")
    @ResponseBody
    public String loadPhotoHandle(@RequestParam("file") MultipartFile file,
    RedirectAttributes attributes,Principal principal) {
        if (file.isEmpty()) {
            return Json.toJson(ReturnStatus.ERROR.chargeMessage("Photo is not available!!!"));
        } else {
            try {
                @SuppressWarnings("null")
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                System.out.println("*** File uploads: "+filename);
                int length = 10;
                String randomString = RandomString.getRandomString(length);
                String finalFileString = randomString + filename;
                String fileLocation = AppUtils.getUploadPath(finalFileString);

                Path path = Paths.get(fileLocation);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                
                String email = "email";
                if (principal != null) {
                    email = principal.getName();
                }
                Optional<Account> optionalAccount = accountService.getByEmail(email);
                
                if (optionalAccount.isPresent()) {
                    Account uploadAccount = optionalAccount.get();
                    Account account_by_id = accountService.getById(uploadAccount.getId()).get();
                    String relativePath = uploadDir.replace("**", "uploads/"+finalFileString);
                    System.out.println("*** Relative path prepare: "+relativePath);
                    account_by_id.setPhoto(relativePath);
                    accountService.save(account_by_id);
                }
                
                
                System.out.println("*** Path was prepare: "+ fileLocation);

                return ReturnStatus.SUCCESS.chargeMessage("You successfully upload!!!").toString();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return Json.toJson(ReturnStatus.SENT.chargeMessage("Cannot load user. Try again!"));
    }
    
    @GetMapping("/forgot-password")
    @ResponseBody
    public String fogotPassword(Model model) {
        return Json.toJson(ReturnStatus.SENT);
    }

    @PostMapping("/forgot-password")
    public ReturnStatus resetPasswordHandler(Model model, @RequestParam("email") String email, RedirectAttributes attributes) {
        //TODO: process POST request
        System.out.println("Controller for password is processing...");
        Optional<Account> accountOptional = accountService.getByEmail(email);
        if (accountOptional.isPresent()) {
            String resetToken = UUID.randomUUID().toString();
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(tokenTimeOut);
            Account account = accountService.getById(accountOptional.get().getId()).get();
            account.setResetPasswordToken(resetToken);
            account.setResetPasswordExpiry(expiry);
            accountService.save(account);

            // Add email service send message
            String resetPasswordMessage = String.format(
                "This is the reset password link: %schange-password?token=%s%n%n" +
                "If you did not request this, you can safely ignore this email.%n" +
                "To unsubscribe, please click here: %s unsubscribe",
                siteDomain, resetToken, siteDomain
            );
            EmailData emailData = new EmailData(email, resetPasswordMessage, "Email link to reset password");
            if (emailService.sendSimpleEmail(emailData) == false) {
                ReturnStatus returnStatus = ReturnStatus.ERROR;
                returnStatus.chargeMessage("Cannot send email!!!");
                return returnStatus;
            }

            ReturnStatus returnStatus = ReturnStatus.SENT;
            returnStatus.chargeMessage("Email was be sent!");
            return returnStatus;
        } else {
            ReturnStatus returnStatus = ReturnStatus.ERROR;
            returnStatus.chargeMessage("Email not available");
            return returnStatus;
        }
    }

    @GetMapping("/change-password")
    public String change_password(Model model, @RequestParam("token") String token,
    RedirectAttributes attributes) {
        Optional<Account> accountOptional = accountService.getByToken(token);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(accountOptional.get().getResetPasswordExpiry())) {
                ReturnStatus returnStatus = ReturnStatus.SENT;
                returnStatus.chargeMessage("Token was expired!");
                return returnStatus.toString();
            }
            model.addAttribute("account",account);
            return account.toString();
        }

        attributes.addFlashAttribute("error","Invalid token");
        return "redirect:/forgot-password";
    }
    
    @PostMapping("/change-password")
    public String postMethodName(@ModelAttribute("account") Account account, RedirectAttributes attributes) {
        Optional<Account> accountOptional = accountService.getById(account.getId());

        if (accountOptional.isPresent()) {
            System.out.println(account.getId() + " " + account.getPassword() + " " + account.getEmail());
            Account reAccount = accountOptional.get();
            reAccount.setPassword(account.getPassword());
            reAccount.setResetPasswordToken(null);
            reAccount.setResetPasswordToken("");
            accountService.save(reAccount);

            attributes.addFlashAttribute("message","Password updated !!!");
            return "redirect:/login";
        }

        attributes.addFlashAttribute("error","Change password unsuccessfull ");
        return "redirect:/login";
    }
}
