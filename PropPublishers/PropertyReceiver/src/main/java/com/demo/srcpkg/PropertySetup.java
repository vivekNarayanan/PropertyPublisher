package com.demo.srcpkg;

import com.properties.receiver.annotation.PropertyScan;
import com.properties.receiver.annotation.SetProperty;

@PropertyScan
public class PropertySetup {
	@SetProperty(property = "DC04.SRVC.CHN.Individual.TabDtls")
	private String ProductName;
	@SetProperty(property = "DC04.SRVC.CHN.UserManagement.Tabs")
	private String TableType;
	@SetProperty(property = "DC04.SRVC.CHN.ApplicationMenuList")
	private String TableName;
	@SetProperty(property = "DATANOTFOUNDERRORCODE")
	private String TableId;
	@SetProperty(property="dateTobeParsed")
	private String dateTobeParsed;
	
	
	public String getDateTobeParsed() {
		return dateTobeParsed;
	}

	public String getProductName() {
		return ProductName;
	}

	public String getTableType() {
		return TableType;
	}

	public String getTableName() {
		return TableName;
	}

	public String getTableId() {
		return TableId;
	}

}
