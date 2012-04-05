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
package org.cruxframework.crux.core.rebind;

import org.cruxframework.crux.core.i18n.DefaultServerMessage;


/**
 * Messages from generator.
 * @author Thiago
 *
 */
public interface GeneratorMessages 
{
	@DefaultServerMessage("[generator 001] - DeclarativeFactory not registered: ")
	String errorGeneratingRegisteredWidgetFactoryNotRegistered();

	@DefaultServerMessage("[generator 002] - Error for register client event handler. Controller: {0}. Error:{1}")
	String errorGeneratingRegisteredController(String controller, String errMesg);

	@DefaultServerMessage("[generator 003] - Error for register client formatter. Formatter: {0}. Error:{1}")
	String errorGeneratingRegisteredFormatter(String formatter, String errMesg);

	@DefaultServerMessage("[generator 004] - Error for generate client class {0}:")
	String errorGeneratingRegisteredElement(String errMsg);

	@DefaultServerMessage("[generator 005] - Error retrieving screen Identifier.")
	String errorGeneratingRegisteredElementInvalidScreenID();

	@DefaultServerMessage("[generator 006] - property {0} could not be created. This is not visible neither has a getter/setter method.")
	String registeredClientObjectPropertyNotFound(String name);

	@DefaultServerMessage("[generator 007] - Error generating class for declared message {0}.")
	String errorGeneratingDeclaredMessagesClassNotFound(String string);

	@DefaultServerMessage("[controller 001] - Method Not Found: ")
	String errorInvokingGeneratedMethod();

	@DefaultServerMessage("[generator 008] - Error for register CruxSerializable serializer. Serializer: {0}. Error:{1}")
	String errorGeneratingRegisteredCruxSerializable(String serializer, String localizedMessage);

	@DefaultServerMessage("[generator 009] - Error for generating screen wrapper: {0}.")
	String errorGeneratingScreenWrapper(String localizedMessage);

	@DefaultServerMessage("[generator 010] - Error for generating context wrapper: Invalid Method signature: {0}.")
	String errorContextWrapperInvalidSignature(String method);

	@DefaultServerMessage("[generator 011] - Error for generating context wrapper: Primitive Parameter not allowed: {0}.")
	String errorContextWrapperPrimitiveParamterNotAllowed(String method);

	@DefaultServerMessage("[generator 012] - Error for generating invoker wrapper: Invalid Method signature: {0}.")
	String errorInvokerWrapperInvalidSignature(String method);

	@DefaultServerMessage("[generator 013] - Error for invoking method. Serialization Error.")
	String errorInvokerWrapperSerializationError();

	@DefaultServerMessage("[generator 014] - Widget {0} not found.")
	String errorGeneratingRegisteredObjectWidgetNotFound(String name);

	@DefaultServerMessage("[generator 015] - Error for register client datasource. DataSource: {0}. Error:{1}")
	String errorGeneratingRegisteredDataSource(String dataSource, String message);

	@DefaultServerMessage("[generator 016] - DataSource class {0} must use annotation DataSourceColumn or implements BindableDataSource<T> interface and use annotation DataSourceBinding")
	String errorGeneratingRegisteredDataSourceNoMetaInformation(String name);

	@DefaultServerMessage("[generator 017] - DataSource class {0} can not use annotation DataSourceColumn if it implements BindableDataSource<T> interface.")
	String errorGeneratingRegisteredDataSourceConflictingMetaInformation(String name);

	@DefaultServerMessage("[generator 018] - DataSource class {0} has invalid annotatted information.")
	String errorGeneratingRegisteredDataSourceInvalidMetaInformation(String dataSourceClassName);

	@DefaultServerMessage("[generator 019] - DataSource not found: ")
	String errorGeneratingRegisteredDataSourceNotFound();
	
	@DefaultServerMessage("[generator 020] - Error generating invokable object: {0}")
	String errorGeneratingInvokableObject(String errMesg);

	@DefaultServerMessage("[generator 021] - Error for register client datasource. DataSource: {0}. Error: Can not realize the type for datasource generic declaration.")
	String errorGeneratingRegisteredDataSourceCanNotRealizeGenericType(String name);

	@DefaultServerMessage("[generator 022] - Error generating invokable object. Can not realize the type for generic declaration.")
	String errorGeneratingInvokableObjectCanNotRealizeGenericType();

