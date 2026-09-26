package jp.co.sss.shop.controller.client.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.repository.UserRepository;
import jp.co.sss.shop.service.EmailCodeCreater;
import jp.co.sss.shop.service.EmailService;

/**
 * Password Reset Controller
 * Handles password reset flow with email verification
 */
@Controller
public class ResetUserController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepo;
	
	
	/**
	 * Display password reset page
	 */
	@GetMapping("/client/user/password/reset/input")
	public String showResetPasswordInput(Model model) {
		return "client/user/password_reset_input";
	}

	/**
	 * Send verification code to user email
	 */
	@PostMapping("/client/user/password/reset/send-code")
	@ResponseBody
	public PasswordResetResponse sendVerificationCode(
			@RequestBody PasswordResetRequest request,
			HttpSession session
	) {
		try {
			String email = request.getEmail().trim();
// -----------db check garna parcha ki email exist garxa ki nai
			// Validate email format
			User emailFromDb = userRepo.findByEmailAndDeleteFlag(email,0 );
			
			if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
				return new PasswordResetResponse(false, "メールアドレスが正しくありません");
			}else if (emailFromDb == null) {
				return new PasswordResetResponse(false, "メールアドレスが存在しません");
			}else {
				email = emailFromDb.getEmail(); // Use the email from the database to ensure consistency
			}

			// Generate verification code
			EmailCodeCreater codeGenerator = new EmailCodeCreater();
			int verificationCode = codeGenerator.RandomNumberGenerator();

			// Send email with code
			emailService.sendVerificationCode(email, verificationCode);

			// Store code in session
			session.setAttribute("resetVerificationCode", verificationCode);
			session.setAttribute("resetEmail", email);

			return new PasswordResetResponse(true, "コードがメールアドレスに送信されました");

		} catch (Exception e) {
			e.printStackTrace();
			return new PasswordResetResponse(false, "エラーが発生しました: " + e.getMessage());
		}
	}

	/**
	 * Verify the code sent to email
	 */
	@PostMapping("/client/user/password/reset/verify-code")
	@ResponseBody
	public PasswordResetResponse verifyCode(
			@RequestBody PasswordResetRequest request,
			HttpSession session
	) {
		try {
			String email = request.getEmail();
			String code = request.getCode();

			// Get stored values from session
			Integer storedCode = (Integer) session.getAttribute("resetVerificationCode");
			String storedEmail = (String) session.getAttribute("resetEmail");

			// Verify email matches
			if (storedEmail == null || !storedEmail.equals(email)) {
				return new PasswordResetResponse(false, "メールアドレスが一致しません");
			}

			// Verify code matches
			if (storedCode == null || !storedCode.toString().equals(code)) {
				return new PasswordResetResponse(false, "コードが正しくありません");
			}

			// Mark as verified in session
			session.setAttribute("resetCodeVerified", true);

			return new PasswordResetResponse(true, "コードが確認されました");

		} catch (Exception e) {
			e.printStackTrace();
			return new PasswordResetResponse(false, "エラーが発生しました");
		}
	}

	/**
	 * Reset password after verification
	 */
	@PostMapping("/client/user/password/reset/confirm")
	@ResponseBody
	public PasswordResetResponse resetPassword(
			@RequestBody PasswordResetRequest request,
			HttpSession session
	) {
		try {
			// Check if code is verified
			Boolean isVerified = (Boolean) session.getAttribute("resetCodeVerified");
			if (isVerified == null || !isVerified) {
				return new PasswordResetResponse(false, "コード検証が完了していません");
			}

			String email = request.getEmail();
			String newPassword = request.getNewPassword();

			// Validate password
			if (newPassword == null || newPassword.length() < 8) {
				return new PasswordResetResponse(false, "パスワードは8文字以上である必要があります");
			}

			// TODO: Update user password in database
			// userService.updatePassword(email, newPassword);

			// Clear session attributes
			session.removeAttribute("resetVerificationCode");
			session.removeAttribute("resetEmail");
			session.removeAttribute("resetCodeVerified");

			return new PasswordResetResponse(true, "パスワードがリセットされました");

		} catch (Exception e) {
			e.printStackTrace();
			return new PasswordResetResponse(false, "エラーが発生しました");
		}
	}

	/**
	 * Request/Response classes
	 */
	public static class PasswordResetRequest {
		private String email;
		private String code;
		private String newPassword;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
	}

	public static class PasswordResetResponse {
		private boolean success;
		private String message;

		public PasswordResetResponse(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}

