package ua.com.owu.dec2022springboot.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendEmail(String email, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setTo(email);
            helper.setText("<h2> " + body + "</h2>", true);
            helper.setFrom(new InternetAddress("v637904@gmail.com"));
        javaMailSender.send(mimeMessage);
    }
}
