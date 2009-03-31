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
package br.com.sysmap.crux.core.server.screen;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.id.jericho.lib.html.Attribute;
import au.id.jericho.lib.html.Attributes;
import au.id.jericho.lib.html.Element;
import au.id.jericho.lib.html.Source;
import br.com.sysmap.crux.core.i18n.MessagesFactory;
import br.com.sysmap.crux.core.server.ServerMessages;
import br.com.sysmap.crux.core.server.config.ConfigurationFactory;
import br.com.sysmap.crux.core.server.screen.config.ComponentConfig;
import br.com.sysmap.crux.core.server.screen.config.ComponentConfigData;
import br.com.sysmap.crux.core.server.screen.formatter.Formatter;
import br.com.sysmap.crux.core.server.screen.formatter.Formatters;
import br.com.sysmap.crux.core.server.screen.formatter.ServerFormatter;
import br.com.sysmap.crux.core.utils.RegexpPatterns;

/**
 * Factory for screens at the application's server side. It is necessary for GWT generators 
 * and for parameters binding.
 *  
 * @author Thiago Bustamante
 *
 */
public class ScreenFactory 
{
	private static final Log logger = LogFactory.getLog(ScreenFactory.class);
	private static final Lock lock = new ReentrantLock();
	private static final Lock screenLock = new ReentrantLock();
	private static ScreenFactory instance;
	private static ServerMessages messages = (ServerMessages)MessagesFactory.getMessages(ServerMessages.class);

	/**
	 * Return the singleton instance. It must be first initialized with getInstance(ServletContext) method.
	 * @return
	 * @throws ScreenConfigException
	 */
	public static ScreenFactory getInstance() throws ScreenConfigException
	{
		if (instance == null)
			throw new ScreenConfigException(messages.screenFactoryNotInitialized());
		return instance;
	}
	
	/**
	 * Singleton method
	 * @param servletContext
	 * @return
	 */
	public static ScreenFactory getInstance(ServletContext servletContext)
	{
		if (instance == null)
		{
			lock.lock();
			try
			{
				if (instance == null)
				{
					instance = new ScreenFactory(servletContext);
				}
			}
			finally 
			{
				lock.unlock();
			}
		}
		return instance;
	}
	
	private boolean hotDeploy;
	private boolean developmentMode = false;
	private ServletContext servletContext;
	private Map<String, Screen> screenCache = new HashMap<String, Screen>();
	private Map<String, Long> screenModified = new HashMap<String, Long>();

	/**
	 * Private Constructor
	 * @param servletContext
	 */
	private ScreenFactory(ServletContext servletContext)
	{
		this.servletContext = servletContext;
		this.hotDeploy = "true".equals(ConfigurationFactory.getConfiguration().enableHotDeployForScreens());
		this.developmentMode = ("development".equals(servletContext.getInitParameter("mode")));
	}
	
