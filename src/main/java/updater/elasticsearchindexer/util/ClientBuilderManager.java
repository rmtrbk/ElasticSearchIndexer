package updater.elasticsearchindexer.util;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AWSRequestSigningApacheInterceptor;
import com.amazonaws.regions.Regions;

/**
 * Builds the AWS clients.
 * 
 * @author theja.kotuwella
 *
 */
public class ClientBuilderManager {
	private static RestHighLevelClient elasticsearchClient 	= null;
	
	public static RestHighLevelClient getElasticSearchClient() {
		if(elasticsearchClient == null) {
			AWS4Signer signer = new AWS4Signer();
			signer.setServiceName(PropertyManager.ELASTICSEARCH_SERVICE_NAME_VALUE);
			signer.setRegionName(Regions.AP_SOUTHEAST_2.getName());
			
			HttpRequestInterceptor interceptor 
							= new AWSRequestSigningApacheInterceptor(
										PropertyManager.ELASTICSEARCH_SERVICE_NAME_VALUE, 
										signer, 
										getcredentials());
			
			elasticsearchClient = new RestHighLevelClient(
									RestClient.builder(HttpHost.create(
										PropertyManager.ELASTICSEARCH_ENDPOINT_VALUE))
											.setHttpClientConfigCallback(
												hacb -> hacb.addInterceptorLast(interceptor)));

		}

		return elasticsearchClient;
	}
	
	private static AWSStaticCredentialsProvider getcredentials() {
		AWSCredentials credentials 	= new BasicAWSCredentials(PropertyManager.ACCESS_KEY_ATTRIBUTE,
																PropertyManager.SECRET_ATTRIBUTE);
		
		AWSStaticCredentialsProvider credProv = new AWSStaticCredentialsProvider(credentials);
		
		return credProv;
	}
}
