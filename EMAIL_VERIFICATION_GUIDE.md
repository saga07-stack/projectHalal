# Email Verification Implementation Guide

## Files Created
✅ `/src/main/resources/templates/client/user/regist_email.html` - Modern email verification page
✅ CSS styles added to `/src/main/resources/static/css/stylesheet.css`

---

## What You Need to Implement

### 1. **Form Class** - Create EmailVerificationForm.java
```java
package jp.co.sss.shop.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailVerificationForm {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Verification code is required")
    @Size(min = 6, max = 6, message = "Code must be 6 digits")
    private String verificationCode;
    
    // Getters and setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getVerificationCode() {
        return verificationCode;
    }
    
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
```

---

### 2. **Controller Methods** - Add to RegistUserController.java

```java
@RequestMapping(path = "/client/user/regist/email/init", method = RequestMethod.GET)
public String emailVerificationInit(
    @ModelAttribute EmailVerificationForm emailVerificationForm,
    Model model) {
    // Initialize empty form
    return "client/user/regist_email";
}

@RequestMapping(path = "/client/user/regist/email/send", method = RequestMethod.POST)
public String sendVerificationCode(
    @Valid @ModelAttribute EmailVerificationForm emailVerificationForm,
    BindingResult result,
    Model model,
    HttpSession session) {
    
    if (result.hasErrors()) {
        return "client/user/regist_email";
    }
    
    String email = emailVerificationForm.getEmail();
    
    // TODO: Call service to generate code and send email
    // emailVerificationService.sendVerificationCode(email);
    
    // Store email in session for verification
    session.setAttribute("verificationEmail", email);
    
    // Return same page but form will show step 2 on client side
    // You can add a success flag to model to trigger JavaScript
    model.addAttribute("verificationSent", true);
    return "client/user/regist_email";
}

@RequestMapping(path = "/client/user/regist/email/verify", method = RequestMethod.POST)
public String verifyCode(
    @Valid @ModelAttribute EmailVerificationForm emailVerificationForm,
    BindingResult result,
    Model model,
    HttpSession session) {
    
    if (result.hasErrors()) {
        return "client/user/regist_email";
    }
    
    String email = (String) session.getAttribute("verificationEmail");
    String code = emailVerificationForm.getVerificationCode();
    
    // TODO: Call service to verify code
    // boolean isValid = emailVerificationService.verifyCode(email, code);
    
    if (/* code is valid */) {
        // Mark email as verified in session
        session.setAttribute("verifiedEmail", email);
        session.removeAttribute("verificationEmail");
        
        // Redirect to registration input page with verified email
        return "redirect:/client/user/regist/input/init?email=" + 
               java.net.URLEncoder.encode(email, "UTF-8");
    } else {
        // Error: Invalid code
        model.addAttribute("verificationError", "Invalid verification code");
        return "client/user/regist_email";
    }
}

@RequestMapping(path = "/client/user/regist/email/resend", method = RequestMethod.POST)
public String resendVerificationCode(
    @RequestParam String email,
    Model model) {
    
    // TODO: Call service to regenerate and resend code
    // emailVerificationService.sendVerificationCode(email);
    
    model.addAttribute("email", email);
    model.addAttribute("verificationSent", true);
    return "client/user/regist_email";
}
```

---

### 3. **Service Class** - Create EmailVerificationService.java

```java
package jp.co.sss.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailVerificationService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    // Simple in-memory storage (replace with database for production)
    private ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();
    
    /**
     * Generate and send verification code to email
     */
    public void sendVerificationCode(String email) {
        // Generate 6-digit random code
        String code = String.format("%06d", new Random().nextInt(999999));
        
        // Store code (with optional expiry time)
        verificationCodes.put(email, code);
        
        // Send email
        sendEmail(email, code);
    }
    
    /**
     * Verify the code submitted by user
     */
    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        
        if (storedCode != null && storedCode.equals(code)) {
            // Remove code after successful verification
            verificationCodes.remove(email);
            return true;
        }
        return false;
    }
    
    /**
     * Send verification email
     */
    private void sendEmail(String email, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Email Verification - Nepali Pasal");
            message.setText("Your verification code is: " + code + 
                          "\n\nThis code will expire in 10 minutes.\n" +
                          "Do not share this code with anyone.");
            
            mailSender.send(message);
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
        }
    }
}
```

---

### 4. **Update Registration Input Controller**

Modify your existing `registInputInit()` method to pre-fill email:

```java
@RequestMapping(path = "/client/user/regist/input/init", method = RequestMethod.GET)
public String registInputInit(
    @Valid @ModelAttribute UserForm userForm,
    BindingResult result,
    Model model,
    HttpSession session,
    @RequestParam(required = false) String email) {
    
    // If email was verified, pre-fill it
    if (email != null && !email.isEmpty()) {
        userForm.setEmail(email);
        model.addAttribute("emailVerified", true);
    }
    
    return "client/user/regist_input";
}
```

---

### 5. **Update regist_input.html** - Pre-fill verified email (Optional)

```html
<input type="email" th:field="*{email}" 
       th:readonly="${emailVerified}" 
       placeholder="Email address" />
```

---

## Email Configuration - application.properties

Add these to your `application.properties`:

```properties
# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

---

## Flow Diagram

```
1. User clicks "Register" → GET /client/user/regist/email/init
   ↓
2. Show Step 1: Email Input Form
   ↓
3. User enters email → POST /client/user/regist/email/send
   ↓
4. Server generates 6-digit code and sends email
   ↓
5. Show Step 2: Verification Code Input (Client-side JavaScript)
   ↓
6. User enters code → POST /client/user/regist/email/verify
   ↓
7. If valid → Redirect to /client/user/regist/input/init with verified email
   ↓
8. Show Registration Input Form (with email pre-filled and locked)
```

---

## JavaScript Features Already Included in HTML

✅ Step indicator animation
✅ Form step switching (Step 1 ↔ Step 2)
✅ Resend code timer (60 seconds)
✅ Auto-formatting of verification code (6 digits only)
✅ Email display on Step 2
✅ Error handling and messages
✅ Responsive design for mobile/tablet

---

## Testing the Page

1. Navigate to: `/client/user/regist/email/init`
2. Enter email and click "Send Verification Code"
3. Check console for the code (or email in production)
4. Enter code and click "Verify Code"
5. Should redirect to registration input page

---

## Important Notes

- Replace in-memory HashMap with database storage for production
- Add code expiry time (10-15 minutes)
- Add rate limiting to prevent brute force attacks
- Implement proper email service (SendGrid, AWS SES, etc.)
- Store hashed verification codes in database
- Add logging for security audit trail
- Consider using Spring Data JPA for database operations

---

**HTML/CSS Part**: ✅ COMPLETE
**Controller Part**: 🔧 YOUR WORK STARTS HERE
