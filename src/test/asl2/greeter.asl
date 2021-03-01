response("Hello","Welcome").
response("Ciao","Benvenuta").

+!accept_guest(Message) : response(Message,Response) =>
    #coms.achieve(#executionContext.src,greet(Response))
.

//+!receive(Message) =>
//    #coms.achieve("translator",translate(Message));
//    +waiting_for_response(Message
//.


