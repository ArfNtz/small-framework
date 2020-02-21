package fr.toyframework.mapper.jdbc.model;

public class JdbcIndex {
	String qualifierName;
	String indexName;
	String columnName;
	int type;
	boolean nonUnique;
	public String getColumnName() {
		return columnName;
	}
	public boolean isNonUnique() {
		return nonUnique;
	}
	public int getType() {
		return type;
	}
	public void setColumnName(String string) {
		columnName = string;
	}
	public void setNonUnique(boolean b) {
		nonUnique = b;
	}
	public void setType(int i) {
		type = i;
	}
	public String getIndexName() {
		return indexName;
	}
	public String getQualifierName() {
		return qualifierName;
	}
	public void setIndexName(String string) {
		indexName = string;
	}
	public void setQualifierName(String string) {
		qualifierName = string;
	}
}