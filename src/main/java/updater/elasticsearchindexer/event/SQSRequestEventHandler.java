package updater.elasticsearchindexer.event;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

import updater.elasticsearchindexer.service.ESTransactionManagerSeviceImpl;
import updater.elasticsearchindexer.service.IESTransactionManagerSevice;
import updater.elasticsearchindexer.service.IMetadataExtractorService;
import updater.elasticsearchindexer.service.MetadataExtractorServiceImpl;
import updater.elasticsearchindexer.util.PropertyManager;

/**
 * Event driven class for SQS notification (trigger message). 
 * Any SQS message triggers a notification here and takes the next action from there.
 *
 */
public class SQSRequestEventHandler implements RequestHandler<SQSEvent, Boolean> {
	@Override
	public Boolean handleRequest(SQSEvent event, Context context) {
		PropertyManager.loadProperties();
		
		return updateBookInventoryIndexInElasticSearch(event.getRecords(), context.getLogger());
	}
	
	private boolean updateBookInventoryIndexInElasticSearch(List<SQSMessage> messages, LambdaLogger logger) {
		IMetadataExtractorService extractorService = new MetadataExtractorServiceImpl(logger);
		Map<String, String> metadata = extractorService.extractMetadata(messages);

		IESTransactionManagerSevice esService = new ESTransactionManagerSeviceImpl(logger);
		boolean isSuccessfull = esService.writeBookIndexToElasticsearch(metadata);

		return isSuccessfull;
	}
}
