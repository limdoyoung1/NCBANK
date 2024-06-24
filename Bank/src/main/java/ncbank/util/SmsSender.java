package ncbank.util;

import org.springframework.stereotype.Repository;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Repository
public class SmsSender {

	
	public String Smsvr(String phone,String text) {
	
		DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSEWDRD60JUAV9X", "FTFMTYRVT8ULHKLS9KWKSPDT26LLNITJ", "https://api.coolsms.co.kr");
		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
		Message message = new Message();
		//sendTo getPhone , text : ? random
		message.setFrom("01057633422");
		message.setTo(phone);
		message.setText(text);

		 try {
	            messageService.send(message);
	            return "success";
	        } catch (NurigoMessageNotReceivedException exception) {
	            System.out.println(exception.getFailedMessageList());
	            System.out.println(exception.getMessage());
	            return "failure";
	        } catch (Exception exception) {
	            System.out.println(exception.getMessage());
	            return "error";
	        }
	    }
	
}
