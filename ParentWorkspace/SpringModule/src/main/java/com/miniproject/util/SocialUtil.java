package com.miniproject.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

/**
 * @author MuhilKennedy 
 * Utility class for social auth services.
 */
@Component
public class SocialUtil {

	private static Logger logger = LoggerFactory.getLogger(SocialUtil.class);

	@Autowired
	private ConfigUtil configUtil;

	public static enum googleData {
		firstName, lastName, email, imageUrl, accessToken
	}

	public String generateVerificationURL(String url, String code, String email) {
		try {
			URIBuilder urlBuilder = new URIBuilder(
					url.replaceAll(CommonUtil.userCreationPath, CommonUtil.verificationPath));
			urlBuilder.addParameter("code", code);
			urlBuilder.addParameter("email", email);
			return urlBuilder.toString();
		} catch (Exception e) {
			logger.error("generateVerificationURL : Exception - " + e.getMessage());
		}
		return null;
	}

	public String verificationEmailHTMLBody(String code, String url) {
		String body = null;
		Element h1Element = new Element("h1");
		h1Element.text("VERIFICATION CODE - " + code);
		body = h1Element.outerHtml();
		if(url != null) {
			Element anchorElement = new Element("a");
			anchorElement.attr("href", url);
			anchorElement.text("Click here to verify your Accout");
			body = h1Element.outerHtml() + "\n" + anchorElement.outerHtml();
		}
		return body;
	}

	/**
	 * @param recipientEmail
	 * @param subject
	 * @param body text html part for formatting
	 * @param attachment file
	 */
	public void sendEmail(List<String> recipientEmail, String subject, String body, File attachment) {
		if (configUtil.isMailingServiceEnabled()) {
			// Set system properties
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			// Make sure the used dev email must have Allow less secure apps option enabled.
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(configUtil.getSenderGmailId(),
							configUtil.getSenderGmailPassword());
				}
			});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(configUtil.getSenderGmailId()));
				message.setSubject(configUtil.getApplicationName() + " : " + subject);
				Multipart multipartObject = new MimeMultipart();
				// Creating first MimeBodyPart object which contains body text.
				InternetHeaders headers = new InternetHeaders();
				headers.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
				BodyPart bodyText = new MimeBodyPart(headers, body.getBytes(StandardCharsets.UTF_8.name()));
				multipartObject.addBodyPart(bodyText);
				// Creating second MimeBodyPart object which contains attachment.
				if (attachment != null) {
					BodyPart fileBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(attachment);
					fileBodyPart.setDataHandler(new DataHandler(source));
					fileBodyPart.setFileName(attachment.getName());
					multipartObject.addBodyPart(fileBodyPart);
				}
				// Attach body text and file attachment to the email.
				message.setContent(multipartObject, MediaType.TEXT_HTML_VALUE);
				recipientEmail.stream().forEach(recipient -> {
					try {
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
						logger.debug("sendEmail :: Sending email to Recipient - " + recipient);
						Transport.send(message);
						logger.debug("sendEmail :: Email sent Successfully to Recipient - " + recipient);
					} catch (Exception ex) {
						logger.debug("sendEmail :: Error sending mail to Recipient - " + recipient);
					}
				});

			} catch (Exception e) {
				logger.error("sendEmail :: Sending email to Recipient - " + e);
			}
		}
	}

}
