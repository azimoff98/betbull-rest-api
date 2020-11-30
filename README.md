# BETBULL-REST-API

Application works with **postgresql** database. 
By default connects locally installed postgres in port :5432,
with default credentials. But may be changed due to demand.
_That is why you must have locally installed potgres in default port :5432._
DDL Policy is defined as **update**.

When application runs for the first time it creates
two tables. **Player, Team**. And updates every next time
if update is necessary.

## Spring Profiles

Application has 3 profiles. **Test, Prod and Develop.**
For the demo version all 3 profiles are sharing
same resource.


## Documentation

All Api are exported with **swagger documentation**.

* [Swagger documentation reference](http:localhost:8080/swagger-ui.html)


 




