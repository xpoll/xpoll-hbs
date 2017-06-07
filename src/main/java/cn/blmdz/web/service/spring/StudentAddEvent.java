package cn.blmdz.web.service.spring;

import org.springframework.context.ApplicationEvent;

/**
 * 增加学生的监听事件(监听的事件对象)
 */
public class StudentAddEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private String name;

	public StudentAddEvent(Object source) {
		super(source);
	}
	public String getStudentName() {
		return name;
	}
}
