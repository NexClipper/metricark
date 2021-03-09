import axios from "axios";

const instance = axios.create({
  baseURL: process.env.REACT_APP_API_BASEURL,
});

const ts = () => Math.floor(new Date().getTime() / 1000);

const request = {
  dashboard(areaNumber: number, clusterId = 1) {
    return instance.get(
      `/v1/cluster/${clusterId}/nexcloud/dashboard/area${areaNumber}`
    );
  },
  kubQueryList(fieldName: string, key = "kubernetes", clusterId = 1) {
    return instance.get(
      `/v1/cluster/${clusterId}/query/key/${key}/field/${fieldName}`
    );
  },
  kubQueryDetail(fieldName = "pods", name: string, clusterId = 1) {
    return instance.get(
      `/v1/cluster/${clusterId}/query/${fieldName}/name/${name}`
    );
  },
  podCpuUsage(name: string, clusterId = 1) {
    return instance.get(
      `/v1/cluster/${clusterId}/pod/${name}/resource/cpu/usage?start=${
        ts() - 3600
      }&end=${ts()}`
    );
  },
  podMemUsage(name: string, clusterId = 1) {
    return instance.get(
      `/v1/cluster/${clusterId}/pod/${name}/resource/memory/usage?start=${
        ts() - 3600
      }&end=${ts()}`
    );
  },
};

export default request;
