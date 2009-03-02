package br.com.sysmap.crux.core.server.screen.formatter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.sysmap.crux.core.i18n.MessagesFactory;
import br.com.sysmap.crux.core.server.ServerMessages;
import br.com.sysmap.crux.core.server.scan.ClassScanner;
import br.com.sysmap.crux.core.server.scan.ScannerURLS;
import br.com.sysmap.crux.core.server.screen.formatter.annotation.FormatterName;

public class Formatters 
{
	private static final Log logger = LogFactory.getLog(Formatters.class);
	private static ServerMessages messages = (ServerMessages)MessagesFactory.getMessages(ServerMessages.class);
	private static final Lock lock = new ReentrantLock();
	private static Map<String, Formatter> formatters;
	
	public static void initialize(URL[] urls)
	{
		if (formatters != null)
		{
			return;
		}
		try
		{
			lock.lock();
			if (formatters != null)
			{
				return;
			}
			
			initializeFormatters(urls);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	protected static void initializeFormatters(URL[] urls)
	{
		formatters = new HashMap<String, Formatter>();
		Set<String> formatterNames =  ClassScanner.getInstance(urls).searchClassesByInterface(Formatter.class);
		if (formatterNames != null)
		{
			for (String formatter : formatterNames) 
			{
				try 
				{
					Class<?> controllerClass = Class.forName(formatter);
					FormatterName annot = controllerClass.getAnnotation(FormatterName.class);
					if (annot != null)
					{
						formatters.put(annot.value(), (Formatter)controllerClass.newInstance());
					}
					else
					{
						formatters.put(controllerClass.getSimpleName(), (Formatter)controllerClass.newInstance());
					}
				} 
				catch (Throwable e) 
				{
					logger.error(messages.formattersFormatterInitializeError(e.getLocalizedMessage()),e);
				}
			}
		}
	}

	public static Formatter getFormatter(String name)
	{
		if (formatters == null)
		{
			initialize(ScannerURLS.getURLsForSearch(null));
		}
		
		if (name == null)
		{
			return null;
		}
		
		int index = name.indexOf("(");
		if (index > 0)
		{
			name = name.substring(0,index);
		}
		return formatters.get(name);
	}
}