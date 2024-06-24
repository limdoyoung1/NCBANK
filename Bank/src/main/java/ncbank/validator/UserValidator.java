package ncbank.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ncbank.beans.UserBean;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserBean loginBean = (UserBean) target;

		String beanName = errors.getObjectName();
		System.out.println(beanName); 
		if (beanName.equals("mBean")) {
			System.out.println(beanName + "2");
			if (!loginBean.getPwd().equals(loginBean.getPwd2())) {
				System.out.println(beanName + "3");
				System.out.println(loginBean.getPwd() + "4");
				System.out.println(loginBean.getPwd() + "4");
				errors.rejectValue("pwd", "NotEquals");
				errors.rejectValue("pwd2", "NotEquals");
			}

			if (!loginBean.isIdExistCheck()) {
				errors.rejectValue("id", "DontCheckUserIdExist");
			}

		}
		
		

	}

}
