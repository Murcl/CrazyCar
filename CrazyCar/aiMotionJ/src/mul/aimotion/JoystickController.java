
package mul.aimotion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import mul.aimotion.car.Car;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author Martin Antenreiter
 */
public class JoystickController extends AbstractController {

    private final String TAG = JoystickController.class.getSimpleName();
    
    private final List<Controller> foundControllers;
    private Controller controller;
    private float xAxisValue;
    private float yAxisValue;
    private boolean button1Pressed;
    private boolean button2Pressed;

    public JoystickController(Car car) throws IOException {
        super(car);
        Logger.getLogger(TAG).info("-Djava.library.path=" + System.getProperty("java.library.path"));
        foundControllers = searchForControllers();
        if(foundControllers.isEmpty()) {
            car.disconnect();
            Logger.getLogger(TAG).severe("No controller found.");
            throw new RuntimeException("No controller found.");
        } else {
            controller = foundControllers.get(0);
        }
        xAxisValue = 0.0f;
        yAxisValue = 0.0f;
    }
    /**
     * Search (and save) for controllers of type Controller.Type.STICK,
     * Controller.Type.GAMEPAD, Controller.Type.WHEEL and
     * Controller.Type.FINGERSTICK.
     */
    private List<net.java.games.input.Controller> searchForControllers() {
       	List<net.java.games.input.Controller> foundControllers = new ArrayList<>();

        net.java.games.input.Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (int i = 0; i < controllers.length; i++) {
            net.java.games.input.Controller controller = controllers[i];

            if (controller.getType() == net.java.games.input.Controller.Type.STICK || controller.getType() == net.java.games.input.Controller.Type.GAMEPAD
                    || controller.getType() == net.java.games.input.Controller.Type.WHEEL
                    || controller.getType() == net.java.games.input.Controller.Type.FINGERSTICK) {
                // Add new controller to the list of all controllers.
                foundControllers.add(controller);

                // Add new controller to the list on the window.
                //window.addControllerName(controller.getName() + " - " + controller.getType().toString() + " type");
            }
        }
        return foundControllers;
    }
    
    public void setController(int index) {
        if(index < 0 || index >= foundControllers.size()) throw new IndexOutOfBoundsException("Controller index is " + index + " < " + foundControllers.size());
        controller = foundControllers.get(index);
    }
    
    @Override
    public void controll() throws IOException {
	if (!controller.poll()) {
            car.disconnect();
            return; // Controller disconnected
	}        
        readJoystick();
        //yAxisValue = -1.0 full speed forward, +1.0 full speed backward
        //xAxisValue = +1.0 turn right, -1.0 turn left
        if(button1Pressed) {
            car.setSpeed(80);
            car.setSteering(car.getSteeringTrim());
            if(button2Pressed) car.disconnect();
        } else {
            car.setSpeed((int) (125 + 125 * (-yAxisValue)));
            car.setSteering( (int)(car.getSteeringTrim() + car.getMaxSteering() * xAxisValue) % 256);
            //car.setSpeed(190);
            //car.setSteering(33);
        }
    }
    
    private void readJoystick() {
        Component[] components = controller.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            Component.Identifier componentIdentifier = component.getIdentifier();
            //System.out.println(componentIdentifier + " " + componentIdentifier.getName() + " " + component.getPollData());
            if(componentIdentifier == Component.Identifier.Button.THUMB) {
                button1Pressed = component.getPollData() >= 0.5f;
            }
            if(componentIdentifier == Component.Identifier.Button.TRIGGER) {
                button2Pressed = component.getPollData() >= 0.5f;
            }
            // Buttons
            // if(component.getName().contains("Button")){ // If the
            // language is not english, this won't work.
/*            if (componentIdentifier.getName().matches("^[0-9]*$")) { // If
                // the component identifier name contains only numbers,
                // then this is a button. Is button pressed?
                boolean isItPressed = true;
                if (component.getPollData() == 0.0f) {
                    isItPressed = false;
                }

                // Button index
                // String buttonIndex = component.getIdentifier().toString();
                continue;
            }*/

            // Hat switch
/*            if (componentIdentifier == Component.Identifier.Axis.POV) {
                float hatSwitchPosition = component.getPollData();

                // We know that this component was hat switch so we can skip
                // to next component.
                continue;
            }*/

            // Axes
            if (component.isAnalog()) {
                float axisValue = component.getPollData();
                // X axis
                if (componentIdentifier == Component.Identifier.Axis.X) {
                    xAxisValue = axisValue;
                    continue; // Go to next component.
                }
                // Y axis
//                if (componentIdentifier == Component.Identifier.Axis.Y) {
                if (componentIdentifier == Component.Identifier.Axis.SLIDER) {
                    yAxisValue = axisValue;
                    continue; // Go to next component.
                }

                // Other axis
                // Do something
            }
        }        
    }
    

}
