1. Initialize JHipster project
2. Add JHipster Entity
3. Remove @Bean in DatabaseConfiguration.java 
   => This is to connect to MongoDB Atlas. Mongobee retrieves all indexes, which isn't allowed in recent MongoDB versions
4. Add your Public IP address to MongoDB Atlas 
   => Currently *all* IPs have been whitelisted to the test database