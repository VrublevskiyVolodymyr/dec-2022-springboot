package ua.com.owu.dec2022springboot.services;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendEmail(String email, String body, Optional<File> file) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(email);
        helper.setText("<h2> " + body + "</h2>", true);

        if (file.isPresent()) {
            File attachment = file.get();
            FileSystemResource fileSystemResource = new FileSystemResource(attachment);
            helper.addAttachment(attachment.getName(), fileSystemResource);
        }
        helper.setFrom(new InternetAddress("v637904@gmail.com"));
        javaMailSender.send(mimeMessage);
    }
}
