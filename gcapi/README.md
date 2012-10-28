GC-API
======

This is the API I created for use in my GC Scripts. Feel free to use
it however you like, but please give credit to me (Fuz on Powerbot)
if you happen to release a script using any of my API (Even though
you probably won't because my code is crap).

These are the current features or features that will be added at
some point:

	Utilities:
	
		> Logger (gcapi.utils.Logger) - Currently doesn't create the log file properly.
		
		> GE Price Checker - Not tested yet, haven't needed for anything so far.
		
		> Anti-random - Not sure if it works, not tested. Uses RoadProphet's skeleton.
		
		> Path Finder - Not started
	
	Listeners:
	
		> Interface Listener - Detects when an interface is opened/closed
		
	Methods:
	
		General use:
		
			> cleanUsername(String) - Removes the unicode character from the username (Displayed as a space, clever Jagex)
			
		Input:
		
			> sendKeys(String) - Types keys instantanously, more efficient when taking time to type is an issue
			
			> sendKeys(String, delayMin, delayMax) - same as above, but with a delay between each key
