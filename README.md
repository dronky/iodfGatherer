# iodfGatherer

rexx client + java host based on sockets.

1. Configure and save properties:
    Via panels (click gear icon) or edit host.properties file:
    - SYSTEMS = adcd1 ibmuser sys1;adcd2 ibmuser sys1 - array with your system address login password separated with ';'
    - #PORTS = 3344;3345 - socket ports for each connection. Number of ports must be equal to number of hosts. Optional.
    - ADDRESS = 10.200.11.111 - Socket host ip address (your PC ip)
    - CONSOLE = DEV99 - unique console name for issuing SDSF commands
    - MSGCLASS - you can specify specific msgclass for rexx client job submission (for example, msgclass with automatic job purge)

2. Start IODF Gatherer.
3. Wait when it collect IODF information from all systems:
 software iodf, hardware iodf, date and time of activation

![Alt text](doc/1.png?raw=true "Configure properties")
![Alt text](doc/2.png?raw=true "Start app")
![Alt text](doc/3.png?raw=true "Wait for results")
