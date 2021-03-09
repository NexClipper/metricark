import React from "react";

import PodsList from "./List/Pods";
import QueryRequest from "./QueryRequest";

function KubQueryDashboard() {
  return (
    <>
      <QueryRequest component={PodsList} type="list" field="pods" />
    </>
  );
}

export default KubQueryDashboard;
