package mp.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import mp.dto.UsuarioRequest;

@Service
public class MailService {
	@Value("${app.sendgrid.api.key}")
	private String SENDGRID_API_KEY;
	
	@Value("${app.sendgrid.api.email}")
	private String SENDGRID_API_EMAIL;
	
	public void sendConfirmationEmail(UsuarioRequest usuario) throws IOException {
		Email from = new Email(SENDGRID_API_EMAIL);
		String subject = "Tu cuenta fue creada con exito";
		Email to = new Email(usuario.getEmail());
		Content content = new Content("text/plain", "Felicidades " + usuario.getUsername() + 
				" por unirte a nuestro proyecto.\n"
				+ "Ya puedes revisar todo nuestro catalogo logueandote en nuestra pagina");
		Mail mail = new Mail(from,subject,to,content);
		
		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		Request request = new Request();
		try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      System.out.println(response.getStatusCode());
		      System.out.println(response.getBody());
		      System.out.println(response.getHeaders());
		    } catch (IOException ex) {
		      throw ex;
		    }
	}
}
