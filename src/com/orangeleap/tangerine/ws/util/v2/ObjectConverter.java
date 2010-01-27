package com.orangeleap.tangerine.ws.util.v2;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.InvalidPropertyException;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class ObjectConverter {
    private Logger logger = LoggerFactory.getLogger(ObjectConverter.class);

    public ObjectConverter() {

    }

    @SuppressWarnings("unchecked")
	public Class findClass(String className) {
    	Class clazz = null;
    	
       	try {
			clazz = Class.forName("com.orangeleap.tangerine.domain." + className);
		} catch (ClassNotFoundException e) {
			try {
				clazz = Class.forName("com.orangeleap.tangerine.domain.paymentInfo." + className);
			} catch (ClassNotFoundException e1) {
				try {
					clazz = Class.forName("com.orangeleap.tangerine.domain.communication." + className);
				} catch (ClassNotFoundException e2) {
					logger.info("Unable to find class for name " + className);
				}
			}
		}
    	
    	return clazz;
    }
    
    @SuppressWarnings("unchecked")
	public void ConvertFromJAXB(Object from, Object to,Site site) {
        Class propertyType = null;
        if (from == null) return;
        if (to == null) return;
        BeanWrapper bwFrom = new BeanWrapperImpl(from);
        BeanWrapper bwTo = new BeanWrapperImpl(to);

        //
        // if the to object contains a site object set it to the default site
        try {
        	PropertyDescriptor siteDescriptor = bwFrom.getPropertyDescriptor("site");
        	
        	siteDescriptor.getWriteMethod().invoke(site);
        } catch (BeansException ex) {
        	//
        	// no such property
        } catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		} catch (InvocationTargetException e) {

		}
        PropertyDescriptor[] pdFrom = bwFrom.getPropertyDescriptors();

        //
        // walk the from property descriptos and set the to propertydescriptors
        for (int i = 0; i < pdFrom.length; i++) {

            try {
                PropertyDescriptor pdTo = bwTo.getPropertyDescriptor(pdFrom[i].getName());

                Method writeMethod = pdTo.getWriteMethod();
                Method readMethod = pdFrom[i].getReadMethod();

                propertyType = pdFrom[i].getPropertyType();

                Class domainClass = pdTo.getPropertyType();


                if (domainClass.getName().startsWith("com.orangeleap.tangerine.domain")) {
                    if (writeMethod != null) {
                    	Object newObject = domainClass.newInstance();
                    	ConvertFromJAXB(readMethod.invoke(from),newObject,site);
                    
                    	writeMethod.invoke(to, newObject);
                    }
                } else if (propertyType == List.class) {
                    //
                    // handle the list items seperately because JaxB does not
                    // create setters for collections
                    List l = (List) readMethod.invoke(from);
                    if (l != null) {
                        for (Object o : l) {
                            String className = o.getClass().getSimpleName();
                            Class newClass = findClass(className);
                            if (newClass != null) {
                            	Object newObject = newClass.newInstance();
                            	ConvertFromJAXB(o, newObject,site);
                            	List<Object> newList = (List<Object>) pdTo.getReadMethod().invoke(to);
                            	newList.clear();
                            	newList.add(newObject);
                            	pdTo.getWriteMethod().invoke(to,newList);
                            }
                        }
                    }

                } else if (propertyType == XMLGregorianCalendar.class) {
                	if (readMethod != null) {
                    //
                    // need to provide date conversions...
                    XMLGregorianCalendar xmlDate = (XMLGregorianCalendar) readMethod.invoke(from);
                    if (xmlDate != null) {
                        GregorianCalendar cal = xmlDate.toGregorianCalendar();

                        writeMethod.invoke(to, cal.getTime());
                    }
                	}
                } else if (propertyType == com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity.CustomFieldMap.class) {
                    //
                    // handle custom field map
                    ConvertCustomFieldMapFromJAXB((com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity) from,  (com.orangeleap.tangerine.domain.AbstractCustomizableEntity)to);
                } else if (propertyType.isEnum()) {
                	if (domainClass.isEnum()) {
                		Enum fromEnum = (Enum) readMethod.invoke(from);
                		if (fromEnum == null) continue;
                		Method valueMethod = fromEnum.getClass().getDeclaredMethod("value");
                		String value = (String) valueMethod.invoke(fromEnum);
                		Enum newObject = Enum.valueOf(domainClass, value);
                		writeMethod.invoke(to, newObject);
                	} else {
                		// assume the domainClass is a String
                		Enum fromEnum = (Enum) readMethod.invoke(from);
                		if (fromEnum == null) continue;
                		//
                		// axis adds a value() method to the generated enum
                		// lets... call this method through reflection to get the value
                		Method valueMethod = fromEnum.getClass().getDeclaredMethod("value");
                		String value = (String) valueMethod.invoke(fromEnum);
                		writeMethod.invoke(to, value);
                	}
                } else if (writeMethod != null && readMethod != null)

                    writeMethod.invoke(to, readMethod.invoke(from));

            } catch (IllegalArgumentException iae) {
                logger.info(iae.getMessage());
            } catch (InvalidPropertyException ipe) {
                logger.debug(ipe.getMessage());
            } catch (IllegalAccessException e) {
                logger.info(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.info(e.getMessage());
            } catch (InstantiationException e) {
                logger.info(e.getMessage());
            } catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    @SuppressWarnings("unchecked")
	public void ConvertToJAXB(Object from, Object to) {
    	if (from == null || to == null) return;
        Class propertyType = null;
        BeanWrapper bwFrom = new BeanWrapperImpl(from);
        BeanWrapper bwTo = new BeanWrapperImpl(to);

        PropertyDescriptor[] pdFrom = bwFrom.getPropertyDescriptors();

        //
        // walk the from property descriptos and set the to propertydescriptors
        for (int i = 0; i < pdFrom.length; i++) {

            try {
                PropertyDescriptor pdTo = bwTo.getPropertyDescriptor(pdFrom[i].getName());

                Method writeMethod = pdTo.getWriteMethod();
                Method readMethod = pdFrom[i].getReadMethod();

                propertyType = pdTo.getPropertyType();
                Class schemaClass = null;

                try {
                    String name = propertyType.getSimpleName();
                    schemaClass = Class.forName("com.orangeleap.tangerine.ws.schema.v2." + name);
                } catch (ClassNotFoundException cnf) {
                    schemaClass = null;
                }

                //
                // if we are trying to set an enum
                if (propertyType.isEnum()) {
                	Class fromClass = pdFrom[i].getPropertyType();
                	if (fromClass.isEnum()) {
                		//
                		// we are going from an enum to a enum
                		Enum fromEnum = (Enum) readMethod.invoke(from);
                		if (fromEnum == null) continue;
                		String value = fromEnum.toString();
                		
                		Class param[] = new Class[1];
                		param[0] = String.class;
						Method fromValueMethod = propertyType.getDeclaredMethod("fromValue",param);

						Enum toEnum = (Enum) fromValueMethod.invoke(null,value);                		
                		
                		writeMethod.invoke(to, toEnum);

                	} else if (fromClass == String.class) {
                		//
                		// we are going from a string to an xml enum
                		// axis generates constructors for all enums that take a string as an argument.
                		String value = (String) readMethod.invoke(from);
                		Class param[] = new Class[1];
                		param[0] = String.class;
						Method fromValueMethod = propertyType.getDeclaredMethod("fromValue",param);
						Enum e = (Enum) fromValueMethod.invoke(null,value);                		

                		writeMethod.invoke(to, e);
                	}
                } else if (schemaClass != null) {
                    Object newObject = schemaClass.newInstance();
                    ConvertToJAXB(readMethod.invoke(from), newObject);
                    writeMethod.invoke(to, newObject);
                } else if (propertyType == List.class) {
                    //
                    // handle the list items seperately because JaxB does not
                    // create setters for collections
                    List l = (List) readMethod.invoke(from);
                    if (l != null) {
                        for (Object o : l) {
                            String className = o.getClass().getSimpleName();

                            Class newClass = Class.forName("com.orangeleap.tangerine.ws.schema.v2." + className);
                            Object newObject = newClass.newInstance();
                            ConvertToJAXB(o, newObject);
                            List<Object> newList = (List<Object>) pdTo.getReadMethod().invoke(to);
                            newList.add(newObject);

                            /*
                            if (o.getClass() == com.orangeleap.tangerine.domain.communication.Address.class) {
                                com.orangeleap.tangerine.ws.schema.Address addr = new com.orangeleap.tangerine.ws.schema.Address();
                                Convert(o,addr);
                                if (to.getClass() == Constituent.class) ((Constituent) to).getAddresses().add(addr);
                            } else if (o.getClass() == com.orangeleap.tangerine.domain.communication.Email.class) {
                                com.orangeleap.tangerine.ws.schema.Email email = new com.orangeleap.tangerine.ws.schema.Email();
                                Convert(o,email);
                                if (to.getClass() == Constituent.class) ((Constituent) to).getEmails().add(email);
                            }  else if (o.getClass() == com.orangeleap.tangerine.domain.communication.Phone.class) {
                                com.orangeleap.tangerine.ws.schema.Phone phone = new com.orangeleap.tangerine.ws.schema.Phone();
                                Convert(o,phone);
                                if (to.getClass() == Constituent.class) ((Constituent) to).getPhones().add(phone);
                            }
                            */
                        }
                    }

                } else if (propertyType == XMLGregorianCalendar.class) {
                    //
                    // need to provide date conversions...
                    Date d = (Date) readMethod.invoke(from);
                    if (d != null) {
                        GregorianCalendar cal = new GregorianCalendar();

                        cal.setTime(d);

                        XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

                        writeMethod.invoke(to, xmlDate);
                    }
                } else if (propertyType == com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity.CustomFieldMap.class) {
                    //
                    // handle custom field map
                    ConvertCustomFieldMapToJAXB((com.orangeleap.tangerine.domain.AbstractCustomizableEntity) from, (com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity) to);
                } else if (writeMethod != null && readMethod != null) {
                	
                	writeMethod.invoke(to, readMethod.invoke(from));
                }

            } catch (ClassNotFoundException cnfe) {
                logger.info(cnfe.getMessage());
            } catch (IllegalArgumentException iae) {
                logger.info(iae.getMessage());
            } catch (InvalidPropertyException ipe) {
                logger.debug(ipe.getMessage());
            } catch (IllegalAccessException e) {
                logger.info(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.info(e.getMessage());
            } catch (InstantiationException e) {
                logger.info(e.getMessage());
            } catch (DatatypeConfigurationException e) {
            	logger.error(e.getMessage());
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        }
    }

//    /**
//     * Convert from a domain gift to a schema gift
//     *
//     * @param from
//     * @param to
//     */
//    static public void Convert(com.orangeleap.tangerine.domain.paymentInfo.Gift from, com.orangeleap.tangerine.ws.schema.Gift to)
//    {
//        String ignoreProperties[] = {"customFieldMap"};
//
//        BeanUtils.copyProperties(from,to,ignoreProperties);
//    }
//

    //
    /**
     * Convert from a domain AbstractEntity to a xml schema AbstractCustomizableEntity
     *
     * @param from domain AbstractCustomizableEntity
     * @param to   schema AbstractCustomizableEntity
     */
    static private void ConvertCustomFieldMapToJAXB(com.orangeleap.tangerine.domain.AbstractCustomizableEntity from, com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity to) {
    	if (from == null || to == null) return;
        //
        // now copy the customFieldMap
        Map<String, CustomField> customFieldMap = from.getCustomFieldMap();
        Iterator it = customFieldMap.entrySet().iterator();

        AbstractCustomizableEntity.CustomFieldMap cMap = new AbstractCustomizableEntity.CustomFieldMap();
        for (int i = 0; i < customFieldMap.size(); i++) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            CustomField value = (CustomField) entry.getValue();

            AbstractCustomizableEntity.CustomFieldMap.Entry cvf = new AbstractCustomizableEntity.CustomFieldMap.Entry();
            com.orangeleap.tangerine.ws.schema.v2.CustomField cf = new com.orangeleap.tangerine.ws.schema.v2.CustomField();

            cf.setDataType(value.getDataType());
            cf.setDisplayEndDate(value.getDisplayEndDate());
            cf.setDisplayStartDate(value.getDisplayStartDate());
            //cf.setEndDate(new XMLGregorianCalendar(value.getEndDate()));
            cf.setEntityId(value.getEntityId());
            cf.setEntityType(value.getEntityType());

            cf.setName(value.getName());
            cf.setSequenceNumber(value.getSequenceNumber());
            //cf.setStartDate(new XMLGregorianCalendar());
            if (value.getValue() != null)
            	cf.setValue(value.getValue());
            else 
            	cf.setValue("");


            cvf.setKey(key);
            cvf.setValue(cf);

            cMap.getEntry().add(cvf);
        }
        to.setCustomFieldMap(cMap);
    }


    /**
     * Convert from a JAXB AbstractEntity to a domain AbstractCustomizableEntity
     *
     * @param from domain AbstractCustomizableEntity
     * @param to   schema AbstractCustomizableEntity
     */
    static private void ConvertCustomFieldMapFromJAXB(com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity from, com.orangeleap.tangerine.domain.AbstractCustomizableEntity to) {
    	if (from == null || to == null) return;
        //
        // now copy the customFieldMap
        com.orangeleap.tangerine.ws.schema.v2.AbstractCustomizableEntity.CustomFieldMap customFieldMap= from.getCustomFieldMap();
        if (customFieldMap == null || customFieldMap.getEntry() == null) return;
        
        Iterator it = customFieldMap.getEntry().iterator();

        Map<String, CustomField> cMap = new HashMap<String,CustomField>();

        while(it.hasNext()) {

            AbstractCustomizableEntity.CustomFieldMap.Entry entry = (AbstractCustomizableEntity.CustomFieldMap.Entry) it.next();
            String key = (String) entry.getKey();
            com.orangeleap.tangerine.ws.schema.v2.CustomField value = (com.orangeleap.tangerine.ws.schema.v2.CustomField) entry.getValue();

            CustomField cf = new CustomField();

            cf.setDataType(value.getDataType());
            cf.setDisplayEndDate(value.getDisplayEndDate());
            cf.setDisplayStartDate(value.getDisplayStartDate());
            //cf.setEndDate(new XMLGregorianCalendar(value.getEndDate()));
            cf.setEntityId(value.getEntityId());
            cf.setEntityType(value.getEntityType());




            cf.setName(value.getName());
            cf.setSequenceNumber(value.getSequenceNumber());
            //cf.setStartDate(new XMLGregorianCalendar());
            cf.setValue(value.getValue());

            cMap.put(value.getName(),cf);
        }
        to.setCustomFieldMap(cMap);
    }


}
