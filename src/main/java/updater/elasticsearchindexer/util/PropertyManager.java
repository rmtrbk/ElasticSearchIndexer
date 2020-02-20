package updater.elasticsearchindexer.util;

/**
 * Reads the properties from the environment.
 * 
 * @author theja.kotuwella
 *
 */
public class PropertyManager {
	
	private static final String ACCESS_KEY_PROP 				= "es_aws_accessKey";
	private static final String SECRET_PROP 					= "es_aws_secret";
	public static final String ELASTICSEARCH_SERVICE_NAME_PROP	= "es_service_name";
	public static final String ELASTICSEARCH_ENDPOINT_PROP		= "es_endpoint";
	
	public static String ELASTICSEARCH_SERVICE_NAME_VALUE  	= null;
	public static String ELASTICSEARCH_ENDPOINT_VALUE  		= null;
	
	public static String ACCESS_KEY_ATTRIBUTE 	= null;
	public static String SECRET_ATTRIBUTE 		= null;
	
	public static void loadProperties() {
		ACCESS_KEY_ATTRIBUTE 	= System.getenv(ACCESS_KEY_PROP);
		SECRET_ATTRIBUTE 		= System.getenv(SECRET_PROP);
		
		ELASTICSEARCH_SERVICE_NAME_VALUE 	= System.getenv(ELASTICSEARCH_SERVICE_NAME_PROP);
		ELASTICSEARCH_ENDPOINT_VALUE 		= System.getenv(ELASTICSEARCH_ENDPOINT_PROP);
	}
}
