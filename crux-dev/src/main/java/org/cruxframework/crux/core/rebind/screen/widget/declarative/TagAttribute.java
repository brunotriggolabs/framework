/*
 * Copyright 2011 cruxframework.org.
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
package org.cruxframework.crux.core.rebind.screen.widget.declarative;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cruxframework.crux.core.client.screen.DeviceAdaptive.Device;
import org.cruxframework.crux.core.rebind.screen.widget.AttributeProcessor;


/**
 * @author Thiago da Rosa de Bustamante
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TagAttribute
{
	String value();
	Class<?> type() default String.class;
	String defaultValue() default "";
	String property() default "";
	boolean required() default false;
	boolean supportsI18N() default false;
	boolean supportsResources() default false;
	boolean supportsDataBinding() default true;
	Class<?> processor() default AttributeProcessor.NoParser.class;
	boolean xsdIgnore() default false;
	Device[] supportedDevices() default {Device.all};
	/**
	 * A description to be used to compose the documentation of the generated library 
	 */
	String description() default "";
}
