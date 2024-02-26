## Assumption & Discussion
### Assumption1 : Sold-out tickets should not be displayed
- 0 amount of ticketID is available in the database and when performing get("/users/:UserId/lotteries"),
  but it will not shown when performing get("/lotteries")
  ```
  {
    "tickets": [ ]
  }
  ```
  this will be illustrated after create lottery
  ```
  {
    "ticket": "666669",
    "price": 40.0,
    "amount": 2
  }
  ```
  Summary, It is business logic not to display lotteries that are out of stocks.
### Assumption2 : Sold ticket will not be returned to the System
- it will not increase amount of ticket in stock. 
### Discussion1 : Dockerfile
- ensure that docker run the current version of my application. you can use these commands below to make sure it's not behind.
```
  docker system prune -a
```
```
  docker-compose up
```
  enjoy!!!
### Discussion2 : Why Testing Repository?
- sometimes, Repositories's method can be affected by @Query and may not work properly as expected.
### Discussion3 : About Testing Repository
- make sure that DB is connected before testing. this test will use Repositories's method, but not store new value in DB. By Contrast, the number of ID may vary.
```
  
@Query("SELECT e FROM YourEntity e WHERE e.someProperty = :someValue")
    List<YourEntity> findMany(@Param("someValue") String someValue);
```
example of case that may affect return value of default Repositories's methods
### Discussion4 : ABout Conflict Exception
- this is used to handle some bussiness rules.
    ex1. buying ticket that already existed
    ex2. creating ticket that already existed
### Discussion5 : Pull Request
- As of understanding previously I thought I must pr on kbtg branch and then merge. So, to got any 1 of PR that already merged I create brance "PR" to do this tasks. the change is only this message in Discussion5
