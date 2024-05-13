#---------------- A Simple file to test jython integration
from  com.dltledgers.Services import ContactGroupManagerService
print(contactgrp.getGroupId())
print("-------")
print(contactgrp.getGroupName())
print("=============")
print(contactgrp.getGroupDescription())
print("endofrecord")
RET_VAL = "TRUE"
ret = ContactGroupManagerService.CreateGroup(contactgrp)
if ( ret == False):
   RET_VAL = "FALSE"

