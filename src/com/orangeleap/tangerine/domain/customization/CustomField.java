package com.orangeleap.tangerine.domain.customization;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateMidnight;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

/**
 * Implementation of a Custom Field which tracks the Entity it
 * is associated with via the Enitity ID and Type.
 */
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class CustomField implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;
    public static final Date PAST_DATE = new DateMidnight(1900, 1, 1).toDate();
    public static final Date FUTURE_DATE = new DateMidnight(3000, 1, 1).toDate();

    private Long id;
    private String name;
    private List<String> values = new ArrayList<String>();
    private String value;

    private String entityType;
    private Long entityId;
    
    private int sequenceNumber;
    private Long dataType = new Long(0);
    private Date startDate = PAST_DATE;
    private Date endDate = FUTURE_DATE;

    public CustomField() {
        super();
    }

    public CustomField(String name) {
        super();
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public void addValue(String value) {
		if (this.values == null) {
			this.values = new ArrayList<String>();
		}
		this.values.add(value);
	}
	
	public void clearValues() {
		if (this.values != null) {
			this.values.clear();
		}
	}

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEntityType() {
        return entityType;
    }

    /**
     * The type of entity this custom field is associated with
     * @param entityType the entity type
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    /**
     * The Id of the entity this custom field is associated with.
     * The combination of the EntityType and Entity ID are what
     * can be used to determine the association of this custom field.
     * @param entityId the Long ID of the associated entity
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("name", name).append("entityType", entityType).append("entityId", entityId).append("value", value).append("startDate", startDate).append("endDate", endDate).append("sequenceNumber", sequenceNumber).toString();
    }

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	private static final String FORMAT = "MM/dd/yyyy";
	
	private String getDisplayDate(Date d) {
		if (d == null || d.equals(PAST_DATE) || d.equals(FUTURE_DATE)) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		return sdf.format(d);
	}
	
	
	public String getDisplayStartDate() {
		return getDisplayDate(this.startDate);
	}
	
	public String getDisplayEndDate() {
		return getDisplayDate(this.endDate);
	}

	public void setDisplayStartDate(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		if (s == null || s.trim().length() == 0) {
			this.startDate = PAST_DATE;
		} 
		else {
			try {
				this.startDate = sdf.parse(s);
			} 
			catch (Exception e) {
				throw new RuntimeException("Invalid date format " + s);
			}
		}
	}
	
	public void setDisplayEndDate(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		if (s == null || s.trim().length() == 0) {
			this.endDate = FUTURE_DATE;
		} 
		else {
			try {
				this.endDate = sdf.parse(s);
			} 
			catch (Exception e) {
				throw new RuntimeException("Invalid date format " + s);
			}
		}
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}

	public Long getDataType() {
		return dataType;
	}
}


