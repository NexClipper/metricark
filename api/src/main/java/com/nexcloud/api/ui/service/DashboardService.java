package com.nexcloud.api.ui.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nexcloud.api.client.PrometheusClient;
import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.util.Const;
import com.nexcloud.util.Util;

@Service
public class DashboardService {
	static final Logger logger = LoggerFactory.getLogger(DashboardService.class);
	
	@Autowired private PrometheusClient prometheusClient;
	
	/**
	 * UI Dashboard Area#1
	 * Controller, API Server Status, Scheduler, Nodes Count, Namespace Count, Docker Container Count
	 * Daemonset, Deployment, Replicaset, Statefulset, Pods, PVCs
	 * @param cluster_id
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getClusterDashboardArea1( String cluster_id )  throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String param								= null;
		JSONObject responseObject					= new JSONObject();
		JSONParser parser							= new JSONParser();
		JSONObject jsonObject 						= null; 
		JSONObject attrObj							= new JSONObject();
		try{
			// Controller Manger Status
			param									= "{container='kube-controller-manager'}";
			query									= "sum(kube_pod_container_status_ready{param}) by ( container)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            responseObject.put("controller", jsonObject.get("data"));
            
            // API Server Status
            param									= "{job=~'.*apiserver.*'}";
			query									= "sum(up{param})/count(up{param}) > bool 0";
			entityData								= prometheusClient.getQuery(query, param, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            responseObject.put("apiserver", jsonObject.get("data"));
            
            // Scheduler Status
            param									= "{container='kube-scheduler'}";
			query									= "sum(kube_pod_container_status_ready{param}) by ( container)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            responseObject.put("scheduler", jsonObject.get("data"));
            
            // Node total Count
            param									= "";
			query									= "count(kube_node_info)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());    
            attrObj.put("total", jsonObject.get("data"));
            
            // Node schedulable Count
            param									= "";
			query									= "count(kube_node_info)-sum(kube_node_spec_unschedulable)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("active", jsonObject.get("data"));
            responseObject.put("node", attrObj);
            
            // Namespace total Count
            attrObj									= null;
            attrObj									= new JSONObject();
            param									= "";
			query									= "count(kube_namespace_created)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("total", jsonObject.get("data"));
            
            // Namespace Active Count
            param									= "{phase='Active'}";
			query									= "sum(kube_namespace_status_phase{param})";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("active", jsonObject.get("data"));
            responseObject.put("namespace", attrObj);
            
            
            // Docker container running count
            param									= "{container_state='running'}";
			query									= "sum(kubelet_running_container_count{param})";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            responseObject.put("docker", jsonObject.get("data"));
            
            // Daemonset total count
            param									= "";
            attrObj									= null;
            attrObj									= new JSONObject();
			query									= "count(kube_daemonset_status_desired_number_scheduled)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("total", jsonObject.get("data"));
            
            // Daemonset total count
            param									= "";
			query									= "count(kube_daemonset_status_desired_number_scheduled==kube_daemonset_status_number_ready)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("active", jsonObject.get("data"));
            responseObject.put("daemonset", attrObj);
            
            // Deployment total count
            param									= "";
            attrObj									= null;
            attrObj									= new JSONObject();
			query									= "count(kube_deployment_spec_replicas)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("total", jsonObject.get("data"));
            
            // Deployment available count
            param									= "";
			query									= "count(kube_deployment_spec_replicas == kube_deployment_status_replicas_available)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("active", jsonObject.get("data"));
            responseObject.put("deployment", attrObj);
            
            // Replicaset total count
            param									= "";
            attrObj									= null;
            attrObj									= new JSONObject();
			query									= "count(kube_replicaset_spec_replicas)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("total", jsonObject.get("data"));
            
            // Replicaset available count
            param									= "";
			query									= "count(kube_replicaset_spec_replicas == kube_replicaset_status_ready_replicas)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("active", jsonObject.get("data"));
            responseObject.put("replicaset", attrObj);
            
            // Statfulset total count
            param									= "";
            attrObj									= null;
            attrObj									= new JSONObject();
			query									= "count(kube_statefulset_status_replicas)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("total", jsonObject.get("data"));
            
            // Statfulset available count
            param									= "";
			query									= "count(kube_statefulset_status_replicas == kube_statefulset_status_replicas_ready)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("active", jsonObject.get("data"));
            responseObject.put("statefulset", attrObj);
            
            // Pod total count
            param									= "";
            attrObj									= null;
            attrObj									= new JSONObject();
			query									= "sum(kube_pod_status_phase)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("total", jsonObject.get("data"));
            
            // Pod available count
            param									= "{phase='Running'}";
			query									= "sum(kube_pod_status_phase{param})";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            attrObj.put("active", jsonObject.get("data"));
            responseObject.put("pod", attrObj);
            
            // PV total count
            param									= "";
			query									= "count(kube_persistentvolumeclaim_info)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());            
            responseObject.put("pv", jsonObject.get("data"));
            
            resData.setData(responseObject);
            resData.setStatus((String)jsonObject.get("status"));
            
            //resData.setData((JSONObject)jsonObject.get("data"));
            //resData.setStatus((String)jsonObject.get("status"));
            
			resData.setResponse_code(entityData.getStatusCodeValue());
			resData.setMessage(Const.SUCCESS);

			response = new ResponseEntity<ResponseData>(resData, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			resData.setMessage(Util.makeStackTrace(e));
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	 * UI Dashboard Area#2
	 * Cluster CPU Usage(%), Used(core), Total(core) / Memory Usage(%), Used(byte), Total(byte) / Pod Usage(%),Used( count), Total(count) 
	 * @param cluster_id
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getClusterDashboardArea2( String cluster_id )  throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String param								= null;
		JSONObject responseObject					= new JSONObject();
		JSONParser parser							= new JSONParser();
		JSONObject jsonObject 						= null; 
		JSONObject attrObj							= new JSONObject();
		try{
			//////////////////////////////////// CPU /////////////////////////////
			// Cluster CPU Total(core)
			attrObj									= null;
            attrObj									= new JSONObject();
            param									= "";
			query									= "sum (machine_cpu_cores)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("total", jsonObject.get("data"));
            
			// Cluster CPU Used(core)
            param									= "";
			query									= "sum(kube_pod_container_resource_requests_cpu_cores)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("used", jsonObject.get("data"));
            
			// Cluster CPU Usage(%)
			param									= "{id='/'}";
			query									= "sum(container_cpu_usage_seconds_total{param})/sum(node_cpu_seconds_total)*100";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("usage", jsonObject.get("data"));
            responseObject.put("cpu", attrObj);
            
			//////////////////////////////////// Memory /////////////////////////////
			// Cluster Memory Total(byte)
			attrObj									= null;
            attrObj									= new JSONObject();
            param									= "";
			query									= "sum (machine_memory_bytes)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("total", jsonObject.get("data"));
            
			// Cluster Memory Used(byte)
            param									= "{id='/'}";
			query									= "sum (container_memory_working_set_bytes{param})";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("used", jsonObject.get("data"));
            
			// Cluster Memory Usage(%)
            param									= "{id='/'}";
			query									= "sum(container_memory_working_set_bytes{param})/sum(machine_memory_bytes)*100";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("usage", jsonObject.get("data"));
            responseObject.put("mem", attrObj);            
            
            
			//////////////////////////////////// POD /////////////////////////////
			// Cluster POD Total(count)
			attrObj									= null;
            attrObj									= new JSONObject();
            param									= "";
			query									= "sum(kube_node_status_allocatable_pods)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("total", jsonObject.get("data"));
            
			// Cluster POD Used(count)
            param									= "";
			query									= "sum(kube_pod_info)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("used", jsonObject.get("data"));
            
			// Cluster POD Usage(%)
            param									= "";
			query									= "sum(kube_pod_info) / sum(kube_node_status_allocatable_pods)";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("usage", jsonObject.get("data"));
            responseObject.put("pod", attrObj);            
            
            resData.setData(responseObject);
            resData.setStatus((String)jsonObject.get("status"));
            
            //resData.setData((JSONObject)jsonObject.get("data"));
            //resData.setStatus((String)jsonObject.get("status"));
            
			resData.setResponse_code(entityData.getStatusCodeValue());
			resData.setMessage(Const.SUCCESS);

			response = new ResponseEntity<ResponseData>(resData, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			resData.setMessage(Util.makeStackTrace(e));
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	 * UI Dashboard Area#3
	 * POD CPU, POD Memory, Node CPU, Node Memory
	 * @param cluster_id
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getClusterDashboardArea3( String cluster_id )  throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String param								= null;
		JSONObject responseObject					= new JSONObject();
		JSONParser parser							= new JSONParser();
		JSONObject jsonObject 						= null; 
		JSONObject attrObj							= new JSONObject();
		try{
			//////////////////////////////////// POD /////////////////////////////
			// POD CPU Used(core)
			attrObj									= null;
            attrObj									= new JSONObject();
            param									= "{image!='',name=~'^k8s_.*'}";
			query									= "sum (rate (container_cpu_usage_seconds_total{param}[5m])) by (pod) * 100 ";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("cpu", jsonObject.get("data"));
            
			// POD Memory Used(%)
            param									= "{image!='',name=~'^k8s_.*'}";
			query									= "sum (container_memory_working_set_bytes{param} > 0 ) by (pod) / sum(container_spec_memory_limit_bytes{param} > 0 ) by (pod) * 100";
			entityData								= prometheusClient.getQuery(query, param, param);
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("mem", jsonObject.get("data"));
            responseObject.put("pod", attrObj); 
            
            
			//////////////////////////////////// Node /////////////////////////////
			// Node CPU Usage (%)
			attrObj									= null;
            attrObj									= new JSONObject();
            param									= "{mode='idle'}";
			query									= "(1 - avg(rate(node_cpu_seconds_total{param}[2m])) by (kubernetes_node)) * 100";
			entityData								= prometheusClient.getQuery(query, param );
			jsonObject 								= (JSONObject) parser.parse(entityData.getBody());
            attrObj.put("cpu", jsonObject.get("data"));
            
			// Node Memory Usage (%)
            param									= "{id='/'}";
			query									= "sum(container_memory_working_set_bytes{param}) by(kubernetes_io_hostname)/sum(machine_memory_bytes) by (kubernetes_io_hostname) *100";
			entityData								= prometheusClient.getQuery(query, param );
            jsonObject 								= (JSONObject) parser.parse(entityData.getBody());                        
            attrObj.put("mem", jsonObject.get("data"));
            responseObject.put("node", attrObj); 
            
            resData.setData(responseObject);
            resData.setStatus((String)jsonObject.get("status"));
            
            //resData.setData((JSONObject)jsonObject.get("data"));
            //resData.setStatus((String)jsonObject.get("status"));
            
			resData.setResponse_code(entityData.getStatusCodeValue());
			resData.setMessage(Const.SUCCESS);

			response = new ResponseEntity<ResponseData>(resData, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			resData.setMessage(Util.makeStackTrace(e));
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
