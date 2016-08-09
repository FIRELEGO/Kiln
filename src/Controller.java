/**
 * Main kiln controller.
 * 
 * @author Nicholas Signori 
 * @version 1.0.0
 */
public class Controller
{

    /**
     * Constructor for objects of class Controller
     */
    public static void main()
    {
        Thermocouple therm = new Thermocouple();
        System.out.println(therm.getTemp());
    }
}
