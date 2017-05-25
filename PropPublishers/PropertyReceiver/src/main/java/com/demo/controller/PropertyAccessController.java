package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.srcpkg.PropertySetup;
import com.demo.srcpkg.SourcePropertySetup;
import com.properties.receiver.annotation.PropertyScan;
import com.properties.receiver.annotation.SetProperty;

@RestController
@PropertyScan
public class PropertyAccessController {
	@Autowired
	private PropertySetup propertySetup;
	@Autowired
	private SourcePropertySetup sourcePropertySetup;
	
	@SetProperty(property = "DC04.SRVC.CHN.EditRole.TabDtls")
	private String tableName; 
	
	@RequestMapping("/rangeFinder")
	@ResponseBody
	
	public String home(@RequestParam("rangeID") String id, Model model) {
		String requestedFor=propertySetup.getProductName()+"</br>";
		requestedFor=requestedFor+propertySetup.getTableId()+"</br>";
		requestedFor=requestedFor+propertySetup.getTableName()+"</br>";
		requestedFor=requestedFor+propertySetup.getTableType()+"</br>";
		requestedFor = requestedFor+"<b>Second Object</b>"+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getProductName()+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getTableId()+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getTableName()+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getTableType()+"</br>";
		requestedFor = requestedFor+"<b>Third Object</b>"+"</br>";
		requestedFor=requestedFor+tableName+"</br>";
		requestedFor=requestedFor+propertySetup.getDateTobeParsed()+"</br>";
		
		model.addAttribute("name", requestedFor);
		return "<b>Object Requested is </b></br>"+requestedFor;

	}
	
	@RequestMapping("/")
	@ResponseBody
	
	public String login(Model model) {
		String requestedFor=propertySetup.getProductName()+"</br>";
		requestedFor=requestedFor+propertySetup.getTableId()+"</br>";
		requestedFor=requestedFor+propertySetup.getTableName()+"</br>";
		requestedFor=requestedFor+propertySetup.getTableType()+"</br>";
		requestedFor = requestedFor+"<b>Second Object</b>"+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getProductName()+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getTableId()+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getTableName()+"</br>";
		requestedFor=requestedFor+sourcePropertySetup.getTableType()+"</br>";
		requestedFor = requestedFor+"<b>Third Object</b>"+"</br>";
		requestedFor=requestedFor+tableName+"</br>";
		
		model.addAttribute("name", requestedFor);
		return "<b>Object Requested is </b></br>"+requestedFor;

	}
}
