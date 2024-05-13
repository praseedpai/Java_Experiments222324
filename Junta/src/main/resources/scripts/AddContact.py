#---------------- A Simple file to test jython integration
from  com.dltledgers.Services import ContactManagerService

RET_VAL = "TRUE"

ret = ContactManagerService.AddContact(contact)
if ( ret == False):
   RET_VAL = "FALSE"

