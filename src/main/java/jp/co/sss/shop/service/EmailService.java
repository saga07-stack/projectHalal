package jp.co.sss.shop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationCode(String toEmail, int code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Email address same, but display name = "Nepali Pasal"
            helper.setFrom(new InternetAddress(fromEmail, "Nepali Pasal"));
            helper.setTo(toEmail);
            helper.setSubject("Your Verification Code - Nepali Pasal");

            String htmlContent = buildHtmlTemplate(code);
            helper.setText(htmlContent, true); // true = isHtml

            mailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            // production ma proper logging + exception handling gara
        }
    }

    private String buildHtmlTemplate(int code) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<body style='margin:0; padding:0; background-color:#f4f4f4; font-family:Arial, sans-serif;'>" +
                "  <table width='100%' cellpadding='0' cellspacing='0'>" +
                "    <tr><td align='center' style='padding:30px 0;'>" +
                "      <table width='400' cellpadding='0' cellspacing='0' style='background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.1);'>" +
                "        <tr><td style='background:linear-gradient(135deg, #667eea, #764ba2); padding:20px; text-align:center;'>" +
                "          <h1 style='color:#ffffff; margin:0; font-size:22px;'>Nepali Pasal</h1>" +
                "        </td></tr>" +
                "        <tr><td style='padding:30px; text-align:center;'>" +
                "          <p style='font-size:15px; color:#333;'>Your verification code is:</p>" +
                "          <div style='font-size:32px; font-weight:bold; color:#667eea; letter-spacing:4px; margin:15px 0;'>" + code + "</div>" +
                "          <p style='font-size:13px; color:#888;'>This code will expire shortly. Please do not share it with anyone.</p>" +
                "        </td></tr>" +
                "        <tr><td style='background:#f4f4f4; padding:15px; text-align:center;'>" +
                "          <p style='font-size:12px; color:#aaa; margin:0;'>© 2026 Nepali Pasal. All rights reserved.</p>" +
                "        </td></tr>" +
                "      </table>" +
                "    </td></tr>" +
                "  </table>" +
                "</body>" +
                "</html>";
    }
}
