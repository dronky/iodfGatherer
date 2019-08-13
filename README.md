# iodfGatherer

rexx client + java server based on sockets.
allows you exchange messages

Setup server.properties:
- SYSTEMS = adcd1:ibmuser:sys1;adcd2:ibmuser:sys1 - array with your system address:login:password separated with ';'
- PORTS = 3344;3345 - socket ports for each connection
- KEY = gsAGJ)Wss3 - random string key for client identification (should be removed later)
- ADDRESS = 10.200.11.111 - Socket server ip address (your PC)
- CONSOLE = DEV99 - unique console name for issuing SDSF commands
- MSGCLASS - you can specify specific msgclass for rexx client job submission (for example, msgclass with automatic job purge)
