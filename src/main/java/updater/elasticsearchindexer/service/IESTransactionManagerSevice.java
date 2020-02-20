package updater.elasticsearchindexer.service;

import java.util.Map;

/**
 * Elastic Search transaction management interface.
 * 
 * @author theja.kotuwella
 *
 */
public interface IESTransactionManagerSevice {
	boolean writeBookIndexToElasticsearch(Map<String, String> payloadMap);
}
