package playground.solrmarc.index.mapping.impl;

import playground.solrmarc.index.extractor.impl.patternMapping.PatternMapping;
import playground.solrmarc.index.extractor.impl.patternMapping.PatternMappingValueExtractorFactory;
import playground.solrmarc.index.indexer.IndexerSpecException;
import playground.solrmarc.index.mapping.AbstractSingleValueMapping;
import playground.solrmarc.index.mapping.AbstractMultiValueMapping;
import playground.solrmarc.index.mapping.AbstractValueMappingFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class PatternMappingFactory extends AbstractValueMappingFactory {
    @Override
    public boolean canHandle(String mappingConfiguration) {
        return mappingConfiguration.startsWith("map(") && mappingConfiguration.endsWith(")");
    }

    @Override
    public AbstractSingleValueMapping createSingleValueMapping(String mappingConfiguration) 
    {
//        final String mappingName = mappingConfiguration.substring(4, mappingConfiguration.length() - 1);
//        List<PatternMapping> patternMappings = PatternMappingValueExtractorFactory.getPatternMappingsForName(mappingName);
    	List<PatternMapping> patternMappings = pattermMappingsFromString(mappingConfiguration.substring(4, mappingConfiguration.length() - 1));
        return new SingleValuePatternMapping(patternMappings);
    }

    @Override
    public AbstractMultiValueMapping createMultiValueMapping(String mappingConfiguration) 
    {
 //       final String mappingName = mappingConfiguration.substring(4, mappingConfiguration.length() - 1);
 //       List<PatternMapping> patternMappings = PatternMappingValueExtractorFactory.getPatternMappingsForName(mappingName);
    	List<PatternMapping> patternMappings = pattermMappingsFromString(mappingConfiguration.substring(4, mappingConfiguration.length() - 1));
        return new MultiValuePatternMapping(patternMappings);
    }
    
    public static List<PatternMapping> pattermMappingsFromString(String mapSpec)
    {
        final String mapParts[] = mapSpec.split("[|][|]");
        List<PatternMapping> pm = new ArrayList<PatternMapping>(mapParts.length);
        
        for (int i = 0; i < mapParts.length; i++)
        {
            String mapEntry[] = mapParts[i].split("[ ]*=>[ ]*", 2);
            if (mapEntry.length == 2) 
            {
                try { 
                	pm.add(new PatternMapping(mapEntry[0], mapEntry[1], i));
                }
                catch (PatternSyntaxException pse)
                {
                	throw new IndexerSpecException("Malformed regular expression in pattern map : " + mapEntry[0] + "\n" + pse.getMessage());
                }
                catch (IndexOutOfBoundsException ioobe)
                {
                	throw new IndexerSpecException("Unknown group in replacement string : " + mapEntry[1] + "\n" + ioobe.getMessage());
                }
            }            
        }
        return(pm);
    }
}