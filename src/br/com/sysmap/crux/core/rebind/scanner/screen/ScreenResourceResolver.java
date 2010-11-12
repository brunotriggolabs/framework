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

import java.io.InputStream;
import java.util.Set;

import br.com.sysmap.crux.core.client.screen.InterfaceConfigException;

/**
 * @author Thiago da Rosa de Bustamante
 *
 */
public interface ScreenResourceResolver
{
	/**
	 * Gets the resource for a given screenId
	 * @param id
	 * @return
	 * @throws InterfaceConfigException 
	 */
	InputStream getScreenResource(String screenId) throws InterfaceConfigException;
	
	/**
	 * Gets the resource for a given screenId, the result must be a valid XML (escaped correctly)
	 * @param id
	 * @return
	 * @throws InterfaceConfigException 
	 */
	InputStream getScreenXMLResource(String screenId) throws InterfaceConfigException;

	/**
	 * List all Crux screen IDs
	 * @return
	 * @throws ScreenConfigException 
	 */
	Set<String> getAllScreenIDs(String module) throws ScreenConfigException;
}
