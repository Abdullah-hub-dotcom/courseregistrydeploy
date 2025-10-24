package com.example.CourseRegistrationSystem.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailsender;

    public void sendCourseRegistrationEmail(String toEmail, String studentName, String courseName) {
        try {
            MimeMessage message = mailsender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("🎓 Course Registration Confirmation - " + courseName);

            // Use regular string concatenation instead of """ text block
            String htmlContent = "<html>" +
                    "<body style=\"font-family: Arial; background:#f9f9f9; padding:20px;\">" +
                    "<div style=\"max-width:600px; margin:auto; background:#fff; padding:20px; border-radius:10px;\">" +
                    "<h2 style=\"color:#4CAF50; text-align:center;\">🎉 Congrats, " + studentName + "!</h2>" +
                    "<p>You have successfully enrolled in: <strong>" + courseName + "</strong></p>" +
                    "<p>Stay tuned for course updates.</p>" +
                    "<hr>" +
                    "<p style=\"text-align:center; color:#777;\">© 2025 Course Registration System</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);
            mailsender.send(message);

            System.out.println("✅ Email sent to " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
