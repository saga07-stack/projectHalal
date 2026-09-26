# Unified Form CSS System - Style Guide

## Overview
A comprehensive, reusable parent-child component system for all forms in the Nepali Pasal application. This system ensures visual consistency across login, registration, user profiles, admin panels, orders, and all other form types.

**Location:** `/src/main/resources/static/css/forms.css`

---

## Color Palette

### Primary Colors
- **Primary:** `#e85d04` (Orange) - Main action color
- **Primary Dark:** `#d34e00` - Darker orange for hover states
- **Primary Light:** `#f0b98a` - Light orange
- **Primary BG:** `#fff0e0` - Very light orange background

### Secondary Colors
- **Secondary:** `#3d98bf` (Blue) - Secondary actions
- **Secondary Dark:** `#2d7aa0` - Darker blue
- **Secondary Light:** `#5fb4d9` - Light blue
- **Secondary BG:** `#edf9fe` - Very light blue background

### Status Colors
- **Success:** `#27a745` (Green)
- **Error:** `#e02424` (Red)
- **Warning:** `#f59e0b` (Amber)
- **Info:** `#3d98bf` (Blue)

### Neutral Colors
- **Text Primary:** `#1a1a1a` (Dark)
- **Text Secondary:** `#666` (Medium)
- **Text Light:** `#999` (Light)
- **Border:** `#e0e0e0`
- **BG Light:** `#f5f5f5`
- **BG White:** `#ffffff`

---

## Component Structure

### 1. Container & Card Components

#### Form Container
Main wrapper for all forms with full-height background gradient.

```html
<div class="form-container">
  <!-- Form content -->
</div>
```

**Variants:**
- `.form-container.login-form` - Blue gradient background
- `.form-container.registration-form` - Orange gradient background
- `.form-container.verification-form` - Light blue gradient background
- `.form-container.admin-form` - Light orange gradient background

#### Form Card
Main card wrapper with shadow and rounded corners.

```html
<div class="form-card">
  <!-- Form content -->
</div>
```

**Variants:**
- `.form-card.admin` - Admin styling with left border
- `.form-card.compact` - Smaller max-width (420px)

---

### 2. Header Components

#### Form Header
Title and subtitle section at the top of the form.

```html
<div class="form-header">
  <h2 class="form-title">Login</h2>
  <p class="form-subtitle">Please enter your credentials</p>
</div>
```

**Modifier:** `.form-header.with-border` - Adds bottom border

#### Form Title
```html
<h1 class="form-title">Main Title</h1>
<!-- Variants: .large, .small -->
```

#### Form Subtitle
```html
<p class="form-subtitle">Descriptive text</p>
```

#### Form Logo
```html
<div class="form-logo">🛒 Nepali Pasal</div>
```

#### Icon Circle
```html
<div class="icon-circle">
  <svg><!-- SVG icon --></svg>
</div>
```

---

### 3. Form Controls

#### Form Group
Container for label + input combo.

```html
<div class="form-group">
  <label class="form-label">Email Address</label>
  <input type="email" class="form-input" />
</div>
```

#### Form Label
```html
<label class="form-label required">Email</label>
<!-- The "required" class adds a red asterisk -->
```

#### Form Input
```html
<input type="text" class="form-input" placeholder="Enter text" />
```

**Variants:**
- `.form-input.error` - Error state with red border
- `.form-input.success` - Success state with green border
- `.form-input.filled` - Orange border when filled

#### Form Textarea
```html
<textarea class="form-textarea" rows="6"></textarea>
```

#### Form Select
```html
<select class="form-select">
  <option>Option 1</option>
  <option>Option 2</option>
</select>
```

#### Form Help Text
```html
<small class="form-help-text">This field is required</small>
```

---

### 4. Validation & Error Messages

#### Error Container
```html
<div class="error-container">
  <ul class="error-list">
    <li>First error message</li>
    <li>Second error message</li>
  </ul>
</div>
```

#### Field Error
```html
<div class="field-error show">
  Error message for this field
</div>
```

#### Success Container
```html
<div class="success-container">
  Success message
</div>
```

---

### 5. OTP Input Fields

#### OTP Inputs Container
For 6-digit verification codes.

```html
<div class="otp-inputs" id="otpInputs">
  <input type="text" inputmode="numeric" maxlength="1" data-index="0">
  <input type="text" inputmode="numeric" maxlength="1" data-index="1">
  <!-- ... 4 more inputs ... -->
</div>
```

---

### 6. Buttons

#### Button Base
All buttons inherit from `.btn` class.

```html
<button class="btn btn-primary">Primary Action</button>
```

#### Button Types

**Primary Button** (Orange)
```html
<button class="btn btn-primary">Submit</button>
```

**Secondary Button** (Blue)
```html
<button class="btn btn-secondary">Cancel</button>
```

**Outline Button** (Orange outline)
```html
<button class="btn btn-outline">Optional Action</button>
```

**Danger Button** (Red)
```html
<button class="btn btn-danger">Delete</button>
```

