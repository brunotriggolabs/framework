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
package br.com.sysmap.crux.tools.schema;

import java.io.File;
import java.util.Map;

import br.com.sysmap.crux.tools.parameters.ConsoleParameter;
import br.com.sysmap.crux.tools.parameters.ConsoleParametersProcessingException;
import br.com.sysmap.crux.tools.parameters.ConsoleParametersProcessor;

/**
 * @author Thiago da Rosa de Bustamante
 *
 */
public class SchemaGenerator
{
	/**
	 *  
	 * @param args
	 */
	public static void generateSchemas(File projectBaseDir, File outputDir, File webDir, boolean generateModuleSchema)
	{
		CruxSchemaGenerator generator = CruxSchemaGeneratorFactory.createSchemaGenerator(projectBaseDir, outputDir, webDir);
		generator.generateSchemas();
		generator.generateCatalog();
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void generateSchemas(String projectBaseDir, String destDir, String webDir, boolean generateModuleSchema)
	{
		generateSchemas(new File(projectBaseDir), new File(destDir), webDir!=null?new File(webDir):null, generateModuleSchema);
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			try
			{
				ConsoleParametersProcessor parametersProcessor = createParametersProcessor();
				Map<String, ConsoleParameter> parameters = parametersProcessor.processConsoleParameters(args);

				if (parameters.containsKey("-help") || parameters.containsKey("-h"))
				{
					parametersProcessor.showsUsageScreen();
				}
				else
				{
					String webDir = null;
					if (parameters.containsKey("webDir"))
					{
						webDir = parameters.get("webDir").getValue();
					}
					SchemaGenerator.generateSchemas(parameters.get("projectBaseDir").getValue(), 
													parameters.get("outputDir").getValue(), 
													webDir, 
													parameters.containsKey("-generateModuleSchema"));
				}
			}
			catch (ConsoleParametersProcessingException e)
			{
				System.out.println("Program aborted");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	private static ConsoleParametersProcessor createParametersProcessor()
	{
		ConsoleParametersProcessor parametersProcessor = new ConsoleParametersProcessor("SchemaGenerator");
		parametersProcessor.addSupportedParameter(new ConsoleParameter("projectBaseDir", "The project folder."));
		parametersProcessor.addSupportedParameter(new ConsoleParameter("outputDir", "The folder where the files will be created."));
		parametersProcessor.addSupportedParameter(new ConsoleParameter("webDir", "The project web root folder.", false, false));
		parametersProcessor.addSupportedParameter(new ConsoleParameter("-generateModuleSchema", "Generates also the modules.xsd file.", false, true));
		parametersProcessor.addSupportedParameter(new ConsoleParameter("-help", "Display the usage screen.", false, true));
		parametersProcessor.addSupportedParameter(new ConsoleParameter("-h", "Display the usage screen.", false, true));
		return parametersProcessor;
	}	
}