/*
 * DevicesListPlace.java
 *
 * Tigase IoT Framework
 * Copyright (C) 2011-2018 "Tigase, Inc." <office@tigase.com>
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.iot.framework.client.client.devices;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 *
 * @author andrzej
 */
public class DevicesListPlace extends Place {

	public DevicesListPlace() {

	}

	public static class Tokenizer implements PlaceTokenizer<DevicesListPlace> {

		public DevicesListPlace getPlace(String token) {
			return new DevicesListPlace();
		}

		public String getToken(DevicesListPlace place) {
			return null;
		}

	}

}
