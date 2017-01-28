/*
 * ValueFormatter.java
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

package tigase.iot.framework.runtime;

import tigase.iot.framework.devices.IValue;
import tigase.jaxmpp.core.client.exceptions.JaxmppException;
import tigase.jaxmpp.core.client.xml.Element;

/**
 * Created by andrzej on 30.10.2016.
 */
public interface ValueFormatter<T extends IValue> {

	Class<T> getSupportedClass();

	default boolean isSupported(Object o) {
		if (o == null) {
			return false;
		}
		return getSupportedClass().isAssignableFrom(o.getClass());
	}

	Element toElement(T value) throws JaxmppException;

	T fromElement(Element elem) throws JaxmppException;

}
