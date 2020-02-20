package indexer.elasticsearchindexer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.MessageAttribute;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.s3.model.ObjectMetadata;

import junit.framework.TestCase;
import updater.elasticsearchindexer.service.MetadataExtractorServiceImpl;

/**
 * Test class for {@link:MetadataExtractorServiceImpl}
 * 
 * @author theja.kotuwella
 *
 */
public class MetadataExtractorServiceImplTest extends TestCase {
	MetadataExtractorServiceImpl extractor = null;
	
	private static final String METADATA_ISBN 		= "isbn";
	private static final String ISBN 				= "978-1-56619-909-4";
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		LambdaLogger logger = Mockito.mock(LambdaLogger.class);
		Mockito.doNothing().when(logger).log(Mockito.anyString());
		
		extractor = new MetadataExtractorServiceImpl(logger);
	}
	
	@Test
	public void testExtractMetadata() {
		ObjectMetadata objectMetadata = Mockito.mock(ObjectMetadata.class);
		Mockito.when(objectMetadata.getUserMetaDataOf(METADATA_ISBN)).thenReturn(ISBN);
		
		MessageAttribute attribute = Mockito.mock(MessageAttribute.class);
		Mockito.when(attribute.getStringValue()).thenReturn(ISBN);
		Map<String, MessageAttribute> payload = new HashMap<>();
		payload.put(METADATA_ISBN, attribute);
		
		SQSMessage message = Mockito.mock(SQSMessage.class);
		Mockito.when(message.getMessageAttributes()).thenReturn(payload);
		
		List<SQSMessage> messages = new ArrayList<>();
		messages.add(message);
		
		// Expectation
		Map<String, String> expected = new HashMap<>();
        expected.put(METADATA_ISBN, ISBN);
        
        // Result
		Map<String, String> results = extractor.extractMetadata(messages);
		
		// Assertions
		assertEquals("Comparing result with expected failed", expected, results);
	}
}
