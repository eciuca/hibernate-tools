package org.hibernate.tool.internal.reveng;

import java.util.List;

import org.hibernate.mapping.Table;
import org.hibernate.tool.api.reveng.ReverseEngineeringStrategy;
import org.hibernate.tool.api.reveng.TableIdentifier;

public class RevEngUtils {

	public static List<String> getPrimaryKeyInfoInRevengStrategy(
			ReverseEngineeringStrategy revengStrat, 
			Table table, 
			String defaultCatalog, 
			String defaultSchema) {
		List<String> result = null;
		TableIdentifier tableIdentifier = TableIdentifier.create(table);
		result = revengStrat.getPrimaryKeyColumnNames(tableIdentifier);
		if (result == null) {
			String catalog = getCatalogForModel(table.getCatalog(), defaultCatalog);
			String schema = getSchemaForModel(table.getSchema(), defaultSchema);
			tableIdentifier = new TableIdentifier(catalog, schema, table.getName());
			result = revengStrat.getPrimaryKeyColumnNames(tableIdentifier);
		}
		return result;
	}
	
	public static String getTableIdentifierStrategyNameInRevengStrategy(
			ReverseEngineeringStrategy revengStrat, 
			TableIdentifier tableIdentifier, 
			String defaultCatalog, 
			String defaultSchema) {
		String result = null;
		result = revengStrat.getTableIdentifierStrategyName(tableIdentifier);
		if (result == null) {
			String catalog = getCatalogForModel(tableIdentifier.getCatalog(), defaultCatalog);
			String schema = getSchemaForModel(tableIdentifier.getSchema(), defaultSchema);
			tableIdentifier = new TableIdentifier(catalog, schema, tableIdentifier.getName());
			result = revengStrat.getTableIdentifierStrategyName(tableIdentifier);
		}
		return result;	
	}

	public static TableIdentifier createTableIdentifier(
			Table table, 
			String defaultCatalog, 
			String defaultSchema) {
		String tableName = table.getName();
		String tableCatalog = getCatalogForModel(table.getCatalog(), defaultCatalog);
		String tableSchema = getSchemaForModel(table.getSchema(), defaultSchema);
		return new TableIdentifier(tableCatalog, tableSchema, tableName);
	}

	/** If catalog is equal to defaultCatalog then we return null so it will be null in the generated code. */
	public static String getCatalogForModel(String catalog, String defaultCatalog) {
		if(catalog==null) return null;
		if(catalog.equals(defaultCatalog)) return null;
		return catalog;
	}

	/** If catalog is equal to defaultSchema then we return null so it will be null in the generated code. */
	public static String getSchemaForModel(String schema, String defaultSchema) {
		if(schema==null) return null;
		if(schema.equals(defaultSchema)) return null;
		return schema;
	}
	
}
