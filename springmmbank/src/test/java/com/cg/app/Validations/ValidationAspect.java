package com.cg.app.Validations;

import java.util.logging.Logger;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class ValidationAspect {
	private static Logger logger = Logger.getLogger(ValidationAspect.class.getName());

	@Around("execution(* com.cg.app.calculator.SavingsAccountSJDAOImpl.*(..))")
	public void ValidateLogic() {
		logger.info("Business logic starts");

	}

	@AfterThrowing("execution(* com.cg.app.calculator.SavingsAccountSJDAOImpl.*(..))")
	public void ValidateLogic() {

	}

}
