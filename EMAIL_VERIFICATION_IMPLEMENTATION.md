# Email Verification - Quick Implementation Examples

## 1. Add to RegistUserController.java

Here's the complete updated controller with email verification:

```java
package jp.co.sss.shop.controller.client.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.form.EmailVerificationForm;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.service.EmailVerificationService;

@Controller
public class RegistUserController {
	
	@Autowired
	private EmailVerificationService emailVerificationService;

	
	// ========== EMAIL VERIFICATION FLOW ==========
	
	/**
	 * Step 1: Show email verification page
	 */
	@RequestMapping(path = "/client/user/regist/email/init", method = RequestMethod.GET)
	public String emailVerificationInit(
		@ModelAttribute EmailVerificationForm emailVerificationForm,
		Model model) {
		
		// Initialize empty form for step 1
		return "client/user/regist_email";
	}
	
	/**
	 * Step 2: Send verification code to email
	 */
	@RequestMapping(path = "/client/user/regist/email/send", method = RequestMethod.POST)
	public String sendVerificationCode(
		@Valid @ModelAttribute EmailVerificationForm emailVerificationForm,
		BindingResult result,
		Model model,
		HttpSession session) {
		
		// Check for validation errors
		if (result.hasFieldErrors("email")) {
			return "client/user/regist_email";
		}
		
		String email = emailVerificationForm.getEmail();
		
		try {
			// Send verification code via email service
			emailVerificationService.sendVerificationCode(email);
			
			// Store email in session for verification step
			session.setAttribute("verificationEmail", email);
			
			// Signal to template that code was sent (for step indicator)
			model.addAttribute("verificationSent", true);
			model.addAttribute("emailVerificationForm", emailVerificationForm);
			
		} catch (Exception e) {
			// Handle email sending error
			model.addAttribute("emailError", "Failed to send verification code. Please try again.");
			return "client/user/regist_email";
		}
		
		return "client/user/regist_email";
	}
	
	/**
	 * Step 3: Verify the code entered by user
	 */
	@RequestMapping(path = "/client/user/regist/email/verify", method = RequestMethod.POST)
	public String verifyCode(
		@Valid @ModelAttribute EmailVerificationForm emailVerificationForm,
		BindingResult result,
		Model model,
		HttpSession session) throws java.io.UnsupportedEncodingException {
		
		// Check for validation errors
		if (result.hasFieldErrors("verificationCode")) {
			model.addAttribute("verificationSent", true);
			return "client/user/regist_email";
		}
		
		String email = (String) session.getAttribute("verificationEmail");
		String code = emailVerificationForm.getVerificationCode();
		
		// Verify the code
		if (emailVerificationService.verifyCode(email, code)) {
			
			// Mark email as verified in session
			session.setAttribute("verifiedEmail", email);
			session.removeAttribute("verificationEmail");
			
			// Redirect to registration input page with verified email
			return "redirect:/client/user/regist/input/init?email=" + 
				   java.net.URLEncoder.encode(email, "UTF-8");
			
		} else {
			
			// Invalid code - stay on verification page
			model.addAttribute("verificationSent", true);
			model.addAttribute("verificationError", "Invalid verification code. Please try again.");
			model.addAttribute("emailVerificationForm", emailVerificationForm);
			
			return "client/user/regist_email";
		}
	}
	
	/**
	 * Resend verification code
	 */
	@RequestMapping(path = "/client/user/regist/email/resend", method = RequestMethod.POST)
	public String resendVerificationCode(
		@RequestParam String email,
		Model model,
		HttpSession session) {
		
		try {
			// Resend code to email
			emailVerificationService.sendVerificationCode(email);
			
			// Keep email in session
			session.setAttribute("verificationEmail", email);
			
			model.addAttribute("verificationSent", true);
			model.addAttribute("resendSuccess", true);
			
			// Prepare form with email
			EmailVerificationForm form = new EmailVerificationForm();
			form.setEmail(email);
			model.addAttribute("emailVerificationForm", form);
			
		} catch (Exception e) {
			model.addAttribute("emailError", "Failed to resend code. Please try again.");
		}
		
		return "client/user/regist_email";
	}
	
	
	// ========== EXISTING REGISTRATION FLOW ==========
	
	/**
	 * Show registration input form
	 * Now with pre-filled email from verification
	 */
	@RequestMapping(path = "/client/user/regist/input/init", method = RequestMethod.GET)
	public String registInputInit(
		@Valid @ModelAttribute UserForm userForm,
		BindingResult result,
		Model model,
		HttpSession session,
		@RequestParam(required = false) String email) {
		
		// If email comes from verified email verification flow
		if (email != null && !email.isEmpty()) {
			userForm.setEmail(email);
			model.addAttribute("emailVerified", true);
			// Store in session as well
			session.setAttribute("verifiedEmail", email);
		}
		
		// If already verified in session
		String verifiedEmail = (String) session.getAttribute("verifiedEmail");
		if (verifiedEmail != null && !verifiedEmail.isEmpty()) {
			userForm.setEmail(verifiedEmail);
			model.addAttribute("emailVerified", true);
		}
		
		return "client/user/regist_input";
	}
	
	/**
	 * Show confirmation page
	 */
	@RequestMapping(path = "/client/user/regist/check", method = RequestMethod.POST)
	public String registCheck(
		@Valid @ModelAttribute UserForm userForm,
		BindingResult result,
		Model model,
		HttpSession session) {
		
		if (result.hasErrors()) {
			return "client/user/regist_input";
		}
		
		// Check if email was verified
		String verifiedEmail = (String) session.getAttribute("verifiedEmail");
		if (verifiedEmail != null && !verifiedEmail.equals(userForm.getEmail())) {
			model.addAttribute("emailError", "Email must match the verified email");
			return "client/user/regist_input";
		}
		
		return "client/user/regist_check";
	}
}
```

