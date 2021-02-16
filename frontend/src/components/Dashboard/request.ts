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
  pods(key = "kubernetes", clusterId = 1) {
    return instance.get(`/v1/cluster/${clusterId}/redis/key/${key}/pods`);
  },
};

export default request;
