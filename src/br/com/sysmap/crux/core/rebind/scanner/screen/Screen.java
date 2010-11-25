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
package br.com.sysmap.crux.core.rebind.scanner.screen;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dev.util.collect.HashSet;

import br.com.sysmap.crux.core.client.utils.StringUtils;
import br.com.sysmap.crux.core.server.Environment;

/**
 * Represents a Crux Screen at the application's server side. Used for GWT Generators.
 * 
 * @author Thiago Bustamante
 */
public class Screen 
{
	protected String id;
	protected String relativeId;
	protected Map<String, Widget> widgets = new HashMap<String, Widget>();
	protected Map<String, Set<String>> widgetPropertiesMap = new HashMap<String, Set<String>>();
	protected Map<String, Event> events = new HashMap<String, Event>();
	protected List<String> controllers = new ArrayList<String>();
	protected List<String> serializers = new ArrayList<String>();
	protected List<String> formatters = new ArrayList<String>();
	protected List<String> dataSources = new ArrayList<String>();
	protected String title;
	
	protected String module;
	
	public Screen(String id, String relativeId, String module) 
	{
		this.id = id;
		this.relativeId = relativeId;
		this.module = module;
	}

	/**
	 * Return DeclarativeFactory associated with the given id
	 * @param widgetId
	 * @return
	 */
	public Widget getWidget(String widgetId)
	{
		if (widgetId == null) return null;
		return widgets.get(widgetId);
	}

	/**
	 * Return a Set containing all types of widgets found on this screen
	 * @return
	 */
	public Set<String> getWidgetTypesIncluded()
	{
		return widgetPropertiesMap.keySet();
	}
	
	/**
	 * Return a Set containing all properties and events used on this screen by all widgets of type widgetType
	 * @param widgetType
	 * @return
	 */
	public Set<String> getWidgetProperties(String widgetType)
	{
		return widgetPropertiesMap.get(widgetType);
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
			if (!widgetPropertiesMap.containsKey(widget.getType()))
			{
				widgetPropertiesMap.put(widget.getType(), new HashSet<String>());
			}
			
			checkUsedProperties(widget);
		}
	}

	/**
	 * @param widget
	 */
	private void checkUsedProperties(Widget widget) 
	{
		if (Environment.isProduction())
		{
		/*For Development purposes does not waste time doing this. That information is only used on {@code WidgetFactory} generator 
		 to improve performance of generated code. It only need to be called when compiling for production.
		 */ 
			Set<String> widgetProperties = widgetPropertiesMap.get(widget.getType());
			Iterator<Event> events = widget.iterateEvents();
			while (events.hasNext())
			{
				Event event = events.next();
				widgetProperties.add(event.getId());
			}
			Iterator<String> properties = widget.iteratePropertyNames();
			while (properties.hasNext())
			{
				widgetProperties.add(properties.next());
			}
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
	 * @return
	 */
	public String getRelativeId()
	{
		return relativeId;
	}
	
	/**
	 * @return
	 */
	public String getModule()
	{
		return module;
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

	/**
	 * Import a controller into screen
	 * @param event
	 */
	protected void addController(String controller)
	{
		if (!StringUtils.isEmpty(controller))
		{
			controllers.add(controller);
		}
	}
	
	/**
	 * Iterate over screen controllers
	 * @return
	 */
	public Iterator<String> iterateControllers()
	{
		return controllers.iterator();
	}

	/**
	 * Import a serializer for a CruxSerializable into screen
	 * @param event
	 */
	protected void addSerializer(String serializer)
	{
		if (!StringUtils.isEmpty(serializer))
		{
			serializers.add(serializer);
		}
	}
	
	/**
	 * Iterate over screen serializers
	 * @return
	 */
	public Iterator<String> iterateSerializers()
	{
		return serializers.iterator();
	}
	
	/**
	 * Import a formatter into screen
	 * @param event
	 */
	protected void addFormatter(String formatter)
	{
		if (!StringUtils.isEmpty(formatter))
		{
			formatters.add(formatter);
		}
	}
	
	/**
	 * Iterate over screen formatters
	 * @return
	 */
	public Iterator<String> iterateFormatters()
	{
		return formatters.iterator();
	}	

	/**
	 * Import a dataSource into screen
	 * @param event
	 */
	protected void addDataSource(String dataSource)
	{
		if (!StringUtils.isEmpty(dataSource))
		{
			dataSources.add(dataSource);
		}
	}
	
	/**
	 * Iterate over screen dataSources
	 * @return
	 */
	public Iterator<String> iterateDataSources()
	{
		return dataSources.iterator();
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
