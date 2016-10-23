# CS6378
Advanced Operating System or Distributed Systems

Implmentation of Message Passing framework with on distributed setting.

Every node sends an object to every other node in mentioned in the config file. 

There are three thread's implmented at every node namely Connection Thread (Handles the lower level connections) 
and remains in blocking mode until the it recieves a new connection requests , the Reader Thread(Applies the logic)
to the incoming objects and the sender thread which routes the object to the destination. 


