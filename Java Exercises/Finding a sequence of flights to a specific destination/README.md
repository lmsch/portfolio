This program loads in a text file containing information about recurring flights in Canada.
Given a departure and arrival airport, the program will attempt to find the shortest sequence of flights.

Data Format: CSV
operator, departure airport, arrival airport

Sample program:
```
Where are you departing from? ('exit') YYG
Where are you going to? ABV
Sorry there is no combination of flights leading from YYG to ABV.

Where are you departing from? ('exit' to quit) YYG
Where are you going to? YYT
The following flights lead from YYG to YYT: YYG to YYZ, YYZ to MCO and MCO to YYT.
```
Translating the above that reads that to get from Charlottetown you could travel from Charlottetown to Toronto, then Toronto to Orlando and finally Orlando to St. John's. Which is an example of the potential solution a depth or breadth first search will provide.