	@DefaultServerMessage("[generator 023] - Error generating widget factory: {0}.")
	String errorGeneratingWidgetFactory(String localizedMessage);

	@DefaultServerMessage("[generator 024] - Error generating widget factory. Can not realize the type for generic declaration.")
	String errorGeneratingWidgetFactoryCanNotRealizeGenericType(String name);

	@DefaultServerMessage("[generator 025] - Error generating widget factory. Invalid attribute name: {0}.")
	String errorGeneratingWidgetFactoryInvalidAttrName(String attrName);

	@DefaultServerMessage("[generator 026] - Error generating widget factory. Widget does not have a valid setter for attribute: {0}.")
	String errorGeneratingWidgetFactoryInvalidProperty(String attrName);

	@DefaultServerMessage("[generator 027] - Error generating widget factory. invalid validation method: {0}.")
	String errorGeneratingRegisteredControllerInvalidValidateMethod(String validateMethod);

	@DefaultServerMessage("[generator 028] - Error generating widget factory. An element can not contains text and other children.")
	String errorGeneratingWidgetFactoryMixedContentNotAllowed();

	@DefaultServerMessage("[generator 029] - Error creating modules scanner. Can not create builder object.")
	String modulesScannerErrorBuilderCanNotBeCreated();

	@DefaultServerMessage("[generator 030] - Searching for modules.")
	String modulesScannerSearchingModuleFiles();

	@DefaultServerMessage("[generator 031] - Error parsing module file: {0}.")
	String modulesScannerErrorParsingModuleFile(String fileName);

	@DefaultServerMessage("[generator 032] - Error initializing modulesScanner. ErrorMsg: {0}")
	String modulesScannerInitializationError(String localizedMessage);

	@DefaultServerMessage("[generator 033] - Can not find the web classes dir.")
	String modulesScannerErrorFindingClassesDir();

	@DefaultServerMessage("[generator 034] - Can not find the module {0}.")
	String errorGeneratingRegisteredElementModuleNotFound(String module);

	@DefaultServerMessage("[generator 035] - Parameter Object {0} has no valid field for binding.")
	String errorGeneratingRegisteredObjectParameterObjectHasNoValidField(String name);
	
	@DefaultServerMessage("[generator 036] - No method found on service interface that matches the async method: {0}.")
	String cruxProxyCreatorMethodNotFoundOnServiceInterface(String name);

	@DefaultServerMessage("[generator 037] - UseSynchronizer Token only can be used with void return type on Async interface.")
	String cruxProxyCreatorInvalidReturnType(String simpleSourceName);

	@DefaultServerMessage("[generator 038] - Unable to find source for type {0}")
	String generatorSourceNotFound(String typeName);

	@DefaultServerMessage("[generator 039] - {0} is not an interface.")
	String generatorTypeIsNotInterface(String qualifiedSourceName);
	
	@DefaultServerMessage("[generator 040] - Cross document interface {0} does not follow the name pattern for cross document objects.")
	String crossDocumentInvalidCrossDocInterfaceName(String crossDocInterfaceName);
	
	@DefaultServerMessage("[generator 041] - Could not find the cross document controller for the interface {0}.")
	String crossDocumentCanNotFindControllerForInterface(String crossDocInterfaceName);

	@DefaultServerMessage("[generator 042] - The controller found for the interface {0} does not have the annotation @Controller.")
	String crossDocumentInvalidController(String crossDocInterfaceName);
	
	@DefaultServerMessage("[screenWrapper 001] - The method {0} from ScreenWrapper {1} must have no parameters.")
	String screenWrapperMethodWithParameters(String methodName, String screenWrpperName);
	
	@DefaultServerMessage("[screenWrapper 002] - The method {0} from ScreenWrapper {1} must return a subclass of com.google.gwt.user.client.ui.Widget.")
	String screenWrapperMethodReturningNonWidget(String methodName, String screenWrpperName);

	@DefaultServerMessage("[screenBinder 001] - The method {1} from ScreenBinder {0} has an invalid signature.")
	String screenBinderInvalidSignature(String qualifiedSourceName, String methodName);
	
	@DefaultServerMessage("[parameter 001] - Required parameter {0} is missing.")
	String requiredParameterMissing(String name);

	@DefaultServerMessage("[parameter 002] - Error parsing parameter {0}.")
	String errorReadingParameter(String name);

