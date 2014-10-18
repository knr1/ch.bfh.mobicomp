/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.implementation;

import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.messagebus.message.definition.Intent;
import net.engio.mbassy.bus.MBassador;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DefaultBusFactoryTest {

    public DefaultBusFactoryTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getIntentBus method, of class DefaultBusFactory.
     */
    @Test
    public void testGetIntentBus() {
	System.out.println("getIntentBus");
	DefaultBusFactory instance = DefaultBusFactory.getInstance();
	MBassador<Intent> result = instance.getIntentBus();
	Assert.assertNotNull(result);
	MBassador<Intent> result2 = instance.getIntentBus();
	Assert.assertSame(result2, result);

    }

    /**
     * Test of getEventBus method, of class DefaultBusFactory.
     */
    @Test
    public void testGetEventBus() {
	System.out.println("getEventBus");
	DefaultBusFactory instance = DefaultBusFactory.getInstance();
	MBassador<Event> result = instance.getEventBus();
	Assert.assertNotNull(result);
	MBassador<Event> result2 = instance.getEventBus();
	Assert.assertSame(result2, result);
    }

    /**
     * Test of getInstance method, of class DefaultBusFactory.
     */
    @Test
    public void testGetInstance() {
	System.out.println("getInstance");
	DefaultBusFactory expResult = null;
	DefaultBusFactory result = DefaultBusFactory.getInstance();
	DefaultBusFactory result2 = DefaultBusFactory.getInstance();
	assertEquals(result2, result);
    }

}
