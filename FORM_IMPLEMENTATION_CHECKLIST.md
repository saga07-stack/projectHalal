# Form Component Implementation Checklist

This checklist helps you implement the new forms.css styling in any HTML form page.

## For Each Form Page

### Step 1: Add Meta Tags
```html
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <!-- Rest of head tags -->
</head>
```

### Step 2: Remove Old Form Area Divs
Remove these old classes:
- ❌ `.user_info_form_area`
- ❌ `.info_form_area`
- ❌ `.detail_area`

Replace with:
- ✅ `.form-container` (outer wrapper)
- ✅ `.form-card` (card wrapper)

### Step 3: Add Form Container
**Old:**
```html
<div class="user_info_form_area">
  <form>...</form>
</div>
```

**New:**
```html
<div class="form-container">
  <div class="form-card">
    <form>...</form>
  </div>
</div>
```

### Step 4: Add Form Header
**Old:**
```html
<h2 class="title">Page Title</h2>
<p class="input_message">Subtitle</p>
```

**New:**
```html
<div class="form-header with-border">
  <h2 class="form-title">Page Title</h2>
  <p class="form-subtitle">Subtitle</p>
</div>
```

### Step 5: Convert Form Groups
**Old:**
```html
<ul class="input_list">
  <li>
    <label><span class="input_title">Field Label</span></label>
    <input type="text" />
  </li>
</ul>
```

**New:**
```html
<div class="input-list">
  <div class="form-group">
    <label class="form-label">Field Label</label>
    <input type="text" class="form-input" />
  </div>
</div>
```

### Step 6: Update Textareas
**Old:**
```html
<textarea rows="6" th:field="*{field}"></textarea>
```

**New:**
```html
<textarea rows="6" th:field="*{field}" class="form-textarea"></textarea>
```

### Step 7: Update Select Dropdowns
**Old:**
```html
<select name="field">
  <option>Option</option>
</select>
```

**New:**
```html
<select name="field" class="form-select">
  <option>Option</option>
</select>
```

### Step 8: Update Error Messages
**Old:**
```html
<ul class="error_list">
  <li th:each="err:${#fields.detailedErrors()}" th:text="${err.message}"></li>
</ul>
```

**New:**
```html
<div class="error-container">
  <ul class="error-list">
    <li th:each="err:${#fields.detailedErrors()}" th:text="${err.message}"></li>
  </ul>
</div>
```

### Step 9: Update Buttons
**Old:**
```html
<input type="submit" value="確認" class="send_button" />
<form th:action="@{/back}" method="post">
  <input type="submit" value="戻る" class="back_button" />
</form>
```

**New:**
```html
<div class="form-actions two-buttons">
  <button type="submit" class="btn btn-primary send_button">確認</button>
  <a th:href="@{/back}" class="btn btn-secondary back_button">戻る</a>
</div>
```

### Step 10: Add Form Footer (Optional)
```html
<div class="form-footer">
  <p class="form-footer-text">
    Already have account? 
    <a th:href="@{/login}" class="form-footer-link">Login here</a>
  </p>
</div>
```

## Form Type Variants

Choose the appropriate wrapper type:

### Login Form
```html
<div class="form-container login-form">
  <div class="form-card">
    <!-- Login form -->
  </div>
</div>
```

### Registration Form
```html
<div class="form-container registration-form">
  <div class="form-card">
    <!-- Registration form -->
  </div>
</div>
```

### Admin Form
```html
<div class="form-container admin-form">
  <div class="form-card admin">
    <!-- Admin form -->
  </div>
</div>
```

### Verification Form
```html
<div class="form-container verification-form">
  <div class="form-card compact">
    <!-- Verification form -->
  </div>
</div>
```

## Completed Example

### BEFORE (Old):
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8" />
  <title>User Registration</title>
