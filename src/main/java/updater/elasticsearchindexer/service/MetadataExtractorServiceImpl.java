package updater.elasticsearchindexer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.MessageAttribute;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

/**
 * Implementation of {link:IMetadataExtractorService}
 * Deals with all metadata related work sent in any event payload.
 * 
 * @author theja.kotuwella
 *
 */
public class MetadataExtractorServiceImpl implements IMetadataExtractorService {
	LambdaLogger logger = null;
	
	private static final String METADATA_ISBN = "isbn";
	
	public MetadataExtractorServiceImpl(LambdaLogger logger) {
		this.logger = logger;
	}
	
	/**
	 * Extracts metadata from a given list and arranges them in key/value pairs
	 *
	 * @param records List of of SQSMessage
	 * @return Extracted metadata in key/value pairs
	 */
	@Override
	public Map<String, String> extractMetadata(List<SQSMessage> messages) {
		Map<String, String> metadata = new HashMap<>();
		
		for(SQSMessage message : messages) {
			Map<String, MessageAttribute> payload = message.getMessageAttributes();
			MessageAttribute isbn = payload.get(METADATA_ISBN);
			
			metadata.put(METADATA_ISBN, isbn.getStringValue());
			
			logger.log("MetadataExtractorServiceImpl: ISBN: " + isbn.getStringValue());
		}
		return metadata;
	}
}
