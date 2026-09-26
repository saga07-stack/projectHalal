# Quick CSS Classes Reference

## Most Used CSS Classes

### Container & Structure
```
.form-container          Main wrapper with background
.form-card              Card with shadow and padding
.form-header            Title + subtitle section
.form-group             Label + input wrapper
.input-list             Multiple form fields
```

### Forms
```
.form-title              Page title
.form-subtitle           Descriptive text
.form-label              Field label (use with ::required modifier)
.form-input              Text/email/password input
.form-textarea           Multi-line text input
.form-select             Select dropdown
.form-help-text          Helper text below input
```

### Buttons
```
.btn                     Base button class
.btn-primary             Orange main action
.btn-secondary           Blue secondary action
.btn-danger              Red delete action
.btn-outline             Orange outline button
.btn-full-width          100% width button
.btn-sm / .btn-lg        Small/Large size
```

### Messages & Errors
```
.error-container         Red error box
.error-list              List of errors
.success-container       Green success box
.field-error             Individual field error
```

### OTP & Special
```
.otp-inputs              6-digit OTP input grid
.display-value           Highlighted value display
.email-display           Email display box
.step-indicator          Progress steps
.timer                   Countdown timer
```

### Button Groups
```
.form-actions            Button wrapper
.form-actions.two-buttons Two equal-width buttons
.form-actions.horizontal  Horizontal layout
```

### Form Variants
```
.form-container.login-form        Blue gradient
.form-container.registration-form Orange gradient
.form-container.verification-form Light blue gradient
.form-container.admin-form        Admin orange gradient
.form-card.admin                  Admin card styling
.form-card.compact                Smaller card
```

### Utility Classes
```
.hidden                  display: none
.visible                 display: block
.mb-sm / .mb-md / .mb-lg Margin bottom
.mt-sm / .mt-md / .mt-lg Margin top
.text-center             Center align text
.opacity-50 / .opacity-75 Transparency
```

## Common Component Patterns

### Login/Sign In Form
```html
<div class="form-container login-form">
  <div class="form-card">
    <div class="form-header">
      <h1 class="form-title">Login</h1>
      <p class="form-subtitle">Enter your credentials</p>
    </div>
    <form>
      <div class="form-group">
        <label class="form-label">Email</label>
        <input class="form-input" />
      </div>
      <div class="form-actions">
        <button class="btn btn-primary btn-full-width">Sign In</button>
      </div>
    </form>
  </div>
</div>
```

### Registration Form
```html
<div class="form-container registration-form">
  <div class="form-card">
    <div class="form-header with-border">
      <h1 class="form-title">Create Account</h1>
    </div>
    <div class="input-list">
      <div class="form-group">
        <label class="form-label required">Name</label>
        <input class="form-input" />
      </div>
      <!-- More fields -->
    </div>
    <div class="form-actions two-buttons">
      <button class="btn btn-primary">Register</button>
      <a class="btn btn-secondary">Cancel</a>
    </div>
  </div>
</div>
```

### Admin Form
```html
<div class="form-container admin-form">
  <div class="form-card admin">
    <div class="form-header with-border">
      <h1 class="form-title">Admin Form</h1>
    </div>
    <!-- Form content -->
  </div>
</div>
```

### Email Verification
```html
<div class="form-container verification-form">
  <div class="form-card compact">
    <div class="form-header">
      <h1 class="form-title small">Verify Email</h1>
    </div>
    <div class="email-display">
      <p>Sent to: <strong class="email-address">user@email.com</strong></p>
    </div>
    <div class="otp-inputs" id="otpInputs">
      <input inputmode="numeric" maxlength="1" />
      <!-- 5 more inputs -->
    </div>
    <div class="form-actions">
      <button class="btn btn-primary btn-full-width">Verify</button>
    </div>
  </div>
</div>
```

### Error Handling
```html
<div class="error-container">
  <ul class="error-list">
    <li>Error message 1</li>
    <li>Error message 2</li>
  </ul>
</div>
```

### Step-by-Step Form
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
</div>
```

## Responsive Breakpoints

- **Desktop:** 1024px and above
- **Tablet:** 768px to 1024px  
- **Mobile:** 480px to 768px
- **Small Mobile:** Below 480px

All components automatically resize for these breakpoints.

## Color Variables

Access in CSS:
```css
--color-primary: #e85d04          /* Orange */
--color-secondary: #3d98bf        /* Blue */
--color-error: #e02424            /* Red */
--color-success: #27a745          /* Green */
--color-text-primary: #1a1a1a     /* Dark */
--color-border: #e0e0e0           /* Light gray */
```

## Spacing Variables

```css
--spacing-xs: 4px
--spacing-sm: 8px
--spacing-md: 16px        /* Default form spacing */
--spacing-lg: 24px
--spacing-xl: 32px
--spacing-xxl: 40px       /* Card padding */
```

## Border Radius Variables

```css
--radius-sm: 4px
--radius-md: 8px          /* Default for inputs */
--radius-lg: 12px         /* Default for cards */
--radius-xl: 16px
```

## Quick Modification Examples

### Make all forms wider
```css
.form-card {
  max-width: 600px;  /* Default 500px */
}
```

### Change primary color
```css
:root {
  --color-primary: #your-color;
}
```

### Add more spacing
```css
:root {
  --spacing-md: 20px;  /* Default 16px */
}
```

### Change button height
```css
.btn {
  padding: 16px 24px;  /* Default 14px 16px */
}
```