</head>
<body>
  <h2 class="title">User Registration</h2>
  <p class="input_message">Please enter your details</p>
  
  <div class="user_info_form_area">
    <form th:action="@{/user/regist}" method="post" th:object="${userForm}">
      <th:block th:if="${#fields.hasErrors()}">
        <ul class="error_list">
          <li th:each="err:${#fields.detailedErrors()}" th:text="${err.message}"></li>
        </ul>
      </th:block>
      
      <ul class="input_list">
        <li>
          <label><span class="input_title">Name</span></label>
          <input type="text" th:field="*{name}" />
        </li>
        <li>
          <label><span class="input_title">Email</span></label>
          <input type="email" th:field="*{email}" />
        </li>
        <li>
          <label><span class="input_title">Address</span></label>
          <textarea rows="4" th:field="*{address}"></textarea>
        </li>
      </ul>
      
      <input type="submit" value="Register" class="send_button" />
    </form>
    
    <form th:action="@{/}" method="get">
      <input type="submit" value="Cancel" class="back_button" />
    </form>
  </div>
</body>
</html>
```

### AFTER (New):
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>User Registration</title>
</head>
<body>
  <div class="form-container registration-form">
    <div class="form-card">
      <div class="form-header with-border">
        <h1 class="form-title">User Registration</h1>
        <p class="form-subtitle">Please enter your details</p>
      </div>
      
      <form th:action="@{/user/regist}" method="post" th:object="${userForm}">
        <!-- Error Messages -->
        <th:block th:if="${#fields.hasErrors()}">
          <div class="error-container">
            <ul class="error-list">
              <li th:each="err:${#fields.detailedErrors()}" th:text="${err.message}"></li>
            </ul>
          </div>
        </th:block>
        
        <!-- Form Fields -->
        <div class="input-list">
          <div class="form-group">
            <label class="form-label">Name</label>
            <input type="text" th:field="*{name}" class="form-input" />
          </div>
          
          <div class="form-group">
            <label class="form-label">Email</label>
            <input type="email" th:field="*{email}" class="form-input" />
          </div>
          
          <div class="form-group">
            <label class="form-label">Address</label>
            <textarea rows="4" th:field="*{address}" class="form-textarea"></textarea>
          </div>
        </div>
        
        <!-- Action Buttons -->
        <div class="form-actions two-buttons">
          <button type="submit" class="btn btn-primary">Register</button>
          <a th:href="@{/}" class="btn btn-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</body>
</html>
```

## CSS Classes to Remove

