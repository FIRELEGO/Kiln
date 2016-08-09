import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

/**
 * Write a description of class Thermocouple here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Thermocouple
{
    // doPin is the Data Output pin from the chip and is used to recieve all data to the pi.
    private GpioPinDigitalInput doPin;
    // csPin is the Chip Select pin which sends a signal to the chip from the pi telling it to take another reading.
    private GpioPinDigitalOutput csPin;
    // clkPin is the CLocK pin which tells the chip to send another bit of data to the pi
    private GpioPinDigitalOutput clkPin;
    
    // tempOffset is the amount of degrees a raw temperature read from the chip is from the true temp
    private int tempOffset = 0;
    
    //An array which maps the GPIO pin numbers to integers
    private static final Pin[] pinMap = new Pin[] {RaspiPin.GPIO_00, RaspiPin.GPIO_01, RaspiPin.GPIO_02, RaspiPin.GPIO_03, RaspiPin.GPIO_04, 
        RaspiPin.GPIO_05, RaspiPin.GPIO_06, RaspiPin.GPIO_07, RaspiPin.GPIO_08, RaspiPin.GPIO_09, RaspiPin.GPIO_10, 
        RaspiPin.GPIO_11, RaspiPin.GPIO_12, RaspiPin.GPIO_13, RaspiPin.GPIO_14, RaspiPin.GPIO_15, RaspiPin.GPIO_16, 
        RaspiPin.GPIO_17, RaspiPin.GPIO_18, RaspiPin.GPIO_19, RaspiPin.GPIO_20};

    //The default pin, used by the no-parameters constructor.
    private final static int defaultDO = 12;
    private final static int defaultCS = 13;
    private final static int defaultCLK = 14;
    private final static int defaultOffset = 0; 

    /**
     * Constructor for objects of class Thermocouple
     */
    public Thermocouple()
    {
        // Instantiates Thermocouple object with default pins
        this(defaultDO, defaultCS, defaultCLK, defaultOffset);
    }

    /**
     * Constructor for objects of class Thermocouple
     * 
     * @param doNum pin number for the DO pin
     * @param csNum pin number for the CS pin
     * @param clkNum pin number for the CLK pin
     */
    public Thermocouple(int doNum, int csNum, int clkNum, int tempOffset)
    {
        // Pin allocation is handled by a GpioController. So first we have to get that from the Factory.
        GpioController gpio = GpioFactory.getInstance();
        
        // initialise pins
        doPin = gpio.provisionDigitalInputPin(pinMap[doNum]);
        csPin = gpio.provisionDigitalOutputPin(pinMap[csNum], PinState.HIGH);
        clkPin = gpio.provisionDigitalOutputPin(pinMap[clkNum], PinState.LOW);
        
        this.tempOffset = tempOffset;
    }
    
    /**
     * Method which request the temperature from the 
     * 
     * @return     the temperature adjusted for thermocouple misread.
     */
    public double getTemp()
    {
        int temp = -tempOffset;
        // Tells the chip to begin outputing data
        csPin.setState(PinState.LOW);
        pause();
        
        int[] data = new int[14];
        
        for(int i = 0; i < 14; i++) {
            clkPin.setState(PinState.LOW);
            pause();
            
            data[i] = (doPin.isHigh() ? 1 : 0);
            
            clkPin.setState(PinState.HIGH);
            pause();
        }
        
        // Returns the chip to normal state
        csPin.setState(PinState.HIGH);
        
        String val = "";
        for(int i = 0; i < 14; i++) {
            val += data[i];
        }
        
        return Integer.parseInt(val, 2) / 4.0;
    }
    
    private void pause()
    {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e){};
    }
}
