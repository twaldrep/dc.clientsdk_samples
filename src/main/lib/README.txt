Add your selected packaging of the Data Integrator Client SDK here
These can be found in your Data Integrator Client Tools installation

di-cli-client-<version>-with-deps.jar supports both ConnectionType.LOCAL and ConnectionType.REMOTE
As shown in the samples, using this packaging makes it possible for embedders 
to create client code that executes jobs, then reuse that in either with either ConnectionType, 
simply by changing the configuration passed to the ConnectionFactory

di-client-sdk-local-<version>.jar supports only ConnectionType.LOCAL
di-client-sdk-remote-<version>.jar supports only ConnectionType.REMOTE
These packagings are provided for embedders who need only one ConnectionType, 
who may want to avoid reduce the risk of version conflicts for 
third-party dependencies specific to the unused ConnectionType.
