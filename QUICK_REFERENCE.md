# Email Verification - Quick Reference

## ✅ What I Created for You

### 1. **HTML Template** (DONE ✓)
- **File**: `src/main/resources/templates/client/user/regist_email.html`
- **Features**:
  - Modern gradient background
  - 2-step process (Email → Verification Code)
  - Step indicator with animations
  - Responsive design (mobile-friendly)
  - Auto-formatting for verification code (6 digits only)
  - Resend button with 60-second timer
  - Error message display
  - Smooth transitions between steps
  - Thymeleaf form binding ready

### 2. **CSS Styling** (DONE ✓)
- **File**: `src/main/resources/static/css/stylesheet.css` (appended)
- **Includes**:
  - Modern gradient buttons with hover effects
  - Responsive breakpoints (tablet, mobile)
  - Animation keyframes
  - Form validation styling
  - Step indicator styling
  - Email display section
  - Resend timer styling

### 3. **Documentation** (DONE ✓)
- `EMAIL_VERIFICATION_GUIDE.md` - Overview and flow
- `EMAIL_VERIFICATION_IMPLEMENTATION.md` - Complete code examples

---

## 📋 What You Need to Create

### Step 1: Create Form Class (5 minutes)
```
📁 src/main/java/jp/co/sss/shop/form/
   └─ EmailVerificationForm.java ← CREATE THIS
```

### Step 2: Create Service Class (10 minutes)
```
📁 src/main/java/jp/co/sss/shop/service/
   └─ EmailVerificationService.java ← CREATE THIS
```

### Step 3: Update Controller (15 minutes)
```
📁 src/main/java/jp/co/sss/shop/controller/client/user/
   └─ RegistUserController.java ← ADD METHODS TO THIS
```
- Add `@Autowired EmailVerificationService`
- Add 4 new methods:
  - `emailVerificationInit()` - GET /client/user/regist/email/init
  - `sendVerificationCode()` - POST /client/user/regist/email/send
  - `verifyCode()` - POST /client/user/regist/email/verify
  - `resendVerificationCode()` - POST /client/user/regist/email/resend

### Step 4: Update Configuration (2 minutes)
```
📁 src/main/resources/
   └─ application.properties ← ADD EMAIL CONFIG
```

---

## 🔗 URL Routes

| Method | URL | Purpose |
|--------|-----|---------|
| GET | `/client/user/regist/email/init` | Show email verification form |
| POST | `/client/user/regist/email/send` | Send verification code |
| POST | `/client/user/regist/email/verify` | Verify code & proceed |
| POST | `/client/user/regist/email/resend` | Resend verification code |
| GET | `/client/user/regist/input/init?email=xxx` | Pre-fill registration form |

---

## 💾 Database Schema (If Using DB)

Optional: Store verification codes in database instead of HashMap

```sql
CREATE TABLE email_verifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    code VARCHAR(6) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    verified BOOLEAN DEFAULT FALSE,
    attempts INT DEFAULT 0
);

-- Clean up expired codes (run periodically)
DELETE FROM email_verifications 
WHERE expires_at < NOW() AND verified = FALSE;
```

---

## 🧪 Testing Checklist

### Without Email (Console Output)
- [ ] Navigate to `/client/user/regist/email/init`
- [ ] Enter test email
- [ ] Click "Send Verification Code"
- [ ] Check console output for 6-digit code
- [ ] Enter code
- [ ] Click "Verify Code"
- [ ] Should redirect to registration form with email pre-filled

### With Gmail SMTP
- [ ] Set up Gmail app password
- [ ] Add credentials to `application.properties`
- [ ] Restart application
- [ ] Repeat testing steps above
- [ ] Check actual email inbox for verification code

### Mobile Testing
- [ ] Test on iPhone (Safari)
- [ ] Test on Android (Chrome)
- [ ] Verify responsive layout
- [ ] Test form input (email keyboard)
- [ ] Test verification code input (numeric keyboard)

---

## 📱 Browser Support

| Browser | Support |
|---------|---------|
| Chrome | ✅ Full |
| Firefox | ✅ Full |
| Safari | ✅ Full |
| Edge | ✅ Full |
| IE 11 | ❌ Not supported |

---

## ⚙️ Configuration Examples

