package updater.elasticsearchindexer.service;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

/**
 * Metadata extraction interface.
 * 
 * @author theja.kotuwella
 *
 */
public interface IMetadataExtractorService {
	Map<String, String> extractMetadata(List<SQSMessage> messages);
}
