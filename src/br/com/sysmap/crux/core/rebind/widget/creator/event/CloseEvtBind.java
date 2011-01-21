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
package br.com.sysmap.crux.core.rebind.widget.creator.event;

import br.com.sysmap.crux.core.client.event.Event;
import br.com.sysmap.crux.core.client.event.Events;
import br.com.sysmap.crux.core.client.screen.parser.CruxMetaDataElement;
import br.com.sysmap.crux.core.rebind.widget.EvtProcessor;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;

/**
 * Helper Class for close events binding
 * @author Thiago Bustamante
 *
 */
public class CloseEvtBind implements EvtProcessor<HasCloseHandlers<?>>
{
	private static final String EVENT_NAME = "onClick";
	
	@SuppressWarnings("unchecked")
	public void bindEvent(CruxMetaDataElement element, HasCloseHandlers<?> widget)
	{
		final Event eventClose = EvtBind.getWidgetEvent(element, EVENT_NAME);
		if (eventClose != null)
		{
			widget.addCloseHandler(new CloseHandler()
			{
				public void onClose(CloseEvent event) 
				{
					Events.callEvent(eventClose, event);
				}
			});
		}
	}

	public String getEventName()
	{
		return EVENT_NAME;
	}		
}