	@DefaultServerMessage("[screenFactory 001] - The id attribute is required for CRUX Widgets. On page {0}, there is an widget of type {1} without id.")
	String screenFactoryWidgetIdRequired(String screenId, String widgetType);

	@DefaultServerMessage("[screenFactory 002] - Can not create widget {0}. Verify the widget type.")
	String screenFactoryErrorCreatingWidget(String widgetId);

	@DefaultServerMessage("[screenFactory 003] - Error creating widget. Duplicated identifier: {0}.")
	String screenFactoryErrorDuplicatedWidget(String widgetId);

	@DefaultServerMessage("[screenFactory 004] - Multiple modules in the same html page is not allowed in CRUX.")
	String screenFactoryErrorMultipleModulesOnPage();
	
	@DefaultServerMessage("[screenFactory 005] - Error retrieving screen {0}. Error: {1}.")
	String screenFactoryErrorRetrievingScreen(String screenId, String errMsg);

	@DefaultServerMessage("[screenFactory 006] - Error creating widget {0}. Error: {1}.")
	String screenFactoryGenericErrorCreateWidget(String screenId, String errMsg);

	@DefaultServerMessage("[screenFactory 007] - Screen {0} not found!")
	String screenFactoryScreeResourceNotFound(String screenId);
	
	@DefaultServerMessage("[screenFactory 008] - Error parsing screen {0}. Details: {1}.")
	String screenFactoryErrorParsingScreen(String screenId, String errMsg);

	@DefaultServerMessage("[screenFactory 009] - Error parsing screen metaData. Screen {0}. ")
	String screenFactoryErrorParsingScreenMetaData(String screenId);

	@DefaultServerMessage("[screenFactory 010] - Datasource {0}, declared on screen {1}, not found!")
	String screenFactoryInvalidDataSource(String datasource, String screenId);
	
	@DefaultServerMessage("[screenFactory 011] - Formatter {0}, declared on screen {1}, not found!")
	String screenFactoryInvalidFormatter(String formatter, String id);
	
	@DefaultServerMessage("[screenFactory 012] - Serializable {0}, declared on screen {1}, not found!")
	String screenFactoryInvalidSerializable(String serializer, String id);
	
	@DefaultServerMessage("[screenFactory 013] - Controller {0}, declared on screen {1}, not found!")
	String screenFactoryInvalidController(String handler, String id);

	@DefaultServerMessage("[ScreenFactory 014] - To use this feature you need to enable crux2 old interfaces compatibility.")
	String crux2OldInterfacesCompatibilityDisabled();

	@DefaultServerMessage("[screenFactory 015] - No module declared on screen {0}.")
	String screenFactoryErrorNoModulesOnPage(String screenId);

	@DefaultServerMessage("[Screen 001] - Error setting property {0} for screen {1}.")
	String screenPropertyError(String property, String screenId);
	
	@DefaultServerMessage("[generator 043] - Error for register client event handler. Error:{1}")
	String errorGeneratingRegisteredController(String errMesg);

	@DefaultServerMessage("[generator 044] - Error generating invoker. Error:{0}")
	String errorGeneratingInvokerWrapper(String localizedMessage);

	@DefaultServerMessage("[generator 045] - Error generating invoker. Controller {0} not found.")
	String errorGeneratingInvokerControllerNotFound(String name);

	@DefaultServerMessage("[generator 046] - The Controller {0} is a CrossDocument controller. However, it does not implement the required interface {0}CrossDoc")
	String crossDocumentCanNotFindControllerCrossDocInterface(String canonicalName);

	@DefaultServerMessage("[generator 047] - Error generating invoker. Controller not found for interface {0}.")
	String errorGeneratingInvokerControllerNotFoundForWrapper(String canonicalName);

	@DefaultServerMessage("[generator 048] - Error Generating registered element. Can not retrieve module's list of screens.")
	String errorGeneratingRegisteredElementCanNotFoundScreens();

	@DefaultServerMessage("[generator 049] - Error Generating DataSource {0}. Can not retrieve identifier field {1}.")
	String errorGeneratingRegisteredDataSourceCanNotFindIdentifier(String name, String field);

	@DefaultServerMessage("[generator 050] - Error Generating DataSource. Column {1} from type {0} is invalid.")
	String errorGeneratingRegisteredDataSourceInvalidColumn(String name, String columnName);

