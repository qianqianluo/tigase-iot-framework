/*
 * PinSwitch.java
 *
 * Tigase IoT Framework
 * Copyright (C) 2011-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.iot.framework.rpi.sensors;

import com.pi4j.io.gpio.*;
import tigase.iot.framework.devices.AbstractSensor;
import tigase.iot.framework.devices.IConfigurationAware;
import tigase.iot.framework.devices.IExecutorDevice;
import tigase.iot.framework.values.OnOffState;
import tigase.kernel.beans.config.ConfigField;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PinSwitch
		extends AbstractSensor<OnOffState>
		implements IExecutorDevice<OnOffState>, IConfigurationAware {

	private static final Logger log = Logger.getLogger(PinSwitch.class.getCanonicalName());

	@ConfigField(desc = "WiringPi Pin number")
	private Integer pin = 2;

	@ConfigField(desc = "Enable with high state")
	private boolean enableWithHigh = true;

	@ConfigField(desc = "Initial state")
	private boolean initialValue = false;

	private GpioController gpio;
	private GpioPinDigitalOutput output;

	public PinSwitch() {
		super("switch", "Switch", "Pin Switch");
	}

	@Override
	public void initialize() {
		gpio = GpioFactory.getInstance();
		initializeOutput();
	}

	@Override
	public void beforeUnregister() {
		super.beforeUnregister();
		if (output != null) {
			gpio.unprovisionPin(output);
		}
	}

	public void setPin(Integer pin) {
		this.pin = pin;
		if (gpio != null) {
			if (output != null) {
				gpio.unprovisionPin(output);
				output = null;
			}
			initializeOutput();
		}
	}

	@Override
	public void setValue(OnOffState value) {
		OnOffState currentValue = getValue();
		if (currentValue != null && value.getValue() == currentValue.getValue() &&
				!value.getTimestamp().isAfter(currentValue.getTimestamp())) {
			return;
		}

		log.log(Level.INFO, "{0} - {1}, setting switch to {2}",
				new Object[]{this.getName(), getLabel(), value.getValue()});

		boolean v = value.getValue();
		if (!enableWithHigh) {
			v = !v;
		}
		output.setState(v);

		updateValue(value);
	}

	private void initializeOutput() {
		output = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pin));
		output.setShutdownOptions(true, PinState.getState(initialValue));
	}
}