**Success Button** (Green)
```html
<button class="btn btn-success">Confirm</button>
```

**Text Button** (Text-only)
```html
<button class="btn btn-text">Link-like button</button>
```

#### Button Sizes

```html
<button class="btn btn-sm">Small</button>
<button class="btn">Default</button>
<button class="btn btn-lg">Large</button>
```

#### Button Modifiers

```html
<button class="btn btn-full-width">Full width button</button>
<button class="btn" disabled>Disabled button</button>
```

---

### 7. Form Actions

#### Form Actions Container
Button group wrapper.

```html
<div class="form-actions">
  <button class="btn btn-primary">Submit</button>
</div>
```

**Layouts:**
- `.form-actions` - Vertical stack (default)
- `.form-actions.horizontal` - Horizontal layout
- `.form-actions.two-buttons` - Two equal-width buttons
- `.form-actions.center` - Center aligned
- `.form-actions.right` - Right aligned

#### Usage Example
```html
<div class="form-actions two-buttons">
  <button class="btn btn-primary">Confirm</button>
  <button class="btn btn-secondary">Cancel</button>
</div>
```

---

### 8. Form Footer

#### Form Footer
```html
<div class="form-footer">
  <p class="form-footer-text">
    Already have an account? 
    <a href="/login" class="form-footer-link">Login here</a>
  </p>
</div>
```

---

### 9. Step Indicator

#### Step Indicator Container
```html
<div class="step-indicator">
  <div class="step active">
    <div class="step-number">1</div>
    <p class="step-label">Email</p>
  </div>
  <div class="step-line"></div>
  
  <div class="step">
    <div class="step-number">2</div>
    <p class="step-label">Verify</p>
  </div>
  <div class="step-line"></div>
  
  <div class="step">
    <div class="step-number">3</div>
    <p class="step-label">Register</p>
  </div>
</div>
```

**Step States:**
- `.step.active` - Current step
- `.step.completed` - Completed step
- `.step` - Upcoming step

---

### 10. Timer & Countdown

#### Timer Container
```html
<div class="timer">
  Resend code in <span class="timer-countdown">01:30</span>
</div>

<div class="timer hidden">
  <!-- Hidden timer -->
</div>
```

---

### 11. Email Display

#### Display Value
```html
<div class="display-value">user@example.com</div>
```

#### Email Display Box
```html
<div class="email-display">
  <p class="email-display-text">
    Sent to: <strong class="email-address">user@example.com</strong>
  </p>
</div>
```

---

### 12. Form Variants

#### Login Form
```html
<div class="form-container login-form">
  <div class="form-card">
    <!-- Login form content -->
  </div>
</div>
```

#### Registration Form
```html
<div class="form-container registration-form">
  <div class="form-card">
    <!-- Registration form content -->
  </div>
</div>
```

#### Admin Form
```html
<div class="form-container admin-form">
  <div class="form-card admin">
    <!-- Admin form content -->
  </div>
</div>
```

---

### 13. Input Lists

#### Multiple Form Groups
```html
<div class="input-list">
  <div class="form-group">
    <label class="form-label">Field 1</label>
    <input type="text" class="form-input" />
  </div>
  <div class="form-group">
    <label class="form-label">Field 2</label>
    <input type="text" class="form-input" />
  </div>
</div>
```

#### Form Row (Side-by-side)
```html
<div class="form-row">
  <div class="form-group">
    <label class="form-label">First Name</label>
    <input type="text" class="form-input" />
  </div>
  <div class="form-group">
    <label class="form-label">Last Name</label>
    <input type="text" class="form-input" />
  </div>
</div>
```

**Variants:**
- `.form-row.three-columns` - Three columns side-by-side

---

### 14. Checkbox & Radio Inputs

#### Checkbox
```html
<div class="form-checkbox">
  <input type="checkbox" id="terms" />
  <label for="terms">I agree to terms and conditions</label>
</div>
```

#### Radio Buttons
```html
<div class="form-radio">
  <label>
    <input type="radio" name="choice" value="1" />
    Option 1
  </label>
  <label>
    <input type="radio" name="choice" value="2" />
    Option 2
  </label>
</div>
```

---

## Complete Example: Login Form

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" type="text/css" th:href="@{/css/forms.css}" />
</head>
<body>
  <div class="form-container login-form">
    <div class="form-card">
      <div class="form-header">
        <h1 class="form-title">Login</h1>
        <p class="form-subtitle">Enter your credentials to access your account</p>
      </div>

      <form method="post" action="/login">
        <!-- Error Messages -->
        <div class="error-container" th:if="${errors}">
          <ul class="error-list">
            <li th:each="error : ${errors}" th:text="${error}"></li>
          </ul>
        </div>

        <!-- Email Field -->
        <div class="form-group">
          <label class="form-label required">Email Address</label>
          <input type="email" name="email" class="form-input" placeholder="Enter your email" />
        </div>

        <!-- Password Field -->
        <div class="form-group">
          <label class="form-label required">Password</label>
          <input type="password" name="password" class="form-input" placeholder="Enter your password" />
        </div>

        <!-- Submit Button -->
        <div class="form-actions">
          <button type="submit" class="btn btn-primary btn-full-width">Login</button>
        </div>
      </form>

      <!-- Footer -->
      <div class="form-footer">
        <p class="form-footer-text">
          Don't have an account? 
          <a href="/register" class="form-footer-link">Register here</a>
        </p>
      </div>
    </div>
  </div>
