#The tournament-core module
This module is independent of the GUI and should provide service-methods and the model-pojos to the GUI module.

##Database
The core uses an h2-embedded Database that is locally saved. It uses OrmLite as an ORM to specify the database model and provide query methods.
Because of laziness the database access objects are also used by the GUI module as model pojos. This should be encapsuled later, but has no priority just yet.

##Services
A lot of services that manage the events and players and so on are provided that can be used by the GUI module.