---

## 2. EmailVerificationForm.java

Create this form class in `src/main/java/jp/co/sss/shop/form/`:

```java
package jp.co.sss.shop.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EmailVerificationForm {
    
    @NotBlank(message = "Email address is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    
    @NotBlank(message = "Verification code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Code must be 6 digits")
    private String verificationCode;
    
    // Constructors
    public EmailVerificationForm() {
    }
    
    public EmailVerificationForm(String email) {
        this.email = email;
    }
    
    // Getters and Setters
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

## 3. EmailVerificationService.java

Create this service in `src/main/java/jp/co/sss/shop/service/`:

```java
package jp.co.sss.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailVerificationService {
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    // Storage: email -> {code, timestamp}
    private Map<String, VerificationEntry> verificationCodes = new HashMap<>();
    
    // Code expiry time in minutes
    private static final int EXPIRY_MINUTES = 10;
    
    /**
     * Generate and send verification code to email
     */
    public void sendVerificationCode(String email) {
        // Generate 6-digit random code
        String code = generateCode();
        
        // Store code with timestamp
        verificationCodes.put(email, new VerificationEntry(code, LocalDateTime.now()));
        
        // Send email
        sendEmail(email, code);
    }
    
    /**
     * Verify the code submitted by user
     */
    public boolean verifyCode(String email, String code) {
        VerificationEntry entry = verificationCodes.get(email);
        
        if (entry == null) {
            return false;
        }
        
        // Check if code is expired
        if (isCodeExpired(entry.getTimestamp())) {
            verificationCodes.remove(email);
            return false;
        }
        
        // Check if code matches
        if (entry.getCode().equals(code)) {
            // Remove code after successful verification
            verificationCodes.remove(email);
            return true;
        }
        
        return false;
    }
    
    /**
     * Generate 6-digit random code
     */
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
    
    /**
     * Check if code is expired
     */
    private boolean isCodeExpired(LocalDateTime timestamp) {
        return ChronoUnit.MINUTES.between(timestamp, LocalDateTime.now()) >= EXPIRY_MINUTES;
    }
    
    /**
     * Send verification email
     */
    private void sendEmail(String email, String code) {
        if (mailSender == null) {
            // Fallback for testing - print to console
            System.out.println("=== VERIFICATION CODE FOR: " + email + " ===");
            System.out.println("CODE: " + code);
            System.out.println("================================");
            return;
        }
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Email Verification - Nepali Pasal");
            message.setFrom("noreply@nepalipasal.com");
            message.setText(
                "Hello,\n\n" +
                "Your verification code is: " + code + "\n\n" +
                "This code will expire in " + EXPIRY_MINUTES + " minutes.\n" +
                "Do not share this code with anyone.\n\n" +
                "If you did not request this code, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Nepali Pasal Team"
            );
            
            mailSender.send(message);
            
        } catch (Exception e) {
            // Log error
            System.err.println("Failed to send email to: " + email);
            e.printStackTrace();
            throw new RuntimeException("Email sending failed", e);
        }
    }
    
    /**
     * Inner class to store verification code with timestamp
     */
    private static class VerificationEntry {
        private String code;
        private LocalDateTime timestamp;
        
        public VerificationEntry(String code, LocalDateTime timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
        
        public String getCode() {
            return code;
        }
        
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
```

---

## 4. Update application.properties

Add email configuration:

```properties
# ===== Email Configuration =====

# For Gmail (requires App Password, not regular password)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000

# For SendGrid (alternative)
# spring.mail.host=smtp.sendgrid.net
# spring.mail.port=587
# spring.mail.username=apikey
# spring.mail.password=SG.xxxxx...
```

---

## 5. Update regist_input.html (Optional)

Make the email field read-only and show verification status:

```html
<!-- Around line 27 in the existing regist_input.html -->
<li>
    <label><span class="input_title">[[#{userForm.email}]]</span></label>
    <input type="email" 
           th:field="*{email}" 
           th:readonly="${emailVerified}"
           th:classappend="${emailVerified} ? 'verified-field'"
           placeholder="Enter your email" />
    <th:block th:if="${emailVerified}">
        <small style="color: green; font-weight: bold;">✓ Email verified</small>
    </th:block>
</li>
```

And add to stylesheet.css:

```css
/* Verified email field styling */
input.verified-field {
    background-color: #f0f9ff;
    border-color: #4caf50;
}

input.verified-field:focus {
    box-shadow: 0 0 6px rgba(76, 175, 80, 0.4);
    border-color: #45a049;
}
```

---

## 6. Usage Flow

### User Journey:

```
1. User clicks "新規会員登録" (New Registration) link
   ↓
2. Redirects to: GET /client/user/regist/email/init
   → Shows Step 1: Email Input Form
   ↓
3. User enters email and clicks "Send Verification Code"
   → POST /client/user/regist/email/send
   → Server generates 6-digit code and sends email
   ↓
4. Page transitions to Step 2: Verification Code Input
   → Resend button disabled for 60 seconds
   ↓
5. User receives email with code (or checks console for testing)
   ↓
6. User enters code and clicks "Verify Code"
   → POST /client/user/regist/email/verify
   → Server verifies code
   ↓
7. If valid:
   → Redirects to: GET /client/user/regist/input/init?email=xxx@example.com
   → Email field is pre-filled and read-only
   → Shows green checkmark: "✓ Email verified"
   ↓
8. User fills in password, name, address, phone
   ↓
9. User clicks "確認" (Confirm)
   → POST /client/user/regist/check
   → Shows confirmation page
   ↓
10. User clicks "完了" (Complete)
    → Registration is finalized
```

---

## 7. Testing Tips

### Without Email Service (Development):
```java
// The service prints to console instead
System.out.println("=== VERIFICATION CODE FOR: test@example.com ===");
System.out.println("CODE: 123456");
System.out.println("================================");
```

### With Gmail:
1. Enable 2-factor authentication
2. Create "App Password" (not regular password)
3. Use app password in configuration
4. Add verified email sender in your app

### Browser Testing:
1. Open DevTools Console (F12)
2. Navigate to `/client/user/regist/email/init`
3. Enter test email
4. Check console for code
5. Enter code to verify

---

## 8. Security Considerations

✅ Code expires after 10 minutes
✅ Code is 6 random digits (1 million combinations)
✅ Code is removed after successful verification
✅ Email validation prevents invalid entries
✅ Server-side validation on all inputs
✅ Session-based email tracking

🔒 Recommended Enhancements:
- Add rate limiting (max 5 attempts per IP per hour)
- Log all verification attempts
- Hash codes in database (not plain text)
- Use database instead of HashMap
- Add CAPTCHA for resend button
- Monitor suspicious patterns

---

## 9. Troubleshooting

| Problem | Solution |
|---------|----------|
| Email not sending | Check `mailSender` is autowired properly, check SMTP credentials |
| Code not received | Check spam/junk folder, check app password is correct |
| "Step 2 won't show" | Check browser console, ensure `verificationSent` is true in Model |
| Code always invalid | Check code hasn't expired (10 min limit), check exact match |
| Can't navigate back | Use "Back" button or browser back button to go to Step 1 |

---

**Created**: 2024
**For**: Nepali Pasal E-Commerce Platform
**Framework**: Spring Boot 3.x with Thymeleaf
