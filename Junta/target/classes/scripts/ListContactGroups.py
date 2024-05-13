from  com.dltledgers.Services import ContactGroupManagerService
RET_VAL = "FALSE"
#--------------- instantiate ContactGroupManagerService
test = ContactGroupManagerService()
#----------------- Retrieve ContactGroups
temp = test.RetrieveContactGroups("org.sqlite.JDBC","jdbc:sqlite:D:\\DLT_PROTO\\DATA\\Contacts.DB")
#------------------ Iterate and Spit
for s in temp:
   print(s.getGroupName())

RET_VAL = "TRUE"

