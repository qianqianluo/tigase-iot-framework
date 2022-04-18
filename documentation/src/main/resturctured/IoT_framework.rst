Tigase IoT Framework
=====================

Overview
---------

.. figure:: ../../asciidoc/images/hardware.jpg
   :alt: Example project

   Figure 1. Example project

Tigase IoT Framework project is a composite project for more and more popular idea of IoT enabled devices. It is based on Jaxmpp library and Jaxmpp Bot Framework for providing XMPP connectivity between devices.

This project is modular and consist of following modules:

**runtime**
   Contains main classes of a framework. It is responsible for connectivity and general functionalities.

**devices**
   Provides API and base classes for implementations of sensors or executor devices.

**devices-rpi**
   Provides support for particular sensors or executors which are supported on Raspberry Pi platform. It is responible for communication between framework and actual devices connected to Raspberry Pi.

**client**
   Web based UI for interaction with devices, including device management task

**client-library**
   Library for interaction with application using Tigase IoT Framework installed and running on a device using XMPP protocol with support for GWT compilation. It provides classes used by UI.

Building
---------

Requirements
^^^^^^^^^^^^^^

Tigase IoT Framework is based on the newest (not yet released) version of JaXMPP Bot Framework. It will automatically download required dependecies from Maven repository.

Building Framework
^^^^^^^^^^^^^^^^^^^^^^

Tigase IoT Framework binaries can be built with Gradle.

.. code:: bash

   ./gradlew build publishToMavenLocal distZip

This line will compile whole framework (including Raspberry Pi releated stuff), install it to Gradle and Maven local repositories and create simplest possible bundle of a framework with only most necessary features. This bundle will be created in a directory ``runtime/build/distributions/``.

Additionally it will create a WAR archive with Tigase IoT Web Client in a directory ``client/build/libs/``. File will be named ``iot-client-2.0.0-SNAPSHOT.war``.

.. Tip::

   If you want want to start Tigase IoT Web Client locally without deploying it to a web server you may start it with ``./gradlew gwtDev`` which will start it using GWT development mode.

Running
--------

Requirements
^^^^^^^^^^^^^^

Tigase IoT Framework requires Tigase IoT Hub to be installed and running in the same network as Tigase IoT Framework. IoT Framework may be started before IoT Hub but it will not be operational as for configuration and management IoT Hub is required. For details about Tigase IoT Hub installation and configuration please check Tigase IoT Hub documentation.

Running framework
~~~~~~~~~~~~~~~~~~~~

Unpack the distribution zip file you created during the build process, then navigate to the directory where it is unpacked. Now we may run the bot.

.. code:: bash

   $ cd tigase-iot-framework-2.0.0-SNAPSHOT/
   $ $ ./bin/tigase-iot-framework
   2016-11-14 10:55:21.716 [main]             Kernel.createNewInstance()         FINER:    [root] Creating instance of bean defaultBeanConfigurator
   2016-11-14 10:55:21.760 [main]             Kernel.createNewInstance()         FINER:    [root] Creating instance of bean defaultTypesConverter
   2016-11-14 10:55:21.761 [main]             Kernel.injectDependencies()        FINER:    [root] Injecting [tigase.bot.runtime.CustomTypesConverter@304a9d7b] to defaultBeanConfigurator:tigase.component.DSLBeanConfigurator#bean:defaultTypesConverter

INFO: At this point you need to have a running Tigase IoT Hub connected to the same network.

Tigase IoT Framework runtime understands some variables that can control it, they may be displayed using the --help switch.

.. code:: bash

   $ ./bin/tigase-iot-framework --help
   usage: tigase-iot-framework [options]

   Options:
   -h, --help                                   display this screen
   -v, --version                                display version
   -log, --logger-config <file>                 logger configuration file
   -D<key=value>,[key=value]                    set system property

Configuration of the bot framework will be store in config file, what is DSL file, at ``etc/config.tdsl``. There is no need to manually edit this file or prepare configuration, as IoT Framework will find local Tigase IoT Hub and automatically register in it and generate configuration for later usage.

Running Web UI
^^^^^^^^^^^^^^^

