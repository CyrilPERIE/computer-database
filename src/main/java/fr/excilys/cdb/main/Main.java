package fr.excilys.cdb.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.cdb.SpringConfig;
import fr.excilys.cdb.view.View;

public class Main {
	
	public static void main(String[] args){
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		View view = context.getBean(View.class);
		context.close();
		view.client();
		
	}

}
