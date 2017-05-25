package com.demo.srcpkg;

import com.properties.receiver.annotation.PropertyScan;
import com.properties.receiver.annotation.SetProperty;

@PropertyScan
public class SourcePropertySetup {
	@SetProperty(property = "SIGNOUT_AUDIT_EVENT_DESC")
	private String ProductName;
	@SetProperty(property = "updateHighlighter.updateMemberUser")
	private String TableType;
	@SetProperty(property = "DC04.SRVC.CHN.createRole.ERROR_CODE_DECISION_FIELD")
	private String TableName;
	@SetProperty(property = "insertAuditTransaction.auditHistoryConfig.forEventName")
	private String TableId;

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
