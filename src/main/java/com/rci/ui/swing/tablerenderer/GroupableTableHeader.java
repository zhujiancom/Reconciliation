package com.rci.ui.swing.tablerenderer;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.JTableHeader;

public class GroupableTableHeader extends JTableHeader {
	private int rowCount;
	
	private int columnCount;
	
	private List<Group> groups = new ArrayList<Group>();
	
	public GroupableTableHeader(){
		this.setReorderingAllowed(false);
	}

	@Override
	public void updateUI() {
		setUI(new GroupableTableHeaderUI());
	}
	
	
}
