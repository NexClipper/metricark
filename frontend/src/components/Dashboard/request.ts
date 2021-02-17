import axios from "axios";

const instance = axios.create({
  baseURL: process.env.REACT_APP_API_BASEURL,
});

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
  kubQueryDetail(id: string, fieldName = "pods", clusterId = 1) {
    return instance.get(
      `/v1/cluster/${clusterId}/query/${fieldName}/name/${id}`
    );
  },
};

export default request;
