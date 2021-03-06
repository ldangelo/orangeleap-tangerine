package com.orangeleap.tangerine.service.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.service.customization.RulesConfService;
import com.orangeleap.tangerine.type.RuleObjectType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.RulesStack;

public class OrangeLeapRuleSession {

		protected final Log logger = OLLogger.getLog(getClass());

		private OrangeLeapRuleBase orangeLeapRuleBase;

		private Map<String, Object> map = new TreeMap<String, Object>();

		public OrangeLeapRuleSession(OrangeLeapRuleBase orangeLeapRuleBase) {
			this.orangeLeapRuleBase = orangeLeapRuleBase;
		}

		public void put(RuleObjectType type, Object object) {
			if (object != null) map.put(type.getType(), object);
		}

		public void executeRules() {
			
			float time = 0f;

			// Re-entrancy check will not execute the same event's rules again within that event's rule processing
			String operation = "OrangeLeapRuleSession.executeRules() " + orangeLeapRuleBase.getRuleEventNameType();
	        boolean reentrant = RulesStack.push(operation);
	        try {
		        if (!reentrant) {
					try {

						long t0 = System.currentTimeMillis();

						map.put(RULE_EXECUTION_SUMMARY, new ArrayList<String>());
						addServicesToMap();

						RulesConfService rulesConfService = (RulesConfService)orangeLeapRuleBase.getApplicationContext().getBean("rulesConfService");
						rulesConfService.fireRulesEvent(orangeLeapRuleBase.getRuleEventNameType(), orangeLeapRuleBase.isTestMode(), map);

						long t1 = System.currentTimeMillis();
						time = (t1-t0)/1000f;

					} catch (OrangeLeapConsequenceRuntimeException e) {
						throw e;
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new RuntimeException(e);
					}
		        }
	        } finally {
	        	printRulesExecutionSummary(time);
	        	RulesStack.pop(operation);
	        }

		}

		@SuppressWarnings("unchecked")
		private void printRulesExecutionSummary(float time) {

			try {

				StringBuilder sb = new StringBuilder();
				sb.append("Rules Execution Summary for ["+orangeLeapRuleBase.getSiteName()+"] "+orangeLeapRuleBase.getRuleEventNameType()+":\n");
				List<String> summary = (List<String>)map.get(RULE_EXECUTION_SUMMARY);
				if (summary != null) for (String s: summary) sb.append(s).append("\n");
				sb.append("Total time: "+time+" sec.\n");

				logger.debug(sb.toString()); 

			} catch (Exception e) {
				logger.error(e);
			}

		}

		public static final String RULE_EXECUTION_SUMMARY = "ruleExecutionSummary";

		// Add any new services used by rules to this list:
		private void addServicesToMap() {
			ApplicationContext applicationContext = orangeLeapRuleBase.getApplicationContext();
			map.put("logger", logger);
			map.put("applicationContext", applicationContext);
			map.put("tangerineUserHelper", applicationContext.getBean("tangerineUserHelper"));
			map.put("userHelper", applicationContext.getBean("tangerineUserHelper"));
			map.put("siteService", applicationContext.getBean("siteService"));
			map.put("constituentService", applicationContext.getBean("constituentService"));
			map.put("giftService", applicationContext.getBean("giftService"));
			map.put("communicationHistoryService", applicationContext.getBean("communicationHistoryService"));
			map.put("picklistItemService", applicationContext.getBean("picklistItemService"));
			map.put("emailService", applicationContext.getBean("emailService"));
			map.put("mailService", applicationContext.getBean("mailService"));
			map.put("errorLogService", applicationContext.getBean("errorLogService"));
			map.put("ruleHelperService", applicationContext.getBean("ruleHelperService"));
			map.put("paymentGateway", applicationContext.getBean("paymentGateway"));
			map.put("achGateway", applicationContext.getBean("achGateway"));
		}

}
