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
And use OpenstackClient.getDomainScopedAuthenticationTokenRequestBody method.  


### Install guide for HANA
1. Pull docker image  
   docker pull repo.nexclipper.io/base-nex/metricark:hana-0.12  
2. Run docker image with environment parameters (Please modify environment parameters by HANA Openstack settings)  
   sudo docker run --name metricark -p 9000:9000 -d -e REDIS_ENDPOINT='http://metricops.nex-system.svc.cluster.local' -e REDIS_PORT='6379' -e PROMETHEUS_ENDPOINT='http://192.168.1.14:9090' -e OPENSTACK_USERDOMAIN='default' -e OPENSTACK_ENDPOINT='http://192.168.1.14' -e OPENSTACK_USERNAME='admin' -e OPENSTACK_PASSWORD='0000' -e OPENSTACK_SENLINPORT='8778' -e OPENSTACK_NEUTRONPORT='9696' repo.nexclipper.io/base-nex/metricark:hana-0.12  
3. Test APIs  (Please modify projectName and domainId by HANA Openstack settings)  
   Swagger: http://localhost:9000/swagger-ui/index.html  
   test example) List VMs  
   curl -X GET "http://localhost:9000/compute/v2.1/servers?projectName=admin&domainId=default" -H "accept: /"
