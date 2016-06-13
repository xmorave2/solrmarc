package playground.solrmarc.solr;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.solr.common.SolrInputDocument;

public interface SolrProxy
{

    /**
     * return true if exception is a SolrException
     */
    public abstract boolean isSolrException(Exception e);

    /**
     * given a map of field names and values, create a Document and add it to
     * the index
     * 
     * @param fieldsMap
     *            - map of field names and values to add to the document
     * @return a string representation of the document
     */
    public abstract String addDoc(Map<String, Object> fieldsMap, boolean verbose, boolean addDocToIndex)
            throws IOException;
    
    public abstract void addDoc(SolrInputDocument document);

    /**
     * delete doc from the index
     * 
     * @param id
     *            the unique identifier of the document to be deleted
     */
    public abstract void delete(String id, boolean fromCommitted, boolean fromPending) throws IOException;

    /**
     * delete all docs from the index Warning: be very sure you want to call
     * this
     */
    public abstract void deleteAllDocs() throws IOException;

    /**
     * commit changes to the index
     */
    public abstract void commit(boolean optimize) throws IOException;

    /**
     * close the solrCore
     */
    public abstract void close();

    public abstract void addDocs(Collection<SolrInputDocument> docQ);


}