Package created during build of Web UI may be deployed to any Java based HTTP server (like ie. Tomcat) or it is possible to unzip content of this archive to any subdirectory and point web server like Apache or NGINX to this directory for serving data.

It is also possible to start Web UI without any web server by running following command:

.. code:: bash

   ./ gradlew gwtRun

This will start web UI using GWT SuperDevMode.

.. Note::

   It is required to build base part of a project with Gradle before building Web UI with Maven.

.. Note::

   It is best to have Web UI deployed on same server which hosts Tigase XMPP Server (HUB).

Installation
--------------

Tigase IoT Framework distribution package comes with startup scripts for ``systemd``. To use them please follow this steps to install Tigase IoT Framework and ``systemd`` startup scripts.

Create user and adjust permissions
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

As the first step we need to create user with which permissions IoT framework will be running

.. code:: bash

   sudo useradd --home-dir /home/tigase --create-home --system tigase

If you are installing Tigase IoT Framework on Raspberry PI and want to use GPIO, SPI or I2C connected devices then we need to add newly created user to proper groups

.. code:: bash

   sudo usermod -a -G spi,i2c,gpio tigase

Switching working directory
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

For next steps we need to switch working directory to home directory of user ``tigase``

.. code:: bash

   cd /home/tigase

Download distribution package
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. Note::

   We assume that you have ``wget`` utility installed.

We are going to change working directory to ``tigase`` user home path and download newest version of Tigase IoT Framework:

.. code:: bash

   sudo -u tigase wget http://build.tigase.org/nightlies/dists/latest/tigase-iot-framework.zip

Unpacking distribution package
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To unpack distribution package and create symlink for easier access we are executing following commands:

.. code:: bash

   sudo -u tigase unzip tigase-iot-framework.zip
   sudo -u tigase ln -s tigase-iot-framework-2.0.0-SNAPSHOT-b62 tigase-iot-framework

.. Warning::

    We assumed that ``tigase-iot-framework-2.0.0-SNAPSHOT-b62`` is name of the directory unpacked from ``tigase-iot-framework.zip``.

