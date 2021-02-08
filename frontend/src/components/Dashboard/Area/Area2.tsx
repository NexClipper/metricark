// @ts-nocheck
import React from "react";

import { ClusterUtilizationInfoRenderer } from "../Renderer";

import AreaRequest from "./Request";

const Area2Slice: React.FC<{
  data: any;
  dataKey: "cpu" | "mem" | "pod";
}> = ({ data, dataKey }) => {
  return (
    <ClusterUtilizationInfoRenderer
      dataKey={dataKey}
      data={data}
      cardSize={1 / 3}
    />
  );
};

const Area2 = () => {
  return (
    <AreaRequest
      areaNumber={2}
      dataKeys={["cpu", "mem", "pod"]}
      component={Area2Slice}
    />
  );
};

export default Area2;
