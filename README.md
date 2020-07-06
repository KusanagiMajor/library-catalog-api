# library-catalog-api
Catalog of books for library, where you able to write and view book reviews, also accessable with REST.

## Installation

### Data Base
- First download and install latest stable Postgres version from [oficial site](https://www.postgresql.org/download/).
- Check version to be sure that it's installed properly `psql -V`
- Then create db for application:
```
sudo -u postgres createdb librarycatalog
```
- Make password for default user (by default app configured to user: `postgres` and password: `password`):
```
sudo -u postgres psql postgres
\password postgres
```

### Application
You can use compiled jar from `/artifacts` folder or build your own from source

#### How to build
- Download and Install Maven from [oficial site](https://maven.apache.org/)
- Go to project folder `cd /path/to/project/`
- Build .jar with maven command
```
mvn clean package spring-boot:repackage
```

After you managed to get .jar file, you need to set it up as service:
- First you need some directory where app is going to sit (as example `/home/pi/library-api/`)
- Then you need to create file `library.service` in `/etc/systemd/system` with your favorite editor

Here are what you need to save into this file
```
[Unit]
Description=Library Catalog API
After=network.target
After=postgres.service

[Service]
Type=simple
# Your app directory
WorkingDirectory=/home/pi/library-api/
# Command to launch .jar file, you might use .sh script instead if you want
ExecStart=java -jar library-catalog-api-1.0.jar
Restart=always

[Install]
WantedBy=multi-user.target
```
- Launch service with command: `sudo systemctl start library.service`

Also you can check status with command: `sudo systemctl status library.service`

And see logs in your app directory in `/log/output.log` or tail it from service with command: `sudo journalctl -u library.service -f`

### Configuration
- All default configuration you can find in `/src/main/resources/application.properties`
- Also you can create your own `application.properties` file in your app directory to override default configuration

### Usage
- You can find usage examples in tests
- Also there are generated REST documentation in `/docs` folder, just open index.html with any browser