Installation of startup scripts
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code:: bash

   sudo cp -r tigase-iot-framework/etc/default/* /etc/default/
   sudo cp -r tigase-iot-framework/etc/systemd/* /etc/systemd/

   sudo systemctl daemon-reload

Enabling Tigase IoT Framework
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code:: bash

   sudo systemctl enable tigase-iot-framework

Summary
^^^^^^^^

At this point we have Tigase IoT Framework installed and configured to start after restart. From now on we can:

Start framework
~~~~~~~~~~~~~~~

.. code:: bash

   sudo systemctl start tigase-iot-framework

Stop framework
~~~~~~~~~~~~~~

.. code:: bash

   sudo systemctl stop tigase-iot-framework

Check status of the framework
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code:: bash

   sudo systemctl status tigase-iot-framework

Upgrade
--------

You can easily upgrade Tigase IoT Framework to the new version following this steps.

Switching working directory
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

For next steps we need to switch working directory to home directory of user ``tigase``

.. code:: bash

   cd /home/tigase

Stopping old installation
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code:: bash

   sudo systemctl stop tigase-iot-framework


Download distribution package
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. Note::

   We assume that you have ``wget`` utility installed.

We are going to change working directory to ``tigase`` user home path and download newest version of Tigase IoT Framework:

.. code:: bash

   sudo -u tigase wget http://build.tigase.org/nightlies/dists/latest/tigase-iot-framework.zip

Unpacking distribution package
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To unpack distribution package and create symlink for easier access we are executing following commands:

.. code:: bash

   sudo -u tigase unzip tigase-iot-framework.zip

Copy configuration
^^^^^^^^^^^^^^^^^^^^

.. code:: bash

   sudo -u tigase tigase-iot-framework/etc/config.tdsl tigase-iot-framework-2.0.0-SNAPSHOT-b62/etc/

.. Warning::

    We assumed that ``tigase-iot-framework-2.0.0-SNAPSHOT-b62`` is name of the directory unpacked from ``tigase-iot-framework.zip``.

Replace old installation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code:: bash

   sudo -u tigase rm tigase-iot-framework
   sudo -u tigase ln -s tigase-iot-framework-2.0.0-SNAPSHOT-b62 tigase-iot-framework

Updating startup scripts
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code:: bash

   sudo cp -r tigase-iot-framework/etc/default/* /etc/default/
   sudo cp -r tigase-iot-framework/etc/systemd/* /etc/systemd/

   sudo systemctl daemon-reload

Starting Tigase IoT Framework
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code:: bash

   sudo systemctl start tigase-iot-framework

Summary
^^^^^^^^

At this point we have Tigase IoT Framework upgraded and running.

.. Tip::

   If everything works ok, you may safely delete old installation directory of Tigase IoT Framework.

Development
-------------

Tigase IoT Framework is an extended version of `Jaxmpp Bot Framework <https://projects.tigase.org/projects/jaxmpp-bot-framework>`__ which allows you to take all benefits and use all features provided by Jaxmpp Bot Framework, such as usage of Tigase Kernel as IoC container and so on.

.. Note::

   For more detailed information about usage of Jaxmpp Bot Framework please look into documentation of Jaxmpp Bot Framework.

*Values*

By default Tigase IoT Framework requires you to wrap value read from sensor in a class implementing ``tigase.iot.framework.devices.IValue``. This requirement was introduces to bond together timestamp (marking time when value was initially read) with a value and allows you to add additional informations as well, ie. temperature can be passed with correct scale, etc.

Currently we provided following implementations for storing following values:

-  temperature - ``tigase.iot.framework.values.Temperature``

-  light - ``tigase.iot.framework.values.Light``

-  movement - ``tigase.iot.framework.values.Movement``

For this classes we provide you with proper serializers and parsers used for transferring this values over XMPP protocol.

.. Warning::

    If you decide to add a new class then you will need to implement and provide Tigase IoT Framework with new parser and serializer (a bean extending ``AbstractValueFormatter`` with a support for your newly created implementation of ``IValue`` interface).

.. Warning::

    Additionally you will need to provide proper support for parsing this data in client library as in other case data from your sensor will not be available in UI.

*Device types*

In our framework device type is just a string to make it easy to extend it by adding new device/sensor types. Currently we provide devices with following device types and only this device types are supported by UI:

-  movement-sensor

-  tv-sensor

-  light-dimmer

-  light-sensor

-  temperature-sensor

To add support for a new device type, you need to override ``createDevice()`` method of ``Devices`` classes from ``client-library`` module and add there support for new device type. This will add support for a new device type in a client model layer. Additionally you need to add support for this new device inside ``DevicesListViewImpl`` to add support in a presentation layer.

.. Note::

   This separation on a client side is create for a reason. It is done in such a way to make presentation layer separate from model layer and allow reuse of a model layer in other clients with different presentation layer.

New sensor
^^^^^^^^^^^^^^

To add support for new a new sensor it is required to create a class which bases on ``tigase.iot.framework.devices.AbstractSensor``. This simple base class will take over of all required tasks and provide you with access to configuration and event bus.

Next you need to implement support for reading data/value from your sensor. When you have received new value, then wrap it in `instance of class implementing interface ``tigase.iot.framework.devices.IValue`` <#ivalue>`__ and call ``updateValue()`` method from your class extending ``AbstractSensor``. This method will then fire event with new value which will be delivered to every device which will be listening to state changes of your sensor.

Constructor of an ``AbstractSensor`` class requires in a parameter a type of a device - string value. This value is later on published in configuration of a device and used by UI to detect device type and use proper controls to display sensor and it’s state. Currently there is only a support for a few `device types <#device-types>`__.

.. Note::

   After you have your class implemented, you need to compile it and add to classpath of Tigase IoT Framework project and add it to configuration as a ``@Bean``

**Example (support for a PIR sensor - HC SR501).**

.. literalinclude:: ../../../../../devices-rpi/src/main/java/tigase/iot/framework/rpi/sensors/pir/HC_SR501.java


New sensor with periodical reads
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you want to create a support for a sensor which requires reads from actual sensor from time to time, then you should create class extending ``tigase.iot.framework.devices.AbstractPeriodSensor``. This class will allow you to specify how often to read data from a sensor and implement actual read from a sensor inside ``<T extends IValue> T readValue()`` method. Inside this method you will also need to wrap value you read in `instance of a class implementing ``tigase.iot.framework.devices.IValue`` <#ivalue>`__

Constructor of an ``AbstractSensor`` class requires in a parameter a type of a device - string value, and default period in which data from sensor will be read. Device type is later on published in configuration of a device and used by UI to detect device type and use proper controls to display sensor and it’s state. Currently there is only a support for a few `device types <#device-types>`__.

.. Note::

   After you have your class implemented, you need to compile it and add to classpath of Tigase IoT Framework project and add it to configuration as a ``@Bean``

.. Note::

   You may not use ``AbstractPeriodSensor`` as your direct base class. We provide you with other classes like ``I2CAbstractPeriodDevice`` or ``W1AbstractPeriodDevice``. They all are based on ``AbstractPeriodSensor`` and provide same functionality but allow you to access I2C, 1Wire easier.

.. Warning::

    Usage of ``W1AbstractPeriodDevice`` is tricky. To use it you need to use ``W1Master`` bean, enable it and register your implementation within ``W1Master`` providing it with key - implementation of ``com.pi4j.io.w1.W1DeviceType``. Classes ``tigase.iot.framework.rpi.sensors.w1.DS1820`` and ``tigase.iot.framework.rpi.sensors.w1.DS1820DeviceType`` are good examples of how to do this.

**Example (support for I2C sensor - BH1750).**

.. literalinclude:: ../../../../../devices-rpi/src/main/java/tigase/iot/framework/rpi/sensors/light/BH1750.java

New device
^^^^^^^^^^^

Not all devices are sensors. In typical use case you will have many devices providing some state by measuring data using external sensors and we call them here sensors. However very often you will have some devices which need to react on changed state of other devices or react on user action - this we call executor devices.

To implement a new executor device you need to create a new implementation of a sensor (executor device is a sensor as well as it reports it current state), which also implements ``IExecutorDevice`` inteface:

.. code:: java

   public interface IExecutorDevice<T> {

       void setValue(T value);

   }

.. Note::

   In typical usage ``T`` should implement ``IValue`` interface.

This method will be automatically called if new state will be published for this device (new state will be published on a device state node).

If your device requires to listen to states published by other sensors then it needs to implement ``NodesObserver`` with method ``getObserverdNodes()`` which needs to return list of state node in which your device is interrested in. Additionally you will have to implement following method:

.. code:: java

   @HandleEvent
   public void onValueChanged(ExtendedPubSubNodesManager.ValueChangedEvent event) {
   }

It will be called whenever new state/value will be published by any sensor or a device. You need to check ``source`` variable of a ``ValueChangedEvent`` to look for a node of a device in which state you are interested in. In ``value`` variable of a ``ValueChangedEvent`` you will find newly published device state which node you will find in ``source`` variable.

Quickstart
^^^^^^^^^^^

Tigase IoT Framework provides you with examples for creating support for a new devices in Java and Python. At https://tigase.tech/projects/tigase-iot-framework-examples/ you will find a project with examples which will help you get started with adding support for a new device. All you need to do is:

Clone a repository containing examples
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code:: bash

   git clone https://git.tigase.tech/tigase-iot-framework-examples/ tigase-iot-framework-examples

Choose a language of your choice
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We provide support for Java and Python, so you may write code for communication in Java or Python and only do some basic mapping in Java to expose your driver to the IoT Framework.


Open project with examples for the language which you selected
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You may edit source codes of the project in any text editor you want. But we would suggest usage of some IDE with support for Java and Gradle.


Modify example drivers
~~~~~~~~~~~~~~~~~~~~~~

At this point, you may select proper template and fill it with your code for communicating with the sensor.


Run your code
~~~~~~~~~~~~~

To make it easier, our example projects contain gradle ``run`` task, so you can start IoT framework with your driver with just one command:

.. code:: bash

   ./gradlew run

This command will start Tigase IoT Framework (new instance) and will try to connect to the IoT hub. All configuration of this instances will be stored in ``etc/config.tdsl`` file in your examples project directory.

Package your driver
~~~~~~~~~~~~~~~~~~~

When you are done, you may run

.. code:: bash

   ./gradlew jar

which will create a jar file which you may add to your existing Tigase IoT Framework installation.

.. Note::

   Jar file will be located at ``build/libs`` directory in your examples project directory.


Creating new project using Tigase IoT Framework
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Creating project
~~~~~~~~~~~~~~~~

If you would like to create new project using Tigase IoT Framework, then simplest solution will be to create new project using Gradle with following entries in @build.gradle@ file:

**Example project file for Gradle.**

.. code:: gradle

   group 'your.project.group'
   version 'your.project-version'

   apply plugin: 'java'
   apply plugin: 'application'

   mainClassName = "tigase.bot.runtime.Main"
   sourceCompatibility = 1.8
   targetCompatibility = 1.8

   publishing {
       publications {
           mavenJava(MavenPublication) {
               from components.java
           }
       }
   }

   repositories {
       maven {url "https://oss.sonatype.org/content/groups/public"}
   }

   dependencies {
       testCompile group: 'junit', name: 'junit', version: '4.12'
       compile group: 'tigase.iot', name: 'iot-runtime', version: '1.0.0-SNAPSHOT'
       compile group: 'tigase.iot', name: 'iot-devices', version: '1.0.0-SNAPSHOT'
       compile group: 'tigase.iot', name: 'iot-devices-rpi', version: '1.0.0-SNAPSHOT'
   }

Now you can focus on implementation of support for devices as described in other parts of this section.

Configuration of a project
~~~~~~~~~~~~~~~~~~~~~~~~~~

If classes containing implementations for your sensors are packaged and available in Tigase IoT Framework class path, then Tigase IoT Framework will load them and allow you to create new devices using drivers which you provided.

Building project
~~~~~~~~~~~~~~~~

Use of following command will build you project and package it to single zip archive with all dependencies and default configuration for easy deployment.

.. code:: bash

   ./gradlew distZip

Running project
~~~~~~~~~~~~~~~

To run this project on a device (ie. Raspberry Pi), copy distribution package to a device and unzip archive. Inside in ``bin`` directory there are project startup files named same as your project name, ie. ``test-project``

After running following command, project will start and try to connect to XMPP server as defined in project configuration file.

.. code:: bash

   ./bin/test-project

.. Note::

   It is possible to pass additional options or configuration options during startup. For a detailed description of this options please look into `section describing running <#running>`__ of Tigase IoT Framework.

.. Warning::

    If your project adds new type of sensors or new types of data then you will need to extend client and client-library modules to add support for them before you will be able to see this new device in a UI.

In our design IoT devices are connecting to Tigase IoT Hub acting as a central hub for all IoT devices. For communication IoT devices uses PubSub component deployed on XMPP server which acts as a message broker.

Initialization
^^^^^^^^^^^^^^^^^

After connection application creates PubSub nodes for every device or sensor connected to RPi and registered in application. Node name is always generated in following form: ``devices/iot-device-id``, where ``iot-device-id`` is ID of a IoT device connected to RPi. This node is created with type ``collection``. This newly created node is always a subnode of root PubSub node used by IoT devices named ``devices``.

As a result for devices with id’s ``iot-1``, ``iot-2`` and ``iot-3`` at this point we have following nodes created:

**devices**
   Root node *(collection)*

**devices/iot-1**
   Contains data related to iot-1 device *(collection)*

**devices/iot-2**
   Contains data related to iot-2 device *(collection)*

**devices/iot-3**
   Contains data related to iot-3 device *(collection)*

After this is completed then for every device framework creates 2 leaf subnodes for every device:

**config**
   Contains configuration of a device (including information about device type, options, etc.)

**state**
   Contains current state of a device (and previous states depending on configuration)

So for a device listed about we will get following nodes:

**devices**
   Root node *(collection)*

   **devices/iot-1**
      Contains data related to iot-1 device *(collection)*

      **devices/iot-1/state**
         Current state of iot-1 device

      **devices/iot-1/config**
         Configuration of iot-1 device

   **devices/iot-2**
      Contains data related to iot-2 device *(collection)*

      **devices/iot-2/state**
         Current state of iot-2 device

      **devices/iot-2/config**
         Configuration of iot-2 device

   **devices/iot-3**
      Contains data related to iot-3 device *(collection)*

      **devices/iot-3/state**
         Current state of iot-3 device

      **devices/iot-3/config**
         Configuration of iot-3 device

At this point application is ready for work.

Publishing data from sensors
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

When a sensor changes it’s state it emits event informing application that it’s state has changed. When application receives this information it serializes it to format similar to format used in `XEP-0323: Internet of Things - Sensor Data <http://www.xmpp.org/extensions/xep-0323.html:>`__ for data representation and publishes this data as a payload to device’s state PubSub node, ie. for ``iot-1`` measuring light intensity in ``lm`` following payload will be published at ``devices/iot-1/state``:

.. code:: xml

   <timestamp value="2016-12-10T19:56:26.460Z">
       <numeric unit="lm" automaticReadout="true" value="37" momentary="true" name="Light"/>
   </timestamp>

Reacting on change of sensor state
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Executor devices need to listen to changes of state of sensor devices and adjust state. To do so, executor device reports to application which sensors it want’s to observe and application automatically subscribes to particular nodes. After that every change of state published to nodes observer by device will be received from PubSub component by application, which will decode it from XML payload and will fire event which will be forwarded to executor device. This event will contain important information related to this change, like timestamp, value, unit, etc.

Publishing data to device
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

It is possible to publish value to a device, which allows you to change state of an executor device, ie. publish data forcing light to be turned on. Our framework will automatically detect this change of published state and forward it to executor device resulting in light being turned on.

Configuration
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Usually devices keep configuration on device itself. In our design only initial configuration is stored on device, which means that there is no need to locally store additional data between restart of a device.

For configuration storage we use ``config`` PubSub node of a particular device. This way a change to a configuration of device, new configuration needs to be published on device configuration node. Updated configuration will be automatically retrieved by application and applied to particular device without restart of an application.

We decided to keep in configuration all important information, ie. list of nodes device observes, etc. Due to that it is possible to retrieve device configuration in a web based client and change it, ie. to change sensor from which temperature is retrieved to adjust temperature in a room.

Configuration in stored in form of a `XEP-0004: Data Forms <http://xmpp.org/extensions/xep-0004.html:>`__, which makes it flexible and expandable.

User Interface
^^^^^^^^^^^^^^^^

For user interface we decided to use web based application due to fact that using using web page you can manage your IoT devices from a computer, mobile phone, tablet or smart tv.

It is very simple to retrieve list of devices as every subnode of a ``devices`` node represents device. Retrieving ``config`` node of that node allows us you easily to retrieve device type and current configuration, while using device type and data retrieved from ``state`` node allows to easily retrieve and observe state of a sensor or a device.

Requirements
--------------

There are a few requirement to use this project:

Device running IoT-XMPP bridge  [1]_ - Raspberry Pi
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

During our work we were using Raspbbery Pi 3 as a development device (due to already working and built-in WiFi), however project should work with any Raspberry Pi version.

.. Note::

   It is possible to use this project with other devices than Raspberry Pi as only ``devices`` module requires Raspbbery Pi to communicate with executor devices and sensors. You can provide your support for other platforms by providing alternative implementations of drivers.

Tigase IoT Hub
^^^^^^^^^^^^^^^

It is required to use `Tigase IoT Hub <http://tigase.tech/projects/iot-xmpp-hub/>`__. We recommend usage of the newest available version. Tigase IoT Hub is a modified version of Tigase XMPP Server with PubSub component which has ready to use configuration and many improvements making usage of IoT Hub and this framework a lot easier.

Configuration
~~~~~~~~~~~~~

Configuration of Tigase IoT Hub is stored in ``etc/config.tdsl`` file in DSL format and ready to use. You should not need to make any changes there.

However it is possible and configuration of Tigase XMPP Server is described in `Tigase XMPP Server Administation Guide <http://docs.tigase.org/>`__.


Network
^^^^^^^^^^^

A properly set up network on a Raspbbery Pi device with working connectivity to Tigase IoT Hub is required. Tigase IoT Hub and Tigase IoT Framework need to be connected to the same network.

.. [1]
   Application which is based on Tigase IoT Framework and connects (creates bridge) IoT unaware sensors and allows them to access XMPP network

.. image:: ../../asciidoc/images/hardware.jpg