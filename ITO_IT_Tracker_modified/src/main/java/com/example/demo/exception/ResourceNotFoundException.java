package com.example.demo.exception;


public class ResourceNotFoundException extends RuntimeException  {
	
	
	
	String resourceName;
	String fieldName;
	String fieldName1;
	public String getFieldName1() {
		return fieldName1;
	}

	public void setFieldName1(String fieldName1) {
		this.fieldName1 = fieldName1;
	}
	long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s :%s" ,resourceName ,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String exceptionmessage) {
		super(exceptionmessage);
	}
	
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public long getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(long fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
	
	
}