	@DefaultServerMessage("[generator 051] - Error Generating DataSource {0}. Invalid Bound object. Primitive is not allowed")
	String errorGeneratingRegisteredDataSourceInvalidBoundObject(String name);

	@DefaultServerMessage("[generator 052] - Error searching for module pages. Module Name = {0}")
	String modulesErrorSearchingModulepages(String moduleName);

	@DefaultServerMessage("[generator 053] - Error generating widgetFactory. LazyCondition must declare one of properties : equals or notEquals")
	String errorGeneratingWidgetFactoryInvalidLazyCondition();

	@DefaultServerMessage("[generator 054] - Error Generating registered element. Can not retrieve current screen.")
	String errorGeneratingRegisteredElementCanNotFoundCurrentScreen();

	@DefaultServerMessage("[generator 055] - Error Generating value binding code for DTO [{0}], Field [{1}]. Circular Reference. ")
	String errorGeneratingValueBindingCodeCircularReference(String className, String fieldName);
	
	@DefaultServerMessage("[generator 056] - Can not read user.agent property.")
	String errorGeneratingRegisteredElementCanNotReadUserAgent();

	@DefaultServerMessage("[generator 058] - Error generating binding code for DTO [{0}], Field [{1}]. No getter method found. ")
	String errorGeneratingBindingCodeNoGetterMethodFound(String className, String fieldName);
	
	@DefaultServerMessage("[generator 057] -Can not read device.features property.")
	String errorGeneratingRegisteredElementCanNotReadDeviceFeatures();

	@DefaultServerMessage("[viewFactory 001] - Crux Meta Data contains an invalid meta element (without type attribute).")
	String viewFactoryMetaElementDoesNotContainsType();

	@DefaultServerMessage("[viewFactory 002] - Error Creating widget: {0}. See Log for more detail.")
	String viewFactoryGenericErrorCreateWidget(String errMesg);

	@DefaultServerMessage("[viewFactory 003] - Can not found widgetFactory for type: {0}.")
	String viewFactoryWidgetFactoryNotFound(String widgetType);

	@DefaultServerMessage("[viewFactory 004] - Error retrieveing widgetFactory for type {0}.")
	String viewFactoryErrorRetrievingWidgetFactory(String widgetType);

	@DefaultServerMessage("[widgetCreator 001] - The widget {1}, declared on screen {0}, must contain at least one child.")
	String widgetCreatorEnsureChildrenEmpty(String screenId, String widgetId);

	@DefaultServerMessage("[widgetCreator 002] - The widget {1}, declared on screen {0}, must contain a text node child.")
	String widgetCreatorEnsureTextChildEmpty(String screenId, String widgetId);

	@DefaultServerMessage("[widgetCreator 003] - The widget {1}, declared on screen {0}, must contain an inner HTML.")
	String widgetCreatorEnsureHtmlChildEmpty(String screenId, String widgetId);

	@DefaultServerMessage("[widgetCreator 004] - Error reading factory declaration.")
	String widgetCreatorErrorReadingFactoryDeclaration();

	@DefaultServerMessage("[widgetCreator 005] - Can not find ChildProcessor for :{0}.")
	String widgetCreatorCanNotFindProcessor(String childName);

	@DefaultServerMessage("[widgetCreator 006] - Error creating AttibuteProcessor.")
	String widgetCreatorErrorCreatingAttributeProcessor();

	@DefaultServerMessage("[widgetCreator 007] - Error running attribute processor for attribute {0}, from widget {1}, on screen {2}.")
	String widgetCreatorRunningAttributeProcessor(String attrName, String widgetId, String screenId);

	@DefaultServerMessage("[widgetCreator 008] - Error creating evtBinder.")
	String widgetCreatorErrorCreatingEvtBinder();

	@DefaultServerMessage("[widgetCreator 009] - Can not process the text property for widget {0}. The widget is not assignable to HasText and its factory does not define any property for text value.")
	String widgetCreatorInvalidTextProperty(String widgetId);

	@DefaultServerMessage("[widgetCreator 010] - Error invoking ChildProcessor method: {0}.")
	String widgetCreatorErrorRunningChildProcessor(String message);