	/**
	 * Test if a target HTML element represents a Screen definition for Crux.
	 * @param element
	 * @return
	 */
	private boolean isScreenDefinitions(Element element)
	{
		if ("span".equalsIgnoreCase(element.getName()))
		{
			String type = element.getAttributeValue("_type");
			if (type != null && "screen".equals(type))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Test if a target HTML element represents a component definition for Crux.
	 * @param element
	 * @return
	 */
	private boolean isValidComponent(Element element)
	{
		if ("span".equalsIgnoreCase(element.getName()))
		{
			String type = element.getAttributeValue("_type");
			if (type != null && type.trim().length() > 0 && !"screen".equals(type))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a component based in its &lt;span&gt; tag definition.
	 * 
	 * @param source
	 * @param element
	 * @param screen
	 * @return
	 * @throws ScreenConfigException
	 */
	private Component createComponent(Source source, Element element, Screen screen) throws ScreenConfigException
	{
		String componentId = element.getAttributeValue("id");
		if (componentId == null || componentId.trim().length() == 0)
		{
			throw new ScreenConfigException(messages.screenFactoryComponentIdRequired());
		}
		Component component = screen.getComponent(componentId);
		if (component != null)
		{
			throw new ScreenConfigException(messages.screenFactoryErrorDuplicatedComponent(componentId));
		}
		
		component = newComponent(element, componentId);
		if (component == null)
		{
			throw new ScreenConfigException(messages.screenFactoryErrorCreateComponent(componentId));
		}
		
		screen.addComponent(component);
		checkAndRegisterComponentFormatter(component, screen);
		return component;
	}
	
	/**
	 * Register a formatter associated to component property, if it is defined.
	 * @param component
	 * @param screen
	 * @throws ScreenConfigException
	 */
	private void checkAndRegisterComponentFormatter(Component component, Screen screen) throws ScreenConfigException
	{
		String formatterString = component.getFormatter();
		String property = component.getProperty();
		if (formatterString != null && formatterString.length() > 0 && property != null && property.length() > 0)
		{
			String formatterParams = null;
			String formatterName = formatterString;
			int index = formatterString.indexOf("(");
			if (index > 0)
			{
				try 
				{
					formatterParams = formatterString.substring(index+1,formatterString.indexOf(")"));
					formatterName = formatterString.substring(0,index).trim();
				} 
				catch (RuntimeException e) 
				{
					throw new ScreenConfigException(messages.screenFactoryErrorCreatingFormatter(formatterString));
				}
			}
			Formatter formatter = Formatters.getFormatter(formatterName);
			if (formatter == null)
			{
				throw new ScreenConfigException(messages.screenFactoryFormatterNotFound(formatterName));
			}
			else
			{
				if (screen.containsFormatter(property))
				{
					throw new ScreenConfigException(messages.screenFactoryDuplicateProperty(property));
				}
				Class<? extends ServerFormatter> serverFormatter = formatter.getServerFormatter();
				if (serverFormatter != null)
				{
					try 
					{
						screen.setFormatter(property, getServerFormatter(serverFormatter, formatterParams));
					} 
					catch (Exception e) 
					{
						throw new ScreenConfigException(messages.screenFactoryErrorCreatingFormatter(formatterString));
					} 
				}
			}
		}
	}

	/**
	 * Creates a ServerFormatter based in formatter class and component declared parameters.
	 * @param serverFormatter
	 * @param formatterParams
	 * @return
	 * @throws Exception
	 */
	private ServerFormatter getServerFormatter(Class<? extends ServerFormatter> serverFormatter, String formatterParams) throws Exception
	{
		if (formatterParams == null)
		{
			return serverFormatter.newInstance();
		}
		try
		{
			Constructor<? extends ServerFormatter> c = serverFormatter.getConstructor(new Class<?>[]{String[].class});
			String[] params = RegexpPatterns.REGEXP_COMMA.split(formatterParams);
			for (int i = 0; i < params.length; i++) 
			{
				params[i] = params[i].trim();
			}
			return c.newInstance(new Object[]{params});
		}
		catch (Throwable e) 
		{
			return serverFormatter.newInstance();
		}
	}
	
	/**
	 * Verify the date of the last modification on screen file
	 * @param url
	 * @return
	 */
	private long getLastModified(URL url)
    {
        File file;
		try 
		{
			file = new File(new URI(RegexpPatterns.REGEXP_SPACE.matcher(url.toString()).replaceAll("%20")));
		} 
		catch (URISyntaxException e) 
		{
			logger.error(messages.screenFactoryCheckFileUpdateError(e.getMessage()), e);
			return Long.MAX_VALUE;
		}
        return file.lastModified();
    }

	/**
	 * Factory method for screens.
	 * @param id
	 * @return
	 * @throws ScreenConfigException
	 */
	public Screen getScreen(String id) throws ScreenConfigException
	{
		try 
		{
			Screen screen;
			
			if (this.developmentMode)
			{
				try
				{
					String[] s = RegexpPatterns.REGEXP_SLASH.split(id);
					String moduleName = s[0].substring(0,s[0].lastIndexOf("."));
					id = RegexpPatterns.REGEXP_DOT.matcher(moduleName).replaceAll("/")+"/"+ConfigurationFactory.getConfiguration().developmentPublicDir()+"/"+s[1];
				}
				catch (Throwable e) 
				{
					throw new ScreenConfigException(messages.screenFactoryErrorRetrievingScreen(id, e.getLocalizedMessage()), e);
				}
			}
			if (!hotDeploy)
			{
				screen = screenCache.get(id);
				if (screen != null)
				{
					return screen;
				}
			}

			URL url = servletContext.getResource((developmentMode?"/":ConfigurationFactory.getConfiguration().pagesHome())+id);
			if (url == null)
			{
				url = getClass().getResource((developmentMode?"/":ConfigurationFactory.getConfiguration().pagesHome())+id);
			}
			
			if (url == null)
			{
				throw new ScreenConfigException(messages.screenFactoryScreeResourceNotFound(id));
			}
			if (hotDeploy)
			{
				long lastModified;
				try 
				{
					lastModified = screenModified.get(id);
				}
				catch(Throwable e)
				{
					lastModified = -1;
				}
				long modified = getLastModified(url);
				if (lastModified < modified)
				{
					screenCache.put(id, null);
					screenLock.lock();
					try
					{
						if (screenCache.get(id) == null)
						{
							screen = parseScreen(id, url);
							screenModified.put(id, modified);
							screenCache.put(id, screen);
						}
					}
					finally
					{
						screenLock.unlock();
					}
				}
				return screenCache.get(id);
			}
			
			screenLock.lock();
			try
			{
				if (screenCache.get(id) == null)
				{
					screen = parseScreen(id, url);
					screenCache.put(id, screen);
				}
			}
			finally
			{
				screenLock.unlock();
			}
			return screenCache.get(id);
			
		} 
		catch (Throwable e) 
		{
			throw new ScreenConfigException(messages.screenFactoryErrorRetrievingScreen(id, e.getLocalizedMessage()), e);
		}
	}
	
	/**
	 * Builds a new component, based on its &lt;span&gt; tag definition.
	 * @param element
	 * @param componentId
	 * @return
	 * @throws ScreenConfigException
	 */
	private Component newComponent(Element element, String componentId) throws ScreenConfigException
	{
		String type = element.getAttributeValue("_type");
		String className = ComponentConfig.getServerClass(type);
		ComponentParser parser = ComponentConfig.getComponentParser(type);
		String parserInput = ComponentConfig.getParserInput(type);
		if (className == null || parser == null || parserInput == null)
		{
			throw new ScreenConfigException(messages.screenFactoryErrorCreateComponent(componentId));
		}
		try 
		{
			Component component = (Component) Class.forName(className).newInstance();
			component.setId(componentId);
			component.setType(type);
			if (ComponentConfigData.PARSER_INPUT_DOM.equals(parserInput))
			{
				parser.parse(component, toDomElment(element));
			}
			else if (ComponentConfigData.PARSER_INPUT_STRING.equals(parserInput))
			{
				parser.parse(component, element.toString());
			}
			else
			{
				parser.parse(component, element);
			}
			return component;
		} 
		catch (Throwable e) 
		{
			throw new ScreenConfigException(messages.screenFactoryErrorCreateComponent(componentId), e);
		} 
	}
	
	/**
	 * Parse the HTML page and build the Crux Screen. 
	 * @param id
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private Screen parseScreen(String id, URL url) throws IOException
	{
		Screen screen = new Screen(id);
		Source source = new Source(url);
		source.fullSequentialParse();
		List<?> elementList = source.findAllElements("span");
		
		for (Object object : elementList) 
		{
			Element compCandidate = (Element) object;
			if (isValidComponent(compCandidate))
			{
				try 
				{
					createComponent(source, compCandidate, screen);
				} 
				catch (ScreenConfigException e) 
				{
					logger.error(messages.screenFactoryGenericErrorCreateComponent(id, e.getLocalizedMessage()));
				}
			}
			else if (isScreenDefinitions(compCandidate))
			{
				parseScreenElement(screen,compCandidate);
			}
		} 
		
		return screen;
	}
	
	/**
	 * Parse screen element
	 * @param screen
	 * @param compCandidate
	 */
	private void parseScreenElement(Screen screen, Element compCandidate) 
	{
		Element elem = (Element) compCandidate;
		
		Attributes attrs =  elem.getAttributes();
		for (Object object : attrs) 
		{
			Attribute attr = (Attribute)object;
			String attrName = attr.getName();
			
			if (!attrName.equals("id") && !attrName.equals("_type"))
			{
				if (attrName.startsWith("_on"))
				{
					Event event = EventFactory.getEvent(attrName, attr.getValue());
					if (event != null)
					{
						screen.addEvent(event);
					}
				}
				else
				{
					try 
					{
						BeanUtils.copyProperty(this, attrName.substring(1), attr.getValue());
					} 
					catch (Throwable e) 
					{
						if (logger.isDebugEnabled()) logger.debug(messages.screenPropertyError(attrName.substring(1), screen.getId()));
					} 
				}
			}
		}
	}
	
	/**
	 * Convert a Jericho element to a DOM element
	 * @param element
	 * @return
	 */
	private org.w3c.dom.Element toDomElment(Element element)
	{
		return null;
	}
}
