# Sample App with Http Basic AuthN, WebFlux, and Reactive Database Interaction

To setup this app, you will need a couple of dependencies.

Firstly, you will need to run a postgres database in an environment
that you have access to (such as your local machine). I think it should work
with any reasonably recent version of postgres (9,10,11,12,13). Create a
database named `segbaus`, and create a relation called `customer`
with an `id` column as a UUID type. Insert a couple uuids in there to
test it out.

Secondly, you will need to have Java installed (should work with 8+, but
tested in java 17 environment).

Once the db is up and running, put the following variables into the 
environment of the java process that executes the app:

1. PG_PASSWORD, the password of the postgres user in the database

You can modify the other database configurations (such as database name)
by editing `R2dbcConfiguration.java`.

Build the app by running `./mvnw package`. This should work on most common
operating systems.

Then, in this directory, run `java -jar ./target/segbaus-0.0.1-SNAPSHOT.jar`.

```
    # Get user ids you put in the database.
    curl --user seg:baus http://localhost:8080/customer
    
    # Get a count as a stream.
    curl --user seg:baus http://localhost:8080/count
```

Hope it works!
