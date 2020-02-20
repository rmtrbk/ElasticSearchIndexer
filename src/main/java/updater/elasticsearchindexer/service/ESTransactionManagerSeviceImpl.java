package updater.elasticsearchindexer.service;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import updater.elasticsearchindexer.util.ClientBuilderManager;
import updater.elasticsearchindexer.util.PropertyManager;

/**
 * Implementation of {@link:IESTransactionManagerSevice}.
 * Manages all Elastic Search transactions.
 * 
 * @author theja.kotuwella
 *
 */
public class ESTransactionManagerSeviceImpl implements IESTransactionManagerSevice {
	private  LambdaLogger logger = null;
	private static final String BOOK_METADATA_INDEX 		= "book-metadata-index";
	private static final String BOOK_METADATA_INDEX_TYPE 	= "book-metadata";
	
	public ESTransactionManagerSeviceImpl(LambdaLogger logger) {
		this.logger = logger;
	}
	
	/**
	 * Writes given values to the Elastic Search index
	 *
	 * @param payloadMap index data of the book that needs to be written to Elastic Search
	 * @return Writing record to Elastic Search was successful or not
	 */
	public boolean writeBookIndexToElasticsearch(Map<String, String> payloadMap) {
		boolean isSuccessful = false;
		if(payloadMap != null && !payloadMap.isEmpty()) {
    		IndexRequest request = new IndexRequest(BOOK_METADATA_INDEX, 
    													BOOK_METADATA_INDEX_TYPE)
    													.source(payloadMap);
    		try {
    			logger.log("ElasticsearchTransactionManager: Service: " 
    						+ PropertyManager.ELASTICSEARCH_SERVICE_NAME_VALUE);
    			logger.log("ElasticsearchTransactionManager: Endpoint: " 
    						+ PropertyManager.ELASTICSEARCH_ENDPOINT_VALUE);
    			
    			IndexResponse indexResponse = ClientBuilderManager
    											.getElasticSearchClient()
    											.index(request, RequestOptions.DEFAULT);
    			isSuccessful = true;
    			
    			logger.log("ElasticsearchTransactionManager: Result: " 
    					+ indexResponse.getResult() + ". ID: " + indexResponse.getId());
    			
    		} catch(ElasticsearchException e) {
    			logger.log("ElasticsearchTransactionManager: ElasticsearchException: " 
    															+ e.getMessage());
    			e.printStackTrace();
    			
    		} catch (IOException e) {
    			logger.log("ElasticsearchTransactionManager: Exception: " 
    															+ e.getMessage());
    			e.printStackTrace();
    		}
    	} else {
    		logger.log("ElasticsearchTransactionManager: Required fields are missing.");
    	}
		
		return isSuccessful;
    }
}
