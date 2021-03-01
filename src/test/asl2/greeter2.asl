+!hello(Name) : #asString(Name).contains("Mr") == true =>
    #coms.achieve(#executionContext.src,greetings("Sir"))
.

+!hello(Name) : #asString(Name).contains("Ms") == true || #asString(Name).contains("Mrs") =>
    #coms.achieve(#executionContext.src,greetings("Madam"))
.

+!hello("John") =>
    #coms.achieve(#executionContext.src,greetings("John"))
.

