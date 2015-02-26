package com.rci.ui.swing.tablerenderer;

public interface ColumnGroup {
	public int getRow();
	
	public int getColumn();
	
	public int getColumnSpan();
	
	public int getRowSpan();
	
	public Object getHeaderValue();
}
