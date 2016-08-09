import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

/**
 * Write a description of class Element here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Element
{
    // pin controls the relay that powers the element
    private GpioPinDigitalOutput relayPin;
    
    //An array which maps the GPIO pin numbers to integers
    private static final Pin[] pinMap = new Pin[] {RaspiPin.GPIO_00, RaspiPin.GPIO_01, RaspiPin.GPIO_02, RaspiPin.GPIO_03, RaspiPin.GPIO_04, 
        RaspiPin.GPIO_05, RaspiPin.GPIO_06, RaspiPin.GPIO_07, RaspiPin.GPIO_08, RaspiPin.GPIO_09, RaspiPin.GPIO_10, 
        RaspiPin.GPIO_11, RaspiPin.GPIO_12, RaspiPin.GPIO_13, RaspiPin.GPIO_14, RaspiPin.GPIO_15, RaspiPin.GPIO_16, 
        RaspiPin.GPIO_17, RaspiPin.GPIO_18, RaspiPin.GPIO_19, RaspiPin.GPIO_20};

    //The default pin, used by the no-parameters constructor.
    private final static int defaultRelay = 11;

    /**
     * Constructor for objects of class Element
     */
    public Element()
    {
        // Instantiates Thermocouple object with default pins
        this(defaultRelay);
    }

    /**
     * Constructor for objects of class Element
     * 
     * @param relayNum pin number for the relay pin
     */
    public Element(int relayNum)
    {
        // Pin allocation is handled by a GpioController. So first we have to get that from the Factory.
        GpioController gpio = GpioFactory.getInstance();
        
        // initialise pin
        relayPin = gpio.provisionDigitalOutputPin(pinMap[relayNum], PinState.LOW);
    }
    
    public void turnOn() {
      relayPin.setState(PinState.HIGH);
    }
    
    public void turnOff() {
      relayPin.setState(PinState.LOW);
    }
}
