package br.com.sysmap.crux.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import br.com.sysmap.crux.core.i18n.DefaultServerMessage;
import br.com.sysmap.crux.core.i18n.MessageException;

/**
 * Dynamic proxy for message resources.
 * @author Thiago da Rosa de Bustamante <code>tr_bustamante@yahoo.com.br</code>
 * @author Gess� S. F. Daf� <code>gessedafe@gmail.com</code>
 */
public abstract class ConstantsInvocationHandler implements InvocationHandler
{
	private Class<?> targetInterface;
	private Map<String, String> resolvedConstants = new ConcurrentHashMap<String, String>();
	
	/**
	 * 
	 * @param targetInterface
	 */
	public ConstantsInvocationHandler(Class<?> targetInterface) 
	{
		this.targetInterface = targetInterface;
	}
	
	/**
	 * 
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		String name = method.getName();
		if (resolvedConstants.containsKey(name))
		{
			return resolvedConstants.get(name);
		}
		String message = null;
		try
		{
			if (isValidPropertySetter(method))
			{
				invokeSetter(method, args);
			}
			else
			{
				message = getMessageFromProperties(args, name);
				if (message == null)
				{
					message = getMessageFromAnnotation(method, args, name);
				}
			}
		}
		catch (Throwable e)
		{
			message = getMessageFromAnnotation(method, args, name);
		}
		
		return message;
	}

	/**
	 * @param args
	 * @param name
	 * @return
	 */
	protected String getMessageFromProperties(Object[] args, String name)
	{
		PropertyResourceBundle properties = getPropertiesForLocale(targetInterface);
		String message = null;
		if (properties != null)
		{
			message = MessageFormat.format(properties.getString(name),args);
			resolvedConstants.put(name, message);
		}
		return message;
	}

	/**
	 * @param method
	 * @param args
	 * @param name
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected String getMessageFromAnnotation(Method method, Object[] args, String name)
	{
		DefaultServerMessage serverAnnot = method.getAnnotation(DefaultServerMessage.class);
		if (serverAnnot != null)
		{
			String value = MessageFormat.format(serverAnnot.value(),args);
			resolvedConstants.put(name, value);
			return value;
		}
		else
		{
			br.com.sysmap.crux.core.i18n.DefaultMessage annot = method.getAnnotation(br.com.sysmap.crux.core.i18n.DefaultMessage.class);
			if (annot != null)
			{
				String value = MessageFormat.format(annot.value(),args);
				resolvedConstants.put(name, value);
				return value;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param method
	 * @return
	 */
	protected boolean isValidPropertySetter(Method method)
	{
		String methodName = method.getName();
		if (methodName.startsWith("set") && methodName.length() > 3)
		{
			String property = getPropertyFromSetterMethodName(methodName);
			if (resolvedConstants.containsKey(property))
			{
				return method.getParameterTypes().length == 1;
			}
			try
			{
				targetInterface.getMethod(property, new Class[]{});
				return true;
			}
			catch (Throwable e)
			{
				return false;
			}
		}
		
		return false;
	}

	/**
	 * 
	 * @param properties
	 * @param method
	 * @param args
	 */
	protected void invokeSetter(Method method, Object[] args)
	{
		String property = getPropertyFromSetterMethodName(method.getName());
		Object value = args[0];
		if (value == null)
		{
			resolvedConstants.remove(property);
		}
		else
		{
			resolvedConstants.put(property, value.toString());
		}
	}
	
	/**
	 * 
	 * @param targetInterface
	 * @param locale
	 * @return
	 */
	protected static PropertyResourceBundle loadProperties (Class<?> targetInterface, final Locale locale)
	{
		PropertyResourceBundle properties = null;
		try
		{
			properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(targetInterface.getSimpleName(), locale);
		}
		catch (Throwable e) 
		{
			try 
			{
				String resourceName = "/"+targetInterface.getName().replaceAll("\\.", "/") + ".properties";
				InputStream input = targetInterface.getClassLoader().getResourceAsStream(resourceName);
				if (input != null)
				{
					properties = new PropertyResourceBundle(input);
				}
			} 
			catch (IOException e1) 
			{
				throw new MessageException(e.getMessage(), e);
			}
		}
		return properties;
	}

	/**
	 * 
	 * @param methodName
	 * @return
	 */
	private String getPropertyFromSetterMethodName(String methodName)
	{
		String property = methodName.substring(3);
		if (property.length() == 1)
		{
			property = Character.toLowerCase(property.charAt(0))+"";
		}
		else
		{
			property = Character.toLowerCase(property.charAt(0)) + property.substring(1);
		}
		return property;
	}

	/**
	 * 
	 * @param <T>
	 * @param targetInterface
	 * @return
	 */
	protected abstract <T> PropertyResourceBundle getPropertiesForLocale(final Class<T> targetInterface); 
}