</body>
</html>
```

---

## Complete Example: Registration Form

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" type="text/css" th:href="@{/css/forms.css}" />
</head>
<body>
  <div class="form-container registration-form">
    <div class="form-card">
      <div class="form-header with-border">
        <h1 class="form-title">Create Account</h1>
        <p class="form-subtitle">Fill in your details to get started</p>
      </div>

      <form method="post" action="/register">
        <!-- Error Messages -->
        <div class="error-container" th:if="${errors}">
          <ul class="error-list">
            <li th:each="error : ${errors}" th:text="${error}"></li>
          </ul>
        </div>

        <!-- Form Fields -->
        <div class="input-list">
          <div class="form-group">
            <label class="form-label required">Email</label>
            <input type="email" name="email" class="form-input" />
          </div>

          <div class="form-group">
            <label class="form-label required">Password</label>
            <input type="password" name="password" class="form-input" />
          </div>

          <div class="form-group">
            <label class="form-label required">Full Name</label>
            <input type="text" name="name" class="form-input" />
          </div>

          <div class="form-group">
            <label class="form-label">Address</label>
            <textarea name="address" class="form-textarea" rows="4"></textarea>
          </div>

          <div class="form-group">
            <label class="form-label">Phone Number</label>
            <input type="tel" name="phone" class="form-input" />
          </div>
        </div>

        <!-- Form Actions -->
        <div class="form-actions two-buttons">
          <button type="submit" class="btn btn-primary">Register</button>
          <a href="/" class="btn btn-secondary">Cancel</a>
        </div>
      </form>

      <!-- Footer -->
      <div class="form-footer">
        <p class="form-footer-text">
          Already have an account? 
          <a href="/login" class="form-footer-link">Login here</a>
        </p>
      </div>
    </div>
  </div>
</body>
</html>
```

---

## CSS Variables (Customization)

All colors, spacing, and dimensions are defined as CSS variables in the `:root` selector. You can easily customize the theme by modifying these variables:

```css
:root {
  --color-primary: #e85d04;
  --color-primary-dark: #d34e00;
  --color-secondary: #3d98bf;
  --font-size-base: 14px;
  --spacing-md: 16px;
  --radius-md: 8px;
  /* ... more variables ... */
}
```

---

## Responsive Design

The system includes responsive breakpoints:
- **Desktop:** Full layout (>1024px)
- **Tablet:** Optimized layout (768px - 1024px)
- **Mobile:** Mobile-first layout (<768px)
- **Small Mobile:** Extra small optimizations (<480px)

All components automatically adapt to smaller screens.

---

## Files Modified

### New Files
- `/src/main/resources/static/css/forms.css` - Main form component system

### Updated Layout Templates
- `/src/main/resources/templates/common/layout_3block.html`
- `/src/main/resources/templates/common/layout_4block.html`
- `/src/main/resources/templates/common/layout_5block.html`

### Updated HTML Files
- `/src/main/resources/templates/login.html` ✅
- `/src/main/resources/templates/client/user/regist_email.html` ✅
- `/src/main/resources/templates/client/user/regist_code.html` ✅
- `/src/main/resources/templates/client/user/regist_input.html` ✅
- `/src/main/resources/templates/client/user/update_input.html` ✅
- `/src/main/resources/templates/admin/user/regist_input.html` ✅
- `/src/main/resources/templates/admin/category/regist_input.html` ✅

---

## Usage Tips

1. **Always wrap forms in `.form-container` and `.form-card`** for consistent styling
2. **Use semantic HTML** - proper `<label>` elements with `for` attributes
3. **Use `.form-group`** to wrap each label + input pair
4. **Apply status classes** (`.error`, `.success`) to inputs as needed
5. **Use `.form-actions`** for button groups
6. **Add responsive classes** like `.form-row` for side-by-side inputs
7. **Keep Thymeleaf logic intact** - CSS classes work seamlessly with Thymeleaf attributes

---

## Theming Guide

The system is highly customizable. To change the theme:

1. Modify CSS variables in `:root`
2. Update color values
3. Adjust spacing/sizing as needed
4. No need to modify HTML structure

Example: Change primary color from orange to purple:
```css
:root {
  --color-primary: #9333ea;
  --color-primary-dark: #7e22ce;
  /* ... etc ... */
}
```

---

## Support

All form pages now use a consistent, professional design that automatically adapts to different screen sizes and devices. The system is maintainable, scalable, and easy to customize.

For questions or modifications, refer to the forms.css file for detailed comments and structure.
