# MemoPay
Send and receive money from anywhere.

The app architecture is based on MVVM offline fast and single source of truth principle. Local repository is backed by LocalDataSource with Room and Remote repository is backed by retrofit/api.  When app goes offline, app shows data from LocalDataSource. Some test cases is introduced to test some features. Test cases are written using espresso, mockito and junit. For dependency injection the project used koin along with architecture components libraries.

Tools and technologies: <br>
Koin for Dependency injection<br>
Kotlin Coroutines for thread management like api calling, background tasks<br>
Retrofit for networking<br>
ViewPager2, RecyclerView with ListAdapter & DiffUtils for better efficiency<br>
Room for cache management<br>

<h1>Testing:</h1><br>
For testing purpose the app used mockito, espresso, coroutine test and junit<br>
Some basic unit & instrumentation test is performed to check the functionality thoroughly. 

<h1>Local Cache/Database:</h1><br>
Two table is used to store account and transaction related information which is 
<br>
<b>class Account(@PrimaryKey accountNumber, name, balance, currency, isFavorite, isOnline, profilePic)</b>

<b>Transaction(@PrimaryKey id, name, type, transactionAmount, currency, profileUrl, date, senderAccountNumber, receiverAccountNumber)</b>

For more information check the project implementation. 