These old classes are no longer needed (but won't break anything):
- `input_title` → Replace with `.form-label`
- `input_value` → Replace with `.form-help-text`
- `input_message` → Replace with `.form-subtitle`
- `input_list` (ul) → Replace with `.input-list` (div)
- `error_list` → Keep but wrap in `.error-container`
- `user_info_form_area` → Replace with `.form-container` + `.form-card`
- `send_button` → Keep class but use with `.btn .btn-primary`
- `back_button` → Keep class but use with `.btn .btn-secondary`

## Common Button Combinations

### Primary + Secondary
```html
<div class="form-actions two-buttons">
  <button type="submit" class="btn btn-primary">Submit</button>
  <button type="button" class="btn btn-secondary">Cancel</button>
</div>
```

### Single Primary
```html
<div class="form-actions">
  <button type="submit" class="btn btn-primary btn-full-width">Submit</button>
</div>
```

### Multiple Actions
```html
<div class="form-actions">
  <button type="submit" class="btn btn-primary">Submit</button>
  <button type="button" class="btn btn-secondary">Save Draft</button>
  <button type="reset" class="btn btn-outline">Reset</button>
</div>
```

### Delete Action
```html
<div class="form-actions right">
  <button type="submit" class="btn btn-danger">Delete</button>
</div>
```

## Special Cases

### Radio Buttons
```html
<div class="form-group">
  <label class="form-label">Choose Option</label>
  <div class="form-radio">
    <label>
      <input type="radio" name="option" value="1" /> Option 1
    </label>
    <label>
      <input type="radio" name="option" value="2" /> Option 2
    </label>
  </div>
</div>
```

### Checkboxes
```html
<div class="form-group">
  <div class="form-checkbox">
    <input type="checkbox" id="terms" />
    <label for="terms">I agree to terms</label>
  </div>
</div>
```

### With Required Asterisk
```html
<label class="form-label required">Email Address</label>
<!-- Automatically adds red * -->
```

### With Help Text
```html
<div class="form-group">
  <label class="form-label">Password</label>
  <input type="password" class="form-input" />
  <small class="form-help-text">Must be at least 8 characters</small>
</div>
```

### Side-by-Side Fields (Responsive)
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

## Testing Checklist

After updating a form:
- ✅ Test on desktop (1200px+)
- ✅ Test on tablet (768px-1024px)
- ✅ Test on mobile (375px-480px)
- ✅ Check all form inputs work
- ✅ Verify error messages display
- ✅ Test button clicks
- ✅ Check Thymeleaf variables render
- ✅ Verify form submission works
- ✅ Check responsive layout
- ✅ Test on different browsers (Chrome, Firefox, Safari)

## Common Issues & Solutions

### Issue: Form not centered
**Solution:** Make sure `.form-container` is the outer wrapper

### Issue: Buttons not aligned
**Solution:** Use `.form-actions` wrapper and appropriate modifier (`.two-buttons`, `.horizontal`)

### Issue: Input too wide
**Solution:** Input is 100% by default. Wrap in `.form-group` to constrain

### Issue: Old styles still showing
**Solution:** Clear browser cache (Ctrl+Shift+R or Cmd+Shift+R)

### Issue: Thymeleaf not working
**Solution:** Make sure `xmlns:th="http://www.thymeleaf.org"` is in `<html>` tag

### Issue: Layout broken on mobile
**Solution:** Add `<meta name="viewport" content="width=device-width, initial-scale=1.0" />`

## Quick Start Template

Use this as a starting point for new forms:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
  th:replace="~{common/layout_5block :: layout(~{::title},~{::body/content()})}">

<head>
  <title>Your Form Title</title>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>

<body class="user your_form_class">
  <div class="form-container">
    <div class="form-card">
      <!-- Header -->
      <div class="form-header with-border">
        <h1 class="form-title">Form Title</h1>
        <p class="form-subtitle">Form subtitle</p>
      </div>

      <!-- Form -->
      <form th:action="@{/your/action}" method="post" th:object="${yourFormObject}">
        
        <!-- Errors -->
        <th:block th:if="${#fields.hasErrors()}">
          <div class="error-container">
            <ul class="error-list">
              <li th:each="err:${#fields.detailedErrors()}" th:text="${err.message}"></li>
            </ul>
          </div>
        </th:block>

        <!-- Fields -->
        <div class="input-list">
          <div class="form-group">
            <label class="form-label required">Field Label</label>
            <input type="text" th:field="*{fieldName}" class="form-input" />
          </div>
          <!-- Add more fields as needed -->
        </div>

        <!-- Actions -->
        <div class="form-actions two-buttons">
          <button type="submit" class="btn btn-primary">Submit</button>
          <a th:href="@{/back}" class="btn btn-secondary">Cancel</a>
        </div>
      </form>

      <!-- Footer (Optional) -->
      <div class="form-footer">
        <p class="form-footer-text">Already have account? <a th:href="@{/login}" class="form-footer-link">Login</a></p>
      </div>
    </div>
  </div>
</body>

</html>
```

---

## Support

Need help? Check:
1. `FORMS_CSS_GUIDE.md` - Detailed documentation
2. `QUICK_CSS_REFERENCE.md` - Quick lookups
3. `forms.css` - Inline comments and explanations
4. Look at already-updated pages for examples

---

**Happy Form Building!** ✨
