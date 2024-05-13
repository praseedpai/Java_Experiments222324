from com.dltledgers.Services import ContactManagerService
#---------------- instantiate group manager service
RET_VAL = "FALSE"
test = ContactManagerService()
#-------- Retrieve Contacts from the DB
temp = test.RetrieveContacts("org.sqlite.JDBC","jdbc:sqlite:D:\\DLT_PROTO\\DATA\\Contacts.DB")
#-------- Iterate and Spit Stuff into the DB
for s in temp:
   print(s.getContactName())
   print("--------------------------")

RET_VAL = "TRUE"