package playground.solrmarc.index.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import playground.solrmarc.index.fieldmatch.FieldFormatter;
import playground.solrmarc.index.fieldmatch.FieldMatch;
import playground.solrmarc.index.specification.conditional.Condition;

public abstract class Specification
{
    abstract public void  addConditional(Condition cond);
    abstract public String [] getTags();
    String specLabel;
    
    public List<FieldMatch> getFieldMatches(Record record)
    {
        final String tags[] = getTags();
        List<VariableField> fields = record.getVariableFields(tags);
        List<FieldMatch> result = new ArrayList<FieldMatch>(fields.size());
        for (VariableField vf : fields) 
        {
            SingleSpecification specUsed = this.getMatchingSpec(vf.getTag(), vf);
            if (specUsed != null && (specUsed.cond == null || specUsed.cond.matches(vf)))
            {
                result.add(new FieldMatch(vf,specUsed));
            }
        }
        return(result);    
    }

    public boolean hasDuplicateTags() { return(false); }

    abstract protected SingleSpecification getMatchingSpec(String tag, VariableField f);
    
    abstract public void addFieldValues(Collection<String> result, VariableField vf);

    public String getSpecLabel()
    {
        return specLabel;
    }
    
    public void setSpecLabel(String specLabel)
    {
        this.specLabel = specLabel;
    }
        
    public abstract void setFormatter(FieldFormatter fmt);
	
}
