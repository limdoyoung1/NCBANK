package ncbank.utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.stereotype.Repository;

@Repository
public class EmailManager {
   // 구글 이메일
   private final String user_email = "jcjhjg12@gmail.com";
   // 애플리케이션 비번
   private final String user_pw = "bpyt jizy nxmn pqro";
   // 호스트 주소 - 호스트를 통해 Gmail SMTP 
   private final String smtp_host = "smtp.gmail.com";
   // 포트번호
   private final int smtp_port = 465; // TLS : 587, SSL : 465
   
   // 받을 이메일, 제목, 내용
   public void sendEmail(String email, String subject, String text, String filePath) {
      System.out.println("EmailManager sendEmail()");

      Properties props = System.getProperties();
      props.put("mail.smtp.host", smtp_host);
      props.put("mail.smtp.port", smtp_port);
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.ssl.enable", "true"); // TLS 사용
      props.put("mail.smtp.ssl.trust", smtp_host);
      
      
      System.out.println("filePath : " + filePath);
      
      Session session = Session.getInstance(props, new Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user_email, user_pw);
         }
      });

      try {
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(user_email));
         // 받는 이메일
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
         // 제목
         message.setSubject(subject);
         // 내용
         MimeBodyPart messageBodyPart = new MimeBodyPart();
         messageBodyPart.setText(text);
         System.out.println("[text 세팅]");
         
         // 첨부 파일
         MimeBodyPart bodyPart = null;
         if (null != filePath && !filePath.isEmpty()) {
            bodyPart = new MimeBodyPart();
               FileDataSource fileDataSource = new FileDataSource(new File(filePath));
               bodyPart.setDataHandler(new DataHandler(fileDataSource));
               bodyPart.setFileName(fileDataSource.getName());
               System.out.println("[메일 첨부파일 세팅]");
         }
         
            // MimeMultipart : 이메일 본문과 첨부파일을 포함하는 복합 메시지 객체
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart); // 메시지 추가
            if (null != bodyPart) {
               multipart.addBodyPart(bodyPart); // 첨부파일 추가
            }
            
            // 메시지에 Multipart 설정
            message.setContent(multipart);
            System.out.println("[메세지 객체 세팅]");
            
         // 발송
         Transport.send(message);
         System.out.println("[발송]");

      } catch (MessagingException e) {
         e.printStackTrace();
         System.out.println("에외발생");
      } catch (Exception e) {
         e.printStackTrace();
      }

      
   } // Send()
   
   
    // 받을 이메일, 제목, JSP 경로
    public void sendJspEmail(String email, String subject, String jspPath, 
          HttpServletRequest request, HttpServletResponse response) {
        System.out.println("EmailManager sendJspEmail()");

        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtp_host);
        props.put("mail.smtp.port", smtp_port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true"); // TLS 사용
        props.put("mail.smtp.ssl.trust", smtp_host);
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user_email, user_pw);
            }
        });

        try {
            // JSP에서 HTML 내용 가져오기
            String htmlContent = getHtmlContent(request, response, jspPath);
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user_email));
            
            // 받는 이메일
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            // 제목
            message.setSubject(subject);
            // 내용

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
            System.out.println("[jsp 세팅]");
            
            // MimeMultipart : 이메일 본문과 첨부파일을 포함하는 복합 메시지 객체
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart); // 메시지 추가
            
            // 메시지에 Multipart 추가
            message.setContent(multipart);
            System.out.println("[메세지 객체 세팅]");
            
            // 발송
            Transport.send(message);
            System.out.println("[발송]");
            
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("에외발생");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // Send()
    
    // 받을 이메일, 제목, JSP 경로, 인라인 이미지 map 
	public void sendJspEmailWithInlineImage(String email, String subject, String jspPath,
			Map<String, String> inlinePathImgs, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("EmailManager sendJspEmailWithInlineImage()");

		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtp_host);
		props.put("mail.smtp.port", smtp_port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true"); // TLS 사용
		props.put("mail.smtp.ssl.trust", smtp_host);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user_email, user_pw);
			}
		});

		try {
			// JSP에서 HTML 내용 가져오기
			String htmlContent = getHtmlContent(request, response, jspPath);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user_email));
			// 받는 이메일
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			// 제목
			message.setSubject(subject);

			// HTML 내용을 담을 파트
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
			System.out.println("[htmlPart set]");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(htmlPart); // html 내용 추가
			System.out.println("[multipart add htmlPart]");

			/* 인라인 이미지 세팅 */
			for (Map.Entry<String, String> entry : inlinePathImgs.entrySet()) {
				MimeBodyPart imagePart = new MimeBodyPart();
				FileDataSource fileDataSource = new FileDataSource(new File(entry.getValue()));

				imagePart.setDataHandler(new DataHandler(fileDataSource));
				imagePart.setHeader("Content-ID", entry.getKey());
				imagePart.setDisposition(MimeBodyPart.INLINE);
				imagePart.setFileName(fileDataSource.getName());

				multipart.addBodyPart(imagePart); // 인라인 이미지 추가
				System.out.println("[multipart add imagePart]");
			}
			message.setContent(multipart);

			// 발송
			Transport.send(message);
			System.out.println("[발송]");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("에외발생");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    // jsp 파일의 html 내용을 추출
    private static String getHtmlContent(HttpServletRequest request, HttpServletResponse response, String jspPath) throws ServletException, IOException {
        StringWriter writer = new StringWriter();
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);

        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() {
                return new PrintWriter(writer);
            }
        };

        dispatcher.include(request, responseWrapper);
        return writer.toString();
    }
    
}
   
