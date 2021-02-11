# REST API for the tourist board of a municipality.

Description:

The API supports adding, modifying, and activating / deactivating landmarks in a municipality.
The landmark contains a name, description, a couple of pictures and geographical coordinates (Lat & Lon).

Visitors can search by name and importance (important, very important, unavoidable) only active sights.

Visitors can give ratings of 1-5 for each of the sights, and the average rating should be displayed next to the sights.

The API is supported by several municipalities in several different countries.

Implementation with persistence in a relational database, using the java Spring stack.

The security part related to the manipulation of sights can be used only by registered (registered) users, while any (unregistered) user can rate the sights.

## Entity Relationship
![er](https://user-images.githubusercontent.com/54961231/107642757-93b4b880-6c75-11eb-8e68-1d1ef72a5aad.PNG)

## In memory users
ADMIN    
username: admin  
password: test    
role: ADMIN    

EDITOR  
username: editor  
password: test  
role: EDITOR  

## Endpoints
### has role ADMIN
#### COUNTRY
GET /api/v1/countries  
return all countries 

GET /api/v1/countries/{id}    
return country where countryId = {id}

POST /api/v1/countries  
save new country(name,activity,abbraviations...)

PUT /api/v1/countries/{id}    
update country where countryId = {id}

GET /api/v1/countries/active?active=false  
return all inactive countries

GET /api/v1/countries/active?active=true  
return all active countries

#### MUNICIPALITY

GET /api/v1/{countryId}/municipalities/  
return all active municipality from country where countryId = {countryId}
 
GET /api/v1/{countryId}/municipalities/{id}    
return municipality where municipalityId = {id}, from country where countryId={countryId}  

POST /api/v1/{countryId}/municipalities/  
save new municipality to country

PUT /api/v1/{countryId}/municipalities/{id}  
update municipality

GET /api/v1/{countryId}/municipalities/active?active=false  
get all inactive municipality from country where countryId = {countryId}

### SIGHTS
#### has any role ADMIN, EDITOR  

POST /api/v1/{municipalityId}/sights  
save new landmark

PUT /api/v1/{municipalityId}/sights/{id}  
update landmarks where sightId = {id}

PUT /api/v1/{municipalityId}/sights/{id}/active?active=false  
deactivate landmark, where sightId = {id}

PUT /api/v1/{municipalityId}/sights/{id}/active?active=true  
activate  landmark, where sightId = {id}

POST /api/v1/{municipalityId}/sights/{id}/upload  
upload new photo to landmark, where sightId = {id}  

DELETE /api/v1/{municipalityId}/sights/{id}/delete/{imageId}  
remove image by id from landmark

#### has no authorization

GET /api/v1/{municipalityId}/sights      
retrun all active sights

GET /api/v1/{municipalityId}/sights/{id}    
return landmark by id

SEARCH   

GET /api/v1/{municipalityId}/sights?name=   
search by name


GET /api/v1/{municipalityId}/sights?importance=   
search by importance

GET /api/v1/4/sights?importance=IMPORTANT&name=  
search by importance and name

REVIEW

POST /api/v1/4/sights/{id}  
add new review (rating)

GET /api/v1/4/sights/{id}/reviews  
get all reviews of sight where sightId = {id}




