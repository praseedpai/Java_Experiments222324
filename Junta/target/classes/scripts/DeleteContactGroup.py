#---------------- A Simple file to test jython integration
from  com.dltledgers.Services import ContactGroupManagerService
#-------------------- Delete a ContactGroup
RET_VAL = "FALSE"
ret = ContactGroupManagerService.Delete(groupid)
if (ret == True):
    RET_VAL = "TRUE"