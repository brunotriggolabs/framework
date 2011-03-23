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
package org.cruxframework.crux.gwt.rebind;

import org.cruxframework.crux.core.rebind.screen.widget.WidgetCreator;
import org.cruxframework.crux.core.rebind.screen.widget.WidgetCreatorContext;
import org.cruxframework.crux.core.rebind.screen.widget.creator.FocusableFactory;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.BlurEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.ClickEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.DoubleClickEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.FocusEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.KeyDownEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.KeyPressEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.KeyUpEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.MouseDownEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.MouseMoveEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.MouseOutEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.MouseOverEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.MouseUpEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.creator.event.MouseWheelEvtBind;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagAttribute;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagAttributes;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagEvent;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagEvents;

/**
 * This is the base factory class for widgets that can receive focus. 
 * 
 * @author Thiago Bustamante
 *
 */
@TagAttributes({
	@TagAttribute(value="enabled", type=Boolean.class)
})
@TagEvents({
	@TagEvent(ClickEvtBind.class),
	@TagEvent(DoubleClickEvtBind.class),
	@TagEvent(FocusEvtBind.class),
	@TagEvent(BlurEvtBind.class),
	@TagEvent(KeyPressEvtBind.class),
	@TagEvent(KeyUpEvtBind.class),
	@TagEvent(KeyDownEvtBind.class),
	@TagEvent(MouseDownEvtBind.class),
	@TagEvent(MouseUpEvtBind.class),
	@TagEvent(MouseOverEvtBind.class),
	@TagEvent(MouseOutEvtBind.class),
	@TagEvent(MouseMoveEvtBind.class),
	@TagEvent(MouseWheelEvtBind.class)
})
public abstract class FocusWidgetFactory <C extends WidgetCreatorContext> extends WidgetCreator<C> implements FocusableFactory<C> 
{
}