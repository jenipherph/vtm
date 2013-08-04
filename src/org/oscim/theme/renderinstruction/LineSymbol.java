/*
 * Copyright 2010, 2011, 2012 mapsforge.org
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.oscim.theme.renderinstruction;

import org.oscim.theme.IRenderCallback;
import org.oscim.theme.RenderThemeHandler;
import org.xml.sax.Attributes;


/**
 * Represents an icon along a polyline on the map.
 */
public final class LineSymbol extends RenderInstruction {
	/**
	 * @param elementName
	 *            the name of the XML element.
	 * @param attributes
	 *            the attributes of the XML element.
	 * @return a new LineSymbol with the given rendering attributes.
	 */
	public static LineSymbol create(String elementName, Attributes attributes) {
		String src = null;
		boolean alignCenter = false;
		boolean repeat = false;

		for (int i = 0; i < attributes.getLength(); ++i) {
			String name = attributes.getLocalName(i);
			String value = attributes.getValue(i);

			if ("src".equals(name)) {
				src = value;
			} else if ("align-center".equals(name)) {
				alignCenter = Boolean.parseBoolean(value);
			} else if ("repeat".equals(name)) {
				repeat = Boolean.parseBoolean(value);
			} else {
				RenderThemeHandler.logUnknownAttribute(elementName, name, value, i);
			}
		}

		validate(elementName, src);
		return new LineSymbol(src, alignCenter, repeat);
	}

	private static void validate(String elementName, String src) {
		if (src == null) {
			throw new IllegalArgumentException("missing attribute src for element: "
					+ elementName);
		}
	}

	public final boolean alignCenter;
	//public final Bitmap bitmap;
	public final boolean repeat;
	public final String bitmap;

	private LineSymbol(String src, boolean alignCenter, boolean repeat) {
		super();

		this.bitmap = src;
		//this.bitmap = BitmapUtils.createBitmap(src);
		this.alignCenter = alignCenter;
		this.repeat = repeat;
	}

	@Override
	public void renderWay(IRenderCallback renderCallback) {
		renderCallback.renderWaySymbol(this);
	}
}
