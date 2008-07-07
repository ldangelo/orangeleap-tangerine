package com.mpower.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.dao.PersonDao;

public class SQLInjectTest extends BaseTest {

	private PersonDao personDao;

	@Test
	public void testSQLInjectionInParameter0() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("firstName = 'Kelly' AND person.firstName='abc' AND person.firstName", "ABC123");

			personDao.readPersons(1L, params);
			assert false;
		} catch (RuntimeException e) {
			assert true;
		}
	}

	@Test
	public void testSQLInjectionInParameter1() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("firstName = 'Kelly' DELETE FROM MPOWER_USER", "ABC123");

			personDao.readPersons(1L, params);
			assert false;
		} catch (RuntimeException e) {
			assert true;
		}
	}

	@Test
	public void testSQLInjectionInParameter2() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("firstName", "Kelly");
			params.put("firstName = 'Kelly'; DELETE FROM MPOWER_USER", "ABC123");

			personDao.readPersons(1L,params);
			assert false;
		} catch (RuntimeException e) {
			assert true;
		}
	}

	@Test
	public void testSQLInjectionInParameter3() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("DELETE FROM MPOWER_USER", "ABC123");

			personDao.readPersons(1L,params);
			assert false;
		} catch (RuntimeException e) {
			assert true;
		}
	}

	@Test
	public void testSQLInjectionInParameter4() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("'DELETE FROM MPOWER_USER'", "ABC123");

			personDao.readPersons(1L,params);
			assert false;
		} catch (RuntimeException e) {
			assert true;
		}
	}

	@Test
	public void testSQLInjectionInParameter5() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("firstName = 'Kelly'; DELETE FROM person", "person");

			personDao.readPersons(1L,params);
			assert false;
		} catch (RuntimeException e) {
			assert true;
		}
	}

	@Test
	public void testSQLInjectionInData1() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("firstName", "'DELETE FROM MPOWER_USER'");

			personDao.readPersons(1L,params);
			assert true;
		} catch (RuntimeException e) {
			assert false;
		}
	}

	@Test
	public void testSQLInjectionInData2() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("firstName", ";DELETE FROM MPOWER_USER");

			personDao.readPersons(1L,params);
			assert true;
		} catch (RuntimeException e) {
			assert false;
		}
	}

	@Test
	public void testSQLInjectionInData3() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("firstName", "DELETE FROM MPOWER_USER");

			personDao.readPersons(1L,params);
			assert true;
		} catch (RuntimeException e) {
			assert false;
		}
	}

	@Test
	public void testSQLInjectionInData4() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("firstName", "AND person.firstName = '222'");

			personDao.readPersons(1L,params);
			assert true;
		} catch (RuntimeException e) {
			assert false;
		}
	}

	@BeforeClass
    public void setup() {
		personDao = (PersonDao) applicationContext.getBean("personDao");
    }
}