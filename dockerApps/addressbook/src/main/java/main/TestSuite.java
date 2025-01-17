package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import tests.AddressBookAddAddressBookTest;
import tests.AddressBookAddGroupTest;
import tests.AddressBookAddMultipleAddressBookTest;
import tests.AddressBookAddMultipleGroupsTest;
import tests.AddressBookAssignToGroupTest;
import tests.AddressBookAssignToMultipleGroupsTest;
import tests.AddressBookCheckAddressBookTest;
import tests.AddressBookCheckBirthdayInfoTest;
import tests.AddressBookCheckMultipleAddressBookTest;
import tests.AddressBookCheckMultipleBirthdaysInfoTest;
import tests.AddressBookEditAddressBookTest;
import tests.AddressBookEditGroupTest;
import tests.AddressBookEditMultipleAddressBookTest;
import tests.AddressBookEditMultipleGroupsTest;
import tests.AddressBookPrintAddressBookTest;
import tests.AddressBookPrintMultipleAddressBookTest;
import tests.AddressBookRemoveAddressBookTest;
import tests.AddressBookRemoveGroupTest;
import tests.AddressBookRemoveMultipleAddressBookTest;
import tests.AddressBookRemoveMultipleGroupsTest;
import tests.AddressBookSearchAddressBookEmailTest;
import tests.AddressBookSearchAddressBookNameTest;
import tests.AddressBookSearchByGroupTest;
import tests.AddressBookSearchByMultipleGroupsTest;
import tests.AddressBookSearchMultipleAddressBookNameTest;
import tests.AddressBookRemoveFromGroupTest;
import tests.AddressBookRemoveFromMultipleGroupsTest;

@RunWith(Suite.class)
@SuiteClasses({

	AddressBookAddAddressBookTest.class,
	AddressBookSearchAddressBookNameTest.class,
	AddressBookSearchAddressBookEmailTest.class,
	AddressBookAddGroupTest.class,
	AddressBookAssignToGroupTest.class,
	AddressBookSearchByGroupTest.class,
	AddressBookCheckBirthdayInfoTest.class,
	AddressBookCheckAddressBookTest.class,
	AddressBookPrintAddressBookTest.class,
	AddressBookEditAddressBookTest.class,
	AddressBookEditGroupTest.class,
	AddressBookRemoveFromGroupTest.class,
	AddressBookRemoveGroupTest.class,
	AddressBookRemoveAddressBookTest.class,
	
	AddressBookAddMultipleAddressBookTest.class,
	AddressBookSearchMultipleAddressBookNameTest.class,
	AddressBookAddMultipleGroupsTest.class,
	AddressBookAssignToMultipleGroupsTest.class,
	AddressBookSearchByMultipleGroupsTest.class,
	AddressBookCheckMultipleBirthdaysInfoTest.class,
	AddressBookCheckMultipleAddressBookTest.class,
	AddressBookPrintMultipleAddressBookTest.class,
	AddressBookEditMultipleAddressBookTest.class,
	AddressBookEditMultipleGroupsTest.class,
	AddressBookRemoveFromMultipleGroupsTest.class,
	AddressBookRemoveMultipleGroupsTest.class,
	AddressBookRemoveMultipleAddressBookTest.class,

//    AddressBookSearchAddressBookTelephoneNegativeTest.class

})

public class TestSuite {}
