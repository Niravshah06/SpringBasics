package com.nirav;

import org.springframework.beans.factory.BeanFactory;  
import org.springframework.beans.factory.xml.XmlBeanFactory;  
import org.springframework.core.io.ClassPathResource;  
import org.springframework.core.io.Resource;  

public class TestPerson {
	public static void main(String[] args) {
		Resource resource = new ClassPathResource("applicationContext.xml");
		BeanFactory factory = new XmlBeanFactory(resource);

		
		//specify the id of bean you created in applicationcontext.xml
		Person student = (Person) factory.getBean("personbean");
		student.displayName();
		
		
	}
}