================================================================================
                    NEPALI PASAL LEARNING ROADMAP
            Spring Boot 3.1.5, Java 17, Thymeleaf, Oracle DB
================================================================================

PROJECT STATUS: INTERMEDIATE STARTER
- Core e-commerce features exist
- Critical security gaps need fixing first
- Foundation for learning path established
- No deadline - learning > speed

================================================================================
                       ROADMAP FILES OVERVIEW
================================================================================

FILE 1: 01_UNDONE_PARTS.txt
  - 30 tasks: completely missing features and infrastructure
  - Format: [ID] Title with Why/Where/Steps/DoneWhen/Concept/Difficulty
  - Groups: User Features / Developer Features / Security
  - Range: Easy to Hard difficulty

FILE 2: 02_IN_PROGRESS_AND_BROKEN_PARTS.txt
  - 20 tasks: half-done, buggy, or poorly designed code
  - Format: same as File 1
  - Examples: plaintext passwords, session-only cart, no CSRF, missing validations
  - These block production deployment

FILE 3: 03_NEW_FEATURES_AND_LEARNING_TOPICS.txt
  - 35+ learning topics and feature ideas
  - Covers Java, Spring Boot, JPA, Oracle, Testing, Logging, Design
  - Practical features for Nepali e-commerce
  - Organized by difficulty level

================================================================================
                      🚀 START HERE (5 TASKS - 1-2 DAYS EACH)
================================================================================

These are the FIRST 5 tasks. Do them in order. One at a time.
After each task: test, commit to git, then move to next.

  1. [S-01] Hash passwords with BCrypt
     Why: CRITICAL SECURITY - plaintext passwords expose customer data
     Difficulty: EASY
     Time: 2 hours
     Learn: Spring Security, BCrypt, password best practices

  2. [S-02] Add Spring Security framework basics
     Why: Prepare for other security tasks (CSRF, auth filters)
     Difficulty: MEDIUM
     Time: 3 hours
     Learn: Spring Security architecture, beans, filters

  3. [S-03] Implement CSRF token protection
     Why: Forms can be exploited via CSRF attacks
     Difficulty: MEDIUM
     Time: 2 hours
     Learn: CSRF attacks, token handling, Thymeleaf integration

  4. [D-01] Externalize secrets from properties
     Why: Email password, DB credentials visible in source
     Difficulty: EASY
     Time: 1 hour
     Learn: Environment variables, 12-factor apps, secrets management

  5. [D-02] Add global exception handler
     Why: Errors leak info, inconsistent error responses
     Difficulty: MEDIUM
     Time: 2 hours
     Learn: @ControllerAdvice, exception hierarchy, error responses

================================================================================
                        PROGRESS CHECKLIST
================================================================================

START HERE (Do first):
  [ ] S-01: Hash passwords with BCrypt
  [ ] S-02: Add Spring Security framework basics
  [ ] S-03: Implement CSRF token protection
  [ ] D-01: Externalize secrets from properties
  [ ] D-02: Add global exception handler

LEVEL 1 - FOUNDATION (Security, Config, Error Handling):
  [ ] S-04: Add security headers (X-Frame-Options, CSP)
  [ ] S-05: Implement IDOR prevention (authorization checks)
  [ ] S-06: Add input validation & sanitization
  [ ] D-03: Setup SLF4J/Logback logging configuration
  [ ] D-04: Add request/response logging interceptor
  [ ] U-01: Implement password reset flow

LEVEL 2 - CORE BUSINESS (Cart, Checkout, Notifications):
  [ ] U-02: Persist cart in database
  [ ] U-03: Implement order checkout flow
  [ ] U-04: Update stock on order
  [ ] U-05: Add order status tracking (order state machine)
  [ ] U-06: Implement order confirmation emails
  [ ] U-07: Add product reviews & ratings system

LEVEL 3 - INTERMEDIATE (JPA Performance, Testing, API):
  [ ] D-05: Fix N+1 queries with fetch joins
  [ ] D-06: Add JUnit 5 & Mockito unit tests
  [ ] D-07: Setup @DataJpaTest for repositories
  [ ] D-08: Add Swagger/OpenAPI API documentation
  [ ] D-09: Implement Flyway database migrations

LEVEL 4 - INTERMEDIATE-PLUS (Advanced, Operations, Scale):
  [ ] D-10: Setup GitHub Actions CI/CD pipeline
  [ ] D-11: Add Docker & docker-compose files
  [ ] D-12: Implement Redis caching for products
  [ ] D-13: Add @Async email processing
  [ ] U-08: Implement payment gateway (Khalti/Stripe)