	@DefaultServerMessage("[widgetCreator 011] - A TextProcessor child processor can not have any sibling processor defined.")
	String widgetCreatorTextProcessorWithSiblingElements();

	@DefaultServerMessage("[widgetCreator 012] - You can not define more than one agregator under the same parent processor.")
	String widgetCreatorVariousAgregatorsOnProcessor();

	@DefaultServerMessage("[widgetCreator 013] - Error creating ChildrenProcessor class: {0}.")
	String widgetCreatorErrorCreatingChildrenProcessor(String message);

	@DefaultServerMessage("[widgetCreator 014] - Invalid tagName for child processor.")
	String widgetCreatorInvalidTagName();

	@DefaultServerMessage("[widgetCreator 0015] - The widget {1}, declared on screen {0}, must contain a valid widget as child.")
	String widgetCreatorEnsureWidgetFail(String id, String parentWidgetId);
	
	@DefaultServerMessage("[eventProcessor 001] - Controller {0} , declared on screen {1},  not found.")
	String eventProcessorErrorControllerNotFound(String controller, String screenId);

	@DefaultServerMessage("[eventProcessor 002] - Screen {0} tries to invoke the method {2} on controller {1}. That method does not exist.")
	String eventProcessorErrorControllerMethodNotFound(String screenId, String controller, String method);

	@DefaultServerMessage("[eventProcessor 003] - Method {1} of Controller {0} is not exposed, so it can not be called from crux.xml pages.")
	String eventProcessorErrorControllerMethodNotExposed(String controller, String method);

	@DefaultServerMessage("[eventProcessor 004] - Method {1} of Controller {0} can not be exposed. It can throw a checked exception.")
	String eventProcessorErrorControllerMethodTrhowsException(String controller, String method);

	@DefaultServerMessage("[dataObjects 001] - Duplicated DataObject found: {0}.")
	String dataObjectsDuplicatedObject(String value);

	@DefaultServerMessage("[dataObjects 002] - Error initializing DataObjects: {0}.")
	String dataObjectsInitializeError(String localizedMessage);

	@DefaultServerMessage("[dataObjects 003] - DataObject [{0}], referenced by widget [{1}] not found.")
	String dataObjectNotFound(String dataObject, String widgetId);

	@DefaultServerMessage("[attributeProcessor 001] - Error parsing attribute value [{0}], of widget [{2}], on screen [{1}].")
	String rowCountProcessorErrorInvalidValue(String attributeValue, String id, String widgetId);

	@DefaultServerMessage("[deviceAdaptive 001] - DeviceAdaptive widget does not declare any valid template for device [{0}].")
	String deviceAdaptiveNoMappingForDevice(String device);

	@DefaultServerMessage("[deviceAdaptive 002] - DeviceAdaptive implementation class [{0}] is not a valid Controller. It must be annotated with @Controller annotation.")
	String deviceAdaptiveImplementationIsNotAController(String controllerClass);

	@DefaultServerMessage("[deviceAdaptive 003] - DeviceAdaptive implementation class [{0}] must externds the base class DeviceAdaptiveController.")
	String deviceAdaptiveImplementationIsNotADeviceAdaptiveController(String controllerClass);

	@DefaultServerMessage("[deviceFeatures 001] - Property device.features can not be assigned to value [{0}].")
	String deviceFeaturesInvalidOption(String value);

	@DefaultServerMessage("[deviceAdaptive 003] - Controller [{1}] can not be used on the deviceAdaptive template. Only the bound controller [{0}] can be refered.")
	String deviceAdaptiveInvalidController(String controllerName, String controller);

	@DefaultServerMessage("[ioc 004] - Error injecting field [{0}] from type [{1}]. Primitive fields can not be handled by ioc container.")
	String iocInjectionForPrimitivesNotAllowed(String name, String qualifiedSourceName);

	@DefaultServerMessage("[ioc 005] - Error injecting field [{0}] from type [{1}]. Static fields can not be handled by ioc container.")
	String iocInjectionForStaticNotAllowed(String name, String qualifiedSourceName);

	@DefaultServerMessage("[ioc 006] - IoC Error Class [{0}] not found.")
	String iocErrorClassNotFound(String className);

	@DefaultServerMessage("[ioc 007] - IoC Error Field [{1}] from class [{0}] is not a writeable property.")
	String ioErrorPropertyCanNotBeWritten(String qualifiedSourceName, String name);
}
