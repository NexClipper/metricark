![MetricArk Logo](https://raw.githubusercontent.com/NexClipper/metricark/main/assets/logo_h.png)

# MetricArk
 * Prometheus based status(State/Event/Metric) gather & exposer

## Installation
### 1. Endpoints configuration
You must have both (1) Redis and (2) Prometheus running and set their endpoints in src/main/resource/application.properties. Replace the existing values. 

For the frontend UI, create .env.local at where package.json is and add:
```
REACT_APP_API_BASEURL={MetricArk Endpoint}
```



### Openstack Authentication token
If Openstack API needs **project-scoped** token, should get **project name and domain id** by querystring.  
And use OpenstackClient.getProjectScopedAuthenticationTokenRequestBody method.


If Openstack API needs **domain-scoped** token, should get **domain id** by querystring.  
And use OpenstackClient.getDomainScopedAuthenticationTokenRequestBody method..


