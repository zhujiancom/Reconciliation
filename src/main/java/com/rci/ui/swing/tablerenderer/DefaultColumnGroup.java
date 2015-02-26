package com.rci.ui.swing.tablerenderer;

public class DefaultColumnGroup implements ColumnGroup {
	private int row = 0;
	
	private int column = 0;
	
	private int rowSpan =1;
	
	private int columnSpan = 1;
	
	private Object headerValue = null;
	@Override
	public int getRow() {
		// TODO Auto-generated method stub
		return this.row;
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return this.column;
	}

	@Override
	public int getColumnSpan() {
		// TODO Auto-generated method stub
		return this.columnSpan;
	}

	@Override
	public int getRowSpan() {
		// TODO Auto-generated method stub
		return this.rowSpan;
	}

	@Override
	public Object getHeaderValue() {
		// TODO Auto-generated method stub
		return this.headerValue;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public void setColumnSpan(int columnSpan) {
		this.columnSpan = columnSpan;
	}

	public void setHeaderValue(Object headerValue) {
		this.headerValue = headerValue;
	}

}
