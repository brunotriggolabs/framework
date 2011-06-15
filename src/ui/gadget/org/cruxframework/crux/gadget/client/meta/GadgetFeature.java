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
package org.cruxframework.crux.gadget.client.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cruxframework.crux.gadget.client.features.AdsFeature;
import org.cruxframework.crux.gadget.client.features.DynamicHeightFeature;
import org.cruxframework.crux.gadget.client.features.GoogleAnalyticsFeature;
import org.cruxframework.crux.gadget.client.features.MiniMessageFeature;
import org.cruxframework.crux.gadget.client.features.PubsubFeature;
import org.cruxframework.crux.gadget.client.features.RpcFeature;
import org.cruxframework.crux.gadget.client.features.SetPrefsFeature;
import org.cruxframework.crux.gadget.client.features.SetTitleFeature;
import org.cruxframework.crux.gadget.client.features.TabsFeature;
import org.cruxframework.crux.gadget.client.features.ViewFeature;
import org.cruxframework.crux.gadget.client.features.osapi.OsapiFeature;


/**
 * @author Thiago da Rosa de Bustamante
 *
 */
public interface GadgetFeature
{

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface NeedsFeatures
	{
		Feature[] value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Feature
	{
		ContainerFeature value();
	}
	
	public enum ContainerFeature{
		ads("ads", AdsFeature.class), 
		dynamicHeight("dynamic-height", DynamicHeightFeature.class),
		googleAnalytics("com.google.gadgets.analytics", GoogleAnalyticsFeature.class),
		lockedDomain("locked-domain", null),
		minimessage("minimessage", MiniMessageFeature.class),
		osapi("osapi", OsapiFeature.class),
		pubsub("pubsub", PubsubFeature.class),
		rpc("rpc", RpcFeature.class),
		setPrefs("setprefs", SetPrefsFeature.class),
		setTitle("settitle", SetTitleFeature.class),
		tabs("tabs", TabsFeature.class),
		views("views", ViewFeature.class);
		
		String featureName;
		Class<?> featureClass;
		ContainerFeature(String featureName, Class<?> featureClass)
		{
			this.featureName = featureName;
			this.featureClass = featureClass;
		}
		
		public String getFeatureName()
		{
			return featureName;
		}

		public Class<?> getFeatureClass()
		{
			return featureClass;
		}
	}
	
	@NeedsFeatures({
		@Feature(ContainerFeature.ads)
	})
	public interface NeedsAdsFeature
	{
		/**
		 * Returns the AdsFeature. 
		 * @return AdsFeature
		 */
		AdsFeature getAdsFeature();		
	}
	
	@NeedsFeatures({
		@Feature(ContainerFeature.dynamicHeight)
	})
	public interface NeedsDynamicHeightFeature
	{
		/**
		 * Returns the DynamicHeightFeature. 
		 * @return DynamicHeightFeature
		 */
		DynamicHeightFeature getDynamicHeightFeature();	
	}
	
	@NeedsFeatures({
		@Feature(ContainerFeature.googleAnalytics)
	})
	public interface NeedsGoogleAnalyticsFeature
	{
		/**
		 * Returns the GoogleAnalyticsFeature. 
		 * @return GoogleAnalyticsFeature
		 */
		GoogleAnalyticsFeature getGoogleAnalyticsFeature();
	}

	@NeedsFeatures({
		@Feature(ContainerFeature.lockedDomain)
	})
	public interface NeedsLockedDomain
	{
	}

	@NeedsFeatures({
		@Feature(ContainerFeature.minimessage)
	})
	public interface NeedsMinimessageFeature
	{
		/**
		 * Returns the MiniMessageFeature. 
		 * @return MiniMessageFeature
		 */
		MiniMessageFeature getMinimessageFeature();
	}
	
	
	@NeedsFeatures({
		@Feature(ContainerFeature.osapi)
	})
	public interface NeedsOsapiFeature
	{
		/**
		 * Returns the OsapiFeature. 
		 * @return OsapiFeature
		 */
		OsapiFeature getOsapiFeature();
	}

	@NeedsFeatures({
		@Feature(ContainerFeature.pubsub)
	})
	public interface NeedsPubsubFeature
	{
		/**
		 * Returns the PubsubFeature. 
		 * @return PubsubFeature
		 */
		PubsubFeature getPubsubFeature();
	}

	@NeedsFeatures({
		@Feature(ContainerFeature.rpc)
	})
	public interface NeedsRpcFeature
	{
		/**
		 * Returns the RpcFeature.
		 * @return RpcFeature
		 */
		RpcFeature getRpcFeature();
	}
	
	@NeedsFeatures({
		@Feature(ContainerFeature.setPrefs)
	})
	public interface NeedsSetPrefsFeature
	{
		/**
		 * Returns the SetPrefsFeature.
		 * @return SetPrefsFeature
		 */
		SetPrefsFeature getSetPrefsFeature();
	}
	
	@NeedsFeatures({
		@Feature(ContainerFeature.setTitle)
	})
	public interface NeedsSetTitleFeature
	{
		/**
		 * Returns the SetTitleFeature.
		 * @return SetTitleFeature
		 */
		SetTitleFeature getSetTitleFeature();
	}

	@NeedsFeatures({
		@Feature(ContainerFeature.tabs)
	})
	public interface NeedsTabsFeature
	{
		/**
		 * Returns the TabsFeature.
		 * @return TabsFeature
		 */
		TabsFeature getTabsFeature();
	}

	@NeedsFeatures({
		@Feature(ContainerFeature.views)
	})
	public interface NeedsViewFeature
	{
		/**
		 * Returns the ViewFeature. 
		 * @return ViewFeature
		 */
		ViewFeature getViewFeature();	
	}
}