### Gmail SMTP Setup
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### SendGrid SMTP Setup
```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=SG.xxxxxxxxxx
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Development (No Email)
```properties
# Just don't add mail configuration
# Service will print codes to console instead
```

---

## 🎨 UI Features

### Visual Elements
- ✅ Gradient background (purple to blue)
- ✅ White card with shadow
- ✅ Step indicators (1 & 2)
- ✅ Progress line between steps
- ✅ Smooth animations & transitions
- ✅ Color-coded buttons (primary & secondary)
- ✅ Error alerts (red background)
- ✅ Info box with benefits

### Interactive Elements
- ✅ Email input with icon
- ✅ Verification code input (auto-format)
- ✅ Send Code button
- ✅ Verify Code button
- ✅ Back button
- ✅ Resend button (with timer)
- ✅ Link to login page

### Responsive Design
- ✅ Desktop: 500px card width
- ✅ Tablet: 100% with padding
- ✅ Mobile: Full width, smaller fonts
- ✅ Form inputs: Large touch targets

---

## 📊 Form Data Flow

```
User Input
    ↓
HTML Form (Thymeleaf binding)
    ↓
EmailVerificationForm (Validation)
    ↓
RegistUserController (Processing)
    ↓
EmailVerificationService (Business Logic)
    ↓
JavaMailSender (Email dispatch)
    ↓
Response back to user
```

---

## 🔐 Security Checklist

- [ ] Validate email format (HTML5 + Spring validation)
- [ ] Validate code format (6 digits only)
- [ ] Expire codes after 10 minutes
- [ ] Remove code after successful verification
- [ ] Hash codes in database (if using DB)
- [ ] Limit verification attempts (optional)
- [ ] Log verification events
- [ ] Use HTTPS in production
- [ ] Protect SMTP credentials in environment variables
- [ ] Sanitize error messages (don't reveal if email exists)

---

## 🚀 Deployment Checklist

- [ ] Create all Java classes
- [ ] Add 4 controller methods
- [ ] Configure email SMTP settings
- [ ] Test locally with console output
- [ ] Test with actual email (Gmail)
- [ ] Update registration input page (optional email field update)
- [ ] Test full flow end-to-end
- [ ] Deploy to staging
- [ ] Test on staging environment
- [ ] Deploy to production
- [ ] Monitor email delivery
- [ ] Set up email logging

---

## 📞 File Creation Order

1. **First**: Create `EmailVerificationForm.java`
2. **Second**: Create `EmailVerificationService.java`
3. **Third**: Update `RegistUserController.java` (add methods)
4. **Fourth**: Update `application.properties` (add email config)
5. **Fifth**: Test everything

---

## 🎯 Success Criteria

You'll know it's working when:

1. ✅ User can navigate to `/client/user/regist/email/init`
2. ✅ Email input form displays with modern styling
3. ✅ Clicking "Send Code" sends verification email (or prints to console)
4. ✅ Form switches to Step 2 automatically
5. ✅ User can enter 6-digit code
6. ✅ Clicking "Verify" validates the code
7. ✅ User redirects to registration form with email pre-filled
8. ✅ Registration can be completed normally
9. ✅ All responsive breakpoints work (desktop, tablet, mobile)

---

## 📞 Quick Links in Code

| Item | Location |
|------|----------|
| HTML Template | `regist_email.html` |
| CSS Styles | `stylesheet.css` (lines 1489+) |
| Form Class | Create `EmailVerificationForm.java` |
| Service Class | Create `EmailVerificationService.java` |
| Controller Methods | Update `RegistUserController.java` |
| Email Config | `application.properties` |
| Guide 1 | `EMAIL_VERIFICATION_GUIDE.md` |
| Guide 2 | `EMAIL_VERIFICATION_IMPLEMENTATION.md` |

---

## 💡 Pro Tips

1. **Testing without email**: Just check the console - codes print there automatically
2. **Gmail password**: Use "App Password", not your regular password
3. **Code expiry**: Default is 10 minutes, change in `EmailVerificationService.java`
4. **Resend timer**: 60 seconds hardcoded in HTML, change in JavaScript
5. **Error messages**: Edit them in validation annotations or service responses
6. **Database**: Upgrade from HashMap to actual database later if needed
7. **Email template**: Make it prettier with HTML email format
8. **Mobile testing**: Use Chrome DevTools device emulation

---

**Status**: 🟢 READY FOR IMPLEMENTATION

Next step: Create the three Java classes mentioned above!
