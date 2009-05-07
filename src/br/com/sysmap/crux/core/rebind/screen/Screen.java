/*
 * Copyright 2009 Sysmap Solutions Software e Consultoria Ltda.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.sysmap.crux.core.rebind.screen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a Crux Screen at the application's server side. Used for GWT Generators and 
 * request parameter binding.
 * 
 * @author Thiago Bustamante
 */
public class Screen 
{
	protected String id;
	protected Map<String, Widget> widgets = new HashMap<String, Widget>();
	protected Map<String, Event> events = new HashMap<String, Event>();
	
	public Screen(String id) 
	{
		this.id = id;
	}

	/**
	 * Return WidgetFactory associated with the given id
	 * @param widgetId
	 * @return
	 */
	public Widget getWidget(String widgetId)
	{
		if (widgetId == null) return null;
		return widgets.get(widgetId);
	}

	/**
	 * Iterate over widgets
	 * @return
	 */
	public Iterator<Widget> iterateWidgets() 
	{
		return widgets.values().iterator();
	}

	/**
	 * Add a new widget to screen
	 * @param widget
	 */
	protected void addWidget(Widget widget)
	{
		if (widget != null)
		{
			widgets.put(widget.getId(), widget);
		}
	}
	
	/**
	 * Return screen identifier
	 * @return
	 */
	public String getId() 
	{
		return id;
	}

	/**
	 * Add a new event to screen
	 * @param event
	 */
	protected void addEvent(Event event)
	{
		if (event != null)
		{
			events.put(event.getId(), event);
		}
	}
	
	/**
	 * Return a event associated with the given id
	 * @param evtId
	 * @return
	 */
	public Event getEvent(String evtId)
	{
		return events.get(evtId);
	}
	
	/**
	 * Iterate over screen events
	 * @return
	 */
	public Iterator<Event> iterateEvents()
	{
		return events.values().iterator();
	}
}