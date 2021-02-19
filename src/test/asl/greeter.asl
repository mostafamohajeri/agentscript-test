+!hello(Name) : #asString(Name).contains("Mr") == true =>
    #achieve(#executionContext.src,greetings("Sir"))
.

+!hello(Name) : #asString(Name).contains("Ms") == true || #asString(Name).contains("Mrs") =>
    #achieve(#executionContext.src,greetings("Madam"))
.

+!hello("John") =>
    #achieve(#executionContext.src,greetings("John"))
.


+!hi =>
    #achieve(#executionContext.src,greetings)
.
