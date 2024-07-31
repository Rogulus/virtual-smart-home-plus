package io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.patriot_framework.generator.dataFeed.ConstantDataFeed;
import io.patriot_framework.generator.device.impl.basicActuators.LinearActuator;
import io.patriot_framework.generator.device.impl.basicSensors.Default;
import io.patriot_framework.generator.device.passive.sensors.SimpleSensor;
import io.patriot_framework.virtualsmarthomeplus.DTOs.AutomaticDoorDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.house.House;
import io.patriot_framework.virtualsmarthomeplus.house.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Representation of automatic door device.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AutomaticDoor extends Device implements Runnable{

    public static final String OPENED = "opened";
    public static final String CLOSED = "closed";
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    private boolean opened = false;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private LinearActuator doorPiston;
    private ConstantDataFeed defaultDataFeed = new ConstantDataFeed(500.0);
    private SimpleSensor proximitySensor;



    /**
     * Creates new door with given label.
     *
     * @param label label creates identity of the door and is compared in the equals method
     */
    @JsonCreator
    public AutomaticDoor(String label) {
        super(label);
        defaultDataFeed.setLabel("proximityDataFeed");
        doorPiston = new LinearActuator("doorPiston" + "-" + label, 5000);
        proximitySensor = new Default("proximitySensor" + "-" + label, defaultDataFeed);
        proximitySensor.registerToCoapServer();
        doorPiston.registerToCoapServer();
        scheduler.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Creates new door with the same values of the attributes as given door except label.
     * Label of the new door is given by parameter.
     *
     * @param origDoor new door copies values of given device
     * @param newLabel label creates identity of the door and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    public AutomaticDoor(AutomaticDoor origDoor, String newLabel) {
        this(newLabel);
    }

    /**
     * Opens the door.
     */
    public void open() {
        if (!opened) {
            doorPiston.controlSignal("extend");
            LOGGER.debug(String.format("Automatic door %s are opening\"", getLabel()));
        }
    }

    /**
     * Closes the door.
     */
    public void close() {
        if (opened) {
            doorPiston.controlSignal("retract");
            LOGGER.debug(String.format("Automatic door %s are closing", getLabel()));
        }
    }

    /**
     * Returns info about the door.
     *
     * @return {@link #OPENED} if the door is opened, {@link #CLOSED} otherwise
     */
    public String getStatus() throws ExecutionException, InterruptedException {
        return Objects.equals(doorPiston.getStateMachine().getCurrent(), "Extended") ? OPENED : CLOSED;
    }

    public double getDistance() {
        return proximitySensor.getDataFeed().getNextValue().get(Double.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutomaticDoor createWithSameAttributes(String newLabel) {
        return new AutomaticDoor(this, newLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSameAttributes(Device door) throws IllegalArgumentException {
        if (door == null) {
            throw new IllegalArgumentException("Door cannot be null");
        }
        if (getClass() != door.getClass()) {
            throw new IllegalArgumentException("device must be of class Automatic Door");
        }

        final AutomaticDoor typedDoor = (AutomaticDoor) door;

        if (isEnabled() != typedDoor.isEnabled()) {
            return false;
        }
        return typedDoor.opened == opened;
    }

    /**
     * Updates the door object with the values from provided DTO.
     *
     * @param deviceDTO door DTO containing the updated values or null if value was not updated
     */
    public void update(DeviceDTO deviceDTO) {
        final AutomaticDoorDTO doorDTO = (AutomaticDoorDTO) deviceDTO;
        if (doorDTO.getStatus() != null) {
            if (doorDTO.getStatus().equals(OPENED)) {
                this.open();
            } else if (doorDTO.getStatus().equals(CLOSED)) {
                this.close();
            }
        }
        super.update(doorDTO);
    }

    @Override
    public void run() {
        double proximity = proximitySensor.getDataFeed().getNextValue().get(Double.class);
        if(proximity < 10) {
            try {
                if(!Objects.equals(doorPiston.getStateMachine().getCurrent(), "Extended")) {
                    doorPiston.controlSignal("extend");
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if(!Objects.equals(doorPiston.getStateMachine().getCurrent(), "Retracted")) {
                    doorPiston.controlSignal("retract");
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}