================================================================================
                        KEY RULES FOR SUCCESS
================================================================================

1. ONE TASK AT A TIME
   - Finish one task completely
   - Test it thoroughly (manual + automated)
   - Commit to git with clear message
   - Then move to next task

2. READ CODE FIRST
   - Read existing code before changing
   - Understand current patterns
   - Follow them, don't invent new ones

3. COMMIT OFTEN
   Format: [TASK_ID] Brief description
   Example: [S-01] Hash user passwords with BCrypt

4. TEST AFTER EACH TASK
   - Manual testing in browser
   - Check database changes
   - Verify no regressions

5. DOCUMENT AS YOU GO
   - Update project ABOUT.txt after major changes
   - Add comments for complex logic
   - Keep this roadmap current

================================================================================
                        LEARNING VALUE NOTES
================================================================================

Each task teaches real concepts that intermediate developers must know:

SECURITY LAYER (Tasks S-01 to S-06):
  - Why plaintext passwords fail
  - How BCrypt iterates hashes
  - Spring Security's filter chain
  - CSRF attack mechanics & tokens
  - Authorization checks (IDOR prevention)
  - Input validation strategies

DEVELOPER EXPERIENCE (Tasks D-01 to D-10):
  - Externalized configuration patterns
  - Exception handling best practices
  - Structured logging (SLF4J appender strategies)
  - Testing pyramid (unit/integration/E2E)
  - API documentation as code
  - Database migration safety
  - CI/CD workflow automation

BUSINESS LOGIC (Tasks U-01 to U-08):
  - E-commerce cart patterns (session vs DB)
  - Order state machines
  - Email notification service design
  - Product review moderation
  - Payment gateway integration patterns
  - Stock management atomicity

PERFORMANCE & SCALE (Tasks at Level 4):
  - N+1 query detection & fix
  - Redis caching strategies
  - Async task processing
  - Docker containerization

================================================================================
                        WHEN STUCK: ASK YOURSELF
================================================================================

Q: Where in the code does this happen?
A: Check ABOUT.txt structure, read the controller/service/entity chain

Q: What pattern should I follow?
A: Look at existing similar code, replicate style, don't invent new patterns

Q: How do I test this?
A: Manual first, then write automated tests as you learn

Q: Is this a breaking change?
A: Check if existing database data needs migration

Q: Does this need secrets?
A: Use environment variables, never hardcode credentials

================================================================================
                         PROJECT QUICK LINKS
================================================================================

Folder Structure:
  Backend: src/main/java/jp/co/sss/shop/
  Frontend: src/main/resources/templates/ & static/
  Config: src/main/resources/application*.properties
  Entities: entity/ folder
  Business: DTOService/ and service/ folders
  REST API: RestController/ folder

Current Technology Stack:
  - Spring Boot 3.1.5
  - Spring Data JPA (Hibernate)
  - Thymeleaf templates
  - Oracle Database (PDBORCL)
  - Maven build tool
  - Java 17
  - Jakarta EE (jakarta.servlet, jakarta.persistence)

Key Entry Points:
  - Main: SharedShopNameApplication.java
  - Login: controller/login/LoginController.java
  - Products: controller/client/item/
  - Cart: controller/client/basket/
  - Orders: controller/client/order/
  - Admin: controller/admin/

Properties Files (DO NOT hardcode secrets here):
  - application.properties (all profiles)
  - application-local.properties (local dev)
  - application-aws.properties (production)

Database Entities (8 tables):
  - User (authentication)
  - Item (products)
  - Category (product categories)
  - Order (customer orders)
  - OrderItem (order line items)
  - CartItems (DB-based cart)
  - FavoriteItem (wishlist)
  - OrderInquiries (order tracking codes)

================================================================================
                         SUPPORT & RESOURCES
================================================================================

Spring Boot Documentation:
  - https://spring.io/projects/spring-boot
  - https://spring.io/projects/spring-security

Java Best Practices:
  - https://github.com/google/styleguide/blob/gh-pages/javaguide.md

Testing Framework:
  - JUnit 5: https://junit.org/junit5/
  - Mockito: https://site.mockito.org/

Security:
  - OWASP Top 10: https://owasp.org/www-project-top-ten/
  - Spring Security Docs: https://spring.io/projects/spring-security

Database:
  - JPA/Hibernate: https://hibernate.org/orm/
  - Oracle JDBC: https://www.oracle.com/database/technologies/

================================================================================
Generated: November 2026
Project: Nepali Pasal (E-Commerce Platform)
Framework: Spring Boot 3.1.5, Java 17
Goal: Reach intermediate-plus level in backend development
================================================================================
