package br.com.sysmap.crux.core.rebind;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.com.sysmap.crux.core.client.utils.EscapeUtils;
import br.com.sysmap.crux.core.server.screen.Component;
import br.com.sysmap.crux.core.server.screen.Container;
import br.com.sysmap.crux.core.server.screen.Screen;
import br.com.sysmap.crux.core.server.screen.formatter.Formatters;
import br.com.sysmap.crux.core.utils.RegexpPatterns;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class RegisteredClientFormattersGenerator extends AbstractRegisteredElementsGenerator
{
	@Override
	protected void generateClass(TreeLogger logger, GeneratorContext context,JClassType classType, Screen screen) 
	{
		String packageName = classType.getPackage().getName();
		String className = classType.getSimpleSourceName();
		String implClassName = className + "Impl";

		PrintWriter printWriter = context.tryCreate(logger, packageName, implClassName);
		// if printWriter is null, source code has ALREADY been generated, return
		if (printWriter == null) return;

		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, implClassName);
		composer.addImplementedInterface("br.com.sysmap.crux.core.client.formatter.RegisteredClientFormatters");
		SourceWriter sourceWriter = null;
		sourceWriter = composer.createSourceWriter(context, printWriter);

		generateConstructor(logger, sourceWriter, screen, implClassName);
		sourceWriter.println("private java.util.Map<String,ClientFormatter> clientFormatters = new java.util.HashMap<String,ClientFormatter>();");

		sourceWriter.println("public ClientFormatter getClientFormatter(String id){");
		sourceWriter.println("return clientFormatters.get(id);");
		sourceWriter.println("}");

		sourceWriter.outdent();
		sourceWriter.println("}");

		context.commit(logger, printWriter);
	}
	
	protected void generateConstructor(TreeLogger logger, SourceWriter sourceWriter, Screen screen, String implClassName) 
	{
		sourceWriter.println("public "+implClassName+"(){ ");
		
		Iterator<Component> iterator = screen.iterateComponents();
		Map<String, Boolean> addedHandler = new HashMap<String, Boolean>();
		Map<String, Boolean> addedCallback = new HashMap<String, Boolean>();
		while (iterator.hasNext())
		{
			Component component = iterator.next();
			generateFormattersForComponent(logger, sourceWriter, component, addedHandler, addedCallback);
		}
		sourceWriter.println("}");
	} 
	
	protected void generateFormattersForComponent(TreeLogger logger,SourceWriter sourceWriter, Component component, Map<String, Boolean> addedHandler, Map<String, Boolean> addedCallback)
	{
		String formatter = component.getFormatter();
		
		if (formatter != null && formatter.length()>0)
		{
			generateFormatterBlock(logger,sourceWriter, component.getId(), formatter, addedCallback);
		}
		if (component instanceof Container)
		{
			Iterator<Component> iterator = ((Container)component).iterateComponents();
			while (iterator.hasNext())
			{
				Component child = iterator.next();
				generateFormattersForComponent(logger, sourceWriter, child, addedHandler, addedCallback);
			}
		}
	}
	
	protected void generateFormatterBlock(TreeLogger logger, SourceWriter sourceWriter, String componentId, String formatter, Map<String, Boolean> added)
	{
		try
		{
			String formatterParams = null;
			String formatterName = formatter;
			StringBuilder parameters = new StringBuilder();
			int index = formatter.indexOf("(");
			if (index > 0)
			{
				formatterParams = formatter.substring(index+1,formatter.indexOf(")"));
				formatterName = formatter.substring(0,index).trim();
				String[] params = RegexpPatterns.REGEXP_COMMA.split(formatterParams);
				parameters.append("new String[]{");
				for (int i=0; i < params.length; i++) 
				{
					if (i>0)
					{
						parameters.append(",");
					}
					parameters.append(EscapeUtils.quote(params[i]).trim());
				}
				parameters.append("}");
			}
			
			if (!added.containsKey(formatter) && Formatters.getFormatter(formatterName)!= null)
			{
				Class<?> formatterClass = Formatters.getFormatter(formatter).getClientFormatter();
				sourceWriter.print("clientFormatters.put(\""+formatter+"\", new " + formatterClass.getName() + "("+parameters.toString()+"));");
				added.put(formatter, true);
			}
		}
		catch (Throwable e) 
		{
			logger.log(TreeLogger.ERROR, messages.errorGeneratingRegisteredFormatter(componentId, e.getLocalizedMessage()), e);
		}
	}
	
	
	
}