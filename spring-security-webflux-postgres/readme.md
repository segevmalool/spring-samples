# Sample App with Http Basic AuthN, WebFlux, and Reactive Database Interaction

## Install a Postgres Database 

Create a database named `segbaus`, and create a relation called `customer`
with an `id` column as a UUID type. Insert a couple uuids in there to
test it out.

On mac, I recommend homebrew services.

On windows, I recommend ![Postgres Downloads](https://www.postgresql.org/download), or 
you can try out chocolatey or something (no endorsement).

You can modify database configurations (such as database name or user)
by editing `R2dbcConfiguration.java`.

## Install Java

Tested with Java 17.

## Set up your environment
The required environment variables are listed in `env.sample`.

On mac: `export a=b`
On windows powershell: `[System.Environment]::SetEnvironmentVariable("a", "b")`

## Build and run the package
Build:
`./mvnw package`

Note that the environment must be properly configured to
load the application context (and to run the tests).

Run:
`java -jar ./target/segbaus-0.0.1-SNAPSHOT.jar`

Or use your favorite IDE integration

Test out the service using your browser for idp login:

- Get user ids you put in the database. `https://localhost:8443/customer`
    
- Get a count as a stream. `https://localhost:8443/count`

Hope it works!
