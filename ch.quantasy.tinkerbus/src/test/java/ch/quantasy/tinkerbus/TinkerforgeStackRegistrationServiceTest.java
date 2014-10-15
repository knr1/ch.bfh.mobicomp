/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.bus.TinkerforgeBusFactory;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationIntent;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationService;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackRegistrationServiceTest {

    public TinkerforgeStackRegistrationServiceTest() {
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
     * Test of handleMessage method, of class TinkerforgeStackRegistrationService.
     */
    @Test
    public void testHandleRegisterMessage() {
	System.out.println("handleMessage");
	TestAgent agent = new TestAgent() {

	    @Override
	    protected void handleEvent(Event message) {
		System.out.println(message);
	    }
	};

	TinkerforgeStackRegistrationService instance = new TinkerforgeStackRegistrationService();

	TinkerforgeStackRegistrationIntent intent = TinkerforgeStackRegistrationIntent.register(agent, new TinkerforgeStackAddress("localhost", 4223));
	agent.publish(intent);
	System.out.println(intent);
	try {
	    Thread.sleep(2000);
	} catch (InterruptedException ex) {
	    //
	}
	Assert.assertTrue(true);
    }

    /**
     * Test of containsSubject method, of class TinkerforgeStackRegistrationService.
     */
    @Test
    public void testGetID() {
	System.out.println("getID");
	TinkerforgeStackRegistrationService instance = new TinkerforgeStackRegistrationService();
	String expResult = instance.getClass().getName();
	String result = instance.getID();
	Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getBusFactory method, of class TinkerforgeStackRegistrationService.
     */
    @Test
    public void testGetBusFactory() {
	System.out.println("getBusFactory");
	TinkerforgeStackRegistrationService instance = new TinkerforgeStackRegistrationService();
	BusFactory expResult = TinkerforgeBusFactory.getInstance();
	BusFactory result = instance.getBusFactory();
	Assert.assertEquals(expResult, result);

    }

    abstract class TestAgent extends ATinkerforgeAgent {

	@Override
	public String getID() {
	    return this.getClass().getName();
	}

    }

}
