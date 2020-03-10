1. Initialize JHipster project
2. Add JHipster Entity
3. Remove @Bean in DatabaseConfiguration.java 
   => This is to connect to MongoDB Atlas. Mongobee retrieves all indexes, which isn't allowed in recent MongoDB versions
4. Add your Public IP address to MongoDB Atlas 
   => Currently *all* IPs have been whitelisted to the test database
5. Run 
   - "gradlew" to launch server
   - "npm start" to launch client with browsersync


ToDos
1. Apply authorization across all the buttons on the UI
2. Apply authorization across all the REST endpoints
3. Automated Course Recommendations for SMEs as well as Participants based on skillset matches
4. Add Footer
5. Modify top navigation
6. Search courses
7. Search SMEs for given skillsets
9. Attendence tracking