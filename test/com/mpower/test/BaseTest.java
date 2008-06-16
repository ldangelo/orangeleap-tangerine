package com.mpower.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
public abstract class BaseTest extends AbstractTestNGSpringContextTests {
}
