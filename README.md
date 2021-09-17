![MetricArk Logo](https://raw.githubusercontent.com/NexClipper/metricark/main/assets/logo_h.png)

# MetricArk
 * Prometheus based status(State/Event/Metric) gather & exposer

## Installation
### 1. Endpoints configuration
You must have both (1) Redis and (2) Prometheus running and set their endpoints in src/main/resource/application.properties. Replace the existing values. 

For the frontend UI, add .env.local and add this:
```
REACT_APP_API_BASEURL={MetricArk Endpoint}
```
